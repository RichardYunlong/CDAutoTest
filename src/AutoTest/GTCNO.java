package AutoTest;

/**
 *  用例输入参数表管理
 */
public class GTCNO {
	
	/**
	 * 	【核心数据结构】
	 *  参数表-Object[][]类型
	 */
	private static Object[][] TCNO = null;
	
	/**
	 *  重置有效用例输入集合：创建与参数缓存区（GParam.strTestCaseInputArray）相同大小的集合，测试输入形式为集合时有效
	 */
	private static void initTCNO() {
		if(GProgress.getTCTotalNum() > 0 && GValue.CASE_NUM_MAX > 0) {
			TCNO = new Object[GProgress.getTCTotalNum()][GValue.CASE_NUM_MAX];
			for(int i = 0;i < GProgress.getTCTotalNum();i++) {
				for(int j = 0;j < GValue.CASE_NUM_MAX;j++) {
					TCNO[i][j]=(Object)("");
				}
			}
		}
	}
	
	/**
	 * 【核心数据结构】
	 *  输入参数文件缓存-Strin[][]g类型
	 *  
	 *  用例输入详情：按照“行列”的形式，将输入参数文件全部读入到缓存中，包含参数文件的【字段名】行和【序号】列
	 */
	public static String[][] TCNO_STR = null;
	
	/**
	 *  设置用例的数组行列值
	 *  
	 *  @param rowNum 用例总个数
	 *  @param colNum 单个用例参数个数
	 */
	public static void initTCNO_STR(int rowNum, int colNum) {
		if((rowNum > 0) && (colNum > 0)) {
			GProgress.setTCTotalNum(rowNum);
			TCNO_STR = new String[rowNum][colNum];
		}
		
		for (int i = 0; i < rowNum; i++) {
			for (int j = 0; j < colNum; j++) {
				TCNO_STR[i][j] = "empty";
			}
		}
	}
	
	/**
	 *  【核心数据结构】
	 *  参数表-String[][]类型
	 *  
	 *  用例输入详情：按照“行列”的形式，将GParam.strTestCaseInputArray中的【有效列】（即去除【字段名】行和【序号】列）读入到缓存中，
	 */
	public static String[][] gTCNO4 = null;
	
