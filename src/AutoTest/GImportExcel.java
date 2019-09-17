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
	private GImportExcel(){
		GLog.logShowConsole("This is a tool class.");
	}

	/**
	 *  测试步骤综述
	 */
	private static String strInputMix = "";

	/**
	 *  输出第一轮测试结果综述
	 */
	public static String strOutputMix = "";

	/**
	 *  参数表缓存器
	 */
	public static List<GRequestVO> inputList = null;

	/**
	 *  读入Excel表格
	 *  
	 *  @param filePath 源文件全名
	 *  @param maxLimit 最大读取条数
	 *  @return 读取到的数据列表
	 */
	public static List<GRequestVO> read(String filePath, int maxLimit) {
		FileInputStream fileInputStream = null;
		File file = null;
		List<GRequestVO> tmp = null;
		try {
			file = new File(filePath);
			fileInputStream = new FileInputStream(file);
			GRequestVO requestVO = new GRequestVO();
			setFields(requestVO);
			setHeader(requestVO);
			@SuppressWarnings("unchecked")
			List<GRequestVO> list = (List<GRequestVO>) GExcel.read(fileInputStream, requestVO.getHeaders(),
					requestVO.getFields(), GRequestVO.class, maxLimit);
			tmp = new ArrayList<GRequestVO>();
			for (int i = 0; i < list.size(); i++) {
				// 读入所有核心参数，TestCaseInputArray保存所有主要输入参数的数组
				if (i >= 0 && i < GProgress.getTestTotalNo()) {
					GProgress.strTestCaseInputArray[i][0] = list.get(i).getIndexNo();
					GProgress.strTestCaseInputArray[i][1] = list.get(i).getSystemModule();
					GProgress.strTestCaseInputArray[i][2] = list.get(i).getFunctionPoint();
					GProgress.strTestCaseInputArray[i][3] = list.get(i).getCaseScription();
					GProgress.strTestCaseInputArray[i][4] = list.get(i).getPrefixCondition();
					GProgress.strTestCaseInputArray[i][5] = list.get(i).getCaseStep();
					GProgress.strTestCaseInputArray[i][6] = list.get(i).getCaseEnvironment();
					GProgress.strTestCaseInputArray[i][7] = list.get(i).getCaseStyle();
					GProgress.strTestCaseInputArray[i][8] = list.get(i).getCaseTSNO();
					GProgress.strTestCaseInputArray[i][9] = list.get(i).getUserName();
					GProgress.strTestCaseInputArray[i][10] = list.get(i).getIdentType();
					GProgress.strTestCaseInputArray[i][11] = list.get(i).getIdentNo();
				}

				GLog.logShowConsole(strInputMix);
				if (i > 0) {
					tmp.add(list.get(i));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (fileInputStream != null) {
				try {
					fileInputStream.close();
				} catch (IOException e) {
					GLog.logRecord(4, "IO ERROR");
					e.printStackTrace();
				}
			}
		}
		return tmp;
	}
	
	/**
	 *  获得表行数
	 *  
	 *  @param strPath 源文件全名
	 *  @return 读取到的数据列表
	 */
	public static int getInputXlsRowCourt(String strPath) {
		FileInputStream fileInputStream = null;
		File file = null;
		try {
			if (!GExcel.checkExcel(strPath))
				GLog.logRecord(4, "INPUT XLS DOES NOT EXIST");
			// 读excel
			inputList = read(strPath, GTestPlan.CASE_NUM_MAX);
			file = new File(strPath);
			fileInputStream = new FileInputStream(file);
			GRequestVO requestVO = new GRequestVO();
			setFields(requestVO);
			setHeader(requestVO);
			@SuppressWarnings("unchecked")
			List<GRequestVO> list = (List<GRequestVO>) GExcel.read(fileInputStream, requestVO.getHeaders(),
					requestVO.getFields(), GRequestVO.class, GTestPlan.CASE_NUM_MAX);
			return list.size();
		} catch (Exception e) {
			return 0;
		} finally {
			if (fileInputStream != null) {
				try {
					fileInputStream.close();
				} catch (IOException e) {
					GLog.logRecord(4, "IO ERROR");
					e.printStackTrace();
				}
			}
		}
	}
	
	/**
	 *  写入Excel表格
	 */
	public static void write() {
		try {
			GLog.logRecord(4, GMsg.MSG_IOFAILED[3]);
		} catch (Exception e) {
			GLog.logRecord(9, GMsg.MSG_IOFAILED[3]);
			e.printStackTrace();
		}
	}

	/**
	 *  设置区域变量
	 *  
	 *  @param requestVO 请求报文类
	 */
	private static void setFields(GRequestVO requestVO) {
		String[] fields = { "indexNo", "systemModule", "functionPoint", "caseScription", "prefixCondition", "caseStep", 
				"caseEnvironment", "caseStyle", "caseTSNO", "userName", "identType", "identNo" };
		requestVO.setFields(fields);
	}

	/**
	 *  设置区域提示文字
	 *  
	 *  @param requestVO 请求报文类
	 */
	private static void setHeader(GRequestVO requestVO) {
		String[] headers = { "序号", "系统模块", "功能点", "用例说明", "前置条件", "步骤描述", 
				"测试环境类型", "用例类型", "用例编号", "用户名", "证件类型", "证件号码" };
		requestVO.setHeaders(headers);
	}

	/**
	 *  输出用例输入缓存
	 */
	public static void recordTestCaseInputArray() {
		int index = 0;
		for (int i = 0; i < GProgress.getTestTotalNo(); i++) {
			for (int j = 0; j < GTestPlan.PARAM_NUM_MAX; j++) {
				GFile.writeStringToRight(GLog.strLogStyle[4], GProgress.strTestCaseInputArray[i][j] + "  ");
				index++;
			}
			GLog.logShowConsole("INIT TESTCASE:" + Integer.toString(i) + " TOTAL:" + Integer.toString(index) + "/" + GProgress.strTestCaseInputArray.length);
		}
	}

	/**
	 *  导入Excel表
	 *  
	 *  @param strPath 源文件全名
	 *  @return 读取成功则返回true，否则返回false
	 */
	public static boolean doImportExcel(String strPath) {
		try {
			if (!GExcel.checkExcel(strPath)) {
				GSys.logSys("INPUTS XLS IS NOT EXIST");
				return false;
			}

			inputList = read(strPath, GTestPlan.CASE_NUM_MAX);
		} catch (Exception e) {
			GSys.logSys("FAIL TO IMPORT XLS");
			e.printStackTrace();
		}

		return true;
	}
}
