package com.gistone.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * <p>
 *
 * </p>
 *
 * @author xjc
 * @since 2019-03-14
 */
@TableName("lm_marker_mobile")
@Data
public class LmMarkerMobile implements Serializable {

    private static final long serialVersionUID = 1L;
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;


    /**
     * 界桩编号
     */
    private String jzNumber;

    /**
     * 界桩刻号
     */
    private String jzKh;
    /**
     * 行政区划
     */
    private String code;
    /**
     * 是否为界桩 0不是，1是
     */
//    @TableField("is_bs")
//    private Integer isBs;

    /**
     * 界桩地貌
     */
    private String landform;

    /**
     * 校对经度
     */
    private Double proofLon;

    /**
     * 校对纬度
     */
    private Double proofLat;
    /**
     * 经度
     */
    private Double longitude;

    /**
     * 度分秒经度
     */
    @TableField(exist = false)
    private String strLon;

    /**
     * 度分秒纬度
     */
    @TableField(exist = false)
    private String strLat;

    /**
     * 纬度
     */
    private Double latitude;
    /**
     * 所在红线区
     */
    @TableField("redline_id")
    private Integer redlineId;

    /**
     * 所属红线名称
     */
    @TableField(exist = false)
    private String redlineName;

    /**
     * 备注
     */
    private String remark;


    /**
     * 界桩位置缩略图
     */
    private String positionPicture;

    /**
     * 审核人
     */
    @TableField("verify_person")
    private String verifyPerson;

    /**
     * 审核时间
     */
    private Date verifyTime;

    /**
     * 数据入库时间
     */
    private Date saveTime;


    /**
     * 创建人
     */
    private Integer createUser;
    /**
     * 创建人
     */
    @TableField(exist = false)
    private String createUserName;

    /**
     * 创建时间
     */
    @TableField("create_time")
    private Date createTime;


    /**
     * 更新时间
     */

    @TableField("update_time")
    private Date updateTime;

    /**
     * 界桩现场图片
     */
    @TableField(exist = false)
    private List<LmMarkerPhoto> lmMarkerPhotos;


    /**
     * 界桩与方位物位置信息
     */
    @TableField(exist = false)
    private List<LmMarkerRelationPosition> lmMarkerRelationPositions;

    /**
     * 记录类型，0预置数据，1采集数据
     */
    private Integer type;
    /**
     * 所属地方名
     */
    @TableField(exist = false)
    private String placeName;

    /**
     * 逻辑删除
     */
    private Integer delFlag;
    /**
     * 登记表路径
     */
    private String fileUrl;

    /**
     * 是否附标识牌
     */
    private Integer isBs;

    /**
     * 标准文档路径
     */
    private String wordUrl;


}
