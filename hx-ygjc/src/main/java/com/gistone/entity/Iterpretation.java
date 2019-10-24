package com.gistone.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 人类活动解译信息表
 * </p>
 *
 * @author zf1017@foxmail.com
 * @since 2019-10-18
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class Iterpretation extends Model<Iterpretation> {

    private static final long serialVersionUID = 1L;



    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 影像关联id
     */
    private Integer imageId;

    /**
     * 活动名称
     */
    private String activeName;

    /**
     * 活动类型
     */
    private Integer activeType;

    /**
     * 活动面积
     */
    private Double area;

    /**
     * 说明
     */
    private String descri;

    /**
     * 创建人id
     */
    @TableField("create_by")
    private Integer createBy;

    /**
     * 创建时间
     */
    @TableField("create_date")
    private LocalDateTime createDate;

    /**
     * 修改人
     */
    @TableField("update_by")
    private Integer updateBy;

    @TableField("update_date")
    private LocalDateTime updateDate;

    /**
     * 备注
     */
    private String remark;

    /**
     * 逻辑删除 0删除，1未删除
     */
    @TableField("del_flag")
    private Integer delFlag;

    @TableField(exist = false)
    private Integer desc;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
