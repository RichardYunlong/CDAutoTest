package yonbip.工作台.数字化建模.权限管理.授权;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import Webdriver.GWCrtlTestCaseRun;
import io.qameta.allure.Step;
import page.page.GUsermgr;

@SuppressWarnings("ALL")
@RunWith(Parameterized.class)
public class 授权 extends GWCrtlTestCaseRun {

	private String tenantName;

	// 参数化构造函数
	public 授权(String tenantName) {
		this.tenantName = tenantName;
	}

	// 从CSV文件读取测试数据
	@Parameterized.Parameters(name = "租户: {0}")
	public static Collection<Object[]> data() {
		Collection<Object[]> params = new ArrayList<>();
		
		try {
			// 使用类加载器获取与测试类同目录的CSV文件
			java.net.URL resource = 授权.class.getResource("tenants.csv");
			if (resource == null) {
				throw new IOException("找不到资源文件: tenants.csv");
			}
			String csvPath = java.net.URLDecoder.decode(resource.getPath(), StandardCharsets.UTF_8.name());
			
			try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(csvPath), StandardCharsets.UTF_8))) {
				String line;
				// 跳过标题行（如果有）
				br.readLine();
				
				// 读取每一行数据
				while ((line = br.readLine()) != null) {
					// 跳过空行
					if (line.trim().isEmpty()) {
						continue;
					}
					// 按逗号分割，取第一个字段作为租户名称
					String[] values = line.split(",", -1);
					if (values.length > 0 && !values[0].trim().isEmpty()) {
						params.add(new Object[]{values[0].trim()});
					}
				}
			}
		} catch (IOException e) {
			System.err.println("读取CSV文件失败: " + e.getMessage());
			e.printStackTrace();
		}
		
		return params;
	}

	@Test
	@Step("授权管理员 - 租户: {tenantName}")
	public void test() {
		// 切换租户
		super.switchTenant(tenantName);
		// 打开菜单
		super.openMenu("应用平台", "数字化建模", "权限管理", "用户管理", "用户管理");
		// 定义用户列表
		String[][] users = {
				// {"王卫强", "15699735574"},
				{ "贺伟", "13717761827" },
				{ "华芸玮", "18311076095" },
				{ "刘旭", "15010750603" },
				{ "陈雄", "19991971147" },
				{ "李菲菲", "15515197675" },
				{ "王依辰", "15110088904" },
				{ "姜楠", "15903422247" },
				{ "王依辰", "15110088904" },
				{ "黄小文", "18500892554" },
				{ "张晓雪", "15600281800" },
				{ "苏晓玲", "18600207909" },
				{ "刘丹", "18566272186" }
		};
		// 为用户授予管理员权限
		GUsermgr 用户 = new GUsermgr(gDr);
		for (String[] user : users) {
			用户.addAndSetManager(gDr, user[0], user[1]);
		}
		// 关闭页签
		super.closeTopTab("用户管理");
	}
}