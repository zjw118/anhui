package com.gistone.bjhky.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 环科院专用标识字段更新时间表
 * </p>
 *
 * @author zjw
 * @since 2019-09-26
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="St4ScsCac对象", description="环科院专用标识字段更新时间表")
public class St4ScsCac extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "关联台账表的主键")
    @TableField("CK001")
    private Integer ck001;

    @ApiModelProperty(value = "行政区")
    @TableField("CAC002")
    private String cac002;

    @ApiModelProperty(value = "问题点位编号（必填）")
    @TableField("CD004")
    private String cd004;

    @ApiModelProperty(value = "保护地数据id")
    @TableField("SG001")
    private String sg001;

    @ApiModelProperty(value = "所在功能区")
    @TableField("CAC003")
    private String cac003;

    @ApiModelProperty(value = "实际功能区")
    @TableField("CAC004")
    private String cac004;

    @ApiModelProperty(value = "经度（必填）")
    @TableField("CAC005")
    private String cac005;

    @ApiModelProperty(value = "纬度（必填）")
    @TableField("CAC006")
    private String cac006;

    @ApiModelProperty(value = "所在位置描述")
    @TableField("CAC007")
    private String cac007;

    @ApiModelProperty(value = "占地面积")
    @TableField("CAC008")
    private String cac008;

    @ApiModelProperty(value = "变化类型")
    @TableField("CAC009")
    private String cac009;

    @ApiModelProperty(value = "是否违法违规")
    @TableField("CAC010")
    private String cac010;

    @ApiModelProperty(value = "问题描述")
    @TableField("CAC011")
    private String cac011;

    @ApiModelProperty(value = "问题类型（活动/设施类型）")
    @TableField("CAC012")
    private String cac012;

    @ApiModelProperty(value = "实际问题类型(描述)")
    @TableField("CAC013")
    private String cac013;

    @ApiModelProperty(value = "建设单位")
    @TableField("CAC014")
    private String cac014;

    @ApiModelProperty(value = "建设时间")
    @TableField("CAC015")
    private String cac015;

    @ApiModelProperty(value = "是否处罚(1是 0否)")
    @TableField("CAC016")
    private String cac016;

    @ApiModelProperty(value = "处罚方式")
    @TableField("CAC017")
    private String cac017;

    @ApiModelProperty(value = "罚款（万元）")
    @TableField("CAC018")
    private String cac018;

    @ApiModelProperty(value = "整改时限")
    @TableField("CAC019")
    private String cac019;

    @ApiModelProperty(value = "整改措施")
    @TableField("CAC020")
    private String cac020;

    @ApiModelProperty(value = "拆除建筑面积（平方米）")
    @TableField("CAC021")
    private String cac021;

    @ApiModelProperty(value = "是否销号")
    @TableField("CAC022")
    private String cac022;

    @ApiModelProperty(value = "联系方式")
    @TableField("CAC023")
    private String cac023;

    @ApiModelProperty(value = "上报的状态:默认是0未上报     1是已上报")
    @TableField("CAC024")
    private String cac024;

    @ApiModelProperty(value = "备注")
    @TableField("CAC025")
    private String cac025;

    @ApiModelProperty(value = "台账来源")
    @TableField("CAC026")
    private String cac026;

    @ApiModelProperty(value = "自用备注")
    @TableField("CAC027")
    private String cac027;

    @ApiModelProperty(value = "是否四类聚焦(1是 0否)")
    @TableField("CAC028")
    private String cac028;

    @ApiModelProperty(value = "是否巡查台账(1是 0否)")
    @TableField("CAC029")
    private String cac029;

    @ApiModelProperty(value = "是否市级巡查(1是 0否)")
    @TableField("CAC030")
    private String cac030;

    @ApiModelProperty(value = "是否国家点(1是 0否)")
    @TableField("CAC031")
    private String cac031;

    @ApiModelProperty(value = "是否重点台账(1是 0否)")
    @TableField("CAC032")
    private String cac032;

    @ApiModelProperty(value = "删除状态  0已删除 1未删除 默认1未删除")
    @TableField("CAC033")
    private String cac033;

    @ApiModelProperty(value = "备用3")
    @TableField("CAC034")
    private String cac034;

    @ApiModelProperty(value = "所属任务ID")
    @TableField("CAC035")
    private String cac035;

    @ApiModelProperty(value = "整改进展")
    @TableField("CAC036")
    private String cac036;


}
