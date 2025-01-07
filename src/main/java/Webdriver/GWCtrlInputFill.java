package Webdriver;

import Base.GTime;
import DT.GLog;
import org.openqa.selenium.*;

/**
 *  Input控件填写处理
 */
public class GWCtrlInputFill {
	
	/**
	 *  通过id查找输入框，清空后写入指定内容
	 *  1.适用于clear()后焦点不丢失的情况
	 *  2.默认使用clear方式，不执行输入确认
	 *
	 *  @param webDriver 浏览器驱动对象
	 *  @param id 输入框id
	 *  @param str 待输入内容
	 */
	public static void ById(WebDriver webDriver,
							String id,
							String str) {
		GLog.logRecordTime(0, "[widget]----[input]----[[");
		try {
			GLog.logRecordTime(0, "----<input[" + id + "]>" + GWCtrlMsg.ui_QUERY[0]);
			GWCtrlWait.ViewWaitingAllById(webDriver, GTestIndicators.PageShowTime, id);
			WebElement input = webDriver.findElement(By.id(id));
			GWCtrlHighLight.apply(webDriver, input, 1, "");
			GLog.logRecordTime(0, "----<input[" + input.getText() + "]>" + GWCtrlMsg.ui_INPUT[2]);
			input.clear();
			input.sendKeys(str);
			GLog.logRecordTime(0, "----<input[id[" + id + "];str[" + str + "]>" + GWCtrlMsg.ui_INPUT[1]);
		}catch (Exception e) {
			GWCtrlException.switchTo(webDriver, e, 1, 0, "----<exception[input[" + id + "]" + GWCtrlMsg.ui_INPUT[4] + "]>", true);
		}
		GLog.logRecordTime(0, "]]----[input]----[widget]");
	}
	
	/**
	 *  通过id查找输入框，清空后写入指定内容
	 *  1.适用于clear()后焦点不丢失的情况
	 *  2.默认使用clear方式，不执行输入确认
	 *  3.此方法只用于游标从“基准iframe”开始的操作
	 *
	 *  @param webDriver 浏览器驱动对象
	 *  @param iframeIndex 目标iframe序号
	 *  @param id 输入框id
	 *  @param str 待输入内容
	 */
	public static void ByIdDefaultIframe(WebDriver webDriver,
										 int iframeIndex,
										 String id,
										 String str) {
		GLog.logRecordTime(0, "[widget]----[input]----[[");
		GWCtrlFrame.ui_C_SWITCN_ELEMENT(webDriver, GWCtrlWebElementIframe.getIframe(iframeIndex));
		try {
			GLog.logRecordTime(0, "----<input[" + id + "]>" + GWCtrlMsg.ui_QUERY[0]);
			GWCtrlWait.ViewWaitingAllById(webDriver, GTestIndicators.PageShowTime, id);
			WebElement input = webDriver.findElement(By.id(id));
			GWCtrlHighLight.apply(webDriver, input, 1, "");
			GLog.logRecordTime(0, "----<input[" + input.getText() + "]>" + GWCtrlMsg.ui_INPUT[2]);
			input.clear();
			input.sendKeys(str);
			GLog.logRecordTime(0, "----<input[id[" + id + "];str[" + str + "]>" + GWCtrlMsg.ui_INPUT[1]);
		}catch (Exception e) {
			GWCtrlException.switchTo(webDriver, e, 1, 0, "----<exception[input[" + id + "]" + GWCtrlMsg.ui_INPUT[4] + "]>", true);
		}
		GWCtrlFrame.ui_C_SWITCN_DEFAULT(webDriver);
		GLog.logRecordTime(0, "]]----[input]----[widget]");
	}
	
	/**
	 *  通过id查找输入框，清空后写入指定内容并且回车
	 *
	 * @param webDriver 浏览器驱动对象
	 * @param id 输入框id
	 * @param str 待输入内容
	 */
	public static void ByIdUnClearEnter(WebDriver webDriver, String id, String str) {
      GLog.logRecordTime(0, "[widget]----[input]----[[");
      try {
          GLog.logRecordTime(0, "----<input[" + id + "]>" + GWCtrlMsg.ui_QUERY[0]);
          GWCtrlWait.ViewWaitingAllById(webDriver, GTestIndicators.PageShowTime, id);
          WebElement input = webDriver.findElement(By.id(id));
          GWCtrlHighLight.apply(webDriver, input, 1, "");
          GLog.logRecordTime(0, "----<input[" + input.getText() + "]>" + GWCtrlMsg.ui_INPUT[2]);
          input.clear();
          input.sendKeys(str);
          GLog.logRecordTime(0, "----<input[id[" + id + "];str[" + str + "]>" + GWCtrlMsg.ui_INPUT[1]);
          input.sendKeys(Keys.ENTER);
          GLog.logRecordTime(0, "----<input[ENTER]>" + GWCtrlMsg.ui_INPUT[0]);
      }catch (Exception e) {
          GWCtrlException.switchTo(webDriver, e, 1, 0, "----<exception[input[" + id + "]" + GWCtrlMsg.ui_INPUT[4] + "]>", true);
      }
      GLog.logRecordTime(0, "]]----[input]----[widget]");
    }
	
