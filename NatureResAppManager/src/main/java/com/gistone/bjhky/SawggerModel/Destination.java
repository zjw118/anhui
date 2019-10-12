package com.gistone.bjhky.SawggerModel;

import com.gistone.bjhky.entity.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @ClassName Destination
 * @Description 此类封装巡护APP上传数据
 * @Author xxh
 * @Date 2019/8/14 16:19
 * @Version 1.0
 **/
@Data
@ApiModel(value="Destination对象", description="航点信息")
public class Destination {


    @ApiModelProperty(value = "巡护或者核查任务的ID")
    private String taskId;


    //巡护基础表
    @ApiModelProperty(value = "航点巡护基础信息")
    private St4ScsCc st4ScsCc;

    //巡护信息表
    @ApiModelProperty(value = "航点巡护信息")
    private St4ScsCf st4ScsCf;

    //巡护信息附件表
    @ApiModelProperty(value = "航点附件信息")
    private List<St4ScsCe> st4ScsCe;

    //巡护台账表
    @ApiModelProperty(value = "台账信息")
    private St4ScsCk st4ScsCk;



}
