package Test;

import Base.GFile;
import Base.GMissionMsg;
import Base.GMsg;
import Base.GValue;
import DT.GLog;
import IO.GExcelImport;
import IO.GObjectImport;
import IO.GTxtImport;
import Sys.GParam;
import Sys.GPath;
import Sys.GSys;

/**
 *  用例输入参数表管理
 */
public class GTCNO {
	
	/**
	 * 【核心数据结构】
	 * 
	 *  未经清洗的输入参数文件缓存-String[][]类型
	 *  
	 *  用例输入详情：按照“行列”的形式，将输入参数文件全部读入到缓存中，包含参数文件的【字段名】行和【序号】列
	 */
	private static String[][] TCNO_STR = null;
	
	/**
	 *  【核心数据结构】
	 *  
	 *  临时参数表-String[][]类型
	 *  
	 *  用例输入详情：按照“行列”的形式，将GParam.strTestCaseInputArray中的【有效列】（即去除【字段名】行和【序号】列）读入到缓存中，
	 */
	private static String[][] gTCNO4 = null;
	
	/**
	 * 	【核心数据结构】
	 * 
	 *  参数表-Object[][]类型
	 */
	private static Object[][] TCNO = null;
	
	public static Object[][] getTCNO() {
		return TCNO;
	}

	public static void setTCNO(Object[][] tCNO) {
		TCNO = tCNO.clone();
	}

	public static String[][] getTCNO_STR() {
		return TCNO_STR;
	}

	public static void setTCNO_STR(String[][] tCNO_STR) {
		TCNO_STR = tCNO_STR.clone();
	}

	public static String[][] getgTCNO4() {
		return gTCNO4;
	}

	public static void setgTCNO4(String[][] gTCNO4) {
		GTCNO.gTCNO4 = gTCNO4.clone();
	}

	/**
	 *  基本的参数组合判断输入方式并打印提示
	 */
	private static String getTCNONotice() {
		String notice = "";
		
		if(GParam.getINPUT_FROM().equals(0)) {
			if(GParam.getINPUT_TYPE().equals(1) || GParam.getINPUT_TYPE().equals(2)) {
				GSys.logErrorSys("XLS OR TXT MUST ONLY BE OUTLAY");
				System.exit(0);
			}else if(GParam.getINPUT_TYPE().equals(0)) {
				notice = "[OBJECT[][]]";
			}else {
				notice = "UNKOWN TEST INPUTS TYPE WHEN SOURCE BUILT-IN";
			}
		}else if(GParam.getINPUT_FROM().equals(1)) {
			if(GParam.getINPUT_TYPE().equals(0)) {
				GSys.logErrorSys("OBJECT[][] MUST ONLY BE BUILT-IN");
				System.exit(0);
			}else if (GParam.getINPUT_TYPE().equals(1)) {
				notice = "[XLS]";
			}else if (GParam.getINPUT_TYPE().equals(2)) {
				notice = "[TXT]";
			}else {
				notice = "[UNKOWN]";
			}
		}else {
			notice = "UNKOWN TEST INPUTS SOURCE";
		}
		
		return notice;
	}
	
	/**
	 *  计算并设置用例总数
	 *  
	 *  @param inputType 源文件类型，用例输入方式
	 */
	private static void setTCNOTotalNum(String inputType) {
		//计算并设置用例总数
		switch (inputType) {
			case "1": {//xls方式
				GTestProgress.setTotalNum(Integer.valueOf(GExcelImport.getRowCourt(GPath.OUTPUT_XLS_PATH + GPath.OUTPUT_XLS_NAME)));
				break;
			}	
			case "2": {//txt方式
				GTestProgress.setTotalNum(Integer.valueOf(GTxtImport.getRowCourt()));
				break;
			}
			case "3": {
				break;
			}
			default:{//obj方式
				GTestProgress.setTotalNum(Integer.valueOf(GObjectImport.getRowCourt()));
				break;
			}
		}
		
		//如果用例总数为零或者每个用例输入没有任何有效字段，则停止系统进程
		if((GTestProgress.getTotalNum() <= 0) || (GParam.getCOL_MAX() <= 0)) {
			GSys.logErrorSys("NO INPUTS FOR TEST CASES");
			System.exit(0);
		}
	}
	
