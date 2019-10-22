package com.gistone.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 巡护任务表
 * </p>
 *
 * @author zjw
 * @since 2019-08-15
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="St4PoSaCl对象", description="巡护任务表")
public class St4PoSaCl implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "任务ID")
    @TableField("CL001")
    private Integer cl001;

    @ApiModelProperty(value = "人员ID")
    @TableField("SA001")
    private Integer sa001;


}
