package Webdriver;

import java.util.HashMap;
import java.util.Map;

/**
 * 系统参数管理
 */
public class GParam {

	/**
	 * 全局脚本模板
	 */
	@SuppressWarnings("serial")
	public Map<String, String> g_Script = new HashMap<String, String>(){{
			put("浏览器类型", "");
			put("访问网址", "");
			put("最大化", "");
			put("业务名称", "");
			put("业务提示", "");
			put("用例名称", "");
			put("功能路径", "");
			put("功能全名", "");
			put("内部数据", "");
			put("重新登录", "");
			put("切换主题", "");
			put("切换层级", "");
			put("操作类型", "");
			put("数据清理", "");
			put("浏览器关闭", "");
	}};
	
	/**
	 * 全局运行单元脚本模板
	 */
	@SuppressWarnings("serial")
	public static Map<String, String> g_UnitScript = new HashMap<String, String>(){{
			put("驱动类型", "");
			put("访问网址", "");
			put("窗口缩放", "");
			put("测试类名", "");
			put("业务名称", "");
			put("业务提示", "");
			put("内部数据", "");
			put("重新登录", "");
			put("切换主题", "");
			put("切换层级", "");
			put("操作类型", "");
			put("数据清理", "");
			put("窗口关闭", "");
	}};
	
	/**
	 * 全局最终用户
	 */
	public static String userName = "";
	
	/**
	 * 全局最终用户密码
	 */
	public static String userPassword = "";
	
	/**
	 * 是否为测试类层级单元测试 此项为true时下两项有效
	 */
	public static boolean isUnit = false;
	
	/**
	 * 已存在集团，用于做测试类层级的单元测试
	 */
	public static String unitGroup = "";
	
	/**
	 * 已存在公司，用于做测试类层级单元测试
	 */
	public static String unitCompany = "";
	
	/**
	 * 已存在项目部，用于做测试类层级单元测试
	 */
	public static String unitProjectDepartment = "";
	
	/**
	 * 已存在用户账号，用于做测试类层级的单元测试
	 */
	public static String unitUserName = "";
	
	/**
	 * 已存在用户密码，用于做测试类层级单元测试
	 */
	public static String unitUserPassword = "";
	
	/**
	 * 全局用户登录状态
	 * ""：未登录
	 * "前台":最终用户在线
	 * "后台":后台管理员在线
	 */
	public static String signStatus = "";
	
	/**
	 * 全局iFrame标记
	 * 0为基准模板
	 */
	public static int iframeIndex = 0;
	
	/**
	 * 全局window标记
	 * 0为当前窗口，其他为子窗口
	 */
	public static int windowIndex = 0;
	
	/**
	 * 全局topTab标记
	 * 记录当前顶层tab的名称
	 */
	public static String childTab = "";
	
	/**
	 * 全局contentTab标记
	 * 记录当前内容区tab的名称
	 */
	public static String contentTab = "";
	
	/**
	 * 全局是否首次登录并切换主题
	 * true：首次
	 * false:非首次
	 */
	public static boolean isFirstSigninAndTheme = true;
	
	/**
	 * 全局循环业务重试次数上线
	 * 某公共业务业务逻辑（例如尝试保存，尝试获取单据编号等）如果执行失败，默认重试次数上线为20次
	 */
	public static final int RETRY_TIMES = 20;

	/**
	 *  当前系统主题
	 */
	private static String sysTheme = "";
	
	public static String getSysTheme() {
		return sysTheme;
	}

	public static void setSysTheme(String sysTheme) {
		GParam.sysTheme = sysTheme;
	}
	
	/**
	 *  初始化数据-统一浏览器驱动类型
	 */
	private static String bro= "";

	public static String getBro() {
		return bro;
	}

	public static void setBro(String bro) {
		GParam.bro = bro;
	}
	
	/**
	 *  初始化数据-统一Server Url
	 */
	private static String serverUrl = "";

	public static String getServerUrl() {
		return serverUrl;
	}

	public static void setServerUrl(String serverUrl) {
		GParam.serverUrl = serverUrl;
	}
	
	/**
	 *  初始化数据-测试数据文件根目录
	 */
	private static String testDataJsonRootPath = "";

	public static String getTestDataJsonRootPath() {
		return testDataJsonRootPath;
	}

	public static void setTestDataJsonRootPath(String testDataJsonRootPath) {
		GParam.testDataJsonRootPath = testDataJsonRootPath;
	}
	
}
