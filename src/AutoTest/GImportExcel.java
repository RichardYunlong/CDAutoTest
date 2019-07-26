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
	 *   配置单个用例参数个数最大值
	 */
	public static final int PARAM_NUM_MAX_EXCEL = 32;
	
	/**
	 *  外置参数文件保存路径
	 */
	public static final String INPUTPATH = "./input/";
	
	/**
	 *  外置XLS文件名
	 */
	public static final String INPUTXLS = "testcase.xls";

	/**
	 *  输入参数合集
	 */
	private static String strInputMix = "";

	/**
	 *  输出第一轮测试结果
	 */
	public static String strOutputMix = "";

	/**
	 *  用例条数上线
	 */
	public static int maxLimit = GParam.curCaseNumMAX;

	/**
	 *  用例存储列表
	 */
	public static List<GRequestVO> inputList = null;


	/**
	 *  存储用例的数组初始化
	 */
	public static void initTestCaseArray() {
		long startTime = System.currentTimeMillis();
		GLog.logDoReady(GTime.getDate() + "[doInitTestCaseArray] START");

		/* do something */

		GLog.logDoReady(startTime, "doInitTestCaseArray");
	}

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
				// 读入所有核心参数，TestCaseInputArray即存错所有主要输入参数的数组
				if (i > 0 && i < GParam.getTestCaseNumMAX()) {
					GParam.strTestCaseInputArray[i][0] = list.get(i).getSystemModule();
					GParam.strTestCaseInputArray[i][1] = list.get(i).getFunctionPoint();
					GParam.strTestCaseInputArray[i][2] = list.get(i).getCaseScription();
					GParam.strTestCaseInputArray[i][3] = list.get(i).getPrefixCondition();
					GParam.strTestCaseInputArray[i][4] = list.get(i).getCaseStep();
					GParam.strTestCaseInputArray[i][5] = list.get(i).getCaseEnvironment();
					GParam.strTestCaseInputArray[i][6] = list.get(i).getCaseStyle();
					GParam.strTestCaseInputArray[i][7] = list.get(i).getCaseTSNO();
					GParam.strTestCaseInputArray[i][8] = list.get(i).getUserName();
					GParam.strTestCaseInputArray[i][9] = list.get(i).getIdentType();
					GParam.strTestCaseInputArray[i][10] = list.get(i).getIdentNo();
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
			if (!GExcel.checkExcel(GParam.getTestCaseInputFullName()))
				GLog.logRecord(4, "INPUT XLS DOES NOT EXIST");
			// 读excel
			inputList = read(strPath, maxLimit);
			file = new File(strPath);
			fileInputStream = new FileInputStream(file);
			GRequestVO requestVO = new GRequestVO();
			setFields(requestVO);
			setHeader(requestVO);
			@SuppressWarnings("unchecked")
			List<GRequestVO> list = (List<GRequestVO>) GExcel.read(fileInputStream, requestVO.getHeaders(),
					requestVO.getFields(), GRequestVO.class, maxLimit);
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
	 *  获得表列数
	 *  
	 *  @return 读取到的数据列表行数
	 */
	public static int getLieCourt() {
		return GParam.getTestParamNumMAX();
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
	 *  读入Excel表格
	 */
	public static void read() {
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
		String[] fields = { "indexNo", "systemModule", "functionPoint", "caseScription", "prefixCondition",
							"caseStep", "caseEnvironment", "caseStyle", "caseTSNO", "userName", 
							"identType", "identNo" };
		requestVO.setFields(fields);
	}

	/**
	 *  设置区域提示文字
	 *  
	 *  @param requestVO 请求报文类
	 */
	private static void setHeader(GRequestVO requestVO) {
		String[] headers = { "序号", "系统模块", "功能点", "用例说明", "前置条件", 
							 "步骤描述", "测试环境类型", "用例类型", "用例编号", "用户名", 
							 "证件类型", "证件号码" };
		requestVO.setHeaders(headers);
	}

	/**
	 *  输出用例输入缓存
	 */
	public static void recordTestCaseInputArray() {
		int index = 0;
		for (int i = 0; i < GParam.getTestCaseNumMAX(); i++) {
			for (int j = 0; j < GParam.getTestParamNumMAX(); j++) {
				GFile.writeStringToRight(GLog.strLogStyle[4], GParam.strTestCaseInputArray[i][j] + "  ");
				index++;
			}
			GLog.logShowConsole("INIT TESTCASE:" + Integer.toString(i) + " TOTAL:" + Integer.toString(index) + "/" + GParam.strTestCaseInputArray.length);
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

			inputList = read(strPath, maxLimit);
		} catch (Exception e) {
			GSys.logSys("FAIL TO IMPORT XLS");
			e.printStackTrace();
		}

		return true;
	}
}
