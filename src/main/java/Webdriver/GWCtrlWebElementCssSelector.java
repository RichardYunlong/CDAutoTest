package Webdriver;

import java.util.HashMap;
import java.util.Map;

import Base.GText;
import DT.GLog;

/**
 * 常用元素Id
 */
public class GWCtrlWebElementCssSelector {

	/**
	 * 常用Id
	 */
	@SuppressWarnings("CanBeFinal")
	public static Map<String, Map<String, String>> CN_CSSSELECTOR = new HashMap<>();
	private static Map<String, String> CN_CSSSELECTOR_TEMP = new HashMap<>();

	/**
	 * 明细页签
	 * 每当对一个新的控件表格类型执行grid3读取时，需要先将其条件id值及其对应名称加到此处，督促开发者梳理一遍这种新控件的数据结构
	 */
	public static final String[][] CSSSELECTOR_DETAIL = {
			{ "隐私申明", "div", "class", "workbench-overlay-content" },
			{ "隐私申明_标题", "p", "class", "title" },
			{ "隐私申明_内容", "p", "class", "content" },
			{ "隐私申明_拒绝", "button", "class", "button_f" },
			{ "隐私申明_接受", "button", "class", "button_f button_accept" },

			{ "登录页面_视频", "div", "class", "video-wrap fixed" },
			{ "登录页面_语言", "div", "class", "wui-select-selector" },

			{ "用户管理", "div", "fieldid", "userIdentityManage-tabs-area-tabs" },
			{ "用户管理_中部表格", "div", "fieldid", "TableComponent_antdTable_table" },
			{ "用户管理_添加按钮", "button", "fieldid", "YSFuncbar_addClick_btn" },
			{ "用户管理_中部页签", "div", "fieldid", "userIdentityManage-tabs-area-tabs" }, // wui-tabs-bar
																					// auth-idm-tabBar//wui-tabs-nav
																					// wui-tabs-nav-animated

			{ "凭证_表头", "div", "class", "voucher-header oh" },
			{ "凭证_过滤器", "div", "class", "form-filter" },
			{ "凭证_表体", "div", "class", "voucher-body oldvoucher" },
			{ "凭证_表体合计行", "div", "class", "voucher-footer" },
			{ "凭证_页面综述", "div", "class", "voucher-page-footer" },

			{ "首页_页签栏", "div", "class", "diwork-header-fixed" },
			{ "页签菜单", "div", "class", "menus--aY8qe" },
			{ "首页_工作台", "div", "class", "diwork-content-fixed um-content" },

			{ "应用中心", "div", "class", "menuWrap--UCcbK" },
			{ "菜单内容", "div", "class", "sideBarContent" },
			{ "菜单左侧区域", "div", "class", "scrollarea WebkitScrollbar navbarLeft--iQ8T3" }, // scrollarea
																							// navbarLeft--2qRon
																							// //scrollarea
																							// WebkitScrollbar
																							// navbarLeft--2qRon
			{ "菜单右侧区域", "div", "class", "navbarRight--KFBMe" },
			{ "菜单右侧区域_三级菜单", "div", "class", "navbarThird" },
			{ "菜单活跃区域", "div", "class", "funcWrap active" },

			{ "个人设置", "div", "class", "win--PIcMz" }, // win--PIcMz //win--g5d_0
			{ "当前租户", "div", "class", "home_title" },
			{ "当前租户_名称", "li", "class", "tenant-toggle" },
			{ "租户列表_展开", "i", "fieldid", "settings-item-tenantToggle_suffix_icon" },
			{ "租户名称_输入框", "input", "class", "tenant-search-input" },
			{ "租户名称_搜索", "i", "class",
					"iconfont font_family_u8c__iconfont___2OzrA icon-sousuo__iconfont___17ipx md__index___3HmlR tenant-search-icon-query" },
			{ "租户名称_搜索结果", "input", "class", "tenant-search-input" },
			{ "租户列表", "li", "role", "menuitem" },
			{ "租户列表_目标", "div", "class", "liTitle--2O1pi" },
			{ "租户名称_确认窗体", "div", "class", "wui-modal wui-modal-open wui-modal-centered" },
			{ "租户名称_确认提示", "span", "class", "acConfirm-body-title-keyword" },
			{ "租户名称_确认按钮", "span", "class", "wui-button-text-wrap" },

			{ "执行队列页面_左侧树", "div", "class", "left-container container-mdf-wrapper jDiwork-container" },
			{ "执行队列页面_左侧树_搜索框", "input", "fieldid", "yontest_task_exec_newTreeTable|children|search" },
			{ "执行队列页面_左侧树_搜索", "div", "fieldid", "yontest_task_exec_newTreeTable|children|search_search" },
			{ "执行队列页面_左侧树_领域", "span", "class", "wui-tree-title" },
			{ "执行队列页面_右侧_筛选条件", "div", "class", "new-filter-container" },

			{ "筛选条件_右侧", "div", "class", "new-filter-container" },

			{ "表格_行存储器", "div", "class", "fixedDataTableLayout_rowsContainer" },
			{ "表格_角色行", "div", "role", "row" },
			{ "表格_第一行", "div", "class",
					"fixedDataTableCellLayout_wrap1 public_fixedDataTableCell_wrap1 retail-table-cell" },
			{ "表格_行操作_编辑", "a", "title", "编辑" },
			{ "表格_确认窗", "div", "role", "dialogRoot" },
			{ "表格_操作栏设置", "div", "class", "meta-default-controls" },

			{ "表格_单元格_凭证编码输入", "div", "id", "fiepubInputcode_input" },
			{ "表格_单元格_凭证编码", "div", "id", "fiepubInputcode" },
			{ "表格_单元格_凭证类型保存", "input", "fieldid", "epub_vouchertype|btnSave" },

			{ "表格_悬停行序号字段", "span", "class", "change-rowNo" },
			{ "表格_悬停菜单内容", "div", "class", "context-menu" },
			{ "表格_悬停按钮内容", "div", "class", "acticonCell " }, //
			{ "表格_悬停菜单节点", "a", "class", "context-menu-item" },

			{ "表格_执行进度", "a", "title", "执行进度" },
			{ "表格_终止任务", "a", "title", "终止任务" },
			{ "表格_设置优先级", "a", "title", "设置优先级" },

			{ "二级窗体_设置优先级", "div", "class", "wui-modal-content react-draggable" },
			{ "二级窗体_当前优先级", "input", "fieldid", "yontest_task_exec_newTreeTable|execPriority_search_input" },
			{ "二级窗体_优先级下拉菜单", "div", "class",
					"wui-select-dropdown select_dropdown_execPriority wui-select-dropdown-placement-bottomLeft " },

	};

