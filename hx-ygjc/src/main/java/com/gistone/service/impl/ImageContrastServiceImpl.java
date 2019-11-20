package com.gistone.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gistone.VO.ResultVO;
import com.gistone.entity.Image;
import com.gistone.entity.ImageConfig;
import com.gistone.entity.ImageContrast;
import com.gistone.mapper.ImageConfigMapper;
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
import java.util.List;
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
    @Autowired
    private ImageConfigMapper imageConfigMapper;

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
        QueryWrapper<Image> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id",imageContrast.getImage1Id());
        Image entity1 = imageService.getOne(queryWrapper);
        QueryWrapper<Image> queryWrapper2 = new QueryWrapper<>();
        queryWrapper2.eq("id",imageContrast.getImage2Id());
        Image entity2 = imageService.getOne(queryWrapper2);

        //获取影像shp数据
        String shp1 = ShpUtil.readShapeFileToStr(entity1.getShp(),1)+"";
        String shp2 = ShpUtil.readShapeFileToStr(entity2.getShp(),1)+"";
        //获取3级类型
        List<ImageConfig> imageConfig3s = imageConfigMapper.getImageConfig3s();


        //shp1汇总数据
        JSONArray jsonArray = JSONArray.fromObject(shp1);
        for (Object ja : jsonArray) {
            JSONObject jsonObject = JSONObject.fromObject(ja);
            Object attributes = jsonObject.get("attributes");
            JSONObject jsonObject1 = JSONObject.fromObject(attributes);
            if(null!=jsonObject1.get("area")){
                for (ImageConfig imageConfig3 : imageConfig3s) {
                    if(null==imageConfig3.getNum1()){
                        imageConfig3.setNum1(0.0);
                    }
                    if(imageConfig3.getId().toString().equals(jsonObject1.get("type"))){
                        imageConfig3.setNum1(imageConfig3.getNum1()+Double.valueOf(jsonObject1.get("area")+""));
                    }
                }
            }
        }


        //shp2汇总数据
        JSONArray jsonArray2 = JSONArray.fromObject(shp2);
        for (Object ja : jsonArray2) {
            JSONObject jsonObject = JSONObject.fromObject(ja);
            Object attributes = jsonObject.get("attributes");
            JSONObject jsonObject1 = JSONObject.fromObject(attributes);
            if(null!=jsonObject1.get("area")){
                for (ImageConfig imageConfig3 : imageConfig3s) {
                    if(null==imageConfig3.getNum2()){
                        imageConfig3.setNum2(0.0);
                    }
                    if(imageConfig3.getId().toString().equals(jsonObject1.get("type"))){
                        imageConfig3.setNum2(imageConfig3.getNum2()+Double.valueOf(jsonObject1.get("area")+""));
                    }

                }
            }
        }


        //对比数据
        for (ImageConfig imageConfig3 : imageConfig3s) {
            if(null==imageConfig3.getNum3()){
                imageConfig3.setNum3(0.0);
            }
            imageConfig3.setNum3(imageConfig3.getNum2()-imageConfig3.getNum1());
        }



        Map map = new HashMap();
        map.put("imageContrast",imageContrast);
        map.put("image1",entity1);
        map.put("image2",entity2);
        map.put("imageConfig",imageConfig3s);
        return ResultVOUtil.success(map);
    }






}
