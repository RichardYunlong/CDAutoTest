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
	 *  用例输入源文件名（不包含后缀）（此文件需要程序先做一定的处理后再调用）
	 */
	private static String txtFileName = "";
	
	/**
	 *  用例输入表源文件名后缀（仅为后缀）
	 */
	private static String txtFileType = "";
	
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
	private static int TXT_LINE_PARAMS_MAX = 32;
	
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
		txtFilePath = txtFileName + txtFileType;
		txtFilePath_Clean= txtFileName + "NonBlank" + txtFileType;
		GFile.deleteFile(txtFilePath_Clean);
	}
	
	/**
	 *  初始化txt参数文件
	 */
	public static int getTxtLineNum(String strName, String strType) {
		txtFileName = strName;
		txtFileType = strType;
		initTxtFilePath();
		txtLineNum = GText.DeleteBlankLine(txtFilePath,txtFilePath_Clean);;
		System.out.println(txtFilePath_Clean);
		GFile.WriteStringToBottom(GSys.Guide,
				"\r\nTHERE ARE " + txtLineNum +" PREERRORCODE\r\n");
		
		if(txtLineNum > TXT_LINE_MAX) {
			txtLineNum = TXT_LINE_MAX;
			GFile.WriteStringToBottom(GSys.Guide,
					"\r\nINPUTS MORE THAN '" +TXT_LINE_MAX+ "' WHICH BE DEFINED IN CODE,ONLY RELOAD " + TXT_LINE_MAX + " INPUTS\r\n");
		}else if(txtLineNum < 1) {
			System.out.println("FILE IS EMPTY");
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
			inputLine = line.split(tag);
			if (inputLine == null) {
				System.out.println("WRONG OR EMPTY PARAMS FILE");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return inputLine;
	}
	
	/**
	 *  导入TXT类型的参数表
	 */
	public static void ImportTxt(String strName, String strType) {
		for(int i = 0;i < getTxtLineNum(strName, strType);i++) {
			readline(strName + strType, i + 1, ",");
			for(int j = 0;j < inputLine.length;j++) {
				inputList[i][j] = inputLine[j];
			}
		}
	}
}
