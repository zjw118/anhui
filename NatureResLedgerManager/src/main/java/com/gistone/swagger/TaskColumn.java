package com.gistone.swagger;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
@ApiModel(value="任务选择核查字段接口专用", description="")
public class TaskColumn {


    @ApiModelProperty(value = "任务ID", required = true,example="1")
    public Integer taskId;

    @ApiModelProperty(value = "表单类型0核查 1巡护", required = true,example="1")
    public Integer cp006;

    @ApiModelProperty(value = "所选择字段的id及展示序号", required = true,example="1")
    public List<ColumnSort> columnSorts;


}
