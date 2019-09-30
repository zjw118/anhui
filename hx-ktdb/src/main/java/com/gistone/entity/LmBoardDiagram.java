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
 * @since 2019-09-04
 */
@Data
public class LmBoardDiagram implements Serializable {

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
     * 关联红线id
     */
    private Integer redlineId;

    /**
     * 行政区划编号
     */
    private String code;

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
     * 文档路径
     */
    private String fileUrl;

    /**
     * 标识牌类型，'0 预置数据，1采集数据'
     */
    private Integer type;

    /**
     * 纬度
     */
    private Double latitude;

    /**
     * 标识牌树立时间
     */
    private Date createDate;

    /**
     * 添加人
     */
    private Integer createBy;

    /**
     * 审核人id
     */
    private Integer verifyBy;

    private String verifyPerson;

    /**
     * 入库时间
     */
    private Date saveDate;

    /**
     * 审核日期
     */
    private Date verifyDate;

    /**
     * 修改人
     */
    private Integer updateBy;

    /**
     * 修改时间
     */
    private Date updateDate;

    /**
     * 逻辑删除 0是删除，1正常，2，永久删除
     */
    private Integer delFlag;

    /**
     * 备注
     */
    private String remarks;

    /**
     * 创建人名称
     */
    @TableField(exist = false)
    private String createUser;

    /**
     * 所属地名称
     */
    @TableField(exist = false)
    private String placeName;
    @TableField(exist = false)
    List<LmBoardDiagramPhoto> photos;

    /**
     * 标准文档路径
     */
    private String wordUrl;



}
