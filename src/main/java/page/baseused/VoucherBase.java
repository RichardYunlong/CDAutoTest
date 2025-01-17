package page.baseused;

import Base.GText;
import DT.GLog;
import Webdriver.GTestIndicators;
import Webdriver.GWCtrlException;
import Webdriver.GWCtrlMsg;
import Webdriver.GWCtrlWait;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import page.base.UniqueWebElementBase;

/**
 *  凭证类页面公共驱动
 *  与【凭证】页面元素组件类似的页面公共驱动
 *  
 *  @author hewei
 *  
 *  @version 20220422173100
 */
public class VoucherBase extends UniqueWebElementBase {
	
	/**
	 *页面头
	 */
	private WebElement header = null;
	
	/**
	 *获得页面头
	 *
	 * @return 页面头
	 */
	public WebElement getHeader() {
		return header;
	}
	
	/**
	 *页面体
	 */
	private WebElement body = null;
	
	/**
	 *获得页面体
	 *
	 * @return 页面体
	 */
	public WebElement getBody() {
		return body;
	}
	
	/**
	 *页面脚
	 */
	private WebElement footer = null;
	
	/**
	 *获得页面脚
	 *
	 * @return 页面脚
	 */
	public WebElement getFooter() {
		return footer;
	}
	
	/**
	 * 凭证左移按钮的WebElement对象
	 */
	private WebElement buttonsLeft = null;
	
	/**
	 * 获得凭证左移按钮的WebElement对象
	 *
	 * @return 凭证左移按钮的WebElement对象
	 */
	public WebElement getButtonsLeft() {
		return buttonsLeft;
	}

	/**
	 *  凭证右移按钮的WebElement对象
	 */
	private WebElement buttonsRight = null;
	
	/**
	 *  获得凭证右移按钮的WebElement对象
	 *
	 * @return 凭证右移按钮的WebElement对象
	 */
	public WebElement getButtonsRight() {
		return buttonsRight;
	}

	/**
	 *  打开设凭证置面板按钮的WebElement对象
	 */
	private WebElement setting = null;

	/**
	 *  获得打开凭证设置面板按钮的WebElement对象
	 *
	 * @return 打开设置面板按钮的WebElement对象
	 */
	public WebElement getSetting() {
		return setting;
	}

	/**
	 *  打开快捷键面板按钮的WebElement对象
	 */
	private WebElement shortcutkey = null;
	
	/**
	 *  获得打开快捷键面板按钮的WebElement对象
	 *
	 * @return 打开快捷键面板按钮的WebElement对象
	 */
	public WebElement getShortcutkey() {
		return shortcutkey;
	}
	
	/**
	 *  过滤器的WebElement对象
	 */
	private WebElement formfilter = null;
	
	/**
	 *  获得过滤器的WebElement对象
	 *
	 * @return 过滤器的WebElement对象
	 */
	public WebElement getFormfilter() {
		return formfilter;
	}

	/**
	 * 表格合计行的WebElement对象
	 */
	private WebElement tableFooter = null;
	
	/**
	 *  获得表格合计行的WebElement对象
	 *
	 * @return 表格合计行的WebElement对象
	 */
	public WebElement getTableFooter() {
		return tableFooter;
	}

	/**
	 *  页面综述的WebElement对象
	 */
	private WebElement pageFooter = null;
	
	/**
	 * 获得页面综述的WebElement对象
	 *
	 * @return 页面综述的WebElement对象
	 */
	public WebElement getPageFooter() {
		return pageFooter;
	}
	
