package page.base;

import Base.GText;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 *  唯一元素基本定位
 *  1.根据不同产品的全段源码特性，来自定义适合该类产品的元素基本定位方式；2.本类仅适用于定位目标是一个唯一存在的元素
 */
public class SecondWinBase extends UniqueBase {

	/**
	 * 目标元素的WebElement对象
	 */
	private WebElement secondWinRoot = null;
	public WebElement getSecondWinRoot() { return secondWinRoot; }

	/**
	 * 确认按钮的WebElement对象
	 */
	@SuppressWarnings("FieldCanBeLocal")
    private WebElement confirm = null;
	public void setConfirm(String cssSelector) { this.confirm = this.secondWinRoot.findElement(By.cssSelector(cssSelector)); }
	public WebElement getConfirm() { return confirm; }

	/**
	 * 取消按钮的WebElement对象
	 */
	@SuppressWarnings("FieldCanBeLocal")
    private WebElement cancel = null;
	public void setCancel(String cssSelector) { this.cancel = this.secondWinRoot.findElement(By.cssSelector(cssSelector)); }
	public WebElement getCancel() { return cancel; }

	/**
	 * 构造函数
	 * 1.通过唯一id的值定位目标元素
	 * 2.如果您所在的产研团队比较愿意配合自动化测试组修改产品前端代码，则推荐此定位方式为主要方式
	 * 3.使用此方式并不意味着可以解决您所有的定位需求，必要时依然需要使用selenium原生的其他如干种定位方式
	 *
	 * @param webDriver 浏览器驱动对象
	 * @param locateTagName 元素标签名
	 * @param locateAtrributeName 元素属性名称
	 * @param locateArributeValue 元素属性值
	 * */
	public SecondWinBase(WebDriver webDriver, String locateTagName, String locateAtrributeName, String locateArributeValue){
		super(webDriver, locateTagName, locateAtrributeName, locateArributeValue);

		secondWinRoot = getUniqueRoot();
	}

	/**
	 * 点击确认按钮
	 */
	public void clickConfirm() {
		this.confirm.click();
	}

	/**
	 * 点击取消按钮
	 */
	public void clickCancel() {
		this.cancel.click();
	}
}
