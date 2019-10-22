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

/**
 * <p>
 * 
 * </p>
 *
 * @author xxh
 * @since 2019-08-14
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="St4SysSd对象", description="")
public class St4SysSd implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "行政区表主键")
    @TableId(value = "SD001", type = IdType.AUTO)
    private Integer sd001;

    @ApiModelProperty(value = "创建人ID")
    @TableField("SD002")
    private Integer sd002;
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "创建时间")
    @TableField("SD003")
    private LocalDateTime sd003;

    @ApiModelProperty(value = "更新人ID")
    @TableField("SD004")
    private Integer sd004;
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "更新时间")
    @TableField("SD005")
    private LocalDateTime sd005;

    @ApiModelProperty(value = "备注")
    @TableField("SD006")
    private String sd006;

    @ApiModelProperty(value = "逻辑删除 0是删除，1是未删除")
    @TableField("SD007")
    private Integer sd007;

    @ApiModelProperty(value = "行政区划名称")
    @TableField("SD008")
    private String sd008;

    @ApiModelProperty(value = "行政区划代码")
    @TableField("SD009")
    private String sd009;

    @ApiModelProperty(value = "行政区划级别")
    @TableField("SD010")
    private Integer sd010;

    @ApiModelProperty(value = "上级行政区划代码")
    @TableField("SD011")
    private String sd011;

    @ApiModelProperty(value = "上级行政区划id")
    @TableField("SD012")
    private Integer sd012;

}
