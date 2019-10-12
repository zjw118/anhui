package com.gistone.bjhky.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 巡查人员授权保护地数据关联表
 * </p>
 *
 * @author zjw
 * @since 2019-08-30
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="St4PoSaSg对象", description="巡查人员授权保护地数据关联表")
public class St4PoSaSg implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "用户唯一主键")
    @TableField("SA001")
    private Integer sa001;

    @ApiModelProperty(value = "保护地唯一主键")
    @TableField("SG001")
    private Integer sg001;


}