	/**
	 *  通过id查找输入框，不清空直接写入指定内容
	 *  1.不清空
	 *  2.使用unclear方式，通过回车键执行输入确认
	 *  3.适用于按下回车后确认输入，并且焦点不丢失的情况
	 *
	 *  @param webDriver 浏览器驱动对象
	 *  @param id 输入框id
	 *  @param str 待输入内容
	 */
	public static void ByIdUnClear(WebDriver webDriver,
								   String id,
								   String str) {
		GLog.logRecordTime(0, "[widget]----[input]----[[");
		try {
			GLog.logRecordTime(0, "----<input[" + id + "]>" + GWCtrlMsg.ui_QUERY[0]);
			WebElement input = webDriver.findElement(By.id(id));
			GWCtrlHighLight.apply(webDriver, input, 1, "");
			input.sendKeys(str);
			GLog.logRecordTime(0, "----<input[id[" + id + "];str[" + str + "]>" + GWCtrlMsg.ui_INPUT[1]);
			input.sendKeys(Keys.ENTER);
			GLog.logRecordTime(0, "----<input[ENTER]>" + GWCtrlMsg.ui_INPUT[0]);
		}catch (Exception e) {
			GWCtrlException.switchTo(webDriver, e, 1, 0, "----<exception[input[" + id + "]" + GWCtrlMsg.ui_INPUT[4] + "]>", true);
		}
		GLog.logRecordTime(0, "]]----[input]----[widget]");
	}
	
	/**
	 *  通过id查找输入框，不清空直接写入指定内容
	 *  1.不清空
	 *  2.使用unclear方式，通过回车键执行输入确认
	 *  3.适用于按下回车后确认输入，并且焦点不丢失的情况
	 *
	 *  @param webDriver 浏览器驱动对象
	 *  @param id 输入框id
	 *  @param str 待输入内容
	 *  @param searchResultId 查询结果框id
	 */
	public static void ByIdUnClearSearchWait(WebDriver webDriver,
											 String id,
											 String str,
											 String searchResultId) {
		GLog.logRecordTime(0, "[widget]----[input]----[[");
		try {
			GWCtrlWait.ViewWaitingAllById(webDriver, GTestIndicators.PageShowTime, id);
			GLog.logRecordTime(0, "----<input[" + id + "]>" + GWCtrlMsg.ui_QUERY[0]);
			WebElement input = webDriver.findElement(By.id(id));
			GWCtrlHighLight.apply(webDriver, input, 1, "");
			input.sendKeys(str);
			GLog.logRecordTime(0, "----<input[id[" + id + "];str[" + str + "]>" + GWCtrlMsg.ui_INPUT[1]);
			input.sendKeys(Keys.ENTER);
			GLog.logRecordTime(0, "----<input[ENTER]>" + GWCtrlMsg.ui_INPUT[0]);
			GWCtrlWait.ViewWaitingTextById(webDriver, GTestIndicators.PageShowTime, searchResultId, str);
		}catch (Exception e) {
			GWCtrlException.switchTo(webDriver, e, 1, 0, "----<exception[input[" + id + "]" + GWCtrlMsg.ui_INPUT[4] + "]>", true);
		}
		GLog.logRecordTime(0, "]]----[input]----[widget]");
	}
	
	/**
	 *  通过id查找输入框
	 *  1.不清空
	 *  2.不执行输入确认
	 *
	 *  @param webDriver 浏览器驱动对象
	 *  @param id 输入框id
	 *  @param str 待输入内容
	 */
	public static void ByIdUnConfirm(WebDriver webDriver,
									 String id,
									 String str) {
		GLog.logRecordTime(0, "[widget]----[input]----[[");
		try {
			GLog.logRecordTime(0, "----<input[" + id + "]>" + GWCtrlMsg.ui_QUERY[0]);
			WebElement input = webDriver.findElement(By.id(id));
			GWCtrlHighLight.apply(webDriver, input, 1, "");
			input.sendKeys(str);
			GLog.logRecordTime(0, "----<input[id[" + id + "];str[" + str + "]>" + GWCtrlMsg.ui_INPUT[1]);
		}catch (Exception e) {
			GWCtrlException.switchTo(webDriver, e, 1, 0, "----<exception[input[" + id + "]" + GWCtrlMsg.ui_INPUT[4] + "]", true);
		}
		GLog.logRecordTime(0, "]]----[input]----[widget]");
	}

	/**
	 *  通过id查找输入框
	 *  1.通过输入框内按键操作删除原有内容
	 *  2.仅写入指定内容，不执行输入确认
	 *
	 *  @param webDriver 浏览器驱动对象
	 *  @param id 输入框id
	 *  @param str 待输入内容
	 */
	public static void ByIdAfterDelete(WebDriver webDriver,
									   String id,
									   String str) {
		GLog.logRecordTime(0, "[widget]----[input]----[[");
		try {
			GLog.logRecordTime(0, "----<input[" + id + "]>" + GWCtrlMsg.ui_QUERY[0]);
			//定位输入框
			WebElement input = webDriver.findElement(By.id(id));
			GWCtrlHighLight.apply(webDriver, input, 1, "");
			//在输入框内部通过“按键方式”删除内容
			input.sendKeys(Keys.BACK_SPACE );
			GLog.logRecordTime(0, "----<input[BACK_SPACE]>" + GWCtrlMsg.ui_INPUT[0]);
			input.sendKeys(Keys.chord(Keys.CONTROL, "a")); 
			GLog.logRecordTime(0, "----<input[CONTROL + a]>" + GWCtrlMsg.ui_INPUT[0]);
			input.sendKeys(Keys.DELETE);
			GLog.logRecordTime(0, "----<input[DELETE]>" + GWCtrlMsg.ui_INPUT[0]);
			//输入指定内容
			input.sendKeys(str);
			GLog.logRecordTime(0, "----<input[id[" + id + "];str[" + str + "]>" + GWCtrlMsg.ui_INPUT[1]);
		}catch (Exception e) {
			GWCtrlException.switchTo(webDriver, e, 1, 0, "----<exception[input[" + id + "]" + GWCtrlMsg.ui_INPUT[4] + "]", true);
		}
		GLog.logRecordTime(0, "]]----[input]----[widget]");
	}
	
