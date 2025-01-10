package Webdriver;

import java.util.HashMap;
import java.util.Map;

/**
 *  主操作区页面控制
 */
public class GWCtrlMainPage {
	
	/**
	 *  模块名称
	 */
	@SuppressWarnings("CanBeFinal")
    public String MODULE_NAME;
	
	/**
	 *  模块初始名称
	 */
	@SuppressWarnings("CanBeFinal")
    public String MODULE_NAME_FORMAT;
	
	/**
	 *  模块重写名称
	 */
	@SuppressWarnings("CanBeFinal")
    public String MODULE_NAME_NEW;
	
	/**
	 *  模块时间戳
	 */
	public String MODULE_TIMESTAMP = "";
	
	/**
	 *  模块关闭
	 */
	@SuppressWarnings("CanBeFinal")
    public String MODULE_CLOSE;
	
	/**
	 *  页面完整等待标识元素
	 */
	@SuppressWarnings("CanBeFinal")
    public String MODULE_WAIT_ID;
	
	/**
	 * 主操作区顶层页签 使用Id定位
	 */
	@SuppressWarnings("CanBeFinal")
    public Map<String, String> mapTopTabId;
	
	/**
	 * 主操作区上部页签 使用Id定位
	 */
	@SuppressWarnings("CanBeFinal")
    public Map<String, String> mapUpTabId;
	
	/**
	 * 主操作区下部页签 使用Id定位
	 */
	@SuppressWarnings("CanBeFinal")
    public Map<String, String> mapDownTabId;
	
	/**
	 * 主操作区顶层菜单栏按钮 使用Id定位
	 */
	@SuppressWarnings("CanBeFinal")
    public Map<String, String> mapTopMenuBtnId;
	
	/**
	 * 主操作区上部菜单栏按钮 使用Id定位
	 */
	@SuppressWarnings("CanBeFinal")
    public Map<String, String> mapUpMenuBtnId;
	
	/**
	 * 主操作区下部菜单栏按钮 使用Id定位
	 */
	@SuppressWarnings("CanBeFinal")
    public Map<String, String> mapDownMenuBtnId;
	
	/**
	 * 主操作区内容 使用Id定位
	 */
	@SuppressWarnings("CanBeFinal")
    public Map<String, String> mapContentId;
	
	/**
	 * 主操作区顶层页签 使用Xpath定位
	 */
	@SuppressWarnings("CanBeFinal")
    public Map<String, String> mapTopTabXpath;
	
	/**
	 * 主操作区上部页签 使用Xpath定位
	 */
	@SuppressWarnings("CanBeFinal")
    public Map<String, String> mapUpTabXpath;
	
	/**
	 * 主操作区下部页签 使用Xpath定位
	 */
	@SuppressWarnings("CanBeFinal")
    public Map<String, String> mapDownTabXpath;
	
	/**
	 * 主操作区顶层菜单栏按钮 使用Xpath定位
	 */
	@SuppressWarnings("CanBeFinal")
    public Map<String, String> mapTopMenuBtnXpath;
	
	/**
	 * 主操作区上部菜单栏按钮 使用Xpath定位
	 */
	@SuppressWarnings("CanBeFinal")
    public Map<String, String> mapUpMenuBtnXpath;
	
	/**
	 * 主操作区下部菜单栏按钮 使用Xpath定位
	 */
	@SuppressWarnings("CanBeFinal")
    public Map<String, String> mapDownMenuBtnXpath;
	
	/**
	 * 主操作区内容 使用Xpath定位
	 */
	@SuppressWarnings("CanBeFinal")
    public Map<String, String> mapContentXpath;
	
	/**
	 * 主操作区顶层页签 使用Class定位
	 */
	@SuppressWarnings("CanBeFinal")
    public Map<String, String> mapTopTabClass;
	
	/**
	 * 主操作区上部页签 使用Class定位
	 */
	@SuppressWarnings("CanBeFinal")
    public Map<String, String> mapUpTabClass;
	
	/**
	 * 主操作区下部页签 使用Class定位
	 */
	@SuppressWarnings("CanBeFinal")
    public Map<String, String> mapDownTabClass;
	
	/**
	 * 主操作区顶层菜单栏按钮 使用Class定位
	 */
	@SuppressWarnings("CanBeFinal")
    public Map<String, String> mapTopMenuBtnClass;
	
	/**
	 * 主操作区上部菜单栏按钮 使用Class定位
	 */
	@SuppressWarnings("CanBeFinal")
    public Map<String, String> mapUpMenuBtnClass;
	
	/**
	 * 主操作区下部菜单栏按钮 使用Class定位
	 */
	@SuppressWarnings("CanBeFinal")
    public Map<String, String> mapDownMenuBtnClass;
	
	/**
	 * 主操作区内容 使用Class定位
	 */
	@SuppressWarnings("CanBeFinal")
    public Map<String, String> mapContentClass;
	
	/**
	 * 常规 使用Id定位
	 */
	@SuppressWarnings("CanBeFinal")
    public Map<String, String> mapId;
	
	/**
	 * 常规 使用Xpath定位
	 */
	@SuppressWarnings("CanBeFinal")
    public Map<String, String> mapXpath;
	
	/**
	 * 常规 使用Class定位
	 */
	@SuppressWarnings("CanBeFinal")
    public Map<String, String> mapClass;
	
	/**
	 * 常规 动态数据
	 */
	@SuppressWarnings("CanBeFinal")
    public Map<String, String> mapDynamicData;
	
	/**
	 * 构造函数
	 * 
	 * @param moduleName 模块名称
	 */
	public GWCtrlMainPage(String moduleName){
		MODULE_NAME = moduleName;
		MODULE_NAME_FORMAT = MODULE_NAME + "新建";
		MODULE_NAME_NEW = MODULE_NAME + "_";
		MODULE_CLOSE = "顶层页签关闭";
		MODULE_WAIT_ID = "admin_frame";
		
		mapTopTabId = new HashMap<>();
		mapUpTabId = new HashMap<>();
		mapDownTabId = new HashMap<>();
		mapTopMenuBtnId = new HashMap<>();
		mapUpMenuBtnId = new HashMap<>();
		mapDownMenuBtnId = new HashMap<>();
		mapContentId = new HashMap<>();
		mapTopTabXpath = new HashMap<>();
		mapUpTabXpath = new HashMap<>();
		mapDownTabXpath = new HashMap<>();
		mapTopMenuBtnXpath = new HashMap<>();
		mapUpMenuBtnXpath = new HashMap<>();
		mapDownMenuBtnXpath = new HashMap<>();
		mapContentXpath = new HashMap<>();
		mapTopTabClass = new HashMap<>();
		mapUpTabClass = new HashMap<>();
		mapDownTabClass = new HashMap<>();
		mapTopMenuBtnClass = new HashMap<>();
		mapUpMenuBtnClass = new HashMap<>();
		mapDownMenuBtnClass = new HashMap<>();
		mapContentClass = new HashMap<>();
		mapId = new HashMap<>();
		mapXpath = new HashMap<>();
		mapClass = new HashMap<>();
		mapDynamicData = new HashMap<>();
	}
}
