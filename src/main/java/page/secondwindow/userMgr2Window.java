package page.secondwindow;

import Base.GText;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import page.base.SecondWinBase;

public class userMgr2Window extends SecondWinBase {

    /**
     * 用户昵称的WebElement对象
     */
    private WebElement userNickNameInput = null;
    public WebElement getUserNickNameInput() { return userNickNameInput; }

    /**
     * 用户昵称的WebElement对象
     */
    private WebElement mobileNoInput = null;
    public WebElement getMobileNoInput() { return mobileNoInput; }

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
    public userMgr2Window(WebDriver webDriver, String locateTagName, String locateAtrributeName, String locateArributeValue){
        super(webDriver, locateTagName, locateAtrributeName, locateArributeValue);

        super.setConfirm(GText.getCssSelectorTxt("button", "fieldid", "backUpperLevel_btn"));
        super.setConfirm(GText.getCssSelectorTxt("button", "fieldid", "onSaveClick_btn"));

        userNickNameInput = super.getSecondWinRoot().findElement(By.cssSelector(GText.getCssSelectorTxt("input", "fieldid", "addUser_userName_input")));
        mobileNoInput = super.getSecondWinRoot().findElement(By.cssSelector(GText.getCssSelectorTxt("input", "fieldid", "multicountry-mobileInput")));
    }
}
