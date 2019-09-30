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
 * @since 2019-03-01
 */
@TableName("data_survey")
public class DataSurvey implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 调查id
     */
    @TableId(value = "survey_id", type = IdType.AUTO)
    private Integer surveyId;
    /**
     * 用户id
     */
    @TableField("user_id")
    private Integer userId;
    /**
     * 调查点名称
     */
    @TableField("survey_name")
    private String surveyName;
    /**
     * 调查点时间
     */
    @TableField("survey_time")
    private String surveyTime;
    /**
     * 经度
     */
    @TableField("survey_lon")
    private String surveyLon;
    /**
     * 纬度
     */
    @TableField("survey_lat")
    private String surveyLat;
    /**
     * 调查点海拔
     */
    @TableField("survey_altitude")
    private String surveyAltitude;
    /**
     * 调查点湿度
     */
    @TableField("survey_temperature")
    private String surveyTemperature;
    /**
     * 调查点天气
     */
    @TableField("survey_weather")
    private String surveyWeather;
    /**
     * 自然背景json串
     */
    @TableField("survey_data")
    private String surveyData;
    /**
     * 生态系统json串
     */
    @TableField("survey_stxt")
    private String surveyStxt;
    /**
     * 环境质量json串
     */
    @TableField("survey_hjzl")
    private String surveyHjzl;
    /**
     * 人为活动json串
     */
    @TableField("survey_rwhd")
    private String surveyRwhd;
    /**
     * 备用字段1
     */
    @TableField("survey_ps01")
    private String surveyPs01;
    /**
     * 备用字段2
     */
    @TableField("survey_ps02")
    private String surveyPs02;
    /**
     * 备用字段3
     */
    @TableField("survey_ps03")
    private String surveyPs03;
    /**
     * 备用字段4
     */
    @TableField("survey_ps04")
    private String surveyPs04;


    public Integer getSurveyId() {
        return surveyId;
    }

    public void setSurveyId(Integer surveyId) {
        this.surveyId = surveyId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getSurveyName() {
        return surveyName;
    }

    public void setSurveyName(String surveyName) {
        this.surveyName = surveyName;
    }

    public String getSurveyTime() {
        return surveyTime;
    }

    public void setSurveyTime(String surveyTime) {
        this.surveyTime = surveyTime;
    }

    public String getSurveyLon() {
        return surveyLon;
    }

    public void setSurveyLon(String surveyLon) {
        this.surveyLon = surveyLon;
    }

    public String getSurveyLat() {
        return surveyLat;
    }

    public void setSurveyLat(String surveyLat) {
        this.surveyLat = surveyLat;
    }

    public String getSurveyAltitude() {
        return surveyAltitude;
    }

    public void setSurveyAltitude(String surveyAltitude) {
        this.surveyAltitude = surveyAltitude;
    }

    public String getSurveyTemperature() {
        return surveyTemperature;
    }

    public void setSurveyTemperature(String surveyTemperature) {
        this.surveyTemperature = surveyTemperature;
    }

    public String getSurveyWeather() {
        return surveyWeather;
    }

    public void setSurveyWeather(String surveyWeather) {
        this.surveyWeather = surveyWeather;
    }

    public String getSurveyData() {
		return surveyData;
	}

	public void setSurveyData(String surveyData) {
		this.surveyData = surveyData;
	}

	public String getSurveyStxt() {
        return surveyStxt;
    }

    public void setSurveyStxt(String surveyStxt) {
        this.surveyStxt = surveyStxt;
    }

    public String getSurveyHjzl() {
        return surveyHjzl;
    }

    public void setSurveyHjzl(String surveyHjzl) {
        this.surveyHjzl = surveyHjzl;
    }

    public String getSurveyRwhd() {
        return surveyRwhd;
    }

    public void setSurveyRwhd(String surveyRwhd) {
        this.surveyRwhd = surveyRwhd;
    }

    public String getSurveyPs01() {
        return surveyPs01;
    }

    public void setSurveyPs01(String surveyPs01) {
        this.surveyPs01 = surveyPs01;
    }

    public String getSurveyPs02() {
        return surveyPs02;
    }

    public void setSurveyPs02(String surveyPs02) {
        this.surveyPs02 = surveyPs02;
    }

    public String getSurveyPs03() {
        return surveyPs03;
    }

    public void setSurveyPs03(String surveyPs03) {
        this.surveyPs03 = surveyPs03;
    }

    public String getSurveyPs04() {
        return surveyPs04;
    }

    public void setSurveyPs04(String surveyPs04) {
        this.surveyPs04 = surveyPs04;
    }

    @Override
    public String toString() {
        return "DataSurvey{" +
        "surveyId=" + surveyId +
        ", userId=" + userId +
        ", surveyName=" + surveyName +
        ", surveyTime=" + surveyTime +
        ", surveyLon=" + surveyLon +
        ", surveyLat=" + surveyLat +
        ", surveyAltitude=" + surveyAltitude +
        ", surveyTemperature=" + surveyTemperature +
        ", surveyWeather=" + surveyWeather +
        ", surveyData=" + surveyData +
        ", surveyStxt=" + surveyStxt +
        ", surveyHjzl=" + surveyHjzl +
        ", surveyRwhd=" + surveyRwhd +
        ", surveyPs01=" + surveyPs01 +
        ", surveyPs02=" + surveyPs02 +
        ", surveyPs03=" + surveyPs03 +
        ", surveyPs04=" + surveyPs04 +
        "}";
    }
}
