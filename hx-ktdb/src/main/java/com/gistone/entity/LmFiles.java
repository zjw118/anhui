package com.gistone.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author xjc
 * @since 2019-02-28
 */
@TableName("lm_files")
@Data
public class LmFiles implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    @TableId(value = "lf_id", type = IdType.AUTO)
    private Integer lfId;
    /**
     * 标识标牌id
     */
    @TableField("lf_board_id")
    private Integer lfBoardId;
    /**
     * 图片路径
     */
    @TableField("lf_path")
    private String lfPath;
    /**
     * 图片名称
     */
    @TableField("lf_name")
    private String lfName;
    /**
     * 图片绝对路径
     */
    @TableField("lf_filePath")
    private String lfFilepath;
    /**
     * 1:标识标牌
     */
    @TableField("lf_type")
    private String lfType;


    public Integer getLfId() {
        return lfId;
    }

    public void setLfId(Integer lfId) {
        this.lfId = lfId;
    }

    public Integer getLfBoardId() {
        return lfBoardId;
    }

    public void setLfBoardId(Integer lfBoardId) {
        this.lfBoardId = lfBoardId;
    }

    public String getLfPath() {
        return lfPath;
    }

    public void setLfPath(String lfPath) {
        this.lfPath = lfPath;
    }

    public String getLfName() {
        return lfName;
    }

    public void setLfName(String lfName) {
        this.lfName = lfName;
    }

    public String getLfFilepath() {
        return lfFilepath;
    }

    public void setLfFilepath(String lfFilepath) {
        this.lfFilepath = lfFilepath;
    }

    public String getLfType() {
        return lfType;
    }

    public void setLfType(String lfType) {
        this.lfType = lfType;
    }

    @Override
    public String toString() {
        return "LmFiles{" +
        "lfId=" + lfId +
        ", lfBoardId=" + lfBoardId +
        ", lfPath=" + lfPath +
        ", lfName=" + lfName +
        ", lfFilepath=" + lfFilepath +
        ", lfType=" + lfType +
        "}";
    }
}
