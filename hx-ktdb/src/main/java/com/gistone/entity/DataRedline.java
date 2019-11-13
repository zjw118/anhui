package com.gistone.entity;

import com.baomidou.mybatisplus.extension.activerecord.Model;
import java.time.LocalDateTime;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author zf1017@foxmail.com
 * @since 2019-11-13
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class DataRedline extends Model<DataRedline> {

    private static final long serialVersionUID = 1L;

    /**
     * 唯一主键
     */
    private Integer srldId;

    /**
     * 红线编码
     */
    private String srldNumber;

    /**
     * 所在地行政区划
     */
    private String srldCode;

    /**
     * 名称
     */
    private String srldName;

    /**
     * 人口
     */
    private String srldPopulation;

    /**
     * 类型
     */
    private String srldType;

    /**
     * 生态系统服务功能与保护目标
     */
    private String target;

    /**
     * 生态系统服务功能与保护目标
     */
    private String srldTarget;

    /**
     * 地理位置
     */
    private String srldPosition;

    /**
     * 区域面积
     */
    private Double srldArea;

    /**
     * 生态系统与植被类型
     */
    private String srldPlantType;

    /**
     * 主要人为活动
     */
    private String srldActive;

    /**
     * 生态环境问题
     */
    private String srldProblem;

    /**
     * 管控措施
     */
    private String srldControl;

    /**
     * 添加时间
     */
    private LocalDateTime srldAddTime;

    /**
     * 添加人
     */
    private Integer srldAddUid;

    /**
     * 0正常1删除
     */
    private Integer srldIsDel;


    @Override
    protected Serializable pkVal() {
        return this.srldId;
    }

}
