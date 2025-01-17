package page.widget;

import Base.GText;
import DT.GLog;
import Webdriver.GTestIndicators;
import Webdriver.GWCtrlWait;
import page.base.UniqueWebElementBase;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 *  应用中心
 *  
 *  @author hewei
 *  @version 20220418093600
 */
public class MenuWarp extends UniqueWebElementBase {
	
	/**
	 *  左侧菜单树的唯一WebElement对象
	 *  用于需要对整体进行操作时调用
	 */
	@SuppressWarnings({"FieldCanBeLocal", "FieldMayBeFinal", "CanBeFinal"})
    private WebElement navbarLeft;

	/**
	 *  右侧菜单树的唯一WebElement对象
	 *  用于需要对整体进行操作时调用
	 */
	@SuppressWarnings({"FieldCanBeLocal", "FieldMayBeFinal", "CanBeFinal"})
	private WebElement navbarRight;

	/**
	 *  一级菜单
	 */
	@SuppressWarnings({"FieldCanBeLocal", "FieldMayBeFinal", "CanBeFinal"})
	private MenuScrollArea menuScrollArea1 = null;

	/**
	 *  二级菜单
	 */
	@SuppressWarnings({"FieldCanBeLocal", "FieldMayBeFinal", "CanBeFinal"})
	private ScrollareaLeftChild scrollareaLeftChild2 = null;

	/**
	 *  三级菜单
	 */
	@SuppressWarnings({"FieldCanBeLocal", "FieldMayBeFinal", "CanBeFinal"})
	private MenuScrollAreaByTitle menuScrollAreaByTitleLevel3 = null;

	/**
	 *  四级菜单
	 */
	@SuppressWarnings({"FieldCanBeLocal", "FieldMayBeFinal", "CanBeFinal"})
	private MenuScrollAreaByLabel menuScrollAreaByTLabelLevel4 = null;

	/**
	 *  五级菜单
	 */
	@SuppressWarnings({"FieldCanBeLocal", "FieldMayBeFinal", "CanBeFinal"})
	private MenuScrollAreaBySpan menuScrollAreaBySpanLevel5 = null;

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
			menuScrollArea1 = new MenuScrollArea(webDriver, navbarLeft);
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
		menuScrollArea1.searchEnter(webDriver, name);

