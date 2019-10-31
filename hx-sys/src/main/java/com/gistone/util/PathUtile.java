package com.gistone.util;

import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.io.File;
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








}