	/**
	 *  通过id查找输入框
	 *  1.通过输入框clear()方法清空原有内容
	 *  2.适用于使用clear()方法后焦点丢失的情况
	 *  3.仅写入指定内容，不执行输入确认
	 *
	 *  @param webDriver 浏览器驱动对象
	 *  @param ToBeClick 用于点击呼出输入框的WebElement对象
	 *  @param id 输入框id
	 *  @param str 待输入内容
	 *  
	 */
	public static void ByIdAfterClear(WebDriver webDriver,
									  WebElement ToBeClick,
									  String id,
									  String str) {
		GLog.logRecordTime(0, "[widget]----[input]----[[");
		try {
			GLog.logRecordTime(0, "----<input[" + id + "]>" + GWCtrlMsg.ui_QUERY[0]);
			//定位输入框
			WebElement input = webDriver.findElement(By.id(id));
			GWCtrlHighLight.apply(webDriver, input, 1, "");
			GLog.logRecordTime(0, "----<input[" + input.getText() + "]>" + GWCtrlMsg.ui_INPUT[2]);
			//在输入框内部通过“清除方式”删除内容
			input.clear();
			GWCtrlDivDoubleClick.ByWebElement(webDriver,ToBeClick);
			input = webDriver.findElement(By.id(id));
			//输入指定内容
			input.sendKeys(str);
			GLog.logRecordTime(0, "----<input[id[" + id + "];str[" + str + "]>" + GWCtrlMsg.ui_INPUT[1]);
		}catch (Exception e) {
			GWCtrlException.switchTo(webDriver, e, 1, 0, "----<exception[input[" + id + "]" + GWCtrlMsg.ui_INPUT[4] + "]", true);
		}
		GLog.logRecordTime(0, "]]----[input]----[widget]");
	}
	
	/**
	 *  通过WebElement查找输入框，写入指定内容
	 *  1.适用于使用clear()、delete等方法后焦点不丢失的情况
	 *  2.使用按键方式执行输入确认
	 *
	 *  @param webDriver 浏览器驱动对象
	 *  @param input 输入框的WebElement对象
	 *  @param str 待输入内容
	 */
	public static void ByWebElement(WebDriver webDriver,
									WebElement input,
									String str) {
		GLog.logRecordTime(0, "[widget]----[input]----[[");
		try {
			GLog.logRecordTime(0, "----<input[" + input.toString() + "]>" + GWCtrlMsg.ui_QUERY[0]);
			GWCtrlHighLight.apply(webDriver, input, 1, "");
			GWCtrlWait.ViewWaitingAllByWebElement(webDriver, GTestIndicators.PageShowTime, input);
			input.click();
			input.clear();
			input.sendKeys(Keys.BACK_SPACE );
			GLog.logRecordTime(0, "----<input[BACK_SPACE]>" + GWCtrlMsg.ui_INPUT[0]);
			input.sendKeys(Keys.chord(Keys.CONTROL, "a")); 
			GLog.logRecordTime(0, "----<input[CONTROL + a]>" + GWCtrlMsg.ui_INPUT[0]);
			input.sendKeys(Keys.DELETE);
			GLog.logRecordTime(0, "----<input[DELETE]>" + GWCtrlMsg.ui_INPUT[0]);
			GLog.logRecordTime(0, "----<input[" + input.getText() + "]>" + GWCtrlMsg.ui_INPUT[3]);
			input.sendKeys(str);
			GLog.logRecordTime(0, "----<input[" + str + "]>" + GWCtrlMsg.ui_INPUT[1]);
			input.sendKeys(Keys.ENTER);
			GLog.logRecordTime(0, "----<input[ENTER]>" + GWCtrlMsg.ui_INPUT[0]);
		}catch (Exception e) {
			GWCtrlException.switchTo(webDriver, e, 1, 0, "----<exception[input[" + input.toString() + "]" + GWCtrlMsg.ui_INPUT[4] + "]", true);
		}
		GLog.logRecordTime(0, "]]----[input]----[widget]");
	}
	
