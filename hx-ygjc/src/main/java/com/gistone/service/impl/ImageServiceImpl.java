package com.gistone.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gistone.VO.ResultVO;
import com.gistone.entity.*;
import com.gistone.mapper.ImageConfigMapper;
import com.gistone.mapper.ImageMapper;
import com.gistone.mapper.ImageNumberMapper;
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
    @Autowired
    private ImageConfigMapper imageConfigMapper;
    @Autowired
    private ImageNumberMapper imageNumberMapper;



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
        Image image = mapper.getImageById(id);
        String json = "";
        if(StringUtils.isNotBlank(image.getAuditPath()))
            json = FileUtil.readFromTextFile(image.getAuditPath());
        //评分系数
//        List<ImageConfig> imageConfig = imageNumberMapper.selectImageConfigByImageId(id);

        Map map = new HashMap();
        map.put("image",image);
        map.put("xs",imageNumberMapper.selectName());
        map.put("out",json);
        return  ResultVOUtil.success(map);
    }





    @Override
    public ResultVO addAudit(Integer id,String json) {
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
            for (int i = 0; i < 5; i++) {
                Thread.sleep(2000);
                out = HttpUtil.GET(IMAGE_EVA+"/jobs"+jobId+"/results/out?f=pjson&returnType=data",null);
                if(-1==out.indexOf("error")) break;
            }
            for (int i = 0; i < 5; i++) {
                redline_area = HttpUtil.GET(IMAGE_EVA+"/jobs"+jobId+"/results/redline_area?f=pjson&returnType=data",null);
                if(-1==redline_area.indexOf("error")) break;
                Thread.sleep(2000);
            }
            for (int i = 0; i < 5; i++) {
                stati = HttpUtil.GET(IMAGE_EVA+"/jobs"+jobId+"/results/stati?f=pjson&returnType=data",null);
                if(-1==stati.indexOf("error")) break;
                Thread.sleep(2000);
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

            //获取类型列表
            List<ImageConfig> icList = imageConfigMapper.getImageConfig3();

            JSONObject jobs = JSONObject.fromObject(stati);
            JSONObject job2s = JSONObject.fromObject(jobs.get("value"));
            JSONArray featuress = JSONArray.fromObject(job2s.get("features"));
            for (Object f : featuress) {
                JSONObject job3s = JSONObject.fromObject(f);
                JSONObject attributess = JSONObject.fromObject(job3s.get("attributes"));
                for (ImageConfig imageConfig : icList) {
                    if(imageConfig.getId()==Integer.valueOf(attributess.get("type")+"")){
                        imageConfig.setNum(imageConfig.getNum()+Double.valueOf(attributess.get("SUM_insect")+""));
                    }
                }
            }

            //影像系数
            List<ImageNumber> imageNumbers = new ArrayList();
            JSONObject numberJson = JSONObject.fromObject(json);
            for (Object o : numberJson.keySet()) {
                ImageNumber imageNumber = new ImageNumber();
                imageNumber.setImage_config_id(Integer.valueOf(o+""));
                imageNumber.setNumber(Double.valueOf(numberJson.get(o)+""));
                imageNumbers.add(imageNumber);
            }

            //评分
            JSONObject jb = new JSONObject();
            double d = 0;
            for (ImageConfig imageConfig : icList) {
                double db = 0;
                if(0<imageNumbers.size()){
                    //有配置
                    for (ImageNumber imageNumber : imageNumbers) {
                        if(imageNumber.getImage_config_id()==imageConfig.getId()){
                            db = imageConfig.getNum()*imageNumber.getNumber();
                        }
                    }
                    jb.put(imageConfig.getId(),db);
                    d += db;
                }
            }
            jb.put("avg",d/sum);

            //更新数据
            image.setContrastRed(jb+"");
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
    public ResultVO audit(Image image) {
        int res = mapper.updateImage(image);
        if(0<res){
            return ResultVOUtil.success();
        }
        return ResultVOUtil.error(ResultEnum.ERROR.getCode(), "审核失败");
    }




}


