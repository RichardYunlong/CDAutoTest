package Test;

import Base.GClazz;
import DT.GLog;
import Sys.GStatic;
import Webdriver.GWCtrlBasic;
import Webdriver.GWebDriver;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;

/**
 *  测试用例-运行数据
 */
public class GTestCaseRun {

	/**
	 *  全局测试环境变量
	 */
	@SuppressWarnings({"FieldMayBeFinal", "CanBeFinal"})
    private GStatic gs = new GStatic();
	public GStatic getGs() { return gs; }

	/**
	 *  全局驱动
	 */
	private GWebDriver gwedriver = null;
	public GWebDriver getGwedriver() { return gwedriver; }

	/**
	 *  是否在用例之间复用浏览器
	 *
	 *  默认关闭，保持"每条用例一个干净浏览器"的原有行为；
	 *  启用方式：启动参数加 -Dcdtest.browser.reuse=true。
	 *  复用后每条用例省掉浏览器启动与驱动握手(通常 5~10s)，但仍会清空会话并重新登录，
	 *  因此用例之间的登录态、缓存不会互相污染。
	 */
	private static final boolean BROWSER_REUSE =
			Boolean.parseBoolean(System.getProperty("cdtest.browser.reuse", "false"));

	/**
	 *  被复用的驱动对象(仅启用复用时保留)
	 */
	private static GWebDriver reusedDriver = null;

    /**
     *  构造函数
	 *  适用于非UI测试的用例
     */
	public GTestCaseRun(){ GClazz.thisADataUnitClass(); }

	/**
	 *  测试方式
	 *  0-白盒测试；1-接口测试；2-WebUI自动化测试；3-客户端UI自动化测试
	 */
	public void setTestFacilityByTestMode() {
		this.gs.testInit();
		String testMode = GStatic.gWebDiverParam.getBrowserDriverType();
		switch (testMode) {
			case "0": {
				// 白盒测试
				break;
			}
			case "1": {
				// 接口测试
				break;
			}
			case "2": case "chrome": case "firefox": case "ie": case "edge": case "safari":{
				// WebUI自动化测试
				String bro = GStatic.gWebDiverParam.getBrowserDriverType();
				if (!useReusedBrowser(bro)) {
					this.gwedriver = new GWebDriver(bro);
					this.gwedriver.setWebDriver(bro);
				}
				GWCtrlBasic.Open(this.getGwedriver().getG_Dr(), GStatic.gTransfer.getgServerUrl()[0]);
				if (!"chrome".equalsIgnoreCase(bro)
						|| !Boolean.parseBoolean(GStatic.gWebDiverParam.getBrowserHeadless())) {
					GWCtrlBasic.Maximize(this.getGwedriver().getG_Dr());
				}
				if (BROWSER_REUSE) {
					reusedDriver = this.gwedriver;
				}
				break;
			}
			case "3": {
				// 客户端UI自动化测试
				break;
			}
			default: {
				GLog.logRecordTime(9, "测试方式错误，请检查配置文件");
			}
		}
		GLog.logRecordTime(9, "当前测试方式[" + (this.gwedriver == null ? testMode : this.gwedriver.getBrsType()) + "]");
	}

	/**
	 *  测试方式
	 *  0-白盒测试；1-接口测试；2-WebUI自动化测试；3-客户端UI自动化测试
	 */
	public void clearTestFacilityByTestMode() {
		String testMode = GStatic.gWebDiverParam.getBrowserDriverType();
		switch (testMode) {
			case "0": {
				// 白盒测试
				break;
			}
			case "1": {
				// 接口测试
				break;
			}
			case "2": case "chrome": case "firefox": case "ie": case "edge": case "safari":{
				// WebUI自动化测试
				// 驱动可能因初始化失败而未被创建(或非UI测试方式进入)，此时跳过浏览器清理，避免清理环节自身抛 NPE
				if (this.gwedriver != null) {
					if (BROWSER_REUSE) {
						// 保留浏览器给下一条用例；会话状态在下一条用例初始化时清空
						reusedDriver = this.gwedriver;
						GLog.logRecordTime(9, "[browser]----[reuse]----[[keep alive]]");
					} else {
						GWCtrlBasic.Quit(this.gwedriver.getG_Dr(), this.gwedriver);
						this.gwedriver.killDriver("all");
					}
				}
				break;
			}
			case "3": {
				// 客户端UI自动化测试
				break;
			}
		}
		GLog.logRecordTime(9, "刚刚的测试方式[" + testMode + "]");
	}

	/**
	 *  尝试复用上一条用例留下的浏览器
	 *
	 *  @param bro 浏览器类型
	 *
	 *  @return true-已复用；false-需要新建
	 */
	private boolean useReusedBrowser(String bro) {
		if (!BROWSER_REUSE || reusedDriver == null) {
			return false;
		}

		try {
			// 触发一次会话级调用：浏览器已被关闭或崩溃时会抛异常，此时只能重建
			reusedDriver.getG_Dr().getWindowHandles();
		} catch (Exception e) {
			GLog.logRecordTime(9, "[browser]----[reuse]----[[browser dead, recreate]]");
			reusedDriver = null;
			return false;
		}

		this.gwedriver = reusedDriver;
		resetSession();
		GLog.logRecordTime(9, "[browser]----[reuse]----[[" + bro + " reused]]");
		return true;
	}

	/**
	 *  清空会话状态，避免上一条用例的登录态/缓存影响下一条
	 */
	private void resetSession() {
		try {
			WebDriver webDriver = this.gwedriver.getG_Dr();
			webDriver.manage().deleteAllCookies();
			((JavascriptExecutor) webDriver)
					.executeScript("try{window.localStorage.clear();window.sessionStorage.clear();}catch(e){}");
		} catch (Exception e) {
			// 清理失败不阻断复用：随后 Open() 会重新加载首页，最坏情况等价于"带着旧会话再登录一次"
			GLog.logRecordTime(9, "[browser]----[reuse]----[[reset session failed:"
					+ e.getClass().getSimpleName() + "]]");
		}
	}

	/**
	 *  关闭被复用的浏览器
	 *
	 *  测试任务结束时必须调用，否则进程退出前会残留一个浏览器窗口与驱动进程。
	 */
	public static void closeReusedDriver() {
		if (reusedDriver == null) {
			return;
		}

		try {
			GLog.logRecordTime(9, "[browser]----[reuse]----[[close at mission end]]");
			GWCtrlBasic.Quit(reusedDriver.getG_Dr(), reusedDriver);
			reusedDriver.killDriver("all");
		} catch (Exception e) {
			GLog.logSysFunctionException("closeReusedDriver", e);
		} finally {
			reusedDriver = null;
		}
	}
}
