package com.gistone.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;

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
 * 边界数据上传资料附件表(江苏用)
 * </p>
 *
 * @author zjw
 * @since 2019-11-16
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="St4ScsCbc对象", description="边界数据上传资料附件表(江苏用)")
public class St4ScsCbc implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "边界数据上传资料附件表主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "原始名称")
    private String orignName;

    @ApiModelProperty(value = "下载url")
    private String uploadUrl;

    @ApiModelProperty(value = "预览url")
    private String previewUrl;

    @ApiModelProperty(value = "边界数据表ID")
    private Integer pid;

    @ApiModelProperty(value = "添加人")
    private Integer addUser;

    @ApiModelProperty(value = "添加时间")
    private LocalDateTime addTime;

    @ApiModelProperty(value = "1未删除 0未删除")
    private Integer isDel;


}
