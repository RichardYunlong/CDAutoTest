package main.java.page.base;

import main.java.Base.GText;
import main.java.DT.GLog;
import main.java.Webdriver.GTestIndicators;
import main.java.Webdriver.GWCtrlException;
import main.java.Webdriver.GWCtrlMsg;
import main.java.Webdriver.GWCtrlWait;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;
import java.util.Objects;

/**
 *  页面元素查询
 *  1.本类的所有静态方法都自带了智能等待；2."_Q"为定位函数；3."_V"为使对象可见；4."_K"为模糊查询；5."_T"为按文本定位，非必要情况不推荐使用
 *  
 *  @author hew-d
 */
public class QueryElement {
	
	/**
	 * id定位
	 * 使用系统默认的driver进行全局定位
	 *
	 * @param webDriver 主驱动对象
	 * @param id 能够确定元素唯一对象的id属性值
	 *  
	 * @return WebElement 唯一元素的WebElement对象
	 */
	public static WebElement ui_Q(WebDriver webDriver, String id){
		GLog.logRecordTime(0, "[query]----[WebElement]----[[");
		WebElement webElement = null;
		try {
			GLog.logRecordTime(0, "----<id[" + id + "]>" + GWCtrlMsg.ui_QUERY[0]);

			GWCtrlWait.ViewWaitingById(webDriver, GTestIndicators.PageShowTime, id);
			webElement = webDriver.findElement(By.id(id));

			GLog.logRecordTime(0, "----<WebElement[" + webElement.toString() + "]>" + GWCtrlMsg.ui_QUERY[1]);
		}catch (Exception e) {
			GWCtrlException.switchTo(webDriver, e, 1, 0, "----<exception[webElement" + GWCtrlMsg.ui_QUERY[2] + "]", true);
		}
		
		GWCtrlWait.ViewWaitingAllByWebElement(webDriver, GTestIndicators.PageShowTime, webElement);
		GLog.logRecordTime(0, "]]----[WebElement]----[query]");
		return webElement;
	}
	
	/**
	 *  表达式定位
	 *  使用系统默认的driver进行全局定位
	 *
	 * @param webDriver 主驱动对象
	 * @param expressionType 能够确定元素唯一对象的定位方式，目前支持id、cssSelector和xpath方式
	 * @param expressionValue 能够确定元素唯一对象的某条件表达式
	 *  
	 * @return WebElement 唯一元素的WebElement对象
	 */
	public static WebElement ui_Q(WebDriver webDriver, String expressionType, String expressionValue){
		GLog.logRecordTime(0, "[query]----[WebElement]----[[");
		WebElement webElement = null;
		try {
			GLog.logRecordTime(0, "----<WebElement[" + expressionType + ":" + expressionValue + "]>" + GWCtrlMsg.ui_QUERY[0]);
			switch(expressionType) {
				case "id":{
					GWCtrlWait.ViewWaitingById(webDriver, GTestIndicators.PageShowTime, expressionValue);
					webElement = webDriver.findElement(By.id(expressionValue));
					break;
				}
				case "cssSelector":{
					GWCtrlWait.ViewWaitingAllByCssSelector(webDriver, GTestIndicators.PageShowTime, expressionValue);
					webElement = webDriver.findElement(By.cssSelector(expressionValue));
					break;
				}
				case "xpath":{
					GWCtrlWait.ViewWaitingAllByXpath(webDriver, GTestIndicators.PageShowTime, expressionValue);
					webElement = webDriver.findElement(By.xpath(expressionValue));
					break;
				}
				default:{
					break;
				}
			}
			GLog.logRecordTime(0, "----<WebElement[" + Objects.requireNonNull(webElement) + "]>" + GWCtrlMsg.ui_QUERY[1]);
		}catch (Exception e) {
			GWCtrlException.switchTo(webDriver, e, 1, 0, "----<exception[webElement" + GWCtrlMsg.ui_QUERY[2] + "]", true);
		}
		
		GWCtrlWait.ViewWaitingAllByWebElement(webDriver, GTestIndicators.PageShowTime, webElement);
		GLog.logRecordTime(0, "]]----[WebElement]----[query]");
		return webElement;
	}
	