	/**
	 *  通过WebElement查找输入框，不清空直接写入指定内容
	 *  1.不清空
	 *  2.使用按键方式执行输入确认
	 *
	 *  @param webDriver 浏览器驱动对象
	 *  @param input 输入框的WebElement对象
	 *  @param str 待输入内容
	 */
	public static void ByWebElementUnClear(WebDriver webDriver,
										   WebElement input,
										   String str) {
		GLog.logRecordTime(0, "[widget]----[input]----[[");
		try {
			GLog.logRecordTime(0, "----<input[" + input.toString() + "]>>" + GWCtrlMsg.ui_QUERY[0]);
			GWCtrlHighLight.apply(webDriver, input, 1, "");
			input.sendKeys(str);
			GLog.logRecordTime(0, "----<input[" + str + "]>" + GWCtrlMsg.ui_INPUT[1]);
			//GWCtrlTime.Pause(3);
			input.sendKeys(Keys.ENTER);
			GLog.logRecordTime(0, "----<input[ENTER]>" + GWCtrlMsg.ui_INPUT[0]);
		}catch (Exception e) {
			GWCtrlException.switchTo(webDriver, e, 1, 0, "----<exception[input[" + input.toString() + "]" + GWCtrlMsg.ui_INPUT[4] + "]", true);
		}
		GLog.logRecordTime(0, "]]----[input]----[widget]");
	}
	
	/**
	 *  通过cssSelector查找输入框，写入指定内容
	 *  1.适用于clear()后焦点不丢失的情况
	 *  2.默认使用clear方式，不执行输入确认
	 *
	 *  @param webDriver 浏览器驱动对象
	 *  @param cssSelector 输入框的cssSelector
	 *  @param str 待输入内容
	 */
	public static void ByCssSelector(WebDriver webDriver,
									 String cssSelector,
									 String str) {
		GLog.logRecordTime(0, "[widget]----[input]----[[");
		try {
			GLog.logRecordTime(0, "----<input[" + cssSelector + "]>" + GWCtrlMsg.ui_QUERY[0]);
			GWCtrlWait.ViewWaitingAllByCssSelector(webDriver, GTestIndicators.PageShowTime, cssSelector);
			WebElement input = webDriver.findElement(By.cssSelector(cssSelector));
			GWCtrlHighLight.apply(webDriver, input, 1, "");
			input.clear();
			GLog.logRecordTime(0, "----<input[" + input.getText() + "]>" + GWCtrlMsg.ui_INPUT[2]);
			input.sendKeys(str);
			GLog.logRecordTime(0, "----<input[" + str + "]>" + GWCtrlMsg.ui_INPUT[1]);
		}catch (Exception e) {
			GWCtrlException.switchTo(webDriver, e, 1, 0, "----<exception[input[" + cssSelector + "]" + GWCtrlMsg.ui_INPUT[4] + "]", true);
		}
		GLog.logRecordTime(0, "]]----[input]----[widget]");
	}

	/**
	 *  通过xpath查找输入框，写入指定内容
	 *  1.适用于clear()后焦点不丢失的情况
	 *  2.默认使用clear方式，不执行输入确认
	 *
	 *  @param webDriver 浏览器驱动对象
	 *  @param xpath 输入框的xpath
	 *  @param str 待输入内容
	 */
	public static void ByXpath(WebDriver webDriver,
							   String xpath,
							   String str) {
		GLog.logRecordTime(0, "[widget]----[input]----[[");
		try {
			GLog.logRecordTime(0, "----<input[" + xpath + "]>" + GWCtrlMsg.ui_QUERY[0]);
			WebElement input = webDriver.findElement(By.xpath(xpath));
			GWCtrlHighLight.apply(webDriver, input, 1, "");
			input.clear();
			GLog.logRecordTime(0, "----<input[" + input.getText() + "]>" + GWCtrlMsg.ui_INPUT[2]);
			input.sendKeys(str);
			GLog.logRecordTime(0, "----<input[" + str + "]>" + GWCtrlMsg.ui_INPUT[1]);
		}catch (Exception e) {
			GWCtrlException.switchTo(webDriver, e, 1, 0, "----<exception[input[" + xpath + "]" + GWCtrlMsg.ui_INPUT[4] + "]", true);
		}
		GLog.logRecordTime(0, "]]----[input]----[widget]");
	}
	
	/**
	 *  通过xpath查找输入框，写入指定内容
	 *  1.不清空
	 *  2.不执行输入确认
	 *
	 *  @param webDriver 浏览器驱动对象
	 *  @param xpath 输入框的xpath
	 *  @param str	待输入内容
	 */
	public static void ByXpathUnClear(WebDriver webDriver,
									  String xpath,
									  String str) {
		GLog.logRecordTime(0, "[widget]----[input]----[[");
		try {
			GLog.logRecordTime(0, "----<input[" + xpath + "]>" + GWCtrlMsg.ui_QUERY[0]);
			WebElement input = webDriver.findElement(By.xpath(xpath));
			GWCtrlHighLight.apply(webDriver, input, 1, "");
			input.sendKeys(str);
			GLog.logRecordTime(0, "----<input[" + str + "]>" + GWCtrlMsg.ui_INPUT[1]);
		}catch (Exception e) {
			GWCtrlException.switchTo(webDriver, e, 1, 0, "----<exception[input[" + xpath + "]" + GWCtrlMsg.ui_INPUT[4] + "]", true);
		}
		GLog.logRecordTime(0, "]]----[input]----[widget]");
	}
	
