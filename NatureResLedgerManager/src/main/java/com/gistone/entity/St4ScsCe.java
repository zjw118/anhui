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
 * 航点附件表
 * </p>
 *
 * @author xxh
 * @since 2019-08-14
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="St4ScsCe对象", description="航点附件表")
public class St4ScsCe implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "航点附件表主键")
    @TableId(value = "CE001", type = IdType.AUTO)
    private Integer ce001;

    @ApiModelProperty(value = "航点唯一标识")
    @TableField("CE002")
    private String ce002;

    @ApiModelProperty(value = "文件URL")
    @TableField("CE003")
    private String ce003;

    @ApiModelProperty(value = "文件类型")
    @TableField("CE004")
    private Integer ce004;

    @ApiModelProperty(value = "添加时间")
    @TableField("CE005")
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime ce005;

    @ApiModelProperty(value = "添加人")
    @TableField("CE006")
    private Integer ce006;


}