	/**
	 * 文本精确定位
	 * 使用系统默认的driver进行全局定位
	 *
	 * @param webDriver 主驱动对象
	 * @param parent 父元素
	 * @param type 能够确定元素唯一对象的类型
	 * @param text 能够确定元素唯一对象的文本值
	 *  
	 * @return WebElement 唯一元素的WebElement对象
	 */
	public static WebElement ui_T(WebDriver webDriver, WebElement parent, String type,String text){
		GLog.logRecordTime(0, "[query]----[WebElement]----[[");
		WebElement webElement = null;
		try {
			GLog.logRecordTime(0, "----<text[" + text + "]>" + GWCtrlMsg.ui_QUERY[0]);

			webElement = parent.findElement(By.xpath("//" + type + "[text()='" + text + "']"));
			ui_V(webDriver, webElement);
			
			GLog.logRecordTime(0, "----<WebElement[" + webElement.toString() + "]>" + GWCtrlMsg.ui_QUERY[1]);
		}catch (Exception e) {
			GWCtrlException.switchTo(webDriver, e, 1, 0, "----<exception[webElement" + GWCtrlMsg.ui_QUERY[2] + "]", true);
		}
		
		GWCtrlWait.ViewWaitingAllByWebElement(webDriver, GTestIndicators.PageShowTime, webElement);
		GLog.logRecordTime(0, "]]----[WebElement]----[query]");
		return webElement;
	}
	
	/**
	 * 文本模糊定位
	 * 使用系统默认的driver进行全局定位
	 *
	 * @param webDriver 主驱动对象
	 * @param parent 父元素
	 * @param type 能够模糊匹配元素唯一对象的类型
	 * @param keywords 能够模糊匹配元素唯一对象的文本值
	 *  
	 * @return WebElement 唯一元素的WebElement对象
	 */
	public static WebElement ui_T_K(WebDriver webDriver, WebElement parent, String type, String keywords){
		GLog.logRecordTime(0, "[query]----[WebElement]----[[");
		WebElement webElement = null;
		try {
			GLog.logRecordTime(0, "----<keywords[" + keywords + "]>" + GWCtrlMsg.ui_QUERY[0]);
			
			webElement = parent.findElement(By.xpath("//" + type + "[contains(text(),'" + keywords + "')]"));
			ui_V(webDriver, webElement);
			
			GLog.logRecordTime(0, "----<WebElement[" + webElement.toString() + "]>" + GWCtrlMsg.ui_QUERY[1]);
		}catch (Exception e) {
			GWCtrlException.switchTo(webDriver, e, 1, 0, "----<exception[webElement" + GWCtrlMsg.ui_QUERY[2] + "]", true);
		}
		
		GWCtrlWait.ViewWaitingAllByWebElement(webDriver, GTestIndicators.PageShowTime, webElement);
		GLog.logRecordTime(0, "]]----[WebElement]----[query]");
		return webElement;
	}
	
	/**
	 * 指定Webelement范围下表达式定位
	 *
	 * @param webDriver 主驱动对象
	 * @param parent 父元素
	 * @param expressionType 能够确定元素唯一对象的某条件名称，目前支持id、cssSelector和xpath方式
	 * @param expressionValue 能够确定元素唯一对象的某条件取值
	 * 
	 * @return WebElement 唯一元素的WebElement对象
	 */
	public static WebElement ui_Q(WebDriver webDriver, WebElement parent, String expressionType, String expressionValue){
        GLog.logRecordTime(0, "[query]----[WebElement]----[[");
        WebElement webElement = null;
        try {
            GLog.logRecordTime(0, "----<WebElement[" + expressionType + ":" + expressionValue + "]>" + GWCtrlMsg.ui_QUERY[0]);
            if (parent == null) {
              webElement = ui_Q(webDriver, expressionType, expressionValue);
            }else {
              switch(expressionType) {
                case "id":{
                    GWCtrlWait.ViewWaitingById(webDriver, GTestIndicators.PageShowTime, expressionValue);
                    webElement = parent.findElement(By.id(expressionValue));
                    break;
                }
                case "cssSelector":{
                    GWCtrlWait.ViewWaitingAllByCssSelector(webDriver, GTestIndicators.PageShowTime, expressionValue);
                    webElement = parent.findElement(By.cssSelector(expressionValue));
                    break;
                }
                case "xpath":{
                    webElement = parent.findElement(By.xpath(expressionValue));
                    break;
                }
                default:{
                    break;
                }
            }
            }
            GLog.logRecordTime(0, "----<WebElement[" + Objects.requireNonNull(webElement) + "]>" + GWCtrlMsg.ui_QUERY[1]);
        }catch (Exception e) {
            GWCtrlException.switchTo(webDriver, e, 1, 0, "----<exception[webElement" + GWCtrlMsg.ui_QUERY[2] + "]", true);
        }
        
        GWCtrlWait.ViewWaitingAllByWebElement(webDriver, GTestIndicators.PageShowTime, webElement);
        GLog.logRecordTime(0, "]]----[WebElement]----[query]");
        return webElement;
    }
	
