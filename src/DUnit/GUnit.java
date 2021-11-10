package DUnit;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import DT.GLog;

/**
 * 运行单元信息
 */
public class GUnit {
	
    /**
     *  构造函数
     */
	public GUnit(){
		unitName = new ArrayList<String>();
		unitInfo = new HashMap<String, GAttribute>();
		unitInfoString = new HashMap<String, String>();
	}
	
	/**
	 *  运行单元名称表
	 *  
	 *  <运行单元唯一标识,运行单元属性表>
	 */
	private List<String> unitName = null;
	
	/**
	 *  运行单元信息
	 *  
	 *  <运行单元唯一标识,运行单元属性表>
	 */
	private Map<String, GAttribute> unitInfo = null;
	
	/**
	 *  运行单元信息文本
	 *  
	 *  <运行单元唯一标识,运行单元属性表文本>
	 */
	private Map<String, String> unitInfoString = null;

    /**
     *  记录完整运行单元列表到日志
     */
	public void logUnitInfo() {
		GLog.logRecord(7, this.unitInfo.toString());
	}
	
    /**
     *  获得完整运行单元列表
     *  
     *  @return List<String> 运行单元信息
     */
	public List<String> getUnitName() {
		return this.unitName;
	}
	
    /**
     *  获得完整运行单元列表
     *  
     *  @return Map<String, GAttribute> 运行单元信息
     */
	public Map<String, GAttribute> getUnitInfo() {
		return this.unitInfo;
	}
	
    /**
     *  获得完整运行单元列表文本
     *  
     *  @return Map<String, GAttribute> 运行单元信息
     */
	public Map<String, String> getUnitInfoString() {
		return this.unitInfoString;
	}
	
    /**
     *  根据运行单元唯一标识获得运行单元属性表
     *  
     *  @param unitCode 运行单元唯一标识
     *  
     *  @return GAttribute 运行单元属性表
     */
	public GAttribute getUnitAttributes(String unitCode) {
		return this.unitInfo.get(unitCode);
	}
	
    /**
     *  根据运行单元唯一标识和运行单元属性名，获得运行单元属性值
     *  
     *  @param unitCode 运行单元唯一标识
     *  
     *  @return GAttribute 运行单元属性值
     */
	public String getUnitAttributeName(String unitCode, String attributeName) {
		return this.unitInfo.get(unitCode).getAttributeValue(attributeName);
	}

    /**
     *  获得完整运行单元列表
     *  
     *  @param unitInfoTemp 克隆目标
     */
	public void setUnitInfo(Map<String, GAttribute> unitInfoTemp) {
		this.unitInfo.putAll(unitInfoTemp);
	}
	
    /**
     *  追加至运行单元列表
     *  
     *  @param unitName 运行单元属性表
     */
	public void addUnitName(String unitNameTemp) {
		this.unitName.add(unitNameTemp);
	}
	
    /**
     *  追加至运行单元列表
     *  
     *  @param unitCode 运行单元唯一标识
     *  @param unitName 运行单元属性表
     */
	public void putUnitInfo(String unitCode, GAttribute attribute) {
		this.unitInfo.put(unitCode, attribute);
		this.unitInfoString.put(unitCode, attribute.getAttribute().toString());
	}
	
    /**
     *  替换至运行单元列表
     *  
     *  @param unitCode 运行单元唯一标识
     *  @param unitName 运行单元属性表
     */
	public void replaceUnitInfo(String unitCode, GAttribute attribute) {
		this.unitInfo.replace(unitCode, attribute);
		this.unitInfoString.replace(unitCode, attribute.getAttribute().toString());
	}
}
