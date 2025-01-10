package page.table;

import Base.GText;
import DT.GLog;
import Webdriver.GTestIndicators;
import Webdriver.GWCtrlWait;
import page.base.QueryElement;
import page.base.QueryElements;
import page.base.UniqueBase;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 *  grid类型的表格
 *  grid类型的表格,暂时找到的公共特征是【行驱动方式】
 *  
 *  @author hewei
 *  
 *  @version 20220424111100
 */
public class GridTable extends UniqueBase {
	
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
	@SuppressWarnings({"FieldMayBeFinal", "CanBeFinal"})
	private WebElement gridRoot;

	/**
	 *获得表格的WebElement对象
	 *
	 * @return 表格的WebElement对象
	 */
	public WebElement getGridRoot() {
		return gridRoot;
	}
	
	/**
	 *表格当前显示区
	 */
	@SuppressWarnings({"FieldMayBeFinal", "CanBeFinal"})
	private WebElement gridShow;
	
	/**
	 *获得表格当前显示区
	 *
	 * @return 表格当前显示区
	 */
	public WebElement getGridShow() {
		return gridShow;
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
	 */
	public GridTable(WebDriver webDriver, String locateTagName, String locateAtrributeName, String locateArributeValue) {
		super(webDriver, locateTagName, locateAtrributeName, locateArributeValue);
		
		if(null == super.getUniqueRoot()) {
			gridRoot = QueryElement.ui_Q(webDriver, "cssSelector", GText.getCssSelectorTxt("div", "role", "grid"));
			if(null != gridRoot){
				GWCtrlWait.ViewWaitingAllByWebElement(webDriver, GTestIndicators.PageShowTime, gridRoot);
			}
		}else {
			gridRoot = super.getUniqueRoot();
		}
		
		gridShow = Objects.requireNonNull(gridRoot).findElement(By.cssSelector(GText.getCssSelectorTxt("div", "class", "fixedDataTableLayout_rowsContainer")));
		
		if(null != gridShow) {
			GWCtrlWait.ViewWaitingAllByWebElement(webDriver, GTestIndicators.PageShowTime, gridShow);
			initRow(webDriver, gridShow);
		}
		
		try {
			gridVerticalScrollbar = gridRoot.findElement(By.cssSelector(GText.getCssSelectorTxt("div", "class", "ScrollbarLayout_face ScrollbarLayout_faceVertical public_Scrollbar_face")));
			if(null != gridVerticalScrollbar) {
				GWCtrlWait.ViewWaitingAllByWebElement(webDriver, GTestIndicators.PageShowTime, gridVerticalScrollbar);
			}
		}catch(Exception e) {
			GLog.logRecordTime(0, "暂时没有垂直滚动条");
		}
		
		try {
			gridHorizontalScrollbar = gridRoot.findElement(By.cssSelector(GText.getCssSelectorTxt("div", "class", "ScrollbarLayout_face ScrollbarLayout_faceHorizontal public_Scrollbar_face")));
			if(null != gridHorizontalScrollbar) {
				GWCtrlWait.ViewWaitingAllByWebElement(webDriver, GTestIndicators.PageShowTime, gridHorizontalScrollbar);
			}
		}catch(Exception e) {
			GLog.logRecordTime(0, "暂时没有水平滚动条");
		}
	}
	
	/**
	 *  初始化所有特征行
	 *
	 * @param webDriver 浏览器驱动对象
	 * @param table 表格的WebElement对象
	 */
	public void initRow(WebDriver webDriver, WebElement table){
		rows = new ArrayList<>();
        List<WebElement> rowsTemp = new ArrayList<>(QueryElements.findElements(webDriver, table, "div", "class", "fixedDataTableRowLayout_rowWrapper"));
		if(!rowsTemp.isEmpty()) {
			rows.addAll(rowsTemp);
			if(null == rows) {
				GLog.logRecordTime(0, "调用GridTable类方法----initRow失败，合适表格读取严重故障，请检查后重试");
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
		QueryElement.ui_V(webDriver, rows.get(rowIndex)).click();
		GLog.logRecordTime(0, "调用GridTable类方法----clickRow");
	}
}
