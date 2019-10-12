package com.gistone.bjhky.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
@ApiModel(value="St4PoSaCl对象", description="巡护任务表")
public class Task {

    @ApiModelProperty(value = "人员IDList")
    private List<Integer> uidList;


    @ApiModelProperty(value = "任务List")
    private List<Integer> taskList;

}
