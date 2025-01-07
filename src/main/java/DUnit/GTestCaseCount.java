package DUnit;

import Base.GClazz;
import DT.GLog;

import java.util.HashMap;
import java.util.Map;

/**
 * 用例分类计数
 */
public class GTestCaseCount {
	
    /**
     *  构造函数
     */
	public GTestCaseCount(){
		GClazz.thisADataUnitClass();
		unitTestCaseCount = new HashMap<>();
	}
	
	/**
	 *  动态数据
	 *  <数据类型,<序号, 关键字>>
	 */
	@SuppressWarnings("FieldMayBeFinal")
    private Map<String, HashMap<String, String>> unitTestCaseCount;

    /**
     *  记录动态数据到日志
     */
	public void logTestCaseCount() {
		GLog.logRecord(9, this.unitTestCaseCount.toString());
	}
	
    /**
     *  获得动态数据对象
	 *
	 *  @return 动态数据对象
     */
	public Map<String, HashMap<String, String>> getTestCaseCount() {
		return this.unitTestCaseCount;
	}
	
    /**
     *  设置动态数据对象
	 *
	 *  @param testCaseCount 用例存储位置
     */
	public void setTestCaseCount(Map<String, HashMap<String, String>> testCaseCount) {
		this.unitTestCaseCount.putAll(testCaseCount);
	}
	
    /**
     *  获得动态数据中某类数据的细表
     *  
     *  @param dataType 运行单元唯一标识
	 *
	 *  @return 某类数据的细表
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
     */
	public void addTestCaseCount(String dataType, String keyword, String keyvalue) {
		
		HashMap<String, String> testCaseCountListTemp = new HashMap<>();
		if(this.unitTestCaseCount.get(dataType) != null) {
			testCaseCountListTemp.putAll(this.unitTestCaseCount.get(dataType));
			
			boolean isExist = false;
			
			if(!testCaseCountListTemp.isEmpty()) {
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
     *  查找某关键字是否已经存在于动态数据中，如果不存在，则添加；如果存在，则加1
     *  
     *  @param dataType 运行单元唯一标识
     *  @param keyword 关键字名称
     */
	public void plus1TestCaseCount(String dataType, String keyword) {
		
		HashMap<String, String> testCaseCountListTemp = new HashMap<>();
		if(this.unitTestCaseCount.get(dataType) != null) {
			testCaseCountListTemp.putAll(this.unitTestCaseCount.get(dataType));
			
			boolean isExist = false;
			
			if(!testCaseCountListTemp.isEmpty()) {
				for(Map.Entry<String, String> entry:testCaseCountListTemp.entrySet()) {
					if(entry.getKey().equals(keyword)) {
						isExist = true;
						break;
					}
				}
			}

			if(!isExist) {
				testCaseCountListTemp.put(keyword, Integer.valueOf(1).toString());
			}else {
				int lastNum = Integer.parseInt(testCaseCountListTemp.get(keyword));
				lastNum = lastNum + 1;
				testCaseCountListTemp.replace(keyword, Integer.toString(lastNum));
			}
		}else {
			testCaseCountListTemp.put(keyword, Integer.valueOf(1).toString());
		}
		
		this.unitTestCaseCount.put(dataType, testCaseCountListTemp);
	}
	
    /**
     *  查找某关键字是否已经存在于动态数据中，如果不存在，则添加；，如果存在，则加1
     *  
     *  @param dataType 运行单元唯一标识
     *  @param keyword 关键字名称
     *  @param n 加数
     */
	public void plusNTestCaseCount(String dataType, String keyword, int n) {
		
		HashMap<String, String> testCaseCountListTemp = new HashMap<>();
		if(this.unitTestCaseCount.get(dataType) != null) {
			testCaseCountListTemp.putAll(this.unitTestCaseCount.get(dataType));
			
			boolean isExist = false;
			
			if(!testCaseCountListTemp.isEmpty()) {
				for(Map.Entry<String, String> entry:testCaseCountListTemp.entrySet()) {
					if(entry.getKey().equals(keyword)) {
						isExist = true;
						break;
					}
				}
			}

			if(!isExist) {
				testCaseCountListTemp.put(keyword, Integer.valueOf(n).toString());
			}else {
				int lastNum = Integer.parseInt(testCaseCountListTemp.get(keyword));
				lastNum = lastNum + n;
				testCaseCountListTemp.replace(keyword, Integer.toString(lastNum));
			}
		}else {
			testCaseCountListTemp.put(keyword, Integer.valueOf(n).toString());
		}
		
		this.unitTestCaseCount.put(dataType, testCaseCountListTemp);
	}
	
    /**
     *  删除动态数据中的某关键字，先查找，如果存在则删除，如果不存在，则不处理
     *  
     *  @param dataType 运行单元唯一标识
     *  @param keyword 运行单元唯一标识
     */
	public void removeTestCaseCount(String dataType, String keyword) {
		
		HashMap<String, String> testCaseCountListTemp = new HashMap<>();
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
