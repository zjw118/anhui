package com.gistone.bjhky.swagger;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;
@Data
@ApiModel(value="下发问题点专用", description="")
public class SharePoint {
    @ApiModelProperty(value = "问题点ID（必传）", required = true,example="[7,8,9,10]")
    public List<Integer> pointIdList;

    @ApiModelProperty(value = "人员ID（必传）", required = true,example="1")
    public List<Integer> uidList;

}
