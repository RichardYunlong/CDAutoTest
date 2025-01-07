package main.java.page.base;

import main.java.Base.GText;
import main.java.DT.GLog;
import main.java.Webdriver.GWCtrlException;
import main.java.Webdriver.GWCtrlMsg;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 *  符合某特征的多个元素定位
 *  静态定位
 *  1.本类的所有静态方法都自带了智能等待；2."_Q"为定位函数；3."_V"为使对象可见；4."_K"为模糊查询；5."_T"为按文本定位，非必要情况不推荐使用
 *  
 *  @author hew-d
 */
public class QueryElements {
	
	/**
     * 
     * cssSelector方式定位一系列元素
     * 当count大于页面实际存在的目标条件元素时，返回页面符合目标条件的全部element元素
     *
     * @param webDriver 浏览器驱动对象
     * @param parent 能找到预期元素集合的父节点（可以为祖父节点等）
	 * @param tagName 能够确定元素唯一对象的属性所在标签类型
	 * @param tagAttributeName 能够确定元素唯一对象的属性名称
	 * @param tagAttributeValue 能够确定元素唯一对象的属性值
     * 
     * @return WebElement 符合制定条件的元素的WebElement对象列表
     */
    public static List<WebElement> findElements(WebDriver webDriver,
                                                WebElement parent,
                                                String tagName,
                                                String tagAttributeName,
                                                String tagAttributeValue){
        GLog.logRecordTime(0, "[query]----[WebElements]----[[");
        //定义存放元素的list
        List<WebElement> webElements = null;
        try {
            GLog.logRecordTime(0, "----<WebElements[" + parent + "]>" + GWCtrlMsg.ui_QUERY[0]);
            //根据父节点查询指定的元素
            try {
                //将第一个目标元素放入list中
            	webElements = parent.findElements(By.cssSelector(GText.getCssSelectorTxt(tagName, tagAttributeName, tagAttributeValue)));
            } catch (Exception e) {
                System.out.println("未找符合条件的目标元素");
            }
            
            GLog.logRecordTime(0, "----<WebElements[" + Objects.requireNonNull(webElements) + "]>" + GWCtrlMsg.ui_QUERY[1]);
        }catch (Exception e) {
            GWCtrlException.switchTo(webDriver, e, 1, 0, "----<exception[webElements" + GWCtrlMsg.ui_QUERY[2] + "]", true);
        }
        
        GLog.logRecordTime(0, "]]----[WebElements]----[query]");
        return webElements;
    }
	
    /**
     * 根据对应的节点查询符合预期的元素列表
     * 当count大于页面实际存在的目标条件元素时，返回页面符合目标条件的全部element元素
     *
     * @param webDriver 浏览器驱动对象
     * @param parent 能找到预期元素集合的父节点（可以为祖父节点等）
     * @param count 查询目标元素的指定条数
     * @param elementTpye 查询条件目标元素的条数
     * @param xpath 父节点或祖父节点距离目标条件元素的xpath路径，可为空
     *
     * @return WebElement 符合制定条件的元素的WebElement对象列表
     */
    public static List<WebElement> ui_FIND_ElEMENTS(WebDriver webDriver,
                                                    WebElement parent,
                                                    int count,
                                                    String elementTpye,
                                                    String xpath){
        
        GLog.logRecordTime(0, "[query]----[WebElements]----[[");
        //定义存放元素的list
        List<WebElement> webElements = new ArrayList<>();
        try {
            GLog.logRecordTime(0, "----<WebElements[" + parent + "]>" + GWCtrlMsg.ui_QUERY[0]);
            //根据父节点查询指定的元素
            try {
                //将第一个目标元素放入list中
                WebElement element = parent.findElement(By.xpath("."+xpath+"/"+elementTpye+ "/table"));
                webElements.add(element);
                //从第二个开始查
                for (int i = 2; i < count+1; i++) {
                    WebElement webElement = parent.findElement(By.xpath("."+xpath+"/"+elementTpye+"["+i+"]"+ "/table"));
                    webElements.add(webElement);
                }
                
            } catch (Exception e) {
                System.out.println("查询当前条件元素不存在，或count超出页面当前条件元素最大值，返回全部符合条件的element元素！");
            }
            
            GLog.logRecordTime(0, "----<WebElements[" + webElements + "]>" + GWCtrlMsg.ui_QUERY[1]);
        }catch (Exception e) {
            GWCtrlException.switchTo(webDriver, e, 1, 0, "----<exception[webElements" + GWCtrlMsg.ui_QUERY[2] + "]", true);
        }
        
        GLog.logRecordTime(0, "]]----[WebElements]----[query]");
        return webElements;
    }
    
