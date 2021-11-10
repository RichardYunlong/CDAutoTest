package Tclg;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;

import Base.GJsonObjectMapper;

/**
 *  测试类群
 */
public class GTestClazzGroup {
	
	/**
	 *  测试类群
	 *  
	 *  <测试类名称,<属性名,属性值>>
	 */
	private Map<String, HashMap<String, String>> testClazzGroup = null;

	/**
	 *  获得测试类群对象
	 *  
	 *  @return testClazzGroup 测试类群对象
	 */
	public Map<String, HashMap<String, String>> getTestClazzGroup() {
		return this.testClazzGroup;
	}
	
	/**
	 *  打印当前测试类群
	 */
	public void consoleTestClazzGroup() {
		System.out.println(this.testClazzGroup.toString());
	}
	
	/**
	 *  添加测试类
	 *  
	 *  @param testClazz 测试类
	 */
	public void addTestClazz(String testClazzName, HashMap<String, String> testClazzProperties) {
		this.testClazzGroup.put(testClazzName, testClazzProperties);
	}
	
	/**
	 *  根据测试类名称获得属性表对象
	 *  
	 *  @param testClazzName 测试类名称
	 *  
	 *  @return 测试类群对象属性表
	 */
	public HashMap<String, String> getTestClazz(String testClazzName) {
		HashMap<String, String> testClazzTemp = new HashMap<String, String>();
		testClazzTemp.putAll(this.testClazzGroup.get(testClazzName));
		return testClazzTemp;
	}
	
	/**
	 *  根据测试类名称修改属性表
	 *  
	 *  @param testClazzName 测试类名称
	 *  
	 *  @return 测试类群对象属性表
	 */
	public void replaceTestClazz(String testClazzName, HashMap<String, String> testClazzProperties) {
		this.testClazzGroup.replace(testClazzName, testClazzProperties);
	}
	
	/**
	 *  根据测试类名称和属性名称获得该属性的值
	 *  
	 *  @param testClazzName 测试类名称
	 *  @param propertyName 测试类目标属性的名称
	 *  
	 *  @return 测试类群对象属性表中的某个属性值
	 */
	public String getTestClazzPropertyValue(String testClazzName, String propertyName) {
		String testClazzPropertyValueTemp = this.testClazzGroup.get(testClazzName).get(propertyName);
		
		return testClazzPropertyValueTemp;
	}
	
	/**
	 *  根据测试类名称、属性名和属性值替换该属性的值
	 *  
	 *  @param testClazzName 测试类名称
	 *  @param propertyName 测试类目标属性的名称
	 *  
	 *  @return 测试类群对象属性表中的某个属性值
	 */
	public void replaceTestClazzPropertyValue(String testClazzName, String propertyName, String propertyValue) {
		HashMap<String, String> testClazzPropertyTemp = new HashMap<String, String>();
		
		testClazzPropertyTemp.putAll(this.testClazzGroup.get(testClazzName));
		
		testClazzPropertyTemp.replace(propertyValue, propertyValue);
		
		this.testClazzGroup.replace(propertyName, testClazzPropertyTemp);
	}
	
	/**
	 *  测试类属性表表头
	 */
	private ArrayList<String> arrayList = null;

	/**
	 *  获得测试类属性表表头
	 */
	public ArrayList<String> getArrayList() {
		return this.arrayList;
	}

	/**
	 *  增加测试类属性表表头
	 *  
	 *  @param arrayListColNames 自定义属性名称数组
	 */
	public void addArrayList(String arrayListColName) {
		this.arrayList.add(arrayListColName);
	}
	
	/**
	 *  设置测试类属性表表头
	 *  
	 *  @param arrayListColNames 自定义属性名称数组
	 */
	public void setArrayList(ArrayList<String> arrayListColNames) {
		this.arrayList.addAll(arrayListColNames);
	}

	/**
	 *  构造函数
	 *  
	 *  @param jsonPath json格式的文件全名，记录测试类基本信息中的固定不变的内容
	 */
	public GTestClazzGroup(String jsonPath) {
		
		//设置默认表头
		this.arrayList = new ArrayList<String>(Arrays.asList("name", "url", "menu", "classPath", "dataPath", "list", "detail"));
		
		//设置测试类群
		this.testClazzGroup = new HashMap<String, HashMap<String, String>>();
		
		JSONObject jsonObject = new JSONObject();
		
		jsonObject.putAll(GJsonObjectMapper.readFilePathJson(jsonPath));
		
		for(int i = 0;i < jsonObject.size();i++) {
			String unitJsonTempString = jsonObject.getString(String.valueOf(i));
			
			HashMap<String, String> mapTemp = new HashMap<String, String>();
			
			for(String colName:this.arrayList) {
				mapTemp.put(colName, GJsonObjectMapper.getJsonValueByName(unitJsonTempString, colName));
			}
			
			this.testClazzGroup.put(GJsonObjectMapper.getJsonValueByName(unitJsonTempString, "name"), mapTemp);
		}
	}
}
