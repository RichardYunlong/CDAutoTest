package AutoTest;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.ss.usermodel.Workbook;

/**
 *  加载Excel
 */
public class GImportExcel {

	/**
	 *  Excel表格有效性检查结果标记
	 */
	public static boolean IsExcelReady = false;

	/**
	 *  Excel表格有效性检查结果标记
	 */
	public static boolean IsExcelReadyToExport = false;

	/**
	 *  用例的数组是否已准备好
	 */
	public static boolean IsTestCaseInputArrayReady = false;

	/**
	 *  输入参数合集
	 */
	public static String InputMix = "";

	/**
	 *  输出第一轮测试结果
	 */
	public static String OutputMix = "";

	/**
	 *  用例条数上线
	 */
	public static int maxLimit = GParam.curCaseNO_MAX;

	/**
	 *  用例存储列表
	 */
	public static List<GRequestVO> inputList = null;

	/**
	 *  设置用例的数组行列值
	 */
	public void initParamAndTestCaseNum(int paramNum, int testCaseNum) {
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
	 *  Excel表格检查
	 */
	public static boolean checkExcel() {
		long startTime = System.currentTimeMillis();
		GLog.GLogDoReady(GTime.getDate() + " [doCheckExcel] START");

		File testExcel = new File(GParam.TestCaseInputExcelFullName);
		if (!testExcel.exists()) {// 文件是否存在
			System.out.println("EXCEL NOT EXIST!");
			IsExcelReady = false;
		}

		GLog.GLogDoReady(startTime, "doCheckExcel");

		return IsExcelReady;
	}

	/**
	 *  存储用例的数组初始化
	 */
	public static boolean initTestCaseArray() {
		long startTime = System.currentTimeMillis();
		GLog.GLogDoReady(GTime.getDate() + "[doInitTestCaseArray] START");

		/* do something */

		GLog.GLogDoReady(startTime, "doInitTestCaseArray");

		return IsTestCaseInputArrayReady;
	}

	/**
	 *  写入Excel表格
	 */
	public static void write(List<GRequestVO> list, String outputPath) {
		Workbook workbook = null;
		FileOutputStream fileOutputStream = null;
		try {
			if (list.size() > 0) {
				int[] widths = { 6000, 3000, 6000, 6000, 6000, 10000, 6000, 6000, 3000, 3000, 3000, 3000, 3000, 3000,
						6000, 10000, 30000, 8000, 8000 };
				workbook = GExcel.write(list, "测试写", "序号", headers(), fields(), widths, GExcel.Type.XLS);
				ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
				workbook.write(outputStream);
				File file = new File(outputPath);
				fileOutputStream = new FileOutputStream(file);
				fileOutputStream.write(outputStream.toByteArray());
				fileOutputStream.flush();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (fileOutputStream != null) {
				try {
					fileOutputStream.close();
				} catch (IOException ioe) {

				}
			}
		}
	}

	/**
	 *  初始化Excel输出流
	 */
	public static List<GRequestVO> initExport(String filePath, int maxLimit) throws Exception {
		FileInputStream fileInputStream = null;
		File file = null;
		// GFile.WriteStringToRight(GParam.TestCaseInputArrayFullName, InputMix +
		// "用例主要参数表\r\n");
		try {
			file = new File(filePath);
			fileInputStream = new FileInputStream(file);
			GRequestVO RequestVO = new GRequestVO();
			setFields(RequestVO);
			setHeader(RequestVO);
			@SuppressWarnings("unchecked")
			List<GRequestVO> list = (List<GRequestVO>) GExcel.read(fileInputStream, RequestVO.getHeaders(),
					RequestVO.getFields(), AutoTest.GRequestVO.class, maxLimit);
			List<GRequestVO> tmp = new ArrayList<GRequestVO>();
			for (int i = 0; i < list.size(); i++) {

				// 构造所有输出，一部分从输入表格读取或读取后组装，另一部分从TSRESULT_TSNO数组即执行结果数组中读取，保存到RequestVO中
				list.get(i).setResultCode("ResultCode:" + GError.TSRESULT_TSNO[i][0]);
				list.get(i).setResultMessage("ResultMessage:" + GError.TSRESULT_TSNO[i][1]);
				list.get(i).setIsPassed(GError.TSRESULT_TSNO[i][2]);
				list.get(i).setCaseKind(GError.TSRESULT_TSNO[i][3]);
				list.get(i).setCasePriority(GError.TSRESULT_TSNO[i][4]);
				InputMix = list.get(i).getUserName() + " " 
						+ list.get(i).getIdentType() + " "
						+ list.get(i).getIdentNo() + " ";
				OutputMix = list.get(i).getResultCode() + " " + list.get(i).getResultMessage();

				if (i > 0 && i < GParam.getTestCaseNum_MAX()) {
					GParam.TestCaseInputArray[i][0] = list.get(i).getCaseStyle();
					GParam.TestCaseInputArray[i][1] = list.get(i).getCaseTSNO();
					GParam.TestCaseInputArray[i][2] = list.get(i).getCaseEnvironment();
					GParam.TestCaseInputArray[i][3] = list.get(i).getUserName();
					GParam.TestCaseInputArray[i][4] = list.get(i).getIdentType();
					GParam.TestCaseInputArray[i][5] = list.get(i).getIdentNo();
				}

				list.get(i).setCaseStep(list.get(i).getCaseStep() + InputMix);
				list.get(i).setOutputMix("得到有效返回报文");
				list.get(i).setOutputMix1(OutputMix);
				list.get(i).setOutputMix2("与预期一致");
				list.get(i).setCaseKind("接口测试");

				System.out.println(InputMix);
				// GFile.WriteStringToRight(GParam.TestCaseInputArrayFullName, InputMix +
				// "\r\n");
				if (i > 0) {
					tmp.add(list.get(i));
				}
			}
			return tmp;
		} catch (Exception e) {
			throw e;
		} finally {
			if (fileInputStream != null) {
				try {
					fileInputStream.close();
				} catch (IOException ioe) {

				}
			}
		}
	}

	/**
	 *  读入Excel表格
	 */
	public static List<GRequestVO> read(String filePath, int maxLimit) throws Exception {
		FileInputStream fileInputStream = null;
		File file = null;
		// GFile.WriteStringToRight(GParam.TestCaseInputArrayFullName, InputMix +
		// "用例主要参数表\r\n");
		try {
			file = new File(filePath);
			fileInputStream = new FileInputStream(file);
			GRequestVO RequestVO = new GRequestVO();
			setFields(RequestVO);
			setHeader(RequestVO);
			@SuppressWarnings("unchecked")
			List<GRequestVO> list = (List<GRequestVO>) GExcel.read(fileInputStream, RequestVO.getHeaders(),
					RequestVO.getFields(), GRequestVO.class, maxLimit);
			List<GRequestVO> tmp = new ArrayList<GRequestVO>();
			for (int i = 0; i < list.size(); i++) {
				// list.get(i).setResultCode("ResultCode:"+GError.TSRESULT_TSNO[i][0]);
				// list.get(i).setResultMessage("ResultMessage:"+GError.TSRESULT_TSNO[i][1]);
				// list.get(i).setIsPassed(GError.TSRESULT_TSNO[i][2]);
				// list.get(i).setCaseKind(GError.TSRESULT_TSNO[i][3]);
				// list.get(i).setCasePriority(GError.TSRESULT_TSNO[i][4]);
				// InputMix = list.get(i).getUserName() + " " + list.get(i).getCredentialType()
				// + " " +list.get(i).getCredentialNo() + " " + list.get(i).getKeyID() + " "
				// +list.get(i).getCertTypeCode()
				// + " " + list.get(i).getCertType() + " " +list.get(i).getKeyAlg() + " " +
				// list.get(i).getKeyLength() + " " +list.get(i).getKeyLevel() + " " +
				// list.get(i).getCaEndTime()
				// + " " +list.get(i).getUserDn() + " " + list.get(i).getSerialNo()+ " "
				// +list.get(i).getAuthCode() + " " + list.get(i).getRefNo() + " "
				// +list.get(i).getCert2keyAlg()
				// + " " + list.get(i).getCert2keyLength() + " "
				// +list.get(i).getCert2keyLevel()+ " ";
				// OutputMix = list.get(i).getResultCode() + " " +
				// list.get(i).getResultMessage();

				// 读入所有核心参数，TestCaseInputArray即存错所有主要输入参数的数组
				if (i > 0 && i < GParam.getTestCaseNum_MAX()) {
					GParam.TestCaseInputArray[i][0] = list.get(i).getCaseStyle();
					GParam.TestCaseInputArray[i][1] = list.get(i).getCaseTSNO();
					GParam.TestCaseInputArray[i][2] = list.get(i).getCaseEnvironment();
					GParam.TestCaseInputArray[i][3] = list.get(i).getUserName();
					GParam.TestCaseInputArray[i][4] = list.get(i).getIdentType();
					GParam.TestCaseInputArray[i][5] = list.get(i).getIdentNo();
				}
				// list.get(i).setCaseStep(list.get(i).getCaseStep()+InputMix);
				// list.get(i).setOutputMix("得到有效返回报文");
				// list.get(i).setOutputMix1(OutputMix);
				// list.get(i).setOutputMix2("与预期一致");
				// list.get(i).setCaseKind("接口测试");

				System.out.println(InputMix);
				// GFile.WriteStringToRight(GParam.TestCaseInputArrayFullName, InputMix +
				// "\r\n");
				if (i > 0) {
					tmp.add(list.get(i));
				}
			}
			return tmp;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			if (fileInputStream != null) {
				try {
					fileInputStream.close();
				} catch (IOException ioe) {

				}
			}
		}
	}
	
	/**
	 *  获得表行数
	 */
	public static int getRowCourt() {
		FileInputStream fileInputStream = null;
		File file = null;
		try {
			if (!checkExcel())
				IsExcelReady = false;
			// 读excel
			inputList = read(GParam.TestCaseInputExcelFullName, maxLimit);
			file = new File(GParam.TestCaseInputExcelFullName);
			fileInputStream = new FileInputStream(file);
			GRequestVO RequestVO = new GRequestVO();
			setFields(RequestVO);
			setHeader(RequestVO);
			@SuppressWarnings("unchecked")
			List<GRequestVO> list = (List<GRequestVO>) GExcel.read(fileInputStream, RequestVO.getHeaders(),
					RequestVO.getFields(), GRequestVO.class, maxLimit);
			return list.size();
		} catch (Exception e) {
			return 0;
		} finally {
			if (fileInputStream != null) {
				try {
					fileInputStream.close();
				} catch (IOException ioe) {

				}
			}
		}
	}

	/**
	 *  获得表列数
	 */
	public static int getLieCourt() {
		return GParam.getTestParamNum_MAX();
	}
	
	/**
	 *  写入Excel表格
	 */
	public static void write() throws Exception {
		System.out.println("Must Be OverWritten !");
		throw new NullPointerException("Must Be OverWritten !");
	}

	/**
	 *  读入Excel表格
	 */
	public static void read() throws Exception {
		System.out.println("Must Be OverWritten !");
		throw new NullPointerException("Must Be OverWritten !");
	}

	/**
	 *  设置区域变量
	 */
	private static void setFields(GRequestVO GRequestVO) {
		String[] fields = { "indexNo", "systemModule", "functionPoint", "caseStyle", "caseTSNO", "caseScription", "prefixCondition",
				"caseStep", "caseEnvironment", "userName", "identType", "identNo" };
		GRequestVO.setFields(fields);
	}

	/**
	 *  设置区域提示文字
	 */
	private static void setHeader(GRequestVO GRequestVO) {
		String[] headers = { "序号", "系统模块", "功能点", "用例类型", "用例编号", "用例说明", "前置条件", "步骤描述", "测试环境类型", "用户名", "证件类型", "证件号码" };
		GRequestVO.setHeaders(headers);
	}

	/**
	 *  格式化区域文字
	 */
	private static String[] headers() {
		String[] headers = { "系统模块", "功能点", "用例类型", "用例编号", "用例说明", "前置条件", "步骤描述", "预期结果", "第一轮测试结果", "第二轮测试结果", "是否通过", "接口测试", "用例优先级", "备注" };
		return headers;
	}

	/**
	 *  格式化区域变量
	 */
	private static String[] fields() {
		String[] fields = { "systemModule", "functionPoint", "caseStyle", "caseTSNO", "caseScription", "prefixCondition",
				"caseStep", "outputMix", "outputMix1", "outputMix2", "isPassed", "isPassed", "casePriority", "caseMark" };
		return fields;
	}

	/**
	 *  输出用例输入缓存
	 */
	public static void RecordTestCaseInputArray() {
		int index = 0;
		for (int i = 0; i < GParam.getTestCaseNum_MAX(); i++) {
			for (int j = 0; j < GParam.getTestParamNum_MAX(); j++) {
				GFile.WriteStringToRight(GLog.LogStyle[4], GParam.TestCaseInputArray[i][j] + "  ");
				index++;
			}
			System.out.println("INIT TESTCASE:" + i + " TOTAL:" + index + "/" + GParam.TestCaseInputArray.length);
		}
	}

	/**
	 *  导入Excel表
	 */
	public boolean doImportExcel() {

		long startTime = System.currentTimeMillis();
		GFile.WriteStringToBottom(GLog.LogStyle[9], "\r\n" + GTime.getDate() + " TEST CASE INPUT START\r\n");
		GLog.GLogDoReady(GTime.getDate() + " [doImportExcel] START");

		try {
			if (!checkExcel())
				IsExcelReady = false;
			// 读excel
			inputList = read(GParam.TestCaseInputExcelFullName, maxLimit);

			// 记录读入的内容到制定文档
			// GLog.GLogDoReady("REDAY TO POST:"+inputList.toString());

			GLog.GLogDoReady(startTime, "doImportExcel");
			GFile.WriteStringToBottom(GLog.LogStyle[9], "\r\n" + GTime.getDate() + " TEST CASE INPUT READY" + "\r\n");

			IsExcelReady = true;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return IsExcelReady;
	}

	/**
	 *  导出Excel表
	 */
	public boolean doExportExcel() {

		long startTime = System.currentTimeMillis();
		GFile.WriteStringToBottom(GLog.LogStyle[9], "\r\n" + GTime.getDate() + " TEST CASE EXPORT START\r\n");
		GLog.GLogDoReady(GTime.getDate() + " [doExportExcel] START");

		try {
			// if(!checkExcel())IsExcelReady=false;
			IsExcelReadyToExport = true;

			// 准备要输出的值
			inputList = initExport(GParam.TestCaseInputExcelFullName, maxLimit);

			// 写excel
			write(inputList, GParam.TestCaseOutputExcelFullName);

			IsExcelReadyToExport = false;

			GLog.GLogDoReady(startTime, "doExportExcel");
			GFile.WriteStringToBottom(GLog.LogStyle[9],
					"\r\n" + GTime.getDate() + " TEST CASE EXPORT COMPELETE" + "\r\n");

			IsExcelReady = true;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return IsExcelReady;
	}
	
	// public static void main(String[] args) {
	// // 允许excel中最大条数
	// int maxLimit = 1000;
	//
	// // 待读取的excel文件路径
	// String filePath = "template.xls";
	//
	// // 生成excel的存放路径
	// String outputPath = "/writeTest.xls";
	// try {
	//
	// // 读excel
	// List<GRequestVO> list = read(filePath, maxLimit);
	//
	// //记录读入的内容到制定文档
	// RecordTestCaseInputArray();
	//
	// // 写excel
	// //write(list, outputPath);
	// } catch (Exception e) {
	// e.printStackTrace();
	// }
	// }
	// public static void main(String[] args) {
	// // TODO Auto-generated method stub
	// int[][] s =new int[5][6];
	// int m=1;
	// for(int i=0;i<5;i++){
	// for(int j=0;j<6;j++){
	// s[i][j]=m;
	// System.out.print(s[i][j]);
	// m++;
	// }
	// System.out.print("\r\n");
	// }
	// }
}
