package com.gistone.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * <p>
 *
 * </p>
 *
 * @author lx
 * @since 2019-12-04
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="RemoteSensingData对象", description="")
public class RemoteSensingData extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "遥感影像数据台账id")
    @TableId(value = "rsd_id", type = IdType.AUTO)
    @Excel(name = "遥感影像数据台账id",height = 11, width = 15)
    private Integer rsdId;

    @ApiModelProperty(value = "标识码")
    @Excel(name = "标识码",height = 11, width = 15)
    private String rsdBsm;

    @ApiModelProperty(value = "要素代码")
    @Excel(name = "要素代码",height = 11, width = 15)
    private String rsdYsdm;

    @ApiModelProperty(value = "数据名称")
    @Excel(name = "数据名称",height = 11, width = 15)
    private String rsdSjmc;

    @ApiModelProperty(value = "卫星")
    @Excel(name = "卫星",height = 11, width = 15)
    private String rsdWx;

    @ApiModelProperty(value = "传感器")
    @Excel(name = "传感器",height = 11, width = 15)
    private String rsdCgq;

    @ApiModelProperty(value = "成像时间")
    @Excel(name = "成像时间",height = 11, width = 15)
    private LocalDate rsdCxsj;

    @ApiModelProperty(value = "景序列号")
    @Excel(name = "景序列号",height = 11, width = 15)
    private String rsdJxlh;

    @ApiModelProperty(value = "产品级别")
    @Excel(name = "产品级别",height = 11, width = 15)
    private String rsdCpjb;

    @ApiModelProperty(value = "数据格式")
    @Excel(name = "数据格式",height = 11, width = 15)
    private String rsdSjgs;

    @ApiModelProperty(value = "波段数")
    @Excel(name = "波段数",height = 11, width = 15)
    private String rsdBds;

    @ApiModelProperty(value = "空间分辨率")
    @Excel(name = "空间分辨率",height = 11, width = 15)
    private String rsdKjfbl;

    @ApiModelProperty(value = "坐标系")
    @Excel(name = "坐标系",height = 11, width = 15)
    private String rsdZbx;

    @ApiModelProperty(value = "左下角X坐标")
    @Excel(name = "左下角X坐标",height = 11, width = 15)
    private String rsdZxjxzb;

    @ApiModelProperty(value = "左下角Y坐标")
    @Excel(name = "左下角Y坐标",height = 11, width = 15)
    private String rsdZxjyzb;

    @ApiModelProperty(value = "右下角X坐标")
    @Excel(name = "右下角X坐标",height = 11, width = 15)
    private String rsdYxjxzb;

    @ApiModelProperty(value = "右下角Y坐标")
    @Excel(name = "右下角X坐标",height = 11, width = 15)
    private String rsdYxjyzb;

    @ApiModelProperty(value = "生产日期")
    @Excel(name = "生产日期",height = 11, width = 15)
    private LocalDate rsdScrq;

    @ApiModelProperty(value = "备注")
    @Excel(name = "备注",height = 11, width = 15)
    private String rsdRemark;

    @ApiModelProperty(value = "添加人")
    @Excel(name = "添加人",height = 11, width = 15)
    private Integer rsdAddUid;

    @ApiModelProperty(value = "添加时间")
    @Excel(name = "添加人",height = 11, width = 15)
    private LocalDateTime rsdAddTime;

    @ApiModelProperty(value = "删除状态0删除1整除")
    private Integer rsdDelFlag;


}
