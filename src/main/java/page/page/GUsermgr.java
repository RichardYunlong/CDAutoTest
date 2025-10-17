package page.page;

import Base.GText;
import DT.GLog;
import Webdriver.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import page.base.UniqueWebElementBase;
import page.page2window.AddUser;
import page.widget.MidTab;

/**
 *  用户管理
 */
public class GUsermgr extends UniqueWebElementBase {

    /**
     *  关键根对象
     */
    public WebElement usermgrRoot;

    /**
     *  用户管理页面
     */
    @SuppressWarnings({"FieldMayBeFinal", "CanBeFinal"})
    private String usermgrRootCssSelector = GParam.getCssSelectorBy3K("用户管理");

    /**
     *  当前视图名称
     *  默认为身份视图
     */
    @SuppressWarnings({"FieldCanBeLocal", "FieldMayBeFinal", "CanBeFinal"})
    private String curViewName;

    /**
     *  中部表格
     */
    private WebElement midTable = null;

    /**
     *  中部表格定位文本
     */
    @SuppressWarnings({"FieldMayBeFinal", "CanBeFinal"})
    private String midTableCssSelector = GParam.getCssSelectorBy3K("用户管理_中部表格");

    /**
     *  当前视图名称
     *  默认为身份视图
     */
    @SuppressWarnings("FieldCanBeLocal")
    private AddUser addUserWindow = null;

    /**
     *  添加按钮
     */
    private WebElement addBtn = null;

    /**
     *  添加按钮定位文本
     */
    @SuppressWarnings({"FieldMayBeFinal", "CanBeFinal"})
    private String addBtnCssSelector = GParam.getCssSelectorBy3K("用户管理_添加按钮");

    /**
     *  构造函数
     *
     * @param webDriver 浏览器驱动对象
     */
    public GUsermgr(WebDriver webDriver){
        super(webDriver, "cssSelector", GParam.getCssSelectorBy3K("用户管理"));
        usermgrRoot = getUniqueRoot();

        curViewName = "身份视图";
    }



    /**
     *  点击页签
     *
     * @param webDriver 浏览器驱动对象
     * @param tabName 页签名称
     */
    public void clickMidTab(WebDriver webDriver, String tabName){
        MidTab midTab = new MidTab(webDriver, GParam.getCssSelectorBy3K("用户管理_中部页签"));
        midTab.getTabList().click(tabName);

        GWCtrlWait.ViewWaitingAllByCssSelector(webDriver, GTestIndicators.PageShowTime, usermgrRootCssSelector);
        GWCtrlWait.ViewWaitingAllByCssSelector(webDriver, GTestIndicators.PageShowTime, midTableCssSelector);
        midTable = webDriver.findElement(By.cssSelector(midTableCssSelector));
    }

    /**
     *  身份视图-用户是否存在-按用户名查询
     *
     * @param webDriver 浏览器驱动对象
     * @param userMobileNo 用户名
     *
     * @return 身份视图下是否存在该用户
     */
    public boolean isUserExistInIdentity(WebDriver webDriver, String userMobileNo) {
        GWCtrlWait.ViewWaitingAllByCssSelector(webDriver, GTestIndicators.PageShowTime, usermgrRootCssSelector);
        WebElement accountSearchInput = usermgrRoot.findElement(By.cssSelector(GText.getCssSelectorTxt("input","fieldid","search-container_input")));
        GWCtrlInputFill.ByWebElement(webDriver, accountSearchInput, userMobileNo);
        GWCtrlWait.Waiting(1000);
        GWCtrlWait.ViewWaitingAllByCssSelector(webDriver, GTestIndicators.PageShowTime, midTableCssSelector);
        midTable = webDriver.findElement(By.cssSelector(midTableCssSelector));

        String searchResaultTatol = webDriver.findElement(By.cssSelector(GText.getCssSelectorTxt( "div","fieldid","TableComponent_antdTable-total"))).findElement(By.tagName("span")).getText();
        if(searchResaultTatol.equals("0")){
            addBtn = usermgrRoot.findElement(By.cssSelector(addBtnCssSelector));
            return false;
        }else{
            GLog.logRecordTime(9,"用户已存在，不添加");
            return true;
        }
    }

