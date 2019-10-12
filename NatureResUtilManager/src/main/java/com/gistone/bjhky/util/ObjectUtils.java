package com.gistone.bjhky.util;

/**
 * 判断非空的字符串及对象
 */
public class ObjectUtils {

    public static boolean isNotNullAndEmpty(Object obj){
        if(obj==null){
            return false;
        }
        if("".equals(obj.toString().trim())){
            return false;
        }
        return true;
    }
    /**
     * 对象是否为数组对象
     *
     * @param obj 对象
     * @return 是否为数组对象，如果为{@code null} 返回false
     */
    public static boolean isArray(Object obj) {
        if (null == obj) {
//            throw new NullPointerException("Object check for isArray is null");
            return false;
        }
//        反射 获得类型
        return obj.getClass().isArray();
    }
}
