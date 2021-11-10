package DUnit;

import java.util.HashMap;
import java.util.Map;

import DT.GLog;

/**
 * 用例分类计数
 */
public class GTestCaseCount {
	
    /**
     *  构造函数
     */
	public GTestCaseCount(){
		unitTestCaseCount = new HashMap<String, HashMap<String, String>>();
	}
	
	/**
	 *  动态数据
	 *  
	 *  <数据类型,<序号, 关键字>>
	 */
	private Map<String, HashMap<String, String>> unitTestCaseCount = null;

    /**
     *  记录动态数据到日志
     */
	public void logTestCaseCount() {
		GLog.logRecord(9, this.unitTestCaseCount.toString());
	}
	
    /**
     *  获得动态数据对象
     *  
     *  @return Map<String, HashMap<String, Integer>> 动态数据对象
     */
	public Map<String, HashMap<String, String>> getTestCaseCount() {
		return this.unitTestCaseCount;
	}
	
    /**
     *  设置动态数据对象
     *  
     *  @return Map<String, HashMap<String, Integer>> 动态数据对象
     */
	public void setTestCaseCount(Map<String, HashMap<String, String>> testCaseCount) {
		this.unitTestCaseCount.putAll(testCaseCount);
	}
	
    /**
     *  获得动态数据中某类数据的细表
     *  
     *  @param dataType 运行单元唯一标识
     *  
     *  @return ArrayList<String> 关键字列表
     */
	public HashMap<String, String> getTestCaseCountList(String dataType) {
		return this.unitTestCaseCount.get(dataType);
	}
	
    /**
     *  获得动态数据中某类数据的细表
     *  
     *  @param dataType 运行单元唯一标识
     *  @param caseType 指定序号
     *  
     *  @return String 关键字
     */
	public String getTestCaseCountOneOfList(String dataType, String caseType) {
		return this.unitTestCaseCount.get(dataType).get(caseType);
	}
	
    /**
     *  查找某关键字是否已经存在于动态数据中，如果不存在，则添加
     *  
     *  @param dataType 运行单元唯一标识
     *  @param keyword 关键字名称
     *  @param keyvalue 关键字取值
     *  
     *  @return GAttribute 运行单元属性值
     */
	public void addTestCaseCount(String dataType, String keyword, String keyvalue) {
		
		HashMap<String, String> testCaseCountListTemp = new HashMap<String, String>();
		if(this.unitTestCaseCount.get(dataType) != null) {
			testCaseCountListTemp.putAll(this.unitTestCaseCount.get(dataType));
			
			boolean isExist = false;
			
			if(testCaseCountListTemp.size() > 0) {
				for(Map.Entry<String, String> entry:testCaseCountListTemp.entrySet()) {
					if(entry.getKey().equals(keyword)) {
						isExist = true;
						break;
					}
				}
			}

			if(!isExist) {
				testCaseCountListTemp.put(keyword, keyvalue);
			}else {
				testCaseCountListTemp.replace(keyword, keyvalue);
			}
		}else {
			testCaseCountListTemp.put(keyword, keyvalue);
		}
		
		this.unitTestCaseCount.put(dataType, testCaseCountListTemp);
	}
	
    /**
     *  删除动态数据中的某关键字，先查找，如果存在则删除，如果不存在，则不处理
     *  
     *  @param dataType 运行单元唯一标识
     *  @param keyword 运行单元唯一标识
     *  
     *  @return GAttribute 运行单元属性值
     */
	public void removeTestCaseCount(String dataType, String keyword) {
		
		HashMap<String, String> testCaseCountListTemp = new HashMap<String, String>();
		if(this.unitTestCaseCount.get(dataType) != null) {
			testCaseCountListTemp.putAll(this.unitTestCaseCount.get(dataType));
		}
		
		for(Map.Entry<String, String> entry:testCaseCountListTemp.entrySet()) {
			if(entry.getValue().equals(keyword)) {
				testCaseCountListTemp.remove(entry.getKey());
				this.unitTestCaseCount.replace(dataType, testCaseCountListTemp);
				break;
			}
		}
	}
}
