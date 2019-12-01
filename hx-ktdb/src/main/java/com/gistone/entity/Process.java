package com.gistone.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * <p>
 *
 * </p>
 *
 * @author zjw
 * @since 2019-11-27
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="Process对象", description="")
public class Process implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "唯一主键")
    @TableId(value = "p_id", type = IdType.AUTO)
    private Integer pId;

    @ApiModelProperty(value = "流程名称")
    private String pName;

    @ApiModelProperty(value = "流程定义单位")
    private String pUnit;

    @ApiModelProperty(value = "流程定义者")
    private String pPersion;

    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "流程添加时间")
    private LocalDateTime pAddTime;

    @ApiModelProperty(value = "流程简介")
    private String pIntroduce;

    @ApiModelProperty(value = "流程内容")
    private String pContent;

    @ApiModelProperty(value = "添加人id")
    private Integer pAddUid;

    @ApiModelProperty(value = "逻辑删除，0删除，1未删除")
    private Integer pDelFlag;


}
