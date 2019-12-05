package com.gistone.entity;


import lombok.Data;
import java.util.Date;

@Data
public class LsRedlineinfoProcess {

    private Integer id;
    private String name;
    private Integer audit;
    private String remark;
    private Date updatetime;
    private Integer closed;


}
