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
@TableName("dic_index_item")
public class DicIndexItem implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "pk_id", type = IdType.AUTO)
    private Integer pkId;
    /**
     * 1自然背景调查2生态系统状况调查3环境质量调查4人为活动调查
     */
    @TableField("index_item")
    private Integer indexItem;
    /**
     * 调查字段名称
     */
    @TableField("filed_name")
    private String filedName;
    /**
     * 调查字段类型
1字符串，2整形，3浮点型，4单选下拉框，5多选下拉框，6checked，7时间类型，
     */
    @TableField("filed_type")
    private Integer filedType;


    public Integer getPkId() {
        return pkId;
    }

    public void setPkId(Integer pkId) {
        this.pkId = pkId;
    }

    public Integer getIndexItem() {
        return indexItem;
    }

    public void setIndexItem(Integer indexItem) {
        this.indexItem = indexItem;
    }

    public String getFiledName() {
        return filedName;
    }

    public void setFiledName(String filedName) {
        this.filedName = filedName;
    }

    public Integer getFiledType() {
        return filedType;
    }

    public void setFiledType(Integer filedType) {
        this.filedType = filedType;
    }

    @Override
    public String toString() {
        return "DicIndexItem{" +
        "pkId=" + pkId +
        ", indexItem=" + indexItem +
        ", filedName=" + filedName +
        ", filedType=" + filedType +
        "}";
    }
}
