package main.java.Webdriver;

import java.util.HashMap;
import java.util.Map;

/**
 *  主操作区页面控制
 */
public class GWCtrlMainPage {
	
	/**
	 *  模块名称
	 */
	public String MODULE_NAME = "";
	
	/**
	 *  模块初始名称
	 */
	public String MODULE_NAME_FORMAT = "";
	
	/**
	 *  模块重写名称
	 */
	public String MODULE_NAME_NEW = "";
	
	/**
	 *  模块时间戳
	 */
	public String MODULE_TIMESTAMP = "";
	
	/**
	 *  模块关闭
	 */
	public String MODULE_CLOSE = "";
	
	/**
	 *  页面完整等待标识元素
	 */
	public String MODULE_WAIT_ID = "";
	
	/**
	 * 主操作区顶层页签 使用Id定位
	 */
	public Map<String, String> mapTopTabId = null;
	
	/**
	 * 主操作区上部页签 使用Id定位
	 */
	public Map<String, String> mapUpTabId = null;
	
	/**
	 * 主操作区下部页签 使用Id定位
	 */
	public Map<String, String> mapDownTabId = null;
	
	/**
	 * 主操作区顶层菜单栏按钮 使用Id定位
	 */
	public Map<String, String> mapTopMenuBtnId = null;
	
	/**
	 * 主操作区上部菜单栏按钮 使用Id定位
	 */
	public Map<String, String> mapUpMenuBtnId = null;
	
	/**
	 * 主操作区下部菜单栏按钮 使用Id定位
	 */
	public Map<String, String> mapDownMenuBtnId = null;
	
	/**
	 * 主操作区内容 使用Id定位
	 */
	public Map<String, String> mapContentId = null;
	
	/**
	 * 主操作区顶层页签 使用Xpath定位
	 */
	public Map<String, String> mapTopTabXpath = null;
	
	/**
	 * 主操作区上部页签 使用Xpath定位
	 */
	public Map<String, String> mapUpTabXpath = null;
	
	/**
	 * 主操作区下部页签 使用Xpath定位
	 */
	public Map<String, String> mapDownTabXpath = null;
	
	/**
	 * 主操作区顶层菜单栏按钮 使用Xpath定位
	 */
	public Map<String, String> mapTopMenuBtnXpath = null;
	
	/**
	 * 主操作区上部菜单栏按钮 使用Xpath定位
	 */
	public Map<String, String> mapUpMenuBtnXpath = null;
	
	/**
	 * 主操作区下部菜单栏按钮 使用Xpath定位
	 */
	public Map<String, String> mapDownMenuBtnXpath = null;
	
	/**
	 * 主操作区内容 使用Xpath定位
	 */
	public Map<String, String> mapContentXpath = null;
	
	/**
	 * 主操作区顶层页签 使用Class定位
	 */
	public Map<String, String> mapTopTabClass = null;
	
	/**
	 * 主操作区上部页签 使用Class定位
	 */
	public Map<String, String> mapUpTabClass = null;
	
	/**
	 * 主操作区下部页签 使用Class定位
	 */
	public Map<String, String> mapDownTabClass = null;
	
	/**
	 * 主操作区顶层菜单栏按钮 使用Class定位
	 */
	public Map<String, String> mapTopMenuBtnClass = null;
	
	/**
	 * 主操作区上部菜单栏按钮 使用Class定位
	 */
	public Map<String, String> mapUpMenuBtnClass = null;
	
	/**
	 * 主操作区下部菜单栏按钮 使用Class定位
	 */
	public Map<String, String> mapDownMenuBtnClass = null;
	
	/**
	 * 主操作区内容 使用Class定位
	 */
	public Map<String, String> mapContentClass = null;
	
	/**
	 * 常规 使用Id定位
	 */
	public Map<String, String> mapId = null;
	
	/**
	 * 常规 使用Xpath定位
	 */
	public Map<String, String> mapXpath = null;
	
	/**
	 * 常规 使用Class定位
	 */
	public Map<String, String> mapClass = null;
	
	/**
	 * 常规 动态数据
	 */
	public Map<String, String> mapDynamicData = null;
	
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
		
		mapTopTabId = new HashMap<String,String>();
		mapUpTabId = new HashMap<String,String>();
		mapDownTabId = new HashMap<String,String>();
		mapTopMenuBtnId = new HashMap<String,String>();
		mapUpMenuBtnId = new HashMap<String,String>();
		mapDownMenuBtnId = new HashMap<String,String>();
		mapContentId = new HashMap<String,String>();
		mapTopTabXpath = new HashMap<String,String>();
		mapUpTabXpath = new HashMap<String,String>();
		mapDownTabXpath = new HashMap<String,String>();
		mapTopMenuBtnXpath = new HashMap<String,String>();
		mapUpMenuBtnXpath = new HashMap<String,String>();
		mapDownMenuBtnXpath = new HashMap<String,String>();
		mapContentXpath = new HashMap<String,String>();
		mapTopTabClass = new HashMap<String,String>();
		mapUpTabClass = new HashMap<String,String>();
		mapDownTabClass = new HashMap<String,String>();
		mapTopMenuBtnClass = new HashMap<String,String>();
		mapUpMenuBtnClass = new HashMap<String,String>();
		mapDownMenuBtnClass = new HashMap<String,String>();
		mapContentClass = new HashMap<String,String>();
		mapId = new HashMap<String,String>();
		mapXpath = new HashMap<String,String>();
		mapClass = new HashMap<String,String>();
		mapDynamicData = new HashMap<String,String>();
	}
}