    /**
     * 按照相对xpath的值，查询并返回指定长度的ListWebElement对象
     * 当count大于页面实际存在的目标条件元素时，返回页面符合目标条件的全部element元素
     *
     * @param webDriver 浏览器驱动对象
     * @param parent 查询范围的WebElement对象
     * @param begin 查询目标元素的开始位置
     * @param end 查询目标元素的结束位置
     * @param relativeRXpath 查询范围的xpath全名到查询目标上层元素之间的相对xpath，如果不为空，则需要包含左侧分隔符"/"
     * @param rowRootTagName 查询目标上层元素名称，与查询目标同等数量标签，一般“套在”查询目标上层某位置
     * @param relativeTXpath 查询目标上层元素的xpath全名到查询目标之间的相对xpath
     * @param rowTagName 查询目标标签名
     * 
     * @return 符合制定条件的元素的WebElement对象列表
     */
    public static List<WebElement> ui_FIND_ElEMENTS(WebDriver webDriver,
                                                    WebElement parent,
                                                    int begin,
                                                    int end,
                                                    String relativeRXpath,
                                                    String rowRootTagName,
                                                    String relativeTXpath,
                                                    String rowTagName){
        GLog.logRecordTime(0, "[query]----[WebElements]----[[");
        
        //定义存放元素的list
        List<WebElement> webElements = new ArrayList<>();
        try {
            GLog.logRecordTime(0, "----<WebElements[" + parent + "]>" + GWCtrlMsg.ui_QUERY[0]);
            //根据父节点查询指定的元素
            try {
                for (int i = begin; i <= end; i++) {
                    WebElement webElement = parent.findElement(By.xpath("." + relativeRXpath + "/" + rowRootTagName + "[" + i +"]" + "/" + rowTagName));
                    webElements.add(webElement);
                }  
            } catch (Exception e) {
                System.out.println("查询当前条件元素不存在，或count超出页面当前条件元素最大值，返回全部符合条件的element元素！");
            }
            
            GLog.logRecordTime(0, "----<WebElements[" + webElements + "]>" + GWCtrlMsg.ui_QUERY[1]);
        }catch (Exception e) {
            GWCtrlException.switchTo(webDriver, e, 1, 0, "----<exception[webElements" + GWCtrlMsg.ui_QUERY[2] + "]", true);
        }
        
        GLog.logRecordTime(0, "]]----[WebElements]----[query]");
        return webElements;
    }
    
    /**
     *  根据对应的节点查询符合预期的指定范围的元素集合
     *  当"Begin"与 "End"的查询区间大于页面实际存在的目标条件元素时，返回页面符合目标条件的全部element元素
     *
     * @param webDriver 浏览器驱动对象
     * @param parent 能找到预期元素集合的父节点（可以为祖父节点等）
     * @param elementTpye 元素tagName
     * @param xpath 父节点或祖父节点距离目标条件元素的xpath路径，可为空
     * @param Begin 起始条数（从第几条数据开始）
     * @param End  结束条数  （从第几条数据结束）
     * 
     * @return WebElement 符合制定条件的元素的WebElement对象列表
     */
    public static List<WebElement> ui_FIND_ElEMENTS(WebDriver webDriver,
                                                    WebElement parent,
    												String elementTpye, 
    												String xpath, 
    												int Begin, 
    												int End){
        
        GLog.logRecordTime(0, "[query]----[WebElements]----[[");
        //定义存放元素的list
        List<WebElement> webElements = new ArrayList<>();
        try {
            GLog.logRecordTime(0, "----<WebElements[" + parent + "]>" + GWCtrlMsg.ui_QUERY[0]);
            //根据父节点查询指定的元素
            try {
                //将第一个目标元素放入list中
                if (Begin==1) {
                    WebElement element = parent.findElement(By.xpath("."+xpath+"/"+elementTpye+ "/table"));
                    webElements.add(element);
                    Begin++;
                }
                //从第Begin个开始
                for (int i = Begin; i < End+1; i++) {
                    WebElement webElement = parent.findElement(By.xpath("."+xpath+"/"+elementTpye+"["+i+"]"+ "/table"));
                    webElements.add(webElement);
                }
                
            } catch (Exception e) {
                System.out.println("查询当前条件元素不存在，或count超出页面当前条件元素最大值，返回全部符合条件的element元素！");
            }
            GLog.logRecordTime(0, "----<WebElements[" + webElements + "]>" + GWCtrlMsg.ui_QUERY[1]);
        }catch (Exception e) {
            GWCtrlException.switchTo(webDriver, e, 1, 0, "----<exception[webElements" + GWCtrlMsg.ui_QUERY[2] + "]", true);
        }
        
        GLog.logRecordTime(0, "]]----[WebElements]----[query]");
        return webElements;
    }
}
