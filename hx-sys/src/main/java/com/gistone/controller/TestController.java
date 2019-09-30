package com.gistone.controller;


import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController

public class TestController {
   /* @Value("${hello}")
    private String hello;

    @RequestMapping("/hello")
    public String from() {
        return this.hello;
    }*/

   @RequestMapping("/testList")
    public String testList(@RequestParam(value = "list") List<String> list){
       return list.get(0);
   }

}
