package com.gistone.feign;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author zf1017@foxmail.com
 * @date 2019/5/13 0013 9:58
 * @description
 */
public interface KtdbFeign {
    @RequestMapping("/lmMarkerMobile/getLevel")
    Integer getLevel(@RequestParam("code") String code);
}
