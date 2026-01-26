package Webdriver;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 时间方法
 */
public class GDate {

  /**
   * 作者: 张超 内容：通过传入时间字符串 确定指定月份的天数
   * 
   * @param dateStr 例:2021-05-27
   * @return 5月的天数
   */
  public static int getDays(String dateStr) {
    int days;
    String[] dateArray = dateStr.split("-");
    // 获取年份
    int year = Integer.parseInt(dateArray[0]);
    // 获取月份
    int month = removeTheZero(dateArray[1]);
    // 是否是闰年(默认值为false)
    boolean isLeapYear = ((year % 4 == 0) && (year % 100 != 0)) || (year % 400 == 0);
    // 判断月份
    switch (month) {
      // (月份为31天)
      case 1:
      case 3:
      case 5:
      case 7:
      case 8:
      case 10:
      case 12: {
        days = 31;
      }
        break;
      case 2: {
        // 是闰年
        if (isLeapYear) {
          days = 29;
        } else {
          days = 28;
        }
      }
        break;
      // 其他默认为（30天）
      default: {
        days = 30;
      }
        break;
    }
    return days;
  }

  /**
   * 根据进场日期和出场日期得到按进场日期为准的整月数和剩余租赁天数
   * "整月数", 值；"剩余租赁天数","值"
   *
   * @param startDate 进场日期
   * @param endDate   出场日期
   *
   * @return Map格式的日期
   */
  public static Map<String, Integer> getDates(String startDate, String endDate) {
    // 整月数
    // 剩余租赁天数
    int days;
    Map<String, Integer> dateMap = new HashMap<>();
    // 分割进场日期
    String[] startDateArray = startDate.split("-");
    // 分割出场日期
    String[] endDateArray = endDate.split("-");
    // 获取相差的整年数
    int years = Integer.parseInt(endDateArray[0]) - Integer.parseInt(startDateArray[0]);

    String FEYZI = GScene.DYNAMIC_DATA.get("月租计费");
    int months;
    if (FEYZI.equals("按自然月")) {
      // 按自然月
      // 获取相差的整月数(取整月则进场日期月份加1，进场月的天数加到租赁剩余天数)
      months = removeTheZero(endDateArray[1]) - (removeTheZero(startDateArray[1]) + 1);
      // 组建租赁剩余天数
      // 开始日期月的租赁剩余天数为当前月的天数减去进场日再加1因为进场日不算
      days = getDays(startDate) - removeTheZero(startDateArray[2]) + 1;
      // 日期再加上截止月份的剩余天数
      days = days + removeTheZero(endDateArray[2]);
    } else {
      // 默认为按进场日
      // 获取相差的整月数
      months = removeTheZero(endDateArray[1]) - removeTheZero(startDateArray[1]);
      // (默认按进场日期)不算起租日故此开始日期减一
      days = removeTheZero(endDateArray[2]) - (removeTheZero(startDateArray[2]) - 1);
      if (days < 0) {
        // 当前月不满足需要拆分一个月
        months = months - 1;
        // 组建新的日期字符串，去得到拆分月份的天数
        String newDateStr = getDateString(endDateArray, startDateArray);

        // 得到新的剩余天数
        days = days + getDays(newDateStr);
      }
    }

    // 得到新的整月数
    months = months + years * 12;
    // 填入整月数
    dateMap.put("整月数", months);
    // 填入剩余租赁天数
    dateMap.put("剩余租赁天数", days);
    return dateMap;

  }

  /**
   * 获取日期字符串
   *
   * @param endDateArray   结束日期数组
   * @param startDateArray 开始日期数组
   *
   * @return 不带零的值
   */
  private static String getDateString(String[] endDateArray, String[] startDateArray) {
    String newDateStr;
    if ((removeTheZero(endDateArray[1]) - 1) == 0) {
      // 月份为零时，年份移动到下一年，月份手动变为12
      String str = new BigDecimal((startDateArray[0])).subtract(new BigDecimal("1")).toString();
      newDateStr = str + "-" + "12" + "-" + startDateArray[2];

    } else {
      newDateStr = startDateArray[0] + "-" + (removeTheZero(endDateArray[1]) - 1) + "-" + startDateArray[2];
    }
    return newDateStr;
  }

  /**
   * 剔除指定字符串中的零值
   *
   * @param Str "01"到"09"
   * @return 不带零的值
   */
  public static int removeTheZero(String Str) {
    int result;
    switch (Str) {
      case "01":
        result = 1;
        break;
      case "02":
        result = 2;
        break;
      case "03":
        result = 3;
        break;
      case "04":
        result = 4;
        break;
      case "05":
        result = 5;
        break;
      case "06":
        result = 6;
        break;
      case "07":
        result = 7;
        break;
      case "08":
        result = 8;
        break;
      case "09":
        result = 9;
        break;
      default:
        result = Integer.parseInt(Str);

    }
    return result;

  }

  /**
   * 张超 得到两个日期之间的天数差
   *
   * @param startDateStr 开始天数 举例"2021-06-06"
   * @param endDateStr   结束天数 举例 "2021-06-07"
   *
   * @return 天数差 举例：1
   */
  @SuppressWarnings({ "UnnecessarySemicolon", "CatchMayIgnoreException" })
  public static int theDateIsPoor(String startDateStr, String endDateStr) {
    int days = 0;
    try {
      SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
      Date startDate = sdf.parse(startDateStr);
      Date endDate = sdf.parse(endDateStr);
      days = (int) ((endDate.getTime() - startDate.getTime()) / (1000 * 3600 * 24));

    } catch (Exception e) {
      ;
    }
    return days;
  }

  /**
   * 张超 拼接指定日期
   *
   * @param startDateStr 初始日期字符串
   * @param day          要添加的天数
   *
   * @return 新的日期字符串
   */
  public static String addDate(String startDateStr, long day) {
    String newDateStr = "";
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    Date startDate;
    try {
      startDate = sdf.parse(startDateStr);
      long time = startDate.getTime(); // 得到指定日期的毫秒数
      day = day * 24 * 60 * 60 * 1000; // 要加上的天数转换成毫秒数
      time += day; // 相加得到新的毫秒数
      newDateStr = sdf.format(new Date(time));
    } catch (ParseException e) {
      // noinspection CallToPrintStackTrace
      e.printStackTrace();
    }

    return newDateStr;
  }

  public static void main(String[] args) {
    System.out.println(addDate("2021-06-06", 3));
  }

}
