package main.java.yonbip.unit;

import main.java.Base.GText;
import main.java.DT.GLog;
import main.java.Webdriver.GTestIndicators;
import main.java.Webdriver.GWCtrlWait;
import main.java.yonbip.base.UniqueBase;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 *  用户设置
 *  用于个人设置
 *  
 *  @author hewei
 *  
 *  @version 20220418165300
 */
public class Setting extends UniqueBase {
	
	

	/**
	 *  构造函数
	 *
	 * @param webDriver 浏览器驱动
	 * @param id 浏览器ID
	 */
	public Setting(WebDriver webDriver, String id) {
		super(webDriver, id);
	}
	
	/**
	 *  退出登陆
	 *
	 * @param webDriver 浏览器驱动
	 */
	public void signOut(WebDriver webDriver) {
		WebElement logOut = webDriver.findElement(By.cssSelector(GText.getCssSelectorTxt("div", "class", "logout")));
		if(null != logOut) {
			GWCtrlWait.ViewWaitingAllByWebElement(webDriver, GTestIndicators.PageShowTime, logOut);
			logOut.click();
			GWCtrlWait.ViewWaitingAllById(webDriver, GTestIndicators.PageShowTime, "yonbip_login_id");
			GLog.logRecordTime(0, "退出登录成功");
		}
	}
	
	/**
	 *  切换租户
	 *
	 *  @param webDriver 浏览器驱动
	 *  @param tenantName 目标租户名称
	 */
	public void changeOrg(WebDriver webDriver, String tenantName) {
		TenantDropDown tdd = new TenantDropDown(webDriver);
		tdd.eject(webDriver);
		tdd.input(webDriver, tenantName);
		tdd.search(webDriver);
		tdd.click(webDriver, tenantName);
		tdd.confirm(webDriver, "确定");
	}
	
}
