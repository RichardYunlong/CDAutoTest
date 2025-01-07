package main.java.Test;

import main.java.Base.*;
import main.java.DT.GLog;
import main.java.IO.GExcelImport;
import main.java.IO.GObjectImport;
import main.java.IO.GTxtImport;
import main.java.Sys.GPath;
import main.java.Sys.GStatic;

/**
 *  用例输入参数表管理
 */
public class GTCNO {
	/**
	 * 	【核心数据结构】
	 *  参数表-Object[][]类型
	 */
	private Object[][] TCNO = null;
	public Object[][] getTCNO() {
		return this.TCNO;
	}
	public void setTCNO(Object[][] tCNO) {
		this.TCNO = tCNO.clone();
	}

	/**
	 * 【核心数据结构】
	 *  未经清洗的输入参数文件缓存-String[][]类型
	 *  用例输入详情：按照“行列”的形式，将输入参数文件全部读入到缓存中，包含参数文件的【字段名】行和【序号】列
	 */
	private String[][] TCNO_STR = null;
	public String[][] getTCNO_STR() {
		return this.TCNO_STR;
	}
	public void setTCNO_STR(String[][] tCNO_STR) {
		this.TCNO_STR = tCNO_STR.clone();
	}
	
	/**
	 *  【核心数据结构】
	 *  临时参数表-String[][]类型
	 *  用例输入详情：按照“行列”的形式，将GParam.strTestCaseInputArray中的【有效列】（即去除【字段名】行和【序号】列）读入到缓存中，
	 */
	private String[][] gTCNO4 = null;
	public String[][] getgTCNO4() {
		return this.gTCNO4;
	}
	public void setgTCNO4(String[][] gTCNO4) {
		this.gTCNO4 = gTCNO4.clone();
	}


	/**
	 * xls类型的导入对象
	 */
	private GExcelImport gExcelImport = null;

	/**
	 * txt类型的导入对象
	 */
	private GTxtImport gTxtImport = null;

	/**
	 * obj类型的导入对象
	 */
	private GObjectImport gObjectImport = null;

	/**
	 * 参数表构造开始时间
	 */
	private long TCNOStartTime = System.currentTimeMillis();

	/**
	 * 参数表构造开结束时间
	 */
	@SuppressWarnings("FieldCanBeLocal")
    private long TCNOEndTime = System.currentTimeMillis();

