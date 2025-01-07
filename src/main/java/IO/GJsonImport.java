package IO;

import Base.GJsonObjectMapper;
import com.alibaba.fastjson.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 导入json
 */
public class GJsonImport {
	
	/**
	 *  参数存放区
	 *  <属性名,属性值>
	 */
	@SuppressWarnings("FieldMayBeFinal")
	private HashMap<String, String> testParams;
	
	/**
	 *  测试数据的json对象
	 *  <序号,<属性名,属性值>>
	 */
	@SuppressWarnings("FieldMayBeFinal")
	private JSONObject jsonObject;

	/**
	 *  获得测试数据的json对象
	 *  
	 *  @return jsonObject 测试数据的json对象
	 */
	public JSONObject getJsonObject() {
		return jsonObject;
	}

	/**
	 *  获得测试类群对象
	 *  
	 *  @return testClazzGroup 测试类群对象
	 */
	public HashMap<String, String> getTestParams() {
		return this.testParams;
	}
	
	/**
	 *  打印当前测试类群
	 */
	public void consoleTestParams() {
		System.out.println(this.testParams.toString());
	}
	
	/**
	 *  添加测试类
	 *  
	 *  @param propertyName 属性名
	 *  @param propertyValue 属性值
	 */
	public void addTestParam(String propertyName, String propertyValue) {
		this.testParams.put(propertyName, propertyValue);
	}
	
	/**
	 *  根据测试类名称修改属性表
	 *  
	 *  @param propertyName 属性名
	 *  @param propertyValue 属性值
	 */
	public void replaceTestParam(String propertyName, String propertyValue) {
		this.testParams.replace(propertyName, propertyValue);
	}
	
	/**
	 *  根据测试类名称获得属性表对象
	 *  
	 *  @param propertyName 属性名
	 *
	 * @return propertyValueTemp 属性值
	 */
	public String getTestParams(String propertyName) {
		String propertyValueTemp;
		propertyValueTemp = this.testParams.get(propertyName);
		return propertyValueTemp;
	}
	
	/**
	 *  测试类属性表表头
	 */
	private ArrayList<String> arrayList = null;

	/**
	 *  获得测试类属性表表头
	 *
	 *  @return 属性表表头
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
	 *  @param colNames 字段名列表
	 */
	public GJsonImport(String jsonPath, List<String> colNames) {
		
		//设置默认表头
		this.arrayList = new ArrayList<>(colNames);
		
		//设置测试类群
		this.testParams = new HashMap<>();
		
		this.jsonObject = new JSONObject();
		
		this.jsonObject.putAll(GJsonObjectMapper.readFilePathJson(jsonPath));
		
		for(int i = 0;i < this.jsonObject.size();i++) {
			String unitJsonTempString = this.jsonObject.getString(String.valueOf(i));

			for(String colName:this.arrayList) {
				this.testParams.put(colName, GJsonObjectMapper.getJsonValueByName(unitJsonTempString, colName));
			}
		}
	}
	
	/**
	 *  构造函数
	 *  
	 *  @param jsonPath json格式的文件全名，记录测试类基本信息中的固定不变的内容
	 */
	public GJsonImport(String jsonPath) {
		this.jsonObject = new JSONObject();
		
		this.jsonObject.putAll(GJsonObjectMapper.readFilePathJson(jsonPath));

		this.testParams = GJsonObjectMapper.jsonLoop(this.jsonObject);
	}
}
