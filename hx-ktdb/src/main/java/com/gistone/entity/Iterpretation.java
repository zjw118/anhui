package com.gistone.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

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
    private String desc;

    /**
     * 创建人id
     */
    @TableField("createBy")
    private Integer createBy;

    /**
     * 创建时间
     */
    @TableField("createDate")
    private LocalDateTime createDate;

    /**
     * 修改人
     */
    @TableField("updateBy")
    private Integer updateBy;

    @TableField("updateDate")
    private LocalDateTime updateDate;

    /**
     * 备注
     */
    private String remark;

    /**
     * 逻辑删除 0删除，1未删除
     */
    private Integer delFlag;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
