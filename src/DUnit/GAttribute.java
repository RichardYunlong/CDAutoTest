package DUnit;

import java.util.HashMap;
import java.util.Map;

import DT.GLog;

/**
 * 属性表
 */
public class GAttribute {
	
    /**
     *  构造函数
     */
	public GAttribute(){
		attribute = new HashMap<String, String>();
	}
	
	/**
	 *  属性表
	 *  
	 *  <属性名,属性值>
	 */
	private Map<String, String> attribute = null;

    /**
     *  记录属性表到日志
     */
	public void logAttribute() {
		GLog.logRecord(4, this.attribute.toString());
	}
	
    /**
     *  获得完整属性表
     */
	public Map<String, String> getAttribute() {
		return this.attribute;
	}
	
    /**
     *  根据属性名获得属性值
     *  
     *  @param attributeName 运行单元唯一标识
     */
	public String getAttributeValue(String attributeName) {
		return this.attribute.get(attributeName);
	}

    /**
     *  获得完整运行单元列表
     *  
     *  @param attributeTemp 克隆目标
     */
	public void setAttribute(Map<String, String> attributeTemp) {
		this.attribute.putAll(attributeTemp);
	}
	
    /**
     *  追加至运行单元列表
     *  
     *  @param attributeName 运行单元唯一标识
     *  @param attributeValue 运行单元名称描述
     */
	public void putAttribute(String attributeName, String attributeValue) {
		this.attribute.put(attributeName, attributeValue);
	}
}
