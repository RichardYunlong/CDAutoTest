package main.java.Webdriver;

import main.java.DT.GLog;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.Color;

/**
 * @author zhangc-z
 *获取控件标签，以及属性
 */
public class GWCtrlLabel {
  
  /**
   * 控件元素
   */
  public WebElement typeElement = null;
  
  /**
   * 控件对应的标签元素
   */
  public WebElement labelElement = null;
  
  /**
   * 是否必填
   */
  public Boolean isNecessary = true;
  
  /**
   *是否只读 
   */
  public Boolean isReadOnly = true;
  
  /**
   * 元素颜色十六进制例：#ffffff
   */
  public String color = null;
  
  
  /**
   * 用于检测控件标签的属性，仅适用基本信息页签，或div窗体中控件周围存在标签元素（label）的情况
   *
   * @param webDriver 目标驱动
   * @param queryCriteriaName 能找到控件的属性名
   * @param queryCriteriaValue 能找到控件的属性值
   * @param typeName 控件名称
   * @param isDefault 控件是否存在默认值
   * @param Hex 预期的控件背景色 ，十六进制的
   */
  public GWCtrlLabel(WebDriver webDriver, String queryCriteriaName, String queryCriteriaValue, String typeName, Boolean isDefault, String Hex) {
    
    GLog.logRecordTime(0, "[widget]----[GWCtrlLabel]----[[");
    try {
      typeElement = GWCtrlQuery.ui_Q(webDriver, queryCriteriaName, queryCriteriaValue);
      WebElement div = GWCtrlQuery.ui_Q(webDriver, typeElement,"xpath","..",2);
      labelElement = div.findElement(By.tagName("label"));
      //获取元素背景色
      color = typeElement.getCssValue("background-color");
      color = Color.fromString(color).asHex();
      //获取单据只读状态
      isReadOnly = !typeElement.isEnabled();
      //是否必填
      String str = labelElement.getText();
      isNecessary = str.contains("*");
      
      if (isNecessary) {
        GLog.logRecordTime(0, "[检测]----["+typeName+"]控件为必填字段!");
      }else {
        GLog.logRecordTime(0, "[检测]----["+typeName+"]控件为非必填字段!");
      }
      
      if (isReadOnly) {
        GLog.logRecordTime(0, "[检测]----["+typeName+"]控件为只读字段!");
      }else {
        GLog.logRecordTime(0, "[检测]----["+typeName+"]控件为可编辑字段!");
      }
      
      if (isDefault) {
        String valueStr =typeElement.getAttribute("value");
        //当前控件存在默认值数据
        if (valueStr.isEmpty()) {
          //当前不存在默认日期值
          GWCtrlException.throwException(webDriver, "[异常]----"+typeName+"缺少默认值！");
        }else {
          GLog.logRecordTime(0, "[动作]----检测"+typeName+"存在默认值为["+valueStr+"]");
        }
      }
      
      if (color.equals(Hex)) {
        GLog.logRecordTime(0, "[动作]----检测当前"+typeName+"背景色为"+color+"期望背景色为"+Hex+"背景色正确！");
      }else {
        GWCtrlException.throwException(webDriver, "[异常]----检测当前"+typeName+"背景色为"+color+"期望背景色为"+Hex+"背景色错误！");
      }
      
    } catch (Exception e) {
      GWCtrlException.switchTo(webDriver, e, 1, 0, "----<exception[GWCtrlLabel]" + GWCtrlMsg.ui_CLICK[1] + "]>", true);
    }
    GLog.logRecordTime(0, "]]----[attribute]----[query]");
  }
}
