package yonbip.登陆;

import org.junit.Test;

import DT.GLog;
import Webdriver.GWCrtlTestCaseRun;
import io.qameta.allure.Step;

public class testcase_签出 extends GWCrtlTestCaseRun {

	@Test
	@Step("调整执行队列")
	public void test() {
		GLog.logRecordTime(9, "演示签入后直接签出，不进行实际操作");
	}
}
