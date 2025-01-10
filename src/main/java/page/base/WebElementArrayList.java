package page.base;

import Base.GText;
import DT.GLog;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;

/**
 *  自定义元素列表
 *  用于确定某个与顺序有关的dom对象的“文本”与“WebElement”对象的对应关系，从而实现通过名称调用对应WebElement对象
 *  
 *  @author hewei
 *  @version 20220418100500
 */
public class WebElementArrayList {
	
	/**
	 *唯一WebElement对象表
	 */
	@SuppressWarnings({"FieldMayBeFinal", "CanBeFinal"})
	private UniqueBase uniqueList;
	
	/**
	 *WebElement对象表
	 */
	private List<WebElement> webElementList = null;
	
	/**
	 *得到WebElement对象表
	 *
	 * @return WebElement对象表
	 */
	public List<WebElement> getWebElementList() {
		return webElementList;
	}

	/**
	 * stringList对象表
	 */
	private List<String> stringList = null;
	
	/**
	 *得到stringList对象表
	 *
	 * @return stringList对象表
	 */
	public List<String> getStringList() {
		return stringList;
	}
	
	/**
	 * 构造函数1
	 * 根据属性定位方式定位父级，然后为列表分配内存控件，在将来的某个时候手动添加可用操作
	 * 
	 * @param root 目标自身的顶层对象
	 */
	public WebElementArrayList(WebElement root) {
		
		uniqueList = new UniqueBase(root);
		stringList = new ArrayList<>();
		webElementList = new ArrayList<>();
	}
	
	/**
	 * 构造函数2
	 * 根据属性定位方式定位父级，然后再父级范围内，根据属性定位方式定位目标
	 * 
	 * @param root 目标自身的顶层对象
	 * @param tagName 目标定位标签名
	 */
	public WebElementArrayList(WebElement root,
							String tagName) {
		
		uniqueList = new UniqueBase(root);
		reload(uniqueList.getUniqueRoot(), tagName);
	}
	
	/**
	 * 构造函数3
	 * 根据属性定位方式定位父级，然后再父级范围内，根据属性定位方式定位目标
	 * 
	 * @param root 目标自身的顶层对象
	 * @param tagName 目标定位标签名
	 * @param childTagName 目标定位子标签名
	 */
	public WebElementArrayList(WebElement root,
							   String tagName,
							   String childTagName) {
		
		uniqueList = new UniqueBase(root);
		reload(uniqueList.getUniqueRoot(), tagName, childTagName);
	}
	
