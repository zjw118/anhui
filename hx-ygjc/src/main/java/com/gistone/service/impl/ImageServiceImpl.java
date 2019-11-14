package com.gistone.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gistone.VO.ResultVO;
import com.gistone.entity.Image;
import com.gistone.entity.ImageConfig;
import com.gistone.entity.ShpBatch;
import com.gistone.entity.SysUser;
import com.gistone.mapper.ImageMapper;
import com.gistone.mapper.ShpBatchMapper;
import com.gistone.service.ImageService;
import com.gistone.util.*;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

/**
 * <p>
 * 影像数据表 服务实现类
 * </p>
 *
 * @author zf1017@foxmail.com
 * @since 2019-10-18
 */
@Service
@Transactional
@Slf4j
public class ImageServiceImpl extends ServiceImpl<ImageMapper, Image> implements ImageService {

    @Autowired
    private ImageMapper mapper;
    @Autowired
    private com.gistone.mapper.ShpBatchMapper shpBatchMapper;


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
    @Value("${PATH}")
    private String PATH;
    @Value("${IMAGE_EVA}")
    private String IMAGE_EVA;


    @Override
    public Map<String, Object> list(Integer pageNum, Integer pageSize, String userName) {
        QueryWrapper<Image> wrapper = new QueryWrapper<>();
        if(StringUtils.isNotBlank(userName)){
            wrapper.like("name",userName);
        }
        wrapper.eq("del_flag",1);
        wrapper.orderByDesc("update_date");
        IPage<Image> imageIPage = mapper.selectPage(new Page<>(pageNum, pageSize), wrapper);
        Map<String, Object> result = new HashMap<>();
        result.put("rows", imageIPage.getRecords());
        result.put("total", imageIPage.getTotal());
        return result;
    }

    @Override
    public void delete(List<Integer> ids) {
        //具体逻辑
        for (Integer id : ids) {
            Image image = mapper.selectById(id);
            image.setDelFlag(0);
            mapper.updateById(image);
        }
    }


    @Override
    public void insert(String name, String url, Integer createBy,String remark) {
        //具体逻辑
        Image image = new Image().setName(name).setUrl(url).setCreateDate(LocalDateTime.now()).setCreateBy(createBy).setUpdateDate(new Date());
        if(StringUtils.isNotBlank(remark)){
           image.setRemark(remark);
       }
        mapper.insert(image);
    }


    @Override
    public void edit(Integer id,String name,String url,Integer  updateBy,String remark) {
        //具体逻辑
        Image image = mapper.selectById(id);
        image.setName(name).setUrl(url).setUpdateDate(new Date()).setUpdateBy(updateBy);
        if(StringUtils.isNotBlank(remark)){
            image.setRemark(remark);
        }
        mapper.updateById(image);
    }

    @Override
    public List<Map<String, Object>> getCount(String code, LocalDate currentTime, LocalDate beforeTime) {
        List<Map<String, Object>> mapList = mapper.selectCount(code, currentTime, beforeTime);
        return mapList;
    }

    @Override
    public int getBeforeCount(String code, LocalDate beforeTime) {
        int sum = mapper.selectBeforeCount(code,beforeTime);
        return sum;
    }

    @Override
    public List<Map<String, Object>> getRlhdTotal() {

        //获取到最新数据的id
        int id = mapper.getLastDataId();
        //通过此id分组统计面积
        List<Map<String,Object>> result = mapper.getAreaGroupByType(id);
        return result;
    }

