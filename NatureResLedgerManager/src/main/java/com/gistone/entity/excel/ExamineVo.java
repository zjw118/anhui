package com.gistone.entity.excel;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.gistone.util.DateUtils;
import lombok.Data;

import java.util.Date;


@Data
public class ExamineVo {


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
    /**
     * 未审核通过斑块数目
     */
    @Excel(name = "未审核通过斑块数目",height = 11, width = 15)
    private String backNum;
    /**
     * 待审核斑块数目
     */
    @Excel(name = "待审核斑块数目",height = 11, width = 15)
    private String unEmaminedNum;

    @Excel(name = "通过率",height = 11, width = 15)
    private String passRate;

    @Excel(name = "斑块总数",height = 11, width = 15)
    private String tzNum;





}