	/**
	 * 构造函数4
	 * 根据属性定位方式定位父级，然后再父级范围内，根据属性定位方式定位目标
	 * 
	 * @param root 目标自身的顶层对象
	 * @param tagName 目标定位标签名
	 * @param tagProName 目标定位属性名
	 * @param tagProValue 目标定位属性值
	 */
	public WebElementArrayList(WebElement root,
							   String tagName,
							   String tagProName,
							   String tagProValue) {
		
		uniqueList = new UniqueBase(root);
		reload(uniqueList.getUniqueRoot(), tagName, tagProName, tagProValue);
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
	public WebElementArrayList(WebDriver webDriver,
							   String id,
							   String tagName,
							   String tagProName,
							   String tagProValue) {
		
		uniqueList = new UniqueBase(webDriver, id);
		reload(uniqueList.getUniqueRoot(), tagName, tagProName, tagProValue);
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
	public WebElementArrayList(WebDriver webDriver,
							   String parentTagName,
							   String parentAtrributeName,
							   String parentArributeValue,
							   String tagName,
							   String tagProName,
							   String tagProValue) {
		
		uniqueList = new UniqueBase(webDriver, parentTagName, parentAtrributeName, parentArributeValue);
		reload(uniqueList.getUniqueRoot(), tagName, tagProName, tagProValue);
	}
	
	/**
	 *重新加载列表1
	 *
	 *@param root 唯一范围元素加载列表
	 *@param tagName 目标定位标签名
	 */
	public void reload(WebElement root, String tagName) {
		webElementList = root.findElements(By.tagName(tagName));
		
		if(null != webElementList && !webElementList.isEmpty()) {
			stringList = new ArrayList<>();
			for(WebElement webElement:webElementList) {
				String title;
				title = webElement.getAttribute("title");
				if("".equals(title)) {
					title = webElement.getText();
				}
				if(null == title || title.isEmpty()) {
					stringList.add("");
				}else {
					stringList.add(title);
				}
			}
		}
	}
	
	/**
	 *重新加载列表2
	 *
	 *@param root 唯一范围元素加载列表
	 *@param tagName 目标定位标签名
	 *@param childTagName 目标定位标签名
	 */
	public void reload(WebElement root, String tagName, String childTagName) {
		webElementList = root.findElements(By.tagName(tagName));
		
		if(null != webElementList && !webElementList.isEmpty()) {
			stringList = new ArrayList<>();
			for(WebElement webElement:webElementList) {
				String title;
				title = webElement.findElement(By.tagName(childTagName)).getAttribute("title");
				if("".equals(title)) {
					title = webElement.getText();
				}
				if(null == title || title.isEmpty()) {
					stringList.add("");
				}else {
					stringList.add(title);
				}
			}
		}
	}
	
	/**
	 *重新加载列表3
	 *
	 *@param root 唯一范围元素加载列表
	 * @param tagName 目标定位标签名
	 * @param tagProName 目标定位属性名
	 * @param tagProValue 目标定位属性值
	 */
	public void reload(WebElement root, String tagName, String tagProName, String tagProValue) {
		webElementList = root.findElements(By.cssSelector(GText.getCssSelectorTxt(tagName, tagProName, tagProValue)));
		
		if(null != webElementList && !webElementList.isEmpty()) {
			stringList = new ArrayList<>();
			for(WebElement webElement:webElementList) {
				if(null == webElement.getText() || "".equals(webElement.getText())) {
					stringList.add("");
				}else {
					stringList.add(webElement.getText());
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
	public void add(String name, WebElement webElement) {
		if(null != stringList) {
			stringList.add(name);
			if(null != webElementList) {
				webElementList.add(webElement);
			}
		}
	}
	
	/**
	 *手动添加WebElementArrayList
	 *
	 *@param webElementListTemp 元素操作表
	 */
	public void addOther(WebElementArrayList webElementListTemp) {
		if(null != stringList) {
			stringList.addAll(webElementListTemp.getStringList());
			if(null != webElementList) {
				webElementList.addAll(webElementListTemp.getWebElementList());
			}
		}
	}
	
	/**
	 *是否存在
	 *
	 * @param name 控件名称文本
	 * @return 是否存在
	 */
	public boolean isContain(String name) {
		boolean isTextExist;
		
		isTextExist = stringList.contains(name);
		
		if(!isTextExist) {
			GLog.logRecordTime(0, "[" + name + "]文本不存在");
		}else {
			int index;
			index = stringList.indexOf(name);
			
			WebElement webElement;
			webElement = webElementList.get(index);
			
			if(null != webElement) {
				GLog.logRecordTime(0, "[" + name + "]存在");
			}else {
				
				GLog.logRecordTime(0, "[" + name + "]的WebElement对象不存在");
			}
		}
		
		return isTextExist;
	}
	
	/**
	 *根据文字名称点击元素
	 *
	 *@param name 控件名称文本
	 *
	 *@return 得到目标WebElement对象的保存序号
	 */
	public int getIndex(String name) {
		int index;
		index = stringList.indexOf(name);
		
		return index;
	}
	
	/**
	 *根据文字名称点击元素
	 *
	 *@param name 控件名称文本
	 *
	 *@return 得到目标的WebElement对象
	 */
	public WebElement getWebElement(String name) {
		int index;
		index = stringList.indexOf(name);
		
		WebElement webElement;
		webElement = webElementList.get(index);
		
		if(null != webElement) {
			GLog.logRecordTime(0, "hashcode[" + webElement.hashCode() + "]");
		}

		return webElement;
	}

	/**
	 *根据文字名称点击元素
	 *
	 *@param name 控件名称文本
	 */
	public void click(String name) {
		int index;
		index = stringList.indexOf(name);
		
		WebElement webElement;
		webElement = webElementList.get(index);
		
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
		
		if(null != webElementList && null != stringList && stringList.size() == webElementList.size()) {
			res = webElementList.size();
		}
		
		return res;
	}
}
