package com.xawl.car.util;


import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;


/**
 * 
 * ClassName: DateUtil <br/>
 * Function: 时间工具类 . <br/>
 * date: 2016年10月9日 下午5:17:45 <br/>
 * 
 * @author 清算项目组
 * @version 2.0
 * @since JDK 1.7
 */
public class DateUtil {


	/**
	 * 格式化当前系统时间（精确到秒）
	 * 
	 * @return String
	 */
	public static String currentTime() {
		String strYMDHMSS = StringUtils.EMPTY;
		Date currentDateTime = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
		strYMDHMSS = formatter.format(currentDateTime);
		return strYMDHMSS;
	}

	/**
	 * 格式化当前系统时间（精确到毫秒）
	 * 
	 * @return String
	 */
	public static String currentTimeToSS() {
		String strYMDHMSSS = StringUtils.EMPTY;
		Date currentDateTime = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		strYMDHMSSS = formatter.format(currentDateTime);
		return strYMDHMSSS;
	}
	
	/**
     * 获取当前系统时间
     *
     * @param pattern 时间格式
     *
     * @return String
     */
    public static final String getNowDT() {
    	String yyyyMMdd = StringUtils.EMPTY;
		Date currentDateTime = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
		yyyyMMdd = formatter.format(currentDateTime);
		return yyyyMMdd;
    }

	public static String getSqlDate() {
		SimpleDateFormat myFmt1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String format = myFmt1.format(new Date());
		return format;
	}

	public static String getSqlDate2addMouth(int addmouth) {
		Calendar cal = Calendar.getInstance();
		cal.add(2, addmouth);
		SimpleDateFormat myFmt1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String format = myFmt1.format(cal.getTime());
		return format;
	}

	public static boolean compTo(String currentTime, int number)
			throws ParseException {
		SimpleDateFormat myFmt1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		Date current = new Date();
		Date parse = myFmt1.parse(currentTime);
		long time = parse.getTime();
		long time2 = current.getTime();

		return (time2 - time) / 86400L <= number;
	}

	public static int getBetweenDays(String t1) throws ParseException {
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		int betweenDays = 0;
		Date d1 = format.parse(t1);
		String t2 = new Date().toLocaleString();
		Date d2 = format.parse(t2);
		Calendar c1 = Calendar.getInstance();
		Calendar c2 = Calendar.getInstance();
		c1.setTime(d1);
		c2.setTime(d2);

		if (c1.after(c2)) {
			c1 = c2;
			c2.setTime(d1);
		}
		int betweenYears = c2.get(1) - c1.get(1);
		betweenDays = c2.get(6) - c1.get(6);
		for (int i = 0; i < betweenYears; i++) {
			c1.set(1, c1.get(1) + 1);
			betweenDays += c1.getMaximum(6);
		}
		return betweenDays;
	}

	public static int daysBetween(String smdate) throws ParseException {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");

		long from = df.parse(new Date().toLocaleString()).getTime();
		long to = df.parse(smdate).getTime();

		return (int) ((to - from) / 86400000L);
	}

}
