package com.gistone.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 
 * </p>
 *
 * @author xjc
 * @since 2019-02-28
 */
@TableName("lm_board")
public class LmXJCBoard implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "lb_id", type = IdType.AUTO)
    private Integer lbId;
    /**
     * 关联红线id
     */
    @TableField("lb_srld_id")
    private Integer lbSrldId;
    @TableField("lb_name")
    private String lbName;
    /**
     * 编号
     */
    @TableField("lb_code")
    private String lbCode;
    @TableField("lb_x")
    private Double lbX;
    @TableField("lb_y")
    private Double lbY;
    @TableField("lb_lon")
    private Double lbLon;
    @TableField("lb_lat")
    private Double lbLat;
    /**
     * 主管部门
     */
    @TableField("lb_zgbm")
    private String lbZgbm;
    /**
     * 监督部门
     */
    @TableField("lb_jdbm")
    private String lbJdbm;
    /**
     * 联系电话
     */
    @TableField("lb_phone")
    private String lbPhone;
    /**
     * 标识牌树立时间
     */
    @TableField("lb_setTime")
    private Date lbSettime;
    @TableField("lb_add_uid")
    private Integer lbAddUid;
    @TableField("lb_add_time")
    private Date lbAddTime;
    @TableField("lb_upd_uid")
    private Integer lbUpdUid;
    @TableField("lb_upd_time")
    private Date lbUpdTime;
    /**
     * 删除状态：0：未删除；1：已删除；
     */
    @TableField("lb_id_del")
    private String lbIdDel;


    public Integer getLbId() {
        return lbId;
    }

    public void setLbId(Integer lbId) {
        this.lbId = lbId;
    }

    public Integer getLbSrldId() {
        return lbSrldId;
    }

    public void setLbSrldId(Integer lbSrldId) {
        this.lbSrldId = lbSrldId;
    }

    public String getLbName() {
        return lbName;
    }

    public void setLbName(String lbName) {
        this.lbName = lbName;
    }

    public String getLbCode() {
        return lbCode;
    }

    public void setLbCode(String lbCode) {
        this.lbCode = lbCode;
    }

    public Double getLbX() {
        return lbX;
    }

    public void setLbX(Double lbX) {
        this.lbX = lbX;
    }

    public Double getLbY() {
        return lbY;
    }

    public void setLbY(Double lbY) {
        this.lbY = lbY;
    }

    public Double getLbLon() {
        return lbLon;
    }

    public void setLbLon(Double lbLon) {
        this.lbLon = lbLon;
    }

    public Double getLbLat() {
        return lbLat;
    }

    public void setLbLat(Double lbLat) {
        this.lbLat = lbLat;
    }

    public String getLbZgbm() {
        return lbZgbm;
    }

    public void setLbZgbm(String lbZgbm) {
        this.lbZgbm = lbZgbm;
    }

    public String getLbJdbm() {
        return lbJdbm;
    }

    public void setLbJdbm(String lbJdbm) {
        this.lbJdbm = lbJdbm;
    }

    public String getLbPhone() {
        return lbPhone;
    }

    public void setLbPhone(String lbPhone) {
        this.lbPhone = lbPhone;
    }

    public Date getLbSettime() {
        return lbSettime;
    }

    public void setLbSettime(Date lbSettime) {
        this.lbSettime = lbSettime;
    }

    public Integer getLbAddUid() {
        return lbAddUid;
    }

    public void setLbAddUid(Integer lbAddUid) {
        this.lbAddUid = lbAddUid;
    }

    public Date getLbAddTime() {
        return lbAddTime;
    }

    public void setLbAddTime(Date lbAddTime) {
        this.lbAddTime = lbAddTime;
    }

    public Integer getLbUpdUid() {
        return lbUpdUid;
    }

    public void setLbUpdUid(Integer lbUpdUid) {
        this.lbUpdUid = lbUpdUid;
    }

    public Date getLbUpdTime() {
        return lbUpdTime;
    }

    public void setLbUpdTime(Date lbUpdTime) {
        this.lbUpdTime = lbUpdTime;
    }

    public String getLbIdDel() {
        return lbIdDel;
    }

    public void setLbIdDel(String lbIdDel) {
        this.lbIdDel = lbIdDel;
    }

    @Override
    public String toString() {
        return "LmXJCBoard{" +
        "lbId=" + lbId +
        ", lbSrldId=" + lbSrldId +
        ", lbName=" + lbName +
        ", lbCode=" + lbCode +
        ", lbX=" + lbX +
        ", lbY=" + lbY +
        ", lbLon=" + lbLon +
        ", lbLat=" + lbLat +
        ", lbZgbm=" + lbZgbm +
        ", lbJdbm=" + lbJdbm +
        ", lbPhone=" + lbPhone +
        ", lbSettime=" + lbSettime +
        ", lbAddUid=" + lbAddUid +
        ", lbAddTime=" + lbAddTime +
        ", lbUpdUid=" + lbUpdUid +
        ", lbUpdTime=" + lbUpdTime +
        ", lbIdDel=" + lbIdDel +
        "}";
    }
}
