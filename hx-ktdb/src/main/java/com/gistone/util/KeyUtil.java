package com.gistone.util;

import java.util.Random;

/**
 * @author zf1017@foxmail.com
 * @date 2019/5/14 0014 14:11
 * @description
 */
public class KeyUtil {
    /**
     * 生成唯一的主键
     * 格式: 时间+随机数
     * @return
     */
    public static synchronized String genUniqueKey() {
        Random random = new Random();
        Integer number = random.nextInt(900000) + 100000;

        return System.currentTimeMillis() + String.valueOf(number);
    }
}
