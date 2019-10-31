package com.gistone.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

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
 * @since 2019-08-28
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="St4SysSj单位对象", description="")
public class St4SysSj extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "唯一主键")
    @TableId(value = "SJ001", type = IdType.AUTO)
    private Integer sj001;

    @ApiModelProperty(value = "单位名称")
    @TableField("SJ002")
    private String sj002;

    @ApiModelProperty(value = "删除状态默认0 已删除 1 未删除")
    @TableField("SJ003")
    private Integer sj003;
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "添加时间")
    @TableField("SJ004")
    private LocalDateTime sj004;

    @ApiModelProperty(value = "添加用户id")
    @TableField("SJ005")
    private Integer sj005;

    @ApiModelProperty(value = "备注")
    @TableField("SJ006")
    private String sj006;

    @ApiModelProperty(value = "是否是二级单位创建 默认0不是 1是")
    @TableField("SJ007")
    private Integer sj007;

    @ApiModelProperty(value = "父单位的ID")
    @TableField("SJ008")
    private Integer sj008;

    @ApiModelProperty(value = "管辖的保护区id的集合,添加修改接口必传" ,example="[2,3,6]")
    @TableField(exist = false)
    private List<Integer> bidList;

    @ApiModelProperty(value = "单位详情接口返回" ,example="[2,3,6]")
    @TableField(exist = false)
    private List<Map<Object,Object>> sgList;



}
