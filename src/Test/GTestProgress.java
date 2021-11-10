package Test;

import DT.GLog;

/**
 *  进度
 *  
 *  按照任务执行数量维度执行进度管理
 */
public class GTestProgress {
	
	private GTestProgress(){
		GLog.logShowConsole("This is a tool class.");
	}
	
	/**
	 *  使用某种用例参数输入方式下，共读取到的用例总数
	 */
	private static Integer totalNum = 0;
	
	public static Integer getTotalNum() {
		return totalNum;
	}

	public static void setTotalNum(Integer totalNum) {
		GTestProgress.totalNum = totalNum;
	}

	/**
	 *  已执行有效类个数
	 */
	private static Integer dTestReal = 0;
	
	/**
	 *  已执行失败类个数
	 */
	private static Integer dTestFail = 0;
	
	/**
	 *  已执行异常场景类个数
	 */
	private static Integer dTestUnReal = 0;
	
	/**
	 *  已执行中断类个数
	 */
	private static Integer dTestUnDo = 0;
	
	/**
	 *  已跳过个数
	 */
	private static Integer dTestJump = 0;

	public static Integer getdTestReal() {
		return dTestReal;
	}

	public static void setdTestReal(Integer dTestReal) {
		GTestProgress.dTestReal = dTestReal;
	}

	public static Integer getdTestFail() {
		return dTestFail;
	}

	public static void setdTestFail(Integer dTestFail) {
		GTestProgress.dTestFail = dTestFail;
	}

	public static Integer getdTestUnReal() {
		return dTestUnReal;
	}

	public static void setdTestUnReal(Integer dTestUnReal) {
		GTestProgress.dTestUnReal = dTestUnReal;
	}

	public static Integer getdTestUnDo() {
		return dTestUnDo;
	}

	public static void setdTestUnDo(Integer dTestUnDo) {
		GTestProgress.dTestUnDo = dTestUnDo;
	}

	public static Integer getdTestJump() {
		return dTestJump;
	}

	public static void setdTestJump(Integer dTestJump) {
		GTestProgress.dTestJump = dTestJump;
	}
}
