package com.gistone.pkname;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @ClassName Pkid
 * @Description TODO
 * @Author xxh
 * @Date 2019/8/12 13:52
 * @Version 1.0
 **/
@Data
@ApiModel(value="PrimaryKey对象", description="删除及根据主键id查询封装对象")
public class PrimaryKey {
    @ApiModelProperty(value = "唯一主键")
    private Integer primaryKey;

}
