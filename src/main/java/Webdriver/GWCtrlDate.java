package Webdriver;

import Base.GText;
import DT.GLog;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;
import java.util.Objects;

/**
 *日期控件
 */
public class GWCtrlDate {
	
	/**
	 * 格式与日期控件上的一致
     *
     * @param webDriver 浏览器驱动对象
	 * @param id 呼出日期控件的id
	 * @param year 年份 格式:2020
	 * @param month 月份 格式 ：一月
	 * @param day 天 格式：1
	 */
	public static void ByValue(WebDriver webDriver,
                               String id,
                               String year,
                               String month,
                               String day) {
		GLog.logRecordTime(9,  "[widget]----[date]----[[");
		try {
		  if (!id.isEmpty()) {
		     //首先呼出日期控件
	         GWCtrlPage.ui_C_SELECT_INPUT_BTN(webDriver, id,"");
          }
		  //查找当前年份以及月份
		  WebElement date=null;
		  List<WebElement> dates = webDriver.findElements(By.cssSelector(GText.getCssSelectorTxt("div", "class", "x-date-picker x-unselectable")));
		  //查找当前显示的日期控件
		  for (WebElement datElement : dates) {
		    if (datElement.isDisplayed()) {
              date=datElement;
              break;
            }
          }
		  if (year.isEmpty() || month.isEmpty() || day.isEmpty()) {
              //选择今天
		      Objects.requireNonNull(date).findElement(By.xpath("table/tbody/tr[3]/td/table/tbody/tr[2]/td[2]/em/button")).click();
          }else {
              //选择指定日期
              WebElement button = Objects.requireNonNull(date).findElement(By.cssSelector(GText.getCssSelectorTxt("em", "class", " x-unselectable x-btn-arrow"))).findElement(By.tagName("button"));
              //呼出年份与月份轮盘  
              button.click();
              //暂时只查找年份区间为十年以内的日期，后续业务需要的话再拓展
              WebElement newDate=null;
              List<WebElement> newDates = webDriver.findElements(By.cssSelector(GText.getCssSelectorTxt("div", "class", "x-date-mp")));
              for (WebElement datElement : newDates) {
                if (datElement.isDisplayed()) {
                  newDate=datElement;
                  break;
                }
              }
              //点击年份
              ui_C_DATECLICK(webDriver, newDate, year);
              //点击月份
              ui_C_DATECLICK(webDriver, newDate,month);
              //点击确定
              List<WebElement> buttons = Objects.requireNonNull(newDate).findElements(By.tagName("button"));
              for (WebElement buttonElement : buttons) {
                if (buttonElement.getText().equals("确定")&&buttonElement.isDisplayed()) {
                    GWCtrlHighLight.apply(webDriver, buttonElement, 1, "");
                    buttonElement.click();
                    break;
                }
              }
              //设置具体日期（重新获取一次）
              dates = webDriver.findElements(By.cssSelector(GText.getCssSelectorTxt("div", "class", "x-date-picker x-unselectable")));
              for (WebElement datElement : dates) {
                if (datElement.isDisplayed()) {
                    date=datElement;
                    break;
                }
              }
              //查找day数据
              WebElement table = date.findElement(By.cssSelector(GText.getCssSelectorTxt("table", "class", "x-date-inner")));
              WebElement tbody = table.findElement(By.tagName("tbody"));
              List<WebElement> tds = tbody.findElements(By.tagName("td"));
              for (WebElement webElement : tds) {
                WebElement span = webElement.findElement(By.tagName("span"));
                if (span.getText().equals(day)) {
                    GWCtrlHighLight.apply(webDriver, span, 1, "");
                    span.click();
                    break;
                }
              }
          }
		  
		}catch (Exception e) {
			GWCtrlException.switchTo(webDriver, e, 1, 0, "----<exception[date[" + id + "];str[" + "] failed]", true);
		}
		
		GLog.logRecordTime(9,  "]]----[date]----[widget]");
	}
	
	/**
     * 点击日期控件
     *
     * @param webDriver 浏览器驱动对象
	 * @param newDate 新日期控件元素 
	 * @param date 日期文字（年份或月份）
	 */
	private static void ui_C_DATECLICK(WebDriver webDriver,
                                       WebElement newDate,
                                       String date) {
	  
	  GLog.logRecordTime(9,  "[widget]----[click]----[[");
	  try {
	    List<WebElement> aElements = newDate.findElements(By.tagName("a"));
	      for (WebElement a : aElements) {
	        String str = a.getText();
	        WebElement td = a.findElement(By.xpath(".."));
	        if (str.equals(date)) {
	          //点击年份
	          GWCtrlHighLight.apply(webDriver, td, 1, "");
	          td.findElement(By.tagName("a")).click();
	          break;
	        }
	      }
        
      } catch (Exception e) {
        GWCtrlException.switchTo(webDriver, e, 1, 0, "----<exception[click[" + newDate + "];str[" +date+ "] failed]", true);
      }
	  GLog.logRecordTime(9,  "]]----[click]----[widget]");
    }
	
