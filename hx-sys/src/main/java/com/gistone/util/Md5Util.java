/**  
* Title: Md5Util.java  
* Description: 
* Copyright: © 北京山海础石信息技术有限公司版权所有 
* Company:www.gistone.cn 
* @author xxh  
* @date 2018年7月5日  
* @version 1.0  
*/  
package com.gistone.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;

/**  
* Title: Md5Util 
* Description:   md5
* @author xxh  
* @date 2018年7月5日  
*/
public class Md5Util {

	/**
	 * MD5加密算法
	 * @param s
	 * @return
	 */
	public synchronized final static String md5(String s) {
		 
        char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
                'a', 'b', 'c', 'd', 'e', 'f' };
        try {
            byte[] strTemp = s.getBytes();
            MessageDigest mdTemp = MessageDigest.getInstance("MD5");
            mdTemp.update(strTemp);
            byte[] md = mdTemp.digest();
            int j = md.length;
            char str[] = new char[j * 2];
            int k = 0;
            for (int i = 0; i < j; i++) {
                byte byte0 = md[i];
                str[k++] = hexDigits[byte0 >>> 4 & 0xf];
                str[k++] = hexDigits[byte0 & 0xf];
            }
            return new String(str);
        } catch (Exception e) {
            return null;
        }
    }

    public static void main(String[] args) {
        String qilianshan2019 = md5("qilianshan2019");
        System.out.println(qilianshan2019);
    }
    /***
     * MD5
     * @param
     * @return
     */
    public static String md5Encode(String inStr){
        MessageDigest md5 = null;
        try {
            md5 = MessageDigest.getInstance("MD5");
        } catch (Exception e) {
            System.out.println(e.toString());
            e.printStackTrace();
        }

        try {
            byte[] byteArray = inStr.getBytes("UTF-8");
            byte[] md5Bytes = md5.digest(byteArray);
            StringBuffer hexValue = new StringBuffer();
            for (int i = 0; i < md5Bytes.length; i++) {
                int val = ((int) md5Bytes[i]) & 0xff;
                if (val < 16) {
                    hexValue.append("0");
                }
                hexValue.append(Integer.toHexString(val));
            }
            return hexValue.toString();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return "";
    }
}
