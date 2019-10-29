package com.gistone.swagger;

import com.gistone.entity.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value="台账统计专用", description="")
public class StaticSwagger extends BaseEntity {
    @ApiModelProperty(value = "任务ID", required = true,example="2")
    public Integer cl001;

    @ApiModelProperty(value = "行政区ID", required = true,example="1")
    public Integer sd001;

    @ApiModelProperty(value = "保护区ID", required = true,example="1")
    public Integer sg001;

    @ApiModelProperty(value = "用户ID", required = true,example="1")
    public Integer sa001;
}