	/**
	 *  根据input控件id输入指定内容
	 *  适用于
	 *  1.必须通过单机或双击于input控件相关联的WebElement对象，才能呼出input控件
	 *  2.呼出input控件后，再根据id找到input控件，写入指定内容
	 *  3.本方法只负责输入内容，是否需要通过“点击空白处”或“点击回车键”等方式确认输入结果，需要在引用本方法的其他位置实现
	 *
	 *  @param webDriver 浏览器驱动对象
	 *  @param toBeClick 点击呼出input控件的区域
	 *  @param inputId 写入位置,可以是input控件，也可以是其他支持输入的控件，例如textArea等
	 *  @param str 待写入内容
	 *  @param tarType 写入方式 0-直接写入;1-下拉选中;2-浏览选中 为0时以下3项为空
	 *  @param inputCheck 是否使用clear()方式清空后输入
	 *   
     *  @param listTagName 列表标签名称 为空时使用下面两项搜多，比如下面两项可以取值为"id;id的值"，然后通过id定位；不为空时，使用cssSelector定位
	 *  @param listAtrributeName 列表属性名
	 *  @param listAtrributeValue 列表属性值
	 *  
     *  @param leafTagName 选项标签名称 为空时使用下面两项搜多，比如下面两项可以取值为"id;id的值"，然后通过id定位；不为空时，使用cssSelector定位
     *  @param leafAtrributeName 选项属性名
     *  @param leafAtrributeValue 选项属性值
	 */
	public static void ByIdFromClick(WebDriver webDriver,
									 WebElement toBeClick,
									 String inputId, 
									 String str,
									 int tarType, 
									 boolean inputCheck,
									 String listTagName,
									 String listAtrributeName, 
									 String listAtrributeValue,
									 String leafTagName, 
									 String leafAtrributeName,
									 String leafAtrributeValue) {
		GLog.logRecordTime(0, "[widget]----[input]----[[");
		try {
			GWCtrlHighLight.apply(webDriver, toBeClick, 1, "");
			switch(tarType) {
				case 0:{
					if(!inputCheck) {
						ByIdAfterDelete(webDriver, inputId, str);
					}else {
						ByIdAfterClear(webDriver, toBeClick, inputId, str);
					}
					break;
				}
				case 1:{
					GWCtrlDropDown.ByList(webDriver,
										  webDriver.findElement(By.id(inputId)),
							              listTagName, 
										  listAtrributeName, 
										  listAtrributeValue, 
										  leafTagName, 
										  leafAtrributeName, 
										  leafAtrributeValue, 
										  str);
					break;
				}
				case 2:{
					//暂未遇到
					break;
				}
				default:{
					break;
				}
			}
		}catch (Exception e) {
			GWCtrlException.switchTo(webDriver, e, 1, 0, "----<exception[input[" + inputId + "]" + GWCtrlMsg.ui_INPUT[4] + "]", true);
		}
		GLog.logRecordTime(0, "]]----[input]----[widget]");
	}
	
	/**
	 *  通过双击div元素临时生成的input类目标元素，通过Xpath地位Div，id查找Input，写入指定内容
	 *
	 *  @param webDriver 浏览器驱动对象
	 *  @param xpath xpath参数
	 *  @param id id参数
	 *  @param str 输入的字符串
	 *  @param blankId 空白处id参数
	 */
	public static void ByXpathFromClickDiv(WebDriver webDriver,
										   String xpath,
										   String id,
										   String str,
										   String blankId) {
		GLog.logRecordTime(0, "[widget]----[input]----[[");
		try {
			GLog.logRecordTime(0, "----<input[" + xpath + "]>" + GWCtrlMsg.ui_QUERY[0]);
			GWCtrlWait.ViewWaitingAllByXpath(webDriver, GTestIndicators.PageShowTime, xpath);
			GWCtrlDivDoubleClick.ByXpath(webDriver,xpath);
			GWCtrlQuery.ui_Q_V(webDriver, "id", id).clear();
			GWCtrlPage.ui_C_CLICK_BLANK_SPACE(webDriver,"id", blankId);
			GWCtrlDivDoubleClick.ByXpath(webDriver,xpath);
			ByIdUnClear(webDriver, id, str);
			GWCtrlPage.ui_C_CLICK_BLANK_SPACE(webDriver,"id", blankId);
		}catch (Exception e) {
			GWCtrlException.switchTo(webDriver, e, 1, 0, "----<exception[input[" + xpath + "]" + GWCtrlMsg.ui_INPUT[4] + "]", true);
		}
		GLog.logRecordTime(0, "]]----[input]----[widget]");
	}
	