	/**
	 *  设置用例输入Ojbect类型存储区的初始大小
	 *  
	 *  @param rowNum 用例总个数
	 *  @param colNum 单个用例输入的字段数
	 */
	public static void initTCNO(int rowNum, int colNum) {
		//初始化集合保存区
		if(rowNum > 0 && colNum > 0) {
			TCNO = new Object[rowNum][GValue.CASE_NUM_MAX];
			for(int i = 0;i < rowNum;i++) {
				for(int j = 0;j < GValue.CASE_NUM_MAX;j++) {
					TCNO[i][j]=(Object)("");
				}
			}
		}
	}
	
	/**
	 *  设置用例输入String类型存储区的初始大小
	 *  
	 *  @param rowNum 用例总个数
	 *  @param colNum 单个用例参数个数
	 */
	public static void initTCNO_STR(int rowNum, int colNum) {
		if((rowNum > 0) && (colNum > 0)) {
			GTestProgress.setTotalNum(Integer.valueOf(rowNum));
			TCNO_STR = new String[rowNum][colNum];
		}
		
		for (int i = 0; i < rowNum; i++) {
			for (int j = 0; j < colNum; j++) {
				TCNO_STR[i][j] = GMsg.MSG_FIlE_TIP[1];
			}
		}
	}
	
	/**
	 *  使用用例输入类型加载参数表
	 *  
	 *  @param inputType 源文件类型，用例输入方式 
	 *  0-集合
	 *  1-Excel表格
	 *  2-txt文本
	 */
	private static void importTCNOSource(String inputStyle)
	{
		switch (inputStyle) {
			case "1": {//xls方式
				if (!GExcelImport.doImportXls(GPath.INPUT_XLS_PATH + GPath.INPUT_XLS_NAME)) {
					GSys.logSys("IMPORT XLS FAILED");
				}
				break;
			}	
			case "2": {//txt方式
				if (!GTxtImport.doImportTxt(GPath.INPUT_TXT_PATH + GPath.INPUT_TXT_NAME)) {
					GSys.logSys("IMPORT TXT FAILED");
				}
				break;
			}
			case "3": {
				GSys.logSys("NOT YET");
				System.exit(0);
				break;
			}
			default:{//obj方式
				if (!GObjectImport.doImportObjs()) {
					GSys.logSys("IMPORT OBJECT[][] FAILED");
				}
				break;
			}
		}
	}
	
	/**
	 *  使用用例输入类型加载参数表
	 */
	private static void loading() {
		String[] logPath = GLog.getLogpath().clone();
		//如果自动显示缓存开关为开启，则将测试用例输入集合写入4号日志
		if (GTestPlan.isbIsBackup()) {
			GFile.writeStringToRight(logPath[4], "[TCNO]\r\n");
		}

		//开始写入参数表日志初始化参数表,原始输入表格或文本文档比实际数据存储区多一行，即第一行“表头”，所以这里初始化数据存储区时减1
		gTCNO4 = new String[GTestProgress.getTotalNum()-1][GValue.PARAM_NUM_MAX];
		
		//无论用例输入为何种方式，只要用例来源是“0”，即“内置Object[][]”,则采用GObjectImport方式构造gTCNO4
		if((GParam.getINPUT_TYPE().intValue() == 1 || GParam.getINPUT_TYPE().intValue() == 2) && GParam.getINPUT_FROM().intValue() == 0) {
			gTCNO4 = (String[][])GObjectImport.get().clone();
		}else {
			loadGTCNO4ByTCNO_STR();
		}
		
		//设置计划执行用例总数
		GTestProgress.setTotalNum(Integer.valueOf(gTCNO4.length));
		
		countTCNOTypeByGTCNO4();
		loadTCNOByGTCNO4();
	}
	
