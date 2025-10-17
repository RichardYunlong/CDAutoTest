package page.widget;

import page.base.UniqueWebElementBase;
import org.openqa.selenium.WebDriver;

public class Workbench extends UniqueWebElementBase {

	/**
	 *  构造函数
	 *
	 * @param webDriver 目标驱动
	 * @param tageName 标签名
	 * @param atrributeName 属性名
	 * @param atrributeValue 属性值
	 */
	public Workbench(WebDriver webDriver, String tageName, String atrributeName, String atrributeValue) {
		super(webDriver, tageName, atrributeName, atrributeValue);
	}

	/**
	 *  构造函数
	 *
	 * @param webDriver 目标驱动
	 * @param cssSelector cssSelector表达式
	 */
	public Workbench(WebDriver webDriver, String cssSelector) {
		super(webDriver, "cssSelector", cssSelector);
	}
}
