package yonbip.工作台.财务云.事项会计中台.财务公共.科目中心;

import DT.GLog;
import Webdriver.GWCrtlTestCaseRun;
import io.qameta.allure.Step;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class 科目表 extends GWCrtlTestCaseRun {

	@Test
    @Step("科目表")
	public void test() {
		GLog.logRecordTime(9, "科目表----占位");
	}

}
