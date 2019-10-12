package com.gistone.bjhky.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidDataFormatUtil {

	/**
	 * 验证时间格式是否正确
	 * @param str 时间
	 * @param format 时间格式
	 * @return
	 */
	public static boolean isValidDate(String str,String format) {
	      boolean convertSuccess=true;
	       SimpleDateFormat sdf = new SimpleDateFormat(format);
	       try {
	    	   sdf.setLenient(false);
	    	   sdf.parse(str);
	       } catch (ParseException e) {
	           convertSuccess=false;
	       } 
	       return convertSuccess;
	}
	
	/**
	 * 验证字符串是否由数字组成
	 * @param str
	 * @return
	 */
	public static boolean isValidNumber(String str){
		if(str!=null&&!"".equals(str)){
			boolean result=str.matches("[0-9]+");
			return result;
		}else{
			return false;
		}
	}
	
	/**
	 * 判断数据是否为浮点型
	 * @param str
	 * @return
	 */
	private static boolean isValidDouble(String str) {  
	    if (null == str || "".equals(str)) {  
	        return false;  
	    }
	    Pattern pattern = Pattern.compile("^[-\\+]?[.\\d]*$");  
	    return pattern.matcher(str).matches();  
	}
	
	/**
	 * 手机号验证
	 * @param str
	 * @return
	 */
	public static boolean isMobile(final String str) {  
	     Pattern p = null;  
	     Matcher m = null;  
	     boolean b = false;  
	     p = Pattern.compile("^[1][3,4,5,7,8][0-9]{9}$"); // 验证手机号  
	     m = p.matcher(str);  
	     b = m.matches();  
	     return b;  
	}  
	
	/**
	 * 电话号码验证
	 * @param str
	 * @return
	 */
	public static boolean isPhone(final String str) {  
	     Pattern p1 = null, p2 = null;  
	     Matcher m = null;  
	     boolean b = false;  
	     p1 = Pattern.compile("^[0][1-9]{2,3}-[0-9]{5,10}$");  // 验证带区号的  
	     p2 = Pattern.compile("^[1-9]{1}[0-9]{5,8}$");         // 验证没有区号的  
	     if (str.length() > 9) {  
	        m = p1.matcher(str);  
	        b = m.matches();  
	     } else {  
	         m = p2.matcher(str);  
	        b = m.matches();  
	     }  
	     return b;  
	 }
	
	/**
	 * 验证密码格式
	 * @param str
	 * @return
	 */
	public static boolean isPassword(String str) {
		Pattern p = null;  
	    Matcher m = null;  
	    boolean b = false;  
	    p = Pattern.compile("^[A-Za-z0-9]{6,10}$");  
	    m = p.matcher(str);  
	    b = m.matches();  
	    return b;
	}
}
