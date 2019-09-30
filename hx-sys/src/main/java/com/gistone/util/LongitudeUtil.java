package com.gistone.util;

/**
 * @author zf1017@foxmail.com
 * @date 2019/9/16 0016 13:51
 * @description
 */
public class LongitudeUtil {
    public static String dblToLocation(double data) {
        StringBuffer result = new StringBuffer();
        //得到度
        int du = (int) data;
        result = result.append(String.valueOf(du) + "°");
        //得到分
        double du1 = data - du;
        int min = (int) (du1 * 60);
        result = result.append(String.valueOf(min) + "′");
        //得到秒
        double min1 = du1 * 60 - min;
        int sec = (int) (min1 * 60);
        result = result.append(String.valueOf(sec) + "″");
        return result.toString();
    }

    public static void main(String[] args) {
        Double d =116.37032;
        String s = LongitudeUtil.dblToLocation(d);
        System.out.println(s);
    }

}
