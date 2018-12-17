package AutoTest;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("rawtypes")
public class GTSNO {
	/**
	 * @see  可以设置将读入的参数表打印只特定的日志文档的条数，此项数字越大，执行速度越慢
	 */
	public static int isRecordInputParamListInTxt = 6;
	
	/**
	 * @see  配置单个用例参数个数最大值
	 */
	private static int ParamNum_MAX_EXCEL = 12;
	
	/**
	 * @see 有效用例输入集合-Object类型
	 */
	private static Object[][] PARAMS_OBJECT = null;
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
	 * @see 有效用例输入集合-String类型
	 */
	public static String[][] TSSTYLE_TSNO4 = null;
	
	/**
	 * @see 中间变量，用于暂存结果
	 */
	public static List<List> TSNOALL = new ArrayList<List>();
	
	/**
	 * @see 是否通过Excel表获得输入参数
	 */
	private static boolean ByExcel = false;
	public static void setByExcel(boolean bByExcel) {
		ByExcel = bByExcel;
	}
	public static boolean getByExcel() {
		return ByExcel;
	}

	/**
	 * @see 使用Excel表格构造用例
	 */
	private static void GTSNOByExcel() {		
		setByExcel(true);
		if(GTestCase.TestInputSource.intValue() == 0) {
			GLog.GLogDoReady("UNSPPORT INLAY EXCEL");
			System.out.println("UNSPPORT INLAY EXCEL");
			return;
		}
		GParam.setTestCaseInputExcelFullName("./input/testcase.xls");// 输入参数表格路径
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
		TSSTYLE_TSNO4 = new String[GParam.getTestCaseNum_MAX()][GParam.getTestParamNum_MAX()];//开始写入参数表日志初始化参数表

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
							if (isRecordInputParamListInTxt != 0 && i < isRecordInputParamListInTxt)
								GFile.WriteStringToRight(GLog.LogStyle[4], "空" + "  ");
						} else {
							if (isRecordInputParamListInTxt != 0 && i < isRecordInputParamListInTxt)
								GFile.WriteStringToRight(GLog.LogStyle[4], TSSTYLE_TSNO4[i - 1][j] + "  ");
						}
					} else {
						TSSTYLE_TSNO4[i - 1][j] = "";
						if (isRecordInputParamListInTxt != 0 && i < isRecordInputParamListInTxt) {
							GFile.WriteStringToRight(GLog.LogStyle[4], "空" + "  ");
						}
						continue;
					}
				} catch (Exception e) {
					GLog.GLogDoReady("WARNING WRONG PARAM AT ROW " + i + " COLUMN " + j + " IN [TestCaseInputArray]!");
				}
			}
			if (isRecordInputParamListInTxt != 0 && i < isRecordInputParamListInTxt)
				GFile.WriteStringToRight(GLog.LogStyle[4], "\r\n");
		}

		// 初始化Collection
		for (int k = 0; k < TSSTYLE_TSNO4.length; k++) {
			List<String> listPARAM = new ArrayList<String>();
			for (j = 0; j < GParam.getTestParamNum_MAX(); j++) {
				listPARAM.add(j, TSSTYLE_TSNO4[k][j]);
				PARAMS_OBJECT[k][j] = (Object)TSSTYLE_TSNO4[k][j];	
			}
			TSNOALL.add(listPARAM);
		}
		GParam.TestTotalNo = TSSTYLE_TSNO4.length;//更正用例总数
	}

	/**
	 * @see 使用集合表格构造用例Cellection
	 */
	private static void GTSNOByCollection() {
		GFile.WriteStringToRight(GLog.LogStyle[4], "\r\nRELOADED TESTCASE INPUTS\r\n");

		String[][] TSSTYLE_TSNO4 = null;
		if(GTestCase.TestInputSource.intValue() == 0) {
			TSSTYLE_TSNO4 = (String[][])GObjectInputs.getTestCases().clone();
		}else{
			TSSTYLE_TSNO4 = (String[][])GImportTxt.getTestCases().clone();
		}
		GParam.TestTotalNo = TSSTYLE_TSNO4.length;// 用例总数
		// 初始化Collection
		for (int k = 0; k < GParam.TestTotalNo; k++) {
			List<String> listPARAM = new ArrayList<String>();
			for (int j = 0; j < 2; j++) {
				if (TSSTYLE_TSNO4[k][j] != null) {
					PARAMS_OBJECT[k][j] = (Object)TSSTYLE_TSNO4[k][j];
					if (isRecordInputParamListInTxt != 0 && k < isRecordInputParamListInTxt) {
						GFile.WriteStringToRight(GLog.LogStyle[4], TSSTYLE_TSNO4[k][j] + "  ");
					}		
				}
			}
			TSNOALL.add(listPARAM);
			if (isRecordInputParamListInTxt != 0 && k < isRecordInputParamListInTxt)
				GFile.WriteStringToRight(GLog.LogStyle[4], "\r\n");
		}
	}

	/**
	 * @see 使用集合表格构造用例Cellection
	 */
	private static void GTSNOByCSV() {
		System.out.println("Check Class Which Is Named TestRunAll");
	}

	/**
	 * @see 使用集合表格构造用例Cellection
	 */
	private static void GTSNOByObject() {
		Object[][] MainError = new Object[][] {
			{0,3210},
		};
		
		PARAMS_OBJECT = MainError;
	}
	
	/**
	 * @see 根据输入参数获取渠道构造参数集合
	 */
	public Object[][] GTSNOS_OBJECT(int dInputsFormatStyle) {
		switch (dInputsFormatStyle) {
			case 1: {
				GTSNOByExcel();
				break;
			}	
			case 2: {
				GTSNOByCollection();
				break;
			}
			case 3: {
				GTSNOByCSV();
				break;
			}
			default:{
				GTSNOByObject();
				break;
			}
		}
		return PARAMS_OBJECT;
	}
	
	/**
	 * @see 根据输入参数获取渠道构造参数集合
	 */
	public void GTSNOS_LIST(int dInputsFormatStyle) {
		switch (dInputsFormatStyle) {
			case 1: {
				GTSNOByExcel();
				break;
			}	
			case 2: {
				GTSNOByCollection();
				break;
			}
			case 3: {
				GTSNOByCSV();
				break;
			}
			default:{
				GTSNOByObject();
				break;
			}
		}
	}
}
