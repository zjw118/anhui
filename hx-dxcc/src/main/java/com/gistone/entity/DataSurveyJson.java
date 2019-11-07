package com.gistone.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;

/**
 * <p>
 *
 * </p>
 *
 * @author xjc
 * @since 2019-03-07
 */
@TableName("data_survey_json")
public class DataSurveyJson implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    @TableId(value = "pk_id", type = IdType.AUTO)
    private Integer pkId;
    /**
     * 调查表关联id
     */
    @TableField("fk_survey_id")
    private Integer fkSurveyId;
    /**
     * 调查子表关联id
     */
    @TableField("index_item_id")
    private Integer indexItemId;
    /**
     * json数据
     */
    @TableField("survey_jsonData")
    private String surveyJsondata;
    /**
     * 备用字段1
     */
    private String ps01;
    /**
     * 备用字段2
     */
    private String ps02;


    public Integer getPkId() {
        return pkId;
    }

    public void setPkId(Integer pkId) {
        this.pkId = pkId;
    }

    public Integer getFkSurveyId() {
        return fkSurveyId;
    }

    public void setFkSurveyId(Integer fkSurveyId) {
        this.fkSurveyId = fkSurveyId;
    }

    public Integer getIndexItemId() {
        return indexItemId;
    }

    public void setIndexItemId(Integer indexItemId) {
        this.indexItemId = indexItemId;
    }

    public String getSurveyJsondata() {
        return surveyJsondata;
    }

    public void setSurveyJsondata(String surveyJsondata) {
        this.surveyJsondata = surveyJsondata;
    }

    public String getPs01() {
        return ps01;
    }

    public void setPs01(String ps01) {
        this.ps01 = ps01;
    }

    public String getPs02() {
        return ps02;
    }

    public void setPs02(String ps02) {
        this.ps02 = ps02;
    }

    @Override
    public String toString() {
        return "DataSurveyJson{" +
        "pkId=" + pkId +
        ", fkSurveyId=" + fkSurveyId +
        ", indexItemId=" + indexItemId +
        ", surveyJsondata=" + surveyJsondata +
        ", ps01=" + ps01 +
        ", ps02=" + ps02 +
        "}";
    }
}
