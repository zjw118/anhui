package com.gistone.util;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;

/**
 * 
 * @Title: DateUtil.java
 * @Package com.gistone.util
 * @Description: TODO
 * @author yanglei
 * @date 2017年11月3日 上午10:13:41
 * @version 1.0
 */
public class DateUtil {
	private final static Logger logger = LoggerFactory.getLogger(DateUtil.class);

	/**
	 * 
	 * @Description: TODO
	 * @param str
	 * @return 字符串转化成日期 年月日形式 Date
	 * @throws @author
	 *             yanglei
	 * @date 
	 */
	public static Date StrtoDateYMD(String str) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Date date = null;
		try {
			date = format.parse(str);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return date;
	}

	/**
	 * 
	 * @Description: 字符串转化为时间
	 * @param str
	 * @param pattern
	 * @return Date
	 * @throws @author
	 *             yanglei
	 * @date 
	 */
	public static Date StrtoDateYMD(String str, String pattern) {
		SimpleDateFormat format = new SimpleDateFormat(pattern);
		Date date = null;
		try {
			date = format.parse(str);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return date;
	}

	/* 
     * 将时间戳转换为时间
     * 返回字符串
     * @autor yanglei
     * 
     */
    public static String stampToDate(String s){
        String res;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        long lt = new Long(s);
        Date date = new Date(lt);
        res = simpleDateFormat.format(date);
        return res;
    }
    /* 
     * 将时间戳转换为时间
     * 返回时间
     * @autor yanglei
     */
    public static Date datestampToDate(String s,String pattern){
        String stampToDate = stampToDate(s);
        Date strtoDateYMD = StrtoDateYMD(stampToDate,pattern);
        return strtoDateYMD;
    }
	/**
	 * 
	 * @Description: TODO
	 * @param date
	 * @return String 时间的转化 年月日
	 * @throws @author  yanglei
	 * 
	 */
	public static String DATEtoString(Date date, String pattern) {
		SimpleDateFormat format = new SimpleDateFormat(pattern);
		String str = format.format(date);
		return str;
	}


	/**
	 * 
	 * @Description: 当前日期的变化增减 返回时间
	 * @param date
	 * @return Date
	 * @throws @author
	 *             yanglei
	 * @date 
	 */

	public static Date ChangeDay(Date date, int i) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.DATE, i);
		return cal.getTime();
	}

	/**
	 * @Description: 时间 去掉当前的时分秒增减小时之后返回一个新的时间
	 * @param initDate
	 * @param pattern
	 * @param i
	 * @return Date
	 * @throws @author
	 *             yanglei
	 * @date 
	 */
	public static Date changedateByHour(Date initDate, int i) {
		/*
		 * SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd HH:mm:ss");
		 * String newDatestr = new
		 * SimpleDateFormat("yyyyMMdd").format(initDate); String newString =
		 * newDatestr+" "+"00:00:00"; Date newDate = null; // Date
		 * handleinitDate = format.parse(newString); Calendar cal =
		 * Calendar.getInstance(); // cal.setTime(handleinitDate);
		 * cal.setTime(initDate); cal.add(Calendar.HOUR_OF_DAY, i); newDate =
		 * cal.getTime();
		 */
		// 上面那个是针对传进来的参数没有格式化处理而写的
		Calendar cal = Calendar.getInstance();
		cal.setTime(initDate);
		cal.add(Calendar.HOUR_OF_DAY, i);
		Date newDate = cal.getTime();
		return newDate;
	}

	/**
	 * 
	 * @Description: TODO
	 * @param date
	 * @return String 时间在现有的时间上增减 返回字符串
	 * @throws @author
	 *             yanglei
	 * @date 
	 */

	public static String changeDate(Date date, String pattern, int i) {
		SimpleDateFormat format = new SimpleDateFormat(pattern);
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.DATE, i);
		String newdate = format.format(cal.getTime());
		return newdate;
	}

	/**
	 * 
	 * @Description:字符串转化为时间 并且增减 返回时间
	 * @param date
	 * @param pattern
	 * @param i
	 * @return Date
	 * @throws @author
	 *             yanglei
	 * @date 
	 */
	public static Date changestrToDate(String date, String pattern, int i) {
		SimpleDateFormat format = new SimpleDateFormat(pattern);
		Calendar cal = Calendar.getInstance();
		Date newDate = null;
		try {
			Date tempDate = format.parse(date);
			cal.setTime(tempDate);
			cal.add(Calendar.DATE, i);
			String newdatestring = format.format(cal.getTime());
			newDate = format.parse(newdatestring);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return newDate;
	}


	/**
	 * @Description: 将时间格式化并返回时间
	 * @param date
	 * @param string
	 * @return Date
	 * @throws @author
	 *             yanglei
	 * @date 
	 */
	public static Date DateToDate(Date date, String pattern) {
		SimpleDateFormat format = new SimpleDateFormat(pattern);
		String str = format.format(date);
		Date newdate = null;
		try {
			newdate = format.parse(str);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return newdate;
	}

	/**
	 * 获取年份
	 * 
	 * @param date
	 * @return
	 */
	public static int getYear(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		return c.get(Calendar.YEAR);
	}


	/**
	 * @Description: 获取两个时间的差值  
	 * void  
	 * @throws
	 * @author yanglei
	 * @date 
	 */
	public static int getTimeDifference(Date bigdate1,Date date2) {
		long day = (bigdate1.getTime()-date2.getTime())/(24*60*60*1000);
		int len = Integer.parseInt(String.valueOf(day));
		return len;
	}
	
	public static LocalDate strToLocalDate(String date) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
		LocalDate localDate = LocalDate.parse(date ,formatter);
		return localDate;
	}
	
	public static LocalDateTime strToLocalDateTime(String date) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
		LocalDateTime localDate = LocalDateTime.parse(date ,formatter);
		return localDate;
	}
}
