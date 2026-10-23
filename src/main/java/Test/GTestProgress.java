package Test;

import Base.GClazz;

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
	/**
	 *  获得使用某种用例参数输入方式下，共读取到的用例总数
	 *
	 *  @return 使用某种用例参数输入方式下，共读取到的用例总数
	 */
	public Integer getTotalNum() {
		return totalNum;
	}
	/**
	 *  设置使用某种用例参数输入方式下，共读取到的用例总数
	 *
	 *  @param totalNum 使用某种用例参数输入方式下，共读取到的用例总数
	 */
	public void setTotalNum(Integer totalNum) { this.totalNum = totalNum; }

	/**
	 *  已执行有效类个数
	 */
	private Integer dTestReal = 0;
	/**
	 *  获得已执行有效类个数
	 *
	 *  @return 已执行有效类个数
	 */
	public Integer getdTestReal() {
		return dTestReal;
	}
	/**
	 *  设置已执行有效类个数
	 *
	 *  @param dTestReal 已执行有效类个数
	 */
	public void setdTestReal(Integer dTestReal) {
		this.dTestReal = dTestReal;
	}
	
	/**
	 *  已执行失败类个数
	 */
	private Integer dTestFail = 0;
	/**
	 *  获得已执行失败类个数
	 *
	 *  @return 已执行失败类个数
	 */
	public Integer getdTestFail() {
		return dTestFail;
	}
	/**
	 *  设置已执行失败类个数
	 *
	 *  @param dTestFail 已执行失败类个数
	 */
	public void setdTestFail(Integer dTestFail) {
		this.dTestFail = dTestFail;
	}
	
	/**
	 *  已执行异常场景类个数
	 */
	private Integer dTestUnReal = 0;
	/**
	 *  获得已执行异常场景类个数
	 *
	 *  @return 已执行异常场景类个数
	 */
	public Integer getdTestUnReal() {
		return dTestUnReal;
	}
	/**
	 *  设置已执行异常场景类个数
	 *
	 *  @param dTestUnReal 已执行异常场景类个数
	 */
	public void setdTestUnReal(Integer dTestUnReal) {
		this.dTestUnReal = dTestUnReal;
	}
	
	/**
	 *  已执行中断类个数
	 */
	private Integer dTestUnDo = 0;
	/**
	 *  获得已执行中断类个数
	 *
	 *  @return 已执行中断类个数
	 */
	public Integer getdTestUnDo() {
		return dTestUnDo;
	}
	/**
	 *  设置已执行中断类个数
	 *
	 *  @param dTestUnDo 已执行中断类个数
	 */
	public void setdTestUnDo(Integer dTestUnDo) {
		this.dTestUnDo = dTestUnDo;
	}
	
	/**
	 *  已跳过个数
	 */
	private Integer dTestJump = 0;
	/**
	 *  获得已跳过个数
	 *
	 *  @return 已跳过个数
	 */
	public Integer getdTestJump() {
		return dTestJump;
	}
	/**
	 *  设置已跳过个数
	 *
	 *  @param dTestJump 已跳过个数
	 */
	public void setdTestJump(Integer dTestJump) {
		this.dTestJump = dTestJump;
	}
}
