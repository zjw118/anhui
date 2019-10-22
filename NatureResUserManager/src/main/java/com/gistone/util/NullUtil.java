package com.gistone.util;

public class NullUtil {

	/**
	 * 判断变量是否为空，为空返回false
	 * 
	 * @param obj
	 * @return
	 */
	public static boolean isnull(String obj) {
		if (obj != null && !obj.equals("")) {
			return true;
		}
		return false;
	}
	
	public static boolean isnull(Object obj) {
		if (obj != null && !obj.equals("")) {
			return true;
		}
		return false;
	}
	
	public static boolean isnull(Integer obj) {
		if (obj != null && !obj.equals("")) {
			return true;
		}
		return false;
	}
}
