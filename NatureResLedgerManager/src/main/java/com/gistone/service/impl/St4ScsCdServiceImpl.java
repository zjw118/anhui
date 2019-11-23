package com.gistone.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gistone.VO.ResultVO;
import com.gistone.entity.*;
import com.gistone.mapper.*;
import com.gistone.service.ISt4ScsCdService;
import com.gistone.util.*;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.ws.rs.HEAD;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

/**
 * <p>
 * 问题点表 服务实现类
 * </p>
 *
 * @author zhaojingwei
 * @since 2019-08-13
 */
@Service
@Component
public class St4ScsCdServiceImpl extends ServiceImpl<St4ScsCdMapper, St4ScsCd> implements ISt4ScsCdService {

    @Autowired
    private St4ScsCdMapper st4ScsCdMapper;
    @Autowired
    private St4PoCdSaMapper st4PoCdSaMapper;
    @Autowired
    private St4ScsCkMapper st4ScsCkMapper;
    @Autowired
    private St4SysSaMapper st4SysSaMapper;
    @Autowired
    private ImageMapper imageMapper;
    @Autowired
    private ImageConfigMapper imageConfigMapper;

    @Autowired
    private ConfigUtils configUtils;

    @Value("${PATH}")
    private String PATH;



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

    @Override
    public ResultVO deletePersonAndPoint(Integer uid, List<Integer> points) {
        QueryWrapper<St4PoCdSa> cdSaQueryWrapper = new QueryWrapper<>();
        cdSaQueryWrapper.eq("sa001", uid);
        cdSaQueryWrapper.in("cd001",points );
        if(st4PoCdSaMapper.delete(cdSaQueryWrapper)>0){
            return ResultVOUtil.success();
        }else {
            return ResultVOUtil.error("1222","处理结果失败");
        }

    }
    @Override
    public ResultVO getPersonAndPoint(Integer data) {
       // List<St4SysSa
        List<St4SysSa> list = st4ScsCdMapper.getPersonAndPoint(0);
//        List<String> sa001List = null;
//        for (Map<String,Object> map:list) {
//            sa001List.add(map.get("cd001").toString());
//        }
//        List<Map<String,Object>> listR = new ArrayList<>();/listReserveData
//        Map<String,Object> mapr = new HashMap<>();
//        for (Map<String,Object> map:list) {
//            String cdid = map.get("cd001").toString();
//            if(ObjectUtils.isNotNullAndEmpty(map.get("cd001"))){
//                if(sa001List!=null&&sa001List.contains(map.get("cd001").toString())){
//                    mapr.
//                }
//            }
//
//        }
//        List<Map> listR = new ArrayList<>();

        return ResultVOUtil.success(list);
    }



    @Override
    public ResultVO getProblemPlaque(St4ScsCd data) {
        //List<St4ScsCd> cdList = new ArrayList<>();
        try {
//            int size = data.getPageSize();//每页条数
//            int number = data.getPageNumber();//开始索引
//            int numberReal =0;
//            if(0==number){
//                numberReal = number;
//            }else{
//                numberReal= (number-1)*size;
//            }
//            data.setPageNumber(numberReal);
//            data.setPageSize(size);
            List<St4ScsCd> list  = st4ScsCdMapper.getProblemPlaque(data);
            data.setPageNumber(null);
            data.setPageSize(null);
            Result result = new Result();
            //Integer tsize =st4ScsCdMapper.getProblemPlaque(data).size();
           // result.setTotal(tsize);
            result.setData(list);
           // result.setPage((int)Math.ceil((double)tsize/size));
            return ResultVOUtil.success(result);
        }catch (Exception e){
            e.printStackTrace();
            return ResultVOUtil.error("1222", ResultMsg.MSG_1003);
        }

    }


