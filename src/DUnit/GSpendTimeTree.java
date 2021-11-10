package DUnit;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import DT.GLog;

/**
 * 指定耗时统计
 */
public class GSpendTimeTree {
	
    /**
     *  构造函数
     */
	public GSpendTimeTree(){
		spendTimes = new ArrayList<Integer>();
	}
	
	/**
	 *  运行单元耗时统计
	 *  
	 *  [运行序号][耗时]
	 */
	private List<Integer> spendTimes = null;
	
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
     *  @param unitCode 运行单元唯一识别码
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
		Double averageValue = 0.0d;
		
		int molecule = 0;
		int denominator = spendTimes.size();
		
		if(denominator > 0) {
			for(int i = 0;i < denominator;i++) {
				molecule += spendTimes.get(i);
			}
			DecimalFormat df=new DecimalFormat("0.00");
			averageValue = Double.valueOf(df.format((double)molecule/(double)denominator));
		}else {
			GLog.logRecord(4, "Spendtimes is not more than 1.");
		}
		
		this.averageSpendTime = averageValue;
	}
	
    /**
     *  根据序号获得消耗时间
     *  
     *  @param index 运行单元唯一标识
     */
	public Integer getSpendTimeByIndex(int index) {
		return this.spendTimes.get(index);
	}
}
