package Base;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;

import Sys.GSys;

/**
 *  时间管理
 */
public class GTime {
	
    /**
     *  构造函数
     */
	private GTime(){
		GClazz.thisAToolClass();
	}
	
	/**
	 *  不同格式的日期字符串
	 */
	public static final String FORMAT_14 = "yyyyMMddHHmmss";
	public static final String FORMAT_14_TEXT = "yyyy-MM-dd HH:mm:ss";
	public static final String FORMAT_8 = "yyyyMMdd";
	public static final String FORMAT_8_TEXT = "yyyy-MM-dd";
	public static final String FORMAT_6 = "HHmmss";
	public static final String FORMAT_6_TEXT = "HH:mm:ss";

	/**
	 *  判断是否为空
	 *  
	 *  @param str 目标字符串
	 */
	public static boolean isEmpty(String str) {
		return (str == null || str.trim().length() == 0);
	}

	/**
	 *  按照Date类型的日期和格式名称获日期字符串
	 *  
	 *  @param date 目标日期
	 *  @param format 格式
	 */
	public static String getTimeInFormat(Date date, String format) {
		return new SimpleDateFormat(format).format(date);
	}

	/**
	 *  按照String类型的日期和格式名称获日期字符串
	 *  
	 *  @param time 目标日期
	 *  @param format 格式
	 */
	public static Date getDateInFormat(String time, String format) throws ParseException {
		return new SimpleDateFormat(format).parse(time);
	}

	/**
	 *  按照格式名称获日期字符串
	 *  
	 *  @param format 格式
	 */
	public static String getCurrentTime(String format) {
		return getTimeInFormat(new Date(), format);
	}
	
	/**
	 *  按照FORMAT_14方式获得当前系统时间，按照{年，月，日，时，分，秒}进行分割，存入数组，按照序号返回今天类型的值
	 *  
	 *  0-年
	 *	1-月
	 *	2-日
	 *	3-时
	 *	4-分
	 *	5-秒
	 *  
	 *  @param index 结果序号
	 */
	public static int getCurrentTime14Split(int index) {
		String[] strTimeSplit = {"1970", "1", "1", "23", "59", "59"};
		int[] dTimeSplit = {1970, 1, 1, 23, 59, 59};

		String curTime14 = getTimeInFormat(new Date(), FORMAT_14);
		strTimeSplit[0] = curTime14.substring(0, 4);
		strTimeSplit[1] = curTime14.substring(4, 6);
		strTimeSplit[2] = curTime14.substring(6, 8);
		strTimeSplit[3] = curTime14.substring(8, 10);
		strTimeSplit[4] = curTime14.substring(10, 12);
		strTimeSplit[5] = curTime14.substring(12, 14);
		
		for(int i = 0;i < dTimeSplit.length;i++) {
			dTimeSplit[i] = Integer.valueOf(strTimeSplit[i]).intValue();
		}
		
		return dTimeSplit[index];
	}
	
	/**
	 *  获得记账起始日期
	 *  
	 *  @param type:year-年度；month-月度；day-日度
	 *  @param region:start-开始；end-结束
	 *  @param format:date-仅获得8位格式的日期；time-获得14位格式的时间
	 */
	public static String getAccountingPeriod(String type, String region, String format) {
		
		String startCsr = "start";
		
		String accountingPeriod= "";
		
		String[] strTimeSplit = {"1970", "1", "1", "23", "59", "59"};
		
		String curTime14 = getTimeInFormat(new Date(), FORMAT_14);
		strTimeSplit[0] = curTime14.substring(0, 4);
		strTimeSplit[1] = curTime14.substring(4, 6);
		strTimeSplit[2] = curTime14.substring(6, 8);
		strTimeSplit[3] = curTime14.substring(8, 10);
		strTimeSplit[4] = curTime14.substring(10, 12);
		strTimeSplit[5] = curTime14.substring(12, 14);
		
		if(type.equals("year")){
			if(region.equals(startCsr)) {
				accountingPeriod = strTimeSplit[0] + "-01-01";
			}else if(region.equals("end")) {
				accountingPeriod = strTimeSplit[0] + "-12-25";
			}else {
				;
			}
		}else if(type.equals("month")) {
			int dMonth = Integer.valueOf(strTimeSplit[1]).intValue();
			String strMonth = String.valueOf(dMonth);
			String strPreMonth = String.valueOf(dMonth);
			if(dMonth < 10) {
				strMonth = "0" + String.valueOf(dMonth);
			}
			if(dMonth > 1 && dMonth < 10) {
				strPreMonth = "0" + String.valueOf(dMonth - 1);
			}
			if(dMonth == 1){
				strPreMonth = String.valueOf(12);
			}
			
			if(region.equals(startCsr)) {
				accountingPeriod = strTimeSplit[0] + "-" + strPreMonth + "-26";
			}else if(region.equals("end")) {
				accountingPeriod = strTimeSplit[0] + "-" + strMonth + "-25";
			}else {
				;
			}
		}else if(type.equals("day")) {
			if(region.equals(startCsr)) {
				accountingPeriod = strTimeSplit[0] + "-" + strTimeSplit[1] + "-" + strTimeSplit[2];
			}else if(region.equals("end")) {
				accountingPeriod = strTimeSplit[0] + "-" + strTimeSplit[1] + "-" + strTimeSplit[2];
			}else {
				;
			}
		}else{
			;
		}
		
		if(format.equals("date")){
			;
		}else if(format.equals("time")) {
			if(region.equals(startCsr)) {
				accountingPeriod += " 00-00-00";
			}else if(region.equals("end")) {
				accountingPeriod += " 23-59-59";
			}else {
				;
			}
		}
		
		return accountingPeriod;
	}

	/**
	 *  获得Date格日期
	 *  
	 *  @param date 目标日期
	 *  @param field 格式
	 *  @param amount 格式
	 *  @return 返回指定格式日期
	 */
	public static Date getOffsetDate(Date date, int field, int amount) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(field, amount);
		return calendar.getTime();
	}

	/**
	 *  根据部分日期字符串获取全日期字符串
	 *  
	 *  @param time 目标日期
	 *  @param fromFormat 格式
	 *  @param toFormat 格式
	 *  @return 返回指定格式日期
	 */
	public static String getChangedFormat(String time, String fromFormat, String toFormat) throws ParseException {
		return new SimpleDateFormat(toFormat).format(new SimpleDateFormat(fromFormat).parse(time));
	}

	/**
	 *  获取yyyy/MM/dd HH:mm:ss格式的日期字符串
	 *  
	 *  @return 返回指定格式日期
	 */
	public static String getDate() {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Date now = new Date();
		String curDate = "";
		curDate = dateFormat.format(now);
		return curDate;
	}
	
	/**
	 *  延时 单位ms
	 *  
	 *  @param mstime 等待秒数
	 */
	public static void pause(int stime) {
		try {
			Thread.sleep((Long.valueOf(stime)));
		} catch (Exception e) {
			GSys.logErrorSys("pause[" + Arrays.toString(e.getStackTrace()) +"]");
		}
	}
}
