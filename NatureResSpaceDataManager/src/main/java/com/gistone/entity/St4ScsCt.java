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

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 
 * </p>
 *
 * @author xxh
 * @since 2019-08-12
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="St4ScsCt对象", description="")
public class St4ScsCt extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "空间数据唯一主键")
    @TableId(value = "CT001", type = IdType.AUTO)
    private Integer ct001;

    @ApiModelProperty(value = "父级id")
    @TableField("CT002")
    private Integer ct002;

    @ApiModelProperty(value = "数据名称")
    @TableField("CT003")
    private String ct003;

    @ApiModelProperty(value = "数据url")
    @TableField("CT004")
    private String ct004;

    @ApiModelProperty(value = "图例标签")
    @TableField("CT005")
    private String ct005;

    @ApiModelProperty(value = "数据url类型，0url，1文件")
    @TableField("CT006")
    private Integer ct006;

    @ApiModelProperty(value = "数据类型，0数据分类，1数据")
    @TableField("CT007")
    private Integer ct007;

    @ApiModelProperty(value = "数据简介")
    @TableField("CT008")
    private String ct008;

    @ApiModelProperty(value = "元数据描述")
    @TableField("CT009")
    private String ct009;

    @ApiModelProperty(value = "元数据摘要")
    @TableField("CT010")
    private String ct010;

    @ApiModelProperty(value = "元数据标签")
    @TableField("CT011")
    private String ct011;

    @ApiModelProperty(value = "元数据访问和使用限制")
    @TableField("CT012")
    private Integer ct012;

    @ApiModelProperty(value = "元数据联系人")
    @TableField("CT013")
    private String ct013;

    @ApiModelProperty(value = "联系人电话")
    @TableField("CT014")
    private String ct014;

    @ApiModelProperty(value = "联系人邮箱")
    @TableField("CT015")
    private String ct015;

    @ApiModelProperty(value = "添加人")
    @TableField("CT016")
    private Integer ct016;

    @ApiModelProperty(value = "添加时间")
    @TableField("CT017")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date ct017;

    @ApiModelProperty(value = "更新人")
    @TableField("CT018")
    private Integer ct018;

    @ApiModelProperty(value = "更新时间")
    @TableField("CT019")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date ct019;

    @ApiModelProperty(value = "逻辑删除 0是删除，1是未删除")
    @TableField("CT020")
    private Integer ct020;

    @ApiModelProperty(value = "所属保护地Id")
    @TableField("SG001")
    private Integer sg001;

    @ApiModelProperty(value = "所属行政区划id")
    @TableField("SD001")
    private Integer sd001;


    @TableField(exist = false)
    @ApiModelProperty(hidden = true)
    private List<St4ScsCt> children;


}
