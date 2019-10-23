package com.gistone.swagger;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;
@Data
@ApiModel(value="安徽下发任务专用", description="")
public class SharePoint {


    @ApiModelProperty(value = "任务ID（必传）", required = true,example="1")
    public Integer taskId;

    @ApiModelProperty(value = "人员ID（必传）", required = true,example="1")
    public List<Integer> uidList;

}
