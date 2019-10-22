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
 * 巡护人员组别关联表
 * </p>
 *
 * @author zjw
 * @since 2019-08-22
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="St4PoSaCz对象", description="巡护人员组别关联表")
public class St4PoSaCz implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "巡护人员ID")
    @TableField("SA001")
    private Integer sa001;

    @ApiModelProperty(value = "巡护小组ID")
    @TableField("CZ001")
    private Integer cz001;

}