	/**
	 *  指定Webelement的第n层父节点元素
	 *
	 * @param webDriver 主驱动对象
	 * @param cur 查询起始元素，可为空null
	 * @param n 移动到目标元素的父级第n层
	 * 
	 * @return WebElement 唯一元素的WebElement对象
	 */
	@SuppressWarnings("CommentedOutCode")
    public static WebElement ui_Q(WebDriver webDriver, WebElement cur, int n){
        GLog.logRecordTime(0, "[query]----[WebElement]----[[");
        WebElement webElement = cur;
        try {
            GLog.logRecordTime(0, "----<WebElement parent[" + n + "]>" + GWCtrlMsg.ui_QUERY[0]);
            for (int i = 0; i < n; i++) {
              webElement = ui_Q(webDriver, webElement,"xpath", "..");
//              GLog.logRecordTime(0, "第:" + String.valueOf(i + 1) + "层");
//              GLog.logRecordTime(0, "text:" + webElement.getText());
//              GLog.logRecordTime(0, "id:" + webElement.getAttribute("id"));
//              GLog.logRecordTime(0, "class:" + webElement.getAttribute("class"));
//              GLog.logRecordTime(0, "title:" + webElement.getAttribute("title"));
            }
            GLog.logRecordTime(0, "----<WebElement parent[" + n + "][" + webElement.toString() + "]>" + GWCtrlMsg.ui_QUERY[1]);
        }catch (Exception e) {
            GWCtrlException.switchTo(webDriver, e, 1, 0, "----<exception[webElement" + GWCtrlMsg.ui_QUERY[2] + "]", true);
        }
        
        GWCtrlWait.ViewWaitingAllByWebElement(webDriver, GTestIndicators.PageShowTime, webElement);
        GLog.logRecordTime(0, "]]----[WebElement]----[query]");
        return webElement;
    }
	
	/**
	 *  指定Webelement范围，表达式定位，取其第n层的父节点元素
	 *
	 * @param webDriver 主驱动对象
	 * @param parent 查询起始元素，可为空null
	 * @param expressionType 能够确定元素唯一对象的某条件名称，目前支持id、cssSelector和xpath方式
	 * @param expressionValue 能够确定元素唯一对象的某条件取值
	 * @param n 移动到目标元素的父级第n层
	 * 
	 * @return WebElement 唯一元素的WebElement对象
	 */
	public static WebElement ui_Q(WebDriver webDriver, WebElement parent, String expressionType, String expressionValue, int n){
        GLog.logRecordTime(0, "[query]----[WebElement]----[[");
        WebElement webElement = null;
        try {
            GLog.logRecordTime(0, "----<WebElement[" + expressionType + ":" + expressionValue + "]>" + GWCtrlMsg.ui_QUERY[0]);
            if (parent==null) {
              webElement = ui_Q(webDriver, expressionType, expressionValue);
            }else {
              webElement = ui_Q(webDriver, parent, expressionType, expressionValue);
            }
            for (int i = 0; i < n; i++) {
              webElement = ui_Q(webDriver, webElement,"xpath", "..");
            }
            GLog.logRecordTime(0, "----<WebElement[" + webElement.toString() + "]>" + GWCtrlMsg.ui_QUERY[1]);
        }catch (Exception e) {
            GWCtrlException.switchTo(webDriver, e, 1, 0, "----<exception[webElement" + GWCtrlMsg.ui_QUERY[2] + "]", true);
        }
        
        GWCtrlWait.ViewWaitingAllByWebElement(webDriver, GTestIndicators.PageShowTime, webElement);
        GLog.logRecordTime(0, "]]----[WebElement]----[query]");
        return webElement;
    }
	
