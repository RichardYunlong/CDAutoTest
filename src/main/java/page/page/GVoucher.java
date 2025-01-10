package page.page;

import Webdriver.GTestIndicators;
import Webdriver.GWCtrlException;
import Webdriver.GWCtrlMsg;
import Webdriver.GWCtrlWait;
import page.base.Iframe;
import page.base.VoucherBase;
import page.base.WebElementArrayList;
import page.table.VoucherTable;
import org.openqa.selenium.WebDriver;

/**
 *  凭证
 *  凭证页面驱动
 *  
 *  @author hewei
 *  
 *  @version 202204221519900
 */
public class GVoucher {
	
	/**
	 * 主要iframe的标识
	 * 如果该页面存在必须切换的iframe,则需要定义一个宏，随着页面种类的增多，尝试找出此类宏的特征
	 */
	public static final String voucherIframe = "newvoucher";
	
	/**
	 *  页面的WebElement对象
	 */
    @SuppressWarnings("CanBeFinal")
    VoucherBase voucherRoot;
	
	/**
	 *  工具按钮表
	 */
	@SuppressWarnings({"FieldMayBeFinal", "CanBeFinal"})
	private WebElementArrayList toolBar;
	
	/**
	 *  获得工具按钮表
	 *
	 * @return 工具按钮表
	 */
	public WebElementArrayList getToolBar() {
		return toolBar;
	}
	
	/**
	 *  主体表格
	 */
	private VoucherTable voucherTable = null;
	
	/**
	 *  获得主体表格
	 *
	 * @return 主体表格
	 */
	public VoucherTable getVoucherTable() {
		return voucherTable;
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
		Iframe.ui_C_SWITCN_ID(webDriver, GVoucher.voucherIframe);
		
		voucherRoot = new VoucherBase(webDriver, "div", "class", "newvoucher");

        GWCtrlWait.ViewWaitingAllByWebElement(webDriver, GTestIndicators.PageShowTime, voucherRoot.getHeader());
        toolBar = new WebElementArrayList(voucherRoot.getHeader(), "span", "class", "wui-button-text-wrap");


        try {
			voucherTable = new VoucherTable(webDriver, "multi", "div", "class", " voucher-table-container");
		}catch (Exception e) {
			GWCtrlException.switchTo(webDriver, e, 1, 0, "----<exception[GVoucher voucherTable" + GWCtrlMsg.ui_QUERY[2] + "]", true);
		}
	}
}
