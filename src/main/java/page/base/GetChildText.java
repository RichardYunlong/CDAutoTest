package page.base;

import DT.GLog;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *  获得指定节点下符合某条件的文本
 *  
 *  @author hewei
 */
public class GetChildText {
	
	/**
	 *  通过指定属性名和属性值来获得文本
	 *  
	 * @param root 目标的父级WebElement对象
	 * @param tagName 父级对象下的元素类型 .当目标下只有一个指定类型的元素时，下两项可以为null或者"",否则必须同时有值
	 * @param attributeName 属性名
	 * @param attributeValue 属性值
	 *
	 * @return 符合条件的元素的文本
	 */
	public static String byTagAttribute(WebElement root, String tagName, String attributeName, String attributeValue) {
		String text = "";
		
		Map<String, String> key_Text = new HashMap<>();
		
		if(null != root) {
			List<WebElement> tags = root.findElements(By.tagName(tagName));
			
			if(null != tags && !tags.isEmpty()) {
				for(WebElement tag:tags) {
					String tagText;
					tagText = tag.getText();
					
					if(null != attributeName && null != attributeValue) {
						GLog.logRecordTime(9, "目标[" + root + "]下唯一[" + tagName + "]的文本为[" + tagText + "]");
						return tagText;
					}
					
					if(!"".equals(tagText)) {
						String tagAttributeValue;
						tagAttributeValue = tag.getAttribute(attributeName);
						key_Text.put(tagAttributeValue, tagText);
					}
				}
			}else {
				GLog.logRecordTime(9, "目标[" + root + "]下不存在[" + tagName + "]");
			}
		}
		
		if(!key_Text.isEmpty()) {
			text = key_Text.get(attributeValue);
			GLog.logRecordTime(9, "目标[" + root + "]下[" + tagName + "[" + attributeName + "[" + attributeValue + "]]]的文本为[" + text + "]");
		}
		
		return text;
	}
	
	/**
	 *  查询tagName，找到第一次文本不为空时的元素的文本
	 *  
	 * @param root 目标的WebElement对象
	 * @param tagName 目标下的元素类型
	 *
	 * @return 第一次文本不为空时的元素的文本
	 */
	public static String byFirst(WebElement root, String tagName) {
		String text = "";

		if(null != root) {
			List<WebElement> tags = root.findElements(By.tagName(tagName));
			
			if(null != tags && !tags.isEmpty()) {
				for(WebElement tag:tags) {
					String tagText;
					tagText = tag.getText();
					
					if(!"".equals(tagText)) {
						text = tagText;
						break;
					}
				}
			}else {
				GLog.logRecordTime(9, "目标[" + root + "]下不存在[" + tagName + "]");
			}
		}
		
		return text;
	}
}
