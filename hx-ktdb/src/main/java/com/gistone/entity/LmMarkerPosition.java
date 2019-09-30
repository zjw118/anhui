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
 * @since 2019-02-28
 */
@TableName("lm_marker_position")
public class LmMarkerPosition implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "lmp_id", type = IdType.AUTO)
    private Integer lmpId;
    /**
     * 界桩id
     */
    @TableField("lmp_lb_id")
    private Integer lmpLbId;
    @TableField("lmp_code")
    private Integer lmpCode;
    /**
     * 距离：m
     */
    @TableField("lmp_distance")
    private Double lmpDistance;
    /**
     * 方位物位置
     */
    @TableField("lmp_position")
    private String lmpPosition;


    public Integer getLmpId() {
        return lmpId;
    }

    public void setLmpId(Integer lmpId) {
        this.lmpId = lmpId;
    }

    public Integer getLmpLbId() {
        return lmpLbId;
    }

    public void setLmpLbId(Integer lmpLbId) {
        this.lmpLbId = lmpLbId;
    }

    public Integer getLmpCode() {
        return lmpCode;
    }

    public void setLmpCode(Integer lmpCode) {
        this.lmpCode = lmpCode;
    }

    public Double getLmpDistance() {
        return lmpDistance;
    }

    public void setLmpDistance(Double lmpDistance) {
        this.lmpDistance = lmpDistance;
    }

    public String getLmpPosition() {
        return lmpPosition;
    }

    public void setLmpPosition(String lmpPosition) {
        this.lmpPosition = lmpPosition;
    }

    @Override
    public String toString() {
        return "LmMarkerPosition{" +
        "lmpId=" + lmpId +
        ", lmpLbId=" + lmpLbId +
        ", lmpCode=" + lmpCode +
        ", lmpDistance=" + lmpDistance +
        ", lmpPosition=" + lmpPosition +
        "}";
    }
}