    @Override
    public Result listCheckPointToView(St4ScsCd data) {
        QueryWrapper<St4ScsCd> queryWrapper = new QueryWrapper<>();
        //保护地id
        if (data.getStrTime() != null && data.getStrTime() != "") {
            data.setStrTime(data.getStrTime() + " 00:00:00");
        }
        if (data.getEndTime() != null && data.getEndTime() != "") {
            data.setEndTime(data.getEndTime() + " 23:59:59");
        }
        queryWrapper.eq("SG001", data.getSg001());
        List<St4ScsCd> list = st4ScsCdMapper.listCheckPointToView(data);
        Result res = new Result();
        res.setData(list);
        res.setStatus(1000);
        res.setMsg("查询问题点位成功");
        return res;
    }

    @Override
    public Result listCheckPoint(St4ScsCd data, St4SysSa seUser) {
       /* QueryWrapper<St4ScsCd> queryWrapper = new QueryWrapper<>();
        //保护地id
        queryWrapper.eq("CD009",1);

        Page<St4ScsCd> page  = new Page<St4ScsCd>(data.getPageNumber(),data.getPageSize());
        IPage<St4ScsCd> ipage = st4ScsCdMapper.selectPage(page,queryWrapper);
        queryWrapper.eq("SG001",data.getSg001());
        List<St4ScsCd> list = ipage.getRecords();*/
        int size = data.getPageSize();//每页条数
        int number = data.getPageNumber();//开始索引
        int numberReal = 0;
        if (0 == number) {
            numberReal = number;
        } else {
            numberReal = (number - 1) * size;
        }
        Result res = new Result();
        data.setPageNumber(numberReal);
        data.setPageSize(size);
        //这里只能查看本单位下的问题点
        seUser = st4SysSaMapper.selectById(seUser);

        //下面这个用cd007方便查询避免实体类代码冗余cd007不是代表原来的意义而是代表了当前人的id
        data.setCd007(seUser.getSa001());
        if (seUser.getSa020() == 0 && seUser.getSa001() != 1) {
            data.setType(0);
            data.setSa01(seUser.getSa001());
            data.setSa02(seUser.getSa002());

        } else if (seUser.getSa020() == 1) {
            data.setType(1);
            data.setSa01(seUser.getSa001());
        } else {
            data.setType(3);//避免默认值
        }
        List<St4ScsCd> list = st4ScsCdMapper.listCheckPoint(data);
        res.setRows(list);
        data.setPageNumber(null);
        data.setPageSize(null);
        int tsize = st4ScsCdMapper.listCheckPoint(data).size();
        res.setTotal(tsize);
        res.setStatus(1000);

        res.setPage((int) Math.ceil((double) tsize / size));
        res.setMsg("加载" + ResultMsg.MSG_1000);
        return res;
    }


    @Override
    public Result insertCheckPoint(St4ScsCd cd) {
        if (!ObjectUtils.isNotNullAndEmpty(cd.getCd002())) {
            return Result.build(1001, "经度" + ResultMsg.MSG_1001);
        }
        if (!ObjectUtils.isNotNullAndEmpty(cd.getCd003())) {
            return Result.build(1001, "纬度" + ResultMsg.MSG_1001);
        }
        if (!ObjectUtils.isNotNullAndEmpty(cd.getCd004())) {
            return Result.build(1001, "编号" + ResultMsg.MSG_1001);
        }
        if (!ObjectUtils.isNotNullAndEmpty(cd.getSg001())) {
            return Result.build(1001, "保护区ID" + ResultMsg.MSG_1001);
        }
        if (!ObjectUtils.isNotNullAndEmpty(cd.getCl001())) {
            return Result.build(1001, "批次任务ID" + ResultMsg.MSG_1001);
        }
        if (!ObjectUtils.isNotNullAndEmpty(cd.getSd001())) {
            return Result.build(1001, "所属行政区ID" + ResultMsg.MSG_1001);
        }
        if (!ObjectUtils.isNotNullAndEmpty(cd.getCl001())) {
            return Result.build(1001, "批次任务ID" + ResultMsg.MSG_1001);
        }
        cd.setCd011(LocalDateTime.now());
        if (st4ScsCdMapper.insert(cd) > 0) {
            St4ScsCk data = new St4ScsCk();
            //问题点编号
            data.setCd004(cd.getCd004());
            //原始台账
            data.setCk088(0);
            //插入台账记录，后台添加问题点的时候默认插入一条空台账

            st4ScsCkMapper.insert(data);
            return Result.build(1000, "添加" + ResultMsg.MSG_1000);
        }
        return Result.build(1005, ResultMsg.MSG_1005);
    }

