package com.gistone.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gistone.VO.ResultVO;
import com.gistone.entity.*;
import com.gistone.mapper.*;
import com.gistone.service.ImageService;
import com.gistone.util.*;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;
import java.io.File;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
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
    @Autowired
    private ImageConfigMapper imageConfigMapper;
    @Autowired
    private ImageNumberMapper imageNumberMapper;
    @Autowired
    private ImageNumber2Mapper imageNumber2Mapper;
    @Autowired
    private ImageMapper imageMapper;
    @Autowired
    private LsProjectModelMapper lsProjectModelMapper;


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
    @Value("${GRPOINT}")
    private String GRPOINT;


    @Override
    public Map<String, Object> list(Integer pageNum, Integer pageSize, String userName) {
        QueryWrapper<Image> wrapper = new QueryWrapper<>();
        if(StringUtils.isNotBlank(userName)){
            wrapper.like("name",userName);
        }
        wrapper.eq("del_flag",1);
        wrapper.orderByDesc("create_date").orderByDesc("id");
        IPage<Image> imageIPage = mapper.selectPage(new Page<>(pageNum, pageSize), wrapper);

        if(imageIPage.getRecords()!=null&&imageIPage.getRecords().size()>0){
            for (Image record : imageIPage.getRecords()) {
                Double area = record.getArea();
                int num = (int) (area/100000);
                if(num>100){
                    record.setScore(100);
                }
                record.setScore(num);
                mapper.updateById(record);
            }
        }

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
    public void insert(String name, String url,String ftpurl,Integer createBy,String remark,String createDate) {
        Image image = new Image().setName(name).setUrl(url).setShpurl(ftpurl).setCreateDate(createDate).setCreateBy(createBy).setUpdateDate(new Date());
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
    public List<Map<String, Object>> getCountGroupByType(Integer imageId) {
        if(imageId==null){
            imageId= mapper.getlastImageId();
        }
        List<Map<String,Object>> result = mapper.getCountGroupByType(imageId);
        return result;
    }

    @Override
    public List<Map<String, Object>> getAreaGroupByType(Integer imageId) {
        if(imageId==null){
            imageId= mapper.getlastImageId();
        }
        List<Map<String,Object>> result = mapper.getAreaByType(imageId);
        return result;
    }

    @Override
    public List<Map<String, Object>> getCountChange() {

            List<Map<String,Object>> result = mapper.getCountChange();
            return result;
    }

    @Override
    public List<Map<String, Object>> getAreaChange() {
        List<Map<String,Object>> result = mapper.getAreaChange();
        return result;
    }

    @Override
    public ResultVO getAudit(Integer id) {
        Image image = mapper.getImageById(id);
        String json = "";
        if(StringUtils.isNotBlank(image.getAuditPath()))
            json = FileUtil.readFromTextFile(image.getAuditPath());

        //评分系数
        String contrastRed = image.getContrastRed();
        List<ImageConfig> imageConfig3s = null;

        Double num = 0.0;               //总分
        if(StringUtils.isNotBlank(contrastRed)){
            JSONObject jsonObject = JSONObject.fromObject(contrastRed);
            Object o1 = jsonObject.get("num");  //总分
            Object o2 = jsonObject.get("hxpf"); //评分JSON
            Object o3 = jsonObject.get("mj");   //面积JSON
            Object o4 = jsonObject.get("bhl");   //变化量

            if(null!=o1) num = Double.valueOf(jsonObject.get("num")+"");

            //获取类别
            imageConfig3s = imageConfigMapper.getImageConfig3s();
            for (ImageConfig imageConfig3 : imageConfig3s) {
                //面积
                if(null!=o3){
                    JSONObject j2 = JSONObject.fromObject(o3);
                    for (Object o : j2.keySet()) {
                        if(imageConfig3.getId()==Integer.valueOf(o+"")){
                            imageConfig3.setNum1(Double.valueOf(j2.get(o+"")+""));
                        }
                    }
                }
                //变化量
                if(null!=o4){
                    JSONObject j3 = JSONObject.fromObject(o4);
                    for (Object o : j3.keySet()) {
                        if(imageConfig3.getId()==Integer.valueOf(o+"")){
                            imageConfig3.setNum2(Double.valueOf(j3.get(o+"")+""));
                        }
                    }
                }
            }
        }

        Map map = new HashMap();
        map.put("image",image);
        map.put("imageConfig",imageConfig3s);
        map.put("xs",imageNumberMapper.selectName());
        map.put("out",json);
        map.put("sum",num);
        return  ResultVOUtil.success(map);
    }


    @Override
    public ResultVO addAudit(Integer id,JSONObject json) {
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

            //发送请求
            String p1 = "?humanActivity="+shpUrl;
            String p2 = "&redline="+ftpShpUrl;
            String p3 = "&f=pjson";
            JSONObject jsonObject = JSONObject.fromObject(HttpUtil.GET(IMAGE_EVA+"/submitJob"+p1+p2+p3,null));
            String jobId = "/"+jsonObject.get("jobId");

            String out = "";
            String redline_area = "";
            String stati = "";
            boolean b1 = false;
            for (int i = 0; i < 5; i++) {
                Thread.sleep(2000);
                out = HttpUtil.GET(IMAGE_EVA+"/jobs"+jobId+"/results/out?f=pjson&returnType=data",null);
                if(-1==out.indexOf("error")){
                    b1 = true;
                    break;
                }
            }
            if(!b1)return ResultVOUtil.error(ResultEnum.ERROR.getCode(), "redline请求失败");
            boolean b2 = false;
            for (int i = 0; i < 5; i++) {
                redline_area = HttpUtil.GET(IMAGE_EVA+"/jobs"+jobId+"/results/redline_area?f=pjson&returnType=data",null);
                if(-1==redline_area.indexOf("error")){
                    b2 = true;
                    break;
                }
                Thread.sleep(2000);
            }
            if(!b2)return ResultVOUtil.error(ResultEnum.ERROR.getCode(), "out请求失败");
            boolean b3 = false;
            for (int i = 0; i < 5; i++) {
                stati = HttpUtil.GET(IMAGE_EVA+"/jobs"+jobId+"/results/stati?f=pjson&returnType=data",null);
                if(-1==stati.indexOf("error")){
                    b3 = true;
                    break;
                }
                Thread.sleep(2000);
            }
            if(!b3)return ResultVOUtil.error(ResultEnum.ERROR.getCode(), "stati请求失败");

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

            //获取类型列表
            List<ImageConfig> icList = imageConfigMapper.getImageConfig3();

            JSONObject jobs = JSONObject.fromObject(stati);
            JSONObject job2s = JSONObject.fromObject(jobs.get("value"));
            JSONArray featuress = JSONArray.fromObject(job2s.get("features"));
            for (Object f : featuress) {
                JSONObject job3s = JSONObject.fromObject(f);
                JSONObject attributess = JSONObject.fromObject(job3s.get("attributes"));
                for (ImageConfig imageConfig : icList) {
                    if(StringUtils.isNotBlank(attributess.get("type")+"")){
                        if(imageConfig.getId()==Integer.valueOf(attributess.get("type")+"")){
                            if(StringUtils.isNotBlank(attributess.get("SUM_insect")+"")){
                                if(null==imageConfig.getNum()) {
                                    imageConfig.setNum(0.0);
                                }
                                imageConfig.setNum(imageConfig.getNum()+Double.valueOf(attributess.get("SUM_insect")+""));
                            }
                        }
                    }
                }
            }
            //影像系数
            List<ImageNumber> imageNumbers = new ArrayList();
            JSONObject numberJson = json;
            for (Object o : numberJson.keySet()) {
                ImageNumber imageNumber = new ImageNumber();
                imageNumber.setImage_config_id(Integer.valueOf(o+""));
                imageNumber.setNumber(Double.valueOf(numberJson.get(o)+""));
                imageNumbers.add(imageNumber);
            }

            //影像评分
            JSONObject jb = new JSONObject();
            JSONObject mj = new JSONObject();
            JSONObject bhl = new JSONObject();
            double d = 0;
            for (ImageConfig imageConfig : icList) {
                if(null==imageConfig.getNum()) {
                    imageConfig.setNum(0.0);
                }
                mj.put(imageConfig.getId(),imageConfig.getNum());
                double db = 0;
                if(0<imageNumbers.size()){
                    //有配置
                    for (ImageNumber imageNumber : imageNumbers) {
                        if(imageNumber.getImage_config_id()==imageConfig.getId()){
                            if(null==imageConfig.getNum())
                                imageConfig.setNum(0.0);
                            if(null==imageNumber.getNumber())
                                imageNumber.setNumber(0.0);
                            db = imageConfig.getNum()*imageNumber.getNumber();
                        }
                    }
                    jb.put(imageConfig.getId(),db);
                    d += db;
                }
            }
            JSONObject jSONObject = new JSONObject();
            jSONObject.put("num",d/sum);
            jSONObject.put("hxpf",jb);
            jSONObject.put("mj",mj);

            //变化量
            Image image2 = imageMapper.getImage2(id);
            if(null!=image2){
                String contrastRed = image2.getContrastRed();
                if(null!=contrastRed){
                    JSONObject jsonObject1 = JSONObject.fromObject(contrastRed);
                    Object mj1 = jsonObject1.get("mj");
                    JSONObject jsonObject2 = JSONObject.fromObject(mj1);
                    for (Object o1 : jsonObject2.keySet()) {
                        for (Object o2 : mj.keySet()) {
                            if(Integer.valueOf(o1.toString())==Integer.valueOf(o2.toString())){
                                double bn = Double.valueOf(mj.get(o2)+"")-Double.valueOf(jsonObject2.get(o1)+"");
                                bhl.put(o1.toString(),bn);
                            }
                        }
                    }
                }
            }
            jSONObject.put("bhl",bhl);

            //更新数据
            image.setContrastRed(jSONObject+"");
            image.setAuditPath(path+name);
            int res2 = mapper.updateImage(image);
            if(0<res2){
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
    public ResultVO getAudit2(Integer id) {
        Image image = mapper.getImageById(id);
        String json = "";
        if(StringUtils.isNotBlank(image.getAuditPath2()))
            json = FileUtil.readFromTextFile(image.getAuditPath2());

        //评分系数
        String contrastRed = image.getContrastRed2();
        List<ImageConfig> imageConfig3s = null;

        Double num = 0.0;               //总分
        if(StringUtils.isNotBlank(contrastRed)){
            JSONObject jsonObject = JSONObject.fromObject(contrastRed);
            Object o1 = jsonObject.get("num");  //总分
            Object o2 = jsonObject.get("hxpf"); //评分JSON
            Object o3 = jsonObject.get("mj");   //面积JSON
            Object o4 = jsonObject.get("bhl");   //变化量

            if(null!=o1) num = Double.valueOf(jsonObject.get("num")+"");

            //获取类别
            imageConfig3s = imageConfigMapper.getImageConfig3s();
            for (ImageConfig imageConfig3 : imageConfig3s) {
                //面积
                if(null!=o3){
                    JSONObject j2 = JSONObject.fromObject(o3);
                    for (Object o : j2.keySet()) {
                        if(imageConfig3.getId()==Integer.valueOf(o+"")){
                            imageConfig3.setNum1(Double.valueOf(j2.get(o+"")+""));
                        }
                    }
                }
                //变化量
                if(null!=o4){
                    JSONObject j3 = JSONObject.fromObject(o4);
                    for (Object o : j3.keySet()) {
                        if(imageConfig3.getId()==Integer.valueOf(o+"")){
                            imageConfig3.setNum2(Double.valueOf(j3.get(o+"")+""));
                        }
                    }
                }
            }
        }

        Map map = new HashMap();
        map.put("image",image);
        map.put("imageConfig",imageConfig3s);
        map.put("xs",imageNumber2Mapper.selectName());
        map.put("out",json);
        map.put("sum",num);
        return  ResultVOUtil.success(map);
    }


    @Override
    public ResultVO addAudit2(Integer id,JSONObject json) {
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

            //发送请求
            String p1 = "?humanActivity="+shpUrl;
            String p2 = "&redline="+ftpShpUrl;
            String p3 = "&f=pjson";
            JSONObject jsonObject = JSONObject.fromObject(HttpUtil.GET(IMAGE_EVA+"/submitJob"+p1+p2+p3,null));
            String jobId = "/"+jsonObject.get("jobId");

            String out = "";
            String redline_area = "";
            String stati = "";
            boolean b1 = false;
            for (int i = 0; i < 5; i++) {
                Thread.sleep(2000);
                out = HttpUtil.GET(IMAGE_EVA+"/jobs"+jobId+"/results/out?f=pjson&returnType=data",null);
                if(-1==out.indexOf("error")){
                    b1 = true;
                    break;
                }
            }
            if(!b1)return ResultVOUtil.error(ResultEnum.ERROR.getCode(), "redline请求失败");
            boolean b2 = false;
            for (int i = 0; i < 5; i++) {
                redline_area = HttpUtil.GET(IMAGE_EVA+"/jobs"+jobId+"/results/redline_area?f=pjson&returnType=data",null);
                if(-1==redline_area.indexOf("error")){
                    b2 = true;
                    break;
                }
                Thread.sleep(2000);
            }
            if(!b2)return ResultVOUtil.error(ResultEnum.ERROR.getCode(), "out请求失败");
            boolean b3 = false;
            for (int i = 0; i < 5; i++) {
                stati = HttpUtil.GET(IMAGE_EVA+"/jobs"+jobId+"/results/stati?f=pjson&returnType=data",null);
                if(-1==stati.indexOf("error")){
                    b3 = true;
                    break;
                }
                Thread.sleep(2000);
            }
            if(!b3)return ResultVOUtil.error(ResultEnum.ERROR.getCode(), "stati请求失败");

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

            //获取类型列表
            List<ImageConfig> icList = imageConfigMapper.getImageConfig3();

            JSONObject jobs = JSONObject.fromObject(stati);
            JSONObject job2s = JSONObject.fromObject(jobs.get("value"));
            JSONArray featuress = JSONArray.fromObject(job2s.get("features"));
            for (Object f : featuress) {
                JSONObject job3s = JSONObject.fromObject(f);
                JSONObject attributess = JSONObject.fromObject(job3s.get("attributes"));
                for (ImageConfig imageConfig : icList) {
                    if(StringUtils.isNotBlank(attributess.get("type")+"")){
                        if(imageConfig.getId()==Integer.valueOf(attributess.get("type")+"")){
                            if(StringUtils.isNotBlank(attributess.get("SUM_insect")+"")){
                                if(null==imageConfig.getNum()) {
                                    imageConfig.setNum(0.0);
                                }
                                imageConfig.setNum(imageConfig.getNum()+Double.valueOf(attributess.get("SUM_insect")+""));
                            }
                        }
                    }
                }
            }
            //影像系数
            List<ImageNumber> imageNumbers = new ArrayList();
            JSONObject numberJson = json;
            for (Object o : numberJson.keySet()) {
                ImageNumber imageNumber = new ImageNumber();
                imageNumber.setImage_config_id(Integer.valueOf(o+""));
                imageNumber.setNumber(Double.valueOf(numberJson.get(o)+""));
                imageNumbers.add(imageNumber);
            }

            //影像评分
            JSONObject jb = new JSONObject();
            JSONObject mj = new JSONObject();
            JSONObject bhl = new JSONObject();
            double d = 0;
            for (ImageConfig imageConfig : icList) {
                if(null==imageConfig.getNum()) {
                    imageConfig.setNum(0.0);
                }
                mj.put(imageConfig.getId(),imageConfig.getNum());
                double db = 0;
                if(0<imageNumbers.size()){
                    //有配置
                    for (ImageNumber imageNumber : imageNumbers) {
                        if(imageNumber.getImage_config_id()==imageConfig.getId()){
                            if(null==imageConfig.getNum())
                                imageConfig.setNum(0.0);
                            if(null==imageNumber.getNumber())
                                imageNumber.setNumber(0.0);
                            db = imageConfig.getNum()*imageNumber.getNumber();
                        }
                    }
                    jb.put(imageConfig.getId(),db);
                    d += db;
                }
            }
            JSONObject jSONObject = new JSONObject();
            jSONObject.put("num",d/sum);
            jSONObject.put("hxpf",jb);
            jSONObject.put("mj",mj);

            //变化量
            Image image2 = imageMapper.getImage2(id);
            String contrastRed = image2.getContrastRed();
            if(null!=contrastRed){
                JSONObject jsonObject1 = JSONObject.fromObject(contrastRed);
                Object mj1 = jsonObject1.get("mj");
                JSONObject jsonObject2 = JSONObject.fromObject(mj1);
                for (Object o1 : jsonObject2.keySet()) {
                    for (Object o2 : mj.keySet()) {
                        if(Integer.valueOf(o1+"")==Integer.valueOf(o2+"")){
                            double bn = Double.valueOf(mj.get(o2)+"")-Double.valueOf(jsonObject2.get(o1)+"");
                            bhl.put(o1+"",bn);
                        }
                    }
                }
                jSONObject.put("bhl",bhl);
            }

            //更新数据
            image.setContrastRed2(jSONObject+"");
            image.setAuditPath2(path+name);
            int res2 = mapper.updateImage(image);
            if(0<res2){
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
    public ResultVO gdShp(double rc) {
        try {
            //获取最新红线url
            ShpBatch newShpBatch = shpBatchMapper.getNewShpBatch();
            if(null!=newShpBatch){
                String ftpShpUrl = newShpBatch.getFtpShpUrl();
                if(StringUtils.isNotBlank(ftpShpUrl)){
                    //请求GP服务
                    JSONObject jSONObject = new JSONObject();
                    jSONObject.put("distance",rc);
                    jSONObject.put("units","esriMeters");
                    //在FTP目录中创建文件
                    String uuid = UUID.randomUUID().toString();

                    String path1 = ftpPt+ftpUrl;
                    String path2 = "/grpoint/" + uuid + "/grpoint.shp";
                    FTPUtil.createDri(ftpHost, ftpUserName, ftpPassword, ftpPort,"/grpoint/"+uuid+"/");
                    String url1 = GRPOINT+"/submitJob?%E5%AE%B9%E5%B7%AE=%7B%0D%0A+%22distance%22%3A+"+rc+"%2C%0D%0A+%22units%22%3A+%22esriMeters%22%0D%0A%7D&parms="+path1+path2+"&redlineUrl="+ftpShpUrl+"&env%3AoutSR=&env%3AprocessSR=&returnZ=false&returnM=false&f=pjson";
//                    String url1 = GRPOINT+"/submitJob?容差="+"jSONObject"+"&parms="+path1+path2+"&redlineUrl="+ftpShpUrl+"&env%3AoutSR=&env%3AprocessSR=&returnZ=false&returnM=false&f=pjson";


                    String num = HttpUtil.GET(url1,null);
                    JSONObject jsonObject = JSONObject.fromObject(num);
                    Object jobId = jsonObject.get("jobId");
                    String url2 = GRPOINT+"/jobs/"+jobId;


                    boolean bb = false;
                    for (int i = 1; i <= 10; i++) {
                        HttpUtil.GET(url2,null);
                        Thread.sleep(2000);
                        //判断FTP目录中是否有此文件
                        int a = FTPUtil.isFile(ftpHost, ftpUserName, ftpPassword, ftpPort,"/grpoint/" + uuid+"/");
                        if(6==a){
                            bb = true;
                            break;
                        }
                    }
                    if(!bb){
                        return ResultVOUtil.error(ResultEnum.ERROR.getCode(), "SHP文件生成失败");
                    }

                    //FTP下载到本地
                    String outUrl = "/grpoint/"+uuid+"/";     // 原ftp文件路径
                    String filePath = PATH+"/epr/grpoint/"+uuid+"/";   // 本地路径
                    String fileName1 = "grpoint.cpg";   // 原ftp文件名称
                    String fileName2 = "grpoint.dbf";   // 原ftp文件名称
                    String fileName3 = "grpoint.prj";   // 原ftp文件名称
                    String fileName4 = "grpoint.shp";   // 原ftp文件名称
                    String fileName5 = "grpoint.shp.xml";   // 原ftp文件名称
                    String fileName6 = "grpoint.shx";   // 原ftp文件名称

                    File f = new File(filePath);
                    if(!f.isDirectory()) f.mkdirs();

                    FTPUtil.downloadFile(ftpHost, ftpUserName, ftpPassword, ftpPort,outUrl, filePath, fileName1);
                    FTPUtil.downloadFile(ftpHost, ftpUserName, ftpPassword, ftpPort,outUrl, filePath, fileName2);
                    FTPUtil.downloadFile(ftpHost, ftpUserName, ftpPassword, ftpPort,outUrl, filePath, fileName3);
                    FTPUtil.downloadFile(ftpHost, ftpUserName, ftpPassword, ftpPort,outUrl, filePath, fileName4);
                    FTPUtil.downloadFile(ftpHost, ftpUserName, ftpPassword, ftpPort,outUrl, filePath, fileName5);
                    FTPUtil.downloadFile(ftpHost, ftpUserName, ftpPassword, ftpPort,outUrl, filePath, fileName6);

                    boolean b = false;
                    for (int i = 0; i < 5; i++) {
                        Thread.sleep(2000);
                        boolean file1 = new File(filePath+fileName1).exists();
                        boolean file2 = new File(filePath+fileName2).exists();
                        boolean file3 = new File(filePath+fileName3).exists();
                        boolean file4 = new File(filePath+fileName4).exists();
                        boolean file5 = new File(filePath+fileName5).exists();
                        boolean file6 = new File(filePath+fileName6).exists();

                        if(file1&&file2&&file3&&file4&&file5&&file6){
                            b = true;
                            break;
                        }
                    }
                    if(!b){
                        return ResultVOUtil.error(ResultEnum.ERROR.getCode(), "FTP下载失败");
                    }
                    //生成shp
                    List<String> list = ShpUtil.readShapeFileToStr(filePath + fileName4, 2);
                    newShpBatch.setGrpoint(filePath);
                    shpBatchMapper.updateGrpoint(newShpBatch);
                    return ResultVOUtil.success(JSONArray.fromObject(list));
                }
            }else{
                return ResultVOUtil.error(ResultEnum.ERROR.getCode(), "最新红线数据获取失败");
            }
            return ResultVOUtil.error(ResultEnum.ERROR.getCode(), "获取失败");
        } catch (Exception e) {
            e.printStackTrace();
            return ResultVOUtil.error(ResultEnum.ERROR.getCode(), "获取失败");
        }
    }

    @Override
    public ResultVO gdShp2(Object data) {
        String uuid = UUID.randomUUID().toString();
        String filePath = PATH+"/epr/grpoint/"+uuid+"gd/";
        File f1 = new File(filePath);
        if(!f1.isDirectory()) f1.mkdirs();

        com.alibaba.fastjson.JSONArray j = com.alibaba.fastjson.JSONArray.parseArray(JSONArray.fromObject(data).toString());
        String res = ShpUtil.importPoint(j, filePath+"grpoint.shp");
        if("0".equals(res)){
            //获取最新红线url
            ShpBatch shpBatch = shpBatchMapper.getNewShpBatch();
            shpBatch.setGrpoint("/epr/grpoint/"+uuid+"gd/");
            int i = shpBatchMapper.updateGrpoint(shpBatch);
            if(0<i){
                return ResultVOUtil.success();
            }
        }
        return ResultVOUtil.error(ResultEnum.ERROR.getCode(), "上传失败");
    }

    @Override
    public ResultVO getGdFile() {
        //获取最新红线url
        ShpBatch shpBatch = shpBatchMapper.getNewShpBatch();
        String grpoint = shpBatch.getGrpoint();
        if(StringUtils.isBlank(grpoint)){
            ResultVOUtil.success("");
        }
        List<String> strings = null;

        if(StringUtils.isNotBlank(grpoint)){
            if(-1<grpoint.indexOf("gd")){
                strings = ShpUtil.pointToStr(grpoint+"grpoint.shp", 2);
            }else{
                strings = ShpUtil.readShapeFileToStr(grpoint+"grpoint.shp", 2);
            }
        }

        return ResultVOUtil.success(strings);
    }

    @Override
    public ResultVO exportZTTJ(String data1, String data2, String data3) {
        //获取模板
        LsProjectModel lsProjectModelByType = lsProjectModelMapper.getLsProjectModelByType(2);
        String path = lsProjectModelByType.getUrl();
//        String path = "C:\\Users\\KING\\Desktop\\人类活动专题统计模板.docx";

        //生成图片
        String path1 = pictureUtil.generate(PATH + "/epr/ZTTJ", data1);
        String path2 = pictureUtil.generate(PATH + "/epr/ZTTJ", data2);
        String path3 = pictureUtil.generate(PATH + "/epr/ZTTJ", data3);

        //生成word
        String uuid = UUID.randomUUID().toString();
        String docxPath = PATH+"/epr/ZTTJ/";
        Map<String,Object> params = new HashMap<>();
        Map<String,String> pictureMap = new HashMap<>();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String date = simpleDateFormat.format(new Date());
        params.put("date",date);
        pictureMap.put("image1",path1);
        pictureMap.put("image2",path2);
        pictureMap.put("image3",path3);
        boolean b = WordUtil.exportWord(PATH+path, docxPath,uuid+".docx", params, pictureMap);
        if(b){
            return ResultVOUtil.success("/epr/ZTTJ/"+uuid+".docx");
        }
        return ResultVOUtil.error(ResultEnum.ERROR.getCode(), "生成报告失败");
    }


    @Override
    public ResultVO gdFile(HttpServletResponse response) {
        try {
            String uuid = UUID.randomUUID().toString();
            //获取最新红线拐点附件地址
            ShpBatch newShpBatch = shpBatchMapper.getNewShpBatch();
            if(null==newShpBatch){
                return ResultVOUtil.error(ResultEnum.ERROR.getCode(), "获取红线数据失败");
            }
            //压缩
            String srcDir = newShpBatch.getGrpoint();
            if(StringUtils.isBlank(srcDir)){
                return ResultVOUtil.error(ResultEnum.ERROR.getCode(), "获取红线本地路径失败");
            }
            boolean b = FileUtil.toZip(srcDir, new File(PATH + "/epr/grpoint/" + uuid + ".zip"), false);
            if(!b){
                return ResultVOUtil.error(ResultEnum.ERROR.getCode(), "压缩失败");
            }
//            //下载
//            boolean b2 = FileUtil.downFile(response,PATH+"/epr/grpoint/"+uuid+".zip","grpoint.zip");
//            if(!b2){
//                return ResultVOUtil.error(ResultEnum.ERROR.getCode(), "下载失败");
//            }
//            return ResultVOUtil.success( "下载完成");
            return ResultVOUtil.success("/epr/grpoint/"+uuid+".zip");
        } catch (Exception e) {
            e.printStackTrace();
            return ResultVOUtil.error(ResultEnum.ERROR.getCode(), "下载失败");
        }
    }







}


