package com.gistone.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author xxh
 * @since 2019-08-14
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="St4ScsCp对象", description="")
public class St4ScsCp implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "唯一主键")
    @TableId(value = "CP001", type = IdType.AUTO)
    private Integer cp001;

    @ApiModelProperty(value = "表单的字段(json)")
    @TableField("CP002")
    private String cp002;

    @ApiModelProperty(value = "巡护或核查任务ID")
    @TableField("CP003")
    private Integer cp003;

    @ApiModelProperty(value = "备注")
    @TableField("CP004")
    private String cp004;

    @ApiModelProperty(value = "是否是必填项 1是 0 否 默认否")
    @TableField("CP005")
    private Integer cp005;

    @ApiModelProperty(value = "表单类型 0台账表  1 巡护表")
    @TableField("CP006")
    private Integer cp006;


}
