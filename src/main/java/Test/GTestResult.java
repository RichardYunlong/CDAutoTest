package main.java.Test;

import main.java.Base.GFile;
import main.java.Base.GMissionMsg;
import main.java.DT.GLog;
import main.java.DUnit.GTracker;
import main.java.Sys.GStatic;

import java.util.HashMap;
import java.util.Map;

/**
 * 测试结果管理
 */
public class GTestResult {
	
	/**
	 * 
	 */
	public GTestResult(){
		GLog.logShowConsole("This is a tool class.");
	}
	
	/**
	 *  执行结果输出缓存区记录数量最大值
	 */
	@SuppressWarnings("FieldCanBeLocal")
    private int RESULT_ROW_MAX = 0;
	
	/**
	 *  执行结果输出缓存区字段数量最大值
	 */
	public final int RESULT_COL_MAX = 11;
	
	/**
	 *  执行结果输出缓存区初始化后占位字符串
	 */
	@SuppressWarnings("FieldCanBeLocal")
    private final String RESULT_CELL = "";
	
	/**
	 *  执行结果输出缓存区
	 *  第二维
	 *  0-响应码
	 *  1-响应信息
	 *  2-是否通过
	 *  3-用例类型
	 *  4-用例等级
	 *  5-
	 *  6-
	 *  7-
	 *  8-
	 *  9-
	 *  10-
	 *  11-
	 */
	private String[][] resultString = null;
	
	/**
	 *  执行结果输出缓存区初始化
	 *  当没有测试用例输入时，默认创建一个长度为1024的结果存储区
	 *
	 *  @param rowNumTemp 行缓存
	 *  @param colNumTemp 列缓存
	 */
	public void initResultString(int rowNumTemp, int colNumTemp) {
		if(rowNumTemp <= 0) {
			RESULT_ROW_MAX = 1024;
		}else {
			RESULT_ROW_MAX = rowNumTemp;
		}
		resultString = new String[RESULT_ROW_MAX][colNumTemp];
		for (int i = 0; i < rowNumTemp; i++) {
			for(int j = 0; j < colNumTemp; j++) {
				resultString[i][j] = RESULT_CELL;
			}	
		}
	}

	/**
	 *  获得执行结果输出缓存对象
	 *
	 *  @return 执行结果输出缓存对象
	 */
	public String[][] getResultString() {
		return resultString;
	}

	/**
	 *  设置执行结果输出缓存对象
	 *
	 *  @param resultStringTemp 返回报文字符串
	 */
	public void setResultString(String[][] resultStringTemp) {
		resultString = resultStringTemp.clone();
	}
	
	
	/**
	 *  全局行为记录器-登录
	 */
	public GTracker loginTrackware = null;
	
	/**
	 *  全局行为记录器-菜单
	 */
	public GTracker menuTrackware = null;
	
	/**
	 *  全局行为记录器-新建
	 */
	public GTracker addTrackware = null;
	
	/**
	 *  全局行为记录器-保存
	 */
	public GTracker saveTrackware = null;
	
	/**
	 *  全局行为记录器-提交
	 */
	public GTracker submitTrackware = null;
	
	/**
	 *  全局执行耗时排序结果
	 */
	public Map<String, String> sortTrackware = null;
	
	/**
	 *  设置“系统信息”
	 */
	public void setDefault(){
		long startTime = 0;
		GFile.writeStringToGuideBottom(GMissionMsg.getStepStart("GTrackware"));
		GFile.writeStringToGuideBottom(GStatic.gSys.getDate() + " TRACKWARE PREPARING");
		
		loginTrackware = new GTracker("登陆跳转");
		menuTrackware = new GTracker("模块打开");
		addTrackware = new GTracker("单据新建");
		saveTrackware = new GTracker("单据保存");
		submitTrackware = new GTracker("单据提交");
		
		sortTrackware = new HashMap<>();

		GStatic.gSys.logShowAndRecordGuide(startTime, "GTrackware");
		GFile.writeStringToGuideBottom(GMissionMsg.getStepEnd());
    }
	
	/**
	 *  按照业务类型计算耗时
	 */
	public void calculationSpendtimes(){
		  loginTrackware.calSpendTimes();
		  menuTrackware.calSpendTimes();
		  addTrackware.calSpendTimes();
		  saveTrackware.calSpendTimes();
		  submitTrackware.calSpendTimes();
	}
}