	/**
	 *  属性值定位
	 *  
	 *  使用系统默认的driver进行全局定位
	 *
	 * @param webDriver 主驱动对象
	 * @param tagType 能够确定元素唯一对象的定位方式，目前支持id、cssSelector和xpath方式
	 * @param tagAttributeValue 能够确定元素唯一对象的属性值
	 * @param tagName 能够确定元素唯一对象的属性所在标签类型
	 * @param tagAttributeName 能够确定元素唯一对象的属性名称
	 *  
	 * @return WebElement 唯一元素的WebElement对象
	 */
	public static WebElement ui_Q(WebDriver webDriver, String tagType, String tagAttributeValue, String tagName, String tagAttributeName){
		GLog.logRecordTime(0, "[query]----[WebElement]----[[");
		WebElement webElement = null;
		try {
			GLog.logRecordTime(0, "----<WebElement[" + tagType + ":" + tagAttributeValue + "]>" + GWCtrlMsg.ui_QUERY[0]);
			switch(tagType) {
				case "id":{
					GWCtrlWait.ViewWaitingById(webDriver, GTestIndicators.PageShowTime, tagAttributeValue);
					webElement = webDriver.findElement(By.id(tagAttributeValue));
					break;
				}
				case "cssSelector":{
					GWCtrlWait.ViewWaitingByCssSelector(webDriver, GTestIndicators.PageShowTime, GText.getCssSelectorTxt(tagName, tagAttributeName, tagAttributeValue));
					webElement = webDriver.findElement(By.cssSelector(GText.getCssSelectorTxt(tagName, tagAttributeName, tagAttributeValue)));
					break;
				}
				case "xpath":{
					GWCtrlWait.ViewWaitingAllByXpath(webDriver, GTestIndicators.PageShowTime, tagAttributeValue);
					webElement = webDriver.findElement(By.xpath(tagAttributeValue));
					break;
				}
				default:{
					break;
				}
			}
			GLog.logRecordTime(0, "----<WebElement[" + Objects.requireNonNull(webElement) + "]>" + GWCtrlMsg.ui_QUERY[1]);
		}catch (Exception e) {
			GWCtrlException.switchTo(webDriver, e, 1, 0, "----<exception[webElement" + GWCtrlMsg.ui_QUERY[2] + "]", true);
		}
		
		GWCtrlWait.ViewWaitingAllByWebElement(webDriver, GTestIndicators.PageShowTime, webElement);
		GLog.logRecordTime(0, "]]----[WebElement]----[query]");
		return webElement;
	}
	
	/**
	 *  使元素可见
	 *
	 * @param webDriver 主驱动对象
	 * @param webElement 能够确定元素唯一对象的WebElement对象
	 *  
	 * @return WebElement 唯一可见元素的WebElement对象
	 */
	public static WebElement ui_V(WebDriver webDriver, WebElement webElement){
		GLog.logRecordTime(0, "[visible]----[WebElement]----[[");
		
		try {
			GWCtrlWait.ViewWaitingAllByWebElement(webDriver, GTestIndicators.PageShowTime, webElement);
			JavascriptExecutor js=(JavascriptExecutor) webDriver;
			js.executeScript("arguments[0].scrollIntoView(true);",webElement);
			GLog.logRecordTime(0, "----<WebElement[" + webElement.toString() + "]>" + GWCtrlMsg.ui_QUERY[3]);
		}catch (Exception e) {
			GWCtrlException.switchTo(webDriver, e, 1, 0, "----<exception[webElement" + GWCtrlMsg.ui_QUERY[4] + "]", true);
		}
		
		GWCtrlWait.ViewWaitingAllByWebElement(webDriver, GTestIndicators.PageShowTime, webElement);
		GLog.logRecordTime(0, "]]----[WebElement]----[visible]");
		return webElement;
	}
	
	/**
	 *  等待该元素加载完成，不移动
	 *
	 * @param webDriver 主驱动对象
	 * @param webElement 能够确定元素唯一对象的WebElement对象
	 *  
	 * @return WebElement 唯一元素的WebElement对象
	 */
	public static WebElement ui_V_NOMOVE(WebDriver webDriver, WebElement webElement){
		GLog.logRecordTime(0, "[visible]----[WebElement]----[[");
		try {
			GWCtrlWait.ViewWaitingAllByWebElement(webDriver, GTestIndicators.PageShowTime, webElement);
			GLog.logRecordTime(0, "----<WebElement[" + webElement.toString() + "]>" + GWCtrlMsg.ui_QUERY[3]);
		}catch (Exception e) {
			GWCtrlException.switchTo(webDriver, e, 1, 0, "----<exception[webElement" + GWCtrlMsg.ui_QUERY[4] + "]", true);
		}
		
		GWCtrlWait.ViewWaitingAllByWebElement(webDriver, GTestIndicators.PageShowTime, webElement);
		GLog.logRecordTime(0, "]]----[WebElement]----[visible]");
		return webElement;
	}
	
