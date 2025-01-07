package main.java.yonbip.base;

import main.java.Base.GText;
import main.java.DT.GLog;
import main.java.Webdriver.GWCtrlException;
import main.java.Webdriver.GWCtrlMsg;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.HashMap;
import java.util.List;

/**
 *  自定义元素map
 *  
 *  @author hewei
 *  @version 20220426131400
 */
public class WebElementHashMap {
	
	/**
	 *唯一WebElement对象表
	 */
	private UniqueBase uniqueHashMap = null;
	
	/**
	 *WebElement对象表
	 */
	private HashMap<String, WebElement> webElementHashMap = null;
	
	/**
	 *获得WebElement对象表
	 *
	 * @return WebElement对象表
	 */
	public HashMap<String, WebElement> getWebElementHashMap() {
		return webElementHashMap;
	}

	/**
	 * string对象表
	 */
	private HashMap<String, String> stringHashMap = null;
	
	/**
	 * 获得string对象表
	 *
	 * @return string对象表
	 */
	public HashMap<String, String> getStringHashMap() {
		return stringHashMap;
	}

	/**
	 * 构造函数1
	 * 根据属性定位方式定位父级，然后为列表分配内存控件，在将来的某个时候手动添加可用操作
	 * 
	 * @param root 目标自身的顶层对象
	 */
	public WebElementHashMap(WebElement root) {
		
		uniqueHashMap = new UniqueBase(root);
		webElementHashMap = new HashMap<String, WebElement>();
		stringHashMap = new HashMap<String, String>();
	}
	
	/**
	 * 构造函数2
	 * 根据属性定位方式定位父级，然后再父级范围内，根据属性定位方式定位目标
	 * 
	 * @param root 目标自身的顶层对象
	 * @param tagName 目标定位标签名
	 */
	public WebElementHashMap(WebElement root,
							String tagName) {
		
		uniqueHashMap = new UniqueBase(root);
		reload(uniqueHashMap.getUniqueRoot(), tagName);
	}
	
	/**
	 * 构造函数3
	 * 根据属性定位方式定位父级，然后再父级范围内，根据属性定位方式定位目标
	 * 
	 * @param root 目标自身的顶层对象
	 * @param tagName 目标定位标签名
	 * @param childTagName 目标定位子标签名
	 */
	public WebElementHashMap(WebElement root,
							String tagName,
							String childTagName) {
		
		uniqueHashMap = new UniqueBase(root);
		reload(uniqueHashMap.getUniqueRoot(), tagName, childTagName);
	}
	
	/**
	 * 构造函数4
	 * 根据属性定位方式定位父级，然后再父级范围内，根据属性定位方式定位目标
	 *
	 * @param webDriver 浏览器驱动对象
	 * @param root 目标自身的顶层对象
	 * @param tagName 目标定位标签名
	 * @param tagProName 目标定位属性名
	 * @param tagProValue 目标定位属性值
	 */
	public WebElementHashMap(WebDriver webDriver,
							 WebElement root,
							 String tagName,
							 String tagProName,
							 String tagProValue) {
		
		uniqueHashMap = new UniqueBase(root);
		reload(webDriver, uniqueHashMap.getUniqueRoot(), tagName, tagProName, tagProValue);
	}
	
	/**
	 * 构造函数5
	 * 根据属性定位方式定位父级，然后再父级范围内，根据属性定位方式定位目标
	 *
	 * @param webDriver 浏览器驱动对象
	 * @param id 父级定位id值
	 * @param tagName 目标定位标签名
	 * @param tagProName 目标定位属性名
	 * @param tagProValue 目标定位属性值
	 */
	public WebElementHashMap(WebDriver webDriver,
							 String id,
							 String tagName,
							 String tagProName,
							 String tagProValue) {
		
		uniqueHashMap = new UniqueBase(webDriver, id);
		reload(webDriver, uniqueHashMap.getUniqueRoot(), tagName, tagProName, tagProValue);
	}

	/**
	 * 构造函数6
	 * 根据属性定位方式定位父级，然后再父级范围内，根据属性定位方式定位目标
	 *
	 * @param webDriver 浏览器驱动对象
	 * @param parentTagName 父级定位标签名
	 * @param parentAtrributeName 父级定位属性名
	 * @param parentArributeValue 父级定位属性值
	 * @param tagName 目标定位标签名
	 * @param tagProName 目标定位属性名
	 * @param tagProValue 目标定位属性值
	 */
	public WebElementHashMap(WebDriver webDriver,
							 String parentTagName,
							 String parentAtrributeName,
							 String parentArributeValue,
							 String tagName,
							 String tagProName,
							 String tagProValue) {
		
		uniqueHashMap = new UniqueBase(webDriver, parentTagName, parentAtrributeName, parentArributeValue);
		reload(webDriver, uniqueHashMap.getUniqueRoot(), tagName, tagProName, tagProValue);
	}
	
