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
 * 保护地级别表
 * </p>
 *
 * @author zjw
 * @since 2019-11-04
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="St4SysSn对象", description="保护地级别表")
public class St4SysSn implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "唯一主键")
    @TableId(value = "SN001", type = IdType.AUTO)
    private Integer sn001;

    @ApiModelProperty(value = "保护地级别名称")
    @TableField("SN002")
    private String sn002;


}
