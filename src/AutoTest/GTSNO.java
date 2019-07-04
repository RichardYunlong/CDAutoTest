package AutoTest;

/**
 *  用例输入参数表管理
 */
public class GTSNO {
	
	/**
	 *  有效用例输入集合-Object类型
	 */
	private static Object[][] PARAMS_OBJECT = null;
	
	/**
	 *  设置用例的数组行列值
	 */
	public static void initParamAndTestCaseNum(int paramNum, int testCaseNum) {
		// long startTime=System.currentTimeMillis();
		// GLog.GLogDoReady("["+startTime+"]CONSTRUCT [TestCaseInputArray] START");
		if((paramNum > 0) && (testCaseNum > 0)) {
			GParam.setTestParamNum_MAX(paramNum);
			GParam.setTestCaseNum_MAX(testCaseNum);
			GParam.TestCaseInputArray = new String[testCaseNum][paramNum];
		}
		
		for (int i = 0; i < testCaseNum; i++) {
			for (int j = 0; j < paramNum; j++) {
				GParam.TestCaseInputArray[i][j] = "empty";
			}
		}

		// GLog.GLogDoReady(startTime, "TestCaseInputArray");
	}
	
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
	 *  根据输入参数获取渠道构造并装填Object[][]集合
	 *  dInputsType：用例输入方式  0-集合，1-Excel表格，2-txt文本
	 *  inputFilePath:指定输入文件时有效，为空时使用默认值"./input/testcase.xls或txt"
	 */
	public void GTSNOS_LIST(int dInputsStyle) {
		//基本的参数组合判断
		if(GTestCase.TestInputSource.equals(1) && GTestCase.TestInputType.equals(0)) {
			GFile.WriteStringToBottom(GSys.Guide, "Object[][] can only be built-in");
			System.exit(0);
		}else if(GTestCase.TestInputSource.equals(0) && GTestCase.TestInputType.equals(1)) {
			GFile.WriteStringToBottom(GSys.Guide, "XLS can only be outlay");
			System.exit(0);
		}else if(GTestCase.TestInputSource.equals(0) && GTestCase.TestInputType.equals(2)) {
			GFile.WriteStringToBottom(GSys.Guide, "TXT can only be outlay");
			System.exit(0);
		}else if(GTestCase.TestInputSource.equals(0) && GTestCase.TestInputType.equals(0)) {
			GFile.WriteStringToBottom(GSys.Guide, "\r\nOBJECT[][] TEST INPUTS\r\n");
		}else if(GTestCase.TestInputSource.equals(1) && GTestCase.TestInputType.equals(1)) {
			GFile.WriteStringToBottom(GSys.Guide, "\r\nXLS TEST INPUTS\r\n");
		}else if(GTestCase.TestInputSource.equals(1) && GTestCase.TestInputType.equals(2)) {
			GFile.WriteStringToBottom(GSys.Guide, "\r\nTXT TEST INPUTS\r\n");
		}else {
			GFile.WriteStringToBottom(GSys.Guide, "\r\nUNKOWN TEST INPUTS\r\n");
		}
		GFile.WriteStringToBottom(GSys.Guide, "\r\nLOAD TESTCASE INPUTS START\r\n");
		
		GParam.setTestCaseOutputFullName(GExportExcel.OUTPUTPATH + GExportExcel.OUTPUTXLS);//初始化输出结果表格路径
		switch (dInputsStyle) {
			case 1: {
				GParam.setTestCaseInputFullName(GImportExcel.INPUTPATH + GImportExcel.INPUTXLS);//输入参数表格路径
				GParam.setTestParamNum_MAX(GImportExcel.PARAM_NUM_MAX_EXCEL);//设置单个用例所包含的参数个数上线
				GParam.setTestCaseNum_MAX(GImportExcel.getInputXlsRowCourt(GParam.getTestCaseInputFullName()));// 计算并设置用例总数，计算前也会先检查输入表格是否存在
				break;
			}	
			case 2: {
				GParam.setTestParamNum_MAX(GImportTxt.PARAM_NUM_MAX_TXT);//设置单个用例所包含的参数个数上线
				GParam.setTestCaseNum_MAX(GImportTxt.getInputTxtRowCourt(GParam.getTestCaseInputFullName()));// 计算并设置用例总数，计算前也会先检查输入表格是否存在
				break;
			}
			case 3: {
				/**/
				break;
			}
			default:{
				GParam.setTestParamNum_MAX(GObjectInputs.PARAM_NUM_MAX_OBJS);//设置单个用例所包含的参数个数上线
				GParam.setTestCaseNum_MAX(GObjectInputs.getInputTxtRowCourt());//计算并设置用例总数，计算前也会先检查输入表格是否存在
				break;
			}
		}
		if((GParam.getTestCaseNum_MAX() <= 0) || (GParam.getTestParamNum_MAX() <= 0)) {
			GFile.WriteStringToBottom(GSys.Guide,"NO INPUTS FOR TEST CASES");
			System.exit(0);
		}
		initParamAndTestCaseNum(GParam.getTestParamNum_MAX(), GParam.getTestCaseNum_MAX());// 初始化参数存储容器
		resetParameters();//初始化集合保存区
		GError.resetGError();// 重置测试结果存储区
		GTSNOByInputsStyle(dInputsStyle);
		
		
		GFile.WriteStringToBottom(GLog.LogStyle[9], "TESTCASE TOTAL:" + GParam.TestTotalNo + "\r\n");
		GFile.WriteStringToBottom(GSys.Guide, "\r\nLOAD TESTCASE INPUTS READY\r\n");//开始写入参数表日志
	}
	
