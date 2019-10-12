package com.gistone.bjhky.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 数据备份表
 * </p>
 *
 * @author zjw
 * @since 2019-09-04
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="St4SysSe对象", description="数据备份表")
public class St4SysSe extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "数据备份表主键")
    @TableId(value = "SE001", type = IdType.AUTO)
    private Integer se001;

    @ApiModelProperty(value = "备份名称")
    @TableField("SE002")
    private String se002;

    @ApiModelProperty(value = "备份人ID")
    @TableField("SE003")
    private Integer se003;

    @ApiModelProperty(value = "备份时间")
    @TableField("SE004")
    private LocalDateTime se004;

    @ApiModelProperty(value = "文件的url")
    @TableField("SE005")
    private String se005;

    @ApiModelProperty(value = "0手动 1自动")
    @TableField("SE006")
    private Integer se006;

    @ApiModelProperty(value = "0已删除 1未删除",example = "1")
    @TableField("SE007")
    private Integer se007;

    @ApiModelProperty(value = "0未恢复 1已恢复过")
    @TableField("SE008")
    private Integer se008;

    @ApiModelProperty(value = "备注")
    @TableField("SE009")
    private String se009;


}
