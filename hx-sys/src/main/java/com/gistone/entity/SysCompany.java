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
@TableName("sys_company")
public class SysCompany extends  BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "PK_ID", type = IdType.AUTO)
    private Integer pkId;
    /**
     * 行政区划名称
     */
    @TableField("COM_NAME")
    private String comName;
    /**
     * 行政区划代码
     */
    @TableField("COM_CODE")
    private String comCode;
    /**
     * 行政区划级别
     */
    @TableField("COM_LEVEL")
    private Integer comLevel;
    /**
     * 上级行政区划代码
     */
    @TableField("COM_F_CODE")
    private String comFCode;
    /**
     * 上级行政区划id
     */
    @TableField("COM_F_PKID")
    private Integer comFPkid;
    /**
     * 经度
     */
    @TableField("LAT")
    private String lat;
    /**
     * 维度
     */
    @TableField("LON")
    private String lon;
@TableField(exist = false)
    private String code;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Integer getPkId() {
        return pkId;
    }

    public void setPkId(Integer pkId) {
        this.pkId = pkId;
    }

    public String getComName() {
        return comName;
    }

    public void setComName(String comName) {
        this.comName = comName;
    }

    public String getComCode() {
        return comCode;
    }

    public void setComCode(String comCode) {
        this.comCode = comCode;
    }

    public Integer getComLevel() {
        return comLevel;
    }

    public void setComLevel(Integer comLevel) {
        this.comLevel = comLevel;
    }

    public String getComFCode() {
        return comFCode;
    }

    public void setComFCode(String comFCode) {
        this.comFCode = comFCode;
    }

    public Integer getComFPkid() {
        return comFPkid;
    }

    public void setComFPkid(Integer comFPkid) {
        this.comFPkid = comFPkid;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLon() {
        return lon;
    }

    public void setLon(String lon) {
        this.lon = lon;
    }

    @Override
    public String toString() {
        return "SysCompany{" +
        "pkId=" + pkId +
        ", comName=" + comName +
        ", comCode=" + comCode +
        ", comLevel=" + comLevel +
        ", comFCode=" + comFCode +
        ", comFPkid=" + comFPkid +
        ", lat=" + lat +
        ", lon=" + lon +
        "}";
    }
}
