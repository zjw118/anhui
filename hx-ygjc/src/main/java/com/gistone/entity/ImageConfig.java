package com.gistone.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class ImageConfig {
        private Integer id;
        private String name;
        private Integer parentid;
        private Integer type;
        private Integer orders;

        //表外字段
        private Double num;

        @ApiModelProperty(value = "子菜单")
        @TableField(exist = false)
        private List<ImageConfig> children;

}
