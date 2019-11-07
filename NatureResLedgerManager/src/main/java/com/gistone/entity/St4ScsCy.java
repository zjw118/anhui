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
 * 巡护记录表
 * </p>
 *
 * @author xxh
 * @since 2019-08-16
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="St4ScsCy对象其中 cy001是主键,剩下的是非必填", description="巡护记录表")
public class St4ScsCy extends BaseEntity  implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "巡护记录唯一主键")
    @TableId(value = "CY001", type = IdType.AUTO)
    private Integer cy001;

    @ApiModelProperty(value = "巡护记录名称")
    @TableField("CY002")
    private String cy002;

    @ApiModelProperty(value = "巡护开始时间(模糊查询项)" ,example = "2019年10月08日 09:42:01")
    @TableField("CY003")
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime cy003;

    @ApiModelProperty(value = "巡护结束时间(模糊查询项)",example = "2019年10月12日 09:42:01")
    @TableField("CY004")
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime cy004;

    @ApiModelProperty(value = "巡护类型（0步行，1骑行）")
    @TableField("CY005")
    private Integer cy005;

    @ApiModelProperty(value = "巡护里程")
    @TableField("CY006")
    private String cy006;

    @ApiModelProperty(value = "巡护时长")
    @TableField("CY007")
    private String cy007;

    @ApiModelProperty(value = "巡护人员id(模糊查询项)")
    @TableField("SA001")
    private Integer sa001;

    @ApiModelProperty(value = "数据提交状态（0字段提交  1手动提交）")
    @TableField("CY009")
    private Integer cy009;

    @ApiModelProperty(value = "保护地id(模糊查询项)")
    @TableField("CY010")
    private Integer cy010;

    @ApiModelProperty(value = "巡护任务类型")
    @TableField("CY011")
    private Integer cy011;

    @ApiModelProperty(value = "添加人(暂时没用因为有巡护人员ID)")
    @TableField("CY012")
    private Integer cy012;

    @ApiModelProperty(value = "添加时间")
    @TableField("CY013")
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime cy013;

    @ApiModelProperty(value = "更新人")
    @TableField("CY014")
    private Integer cy014;

    @ApiModelProperty(value = "更新时间")
    @TableField("CY015")
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime cy015;

    @ApiModelProperty(value = "逻辑删除（0已删除，1未删除）")
    @TableField("CY016")
    private Integer cy016;

    @ApiModelProperty(value = "巡护记录唯一标识")
    @TableField("CY017")
    private String cy017;

    @ApiModelProperty(value = "巡护任务唯一标识(模糊查询项)",example = "3")
    @TableField("CL003")
    private String cl003;


    @ApiModelProperty(value = "保护地实体(包裹保护地信息)")
    @TableField(exist = false)
    private St4SysSg st4SysSg;

    @ApiModelProperty(value = "行政区(包裹行政区信息)")
    @TableField(exist = false)
    private St4SysSd st4SysSd;

    @ApiModelProperty(value = "添加人实体(包裹添加人信息)")
    @TableField(exist = false)
    private St4SysSa st4SysSa;

    @ApiModelProperty(value = "包裹路段信息")
    @TableField(exist = false)
    private List<St4ScsCg> st4ScsCg;
    @ApiModelProperty(value = "包裹航点信息")
    @TableField(exist = false)
    private List<St4ScsCc> st4ScsCc;
    //条件

    @ApiModelProperty(value = "巡查任务ID")
    @TableField(exist = false)
    private String cl001;

    @ApiModelProperty(value = "保护地名称")
    @TableField(exist = false)
    private String bhdmc;

    @ApiModelProperty(value = "巡查人员")
    @TableField(exist = false)
    private String xcry;

    @ApiModelProperty(value = "省份")
    @TableField(exist = false)
    private String sf;
    @ApiModelProperty(value = "开始巡查时间-时间戳")
    @TableField(exist = false)
    private String kssj;
    @ApiModelProperty(value = "结束巡查时间-时间戳")
    @TableField(exist = false)
    private String jssj;
    @ApiModelProperty(value = "任务次数")
    @TableField(exist = false)
    private int rwcs;
    @ApiModelProperty(value = "轨迹里程")
    @TableField(exist = false)
    private float gjlc;

@Ignore
    @ApiModelProperty(value = "权限用，不用管")
    @TableField(exist = false)
    private Integer type;


}
