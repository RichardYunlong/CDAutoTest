package AutoTest;

/**
 * @see 预置的错误码集合
 */
public class GPreErrorCode {
	/**
	 * @see 错误码表源文件名（不包含后缀）（此文件需要程序先做一定的处理后再调用）
	 */
	private static String PreErrorCodeFileName = "";
	
	/**
	 * @see 错误码表源文件名后缀（仅为后缀）
	 */
	private static String PreErrorCodeFileType = "";
	
	/**
	 * @see 错误码表源文件全名
	 */
	private static String PreErrorCodeFilePath = "";
	
	/**
	 * @see 去掉空白行后的错误码表文件（此文件为程序最后调用的文件）
	 */
	private static String PreErrorCodeFile_Clean= "";
	
	/**
	 * @see 预置的错误码最大个数
	 */
	private static int PREERRORCODE_MAX= 1024;
	
	/**
	 * @see 预置的错误码表单条记录的字段上线（此处为2，即“错误码”和“错误信息”）
	 */
	private static int PRERRORCODE_VALUE_MAX= 2;
	
	/**
	 * @see 去掉空行后的预置错误码表源文件行数
	 */
	private static int PreErrorCodeNum = 0;
	
	/**
	 * @see 预置的错误码集合
	 */
	private static String[][] PreErrorCodeContainer = new String[1024][2];
	
	/**
	 * @see 初始化错误码集合
	 */
	private static void initPreErrorCodeContainer() {
		PreErrorCodeContainer = new String[PREERRORCODE_MAX][PRERRORCODE_VALUE_MAX];
		
		for(int i=0;i<PREERRORCODE_MAX;i++) {
			for(int j=0;j<PRERRORCODE_VALUE_MAX;j++) {
				PreErrorCodeContainer[i][j] = "";
			}
		}
	}
	
	/**
	 * @see 构造预置的错误码源文件路径
	 */
	private static void initPreErrorCodeFilePath() {
		initPreErrorCodeContainer();
		PreErrorCodeFilePath = PreErrorCodeFileName + PreErrorCodeFileType;
		PreErrorCodeFile_Clean= PreErrorCodeFileName + "NonBlank" + PreErrorCodeFileType;
		GFile.deleteFile(PreErrorCodeFile_Clean);
	}
	
	/**
	 * @see 获取去掉空行后的预置错误码表源文件行数
	 */
	private static int getPreErrorCodeNum(String strName, String strType) {
		PreErrorCodeFileName = strName;
		PreErrorCodeFileType = strType;
		initPreErrorCodeFilePath();
		PreErrorCodeNum = GText.DeleteBlankLine(PreErrorCodeFilePath,PreErrorCodeFile_Clean);
		System.out.println(PreErrorCodeFile_Clean);
		GFile.WriteStringToBottom(GSys.Guide,
				"\r\nTHERE ARE " + PreErrorCodeNum +" PREERRORCODE\r\n");
		
		if(PreErrorCodeNum > PREERRORCODE_MAX) {
			PreErrorCodeNum = PREERRORCODE_MAX;
			GFile.WriteStringToBottom(GSys.Guide,
					"\r\nPREERRORCODE MORE THAN '" +PREERRORCODE_MAX+ "' WHICH BE DEFINED IN CODE,ONLY RELOAD 1024 REERRORCODE\r\n");
		}
		
		return PreErrorCodeNum;
	}
	
	/**
	 * @see 填装填预置的错误码集合
	 */
	private static void reloadPreErrorCodeContainer() {
		GFile.WriteStringToRight(GLog.LogStyle[4], "\r\nRELOADED PREERRORCODES\r\n");
		for(int i=0;i<PreErrorCodeNum;i++) {
			String[] stError = new String[2]; 
			if(GText.ReadTxtLineSplitByTag(PreErrorCodeFile_Clean, Long.valueOf(i), "=") != null)
				stError = GText.ReadTxtLineSplitByTag(PreErrorCodeFile_Clean, Long.valueOf(i+1), "=");
			PreErrorCodeContainer[i][0] = stError[0];
			PreErrorCodeContainer[i][1] = stError[1];
			GFile.WriteStringToRight(GLog.LogStyle[4], "PREERRORCODE:" + PreErrorCodeContainer[i][0] + " PREERRORMESSAGE:" + PreErrorCodeContainer[i][1] + "\n");
		}
	}
	
	/**
	 * @see 准备预置的错误码集合（需要预置错误码表时调用此方法）
	 */
	public static void PreErrorCode(String strName, String strType) {
		if(getPreErrorCodeNum(strName, strType) != 0) {
			reloadPreErrorCodeContainer();
		}else {
			GFile.WriteStringToBottom(GSys.Guide,
					"\r\nNON REERRORCODES\r\n");
		}
//		for(int i=0;i<PREERRORCODE_MAX;i++) {
//				if(PreErrorCodeContainer[i][0] != "" && PreErrorCodeContainer[i][1] != "") {
//					System.out.print(PreErrorCodeContainer[i][0]);
//					System.out.println(PreErrorCodeContainer[i][1]);
//				}
//		}
	}
	
	/**
	 * @see 准备预置的错误码集合（需要预置错误码表时调用此方法）
	 */
	public static void RecordPreError(int AssertIndex) {
		GTestCase.RecordErrorCode(GTestCase.TSNO.toString(), PreErrorCodeContainer[AssertIndex][0], PreErrorCodeContainer[AssertIndex][1]);
	}
}