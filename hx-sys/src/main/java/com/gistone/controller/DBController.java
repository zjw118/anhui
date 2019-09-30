package com.gistone.controller;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/sys/db")
public class DBController {
    @RequestMapping("/")
    public Object getDBFile(@RequestBody Map<String, Object> paramsMap){
        //1.拿到数据库版本号
        Map<String, Object> param = (Map<String, Object>) paramsMap.get("data");
        String Version = (String) param.get("version");
        //2.取出最新的版本号

        //3.然后作比较，如果是最新的，告知用户，否则，返回最新的记录
        return null;
    }
}
