package com.gistone.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

@Data
public class ImageContrast implements Serializable {
    private static final long serialVersionUID = 1L;

//    @TableId(value = "id",type = IdType.INPUT)
    private Integer id;
//    @TableId(value = "image1_id")
    private Integer image1Id;
//    @TableId(value = "image2_id")
    private Integer image2Id;
//    @TableId(value = "name")
    private String name;
//    @TableId(value = "data")
    private String data;
//    @TableId(value = "date")
    private Date date;
//    @TableId(value = "remark")
    private String remark;
//    @TableId(value = "del_flag")
    private Integer delFlag;
//    @TableId(value = "user_id")
    private Integer userId;



}
