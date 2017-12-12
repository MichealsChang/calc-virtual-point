package com.iot.calcvirtualpoint.util;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DateUtil {

	/**
	 * 获取当月第一天的0点开始时间点
	 * @return
	 */
	public static Date getTimesMonthmorning() {
		Calendar cal = Calendar.getInstance();
		cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONDAY), cal.get(Calendar.DAY_OF_MONTH), 0, 0, 0);
		cal.set(Calendar.DAY_OF_MONTH, cal.getActualMinimum(Calendar.DAY_OF_MONTH));
		return cal.getTime();
	}

	/**
	 * 获取当前时间的下一个整点
	 * @param timestamp
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public static Timestamp getNextIntegralPoint(Timestamp timestamp) {
		Calendar calendar = Calendar.getInstance();
		calendar.set(timestamp.getYear() + 1900, timestamp.getMonth(), timestamp.getDate(), timestamp.getHours() + 1, 0, 0);
		timestamp = new Timestamp(calendar.getTime().getTime());
		return timestamp;
	}

	@SuppressWarnings("deprecation")
	public static String getNextIntegralPointString(Timestamp timestamp) {
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Calendar calendar = Calendar.getInstance();
		if (timestamp.getHours() == 23) {
			calendar.set(timestamp.getYear() + 1900, timestamp.getMonth(), timestamp.getDate() + 1, 0, 0, 0);
		} else {
			calendar.set(timestamp.getYear() + 1900, timestamp.getMonth(), timestamp.getDate(), timestamp.getHours() + 1, 0, 0);
		}
		Date date = new Date(calendar.getTimeInMillis());
		return sf.format(date);
	}

	/**
	 * 获取下一天的的零点
	 * @param timestamp
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public static Timestamp getNextDayZeroPoint(Timestamp timestamp) {
		Calendar calendar = Calendar.getInstance();
		calendar.set(timestamp.getYear() + 1900, timestamp.getMonth(), timestamp.getDate() + 1, 0, 0, 0);
		timestamp = new Timestamp(calendar.getTime().getTime());
		return timestamp;
	}

	/**
	 * 获取传入的时间到当天零点的分钟数
	 * @param mills
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public static long getCurrentTimeToZeroPointMinites(long mills) {
		Date date = new Date(mills);
		Calendar calendar = Calendar.getInstance();
		calendar.set(date.getYear() + 1900, date.getMonth(), date.getDate(), 0, 0, 0);
		long zeroTime = calendar.getTimeInMillis();
		long result = (mills - zeroTime) / 1000 / 60;
		return result;
	}

	/**
	 * 获取当前时间的年月
	 * @return
	 * @throws ParseException 
	 */
	public static String getDateMonth() throws ParseException {
		Date d = new Date();
		System.out.println(d);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
		String dateNowStr = sdf.format(d);
		return dateNowStr;
	}

	/**
	 * 将字符串时间转成年月
	 * @param dataDate
	 * @return
	 */
	public static String getDateMonth(String dataDate) {
		SimpleDateFormat sim = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat str = new SimpleDateFormat("yyyy-MM");// 时间格式
		try {
			Date d = sim.parse(dataDate);
			String string = str.format(d);
			return string;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "";
	}

	/**
	 * 获取传入的时间到当月零点的分钟数
	 * @param mills
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public static long getCurrentMonthToMonthZeroPointMinites(long mills) {
		Date date = new Date(mills);
		Calendar calendar = Calendar.getInstance();
		calendar.set(date.getYear() + 1900, date.getMonth(), 1, 0, 0, 0);
		long zeroTime = calendar.getTimeInMillis();
		long result = (mills - zeroTime) / 1000 / 60;
		return result;
	}

	/**
	 * 校验日期字符串是否是日期格式
	 * @param dateString
	 * @return
	 */
	public static boolean validateDateString(String dateString) {
		Pattern pattern = Pattern.compile("^((\\d{2}(([02468][048])|([13579][26]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])))))|(\\d{2}(([02468][1235679])|([13579][01345789]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|(1[0-9])|(2[0-8]))))))(\\s(((0?[0-9])|([1-2][0-3]))\\:([0-5]?[0-9])((\\s)|(\\:([0-5]?[0-9])))))?$");
		Matcher m = pattern.matcher(dateString);
		if (m.matches()) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 字符串日期转换成Date对象
	 * @param dateString
	 * @return
	 */
	public static Date getDate(String dateString) {
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
		try {
			Date date = sf.parse(dateString);
			return date;
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 获取传入日期时间的上一个整点
	 * @param date
	 */
	@SuppressWarnings("deprecation")
	public static Date getLastIntegralPoint(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.set(date.getYear() + 1900, date.getMonth(), date.getDate(), date.getHours(), 0, 0);
		return calendar.getTime();
	}

}
