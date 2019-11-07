package com.gistone.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 
 * </p>
 *
 * @author zjw
 * @since 2019-11-07
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="St4ScsCkrl对象", description="")
public class St4ScsCkrl  extends  BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "唯一主键")
    @TableId(value = "CK001", type = IdType.AUTO)
    private Integer ck001;

    @ApiModelProperty(value = "行政区")
    @TableField("CK002")
    private Integer ck002;

    @ApiModelProperty(value = "问题点位编号（必填）")
    @TableField("CD004")
    private String cd004;

    @ApiModelProperty(value = "保护地数据id")
    @TableField("SG001")
    private String sg001;

    @ApiModelProperty(value = "所在功能区")
    @TableField("CK005")
    private String ck005;

    @ApiModelProperty(value = "实际功能区(绿盾使用修改为varchar)")
    @TableField("CK006")
    private String ck006;

    @ApiModelProperty(value = "经度（必填）")
    @TableField("CK007")
    private String ck007;

    @ApiModelProperty(value = "纬度（必填）")
    @TableField("CK008")
    private String ck008;

    @ApiModelProperty(value = "所在位置描述")
    @TableField("CK009")
    private String ck009;

    @ApiModelProperty(value = "活动/设施名称")
    @TableField("CK010")
    private String ck010;

    @ApiModelProperty(value = "占地面积")
    @TableField("CK011")
    private String ck011;

    @ApiModelProperty(value = "活动设施现状")
    @TableField("CK012")
    private String ck012;

    @ApiModelProperty(value = "变化类型")
    @TableField("CK013")
    private String ck013;

    @ApiModelProperty(value = "是否违法违规")
    @TableField("CK014")
    private String ck014;

    @ApiModelProperty(value = "问题描述")
    @TableField("CK015")
    private String ck015;

    @ApiModelProperty(value = "问题类型（活动/设施类型）(绿盾使用修改为varchar)")
    @TableField("CK016")
    private String ck016;

    @ApiModelProperty(value = "实际问题类型(描述)")
    @TableField("CK017")
    private String ck017;

    @ApiModelProperty(value = "存在问题及主要生态影响")
    @TableField("CK018")
    private String ck018;

    @ApiModelProperty(value = "建设单位")
    @TableField("CK019")
    private String ck019;

    @ApiModelProperty(value = "建设时间")
    @TableField("CK020")
    private String ck020;

    @ApiModelProperty(value = "有无环评手续(1是 0无)")
    @TableField("CK021")
    private String ck021;

    @ApiModelProperty(value = "环评手续批复及验收文号")
    @TableField("CK022")
    private String ck022;

    @ApiModelProperty(value = "有无林业相关审批手续(1是 0无)")
    @TableField("CK023")
    private String ck023;

    @ApiModelProperty(value = "林业手续批复及验收文号")
    @TableField("CK024")
    private String ck024;

    @ApiModelProperty(value = "有无养殖业相关审批手续(1是 0无)")
    @TableField("CK025")
    private String ck025;

    @ApiModelProperty(value = "养殖业手续批复及验收文号")
    @TableField("CK026")
    private String ck026;

    @ApiModelProperty(value = "有无发改部门相关手续(1是 0无)")
    @TableField("CK027")
    private String ck027;

    @ApiModelProperty(value = "发改部门相关手续")
    @TableField("CK028")
    private String ck028;

    @ApiModelProperty(value = "有无规划相关审批手续(1是 0无)")
    @TableField("CK029")
    private String ck029;

    @ApiModelProperty(value = "规划手续批复及验收文号")
    @TableField("CK030")
    private String ck030;

    @ApiModelProperty(value = "有无旅游相关手续(1是 0无)")
    @TableField("CK031")
    private String ck031;

    @ApiModelProperty(value = "旅游手续批复及验收文号")
    @TableField("CK032")
    private String ck032;

    @ApiModelProperty(value = "有无财政部门相关资金手续(1是 0无)")
    @TableField("CK033")
    private String ck033;

    @ApiModelProperty(value = "财政部门相关资金手续批复文号")
    @TableField("CK034")
    private String ck034;

    @ApiModelProperty(value = "有无工商营业执照(1是 0无)")
    @TableField("CK035")
    private String ck035;

    @ApiModelProperty(value = "营业执照注册号或统一社会信用代码")
    @TableField("CK036")
    private String ck036;

    @ApiModelProperty(value = "有无其他相关审批手续或行政许可手续(1是 0无)")
    @TableField("CK037")
    private String ck037;

    @ApiModelProperty(value = "其他相关审批手续或行政许可手续(绿盾使用)")
    @TableField("CK038")
    private String ck038;

    @ApiModelProperty(value = "是否处罚(1是 0否)")
    @TableField("CK039")
    private String ck039;

    @ApiModelProperty(value = "处罚方式")
    @TableField("CK040")
    private String ck040;

    @ApiModelProperty(value = "罚款（万元）")
    @TableField("CK041")
    private String ck041;

    @ApiModelProperty(value = "整改时限")
    @TableField("CK042")
    private String ck042;

    @ApiModelProperty(value = "整改措施")
    @TableField("CK043")
    private String ck043;

    @ApiModelProperty(value = "拆除建筑面积（平方米）")
    @TableField("CK044")
    private String ck044;

    @ApiModelProperty(value = "处理情况")
    @TableField("CK045")
    private String ck045;

    @ApiModelProperty(value = "是否销号(绿盾修改为varchar)")
    @TableField("CK046")
    private String ck046;

    @ApiModelProperty(value = "核查单位")
    @TableField("CK047")
    private String ck047;

    @ApiModelProperty(value = "核查时间")
    @TableField("CK048")
    private String ck048;

    @ApiModelProperty(value = "核查人（必填）")
    @TableField("CK049")
    private String ck049;

    @ApiModelProperty(value = "联系方式")
    @TableField("CK050")
    private String ck050;

    @ApiModelProperty(value = "参与核查人数")
    @TableField("CK051")
    private String ck051;

    @ApiModelProperty(value = "填表人")
    @TableField("CK052")
    private String ck052;

    @ApiModelProperty(value = "审核人")
    @TableField("CK053")
    private String ck053;

    @ApiModelProperty(value = "是否填写问责情况")
    @TableField("CK054")
    private String ck054;

    @ApiModelProperty(value = "省级追责问责人数")
    @TableField("CK055")
    private Integer ck055;

    @ApiModelProperty(value = "厅级追责问责人数")
    @TableField("CK056")
    private Integer ck056;

    @ApiModelProperty(value = "县级追责问责人数")
    @TableField("CK057")
    private Integer ck057;

    @ApiModelProperty(value = "科级追责问责人数")
    @TableField("CK058")
    private Integer ck058;

    @ApiModelProperty(value = "其他追责问责人数")
    @TableField("CK059")
    private Integer ck059;

    @ApiModelProperty(value = "党纪政纪处分人数")
    @TableField("CK060")
    private Integer ck060;

    @ApiModelProperty(value = "诫勉谈话人数")
    @TableField("CK061")
    private Integer ck061;

    @ApiModelProperty(value = "通报批评人数")
    @TableField("CK062")
    private Integer ck062;

    @ApiModelProperty(value = "追责问责情况表备注")
    @TableField("CK063")
    private String ck063;

    @ApiModelProperty(value = "追责问责情况表填表人")
    @TableField("CK064")
    private String ck064;

    @ApiModelProperty(value = "追责问责情况表联系方式")
    @TableField("CK065")
    private String ck065;

    @ApiModelProperty(value = "追责问责情况表审核人")
    @TableField("CK066")
    private String ck066;

    @ApiModelProperty(value = "第一次审核:未审核0 1是已审核 2是退回")
    @TableField("CK067")
    private Integer ck067;

    @ApiModelProperty(value = "上报的状态:默认是0未上报     1是已上报")
    @TableField("CK068")
    private Integer ck068;

    @ApiModelProperty(value = "审核所处的阶段1表示重审了1次，2表示重审了2次以此类推")
    @TableField("CK069")
    private Integer ck069;

    @ApiModelProperty(value = "退回原因(专人审核)")
    @TableField("CK070")
    private String ck070;

    @ApiModelProperty(value = "退回原因(追责退回原因)")
    @TableField("CK071")
    private String ck071;

    @ApiModelProperty(value = "核查数据编码（移动端生成）")
    @TableField("CK072")
    private String ck072;

    @ApiModelProperty(value = "航点编号（唯一标识）绿盾用")
    @TableField("CC002")
    private String cc002;

    @ApiModelProperty(value = "激活状态 0未激活 1已激活")
    @TableField("CK074")
    private Integer ck074;

    @ApiModelProperty(value = "0无问责   1有问责")
    @TableField("CK075")
    private Integer ck075;

    @ApiModelProperty(value = "备注")
    @TableField("CK076")
    private String ck076;

    @ApiModelProperty(value = "问责问题描述")
    @TableField("CK077")
    private String ck077;

    @ApiModelProperty(value = "台账来源")
    @TableField("CK078")
    private String ck078;

    @ApiModelProperty(value = "自用备注")
    @TableField("CK079")
    private String ck079;

    @ApiModelProperty(value = "是否四类聚焦(1是 0否)")
    @TableField("CK080")
    private String ck080;

    @ApiModelProperty(value = "是否巡查台账(1是 0否)")
    @TableField("CK081")
    private String ck081;

    @ApiModelProperty(value = "是否市级巡查(1是 0否)")
    @TableField("CK082")
    private String ck082;

    @ApiModelProperty(value = "是否国家点(1是 0否)")
    @TableField("CK083")
    private String ck083;

    @ApiModelProperty(value = "是否重点台账(1是 0否)")
    @TableField("CK084")
    private String ck084;

    @ApiModelProperty(value = "删除状态  0已删除 1未删除 默认1未删除")
    @TableField("CK085")
    private Integer ck085;

    @ApiModelProperty(value = "添加时间")
    @TableField("CK086")
    private LocalDateTime ck086;

    @ApiModelProperty(value = "添加人ID")
    @TableField("CK087")
    private Integer ck087;

    @ApiModelProperty(value = "是否是原始台账  0是 1不是 默认0")
    @TableField("CK088")
    private Integer ck088;

    @ApiModelProperty(value = "申诉状态  0未申诉 1申诉中")
    @TableField("CK089")
    private Integer ck089;

    @ApiModelProperty(value = "备用3（绿盾暂时使用，是否与台账一致,修改为varchar）")
    @TableField("CK090")
    private String ck090;

    @ApiModelProperty(value = "所属任务ID")
    @TableField("CK091")
    private Integer ck091;


}
