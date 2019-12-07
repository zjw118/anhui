package com.gistone.entity.excel;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.Data;

@Data
public class St4ScsCkrlVO {

    /**
     * 核查人
     */
    @Excel(name = "核查人",height = 11, width = 15)
    private String uname;
    /**
     * 已审核通过斑块数目
     */
    @Excel(name = "已审核通过斑块数目",height = 11, width = 15)
    private String emaminedNum;
}
