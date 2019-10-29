package com.gistone.swagger;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value="任务选择核查字段接口专用", description="")
public class ColumnSort {
    @ApiModelProperty(value = "所选择字段的id", required = true,example="1")
    private Integer columnId;
    @ApiModelProperty(value = "所选择字段的展示序号", required = true,example="1")
    private Integer sortNumber;
}