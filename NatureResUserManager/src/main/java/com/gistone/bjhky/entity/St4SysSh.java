package com.gistone.bjhky.entity;

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

/**
 * <p>
 * 系统日志表
 * </p>
 *
 * @author xxh
 * @since 2019-08-12
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="St4SysSh对象", description="系统日志表")
public class St4SysSh extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "唯一主键")
    @TableId(value = "SH001", type = IdType.AUTO)
    private Integer sh001;

    @ApiModelProperty(value = "创建人ID")
    @TableField("SH002")
    private Integer sh002;
    @JsonFormat(shape= JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "创建时间")
    @TableField("SH003")
    private LocalDateTime sh003;

    @ApiModelProperty(value = "更新人ID")
    @TableField("SH004")
    private Integer sh004;
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "更新时间")
    @TableField("SH005")
    private LocalDateTime sh005;

    @ApiModelProperty(value = "备注")
    @TableField("SH006")
    private String sh006;

    @ApiModelProperty(value = "逻辑删除 0是删除，1是未删除")
    @TableField("SH007")
    private Integer sh007;
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "请求时间")
    @TableField("SH008")
    private LocalDateTime sh008;

    @ApiModelProperty(value = "主机名")
    @TableField("SH009")
    private String sh009;

    @ApiModelProperty(value = "日志级别")
    @TableField("SH010")
    private String sh010;

    @ApiModelProperty(value = "类名")
    @TableField("SH011")
    private String sh011;

    @ApiModelProperty(value = "线程名")
    @TableField("SH012")
    private String sh012;

    @ApiModelProperty(value = "方法名")
    @TableField("SH013")
    private String sh013;

    @ApiModelProperty(value = "日志内容")
    @TableField("SH014")
    private String sh014;


}
