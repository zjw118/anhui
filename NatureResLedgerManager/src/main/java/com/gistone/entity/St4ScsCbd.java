package com.gistone.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * <p>
 * 移动端提交检测表
 * </p>
 *
 * @author zjw
 * @since 2019-11-26
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="St4ScsCbd对象", description="移动端提交检测表")
public class St4ScsCbd extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "移动端提交检测表主键")
    @TableId(value = "CBD001", type = IdType.AUTO)
    private Integer cbd001;

    @ApiModelProperty(value = "操作人")
    @TableField("CBD002")
    private String cbd002;
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "操作时间")
    @TableField("CBD003")
    private LocalDateTime cbd003;

    @ApiModelProperty(value = "问题斑块ID")
    @TableField("CBD004")
    private Integer cbd004;

    @ApiModelProperty(value = "推送内容中间内容")
    @TableField("CBD005")
    private String cbd005;

    @ApiModelProperty(value = "推送内容后缀内容")
    @TableField("CBD006")
    private String cbd006;


}