	/**
	 *  使用用例输入类型加载参数表
	 */
	private static void PreInputs() {
		int index = 0;
		int i = 0;
		int j = 0;
		TSSTYLE_TSNO4 = new String[GParam.getTestCaseNum_MAX()-1][GParam.getTestParamNum_MAX()];//开始写入参数表日志初始化参数表,原始输入表格比实际数据存储区多一行，即第一行“表头”，所以这里初始化数据存储区时减1
		if((GTestCase.TestInputType.intValue() == 1 || GTestCase.TestInputType.intValue() == 2) && GTestCase.TestInputSource.intValue() == 0) {
			TSSTYLE_TSNO4 = (String[][])GObjectInputs.getTestCases().clone();
		}else {
			// 检测已读取得参数表
			index = 0;
			for (i = GTestCase.TestInputBeginRowIndex; i < GParam.getTestCaseNum_MAX(); i++) {
				index++;
				System.out.println("INIT TESTCASE:" + i + " TOTAL:" + index + "/" + (GParam.TestCaseInputArray.length - 1));
				for (j = 0; j < GParam.getTestParamNum_MAX(); j++) {
					try {
						if (GParam.TestCaseInputArray[i][j] != null) {
							TSSTYLE_TSNO4[i - 1][j] = GParam.TestCaseInputArray[i][j + GTestCase.TestInputBeginColumnIndex];
							
							if (TSSTYLE_TSNO4[i - 1][j].equals("empty") || TSSTYLE_TSNO4[i - 1][j].equals("")) {
								TSSTYLE_TSNO4[i - 1][j] = "";
								if (GParam.isRecordInputParamListInTxt != 0 && i <= GParam.isRecordInputParamListInTxt)
									GFile.WriteStringToRight(GLog.LogStyle[4], "空" + "||");
							} else {
								if (GParam.isRecordInputParamListInTxt != 0 && i <= GParam.isRecordInputParamListInTxt)
									GFile.WriteStringToRight(GLog.LogStyle[4], TSSTYLE_TSNO4[i - 1][j] + "||");
							}
						} else {
							TSSTYLE_TSNO4[i - 1][j] = "";
							if (GParam.isRecordInputParamListInTxt != 0 && i <= GParam.isRecordInputParamListInTxt) {
								GFile.WriteStringToRight(GLog.LogStyle[4], "空" + "  ");
							}
							continue;
						}
					} catch (Exception e) {
						GFile.WriteStringToBottom(GSys.Guide,"WARNING WRONG PARAM AT ROW " + i + " COLUMN " + j + " IN [TestCaseInputArray]!");
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
	 *  使用用例输入类型加载参数表
	 */
	private static void GTSNOByInputsStyle(int dInputsStyle)
	{
		switch (dInputsStyle) {
			case 1: {
				if (!GImportExcel.doImportExcel(GParam.getTestCaseInputFullName())) {// 读入用例输入Excel
					GFile.WriteStringToBottom(GSys.Guide, "\r\nIMPORT XLS FAILED\r\n");
				}
				break;
			}	
			case 2: {
				if (!GImportTxt.doImportTxt(GParam.getTestCaseInputFullName())) {
					GFile.WriteStringToBottom(GSys.Guide, "\r\nIMPORT TXT FAILED\r\n");
				}
				break;
			}
			case 3: {
				GFile.WriteStringToBottom(GSys.Guide,"CAN NOT BE USED YET！");
				System.exit(0);
				break;
			}
			default:{
				if (!GObjectInputs.importObjectInputs()) {
					GFile.WriteStringToBottom(GSys.Guide, "\r\nIMPORT OBJECT[][] FAILED\r\n");
				}
				break;
			}
		}
		PreInputs();
	}
	
	
}
