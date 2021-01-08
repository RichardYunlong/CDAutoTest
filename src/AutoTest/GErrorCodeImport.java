package AutoTest;

/**
 *  加载错误码表
 */
public class GErrorCodeImport {
	private GErrorCodeImport(){
		GLog.logShowConsole("This is a tool class.");
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
	private static String strPreErrorCodeFilePath = "";
	
	/**
	 *  去掉空白行后的错误码表文件（此文件为程序最后调用的文件）
	 */
	private static String strPreErrorCodeFileClear= "";
	
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
	private static int dPreErrorCodeNum = 0;
	
	/**
	 *  预置的错误码集合
	 */
	private static String[][] strPreErrorCodeContainer = new String[1024][2];
	
	/**
	 *  初始化错误码集合
	 */
	private static void initPreErrorCodeContainer() {
		strPreErrorCodeContainer = new String[PREERRORCODE_MAX][PRERRORCODE_VALUE_MAX];
		
		for(int i=0;i<PREERRORCODE_MAX;i++) {
			for(int j=0;j<PRERRORCODE_VALUE_MAX;j++) {
				strPreErrorCodeContainer[i][j] = "";
			}
		}
	}
	
	/**
	 *  构造预置的错误码源文件路径
	 */
	private static void initPreErrorCodeFilePath() {
		initPreErrorCodeContainer();
		strPreErrorCodeFilePath = ERRORCODEPATH + ERRORCODEFILE;
		strPreErrorCodeFileClear= ERRORCODEPATH + "NonBlank_" + ERRORCODEFILE;
		GFile.deleteFile(strPreErrorCodeFileClear);
	}
	
	/**
	 *  获取去掉空行后的预置错误码表源文件行数
	 *  
	 *  @return 返回已加载的错误码数量
	 */
	private static int getPreErrorCodeNum() {
		initPreErrorCodeFilePath();
		if(GFile.judeFileExists(strPreErrorCodeFilePath)) {
			dPreErrorCodeNum = GText.deleteBlankLine(strPreErrorCodeFilePath,strPreErrorCodeFileClear);
			GLog.logShowConsole(strPreErrorCodeFileClear);
			GSys.logSys("THERE ARE " + dPreErrorCodeNum +" PREERRORCODE");
			
			if(dPreErrorCodeNum > PREERRORCODE_MAX) {
				dPreErrorCodeNum = PREERRORCODE_MAX;
				GSys.logSys("PREERRORCODE MORE THAN '" +PREERRORCODE_MAX+ "' WHICH BE DEFINED IN CODE,ONLY RELOAD 1024 REERRORCODE");
			}
		}
		
		return dPreErrorCodeNum;
	}
	
	/**
	 *  填装填预置的错误码集合 如果需要检查是否正确读入，则将读入结果打印至对应日志
	 */
	private static void reloadPreErrorCodeContainer() {
		if (GTestPlan.bTestOutputBackupResult) {
			GFile.writeStringToRight(GLog.strLogStyle[4], "[PREERRORCODE]\r\n");
		}
		for(int i=0;i<dPreErrorCodeNum;i++) {
			String[] stError = new String[2]; 
			if(GText.readTxtLineSplitByTag(strPreErrorCodeFileClear, (long)(i + 1), "=") != null)
				stError = GText.readTxtLineSplitByTag(strPreErrorCodeFileClear, (long)(i+1), "=");
			strPreErrorCodeContainer[i][0] = stError[0];
			strPreErrorCodeContainer[i][1] = stError[1];
			if(null != GLog.strLogStyle && GTestPlan.bTestOutputBackupResult) {
				GFile.writeStringToRight(GLog.strLogStyle[4], "PREERRORCODE:" + strPreErrorCodeContainer[i][0] + " PREERRORMESSAGE:" + strPreErrorCodeContainer[i][1] + "\n");
			}
		}
	}
	
	/**
	 *  准备预置的错误码集合（需要预置错误码表时调用此方法）
	 */
	public static void preErrorCode() {
		GSys.logSys("LOAD REERRORCODES START");
		if(getPreErrorCodeNum() != 0) {
			reloadPreErrorCodeContainer();
			GSys.logSys("LOAD REERRORCODES READY");
		}else {
			GSys.logErrorSys(GMsg.MSG_NOTFOUND[0]);
		}
	}
	
	/**
	 *  准备预置的错误码集合（需要预置错误码表时调用此方法）
	 *  
	 *  @param assertIndex 形式比对值
	 */
	public static void recordPreError(int assertIndex) {
			GLog.logRecord(5, 
				  "     CASE NUMBER:" + GTestCase.TC_NO.toString()
				+ "     ERROR CODE:" + strPreErrorCodeContainer[assertIndex][0] 
				+ "     ERROR MESSAGE:"+ strPreErrorCodeContainer[assertIndex][1]);
	}
}
