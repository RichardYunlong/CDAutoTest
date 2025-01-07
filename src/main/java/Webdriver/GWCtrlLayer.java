package Webdriver;

import Base.GText;
import DT.GLog;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

/**
 * 悬浮层控件
 * 悬浮在材料名称后出现的控件
 */
public class GWCtrlLayer {

    /**
	  * 点击悬浮控件上的目标值
	  *
	  * @param webDriver 浏览器驱动对象
	  * @param text 目标值
	  * @return 是否点击
	 */
	public static boolean ByLikeText(WebDriver webDriver, String text){
	    boolean isClick = false;
		GLog.logRecordTime(0, "[widget]----[Layer]----[[");
		try {
		  GWCtrlWait.Wait2BeClickableByCssSelector(webDriver, GTestIndicators.PageShowTime, GText.getCssSelectorTxt("div", "class", "x-layer x-combo-list"));
		  List<WebElement> layers = webDriver.findElements(By.cssSelector(GText.getCssSelectorTxt("div", "class", "x-layer x-combo-list")));
		  for (WebElement layer : layers) {
            if (layer.isDisplayed()) {
              GWCtrlWait.Wait2BeClickableByCssSelector(webDriver, GTestIndicators.PageShowTime, GText.getCssSelectorTxt("div", "class", "x-combo-list-item"));
              List<WebElement> items = layer.findElements(By.cssSelector(GText.getCssSelectorTxt("div", "class", "x-combo-list-item")));
              for (WebElement item : items) {
                if (item.getText().equals(text)) {
                  //此处首次点击时，因为文本框是先清空后填入，存在必填项效验提示，遮挡元素，硬等待4s，等待提示语句消失
                  GWCtrlTime.Pause(webDriver, 4);
                  item.click();
                  isClick = true;
                  break;
                }
              }
              break;
            }
          }
		  
		} catch (Exception e) {
			GWCtrlException.switchTo(webDriver, e, 1, 0, "元素点击被拦截！", true);
		}
		GLog.logRecordTime(0, "]]----[Layer]----[widget]");
		return isClick;
	 }
}
