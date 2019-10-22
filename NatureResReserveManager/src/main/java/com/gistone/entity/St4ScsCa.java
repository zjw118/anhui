package com.gistone.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

/**
 * <p>
 * 保护地类型表
 * </p>
 *
 * @author LiuXiong
 * @since 2019-08-15
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="St4ScsCa对象", description="保护地类型表")
public class St4ScsCa implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "唯一主键")
    @TableId(value = "CA001", type = IdType.AUTO)
    private Integer ca001;

    @ApiModelProperty(value = "保护地类型名称")
    @TableField("CA002")
    private String ca002;
    /**
     * 保护地数据对象
     */
    @ApiModelProperty(name="reserveData",value="保护地数据对象集合",dataType = "List",required = false,example="")
    @TableField(exist = false)
    private List<St4SysSg> reserveData;

}
