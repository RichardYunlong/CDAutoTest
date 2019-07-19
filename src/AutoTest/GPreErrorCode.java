package AutoTest;

/**
 *  预置错误码
 */
public class GPreErrorCode {
	private GPreErrorCode(){
		System.out.println("This is a tool class.");
	}
	
	/**
	 *  错误码表源文文件路径
	 */
	public static final String ERRORCODEPATH = "./input/";
	
	/**
	 *  错误码表源文文件路径
	 */
	public static final String ERRORCODEFILE = "errorcode.txt";
	
	/**
	 *  错误码表源文件全名
	 */
	private static String PreErrorCodeFilePath = "";
	
	/**
	 *  去掉空白行后的错误码表文件（此文件为程序最后调用的文件）
	 */
	private static String PreErrorCodeFile_Clean= "";
	
	/**
	 *  预置的错误码最大个数
	 */
	private static final int PREERRORCODE_MAX= 1024;
	
	/**
	 *  预置的错误码表单条记录的字段上线（此处为2，即“错误码”和“错误信息”）
	 */
	private static final int PRERRORCODE_VALUE_MAX= 2;
	
	/**
	 *  去掉空行后的预置错误码表源文件行数
	 */
	private static int PreErrorCodeNum = 0;
	
	/**
	 *  预置的错误码集合
	 */
	private static String[][] PreErrorCodeContainer = new String[1024][2];
	
	/**
	 *  初始化错误码集合
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
	 *  构造预置的错误码源文件路径
	 */
	private static void initPreErrorCodeFilePath() {
		initPreErrorCodeContainer();
		PreErrorCodeFilePath = ERRORCODEPATH + ERRORCODEFILE;
		PreErrorCodeFile_Clean= ERRORCODEPATH + "NonBlank_" + ERRORCODEFILE;
		GFile.deleteFile(PreErrorCodeFile_Clean);
	}
	
	/**
	 *  获取去掉空行后的预置错误码表源文件行数
	 */
	private static int getPreErrorCodeNum() {
		initPreErrorCodeFilePath();
		if(GFile.judeFileExists(PreErrorCodeFilePath)) {
			PreErrorCodeNum = GText.DeleteBlankLine(PreErrorCodeFilePath,PreErrorCodeFile_Clean);
			System.out.println(PreErrorCodeFile_Clean);
			GSys.GLogSys("THERE ARE " + PreErrorCodeNum +" PREERRORCODE");
			
			if(PreErrorCodeNum > PREERRORCODE_MAX) {
				PreErrorCodeNum = PREERRORCODE_MAX;
				GSys.GLogSys("PREERRORCODE MORE THAN '" +PREERRORCODE_MAX+ "' WHICH BE DEFINED IN CODE,ONLY RELOAD 1024 REERRORCODE");
			}
		}
		
		return PreErrorCodeNum;
	}
	
	/**
	 *  填装填预置的错误码集合
	 */
	private static void reloadPreErrorCodeContainer() {
		for(int i=0;i<PreErrorCodeNum;i++) {
			String[] stError = new String[2]; 
			if(GText.ReadTxtLineSplitByTag(PreErrorCodeFile_Clean, (long)(i + 1), "=") != null)
				stError = GText.ReadTxtLineSplitByTag(PreErrorCodeFile_Clean, (long)(i+1), "=");
			PreErrorCodeContainer[i][0] = stError[0];
			PreErrorCodeContainer[i][1] = stError[1];
			GFile.WriteStringToRight(GLog.LogStyle[4], "PREERRORCODE:" + PreErrorCodeContainer[i][0] + " PREERRORMESSAGE:" + PreErrorCodeContainer[i][1] + "\n");
		}
	}
	
	/**
	 *  准备预置的错误码集合（需要预置错误码表时调用此方法）
	 */
	public static void PreErrorCode() {
		GSys.GLogSys("LOAD REERRORCODES START");
		if(getPreErrorCodeNum() != 0) {
			reloadPreErrorCodeContainer();
			GSys.GLogSys("LOAD REERRORCODES READY");
		}else {
			GSys.GLogErrorSys(GMsg.MSG_NOTFOUND[0]);
		}
	}
	
	/**
	 *  准备预置的错误码集合（需要预置错误码表时调用此方法）
	 */
	public static void RecordPreError(int AssertIndex) {
			GLog.GLogRecord(5, 
				  "     CASE NUMBER:" + GTestCase.TSNO.toString()
				+ "     ERROR CODE:" + PreErrorCodeContainer[AssertIndex][0] 
				+ "     ERROR MESSAGE:"+ PreErrorCodeContainer[AssertIndex][1]);
	}
}
