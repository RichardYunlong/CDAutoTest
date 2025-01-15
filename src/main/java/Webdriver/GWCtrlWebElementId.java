package Webdriver;

import java.util.HashMap;
import java.util.Map;

/**
 * 常用元素Id
 */
public class GWCtrlWebElementId {
	
	/**
	 * 常用Id
	 */
	@SuppressWarnings("CanBeFinal")
    public static Map<String, String> CN_ID = new HashMap<>();
	
	/**
	 * 明细页签
	 * 每当对一个新的控件表格类型执行grid3读取时，需要先将其条件id值及其对应名称加到此处，督促开发者梳理一遍这种新控件的数据结构
	 */
	public static String[][] ID_DETAIL = {{"id1", "样例1"},{"id2", "样例2"},};
	
	public GWCtrlWebElementId(){
		CN_ID.put("用户管理","userIdentityDomNew");
	}
}
