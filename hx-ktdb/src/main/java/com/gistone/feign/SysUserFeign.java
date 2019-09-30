package com.gistone.feign;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

/**
 * @author zf1017@foxmail.com
 * @date 2019/5/13 0013 9:58
 * @description
 */
public interface SysUserFeign {
    @RequestMapping(value = "/sysUser/getByPhone",method = RequestMethod.POST)
    Map getByPhone(@RequestParam(value = "phone") String phone);

    @PostMapping("/testList")
    String testList(@RequestParam(value = "list") List<String> list);
}
