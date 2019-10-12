package com.gistone.bjhky.entity;

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

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 * 系统用户
 * </p>
 *
 * @author xxh
 * @since 2019-08-12
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="St4SysSa对象", description="系统用户")
public class St4SysSa extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "唯一主键")
    @TableId(value = "SA001", type = IdType.AUTO)
    private Integer sa001;

    @ApiModelProperty(value = "创建人ID")
    @TableField("SA002")
    private Integer sa002;
    @JsonFormat(shape= JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "创建时间")
    @TableField("SA003")
    private LocalDateTime sa003;

    @ApiModelProperty(value = "更新人")
    @TableField("SA004")
    private Integer sa004;
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "更新时间")
    @TableField("SA005")
    private LocalDateTime sa005;

    @ApiModelProperty(value = "备注")
    @TableField("SA006")
    private String sa006;

    @ApiModelProperty(value = "逻辑删除 0是删除，1是未删除")
    @TableField("SA007")
    private Integer sa007;

    @ApiModelProperty(value = "用户名")
    @TableField("SA008")
    private String sa008;

    @ApiModelProperty(value = "密码")
    @TableField("SA009")
    private String sa009;

    @ApiModelProperty(value = "旧密码")
    @TableField(exist = false)
    private String sa009Old;

    @Ignore
    @ApiModelProperty(value = "盐值")
    @TableField("SA010")
    private String sa010;

    @ApiModelProperty(value = "0男1女")
    @TableField("SA011")
    private Integer sa011;

    @ApiModelProperty(value = "手机号码")
    @TableField("SA012")
    private String sa012;

    @ApiModelProperty(value = "电子邮箱")
    @TableField("SA013")
    private String sa013;

    @ApiModelProperty(value = "修改次数")
    @TableField("SA014")
    private Integer sa014;

    @ApiModelProperty(value = "所属部门")
    @TableField("SA015")
    private String sa015;

    @ApiModelProperty(value = "用户类型  0后台用户 1移动 2 APP和移动端都能登录")
    @TableField("SA016")
    private Integer sa016;
    @JsonFormat(shape= JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "授权截止时间")
    @TableField("SA017")
    private LocalDateTime sa017;

    @ApiModelProperty(value = "用户头像路径")
    @TableField("SA018")
    private String sa018;

    @ApiModelProperty(value = "真实姓名")
    @TableField("SA019")
    private String sa019;

    @ApiModelProperty(value = "用户数据类型 0管理员 1操作员")
    @TableField("SA020")
    private Integer sa020;

    @ApiModelProperty(value = "保护地Id")
    @TableField("SG001")
    private Integer sg001;


    @ApiModelProperty(value = "权限信息")
    @TableField(exist = false)
    private List<St4SysSc> st4SysSc;

    @ApiModelProperty(value = "角色ID集合添加必传，其余可传")
    @TableField(exist = false)
    private List<Integer> roleList;

    @ApiModelProperty(value = "单位ID集合添加必传，其余可传")
    @TableField(exist = false)
    private List<Integer> unitList;

    @ApiModelProperty(value = "详情接口返回参数，前台不必传")
    @TableField(exist = false)
    private List<St4SysSb> sbList;

    @ApiModelProperty(value = "详情接口返回参数，前台不必传")
    @TableField(exist = false)
    private List<St4SysSj> sjList;
    @Ignore
    @ApiModelProperty(value = "列表权限用,不用管")
    @TableField(exist = false)
    private Integer ptype;


    @ApiModelProperty(value = "实时位置查询在线离线用其余接口不管(人员状态 0离线 1在线 2正在巡护中)")
    @TableField(exist = false)
    private Integer ch005;








}
