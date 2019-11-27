package com.gistone.swagger;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value="统计斑块质量评估专用", description="")
public class ActivityStatic {
    @ApiModelProperty(value = "原来的活动设施名称", required = true,example="1")
    private String orgin;
    @ApiModelProperty(value = "原来的活动设施数目", required = true,example="1")
    private Integer orginCount;
    @ApiModelProperty(value = "现在的活动设施名称", required = true,example="1")
    private String nows;
    @ApiModelProperty(value = "现在的活动设施数目", required = true,example="1")
    private Integer nowsCount;
}
