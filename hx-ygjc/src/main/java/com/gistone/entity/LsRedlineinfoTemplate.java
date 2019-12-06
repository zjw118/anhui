package com.gistone.entity;


import lombok.Data;
import java.util.Date;

@Data
public class LsRedlineinfoTemplate {

    private Integer id;
    private Integer compass;
    private Integer scale;
    private String name;
    private Date updatetime;
    private Integer closed;



}
