package com.gistone.entity;

import lombok.Data;
import java.io.Serializable;
import java.util.Date;

@Data
public class ImageContrast implements Serializable {
    private static final long serialVersionUID = 1L;

//    private Integer id;
    private Integer image1Id;
    private Integer image2Id;
    private String name;
    private String data1;
    private String data2;
    private Date date;
    private String remark;
    private Integer delFlag;
    private Integer userId;



}
