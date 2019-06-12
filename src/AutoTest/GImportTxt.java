package AutoTest;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 *  加载TXT
 */
public class GImportTxt {
	/**
	 *  外置参数文件保存路径
	 */
	public static final String INPUTPATH = "./input/";
	/**
	 *  外置TXT文件全名
	 */
	public static final String INPUTTXT = "testcase.txt";
	
	/**
	 *  用例输入表源文件全名
	 */
	private static String txtFilePath = "";
	
	/**
	 *  去掉空白行后的用例输入文件（此文件为程序最后调用的文件）
	 */
	private static String txtFilePath_Clean= "";
	
	/**
	 *  用例输入最大个数(txt文件行数)
	 */
	private static int TXT_LINE_MAX = 250000;
	
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
		txtFilePath_Clean= INPUTPATH + "NonBlank_" + INPUTTXT;
		GFile.deleteFile(txtFilePath_Clean);
	}
	
	/**
	 *  获得txt参数文件有效行数
	 *  
	 *  算法为：读出目标文件的总行数，但实际txt文本中的第一行，行号为“0”，实际txt文本中的第二行，行号为“1”，以此类推
	 *  如果GTestCase.TestInputBeginRowIndex的值为1，即“从行号为1的行开始读取”，则认为“行号为0”的行为无效行（有可能为字段名）
	 *  这样定义的目的是考虑到有可能实际的TXT文本中会有标题栏，与Excel输入表格的形式一致，所以做同一性处理
	 *  如果GTestCase.TestInputBeginRowIndex = 2，即从行号为2（实际文本中第3行）开始读取，则有效行数为“实际TXT文本总行数-2”
	 *  以此类推
	 */
	public static int getTxtLineNumByInputFile() {
		initTxtFilePath();
		txtLineNum = GText.DeleteBlankLine(txtFilePath,txtFilePath_Clean) - GTestCase.TestInputBeginRowIndex;

		GFile.WriteStringToBottom(GSys.Guide, "\r\nTHERE ARE " + txtLineNum +" ROWS OF INPUTS\r\n");
		
		if(txtLineNum > TXT_LINE_MAX) {
			txtLineNum = TXT_LINE_MAX;
			GFile.WriteStringToBottom(GSys.Guide,
					"\r\nINPUTS MORE THAN '" +TXT_LINE_MAX+ "' WHICH BE DEFINED IN CODE,ONLY RELOAD " + TXT_LINE_MAX + " INPUTS\r\n");
		}else if(txtLineNum < 1) {
			GFile.WriteStringToBottom(GSys.Guide, "FILE IS EMPTY");
		}else {
			initInputList();
		}
		
		return txtLineNum;
	}
	
	/**
	 *  按行读入Txt表格，每行按照指定字符分割后，每一段即为一个参数值
	 */
	public static String[] readline(String txtPath, long lineNo, String tag) {
		String line = "";
		String encoding = "UTF-8";
		try {
			File txtFile = new File(txtPath);
			InputStream in = new FileInputStream(txtFile);
			InputStreamReader read = new InputStreamReader(in, encoding);
			BufferedReader reader = new BufferedReader(read);
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
				GFile.WriteStringToBottom(GSys.Guide, "WRONG OR EMPTY PARAMS FILE");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return inputLine;
	}
	
	/**
	 *  导入TXT类型的参数表
	 */
	public static void doImportTxt() {
		int inputListLength = getTxtLineNumByInputFile();//此处获取的为出标题栏外的有效行数
		for(int i = 0;i < inputListLength;i++) {
			//从第二行开始读入，第一行为注释行
			readline(txtFilePath_Clean, i + 1 + GTestCase.TestInputBeginRowIndex, ",");
			if(null != inputLine) {
				//从第三个字段开始记录
				for(int j = 0;j < inputLine.length;j++) {
					if((j+GTestCase.TestInputBeginColumnIndex) < inputLine.length) {
						inputList[i][j] = inputLine[j+GTestCase.TestInputBeginColumnIndex];
					}
				}
			}
		}
	}
}
