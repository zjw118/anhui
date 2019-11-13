package com.gistone.swagger;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value="轨迹分布地图接口", description="")
public class TrackDistribution {
    @ApiModelProperty(value = "任务名称", required = true,example="1")
    public String taskName;

    @ApiModelProperty(value = "台账ID", required = true,example="1")
    public Integer ledgerId;
}