	/**
	 *  日期控件
     *
     * @param webDriver 浏览器驱动对象
	 * @param id 呼出日期控件的id 当控件已呼出时，id为空
	 * @param date 要设置的日期格式 例：2021-05-5，注意月份若小于10前面带零，而天不带零
	 */
	public static void ByValue(WebDriver webDriver,
                               String id,
                               String date) {
      GLog.logRecordTime(9,  "[widget]----[date]----[[");
      //分割字符串
      String[] strList = date.split("-");
      strList[2] = String.valueOf(GDate.removeTheZero(strList[2]));
      switch (strList[1]) {
        case "01":
          ByValue(webDriver, id, strList[0],"一月",strList[2]);
          break;
        case "02":
          ByValue(webDriver, id, strList[0],"二月",strList[2]);
          break;
        case "03":
          ByValue(webDriver, id, strList[0],"三月",strList[2]);
          break;
        case "04":
          ByValue(webDriver, id, strList[0],"四月",strList[2]);
          break;
        case "05":
          ByValue(webDriver, id, strList[0],"五月",strList[2]);
          break;
        case "06":
          ByValue(webDriver, id, strList[0],"六月",strList[2]);
          break;
        case "07":
          ByValue(webDriver, id, strList[0],"七月",strList[2]);
          break;
        case "08":
          ByValue(webDriver, id, strList[0],"八月",strList[2]);
          break;
        case "09":
          ByValue(webDriver, id, strList[0],"九月",strList[2]);
          break;
        case "10":
          ByValue(webDriver, id, strList[0],"十月",strList[2]);
          break;
        case "11":
          ByValue(webDriver, id, strList[0],"十一月",strList[2]);
          break;
        case "12":
          ByValue(webDriver, id, strList[0],"十二月",strList[2]);
          break;
        default:
          break;
      }
      
      GLog.logRecordTime(9,  "]]----[date]----[widget]");
    }
	
	

	/**
	 * 单据列表区查询方案时间控件
	 *
     * @param webDriver 浏览器驱动对象
	 * @param fatherElement 单据列表区元素
	 * @param dateElement 时间控件元素
	 * @param dateElementStr 要设置的日期格式 例：2021-05-5 
	 * @param index 第几个时间控件，以0开始
	 */
	public static void ByElement(WebDriver webDriver,
                                 WebElement fatherElement,
                                 WebElement dateElement,
                                 String dateElementStr,
                                 int index) {
      GLog.logRecordTime(9,  "[widget]----[dateElement]----[[");
      String[] strList = dateElementStr.split("-");
      if (strList[2].contains("0")) {
        strList[2] = strList[2].replace("0", "");
      }
      String year = strList[0];
      String month = "";
      String day = strList[2];
      switch (strList[1]) {
        case "01":
          month = "一月";
          break;
        case "02":
          month = "二月";
          break;
        case "03":
          month = "三月";
          break;
        case "04":
          month = "四月";
          break;
        case "05":
          month = "五月";
          break;
        case "06":
          month = "六月";
          break;
        case "07":
          month = "七月";
          break;
        case "08":
          month = "八月";
          break;
        case "09":
          month = "九月";
          break;
        case "10":
          month = "十月";
          break;
        case "11":
          month = "十一月";
          break;
        case "12":
          month = "十二月";
          break;
        default:
          break;
      }
      try {
        WebElement button = dateElement.findElement(By.cssSelector(GText.getCssSelectorTxt("em", "class", " x-unselectable x-btn-arrow"))).findElement(By.tagName("button"));
        //呼出年份与月份轮盘  
        button.click();
        //暂时只查找年份区间为十年以内的日期，后续业务需要的话再拓展
        WebElement newdateElement=null;
        List<WebElement> newdateElements = fatherElement.findElements(By.cssSelector(GText.getCssSelectorTxt("div", "class", "x-date-mp")));
        for (WebElement dateElementlement : newdateElements) {
          if (dateElementlement.isDisplayed()) {
            newdateElement=dateElementlement;
            break;
          }
        }
        //点击年份
        ui_C_DATECLICK(webDriver, newdateElement, year);
        //点击月份
        ui_C_DATECLICK(webDriver, newdateElement, month);
        //点击确定
        List<WebElement> buttons = Objects.requireNonNull(newdateElement).findElements(By.tagName("button"));
        for (WebElement buttonElement : buttons) {
          if (buttonElement.getText().equals("确定")&&buttonElement.isDisplayed()) {
              GWCtrlHighLight.apply(webDriver, buttonElement, 1, "");
              buttonElement.click();
              break;
          }
        }
        //设置具体日期（重新获取一次）
        List<WebElement> dateElements = fatherElement.findElements(By.cssSelector(GText.getCssSelectorTxt("div", "class", "x-date-picker x-unselectable x-box-item")));
        dateElement = dateElements.get(index);
        //查找day数据
        WebElement table = dateElement.findElement(By.cssSelector(GText.getCssSelectorTxt("table", "class", "x-date-inner")));
        WebElement tbody = table.findElement(By.tagName("tbody"));
        List<WebElement> tds = tbody.findElements(By.tagName("td"));
        for (WebElement webElement : tds) {
          WebElement span = webElement.findElement(By.tagName("span"));
          if (span.getText().equals(day)) {
              GWCtrlHighLight.apply(webDriver, span, 1, "");
              span.click();
              break;
          }
        }
      }catch (Exception e) {
          GWCtrlException.switchTo(webDriver, e, 1, 0, "----<exception[dateElement[" + "];str[" + "] failed]", true);
      }
      GLog.logRecordTime(9,  "]]----[dateElement]----[widget]");
    }
}
