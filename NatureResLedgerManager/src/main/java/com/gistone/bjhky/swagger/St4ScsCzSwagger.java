package com.gistone.bjhky.swagger;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;
@ApiModel(value="所有接口的外层data对象", description="")
public class St4ScsCzSwagger {
    @ApiModelProperty(value = "分组ID（修改删除必传）", required = true,example="1")
    public Integer groupId;
    @ApiModelProperty(value = "巡护人员ID集合（新增必传，修改可传，删除不传）", required = true,example="[1,2,3,5]")
    public List<Integer> uidList;
    @ApiModelProperty(value = "巡护小组组长ID（新增必传，修改可传，删除不传）", required = true,example="4")
    public Integer captainId;
    @ApiModelProperty(value = "巡护小组名称（新增必传，修改可传，删除不传）", required = true,example="天山核查小组")
    public String groupName;
    @ApiModelProperty(value = "巡查任务ID（新增必传，修改可传，删除不传）", required = true,example="1")
    public Integer taskId;

}
