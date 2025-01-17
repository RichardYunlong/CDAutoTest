package page.page;

import DT.GLog;
import Webdriver.GTestIndicators;
import Webdriver.GWCtrlException;
import Webdriver.GWCtrlMsg;
import Webdriver.GWCtrlWait;
import org.openqa.selenium.WebElement;
import page.base.Iframe;
import page.baseused.VoucherBase;
import page.baseused.WebElementArrayList;
import page.table.VoucherTable;
import org.openqa.selenium.WebDriver;

/**
 *  凭证
 *  凭证页面驱动
 *  
 *  @author hewei
 */
public class GVoucher extends VoucherBase {
	
	/**
	 * 主要iframe的标识
	 * 如果该页面存在必须切换的iframe,则需要定义一个宏，随着页面种类的增多，尝试找出此类宏的特征
	 */
	public static final String voucherIframe = "newvoucher";
	
	/**
	 *  页面的WebElement对象
	 */
    @SuppressWarnings("CanBeFinal")
	WebElement gVoucherRoot;
	
	/**
	 *  工具按钮表
	 */
	@SuppressWarnings({"FieldMayBeFinal", "CanBeFinal"})
	private WebElementArrayList gToolBar;
	
	/**
	 *  获得工具按钮表
	 *
	 * @return 工具按钮表
	 */
	public WebElementArrayList getToolBar() {
		return gToolBar;
	}
	
	/**
	 *  主体表格
	 */
	private VoucherTable gVoucherTable = null;
	
	/**
	 *  获得主体表格
	 *
	 * @return 主体表格
	 */
	public VoucherTable getVoucherTable() {
		return gVoucherTable;
	}
	
	/**
	 *  等待凭证页面加载完毕
	 *
	 *  @param webDriver 浏览器驱动对象
	 */
	public void waitVoucherPage(WebDriver webDriver) {
		GWCtrlWait.ViewWaitingAllById(webDriver, GTestIndicators.PageShowTime, "root");
	}
	
	/**
	 *  等待凭证页面加载完毕
	 *
	 *  @param webDriver 浏览器驱动对象
	 */
	public void leaveVoucherPage(WebDriver webDriver) {
		GWCtrlWait.ViewWaitingAllById(webDriver, GTestIndicators.PageShowTime, "root");
		Iframe.ui_C_SWITCN_DEFAULT(webDriver);
	}
	
	/**
	 *	构造函数
	 *
	 *  @param webDriver 浏览器驱动对象
	 */
	public GVoucher(WebDriver webDriver) {
		super(webDriver, "div", "class", "newvoucher");
		gVoucherRoot = getUniqueRoot();

		gToolBar = new WebElementArrayList(super.getHeader(), "span", "class", "wui-button-text-wrap");

        try {
			gVoucherTable = new VoucherTable(webDriver, "multi", "div", "class", " voucher-table-container");
		}catch (Exception e) {
			GWCtrlException.switchTo(webDriver, e, 1, 0, "----<exception[GVoucher voucherTable" + GWCtrlMsg.ui_QUERY[2] + "]", true);
		}
	}

	/**
	 *  打印主要对象的hashcode
	 */
	public void showUnitsHash() {
		GLog.logRecordTime(9, "主要成员对象VVVV");
		GLog.logRecordTime(9, "gVoucherRoot -> " + gVoucherRoot.hashCode());
		GLog.logRecordTime(9, "gToolBar -> " + gToolBar.hashCode());
		GLog.logRecordTime(9, "gVoucherTable -> " + gVoucherTable.hashCode());
		GLog.logRecordTime(9, "主要成员对象^^^^");
	}
}
