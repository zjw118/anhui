package com.gistone.entity;

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
    private Integer rsdId;

    @ApiModelProperty(value = "标识码")
    private String rsdBsm;

    @ApiModelProperty(value = "要素代码")
    private String rsdYsdm;

    @ApiModelProperty(value = "数据名称")
    private String rsdSjmc;

    @ApiModelProperty(value = "卫星")
    private String rsdWx;

    @ApiModelProperty(value = "传感器")
    private String rsdCgq;

    @ApiModelProperty(value = "成像时间")
    private LocalDate rsdCxsj;

    @ApiModelProperty(value = "景序列号")
    private String rsdJxlh;

    @ApiModelProperty(value = "产品级别")
    private String rsdCpjb;

    @ApiModelProperty(value = "数据格式")
    private String rsdSjgs;

    @ApiModelProperty(value = "波段数")
    private String rsdBds;

    @ApiModelProperty(value = "空间分辨率")
    private String rsdKjfbl;

    @ApiModelProperty(value = "坐标系")
    private String rsdZbx;

    @ApiModelProperty(value = "左下角X坐标")
    private String rsdZxjxzb;

    @ApiModelProperty(value = "左下角Y坐标")
    private String rsdZxjyzb;

    @ApiModelProperty(value = "右下角X坐标")
    private String rsdYxjxzb;

    @ApiModelProperty(value = "右下角Y坐标")
    private String rsdYxjyzb;

    @ApiModelProperty(value = "生产日期")
    private LocalDate rsdScrq;

    @ApiModelProperty(value = "备注")
    private String rsdRemark;

    @ApiModelProperty(value = "添加人")
    private Integer rsdAddUid;

    @ApiModelProperty(value = "添加时间")
    private LocalDateTime rsdAddTime;

    @ApiModelProperty(value = "删除状态0删除1整除")
    private Integer rsdDelFlag;


}
