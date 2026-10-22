package yonbip.工作台.云平台.友云测.测试服务;

import org.junit.Test;

import Webdriver.GWCrtlTestCaseRun;
import io.qameta.allure.Step;
import page.page.GExecutionQueue;

public class 执行队列 extends GWCrtlTestCaseRun {

	@Test
	@Step("调整执行队列")
	public void test() {
		// 切换租户
		super.switchTenant("BIP全领域自动化运营");
		// 打开菜单
		super.openMenu("云平台", "友云测", "测试服务", "任务管理", "执行队列");
		// 创建执行队列实例
		GExecutionQueue 执行队列 = new GExecutionQueue(gDr);
		// 定义需要调整优先级的领域列表
		String[] domains = { "共享服务", "费控云", "智能会计", "企业绩效", "业财数智", "税务云", "司库" };
		// 循环调整每个领域下所有待执行任务的优先级
		for (String domain : domains) {
			执行队列.modifyAllPriority(gDr, domain, "P0");
		}
		// 关闭页签
		super.closeTopTab("执行队列");
	}
}