    /**
     *  用户视图-用户是否存在-按手机号查询
     *
     * @param webDriver 浏览器驱动对象
     * @param userMobileNo 手机号
     *
     * @return 用户视图下是否存在该用户
     */
    public boolean isUserExistInUser(WebDriver webDriver, String userMobileNo) {
        GWCtrlWait.ViewWaitingAllByCssSelector(webDriver, GTestIndicators.PageShowTime, usermgrRootCssSelector);
        WebElement accountSearchInput = usermgrRoot.findElement(By.cssSelector(GText.getCssSelectorTxt("input","fieldid","FuncBar_Search_Input")));
        GWCtrlInputFill.ByWebElement(webDriver, accountSearchInput, userMobileNo);
        GWCtrlWait.ViewWaitingAllByCssSelector(webDriver, GTestIndicators.PageShowTime, midTableCssSelector);
        midTable = webDriver.findElement(By.cssSelector(midTableCssSelector));

        String searchResaultTatol = webDriver.findElement(By.cssSelector(GText.getCssSelectorTxt( "div","fieldid","TableComponent_antdTable-total"))).findElement(By.tagName("span")).getText();
        if(searchResaultTatol.equals("0")){
            addBtn = usermgrRoot.findElement(By.cssSelector(addBtnCssSelector));
            return false;
        }else{
            GLog.logRecordTime(9,"用户已存在，不添加");
            return true;
        }
    }

    /**
     *  用户是否为管理员角色
     *
     * @param webDriver 浏览器驱动对象
     * @param userMobileNo 手机号

     * @return 用户是否为管理员角色
     */
    public boolean isManager(WebDriver webDriver, String userMobileNo) {
        String accountSearchInputCssSelector = GText.getCssSelectorTxt("input","fieldid","FuncBar_Search_Input");
        GWCtrlWait.ViewWaitingAllByCssSelector(webDriver,GTestIndicators.PageShowTime, accountSearchInputCssSelector);
        WebElement accountSearchInput = webDriver.findElement(By.cssSelector(accountSearchInputCssSelector));
        GWCtrlWait.ViewWaitingAllByWebElement(webDriver, GTestIndicators.PageShowTime, accountSearchInput);

        GWCtrlInputFill.ByWebElement(webDriver, accountSearchInput, userMobileNo);

        String searchIconTemp = GText.getCssSelectorTxt("div","fieldid","FuncBar_Search_Input_search");
        GWCtrlWait.ViewWaitingAllByCssSelector(webDriver, GTestIndicators.PageShowTime, searchIconTemp);
        WebElement searchIcon = webDriver.findElement(By.cssSelector(searchIconTemp));

        String searchResaultTatol = "";

        if(null != searchIcon){
            for(int i = 0;i < 20;i++){
                searchIcon.click();
                //GWCtrlWait.Waiting(1000);
                GWCtrlWait.ViewWaitingAllByCssSelector(webDriver, GTestIndicators.PageShowTime, usermgrRootCssSelector);
                GWCtrlWait.ViewWaitingAllByCssSelector(webDriver, GTestIndicators.PageShowTime, midTableCssSelector);
                GWCtrlWait.ViewWaitingAllByWebElement(webDriver, GTestIndicators.PageShowTime, midTable);

                midTable = webDriver.findElement(By.cssSelector(midTableCssSelector));
                searchResaultTatol = webDriver.findElement(By.cssSelector(GText.getCssSelectorTxt( "div","fieldid","TableComponent_antdTable-total"))).findElement(By.tagName("span")).getText();
                if(!searchResaultTatol.equals("0")){
                    break;
                }
            }
        }

        if(searchResaultTatol.equals("1")){
            String roleName = midTable.findElement(By.cssSelector(GText.getCssSelectorTxt("td","fieldid","YSUserList_otherColunms_setmanager"))).getText();
            if(!roleName.equals("管理员")){
                return false;
            }else{
                GLog.logRecordTime(9,"用户已是管理员，无需分配权限");
                return true;
            }
        }else{
            GLog.logRecordTime(9,"未找到用户，请重试查询");
            return true;
        }
    }