	/**
	 *  当输入框输入后，点击空白处时，系统做了自动校验，则不能再输入为空的情况下点击空白处
	 *
	 *  @param webDriver 浏览器驱动对象
	 *  @param xpath xpath参数
	 *  @param id id参数
	 *  @param str 输入的字符串
	 *  @param blankId 空白处id参数
	 *  @param bAutoCheck 点击空白处是否自动校验
	 */
	public static void ByXpathFromClickDiv(WebDriver webDriver,
										   String xpath,
										   String id,
										   String str,
										   String blankId,
										   boolean bAutoCheck) {
		GLog.logRecordTime(0, "[widget]----[input]----[[");
		try {
			GLog.logRecordTime(0, "----<input[" + xpath + "]>" + GWCtrlMsg.ui_QUERY[0]);
			GWCtrlQuery.ui_Q_V(webDriver, "xpath", xpath).click();
			WebElement input = GWCtrlQuery.ui_Q_V(webDriver, "id", id);
			GWCtrlHighLight.apply(webDriver, input, 1, "");
			if(!bAutoCheck) {
				input.clear();
				GWCtrlPage.ui_C_CLICK_BLANK_SPACE(webDriver,"id", blankId);
			}
			input.sendKeys(str);
			GLog.logRecordTime(0, "----<input[" + str + "]>" + GWCtrlMsg.ui_INPUT[1]);
			input.sendKeys(Keys.ENTER);
			GLog.logRecordTime(0, "----<input[ENTER]>" + GWCtrlMsg.ui_INPUT[0]);
			GWCtrlPage.ui_C_CLICK_BLANK_SPACE(webDriver,"id", blankId);
		}catch (Exception e) {
			GWCtrlException.switchTo(webDriver, e, 1, 0, "----<exception[input[" + xpath + "]" + GWCtrlMsg.ui_INPUT[4] + "]", true);
		}
		GLog.logRecordTime(0, "]]----[input]----[widget]");
	}
	
	/**
	 *  通过双击div元素临时生成的Input类目标元素，通过WebElement地位div，id查找input，写入指定内容
	 *
	 *  @param webDriver 浏览器驱动对象
	 *  @param webElement WebElement参数
	 *  @param id id参数
	 *  @param str 输入的字符串
	 *  @param blankId 空白处id参数
	 *  @param bAutoCheck 是否需要先清空后输入
	 */
	@SuppressWarnings({"ConstantExpression", "ConstantValue"})
    public static void ByWebElementFromClickDiv(WebDriver webDriver,
                                                WebElement webElement,
                                                String id,
                                                String str,
                                                String blankId,
                                                boolean bAutoCheck) {
		GLog.logRecordTime(0, "[widget]----[input]----[[");
		try {
			GLog.logRecordTime(0, "----<input[" + webElement.toString() + "]>" + GWCtrlMsg.ui_QUERY[0]);		
			
			if(!bAutoCheck) {
				GWCtrlDivDoubleClick.ByWebElement(webDriver, webElement);
				WebElement input = GWCtrlQuery.ui_Q_V(webDriver, "id", id);
				GWCtrlHighLight.apply(webDriver, input, 1, "");
				input.sendKeys(Keys.DELETE);
				GLog.logRecordTime(0, "----<input[ENTER]>" + GWCtrlMsg.ui_INPUT[3]);
				ByIdUnClear(webDriver, id, str);
				GWCtrlPage.ui_C_CLICK_BLANK_SPACE(webDriver,"id", blankId);
			}else {
				GWCtrlQuery.ui_V(webDriver, webElement);
				WebElement input = GWCtrlQuery.ui_Q_V(webDriver, "id", id);
				if(!bAutoCheck) {
					input.clear();
					GWCtrlPage.ui_C_CLICK_BLANK_SPACE(webDriver,"id", blankId);
				}
				input.sendKeys(str);
				GLog.logRecordTime(0, "----<input[" + str + "]>" + GWCtrlMsg.ui_INPUT[1]);
				input.sendKeys(Keys.ENTER);
				GLog.logRecordTime(0, "----<input[ENTER]>" + GWCtrlMsg.ui_INPUT[0]);
				GWCtrlPage.ui_C_CLICK_BLANK_SPACE(webDriver,"id", blankId);
			}
		}catch (Exception e) {
			GWCtrlException.switchTo(webDriver, e, 1, 0, "----<exception[input[" + webElement.toString() + "]" + GWCtrlMsg.ui_INPUT[4] + "]", true);
		}
		GLog.logRecordTime(0, "]]----[input]----[widget]");
	}
	
