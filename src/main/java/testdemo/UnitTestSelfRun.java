package testdemo;

import DT.GLog;
import Sys.GStatic;
import Webdriver.GWCtrlBasic;
import Webdriver.GWebDiverParam;
import Webdriver.GWebDriver;

/**
 *  系统二级自检
 *  自检范围：需要加载整个系统才能验证的功能
 */
public class UnitTestSelfRun {

	public static void main(String[] agrs) {
		GStatic gs = new GStatic();
		gs.testInit();

		String bro = GStatic.gWebDiverParam.getBrowserDriverType();
		// 加载浏览器设置;
		GWebDriver gwedriver = new GWebDriver(bro);
		gwedriver.setWebDriver(bro);
		GLog.logRecordTime(9, gwedriver.getBrsType());

		GWCtrlBasic.Open(gwedriver.getG_Dr(),GStatic.gWebDiverParam.getBrowserDriverDownloadUrl());
		GWCtrlBasic.Maximize(gwedriver.getG_Dr());

		try {
			System.out.println("等待开始");
			// 等待6秒
			Thread.sleep(5000);
			System.out.println("等待结束");
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
			System.out.println("等待过程中被打断");
		}

		GWCtrlBasic.Quit(gwedriver.getG_Dr(),gwedriver);
		gwedriver.killDriver("all");

		gs.testFinish();
	}
}
