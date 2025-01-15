package page.table;

import DT.GLog;
import page.base.WebElementArrayList;
import page.unit.Paging;
import page.unit.QueryScheme;
import org.openqa.selenium.WebDriver;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 *  加强grid类型的表格
 *  增加了查询条件区，操作按钮区，分页控制区，表头行读取的方法预置，需要具体的模块来实现
 *  
 *  @author hewei
 *  
 *  @version 20220425203600
 */
public class EnhanceGridTable extends GridTable {
	
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
	 *获得查询方案对象
	 *
	 * @return 查询方案对象
	 */
	public QueryScheme getQueryScheme() {
		return queryScheme;
	}

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
	@SuppressWarnings({"FieldMayBeFinal", "CanBeFinal"})
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
	 */
	public EnhanceGridTable(WebDriver webDriver, String headerType, String locateTagName, String locateAtrributeName, String locateArributeValue) {
		super(webDriver, locateTagName, locateAtrributeName, locateArributeValue);
		
		if(null != super.getGridShow()) {
			colName_colIdent = new LinkedHashMap<>();
			
			initQueryScheme();
			initTooBar();
			initPaging();
			
			initHeader(headerType);
		}
	}
	
	/**
	 *  初始化查询方案
	 */
	public void initQueryScheme() {
		GLog.logRecordTime(9, "调用EnhanceGridTable类方法----initQueryScheme");
	}
	
	/**
	 *  初始化功能按钮
	 */
	public void initTooBar() {
		GLog.logRecordTime(9, "调用EnhanceGridTable类方法----initTooBar");
	}
	
	/**
	 *  初始化分页控制
	 */
	public void initPaging() {
		GLog.logRecordTime(9, "调用EnhanceGridTable类方法----initPaging");
	}
	
	/**
	 *  初始化表头类型
	 *  需要具体的表格类型重写
	 *  
	 * @param headerType single-单表头；multi-多级表头
	 */
	public void initHeader(String headerType) {
		GLog.logRecordTime(9, "调用EnhanceGridTable类方法----initHeader");
	}
}