		GLog.logRecordTime(9, "选择目标[" + name + "]成功");
	}

	/**
	 *  重新加载右侧菜单
	 *
	 * @param webDriver 目标驱动
	 */
	private void reloadNavbarRight(WebDriver webDriver){
		GWCtrlWait.ViewWaitingAllByCssSelector(webDriver, GTestIndicators.PageShowTime, GText.getCssSelectorTxt("div", "class", "navbarRight--2lj4M"));
		navbarRight = webDriver.findElement(By.cssSelector(GText.getCssSelectorTxt("div", "class", "navbarRight--2lj4M")));
	}
	
	/**
	 *  点击菜单路径
	 *  1.点击一级目录，展开二级菜单
	 *  2.点击二级菜单，显示应用中心右侧结果树
	 *  3.点击分类结果，直接打开模块
	 *
	 *  @param webDriver 目标驱动
	 *  @param org 应用中心菜单树选择，。仅支持长度为2的String[]，即一级菜单：大领域云
	 *  @param department 应用中心结果树选择。仅支持长度为2的String[]，即二级菜单：领域云
	 *  @param product 应用中心结果树选择。仅支持长度为2的String[]，即三级菜单：子产品
	 *  @param module 应用中心结果树选择。仅支持长度为2的String[]，即四级菜单：模块
	 *  @param node 应用中心结果树选择。仅支持长度为2的String[]，即五级菜单：节点
	 */
	public void click(WebDriver webDriver,
					  String org,
					  String department,
					  String product,
					  String module,
					  String node) {


		chooseOrg(webDriver, org);
		chooseDepartment(webDriver, department);
		reloadNavbarRight(webDriver);
		chooseProduct(webDriver, product);
		reloadNavbarRight(webDriver);
		chooseModule(webDriver, module);
		reloadNavbarRight(webDriver);
		chooseNode(webDriver, node);
	}

	/**
	 *  点击菜单路径(无领域云菜单，如“数字化建模”)
	 *  1.点击一级目录，展开二级菜单
	 *  2.点击二级菜单，显示应用中心右侧结果树
	 *  3.点击分类结果，直接打开模块
	 *
	 *  @param webDriver 目标驱动
	 *  @param org 应用中心菜单树选择，。仅支持长度为2的String[]，即一级菜单：大领域云
	 *  @param product 应用中心结果树选择。仅支持长度为2的String[]，即三级菜单：子产品
	 *  @param module 应用中心结果树选择。仅支持长度为2的String[]，即四级菜单：模块
	 *  @param node 应用中心结果树选择。仅支持长度为2的String[]，即五级菜单：节点
	 */
	public void click(WebDriver webDriver,
					  String org,
					  String product,
					  String module,
					  String node) {


		chooseOrg(webDriver, org);
		reloadNavbarRight(webDriver);
		chooseProduct(webDriver, product);
		reloadNavbarRight(webDriver);
		chooseModule(webDriver, module);
		reloadNavbarRight(webDriver);
		chooseNode(webDriver, node);
	}

	/**
	 *  一级菜单：大领域云
	 *
	 * @param webDriver 目标驱动
	 * @param org 应用中心菜单树选择，。仅支持长度为2的String[]，即一级菜单：大领域云
	 */
	private void chooseOrg(WebDriver webDriver, String org){
		menuScrollArea1.click(webDriver, org);
		GLog.logRecordTime(9, "选择菜单[" + org + "]成功");
	}

	/**
	 *  二级菜单：领域云
	 *
	 * @param webDriver 目标驱动
	 * @param department 应用中心结果树选择。仅支持长度为2的String[]，即二级菜单：领域云
	 */
	private void chooseDepartment(WebDriver webDriver, String department){
		menuScrollArea1.clickChild(webDriver, department);
		GLog.logRecordTime(9, "选择菜单[" + department + "]成功");
	}

	/**
	 *  三级菜单：子产品
	 *
	 * @param webDriver 目标驱动
	 * @param product 应用中心结果树选择。仅支持长度为2的String[]，即三级菜单：子产品
	 */
	private void chooseProduct(WebDriver webDriver, String product){
		menuScrollAreaByTitleLevel3 = new MenuScrollAreaByTitle(webDriver, navbarRight.findElement(By.cssSelector(GText.getCssSelectorTxt("div", "class", "funcWrap active"))));
		//noinspection ConstantValue
		if (null != menuScrollAreaByTitleLevel3) {
			menuScrollAreaByTitleLevel3.click(webDriver, product);
			GLog.logRecordTime(9, "选择菜单[" + product + "]成功");
		}else {
			GLog.logRecordTime(9, "菜单显示失败，请检查页面加载情况");
		}
	}

	/**
	 *  四级菜单：模块
	 *
	 * @param webDriver 目标驱动
	 * @param module 应用中心结果树选择。仅支持长度为2的String[]，即四级菜单：模块
	 */
	private void chooseModule(WebDriver webDriver, String module){
		menuScrollAreaByTLabelLevel4 = new MenuScrollAreaByLabel(webDriver, navbarRight.findElement(By.cssSelector(GText.getCssSelectorTxt("div", "class", "funcWrap active"))));
		//noinspection ConstantValue
		if (null!= menuScrollAreaByTLabelLevel4) {
			menuScrollAreaByTLabelLevel4.click(webDriver, module);
			GLog.logRecordTime(9, "选择菜单[" + module + "]成功");
		}else {
			GLog.logRecordTime(9, "菜单显示失败，请检查页面加载情况");
		}
	}

	/**
	 *  五级菜单：节点
	 *
	 * @param webDriver 目标驱动
	 * @param node 应用中心结果树选择。仅支持长度为2的String[]，即四级菜单：模块
	 */
	private void chooseNode(WebDriver webDriver, String node){
		menuScrollAreaBySpanLevel5 = new MenuScrollAreaBySpan(webDriver, navbarRight.findElement(By.cssSelector(GText.getCssSelectorTxt("div", "class", "funcWrap active"))));
        //noinspection ConstantValue
        if (null!= menuScrollAreaBySpanLevel5) {
			menuScrollAreaBySpanLevel5.click(webDriver, node);
			GLog.logRecordTime(9, "选择菜单[" + node + "]成功");
		}else {
			GLog.logRecordTime(9, "菜单显示失败，请检查页面加载情况");
		}
	}
}