	/**
	 * 表达式定位，并使其可见
	 *  使用系统默认的driver进行全局定位
	 *
	 * @param webDriver 主驱动对象
	 * @param expressionType 能够确定元素唯一对象的某条件名称，目前支持id、cssSelector和xpath方式
	 * @param expressionValue 能够确定元素唯一对象的某条件取值
	 *  
	 * @return WebElement 唯一可见元素的WebElement对象
	 */
	public static WebElement ui_Q_V(WebDriver webDriver, String expressionType, String expressionValue){
		WebElement webElement;

		webElement = ui_Q(webDriver, expressionType, expressionValue);
		ui_V(webDriver, webElement);
		
		return webElement;
	}
	
	/**
	 *  指定WebElement范围内，表达式定位第一个，并使其可见
	 * 1.在指定范围内定位
	 * 2.在指定范围内定位
	 *
	 * @param webDriver 主驱动对象
	 * @param parent 已有元素
	 * @param expressionType 能够确定元素唯一对象的某条件名称，目前支持id、cssSelector和xpath方式
	 * @param expressionValue 能够确定元素唯一对象的某条件取值
	 *  
	 *  @return WebElement 首次出现的WebElement对象
	 */
	public static WebElement ui_Q_V(WebDriver webDriver, WebElement parent, String expressionType, String expressionValue){
		GLog.logRecordTime(0, "[query]----[WebElement]----[[");
		WebElement webElement = null;
		try {
			ui_V(webDriver, parent);
			
			List<WebElement> webElementTemps = null;
			switch(expressionType) {
				case "id":{
					GWCtrlWait.ViewWaitingById(webDriver, GTestIndicators.PageShowTime, expressionValue);
					webElementTemps = parent.findElements(By.id(expressionValue));
					break;
				}
				case "cssSelector":{
					GWCtrlWait.ViewWaitingAllByCssSelector(webDriver, GTestIndicators.PageShowTime, expressionValue);
					webElementTemps = parent.findElements(By.cssSelector(expressionValue));
					break;
				}
				case "xpath":{
					GWCtrlWait.ViewWaitingAllByXpath(webDriver, GTestIndicators.PageShowTime, expressionValue);
					webElementTemps = parent.findElements(By.xpath(expressionValue));
					break;
				}
				default:{
					break;
				}
			}
			
			if(webElementTemps != null && !webElementTemps.isEmpty()) {
				for(WebElement webElementTemp:webElementTemps) {
					webElement = webElementTemp;
					break;
				}
			}
			
			ui_V(webDriver, webElement);
			
			GLog.logRecordTime(0, "----<WebElement[" + Objects.requireNonNull(webElement) + "]>" + GWCtrlMsg.ui_QUERY[1]);
		}catch (Exception e) {
			GWCtrlException.switchTo(webDriver, e, 1, 0, "----<exception[webElement" + GWCtrlMsg.ui_QUERY[2] + "]", true);
		}
		
		GWCtrlWait.ViewWaitingAllByWebElement(webDriver, GTestIndicators.PageShowTime, webElement);
		GLog.logRecordTime(0, "]]----[WebElement]----[query]");
		return webElement;
	}
	
