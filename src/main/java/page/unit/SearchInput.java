package page.unit;

import DT.GLog;
import Webdriver.GWCtrlInputFill;
import page.base.UniqueBase;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 *  应用中心搜索框
 *  
 *  @author hewei
 *  @version 20220418143500
 */
public class SearchInput extends UniqueBase {
	
	/**
	 *构造函数
	 *
	 * @param webDriver 目标驱动
	 * @param locateTagName 定位标签名
	 * @param locateAtrributeName 定位属性名
	 * @param locateArributeValue 定位属性值
	 */
	public SearchInput(WebDriver webDriver, String locateTagName, String locateAtrributeName, String locateArributeValue) {
		super(webDriver, locateTagName, locateAtrributeName, locateArributeValue);
	}
	
	/**
	 *查询
	 *
	 * @param webDriver 目标驱动
	 * @param content 搜索内容
	 */
	public void input(WebDriver webDriver, String content) {
		GWCtrlInputFill.ByWebElement(webDriver, super.getUniqueRoot(), content);
		GLog.logRecordTime(0, "查询[" + content + "]成功");
	}
	
	/**
	 *点击查询按钮
	 *
	 * @param webDriver 目标驱动
	 */
	public void click(WebDriver webDriver) {
		WebElement searchButton;
		searchButton = super.getChildElement(webDriver, "i", "class", "iconfont font_family_u8c__iconfont___2OzrA icon-guanbi__iconfont___3wJRC md__index___3HmlR search-input-x");
		
		searchButton.click();
	}
	
	/**
	 *点击清除按钮
	 *
	 * @param webDriver 目标驱动
	 */
	public void clear(WebDriver webDriver) {
		WebElement searchClear;
		searchClear = super.getChildElement(webDriver, "i", "class", "iconfont font_family_u8c__iconfont___2OzrA icon-sousuo__iconfont___17ipx md__index___3HmlR search-input-query");
		
		searchClear.click();
	}
}
