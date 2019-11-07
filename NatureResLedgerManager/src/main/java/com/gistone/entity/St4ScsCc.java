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
import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 * 航点基础表
 * </p>
 *
 * @author xxh
 * @since 2019-08-16
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="St4ScsCc对象", description="航点基础表")
public class St4ScsCc extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "航点基础表主键")
    @TableId(value = "CC001", type = IdType.AUTO)
    private Integer cc001;

    @ApiModelProperty(value = "航点唯一标识（必填）")
    @TableField("CC002")
    private String cc002;

    @ApiModelProperty(value = "航点类型 0巡护   1核查  (必填,航点管理列表查询项)")
    @TableField("CC003")
    private Integer cc003;

    @ApiModelProperty(value = "经度")
    @TableField("CC004")
    private String cc004;

    @ApiModelProperty(value = "纬度")
    @TableField("CC005")
    private String cc005;

    @ApiModelProperty(value = "巡查记录唯一标识")
    @TableField("CY017")
    private String cy017;

    @ApiModelProperty(value = "添加时间（航点管理列表查询项）")
    @TableField("CC007")
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime cc007;

    @ApiModelProperty(value = "添加人")
    @TableField("CC008")
    private Integer cc008;

    @ApiModelProperty(value = "更改时间")
    @TableField("CC009")
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime cc009;

    @ApiModelProperty(value = "更改人ID")
    @TableField("CC010")
    private Integer cc010;

    @ApiModelProperty(value = "删除状态 0已删除  1未删除")
    @TableField("CC011")
    private Integer cc011;

    @ApiModelProperty(value = "航点名称（必填航点管理列表查询项）")
    @TableField("CC012")
    private String cc012;


    @ApiModelProperty(value = "问题点编码")
    @TableField("CD004")
    private String cd004;


    @ApiModelProperty(value = "添加人实体(包裹添加人信息)")
    @TableField(exist = false)
    private  St4SysSa st4SysSa;

    @ApiModelProperty(value = "航迹实体(包裹航迹信息)")
    @TableField(exist = false)
    private  St4ScsCf st4Scscf;

    @ApiModelProperty(value = "台账实体(包裹添台账信息)")
    @TableField(exist = false)
    private  St4ScsCk st4ScsCk;


    @ApiModelProperty(value = "航点附件")
    @TableField(exist = false)
    private List<St4ScsCe> st4ScsCe;


    /**
     * NatureResDecodeManager
     */
    @TableField(exist = false)
    @ApiModelProperty(value="统计数量",example = "99")
    private Integer num;

    @TableField(exist = false)
    @ApiModelProperty(value="航点统计横轴类型(1人员，2保护地，3行政区)",example = "3")
    private String type;

    @TableField(exist = false)
    @ApiModelProperty(value="开始添加时间-时间戳",example = "99999999999999")
    private String startDate;

    @TableField(exist = false)
    @ApiModelProperty(value="结束添加时间-时间戳",example = "99999999999999")
    private String endDate;

    @TableField(exist = false)
    @ApiModelProperty(value="人员姓名",example = "张三")
    private String name;

    @TableField(exist = false)
    @ApiModelProperty(value="保护地主键",example = "1")
    private Integer sg001;

    @TableField(exist = false)
    @ApiModelProperty(value="保护地名称",example = "西湖")
    private String sg008;

    @TableField(exist = false)
    @ApiModelProperty(value="行政区主键",example = "1")
    private Integer sd001;

    @TableField(exist = false)
    @ApiModelProperty(value="行政区名称",example = "杭州")
    private String sd008;
@Ignore
    @TableField(exist = false)
    @ApiModelProperty(value="权限用",example = "")
    private Integer ptype;




}
