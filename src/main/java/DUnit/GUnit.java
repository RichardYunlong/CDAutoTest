package DUnit;

import Base.GClazz;
import DT.GLog;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 运行单元信息
 */
public class GUnit {
	
    /**
     *  构造函数
     */
	public GUnit(){
		GClazz.thisADataUnitClass();
		unitName = new ArrayList<>();
		unitInfo = new HashMap<>();
		unitInfoString = new HashMap<>();
	}
	
	/**
	 *  运行单元名称表
	 *
	 *  <p>按执行顺序存放各运行单元的唯一标识。</p>
	 */
	 @SuppressWarnings({"FieldMayBeFinal", "CanBeFinal"})
	 private List<String> unitName;
	
	/**
	*  运行单元信息
	*
	*  <p>以 {@code Map<运行单元唯一标识, 运行单元属性表>} 的形式存放全部运行单元的属性。</p>
	*/
	@SuppressWarnings({"FieldMayBeFinal", "CanBeFinal"})
	private Map<String, GAttribute> unitInfo;
	
	/**
	*  运行单元信息文本
	*
	*  <p>与 {@link #unitInfo} 一一对应的文本形式，以 {@code Map<运行单元唯一标识, 运行单元属性表文本>} 存放，
	*  主要用于日志输出与报告展示。</p>
	*/
	@SuppressWarnings({"FieldMayBeFinal", "CanBeFinal"})
	private Map<String, String> unitInfoString;

	/**
	 *  记录完整运行单元列表到日志
	 *
	 *  <p>输出 {@link #unitInfo} 的全部内容，便于排查运行单元属性是否正确装载。</p>
	 */
	public void logUnitInfo() {
	GLog.logRecord(7, this.unitInfo.toString());
	}
	
	/**
	 *  获得完整运行单元名称列表
	 *
	 *  @return 按执行顺序排列的运行单元唯一标识列表
	 */
	public List<String> getUnitName() { return this.unitName; }
	
	/**
	 *  获得完整运行单元信息表
	 *
	 *  @return {@code Map<运行单元唯一标识, 运行单元属性表>}
	 */
	public Map<String, GAttribute> getUnitInfo() { return this.unitInfo; }
	
	/**
	 *  获得完整运行单元信息表的文本形式
	 *
	 *  @return {@code Map<运行单元唯一标识, 运行单元属性表文本>}
	 */
	public Map<String, String> getUnitInfoString() { return this.unitInfoString; }
	
	/**
	 *  根据运行单元唯一标识获得运行单元属性表
	 *  
	 *  @param unitCode 运行单元唯一标识
	 *  
	 *  @return 运行单元属性表；{@code unitCode} 不存在时返回 {@code null}
	 */
	public GAttribute getUnitAttributes(String unitCode) {
	return this.unitInfo.get(unitCode);
	}
	
	/**
	 *  根据运行单元唯一标识和属性名，获得对应的属性值
	 *  
	 *  @param unitCode 运行单元唯一标识
	*  @param attributeName 属性名称
	 *  
	 *  @return 属性值字符串
	 *  @throws NullPointerException 当 {@code unitCode} 对应的运行单元不存在时抛出
	 */
	public String getUnitAttributeName(String unitCode, String attributeName) {
	return this.unitInfo.get(unitCode).getAttributeValue(attributeName);
	}

	/**
	 *  批量合并运行单元信息表
	 *
	 *  <p>采用 {@code putAll} 语义：同名的运行单元会被 {@code unitInfoTemp} 中的值覆盖。</p>
	 *  
	 *  @param unitInfoTemp 待合并的运行单元信息表
	 */
	public void setUnitInfo(Map<String, GAttribute> unitInfoTemp) {
	this.unitInfo.putAll(unitInfoTemp);
	}
	
	/**
	 *  追加一个运行单元名称
	 *  
	 *  @param unitNameTemp 运行单元唯一标识
	 */
	public void addUnitName(String unitNameTemp) {
	this.unitName.add(unitNameTemp);
	}
	
	/**
	 *  追加一个运行单元及其属性表
	 *
	 *  <p>同步维护 {@link #unitInfo} 与 {@link #unitInfoString}，保证两者内容一致。</p>
	 *  
	 *  @param unitCode 运行单元唯一标识
	 *  @param attribute 运行单元属性表
	 */
	public void putUnitInfo(String unitCode, GAttribute attribute) {
	this.unitInfo.put(unitCode, attribute);
	this.unitInfoString.put(unitCode, attribute.getAttribute().toString());
	}
	
	/**
	 *  替换指定运行单元的属性表
	 *
	 *  <p>与 {@link #putUnitInfo(String, GAttribute)} 的区别是：仅当 {@code unitCode} 已存在时才替换，
	 *  不会新增条目。</p>
	 *  
	 *  @param unitCode 运行单元唯一标识
	 *  @param attribute 运行单元属性表
	 */
	public void replaceUnitInfo(String unitCode, GAttribute attribute) {
	this.unitInfo.replace(unitCode, attribute);
	this.unitInfoString.replace(unitCode, attribute.getAttribute().toString());
	}
}
