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
 * @since 2019-03-20
 */
@TableName("lm_marker_relation_position")
@Data
public class LmMarkerRelationPosition implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 界桩id
     */
    @TableField("jz_id")
    private Integer jzId;
    /**
     * 界桩与方位物的相对距离
     */
    private Double distance;
    /**
     * 方向
     */
    private String direction;
    /**
     * 参照物
     */
    @TableField("of_reference")
    private String ofReference;



}
