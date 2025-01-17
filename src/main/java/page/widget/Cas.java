package page.widget;

import DT.GLog;
import Webdriver.*;
import page.base.Iframe;
import page.base.UniqueWebElementBase;
import page.page.GLogin;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 *  登录页面
 *  
 *  @author hewei
 *  
 *  @version 20220415171100
 */
public class Cas extends UniqueWebElementBase {
	
	/**
	 *  账号
	 */
	private WebElement username = null;
	
	/**
	 *  密码
	 */
	private WebElement password = null;
	
	/**
	 *  登陆按钮
	 */
	@SuppressWarnings({"unused", "FieldCanBeLocal"})
	private WebElement submit_btn_login = null;
	
	/**
	 *  构造函数
	 *
	 *  @param webDriver 浏览器驱动
	 *  @param id 唯一定远id
	 */
	public Cas(WebDriver webDriver, String id) {
		super(webDriver, id);
		try {
			username = super.getUniqueRoot().findElement(By.id("username"));
			password = super.getUniqueRoot().findElement(By.id("password"));
			submit_btn_login = super.getUniqueRoot().findElement(By.id("submit_btn_login"));
		}catch (Exception e) {
			GWCtrlException.switchTo(webDriver, e, 1, 0, "----<exception[Cas" + GWCtrlMsg.ui_QUERY[2] + "]", true);
		}
	}

	/**
	 *  登陆
	 *
	 *  @param webDriver 浏览器驱动
	 *  @param acc 账号
	 *  @param pw 密码
	 */
	public void signIn(WebDriver webDriver, String acc, String pw) {
		Iframe.ui_C_SWITCN_ID(webDriver, GLogin.casIframe);
		
		WebElement switchLabel = webDriver.findElement(By.id("switchLabel"));
		if(null != switchLabel && "切换至帐号登录".equals(switchLabel.getText())) {
			switchLabel.click();
			GWCtrlWait.ViewWaitingAllById(webDriver, GTestIndicators.PageShowTime, "toNormalLogin");
		}
		
		GWCtrlWait.ViewWaitingAllById(webDriver, GTestIndicators.PageShowTime, "username");
		GWCtrlWait.ViewWaitingAllById(webDriver, GTestIndicators.PageShowTime, "password");
		GWCtrlWait.ViewWaitingAllById(webDriver, GTestIndicators.PageShowTime, "submit_btn_login");
		
		GWCtrlInputFill.ByWebElement(webDriver, username, acc);
		GWCtrlInputFill.ByWebElement(webDriver, password, pw);
		GLog.logRecordTime(9, "填写用户名密码成功");
	
//		submit_btn_login.click();
		GLog.logRecordTime(9, "使用回车登陆成功");
		
		Iframe.ui_C_SWITCN_DEFAULT(webDriver);
	}
}
