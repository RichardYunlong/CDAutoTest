package page.baseused;

import Base.GText;
import DT.GLog;
import Webdriver.GWCtrlException;
import Webdriver.GWCtrlMsg;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import page.base.UniqueWebElementBase;

import java.util.ArrayList;
import java.util.List;

/**
 *  自定义元素列表
 *  用于确定某个与顺序有关的dom对象的“文本”与“WebElement”对象的对应关系，从而实现通过名称调用对应WebElement对象
 *  
 *  @author hewei
 */
public class WebElementArrayList extends UniqueWebElementBase {
	
	/**
	 *唯一WebElement对象表
	 */
	@SuppressWarnings({"FieldMayBeFinal", "CanBeFinal"})
	private WebElement webElementArrayListRoot;
	
	/**
	 *WebElement对象表
	 */
	private List<WebElement> webElementArrayList = null;
	
	/**
	 *得到WebElement对象表
	 *
	 * @return WebElement对象表
	 */
	public List<WebElement> getWebElementArrayList() {
		return webElementArrayList;
	}

	/**
	 * stringList对象表
	 */
	private List<String> stringArrayList = null;
	
	/**
	 *得到stringList对象表
	 *
	 * @return stringList对象表
	 */
	public List<String> getStringArrayList() {
		return stringArrayList;
	}
	
	/**
	 * 构造函数1
	 * 根据属性定位方式定位父级，然后为列表分配内存控件，在将来的某个时候手动添加可用操作
	 * 
	 * @param root 目标自身的顶层对象
	 */
	public WebElementArrayList(WebElement root) {
		super(root);

		webElementArrayListRoot = super.getUniqueRoot();
		stringArrayList = new ArrayList<>();
		webElementArrayList = new ArrayList<>();
	}
	
	/**
	 * 构造函数2
	 * 根据属性定位方式定位父级，然后再父级范围内，根据属性定位方式定位目标
	 *
	 * @param webDriver 浏览器驱动对象
	 * @param root 目标自身的顶层对象
	 * @param tagName 目标定位标签名
	 */
	public WebElementArrayList(WebDriver webDriver,
							   WebElement root,
							   String tagName) {
		super(root);

		webElementArrayListRoot = super.getUniqueRoot();
		reload(webDriver, webElementArrayListRoot, tagName);
	}
	
	/**
	 * 构造函数3
	 * 根据属性定位方式定位父级，然后再父级范围内，根据属性定位方式定位目标
	 *
	 * @param webDriver 浏览器驱动对象
	 * @param root 目标自身的顶层对象
	 * @param tagName 目标定位标签名
	 * @param childTagName 目标定位子标签名
	 */
	public WebElementArrayList(WebDriver webDriver,
							   WebElement root,
							   String tagName,
							   String childTagName) {
		super(root);

		webElementArrayListRoot = super.getUniqueRoot();
		reload(webDriver, webElementArrayListRoot, tagName, childTagName);
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

		super(root);

		webElementArrayListRoot = super.getUniqueRoot();
		reload(webElementArrayListRoot, tagName, tagProName, tagProValue);
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
		super(webDriver, id);

		webElementArrayListRoot = super.getUniqueRoot();
		reload(webElementArrayListRoot, tagName, tagProName, tagProValue);
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
		super(webDriver, parentTagName, parentAtrributeName, parentArributeValue);

		webElementArrayListRoot = super.getUniqueRoot();
		reload(webElementArrayListRoot, tagName, tagProName, tagProValue);
	}
	
	/**
	 *重新加载列表1
	 *
	 * @param webDriver 浏览器驱动对象
	 * @param root 唯一范围元素加载列表
	 * @param tagName 目标定位标签名
	 */
	public void reload(WebDriver webDriver,WebElement root, String tagName) {
		webElementArrayList = root.findElements(By.tagName(tagName));
		
		if(null != webElementArrayList && !webElementArrayList.isEmpty()) {
			stringArrayList = new ArrayList<>();
			for(WebElement webElement: webElementArrayList) {
				String title = "";
				try{
					title = webElement.getAttribute("title");
				}catch (Exception e) {
					GWCtrlException.switchTo(webDriver, e, 1, 0, "----<exception[title" + GWCtrlMsg.ui_QUERY[2] + "]", true);
				}

				if("".equals(title)) {
					title = webElement.getText();
				}
				if(null == title || title.isEmpty()) {
					stringArrayList.add("");
				}else {
					stringArrayList.add(title);
				}
			}
		}
	}
	
