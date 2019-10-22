package com.gistone.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 
 * </p>
 *
 * @author zf1017@foxmail.com
 * @since 2019-06-21
 */
@Data
public class TQuestionVerification implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    @TableId(value = "tqv_id", type = IdType.AUTO)
    private Integer tqvId;

    /**
     * 问题
     */
    private String tqvQuestion;

    /**
     * 编号
     */
    private String tqvSerivalNumber;

    /**
     * 设施名称
     */
    private String tqvFacilityName;

    /**
     * 设施类型
     */
    private String tqvFacilityType;

    /**
     * 所在功能分区（1：核心区，2：缓冲区，3：实验区）
     */
    private String tqvSectorization;

    /**
     * 面积
     */
    private String tqvArea;

    /**
     * 变化类型（1：不变，2：新增，3：规模扩大）
     */
    private String tqvChangeType;

    /**
     * 设施现状
     */
    private String tqvFacilityStatus;

    /**
     * 建设时间
     */
    private Date tqvBuildTime;

    /**
     * 有无手续（0：无，1：有）
     */
    private String tqvFormalities;

    /**
     * 批复及验收文号
     */
    private String tqvProof;

    /**
     * 存在问题及主要生态环境影响
     */
    private String tqvInfluence;

    /**
     * 处理情况
     */
    private String tqvResult;

    /**
     * 标注点id
     */
    private Integer tqvBzId;

    /**
     * 巡护记录id
     */
    private Integer tqvXhId;

    /**
     * 核查点的id
     */
    private Integer tqvPointId;

    /**
     * 添加时间
     */
    private Date tqvAddTime;

    /**
     * 核查单位
     */
    private String tqvCheckUnit;

    /**
     * 核查时间
     */
    private Date tqvCheckTime;

    /**
     * 核查人
     */
    private String tqvCheckPeople;

    /**
     * 联系方式
     */
    private String tqvContactWay;

    /**
     * 填表人
     */
    private String tqvPreparer;

    /**
     * 审核人
     */
    private String tqvVerifier;

    /**
     * 经度
     */
    private String tqvLng;

    /**
     * 纬度
     */
    private String tqvLat;

    /**
     * 现状图片
     */
    private String tqvImage;

    /**
     * 是否与遥感监测、地方核查一致
     */
    private String tqvRemoteSame;

    /**
     * 环评等相关手续：填写有或无，如果有，则需填写批复或验收文号。
     */
    private String tqvEnvEvaluate;

    /**
     * 填下发点位或新增点位。下发点位是指生态环境部下发给各巡查组的每个保护地的必看点位；新增点位是根据省里汇报、问题台账等线索，巡查组现场增加的点位。
     */
    private String tqvPointType;

    /**
     * 备注
     */
    private String tqvRemark;

    /**
     * 暂时用
     */
    private Integer glIds;



}
