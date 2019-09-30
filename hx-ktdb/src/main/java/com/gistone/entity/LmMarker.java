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
@TableName("lm_marker")
public class LmMarker implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 唯一主键
     */
    @TableId(value = "lm_id", type = IdType.AUTO)
    private Integer lmId;
    /**
     * 生态红线id
     */
    @TableField("lm_srld_id")
    private Integer lmSrldId;
    /**
     * 拐点id
     */
    @TableField("lm_lp_id")
    private Integer lmLpId;
    /**
     * 界桩刻号
     */
    @TableField("lm_jzkh")
    private String lmJzkh;
    /**
     * 所在地
     */
    @TableField("lm_szd")
    private String lmSzd;
    /**
     * 是否附标识牌：0：否；1：是；
     */
    @TableField("lm_sffbsp")
    private String lmSffbsp;
    /**
     * 界桩位置略图
     */
    @TableField("lm_jzwzlt")
    private String lmJzwzlt;
    /**
     * 界桩位置略图名称
     */
    @TableField("lm_jzwzlt_name")
    private String lmJzwzltName;
    /**
     * 界桩现场照片
     */
    @TableField("lm_jzxczp")
    private String lmJzxczp;
    /**
     * 界桩现场照片名称
     */
    @TableField("lm_jzxczp_name")
    private String lmJzxczpName;
    /**
     * 备注
     */
    @TableField("lm_beizhu")
    private String lmBeizhu;
    /**
     * 技术负责人
     */
    @TableField("lm_jsfzr")
    private String lmJsfzr;
    /**
     * 设立时间
     */
    @TableField("lm_setTime")
    private Date lmSettime;
    /**
     * 添加用户id
     */
    @TableField("lm_add_uid")
    private Integer lmAddUid;
    /**
     * 添加时间
     */
    @TableField("lm_add_time")
    private Date lmAddTime;
    /**
     * 修改用户id
     */
    @TableField("lm_upd_uid")
    private Integer lmUpdUid;
    /**
     * 修改时间
     */
    @TableField("lm_upd_time")
    private Date lmUpdTime;
    /**
     * 修改时间
     */
    @TableField("lm_id_del")
    private String lmIdDel;


    public Integer getLmId() {
        return lmId;
    }

    public void setLmId(Integer lmId) {
        this.lmId = lmId;
    }

    public Integer getLmSrldId() {
        return lmSrldId;
    }

    public void setLmSrldId(Integer lmSrldId) {
        this.lmSrldId = lmSrldId;
    }

    public Integer getLmLpId() {
        return lmLpId;
    }

    public void setLmLpId(Integer lmLpId) {
        this.lmLpId = lmLpId;
    }

    public String getLmJzkh() {
        return lmJzkh;
    }

    public void setLmJzkh(String lmJzkh) {
        this.lmJzkh = lmJzkh;
    }

    public String getLmSzd() {
        return lmSzd;
    }

    public void setLmSzd(String lmSzd) {
        this.lmSzd = lmSzd;
    }

    public String getLmSffbsp() {
        return lmSffbsp;
    }

    public void setLmSffbsp(String lmSffbsp) {
        this.lmSffbsp = lmSffbsp;
    }

    public String getLmJzwzlt() {
        return lmJzwzlt;
    }

    public void setLmJzwzlt(String lmJzwzlt) {
        this.lmJzwzlt = lmJzwzlt;
    }

    public String getLmJzwzltName() {
        return lmJzwzltName;
    }

    public void setLmJzwzltName(String lmJzwzltName) {
        this.lmJzwzltName = lmJzwzltName;
    }

    public String getLmJzxczp() {
        return lmJzxczp;
    }

    public void setLmJzxczp(String lmJzxczp) {
        this.lmJzxczp = lmJzxczp;
    }

    public String getLmJzxczpName() {
        return lmJzxczpName;
    }

    public void setLmJzxczpName(String lmJzxczpName) {
        this.lmJzxczpName = lmJzxczpName;
    }

    public String getLmBeizhu() {
        return lmBeizhu;
    }

    public void setLmBeizhu(String lmBeizhu) {
        this.lmBeizhu = lmBeizhu;
    }

    public String getLmJsfzr() {
        return lmJsfzr;
    }

    public void setLmJsfzr(String lmJsfzr) {
        this.lmJsfzr = lmJsfzr;
    }

    public Date getLmSettime() {
        return lmSettime;
    }

    public void setLmSettime(Date lmSettime) {
        this.lmSettime = lmSettime;
    }

    public Integer getLmAddUid() {
        return lmAddUid;
    }

    public void setLmAddUid(Integer lmAddUid) {
        this.lmAddUid = lmAddUid;
    }

    public Date getLmAddTime() {
        return lmAddTime;
    }

    public void setLmAddTime(Date lmAddTime) {
        this.lmAddTime = lmAddTime;
    }

    public Integer getLmUpdUid() {
        return lmUpdUid;
    }

    public void setLmUpdUid(Integer lmUpdUid) {
        this.lmUpdUid = lmUpdUid;
    }

    public Date getLmUpdTime() {
        return lmUpdTime;
    }

    public void setLmUpdTime(Date lmUpdTime) {
        this.lmUpdTime = lmUpdTime;
    }

    public String getLmIdDel() {
        return lmIdDel;
    }

    public void setLmIdDel(String lmIdDel) {
        this.lmIdDel = lmIdDel;
    }

    @Override
    public String toString() {
        return "LmMarker{" +
        "lmId=" + lmId +
        ", lmSrldId=" + lmSrldId +
        ", lmLpId=" + lmLpId +
        ", lmJzkh=" + lmJzkh +
        ", lmSzd=" + lmSzd +
        ", lmSffbsp=" + lmSffbsp +
        ", lmJzwzlt=" + lmJzwzlt +
        ", lmJzwzltName=" + lmJzwzltName +
        ", lmJzxczp=" + lmJzxczp +
        ", lmJzxczpName=" + lmJzxczpName +
        ", lmBeizhu=" + lmBeizhu +
        ", lmJsfzr=" + lmJsfzr +
        ", lmSettime=" + lmSettime +
        ", lmAddUid=" + lmAddUid +
        ", lmAddTime=" + lmAddTime +
        ", lmUpdUid=" + lmUpdUid +
        ", lmUpdTime=" + lmUpdTime +
        ", lmIdDel=" + lmIdDel +
        "}";
    }
}
