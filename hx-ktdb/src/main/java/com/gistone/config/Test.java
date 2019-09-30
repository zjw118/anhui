package com.gistone.config;

import com.gistone.util.DateUtils;

import java.time.LocalDate;
import java.util.Date;

/**
 * @author zf1017@foxmail.com
 * @date 2019/6/18 0018 18:04
 * @description
 */
public class Test {
    public static void main(String[] args) {
        Date date = new Date();
        System.out.println(date);
        String format = DateUtils.format(date);
        System.out.println(format);
        LocalDate date1 = LocalDate.now();
        System.out.println(date1);
    }
}