    /**
     *  点击新增按钮
     *
     * @param webDriver 浏览器驱动对象
     * @param userName 用户名
     * @param userMobileNo 手机号
     */
    public void addUser(WebDriver webDriver, String userName, String userMobileNo) {
        if(!isUserExistInIdentity(webDriver, userMobileNo)){
            addBtn.click();
            GWCtrlWait.ViewWaitingAllByCssSelector(webDriver, GTestIndicators.PageShowTime, GText.getCssSelectorTxt("div", "class", "ys-adduser-page ys-adduser-page-GZTACT015"));
            addUserWindow = new AddUser(webDriver, "div", "class", "ys-adduser-page ys-adduser-page-GZTACT015");
            GWCtrlInputFill.ByWebElement(webDriver, addUserWindow.getUserNickNameInput(), userName);
            GWCtrlInputFill.ByWebElement(webDriver, addUserWindow.getMobileNoInput(), userMobileNo);

            addUserWindow.clickConfirm();
            GWCtrlWait.ViewWaitingAllByCssSelector(webDriver, GTestIndicators.PageShowTime, GText.getCssSelectorTxt("button","fieldid","confirm-modal_footer_ok"));
            WebElement secondConfirm = webDriver.findElement(By.cssSelector(GText.getCssSelectorTxt("button","fieldid","confirm-modal_footer_ok")));
            secondConfirm.click();
            GWCtrlWait.ViewWaitingAllByCssSelector(webDriver, GTestIndicators.PageShowTime, usermgrRootCssSelector);
            GWCtrlWait.ViewWaitingAllByCssSelector(webDriver, GTestIndicators.PageShowTime, midTableCssSelector);

            setUniqueRoot(webDriver.findElement(By.id(GParam.getId("用户管理页面"))));
            usermgrRoot = getUniqueRoot();

            if(isUserExistInIdentity(webDriver, userMobileNo)){
                GLog.logRecordTime(9,"用户[" + userName + "," + userMobileNo +"]，添加成功");
            }
        }
    }

    /**
     *  选中并设置为管理员
     *
     * @param webDriver 浏览器驱动对象
     * @param userName 用户名
     * @param userMobileNo 手机号
     */
    public void setManager(WebDriver webDriver, String userName, String userMobileNo) {
        if(!isManager(webDriver, userMobileNo)){
            WebElement checkBox = midTable.findElement(By.cssSelector(GText.getCssSelectorTxt("input","fieldid","TableComponent_antdTable_checkbox")));
            checkBox.click();
            WebElement setOrResetManagerBtn = webDriver.findElement(By.cssSelector(GText.getCssSelectorTxt("span","fieldid","GZTACT015_AddUser_batchSetAdmin")));
            setOrResetManagerBtn.click();
            WebElement setManager = webDriver.findElement(By.cssSelector(GText.getCssSelectorTxt("span","class","active dropdown-button DropDownHoverGZTACT015 newselect")));
            setManager.click();
            WebElement confirm = webDriver.findElement(By.cssSelector(GText.getCssSelectorTxt("button","class","wui-button wui-modal-ok-button wui-button-primary")));
            confirm.click();
            GWCtrlWait.ViewWaitingAllByCssSelector(webDriver, GTestIndicators.PageShowTime, midTableCssSelector);
            if(isUserExistInUser(webDriver, userMobileNo)){
                GLog.logRecordTime(9,"用户[" + userName + "," + userMobileNo +"]，设置管理员成功");
            }
        }
    }

    /**
     *  增加并设置为管理员
     *
     * @param webDriver 浏览器驱动对象
     * @param userName 用户名
     * @param userMobileNo 手机号
     */
    public void addAndSetManager(WebDriver webDriver, String userName, String userMobileNo) {
        this.clickMidTab(webDriver,"身份视图");
        this.addUser(webDriver, userName, userMobileNo);
        this.clickMidTab(webDriver,"用户视图");
        this.setManager(webDriver,userName, userMobileNo);
    }
}