	/**
	 *重新加载列表1
	 *
	 *@param root 唯一范围元素加载列表
	 *@param tagName 目标定位标签名
	 */
	public void reload(WebElement root, String tagName) {
		;
	}
	
	/**
	 *重新加载列表2
	 *
	 *@param root 唯一范围元素加载列表
	 *@param tagName 目标定位标签名
	 *@param childTagName 目标定位标签名
	 */
	public void reload(WebElement root, String tagName, String childTagName) {
		;
	}
	
	/**
	 *重新加载列表3
	 *
	 * @param webDriver 浏览器驱动对象
	 * @param root 唯一范围元素加载列表
	 * @param tagName 目标定位标签名
	 * @param tagProName 目标定位属性名
	 * @param tagProValue 目标定位属性值
	 */
	public void reload(WebDriver webDriver,
					   WebElement root,
					   String tagName,
					   String tagProName,
					   String tagProValue) {
		List<WebElement> webElementList = root.findElements(By.cssSelector(GText.getCssSelectorTxt(tagName, tagProName, tagProValue)));
		
		if(null != webElementList && !webElementList.isEmpty()) {
			for(WebElement webElement:webElementList) {
				
				WebElement label = null;
				WebElement input = null;
				
				try {
					label = webElement.findElement(By.cssSelector(GText.getCssSelectorTxt("div", "class", "col-float label-control")));
				}catch (Exception e) {
					GWCtrlException.switchTo(webDriver, e, 1, 0, "----<exception[label" + GWCtrlMsg.ui_QUERY[2] + "]", true);
				}
				
				try {
					input = webElement.findElement(By.tagName("input"));
				}catch (Exception e) {
					GWCtrlException.switchTo(webDriver, e, 1, 0, "----<exception[input" + GWCtrlMsg.ui_QUERY[2] + "]", true);
				}
				
				if(null != label && null != input) {
					String key = "";
					key = label.getText();
					String value = "";
					value = input.getAttribute("value");
					if(!"".equals(key)) {
						webElementHashMap.put(key, input);
						stringHashMap.put(key, value);
					}
				}
			}
		}
	}
	
	
	/**
	 *手动添加“文本-元素”
	 *
	 * @param name 控件名称文本
	 * @param webElement 控件元素
	 */
	public void put(String name, WebElement webElement) {
		if(null != webElementHashMap) {
			webElementHashMap.put(name, webElement);
			if(null != stringHashMap) {
				String value = "";
				value = webElement.getAttribute("value");
				stringHashMap.put(name, value);
			}
		}
	}
	
	/**
	 *手动添加WebElementArrayList
	 *
	 *@param webElementHashMapTemp 元素操作表
	 */
	public void putOther(WebElementHashMap webElementHashMapTemp) {
		if(null != webElementHashMap) {
			webElementHashMap.putAll(webElementHashMapTemp.getWebElementHashMap());
			if(null != stringHashMap) {
				stringHashMap.putAll(webElementHashMapTemp.getStringHashMap());
			}
		}
	}
	
	/**
	 *是否存在
	 *
	 *@param name 控件名称文本
	 *
	 * @return 是否存在
	 */
	public boolean isContainKey(String name) {
		boolean isTextExist = false;
		
		isTextExist = webElementHashMap.containsKey(name);
		
		if(!isTextExist) {
			GLog.logRecordTime(0, "[" + name + "]文本不存在");
		}
		
		return isTextExist;
	}
	
	/**
	 *根据文字名称点击元素
	 *
	 *@param name 控件名称文本
	 *
	 *@return 得到目标的WebElement对象
	 */
	public WebElement getWebElement(String name) {
		WebElement webElement = null;
		webElement = webElementHashMap.get(name);
		
		if(null != webElement) {
			GLog.logRecordTime(0, "hashcode[" + webElement.hashCode() + "]");
		}

		return webElement;
	}
	
	/**
	 *根据文字名称点击元素
	 *
	 *@param name 控件名称文本
	 *
	 *@return 得到目标的当前填写内容
	 */
	public String getInputText(String name) {
		WebElement webElement = null;
		webElement = webElementHashMap.get(name);
		
		String value = webElement.getAttribute("value");
		stringHashMap.put(name, value);
		
		if(null != value) {
			GLog.logRecordTime(0, "input text[" + value + "]");
		}

		return value;
	}

	/**
	 *根据文字名称点击元素
	 *
	 *@param name 控件名称文本
	 */
	public void click(String name) {
		WebElement webElement = null;
		webElement = getWebElement(name);
		
		if(null != webElement) {
			webElement.click();
			GLog.logRecordTime(0, "[" + name + "]点击成功");
		}else {
			GLog.logRecordTime(0, "[" + name + "]不存在");
		}
	}
	
	/**
	 *获得列表长度
	 *
	 * @return 列表长度
	 */
	public int size() {
		int res = 0;
		
		if(null != webElementHashMap && null != stringHashMap && stringHashMap.size() == webElementHashMap.size()) {
			res = webElementHashMap.size();
		}
		
		return res;
	}
}
