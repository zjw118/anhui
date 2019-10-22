package com.gistone.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 巡查人员实时位置表
 * </p>
 *
 * @author zjw
 * @since 2019-08-22
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="St4ScsCh对象", description="巡查人员实时位置表")
public class St4ScsCh extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "巡查人员实时位置表主键")
    @TableId(value = "CH001", type = IdType.AUTO)
    private Integer ch001;

    @ApiModelProperty(value = "巡查人员ID")
    @TableField("CH002")
    private Integer ch002;

    @ApiModelProperty(value = "经度")
    @TableField("CH003")
    private String ch003;

    @ApiModelProperty(value = "纬度")
    @TableField("CH004")
    private String ch004;

    @ApiModelProperty(value = "人员状态 0离线 1在线 2正在巡护中")
    @TableField("CH005")
    private Integer ch005;
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "时间")
    @TableField("CH006")
    private LocalDateTime ch006;


}
