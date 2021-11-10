package DUnit;

import java.util.HashMap;
import java.util.Map;

import Base.GFile;
import Base.GTime;
import DT.GLog;
import Sut.GSut;
import Test.GTestResult;

/**
 * 监视器
 */
public class GTracker {
	
	/**
	 *  监视器
	 */
	public GUnit tracker = null;
	
	/**
	 *  添加运行单元
	 */
	public void addTracker(String trackerName) {
		this.tracker.addUnitName(trackerName);
	}

	/**
	 *  获取执行记录
	 */
	public GUnit getTracker() {
		return this.tracker;
	}
	
	/**
	 *  监视器名称
	 */
	public String trackerName = "";

	/**
	 *  属性管理器
	 *  
	 *  <行为名称，运行单元>
	 */
	public Map<String, GUnit> trackerAttributes = null;
	
	/**
	 *  行为计时器
	 *  
	 *  <行为名称，若干计时记录>
	 */
	public Map<String, GSpendTimeTree> trackerSpendTimeTree = null;
	
	/**
	 *  行为平均计时器
	 *  
	 *  <行为名称，平均计时>
	 */
	public Map<String, String> trackerAverageSpendTimeTree = null;
	
	/**
	 *  上次运行单元名称
	 *  
	 *  用于任务切换
	 */
	private String lastUnitName = "";
	
	/**
	 * 构造函数
	 */
	public GTracker(){
		trackerName = "未知类型";
		tracker = new GUnit();
		trackerAttributes = new HashMap<String, GUnit>();
		trackerSpendTimeTree = new HashMap<String, GSpendTimeTree>();
		trackerAverageSpendTimeTree = new HashMap<String, String>();
	}
	
	/**
	 * 构造函数
	 * 
	 * @param nameTemp 监视器名称
	 */
	public GTracker(String nameTemp){
		trackerName = nameTemp;
		tracker = new GUnit();
		trackerAttributes = new HashMap<String, GUnit>();
		trackerSpendTimeTree = new HashMap<String, GSpendTimeTree>();
		trackerAverageSpendTimeTree = new HashMap<String, String>();
	}
	
	/**
	 *  根据动作名称获得执行耗时并记录到日志文件
	 *  
	 *  @param tc_name
	 */
	public void logSpendTimesByName(String tc_name) {
		int testCaseTimes = 0;
		GLog.logRecord(7, "动作名称:" + tc_name);
		if(this.trackerSpendTimeTree.get(tc_name) != null) {
			if(this.trackerSpendTimeTree.get(tc_name).getSpendTimes() != null) {
				testCaseTimes = this.trackerSpendTimeTree.get(tc_name).getSpendTimes().size();
				if(testCaseTimes > 0) {
					GLog.logRecord(7, "执行次数:" + testCaseTimes);
					for(int i = 0;i < testCaseTimes;i++) {
						if(this.trackerSpendTimeTree.get(tc_name).getSpendTimes().get(i) != null) {
							GLog.logRecord(7, "第" + String.valueOf(i + 1) 
							+ "次执行,耗时[" + this.trackerSpendTimeTree.get(tc_name).getSpendTimes().get(i) 
							+ "]秒");
						}
					}
				}
			}
		}else {
			GLog.logRecord(7, "执行次数:0");
		}
	}
	
	/**
	 *  按照关键步骤的名称耗时记录
	 *  
	 *  只记录被执行了1次及以上的步骤。关键步骤可能被执行了多次，所以该步骤的耗时可能是一个平均值
	 */
	public void calSpendTimes() {
		int trackerSize = 0;
		trackerSize = this.tracker.getUnitName().size();
		if(trackerSize > 0) {
			for(int i = 0;i < trackerSize;i++) {
				logSpendTimesByName(this.tracker.getUnitName().get(i));
			}
		}
	}
	
