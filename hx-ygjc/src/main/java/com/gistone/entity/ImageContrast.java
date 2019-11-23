package com.gistone.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class ImageContrast implements Serializable {
    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    @TableId(value = "image1_id")
    private Integer image1Id;
    @TableId(value = "image2_id")
    private Integer image2Id;
    @TableId(value = "name")
    private String name;
    @TableId(value = "data1")
    private String data1;
    @TableId(value = "data2")
    private String data2;
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
    @TableId(value = "date")
    private Date date;
    @TableId(value = "remark")
    private String remark;
    @TableId(value = "del_flag")
    private Integer delFlag;
    @TableId(value = "userId")
    private Integer userId;



}
