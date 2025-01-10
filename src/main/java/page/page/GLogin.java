package page.page;

import DT.GLog;
import Webdriver.GTestIndicators;
import Webdriver.GWCtrlWait;
import page.base.Iframe;
import page.unit.Cas;
import page.unit.Language;
import page.unit.Video;
import org.openqa.selenium.WebDriver;

/**
 *  登录页面
 *  用于驱动登陆页面
 *  
 *  @author hewei
 *  
 *  @version 20220415171100
 */
public class GLogin {
	
	/**
	 * 主要iframe的标识
	 */
	public static final String casIframe = "yonbip_login_id";
	
	/**
	 *  宣传广告
	 */
	@SuppressWarnings({"FieldMayBeFinal", "CanBeFinal"})
	private Video video_target;
	
	/**
	 *  语言设置
	 */
	@SuppressWarnings({"FieldMayBeFinal", "CanBeFinal"})
	private Language wui_select;
	
	/**
	 *  登陆控件
	 */
	@SuppressWarnings({"FieldMayBeFinal", "CanBeFinal"})
	private Cas cas;
	
	/**
	 *  得到宣传视频对象
	 *
	 * @return 宣传视频对象
	 */
	public Video getVideo_target() {
		return video_target;
	}

	/**
	 *  语言切换对象
	 *
	 * @return 语言切换对象
	 */
	public Language getWui_select() {
		return wui_select;
	}

	/**
	 *  得到登陆控件
	 *
	 * @return 登陆控件
	 */
	public Cas getCas() {
		return cas;
	}

	/**
	 *  构造函数
	 *
	 *  @param webDriver 浏览器驱动对象
	 */
	public GLogin(WebDriver webDriver) {
		video_target = new Video(webDriver, "div", "class", "video-wrap fixed");
		wui_select = new Language(webDriver, "div", "class", "wui-select-selector");
		Iframe.ui_C_SWITCN_ID(webDriver, casIframe);
		cas = new Cas(webDriver, "cas");
		Iframe.ui_C_SWITCN_DEFAULT(webDriver);
	}
	
	/**
	 *  等待首页加载完毕
	 *
	 *  @param webDriver 浏览器驱动对象
	 */
	public void waitHomePage(WebDriver webDriver) {
		GWCtrlWait.ViewWaitingAllById(webDriver, GTestIndicators.PageShowTime, "workbench_content");
	}
	
	/**
	 *  打印登录页主要对象的hashcode
	 */
	public void showUnitsHash() {
		GLog.logRecordTime(0, "video_target -> " + video_target.hashCode());
		GLog.logRecordTime(0, "wui_select -> " + wui_select.hashCode());
		GLog.logRecordTime(0, "cas -> " + cas.hashCode());
	}
}
