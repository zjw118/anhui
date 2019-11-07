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


    @Value("${PATH}")
    private String PATH;
    @Value("${IMAGE_SERVICE}")
    private String IMAGE_SERVICE;

    @Override
    public ResultVO add(ImageContrast imageContrast) throws Exception {
        //创建影像对比文件夹-FTP
        SimpleDateFormat ymd = new SimpleDateFormat("yyyy-MM-dd");
        String outUrl = "E:/FTP/影像对比/"+ymd.format(new Date())+"/"+ UUID.randomUUID()+"/";


        Image image1 = imageMapper.getImageById(imageContrast.getImage1Id());
        Image image2 = imageMapper.getImageById(imageContrast.getImage2Id());
        String p1 = "?file1="+PATH+image1.getShpurl();
        String p2 = "&file2="+PATH+image2.getShpurl();
        String p3 = "&outfile="+outUrl;
        String p4 = "&f=pjson";

        File file1 = new File(outUrl+"add.shp");
        File file2 = new File(outUrl+"sub.shp");

        boolean b = true;
        for (int i = 0; i < 5; i++) {
            HttpUtil.GET(IMAGE_SERVICE+"/submitJob"+p1+p2+p3+p4,null);
            if(file1.exists()&&file2.exists()){
                b = false;
                break;
            }
            Thread.sleep(1500);
        }
        if(b){
            return ResultVOUtil.error(ResultEnum.ERROR.getCode(),"SHP文件生成失败");
        }
        //FTP读取SHP数据
        String ftpHost = "10.34.100.135"; // ftp服务器地址
        int ftpPort = 21;// ftp服务员器端口号
        String ftpUserName = "135";// anonymous匿名用户登录，不需要密码。administrator指定用户登录
        String ftpPassword = "123456";// 指定用户密码

        String ftpPath = outUrl+"add.shp"; // ftp文件存放物理路径
        String filePath = PATH+"/epr/image/影像对比"+UUID.randomUUID()+"/"; // 文件路径
        String fileName = UUID.randomUUID()+".shp";// 文件名称
        String res = FTPUtil.downloadFile(ftpHost, ftpUserName, ftpPassword, ftpPort, ftpPath, filePath, fileName);

        String ftpPath2 = outUrl+"sub.shp"; // ftp文件存放物理路径
        String filePath2 = PATH+"/epr/image/影像对比"+UUID.randomUUID()+"/"; // 文件路径
        String fileName2 = UUID.randomUUID()+".shp";// 文件名称
        String res2 = FTPUtil.downloadFile(ftpHost, ftpUserName, ftpPassword, ftpPort, ftpPath2, filePath2, fileName2);

        if("00".equals(res+res2)){
            String str1 = ShpUtil.readShapeFileToStr(filePath+fileName,1)+"";
            String str2 = ShpUtil.readShapeFileToStr(filePath2+fileName2,1)+"";
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
