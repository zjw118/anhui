package com.gistone.bjhky.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 核查点分配记录表
 * </p>
 *
 * @author zjw
 * @since 2019-08-15
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="St4PoCdSa对象，只传递巡查人员ID即可", description="核查点分配记录表")
public class St4PoCdSa implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "问题点序号(台账表中的)")
    @TableField("CD001")
    private Integer cd001;

    @ApiModelProperty(value = "巡护人员ID")
    @TableField("SA001")
    private Integer sa001;

    @ApiModelProperty(value = "是否提交1是 0否")
    @TableField("CDSA001")
    private Integer cdsa001;

    @ApiModelProperty(value = "名字")
    @TableField(exist = false)
    private String sa019;

    @ApiModelProperty(name="checkLedger",value="台账信息",dataType = "com.gistone.bjhky.entity.St4ScsCk" )
    @TableField(exist = false)
    private St4ScsCk checkLedger;









}
