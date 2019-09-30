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
@TableName("dic_field_type")
public class DicFieldType implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "pk_id", type = IdType.AUTO)
    private Integer pkId;
    /**
     * 关联字典表字段pkid
     */
    @TableField("fk_index_item_id")
    private Integer fkIndexItemId;
    /**
     * 最大值（用于浮点型及整形的判断）
     */
    private String max;
    /**
     * 最小值（用于浮点型及整形的判断）
     */
    private String min;
    /**
     * 浮点行，保留小数点位数
     */
    @TableField("decimal_point")
    private String decimalPoint;
    /**
     * 字符串限制长度
     */
    @TableField("string_length")
    private String stringLength;
    /**
     * 单选按钮值
     */
    @TableField("rediao_type")
    private String rediaoType;
    /**
     * 单选下拉框值（名称）
     */
    @TableField("single_select")
    private String singleSelect;
    /**
     * 多选下拉框值（名称）
     */
    @TableField("multiple_select")
    private String multipleSelect;
    /**
     * 时间类型（yyyy-mm-dd，yyyy-mm-dd HH:MM:ss）
     */
    @TableField("time_limit")
    private String timeLimit;
    /**
     * 数据有效性
     */
    @TableField("data_status")
    private Integer dataStatus;

    public Integer getPkId() {
        return pkId;
    }

    public void setPkId(Integer pkId) {
        this.pkId = pkId;
    }

    public Integer getFkIndexItemId() {
        return fkIndexItemId;
    }

    public void setFkIndexItemId(Integer fkIndexItemId) {
        this.fkIndexItemId = fkIndexItemId;
    }

    public String getMax() {
        return max;
    }

    public void setMax(String max) {
        this.max = max;
    }

    public String getMin() {
        return min;
    }

    public void setMin(String min) {
        this.min = min;
    }

    public String getDecimalPoint() {
        return decimalPoint;
    }

    public void setDecimalPoint(String decimalPoint) {
        this.decimalPoint = decimalPoint;
    }

    public String getStringLength() {
        return stringLength;
    }

    public void setStringLength(String stringLength) {
        this.stringLength = stringLength;
    }

    public String getRediaoType() {
        return rediaoType;
    }

    public void setRediaoType(String rediaoType) {
        this.rediaoType = rediaoType;
    }

    public String getSingleSelect() {
        return singleSelect;
    }

    public void setSingleSelect(String singleSelect) {
        this.singleSelect = singleSelect;
    }

    public String getMultipleSelect() {
        return multipleSelect;
    }

    public void setMultipleSelect(String multipleSelect) {
        this.multipleSelect = multipleSelect;
    }

    public String getTimeLimit() {
		return timeLimit;
	}

	public void setTimeLimit(String timeLimit) {
		this.timeLimit = timeLimit;
	}

	public Integer getDataStatus() {
		return dataStatus;
	}

	public void setDataStatus(Integer dataStatus) {
		this.dataStatus = dataStatus;
	}

	@Override
    public String toString() {
        return "DicFieldType{" +
        "pkId=" + pkId +
        ", fkIndexItemId=" + fkIndexItemId +
        ", max=" + max +
        ", min=" + min +
        ", decimalPoint=" + decimalPoint +
        ", stringLength=" + stringLength +
        ", rediaoType=" + rediaoType +
        ", singleSelect=" + singleSelect +
        ", multipleSelect=" + multipleSelect +
        ", timeLimit=" + timeLimit +
        ", dataStatus=" + dataStatus +
        "}";
    }
}
