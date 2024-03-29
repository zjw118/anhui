package com.gistone.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

@Data
public class ImageNumber2 {
    private Integer id;
    private Integer image_config_id;
    private Integer sign;
    private Double number;
    private String name;

    @TableField(exist = false)
    private String data;
    @TableField(exist = false)
    private Integer imageConfigId;
    @TableField(exist = false)
    private Integer parentid;
    @TableField(exist = false)
    private String parentName;


}
