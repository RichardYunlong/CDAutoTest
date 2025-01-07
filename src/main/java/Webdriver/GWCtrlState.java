package Webdriver;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 *  状态判断
 *  
 *  @author hew-d
 *  
 *  遵守“参数尽少”原则编写
 */
public class GWCtrlState {
	
	/**
	 *  按照元素id的值判断是否可见
	 *
	 *  @param webDriver 目标驱动
	 *  @param id 元素id值
	 *  
	 *  @return 如果目标元素的class属性值字符串中存在“-disabled”子串，则认为该元素属于不可操作
	 */
	public static boolean isOperableByid(WebDriver webDriver, String id) {
	    boolean isOperable = false;
  	    String strClassValue = webDriver.findElement(By.id(id)).getAttribute("class");
        if(null != strClassValue && !strClassValue.equals("")) {
            if(strClassValue.contains("-disabled")) {
            	isOperable = true;
            }
        }
        return isOperable;
	}
	
	/**
	 *  按照元素CssSelector表达式判断是否可见
	 *
	 *  @param webDriver 目标驱动
	 *  @param cssSelector 元素cssSelector表达式
	 *  
	 *  @return 如果目标元素的class属性值字符串中存在“-disabled”子串，则认为该元素属于不可操作 
	 */
	public static boolean isVisibleByCssSelector(WebDriver webDriver, String cssSelector) {
	    boolean isOperable = false;
  	    String strClassValue = webDriver.findElement(By.cssSelector(cssSelector)).getAttribute("class");
        if(null != strClassValue && !strClassValue.isEmpty()) {
            if(strClassValue.contains("-disabled")) {
            	isOperable = true;
            }
        }
        return isOperable;
	}
	
	/**
	 *  按照元素WebElement对象判断是否可见
	 *  
	 *  @param element 元素element对象
	 *  
	 *  @return 如果目标元素的class属性值字符串中存在“-disabled”子串，则认为该元素属于不可操作 
	 */
	public static boolean isVisibleByWebElement(WebElement element) {
	    boolean isOperable = false;
  	    String strClassValue = element.getAttribute("class");
        if(null != strClassValue && !strClassValue.isEmpty()) {
            if(strClassValue.contains("-disabled")) {
            	isOperable = true;
            }
        }
        return isOperable;
	}
}
