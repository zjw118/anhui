package com.gistone.SawggerModel;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @ClassName SysData
 * @Description 此类封装人员ID
 * @Author xxh
 * @Date 2019/8/14 16:19
 * @Version 1.0
 **/
@Data
@ApiModel(value="同步数据SysData对象", description="")
public class SysData {
    @ApiModelProperty(value = "巡护人员ID",example = "1")
    private  Integer uid;
}
