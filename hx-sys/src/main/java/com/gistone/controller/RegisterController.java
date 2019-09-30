package com.gistone.controller;


import com.gistone.entity.SysUser;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * 用户注册控制器
 */
@RestController
public class RegisterController {
    @RequestMapping("/register")
    public  Object reginster(@Valid SysUser sysUser){

        return null;
    }
}
