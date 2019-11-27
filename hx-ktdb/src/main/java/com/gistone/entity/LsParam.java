package com.gistone.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 *
 * </p>
 *
 * @author zf1017@foxmail.com
 * @since 2019-11-26
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class LsParam extends Model<LsParam> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    @NotEmpty(message = "参数名称不能为空")
    private String name;
    @NotEmpty(message = "参数类型不能为空")
    private String type;

    private Integer delFlag;

    private LocalDateTime createTime;
    private String remark;
    private String url;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
