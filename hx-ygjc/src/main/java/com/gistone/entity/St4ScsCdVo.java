package com.gistone.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.Data;

import java.util.Date;


@Data
public class St4ScsCdVo{
    private Integer CD001;
    private String CD002;
    private String CD003;
    private String CD004;
    private Integer SG001;
    private Integer CL001;
    private Integer CD007;
    private Integer SD001;
    private Integer CD009;
    private Integer CD010;
    private Date CD011;
    private String CD012;
    private String CD013;
    private String CD014;
    private Integer CD015;
    @Excel(name = "id", orderNum = "0")
    private Integer imageId;
    @Excel(name = "活动名称", orderNum = "1")
    private String activeName;
    @Excel(name = "活动类型", orderNum = "2")
    private String activeType;
    @Excel(name = "活动面积", orderNum = "2")
    private String area;
    @Excel(name = "说明", orderNum = "2")
    private String descri;
    private String geometry;
    private Integer groupId;
    private String CD016;
    private Integer CD017;
    private String region;
    private String position;
}
