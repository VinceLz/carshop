package com.xawl.car.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {

	public static String getSqlDate() {
		SimpleDateFormat myFmt1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String format = myFmt1.format(new Date());
		return format;
	}

	public static String getSqlDate2addMouth(int addmouth) {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.MONTH, addmouth);
		SimpleDateFormat myFmt1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String format = myFmt1.format(cal.getTime());
		return format;
	}

	public static boolean compTo(String currentTime, int number)
			throws ParseException {
		SimpleDateFormat myFmt1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		Date current = new java.util.Date();
		Date parse = myFmt1.parse(currentTime);
		long time = parse.getTime();
		long time2 = current.getTime();
		if ((time2 - time) / 86400 <= number) {
			return true;
		} else {
			return false;
		}
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
		// 保证第二个时间一定大于第一个时间
		if (c1.after(c2)) {
			c1 = c2;
			c2.setTime(d1);
		}
		int betweenYears = c2.get(Calendar.YEAR) - c1.get(Calendar.YEAR);
		betweenDays = c2.get(Calendar.DAY_OF_YEAR)
				- c1.get(Calendar.DAY_OF_YEAR);
		for (int i = 0; i < betweenYears; i++) {
			c1.set(Calendar.YEAR, (c1.get(Calendar.YEAR) + 1));
			betweenDays += c1.getMaximum(Calendar.DAY_OF_YEAR);
		}
		return betweenDays;
	}

	/**
	 * 计算两个日期之间相差的天数
	 * 
	 * @param smdate
	 *            较小的时间
	 * @param bdate
	 *            较大的时间
	 * @return 相差天数
	 * @throws ParseException
	 */

	public static int daysBetween(String smdate) throws ParseException {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");

		long from = df.parse(new Date().toLocaleString()).getTime();
		long to = df.parse(smdate).getTime();

		return (int) ((to - from) / (1000 * 60 * 60 * 24));
	}

}
