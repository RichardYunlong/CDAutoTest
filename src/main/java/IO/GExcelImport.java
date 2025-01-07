package main.java.IO;

import main.java.Base.GClazz;
import main.java.Base.GFile;
import main.java.Base.GMsg;
import main.java.Base.GValue;
import main.java.DT.GLog;
import main.java.Test.GTestMission;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *  导入Excel
 */
public class GExcelImport {
	
	/**
	 * 构造函数 
	 */
	public GExcelImport(){
		GClazz.thisADataUnitClass();
	}
	
	/**
	 *  参数表缓存器
	 */
	private List<GXlsRequestVO> inputList = null;

	public List<GXlsRequestVO> getInputList() {
		return inputList;
	}

	public void setInputList(List<GXlsRequestVO> inputList) {
		this.inputList = inputList;
	}
	
	/**
	 *  测试步骤综述
	 */
	@SuppressWarnings({"CanBeFinal", "FieldMayBeFinal", "FieldCanBeLocal"})
    private String strInputMix = "";

	/**
	 *  输出第一轮测试结果综述
	 */
	private String strOutputMix = "";

	public String getStrOutputMix() {
		return strOutputMix;
	}

	public void setStrOutputMix(String strOutputMix) {
		this.strOutputMix = strOutputMix;
	}

	/**
	 *  读入Excel表格
	 *  
	 *  @param filePath 源文件全名
	 *  @param maxLimit 最大读取条数
	 *  
	 *  @return 读取到的数据列表
	 */
	public List<GXlsRequestVO> read(String filePath, int maxLimit) {
		List<GXlsRequestVO> tmp = null;
		
		File file = new File(filePath);
		String[][] xls = GTestMission.gtcno.getTCNO_STR().clone();
		try(FileInputStream fileInputStream = new FileInputStream(file)) {
			GXlsRequestVO requestVO = new GXlsRequestVO();
			setFields(requestVO);
			setHeader(requestVO);
			@SuppressWarnings("unchecked")
			List<GXlsRequestVO> list = (List<GXlsRequestVO>) GExcelBase.read(fileInputStream, requestVO.getHeaders(),
					requestVO.getFields(), GXlsRequestVO.class, maxLimit);
			tmp = new ArrayList<>();
			for (int i = 0; i < list.size(); i++) {
				// 读入所有核心参数，TestCaseInputArray保存所有主要输入参数的数组
				if (i < GTestMission.gTestProgress.getTotalNum()) {
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
			GTestMission.gtcno.setTCNO_STR(xls);
		} catch (Exception e) {
			GFile.writeStringErrorToGuideBottom("read[" + Arrays.toString(e.getStackTrace()) +"]");
		}
		
		return tmp;
	}
	
	/**
	 *  写入Excel表格
	 */
	public void write() {
		try {
			GLog.logRecord(4, GMsg.MSG_IOFAILED[3]);
		} catch (Exception e) {
			GLog.logRecord(9, GMsg.MSG_IOFAILED[3]);
			GFile.writeStringErrorToGuideBottom("write[" + Arrays.toString(e.getStackTrace()) +"]");
		}
	}
	
	/**
	 *  获得表行数
	 *  
	 *  @param strPath 源文件全名
	 *  @return 读取到的数据列表
	 */
	public int getRowCourt(String strPath) {
		File file;
		if (!GExcelBase.checkExcel(strPath))
			GLog.logRecord(4, "INPUT XLS DOES NOT EXIST");
		// 读excel
		setInputList(read(strPath, GValue.CASE_NUM_MAX));
		file = new File(strPath);
		
		try(FileInputStream fileInputStream = new FileInputStream(file)) {
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
	private void setFields(GXlsRequestVO requestVO) {
		String[] fields = { "indexNo", "systemModule", "functionPoint", "caseScription", "prefixCondition", "caseStep", 
				"caseEnvironment", "caseStyle", "caseTSNO", "userName", "identType", "identNo" };
		requestVO.setFields(fields);
	}

	/**
	 *  设置区域提示文字
	 *  
	 *  @param requestVO 请求报文类
	 */
	private void setHeader(GXlsRequestVO requestVO) {
		String[] headers = { "序号", "系统模块", "功能点", "用例说明", "前置条件", "步骤描述", 
				"测试环境类型", "用例类型", "用例编号", "用户名", "证件类型", "证件号码" };
		requestVO.setHeaders(headers);
	}

	/**
	 *  输出用例输入缓存
	 */
	public void doLogXls() {
		int index = 0;
		String[] logPath = GLog.getLogpath().clone();
		String[][] xls = GTestMission.gtcno.getTCNO_STR().clone();
		for (int i = 0; i < GTestMission.gTestProgress.getTotalNum(); i++) {
			for (int j = 0; j < GValue.PARAM_NUM_MAX; j++) {
				GFile.writeStringToRight(logPath[4], xls[i][j] + "  ");
				index++;
			}
			GLog.logShowConsole("INIT TESTCASE:" + Integer.valueOf(i).toString() + " TOTAL:" + index + "/" + xls.length);
		}
	}

	/**
	 *  导入Excel表
	 *  
	 *  @param strPath 源文件全名
	 *  
	 *  @return 读取成功则返回true，否则返回false
	 */
	public boolean doImportXls(String strPath) {
		try {
			if (!GExcelBase.checkExcel(strPath)) {
				GFile.writeStringToGuideBottom("INPUTS XLS IS NOT EXIST");
				return false;
			}
			GFile.writeStringToGuideBottom("READ INPUTS[" + strPath + "]");
			setInputList(read(strPath, GValue.CASE_NUM_MAX));
		} catch (Exception e) {
			GFile.writeStringToGuideBottom("FAIL TO IMPORT XLS");
			GFile.writeStringErrorToGuideBottom("doImportXls[" + Arrays.toString(e.getStackTrace()) +"]");
		}
		return true;
	}
}
