package main.java.page.unit;

import main.java.Base.GText;
import main.java.DT.GLog;
import main.java.Webdriver.GTestIndicators;
import main.java.Webdriver.GWCtrlWait;
import main.java.page.base.UniqueBase;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 *  应用中心
 *  
 *  @author hewei
 *  @version 20220418093600
 */
public class MenuWarp extends UniqueBase {
	
	/**
	 *  左侧菜单树的唯一WebElement对象
	 *  用于需要对整体进行操作时调用
	 */
	@SuppressWarnings("FieldCanBeLocal")
    private WebElement navbarLeft = null;
	
	/**
	 *  右侧结果树的唯一WebElement对象
	 *  用于需要对整体进行操作时调用
	 */
	@SuppressWarnings("FieldCanBeLocal")
	private WebElement navbarRight = null;
	
	/**
	 *  左侧菜单树对象
	 */
	private ScrollareaLeft scrollareaLeft = null;
	
	/**
	 *  右侧结果树对象
	 */
	private ScrollareaRight scrollareaRight = null;
	
	/**
	 *  构造函数
	 *
	 * @param webDriver 目标驱动
	 * @param tageName 目标标签名
	 * @param atrributeName 目标属性名
	 * @param atrributeValue 目标属性值
	 */
	public MenuWarp(WebDriver webDriver, String tageName, String atrributeName, String atrributeValue) {
		super(webDriver, tageName, atrributeName, atrributeValue);
		
		navbarLeft = webDriver.findElement(By.cssSelector(GText.getCssSelectorTxt("div", "class", "scrollarea navbarLeft--2qRon")));
		
		if(null != navbarLeft) {
			GWCtrlWait.ViewWaitingAllByWebElement(webDriver, GTestIndicators.PageShowTime, navbarLeft);
			scrollareaLeft = new ScrollareaLeft(webDriver, navbarLeft);
		}
	}

	/**
	 *  根据菜单名称搜索并点击模块
	 *  点击模块名直接打开模块，不显示应用中心右侧结果树
	 *  目前只有左侧菜单树有专用的搜索控件
	 *
	 *  @param webDriver 目标驱动
	 *  @param name 目标模块名称
	 */
	public void searchAndCLick(WebDriver webDriver,String name) {
		scrollareaLeft.searchEnter(webDriver, name);

		GLog.logRecordTime(0, "选择目标[" + name + "]成功");
	}
	
	/**
	 *  点击菜单路径
	 *  1.点击一级目录，展开二级菜单
	 *  2.点击二级菜单，显示应用中心右侧结果树
	 *  3.点击分类结果，直接打开模块
	 *
	 *  @param webDriver 目标驱动
	 *  @param menuLevel1 应用中心菜单树选择。仅支持长度为2的String[]，即1级菜单
	 *  @param menuLevel2 应用中心结果树选择。仅支持长度为2的String[]，即2级菜单
	 *  @param resultLevel1 应用中心结果树选择。仅支持长度为2的String[]，即3级菜单
	 *  @param leaf 应用中心结果树选择。仅支持长度为2的String[]，即4级菜单
	 */
	public void click(WebDriver webDriver, String menuLevel1, String menuLevel2, String resultLevel1, String leaf) {
		scrollareaLeft.clickMenuLevel1(menuLevel1);
		scrollareaLeft.clickMenuLevel2(menuLevel2);
		
		GLog.logRecordTime(0, "选择左侧菜单区[" + menuLevel1 + "-" + menuLevel2 + "]成功");
		
		navbarRight = webDriver.findElement(By.cssSelector(GText.getCssSelectorTxt("div", "class", "navbarRight--2lj4M")));
		GWCtrlWait.ViewWaitingAllByWebElement(webDriver, GTestIndicators.PageShowTime, navbarRight);
		
		if(null != navbarRight) {
			scrollareaRight = new ScrollareaRight(webDriver, navbarRight);
		}
		
		if(null != scrollareaRight) {
			scrollareaRight.clickMenuLevel1(resultLevel1);
			scrollareaRight.clickLeaf(leaf);
			
			GLog.logRecordTime(0, "选择菜单全路径[" + menuLevel1 + "-" + menuLevel2 + "-" + resultLevel1 + "-" + leaf + "]成功");
		}
		
		GWCtrlWait.ViewWaitingAllById(webDriver, GTestIndicators.PageShowTime, "workbench-root");
	}
}