	/**
	 *重新加载列表2
	 *
	 * @param webDriver 浏览器驱动对象
	 * @param root 唯一范围元素加载列表
	 * @param tagName 目标定位标签名
	 * @param childTagName 目标定位标签名
	 */
	public void reload(WebDriver webDriver,WebElement root, String tagName, String childTagName) {
		webElementArrayList = root.findElements(By.tagName(tagName));
		
		if(null != webElementArrayList && !webElementArrayList.isEmpty()) {
			stringArrayList = new ArrayList<>();
			for(WebElement webElement: webElementArrayList) {
				String title = "";
				try{
					title = webElement.findElement(By.tagName(childTagName)).getAttribute("title");
				}catch (Exception e) {
					GWCtrlException.switchTo(webDriver, e, 1, 0, "----<exception[title" + GWCtrlMsg.ui_QUERY[2] + "]", true);
				}
				if("".equals(title)) {
					title = webElement.getText();
				}
				if(null == title || title.isEmpty()) {
					stringArrayList.add("");
				}else {
					stringArrayList.add(title);
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
		webElementArrayList = root.findElements(By.cssSelector(GText.getCssSelectorTxt(tagName, tagProName, tagProValue)));
		
		if(null != webElementArrayList && !webElementArrayList.isEmpty()) {
			stringArrayList = new ArrayList<>();
			for(WebElement webElement: webElementArrayList) {
				if(null == webElement.getText() || "".equals(webElement.getText())) {
					stringArrayList.add("");
				}else {
					stringArrayList.add(webElement.getText());
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
		if(null != stringArrayList) {
			stringArrayList.add(name);
			if(null != webElementArrayList) {
				webElementArrayList.add(webElement);
			}
		}
	}
	
	/**
	 *手动添加WebElementArrayList
	 *
	 *@param webElementListTemp 元素操作表
	 */
	public void addOther(WebElementArrayList webElementListTemp) {
		if(null != stringArrayList) {
			stringArrayList.addAll(webElementListTemp.getStringArrayList());
			if(null != webElementArrayList) {
				webElementArrayList.addAll(webElementListTemp.getWebElementArrayList());
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
		
		isTextExist = stringArrayList.contains(name);
		
		if(!isTextExist) {
			GLog.logRecordTime(9, "[" + name + "]文本不存在");
		}else {
			int index;
			index = stringArrayList.indexOf(name);
			
			WebElement webElement;
			webElement = webElementArrayList.get(index);
			
			if(null != webElement) {
				GLog.logRecordTime(9, "[" + name + "]存在");
			}else {
				
				GLog.logRecordTime(9, "[" + name + "]的WebElement对象不存在");
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
		index = stringArrayList.indexOf(name);
		
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
		index = stringArrayList.indexOf(name);
		
		WebElement webElement;
		webElement = webElementArrayList.get(index);
		
		if(null != webElement) {
			GLog.logRecordTime(9, "hashcode[" + webElement.hashCode() + "]");
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
		index = stringArrayList.indexOf(name);
		
		WebElement webElement;
		webElement = webElementArrayList.get(index);
		
		if(null != webElement) {
			webElement.click();
			GLog.logRecordTime(9, "[" + name + "]点击成功");
		}else {
			GLog.logRecordTime(9, "[" + name + "]不存在");
		}
	}
	
	/**
	 *获得列表长度
	 *
	 * @return 列表长度
	 */
	public int size() {
		int res = 0;
		if(null != webElementArrayList && null != stringArrayList && stringArrayList.size() == webElementArrayList.size()) {
			res = webElementArrayList.size();
		}
		return res;
	}

	/**
	 *  打印主要对象的hashcode
	 */
	public void showUnitsHash() {
		GLog.logRecordTime(9, "主要成员对象VVVV");
		GLog.logRecordTime(9, "webElementArrayListRoot -> " + webElementArrayListRoot.hashCode());
		GLog.logRecordTime(9, "webElementArrayList -> " + webElementArrayList.hashCode());
		GLog.logRecordTime(9, "stringArrayList -> " + stringArrayList.hashCode());
		GLog.logRecordTime(9, "主要成员对象^^^^");
	}
}