	/**
	 * 通过双击div元素临时生成的Input类目标元素，通过WebElement地位div，id查找input，写入指定内容
	 *
	 * @param webDriver 浏览器驱动对象
	 * @param webElement WebElement参数
	 * @param id id参数
	 * @param str 输入的字符串
	 * @param blankId 空白处id参数
	 * @param bAutoCheck 是否需要先清空后输入
	 * @param pauseTime 延时时间（单位毫秒）
	 */
	@SuppressWarnings({"ConstantExpression", "ConstantValue"})
    public static void ByWebElementFromClickDiv(WebDriver webDriver,
                                                WebElement webElement,
                                                String id,
                                                String str,
                                                String blankId,
                                                boolean bAutoCheck ,
                                                int pauseTime) {
      GLog.logRecordTime(0, "[widget]----[input]----[[");
      try {
          GLog.logRecordTime(0, "----<input[" + webElement.toString() + "]>" + GWCtrlMsg.ui_QUERY[0]);

          //noinspection IfStatementWithIdenticalBranches
          if(!bAutoCheck) {
              GWCtrlDivDoubleClick.ByWebElement(webDriver, webElement);
              WebElement input = GWCtrlQuery.ui_Q_V(webDriver, "id", id);
              GWCtrlHighLight.apply(webDriver, input, 1, "");
              input.sendKeys(Keys.DELETE);
              GLog.logRecordTime(0, "----<input[ENTER]>" + GWCtrlMsg.ui_INPUT[3]);
              ByIdUnClear(webDriver, id, str);
              //此处增加延时是为的避免自动化执行过快导致页面反应不过来
              GTime.pause(pauseTime);
              GWCtrlPage.ui_C_CLICK_BLANK_SPACE(webDriver,"id", blankId);
          }else {
              GWCtrlQuery.ui_V(webDriver, webElement);
              WebElement input = GWCtrlQuery.ui_Q_V(webDriver, "id", id);
              if(!bAutoCheck) {
                  input.clear();
                  GWCtrlPage.ui_C_CLICK_BLANK_SPACE(webDriver,"id", blankId);
              }
              input.sendKeys(str);
              GLog.logRecordTime(0, "----<input[" + str + "]>" + GWCtrlMsg.ui_INPUT[1]);
              input.sendKeys(Keys.ENTER);
              GLog.logRecordTime(0, "----<input[ENTER]>" + GWCtrlMsg.ui_INPUT[0]);
              //此处增加延时是为的避免自动化执行过快导致页面反应不过来
              GTime.pause(pauseTime);
              GWCtrlPage.ui_C_CLICK_BLANK_SPACE(webDriver,"id", blankId);
          }
          
      }catch (Exception e) {
          GWCtrlException.switchTo(webDriver, e, 1, 0, "----<exception[input[" + webElement.toString() + "]" + GWCtrlMsg.ui_INPUT[4] + "]", true);
      }
      GLog.logRecordTime(0, "]]----[input]----[widget]");
    }
	
	
	   /**
     	*  通过双击div元素临时生成的Input类目标元素，通过WebElement地位div，id查找input，写入指定内容，
     	*  悬浮，选择悬浮控件中的期望值
		*
		*  @param webDriver 浏览器驱动对象
     	*  @param webElement WebElement参数
     	*  @param id id参数
     	*  @param str 输入的字符串
     	*  @param blankId 空白
		*
		*  @return 是否点击
     	*/
    public static Boolean ByWebElementFromLayerDiv(WebDriver webDriver,
												   WebElement webElement,
												   String id,
												   String str,
												   String blankId) {
        GLog.logRecordTime(0, "[widget]----[input]----[[");
        boolean isClick = false;
        try {
            GLog.logRecordTime(0, "----<input[" + webElement.toString() + "]>" + GWCtrlMsg.ui_QUERY[0]);        
            GWCtrlDivDoubleClick.ByWebElement(webDriver, webElement);
            WebElement input = GWCtrlQuery.ui_Q_V(webDriver, "id", id);
            GWCtrlHighLight.apply(webDriver, input, 1, "");
            input.sendKeys(Keys.DELETE);
            input.sendKeys(str);
            for (int i = 0; i < 10; i++) {
              if (!isClick) {
                isClick =GWCtrlLayer.ByLikeText(webDriver, str);
                GWCtrlTime.Pause(webDriver, 1);
              }else {
                break;
              }
            }
            GWCtrlPage.ui_C_CLICK_BLANK_SPACE(webDriver,"id", blankId);
            GLog.logRecordTime(0, "----<input[ENTER]>" + GWCtrlMsg.ui_INPUT[3]);
        }catch (Exception e) {
            GWCtrlException.switchTo(webDriver, e, 1, 0, "----<exception[input[" + webElement.toString() + "]" + GWCtrlMsg.ui_INPUT[4] + "]", true);
        }
        GLog.logRecordTime(0, "]]----[input]----[widget]");
        return isClick;
    }
	
