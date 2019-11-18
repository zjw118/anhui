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

    @ApiModelProperty(value = "序号")
    @TableId(value = "ID", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "提交数据时间")
    @TableField("TJSJ")
    private LocalDate tjsj;

    @ApiModelProperty(value = "具有一定规律的编号（代码）")
    @TableField("BMS")
    private String bms;

    @ApiModelProperty(value = "省行政区")
    @TableField("Province")
    private String Province;

    @ApiModelProperty(value = "市行政区")
    @TableField("City")
    private String City;

    @ApiModelProperty(value = "行政区：District (county)")
    @TableField("DCounty")
    private String DCounty;

    @ApiModelProperty(value = "行政区代码")
    @TableField("XZQHDM")
    private String xzqhdm;

    @ApiModelProperty(value = "要素代码")
    @TableField("YSDM")
    private String ysdm;

    @ApiModelProperty(value = "主导生态系统服务功能")
    @TableField("ZDSTXTFWGN")
    private String zdstxtfwgn;

    @ApiModelProperty(value = "红线类型")
    @TableField("HXLX")
    private String hxlx;

    @ApiModelProperty(value = "类型编码(类型代码由2位数字组成，第1位表示类型特征，其中，1表示生态功能，2表示生态环境敏感性。后1位表示属性分类，其中，生态功能包括：1-水源涵养，2-生物多样性维护，3-水土保持，4-防风固沙，5-其他生态功能。生态环境敏感性包括：1-水土流失，2-土地沙化，3-石漠化，4-盐渍化，5-其他敏感性。)")
    @TableField("LXBM")
    private String lxbm;

    @ApiModelProperty(value = "保护地名称")
    @TableField("BHDMC")
    private String bhdmc;

    @ApiModelProperty(value = "保护地级别")
    @TableField("BHDJB")
    private String bhdjb;

    @ApiModelProperty(value = "红线命名")
    @TableField("HXMM")
    private String hxmm;

    @ApiModelProperty(value = "红线编码")
    @TableField("HXBM")
    private String hxbm;

    @ApiModelProperty(value = "生态系统与植被类型")
    @TableField("STXTYZBLX")
    private String stxtyzblx;

    @ApiModelProperty(value = "主要人为活动类型")
    @TableField("ZYRWHDLX")
    private String zyrwhdlx;

    @ApiModelProperty(value = "生态环境问题")
    @TableField("STHJWT")
    private String sthjwt;

    @ApiModelProperty(value = "管控措施")
    @TableField("GKCS")
    private String gkcs;

    @ApiModelProperty(value = "图斑编码")
    @TableField("TBBM")
    private String tbbm;

    @ApiModelProperty(value = "图斑面积")
    @TableField("TBMJ")
    private Float tbmj;

    @ApiModelProperty(value = "备注")
    @TableField("BZ")
    private String bz;

    @ApiModelProperty(value = "地理范围")
    @TableField("DLFW")
    private String dlfw;

    @ApiModelProperty(value = "红线具体位置")
    @TableField("HXJTWZ")
    private String hxjtwz;

    @ApiModelProperty(value = "数据描述")
    @TableField("SJMS")
    private String sjms;

    @ApiModelProperty(value = "功能分区")
    @TableField("GNFQ")
    private String gnfq;

    @ApiModelProperty(value = "分带")
    @TableField("FD")
    private String fd;

    @ApiModelProperty(value = "一级流域")
    @TableField("YJLY")
    private String yjly;

    @ApiModelProperty(value = "分类名称")
    @TableField("FLMC")
    private String flmc;

    @ApiModelProperty(value = "保护对象")
    @TableField("BHDX")
    private String bhdx;

    @ApiModelProperty(value = "保护目标")
    @TableField("BHMB")
    private String bhmb;

    @ApiModelProperty(value = "管控要求")
    @TableField("GKYQ")
    private String gkyq;

    @ApiModelProperty(value = "变更时间")
    @TableField("BGSJ")
    private LocalDate bgsj;

    @ApiModelProperty(value = "现状")
    @TableField("XZ")
    private String xz;

    @ApiModelProperty(value = "要求")
    @TableField("YQ")
    private String yq;

    @ApiModelProperty(value = "水质现状")
    @TableField("SZXZ")
    private String szxz;

    @ApiModelProperty(value = "水质目标")
    @TableField("SZMB")
    private String szmb;

    @ApiModelProperty(value = "删除状态0已删除 1未删除")
    @TableField("DEL")
    private String del;


}
