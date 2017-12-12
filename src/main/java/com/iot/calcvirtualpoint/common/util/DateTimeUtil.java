package com.iot.calcvirtualpoint.common.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 
 * @author kf.wei
 * @version 1.0
 * @since 日期格式转换类
 *
 */
public class DateTimeUtil {

	/**
	* 根据传入的时间格式返回系统日期
	* @param src
	* @return Date
	*/
	public static String getServerTime(String fmt) {
		SimpleDateFormat format = new SimpleDateFormat(fmt);
		return format.format(new Date());
	}

	/**
	 * 根据传入的字符串返回Date，格式为"yyyy-MM-dd"
	 * @param src
	 * @return Date
	 */
	public static Date parseDate(String src) {
		return parse(src, "yyyy-MM-dd");
	}

	/**
	 * 根据传入的字符串返回Date，格式为"yyyy-MM-dd HH:mm:ss"
	 * @param src
	 * @return Date
	 */
	public static Date parseDatetime(String src) {
		return parse(src, "yyyy-MM-dd HH:mm:ss");
	}

	/**
	 * 根据传入的字符串返回Date，格式为"yyyy-MM-dd HH:mm:ss SSS"
	 * @param src
	 * @return Date
	 */
	public static Date parseDatetime1(String src) {
		return parse(src, "yyyy-MM-dd HH:mm:ss SSS");
	}

	/**
	 * 根据传入的日期字符串和格式返回Date
	 * @param src
	 * @param pattern
	 * @return Date
	 */
	public static Date parse(String src, String pattern) {
		if (pattern == null)
			throw new IllegalArgumentException("日期格式不能为空");
		if (src == null)
			return null;
		try {
			return new SimpleDateFormat(pattern).parse(src);
		} catch (ParseException ex) {
			throw new IllegalArgumentException("日期格式转换出错,src=" + src + ",pattern=" + pattern);
		}
	}

	/**
	 * 格式化日期为yyyy-MM-dd
	 * @param src
	 * @return String
	 */
	public static String formatDate(Date src) {
		return format(src, "yyyy-MM-dd");
	}

	/**
	 * 格式化日期为yyyyMMdd
	 * @param src
	 * @return String
	 */
	public static String formatDate_nodash(Date src) {
		String s = format(src, "yyyy-MM-dd");
		if (null != s && s.length() > 0) {
			s = s.replace("-", "");
		}
		return s;
	}

	/**
	 * 格式化日期为HHmmss
	 * @param src
	 * @return String
	 */
	public static String formatNot_Time(Date src) {
		return format(src, "HHmmss");
	}

	/**
	 * 格式化日期为HH:mm:ss
	 * @param src
	 * @return String
	 */
	public static String formatTime(Date src) {
		return format(src, "HH:mm:ss");
	}

	/**
	 * 格式化日期为HH:00:00
	 * @param src
	 * @return String
	 */
	public static String formatTime1(Date src) {
		return format(src, "HH:00:00");
	}

	/**
	 * 格式化日期为yyyyMMdd
	 * @param src
	 * @return String
	 */
	public static String formatNot_Date(Date src) {
		return format(src, "yyyyMMdd");
	}

	/**
	 * 格式化日期为yyyyMM
	 * @param src
	 * @return String
	 */
	public static String formatNot_M(Date src) {
		return format(src, "yyyyMM");
	}

	/**
	 * 格式化日期为yyyy-MM-dd HH:mm:ss
	 * @param src
	 * @return String
	 */
	public static String formatDatetime(Date src) {
		return format(src, "yyyy-MM-dd HH:mm:ss");
	}

	/**
	 * 获取当天日期字符串
	 * @param src
	 * @return String
	 */
	public static String nowdate(String src) {
		return today(src, "yyyy-MM-dd");
	}

	/**
	 * 获取当天日期+时间字符串
	 * @param src
	 * @return String
	 */
	public static String nowdatetime(String src) {
		return today(src, "yyyy-MM-dd HH:mm:ss SSS");
	}

	/**
	 * 根据传入的日期字符串和格式化pattern返回指定格式化的日期字符串
	 * @param src
	 * @param pattern
	 * @return String
	 */
	public static String today(String src, String pattern) {
		if (pattern == null)
			throw new IllegalArgumentException("日期格式不能为空");
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		String dt = sdf.format(parseDatetime1(src));
		return dt;
	}

	/**
	 * 格式化日期为日历时间
	 * @param src
	 * @return Date
	 */
	public static Date formatSysDate(Date src) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(src);

