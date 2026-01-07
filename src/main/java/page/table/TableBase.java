package page.table;

import Base.GText;
import DT.GLog;
import Sys.GStatic;
import Webdriver.GTestIndicators;
import Webdriver.GWCtrlQuery;
import Webdriver.GWCtrlWait;
import page.base.UniqueWebElementBase;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 *  grid类型的表格
 *  grid类型的表格,暂时找到的公共特征是【行驱动方式】
 *  
 *  @author hewei
 */
public class TableBase extends UniqueWebElementBase {

	/**
	 *表格关键参数
	 */
	@SuppressWarnings({"FieldMayBeFinal", "CanBeFinal"})
    private Map<String, String> tableKeywords = null;

	public Map<String, String> getTableKeywords() {
		return tableKeywords;
	}

	public void setTableKeywords(Map<String, String> tableKeywords) {
		this.tableKeywords.putAll(tableKeywords);
	}

	/**
	 *行管理器
	 *1.暂存当前表格所有
	 *2.如有分页，则仅显示当前页所有行
	 *3.无论浏览器窗口是否显示，都暂存
	 */
	private List<WebElement> rows = null; 
	
	/**
	 *得到行管理器
	 *
	 * @return 行管理器
	 */
	public List<WebElement> getRows() {
		return rows;
	}

	/**
	 *表格的WebElement对象
	 */
	@SuppressWarnings({"FieldMayBeFinal", "CanBeFinal", "RedundantSuppression"})
	private WebElement gridRoot;

	public void setGridRoot(WebElement gridRoot) {
		this.gridRoot = gridRoot;
	}

	/**
	 *获得表格的WebElement对象
	 *
	 * @return 表格的WebElement对象
	 */
	public WebElement getGridRoot() {
		return gridRoot;
	}

	/**
	 *表格垂直滚动条
	 */
	private WebElement gridVerticalScrollbar = null;
	
	/**
	 *获得表格垂直滚动条
	 *
	 * @return 表格垂直滚动条
	 */
	public WebElement getGridVerticalScrollbar() {
		return gridVerticalScrollbar;
	}
	
	/**
	 *表格水平滚动条
	 */
	private WebElement gridHorizontalScrollbar = null;
	
	/**
	 *获得表格水平滚动条
	 *
	 * @return 表格水平滚动条
	 */
	public WebElement getGridHorizontalScrollbar() {
		return gridHorizontalScrollbar;
	}

	/**
	 *构造函数
	 *
	 * @param webDriver 浏览器驱动对象
	 * @param locateTagName 元素标签名
	 * @param locateAtrributeName 元素属性名称
	 * @param locateArributeValue 元素属性值
	 * @param locateArributeValueRowLayout 行布局属性值
	 * @param locateArributeValueVerticalScrollbar 垂直滚动条属性值
	 * @param locateArributeValueHorizontalScrollbar 水平滚动条属性值
	 */
	public TableBase(WebDriver webDriver,
					 String locateTagName,
					 String locateAtrributeName,
					 String locateArributeValue,
					 String locateArributeValueRowLayout,
					 String locateArributeValueVerticalScrollbar,
					 String locateArributeValueHorizontalScrollbar) {
		super(webDriver, locateTagName, locateAtrributeName, locateArributeValue);
		
		if(null == super.getUniqueRoot()) {
			gridRoot = GWCtrlQuery.ui_Q(webDriver, "cssSelector", GText.getCssSelectorTxt(locateTagName, locateAtrributeName, locateArributeValue));
			if(null != gridRoot){
				GWCtrlWait.ViewWaitingAllByWebElement(webDriver, GTestIndicators.PageShowTime, gridRoot);
			}
		}else {
			gridRoot = super.getUniqueRoot();
		}

		if(null != gridRoot) {
			GWCtrlWait.ViewWaitingAllByWebElement(webDriver, GTestIndicators.PageShowTime, gridRoot);
			initRows(webDriver, gridRoot, locateTagName, locateAtrributeName, locateArributeValueRowLayout);
		}
		
		try {
			gridVerticalScrollbar = Objects.requireNonNull(gridRoot).findElement(By.cssSelector(GText.getCssSelectorTxt(locateTagName, locateAtrributeName, locateArributeValueVerticalScrollbar)));
			if(null != gridVerticalScrollbar) {
				GWCtrlWait.ViewWaitingAllByWebElement(webDriver, GTestIndicators.PageShowTime, gridVerticalScrollbar);
			}
		}catch(Exception e) {
			GLog.logRecordTime(9, "暂时没有垂直滚动条");
		}
		
		try {
			gridHorizontalScrollbar = gridRoot.findElement(By.cssSelector(GText.getCssSelectorTxt(locateTagName, locateAtrributeName, locateArributeValueHorizontalScrollbar)));
			if(null != gridHorizontalScrollbar) {
				GWCtrlWait.ViewWaitingAllByWebElement(webDriver, GTestIndicators.PageShowTime, gridHorizontalScrollbar);
			}
		}catch(Exception e) {
			GLog.logRecordTime(9, "暂时没有水平滚动条");
		}
	}
	
	/**
	 *  初始化所有特征行
	 *
	 * @param webDriver 浏览器驱动对象
	 * @param table 表格的WebElement对象
	 * @param locateTagName 元素标签名
	 * @param locateAtrributeName 元素属性名称
	 * @param locateArributeValue 元素属性值
	 */
	public void initRows(WebDriver webDriver,
						WebElement table,
						String locateTagName,
						String locateAtrributeName,
						String locateArributeValue){
		rows = new ArrayList<>();
        List<WebElement> rowsTemp = new ArrayList<>(GWCtrlQuery.findElements(webDriver, table, locateTagName, locateAtrributeName, locateArributeValue));
		if(!rowsTemp.isEmpty()) {
			rows.addAll(rowsTemp);
			if(null == rows) {
				GLog.logRecordTime(9, "调用GridTable类方法----initRow失败，合适表格读取严重故障，请检查后重试");
				System.exit(0);
			}
		}
	}
	
	/**
	 *  点击行
	 *
	 * @param webDriver 浏览器驱动对象
	 * @param rowIndex 行号
	 */
	public void clickRow(WebDriver webDriver, int rowIndex) {
		GWCtrlQuery.ui_V(webDriver, rows.get(rowIndex)).click();
		GLog.logRecordTime(9, "调用GridTable类方法----clickRow");
	}

	/**
	 *  打印主要对象的hashcode
	 */
	public void showUnitsHash() {
        if(GStatic.gWebDiverParam.getBrowserLogType().equals("mainChinese")){
            return;
        }

        GLog.logRecordTime(9, "------------------------------------------------------------------");
        GLog.logRecordTime(9, "|                        MEMBER OBJECT                           |");
		GLog.logRecordTime(9, "TableBase.rows -> " + rows.hashCode());
		GLog.logRecordTime(9, "TableBase.gridRoot -> " + gridRoot.hashCode());
		GLog.logRecordTime(9, "TableBase.gridVerticalScrollbar -> " + gridVerticalScrollbar.hashCode());
		GLog.logRecordTime(9, "TableBase.gridHorizontalScrollbar -> " + gridHorizontalScrollbar.hashCode());
        GLog.logRecordTime(9, "|                              END                               |");
        GLog.logRecordTime(9, "------------------------------------------------------------------");
	}
}