	/**
	 * 通过双击div元素临时生成的input类目标元素，通过WebElement地位div，id查找input，写入指定内容(适用于类别)
	 *
	 * @param webDriver 浏览器驱动对象
	 * @param inputWebElement inputWebElement参数
	 * @param id id参数
	 * @param str 输入的字符串
	 * @param blank 空白处id参数
	 * @param bAutoCheck 是否需要先清空后输入
	 */
	public static void ByWebElementFromClickDivCategory(WebDriver webDriver,
														WebElement inputWebElement,
														String id,
														String str,
														String blank,
														boolean bAutoCheck) {
		GLog.logRecordTime(0, "[widget]----[input]----[[");
		try {
			WebElement input;
			if(!bAutoCheck) {
				GWCtrlDivDoubleClick.ByWebElement(webDriver, inputWebElement);
				input = webDriver.findElement(By.id(id));
				GWCtrlHighLight.apply(webDriver, input, 1, "");
				JavascriptExecutor js=(JavascriptExecutor) webDriver;
				js.executeScript("arguments[0].scrollIntoView(true);",input);
				GWCtrlWait.ViewWaitingAllByWebElement(webDriver, GTestIndicators.PageShowTime, input);
				input.sendKeys(Keys.DELETE);
				GLog.logRecordTime(0, "----<input<keyboard[DELETE]>>" + GWCtrlMsg.ui_INPUT[0]);
				GWCtrlInputFill.ById(webDriver, id, str);
				GLog.logRecordTime(0, "----<input<id[" + id + "];str[" + str + "]>>" + GWCtrlMsg.ui_INPUT[1]);
			}
		}catch (Exception e) {
			GWCtrlException.switchTo(webDriver, e, 1, 0, "----<exception[input[" + id + "]" + GWCtrlMsg.ui_INPUT[4] + "]", true);
		}
		GLog.logRecordTime(0, "]]----[input]----[widget]");
	}
	
	
	/**
	 * 适用于类别中下拉框
	 *
	 * @param webDriver 浏览器驱动对象
	 * @param inputWebElement inputWebElement参数
	 * @param id id参数
	 * @param str 输入的字符串
	 * @param blank 空白处id参数
	 * @param bAutoCheck 是否需要先清空后输入
	 */
	public static void ByWebElementFromDropDownCategory(WebDriver webDriver,
														WebElement inputWebElement,
														String id,
														String str,
														String blank,
														boolean bAutoCheck) {
		GLog.logRecordTime(0, "[widget]----[input]----[[");
		try {
			WebElement input;
			if(!bAutoCheck) {
				GWCtrlDivDoubleClick.ByWebElement(webDriver, inputWebElement);
				input = webDriver.findElement(By.id(id));
				GWCtrlHighLight.apply(webDriver, input, 1, "");
				JavascriptExecutor js=(JavascriptExecutor) webDriver;
				js.executeScript("arguments[0].scrollIntoView(true);",input);
				GWCtrlWait.ViewWaitingAllByWebElement(webDriver, GTestIndicators.PageShowTime, input);
				input.sendKeys(Keys.DELETE);
				GLog.logRecordTime(0, "----<input<keyboard[DELETE]>>" + GWCtrlMsg.ui_INPUT[0]);
				GWCtrlDropDown.ByElement(webDriver, input, str);
				GLog.logRecordTime(0, "----<input<id[" + id + "];str[" + str + "]>>" + GWCtrlMsg.ui_INPUT[1]);
			}
		}catch (Exception e) {
			GWCtrlException.switchTo(webDriver, e, 1, 0, "----<exception[input[" + id + "]" + GWCtrlMsg.ui_INPUT[4] + "]", true);
		}
		GLog.logRecordTime(0, "]]----[input]----[widget]");
  }
	/**
     * 适用于下拉框是单位类型
	 *
	 * @param webDriver 浏览器驱动对象
     * @param inputWebElement inputWebElement参数
     * @param id id参数
     * @param str 输入的字符串
     * @param bAutoCheck 是否需要先清空后输入
     */
    public static void ByWebElementFromDropDownUnit(WebDriver webDriver,
													WebElement inputWebElement,
													String id,
													String str,
													boolean bAutoCheck) {
		GLog.logRecordTime(0, "[widget]----[input]----[[");
		try {
			WebElement input;
			if(!bAutoCheck) {
				GWCtrlDivDoubleClick.ByWebElement(webDriver, inputWebElement);
				input = webDriver.findElement(By.id(id));
				GWCtrlHighLight.apply(webDriver, input, 1, "");
				JavascriptExecutor js=(JavascriptExecutor) webDriver;
				js.executeScript("arguments[0].scrollIntoView(true);",input);
				GWCtrlWait.ViewWaitingAllByWebElement(webDriver, GTestIndicators.PageShowTime, input);
				input.sendKeys(Keys.DELETE);
				GLog.logRecordTime(0, "----<input<keyboard[DELETE]>>" + GWCtrlMsg.ui_INPUT[0]);
				GWCtrlDropDown.ByElementUnit(webDriver, input, str);
				GLog.logRecordTime(0, "----<input<id[" + id + "];str[" + str + "]>>" + GWCtrlMsg.ui_INPUT[1]);
			}
		}catch (Exception e) {
			GWCtrlException.switchTo(webDriver, e, 1, 0, "----<exception[input[" + id + "]" + GWCtrlMsg.ui_INPUT[4] + "]", true);
		}
		GLog.logRecordTime(0, "]]----[input]----[widget]");
	}
    
	/**
     * 适用于可输可选日期类型
	 *
	 * @param webDriver 浏览器驱动对象
     * @param inputWebElement inputWebElement参数
     * @param id id参数
     * @param str 输入的字符串
     * @param bAutoCheck 是否需要先清空后输入
     */
    public static void ByWebElementFromDate(WebDriver webDriver,
											WebElement inputWebElement,
											String id,
											String str,
											boolean bAutoCheck) {
		GLog.logRecordTime(0, "[widget]----[input]----[[");
		try {
			WebElement input;
			if(!bAutoCheck) {
				GWCtrlDivDoubleClick.ByWebElement(webDriver, inputWebElement);
				input = webDriver.findElement(By.id(id));
				GWCtrlHighLight.apply(webDriver, input, 1, "");
				JavascriptExecutor js=(JavascriptExecutor) webDriver;
				js.executeScript("arguments[0].scrollIntoView(true);",input);
				GWCtrlWait.ViewWaitingAllByWebElement(webDriver, GTestIndicators.PageShowTime, input);
				input.sendKeys(Keys.DELETE);
				GLog.logRecordTime(0, "----<input<keyboard[DELETE]>>" + GWCtrlMsg.ui_INPUT[0]);
				GWCtrlDate.ByValue(webDriver, id, str);
				GLog.logRecordTime(0, "----<input<id[" + id + "];str[" + str + "]>>" + GWCtrlMsg.ui_INPUT[1]);
			}
		}catch (Exception e) {
			GWCtrlException.switchTo(webDriver, e, 1, 0, "----<exception[input[" + id + "]" + GWCtrlMsg.ui_INPUT[4] + "]", true);
		}
		GLog.logRecordTime(0, "]]----[input]----[widget]");
	}
}
