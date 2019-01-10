package AutoTest;

/**
 *  用例输入参数表管理
 */
public class GTSNO {
	
	/**
	 *   配置单个用例参数个数最大值
	 */
	private static int ParamNum_MAX_EXCEL = 12;
	
	/**
	 *  有效用例输入集合-Object类型
	 */
	private static Object[][] PARAMS_OBJECT = null;
	
	/**
	 *  重置有效用例输入集合
	 */
	private static void resetParameters() {
		if(GParam.getTestCaseNum_MAX() > 0 && GParam.getTestParamNum_MAX() > 0) {
			PARAMS_OBJECT = new Object[GParam.getTestCaseNum_MAX()][GParam.getTestParamNum_MAX()];
			for(int i = 0;i < GParam.getTestCaseNum_MAX();i++) {
				for(int j = 0;j < GParam.getTestParamNum_MAX();j++) {
					PARAMS_OBJECT[i][j]=(Object)("");
				}
			}
		}
	}

	/**
	 *  有效用例输入集合-String类型
	 */
	public static String[][] TSSTYLE_TSNO4 = null;
	
	/**
	 *  是否通过Excel表获得输入参数
	 */
	private static boolean ByExcel = false;
	
	/**
	 *  设置是否通过Excel表获得输入参数
	 */
	public static void setByExcel(boolean bByExcel) {
		ByExcel = bByExcel;
	}
	
	/**
	 *  设置是否通过Excel表获得输入参数
	 */
	public static boolean getByExcel() {
		return ByExcel;
	}

	/**
	 *  使用Excel表格构造用例输入参数表
	 */
	private static void GTSNOByExcel(String inputFilePath) {		
		setByExcel(true);
		if(GTestCase.TestInputSource.intValue() == 0) {
			GLog.GLogDoReady("UNSPPORT INLAY EXCEL");
			System.out.println("UNSPPORT INLAY EXCEL");
			return;
		}
		
		GParam.setTestCaseInputExcelFullName(inputFilePath);// 输入参数表格路径
		GParam.setTestCaseOutputExcelFullName("./output/output.xls");// 输出结果表格路径
		GImportExcel gImportExcel = new GImportExcel();// 检查输入Excel，准备导入参数
		GParam.setTestParamNum_MAX(ParamNum_MAX_EXCEL);//设置单个用例所包含的参数个数上线
		GParam.setTestCaseNum_MAX(GImportExcel.getRowCourt());// 计算并设置用例总数，计算前也会先检查输入表格是否存在
		gImportExcel.initParamAndTestCaseNum(GParam.getTestParamNum_MAX(), GParam.getTestCaseNum_MAX());// 初始化参数存储容器
		if (!gImportExcel.doImportExcel()) {// 读入用例输入Excel
			GLog.GLogDoReady("IMPORT EXCEL FAILED");
		}
		resetParameters();//初始化集合保存区
		GError.resetGError();// 重置测试结果存储区
		
		int index = 0;
		int i = 0;
		int j = 0;
		
		GFile.WriteStringToRight(GLog.LogStyle[4], "\r\nRELOADED TESTCASE INPUTS\r\n");//开始写入参数表日志
		TSSTYLE_TSNO4 = new String[GParam.getTestCaseNum_MAX()-1][GParam.getTestParamNum_MAX()];//开始写入参数表日志初始化参数表,原始输入表格比实际数据存储区多一行，即第一行“表头”，所以这里初始化数据存储区时减1

		if(GTestCase.TestInputSource.intValue() == 0) {
			TSSTYLE_TSNO4 = (String[][])GObjectInputs.getTestCases().clone();
		}else{
			// 检测已读取得参数表
			index = 0;
			for (i = 1; i < GParam.getTestCaseNum_MAX(); i++) {
				index++;
				System.out.println("INIT TESTCASE:" + i + " TOTAL:" + index + "/" + (GParam.TestCaseInputArray.length - 1));
				for (j = 0; j < GParam.getTestParamNum_MAX(); j++) {
					try {
						if (GParam.TestCaseInputArray[i][j] != null) {
							TSSTYLE_TSNO4[i - 1][j] = GParam.TestCaseInputArray[i][j];
							if (TSSTYLE_TSNO4[i - 1][j].equals("empty") || TSSTYLE_TSNO4[i - 1][j].equals("")) {
								TSSTYLE_TSNO4[i - 1][j] = "";
								if (GParam.isRecordInputParamListInTxt != 0 && i <= GParam.isRecordInputParamListInTxt)
									GFile.WriteStringToRight(GLog.LogStyle[4], "空" + "  ");
							} else {
								if (GParam.isRecordInputParamListInTxt != 0 && i <= GParam.isRecordInputParamListInTxt)
									GFile.WriteStringToRight(GLog.LogStyle[4], TSSTYLE_TSNO4[i - 1][j] + "  ");
							}
						} else {
							TSSTYLE_TSNO4[i - 1][j] = "";
							if (GParam.isRecordInputParamListInTxt != 0 && i <= GParam.isRecordInputParamListInTxt) {
								GFile.WriteStringToRight(GLog.LogStyle[4], "空" + "  ");
							}
							continue;
						}
					} catch (Exception e) {
						GLog.GLogDoReady("WARNING WRONG PARAM AT ROW " + i + " COLUMN " + j + " IN [TestCaseInputArray]!");
					}
				}
				if (GParam.isRecordInputParamListInTxt != 0 && i < GParam.isRecordInputParamListInTxt)
					GFile.WriteStringToRight(GLog.LogStyle[4], "\r\n");
			}
		}
		
		GParam.TestTotalNo = TSSTYLE_TSNO4.length;// 用例总数
		// 初始化Collection
		for (int k = 0; k < TSSTYLE_TSNO4.length; k++) {
			for (j = 0; j < GParam.getTestParamNum_MAX(); j++) {
				PARAMS_OBJECT[k][j] = (Object)TSSTYLE_TSNO4[k][j];	
			}
		}
	}

