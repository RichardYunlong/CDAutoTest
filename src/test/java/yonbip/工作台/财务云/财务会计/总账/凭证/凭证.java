package yonbip.工作台.财务云.财务会计.总账.凭证;

import Webdriver.GWCrtlTestCaseRun;
import io.qameta.allure.Step;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class 凭证 extends GWCrtlTestCaseRun {

	@SuppressWarnings("CommentedOutCode")
    @Test
	@Step("凭证录入")
	public void test() {
		//切换租户
		super.switchTenant("测试租户企业绩效预发旗舰版自动化专用240131");
		//打开菜单
		super.openMenu("财务云", "财务会计", "总账", "凭证",  "凭证");

//		GVoucher voucher = new GVoucher(gDr);
//		voucher.getVoucherTable().clickCell(gDr,"摘要", 0);
//		voucher = new GVoucher(gDr);
//		voucher.getVoucherTable().getCellText(gDr,"摘要", 0);
//		voucher.leaveVoucherPage(gDr);

		super.closeTopTab("凭证");
	}
}
