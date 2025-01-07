package main.java.page.unit;

import main.java.page.base.UniqueBase;
import org.openqa.selenium.WebDriver;

/**
 *  自定义元素列表
 *  
 *  @author hewei
 *  @version 20220420181600
 */
public class Header extends UniqueBase {

	/**
	 *构造函数
	 *
	 * @param webDriver 目标驱动
	 * @param tageName 元素标签名
	 * @param atrributeName 元素属性名称
	 * @param atrributeValue 元素属性值
	 */
	public Header(WebDriver webDriver, String tageName, String atrributeName, String atrributeValue) {
		super(webDriver, tageName, atrributeName, atrributeValue);
	}
	
}