	/**
	 *  使用用例输入类型加载参数表
	 */
	private static void preInputs() {
		if (GTestPlan.bTestOutputBackupResult) {
			GFile.writeStringToRight(GLog.strLogStyle[4], "[PREINPUTS]\r\n");
		}
		int index = 0;
		int i = 0;
		int j = 0;
		gTCNO4 = new String[GProgress.getTCTotalNum()-1][GValue.PARAM_NUM_MAX];//开始写入参数表日志初始化参数表,原始输入表格比实际数据存储区多一行，即第一行“表头”，所以这里初始化数据存储区时减1
		if((GParam.INPUT_TYPE.intValue() == 1 || GParam.INPUT_TYPE.intValue() == 2) && GParam.INPUT_FROM.intValue() == 0) {
			gTCNO4 = (String[][])GObjectImport.get().clone();
		}else {
			// 检测已读取得参数表
			index = 0;
			for (i = GParam.INPUT_ROW_FROM; i < GProgress.getTCTotalNum(); i++) {
				index++;
				GLog.logShowConsole("INIT TESTCASE:" + Integer.toString(i) + " TOTAL:" + Integer.toString(index) + "/" + (TCNO_STR.length - 1));
				for (j = 0; j < GValue.PARAM_NUM_MAX; j++) {
					try {
						if (TCNO_STR[i][j] != null) {
							gTCNO4[i - 1][j] = TCNO_STR[i][j + GParam.INPUT_COL_FROM];
							
							if (gTCNO4[i - 1][j].equals("empty") || gTCNO4[i - 1][j].equals("")) {
								gTCNO4[i - 1][j] = "";
								if (GTestPlan.dRecordInputParamListInTxt != 0 && i <= GTestPlan.dRecordInputParamListInTxt)
									GFile.writeStringToRight(GLog.strLogStyle[4], "空" + "||");
							} else {
								if (GTestPlan.dRecordInputParamListInTxt != 0 && i <= GTestPlan.dRecordInputParamListInTxt)
									GFile.writeStringToRight(GLog.strLogStyle[4], gTCNO4[i - 1][j] + "||");
							}
						} else {
							gTCNO4[i - 1][j] = "";
							if (GTestPlan.dRecordInputParamListInTxt != 0 && i <= GTestPlan.dRecordInputParamListInTxt) {
								GFile.writeStringToRight(GLog.strLogStyle[4], "空" + "  ");
							}
							continue;
						}
					} catch (Exception e) {
						GSys.logSys("WARNING----WRONG PARAM AT ROW " + i + " COLUMN " + j + " IN [TestCaseInputArray]!");
					}
				}
				if (GTestPlan.dRecordInputParamListInTxt != 0 && i < GTestPlan.dRecordInputParamListInTxt)
					GFile.writeStringToRight(GLog.strLogStyle[4], "\r\n");
			}
		}
		
		// 设置计划执行用例总数
		GProgress.setTCTotalNum(gTCNO4.length);
		for(int dIndex = 0; dIndex < gTCNO4.length; dIndex++) {
			if((gTCNO4[dIndex][1]) != null 
			&& (!gTCNO4[dIndex][1].equals("empty")) 
			&& (!gTCNO4[dIndex][1].equals(""))) {
				GTestPlan.TestPlanNumPlus(gTCNO4[dIndex][1]);
			}
		}
		
		// 初始化Collection
		for (int k = 0; k < gTCNO4.length; k++) {
			for (j = 0; j < GValue.PARAM_NUM_MAX; j++) {
				TCNO[k][j] = (Object)gTCNO4[k][j];	
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
	private static void gTCNOByInputType(int inputStyle)
	{
		switch (inputStyle) {
			case 1: {//xls方式
				if (!GExcelImport.doImportXls(GPath.INPUT_XLS_PATH + GPath.INPUT_XLS_NAME)) {
					GSys.logSys("IMPORT XLS FAILED");
				}
				break;
			}	
			case 2: {//txt方式
				if (!GTxtImport.doImportTxt(GPath.INPUT_TXT_PATH + GPath.INPUT_TXT_NAME)) {
					GSys.logSys("IMPORT TXT FAILED");
				}
				break;
			}
			case 3: {
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
		preInputs();
	}
	
	/**
	 *  根据输入参数获取渠道构造并装填Object[][]集合
	 *  
	 *  @param inputType 源文件类型，用例输入方式 
	 *  0-集合
	 *  1-Excel表格
	 *  2-txt文本
	 */
	public void gTCLIST(int inputType) {
		//基本的参数组合判断输入方式并打印提示
		String notice = "";
		if(GParam.INPUT_FROM.equals(0)) {
			if(GParam.INPUT_TYPE.equals(1) || GParam.INPUT_TYPE.equals(2)) {
				GSys.logErrorSys("XLS or TXT must only be outlay");
				System.exit(0);
			}else if(GParam.INPUT_TYPE.equals(0)) {
				notice = "OBJECT[][] TEST INPUTS";
			}else {
				notice = "UNKOWN TEST INPUTS TYPE WHEN SOURCE BUILT-IN";
			}
		}else if(GParam.INPUT_FROM.equals(1)) {
			if(GParam.INPUT_TYPE.equals(0)) {
				GSys.logErrorSys("Object[][] must only be built-in");
				System.exit(0);
			}else if (GParam.INPUT_TYPE.equals(1)) {
				notice = "XLS TEST INPUTS";
			}else if (GParam.INPUT_TYPE.equals(2)) {
				notice = "TXT TEST INPUTS";
			}else {
				notice = "UNKOWN TEST INPUTS TYPE WHEN SOURCE OUTLAY";
			}
		}else {
			notice = "UNKOWN TEST INPUTS SOURCE";
		}
		GSys.logSys(notice);
		
		//提示开始加载参数表
		GSys.logSys("LOAD TESTCASE INPUTS START");
		
		//计算并设置用例总数
		switch (inputType) {
			case 1: {//xls方式
				GProgress.setTCTotalNum(GExcelImport.getRowCourt(GPath.OUTPUT_XLS_PATH + GPath.OUTPUT_XLS_NAME));
				break;
			}	
			case 2: {//txt方式
				GProgress.setTCTotalNum(GTxtImport.getRowCourt());
				break;
			}
			case 3: {
				break;
			}
			default:{//obj方式
				GProgress.setTCTotalNum(GObjectImport.getRowCourt());
				break;
			}
		}
		if((GProgress.getTCTotalNum() <= 0) || (GParam.COL_MAX <= 0)) {
			GSys.logErrorSys("NO INPUTS FOR TEST CASES");
			System.exit(0);
		}
		
		//根据读入的用例数初始化存储容器
		initTCNO_STR(GProgress.getTCTotalNum(), GValue.PARAM_NUM_MAX);
		//初始化集合保存区
		initTCNO();
		//重置测试结果存储区
		GResult.initRESULT_STR();
		
		gTCNOByInputType(inputType);
		
		GLog.logRecord(9, "TESTCASE TOTAL:" + GProgress.getTCTotalNum() + "\r\n");
		GSys.logSys("LOAD TESTCASE INPUTS READY");
	}
}
