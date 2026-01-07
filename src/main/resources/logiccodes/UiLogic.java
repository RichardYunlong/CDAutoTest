package logiccodes;

// 手写的业务逻辑（仅定义操作流程，无需写完整Selenium代码）
public class UiLogic {
    public void login() {
        get("https://c2.yonyoucloud.com/");
        findElement("username");
        sendKeys("13717761827");
        findElement("password");
        sendKeys("Pass!@3456");
        click("submit_btn_login");
    }
}
