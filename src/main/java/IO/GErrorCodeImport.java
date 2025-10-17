package IO;

import Base.*;
import DT.GLog;
import Sys.GPath;
import Sys.GStatic;
import Sys.GSys;
import Test.GTestMission;

/**
 *  加载错误码表
 */
public class GErrorCodeImport {

	/**
	 *  构造函数
	 */
	public GErrorCodeImport(){
		GClazz.thisADataUnitClass();
	}
	
	/**
	 *  错误码表源文文件路径
	 */
	public final String ERRORCODEPATH = GPath.INPUT_TEMP_PATH;
	
	/**
	 *  错误码表源文文件路径
	 */
	public final String ERRORCODEFILE = "errorcode.txt";
	
	/**
	 *  错误码表源文件全名
	 */
	private String strPreErrorCodeFilePath = "";
	
	/**
	 *  去掉空白行后的错误码表文件（此文件为程序最后调用的文件）
	 */
	private String strPreErrorCodeFileClear= "";
	
	/**
	 *  预置的错误码最大个数
	 */
	private final int PREERRORCODE_MAX= 1024;
	
	/**
	 *  预置的错误码表单条记录的字段上线（此处为2，即“错误码”和“错误信息”）
	 */
	@SuppressWarnings("FieldCanBeLocal")
    private final int PRERRORCODE_VALUE_MAX= 2;
	
	/**
	 *  去掉空行后的预置错误码表源文件行数
	 */
	private int dPreErrorCodeNum = 0;
	
	/**
	 *  预置的错误码集合
	 */
	private String[][] strPreErrorCodeContainer = new String[1024][2];
	
	/**
	 *  初始化错误码集合
	 */
	private void initPreErrorCodeContainer() {
		this.strPreErrorCodeContainer = new String[this.PREERRORCODE_MAX][this.PRERRORCODE_VALUE_MAX];
		
		for(int i=0;i<this.PREERRORCODE_MAX;i++) {
			for(int j=0;j<this.PRERRORCODE_VALUE_MAX;j++) {
				this.strPreErrorCodeContainer[i][j] = "";
			}
		}
	}
	
	/**
	 *  构造预置的错误码源文件路径
	 */
	private void initPreErrorCodeFilePath() {
		initPreErrorCodeContainer();
		this.strPreErrorCodeFilePath = this.ERRORCODEPATH + this.ERRORCODEFILE;
		this.strPreErrorCodeFileClear= this.ERRORCODEPATH + "NonBlank_" + this.ERRORCODEFILE;
		GFile.deleteFile(this.strPreErrorCodeFileClear);
	}
	
	/**
	 *  获取去掉空行后的预置错误码表源文件行数
	 *  
	 *  @return 返回已加载的错误码数量
	 */
	private int getPreErrorCodeNum() {
		initPreErrorCodeFilePath();
		if(GFile.judeFileExists(this.strPreErrorCodeFilePath)) {
			this.dPreErrorCodeNum = GText.deleteBlankRow(this.strPreErrorCodeFilePath, this.strPreErrorCodeFileClear);
			GLog.logShowConsole(strPreErrorCodeFileClear);
			GFile.writeStringToBottom(GSys.GUIDE,"THERE ARE " + this.dPreErrorCodeNum +" PREERRORCODE");
			
			if(this.dPreErrorCodeNum > this.PREERRORCODE_MAX) {
				this.dPreErrorCodeNum = this.PREERRORCODE_MAX;
				GFile.writeStringToBottom(GSys.GUIDE,"PREERRORCODE MORE THAN '" + this.PREERRORCODE_MAX+ "' WHICH BE DEFINED IN CODE,ONLY RELOAD 1024 REERRORCODE");
			}
		}
		
		return dPreErrorCodeNum;
	}
	
	/**
	 *  填装填预置的错误码集合 如果需要检查是否正确读入，则将读入结果打印至对应日志
	 */
	private void reloadPreErrorCodeContainer() {
		String[] logPath = GLog.getLogpath().clone();
		if (GTestMission.gTestPlan.isbIsBackup()) {
			GFile.writeStringToRight(logPath[4], "[PREERRORCODE]\r\n");
		}
		for(int i=0;i<this.dPreErrorCodeNum;i++) {
			String[] stError = new String[2]; 
			if(GText.readByRowIndexSplitByTag(this.strPreErrorCodeFileClear, i + 1, "=") != null)
				stError = GText.readByRowIndexSplitByTag(this.strPreErrorCodeFileClear, i + 1, "=");
			this.strPreErrorCodeContainer[i][0] = stError[0];
			this.strPreErrorCodeContainer[i][1] = stError[1];
			if(null != logPath && GTestMission.gTestPlan.isbIsBackup()) {
				GFile.writeStringToRight(logPath[4], "PREERRORCODE:" + this.strPreErrorCodeContainer[i][0] + " PREERRORMESSAGE:" + this.strPreErrorCodeContainer[i][1] + "\n");
			}
		}
	}
	
	/**
	 *  准备预置的错误码集合（需要预置错误码表时调用此方法）
	 */
	public void preErrorCode() {
		GFile.writeStringToBottom(GSys.GUIDE,GMissionMsg.getStepTop("REERRORCODES LOADING"));
		GFile.writeStringToBottom(GSys.GUIDE,GStatic.gSys.getDate() + " READ REERRORCODES");
		if(getPreErrorCodeNum() != 0) {
			reloadPreErrorCodeContainer();
			GFile.writeStringToBottom(GSys.GUIDE,GMissionMsg.getStepBottom("REERRORCODES COMPELETE"));
		}else {
			GFile.writeStringToGuideBottom(GMsg.MSG_NOTFOUND[0]);
		}
	}
	
	/**
	 *  准备预置的错误码集合（需要预置错误码表时调用此方法）
	 *  
	 *  @param assertIndex 形式比对值
	 */
	public void recordPreError(int assertIndex) {
			GLog.logRecord(5, 
				  "     CASE NUMBER:" + GTestMission.gTestCase.getTC_NO().toString()
				+ "     ERROR CODE:" + this.strPreErrorCodeContainer[assertIndex][0]
				+ "     ERROR MESSAGE:"+ this.strPreErrorCodeContainer[assertIndex][1]);
	}
}