	/**
	 *  按"行号"读取用例中的所有字段
	 *  
	 *  @param rowIndex 行
	 */
	private static void loadCols(int rowIndex) {
		String[] logPath = GLog.getLogpath().clone();
		String inputTemp = "";
		
		for (int j = 0; j < GValue.PARAM_NUM_MAX; j++) {
			try {
				if (TCNO_STR[rowIndex][j] != null) {
					//配置文件中的行号说明值-1，对应数组中的下标
					gTCNO4[rowIndex - 1][j] = TCNO_STR[rowIndex][j + GParam.getINPUT_COL_FROM()];
					
					if (gTCNO4[rowIndex - 1][j].equals(GMsg.MSG_FIlE_TIP[1]) || gTCNO4[rowIndex - 1][j].equals("")) {
						gTCNO4[rowIndex - 1][j] = "";
						inputTemp = "空" + "||";
							
					} else {
						inputTemp = gTCNO4[rowIndex - 1][j] + "||";
					}
				} else {
					inputTemp = "空" + "  ";
				}
				
				if (GTestPlan.getRecordInputParamListInTxt() != 0 && rowIndex <= GTestPlan.getRecordInputParamListInTxt()) {
					GFile.writeStringToRight(logPath[4], inputTemp);
				}
			} catch (Exception e) {
				GFile.writeStringToRight(logPath[4], "WRONG PARAM AT ROW " + rowIndex + " COLUMN " + j + " IN [TestCaseInputArray]!");
			}
		}
	}
	
	/**
	 *  用TCNO_STR装填gTCNO4
	 *  
	 *  用原始未经“清洗”的String[][]装填纯净的String[][]
	 */
	private static void loadGTCNO4ByTCNO_STR() {
		String[] logPath = GLog.getLogpath().clone();
		//检测已读取得参数表已经读取了多少条用例
		int index = 0;
		for (int i = GParam.getINPUT_ROW_FROM(); i < GTestProgress.getTotalNum(); i++) {
			index++;
			GLog.logShowConsole("INIT TESTCASE:" + Integer.toString(i) + " TOTAL:" + Integer.toString(index) + "/" + (TCNO_STR.length - 1));
			loadCols(index);
			if (GTestPlan.getRecordInputParamListInTxt() != 0 && i < GTestPlan.getRecordInputParamListInTxt())
				GFile.writeStringToRight(logPath[4], "\r\n");
		}
	}
	
	/**
	 *  根据gTCNO4中的用例类型字段，统计各类用例的数量
	 */
	private static void countTCNOTypeByGTCNO4() {
		for(int i = 0; i < gTCNO4.length; i++) {
			if((gTCNO4[i][1]) != null 
			&& (!gTCNO4[i][1].equals(GMsg.MSG_FIlE_TIP[1])) 
			&& (!gTCNO4[i][1].equals(""))) {
				GTestPlan.TestPlanNumPlus(gTCNO4[i][1]);
			}
		}
	}
	
	/**
	 *  用gTCNO4装填TCNO
	 *  
	 *  用String[][]装填Object[][]
	 */
	private static void loadTCNOByGTCNO4() {
		// 初始化Collection
		for (int i = 0; i < gTCNO4.length; i++) {
			for (int j= 0; j < GValue.PARAM_NUM_MAX; j++) {
				TCNO[i][j] = (Object)gTCNO4[i][j];	
			}
		}
	}
	
	/**
	 *  构造函数
	 *  
	 *  根据输入参数获取渠道构造并装填Object[][]集合
	 *  
	 *  @param inputType 源文件类型，用例输入方式
	 *  
	 *  0-集合
	 *  1-Excel表格
	 *  2-txt文本
	 */
	public GTCNO(String inputType) {
		//日志准备
		long startTime = System.currentTimeMillis();
		GSys.logSys(GMissionMsg.getStepTop("SYSTEM INPUTS LOADING"));
		GSys.logSys(GSys.getDate() + " READ INPUTS");
		GSys.logSys("INPUTS TYPE " + getTCNONotice());
		GSys.logSys("LOAD START");
		
		//初始化存储器
		setTCNOTotalNum(inputType);
		initTCNO(GTestProgress.getTotalNum(), GValue.PARAM_NUM_MAX);
		initTCNO_STR(GTestProgress.getTotalNum(), GValue.PARAM_NUM_MAX);
		GTestResult.initResultString(GTestProgress.getTotalNum(), GValue.PARAM_NUM_MAX);
		
		//导入参数文件
		importTCNOSource(inputType);
		
		//加载所有参数
		loading();
		
		//日志补充
		GLog.logRecord(9, "TESTCASE TOTAL:" + GTestProgress.getTotalNum() + "\r\n");
		GSys.logSys("LOAD " + GTestProgress.getTotalNum() + " COMPLETE");
		long endTime = System.currentTimeMillis();
		GSys.logSys("SYSTEM INPUTS LOADING -SPEND:" + (endTime - startTime) + "MS");
		GSys.logSys(GMissionMsg.getStepBottom("SYSTEM INPUTS COMPELETE"));
	}
}
