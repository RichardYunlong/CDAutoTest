package yonbip.工作台.数字化建模.权限管理.角色管理;

import Webdriver.GWCrtlTestCaseRun;
import io.qameta.allure.Step;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class 角色管理 extends GWCrtlTestCaseRun {
	
	@Test
    @Step("角色管理")
	public void test() {
		//切换租户
		super.switchTenant("0615预发专业多1");
		//打开菜单
		super.openMenu("数字化建模", "数字化建模", "权限管理", "角色管理", "角色管理");


		super.closeTopTab("角色管理");
	}
}