	/**
	 *  使用TXT文本构造用例输入参数表
	 */
	private static void GTSNOByTxt(String inputFilePath) {
		GFile.WriteStringToRight(GLog.LogStyle[4], "\r\nRELOADED TESTCASE INPUTS\r\n");

		GImportTxt.ImportTxt(inputFilePath, ".txt");//导入txt
		GParam.setTestParamNum_MAX(ParamNum_MAX_EXCEL);//设置单个用例所包含的参数个数上线
		GParam.setTestCaseNum_MAX(GImportTxt.getTxtLineNum());// 计算并设置用例总数，计算前也会先检查输入表格是否存在
		
		resetParameters();//初始化集合保存区
		GError.resetGError();// 重置测试结果存储区
		
		TSSTYLE_TSNO4 = new String[GParam.getTestCaseNum_MAX()-1][GParam.getTestParamNum_MAX()];
		
		if(GTestCase.TestInputSource.intValue() == 0) {
			TSSTYLE_TSNO4 = GObjectInputs.getTestCasesToString().clone();
		}else{
			TSSTYLE_TSNO4 = GImportTxt.getTestCases().clone();
		}
		
		GParam.TestTotalNo = TSSTYLE_TSNO4.length;// 用例总数
		// 初始化Collection
		for (int k = 0; k < GParam.TestTotalNo; k++) {
			for (int j = 0; j < TSSTYLE_TSNO4[k].length; j++) {
				if (TSSTYLE_TSNO4[k][j] != null) {
					PARAMS_OBJECT[k][j] = (Object)TSSTYLE_TSNO4[k][j];
					if (GParam.isRecordInputParamListInTxt != 0 && k <= GParam.isRecordInputParamListInTxt) {
						GFile.WriteStringToRight(GLog.LogStyle[4], TSSTYLE_TSNO4[k][j] + "  ");
					}		
				}
			}
			if (GParam.isRecordInputParamListInTxt != 0 && k <= GParam.isRecordInputParamListInTxt)
				GFile.WriteStringToRight(GLog.LogStyle[4], "\r\n");
		}
	}
	
	/**
	 *  使用CSV表格构造用例输入参数表
	 */
	private static void GTSNOByCSV(String inputFilePath) {
		System.out.println("CAN NOT BE USED YET！");
	}

