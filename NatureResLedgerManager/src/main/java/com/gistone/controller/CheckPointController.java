package com.gistone.controller;


import com.auth0.jwt.JWT;
import com.gistone.VO.ResultVO;
import com.gistone.annotation.PassToken;
import com.gistone.entity.*;
import com.gistone.mapper.*;
import com.gistone.pkname.Swagger;
import com.gistone.service.*;
import com.gistone.swagger.SharePoint;
import com.gistone.util.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import net.sf.json.JSONObject;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.*;

/**
 * <p>
 * 问题斑块表 前端控制器
 * </p>
 *
 * @author LiuXiong
 * @since 2019-08-13
 */
@RestController
@RequestMapping("/api/checkPoint")
@Api(value = "API-UserManage", tags = "问题斑块位管理接口", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
public class CheckPointController {

    @Autowired
    private ISt4ScsCdService iSt4ScsCdService;
    @Autowired
    private ISt4ScsCoService iSt4ScsCoService;
    @Autowired
    private ISt4ScsClService iSt4ScsClService;
    @Autowired
    private ISt4ScsChService iSt4ScsChService;

    @Autowired
    private ISt4PoCdSaService checkUserRelavantService;

    @Autowired
    private ISt4PoSaSgService st4PoSaSgService;
    @Autowired
    private ISt4ScsCrService st4ScsCrService;

    @Autowired
    private ISt4PoClCoService st4PoClCoService;

    @Autowired
    private ImageService service;
    @Autowired
    private ImageMapper mapper;
    @Autowired
    private ILmPointService iLmPointService;
    @Autowired
    private ImageConfigMapper imageConfigMapper;
    @Autowired
    private ImageConfigService imageConfigService;
    @Autowired
    private ImageNumberMapper imageNumberMapper;
    @Autowired
    private ImageNumber2Mapper imageNumber2Mapper;
    @Autowired
    private IImageTempService iImageTempService;

    @Autowired
    private St4ScsCdMapper st4ScsCdMapper;

    @Value("${ftp_host}")
    private String ftpHost;
    @Value("${ftp_port}")
    private Integer ftpPort;
    @Value("${ftp_username}")
    private String ftpUserName;
    @Value("${ftp_password}")
    private String ftpPassword;
    @Value("${ftp_pt}")
    private String ftpPt;
    @Value("${ftp_url}")
    private String ftpUrl;
    @Value("${dynamic_path}")
    private String dynamicPath;//动态工作空间路径
    @Value("${JYResult_PATH}")
    private String jYResultPATH;//解疑结果存储路径

    @Value("${PATH}")
    private String PATH;

    @ApiOperation(value = "image识别添加接口", notes = "此接口返回问题点批次数据", response = Result.class)
    @PostMapping("/insertImagerTemp")
    public ResultVO insertImagerTemp(@RequestBody @ApiParam(name = "任务批次添加接口", value = "json格式", required = true) Swagger<ImageTemp> data,
                                     HttpServletRequest request) throws FileNotFoundException, ParseException {
        ImageTemp it = data.getData();
        if(!ObjectUtils.isNotNullAndEmpty(it.getName())){
            return ResultVOUtil.error(ResultEnum.PARAMETEREMPTY.getCode(), "名称不能为空！");
        }
        if(!ObjectUtils.isNotNullAndEmpty(it.getZipUrl())){
            return ResultVOUtil.error(ResultEnum.PARAMETEREMPTY.getCode(), "识别的url不能为空！");
        }
        it.setUpdateDate(LocalDateTime.now());
        it.setShp(it.getShpurl());
        String type[] = {"shp","dbf","prj","sbn","sbx","shx"};
        String fileName  = it.getShpurl().substring(it.getShpurl().lastIndexOf("/") + 1 ,it.getShpurl().length());
        String fileUUid = fileName.substring(0,32);
        //循环遍历将解译结果下载至服务器
        for(String ty : type){
            FTPUtilUtil.downloadFile(ftpHost, ftpUserName, ftpPassword, ftpPort, dynamicPath  , jYResultPATH+ fileUUid, it.getShpurl().substring(it.getShpurl().lastIndexOf("/") + 1 ,it.getShpurl().length()).substring(0,it.getShpurl().substring(it.getShpurl().lastIndexOf("/"),it.getShpurl().length()).lastIndexOf(".")) + ty);
        }
        //将解译结果压缩
        ZipUtil.toZip(jYResultPATH+ fileUUid , jYResultPATH  , jYResultPATH+ fileUUid + ".zip"   , true);
        //解译结果下载路径存入数据库
        it.setResultUrl(jYResultPATH + fileUUid + ".zip");
        //构造判读数据对象，在自动解译成功后，将数据同步添加到image表中，才能将流程串起来进行第二个操作，标绘操作
        Image toPD = new Image();//构造判读数据对象
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        toPD.setUpdateDate(new Date());
        toPD.setCreateDate(sdf.format(it.getCreateDate()));
        toPD.setName(it.getName());
        toPD.setAuditDate(it.getAuditDate() == null ? null : sdf.parse(it.getAuditDate().toString()));
        toPD.setRemark(it.getRemark());
        toPD.setShp(jYResultPATH+ fileUUid + ".shp");
        //将数据添加至image表
        //service.save(toPD);
        //将文件上传至影像斑块地址，并且进行解析存入斑块表
        upload(request,jYResultPATH + fileUUid + ".zip" , toPD , fileUUid ,it);
        return ResultVOUtil.success();

    }
    public void upload(HttpServletRequest request , String filePath, Image image , String uuid ,ImageTemp it) {
        try {
            SimpleDateFormat ymdhms = new SimpleDateFormat("yyyy-MM-dd");
            String path = FileUtil.getPath(PATH+"/epr/image/");
            //上传
          /*  String[] arr = {"zip","ZIP"};

            Map resMap = FileUtil.uploadFile(request, path, arr, 3000000000l);  //限制3000MB
            if(null!=resMap.get("error")){
                return ResultVOUtil.error(ResultEnum.ERROR.getCode(),resMap.get("error")+"");
            }
            String newName = resMap.get("newName")+""; //附件名称
            */

            // String uuid = newName.split("\\.")[0];
            //解压
            File f = new File(path+uuid);
            if(!f.isDirectory()) f.mkdirs();
            FileUtil.unPackZip(filePath,path+uuid,null);

            //获取shp文件路径
            String shp = "";
            List<File> list = FileUtil.listFiles(new File(path+uuid));
            for (File pt : list) {
                if(pt.getName().endsWith(".shp")){
                    shp = pt.getPath();
                }
            }

            //读取SHP数据
            String shpStr = ShpUtil.readShapeFileToStr(shp,1)+"";
            //替换字段名
            net.sf.json.JSONArray jsonArray = net.sf.json.JSONArray.fromObject(shpStr);
            net.sf.json.JSONArray jSONArray = new net.sf.json.JSONArray();

            double area = 0; //总面积
            int plaque_number = 0; //斑块数量
            for (Object o : jsonArray){
                plaque_number ++;
                JSONObject jo = JSONObject.fromObject(o);
                JSONObject attributes = JSONObject.fromObject(jo.get("attributes"));
                JSONObject jSONObject1 = new JSONObject();

                /*String type = "农业用地";
                if(null!=attributes.get("type"))
                    type = attributes.get("type").toString();

                ImageConfig ic = imageConfigMapper.like(type);
                if(null!=ic){
                    jSONObject1.put("type",ic.getId());
                }else{
                    jSONObject1.put("type",imageConfigMapper.like("未识别类型").getId());
                }*/
                //匹配类型
           /*     String type = attributes.get("二级类") == null ? "":attributes.get("二级类").toString();
                if("".equals(type)){
                    type = attributes.get("一级类") == null ? "":attributes.get("一级类").toString();
                }
                ImageConfig ic = imageConfigMapper.like(type);
                if(null!=ic){
                    jSONObject1.put("type",ic.getId());
                }else{
                    jSONObject1.put("type",59);
                }*/
                //20191212暂时将解译结果设置为农业用地
                jSONObject1.put("type","1");
                jSONObject1.put("region",attributes.get("region"));
                jSONObject1.put("position",attributes.get("position"));
                jSONObject1.put("area",attributes.get("area")+"");
                area += Double.valueOf(attributes.get("area")+"");
                //gp服务分析出斑块中心点，进行转换存储
                String j = ShpUtil.DddToDms(attributes.get("CENTROID_X") == null ? null : Double.valueOf(attributes.get("CENTROID_X").toString()));
                String w = ShpUtil.DddToDms(attributes.get("CENTROID_Y") == null ? null :Double.valueOf( attributes.get("CENTROID_Y").toString()));
                //String j = ShpUtil.DddToDms(101.366);
                //String w = ShpUtil.DddToDms(36.36);
                jSONObject1.put("center",j+","+w);

                if (StringUtils.isNotBlank(image.getRemark()))
                    jSONObject1.put("remark",image.getRemark());
                if (StringUtils.isNotBlank(image.getName()))
                    jSONObject1.put("name","");

                if (null!=image.getCreateDate()){
                    jSONObject1.put("createDate",image.getCreateDate());
                }

                JSONObject geometry = JSONObject.fromObject(jo.get("geometry"));
                geometry.put("rings","\""+geometry.get("rings")+"\"");
                Map<String,String> map = new HashMap();
                map.put("attributes",jSONObject1+"");
                map.put("geometry",geometry+"");
                jSONArray.add(map);
            }
//            System.out.println(jSONArray);



            //生成新SHP
            String url = PathUtile.getRandomPath(PATH+"/epr/image/","x.shp");
            String res2 = ShpUtil.handleWebData(com.alibaba.fastjson.JSONArray.parseArray(jSONArray+""),url);

            //新SHP上传FTP
            String ftpPath = "/epr/image/"+ymdhms.format(new Date())+"/"+new Random().nextInt(20)+"/";//FTP保存路径
            String fileName1 = uuid+".shp";
            String fileName2 = uuid+".dbf";
            String fileName3 = uuid+".prj";
            String fileName4 = uuid+".sbn";
            String fileName5 = uuid+".sbx";

            FileInputStream input1 = new FileInputStream(new File(url.split("\\.")[0]+".shp"));
            FileInputStream input2 = new FileInputStream(new File(url.split("\\.")[0]+".dbf"));
            FileInputStream input3 = new FileInputStream(new File(url.split("\\.")[0]+".fix"));
            FileInputStream input4 = new FileInputStream(new File(url.split("\\.")[0]+".prj"));
            FileInputStream input5 = new FileInputStream(new File(url.split("\\.")[0]+".shx"));

            FTPUtil.uploadFile(ftpHost, ftpUserName, ftpPassword, ftpPort, ftpPath, fileName1, input1);
            FTPUtil.uploadFile(ftpHost, ftpUserName, ftpPassword, ftpPort, ftpPath, fileName2, input2);
            FTPUtil.uploadFile(ftpHost, ftpUserName, ftpPassword, ftpPort, ftpPath, fileName3, input3);
            FTPUtil.uploadFile(ftpHost, ftpUserName, ftpPassword, ftpPort, ftpPath, fileName4, input4);
            FTPUtil.uploadFile(ftpHost, ftpUserName, ftpPassword, ftpPort, ftpPath, fileName5, input5);

            //插入数据库
            HttpSession session = request.getSession();
            SysUser user = (SysUser) session.getAttribute("user");

            image.setShpurl("E:/FTP"+ftpPath+fileName1);
            if(null!=user)
                image.setCreateBy(user.getId());
            if(null!=user)
                image.setUpdateBy(user.getId());
            image.setShp(url); //本地SHP路径
            image.setRemark(image.getRemark());
            image.setDelFlag(1);
            image.setSign(1);
            image.setArea(area);
            image.setPlaqueNumber(plaque_number);
            it.setArea(area);//自动解译对象
            it.setPlaqueNumber(plaque_number);//自动解译对象
            if(null!=image.getCreateDate()){
                image.setCreateDate(image.getCreateDate());
            }
            boolean res = service.save(image);
            iImageTempService.save(it);//自动解译对象
            //从data中构造属性
            for (Object o : jSONArray) {
                Map<String, Object> datum = (Map<String, Object>) o;
                Map<String, Object> attributes = (Map<String, Object>) JSONObject.fromObject(datum.get("attributes"));
                //通过属性构造参数
                St4ScsCd iterpretation = new St4ScsCd();
                if (null != attributes.get("name")){
                    iterpretation.setActiveName(attributes.get("name") + "");
                }
                if (null != attributes.get("center")) {
                    String center = attributes.get("center") + "";
                    iterpretation.setCenter(center);
                    if(-1<center.indexOf("°")){
                        iterpretation.setCd013(center.split(",")[0]);
                        iterpretation.setCd014(center.split(",")[1]);
                        iterpretation.setCd002(PointHelp.Dms2D(center.split(",")[0]));
                        iterpretation.setCd003(PointHelp.Dms2D(center.split(",")[1]));
                        iterpretation.setCd015(1);
                    }else{
                        iterpretation.setCd013(PointHelp.toDfm(center.split(",")[0]));
                        iterpretation.setCd014(PointHelp.toDfm(center.split(",")[1]));
                        iterpretation.setCd002(center.split(",")[0]);
                        iterpretation.setCd003(center.split(",")[1]);
                        iterpretation.setCd015(0);
                    }
                }
                if (null != attributes.get("area")) {
                    iterpretation.setArea(attributes.get("area") + "");
                }
                if (null != attributes.get("position")) {
                    iterpretation.setPosition(attributes.get("position") + "");
                }
                if (null != attributes.get("region")) {
                    iterpretation.setRegion(attributes.get("region") + "");
                }
                if (null != attributes.get("type")) {
                    iterpretation.setActiveType(attributes.get("type") + "");
                }
                if (null != attributes.get("descri")) {
                    iterpretation.setDescri(attributes.get("descri") + "");
                }
                if (null != attributes.get("date")) {
                    iterpretation.setCd012(attributes.get("date") + "");
                }

                Map<String, Object> rings = (Map<String, Object>) JSONObject.fromObject(datum.get("geometry"));
                String geometry = rings.get("rings").toString();
                iterpretation.setGeometry(geometry);
                iterpretation.setImageId(image.getId());
                if(null!=user)
                    iterpretation.setCd010(user.getId());
                iterpretation.setCd011(LocalDateTime.now());
                iterpretation.setCd004(UUID.randomUUID().toString().replace("-", ""));
                int row = st4ScsCdMapper.insert(iterpretation);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**
     * 一张图展示，问题斑块按保护地查询
     * @param
     * @return
     */

    @ApiOperation(value = "一张图展示，问题斑块按保护地查询", notes = "此接口返回问题斑块数据数据", response = Result.class)
    @PostMapping("/listCheckPointToView")
    public Result listCheckPointToView(@RequestBody @ApiParam(name = "保护地id", value = "json格式", required = true) Swagger<St4ScsCd> data) {
        St4ScsCd param = data.getData();
        return iSt4ScsCdService.listCheckPointToView(param);
    }



    /**
     * 问题斑块列表
     * @param
     * @return
     */
    @PassToken
    @ApiOperation(value = "问题斑块列表", notes = "问题斑块列表", response = Result.class)
    @PostMapping("/listCheckPoint")
    public Result listCheckPoint(@RequestBody @ApiParam(name = "", value = "json格式", required = true) Swagger<St4ScsCd> data, HttpServletRequest request) {
        St4ScsCd param = data.getData();
        if(!ObjectUtils.isNotNullAndEmpty(param.getPageNumber())||!ObjectUtils.isNotNullAndEmpty(param.getPageSize())){
            return Result.build(1001,"pageSize,pageNumber"+ ResultMsg.MSG_1001);
        }
        String token = request.getHeader("token");// 从 http 请求头中取出 token
        String userId = JWT.decode(token).getAudience().get(0);
        St4SysSa seUser = new St4SysSa();
        seUser.setSa001(Integer.valueOf(userId));
        return iSt4ScsCdService.listCheckPoint(param,seUser);
    }

    /**
     * 问题斑块修改接口
     * @param
     * @return
     */
    @PassToken
    @ApiOperation(value = "问题斑块修改接口", notes = "问题斑块修改接口", response = Result.class)
    @PostMapping("/updateCheckPoint")
    public Result updateCheckPoint(@RequestBody @ApiParam(name = "", value = "json格式", required = true) Swagger<St4ScsCd> data) {
        St4ScsCd param = data.getData();
        if(!ObjectUtils.isNotNullAndEmpty(param.getCd001())){
            return Result.build(1001,ResultMsg.MSG_1001);
        }
        if(iSt4ScsCdService.updateById(param)){
            return  Result.build(1000,"修改"+ResultMsg.MSG_1000);
        }
        return  Result.build(1005,ResultMsg.MSG_1005);
    }

    /**
     * 问题斑块删除接口
     * @param
     * @return
     */
    @PassToken
    @ApiOperation(value = "问题斑块删除接口", notes = "问题斑块删除接口", response = Result.class)
    @PostMapping("/deleteCheckPoint")
    public Result deleteCheckPoint(@RequestBody @ApiParam(name = "", value = "json格式", required = true) Swagger<St4ScsCd> data) {
        St4ScsCd param = data.getData();
        if(!ObjectUtils.isNotNullAndEmpty(param.getCd001())){
            return Result.build(1001,ResultMsg.MSG_1001);
        }
        param.setCd009(0);
        if(iSt4ScsCdService.updateById(param)){
            return  Result.build(1000,"删除"+ResultMsg.MSG_1000);
        }
        return  Result.build(1005,ResultMsg.MSG_1005);
    }

    /**
     * 问题斑块添加接口
     * @param
     * @return
     */
    @PassToken
    @ApiOperation(value = "问题斑块添加接口", notes = "问题斑块添加接口", response = Result.class)
    @PostMapping("/insertCheckPoint")
    public Result insertCheckPoint( @RequestBody @ApiParam(name = "", value = "json格式", required = true) @Valid Swagger<St4ScsCd> data
            , HttpServletRequest request) {

        St4ScsCd param = data.getData();
        String token = request.getHeader("token");// 从 http 请求头中取出 token
        String userId = JWT.decode(token).getAudience().get(0);
        param.setCd010(Integer.valueOf(userId));

        return  iSt4ScsCdService.insertCheckPoint(param);
    }
    /**
     * 问题斑块详情接口
     * @param
     * @return
     */
    @PassToken
    @ApiOperation(value = "问题斑块详情接口", notes = "问题斑块详情接口", response = Result.class)
    @PostMapping("/getCheckPointById")
    public Result getCheckPointById( @RequestBody @ApiParam(name = "", value = "json格式", required = true)  Swagger<St4ScsCd> data
            ) {

        St4ScsCd param = data.getData();
        if(!ObjectUtils.isNotNullAndEmpty(param.getCd001())){
            return Result.build(1001,"问题斑块cd001"+ResultMsg.MSG_1001);
        }
        param = iSt4ScsCdService.getById(param.getCd001());
        Result res = new Result();
        res.setStatus(1000);
        res.setMsg("加载"+ResultMsg.MSG_1000);
        Integer taskId = param.getCl001();
        St4ScsCl cl = iSt4ScsClService.getById(taskId);
        if(ObjectUtils.isNotNullAndEmpty(cl.getCl002())){
            param.setTaskName(cl.getCl002());
        }

        try{
            res.setData(BeanUtils.describe(param));
        }catch (Exception e){
            e.printStackTrace();
            return Result.build(1003,ResultMsg.MSG_1003);
        }

        return  res;
    }

    /**
     * 下发问题斑块
     * @param spSwagger
     * @return
     */
    @ApiOperation(value="下发问题斑块",notes = "下发问题斑块",response = St4PoCdSa.class)
    @RequestMapping(value="/sharePoint",method = RequestMethod.POST)
    public ResultVO sharePoint(@ApiParam(name="下发问题斑块", value="json格式", required=true)@RequestBody Swagger<SharePoint> spSwagger) {
        /**
         * 这里的业务逻辑是这样的:
         * 1.拿到传递过来的任务id去找到对应的台账(可能是多个)
         * 2.在拿每一个台账的id去拿斑点的的id
         * 3.最终是把斑点下发到人员
         */
        SharePoint sp = spSwagger.getData();

        List<Integer> pointList = sp.getPointIdList();
        List<Integer> uids = sp.getUidList();
        if(!ObjectUtils.isNotNullAndEmpty(pointList)){
            return ResultVOUtil.error(ResultEnum.PARAMETEREMPTY.getCode(), "问题斑块不能为空！");
        }
        if(uids==null||uids.size()<1){
            return ResultVOUtil.error(ResultEnum.PARAMETEREMPTY.getCode(), "下发人员不能为空！");
        }
        Integer taskId = sp.getTaskId();
        if(!ObjectUtils.isNotNullAndEmpty(taskId)||0==taskId){
            return ResultVOUtil.error(ResultEnum.PARAMETEREMPTY.getCode(), "所属任务ID不能为空！");
        }
        return checkUserRelavantService.givePoint(uids,pointList,taskId);

    }





}

