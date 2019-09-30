package com.gistone.util;

import java.util.List;

/**
 * @author zf1017@foxmail.com
 * @date 2019/6/10 0010 11:08
 * @description
 */
public class ListUtil {
    private static Boolean isNull(List T){
        if(T!=null&&T.size()>0){
            return  true;
        }
        return false;
    }
}
