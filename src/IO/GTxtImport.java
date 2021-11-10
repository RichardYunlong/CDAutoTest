package IO;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;

import Base.GFile;
import Base.GMsg;
import Base.GText;
import Base.GValue;
import DT.GLog;
import Sys.GPath;
import Sys.GSys;
import Test.GTCNO;

/**
 * Txt导入
 */
public class GTxtImport {
	
	/**
	 * 
	 */
	private GTxtImport(){
		GLog.logShowConsole("This is a tool class.");
	}

	/**
	 *  参数表缓存器
	 */
	private static String[][] inputList = null;
	
	/**
	 *  参数表单行缓存器
	 */
	private static String[] inputLine = null;
	
	/**
	 *  获取所有用例集合
	 */
	public static String[][] get() {
		return inputList;
	}
	
	/**
	 *  获得txt参数文件有效行数
	 *  
	 *  算法为：读出目标文件的总行数，但实际txt文本中的第一行，行号为“0”，实际txt文本中的第二行，行号为“1”，以此类推
	 *  如果GTestCase.TestInputBeginRowIndex的值为1，即“从行号为1的行开始读取”，则认为“行号为0”的行为无效行（有可能为字段名）
	 *  这样定义的目的是考虑到有可能实际的TXT文本中会有标题栏，与Excel输入表格的形式一致，所以做同一性处理
	 *  如果GTestCase.TestInputBeginRowIndex = 2，即从行号为2（实际文本中第3行）开始读取，则有效行数为“实际TXT文本总行数-2”
	 *  以此类推
	 *  
	 *  @return 读取的参数表行数
	 */
	public static int getRowCourt() {
		int txtLineNum = 0;
		GFile.deleteFile(GPath.INPUT_TXT_PATH + GMsg.MSG_FIlE_TIP[0] + GPath.INPUT_TXT_NAME);
		txtLineNum = GText.deleteBlankRow(GPath.INPUT_TXT_PATH + GPath.INPUT_TXT_NAME, GPath.INPUT_TXT_PATH + GMsg.MSG_FIlE_TIP[0] + GPath.INPUT_TXT_NAME);
		
		if(txtLineNum > GValue.CASE_NUM_MAX) {
			txtLineNum = GValue.CASE_NUM_MAX;
			GSys.logSys("INPUTS MORE THAN '" +GValue.CASE_NUM_MAX+ "' WHICH BE DEFINED IN CODE,ONLY RELOAD " + GValue.CASE_NUM_MAX + " INPUTS");
		}else if(txtLineNum < 1) {
			GSys.logErrorSys(GMsg.MSG_EMPTY[0]);
		}else {
			inputList = new String[txtLineNum][GValue.PARAM_NUM_MAX];
			
			for(int i=0;i<txtLineNum;i++) {
				for(int j=0;j<GValue.PARAM_NUM_MAX;j++) {
					inputList[i][j] = "";
				}
			}
		}
		
		return txtLineNum;
	}
	
	/**
	 *  按行读入Txt表格，每行按照指定字符分割后，每一段即为一个参数值
	 *  
	 *  @param txtPath 源文件全名
	 *  @param lineNo 行号 
	 *  @param tag 分隔符
	 *  @return 分割后的String[]
	 */
	public static String[] readline(String txtPath, long lineNo, String tag) {
		String line = "";
		String encoding = "UTF-8";
		File txtFile = new File(txtPath);
		
		try(InputStream in = new FileInputStream(txtFile);
			InputStreamReader read = new InputStreamReader(in, encoding);
			BufferedReader reader = new BufferedReader(read);) {
			int i = 0;
			while (i < lineNo) {
				line = reader.readLine();
				i++;
			}
			if(null != line) {
				inputLine = line.split(tag);
			}
			if (inputLine == null) {
				GSys.logErrorSys(GMsg.MSG_EXIST[1] + " OR " + GMsg.MSG_EMPTY[0]);
			}
		} catch (Exception e) {
			GSys.logErrorSys("readline[" + Arrays.toString(e.getStackTrace()) +"]");
		}
		
		return inputLine;
	}
	
	/**
	 *  导入TXT类型的参数表
	 *  
	 *  @param strPath 源文件全名
	 *  @return 读取成功返回true，否则返回false
	 */
	public static boolean doImportTxt(String strPath) {
		if(!strPath.equals(GPath.INPUT_TXT_PATH + GPath.INPUT_TXT_NAME)) {
			GSys.logSys("FILE PATH IS NOT LIKE INIT");
		}
		GSys.logSys("READ INPUTS[" + strPath + "]");
		int inputListLength = getRowCourt();//此处获取的为出标题栏外的有效行数
		GSys.logSys("THERE ARE " + String.valueOf(inputListLength) +" ROWS OF INPUTS,INCLUDE 1 ROWS WHICH IS RECORD BY FIELD NAME");
		if(inputListLength <= 0)return false;
		
		String[][] txt = GTCNO.getTCNO_STR().clone();
		for(int i = 0;i < inputListLength;i++) {
			readline(GPath.INPUT_TXT_PATH + GMsg.MSG_FIlE_TIP[0] + GPath.INPUT_TXT_NAME, (long)(i+1), ",");
			if(null != inputLine) {
				for(int j = 0;j < inputLine.length;j++) {
					if(j < inputLine.length) {
						txt[i][j] = inputLine[j];
					}
				}
			}
		}
		GTCNO.setTCNO_STR(txt);
		return true;
	}
}
