package com.gistone.bjhky.swagger;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
@ApiModel(value="授权保护地专用", description="")
public class GiveReserve {
    @ApiModelProperty(value = "保护地ID（非必传）", required = true,example="[7,8,9,10]")
    public List<Integer> reserveIdList;

    @ApiModelProperty(value = "人员ID（必传），", required = true,example="1")
    public Integer uid;

}
