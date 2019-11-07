package com.gistone.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * app问题反馈表主键
 * </p>
 *
 * @author zjw
 * @since 2019-11-07
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="St4ScsCar对象", description="app问题反馈表主键")
public class St4ScsCar implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "app问题反馈表主键")
    @TableId(value = "CAR001", type = IdType.AUTO)
    private Integer car001;

    @ApiModelProperty(value = "反馈内容")
    @TableField("CAR002")
    private String car002;

    @ApiModelProperty(value = "反馈人ID")
    @TableField("CAR003")
    private Integer car003;

    @ApiModelProperty(value = "反馈时间")
    @TableField("CAR004")
    private LocalDateTime car004;

    @ApiModelProperty(value = "反馈类型")
    @TableField("CAR005")
    private String car005;

    @ApiModelProperty(value = "反馈表备用字段")
    @TableField("CAR006")
    private Integer car006;


}
