package com.gistone.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 *
 * </p>
 *
 * @author LiuXiong
 * @since 2019-08-22
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="St4SysSb角色对象", description="")
public class St4SysSb extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "角色表的id")
    @TableId(value = "SB001", type = IdType.AUTO)
    private Integer sb001;

    @ApiModelProperty(value = "角色名称")
    @TableField("SB002")
    private String sb002;

    @ApiModelProperty(value = "角色的删除状态 0删除 1未删除")
    @TableField("SB003")
    private Integer sb003;
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "角色的添加时间")
    @TableField("SB004")
    private LocalDateTime sb004;

    @ApiModelProperty(value = "添加人的ID")
    @TableField("SB005")
    private Integer sb005;

    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "角色的更新时间")
    @TableField("SB006")
    private LocalDateTime sb006;

    @ApiModelProperty(value = "更新人的ID")
    @TableField("SB007")
    private Integer sb007;

    @ApiModelProperty(value = "备注")
    @TableField("SB008")
    private String sb008;

    @ApiModelProperty(value = "权限ID集合（添加修改必传）",example = "[1,2,3,5]")
    @TableField(exist = false)
    private List<Integer> priviledgeIds;

    @ApiModelProperty(value = "权限实体）")
    @TableField(exist = false)
    private St4SysSc st4SysSc;


    @ApiModelProperty(value = "权限集合 角色详情接口用")
    @TableField(exist = false)
    private List<St4SysSc> st4SysScList;



}
