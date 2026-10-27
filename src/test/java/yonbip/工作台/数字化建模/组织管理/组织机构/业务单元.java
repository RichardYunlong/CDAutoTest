package yonbip.工作台.数字化建模.组织管理.组织机构;

import DT.GLog;
import Webdriver.GWCrtlTestCaseRun;
import io.qameta.allure.Step;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class 业务单元 extends GWCrtlTestCaseRun {

	@Test
    @Step("业务单元")
	public void test() {
		GLog.logRecordTime(9, "业务单元----占位");
	}

}
