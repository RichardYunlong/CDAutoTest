package page.table;

import Base.GText;
import DT.GLog;
import Webdriver.GTestIndicators;
import Webdriver.GWCtrlQuery;
import Webdriver.GWCtrlWait;
import Webdriver.GWCtrlGetChildText;
import page.baseused.WebElementArrayList;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;
import java.util.Objects;

/**
 *  凭证类表格驱动
 *  记账凭证模块专用表格。注意，前端源码中有关对“行号”的描写存在较难理解的差异。例如，打开模块后，视觉上的表格，第1行是表头，但其下单元格元素的id属性值为“col-1”，意思是“表头区的对应元素id值只跟一个数字，就是字段号”；
 *  视觉上的第2行及以下，为表格数据，但其下单元格元素的id属性值为“col-0-1”，意思是“字段-行号-列号”，此时，如果单独根据
 *  增加了凭证类表格的单元格操作方式
 *  此类为与【凭证表格】相似的表格驱动
 *
 *  @author hewei
 */
public class VoucherTable extends EnhanceTable {
	
	/**
	 *  表头字段操作表 如果表格没有没有锁定区，则可使用此成员调用元素
	 */
	private WebElementArrayList header = null;
	
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
	public VoucherTable(WebDriver webDriver,
						String headerType,
						String locateTagName,
						String locateAtrributeName,
						String locateArributeValue,
						String locateArributeValueTableLayout,
						String locateArributeValueRowLayout,
						String locateArributeValueVerticalScrollbar,
						String locateArributeValueHorizontalScrollbar) {
		super(webDriver, headerType, locateTagName, locateAtrributeName, locateArributeValue, locateArributeValueTableLayout, locateArributeValueRowLayout, locateArributeValueVerticalScrollbar, locateArributeValueHorizontalScrollbar);
	}
	
