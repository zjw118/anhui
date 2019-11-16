package com.gistone.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gistone.VO.ResultVO;
import com.gistone.entity.Image;
import com.gistone.entity.ImageContrast;
import com.gistone.mapper.ImageContrastMapper;
import com.gistone.mapper.ImageMapper;
import com.gistone.service.ImageContrastService;
import com.gistone.service.ImageService;
import com.gistone.util.*;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;


@Service
@Transactional
@Slf4j
@Component
public class ImageContrastServiceImpl extends ServiceImpl<ImageContrastMapper,ImageContrast> implements ImageContrastService {
    @Autowired
    private ImageMapper imageMapper;
    @Autowired
    private ImageContrastMapper imageContrastMapper;
    @Autowired
    private ImageService imageService;

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
    @Value("${IMAGE_SERVICE}")
    private String IMAGE_SERVICE;




    @Override
    public ResultVO add(ImageContrast imageContrast) throws Exception {
        //创建影像对比文件夹-FTP
        String outUrl = "/yxdb/"+UUID.randomUUID()+"/";
        boolean bo = FTPUtil.createDri(ftpHost,ftpUserName,ftpPassword,ftpPort,outUrl);
        if(!bo){
            return ResultVOUtil.error(ResultEnum.ERROR.getCode(),"FTP创建文件夹失败");
        }

        Image image1 = imageMapper.getImageById(imageContrast.getImage1Id());
        Image image2 = imageMapper.getImageById(imageContrast.getImage2Id());
        String res = "";
        if(StringUtils.isBlank(image1.getShpurl())){
            res = image1.getName()+"<=未知比对数据";
        }
        if(StringUtils.isBlank(image2.getShpurl())){
            res = image2.getName()+"<=未知比对数据";
        }
        if(!"".equals(res)){
            return ResultVOUtil.error(ResultEnum.ERROR.getCode(),res);
        }
        String p1 = "?file1="+image1.getShpurl();
        String p2 = "&file2="+image2.getShpurl();
        String p3 = "&outfile="+ftpPt+ftpUrl+outUrl;
        String p4 = "&f=pjson";

//        System.out.println(IMAGE_SERVICE+"/submitJob"+p1+p2+p3+p4);
        HttpUtil.GET(IMAGE_SERVICE+"/submitJob"+p1+p2+p3+p4,null);
        Thread.sleep(6000);
        boolean b = true;
        for (int i = 1; i <= 20; i++) {
            if(
                    FTPUtil.isDri(ftpHost,ftpUserName,ftpPassword,ftpPort,outUrl+"add.shp")
                    &&FTPUtil.isDri(ftpHost,ftpUserName,ftpPassword,ftpPort,outUrl+"add.dbf")
                    &&FTPUtil.isDri(ftpHost,ftpUserName,ftpPassword,ftpPort,outUrl+"add.cpg")
                    &&FTPUtil.isDri(ftpHost,ftpUserName,ftpPassword,ftpPort,outUrl+"add.prj")
                    &&FTPUtil.isDri(ftpHost,ftpUserName,ftpPassword,ftpPort,outUrl+"add.shx")
                    &&FTPUtil.isDri(ftpHost,ftpUserName,ftpPassword,ftpPort,outUrl+"add.shp.xml")
                    &&FTPUtil.isDri(ftpHost,ftpUserName,ftpPassword,ftpPort,outUrl+"sub.shp")
                    &&FTPUtil.isDri(ftpHost,ftpUserName,ftpPassword,ftpPort,outUrl+"sub.dbf")
                    &&FTPUtil.isDri(ftpHost,ftpUserName,ftpPassword,ftpPort,outUrl+"sub.cpg")
                    &&FTPUtil.isDri(ftpHost,ftpUserName,ftpPassword,ftpPort,outUrl+"sub.prj")
                    &&FTPUtil.isDri(ftpHost,ftpUserName,ftpPassword,ftpPort,outUrl+"sub.shx")
                    &&FTPUtil.isDri(ftpHost,ftpUserName,ftpPassword,ftpPort,outUrl+"sub.shp.xml")
            ){
                b = false;
                break;
            }
            HttpUtil.GET(IMAGE_SERVICE+"/submitJob"+p1+p2+p3+p4,null);
            Thread.sleep(2000);
        }
        if(b){
            System.out.println("SHP文件生成失败");
            return ResultVOUtil.error(ResultEnum.ERROR.getCode(),IMAGE_SERVICE+"/submitJob"+p1+p2+p3+p4+"<===SHP文件生成失败");
        }

        //FTP将SHP复制到本地
        String ftpPath = outUrl; // 原ftp文件路径
        String filePath = PATH+"/epr/FTP"+outUrl; // 本地路径
        String fileName1 = "add.shp";// 原ftp文件名称
        String fileName2 = "add.dbf";// 原ftp文件名称
        String fileName3 = "add.cpg";// 原ftp文件名称
        String fileName4 = "add.prj";// 原ftp文件名称
        String fileName5 = "add.shx";// 原ftp文件名称
        String fileName6 = "add.shp.xml";// 原ftp文件名称
        String res1 = FTPUtil.downloadFile(ftpHost, ftpUserName, ftpPassword, ftpPort, outUrl, filePath, fileName1);
        String res2 = FTPUtil.downloadFile(ftpHost, ftpUserName, ftpPassword, ftpPort, outUrl, filePath, fileName2);
        String res3 = FTPUtil.downloadFile(ftpHost, ftpUserName, ftpPassword, ftpPort, outUrl, filePath, fileName3);
        String res4 = FTPUtil.downloadFile(ftpHost, ftpUserName, ftpPassword, ftpPort, outUrl, filePath, fileName4);
        String res5 = FTPUtil.downloadFile(ftpHost, ftpUserName, ftpPassword, ftpPort, outUrl, filePath, fileName5);
        String res6 = FTPUtil.downloadFile(ftpHost, ftpUserName, ftpPassword, ftpPort, outUrl, filePath, fileName6);

//        String ftpPath2 = outUrl; // ftp文件存放物理路径
//        String filePath2 = PATH+"/epr/FTP/"+outUrl+"/"; // 文件路径
        String fileName11 = "sub.shp";// 原ftp文件名称
        String fileName22 = "sub.dbf";// 原ftp文件名称
        String fileName33 = "sub.cpg";// 原ftp文件名称
        String fileName44 = "sub.prj";// 原ftp文件名称
        String fileName55 = "sub.shx";// 原ftp文件名称
        String fileName66 = "sub.shp.xml";// 原ftp文件名称
        String res11 = FTPUtil.downloadFile(ftpHost, ftpUserName, ftpPassword, ftpPort, outUrl, filePath, fileName11);
        String res22 = FTPUtil.downloadFile(ftpHost, ftpUserName, ftpPassword, ftpPort, outUrl, filePath, fileName22);
        String res33 = FTPUtil.downloadFile(ftpHost, ftpUserName, ftpPassword, ftpPort, outUrl, filePath, fileName33);
        String res44 = FTPUtil.downloadFile(ftpHost, ftpUserName, ftpPassword, ftpPort, outUrl, filePath, fileName44);
        String res55 = FTPUtil.downloadFile(ftpHost, ftpUserName, ftpPassword, ftpPort, outUrl, filePath, fileName55);
        String res66 = FTPUtil.downloadFile(ftpHost, ftpUserName, ftpPassword, ftpPort, outUrl, filePath, fileName66);

        //读取本地SHP数据
        if("000000000000".equals(res1+res2+res3+res4+res5+res6+res11+res22+res33+res44+res55+res66)){
            boolean file1 = new File(filePath+fileName1).exists();
            boolean file2 = new File(filePath+fileName2).exists();
            boolean file3 = new File(filePath+fileName3).exists();
            boolean file4 = new File(filePath+fileName4).exists();
            boolean file5 = new File(filePath+fileName5).exists();
            boolean file6 = new File(filePath+fileName6).exists();
            boolean file7 = new File(filePath+fileName11).exists();
            boolean file8 = new File(filePath+fileName22).exists();
            boolean file9 = new File(filePath+fileName33).exists();
            boolean file10 = new File(filePath+fileName44).exists();
            boolean file11 = new File(filePath+fileName55).exists();
            boolean file12 = new File(filePath+fileName66).exists();

            for (int i = 0; i < 5; i++) {
                if(!file1||!file2||!file3||!file4||!file5||!file6||!file7||!file8||!file9||!file10||!file11||!file12){
                    Thread.sleep(1000);
                }else{
                    break;
                }
            }

            if(!file1||!file2||!file3||!file4||!file5||!file6||!file7||!file8||!file9||!file10||!file11||!file12){
                System.out.println(filePath+"<=FTP转存本地丢失文件");
                return ResultVOUtil.error(ResultEnum.ERROR.getCode(),"丢失FTP转存文件");
            }
            String str1 = ShpUtil.readShapeFileToStr(filePath+fileName1,1)+"";
            String str2 = ShpUtil.readShapeFileToStr(filePath+fileName11,1)+"";

            //存表
            imageContrast.setData1(str1);
            imageContrast.setData2(str2);
            int addres = imageContrastMapper.insertImageContrast(imageContrast);
            if(addres>0){
                return ResultVOUtil.success(filePath);
            }
        }
        return ResultVOUtil.error(ResultEnum.ERROR.getCode(),"对比失败");
    }

