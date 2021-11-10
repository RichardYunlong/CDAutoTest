package Test;

import java.util.HashMap;
import java.util.Map;

import Base.GMissionMsg;
import DT.GLog;
import DUnit.GTracker;
import Sys.GSys;

/**
 * 测试结果
 */
public class GTestResult {
	
	/**
	 * 
	 */
	private GTestResult(){
		GLog.logShowConsole("This is a tool class.");
	}
	
	/**
	 *  执行结果输出缓存区记录数量最大值
	 */
	private static int RESULT_ROW_MAX = 0;
	
	/**
	 *  执行结果输出缓存区字段数量最大值
	 */
	public static final int RESULT_COL_MAX = 11;
	
	/**
	 *  执行结果输出缓存区初始化后占位字符串
	 */
	private static final String RESULT_CELL = "";
	
	/**
	 *  执行结果输出缓存区
	 *  
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
	private static String[][] resultString = null;
	
	/**
	 *  执行结果输出缓存区初始化
	 *  
	 *  当没有测试用例输入时，默认创建一个1024长度的结果存储区
	 */
	public static void initResultString(int rowNumTemp, int colNumTemp) {
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
	 */
	public static String[][] getResultString() {
		return resultString;
	}

	/**
	 *  设置执行结果输出缓存对象
	 */
	public static void setResultString(String[][] resultStringTemp) {
		resultString = resultStringTemp.clone();
	}
	
	
	/**
	 *  全局行为记录器-登录
	 */
	public static GTracker loginTrackware = null;
	
	/**
	 *  全局行为记录器-菜单
	 */
	public static GTracker menuTrackware = null;
	
	/**
	 *  全局行为记录器-新建
	 */
	public static GTracker addTrackware = null;
	
	/**
	 *  全局行为记录器-保存
	 */
	public static GTracker saveTrackware = null;
	
	/**
	 *  全局行为记录器-提交
	 */
	public static GTracker submitTrackware = null;
	
	/**
	 *  全局执行耗时排序结果
	 */
	public static Map<String, String> sortTrackware = null;
	
	/**
	 *  设置“系统信息”
	 */
	public static void setDefault(){
		long startTime = 0;
		GSys.logSys(GMissionMsg.getStepStart("GTrackware"));
		GSys.logSys(GSys.getDate() + " TRACKWARE PREPARING");
		
		loginTrackware = new GTracker("登陆跳转");
		menuTrackware = new GTracker("模块打开");
		addTrackware = new GTracker("单据新建");
		saveTrackware = new GTracker("单据保存");
		submitTrackware = new GTracker("单据提交");
		
		sortTrackware = new HashMap<String, String>();
		
		GSys.logShowAndRecord(startTime, "GTrackware");
		GSys.logSys(GMissionMsg.getStepEnd());
    }
	
	/**
	 *  按照业务类型计算耗时
	 */
	public static void calculationSpendtimes(){
		  loginTrackware.calSpendTimes();
		  menuTrackware.calSpendTimes();
		  addTrackware.calSpendTimes();
		  saveTrackware.calSpendTimes();
		  submitTrackware.calSpendTimes();
	}
}
