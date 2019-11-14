package com.gistone.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

@Data
public class ImageNumber {

    private Integer id;
    private Integer image_id;
    private Integer image_config_id;
    private Double number;




}
