package main.java.Webdriver;

import org.openqa.selenium.WebDriver;

import java.util.HashMap;
import java.util.Map;

/**
 *  主操作区页面控制
 */
public class GWCtrlUnitPage {
	
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
	 * 主操作区内容 定位方式不确定
	 */
	public Map<String, String> mapWebElement = null;

	/**
	 * 构造函数
	 *
	 * @param webDriver 目标驱动
	 * @param moduleName 模块名称
	 * @param pageData 页面数据
	 * @param iframeIndex iframe序号
	 */
	public GWCtrlUnitPage(WebDriver webDriver, String moduleName, HashMap<String, String> pageData, int iframeIndex){
		this.MODULE_NAME = moduleName;
		this.MODULE_NAME_FORMAT = MODULE_NAME + "新建";
		this.MODULE_NAME_NEW = MODULE_NAME + "_";
		this.MODULE_CLOSE = "顶层页签关闭";
		this.MODULE_WAIT_ID = pageData.get("默认等待");
		
		this.mapTopTabId = new HashMap<>();
		this.mapUpTabId = new HashMap<>();
		this.mapDownTabId = new HashMap<>();
		this.mapTopMenuBtnId = new HashMap<>();
		this.mapUpMenuBtnId = new HashMap<>();
		this.mapDownMenuBtnId = new HashMap<>();
		this.mapContentId = new HashMap<>();
		this.mapTopTabXpath = new HashMap<>();
		this.mapUpTabXpath = new HashMap<>();
		this.mapDownTabXpath = new HashMap<>();
		this.mapTopMenuBtnXpath = new HashMap<>();
		this.mapUpMenuBtnXpath = new HashMap<>();
		this.mapDownMenuBtnXpath = new HashMap<>();
		this.mapContentXpath = new HashMap<>();
		this.mapTopTabClass = new HashMap<>();
		this.mapUpTabClass = new HashMap<>();
		this.mapDownTabClass = new HashMap<>();
		this.mapTopMenuBtnClass = new HashMap<>();
		this.mapUpMenuBtnClass = new HashMap<>();
		this.mapDownMenuBtnClass = new HashMap<>();
		this.mapContentClass = new HashMap<>();
		this.mapId = new HashMap<>();
		this.mapXpath = new HashMap<>();
		this.mapClass = new HashMap<>();
		this.mapDynamicData = new HashMap<>();
		this.mapWebElement = new HashMap<>();

        this.mapWebElement.putAll(pageData);
		
		GWCtrlWait.ViewWaitingAllById(webDriver, GTestIndicators.PageShowTime, "template_0");
	}
}
