package com.gistone.swagger;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;
@Data
@ApiModel(value="红线下发问题斑块专用", description="")
public class SharePoint {

    @ApiModelProperty(value = "问题斑块ID（必传）", required = true,example="[7,8,9,10]")
    public List<Integer> pointIdList;

    @ApiModelProperty(value = "人员ID（必传）", required = true,example="1")
    public List<Integer> uidList;


    @ApiModelProperty(value = "撤销下发斑块时传递的人员ID", required = true,example="1")
    public Integer uid;

//    @ApiModelProperty(value = "任务ID（必传）", required = true,example="1")
//    public Integer taskId;
//
//    @ApiModelProperty(value = "人员ID（下发任务必传）", required = true,example="1")
//    public List<Integer> uidList;
//
//    @ApiModelProperty(value = "台账Id集合（任务绑定台账必传）", required = true,example="1")
//    public List<Integer> ledgerIdList;

}
