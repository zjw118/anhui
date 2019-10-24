package com.gistone.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 人类活动台账信息表
 * </p>
 *
 * @author zjw
 * @since 2019-10-24
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="St4ScsCo对象", description="人类活动台账信息表")
public class St4ScsCo extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "co001", type = IdType.AUTO)
    private Integer co001;

    @ApiModelProperty(value = "台账名称")
    private String name;

    @ApiModelProperty(value = "台账年份")
    private String year;

    @ApiModelProperty(value = "行政区")
    private String adminRegion;

    @ApiModelProperty(value = "创建人id")
    private Integer createBy;

    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createDate;

    private Integer updateBy;

    private LocalDateTime updateDate;

    @ApiModelProperty(value = "备注")
    private String remark;

    @ApiModelProperty(value = "逻辑删除 0删除，1未删除")
    private Integer delFlag;


}
