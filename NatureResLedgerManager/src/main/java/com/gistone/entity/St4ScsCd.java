package com.gistone.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import jdk.nashorn.internal.ir.annotations.Ignore;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;


@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class St4ScsCd extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 问题点表主键
     */
    @TableId(value = "CD001", type = IdType.AUTO)
    @ApiModelProperty(name="cd001",value="问题点Id",dataType = "Integer",required = false,example="1")
    private Integer cd001;

    /**
     * 经度(经纬度格式)
     */
    @NotNull
    @TableField("CD002")
    @ApiModelProperty(name="cd002",value="问题点经度(添加必传)",dataType = "String",required = false,example="101.36534345")
    private String cd002;

    /**
     * 纬度(经纬度格式)
     */
    @NotNull
    @TableField("CD003")
    @ApiModelProperty(name="cd003",value="问题点纬度(添加必传)",dataType = "String",required = false,example="36.363454235")
    private String cd003;

    /**
     * 编号
     */
    @TableField("CD004")
    @ApiModelProperty(name="cd004",value="问题点编号(添加必传)",dataType = "String",required = false,example="t001")
    private String cd004;

    /**
     * 保护区ID
     */
    @TableField("SG001")
    @ApiModelProperty(name="sg001",value="保护地id(添加必传)",dataType = "Integer",required = false,example="1")
    private Integer sg001;

    /**
     * 批次任务ID
     */
    @TableField("CL001")
    @ApiModelProperty(name="cl001",value="任务批次id(添加必传)",dataType = "Integer",required = false,example="1")
    private Integer cl001;

    /**
     * 核查点类型 0代表移动端移交 1代表pc端下发
     */
    @TableField("CD007")
    @ApiModelProperty(name="cd007",value="核查点类型 0代表移动端移交 1代表pc端下发(添加必传)",dataType = "Integer",required = false,example="1")
    private Integer cd007;

    /**
     * 所属行政区ID
     */
    @TableField("SD001")
    @ApiModelProperty(name="sd001",value="所属行政区ID(添加必传)",dataType = "Integer",required = false,example="1")
    private Integer sd001;

    /**
     * 删除状态 0已删除  1未删除  默认1
     */
    @TableField("CD009")
    @ApiModelProperty(name="cd009",value="删除状态 0已删除  1未删除  默认1",dataType = "String",required = false,example="1")
    private Integer cd009;

    /**
     * 添加人
     */
    @TableField("CD010")
    @ApiModelProperty(name="cd010",value="添加人",dataType = "String",required = false,example="")
    @JsonIgnore
    private Integer cd010;

    /**
     * 添加时间
     */
    @JsonFormat(shape= JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @TableField("CD011")
    @ApiModelProperty(name="cd011",value="添加时间",dataType = "String",required = false,example="")
    private LocalDateTime cd011;

    /**
     * 经度(度分秒格式)
     */
    @NotNull
    @TableField("CD013")
    @ApiModelProperty(name="cd013",value="问题点经度(度分秒格式，添加必传)",dataType = "String",required = false,example="")
    private String cd013;

    /**
     * 纬度(度分秒格式)
     */
    @NotNull
    @TableField("CD014")
    @ApiModelProperty(name="cd014",value="问题点纬度(度分秒格式添加必传)",dataType = "String",required = false,example="")
    private String cd014;
    /**
     * 原始坐标格式（0经纬度，1度分秒）
     */
    @NotNull
    @TableField("CD015")
    @ApiModelProperty(name="cd015",value="原始坐标格式（0经纬度，1度分秒）",dataType = "Integer",required = false,example="")
    private Integer cd015;

    /**
     * 问题点备注
     */
    @TableField("CD012")
    @ApiModelProperty(name="cd012",value="问题点备注",dataType = "String",required = false,example="")
    private String cd012;

    @ApiModelProperty(name="st4ScsCl",value="批次任务实体",dataType = "{}",required = false,example="")
    @TableField(exist = false)
    private  St4ScsCl st4ScsCl;

    @ApiModelProperty(name="st4PoCdSa",value="问题点和人关联表",dataType = "{}",required = false,example="")
    @TableField(exist = false)
    private  List<St4PoCdSa> st4PoCdSaList;


    @ApiModelProperty(name="ck088",value="是否是原始台账（0是原始台账，1是核查数据）",dataType = "{}",required = false,example="")
    @TableField(exist = false)
    private String ck088;

    //保护地
    @ApiModelProperty(name="st4SysSg",value="保护地实体",dataType = "{}",required = false,example="")
    @TableField(exist = false)
    private St4SysSg st4SysSg;


    //行政区
    @ApiModelProperty(name="st4SysSd",value="行政区实体",dataType = "{}",required = false,example="")
    @TableField(exist = false)
    private St4SysSd st4SysSd;

    @ApiModelProperty(name="st4ScsCk",value="提交问题台账对象(查询接口的整改进展塞在这个里面传递，参数名叫ck016)",dataType = "object",example="")
    @TableField(exist = false)
    private St4ScsCk st4ScsCk;

    @ApiModelProperty(name="st4ScsCn",value="整改进度及图片对象(查询接口的整改进展塞在这个里面传递，参数名叫cn010)",dataType = "object",example="")
    @TableField(exist = false)
    private St4ScsCn st4ScsCn;


    @ApiModelProperty(name="rlhdGroup",value="安徽台账实体（包裹台账信息）",dataType = "object",example="")
    @TableField(exist = false)
    private RlhdGroup rlhdGroup;


    @ApiModelProperty(name="st4ScsCk",value="同步问题台账,CK088=0是原始台账，否则是核查台账",dataType = "object",example="")
    @TableField(exist = false)
    private List<St4ScsCk> st4ScsCkList;

    @ApiModelProperty(name="taskName",value="任务名称(移动端用)",dataType = "{}",required = false,example="")
    @TableField(exist = false)
    private String taskName;
    @ApiModelProperty(name="reserveName",value="保护区名称(移动端用)",dataType = "{}",required = false,example="")
    @TableField(exist = false)
    private String reserveName;
    @ApiModelProperty(name="adminRegionName",value="行政区实体(移动端用)",dataType = "{}",required = false,example="")
    @TableField(exist = false)
    private String adminRegionName;

    @ApiModelProperty(name="activityName",value="活动设施名称(PC端用)",dataType = "{}",required = false,example="")
    @TableField(exist = false)
    private String activityName;

    @Ignore
    @ApiModelProperty(name="sa01",value="权限用不必管",dataType = "{}",required = false,example="")
    @TableField(exist = false)
    private Integer sa01;
    @Ignore
    @ApiModelProperty(name="sa02",value="权限用不必管",dataType = "{}",required = false,example="")
    @TableField(exist = false)
    private Integer sa02;
    @Ignore
    @ApiModelProperty(name="type",value="权限用不必管",dataType = "{}",required = false,example="")
    @TableField(exist = false)
    private Integer type;


    @TableField("image_id")
    private Integer imageId;
    @TableField("active_name")
    private String activeName;
    @TableField("active_type")
    private String activeType;
    @TableField("area")
    private String area;
    @TableField("descri")
    private String descri;
    @TableField("geometry")
    private String geometry;
    @TableField("group_id")
    private Integer groupId;

    @TableField("region")
    private String region;

    @TableField("position")
    private String position;




    @ApiModelProperty(name="year",value="同步图斑信息返回",dataType = "{}",required = false,example="")
    @TableField(exist = false)
    private String year;



}
