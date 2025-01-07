package Tclg;

import Base.GJsonObjectMapper;
import com.alibaba.fastjson.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 *  测试类群
 */
public class GTestClazzGroup {
	
	/**
	 *  测试类群
	 *  <测试类名称,<属性名,属性值>>
	 */
	@SuppressWarnings("FieldMayBeFinal")
    private Map<String, HashMap<String, String>> testClazzGroup;

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
	 *  @param testClazzName 测试类名称
	 *  @param testClazzProperties 类属性内容
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
        return new HashMap<>(this.testClazzGroup.get(testClazzName));
	}
	
	/**
	 *  根据测试类名称修改属性表
	 *  
	 *  @param testClazzName 测试类名称
	 *  @param testClazzProperties 类属性
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
		return this.testClazzGroup.get(testClazzName).get(propertyName);
	}
	
	/**
	 *  根据测试类名称、属性名和属性值替换该属性的值
	 *  
	 *  @param testClazzName 测试类名称
	 *  @param propertyName 测试类目标属性的名称
	 *  @param propertyValue 测试类目标属性的值
	 */
	public void replaceTestClazzPropertyValue(String testClazzName, String propertyName, String propertyValue) {

        HashMap<String, String> testClazzPropertyTemp = new HashMap<>(this.testClazzGroup.get(testClazzName));
		
		testClazzPropertyTemp.replace(propertyValue, propertyValue);
		
		this.testClazzGroup.replace(propertyName, testClazzPropertyTemp);
	}
	
	/**
	 *  测试类属性表表头
	 */
	@SuppressWarnings("FieldMayBeFinal")
    private ArrayList<String> arrayList;

	/**
	 *  获得测试类属性表表头
	 *
	 *  @return 表头
	 */
	public ArrayList<String> getArrayList() {
		return this.arrayList;
	}

	/**
	 *  增加测试类属性表表头
	 *  
	 *  @param arrayListColName 自定义属性名称数组
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
		this.arrayList = new ArrayList<>(Arrays.asList("name", "url", "menu", "classPath", "dataPath", "list", "detail"));
		
		//设置测试类群
		this.testClazzGroup = new HashMap<>();
		
		JSONObject jsonObject = new JSONObject();
		
		jsonObject.putAll(GJsonObjectMapper.readFilePathJson(jsonPath));
		
		for(int i = 0;i < jsonObject.size();i++) {
			String unitJsonTempString = jsonObject.getString(String.valueOf(i));
			
			HashMap<String, String> mapTemp = new HashMap<>();
			
			for(String colName:this.arrayList) {
				mapTemp.put(colName, GJsonObjectMapper.getJsonValueByName(unitJsonTempString, colName));
			}
			
			this.testClazzGroup.put(GJsonObjectMapper.getJsonValueByName(unitJsonTempString, "name"), mapTemp);
		}
	}
}
