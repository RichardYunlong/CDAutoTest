package IO;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import Base.GFile;
import Base.GMsg;
import Base.GValue;
import DT.GLog;
import Sys.GSys;
import Test.GTestProgress;
import Test.GTCNO;

/**
 *  导入Excel
 */
public class GExcelImport {
	
	/**
	 * 构造函数 
	 */
	private GExcelImport(){
		GLog.logShowConsole("This is a tool class.");
	}
	
	/**
	 *  参数表缓存器
	 */
	private static List<GXlsRequestVO> inputList = null;

	public static List<GXlsRequestVO> getInputList() {
		return inputList;
	}

	public static void setInputList(List<GXlsRequestVO> inputList) {
		GExcelImport.inputList = inputList;
	}
	
	/**
	 *  测试步骤综述
	 */
	private static String strInputMix = "";

	/**
	 *  输出第一轮测试结果综述
	 */
	private static String strOutputMix = "";

	public static String getStrOutputMix() {
		return strOutputMix;
	}

	public static void setStrOutputMix(String strOutputMix) {
		GExcelImport.strOutputMix = strOutputMix;
	}

	/**
	 *  读入Excel表格
	 *  
	 *  @param filePath 源文件全名
	 *  @param maxLimit 最大读取条数
	 *  
	 *  @return 读取到的数据列表
	 */
	public static List<GXlsRequestVO> read(String filePath, int maxLimit) {
		List<GXlsRequestVO> tmp = null;
		
		File file = new File(filePath);
		String[][] xls = GTCNO.getTCNO_STR().clone();
		try(FileInputStream fileInputStream = new FileInputStream(file);) {
			GXlsRequestVO requestVO = new GXlsRequestVO();
			setFields(requestVO);
			setHeader(requestVO);
			@SuppressWarnings("unchecked")
			List<GXlsRequestVO> list = (List<GXlsRequestVO>) GExcelBase.read(fileInputStream, requestVO.getHeaders(),
					requestVO.getFields(), GXlsRequestVO.class, maxLimit);
			tmp = new ArrayList<GXlsRequestVO>();
			for (int i = 0; i < list.size(); i++) {
				// 读入所有核心参数，TestCaseInputArray保存所有主要输入参数的数组
				if (i >= 0 && i < GTestProgress.getTotalNum()) {
					xls[i][0] = list.get(i).getIndexNo();
					xls[i][1] = list.get(i).getSystemModule();
					xls[i][2] = list.get(i).getFunctionPoint();
					xls[i][3] = list.get(i).getCaseScription();
					xls[i][4] = list.get(i).getPrefixCondition();
					xls[i][5] = list.get(i).getCaseStep();
					xls[i][6] = list.get(i).getCaseEnvironment();
					xls[i][7] = list.get(i).getCaseStyle();
					xls[i][8] = list.get(i).getCaseTSNO();
					xls[i][9] = list.get(i).getUserName();
					xls[i][10] = list.get(i).getIdentType();
					xls[i][11] = list.get(i).getIdentNo();
				}

				GLog.logShowConsole(strInputMix);
				if (i > 0) {
					tmp.add(list.get(i));
				}
			}
			GTCNO.setTCNO_STR(xls);
		} catch (Exception e) {
			GSys.logErrorSys("read[" + Arrays.toString(e.getStackTrace()) +"]");
		}
		
		return tmp;
	}
	
	/**
	 *  写入Excel表格
	 */
	public static void write() {
		try {
			GLog.logRecord(4, GMsg.MSG_IOFAILED[3]);
		} catch (Exception e) {
			GLog.logRecord(9, GMsg.MSG_IOFAILED[3]);
			GSys.logErrorSys("write[" + Arrays.toString(e.getStackTrace()) +"]");
		}
	}
	
	/**
	 *  获得表行数
	 *  
	 *  @param strPath 源文件全名
	 *  @return 读取到的数据列表
	 */
	public static int getRowCourt(String strPath) {
		File file = null;
		if (!GExcelBase.checkExcel(strPath))
			GLog.logRecord(4, "INPUT XLS DOES NOT EXIST");
		// 读excel
		setInputList(read(strPath, GValue.CASE_NUM_MAX));
		file = new File(strPath);
		
		try(FileInputStream fileInputStream = new FileInputStream(file);) {
			GXlsRequestVO requestVO = new GXlsRequestVO();
			setFields(requestVO);
			setHeader(requestVO);
			@SuppressWarnings("unchecked")
			List<GXlsRequestVO> list = (List<GXlsRequestVO>) GExcelBase.read(fileInputStream, requestVO.getHeaders(),
					requestVO.getFields(), GXlsRequestVO.class, GValue.CASE_NUM_MAX);
			return list.size();
		} catch (Exception e) {
			return 0;
		}
	}

	/**
	 *  设置区域变量
	 *  
	 *  @param requestVO 请求报文类
	 */
	private static void setFields(GXlsRequestVO requestVO) {
		String[] fields = { "indexNo", "systemModule", "functionPoint", "caseScription", "prefixCondition", "caseStep", 
				"caseEnvironment", "caseStyle", "caseTSNO", "userName", "identType", "identNo" };
		requestVO.setFields(fields);
	}

	/**
	 *  设置区域提示文字
	 *  
	 *  @param requestVO 请求报文类
	 */
	private static void setHeader(GXlsRequestVO requestVO) {
		String[] headers = { "序号", "系统模块", "功能点", "用例说明", "前置条件", "步骤描述", 
				"测试环境类型", "用例类型", "用例编号", "用户名", "证件类型", "证件号码" };
		requestVO.setHeaders(headers);
	}

	/**
	 *  输出用例输入缓存
	 */
	public static void doLogXls() {
		int index = 0;
		String[] logPath = GLog.getLogpath().clone();
		String[][] xls = GTCNO.getTCNO_STR().clone();
		for (int i = 0; i < GTestProgress.getTotalNum(); i++) {
			for (int j = 0; j < GValue.PARAM_NUM_MAX; j++) {
				GFile.writeStringToRight(logPath[4], xls[i][j] + "  ");
				index++;
			}
			GLog.logShowConsole("INIT TESTCASE:" + Integer.toString(i) + " TOTAL:" + Integer.toString(index) + "/" + xls.length);
		}
	}

	/**
	 *  导入Excel表
	 *  
	 *  @param strPath 源文件全名
	 *  
	 *  @return 读取成功则返回true，否则返回false
	 */
	public static boolean doImportXls(String strPath) {
		try {
			if (!GExcelBase.checkExcel(strPath)) {
				GSys.logSys("INPUTS XLS IS NOT EXIST");
				return false;
			}
			GSys.logSys("READ INPUTS[" + strPath + "]");
			setInputList(read(strPath, GValue.CASE_NUM_MAX));
		} catch (Exception e) {
			GSys.logSys("FAIL TO IMPORT XLS");
			GSys.logErrorSys("doImportXls[" + Arrays.toString(e.getStackTrace()) +"]");
		}
		return true;
	}
}
