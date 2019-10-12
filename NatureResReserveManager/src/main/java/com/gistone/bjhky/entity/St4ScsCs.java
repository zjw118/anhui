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
 *
 * </p>
 *
 * @author LiuXiong
 * @since 2019-08-15
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="St4ScsCs对象", description="")
public class St4ScsCs implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "唯一主键")
    @TableId(value = "CS001", type = IdType.AUTO)
    private Integer cs001;

    @ApiModelProperty(value = "所属保护地id")
    @TableField("SG001")
    private Integer sg001;

    @ApiModelProperty(value = "保护地边界数据版本号")
    @TableField("CS003")
    private Integer cs003;

    @ApiModelProperty(value = "保护地边界数据文件地址")
    @TableField("CS004")
    private String cs004;

    @ApiModelProperty(value = "保护地影像文件版本号")
    @TableField("CS005")
    private Integer cs005;

    @ApiModelProperty(value = "保护地影像文件地址")
    @TableField("CS006")
    private String cs006;

    @ApiModelProperty(value = "添加人id")
    @TableField("CS007")
    private Integer cs007;
    @JsonFormat(shape= JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "添加时间")
    @TableField("CS008")
    private LocalDateTime cs008;

    @ApiModelProperty(value = "删除状态0未删除1已删除")
    @TableField("CS009")
    private Integer cs009;

    @ApiModelProperty(value = "备注")
    @TableField("CS010")
    private String cs010;

    @ApiModelProperty(value = "边界数据文件名")
    @TableField("CS011")
    private String cs011;

    @ApiModelProperty(value = "影像数据文件名")
    @TableField("CS012")
    private String cs012;


}
