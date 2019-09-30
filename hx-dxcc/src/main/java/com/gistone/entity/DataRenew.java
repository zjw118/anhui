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
 * @since 2019-04-19
 */
@TableName("data_renew")
public class DataRenew implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "pk_id", type = IdType.AUTO)
    private Integer pkId;
    /**
     * sft文件路径
     */
    @TableField("sft_path")
    private String sftPath;
    /**
     * db文件路径
     */
    @TableField("db_path")
    private String dbPath;
    /**
     * 更新类型（1sft文件更新  2db文件更新  0都更新）
     */
    private String type;
    /**
     * 版本号
     */
    private String version;
    /**
     * 更新说明
     */
    @TableField("renew_remark")
    private String renewRemark;
    /**
     * 更新时间
     */
    @TableField("add_time")
    private String addTime;
    /**
     * 更新人
     */
    @TableField("add_uid")
    private String addUid;
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

    public String getSftPath() {
        return sftPath;
    }

    public void setSftPath(String sftPath) {
        this.sftPath = sftPath;
    }

    public String getDbPath() {
        return dbPath;
    }

    public void setDbPath(String dbPath) {
        this.dbPath = dbPath;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getRenewRemark() {
        return renewRemark;
    }

    public void setRenewRemark(String renewRemark) {
        this.renewRemark = renewRemark;
    }

    public String getAddTime() {
        return addTime;
    }

    public void setAddTime(String addTime) {
        this.addTime = addTime;
    }

    public String getAddUid() {
        return addUid;
    }

    public void setAddUid(String addUid) {
        this.addUid = addUid;
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
        return "DataRenew{" +
        "pkId=" + pkId +
        ", sftPath=" + sftPath +
        ", dbPath=" + dbPath +
        ", type=" + type +
        ", version=" + version +
        ", renewRemark=" + renewRemark +
        ", addTime=" + addTime +
        ", addUid=" + addUid +
        ", ps01=" + ps01 +
        ", ps02=" + ps02 +
        "}";
    }
}
