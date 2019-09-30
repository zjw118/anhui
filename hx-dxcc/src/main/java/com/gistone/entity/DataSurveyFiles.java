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
 * @since 2019-03-27
 */
@TableName("data_survey_files")
public class DataSurveyFiles implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "pkid", type = IdType.AUTO)
    private Integer pkid;
    /**
     * 调查子表id
     */
    @TableField("index_item_id")
    private Integer indexItemId;
    /**
     * 每次上传图片标识
     */
    private String identification;
    /**
     * 图片路径
     */
    private String path;
    /**
     * 备用字段1
     */
    private String ps01;
    /**
     * 备用字段2
     */
    private String ps02;
    /**
     * 备用字段3
     */
    private String ps03;
    /**
     * 备用字段4
     */
    private String ps04;


    public Integer getPkid() {
        return pkid;
    }

    public void setPkid(Integer pkid) {
        this.pkid = pkid;
    }

    public Integer getIndexItemId() {
        return indexItemId;
    }

    public void setIndexItemId(Integer indexItemId) {
        this.indexItemId = indexItemId;
    }

    public String getIdentification() {
        return identification;
    }

    public void setIdentification(String identification) {
        this.identification = identification;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
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

    public String getPs03() {
        return ps03;
    }

    public void setPs03(String ps03) {
        this.ps03 = ps03;
    }

    public String getPs04() {
        return ps04;
    }

    public void setPs04(String ps04) {
        this.ps04 = ps04;
    }

    @Override
    public String toString() {
        return "DataSurveyFiles{" +
        "pkid=" + pkid +
        ", indexItemId=" + indexItemId +
        ", identification=" + identification +
        ", path=" + path +
        ", ps01=" + ps01 +
        ", ps02=" + ps02 +
        ", ps03=" + ps03 +
        ", ps04=" + ps04 +
        "}";
    }
}
