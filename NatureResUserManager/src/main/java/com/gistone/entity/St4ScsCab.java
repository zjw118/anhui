package com.gistone.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableField;
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
 * @since 2019-09-02
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="St4ScsCab对象", description="")
public class St4ScsCab extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "党建信息唯一主键")
    @TableId(value = "CAB001", type = IdType.AUTO)
    private Integer cab001;

    @ApiModelProperty(value = "标题")
    @TableField("CAB002")
    private String cab002;

    @ApiModelProperty(value = "发布时间")
    @TableField("CAB003")
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime cab003;

    @ApiModelProperty(value = "信息来源")
    @TableField("CAB004")
    private String cab004;

    @ApiModelProperty(value = "封面地址")
    @TableField("CAB005")
    private String cab005;

    @ApiModelProperty(value = "发布人")
    @TableField("CAB006")
    private String cab006;

    @ApiModelProperty(value = "是否置顶  0不置顶  1置顶")
    @TableField("CAB007")
    private Integer cab007;

    @ApiModelProperty(value = "内容")
    @TableField("CAB008")
    private String cab008;

    @ApiModelProperty(value = "阅读量")
    @TableField("CAB009")
    private Integer cab009;

    @ApiModelProperty(value = "所属行政区")
    @TableField("CAB010")
    private Integer cab010;

    @ApiModelProperty(value = "所属保护地")
    @TableField("CAB011")
    private Integer cab011;

    @ApiModelProperty(value = "添加人")
    @TableField("CAB012")
    private Integer cab012;

    @ApiModelProperty(value = "添加时间")
    @TableField("CAB013")
    private LocalDateTime cab013;

    @ApiModelProperty(value = "逻辑删除  0删除 1正常")
    @TableField("CAB014")
    private Integer cab014;

    @ApiModelProperty(value = "数据类型  0党建信息 1手册")
    @TableField("CAB015")
    private Integer cab015;


}
