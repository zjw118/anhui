package com.gistone.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zf1017@foxmail.com
 * @date 2019/10/21 0021 17:01
 * @description
 */
@RestController
public class TestController {
    @RequestMapping("/hello")
    public String hello(){
        return "hello";
    }

}
