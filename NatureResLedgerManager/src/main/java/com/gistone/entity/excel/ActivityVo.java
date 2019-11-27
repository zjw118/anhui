package com.gistone.entity.excel;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.Data;

/**
 * 核查活动类型变化辅助类
 */
@Data
public class ActivityVo {


    /**
     * 核查人
     */
    @Excel(name = "原斑块活动类型名称",height = 11, width = 15)
    private String orign;
    /**
     * 已审核通过斑块数目
     */
    @Excel(name = "原斑块活动类型数目",height = 11, width = 15)
    private String orignCount;
    /**
     * 未审核通过斑块数目
     */
    @Excel(name = "现在斑块活动类型",height = 11, width = 15)
    private String nows;
    /**
     * 待审核斑块数目
     */
    @Excel(name = "现在斑块活动数目",height = 11, width = 15)
    private String nowsCount;







}
