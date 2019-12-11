package com.gistone.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.excel.annotation.ExcelIgnore;
import lombok.Data;

@Data
public class StaticHelp {


    @ExcelIgnore
    private String cd001;


    /**
     * 原人类活动名称
     */
    @Excel(name = "原人类活动名称",height = 11, width = 15)
    private String name;
    /**
     * 原斑块数量
     */
    @Excel(name = "原人类活动斑块数量",height = 11, width = 15)
    private String orignCount="0";

    /**
     * 现在斑块活动数目
     */
    @Excel(name = "核查后人类活动斑块数量",height = 11, width = 25)
    private String nowsCount="0";

}
