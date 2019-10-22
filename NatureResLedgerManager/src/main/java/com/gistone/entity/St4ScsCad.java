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
 * 消息列表主键
 * </p>
 *
 * @author zjw
 * @since 2019-09-27
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="St4ScsCad对象", description="消息列表主键")
public class St4ScsCad extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "消息列表主键")
    @TableId(value = "CAD001", type = IdType.AUTO)
    private Integer cad001;

    @ApiModelProperty(value = "消息类型")
    @TableField("CAD002")
    private Integer cad002;

    @ApiModelProperty(value = "消息内容")
    @TableField("CAD003")
    private String cad003;

    @ApiModelProperty(value = "已读1  未读0")
    @TableField("CAD004")
    private Integer cad004;

    @ApiModelProperty(value = "消息创建人ID")
    @TableField("CAD005")
    private Integer cad005;

    @ApiModelProperty(value = "消息创建时间")
    @TableField("CAD006")
    private LocalDate cad006;

    @ApiModelProperty(value = "消息推送人ID")
    @TableField("CAD007")
    private Integer cad007;

    @ApiModelProperty(value = "删除状态1未删除  0已删除 默认未删除1")
    @TableField("CAD008")
    private Integer cad008;

    @ApiModelProperty(value = "消息标题")
    @TableField("CAD009")
    private String cad009;

    @ApiModelProperty(value = "经度")
    @TableField("CAD010")
    private Integer cad0010;

    @ApiModelProperty(value = "纬度")
    @TableField("CAD011")
    private Integer cad011;

    @ApiModelProperty(value = "必传：1是 0否")
    @TableField(exist = false)
    private Integer isApp;



}
