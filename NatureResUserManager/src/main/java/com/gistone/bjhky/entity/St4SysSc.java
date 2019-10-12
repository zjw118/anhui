package com.gistone.bjhky.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
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
 * 模块权限表
 * </p>
 *
 * @author LiuXiong
 * @since 2019-08-22
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="St4SysSc对象", description="模块权限表")
public class St4SysSc extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "唯一主键")
    @TableId(value = "SC001", type = IdType.AUTO)
    private Integer sc001;

    @ApiModelProperty(value = "模块名称")
    @TableField("SC002")
    private String sc002;

    @ApiModelProperty(value = "父级id")
    @TableField("SC003")
    private Integer sc003;

    @ApiModelProperty(value = "页面标签id")
    @TableField("SC004")
    private String sc004;

    @ApiModelProperty(value = "添加用户")
    @TableField("SC005")
    private Integer sc005;
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "添加时间")
    @TableField("SC006")
    private LocalDateTime sc006;

    @ApiModelProperty(value = "更新人")
    @TableField("SC007")
    private Integer sc007;
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "更新时间")
    @TableField("SC008")
    private LocalDateTime sc008;

    @ApiModelProperty(value = "逻辑删除 ，0是删除，1是未删除")
    @TableField("SC009")
    private Integer sc009;

    @ApiModelProperty(value = "备注")
    @TableField("SC010")
    private String sc010;

    @ApiModelProperty(value = "图标")
    @TableField("SC011")
    private String sc011;

    @ApiModelProperty(value = "接口url")
    @TableField("SC012")
    private String sc012;

    @ApiModelProperty(value = "资源类型  1.菜单 2.按钮")
    @TableField("SC013")
    private Integer sc013;

    @ApiModelProperty(value = "级别")
    @TableField("SC014")
    private Integer sc014;

    @ApiModelProperty(value = "排序")
    @TableField("SC015")
    private Integer sc015;

    @ApiModelProperty(value = "资源标志")
    @TableField("SC016")
    private String sc016;

    @ApiModelProperty(value = "子菜单")
    @TableField(exist = false)
    private List<St4SysSc> children;

}
