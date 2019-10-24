package com.gistone.entity;

import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 图斑台账关联表
 * </p>
 *
 * @author zjw
 * @since 2019-10-24
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="St4PoCdCo对象", description="图斑台账关联表")
public class St4PoCdCo implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "图斑表的主键")
    private Integer cd001;

    @ApiModelProperty(value = "台账表的主键")
    private Integer co001;


}
