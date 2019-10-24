package com.gistone.swagger;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;
@Data
@ApiModel(value="安徽下发任务接口及台账绑定任务专用", description="")
public class SharePoint {


    @ApiModelProperty(value = "任务ID（必传）", required = true,example="1")
    public Integer taskId;

    @ApiModelProperty(value = "人员ID（下发任务必传）", required = true,example="1")
    public List<Integer> uidList;

    @ApiModelProperty(value = "台账Id集合（任务绑定台账必传）", required = true,example="1")
    public List<Integer> ledgerIdList;

}
