package Webdriver;

import DT.GLog;

import java.util.HashMap;
import java.util.Map;

/**
 * 常用元素Id
 */
public class GWCtrlWebElementId {
	
	/**
	 * 常用Id
	 */
	@SuppressWarnings("CanBeFinal")
    public static Map<String, String> CN_ID = new HashMap<>();
	
	/**
	 * 明细页签
	 * 每当对一个新的控件表格类型执行grid3读取时，需要先将其条件id值及其对应名称加到此处，督促开发者梳理一遍这种新控件的数据结构
	 */
	public static final String[][] ID_DETAIL = {
			{"登录页面", "root"},
			{"登录页面_账密", "cas"},
			{"登录页面_账密区iframe", "yonbip_login_id"},
			{"首页", "workbench_content"},
			{"用户管理页面", "userIdentityDomNew"},
			{"执行队列页面", "YYCEQ"},
			{"凭证_切换_向左", "voucher-switching-left"},
			{"凭证_切换_向右", "voucher-switching-right"},
			{"凭证_设置", "voucher-setting"},
			{"凭证_键盘", "keyboard"},
			{"凭证_表体_合计行", "newvoucher_fotter-operater"},
			{"租户切换", "tenantToggleId"},
			{"首页_确认框", "home_header"},
	};
	
	public GWCtrlWebElementId(){
		for (String[] ids : ID_DETAIL) {
			if(ids != null){
				CN_ID.put(ids[0], ids[1]);
			}
		}

		showUnitsString();
	}

	public static void showUnitsString() {
		GLog.logToBottom(9, "------------------------------------------------------------------");
		GLog.logToBottom(9, "|                           预置的元素ID值                          |");

		CN_ID.forEach((key, value) -> GLog.logRecordTime(9, "| " + key + " : " + value));

		GLog.logToBottom(9, "|                              END                               |");
		GLog.logToBottom(9, "------------------------------------------------------------------");
	}
}
