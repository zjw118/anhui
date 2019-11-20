package com.gistone.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
//@JsonInclude(JsonInclude.Include.NON_NULL)
public class ImageConfig {
        private Integer id;
        private String name;
        private Integer parentid;
        private Integer type;
        private Integer orders;
        private Integer sign;

        //表外字段
        @TableField(exist = false)
        private Double num;
        @TableField(exist = false)
        private Double num1;
        @TableField(exist = false)
        private Double num2;
        @TableField(exist = false)
        private Double num3;
        @TableField(exist = false)
        private String name1;
        @TableField(exist = false)
        private String name2;
        @TableField(exist = false)
        @ApiModelProperty(value = "子菜单")
        private List<ImageConfig> children;



}