	/**
	 * 构造函数
	 *
	 * @param webDriver 浏览器驱动对象
	 * @param id 能够确定元素唯一对象的属性所在标签类型
	 */
	public VoucherBase(WebDriver webDriver, String id) {
		super(webDriver, id);
		
		if(null != super.getUniqueRoot()) {
			try {
				header = super.getUniqueRoot().findElement(By.cssSelector(GText.getCssSelectorTxt("div", "class", "voucher-header oh")));
				if(null != header) {
					GWCtrlWait.ViewWaitingAllByWebElement(webDriver, GTestIndicators.PageShowTime, header);
					
					try {
						buttonsLeft = header.findElement(By.id("voucher-switching-left"));
					}catch (Exception e) {
						GLog.logRecordTime(9, "此页面暂无voucher-switching-left按钮");
					}
					
					try {
						buttonsRight  = header.findElement(By.id("voucher-switching-right"));
					}catch (Exception e) {
						GLog.logRecordTime(9, "此页面暂无voucher-switching-right按钮");
					}
					
					try {
						setting = header.findElement(By.id("voucher-setting"));
					}catch (Exception e) {
						GLog.logRecordTime(9, "此页面暂无voucher-setting按钮");
					}
					
					try {
						shortcutkey = header.findElement(By.id("keyboard"));
					}catch (Exception e) {
						GLog.logRecordTime(9, "此页面暂无keyboard按钮");
					}
					
					try {
						formfilter = header.findElement(By.cssSelector(GText.getCssSelectorTxt("div", "class", "form-filter")));
					}catch (Exception e) {
						GLog.logRecordTime(9, "此页面暂无form-filter");
					}
				}
			}catch (Exception e) {
				GWCtrlException.switchTo(webDriver, e, 1, 0, "----<exception[VoucherBase header" + GWCtrlMsg.ui_QUERY[2] + "]", true);
			}
			
			try {
				body = super.getUniqueRoot().findElement(By.cssSelector(GText.getCssSelectorTxt("div", "class", "voucher-body oldvoucher")));
				if(null != body) {
					GWCtrlWait.ViewWaitingAllByWebElement(webDriver, GTestIndicators.PageShowTime, body);
				}
			}catch (Exception e) {
				GWCtrlException.switchTo(webDriver, e, 1, 0, "----<exception[VoucherBase body" + GWCtrlMsg.ui_QUERY[2] + "]", true);
			}
			
			try {
				footer = super.getUniqueRoot().findElement(By.cssSelector(GText.getCssSelectorTxt("div", "class", "voucher-footer")));
				if(null != footer) {
					GWCtrlWait.ViewWaitingAllByWebElement(webDriver, GTestIndicators.PageShowTime, footer);
					
					try {
						tableFooter = footer.findElement(By.id("newvoucher_fotter-operater"));
					}catch (Exception e) {
						GLog.logRecordTime(9, "此页面暂无newvoucher_fotter-operater");
					}
					
					try {
						pageFooter = footer.findElement(By.cssSelector(GText.getCssSelectorTxt("div", "class", "voucher-page-footer")));
					}catch (Exception e) {
						GLog.logRecordTime(9, "此页面暂无voucher-page-footer");
					}
				}
			}catch (Exception e) {
				GWCtrlException.switchTo(webDriver, e, 1, 0, "----<exception[VoucherBase footer" + GWCtrlMsg.ui_QUERY[2] + "]", true);
			}
		}
	}

	/**
	 *  打印主要对象的hashcode
	 */
	public void showUnitsHash() {
		GLog.logRecordTime(9, "主要成员对象VVVV");
		GLog.logRecordTime(9, "header -> " + header.hashCode());
		GLog.logRecordTime(9, "body -> " + body.hashCode());
		GLog.logRecordTime(9, "footer -> " + footer.hashCode());
		GLog.logRecordTime(9, "buttonsLeft -> " + buttonsLeft.hashCode());
		GLog.logRecordTime(9, "buttonsRight -> " + buttonsRight.hashCode());
		GLog.logRecordTime(9, "setting -> " + setting.hashCode());
		GLog.logRecordTime(9, "shortcutkey -> " + shortcutkey.hashCode());
		GLog.logRecordTime(9, "formfilter -> " + formfilter.hashCode());
		GLog.logRecordTime(9, "tableFooter -> " + tableFooter.hashCode());
		GLog.logRecordTime(9, "pageFooter -> " + pageFooter.hashCode());
		GLog.logRecordTime(9, "主要成员对象^^^^");
	}
}
