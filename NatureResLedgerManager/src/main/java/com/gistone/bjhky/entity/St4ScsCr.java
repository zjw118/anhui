package com.gistone.bjhky.entity;

import com.baomidou.mybatisplus.annotation.IdType;
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
 * 
 * </p>
 *
 * @author xxh
 * @since 2019-08-14
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="St4ScsCr对象", description="")
public class St4ScsCr implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "问题类型表唯一主键")
    @TableId(value = "CR001", type = IdType.AUTO)
    private Integer cr001;

    @ApiModelProperty(value = "问题类型")
    @TableField("CR002")
    private String cr002;


}