	/**
	 *  凭证类表格表头加载方式实现方法
	 *
	 * @param webDriver 浏览器驱动
	 * @param headerType single-单表头；multi-多级表头
	 */
	public void initHeader(WebDriver webDriver, String headerType) {

		if(null != super.getRows() && !super.getRows().isEmpty()) {
			GLog.logRecordTime(9, "找到[" + super.getRows().size() + "]行");
			
			for(WebElement row:super.getRows()) {
				GWCtrlWait.ViewWaitingAllByWebElement(webDriver, GTestIndicators.PageShowTime, row);
				
				List<WebElement> columnheaders = row.findElements(By.cssSelector(GText.getCssSelectorTxt("div", "role", "columnheader")));
				if(null != columnheaders && !columnheaders.isEmpty()) {
					header = new WebElementArrayList(row);
					GLog.logRecordTime(9, "找到[" + columnheaders.size() + "]列");
				}

				List<WebElement> groups;
				groups = row.findElements(By.cssSelector(GText.getCssSelectorTxt("div", "class", "fixedDataTableCellGroupLayout_cellGroupWrapper")));
				
				if(null != groups && groups.size() == 3) {
					WebElementArrayList headerLeftLocker;
					WebElementArrayList headerMiddleScroller;
					WebElementArrayList headerRightLocker;
					
					headerLeftLocker = new WebElementArrayList(webDriver, groups.get(0).findElement(By.cssSelector(GText.getCssSelectorTxt("div", "class", "public_fixedDataTableCell_cellContent"))), "div");
					if(null != headerLeftLocker.getStringArrayList() && !headerLeftLocker.getStringArrayList().isEmpty()) {
						for(String colName:headerLeftLocker.getStringArrayList()) {
							WebElement colIdent;
							colIdent = GWCtrlQuery.ui_Q(webDriver, headerLeftLocker.getWebElement(colName), 4);
							GWCtrlWait.ViewWaitingAllByWebElement(webDriver, GTestIndicators.PageShowTime, colIdent);
							super.getColName_colIdent().put(colName, colIdent.getAttribute("id").replace("col-", ""));
						}
					}
					header.addOther(headerLeftLocker);
					
					headerMiddleScroller = new WebElementArrayList(groups.get(1), "span", "class", "towline-text-ellipsis");
					if(null != headerMiddleScroller.getStringArrayList() && !headerMiddleScroller.getStringArrayList().isEmpty()) {
						for(String colName:headerMiddleScroller.getStringArrayList()) {
							WebElement colIdent;
							colIdent = GWCtrlQuery.ui_Q(webDriver, headerMiddleScroller.getWebElement(colName), 4);
							GWCtrlWait.ViewWaitingAllByWebElement(webDriver, GTestIndicators.PageShowTime, colIdent);
							super.getColName_colIdent().put(colName, colIdent.getAttribute("id").replace("col-", ""));
						}
					}
					header.addOther(headerMiddleScroller);
					
					headerRightLocker = new WebElementArrayList(groups.get(2), "div", "class", "public_fixedDataTableCell_cellContent");
					if(null != headerRightLocker.getStringArrayList() && !headerRightLocker.getStringArrayList().isEmpty()) {
						for(String colName:headerRightLocker.getStringArrayList()) {
							WebElement colIdent;
							colIdent = GWCtrlQuery.ui_Q(webDriver, headerRightLocker.getWebElement(colName), 3);
							GWCtrlWait.ViewWaitingAllByWebElement(webDriver, GTestIndicators.PageShowTime, colIdent);
							super.getColName_colIdent().put(colName, colIdent.getAttribute("id").replace("col-", ""));
						}
					}
					header.addOther(headerRightLocker);
				}else {
					GLog.logRecordTime(9, "此表格无锁定区或区域类型不是3");

				}
				
				if(Objects.requireNonNull(groups).size() == 1) {
					GLog.logRecordTime(9, "尝试按照区域类型为1读取");
					header = new WebElementArrayList(row, "span", "class", "towline-text-ellipsis");
				}
				break;//如果当前行是表头行，则结束循环
			}
			
			if(null != header) {
				GLog.logRecordTime(9, "加载一级表头目标[" + header + "]成功");
				super.getRows().remove(0);//rows成员只保留处表头以外的其他数据
			}
			
			if(null != super.getColName_colIdent()) {
				GLog.logRecordTime(9, "加载一级表头字段名和字段唯一标识的对应关系[" + super.getColName_colIdent().toString() + "]成功");
			}
		}	
	}
	
	/**
	 *  获得单元格的WebElement对象
	 *
	 * @param webDriver 浏览器驱动
	 * @param colName 字段名
	 * @param rowIndex 行号
	 *
	 * @return 单元格的WebElement对象
	 */
	public WebElement getCellWebElement(WebDriver webDriver, String colName, int rowIndex) {
		WebElement cell;
		
		//单元格唯一标识："col"+行号+列号(列唯一标识)
		String id = "col";
		id += "-" + rowIndex;
		String colIndex = super.getColName_colIdent().get(colName);
		id += "-" + colIndex;
		
		cell = super.getRows().get(rowIndex).findElement(By.id(id));
		GWCtrlQuery.ui_V(webDriver, cell);
		GLog.logRecordTime(9, "查询单元格" + colName + "[" + rowIndex + "][" + colIndex + "]成功");
		
		return cell;
	}
	
	/**
	 *  点击单元格
	 *
	 * @param webDriver 浏览器驱动
	 * @param colName 字段名
	 * @param rowIndex 行号
	 */
	public void clickCell(WebDriver webDriver, String colName, int rowIndex) {
		this.getCellWebElement(webDriver, colName, rowIndex).click();
		GLog.logRecordTime(9, "并点击成功");
	}
	
	/**
	 *  获得单元格的String对象
	 *
	 * @param webDriver 浏览器驱动
	 * @param colName 字段名
	 * @param rowIndex 行号
	 *
	 * @return 单元格的String对象
	 */
	@SuppressWarnings("UnusedReturnValue")
    public String getCellText(WebDriver webDriver, String colName, int rowIndex) {
		String cell;
		cell = GWCtrlGetChildText.byFirst(getCellWebElement(webDriver, colName, rowIndex), "div");
		
		return cell;
	}

}
