package Webdriver;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * 元素基本操作
 */
public class GWCtrl {

    /**
     * 根据cssSelector表达式，先显式等待元素出现，等待时间为PageShowTime，获得元素对象
     *
     * @param webDriver   浏览器驱动对象
     * @param cssSelector CSS选择器表达式
     * @param mtime       等待时间（ms）
     */
    public static WebElement getWebElement(WebDriver webDriver, String cssSelector, int mtime) {
        GWCtrlWait.ViewWaitingAllByCssSelector(webDriver, mtime, cssSelector);
        WebElement element = webDriver.findElement(By.cssSelector(cssSelector));
        return element;
    }

    /**
     * 根据cssSelector表达式，先显式等待元素出现，等待时间为PageShowTime，在某个已有元素对象内获得元素对象
     *
     * @param webDriver   浏览器驱动对象
     * @param cssSelector CSS选择器表达式
     * @param mtime       等待时间（ms）
     */
    public static WebElement getWebElement(WebDriver webDriver, WebElement tempWebElement, String cssSelector,
            int mtime) {
        GWCtrlWait.ViewWaitingAllByWebElement(webDriver, mtime, tempWebElement);
        WebElement element = tempWebElement.findElement(By.cssSelector(cssSelector));
        return element;
    }

    /**
     * 根据cssSelector表达式，先显式等待元素出现，等待时间为PageShowTime，再执行点击操作
     *
     * @param webDriver   浏览器驱动对象
     * @param cssSelector CSS选择器表达式
     * @param mtime       等待时间（ms）
     */
    public static void waitingAndClick(WebDriver webDriver, String cssSelector, int mtime) {
        GWCtrlWait.ViewWaitingAllByCssSelector(webDriver, mtime, cssSelector);
        WebElement element = webDriver.findElement(By.cssSelector(cssSelector));
        element.click();
    }

    /**
     * 根据cssSelector表达式，先显式等待元素出现，等待时间为PageShowTime，再执行点击操作
     *
     * @param webDriver   浏览器驱动对象
     * @param cssSelector CSS选择器表达式
     * @param mtime       等待时间（ms）
     */
    public static void waitingAndClick(WebDriver webDriver, WebElement tempWebElement, String cssSelector, int mtime) {
        GWCtrlWait.ViewWaitingAllByWebElement(webDriver, mtime, tempWebElement);
        WebElement element = tempWebElement.findElement(By.cssSelector(cssSelector));
        element.click();
    }
}