	/**
	 *  指定表达式范围内，表达式定位，并使其可见
	 * 1.在指定范围内定位
	 * 2.由于在父节点中，使用给出的搜索条件搜索目标可能会有若干结果，本方法取“首次出现的”元素为目标元素返回
	 *
	 * @param webDriver 主驱动对象
	 * @param parentExpressionType 能够确定目标父级元素唯一范围对象的某条件名称，目前支持id、cssSelector和xpath方式
	 * @param parentExpressionValue 能够确定目标父级元素唯一范围对象的某条件取值
	 * @param expressionType 能够确定元素唯一对象的某条件名称，目前支持id、cssSelector和xpath方式
	 * @param expressionValue 能够确定元素唯一对象的某条件取值
	 *  
	 *  @return WebElement 首次出现的WebElement对象
	 */
	public static WebElement ui_Q_V(WebDriver webDriver, String parentExpressionType, String parentExpressionValue,
									String expressionType, String expressionValue){
		GLog.logRecordTime(0, "[query]----[WebElement]----[[");
		WebElement webElement = null;
		try {
			WebElement parent;
			parent = ui_Q(webDriver, parentExpressionType, parentExpressionValue);
			
			webElement = ui_Q_V(webDriver, parent, expressionType, expressionValue);
			
			GLog.logRecordTime(0, "----<WebElement[" + webElement.toString() + "]>" + GWCtrlMsg.ui_QUERY[1]);
		}catch (Exception e) {
			GWCtrlException.switchTo(webDriver, e, 1, 0, "----<exception[webElement" + GWCtrlMsg.ui_QUERY[2] + "]", true);
		}
		
		GWCtrlWait.ViewWaitingAllByWebElement(webDriver, GTestIndicators.PageShowTime, webElement);
		GLog.logRecordTime(0, "]]----[WebElement]----[query]");
		return webElement;
	}
	
	/**
	 * 指定WebElement范围内，属性值定位，模糊查询，并使其可见
	 *
	 *  @param webDriver 主驱动对象
	 *  @param parent 已有元素
	 *  @param tagName 目标元素类型
	 *  @param tagAttibuteName 元素类属性名
	 *  @param tagAttibuteKeywords 部分关键字
	 *  
	 *  @return WebElement 唯一元素的WebElement对象
	 */
	public static WebElement ui_Q_K_V(WebDriver webDriver, WebElement parent, String tagName, String tagAttibuteName, String tagAttibuteKeywords){
		GLog.logRecordTime(0, "[query]----[WebElement]----[[");
		WebElement webElement = null;
		try {
			ui_V(webDriver, parent);
			
			List<WebElement> webElementTemps;
			
			webElementTemps = parent.findElements(By.tagName(tagName));
			for(WebElement webElementTemp:webElementTemps) {
				if(webElementTemp.getAttribute(tagAttibuteName).contains(tagAttibuteKeywords)) {
					webElement = webElementTemp;
					ui_V_NOMOVE(webDriver, webElement);
					GLog.logRecordTime(0, "----<WebElement<" + tagName + "[" + tagAttibuteName + "包含" + tagAttibuteKeywords + "]>>" + GWCtrlMsg.ui_QUERY[1]);
					break;
				}
			}
		}catch (Exception e) {
			GWCtrlException.switchTo(webDriver, e, 1, 0, "----<exception[webElement" + GWCtrlMsg.ui_QUERY[2] + "]", true);
		}
		
		GWCtrlWait.ViewWaitingAllByWebElement(webDriver, GTestIndicators.PageShowTime, webElement);
		GLog.logRecordTime(0, "]]----[WebElement]----[query]");
		return webElement;
	}
	
	/**
	 * 指定表达式范围内，属性值定位，模糊查询，并使其可见
	 * 1.在指定范围内定位
	 * 2.只知道字符串和表达式的部分关键字
	 *
	 * @param webDriver 主驱动对象
	 * @param parentExpressionType 能够确定目标父级元素唯一范围对象的某条件名称，目前支持id、cssSelector和xpath方式
	 * @param parentExpressionValue 能够确定目标父级元素唯一范围对象的某条件取值
	 * @param tagName 目标元素类型
	 * @param tagAttibuteName 元素类属性名
	 * @param tagAttibuteKeywords 部分关键字
	 *  
	 * @return WebElement 唯一元素的WebElement对象
	 */
	public static WebElement ui_Q_K_V(WebDriver webDriver, String parentExpressionType, String parentExpressionValue, String tagName, String tagAttibuteName, String tagAttibuteKeywords){
		GLog.logRecordTime(0, "[query]----[WebElement]----[[");
		WebElement webElement = null;
		try {
			WebElement parent;
			parent = ui_Q(webDriver, parentExpressionType, parentExpressionValue);
			webElement = ui_Q_K_V(webDriver, parent, tagName, tagAttibuteName, tagAttibuteKeywords);

		}catch (Exception e) {
			GWCtrlException.switchTo(webDriver, e, 1, 0, "----<exception[webElement" + GWCtrlMsg.ui_QUERY[2] + "]", true);
		}
		
		GWCtrlWait.ViewWaitingAllByWebElement(webDriver, GTestIndicators.PageShowTime, webElement);
		GLog.logRecordTime(0, "]]----[WebElement]----[query]");
		return webElement;
	}
}