	/**
	 *  基本的参数组合判断输入方式并打印提示
	 */
	private String getTCNONotice() {
		String notice = "";
		
		if(GStatic.gP.getINPUT_FROM().equals(0)) {
			if(GStatic.gP.getINPUT_TYPE().equals(1) || GStatic.gP.getINPUT_TYPE().equals(2)) {
				GFile.writeStringToGuideBottom("ERROR----" + "XLS OR TXT MUST ONLY BE OUTLAY");
				System.exit(0);
			}else if(GStatic.gP.getINPUT_TYPE().equals(0)) {
				notice = "[OBJECT[][]]";
			}else {
				notice = "UNKOWN TEST INPUTS TYPE WHEN SOURCE BUILT-IN";
			}
		}else if(GStatic.gP.getINPUT_FROM().equals(1)) {
			if(GStatic.gP.getINPUT_TYPE().equals(0)) {
				GFile.writeStringToGuideBottom("ERROR----" + "OBJECT[][] MUST ONLY BE BUILT-IN");
				System.exit(0);
			}else if (GStatic.gP.getINPUT_TYPE().equals(1)) {
				notice = "[XLS]";
			}else if (GStatic.gP.getINPUT_TYPE().equals(2)) {
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
	private void setTCNOTotalNum(String inputType) {
		//计算并设置用例总数
		switch (inputType) {
			case "1": {//xls方式
				this.gExcelImport = new GExcelImport();

				GExcelImport gExcelImportTemp = new GExcelImport();
				GTestMission.gTestProgress.setTotalNum(gExcelImportTemp.getRowCourt(GPath.OUTPUT_XLS_PATH + GPath.OUTPUT_XLS_NAME));
				break;
			}	
			case "2": {//txt方式
				this.gTxtImport = new GTxtImport();

				GTxtImport gTxtImportTemp = new GTxtImport();
				GTestMission.gTestProgress.setTotalNum(gTxtImportTemp.getRowCourt());
				break;
			}
			case "3": {
				break;
			}
			default:{//obj方式
				this.gObjectImport = new GObjectImport();

				GObjectImport gObjectImportTemp = new GObjectImport();
				GTestMission.gTestProgress.setTotalNum(gObjectImportTemp.getRowCourt());
				break;
			}
		}
		
		//如果用例总数为零或者每个用例输入没有任何有效字段，则停止系统进程
		if((GTestMission.gTestProgress.getTotalNum() <= 0) || (GStatic.gP.getCOL_MAX() <= 0)) {
			GFile.writeStringToGuideBottom("ERROR----" + "NO INPUTS FOR TEST CASES");
			System.exit(0);
		}
	}
	
	/**
	 *  设置用例输入Ojbect类型存储区的初始大小
	 *  
	 *  @param rowNum 用例总个数
	 *  @param colNum 单个用例输入的字段数
	 */
	public void initTCNO(int rowNum, int colNum) {
		//初始化集合保存区
		if(rowNum > 0 && colNum > 0) {
			this.TCNO = new Object[rowNum][GValue.CASE_NUM_MAX];
			for(int i = 0;i < rowNum;i++) {
				for(int j = 0;j < GValue.CASE_NUM_MAX;j++) {
					this.TCNO[i][j]= "";
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
	public void initTCNO_STR(int rowNum, int colNum) {
		if((rowNum > 0) && (colNum > 0)) {
			GTestMission.gTestProgress.setTotalNum(rowNum);
			this.TCNO_STR = new String[rowNum][colNum];
		}
		
		for (int i = 0; i < rowNum; i++) {
			for (int j = 0; j < colNum; j++) {
				this.TCNO_STR[i][j] = GMsg.MSG_FIlE_TIP[1];
			}
		}
	}
	
	/**
	 *  使用用例输入类型加载参数表
	 *  
	 *  @param inputStyle 源文件类型，用例输入方式
	 *  0-集合
	 *  1-Excel表格
	 *  2-txt文本
	 */
	private void importTCNOSource(String inputStyle)
	{
		switch (inputStyle) {
			case "1": {//xls方式
				if (!this.gExcelImport.doImportXls(GPath.INPUT_XLS_PATH + GPath.INPUT_XLS_NAME)) {
					GFile.writeStringToGuideBottom("IMPORT XLS FAILED");
				}
				break;
			}	
			case "2": {//txt方式
				if (!this.gTxtImport.doImportTxt(GPath.INPUT_TXT_PATH + GPath.INPUT_TXT_NAME)) {
					GFile.writeStringToGuideBottom("IMPORT TXT FAILED");
				}
				break;
			}
			case "3": {
				GFile.writeStringToGuideBottom("NOT YET");
				System.exit(0);
				break;
			}
			default:{//obj方式
				if (!this.gObjectImport.doImportObjs()) {
					GFile.writeStringToGuideBottom("IMPORT OBJECT[][] FAILED");
				}
				break;
			}
		}
	}
	
	/**
	 *  使用用例输入类型加载参数表
	 */
	private void loading() {
		String[] logPath = GLog.getLogpath().clone();
		//如果自动显示缓存开关为开启，则将测试用例输入集合写入4号日志
		if (GTestMission.gTestPlan.isbIsBackup()) {
			GFile.writeStringToRight(logPath[4], "[TCNO]\r\n");
		}

		//开始写入参数表日志初始化参数表,原始输入表格或文本文档比实际数据存储区多一行，即第一行“表头”，所以这里初始化数据存储区时减1
		this.gTCNO4 = new String[GTestMission.gTestProgress.getTotalNum()-1][GValue.PARAM_NUM_MAX];
		
		//无论用例输入为何种方式，只要用例来源是“0”，即“内置Object[][]”,则采用GObjectImport方式构造gTCNO4
		if((GStatic.gP.getINPUT_TYPE() == 1 || GStatic.gP.getINPUT_TYPE() == 2) && GStatic.gP.getINPUT_FROM() == 0) {
			this.gTCNO4 = (String[][])this.gObjectImport.get().clone();
		}else {
			loadGTCNO4ByTCNO_STR();
		}
		
		//设置计划执行用例总数
		GTestMission.gTestProgress.setTotalNum(this.gTCNO4.length);
		
		countTCNOTypeByGTCNO4();
		loadTCNOByGTCNO4();
	}
	
	/**
	 *  按"行号"读取用例中的所有字段
	 *  
	 *  @param rowIndex 行
	 */
	private void loadCols(int rowIndex) {
		String[] logPath = GLog.getLogpath().clone();
		String inputTemp = "";
		
		for (int j = 0; j < GValue.PARAM_NUM_MAX; j++) {
			try {
				if (this.TCNO_STR[rowIndex][j] != null) {
					//配置文件中的行号说明值-1，对应数组中的下标
					this.gTCNO4[rowIndex - 1][j] = this.TCNO_STR[rowIndex][j + GStatic.gP.getINPUT_COL_FROM()];
					
					if (this.gTCNO4[rowIndex - 1][j].equals(GMsg.MSG_FIlE_TIP[1]) || this.gTCNO4[rowIndex - 1][j].isEmpty()) {
						this.gTCNO4[rowIndex - 1][j] = "";
						inputTemp = "空" + "||";
							
					} else {
						inputTemp = this.gTCNO4[rowIndex - 1][j] + "||";
					}
				} else {
					inputTemp = "空" + "  ";
				}
				
				if (GTestMission.gTestPlan.getRecordInputParamListInTxt() != 0 && rowIndex <= GTestMission.gTestPlan.getRecordInputParamListInTxt()) {
					GFile.writeStringToRight(logPath[4], inputTemp);
				}
			} catch (Exception e) {
				GFile.writeStringToRight(logPath[4], "WRONG PARAM AT ROW " + rowIndex + " COLUMN " + j + " IN [TestCaseInputArray]!");
			}
		}
	}
	
	/**
	 *  用TCNO_STR装填gTCNO4
	 *  用原始未经“清洗”的String[][]装填纯净的String[][]
	 */
	private void loadGTCNO4ByTCNO_STR() {
		String[] logPath = GLog.getLogpath().clone();
		//检测已读取得参数表已经读取了多少条用例
		int index = 0;
		for (int i = GStatic.gP.getINPUT_ROW_FROM(); i < GTestMission.gTestProgress.getTotalNum(); i++) {
			index++;
			GLog.logShowConsole("INIT TESTCASE:" + Integer.valueOf(i).toString() + " TOTAL:" + Integer.valueOf(index).toString() + "/" + (this.TCNO_STR.length - 1));
			loadCols(index);
			if (GTestMission.gTestPlan.getRecordInputParamListInTxt() != 0 && i < GTestMission.gTestPlan.getRecordInputParamListInTxt())
				GFile.writeStringToRight(logPath[4], "\r\n");
		}
	}
	
	/**
	 *  根据gTCNO4中的用例类型字段，统计各类用例的数量
	 */
	private void countTCNOTypeByGTCNO4() {
        for (String[] strings : this.gTCNO4) {
            if ((strings[1]) != null
                    && (!strings[1].equals(GMsg.MSG_FIlE_TIP[1]))
                    && (!strings[1].isEmpty())) {
                GTestMission.gTestPlan.TestPlanNumPlus(strings[1]);
            }
        }
	}
	
	/**
	 *  用gTCNO4装填TCNO
	 *  用String[][]装填Object[][]
	 */
	private void loadTCNOByGTCNO4() {
		// 初始化Collection
		for (int i = 0; i < this.gTCNO4.length; i++) {
            System.arraycopy(this.gTCNO4[i], 0, this.TCNO[i], 0, GValue.PARAM_NUM_MAX);
		}
	}
	
	/**
	 *  构造函数
	 *  根据输入参数获取渠道构造Object[][]集合
	 *  
	 *  @param inputType 源文件类型，用例输入方式
	 *  0-集合
	 *  1-Excel表格
	 *  2-txt文本
	 */
	public GTCNO(String inputType) {
		GClazz.thisADataUnitClass();

		//日志准备
		TCNOStartTime = System.currentTimeMillis();

		GFile.writeStringToGuideBottom(GMissionMsg.getStepTop("SYSTEM INPUTS LOADING"));
		GFile.writeStringToGuideBottom(GStatic.gSys.getDate() + " READ INPUTS");
		GFile.writeStringToGuideBottom("INPUTS TYPE " + getTCNONotice());
		GFile.writeStringToGuideBottom("LOAD START");
		
		//初始化存储器
		setTCNOTotalNum(inputType);
		initTCNO(GTestMission.gTestProgress.getTotalNum(), GValue.PARAM_NUM_MAX);
		initTCNO_STR(GTestMission.gTestProgress.getTotalNum(), GValue.PARAM_NUM_MAX);
		GTestMission.gTestResult.initResultString(GTestMission.gTestProgress.getTotalNum(), GValue.PARAM_NUM_MAX);
	}

	/**
	 *  装填函数
	 *  根据输入参数获取渠道装填Object[][]集合
	 *
	 *  @param inputType 源文件类型，用例输入方式
	 *  0-集合
	 *  1-Excel表格
	 *  2-txt文本
	 */
	public void GTCNO_CONSTRUTION(String inputType){
		//导入参数文件
		importTCNOSource(inputType);

		//加载所有参数
		loading();

		//日志补充
		GLog.logRecord(9, "TESTCASE TOTAL:" + GTestMission.gTestProgress.getTotalNum() + "\r\n");
		GFile.writeStringToGuideBottom("LOAD " + GTestMission.gTestProgress.getTotalNum() + " COMPLETE");
		TCNOEndTime = System.currentTimeMillis();
		GFile.writeStringToGuideBottom("SYSTEM INPUTS LOADING -SPEND:" + (TCNOEndTime - TCNOStartTime) + "MS");
		GFile.writeStringToGuideBottom(GMissionMsg.getStepBottom("SYSTEM INPUTS COMPELETE"));
	}
}
