package com.gistone.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * <p>
 *
 * </p>
 *
 * @author xxh
 * @since 2019-08-14
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="St4ScsCn对象", description="")
public class St4ScsCn implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "图片表主键")
    @TableId(value = "CN001", type = IdType.AUTO)
    private Integer cn001;

    @ApiModelProperty(value = "类型")
    @TableField("CN002")
    private Integer cn002;
    @JsonFormat(shape= JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "添加时间")
    @TableField("CN003")
    private LocalDateTime cn003;

    @ApiModelProperty(value = "整改图片或者影像图的url")
    @TableField("CN004")
    private String cn004;

    @ApiModelProperty(value = "外键(关联台账表的主键)")
    @TableField("CK001")
    private Integer ck001;

    @ApiModelProperty(value = "图片描述")
    @TableField("CN005")
    private String cn005;

    @ApiModelProperty(value = "添加人ID")
    @TableField("CN006")
    private Integer cn006;

    @ApiModelProperty(value = "图片名称")
    @TableField("CN007")
    private String cn007;
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "图片日期")
    @TableField("CN008")
    private LocalDateTime cn008;

    @ApiModelProperty(value = "删除状态 0未删除 0已经删除 1未删除  默认1")
    @TableField("CN009")
    private Integer cn009;

    @ApiModelProperty(value = "(绿盾暂时将类型改为varchar)0未整改 1整改中 2整改完成")
    @TableField("CN010")
    private String cn010;



}
