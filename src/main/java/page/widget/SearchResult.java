package page.widget;

import DT.GLog;
import page.base.UniqueWebElementBase;
import page.baseused.WebElementArrayList;
import org.openqa.selenium.WebDriver;

/**
 *  应用中心搜索结果
 *  
 *  @author hewei
 *  @version 20220418152300
 */
public class SearchResult extends UniqueWebElementBase {
	
	/**
	 *路径区
	 */
	@SuppressWarnings({"FieldMayBeFinal", "CanBeFinal"})
	private WebElementArrayList searchResultH1s;
	
	/**
	 *点击区
	 */
	@SuppressWarnings({"FieldMayBeFinal", "CanBeFinal"})
	private WebElementArrayList searchResultSpans;
	
	/**
	 *构造函数
	 *
	 * @param webDriver 目标驱动
	 * @param locateTagName 定位标签名
	 * @param locateAtrributeName 定位属性名
	 * @param locateArributeValue 定位属性值
	 */
	public SearchResult(WebDriver webDriver, String locateTagName, String locateAtrributeName, String locateArributeValue) {
		super(webDriver, locateTagName, locateAtrributeName, locateArributeValue);
		
		searchResultH1s = new WebElementArrayList(super.getUniqueRoot(), "h1", "class", "searchTitle--kKACq");
		searchResultSpans = new WebElementArrayList(super.getUniqueRoot(), "span", "style", "color:#588CE9;");
	}
	
	/**
	 *构造函数
	 *
	 *@param name 结构结果名称
	 */
	public void click(String name) {
		searchResultSpans.click(name);
		GLog.logRecordTime(9, "点击[" + searchResultH1s.getWebElementArrayList().get(searchResultSpans.getIndex(name)) + "]");
	}
}
