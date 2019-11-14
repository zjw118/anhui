package com.gistone.entity;


import lombok.Data;

@Data
public class ImageConfig {
        private Integer id;
        private String type1;
        private String type2;
        private String type3;
        private Double number;//系数

        //表外字段
        private Double num; //面积
}
