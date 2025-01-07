package main.java.yonbip.table;

import main.java.DT.GLog;
import main.java.yonbip.base.WebElementArrayList;
import org.openqa.selenium.WebDriver;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 *  listTable具有独特的行驱动和单元格驱动方式
 *  增加了凭证类表格的单元格操作方式
 *  
 *  @author hewei
 *  
 *  @version 20220425205200
 */
public class ListTable extends EnhanceGridTable {
	
	/**
	 *  表头字段操作表 如果表格没有没有锁定区，则可使用此成员调用元素
	 */
	@SuppressWarnings({"FieldCanBeLocal", "FieldMayBeFinal"})
	private WebElementArrayList header = null;
	
	/**
	 *字段名和字段标识的对应关系
	 */
	@SuppressWarnings("FieldCanBeLocal")
    private Map<String, String> colName_colIdent = null;

	/**
	 *  表头字段操作表
	 *
	 * @param webDriver 浏览器驱动
	 * @param headerType 表头类型
	 * @param locateTagName 元素标签名
	 * @param locateAtrributeName 元素属性名称
	 * @param locateArributeValue 元素属性值
	 */
	public ListTable(WebDriver webDriver, String headerType, String locateTagName, String locateAtrributeName, String locateArributeValue) {
		super(webDriver, headerType, locateTagName, locateAtrributeName, locateArributeValue);
		
		if(null != super.getGridShow()) colName_colIdent = new LinkedHashMap<>();
	}
	
	/**
	 *  列表类表格表头加载方式实现方法
	 *  
	 * @param headerType single-单表头；multi-多级表头
	 */
	public void initHeader(String headerType) {
		GLog.logRecordTime(0, "调用ListTable类方法----initHeader");
	}
}
