package main.java.Quality;

import main.java.Base.GClazz;

import java.util.HashMap;
import java.util.Map;

/*
 * 软件品质系数
 * 
 * 根据软件所属行业不同，取不同的品质系数
 * */
public class GQFactor {
	
    /**
     *  构造函数
     */
	public GQFactor(){
		GClazz.thisAToolClass();
	}
	
	/**
	 * 当前选中的行业序号
	 * 默认为0，即未指定
	 */
	private final int GQFMK = 0;
	
	/**
	 * 软件品质系数模型名称
	 * 分类方式参考[国民经济行业分类与代码（GB/4754-2011）]
	 * <序号,名称>
	 */
	@SuppressWarnings({"FieldMayBeFinal", "CanBeFinal"})
    private Map<Integer,String> gQfmN = new HashMap<>();
	
	/**
	 * 软件品质系数值
	 * <名称,系数值>
	 */
	@SuppressWarnings({"FieldMayBeFinal", "CanBeFinal"})
    private Map<Integer, String> gQfmV = new HashMap<>();
	
	/**
	 * 设置软件品质系数值
	 */
	public void set() {
		gQfmN.put(1, "制造业-核工业、兵器、航空航天、船舶、轨道交通、车辆");
		gQfmN.put(2, "制造业-其他");
		gQfmN.put(3, "信息和通信、交通运输、仓储和邮政业");  
		gQfmN.put(4, "金融-银行、保险、证券"); 
		gQfmN.put(5, "运输与存储");  
		gQfmN.put(6, "房地产"); 
		gQfmN.put(7, "建筑业");  
		gQfmN.put(8, "教育、卫生和社会工作、文化、体育和娱乐业"); 
		gQfmN.put(9, "租赁和商务服务业、居民服务、批发和零售业、修理和其他服务业、水利、环境和公共设施管理业、公共管理、社会保障和社会组织、国际组织、住宿和餐饮业、农、林、牧、渔业、采矿业");  
		gQfmN.put(0, "未指定");
		
		gQfmV.put(1, "0.5");
		gQfmV.put(2, "0.6");
		gQfmV.put(3, "0.7");  
		gQfmV.put(4, "0.8"); 
		gQfmV.put(5, "0.8");  
		gQfmV.put(6, "0.6"); 
		gQfmV.put(7, "0.6");  
		gQfmV.put(8, "0.6"); 
		gQfmV.put(9, "0.6");  
		gQfmV.put(0, "1");  
	}

	/**
	 * 根据行业序号获得指定软件品质系数描述
	 * 
	 * @param index 行业序号
	 *
	 * @return 指定软件品质系数描述
	 */
	public String getStrCombByIndex(int index) {
		String strComb = "";
		
		if(index > 0 && index < gQfmN.size()) {
			strComb = "[" + gQfmN.get(index) + "][" + gQfmV.get(index) + "]";
		}
		
		return strComb;
	}
	
	/**
	 * 根据行业序号获得指定软件品质系数值
	 * 
	 * @param index 行业序号
	 *
	 * @return 指定软件品质系数值
	 */
	public double getValueByIndex(int index) {
		double dValue = 1.0d;
		
		if(index > 0 && index < gQfmN.size()) {
			dValue = (Integer.valueOf(gQfmV.get(index))).doubleValue();
		}
		
		return dValue;
	}
	
	/**
	 * 获得当前软件品质系数描述
	 *
	 * @return 当前软件品质系数描述
	 */
	@SuppressWarnings({"unused", "UnreachableCode", "ConstantValue"})
	public String getStrComb() {
		String strComb = "";

		if(GQFMK > 0 && !gQfmN.isEmpty()) {
			strComb = "[" + gQfmN.get(GQFMK) + "][" + gQfmV.get(GQFMK) + "]";
		}

		return strComb;
	}
	
	/**
	 * 获得当前软件品质系数值
	 *
	 * @return 当前软件品质系数值
	 */
	@SuppressWarnings({"unused", "UnreachableCode", "ConstantValue"})
	public double getValue() {
		double dValue = 1.0d;
		
		if(GQFMK > 0 && !gQfmN.isEmpty()) {
			dValue = (Integer.valueOf(gQfmV.get(GQFMK))).doubleValue();
		}
		
		return dValue;
	}
}