    @Override
    public ResultVO list(PageBean pageBean){
        pageBean.setPoSum(imageContrastMapper.getPoSum(pageBean));
        pageBean.setPoList(imageContrastMapper.selectPoList(pageBean));
        return ResultVOUtil.success(pageBean);
    }

    @Override
    public ResultVO like(String name) {
        Image image = new Image();
        image.setName(name);
        return ResultVOUtil.success(imageContrastMapper.likeList(image));
    }

    @Override
    public ResultVO delete(Integer id) {
        int res = imageContrastMapper.deleteImageContrast(id);
        if(0<res){
            return ResultVOUtil.success();
        }
        return ResultVOUtil.error(ResultEnum.ERROR.getCode(),"删除失败");
    }

    @Override
    public ResultVO get(Map<String, Object> params) {
        ImageContrast ic = new ImageContrast();
        ic.setId(Integer.valueOf(params.get("id")+""));
        ImageContrast imageContrast = imageContrastMapper.getImageContrast(ic);
//        ImageContrast imageContrast = imageContrastService.getById(Integer.valueOf(params.get("id")+""));
        QueryWrapper<Image> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id",imageContrast.getImage1Id());
        Image entity1 = imageService.getOne(queryWrapper);
        QueryWrapper<Image> queryWrapper2 = new QueryWrapper<>();
        queryWrapper2.eq("id",imageContrast.getImage2Id());
        Image entity2 = imageService.getOne(queryWrapper2);


        String shp1 = ShpUtil.readShapeFileToStr(entity1.getShp(),1)+"";
        String shp2 = ShpUtil.readShapeFileToStr(entity2.getShp(),1)+"";

//        String shp1 = entity1.getShp();
//        String shp2 = entity2.getShp();

        int nyyd = 0;
        int jmd = 0;
        int gkyd = 0;
        int csc = 0;
        int nyss = 0;
        int lyss = 0;
        int jtss = 0;
        int yzc = 0;
        int dl = 0;
        int qt = 0;
        JSONArray jsonArray = JSONArray.fromObject(shp1);
        for (Object ja : jsonArray) {
            JSONObject jsonObject = JSONObject.fromObject(ja);
            Object attributes = jsonObject.get("attributes");
            JSONObject jsonObject1 = JSONObject.fromObject(attributes);

            if(null!=jsonObject1.get("area")){
                if("农业用地".equals(jsonObject1.get("type")))
                    nyyd+= Double.valueOf(jsonObject1.get("area")+"");
                if("居民点".equals(jsonObject1.get("type")))
                    jmd+= Double.valueOf(jsonObject1.get("area")+"");
                if("工矿用地".equals(jsonObject1.get("type")))
                    gkyd+= Double.valueOf(jsonObject1.get("area")+"");
                if("采石场".equals(jsonObject1.get("type")))
                    csc+= Double.valueOf(jsonObject1.get("area")+"");
                if("能源设施".equals(jsonObject1.get("type")))
                    nyss+= Double.valueOf(jsonObject1.get("area")+"");
                if("旅游设施".equals(jsonObject1.get("type")))
                    lyss+= Double.valueOf(jsonObject1.get("area")+"");
                if("交通设施".equals(jsonObject1.get("type")))
                    jtss+= Double.valueOf(jsonObject1.get("area")+"");
                if("养殖场".equals(jsonObject1.get("type")))
                    yzc+= Double.valueOf(jsonObject1.get("area")+"");
                if("道路".equals(jsonObject1.get("type")))
                    dl+= Double.valueOf(jsonObject1.get("area")+"");
                if("其他人工设施".equals(jsonObject1.get("type")))
                    qt+= Double.valueOf(jsonObject1.get("area")+"");
            }
        }

        int nyyd2 = 0;
        int jmd2 = 0;
        int gkyd2 = 0;
        int csc2 = 0;
        int nyss2 = 0;
        int lyss2 = 0;
        int jtss2 = 0;
        int yzc2 = 0;
        int dl2 = 0;
        int qt2 = 0;
        JSONArray jsonArray2 = JSONArray.fromObject(shp2);
        for (Object ja : jsonArray2) {
            JSONObject jsonObject = JSONObject.fromObject(ja);
            Object attributes = jsonObject.get("attributes");
            JSONObject jsonObject1 = JSONObject.fromObject(attributes);

            if(null!=jsonObject1.get("area")){
                if("农业用地".equals(jsonObject1.get("type")))
                    nyyd2+= Double.valueOf(jsonObject1.get("area")+"");
                if("居民点".equals(jsonObject1.get("type")))
                    jmd2+= Double.valueOf(jsonObject1.get("area")+"");
                if("工矿用地".equals(jsonObject1.get("type")))
                    gkyd2+= Double.valueOf(jsonObject1.get("area")+"");
                if("采石场".equals(jsonObject1.get("type")))
                    csc2+= Double.valueOf(jsonObject1.get("area")+"");
                if("能源设施".equals(jsonObject1.get("type")))
                    nyss2+= Double.valueOf(jsonObject1.get("area")+"");
                if("旅游设施".equals(jsonObject1.get("type")))
                    lyss2+= Double.valueOf(jsonObject1.get("area")+"");
                if("交通设施".equals(jsonObject1.get("type")))
                    jtss2+= Double.valueOf(jsonObject1.get("area")+"");
                if("养殖场".equals(jsonObject1.get("type")))
                    yzc2+= Double.valueOf(jsonObject1.get("area")+"");
                if("道路".equals(jsonObject1.get("type")))
                    dl2+= Double.valueOf(jsonObject1.get("area")+"");
                if("其他人工设施".equals(jsonObject1.get("type")))
                    qt2+= Double.valueOf(jsonObject1.get("area")+"");
            }
        }

        int nyyd3 = nyyd2-nyyd;
        int jmd3 = jmd2-jmd;
        int gkyd3 = gkyd2-gkyd;
        int csc3 = csc2-csc;
        int nyss3 = nyss2-nyss;
        int lyss3 = lyss2-lyss;
        int jtss3 = jtss2-jtss;
        int yzc3 = yzc2-yzc;
        int dl3 = dl2-dl;
        int qt3 = qt2-qt;


        Map map = new HashMap();
        map.put("imageContrast",imageContrast);
        map.put("image1",entity1);
        map.put("image2",entity2);

        map.put("nyyd1",nyyd);
        map.put("jmd1",jmd);
        map.put("gkyd1",gkyd);
        map.put("csc1",csc);
        map.put("nyss1",nyss);
        map.put("lyss1",lyss);
        map.put("jtss1",jtss);
        map.put("yzc1",yzc);
        map.put("dl1",dl);
        map.put("qt1",qt);

        map.put("nyyd2",nyyd2);
        map.put("jmd2",jmd2);
        map.put("gkyd2",gkyd2);
        map.put("csc2",csc2);
        map.put("nyss2",nyss2);
        map.put("lyss2",lyss2);
        map.put("jtss2",jtss2);
        map.put("yzc2",yzc2);
        map.put("dl2",dl2);
        map.put("qt2",qt2);

        map.put("nyyd3",nyyd3);
        map.put("jmd3",jmd3);
        map.put("gkyd3",gkyd3);
        map.put("csc3",csc3);
        map.put("nyss3",nyss3);
        map.put("lyss3",lyss3);
        map.put("jtss3",jtss3);
        map.put("yzc3",yzc3);
        map.put("dl3",dl3);
        map.put("qt3",qt3);

        return ResultVOUtil.success(map);
    }


}
