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
import java.sql.Blob;
import java.time.LocalDateTime;

/**
 * <p>
 * 巡护路段表
 * </p>
 *
 * @author xxh
 * @since 2019-08-16
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="St4ScsCg对象", description="巡护路段表")
public class St4ScsCg implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "巡护路段表主键")
    @TableId(value = "CG001", type = IdType.AUTO)
    private Integer cg001;

    @ApiModelProperty(value = "巡护记录唯一标识")
    @TableField("CY017")
    private String cy017;

    @ApiModelProperty(value = "航迹数据")
    @TableField("CG003")
    private String cg003;

    @ApiModelProperty(value = "开始时间")
    @TableField("CG004")
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime cg004;

    @ApiModelProperty(value = "结束时间")
    @TableField("CG005")
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime cg005;

    @ApiModelProperty(value = "类型")
    @TableField("CG006")
    private Integer cg006;


}
