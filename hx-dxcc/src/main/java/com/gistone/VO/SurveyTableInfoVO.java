package com.gistone.VO;

import lombok.Data;

import java.util.List;


@Data
public class SurveyTableInfoVO {

    /**
     * 字段名称
     */
    private Integer id;
    /**
     * 字段名称
     */
    private String filedName;
    /**
     * 是否必填，1必填，0非必填
     */
    private Integer isMust;
    /**
     * 字段类型 1字符串，2整形，3浮点型，4单选下拉框，5多选下拉框，6checked，7时间类型
     */
    private Integer filedType;
    /**
     * 最大值
     */
    private String max;
    /**
     * 最小值
     */
    private String min;

    /**
     * 浮点型，保留小数点位数
     */
    private String decimalPoint;
    /**
     * 字符串限制长度
     */
    private String stringLength;
    /**
     * 单选按钮值
     */
    private String rediaoType;
    /**
     * 单选下拉框值（名称）
     */
    private String singleSelect;
    /**
     * 多选下拉框值（名称）
     */
    private String multipleSelect;

    /**
     * 时间类型（yyyy-mm-dd，yyyy-mm-dd HH:MM:ss）
     */
    private String timeLimit;

    /**
     * 多选下拉框集合
     */
    private List<String> Selects;

    /**
     * 排序值
     */
    private Integer sort;

}
