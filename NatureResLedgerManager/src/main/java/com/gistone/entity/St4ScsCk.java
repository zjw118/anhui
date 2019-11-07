package com.gistone.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import jdk.nashorn.internal.ir.annotations.Ignore;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

/**
 * <p>
 *
 * </p>
 *
 * @author zhaojingwei
 * @since 2019-07-26
 */
//@JsonIgnoreProperties(value = { "checkLedgerAttach", "st4ScsCd" })

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="台账实体类对象    其中ck001参数添加删除接口必传，pageNumber,pageSize列表查询接口必传，其余参数非必传", description="")
public class St4ScsCk extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 唯一主键
     */
    @ApiModelProperty(name="ck001",value = "唯一主键",dataType = "Integer",required = true,example = "1")
    @TableId(value = "CK001", type = IdType.AUTO)
    private Integer ck001;

    @ApiModelProperty(value = "行政区",dataType = "Integer",example = "1")
    @TableField("CK002")
    private Integer ck002;

    @ApiModelProperty(value = "问题点位编号",dataType = "String",example = "ABC")
    @TableField("CD004")
    private String cd004;

    @ApiModelProperty(value = "保护地数据id",dataType = "Integer",example = "2")
    @TableField("SG001")
    private Integer sg001;

    @ApiModelProperty(value = "所在功能区",dataType = "String",example = "核心区")
    @TableField("CK005")
    private String ck005;

    @ApiModelProperty(value = "(绿盾使用将类型改为varchar)实际功能区",dataType = "String",example = "2")
    @TableField("CK006")
    private String ck006;

    @ApiModelProperty(value = "经度",dataType = "Integer",example = "116.32346")
    @TableField("CK007")
    private String ck007;

    @ApiModelProperty(value = "纬度",dataType = "String",example = "23.23676")
    @TableField("CK008")
    private String ck008;

    @ApiModelProperty(value = "所在位置描述",dataType = "String",example = "北京")
    @TableField("CK009")
    private String ck009;

    @ApiModelProperty(value = "活动/设施名称",dataType = "String",example = "名称")
    @TableField("CK010")
    private String ck010;

    @ApiModelProperty(value = "占地面积",dataType = "String",example = "2")
    @TableField("CK011")
    private String ck011;

    @ApiModelProperty(value = "活动设施现状",dataType = "String",example = "破")
    @TableField("CK012")
    private String ck012;

    @ApiModelProperty(value = "变化类型",dataType = "String",example = "新增")
    @TableField("CK013")
    private String ck013;

    @ApiModelProperty(value = "是否违法违规",dataType = "Integer",example = "是")
    @TableField("CK014")
    private String ck014;

    @ApiModelProperty(value = "问题描述",dataType = "String",example = "问题描述")
    @TableField("CK015")
    private String ck015;

    @ApiModelProperty(value = "（绿盾使用，将类型改为varvhar）问题类型（活动/设施类型）",dataType = "String",example = "问题类型（活动/设施类型）")
    @TableField("CK016")
    private String ck016;

    @ApiModelProperty(value = "实际问题类型(描述)",dataType = "String",example = "实际问题类型")
    @TableField("CK017")
    private String ck017;

    @ApiModelProperty(value = "存在问题及主要生态影响",dataType = "String",example = "存在问题及主要生态影响")
    @TableField("CK018")
    private String ck018;

    @ApiModelProperty(value = "建设单位",dataType = "String",example = "北京环保局")
    @TableField("CK019")
    private String ck019;

    @ApiModelProperty(value = "建设时间",dataType = "String",example = "")
    @TableField("CK020")
    //@JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd")
    //@DateTimeFormat(pattern = "yyyy-MM-dd")
    private String ck020;

    @ApiModelProperty(value = "有无环评手续(1是 0无)",dataType = "Integer",example = "1")
    @TableField("CK021")
    private Integer ck021;

    @ApiModelProperty(value = "环评手续批复及验收文号",dataType = "String",example = "a")
    @TableField("CK022")
    private String ck022;

    @ApiModelProperty(value = "有无林业相关审批手续(1是 0无)",dataType = "Integer",example = "1")
    @TableField("CK023")
    private Integer ck023;

    @ApiModelProperty(value = "林业手续批复及验收文号",dataType = "String",example = "a")
    @TableField("CK024")
    private String ck024;

    @ApiModelProperty(value = "有无养殖业相关审批手续(1是 0无)",dataType = "Integer",example = "2")
    @TableField("CK025")
    private Integer ck025;

    @ApiModelProperty(value = "养殖业手续批复及验收文号",dataType = "String",example = "a")
    @TableField("CK026")
    private String ck026;

    @ApiModelProperty(value = "有无发改部门相关手续(1是 0无)",dataType = "Integer",example = "1")
    @TableField("CK027")
    private Integer ck027;

    @ApiModelProperty(value = "发改部门相关手续",dataType = "String",example = "s")
    @TableField("CK028")
    private String ck028;

    @ApiModelProperty(value = "有无规划相关审批手续(1是 0无)",dataType = "Integer",example = "1")
    @TableField("CK029")
    private Integer ck029;

    @ApiModelProperty(value = "规划手续批复及验收文号",dataType = "String",example = "a")
    @TableField("CK030")
    private String ck030;

    @ApiModelProperty(value = "有无旅游相关手续(1是 0无)",dataType = "Integer",example = "1")
    @TableField("CK031")
    private Integer ck031;

    @ApiModelProperty(value = "旅游手续批复及验收文号",dataType = "String",example = "a")
    @TableField("CK032")
    private String ck032;

    @ApiModelProperty(value = "有无财政部门相关资金手续(1是 0无)",dataType = "Integer",example = "1")
    @TableField("CK033")
    private Integer ck033;

    @ApiModelProperty(value = "财政部门相关资金手续批复文号",dataType = "String",example = "b")
    @TableField("CK034")
    private String ck034;

    @ApiModelProperty(value = "有无工商营业执照(1是 0无)",dataType = "Integer",example = "1")
    @TableField("CK035")
    private Integer ck035;

    @ApiModelProperty(value = "营业执照注册号或统一社会信用代码",dataType = "String",example = "a")
    @TableField("CK036")
    private String ck036;

    @ApiModelProperty(value = "有无其他相关审批手续或行政许可手续(1是 0无)",dataType = "Integer",example = "1")
    @TableField("CK037")
    private Integer ck037;

    @ApiModelProperty(value = "(绿盾使用)其他相关审批手续或行政许可手续",dataType = "String",example = "g")
    @TableField("CK038")
    private String ck038;

    @ApiModelProperty(value = "是否处罚 ",dataType = "Integer",example = "1")
    @TableField("CK039")
    private String ck039;

    @ApiModelProperty(value = "处罚方式",dataType = "String",example = "现金")
    @TableField("CK040")
    private String ck040;

    @ApiModelProperty(value = "罚款（万元）",dataType = "String",example = "2")
    @TableField("CK041")
    private String ck041;




    @ApiModelProperty(value = "整改时限",dataType = "LocalDateTime",example = "2015-12-30")
    @TableField("CK042")
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate ck042;

    @ApiModelProperty(value = "整改措施->整改进展?",dataType = "String",example = "整改措施")
    @TableField("CK043")
    private String ck043;

    @ApiModelProperty(value = "拆除建筑面积（平方米）",dataType = "String",example = "2")
    @TableField("CK044")
    private String ck044;

    @ApiModelProperty(value = "处理情况",dataType = "String",example = "处理情况")
    @TableField("CK045")
    private String ck045;

    @ApiModelProperty(value = "(绿盾将类型改为String)是否销号",dataType = "String",example = "1")
    @TableField("CK046")
    private String ck046;

    @ApiModelProperty(value = "核查单位",dataType = "String",example = "北京环保局")
    @TableField("CK047")
    private String ck047;

    @ApiModelProperty(value = "核查时间",dataType = "LocalDateTime",example = "2015-12-30")
    @TableField("CK048")
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime ck048;

    @ApiModelProperty(value = "核查人",dataType = "String",example = "张三")
    @TableField("CK049")
    private String ck049;

    @ApiModelProperty(value = "联系方式",dataType = "String",example = "18435131")
    @TableField("CK050")
    private String ck050;

    @ApiModelProperty(value = "参与核查人数",dataType = "Integer",example = "2")
    @TableField("CK051")
    private Integer ck051;

    @ApiModelProperty(value = "填表人",dataType = "String",example = "李四")
    @TableField("CK052")
    private String ck052;

    @ApiModelProperty(value = "审核人",dataType = "String",example = "")
    @TableField("CK053")
    private String ck053;

    @ApiModelProperty(value = "是否填写问责情况",dataType = "Integer",example = "1")
    @TableField("CK054")
    private Integer ck054;

    @ApiModelProperty(value = "省级追责问责人数",dataType = "Integer",example = "2")
    @TableField("CK055")
    private Integer ck055;

    @ApiModelProperty(value = "厅级追责问责人数",dataType = "Integer",example = "2")
    @TableField("CK056")
    private Integer ck056;

    @ApiModelProperty(value = "县级追责问责人数",dataType = "Integer",example = "2")
    @TableField("CK057")
    private Integer ck057;

    @ApiModelProperty(value = "科级追责问责人数",dataType = "Integer",example = "2")
    @TableField("CK058")
    private Integer ck058;

    @ApiModelProperty(value = "其他追责问责人数",dataType = "Integer",example = "2")
    @TableField("CK059")
    private Integer ck059;

    @ApiModelProperty(value = "党纪政纪处分人数",dataType = "Integer",example = "2")
    @TableField("CK060")
    private Integer ck060;

    @ApiModelProperty(value = "诫勉谈话人数",dataType = "Integer",example = "2")
    @TableField("CK061")
    private Integer ck061;

    @ApiModelProperty(value = "通报批评人数",dataType = "Integer",example = "2")
    @TableField("CK062")
    private Integer ck062;

    @ApiModelProperty(value = "追责问责情况表备注",dataType = "String",example = "2")
    @TableField("CK063")
    private String ck063;

    @ApiModelProperty(value = "追责问责情况表填表人",dataType = "String",example = "2")
    @TableField("CK064")
    private String ck064;

    @ApiModelProperty(value = "追责问责情况表联系方式",dataType = "String",example = "2")
    @TableField("CK065")
    private String ck065;

    @ApiModelProperty(value = "追责问责情况表审核人",dataType = "String",example = "2")
    @TableField("CK066")
    private String ck066;

    @ApiModelProperty(value = "第一次审核:未审核0 1是已审核 2是退回",dataType = "Integer",example = "")
    @TableField("CK067")
    private Integer ck067;

    @ApiModelProperty(value = "上报的状态:默认是0未上报     1是已上报",dataType = "Integer",example = "")
    @TableField("CK068")
    private Integer ck068;

    @ApiModelProperty(value = "审核所处的阶段1表示重审了1次，2表示重审了2次以此类推",dataType = "Integer",example = "")
    @TableField("CK069")
    private Integer ck069;

    @ApiModelProperty(value = "退回原因(专人审核)",dataType = "String",example = "")
    @TableField("CK070")
    private String ck070;

    @ApiModelProperty(value = "退回原因(追责退回原因)",dataType = "String",example = "")
    @TableField("CK071")
    private String ck071;

    @ApiModelProperty(value = "核查数据编码（移动端生成）",dataType = "String",example = "")
    @TableField("CK072")
    private String ck072;

    @ApiModelProperty(value = "航点编号（唯一标识）",dataType = "String",example = "")
    @TableField("CC002")
    private String cc002;

    @ApiModelProperty(value = "激活状态 0未激活 1已激活",dataType = "Integer",example = "")
    @TableField("CK074")
    private Integer ck074;

    @ApiModelProperty(value = "0无问责   1有问责",dataType = "Integer",example = "1")
    @TableField("CK075")
    private Integer ck075;

    @ApiModelProperty(value = "备注",dataType = "String",example = "")
    @TableField("CK076")
    private String ck076;

    @ApiModelProperty(value = "问责问题描述",dataType = "String",example = "")
    @TableField("CK077")
    private String ck077;

    @ApiModelProperty(value = "台账来源",dataType = "String",example = "")
    @TableField("CK078")
    private String ck078;

    @ApiModelProperty(value = "自用备注",dataType = "String",example = "")
    @TableField("CK079")
    private String ck079;

    @ApiModelProperty(value = "是否四类聚焦 ",dataType = "Integer",example = "1")
    @TableField("CK080")
    private String ck080;

    @ApiModelProperty(value = "是否巡查台账 ",dataType = "Integer",example = "1")
    @TableField("CK081")
    private String ck081;

    @ApiModelProperty(value = "是否市级巡查 ",dataType = "Integer",example = "1")
    @TableField("CK082")
    private String ck082;

    @ApiModelProperty(value = "是否国家点 ",dataType = "Integer",example = "1")
    @TableField("CK083")
    private String ck083;

    @ApiModelProperty(value = "是否重点台账 ",dataType = "Integer",example = "1")
    @TableField("CK084")
    private String ck084;

    @ApiModelProperty(value = "删除状态  0已删除 1未删除 默认1未删除",dataType = "Integer",example = "1")
    @TableField("CK085")
    private Integer ck085;

    @ApiModelProperty(value = "添加时间",dataType = "LocalDateTime",example = "2016-03-25")
    @TableField("CK086")
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime ck086;

    @ApiModelProperty(value = "添加人ID",dataType = "Integer",example = "")
    @TableField("CK087")
    private Integer ck087;

    @ApiModelProperty(value = "(1是 0否)(绿盾使用，将类型改为varchar)是否是原始台账 0不是 1是 默认1",dataType = "Integer",example = "1")
    @TableField("CK088")
    private Integer ck088;

    @ApiModelProperty(value = "申诉状态",dataType = "String",example = "")
    @TableField("CK089")
    private Integer ck089;

    @ApiModelProperty(value = "备用3",dataType = "String",example = "")
    @TableField("CK090")
    private String ck090;

    @ApiModelProperty(value = "所属任务ID",dataType = "Integer",example = "2")
    @TableField("CK091")
    private Integer ck091;

    @ApiModelProperty(value = "问题分类",dataType = "Integer",example = "2")
    @TableField("CK092")
    private String ck092;
    @ApiModelProperty(value = "是否是巡查点",dataType = "Integer",example = "2")
    @TableField("CK093")
    private String ck093;


    @ApiModelProperty(name="checkLedgerAttach",value="照片集合",dataType = "List",example="[d:1.jpg,d:2.png]")
    @TableField(exist = false)
    private List<St4ScsCn> checkLedgerAttach;

    @ApiModelProperty(name="st4ScsCd",value="问题点",dataType = "object",example="")
    @TableField(exist = false)
    private St4ScsCd st4ScsCd;

    @ApiModelProperty(name="processStatus",value="整改状态",dataType = "Integer",example="1")
    @TableField(exist = false)
    private String processStatus;

    //整改状态表
    @ApiModelProperty(name="st4ScsCn",value="",dataType = "Integer",example="1")
    @TableField(exist = false)
    private St4ScsCn st4ScsCn;

    //任务批次
    @TableField(exist = false)
    private St4ScsCl st4ScsCl;

    //任务批次集合(安徽因为co台账可以重复被多个任务绑定所以返回的可能是任务的集合)
    @TableField(exist = false)
    private List<St4ScsCl> st4ScsClList;
    //动态配置的核查信息
    @TableField(exist = false)
    private St4ScsCp st4ScsCp;

    //人员
    @TableField(exist = false)
    private St4SysSa st4SysSa;
    /**
     * 保护地级别
     */
    @TableField(exist = false)
    private St4ScsCb st4ScsCb;
    /**
     * 环科院用
     */
    @ApiModelProperty(name="st4ScsCn",value="环科院项目用，其余项目不用管",dataType = "St4ScsCac",example="")
    @TableField(exist = false)
    private  St4ScsCac st4ScsCac;

    //保护地
    @TableField(exist = false)
    private St4SysSg st4SysSg;

    //行政区
    @TableField(exist = false)
    private SysCompany sysCompany;

    //整改状态
    @TableField(exist = false)
    @ApiModelProperty(value="整改状态",example = "")
    private List<St4ScsCn> st4ScsCnList;

    //核查小组
    @ApiModelProperty(value="核查小组(包裹所属核查小组的信息)",example = "")
    @TableField(exist = false)
    private St4ScsCz st4ScsCz;
    @ApiModelProperty(name="iterpretation",value="图斑实体类(包裹图斑信息)",dataType = "object",example="")
    @TableField(exist = false)
    private St4ScsCd iterpretation;

    @ApiModelProperty(name="st4ScsCo",value="安徽图斑绑定的台账表(模糊查询项)",dataType = "object",example="")
    @TableField(exist = false)
    private RlhdGroup rlhdGroup;

    ///整改进展，移动上传方便数据传输结构用，其余接口不用关心
    @ApiModelProperty(value="整改进展，移动上传方便数据传输结构用，其余接口不用关心",example = "")
    @TableField(exist = false)
    private String cn010;

    @ApiModelProperty(value="整改照片，移动上传方便数据传输结构用，其余接口不用关心",example = "")
    @TableField(exist = false)
    private List<String> cn004;


    /**
     * NatureResDecodeManager
     */
    @TableField(exist = false)
    @ApiModelProperty(value="任务批次年份",example = "2019")
    private String taskYear;
    @TableField(exist = false)
    @ApiModelProperty(value="任务批次主键",example = "1")
    private Integer cl001;
    @TableField(exist = false)
    @ApiModelProperty(value="保护区名称",example = "模糊查询")
    private String bhqmc;



    @TableField(exist = false)
    @ApiModelProperty(value="未整改数量",example = "99")
    private Integer wzg;
    @TableField(exist = false)
    @ApiModelProperty(value="整改中数量",example = "99")
    private Integer zgz;
    @TableField(exist = false)
    @ApiModelProperty(value="整改完成数量",example = "99")
    private Integer zgwc;
    @TableField(exist = false)
    @ApiModelProperty(value="无需整改数量",example = "99")
    private Integer wxzg;
    @TableField(exist = false)
    @ApiModelProperty(value="保护区名称",example = "花果山")
    private String SG008;
    @TableField(exist = false)
    @ApiModelProperty(value="行政区名称",example = "北京")
    private String SD008;




    @TableField(exist = false)
    @ApiModelProperty(value="整改查询类型(1保护地,2行政区)",example = "1")
    private String type;
    @TableField(exist = false)
    @ApiModelProperty(value="台账数量",example = "99")
    private String tzNum;
    @TableField(exist = false)
    @ApiModelProperty(value="已销号数量",example = "99")
    private String xhNum;
    @TableField(exist = false)
    @ApiModelProperty(value="未销号数量",example = "99")
    private String unxhNum;
    @TableField(exist = false)
    @ApiModelProperty(value="违法违规数目数量",example = "99")
    private String isPunishNum;
    @TableField(exist = false)
    @ApiModelProperty(value="未违法违规数量",example = "99")
    private String isNotPunishNum;

    @TableField(exist = false)
    @ApiModelProperty(value="农业用地数量",example = "99")
    private String farmNum;

    @TableField(exist = false)
    @ApiModelProperty(value="居民点数量",example = "99")
    private String residentNum;

    @TableField(exist = false)
    @ApiModelProperty(value="工矿用地数量",example = "99")
    private String industrialNum;

    @TableField(exist = false)
    @ApiModelProperty(value="采石场数量",example = "99")
    private String quarryNum;

    @TableField(exist = false)
    @ApiModelProperty(value="能源设施数量",example = "99")
    private String EnergyNum;

    @TableField(exist = false)
    @ApiModelProperty(value="旅游设施数量",example = "99")
    private String journeyNum;

    @TableField(exist = false)
    @ApiModelProperty(value="交通设施数量",example = "99")
    private String trafficNum;

    @TableField(exist = false)
    @ApiModelProperty(value="养殖场数量",example = "99")
    private String feetNum;

    @TableField(exist = false)
    @ApiModelProperty(value="道路数量",example = "99")
    private String roadNum;

    @TableField(exist = false)
    @ApiModelProperty(value="水电设施数量",example = "99")
    private String waterElectricityNum;


    @Ignore
    @TableField(exist = false)
    @ApiModelProperty(value="权限用不用管",example = "")
    private Integer ptype;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        St4ScsCk st4ScsCk = (St4ScsCk) o;
        return
                Objects.equals(ck002, st4ScsCk.ck002) &&
                        Objects.equals(cd004, st4ScsCk.cd004) &&
                        Objects.equals(sg001, st4ScsCk.sg001) &&
                        Objects.equals(ck005, st4ScsCk.ck005) &&
                        Objects.equals(ck006, st4ScsCk.ck006) &&
                        Objects.equals(ck007, st4ScsCk.ck007) &&
                        Objects.equals(ck008, st4ScsCk.ck008) &&
                        Objects.equals(ck009, st4ScsCk.ck009) &&
                        Objects.equals(ck010, st4ScsCk.ck010) &&
                        Objects.equals(ck011, st4ScsCk.ck011) &&
                        Objects.equals(ck012, st4ScsCk.ck012) &&
                        Objects.equals(ck013, st4ScsCk.ck013) &&
                        Objects.equals(ck014, st4ScsCk.ck014) &&
                        Objects.equals(ck015, st4ScsCk.ck015) &&
                        Objects.equals(ck016, st4ScsCk.ck016) &&
                        Objects.equals(ck017, st4ScsCk.ck017) &&
                        Objects.equals(ck018, st4ScsCk.ck018) &&
                        Objects.equals(ck019, st4ScsCk.ck019) &&
                        Objects.equals(ck020, st4ScsCk.ck020) &&
                        Objects.equals(ck021, st4ScsCk.ck021) &&
                        Objects.equals(ck022, st4ScsCk.ck022) &&
                        Objects.equals(ck023, st4ScsCk.ck023) &&
                        Objects.equals(ck024, st4ScsCk.ck024) &&
                        Objects.equals(ck025, st4ScsCk.ck025) &&
                        Objects.equals(ck026, st4ScsCk.ck026) &&
                        Objects.equals(ck027, st4ScsCk.ck027) &&
                        Objects.equals(ck028, st4ScsCk.ck028) &&
                        Objects.equals(ck029, st4ScsCk.ck029) &&
                        Objects.equals(ck030, st4ScsCk.ck030) &&
                        Objects.equals(ck031, st4ScsCk.ck031) &&
                        Objects.equals(ck032, st4ScsCk.ck032) &&
                        Objects.equals(ck033, st4ScsCk.ck033) &&
                        Objects.equals(ck034, st4ScsCk.ck034) &&
                        Objects.equals(ck035, st4ScsCk.ck035) &&
                        Objects.equals(ck036, st4ScsCk.ck036) &&
                        Objects.equals(ck037, st4ScsCk.ck037) &&
                        Objects.equals(ck038, st4ScsCk.ck038) &&
                        Objects.equals(ck039, st4ScsCk.ck039) &&
                        Objects.equals(ck040, st4ScsCk.ck040) &&
                        Objects.equals(ck041, st4ScsCk.ck041) &&
                        Objects.equals(ck042, st4ScsCk.ck042) &&
                        Objects.equals(ck043, st4ScsCk.ck043) &&
                        Objects.equals(ck044, st4ScsCk.ck044) &&
                        Objects.equals(ck045, st4ScsCk.ck045) &&
                        Objects.equals(ck046, st4ScsCk.ck046) &&
                        Objects.equals(ck047, st4ScsCk.ck047) &&
                        Objects.equals(ck048, st4ScsCk.ck048) &&
                        Objects.equals(ck049, st4ScsCk.ck049) &&
                        Objects.equals(ck050, st4ScsCk.ck050) &&
                        Objects.equals(ck051, st4ScsCk.ck051) &&
                        Objects.equals(ck052, st4ScsCk.ck052) &&
                        Objects.equals(ck053, st4ScsCk.ck053) &&
                        Objects.equals(ck054, st4ScsCk.ck054) &&
                        Objects.equals(ck055, st4ScsCk.ck055) &&
                        Objects.equals(ck056, st4ScsCk.ck056) &&
                        Objects.equals(ck057, st4ScsCk.ck057) &&
                        Objects.equals(ck058, st4ScsCk.ck058) &&
                        Objects.equals(ck059, st4ScsCk.ck059) &&
                        Objects.equals(ck060, st4ScsCk.ck060) &&
                        Objects.equals(ck061, st4ScsCk.ck061) &&
                        Objects.equals(ck062, st4ScsCk.ck062) &&
                        Objects.equals(ck063, st4ScsCk.ck063) &&
                        Objects.equals(ck064, st4ScsCk.ck064) &&
                        Objects.equals(ck065, st4ScsCk.ck065) &&
                        Objects.equals(ck066, st4ScsCk.ck066) &&
                        Objects.equals(ck067, st4ScsCk.ck067) &&
                        Objects.equals(ck078, st4ScsCk.ck078) &&
                        Objects.equals(ck079, st4ScsCk.ck079) &&
                        Objects.equals(ck080, st4ScsCk.ck080) &&
                        Objects.equals(ck081, st4ScsCk.ck081) &&
                        Objects.equals(ck082, st4ScsCk.ck082) &&
                        Objects.equals(ck083, st4ScsCk.ck083) &&
                        Objects.equals(ck084, st4ScsCk.ck084) &&
                        Objects.equals(ck085, st4ScsCk.ck085) &&
                        Objects.equals(ck091, st4ScsCk.ck091) ;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), ck002, cd004, sg001, ck005, ck006, ck007, ck008, ck009, ck010, ck011, ck012, ck013, ck014,
                ck015, ck016, ck017, ck018, ck019, ck020, ck021, ck022, ck023, ck024, ck025, ck026, ck027, ck028, ck029, ck030, ck031,
                ck032, ck033, ck034, ck035, ck036, ck037, ck038, ck039, ck040, ck041, ck042, ck043, ck044, ck045, ck046, ck047, ck048,
                ck049, ck050, ck051, ck052, ck053, ck054, ck055, ck056, ck057, ck058, ck059, ck060, ck061, ck062, ck063, ck064, ck065,
                ck066, ck067, ck068, ck069, ck070, ck071, ck072, cc002, ck074, ck075, ck076, ck077, ck078, ck079, ck080, ck081, ck082,
                ck083, ck084, ck085, ck086, ck087, ck088, ck089, ck090, ck091, checkLedgerAttach, st4ScsCd, processStatus, st4ScsCn,
                st4ScsCl, st4ScsCb, st4SysSg, sysCompany, st4ScsCnList, st4ScsCz, taskYear, cl001, bhqmc, wzg, zgz, zgwc, wxzg, SG008,
                SD008, type, ptype);
    }
}