    @Override
    public Result sysPointDataLd(Integer roleId, Integer uid) {
        //这里存在矛盾就是一个点被重复导入，和被重复提交到时候同步的时候取哪个活动设施为准 还有就是activityName为null的时候返回不了来了看看
        /**
         * 2019.10.8.1523这里考虑到在同一个任务下的同一个点是分给了多个人，而在每一个人同步的时候也要把其他人对这个点的核查状态也同步下来
         * sysPointAndLedgerDataLd方法原来是传递uid的现在改成cd001,因为往原sql里面传进去uid那样查询出来的只是一个人的信息，所以不满足
         * 这里的解决办法就是先把这个人所要核查的点全部去拿到传递给上面的方法这样就ok
         */
        QueryWrapper<St4PoCdSa> wrapper = new QueryWrapper<>();
        wrapper.eq("SA001", uid);
        List<St4PoCdSa> cdSas = st4PoCdSaMapper.selectList(wrapper);
        List<Integer> cds = cdSas.stream().map(St4PoCdSa::getCd001).collect(Collectors.toList());

        HashSet h = new HashSet(cds);
        cds.clear();

        cds.addAll(h);
        Result res = new Result();
        if (cds != null && cds.size() > 0) {
            List<St4ScsCd> list = st4ScsCdMapper.sysPointAndLedgerDataLd(cds);
            for (int i = 0; i < list.size(); i++) {
                //将坐标格式转为经纬度
                list.get(i).setCd002(PointHelp.Dms2D(list.get(i).getCd002()));
                list.get(i).setCd003(PointHelp.Dms2D(list.get(i).getCd003()));
                //如果核查台账的建设时间是"0001-00-00 00:00:00"，则说明移动端填写的是不详，返回的时候将数据修改为null
           /* if(list.get(i).getSt4ScsCkList().size() > 1 && list.get(i).getSt4ScsCkList().get(1).getCk020() != null && list.get(i).getSt4ScsCkList().get(1).getCk020().equals("0001-00-00 00:00:00")){
                list.get(i).getSt4ScsCkList().get(1).setCk020(null);
            }*/
                //cse
            }
            res.setData(list);
        } else {
            List<St4ScsCd> list = new ArrayList<>();
            res.setData(list);
        }
        res.setStatus(1000);
        res.setMsg("同步问题点位成功");
        return res;
    }


    @Override
    public Map<String, Object> list(Integer pageNum, Integer pageSize, Integer id) {
        QueryWrapper<St4ScsCd> wrapper = new QueryWrapper<>();
        wrapper.eq("image_id", id);
        IPage<St4ScsCd> iPage = st4ScsCdMapper.selectPage(new Page<>(pageNum, pageSize), wrapper);
        Map<String, Object> result = new HashMap<>();
        result.put("rows", iPage.getRecords());
        result.put("total", iPage.getTotal());
        return result;
    }

    public Map list2(Integer id) {
        List<Map> st4ScsCds = st4ScsCdMapper.select(id);
        Map map = new HashMap();
        map.put("data", st4ScsCds);
        return map;
    }

    @Override
    public void delete(List<Integer> ids) {
        //具体逻辑
    }

