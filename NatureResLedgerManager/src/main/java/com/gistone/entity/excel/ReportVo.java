package com.gistone.entity.excel;

import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.excel.annotation.ExcelIgnore;
import lombok.Data;

/**
 * 生态保护红线核查监管报告辅助类
 */
@Data
public class ReportVo {

    @Excel(name = "行政区划",height = 11, width = 15)
    private String adminRegion;

    @Excel(name = "斑块编号",height = 11, width = 15)
    private String pointNumber;

    @Excel(name = "斑块名称",height = 11, width = 15)
    private String pointName;

    @Excel(name = "原斑块活动类型名称",height = 15, width = 15)
    private String orignType;

    @Excel(name = "现在斑块活动类型",height = 15, width = 15)
    private String nowType;

    @Excel(name = "原斑块面积(亩)",height = 11, width = 15)
    private String orignScale;

    @Excel(name = "现在斑块面积(亩)",height = 11, width = 15)
    private String nowScale;









}
