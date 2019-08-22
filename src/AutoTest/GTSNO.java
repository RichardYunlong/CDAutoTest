package AutoTest;

/**
 *  用例输入参数表管理
 */
public class GTSNO {
	
	/**
	 * 	【核心数据结构】
	 *  参数表-Object[][]类型
	 */
	private static Object[][] strParamsObject = null;
	
	/**
	 *  重置有效用例输入集合：创建与参数缓存区（GParam.strTestCaseInputArray）相同大小的集合，测试输入形式为集合时有效
	 */
	private static void initObjectParameters() {
		if(GParam.getTestCaseNum() > 0 && GParam.CASE_NUM_MAX > 0) {
			strParamsObject = new Object[GParam.getTestCaseNum()][GParam.CASE_NUM_MAX];
			for(int i = 0;i < GParam.getTestCaseNum();i++) {
				for(int j = 0;j < GParam.CASE_NUM_MAX;j++) {
					strParamsObject[i][j]=(Object)("");
				}
			}
		}
	}
	
	/**
	 *  【核心数据结构】
	 *  参数表-String[][]类型
	 *  用例输入详情：按照“行列”的形式，将GParam.strTestCaseInputArray中的【有效列】（即去除【字段名】行和【序号】列）读入到缓存中，
	 */
	public static String[][] gStyleTSNO4 = null;
	
	/**
	 *  使用用例输入类型加载参数表
	 */
	private static void preInputs() {
		int index = 0;
		int i = 0;
		int j = 0;
		gStyleTSNO4 = new String[GParam.getTestCaseNum()-1][GParam.getTestCaseNum()];//开始写入参数表日志初始化参数表,原始输入表格比实际数据存储区多一行，即第一行“表头”，所以这里初始化数据存储区时减1
		if((GTestCase.dTestInputType.intValue() == 1 || GTestCase.dTestInputType.intValue() == 2) && GTestCase.dTestInputSource.intValue() == 0) {
			gStyleTSNO4 = (String[][])GObjectInputs.getTestCases().clone();
		}else {
			// 检测已读取得参数表
			index = 0;
			for (i = GTestCase.dTestInputBeginRowIndex; i < GParam.getTestCaseNum(); i++) {
				index++;
				GLog.logShowConsole("INIT TESTCASE:" + Integer.toString(i) + " TOTAL:" + Integer.toString(index) + "/" + (GParam.strTestCaseInputArray.length - 1));
				for (j = 0; j < GParam.getTestCaseNum(); j++) {
					try {
						if (GParam.strTestCaseInputArray[i][j] != null) {
							gStyleTSNO4[i - 1][j] = GParam.strTestCaseInputArray[i][j + GTestCase.dTestInputBeginColumnIndex];
							
							if (gStyleTSNO4[i - 1][j].equals("empty") || gStyleTSNO4[i - 1][j].equals("")) {
								gStyleTSNO4[i - 1][j] = "";
								if (GParam.dRecordInputParamListInTxt != 0 && i <= GParam.dRecordInputParamListInTxt)
									GFile.writeStringToRight(GLog.strLogStyle[4], "空" + "||");
							} else {
								if (GParam.dRecordInputParamListInTxt != 0 && i <= GParam.dRecordInputParamListInTxt)
									GFile.writeStringToRight(GLog.strLogStyle[4], gStyleTSNO4[i - 1][j] + "||");
							}
						} else {
							gStyleTSNO4[i - 1][j] = "";
							if (GParam.dRecordInputParamListInTxt != 0 && i <= GParam.dRecordInputParamListInTxt) {
								GFile.writeStringToRight(GLog.strLogStyle[4], "空" + "  ");
							}
							continue;
						}
					} catch (Exception e) {
						GSys.logSys("WARNING----WRONG PARAM AT ROW " + i + " COLUMN " + j + " IN [TestCaseInputArray]!");
					}
				}
				if (GParam.dRecordInputParamListInTxt != 0 && i < GParam.dRecordInputParamListInTxt)
					GFile.writeStringToRight(GLog.strLogStyle[4], "\r\n");
			}
		}
		
		GParam.dTestTotalNo = gStyleTSNO4.length;// 用例总数
		// 初始化Collection
		for (int k = 0; k < gStyleTSNO4.length; k++) {
			for (j = 0; j < GParam.getTestCaseNum(); j++) {
				strParamsObject[k][j] = (Object)gStyleTSNO4[k][j];	
			}
		}
	}
	