	/**
	 *  纪录耗时平均值
	 *  
	 *  @param jsonPath json文件全名
	 *  @param jsonSplit json分隔符
	 *  @param tc_name 功能名称 
	 */
	public void saveSpendTimesJsonDataByName(String jsonPath, String jsonSplit, String tc_name) {
		int testCaseTimes = 0;
		String version = "[" + GTime.getCurrentTime(GTime.FORMAT_14_TEXT) + "][" + GSut.getSysName() + "+" + GSut.getSectionName1() + "+" + GSut.getSectionName2() + "+" + GSut.getSectionName3() + "][" + tc_name + "]";
		GLog.logRecord(7, "动作名称:" + tc_name);
		if(this.trackerSpendTimeTree.get(tc_name) != null) {
			if(this.trackerSpendTimeTree.get(tc_name).getSpendTimes() != null) {
				testCaseTimes = this.trackerSpendTimeTree.get(tc_name).getSpendTimes().size();
				if(testCaseTimes > 0) {
					this.trackerAverageSpendTimeTree.put(tc_name + jsonSplit + this.trackerName, this.trackerSpendTimeTree.get(tc_name).logAverageSpendTime());
					GFile.writeStringToRight(jsonPath, "[\"" + version  + "\", " + this.trackerSpendTimeTree.get(tc_name).getAverageSpendTime().toString() + "]" + jsonSplit);
				}
			}
		}
	}
	
	/**
	 *  关键步骤平均耗时记录
	 *  
	 *  @param jsonPath json文件全名
	 */
	public void saveSpendTimesJsonData(String jsonPath) {
		GFile.clearFile(jsonPath);
		
		GFile.writeStringToRight(jsonPath, "[");
		
		int trackerSize = 0;
		trackerSize = this.tracker.getUnitName().size();
		if(trackerSize > 0) {
			for(int i = 0;i < trackerSize;i++) {
				if(i != trackerSize - 1) {
					saveSpendTimesJsonDataByName(jsonPath, ",", this.tracker.getUnitName().get(i));
				}else {
					saveSpendTimesJsonDataByName(jsonPath, "", this.tracker.getUnitName().get(i));		
				}
			}
		}
		
		GFile.writeStringToRight(jsonPath, "]");
	}
	
	/**
	 *  纪录耗时平均值
	 *  
	 *  非写入方式
	 *  
	 *  @param jsonSplit json分隔符
	 *  @param tc_name 功能名称 
	 */
	public String getSpendTimesJsonDataByName(String jsonSplit, String tc_name) {
		String res= "";
		int testCaseTimes = 0;
		String version = "[\"" + GTime.getCurrentTime(GTime.FORMAT_14_TEXT) + "][" + GSut.getSysName() + "+" + GSut.getSectionName1() + "+" + GSut.getSectionName2() + "+" + GSut.getSectionName3() + "][" + tc_name + "]\"";
		GLog.logRecord(7, "动作名称:" + tc_name);
		if(this.trackerSpendTimeTree.get(tc_name) != null) {
			if(this.trackerSpendTimeTree.get(tc_name).getSpendTimes() != null) {
				testCaseTimes = this.trackerSpendTimeTree.get(tc_name).getSpendTimes().size();
				if(testCaseTimes > 0) {
					String moduleTemp = "";
					if(!jsonSplit.equals("")) {
						moduleTemp = tc_name + "||" + this.trackerName;
					}else {
						moduleTemp = tc_name;
					}
					
					this.trackerAverageSpendTimeTree.put(moduleTemp, this.trackerSpendTimeTree.get(tc_name).logAverageSpendTime());
					res = version + "," + this.trackerSpendTimeTree.get(tc_name).getAverageSpendTime().toString() + "]" + jsonSplit;
				}
			}
		}
		
		return res;
	}
	
	/**
	 *  关键步骤平均耗时记录
	 */
	public String getSpendTimesJsonData() {
		StringBuilder dynamicTable = new StringBuilder();
		dynamicTable.append("[");
		
		int trackerSize = 0;
		trackerSize = this.tracker.getUnitName().size();
		if(trackerSize > 0) {
			for(int i = 0;i < trackerSize;i++) {
				if(i != trackerSize - 1) {
					if(!this.lastUnitName.equals(this.tracker.getUnitName().get(i))) {
						dynamicTable.append(getSpendTimesJsonDataByName(",", this.tracker.getUnitName().get(i)));
						this.lastUnitName = this.tracker.getUnitName().get(i);
					}
				}else {
					dynamicTable.append(getSpendTimesJsonDataByName("", this.tracker.getUnitName().get(i)));		
				}	
			}
		}
		
		dynamicTable.append("]");
		
		GTestResult.sortTrackware.putAll(this.trackerAverageSpendTimeTree);
		
		return dynamicTable.toString();
	}
}
