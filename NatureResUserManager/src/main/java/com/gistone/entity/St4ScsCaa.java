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
 * @author LiuXiong
 * @since 2019-08-27
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="St4ScsCaa对象", description="")
public class St4ScsCaa implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "唯一主键")
    @TableId(value = "CAA001", type = IdType.AUTO)
    private Integer caa001;

    @ApiModelProperty(value = "app下载路径")
    @TableField("CAA002")
    private String caa002;

    @ApiModelProperty(value = "app更新版本号")
    @TableField("CAA003")
    private Integer caa003;

    @ApiModelProperty(value = "app版本号")
    @TableField("CAA004")
    private String caa004;

    @ApiModelProperty(value = "版本更新说明")
    @TableField("CAA005")
    private String caa005;

    @ApiModelProperty(value = "添加人id")
    @TableField("CAA006")
    private Integer caa006;
    @JsonFormat(shape= JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "添加时间")
    @TableField("CAA007")
    private LocalDateTime caa007;

    @ApiModelProperty(value = "备注")
    @TableField("CAA008")
    private String caa008;

    @ApiModelProperty(value = "逻辑删除  0删除1未删除")
    @TableField("CAA009")
    private Integer caa009;


}
