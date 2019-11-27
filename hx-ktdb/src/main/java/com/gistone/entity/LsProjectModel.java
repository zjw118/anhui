package com.gistone.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

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
public class LsProjectModel extends Model<LsProjectModel> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 模板名称
     */
    @NotEmpty(message = "模板名称不能为空")
    private String name;

    /**
     * 模板地址
     */
//    @NotEmpty(message = "模板地址不能为空")
    private String url;

   @NotNull(message = "类型不能为空")
    private Integer type;
    /**
     * 0不是默认，1是默认
     */

    private Integer flag;

    private Integer delFlag;

    private String remark;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
