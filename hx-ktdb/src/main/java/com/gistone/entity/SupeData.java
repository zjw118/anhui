package com.gistone.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.util.Date;
import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author xjc
 * @since 2019-02-28
 */
@TableName("supe_data")
public class SupeData implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 唯一主键
     */
    @TableId(value = "sd_id", type = IdType.AUTO)
    private Integer sdId;
    /**
     * 所属红线斑块id
     */
    @TableField("sd_redline_id")
    private Integer sdRedlineId;
    /**
     * 人类活动台账名称
     */
    @TableField("sd_name")
    private String sdName;
    /**
     * 平面坐标x
     */
    @TableField("sd_x")
    private String sdX;
    /**
     * 平面坐标y
     */
    @TableField("sd_y")
    private String sdY;
    /**
     * 人类活动类型
     */
    @TableField("sd_type")
    private String sdType;
    /**
     * 人口数量
     */
    @TableField("sd_population")
    private String sdPopulation;
    /**
     * 面积
     */
    @TableField("sd_area")
    private String sdArea;
    /**
     * 经济总量
     */
    @TableField("sd_jjnum")
    private String sdJjnum;
    /**
     * 巡查人员
     */
    @TableField("sd_c_user")
    private String sdCUser;
    /**
     * 巡查时间
     */
    @TableField("sd_c_time")
    private Date sdCTime;
    /**
     * 活动地址
     */
    @TableField("sd_address")
    private String sdAddress;
    /**
     * 添加人
     */
    @TableField("sd_add_uid")
    private Integer sdAddUid;
    /**
     * 添加时间
     */
    @TableField("sd_add_time")
    private Date sdAddTime;
    /**
     * 修改人
     */
    @TableField("sd_upd_uid")
    private Integer sdUpdUid;
    /**
     * 修改时间
     */
    @TableField("sd_upd_time")
    private Date sdUpdTime;
    /**
     * 0正常1删除
     */
    @TableField("sd_is_del")
    private Integer sdIsDel;
    /**
     * 备注
     */
    @TableField("sd_remark")
    private String sdRemark;


    public Integer getSdId() {
        return sdId;
    }

    public void setSdId(Integer sdId) {
        this.sdId = sdId;
    }

    public Integer getSdRedlineId() {
        return sdRedlineId;
    }

    public void setSdRedlineId(Integer sdRedlineId) {
        this.sdRedlineId = sdRedlineId;
    }

    public String getSdName() {
        return sdName;
    }

    public void setSdName(String sdName) {
        this.sdName = sdName;
    }

    public String getSdX() {
        return sdX;
    }

    public void setSdX(String sdX) {
        this.sdX = sdX;
    }

    public String getSdY() {
        return sdY;
    }

    public void setSdY(String sdY) {
        this.sdY = sdY;
    }

    public String getSdType() {
        return sdType;
    }

    public void setSdType(String sdType) {
        this.sdType = sdType;
    }

    public String getSdPopulation() {
        return sdPopulation;
    }

    public void setSdPopulation(String sdPopulation) {
        this.sdPopulation = sdPopulation;
    }

    public String getSdArea() {
        return sdArea;
    }

    public void setSdArea(String sdArea) {
        this.sdArea = sdArea;
    }

    public String getSdJjnum() {
        return sdJjnum;
    }

    public void setSdJjnum(String sdJjnum) {
        this.sdJjnum = sdJjnum;
    }

    public String getSdCUser() {
        return sdCUser;
    }

    public void setSdCUser(String sdCUser) {
        this.sdCUser = sdCUser;
    }

    public Date getSdCTime() {
        return sdCTime;
    }

    public void setSdCTime(Date sdCTime) {
        this.sdCTime = sdCTime;
    }

    public String getSdAddress() {
        return sdAddress;
    }

    public void setSdAddress(String sdAddress) {
        this.sdAddress = sdAddress;
    }

    public Integer getSdAddUid() {
        return sdAddUid;
    }

    public void setSdAddUid(Integer sdAddUid) {
        this.sdAddUid = sdAddUid;
    }

    public Date getSdAddTime() {
        return sdAddTime;
    }

    public void setSdAddTime(Date sdAddTime) {
        this.sdAddTime = sdAddTime;
    }

    public Integer getSdUpdUid() {
        return sdUpdUid;
    }

    public void setSdUpdUid(Integer sdUpdUid) {
        this.sdUpdUid = sdUpdUid;
    }

    public Date getSdUpdTime() {
        return sdUpdTime;
    }

    public void setSdUpdTime(Date sdUpdTime) {
        this.sdUpdTime = sdUpdTime;
    }

    public Integer getSdIsDel() {
        return sdIsDel;
    }

    public void setSdIsDel(Integer sdIsDel) {
        this.sdIsDel = sdIsDel;
    }

    public String getSdRemark() {
        return sdRemark;
    }

    public void setSdRemark(String sdRemark) {
        this.sdRemark = sdRemark;
    }

    @Override
    public String toString() {
        return "SupeData{" +
        "sdId=" + sdId +
        ", sdRedlineId=" + sdRedlineId +
        ", sdName=" + sdName +
        ", sdX=" + sdX +
        ", sdY=" + sdY +
        ", sdType=" + sdType +
        ", sdPopulation=" + sdPopulation +
        ", sdArea=" + sdArea +
        ", sdJjnum=" + sdJjnum +
        ", sdCUser=" + sdCUser +
        ", sdCTime=" + sdCTime +
        ", sdAddress=" + sdAddress +
        ", sdAddUid=" + sdAddUid +
        ", sdAddTime=" + sdAddTime +
        ", sdUpdUid=" + sdUpdUid +
        ", sdUpdTime=" + sdUpdTime +
        ", sdIsDel=" + sdIsDel +
        ", sdRemark=" + sdRemark +
        "}";
    }
}
