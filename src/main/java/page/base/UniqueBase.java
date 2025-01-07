package main.java.page.base;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 *  唯一元素基本定位
 *  1.根据不同产品的全段源码特性，来自定义适合该类产品的元素基本定位方式；2.本类仅适用于定位目标是一个唯一存在的元素
 *  
 *  @author hewei
 *  @version 20220418100700
 */
public class UniqueBase {
	
	/**
	 * 目标元素的WebElement对象
	 */
	private WebElement uniqueRoot = null;
	
	/**
	 * 获得目标元素的WebElement对象
	 *
	 * @return 目标元素的WebElement对象
	 */
	public WebElement getUniqueRoot() {
		return uniqueRoot;
	}

	/**
	 * 构造函数一
	 * 1.通过唯一id的值定位目标元素
	 * 2.如果您所在的产研团队比较愿意配合自动化测试组修改产品前端代码，则推荐此定位方式为主要方式
	 * 3.使用此方式并不意味着可以解决您所有的定位需求，必要时依然需要使用selenium原生的其他如干种定位方式
	 *
	 * @param webDriver 浏览器驱动对象
	 * @param id 元素id的值
	 */
	public UniqueBase(WebDriver webDriver, String id) {
		uniqueRoot = QueryElement.ui_Q(webDriver, "id", id);
	}

	/**
	 * 构造函数二
	 * 1.通过唯一cssSelector表达式定位目标元素
	 * 2.如果您所在的产研团队不太愿意配合自动化测试组修改产品前端代码，则推荐此定位方式为主要方式
	 * 3.使用此方式并不意味着可以解决您所有的定位需求，必要时依然需要使用selenium原生的其他如干种定位方式
	 *
	 * @param webDriver 浏览器驱动对象
	 * @param locateTagName 元素标签名
	 * @param locateAtrributeName 元素属性名称
	 * @param locateArributeValue 元素属性值
	 */
	public UniqueBase(WebDriver webDriver, String locateTagName, String locateAtrributeName, String locateArributeValue) {
		uniqueRoot = QueryElement.ui_Q(webDriver, "cssSelector", locateArributeValue, locateTagName, locateAtrributeName);
	}
	
	/**
	 * 构造函数三
	 * 1.通过唯一cssSelector表达式定位目标元素
	 * 2.如果无法直接找到元素，但是可以找到距离目标元素最近的父级元素，然后再按照唯一父级元素定位目标元素，则可使用此方式
	 * 3.如果您所在的产研团队不太愿意配合自动化测试组修改产品前端代码，则推荐此定位方式为主要方式
	 * 4.使用此方式并不意味着可以解决您所有的定位需求，必要时依然需要使用selenium原生的其他如干种定位方式
	 *
	 * @param webDriver 浏览器驱动对象
	 * @param parent 元素的唯一最近父级
	 * @param locateQureyType 元素定位方式
	 * @param locateQureyCondition 元素定位条件，精确查询
	 */
	public UniqueBase(WebDriver webDriver, WebElement parent, String locateQureyType, String locateQureyCondition) {
		uniqueRoot = QueryElement.ui_Q_V(webDriver, parent, locateQureyType, locateQureyCondition);
	}
	
	/**
	 * 构造函数四
	 * 1.通过唯一cssSelector表达式定位目标元素
	 * 2.如果无法直接找到元素，但是可以找到距离目标元素最近的父级元素，然后再按照唯一父级元素定位目标元素，则可使用此方式
	 * 3.如果您所在的产研团队不太愿意配合自动化测试组修改产品前端代码，则推荐此定位方式为主要方式
	 * 4.使用此方式并不意味着可以解决您所有的定位需求，必要时依然需要使用selenium原生的其他如干种定位方式
	 *
	 * @param webDriver 浏览器驱动对象
	 * @param parent 元素的唯一最近父级
	 * @param locateTagName 元素标签名
	 * @param locateAtrributeName 元素属性名称
	 * @param locateArributeValue 元素属性值，模糊查询条件
	 */
	public UniqueBase(WebDriver webDriver, WebElement parent, String locateTagName, String locateAtrributeName, String locateArributeValue) {
		uniqueRoot = QueryElement.ui_Q_K_V(webDriver, parent, locateTagName, locateAtrributeName, locateArributeValue);
	}
	
	/**
	 * 构造函数五
	 * 1.如果您所在的产研团队不太愿意配合自动化测试组修改产品前端代码，则推荐此定位方式为主要方式
	 * 2.使用此方式并不意味着可以解决您所有的定位需求，必要时依然需要使用selenium原生的其他如干种定位方式
	 *
	 * @param self 参数即本身
	 */
	public UniqueBase(WebElement self) {
		if(null != self) {
			uniqueRoot = self;
		}
	}
	
	/**
	 * 按照指定条件在目标元素下定位子元素
	 *
	 * @param webDriver 浏览器驱动对象
	 * @param locateTagName 子元素标签名
	 * @param locateAtrributeName 子元素属性名称
	 * @param locateArributeValue 子元素属性值
	 *
	 * @return 子元素的WebElement对象
	 */
	public WebElement getChildElement(WebDriver webDriver, String locateTagName, String locateAtrributeName, String locateArributeValue) {
		WebElement childWebElement;
		
		childWebElement = QueryElement.ui_Q_K_V(webDriver, uniqueRoot, locateTagName, locateAtrributeName, locateArributeValue);
		
		return childWebElement;
	}
}
