package page.widget;

import page.base.UniqueWebElementBase;
import org.openqa.selenium.WebDriver;

public class Language extends UniqueWebElementBase {

	public Language(WebDriver webDriver, String tageName, String atrributeName, String atrributeValue) {
		super(webDriver, tageName, atrributeName, atrributeValue);
	}

	/**
	 *  构造函数
	 *
	 * @param webDriver 目标驱动
	 * @param cssSelector cssSelector表达式
	 */
	public Language(WebDriver webDriver, String cssSelector) {
		super(webDriver, "cssSelector", cssSelector);
	}
	
}
