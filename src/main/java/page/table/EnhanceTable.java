package page.table;

import Base.GText;
import DT.GLog;
import org.openqa.selenium.By;
import page.baseused.WebElementArrayList;
import page.widget.Paging;
import page.widget.QueryScheme;
import org.openqa.selenium.WebDriver;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 *  加强grid类型的表格
 *  增加了查询条件区，操作按钮区，分页控制区，表头行读取的方法预置，需要具体的模块来实现
 *  
 *  @author hewei
 */
public class EnhanceTable extends TableBase {

	/**
	 *表格关键参数
	 */
	@SuppressWarnings({"CanBeFinal", "FieldMayBeFinal"})
    private Map<String, String> enhanceTableKeywords;

	/**
	 *字段名和字段标识的对应关系
	 */
	private Map<String, String> colName_colIdent = null;
	
	/**
	 *获得字段名和字段标识的对应关系
	 *
	 * @return 字段名和字段标识的对应关系
	 */
	public Map<String, String> getColName_colIdent() {
		return colName_colIdent;
	}

	/**
	 *查询方案对象
	 */
	@SuppressWarnings({"FieldMayBeFinal", "CanBeFinal"})
	private QueryScheme queryScheme = null;


	/**
	 *功能按钮对象
	 */
	@SuppressWarnings({"FieldMayBeFinal", "CanBeFinal"})
	private WebElementArrayList tooBar = null;
	
	/**
	 *获得功能按钮对象
	 *
	 * @return 功能按钮对象
	 */
	public WebElementArrayList getTooBar() {
		return tooBar;
	}

	/**
	 *分页控制对象
	 */
	@SuppressWarnings({"FieldMayBeFinal", "CanBeFinal", "RedundantSuppression"})
	private Paging paging = null;
	
	/**
	 *获得分页控制对象
	 *
	 * @return 分页控制对象
	 */
	public Paging getPaging() {
		return paging;
	}

	/**
	 *  表头字段操作表
	 *
	 * @param webDriver 浏览器驱动
	 * @param headerType 表头类型
	 * @param locateTagName 元素标签名
	 * @param locateAtrributeName 元素属性名称
	 * @param locateArributeValue 元素属性值
	 * @param locateArributeValueTableLayout 表格布局属性值
	 * @param locateArributeValueRowLayout 行布局属性值
	 * @param locateArributeValueVerticalScrollbar 垂直滚动条属性值
	 * @param locateArributeValueHorizontalScrollbar 水平滚动条属性值
	 */
	public EnhanceTable(WebDriver webDriver,
						String headerType,
						String locateTagName,
						String locateAtrributeName,
						String locateArributeValue,
						String locateArributeValueTableLayout,
						String locateArributeValueRowLayout,
						String locateArributeValueVerticalScrollbar,
						String locateArributeValueHorizontalScrollbar) {
		super(webDriver, locateTagName, locateAtrributeName, locateArributeValue, locateArributeValueRowLayout, locateArributeValueVerticalScrollbar, locateArributeValueHorizontalScrollbar);

		enhanceTableKeywords = new LinkedHashMap<>();
		enhanceTableKeywords.put("定位目标元素类型", locateTagName);
		enhanceTableKeywords.put("定位目标元素关键属性名称", locateAtrributeName);
		enhanceTableKeywords.put("定位目标元素关键属性值", locateArributeValue);
		enhanceTableKeywords.put("定位目标元素关键属性值_表格", locateArributeValueTableLayout);
		enhanceTableKeywords.put("定位目标元素关键属性值_表格行", locateArributeValueRowLayout);
		enhanceTableKeywords.put("定位目标元素关键属性值_垂直滚动条", locateArributeValueVerticalScrollbar);
		enhanceTableKeywords.put("定位目标元素关键属性值_水平滚动条", locateArributeValueHorizontalScrollbar);

		if(null != super.getGridRoot()) {
			colName_colIdent = new LinkedHashMap<>();
			initTooBar(webDriver);
			initHeader(webDriver, headerType);
			initPaging(webDriver);
			initRows(webDriver);
		}
	}
	
	/**
	 *  初始化功能按钮
	 *
	 * @param webDriver 浏览器驱动
	 */
	public void initTooBar(WebDriver webDriver) {
		GLog.logRecordTime(9, "调用EnhanceGridTable类方法----initTooBar");
	}

	/**
	 *  初始化表头类型
	 *  需要具体的表格类型重写
	 *
	 * @param webDriver 浏览器驱动
	 * @param headerType single-单表头；multi-多级表头
	 */
	public void initHeader(WebDriver webDriver, String headerType) {
		GLog.logRecordTime(9, "调用EnhanceGridTable类方法----initHeader");
	}

	/**
	 *  初始化分页控制
	 *
	 * @param webDriver 浏览器驱动
	 */
	public void initPaging(WebDriver webDriver) {
		paging = new Paging(webDriver, "div", "class", "pagination-new");
		paging.setSize(webDriver,1000);
	}

	/**
	 *  打印主要对象的hashcode
	 */
	public void showUnitsHash() {
		GLog.logRecordTime(9, "主要成员对象VVVV");
		GLog.logRecordTime(9, "queryScheme -> " + queryScheme.hashCode());
		GLog.logRecordTime(9, "tooBar -> " + tooBar.hashCode());
		GLog.logRecordTime(9, "paging -> " + paging.hashCode());
		GLog.logRecordTime(9, "主要成员对象^^^^");
	}

	/**
	 * 页面刷新，加载新元素
	 *
	 * @param webDriver 目标驱动
	 */
	public void initRows(WebDriver webDriver){
		String reTableCssSelector = GText.getCssSelectorTxt(enhanceTableKeywords.get("定位目标元素类型"), enhanceTableKeywords.get("定位目标元素关键属性名称"), enhanceTableKeywords.get("定位目标元素关键属性值"));
		super.setGridRoot(webDriver.findElement(By.cssSelector(reTableCssSelector)));
		super.initRows(webDriver, super.getGridRoot(), enhanceTableKeywords.get("定位目标元素类型"), enhanceTableKeywords.get("定位目标元素关键属性名称"), enhanceTableKeywords.get("定位目标元素关键属性值_表格行"));
	}

	/**
	 * 页面刷新，加载新元素
	 *
	 * @param webDriver 目标驱动
	 */
	public void reload(WebDriver webDriver){
		initRows(webDriver);
	}
}