    @Override
    public ResultVO getAudit(Integer id) {
        try {
            //获取影像SHP数据的FTP地址
            Image image = mapper.getImageById(id);
            String shpUrl = image.getShpurl();
            if(StringUtils.isBlank(shpUrl)){
                return ResultVOUtil.error(ResultEnum.ERROR.getCode(), "未知影像数据");
            }
            //获取最新红线SHP数据的FTP地址
            ShpBatch newShpBatch = shpBatchMapper.getNewShpBatch();
            String ftpShpUrl = newShpBatch.getFtpShpUrl();
            if(StringUtils.isBlank(ftpShpUrl)){
                return ResultVOUtil.error(ResultEnum.ERROR.getCode(), "未知红线数据");
            }
//            System.out.println( "?humanActivity="+shpUrl);
//            System.out.println("&redline="+ftpShpUrl);
            //发送请求
            String p1 = "?humanActivity="+shpUrl;
            String p2 = "&redline="+ftpShpUrl;
            String p3 = "&f=pjson";
            JSONObject jsonObject = JSONObject.fromObject(HttpUtil.GET(IMAGE_EVA+"/submitJob"+p1+p2+p3,null));
            String jobId = "/"+jsonObject.get("jobId");

            String out = "";
            String redline_area = "";
            String stati = "";
//            for (int i = 0; i < 5; i++) {
//                Thread.sleep(2000);
//                out = HttpUtil.GET(IMAGE_EVA+"/jobs"+jobId+"/results/out?f=pjson",null);
//                if(-1==out.indexOf("error")) break;
//            }
//            System.out.println(out);
            for (int i = 0; i < 5; i++) {
                System.out.println("+1");
                redline_area = HttpUtil.GET(IMAGE_EVA+"/jobs"+jobId+"/results/redline_area?f=pjson",null);
                if(-1==redline_area.indexOf("error")) break;
                Thread.sleep(2000);
            }
            System.out.println(redline_area);
//            for (int i = 0; i < 5; i++) {
//                stati = HttpUtil.GET(IMAGE_EVA+"/jobs"+jobId+"/results/stati?f=pjson",null);
//                if(-1==stati.indexOf("error")) break;
//                Thread.sleep(2000);
//            }
//            System.out.println(stati);

            if(1==1){
                return null;
            }

            //判断是否成功
            if(-1<out.indexOf("error")||"".equals(out)){
                return ResultVOUtil.error(ResultEnum.ERROR.getCode(), "out请求失败");
            }
            if(-1<redline_area.indexOf("error")||"".equals(redline_area)){
                return ResultVOUtil.error(ResultEnum.ERROR.getCode(), "redline_area请求失败");
            }
            if(-1<stati.indexOf("error")||"".equals(stati)){
                return ResultVOUtil.error(ResultEnum.ERROR.getCode(), "stati请求失败");
            }

            //保存文件
            String path = FileUtil.getPath(PATH+"/epr/out/");
            String name = UUID.randomUUID().toString()+".json";
            boolean b = FileUtil.writeInFile(path,name,out);
            if(!b) return ResultVOUtil.error(ResultEnum.ERROR.getCode(), "文件保存失败");


            //总面积
            double sum = 0.0;
            JSONObject job = JSONObject.fromObject(redline_area);
            JSONObject job2 = JSONObject.fromObject(job.get("value"));
            JSONArray features = JSONArray.fromObject(job2.get("features"));
            for (Object feature : features) {
                JSONObject job3 = JSONObject.fromObject(feature);
                JSONObject attributes = JSONObject.fromObject(job3.get("attributes"));
                sum += Double.valueOf(attributes.get("hxarea")+"");
            }

            //获取系数
            ImageConfig imageConfig = mapper.getImageConfig();
            double nyyd = imageConfig.getNyyd();
            double jmd = imageConfig.getJmd();
            double gkyd = imageConfig.getGkyd();
            double csc = imageConfig.getCsc();
            double nyss = imageConfig.getNyss();
            double lyss = imageConfig.getLyss();
            double jtss = imageConfig.getJtss();
            double yzc = imageConfig.getYzc();
            double dl = imageConfig.getDl();
            double qt = imageConfig.getQt();


            //1农业用地
            double nyyds = 0.0;
            //2居民点
            double jmds = 0.0;
            //3工矿用地
            double gkyds = 0.0;
            //4采石场
            double cscs = 0.0;
            //5能源设施
            double nysss = 0.0;
            //6旅游设施
            double lysss = 0.0;
            //7交通设施
            double jtsss = 0.0;
            //8养殖场
            double yzcs = 0.0;
            //9道路
            double dls = 0.0;
            //10其它
            double qts = 0.0;
            JSONObject jobs = JSONObject.fromObject(stati);
            JSONObject job2s = JSONObject.fromObject(jobs.get("value"));
            JSONArray featuress = JSONArray.fromObject(job2s.get("features"));
            for (Object f : featuress) {
                JSONObject job3s = JSONObject.fromObject(f);
                JSONObject attributess = JSONObject.fromObject(job3s.get("attributes"));
                if("农业用地".equals(attributess.get("type")))nyyds += Double.valueOf(attributess.get("SUM_insect")+"");
                if("居民点".equals(attributess.get("type")))jmds += Double.valueOf(attributess.get("SUM_insect")+"");
                if("工矿用地".equals(attributess.get("type")))gkyds += Double.valueOf(attributess.get("SUM_insect")+"");
                if("采石场".equals(attributess.get("type")))cscs += Double.valueOf(attributess.get("SUM_insect")+"");
                if("能源设施".equals(attributess.get("type")))nysss += Double.valueOf(attributess.get("SUM_insect")+"");
                if("旅游设施".equals(attributess.get("type")))lysss += Double.valueOf(attributess.get("SUM_insect")+"");
                if("交通设施".equals(attributess.get("type")))jtsss += Double.valueOf(attributess.get("SUM_insect")+"");
                if("养殖场".equals(attributess.get("type")))yzcs += Double.valueOf(attributess.get("SUM_insect")+"");
                if("道路".equals(attributess.get("type")))dls += Double.valueOf(attributess.get("SUM_insect")+"");
                if("其他人工设施".equals(attributess.get("type")))qts += Double.valueOf(attributess.get("SUM_insect")+"");
            }
            Double num1 = (nyyds*nyyd+jmds*jmd+gkyds*gkyd+cscs*csc+nysss*nyss+lysss*lyss+jtsss*jtss+yzcs*yzc+dls*dl+qts*qt)/sum;


            JSONObject jb = new JSONObject();
            jb.put("num",num1);
            jb.put("nyyd",nyyd*nyyds);
            jb.put("jmd",jmd*jmds);
            jb.put("gkyd",gkyd*gkyds);
            jb.put("csc",csc*cscs);
            jb.put("nyss",nyss*nysss);
            jb.put("lyss",lyss*lysss);
            jb.put("jtss",jtss*jtsss);
            jb.put("yzc",yzc*yzcs);
            jb.put("dl",dl*dls);
            jb.put("qt",qt*qts);


            //更新数据
            image.setContrastRed(jb+"");
            image.setAuditPath(path+name);
            int res2 = mapper.updateImage(image);
            if(0<res2){
//                jSONObject = new JSONObject();
//                jSONObject.put("image",JSON.toJSONString(image, SerializerFeature.DisableCircularReferenceDetect,SerializerFeature.WriteDateUseDateFormat));
//                jSONObject.put("out",out);
//                jSONObject.put("redline_area",redline_area);
//                jSONObject.put("stati",stati);
                return ResultVOUtil.success();
            }else{
                return ResultVOUtil.error(ResultEnum.ERROR.getCode(), "更新失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResultVOUtil.error(ResultEnum.ERROR.getCode(), "审核失败");
        }

    }

    @Override
    public ResultVO audit(Image image) {
        int res = mapper.updateImage(image);
        if(0<res){
            return ResultVOUtil.success();
        }
        return ResultVOUtil.error(ResultEnum.ERROR.getCode(), "审核失败");
    }

    @Override
    public ResultVO upload(HttpServletRequest request,Image image) {
        try {
            SimpleDateFormat ymdhms = new SimpleDateFormat("yyyy-MM-dd");
            //上传
            String[] arr = {"zip","ZIP"};
            String path = FileUtil.getPath(PATH+"/epr/image/");
            Map resMap = FileUtil.uploadFiles(request, path, arr, 50);  //每个附件限制50MB
//            Map<String,String> resMap = FileUtil.uploadFile(request,path,arr,10000000l);//10MB
            String error = resMap.get("error")+"";
            if(!"0".equals(error)){
                return ResultVOUtil.error(ResultEnum.ERROR.getCode(),error);
            }
            String newName; //附件名称
            List<Map> fl = (List)resMap.get("fileList");
            if(0<fl.size()){
                newName = fl.get(0).get("newName")+"";
            }else{
                return ResultVOUtil.error(ResultEnum.ERROR.getCode(),"上传失败");
            }
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
            JSONArray jsonArray = JSONArray.fromObject(shpStr);
//            System.out.println(jsonArray);
            JSONArray jSONArray = new JSONArray();
            for (Object o : jsonArray){
                JSONObject jo = JSONObject.fromObject(o);
                JSONObject attributes = JSONObject.fromObject(jo.get("attributes"));
                JSONObject jSONObject1 = new JSONObject();
                jSONObject1.put("name",attributes.get("标准名"));
                jSONObject1.put("type",attributes.get("一级类"));
                jSONObject1.put("region",attributes.get("功能分"));
                jSONObject1.put("position",attributes.get("位置"));
                jSONObject1.put("area",attributes.get("面积")+"");
                jSONObject1.put("center",attributes.get("实地经")+","+attributes.get("实地纬"));

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
            image.setCreateDate(LocalDateTime.now());
            if(null!=user)
                image.setUpdateBy(user.getId());
            image.setUpdateDate(new Date());
            image.setShp(url); //本地SHP路径
            image.setDelFlag(1);
            image.setSign(1);
            if(null!=resMap.get("name"))
            image.setName(resMap.get("name")+"");
            if(null!=resMap.get("url"))
            image.setUrl(resMap.get("url")+"");
            if(null!=resMap.get("remark"))
            image.setRemark(resMap.get("remark")+"");
            int res = mapper.insertImage(image);
            if(0<res){
                return ResultVOUtil.success();
            }
            return ResultVOUtil.error(ResultEnum.PARAMETEREMPTY.getCode(), "导入失败");
        } catch (Exception e) {
            e.printStackTrace();
            return ResultVOUtil.error(ResultEnum.PARAMETEREMPTY.getCode(), "导入失败");
        }
    }


}


