package com.gistone.entity;

import lombok.Data;

@Data
public class ImageConfig {
        private Integer id;
        private String name;
        private Integer parentid;
        private Integer type;
        private Integer orders;

        //表外字段
        private Double num;


}
