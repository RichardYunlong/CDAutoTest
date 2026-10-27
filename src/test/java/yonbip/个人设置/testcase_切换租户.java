package yonbip.个人设置;

import Webdriver.GWCrtlTestCaseRun;
import io.qameta.allure.Step;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class testcase_切换租户 extends GWCrtlTestCaseRun {

	@SuppressWarnings("UnnecessarySemicolon")
    @Test
    @Step("切换租户")
	public void test() {
        // 切换租户
        super.switchTenant("BIP全领域自动化运营");;
	}
}