	/**
	 *  使用集合表格构造用例输入参数表
	 */
	private static void GTSNOByObject() {
		GFile.WriteStringToRight(GLog.LogStyle[4], "\r\nRELOADED TESTCASE INPUTS\r\n");
		GParam.setTestParamNum_MAX(ParamNum_MAX_EXCEL);//设置单个用例所包含的参数个数上线
		GParam.setTestCaseNum_MAX(GObjectInputs.getTestTotal());// 计算并设置用例总数，计算前也会先检查输入表格是否存在
		resetParameters();//初始化集合保存区
		GError.resetGError();// 重置测试结果存储区
		
		TSSTYLE_TSNO4 = new String[GParam.getTestCaseNum_MAX()-1][GParam.getTestParamNum_MAX()];
		
		TSSTYLE_TSNO4 = GObjectInputs.getTestCasesToString().clone();
		
		GParam.TestTotalNo = TSSTYLE_TSNO4.length;// 用例总数
		// 初始化Collection
		for (int k = 0; k < GParam.TestTotalNo; k++) {
			for (int j = 0; j < TSSTYLE_TSNO4[k].length; j++) {
				if (TSSTYLE_TSNO4[k][j] != null) {
					PARAMS_OBJECT[k][j] = (Object)TSSTYLE_TSNO4[k][j];
					if (GParam.isRecordInputParamListInTxt != 0 && k <= GParam.isRecordInputParamListInTxt) {
						GFile.WriteStringToRight(GLog.LogStyle[4], TSSTYLE_TSNO4[k][j] + "  ");
					}		
				}
			}
			if (GParam.isRecordInputParamListInTxt != 0 && k <= GParam.isRecordInputParamListInTxt)
				GFile.WriteStringToRight(GLog.LogStyle[4], "\r\n");
		}
	}
	
	/**
	 *  根据输入参数获取渠道构造并返回Object[][]的参数集合。当参数为0时，仅使用内置集合构造输入参数表
	 *  dInputsType：用例输入方式  0-集合，1-Excel表格，2-txt文本
	 *  inputFilePath:指定输入文件时有效，为空时使用默认值"./input/testcase.xls或txt"
	 */
	public Object[][] GTSNOS_OBJECT(int dInputsStyle, String inputFilePath) {
		switch (dInputsStyle) {
			case 1: {
				if(inputFilePath == null || inputFilePath == "")inputFilePath = "./input/testcase.xls";
				GTSNOByExcel(inputFilePath);
				break;
			}	
			case 2: {
				if(inputFilePath == null || inputFilePath == "")inputFilePath = "./input/testcase.txt";
				GTSNOByTxt(inputFilePath);
				break;
			}
			case 3: {
				if(inputFilePath == null || inputFilePath == "")inputFilePath = "./input/testcase.csv";
				GTSNOByCSV(inputFilePath);
				break;
			}
			default:{
				GTSNOByObject();
				break;
			}
		}
		GLog.GLogDoReady("TESTCASE TOTAL:" + GParam.TestTotalNo);
		return PARAMS_OBJECT;
	}
	
	/**
	 *  根据输入参数获取渠道构造并装填Object[][]集合
	 *  dInputsType：用例输入方式  0-集合，1-Excel表格，2-txt文本
	 *  inputFilePath:指定输入文件时有效，为空时使用默认值"./input/testcase.xls或txt"
	 */
	public void GTSNOS_LIST(int dInputsStyle, String inputFilePath) {
		switch (dInputsStyle) {
			case 1: {
				if(inputFilePath == null || inputFilePath == "")inputFilePath = "./input/testcase.xls";
				GTSNOByExcel(inputFilePath);
				break;
			}	
			case 2: {
				if(inputFilePath == null || inputFilePath == "")inputFilePath = "./input/testcase.txt";
				GTSNOByTxt(inputFilePath);
				break;
			}
			case 3: {
				if(inputFilePath == null || inputFilePath == "")inputFilePath = "./input/testcase.csv";
				GTSNOByCSV(inputFilePath);
				break;
			}
			default:{
				GTSNOByObject();
				break;
			}
		}
		GLog.GLogDoReady("TESTCASE TOTAL:" + GParam.TestTotalNo + "\r\n");
	}
}
