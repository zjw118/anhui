package com.gistone.util;

import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.apache.tomcat.jni.Directory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileInputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.UUID;

@Data
public class PathUtile {

    /**
     * 目录打散+随机附件名
     * @param path  前置路径
     * @param name  原文件名称（带扩展名）  可为空
     * @return 全路径
     */
    public static String getRandomPath(String path,String name){
        if(StringUtils.isNotBlank(path)){
            SimpleDateFormat ymd = new SimpleDateFormat("yyyy-MM-dd");
            if(!path.endsWith("/")) path += "/";
            path = path + ymd.format(new Date())+"/"+new Random().nextInt(20)+"/";
            File ml = new File(path);
            if(!ml.isDirectory()) ml.mkdirs();
            if(StringUtils.isNotBlank(name)){
                name = UUID.randomUUID().toString().replace("-","")+name.substring(name.lastIndexOf("."));
            }
            return path + name;
        }
        return null;
    }


    public static void main(String[] args) throws Exception {
        File file = new File("D:/FTP\\\\epr\\UploadData\\dynamicLayerSpace\\2019-11-25-15-50\\");
        File file1 = new File("D:\\epr\\UploadData\\dynamicLayerSpace\\2019-11-25-16-50" +
        "\\e3eb8e08ab484f4ca772e36c57b8e41e_GF1B_PMS_E117.9_N31.3_20190907_L1A1227691115-PAN_ortho_fuse_clip.img.enp");
        FileInputStream in = new FileInputStream(file1);
        FTPUtilUtil.uploadFile("10.34.100.135", "135", "123456", 21, "\\dynamicSpace\\", "e3eb8e08ab484f4ca772e36c57b8e41e_GF1B_PMS_E117.9_N31.3_20190907_L1A1227691115-PAN_ortho_fuse_clip.img.enp", in);
       System.out.println("sucess");
//        if(!file.isDirectory()){
//            File parent = file.getParentFile();
//            if(!parent.exists()) {
//                parent.mkdirs();
//            }
//            System.out.println(file.mkdirs());
//            System.out.println("success");
//        }
    }




}
