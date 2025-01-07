package Test;

import Base.GClazz;
import Base.GFile;
import Sys.GSys;

/**
 * 计划管理
 */
public class GTestPlan {
	
    /**
     *  构造函数
     */
	public GTestPlan(){
		GClazz.thisADataUnitClass();
	}
	
	/**
	 *  可以设置将读入的参数表打印只特定的日志文档的条数，此项数字越大，执行速度越慢
	 */
	private int recordInputParamListInTxt = 0;
	public int getRecordInputParamListInTxt() { return recordInputParamListInTxt; }
	public void setRecordInputParamListInTxt(int recordInputParamListInTxt) { this.recordInputParamListInTxt = recordInputParamListInTxt;}
	
	/**
	 *  用例之间的时间间隔
	 */
	private int timeWait = 0;
	public int getTimeWait() {
		return timeWait;
	}
	public void setTimeWait(int timeWait) {
		this.timeWait = timeWait;
	}
	
	/**
	 *  是否启用备份
	 */
	private boolean bIsBackup = false;
	public boolean isbIsBackup() {
		return bIsBackup;
	}
	public void setbIsBackup(boolean bIsBackup) {
		this.bIsBackup = bIsBackup;
	}

	/**
	 *  计划用例总分类标记
	 */
	private String testCaseType = "";
	public String getTestCaseType() {
		return testCaseType;
	}
	public void setTestCaseType(String testCaseType) {
		this.testCaseType = testCaseType;
	}

	/**
	 *  计划用例总数
	 */
	private int testCaseNum = 0;
	public int getTestCaseNum() {
		return testCaseNum;
	}
	public void setTestCaseNum(int testCaseNum) {
		this.testCaseNum = testCaseNum;
	}

	/**
	 *  计划执行有效类个数
	 */
	private Integer testReal = 0;
	public Integer getTestReal() {
		return testReal;
	}
	public void setTestReal(Integer testReal) {
		this.testReal = testReal;
	}
	
	/**
	 *  计划执行失败类个数
	 */
	private Integer testFail = 0;
	public Integer getTestFail() {
		return testFail;
	}
	public void setTestFail(Integer testFail) {
		this.testFail = testFail;
	}
	
	/**
	 * 计划执行异常场景类个数
	 */
	private Integer testUnReal = 0;
	public Integer getTestUnReal() {
		return testUnReal;
	}
	public void setTestUnReal(Integer testUnReal) {
		this.testUnReal = testUnReal;
	}
	
	/**
	 *  计划执行中断类个数
	 */
	private Integer testUnDo = 0;
	public Integer getTestUnDo() {
		return testUnDo;
	}
	public void setTestUnDo(Integer testUnDo) {
		this.testUnDo = testUnDo;
	}
	
	/**
	 *  加载参数
	 */	
	public void loadConfig() {
		if(recordInputParamListInTxt >= 0){
			GFile.writeStringToGuideBottom("GTestPlan Params Loaded");
		}else {
			GFile.writeStringToBottom(GSys.GUIDE,"ERROR----" +"One of these GTestPlan params from sysConfig may has no value, Please check again!");
			System.exit(0);
		}
	}

	/**
	 *  根据用例类型编码增加对应计数
	 *
	 * @param strType 例类型编码
	 */
	public void TestPlanNumPlus(String strType) {
		int dType = Integer.parseInt(strType);

		switch(dType) {
			case 0:{
				testReal++;
				break;
			}
			case 2:{
				testUnReal++;
				break;
			}
			case 3:{
				testUnDo++;
				break;
			}
			default:{
				testFail++;
				break;
			}
		}	
	}
}
