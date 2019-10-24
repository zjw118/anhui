package com.gistone.util;

import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

@Data
@Component
@Configuration
public class PathUtile {


    /**
     * 目录打散+随机附件名
     * @param path  前置路径
     * @param name  原文件名称（带扩展名）  可为空
     * @return 全路径
     */
    public static String getRandomPath(String path,String name){
        if(StringUtils.isNotBlank(path)){
            Date date = new Date();
            SimpleDateFormat ymd = new SimpleDateFormat("yyyy-MM-dd");
            SimpleDateFormat h = new SimpleDateFormat("HH");
            if(-1==path.indexOf("/")) path = path + "/";
            path = path + ymd.format(date)+"/"+h.format(date)+"/";
            if(StringUtils.isNotBlank(name)){
                name = UUID.randomUUID().toString().replace("-","")+name.substring(name.lastIndexOf("."));
            }
            return path + name;
        }
        return null;
    }


    /**
     * application 配置文件中配置 path=xxx
     */
    @Value("${path}")
    private String path;





}