	public GWCtrlWebElementCssSelector() {
		for (String[] cssselector : CSSSELECTOR_DETAIL) {
			if (CN_CSSSELECTOR_TEMP != null) {
				CN_CSSSELECTOR_TEMP.put("属性类型", cssselector[1]);
				CN_CSSSELECTOR_TEMP.put("属性名称", cssselector[2]);
				CN_CSSSELECTOR_TEMP.put("属性取值", cssselector[3]);
			}
			if (CN_CSSSELECTOR_TEMP != null) {
				CN_CSSSELECTOR.put(cssselector[0], CN_CSSSELECTOR_TEMP);
				CN_CSSSELECTOR_TEMP = new HashMap<>();
			}
		}

		showUnitsString();
	}

	public static String getProValue(String objName, String proValue) {
		return CN_CSSSELECTOR.get(objName).get(proValue);
	}

	public static void showUnitsString() {
		GLog.logToBottom(9, "------------------------------------------------------------------");
		GLog.logToBottom(9, "|                    预置的元素CSSSELECTOR表达式                    |");

		CN_CSSSELECTOR.forEach((key, value) -> GLog.logRecordTime(9,
				"| " + key + " : " + GText.getCssSelectorTxt(value.get("属性类型"), value.get("属性名称"), value.get("属性取值"))));

		GLog.logToBottom(9, "|                              END                               |");
		GLog.logToBottom(9, "------------------------------------------------------------------");
	}
}
