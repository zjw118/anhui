package com.gistone.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

@Data
public class ImageNumber {

    private Integer id;
    private Integer image_config_id;
    private Double number;
    private String name;

    @TableField(exist = false)
    private String data;


}
