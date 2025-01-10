package page.unit;

import Base.GText;
import DT.GLog;
import Webdriver.GTestIndicators;
import Webdriver.GWCtrlWait;
import page.base.UniqueBase;
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
	@SuppressWarnings({"FieldCanBeLocal", "FieldMayBeFinal", "CanBeFinal"})
    private WebElement navbarLeft;

	/**
	 *  二级菜单树的唯一WebElement对象
	 *  用于需要对整体进行操作时调用
	 */
	@SuppressWarnings("FieldCanBeLocal")
	private WebElement navbarRightLevel2 = null;

	/**
	 *  三级菜单树的唯一WebElement对象
	 *  用于需要对整体进行操作时调用
	 */
	@SuppressWarnings("FieldCanBeLocal")
	private WebElement navbarRightLevel3 = null;

	/**
	 *  一级菜单
	 */
	private MenuScrollArea menuScrollAreaLevel1 = null;

	/**
	 *  二级菜单
	 */
	private MenuScrollAreaByTitle menuScrollAreaByTitleLevel2 = null;

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
			menuScrollAreaLevel1 = new MenuScrollArea(webDriver, navbarLeft);
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
	@SuppressWarnings("unused")
    public void searchAndCLick(WebDriver webDriver, String name) {
		menuScrollAreaLevel1.searchEnter(webDriver, name);

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
	 *  @param resultLevel3 应用中心结果树选择。仅支持长度为2的String[]，即3级菜单
	 *  @param leaf4 应用中心结果树选择。仅支持长度为2的String[]，即4级菜单
	 */
	public void click(WebDriver webDriver, String menuLevel1, String menuLevel2, String resultLevel3, String leaf4) {
		menuScrollAreaLevel1.click(webDriver, menuLevel1);
		GLog.logRecordTime(0, "选择一级菜单[" + menuLevel1 + "]成功");

		navbarRightLevel2 = webDriver.findElement(By.cssSelector(GText.getCssSelectorTxt("div", "class", "scrollarea navbarSecond")));
		GWCtrlWait.ViewWaitingAllByWebElement(webDriver, GTestIndicators.PageShowTime, navbarRightLevel2);
		if (null != navbarRightLevel2) {
			menuScrollAreaByTitleLevel2 = new MenuScrollAreaByTitle(webDriver, navbarRightLevel2);
		}

		if (null != menuScrollAreaByTitleLevel2) {
			menuScrollAreaByTitleLevel2.click(webDriver, menuLevel2);
			GLog.logRecordTime(0, "选择二级菜单[" + menuLevel2 + "]成功");

			GWCtrlWait.ViewWaitingAllByCssSelector(webDriver, GTestIndicators.PageShowTime, GText.getCssSelectorTxt("div", "class", "funcWrap active"));
			WebElement navbarRight = webDriver.findElement(By.cssSelector(GText.getCssSelectorTxt("div", "class", "funcWrap active")));

			if (null != navbarRight) {
				navbarRightLevel3 = navbarRight.findElement(By.cssSelector(GText.getCssSelectorTxt("div", "class", "navbarThird")));
				GWCtrlWait.ViewWaitingAllByWebElement(webDriver, GTestIndicators.PageShowTime, navbarRightLevel3);
				if (null != navbarRightLevel3) {
					MenuScrollAreaByLabel menuScrollAreaByTLabelLevel3 = new MenuScrollAreaByLabel(webDriver, navbarRightLevel3);
					//noinspection ConstantValue
					if (null != menuScrollAreaByTLabelLevel3) {
						menuScrollAreaByTLabelLevel3.click(webDriver, resultLevel3);
						GLog.logRecordTime(0, "选择三级菜单[" + resultLevel3 + "]成功");

						GWCtrlWait.ViewWaitingAllByCssSelector(webDriver, GTestIndicators.PageShowTime, GText.getCssSelectorTxt("div", "class", "funcWrap active"));
						navbarRight = webDriver.findElement(By.cssSelector(GText.getCssSelectorTxt("div", "class", "funcWrap active")));

						if (null != navbarRight) {
							navbarRightLevel3 = navbarRight.findElement(By.cssSelector(GText.getCssSelectorTxt("div", "class", "navbarThird")));
							GWCtrlWait.ViewWaitingAllByWebElement(webDriver, GTestIndicators.PageShowTime, navbarRightLevel3);
							if (null != navbarRightLevel3) {
								MenuScrollAreaBySpan menuScrollAreaBySpanLevel4 = new MenuScrollAreaBySpan(webDriver, navbarRightLevel3);
								//noinspection ConstantValue
								if (null != menuScrollAreaBySpanLevel4) {
									menuScrollAreaBySpanLevel4.click(webDriver, leaf4);
									GLog.logRecordTime(0, "选择四级菜单[" + leaf4 + "]成功");
								}
							}
						} else {
							GLog.logRecordTime(0, "菜单显示失败，请检查页面加载情况");
						}
					}
				} else {
					GLog.logRecordTime(0, "菜单显示失败，请检查页面加载情况");
				}

				GLog.logRecordTime(0, "选择菜单全路径[" + menuLevel1 + "-" + menuLevel2 + "-" + resultLevel3 + "-" + leaf4 + "]成功");
			}

			GWCtrlWait.ViewWaitingAllById(webDriver, GTestIndicators.PageShowTime, "workbench-root");
		}
	}
}
