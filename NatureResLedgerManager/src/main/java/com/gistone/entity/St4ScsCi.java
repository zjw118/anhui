package com.gistone.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import jdk.nashorn.internal.ir.annotations.Ignore;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * app预警表
 * </p>
 *
 * @author zjw
 * @since 2019-08-17
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="预警实体类对象其中 ci001是主键app提交不用填，预警类型和预警名称是必填，剩下的是非必填", description="app预警表")
public class St4ScsCi extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "预警唯一主键")
    @TableId(value = "CI001", type = IdType.AUTO)
    private Integer ci001;

    @ApiModelProperty(value = "预警类型")
    @TableField("CI002")
    private Integer ci002;

    @ApiModelProperty(value = "备注")
    @TableField("CI003")
    private String ci003;

    @ApiModelProperty(value = "预警时间",example = "2019-08-15 19:20:20")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss" ,  timezone="GMT+8")
    @TableField("CI004")
    private Date ci004;

    @ApiModelProperty(value = "预警人")
    @TableField("CI005")
    private Integer ci005;

    @ApiModelProperty(value = "1未删除 0已删除")
    @TableField("CI006")
    private Integer ci006;

    @ApiModelProperty(value = "经度")
    @TableField("CI007")
    private String ci007;

    @ApiModelProperty(value = "纬度")
    @TableField("CI008")
    private String ci008;

    @ApiModelProperty(value = "包裹预警人信息")
    @TableField(exist = false)
    private St4SysSa st4SysSa;
@Ignore
    @ApiModelProperty(value = "权限用，单位id")
    @TableField(exist = false)
    private Integer unitId;


}
