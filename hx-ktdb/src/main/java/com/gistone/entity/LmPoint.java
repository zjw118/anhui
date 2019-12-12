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
 * @since 2019-02-28
 */
@TableName("lm_point")
@Data
public class LmPoint implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "lp_id", type = IdType.AUTO)
    private Integer lpId;
    /**
     * 编号
     */
    @TableField("lp_code")
    private String lpCode;
    /**
     * 红线台账id
     */
    @TableField("lp_srld_id")
    private Integer lpSrldId;
    /**
     * 经度
     */
    @TableField("lp_lon")
    private Double lpLon;
    /**
     * 纬度
     */
    @TableField("lp_lat")
    private Double lpLat;
    @TableField("lp_x")
    private Double lpX;
    @TableField("lp_y")
    private Double lpY;
    /**
     * 采集时间
     */
    @TableField("lp_getTime")
    private Date lpGettime;

    @TableField("lp_add_uid")
    private Integer lpAddUid;

    /**
     * 创建人
     */
    @TableField(exist = false)
    private String createUser;
    @TableField("lp_add_time")
    private Date lpAddTime;
    @TableField("lp_upd_uid")
    private Integer lpUpdUid;

    /**
     * 修改人
     */
    @TableField(exist = false)
    private String updateUser;
    @TableField("lp_upd_time")
    private Date lpUpdTime;
    /**
     * 删除状态：0：未删除；1：已删除；
     */
    @TableField("lp_id_del")
    private String lpIdDel;

    /**
     * 所属地名称
     */
    @TableField(exist = false)
    private String placeName;


}
