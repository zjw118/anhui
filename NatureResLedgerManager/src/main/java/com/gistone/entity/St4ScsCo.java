package com.gistone.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;

/**
 * <p>
 * 人类活动台账信息表
 * </p>
 *
 * @author zhaojingwei
 * @since 2019-10-23
 */
public class St4ScsCo implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "co001", type = IdType.AUTO)
    private Integer co001;

    /**
     * 台账名称
     */
    private String name;

    /**
     * 台账年份
     */
    private String year;

    /**
     * 行政区
     */
    private String adminRegion;

    /**
     * 创建人id
     */
    private Integer createBy;

    /**
     * 创建时间
     */
    private Date createDate;

    private Integer updateBy;

    private Date updateDate;

    /**
     * 备注
     */
    private String remark;

    /**
     * 逻辑删除 0删除，1未删除
     */
    private Integer delFlag;


    public Integer getCo001() {
        return co001;
    }

    public void setCo001(Integer co001) {
        this.co001 = co001;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getAdminRegion() {
        return adminRegion;
    }

    public void setAdminRegion(String adminRegion) {
        this.adminRegion = adminRegion;
    }

    public Integer getCreateBy() {
        return createBy;
    }

    public void setCreateBy(Integer createBy) {
        this.createBy = createBy;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Integer getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(Integer updateBy) {
        this.updateBy = updateBy;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Integer getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(Integer delFlag) {
        this.delFlag = delFlag;
    }

    @Override
    public String toString() {
        return "St4ScsCo{" +
        "co001=" + co001 +
        ", name=" + name +
        ", year=" + year +
        ", adminRegion=" + adminRegion +
        ", createBy=" + createBy +
        ", createDate=" + createDate +
        ", updateBy=" + updateBy +
        ", updateDate=" + updateDate +
        ", remark=" + remark +
        ", delFlag=" + delFlag +
        "}";
    }
}
