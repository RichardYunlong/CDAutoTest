package DUnit;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import DT.GLog;

/**
 * 动态数据
 */
public class GDynamicData {
	
    /**
     *  构造函数
     */
	public GDynamicData(){
		unitDynamicData = new HashMap<String, ArrayList<String>>();
	}
	
	/**
	 *  动态数据
	 *  
	 *  <数据类型,<序号, 关键字>>
	 */
	private Map<String, ArrayList<String>> unitDynamicData = null;

    /**
     *  记录动态数据到日志
     */
	public void logDynamicData() {
		GLog.logRecord(9, this.unitDynamicData.toString());
	}
	
    /**
     *  获得动态数据对象
     *  
     *  @return Map<String, ArrayList<String>> 动态数据对象
     */
	public Map<String, ArrayList<String>> getDynamicData() {
		return this.unitDynamicData;
	}
	
    /**
     *  获得动态数据中某类数据的细表
     *  
     *  @param dataType 运行单元唯一标识
     *  
     *  @return ArrayList<String> 关键字列表
     */
	public ArrayList<String> getDynamicDataList(String dataType) {
		return this.unitDynamicData.get(dataType);
	}
	
    /**
     *  获得动态数据中某类数据的细表
     *  
     *  @param dataType 运行单元唯一标识
     *  @param index 指定序号
     *  
     *  @return String 关键字
     */
	public String getDynamicDataOneOfList(String dataType, int index) {
		return this.unitDynamicData.get(dataType).get(index);
	}
	
    /**
     *  查找某关键字是否已经存在于动态数据中，如果不存在，则添加
     *  
     *  @param dataType 运行单元唯一标识
     *  @param keyword 运行单元唯一标识
     *  
     *  @return GAttribute 运行单元属性值
     */
	public void addDynamicData(String dataType, String keyword) {
		
		ArrayList<String> dynamicDataListTemp = new ArrayList<String>();
		if(this.unitDynamicData.get(dataType) != null) {
			dynamicDataListTemp.addAll(this.unitDynamicData.get(dataType));
		}

		boolean isExist = false;
		
		if(dynamicDataListTemp.size() > 0) {
			for(String data:dynamicDataListTemp) {
				if(data.equals(keyword)) {
					isExist = true;
					break;
				}
			}
		}

		if(!isExist) {
			dynamicDataListTemp.add(keyword);
			this.unitDynamicData.put(dataType, dynamicDataListTemp);
		}
	}
	
    /**
     *  删除动态数据中的某关键字，先查找，如果存在则删除，如果不存在，则不处理
     *  
     *  @param dataType 运行单元唯一标识
     *  @param keyword 运行单元唯一标识
     *  
     *  @return GAttribute 运行单元属性值
     */
	public void removeDynamicData(String dataType, String keyword) {
		
		ArrayList<String> dynamicDataListTemp = new ArrayList<String>();
		if(this.unitDynamicData.get(dataType) != null) {
			dynamicDataListTemp.addAll(this.unitDynamicData.get(dataType));
		}
		
		int indexExist = -1;
		
		for(int i = 0;i < dynamicDataListTemp.size();i++) {
			if(dynamicDataListTemp.get(i).equals(keyword)) {
				indexExist = i;
				break;
			}
		}
		
		if(indexExist >= 0) {
			dynamicDataListTemp.remove(indexExist);
			this.unitDynamicData.replace(dataType, dynamicDataListTemp);
		}
	}
}
