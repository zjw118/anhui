package com.gistone.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 *
 * </p>
 *
 * @author zf1017@foxmail.com
 * @since 2019-11-27
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class LsDataStrategy extends Model<LsDataStrategy> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    @NotEmpty(message = "周期不能为空")
    private String cycle;
    @NotEmpty(message = "类型不能为空")
    private String type;

    private Date time;
    @TableField(exist = false)
    @NotNull(message = "时间不能为空")
    private String timeStr;

    private Integer delFlag;

    private Date createTime;

    private String remark;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
