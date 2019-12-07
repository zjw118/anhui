package com.gistone.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.excel.annotation.ExcelIgnore;
import com.baomidou.mybatisplus.annotation.IdType;
import java.time.LocalDate;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import java.time.LocalDateTime;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 生态保护红线陆地边界数据表
 * </p>
 *
 * @author zjw
 * @since 2019-11-16
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="St4ScsCba对象", description="生态保护红线陆地边界数据表")
public class St4ScsCba extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @ExcelIgnore
    @ApiModelProperty(value = "序号")
    @TableId(value = "ID", type = IdType.AUTO)
    private Integer id;
    @ExcelIgnore
    @ApiModelProperty(value = "提交数据时间")
    @TableField("TJSJ")
    private LocalDate tjsj;
    @Excel(name = "编号",height = 11, width = 15)
    @ApiModelProperty(value = "具有一定规律的编号（代码）")
    @TableField("BMS")
    private String bms;
    @Excel(name = "省行政区",height = 11, width = 15)
    @ApiModelProperty(value = "省行政区")
    @TableField("PROVINCE")
    private String province;
    @Excel(name = "市行政区",height = 11, width = 15)
    @ApiModelProperty(value = "市行政区")
    @TableField("CITY")
    private String city;
    @Excel(name = "行政区",height = 11, width = 15)
    @ApiModelProperty(value = "行政区：District (county)")
    @TableField("DCOUNTY")
    private String dcounty;
    @Excel(name = "行政区代码",height = 11, width = 15)
    @ApiModelProperty(value = "行政区代码")
    @TableField("XZQHDM")
    private String xzqhdm;

    @Excel(name = "要素代码",height = 11, width = 15)
    @ApiModelProperty(value = "要素代码")
    @TableField("YSDM")
    private String ysdm;

    @Excel(name = "主导生态系统服务功能",height = 11, width = 15)
    @ApiModelProperty(value = "主导生态系统服务功能")
    @TableField("ZDSTXTFWGN")
    private String zdstxtfwgn;

    @Excel(name = "红线类型",height = 11, width = 15)
    @ApiModelProperty(value = "红线类型")
    @TableField("HXLX")
    private String hxlx;

    @Excel(name = "类型编码",height = 11, width = 15)
    @ApiModelProperty(value = "类型编码(类型代码由2位数字组成，第1位表示类型特征，其中，1表示生态功能，2表示生态环境敏感性。后1位表示属性分类，其中，生态功能包括：1-水源涵养，2-生物多样性维护，3-水土保持，4-防风固沙，5-其他生态功能。生态环境敏感性包括：1-水土流失，2-土地沙化，3-石漠化，4-盐渍化，5-其他敏感性。)")
    @TableField("LXBM")
    private String lxbm;

    @Excel(name = "保护地名称",height = 11, width = 15)
    @ApiModelProperty(value = "保护地名称")
    @TableField("BHDMC")
    private String bhdmc;

    @Excel(name = "保护地级别",height = 11, width = 15)
    @ApiModelProperty(value = "保护地级别")
    @TableField("BHDJB")
    private String bhdjb;

    @Excel(name = "红线命名",height = 11, width = 15)
    @ApiModelProperty(value = "红线命名")
    @TableField("HXMM")
    private String hxmm;

    @Excel(name = "红线编码",height = 11, width = 15)
    @ApiModelProperty(value = "红线编码")
    @TableField("HXBM")
    private String hxbm;

    @Excel(name = "生态系统与植被类型",height = 11, width = 15)
    @ApiModelProperty(value = "生态系统与植被类型")
    @TableField("STXTYZBLX")
    private String stxtyzblx;

    @Excel(name = "主要人为活动类型",height = 11, width = 15)
    @ApiModelProperty(value = "主要人为活动类型")
    @TableField("ZYRWHDLX")
    private String zyrwhdlx;

    @Excel(name = "生态环境问题",height = 11, width = 15)
    @ApiModelProperty(value = "生态环境问题")
    @TableField("STHJWT")
    private String sthjwt;

    @Excel(name = "管控措施",height = 11, width = 15)
    @ApiModelProperty(value = "管控措施")
    @TableField("GKCS")
    private String gkcs;

    @Excel(name = "图斑编码",height = 11, width = 15)
    @ApiModelProperty(value = "图斑编码")
    @TableField("TBBM")
    private String tbbm;

    @Excel(name = "图斑面积",height = 11, width = 15)
    @ApiModelProperty(value = "图斑面积")
    @TableField("TBMJ")
    private Float tbmj;

    @Excel(name = "备注",height = 11, width = 15)
    @ApiModelProperty(value = "备注")
    @TableField("BZ")
    private String bz;

    @Excel(name = "地理范围",height = 11, width = 15)
    @ApiModelProperty(value = "地理范围")
    @TableField("DLFW")
    private String dlfw;

    @Excel(name = "红线具体位置",height = 11, width = 15)
    @ApiModelProperty(value = "红线具体位置")
    @TableField("HXJTWZ")
    private String hxjtwz;

    @Excel(name = "数据描述",height = 11, width = 15)
    @ApiModelProperty(value = "数据描述")
    @TableField("SJMS")
    private String sjms;

    @Excel(name = "功能分区",height = 11, width = 15)
    @ApiModelProperty(value = "功能分区")
    @TableField("GNFQ")
    private String gnfq;

    @Excel(name = "分带",height = 11, width = 15)
    @ApiModelProperty(value = "分带")
    @TableField("FD")
    private String fd;

    @Excel(name = "一级流域",height = 11, width = 15)
    @ApiModelProperty(value = "一级流域")
    @TableField("YJLY")
    private String yjly;

    @Excel(name = "分类名称",height = 11, width = 15)
    @ApiModelProperty(value = "分类名称")
    @TableField("FLMC")
    private String flmc;

    @Excel(name = "保护对象",height = 11, width = 15)
    @ApiModelProperty(value = "保护对象")
    @TableField("BHDX")
    private String bhdx;

    @Excel(name = "保护目标",height = 11, width = 15)
    @ApiModelProperty(value = "保护目标")
    @TableField("BHMB")
    private String bhmb;

    @Excel(name = "管控要求",height = 11, width = 15)
    @ApiModelProperty(value = "管控要求")
    @TableField("GKYQ")
    private String gkyq;
@ExcelIgnore
    @ApiModelProperty(value = "变更时间")
    @TableField("BGSJ")
    private LocalDate bgsj;

    @Excel(name = "现状",height = 11, width = 15)
    @ApiModelProperty(value = "现状")
    @TableField("XZ")
    private String xz;

    @Excel(name = "要求",height = 11, width = 15)
    @ApiModelProperty(value = "要求")
    @TableField("YQ")
    private String yq;

    @Excel(name = "水质现状",height = 11, width = 15)
    @ApiModelProperty(value = "水质现状")
    @TableField("SZXZ")
    private String szxz;

    @Excel(name = "水质目标",height = 11, width = 15)
    @ApiModelProperty(value = "水质目标")
    @TableField("SZMB")
    private String szmb;
    @ExcelIgnore
    @ApiModelProperty(value = "删除状态0已删除 1未删除")
    @TableField("DEL")
    private String del;
    @ExcelIgnore
    @ApiModelProperty(value = "添加人id")
    @TableField("ADD_UID")
    private Integer add_uid;
    @ExcelIgnore
    @ApiModelProperty(value = "添加时间")
    @TableField("ADD_TIME")
    private LocalDateTime add_time;


}
