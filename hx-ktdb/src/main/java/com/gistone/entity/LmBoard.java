package com.gistone.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 
 * </p>
 *
 * @author zf1017@foxmail.com
 * @since 2019-04-29
 */
@Data
public class LmBoard implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 红线编码
     */
    private String redlineNum;

    /**
     * 行政区划编码
     */
    private String code;

    /**
     * 关联红线id
     */
    private Integer redlineId;


    /**
     * 标识牌内容
     */
    private String content;

    /**
     * 编号
     */
    private String number;

    /**
     * 校正经度
     */
    private Double proofLon;
    /**
     * 度分秒经度
     */
    @TableField(exist = false)
    private String strLon;



    /**
     * 校正纬度
     */
    private Double proofLat;
    /**
     * 度分秒纬度
     */
    @TableField(exist = false)
    private String strLat;

    /**
     * 经度
     */
    private Double longitude;

    /**
     * 纬度
     */
    private Double latitude;

    /**
     * 0预置数据，1采集数据
     */
    private Integer type;

    /**
     * 标识牌树立时间
     */
    private Date createDate;

    /**
     * 添加人
     */
    private Integer createBy;
    /**
     * 入库时间
     */
    private Date saveDate;

    /**
     * 创建人
     */
    @TableField(exist = false)
    private String createUser;

    /**
     * 修改人
     */
    private Integer updateBy;

    /**
     * 修改时间
     */
    private Date updateDate;

    /**
     * 逻辑删除 0是删除，1正常
     */
    private Integer delFlag;

    /**
     * 备注
     */
    private String remarks;

    /**
     * 标识牌现场图片
     */
    @TableField(exist = false)
    private List<LmBoardPhoto> lmBoardPhotos;
    /**
     * 红线名称
     */
    @TableField(exist = false)
    private String redlineName;

    /**
     * 所在行政区划名称
     */
    @TableField(exist = false)
    private String placeName;

    /**
     * 审核人
     */
    private Integer verifyBy;

    /**
     * 审核人姓名
     */
    @TableField(exist = false)
    private String verifyUser;

    /**
     * 审核人
     */
    private String verifyPerson;

    /**
     * 审核日期
     */
    private Date verifyDate;
    /**
     * word文档地址
     */
    private String fileUrl;

    /**
     * 标准文档路径
     */
    private String wordUrl;




}
