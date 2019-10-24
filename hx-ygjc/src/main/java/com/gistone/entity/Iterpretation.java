package com.gistone.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import java.io.Serializable;
import java.util.Date;

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
    private Integer imageId;
    private String activeName;
    private String geometry;
    private String activeType;
    private Double area;
    private String descri;
    @TableField("create_by")
    private Integer createBy;
    @TableField("create_date")
    private Date createDate;
    @TableField("update_by")
    private Integer updateBy;
    @TableField("update_date")
    private Date updateDate;
    @TableField("remark")
    private String remark;
    @TableField("del_flag")
    private Integer delFlag;

    @TableField(exist = false)
    private Integer desc;
    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
