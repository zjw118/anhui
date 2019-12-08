package com.gistone.entity.excel;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.Data;

/**
 * 核查活动类型变化辅助类
 */
@Data
public class ActivityVo {


    /**
     * 原人类活动名称
     */
    @Excel(name = "原人类活动名称",height = 11, width = 15)
    private String orign;
    /**
     * 原斑块数量
     */
    @Excel(name = "原人类活动斑块数量",height = 11, width = 15)
    private String orignCount;
    /**
     * 核查后斑块数量
     */
    @Excel(name = "核查后人类活动名称",height = 11, width = 20)
    private String nows;
    /**
     * 现在斑块活动数目
     */
    @Excel(name = "核查后人类活动斑块数量",height = 11, width = 25)
    private String nowsCount;







}
