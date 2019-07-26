package AutoTest;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 *  加载TXT
 */
public class GImportTxt {
	private GImportTxt(){
		GLog.logShowConsole("This is a tool class.");
	}
	
	/**
	 *   配置单个用例参数个数最大值
	 */
	public static final int PARAM_NUM_MAX_TXT = 32;
	
	/**
	 *  外置参数文件保存路径
	 */
	private static final String INPUTPATH = "./input/";
	/**
	 *  外置TXT文件全名
	 */
	private static final String INPUTTXT = "testcase.txt";
	
	/**
	 *  用例输入表源文件全名
	 */
	private static String txtFilePath = "";
	
	/**
	 *  去掉空白行后的用例输入文件（此文件为程序最后调用的文件）
	 */
	private static String txtFilePathClear= "";
	
	/**
	 *  用例输入最大个数(txt文件行数)
	 */
	private static final int TXT_LINE_MAX = 250000;
	
	/**
	 *  用例输入单条记录的字段上线
	 */
	private static final int TXT_LINE_PARAMS_MAX = 32;
	
	/**
	 *  去掉空行后的txt文件行数
	 */
	private static int txtLineNum = 0;
	
	/**
	 *  获得用例总数
	 */
	public static int getTxtLineNum() {
		return txtLineNum;
	}
	
	/**
	 *  所有用例集合
	 */
	private static String[] inputLine = null;
	
	/**
	 *  所有用例集合
	 */
	private static String[][] inputList = null;
	
	/**
	 *  获取所有用例集合
	 */
	public static String[][] getTestCases() {
		return inputList;
	}
	
	/**
	 *  设置所有用例集合
	 *  
	 *  @param txtTestCases 形式数组
	 */
	public static void setTestCases(String[][] txtTestCases) {
		inputList = txtTestCases;
	}
	
	/**
	 *  初始化inputs集合
	 */
	private static void initInputList() {
		inputList = new String[txtLineNum][TXT_LINE_PARAMS_MAX];
		
		for(int i=0;i<txtLineNum;i++) {
			for(int j=0;j<TXT_LINE_PARAMS_MAX;j++) {
				inputList[i][j] = "";
			}
		}
	}
	
	/**
	 *  构造用例输入源文件路径
	 */
	private static void initTxtFilePath() {
		txtFilePath = INPUTPATH + INPUTTXT;
		txtFilePathClear= INPUTPATH + "NonBlank_" + INPUTTXT;
		GFile.deleteFile(txtFilePathClear);
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
	public static int getInputTxtRowCourt() {
		initTxtFilePath();
		txtLineNum = GText.deleteBlankLine(txtFilePath,txtFilePathClear);

		GSys.logSys("THERE ARE " + txtLineNum +" ROWS OF INPUTS");
		
		if(txtLineNum > TXT_LINE_MAX) {
			txtLineNum = TXT_LINE_MAX;
			GSys.logSys("INPUTS MORE THAN '" +TXT_LINE_MAX+ "' WHICH BE DEFINED IN CODE,ONLY RELOAD " + TXT_LINE_MAX + " INPUTS");
		}else if(txtLineNum < 1) {
			GSys.logErrorSys(GMsg.MSG_EMPTY[0]);
		}else {
			initInputList();
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
		InputStream in = null;
		InputStreamReader read = null;
		BufferedReader reader = null;
		try {
			File txtFile = new File(txtPath);
			in = new FileInputStream(txtFile);
			read = new InputStreamReader(in, encoding);
			reader = new BufferedReader(read);
			int i = 0;
			while (i < lineNo) {
				line = reader.readLine();
				i++;
			}
			reader.close();
			if(null != line) {
				inputLine = line.split(tag);
			}
			if (inputLine == null) {
				GSys.logErrorSys(GMsg.MSG_EXIST[1] + " OR " + GMsg.MSG_EMPTY[0]);
			}
			in.close();
			read.close();
			reader.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if(in != null)in.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			try {
				if(read != null)read.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			try {
				if(reader != null)reader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
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
		if(!strPath.equals(INPUTPATH + INPUTTXT)) {
			GSys.logSys("FILE PATH IS NOT LIKE INIT");
		}
		
		int inputListLength = getInputTxtRowCourt();//此处获取的为出标题栏外的有效行数
		if(inputListLength <= 0)return false;
		
		for(int i = 0;i < inputListLength;i++) {
			readline(txtFilePathClear, (long)(i+1), ",");
			if(null != inputLine) {
				//从第2个字段开始记录
				for(int j = 0;j < inputLine.length;j++) {
					if(j < inputLine.length-1) {
						GParam.strTestCaseInputArray[i][j] = inputLine[j+1];
					}
				}
			}
		}
		
		return true;
	}
}
