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
 * 问题点分组表
 * </p>
 *
 * @author zjw
 * @since 2019-08-22
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="St4ScsCz对象", description="问题点分组表")
public class St4ScsCz extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "唯一主键(修改删除必传)")
    @TableId(value = "CZ001", type = IdType.AUTO)
    private Integer cz001;

    @ApiModelProperty(value = "核查小组名称（新增必填）")
    @TableField("CZ002")
    private String cz002;

    @ApiModelProperty(value = "1未删除 0已删除")
    @TableField("CZ003")
    private Integer cz003;

    @ApiModelProperty(value = "添加人ID(永不传)")
    @TableField("CZ004")
    private Integer cz004;
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @ApiModelProperty(value = "添加时间(永不传)")
    @TableField("CZ005")
    private LocalDateTime cz005;


    @ApiModelProperty(value = "组长ID(新增修改可传可不传)")
    @TableField("CZ006")
    private Integer cz006;

    @ApiModelProperty(value = "核查任务ID(可传可不传)")
    @TableField("CL001")
    private Integer cl001;



    @ApiModelProperty(value = "组长名称(列表模糊查询才传)")
    @TableField(exist = false)
    private String captainName;
    @ApiModelProperty(value = "组长真实姓名")
    @TableField(exist = false)
    private String captainRealName;
    @ApiModelProperty(value = "组长手机号")
    @TableField(exist = false)
    private String captainPhone;

    @ApiModelProperty(value = "组员名称(列表模糊查询才传)")
    @TableField(exist = false)
    private String memberName;

    @ApiModelProperty(value = "添加人实体(包裹添加人信息，永不传)")
    @TableField(exist = false)
    private St4SysSa st4SysSa;

    @ApiModelProperty(value = "添加人实体集合(详情接口用包裹添加人信息)")
    @TableField(exist = false)
    private List<St4SysSa> st4SysSaList;
    @ApiModelProperty(value = "巡护小组长id集合（新增修改必传）")
    @TableField(exist = false)
    private List<Integer> uidList;
    @Ignore
    @ApiModelProperty(value = "权限用,不必管")
    @TableField(exist = false)
    private Integer type;


}
