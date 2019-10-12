package com.gistone.bjhky.util;

import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;

public class MapToBean {

	//map转换为实体对象
	public static void mapToBean(Map<String, Object> map, Object obj) {
        if (map == null || obj == null) {
            return;
        }
        try {
            BeanUtils.populate(obj, map);
        } catch (Exception e) {
            System.out.println("transMap2Bean2 Error " + e);
        }
    }
}
