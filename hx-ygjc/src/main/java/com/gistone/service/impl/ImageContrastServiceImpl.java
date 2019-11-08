package com.gistone.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gistone.VO.ResultVO;
import com.gistone.entity.Image;
import com.gistone.entity.ImageContrast;
import com.gistone.mapper.ImageContrastMapper;
import com.gistone.mapper.ImageMapper;
import com.gistone.service.ImageContrastService;
import com.gistone.util.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.net.ftp.FTPClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
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
        String p1 = "?file1="+image1.getShpurl();
        String p2 = "&file2="+image2.getShpurl();
        String p3 = "&outfile="+ftpPt+ftpUrl+outUrl;
        String p4 = "&f=pjson";

        boolean b = true;
        for (int i = 0; i < 20; i++) {
            HttpUtil.GET(IMAGE_SERVICE+"/submitJob"+p1+p2+p3+p4,null);
            if(FTPUtil.isDri(ftpHost,ftpUserName,ftpPassword,ftpPort,outUrl+"add.shp")&&FTPUtil.isDri(ftpHost,ftpUserName,ftpPassword,ftpPort,outUrl+"sub.shp")){
                b = false;
                break;
            }
            Thread.sleep(2000);
        }
        if(b){
            return ResultVOUtil.error(ResultEnum.ERROR.getCode(),IMAGE_SERVICE+"/submitJob"+p1+p2+p3+p4+"<===SHP文件生成失败");
        }

        String url = UUID.randomUUID()+"";
        //FTP读取SHP数据
        String ftpPath = outUrl; // 原ftp文件路径
        String filePath = PATH+"/epr/image/影像对比/"+url+"/"; // 本地路径
        String fileName1 = "add.shp";// 原ftp文件名称
        String fileName2 = "add.dbf";// 原ftp文件名称
        String fileName3 = "add.cpg";// 原ftp文件名称
        String fileName4 = "add.prj";// 原ftp文件名称
        String fileName5 = "add.shx";// 原ftp文件名称
        String fileName6 = "add.shp.xml";// 原ftp文件名称
        String res1 = FTPUtil.downloadFile(ftpHost, ftpUserName, ftpPassword, ftpPort, ftpPath, filePath, fileName1);
        String res2 = FTPUtil.downloadFile(ftpHost, ftpUserName, ftpPassword, ftpPort, ftpPath, filePath, fileName2);
        String res3 = FTPUtil.downloadFile(ftpHost, ftpUserName, ftpPassword, ftpPort, ftpPath, filePath, fileName3);
        String res4 = FTPUtil.downloadFile(ftpHost, ftpUserName, ftpPassword, ftpPort, ftpPath, filePath, fileName4);
        String res5 = FTPUtil.downloadFile(ftpHost, ftpUserName, ftpPassword, ftpPort, ftpPath, filePath, fileName5);
        String res6 = FTPUtil.downloadFile(ftpHost, ftpUserName, ftpPassword, ftpPort, ftpPath, filePath, fileName6);


        String ftpPath2 = outUrl; // ftp文件存放物理路径
        String filePath2 = PATH+"/epr/image/影像对比/"+url+"/"; // 文件路径
        String fileName11 = "add.shp";// 原ftp文件名称
        String fileName22 = "add.dbf";// 原ftp文件名称
        String fileName33 = "add.cpg";// 原ftp文件名称
        String fileName44 = "add.prj";// 原ftp文件名称
        String fileName55 = "add.shx";// 原ftp文件名称
        String fileName66 = "add.shp.xml";// 原ftp文件名称
        String res11 = FTPUtil.downloadFile(ftpHost, ftpUserName, ftpPassword, ftpPort, ftpPath2, filePath2, fileName11);
        String res22 = FTPUtil.downloadFile(ftpHost, ftpUserName, ftpPassword, ftpPort, ftpPath2, filePath2, fileName22);
        String res33 = FTPUtil.downloadFile(ftpHost, ftpUserName, ftpPassword, ftpPort, ftpPath2, filePath2, fileName33);
        String res44 = FTPUtil.downloadFile(ftpHost, ftpUserName, ftpPassword, ftpPort, ftpPath2, filePath2, fileName44);
        String res55 = FTPUtil.downloadFile(ftpHost, ftpUserName, ftpPassword, ftpPort, ftpPath2, filePath2, fileName55);
        String res66 = FTPUtil.downloadFile(ftpHost, ftpUserName, ftpPassword, ftpPort, ftpPath2, filePath2, fileName66);


        if("000000000000".equals(res1+res2+res3+res4+res5+res6+res11+res22+res33+res44+res55+res66)){
            String str1 = ShpUtil.readShapeFileToStr(filePath+fileName1,1)+"";
            String str2 = ShpUtil.readShapeFileToStr(filePath2+fileName11,1)+"";
            imageContrast.setData1(str1);
            imageContrast.setData2(str2);
            int addres = imageContrastMapper.insertImageContrast(imageContrast);
            if(addres>0){
                return ResultVOUtil.success();
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



}
