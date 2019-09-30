package com.gistone.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 
 * </p>
 *
 * @author xjc
 * @since 2019-03-20
 */
@TableName("lm_marker_photo")
@Data
public class LmMarkerPhoto implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 界桩id
     */
    @TableField("jz_id")
    private Integer jzId;
    /**
     * 界桩现场图片地址
     */
    private String url;
    /**
     * 创建时间
     */
    @TableField("create_time")
    private Date createTime;

    /**
     * 图片类型1.东，2.南，3.西，4.北，5.中间位置
     */
    private Integer type;

    /**
     * 照片编号
     */
    private String number;


}
