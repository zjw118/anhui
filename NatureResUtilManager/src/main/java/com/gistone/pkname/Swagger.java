package com.gistone.pkname;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.Valid;

@ApiModel(value="所有接口的外层data对象", description="")
public class Swagger<T> {
@Valid
    @ApiModelProperty(value = "实体类", required = true,example="")
    public T data;

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
