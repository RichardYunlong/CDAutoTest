package Webdriver;

import DT.GLog;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 常用元素iframe
 */
public class GWCtrlWebElementIframe {
	
	/**
	 * 已加载的iframe
	 */
	@SuppressWarnings({"CanBeFinal", "FieldMayBeFinal"})
    private static Map<String, WebElement> CN_IFRAME = new HashMap<>();
	
	public static Map<String, WebElement> getCN_IFRAME() {
		return CN_IFRAME;
	}

	/**
	 *  设置新iframe元素值
	 *
	 *  @param webDriver 驱动对象
	 *  @param tabIndex 页签序号 
	 *  @param waitByType 等待目标的条件类型 例如“id”，意为通过id来等待
	 *  @param waitByTar 等待目标的条件值  例如“main-content”，意为等待的目标元素id值为“main-content”
	 */
	@SuppressWarnings("CallToPrintStackTrace")
    public static void setIframe(WebDriver webDriver, int tabIndex, String waitByType, String waitByTar){
		GLog.logRecordTime(9,  "----<iframe<index[" + tabIndex + "];waitByType[" + waitByType + "];waitByTar[" + waitByTar + "]>>");
		WebElement webElement;
		WebElement webElementIframe = null;
		try {			
			webElement = GWCtrlQuery.ui_Q(webDriver, waitByType, waitByTar, "div", "class");
			List<WebElement> iframes = webElement.findElements(By.tagName("iframe"));
			//第一个找到的即为目标元素“最外层的iframe”，返回该元素
			for(WebElement iframe:iframes){
				//取得属性src对应的值
				String strAttribute = iframe.getAttribute("src");
				if(strAttribute.contains("portalPage")) {//如果src对应的值中包含“portalPage”字符串，则正面当前页面iframe为【门户页】，即【我的桌面】
					GLog.logRecordTime(9,  "----<iframe[add[我的桌面]]>");
				}else if (strAttribute.contains("ListPage")) {//如果src对应的值中包含“ListPage”字符串，则正面当前页面iframe为【列表页签】
					GLog.logRecordTime(9,  "----<iframe[add[列表区]]>");
				}else if (strAttribute.contains("EditPage")) {//如果src对应的值中包含“EditPage”字符串，则正面当前页面iframe为【编辑页签】
					GLog.logRecordTime(9,  "----<iframe[add[详情区]]>");
				}else{
					GLog.logRecordTime(9,  "----<iframe[add[未知]]>");
				}
				webElementIframe = iframe;
				break;
			}
			
			CN_IFRAME.put(String.valueOf(tabIndex), webElementIframe);
		} catch (Exception e){
			GLog.logRecordTime(9,  "----<iframe>增加失败");
			e.printStackTrace();
		}
	}
	
	/**
	 * 获取iframe
	 * 
	 * @param tabIndex 页面序号
	 * 
	 * @return 页面的WebElement对象
	 */
	public static WebElement getIframe(int tabIndex){
		return CN_IFRAME.get(String.valueOf(tabIndex));
	}
	
	/**
	 * 替换iframe
	 * 
	 * @param tabIndex 页面序号
	 * @param webElement 要替换的页面WebElement对象
	 * 
	 * @return 新的页面WebElement对象
	 */
	public static WebElement replaceIframe(int tabIndex, WebElement webElement){
		return CN_IFRAME.replace(String.valueOf(tabIndex), webElement);
	}
}