    @Override
    public ResultVO insert(List<Map<String, Object>> data, Integer imageId, Integer createBy){
        try {
            //通过影像id先删除记录然后再插入,然后再写入shp文件，将地址更新到影像表中！
            st4ScsCdMapper.delete(new QueryWrapper<St4ScsCd>().eq("image_id", imageId));
            //从data中构造属性
            int plaqueNumber = data.size(); //斑块数量
            double area = 0; //总面积
            for (Map<String, Object> datum : data) {
                Map<String, Object> attributes = (Map<String, Object>) datum.get("attributes");
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
                    area += Double.valueOf(attributes.get("area")+"");
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
                }else{
                    return ResultVOUtil.error(ResultEnum.ERROR.getCode(), "类型不能为空");
                }
                if (null != attributes.get("descri")) {
                    iterpretation.setDescri(attributes.get("descri") + "");
                }
    //            if (null != attributes.get("remark")) {
    //                iterpretation.setCd012(attributes.get("remark") + "");
    //            }
                Map<String, Object> rings = (Map<String, Object>) datum.get("geometry");
                String geometry = rings.get("rings").toString();
                iterpretation.setGeometry(geometry);
                iterpretation.setImageId(imageId);
                iterpretation.setCd010(createBy);
                iterpretation.setCd011(LocalDateTime.now());
                iterpretation.setCd004(UUID.randomUUID().toString().replace("-", ""));
                st4ScsCdMapper.insert(iterpretation);
            }

            //写入本地shp文件
            String url = PathUtile.getRandomPath(PATH+"/epr/image/","x.shp");

            String res = ShpUtil.handleWebData(JSONArray.parseArray(net.sf.json.JSONArray.fromObject(data)+""),url);
            //SHP上传到GIS服务器
            String u = url.split("\\:")[1];
            String ftpPath = u.split("\\.")[0];
            ftpPath = ftpPath.substring(0,u.lastIndexOf("/"))+"/";
            String name = u.substring(u.lastIndexOf("/")+1);
            name = name.split("\\.")[0];

            String fileName1 = name+".shp";
            FileInputStream input1 = new FileInputStream(new File(url));
            String fileName2 = name + ".dbf";
            FileInputStream input2 = new FileInputStream(new File(url.split("\\.")[0] + ".dbf"));
            String fileName3 = name + ".fix";
            FileInputStream input3 = new FileInputStream(new File(url.split("\\.")[0] + ".fix"));
            String fileName4 = name + ".prj";
            FileInputStream input4 = new FileInputStream(new File(url.split("\\.")[0] + ".prj"));
            String fileName5 = name + ".shx";
            FileInputStream input5 = new FileInputStream(new File(url.split("\\.")[0] + ".shx"));

            String res1 = FTPUtil.uploadFile(ftpHost, ftpUserName, ftpPassword, ftpPort, ftpPath, fileName1, input1);
            String res2 = FTPUtil.uploadFile(ftpHost, ftpUserName, ftpPassword, ftpPort, ftpPath, fileName2, input2);
            String res3 = FTPUtil.uploadFile(ftpHost, ftpUserName, ftpPassword, ftpPort, ftpPath, fileName3, input3);
            String res4 = FTPUtil.uploadFile(ftpHost, ftpUserName, ftpPassword, ftpPort, ftpPath, fileName4, input4);
            String res5 = FTPUtil.uploadFile(ftpHost, ftpUserName, ftpPassword, ftpPort, ftpPath, fileName5, input5);

            if ("00000".equals(res1 + res2 + res3 + res4 + res5)) {
                //插入影像表
                if ("0".equals(res)) {
                    Image image = new Image();
                    image.setId(imageId);
                    image.setShpurl(ftpPt+ftpUrl+ftpPath+fileName1);
                    image.setShp(url);
                    image.setArea(area);
                    image.setPlaqueNumber(plaqueNumber);
                    int r = imageMapper.updateById(image);
                    if(0<r){
                        return ResultVOUtil.success();
                    }
                }
            }
            return ResultVOUtil.error(ResultEnum.ERROR.getCode(), "添加失败");
        } catch (Exception e) {
            e.printStackTrace();
            return ResultVOUtil.error(ResultEnum.ERROR.getCode(), "添加失败");
        }
    }

    @Override
    public void edit(St4ScsCd entity) {
        //具体逻辑
    }

    @Override
    public Map<String, Object> exporExcel(Integer id, HttpServletResponse response) {
        QueryWrapper<St4ScsCd> wrapper = new QueryWrapper<>();
        wrapper.eq("image_id", id);
        List<St4ScsCd> st4ScsCds = st4ScsCdMapper.selectList(wrapper);

        String filepath = ExcelUtil.toXls("人类活动解译信息", st4ScsCds, configUtils.getExcel_PATH(), St4ScsCd.class, response);
        Map map1 = new HashMap();
        map1.put("excelPath", filepath.substring(2));

        return map1;
    }





    @Override
    public ResultVO upload(HttpServletRequest request, Image image) {
        try {
            SimpleDateFormat ymdhms = new SimpleDateFormat("yyyy-MM-dd");
            //上传
            String[] arr = {"zip","ZIP"};
            String path = FileUtil.getPath(PATH+"/epr/image/");
            Map resMap = FileUtil.uploadFile(request, path, arr, 30000000l);  //限制30MB
            if(null!=resMap.get("error")){
                return ResultVOUtil.error(ResultEnum.ERROR.getCode(),resMap.get("error")+"");
            }
            String newName = resMap.get("newName")+""; //附件名称


            String uuid = newName.split("\\.")[0];
            //解压
            File f = new File(path+uuid);
            if(!f.isDirectory()) f.mkdirs();
            FileUtil.unPackZip(path+newName,path+uuid,null);

            //获取shp文件路径
            String shp = "";
            List<File> list = FileUtil.listFiles(new File(path+uuid));
            for (File pt : list) {
                if(pt.getName().endsWith(".shp")){
                    shp = pt.getPath();
                }
            }
            if(StringUtils.isBlank(shp)){
                return ResultVOUtil.error(ResultEnum.ERROR.getCode(),"未知shp文件");
            }

            //读取SHP数据
            String shpStr = ShpUtil.readShapeFileToStr(shp,1)+"";
            if(StringUtils.isBlank(shpStr)){
                return ResultVOUtil.error(ResultEnum.ERROR.getCode(),"读取SHP失败");
            }

            //替换字段名
            net.sf.json.JSONArray jsonArray = net.sf.json.JSONArray.fromObject(shpStr);
//            System.out.println(jsonArray);
            net.sf.json.JSONArray jSONArray = new net.sf.json.JSONArray();
            for (Object o : jsonArray){
                JSONObject jo = JSONObject.fromObject(o);
                JSONObject attributes = JSONObject.fromObject(jo.get("attributes"));
                JSONObject jSONObject1 = new JSONObject();

                //匹配类型
                ImageConfig ic = imageConfigMapper.like(attributes.get("一级类") + "");
                if(null!=ic)
                    jSONObject1.put("type",ic.getId());
                jSONObject1.put("region",attributes.get("功能分"));
                jSONObject1.put("position",attributes.get("位置"));
                jSONObject1.put("area",attributes.get("面积")+"");
                jSONObject1.put("center",attributes.get("实地经")+","+attributes.get("实地纬"));

                if (StringUtils.isNotBlank(image.getRemark()))
                    jSONObject1.put("remark",image.getRemark());
                if (StringUtils.isNotBlank(image.getName()))
                    jSONObject1.put("name",image.getName());

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
            if(!"0".equals(res2)){
                return ResultVOUtil.error(ResultEnum.ERROR.getCode(),"生成新SHP失败");
            }

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
//            image.setUpdateDate(new Date());
            image.setShp(url); //本地SHP路径
            image.setRemark(image.getRemark());
            image.setDelFlag(1);
            image.setSign(1);
            if(null!=image.getCreateDate()){
                image.setCreateDate(image.getCreateDate());
            }

            int res1 = imageMapper.updateById(image);
            if(0==res1){
                return ResultVOUtil.error(ResultEnum.ERROR.getCode(),"插入image失败");
            }


    //--------------------------------------------------------------------



            st4ScsCdMapper.delete(new QueryWrapper<St4ScsCd>().eq("image_id", resMap.get("id")));
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
                        iterpretation.setCd015(1);
                    }else{
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
                int res = st4ScsCdMapper.insert(iterpretation);
                if(0<res){
                    return ResultVOUtil.success();
                }else{
                    return ResultVOUtil.error(ResultEnum.PARAMETEREMPTY.getCode(), "插入st4ScsCd失败");
                }
            }

            return ResultVOUtil.error(ResultEnum.PARAMETEREMPTY.getCode(), "导入失败");
        } catch (Exception e) {
            e.printStackTrace();
            return ResultVOUtil.error(ResultEnum.PARAMETEREMPTY.getCode(), "导入失败");
        }
    }





}
