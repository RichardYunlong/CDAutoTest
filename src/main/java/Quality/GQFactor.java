package Quality;

import Base.GClazz;

import java.util.HashMap;
import java.util.Map;

/**
 * 软件品质系数
 *
 * <p>根据被测软件所属行业的差异，取不同的品质系数。系数越小代表该行业对软件品质的要求越高，
 * 最终会作为权重参与质量评分计算。</p>
 *
 * <p>行业分类方式参考《国民经济行业分类与代码（GB/T 4754-2011）》。</p>
 *
 * @see Quality.GQualityAlgo
 */
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
	 *
	 * <p>以 {@code Map<行业序号, 行业名称>} 的形式存放行业分类描述。</p>
	 */
	@SuppressWarnings({"FieldMayBeFinal", "CanBeFinal"})
    private Map<Integer,String> gQfmN = new HashMap<>();
	
	/**
	 * 软件品质系数值
	 *
	 * <p>以 {@code Map<行业序号, 系数值>} 的形式存放各行业的品质系数，与 {@link #gQfmN} 的键一一对应。</p>
	 */
	@SuppressWarnings({"FieldMayBeFinal", "CanBeFinal"})
    private Map<Integer, String> gQfmV = new HashMap<>();
	
	/**
	 * 装载软件品质系数模型
	 *
	 * <p>将内置的行业名称与系数值写入 {@link #gQfmN} 和 {@link #gQfmV}。使用前必须先调用本方法，
	 * 否则两个映射表为空，取值方法会返回默认值。</p>
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
