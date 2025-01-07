package main.java.Test;

import main.java.Base.GClazz;
import main.java.DT.GLog;
import main.java.Sys.GStatic;
import main.java.Webdriver.GWCtrlBasic;
import main.java.Webdriver.GWebDiverParam;
import main.java.Webdriver.GWebDriver;

/**
 *  测试用例-运行数据
 */
public class GTestCaseRun {

	/**
	 *  全局测试环境变量
	 */
	@SuppressWarnings("FieldMayBeFinal")
    private GStatic gs = new GStatic();
	public GStatic getGs() { return gs; }

	/**
	 *  全局驱动
	 */
	private GWebDriver gwedriver = null;
	public GWebDriver getGwedriver() { return gwedriver; }

    /**
     *  构造函数
	 *  适用于非UI测试的用例
     */
	public GTestCaseRun(){ GClazz.thisADataUnitClass(); }

	/**
	 *  测试方式
	 *  0-白盒测试；1-接口测试；2-WebUI自动化测试；3-客户端UI自动化测试
	 *
	 * @param testMode 测试方式
	 * @
	 */
	public void setTestFacilityByTestMode(String testMode) {
		this.gs.testInit();
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
				String bro = GWebDiverParam.getBrowserDriverType();
				this.gwedriver = new GWebDriver(bro);
				this.gwedriver.setWebDriver(bro);
				GLog.logRecordTime(0, this.gwedriver.getBrsType());
				GWCtrlBasic.Open(this.getGwedriver().getG_Dr(), GStatic.gTransfer.getgServerUrl()[0]);
				GWCtrlBasic.Maximize(this.getGwedriver().getG_Dr());
				break;
			}
			case "3": {
				// 客户端UI自动化测试
				break;
			}
		}
	}

	/**
	 *  测试方式
	 *  0-白盒测试；1-接口测试；2-WebUI自动化测试；3-客户端UI自动化测试
	 *
	 * @param testMode 测试方式
	 */
	public void clearTestFacilityByTestMode(String testMode) {
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
				GWCtrlBasic.Quit(this.gwedriver.getG_Dr(), this.gwedriver);
				this.gwedriver.killDriver("all");
				break;
			}
			case "3": {
				// 客户端UI自动化测试
				break;
			}
		}
	}
}
