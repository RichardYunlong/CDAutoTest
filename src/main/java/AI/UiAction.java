package AI;

// 基础UI操作枚举（Call Graph核心节点）
public enum UiAction {
    FIND_ELEMENT("findElement"),  // 定位元素
    CLICK("click"),               // 点击
    SEND_KEYS("sendKeys"),        // 输入文本
    GET_URL("get");               // 打开网址
    private final String methodName;
    UiAction(String methodName) { this.methodName = methodName; }
    public String getMethodName() { return methodName; }
}
