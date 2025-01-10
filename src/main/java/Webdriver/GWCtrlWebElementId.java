package Webdriver;

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
	public static String[][] ID_DETAIL = {{"billView", "列表区"}, 
										  {"gvCLMX", "材料明细"}, 
										  {"gvDHDYDCLMX", "到货点验单材料明细"}, 
										  {"gvSTMX", "商砼明细"}, 
										  {"gvZLMX", "周转材料租赁结算单"}, {"gvRZMX", "周转材料租赁合同"}, {"gvCCMX", "租赁周转材料出场单"}, 
										  {"gvPartnerAssList", "分包商集中评价，分包商页签上半部分"}, {"dictView", "分包商类别"}, 
									  	  {"naviTree_orgTree_qryList", "用户权限范围搜索用户后出现的记过列表"}, 
										  {"BizDimTemplateDetails_srcFunc", "组织管理范围常用对象"}, 
										  {"userList", "用户删除查询出的用户信息"},
										  {"RegDialog_cardPanel_orgSelector_targetList", "授权用户树"},
										  {"RegDialog_cardPanel_orgSelector_sourcePanel_qryList", "选择授权对象、选择功能操作表格"}, 
										  {"RegDialog_cardPanel_funcSelector_tar", "授权明细项列表"},
										  {"undefined_deptSelector_qryList", "新建用户时搜索部门后出现的记过列表"},
										  {"treeGrid", "合同数据源"}, {"tgGCBW", "工程部位分解"}, 
										  {"treeView", "二级窗体类别树表格"}, {"Source", "二级窗体明细树表格"}, {"Selected", "二级窗体选中树表格"},
										  {"gvFyxmx", "运杂费记录单费用项明细"},{"gvQTFY", "其他费用"},{"gvFYMX", "运杂费结算单-费用明细"},{"gvJSMX", "材料付款申请单-结算明细"},
										  {"gridView", "工程款到账记录-收款明细"},{"gridView_ZFMX", "项目资金计划-支付明细左侧支付类型"},{"treeGrid_FQTFY", "项目资金计划-支付明细右侧数据源"},
										  {"tgCSFYX", "目标责任成本编制（测算方式）-测算明细"},{"gvJGFL", "价格分类"},{"tgQDMX", "清单明细"},{"tgQDBWMX", "清单部位明细"},{"wvSZDJGC", "设置单价构成"},{"gvZJFL", "组价分类"},{"gvCLJGBZJ", "材料价格表组价"},
										  {"gvJXSBMX","机械设备租赁申请单-机械明细"},{"gvRZMX","机械设备租赁合同-日租明细"},{"gridView_JXMX","机械设备进场验收单-机械明细"},
										  {"gvJXMX","机械设备购置结算单-机械明细"},{"gridCLMX","混凝土配合比-配比材料明细"},{"gvPDD","自有周转材料现场盘点单-材料明细-数据区"},{"tgSJLB","材料三级帐-日期树"},
										  {"tgvCLPCMX","材料批次明细-数据区"},{"tgCLHZ","材料汇总"},{"gvCLHZ","采购计划-材料汇总"},{"gvFJF","商品混凝土供应合同-附加费明细"},{"gvFJFMX","商品混凝土对账单-附加费明细"},{"gvDJMX","商品混凝土对账单-单据明细"},
										  {"gvDZDMX","商品混凝土结算单-商砼对账单明细"},{"gvXPMX","商品混凝土结算单-商砼小票明细"},{"gvYZMX","周转材料租赁合同-月租明细"},{"gvGCLMX","周转材料租赁合同-工程量明细"},{"gvWXXMX","周转材料租赁合同-维修项明细"},{"gvBSMX","报损明细-数据区"},
										  {"gvBSHZ","租赁周转材料出场单-报损汇总"},{"gvWXMX","租赁周转材料出场单-维修明细"},{"CategoryTreeGrid","定额分类"},{"BudgetItemGV","定额信息"},
										  {"ResourceDetailGV","资源明细"},{"gvFixedCate","选择资源-资源业务分类"},{"tgCenterCate","选择资源-资源分类"},{"gvResourceDateail","选择资源-资源明细"},
										  {"gvJSTZMX","最终结算调整"},{"gvTZMX","停租明细"},{"gvQTFYs","材料采购结算单-其他费用"},{"leftTreeGrid","分包项目字典分类-左搜索树"},{"rightTreeGrid","分包项目字典分类-右数据区"},
										  {"FBFLMX","指导价明细-左数据区"},{"FBMXXMX","指导价明细-右数据区"},{"tgFYX","劳务分包工程计划-费用项-数据区"},{"cvFKMX","付款明细"},{"gYSSMXs","劳务分包变更-预算书-数据区"},{"gvRWMX","任务明细-数据区"},{"gvJFFYMX","奖罚费用明细-数据区"},
										  {"tgFBFX","产值统计-分部分项"},{"tgBLJJCX","产值统计-计价程序"},{"treeGrid_SRMX","项目资金计划-收入明细"},{"gridView_ZFHZ","项目资金计划-支付汇总"},{"gridView_QTZC","公司资金计划-其他支出"},{"gridView_QTSR","公司资金计划-其他收入"},
										  {"gvFKMX","公司付款申请单集中审批-付款明细"},{"gvFTJG","费用分摊单-分摊机构"},{"gvFYX","费用分摊单-左侧分摊明细"},{"gvFTJGMX","费用分摊单-右侧分摊明细"},{"gridView_Import","预算管理-导入预算数据区"},{"tgCSXM","预算管理-措施项目数据区"},
										  {"tgQTXM","预算管理-其他项目数据区"},{"gvRCJHZ","预算管理-人材机汇总数据区"},{"gvFYXMX","其他支出合同结算单-费用项明细"},{"gvYSSMXs","专业分包变更-预算书-数据区"},{"gridView_ImportBoao","目标责任成本编制(预算方式)-导入预算数据区"},
										  {"tgRJFYX","预算管理-认价明细"},{"gvRCJ","人材机汇总"},{"tgJJCX","计价程序"},{"tgCSFL","测算分类-数据区"},{"gridView_FYMX","机械设备进场验收单-费用明细"},{"gvHZMX","机械设备租赁结算单-租赁汇总明细"},{"gvJBMX","机械设备租赁结算单-加班明细"},
										};
	
	public GWCtrlWebElementId(){
		
		//
		CN_ID.put("模块搜索-搜索框","searchKey");
		
		//一级窗体
		//主体显示区
		CN_ID.put("门户页","template_0");
		CN_ID.put("desktop","PTL.Mng.Desktop.previewPanel");
		
		//登入登出
		CN_ID.put("账号","username");
		CN_ID.put("密码","password");
		CN_ID.put("登入","btLogin");
		
		//切换层级
		CN_ID.put("打开机构层级","orgNameGroup");
		CN_ID.put("机构层级名称","orgName");
		CN_ID.put("机构层级","orgNameDrop");
		CN_ID.put("机构容器","PTL.frame.NaviPanel");
		CN_ID.put("查看机构树","PTL.frame.NaviPanel.btnShowTree");
		CN_ID.put("查看机构表","PTL.frame.NaviPanel.btnShowList");
		CN_ID.put("机构搜索框","PTL.frame.NaviPanel.edQuery");
		CN_ID.put("机构列表","PTL.frame.NaviList");
		
		//用户状态
		CN_ID.put("用户","userNameGroup");
		CN_ID.put("用户状态","userNameDrop");
		CN_ID.put("我的桌面","myDesktop");
		CN_ID.put("我的设备","myDevice");
		CN_ID.put("个人信息","ModifiedUserInfo");
		CN_ID.put("修改密码","ModifiedPwd");
		CN_ID.put("注销","sysExit");
		
		//切换主题
		CN_ID.put("打开主题选择","themeGroup");
		CN_ID.put("确认主题选择","themeDropOKBtn");
		
		//顶层页签菜单栏
		CN_ID.put("左侧根菜单","l_navigator");
		CN_ID.put("左侧列表菜单","l_menu_list");
		CN_ID.put("顶端根菜单","topDiv");
		CN_ID.put("顶端列表菜单","nav_item");
		
		//顶层页签菜单栏
		CN_ID.put("顶层页签栏","lst_tabpages");
		
		//列表区-菜单
		CN_ID.put("新_建","btn_New");
		CN_ID.put("新_建合同","btn_New");
		CN_ID.put("查_看","btn_View");
		CN_ID.put("编_辑","btn_Edit");
		CN_ID.put("删_除","btn_Delete");
		CN_ID.put("提_交","btnSubmit");
		CN_ID.put("新_增", "btnAdd");
		CN_ID.put("新_增下级", "btnAddChild");
		CN_ID.put("保_存", "btnSave");
		CN_ID.put("移_除", "btnRemove");
		CN_ID.put("放_弃", "btnCancel");
		CN_ID.put("废_弃", "btnDiscard");
		CN_ID.put("启_用", "btnEnable");
		CN_ID.put("新_建预算", "btnNew");
	    CN_ID.put("批量导入", "btnImportProject");
		
		//列表区-细表
		CN_ID.put("单据列表","billView");
		CN_ID.put("字典列表","dictView");
		
		//单据区-菜单
		CN_ID.put("新建", "btnNew");
		CN_ID.put("复制", "btnCopy");
		CN_ID.put("编辑", "btnModify");
		CN_ID.put("删除", "btnDelete");
		CN_ID.put("类别-删除", "btnRemove");
		CN_ID.put("保存", "btnSave");
		CN_ID.put("放弃", "btGiveup");
		CN_ID.put("提交", "btnSubmit");
		CN_ID.put("取消提交", "btnSubmit");
		CN_ID.put("打印", "btnListPrintWindowView");
		CN_ID.put("刷新", "btnRefresh");
		CN_ID.put("选择字典材料", "btnImportMaterial");
	    CN_ID.put("黑名单删除按钮", "btn_Delete");
		CN_ID.put("加入黑名单", "btnAddBlacklist");
	    CN_ID.put("取消黑名单", "btnDeleteBlacklist");
	    CN_ID.put("选择字典机械设备", "btnSelectMachinery");
		
		
		//单据区-基本信息
		CN_ID.put("基本信息", "InfoContainerView");
		CN_ID.put("单据编号", "Efd_Code");
		CN_ID.put("预算编号", "Efd_YS_Code");
		
		//单据区-合同明细
		CN_ID.put("工程部位分解", "tgGCBW");
		CN_ID.put("价格分类", "gvJGFL");
		CN_ID.put("清单明细", "tgQDMX");
		CN_ID.put("清单部位明细", "tgQDBWMX");
		CN_ID.put("设置单价构成", "wvSZDJGC");
		CN_ID.put("组价分类", "gvZJFL");
		CN_ID.put("材料价格表组价", "gvCLJGBZJ");
		
		//单据区-细表-材料明细
		CN_ID.put("材料明细", "gvCLMX");
		CN_ID.put("材料到货点验单-材料明细", "gvDHDYDCLMX");
		
		//单据区-细表-材料汇总
		CN_ID.put("材料汇总", "tgCLHZ");
		CN_ID.put("采购计划-材料汇总", "gvCLHZ");
		
	    //单据区-细表-商砼明细
        CN_ID.put("商砼明细", "gvSTMX");
		
		//单据区-细表-单据明细
		CN_ID.put("单据明细", "gvDJMX");
		
		//单据区-细表-评价信息
		CN_ID.put("评价信息", "gvPJXX");
		
		//单据区-细表-评价信息
        CN_ID.put("新增银行信息", "btn_actAddRecord");
		
		//单据区-细表-供应商列表
		CN_ID.put("供应商列表", "gvPartnerAssList");
		
		//单据去-细表-费用项明细/其他费用
		CN_ID.put("运杂费记录单-费用项明细", "gvFyxmx");
		CN_ID.put("其他费用", "gvQTFY");
		CN_ID.put("运杂费结算单-费用明细", "gvFYMX");
		CN_ID.put("材料付款申请单-结算明细", "gvJSMX");
		
		//单据区-细表-机械设备明细
		CN_ID.put("机械设备明细", "gvJXSBMX");
		CN_ID.put("机械设备购置结算单-机械明细", "gvJXMX");

		//二级窗体
		//顶部页签
		CN_ID.put("外部单位", "tabView__UnittabView_tab");
		CN_ID.put("内部单位", "tabView__OrgtabView_tab");
		
		//功能按钮
		CN_ID.put("二级窗体 移入", "btn_actSelect");
	    CN_ID.put("二级窗体 移入新", "btn_actSelect_New");
		CN_ID.put("二级窗体 移出", "btn_actRemove");
	    CN_ID.put("二级窗体 移出新", "btn_actRemove_New");
		CN_ID.put("二级窗体 全部移入", "btn_actSelectAll");
	    CN_ID.put("二级窗体 全部移入新", "btn_actSelectAll_New");
		CN_ID.put("二级窗体 全部移出", "btn_actRemoveAll");
	    CN_ID.put("二级窗体 全部移出新", "btn_actRemoveAll_New");
		
		CN_ID.put("二级窗体 确定", "btn_actSubmit");
		CN_ID.put("二级窗体 取消", "btn_actCancel");
		CN_ID.put("二级窗体 单确定", "btn_act_Submit");
		
		//
		CN_ID.put("二级窗体-选择部门-确定", "btn_actOK");
		CN_ID.put("二级窗体-预算导入-确定", "btnOk");
		
		CN_ID.put("清单计价构成-确定", "btnSetQDJJGC_OK");
		CN_ID.put("清单计价构成-取消", "btnSetQDDJGC_Cancel");
		
		//表格数据
		//表格数据-类别树
		CN_ID.put("类别树", "treeView");
		
		CN_ID.put("类别树-展收栏", "treeView_toolbar");
		CN_ID.put("类别树-搜索区", "treeView_search");
		CN_ID.put("类别树-搜索区-搜索框", "treeSearch");
		//表格数据-数据源
		CN_ID.put("数据源", "Source");
		CN_ID.put("数据源-综合搜索", "Source_toolbar");
		CN_ID.put("数据源-综合搜索-搜索条件", "query_panel");
		CN_ID.put("数据源-分类搜索", "Source_clSearch");
		CN_ID.put("数据源-分类搜索-材料名称", "clmcText");
		CN_ID.put("数据源-分类搜索-规格", "clggText");
		CN_ID.put("数据源-分类搜索-型号", "clxhText");
		CN_ID.put("数据源-分类搜索-搜索钮", "btnCLSearch");
		CN_ID.put("数据源-分页", "Source_paging");
		//表格数据-选中值
		CN_ID.put("选中值", "Selected");
		
		//参考数据 
		CN_ID.put("参考树", "treeGrid");//与【评价内容】细表id相同
		CN_ID.put("单据树", "gvBill");
		CN_ID.put("单位树", "layoutPanel1");
		
		//目标责任成本编制
		CN_ID.put("费用项", "tgFYX");
		CN_ID.put("计价程序", "tgBLJJCX");
		CN_ID.put("分部分项", "tgFBFX");
		CN_ID.put("预算明细", "gvFYMX");
		CN_ID.put("测算方式", "tgCSFYX");

		//选择合同二级窗体中的查询方案
		CN_ID.put("查询方案", "_menu_button");
		
		//查看科目说明
		CN_ID.put("文档模板树", "docTempFolderTree_dictView");
		CN_ID.put("文档模板树-搜索框", "KeyWorddictView");
		
		//细表-费用明细
		CN_ID.put("删除费用项", "btnDelete1");
	}
}
