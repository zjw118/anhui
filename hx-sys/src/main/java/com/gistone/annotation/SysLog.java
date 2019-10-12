package com.gistone.annotation;

import java.lang.annotation.*;

/**
 * @author zf1017@foxmail.com
 * @date 2019/10/11 0011 14:42
 * @description 系统日志注解
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface SysLog {
    String value() default "";
}
