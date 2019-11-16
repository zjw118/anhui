package com.gistone.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import java.time.LocalDate;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 *
 * </p>
 *
 * @author zjw
 * @since 2019-11-16
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="St4ScsCbb对象", description="")
public class St4ScsCbb extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "序号")
    @TableId(value = "ID", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "标识码(具有一定规律的编号（代码）)")
    @TableField("BMS")
    private String bms;

    @ApiModelProperty(value = "省")
    @TableField("Province")
    private String Province;

    @ApiModelProperty(value = "市")
    @TableField("City")
    private String City;

    @ApiModelProperty(value = "县（区）")
    @TableField("DCounty")
    private String DCounty;

    @ApiModelProperty(value = "行政区划代码")
    @TableField("XZQHDM")
    private String xzqhdm;

    @ApiModelProperty(value = "名称")
    @TableField("MC")
    private String mc;

    @ApiModelProperty(value = "级别")
    @TableField("JB")
    private String jb;

    @ApiModelProperty(value = "类型")
    @TableField("LX")
    private String lx;

    @ApiModelProperty(value = "分区")
    @TableField("FQ")
    private String fq;

    @ApiModelProperty(value = "图斑面积统计（单位km²）")
    @TableField("TBMJ")
    private Float tbmj;

    @ApiModelProperty(value = "批准时间")
    @TableField("APTime")
    private LocalDate APTime;

    @ApiModelProperty(value = "调整时间")
    @TableField("ADtime")
    private LocalDate ADtime;

    @ApiModelProperty(value = "备注")
    @TableField("BZ")
    private String bz;

    @ApiModelProperty(value = "所属园区")
    @TableField("SSYQ")
    private String ssyq;

    @ApiModelProperty(value = "1未删除 0已删除")
    @TableField("DEL")
    private Integer del;


}
