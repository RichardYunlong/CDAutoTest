package page.baseused;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import DT.GLog;
import page.base.UniqueWebElementBase;

/**
 * 二级窗体基础类
 */
public class SecondWinBase extends UniqueWebElementBase {

	/**
	 * 目标元素的WebElement对象
	 */
	@SuppressWarnings({ "CanBeFinal" })
	private WebElement secondWinRoot;

	/**
	 * 获得目标元素的WebElement对象
	 * 
	 * @return 目标元素的WebElement对象
	 */
	public WebElement getSecondWinRoot() {
		return secondWinRoot;
	}

	/**
	 * 按照cssSelector方式重新获取目标元素
	 *
	 * @param webDriver   浏览器驱动对象
	 * @param cssSelector cssSelector表达式
	 *
	 * @return 目标元素的WebElement对象
	 */
	public WebElement reFreshWebElementAndGetUniqueRoot(WebDriver webDriver, String cssSelector) {
		secondWinRoot = super.reFreshWebElementAndGetUniqueRoot(webDriver, cssSelector);
		return secondWinRoot;
	}

	/**
	 * 确认按钮的WebElement对象
	 */
	private WebElement confirm = null;

	/**
	 * 设置确认按钮的WebElement对象
	 * 
	 * @param cssSelector cssSelector表达式
	 */
	public void setConfirm(String cssSelector) {
		this.confirm = this.secondWinRoot.findElement(By.cssSelector(cssSelector));
	}

	/**
	 * 获得确认按钮的WebElement对象
	 * 
	 * @return 确认按钮的WebElement对象
	 */
	public WebElement getConfirm() {
		return confirm;
	}

	/**
	 * 取消按钮的WebElement对象
	 */
	private WebElement cancel = null;

	/**
	 * 设置取消按钮的WebElement对象
	 * 
	 * @param cssSelector cssSelector表达式
	 */
	public void setCancel(String cssSelector) {
		this.cancel = this.secondWinRoot.findElement(By.cssSelector(cssSelector));
	}

	/**
	 * 获得取消按钮的WebElement对象
	 * 
	 * @return 取消按钮的WebElement对象
	 */
	public WebElement getCancel() {
		return cancel;
	}

	/**
	 * 构造函数
	 * 1.通过唯一id的值定位目标元素
	 * 2.如果您所在的产研团队比较愿意配合自动化测试组修改产品前端代码，则推荐此定位方式为主要方式
	 * 3.使用此方式并不意味着可以解决您所有的定位需求，必要时依然需要使用selenium原生的其他如干种定位方式
	 *
	 * @param webDriver           浏览器驱动对象
	 * @param locateTagName       元素标签名
	 * @param locateAtrributeName 元素属性名称
	 * @param locateArributeValue 元素属性值
	 */
	public SecondWinBase(WebDriver webDriver, String locateTagName, String locateAtrributeName,
			String locateArributeValue) {
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

	/**
	 * 打印主要对象的hashcode
	 */
	public void showUnitsHash() {
		GLog.logRecordTime(9, "------------------------------------------------------------------");
		GLog.logRecordTime(9, "|                        MEMBER OBJECT                           |");
		GLog.logRecordTime(9, "SecondWinBase.secondWinRoot -> " + secondWinRoot.hashCode());
		GLog.logRecordTime(9, "SecondWinBase.confirm -> " + confirm.hashCode());
		GLog.logRecordTime(9, "SecondWinBase.cancel -> " + cancel.hashCode());
		GLog.logRecordTime(9, "|                              END                               |");
		GLog.logRecordTime(9, "------------------------------------------------------------------");
	}
}
