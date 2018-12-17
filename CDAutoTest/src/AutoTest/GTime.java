package AutoTest;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class GTime {
	/**
	 * @see 不同格式的日期字符串
	 */
	public static final String FORMAT_14 = "yyyyMMddHHmmss";
	public static final String FORMAT_14_TEXT = "yyyy-MM-dd HH:mm:ss";
	public static final String FORMAT_8 = "yyyyMMdd";
	public static final String FORMAT_8_TEXT = "yyyy-MM-dd";
	public static final String FORMAT_6 = "HHmmss";
	public static final String FORMAT_6_TEXT = "HH:mm:ss";

	/**
	 * @see 判断是否为空
	 */
	public static boolean isEmpty(String str) {
		return (str == null || str.trim().length() == 0);
	}

	/**
	 * @see 按照Date类型的日期和格式名称获日期字符串
	 */
	public static String getTimeInFormat(Date date, String format) {
		return new SimpleDateFormat(format).format(date);
	}

	/**
	 * @see 按照String类型的日期和格式名称获日期字符串
	 */
	public static Date getDateInFormat(String time, String format) throws ParseException {
		return new SimpleDateFormat(format).parse(time);
	}

	/**
	 * @see 按照格式名称获日期字符串
	 */
	public static String getCurrentTime(String format) {
		return getTimeInFormat(new Date(), format);
	}

	/**
	 * @see 获得Date格日期
	 */
	public static Date getOffsetDate(Date date, int field, int amount) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(field, amount);
		return calendar.getTime();
	}

	/**
	 * @see 根据部分日期字符串获取全日期字符串
	 */
	public static String getChangedFormat(String time, String fromFormat, String toFormat) throws ParseException {
		return new SimpleDateFormat(toFormat).format(new SimpleDateFormat(fromFormat).parse(time));
	}

	/**
	 * @see 获取yyyy/MM/dd HH:mm:ss格式的日期字符串
	 */
	public static String getDate() {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Date now = new Date();
		String curDate = "";
		curDate = dateFormat.format(now);
		return curDate;
	}
}
