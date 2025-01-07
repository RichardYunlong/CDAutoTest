package main.java.Test;

import main.java.Base.GClazz;

/**
 *  进度管理
 *  按照任务执行数量维度执行进度管理
 */
public class GTestProgress {

	public GTestProgress(){
		GClazz.thisADataUnitClass();
	}
	
	/**
	 *  使用某种用例参数输入方式下，共读取到的用例总数
	 */
	private Integer totalNum = 0;
	public Integer getTotalNum() {
		return totalNum;
	}
	public void setTotalNum(Integer totalNum) { this.totalNum = totalNum; }

	/**
	 *  已执行有效类个数
	 */
	private Integer dTestReal = 0;
	public Integer getdTestReal() {
		return dTestReal;
	}
	public void setdTestReal(Integer dTestReal) {
		this.dTestReal = dTestReal;
	}
	
	/**
	 *  已执行失败类个数
	 */
	private Integer dTestFail = 0;
	public Integer getdTestFail() {
		return dTestFail;
	}
	public void setdTestFail(Integer dTestFail) {
		this.dTestFail = dTestFail;
	}
	
	/**
	 *  已执行异常场景类个数
	 */
	private Integer dTestUnReal = 0;
	public Integer getdTestUnReal() {
		return dTestUnReal;
	}
	public void setdTestUnReal(Integer dTestUnReal) {
		this.dTestUnReal = dTestUnReal;
	}
	
	/**
	 *  已执行中断类个数
	 */
	private Integer dTestUnDo = 0;
	public Integer getdTestUnDo() {
		return dTestUnDo;
	}
	public void setdTestUnDo(Integer dTestUnDo) {
		this.dTestUnDo = dTestUnDo;
	}
	
	/**
	 *  已跳过个数
	 */
	private Integer dTestJump = 0;
	public Integer getdTestJump() {
		return dTestJump;
	}
	public void setdTestJump(Integer dTestJump) {
		this.dTestJump = dTestJump;
	}
}
