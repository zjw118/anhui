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
@TableName("supe_data_type")
public class SupeDataType implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 唯一主键
     */
    @TableId(value = "sdt_id", type = IdType.AUTO)
    private Integer sdtId;
    /**
     * 人类活动类型名称
     */
    @TableField("sdt_name")
    private String sdtName;


    public Integer getSdtId() {
        return sdtId;
    }

    public void setSdtId(Integer sdtId) {
        this.sdtId = sdtId;
    }

    public String getSdtName() {
        return sdtName;
    }

    public void setSdtName(String sdtName) {
        this.sdtName = sdtName;
    }

    @Override
    public String toString() {
        return "SupeDataType{" +
        "sdtId=" + sdtId +
        ", sdtName=" + sdtName +
        "}";
    }
}