		Calendar currCalendar = Calendar.getInstance();
		currCalendar.setTime(new Date());
		currCalendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));

		return currCalendar.getTime();
	}

	/**
	 * 按指定格式格式化传入日期并返回
	 * @param src
	 * @param pattern
	 * @return String
	 */
	public static String format(Date src, String pattern) {
		if (pattern == null)
			throw new IllegalArgumentException("日期格式不能为空");
		if (src == null)
			return null;
		return new SimpleDateFormat(pattern).format(src);
	}

	/**
	 * 根据日历判断是否为同一天
	 * @param cal1
	 * @param cal2
	 * @return boolean
	 */
	public static boolean isSameDay(Calendar cal1, Calendar cal2) {
		if (cal1 == null || cal2 == null)
			return false;
		return (cal1.get(Calendar.ERA) == cal2.get(Calendar.ERA) && cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR) && cal1.get(Calendar.DAY_OF_YEAR) == cal2.get(Calendar.DAY_OF_YEAR));
	}

	/**
	 * 根据Date判断是否为同一天
	 * @param date1
	 * @param date2
	 * @return boolean
	 */
	public static boolean isSameDay(Date date1, Date date2) {
		if (date1 == null || date2 == null)
			return false;
		Calendar cal1 = Calendar.getInstance();
		cal1.setTime(date1);
		Calendar cal2 = Calendar.getInstance();
		cal2.setTime(date2);
		return isSameDay(cal1, cal2);
	}

	/**
	 * 根据Date判断时分是否相等
	 * @param date1
	 * @param date2
	 * @return boolean
	 */
	public static boolean isSameInstant(Date date1, Date date2) {
		if (date1 == null || date2 == null)
			return false;
		return date1.getTime() == date2.getTime();
	}

	/**
	 * 根据日历判断时分是否相等
	 * @param cal1
	 * @param cal2
	 * @return boolean
	 */
	public static boolean isSameInstant(Calendar cal1, Calendar cal2) {
		if (cal1 == null || cal2 == null)
			return false;
		return cal1.getTime().getTime() == cal2.getTime().getTime();
	}

	/**
	 * 增加年
	 * @param date
	 * @param amount
	 * @return Date
	 */
	public static Date addYears(Date date, int amount) {
		return add(date, Calendar.YEAR, amount);
	}

	/**
	 * 增加月
	 * @param date
	 * @param amount
	 * @return Date
	 */
	public static Date addMonths(Date date, int amount) {
		return add(date, Calendar.MONTH, amount);
	}

	/**
	 * 增加周
	 * @param date
	 * @param amount
	 * @return Date
	 */
	public static Date addWeeks(Date date, int amount) {
		return add(date, Calendar.WEEK_OF_YEAR, amount);
	}

	/**
	 * 增加天
	 * @param date
	 * @param amount
	 * @return Date
	 */
	public static Date addDays(Date date, int amount) {
		return add(date, Calendar.DAY_OF_MONTH, amount);
	}

	/**
	 * 增加小时
	 * @param date
	 * @param amount
	 * @return Date
	 */
	public static Date addHours(Date date, int amount) {
		return add(date, Calendar.HOUR_OF_DAY, amount);
	}

	/**
	 * 增加分钟
	 * @param date
	 * @param amount
	 * @return Date
	 */
	public static Date addMinutes(Date date, int amount) {
		return add(date, Calendar.MINUTE, amount);
	}

	/**
	 * 增加秒
	 * @param date
	 * @param amount
	 * @return Date
	 */
	public static Date addSeconds(Date date, int amount) {
		return add(date, Calendar.SECOND, amount);
	}

	/**
	 * 增加毫秒
	 * @param date
	 * @param amount
	 * @return Date
	 */
	public static Date addMilliseconds(Date date, int amount) {
		return add(date, Calendar.MILLISECOND, amount);
	}

	/**
	 * 根据日历选项calendarField增加
	 * @param date
	 * @param calendarField
	 * @param amount
	 * @return Date
	 */
	public static Date add(Date date, int calendarField, int amount) {
		if (date == null)
			throw new IllegalArgumentException("日期不能为空");

		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(calendarField, amount);
		return c.getTime();
	}

	public static int getLastDayOfMonth(int y, int m) {
		boolean IsLeapYear = (y % 4 == 0) && (y % 100 != 0) || (y % 400 == 0);
		int days = 0;
		switch (m) {
		case 2:
			if (IsLeapYear) {
				days = 29;
			} else
				days = 28;
			break;
		case 4:
		case 6:
		case 9:
		case 11:
			days = 30;
			break;
		default:
			days = 31;
			break;
		}
		return days;
	}

	/**
	 * 中文星期几
	 * @return
	 */
	public static String getCnWeekDay() {
		return getCnWeekDay(new Date());
	}

	/**
	 * 中文星期几
	 * @return
	 */
	public static String getCnWeekDay(Date date) {
		String[] weekDays = { "日", "一", "二", "三", "四", "五", "六" };
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
		if (w < 0)
			w = 0;
		return "星期" + weekDays[w];
	}

	/**
	 * 
	 * 功能：计算两个任意时间中间的间隔天数
	 * 参数：Date startday,Date endday  ===> 传进Date对象
	 * 作者：kf.wei
	 * 时间：2011-5-7下午09:08:13
	 */
	public static int getIntervalDays(Date startday, Date endday) {
		if (startday.after(endday)) {
			Date cal = startday;
			startday = endday;
			endday = cal;
		}
		long sl = startday.getTime();
		long el = endday.getTime();
		long ei = el - sl;
		return (int) (ei / (1000 * 60 * 60 * 24));
	}

	/**
	 * 
	 * 功能：计算两个任意时间中间的间隔天数
	 * 参数：传进Calendar对象
	 * 作者：kf.wei
	 * 时间：2011-5-7下午09:09:09
	 */
	public static int getIntervalDays(Calendar startday, Calendar endday) {
		if (startday.after(endday)) {
			Calendar cal = startday;
			startday = endday;
			endday = cal;
		}
		long sl = startday.getTimeInMillis();
		long el = endday.getTimeInMillis();
		long ei = el - sl;
		return (int) (ei / (1000 * 60 * 60 * 24));
	}

	/**
	 * 
	 * 功能：取得当前的月份
	 * 参数：传进Date对象
	 * 作者：kf.wei
	 * 时间：2011-5-7下午09:09:09
	 */
	@SuppressWarnings("static-access")
	public static int getMonth() {
		Calendar c = Calendar.getInstance();
		c.setTime(new Date());
		return c.get(c.MONTH) + 1;
	}

	/**
	 * 
	 * 功能：根据月份取得时间
	 * 参数：传进月份int值
	 * 作者：kf.wei
	 * 时间：2011-5-7下午09:09:09
	 */
	@SuppressWarnings("static-access")
	public static String monthToDate(int month) {
		Calendar c = Calendar.getInstance();
		// 月份大于当前月则认为是去年的那个月
		if (month > getMonth()) {
			c.add(c.YEAR, -1);
		}
		c.set(c.MONTH, month - 1);
		return format(c.getTime(), "yyyyMM");
	}

	/**
	 * 
	 * 功能：获取当前年份YYYY格式
	 * 作者：kf.wei
	 * 时间：2011-5-7下午09:09:09
	 */
	public static String getNowYear() {
		Calendar c = Calendar.getInstance();
		return format(c.getTime(), "yyyy");
	}

	/**
	 * 功能：获取当前日格式dd
	 * 作者：kf.wei
	 * 时间：2011-5-7下午09:09:09
	 */
	public static String getDay() {
		Calendar c = Calendar.getInstance();
		return format(c.getTime(), "yyyyMMdd");
	}

	/**
	 * 功能：获取当前系统日期
	 * 作者：
	 * 时间：
	 */
	public static Date getSysDate() {
		return new Date();
	}

	/**
	 * 把yyyy-MM-dd HH:mm:ss时间字符串转换成pattern格式的字符串
	 * @param datetime
	 * @param pattern
	 * @return
	 */
	public static String getDateTimeString(String datetime, String pattern) {
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			Date date = sf.parse(datetime);
			return new SimpleDateFormat(pattern).format(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 获取传入日期的上个 00分、15分、30分、45分，并返回;
	 * 参数格式必须是 yyyy/MM/dd HH:mm:ss
	 * @param dateString
	 * @return
	 */
	public static String getLast15MinutesDateByDate(String dateString) { 
		Calendar calendar = Calendar.getInstance();
		SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		try {
			calendar.setTime(df.parse(dateString));
			int minute = calendar.get(Calendar.MINUTE);// 得到分钟
			int second = calendar.get(Calendar.SECOND);// 得到秒
			int count = 0;
			if (minute != 0 && minute != 15 && minute != 30 && minute != 45) { // 时间不为00，15，30，45的情况
				for (int i = minute; i >= 0; i--) { // 判断距离最近的00，15，30，45的分钟数
					if (i % 15 == 0) {
						break;
					} else {
						count++;
					}
				}
			}else{
				if(second == 0){
					count = 15;
				}
			}
			calendar.add(Calendar.MINUTE, -count); // 获取最近的分钟
			int year = calendar.get(Calendar.YEAR); // 得到年
			int month = calendar.get(Calendar.MONTH); // 得到月
			int day = calendar.get(Calendar.DAY_OF_MONTH);// 得到天
			int hour = calendar.get(Calendar.HOUR_OF_DAY);// 得到小时
			int min = calendar.get(Calendar.MINUTE);// 得到分钟
			calendar.set(year, month, day, hour, min, 0);
			return df.format(calendar.getTime());
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 根据传入的字符串返回Date，格式为"yyyy/MM/dd HH:mm:ss"
	 * @param src
	 * @return Date
	 */
	public static Date parseDatetime(String src, String pattern) {
		return parse(src, pattern);
	}
	
	public static void main(String[] args) {
		String str = getLast15MinutesDateByDate("2017/07/07 17:51:34");
		System.out.println(str);
	}
}