	/**
	 *  使用用例输入类型加载参数表
	 *  
	 *  @param dInputsStyle 源文件类型
	 */
	private static void gTSNOByInputsStyle(int dInputsStyle)
	{
		switch (dInputsStyle) {
			case 1: {
				if (!GImportExcel.doImportExcel(GParam.getTestCaseInputFullName())) {// 读入用例输入Excel
					GSys.logSys("IMPORT XLS FAILED");
				}
				break;
			}	
			case 2: {
				if (!GImportTxt.doImportTxt(GParam.getTestCaseInputFullName())) {
					GSys.logSys("IMPORT TXT FAILED");
				}
				break;
			}
			case 3: {
				GSys.logSys("NOT YET");
				System.exit(0);
				break;
			}
			default:{
				if (!GObjectInputs.importObjectInputs()) {
					GSys.logSys("IMPORT OBJECT[][] FAILED");
				}
				break;
			}
		}
		preInputs();
	}
	
	/**
	 *  根据输入参数获取渠道构造并装填Object[][]集合
	 *  dInputsType：用例输入方式  0-集合，1-Excel表格，2-txt文本
	 *  inputFilePath:指定输入文件时有效，为空时使用默认值"./input/testcase.xls或txt"
	 *  
	 *  @param dInputsStyle 源文件类型
	 */
	public void gTSNOSLIST(int dInputsStyle) {
		String notice = "";
		//基本的参数组合判断		
		if(GTestCase.dTestInputSource.equals(0)) {
			if(GTestCase.dTestInputType.equals(1) || GTestCase.dTestInputType.equals(2)) {
				GSys.logErrorSys("XLS or TXT must only be outlay");
				System.exit(0);
			}else if(GTestCase.dTestInputType.equals(0)) {
				notice = "OBJECT[][] TEST INPUTS";
			}else {
				notice = "UNKOWN TEST INPUTS TYPE WHEN SOURCE BUILT-IN";
			}
		}else if(GTestCase.dTestInputSource.equals(1)) {
			if(GTestCase.dTestInputType.equals(0)) {
				GSys.logErrorSys("Object[][] must only be built-in");
				System.exit(0);
			}else if (GTestCase.dTestInputType.equals(1)) {
				notice = "XLS TEST INPUTS";
			}else if (GTestCase.dTestInputType.equals(2)) {
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
		
		GParam.setTestCaseOutputFullName(GExportExcel.OUTPUTPATH + GExportExcel.OUTPUTXLS);//初始化输出结果表格路径
		switch (dInputsStyle) {
			case 1: {
				GParam.setTestCaseInputFullName(GParam.INPUT_XLS_PATH + GParam.INPUT_XLS_NAME);//输入参数表格路径
				GParam.setTestCaseNum(GImportExcel.getInputXlsRowCourt(GParam.getTestCaseInputFullName()));// 计算并设置用例总数，计算前也会先检查输入表格是否存在
				break;
			}	
			case 2: {
				GParam.setTestCaseNum(GImportTxt.getInputTxtRowCourt());// 计算并设置用例总数，计算前也会先检查输入表格是否存在
				break;
			}
			case 3: {
				break;
			}
			default:{
				GParam.setTestCaseNum(GObjectInputs.getTestTotal());//计算并设置用例总数，计算前也会先检查输入表格是否存在
				break;
			}
		}
		if((GParam.getTestCaseNum() <= 0) || (GParam.getTestParamNum() <= 0)) {
			GSys.logErrorSys("NO INPUTS FOR TEST CASES");
			System.exit(0);
		}
		
		GParam.initParamAndTestCaseNum(GParam.PARAM_NUM_MAX, GParam.getTestCaseNum());// 初始化参数存储容器
		initObjectParameters();//初始化集合保存区
		GResult.initGError();//重置测试结果存储区
		gTSNOByInputsStyle(dInputsStyle);
		
		
		GLog.logRecord(9, "TESTCASE TOTAL:" + GParam.dTestTotalNo + "\r\n");
		GSys.logSys("LOAD TESTCASE INPUTS READY");
	}
}
