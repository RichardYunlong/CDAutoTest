package main.java.DUnit;

import main.java.Base.GClazz;
import main.java.DT.GLog;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * 指定耗时统计
 */
public class GSpendTimeTree {
	
    /**
     *  构造函数
     */
	public GSpendTimeTree(){
		GClazz.thisADataUnitClass();
		spendTimes = new ArrayList<>();
	}
	
	/**
	 *  运行单元耗时统计
	 *  [运行序号][耗时]
	 */
	@SuppressWarnings("FieldMayBeFinal")
    private List<Integer> spendTimes;
	
    public List<Integer> getSpendTimes() {
		return spendTimes;
	}

	public void setSpendTimes(List<Integer> spendTimestemp) {
		this.spendTimes.addAll(spendTimestemp);
	}

    /**
     *  追加至运行单元列表
     *  
     *  @param spendTimeTemp 某一次运行耗时
     */
	public void addSpendTime(Integer spendTimeTemp) {
		this.spendTimes.add(spendTimeTemp);
	}
	
	/**
	 *  运行单元平均耗时
	 */
	private Double averageSpendTime = null;

	public Double getAverageSpendTime() {
		return averageSpendTime;
	}

	public void setAverageSpendTime(Double averageSpendTime) {
		this.averageSpendTime = averageSpendTime;
	}

	/**
     *  记录运行时间到日志
     *  
     *  @param unitCode 运行单元唯一识别码
     */
	public void logSpendTimes(String unitCode) {
		GLog.logRecord(4, unitCode);
		GLog.logRecord(4, this.spendTimes.toString());
		logAverageSpendTime();
	}
	
    /**
     *  记录运行时间到日志
	 *
	 *  @return 平均消耗时间
     */
	public String logAverageSpendTime() {
		calAverageSpendTime();
		GLog.logRecord(4, "averageSpendTime:" + this.averageSpendTime.toString());
		return this.averageSpendTime.toString();
	}
	
    /**
     *  计算平均执行时间
     */
	private void calAverageSpendTime() {
		double averageValue = 0.0d;
		
		int molecule = 0;
		int denominator = spendTimes.size();
		
		if(denominator > 0) {
            for (Integer spendTime : spendTimes) {
                molecule += spendTime;
            }
			DecimalFormat df=new DecimalFormat("0.00");
			averageValue = Double.parseDouble(df.format((double) molecule / (double) denominator));
		}else {
			GLog.logRecord(4, "Spendtimes is not more than 1.");
		}
		
		this.averageSpendTime = averageValue;
	}
	
    /**
     *  根据序号获得消耗时间
     *  
     *  @param index 运行单元唯一标识
	 *
	 *  @return 消耗时间
     */
	public Integer getSpendTimeByIndex(int index) {
		return this.spendTimes.get(index);
	}
}
