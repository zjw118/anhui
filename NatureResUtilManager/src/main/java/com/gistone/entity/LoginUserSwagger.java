package com.gistone.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @ClassName LoginUserSwagger
 * @Description TODO
 * @Author xxh
 * @Date 2019/7/22 11:07
 * @Version 1.0
 **/
@Data
@ApiModel(value = "登陆参数", description = "登陆参数")
public class LoginUserSwagger {

    @ApiModelProperty(value = "用户登录账号", required = true, example = "xxh")
    private String username;

    @ApiModelProperty(value = "用户登录密码", required = true, example = "123456")
    private String password;

    @ApiModelProperty(value = "用户登录验证码", required = true, example = "abcd")
    private String kaptcha;

    @ApiModelProperty(value = "登录类型-手机端1/pc端0", required = true, example = "0")
    private Integer type;
}
