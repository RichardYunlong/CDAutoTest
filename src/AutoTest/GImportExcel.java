package AutoTest;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 *  加载Excel
 */
public class GImportExcel {
	/**
	 *   配置单个用例参数个数最大值
	 */
	public static final int PARAM_NUM_MAX_EXCEL = 32;
	
	/**
	 *  外置参数文件保存路径
	 */
	public static String INPUTPATH = "./input/";
	
	/**
	 *  外置XLS文件名
	 */
	public static String INPUTXLS = "testcase.xls";

	/**
	 *  输入参数合集
	 */
	private static String InputMix = "";

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
	 *  存储用例的数组初始化
	 */
	public static void initTestCaseArray() {
		long startTime = System.currentTimeMillis();
		GLog.GLogDoReady(GTime.getDate() + "[doInitTestCaseArray] START");

		/* do something */

		GLog.GLogDoReady(startTime, "doInitTestCaseArray");
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
				// 读入所有核心参数，TestCaseInputArray即存错所有主要输入参数的数组
				if (i > 0 && i < GParam.getTestCaseNum_MAX()) {
					GParam.TestCaseInputArray[i][0] = list.get(i).getSystemModule();
					GParam.TestCaseInputArray[i][1] = list.get(i).getFunctionPoint();
					GParam.TestCaseInputArray[i][2] = list.get(i).getCaseScription();
					GParam.TestCaseInputArray[i][3] = list.get(i).getPrefixCondition();
					GParam.TestCaseInputArray[i][4] = list.get(i).getCaseStep();
					GParam.TestCaseInputArray[i][5] = list.get(i).getCaseEnvironment();
					GParam.TestCaseInputArray[i][6] = list.get(i).getCaseStyle();
					GParam.TestCaseInputArray[i][7] = list.get(i).getCaseTSNO();
					GParam.TestCaseInputArray[i][8] = list.get(i).getUserName();
					GParam.TestCaseInputArray[i][9] = list.get(i).getIdentType();
					GParam.TestCaseInputArray[i][10] = list.get(i).getIdentNo();
				}

				System.out.println(InputMix);
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
	public static int getInputXlsRowCourt(String strPath) {
		FileInputStream fileInputStream = null;
		File file = null;
		try {
			if (!GExcel.checkExcel(GParam.getTestCaseInputFullName()))
				GFile.WriteStringToBottom(GLog.LogStyle[4], "INPUT XLS DOES NOT EXIST");
			// 读excel
			inputList = read(strPath, maxLimit);
			file = new File(strPath);
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
		GFile.WriteStringToBottom(GLog.LogStyle[4], "NUST BE OVERWRITTEN");
		throw new NullPointerException("NUST BE OVERWRITTEN");
	}

	/**
	 *  读入Excel表格
	 */
	public static void read() throws Exception {
		GFile.WriteStringToBottom(GLog.LogStyle[4], "NUST BE OVERWRITTEN");
		throw new NullPointerException("NUST BE OVERWRITTEN");
	}

	/**
	 *  设置区域变量
	 */
	private static void setFields(GRequestVO GRequestVO) {
		String[] fields = { "indexNo", "systemModule", "functionPoint", "caseScription", "prefixCondition",
							"caseStep", "caseEnvironment", "caseStyle", "caseTSNO", "userName", 
							"identType", "identNo" };
		GRequestVO.setFields(fields);
	}

	/**
	 *  设置区域提示文字
	 */
	private static void setHeader(GRequestVO GRequestVO) {
		String[] headers = { "序号", "系统模块", "功能点", "用例说明", "前置条件", 
							 "步骤描述", "测试环境类型", "用例类型", "用例编号", "用户名", 
							 "证件类型", "证件号码" };
		GRequestVO.setHeaders(headers);
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
	public static boolean doImportExcel(String StrPath) {
		try {
			if (!GExcel.checkExcel(StrPath)) {
				GFile.WriteStringToBottom(GSys.Guide, "\r\nINPUTS XLS IS NOT EXIST\r\n");
				return false;
			}

			inputList = read(StrPath, maxLimit);
		} catch (Exception e) {
			GFile.WriteStringToBottom(GSys.Guide, "\r\nFAIL TO IMPORT XLS\r\n");
			e.printStackTrace();
		}

		return true;
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
