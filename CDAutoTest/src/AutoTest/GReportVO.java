package AutoTest;

/**
 *  格式化输出
 */
public class GReportVO {

	/**
	 *  输入内容字段名称举例：1序号、2系统模块、3功能点、4用例类型、5用例编号、6用例说明、7前置条件、8步骤描述、9测试环境类型
	 *  及其获取和设置方法
	 */
	public String indexNo;
	public String systemModule;
	public String functionPoint;
	public String caseStyle;
	public String caseTSNO;
	public String caseScription;
	public String prefixCondition;
	public String caseStep;
	public String caseEnvironment;

	public String getIndexNo() {
		return indexNo;
	}

	public void setIndexNo(String indexNo) {
		this.indexNo = indexNo;
	}

	public String getSystemModule() {
		return systemModule;
	}

	public void setSystemModule(String systemModule) {
		this.systemModule = systemModule;
	}

	public String getFunctionPoint() {
		return functionPoint;
	}

	public void setFunctionPoint(String functionPoint) {
		this.functionPoint = functionPoint;
	}

	public String getCaseStyle() {
		return caseStyle;
	}

	public void setCaseStyle(String caseStyle) {
		this.caseStyle = caseStyle;
	}

	public String getCaseTSNO() {
		return caseTSNO;
	}

	public void setCaseTSNO(String caseTSNO) {
		this.caseTSNO = caseTSNO;
	}

	public String getCaseScription() {
		return caseScription;
	}

	public void setCaseScription(String caseScription) {
		this.caseScription = caseScription;
	}

	public String getPrefixCondition() {
		return prefixCondition;
	}

	public void setPrefixCondition(String prefixCondition) {
		this.prefixCondition = prefixCondition;
	}

	public String getCaseStep() {
		return caseStep;
	}

	public void setCaseStep(String caseStep) {
		this.caseStep = caseStep;
	}

	public String getCaseEnvironment() {
		return caseEnvironment;
	}

	public void setCaseEnvironment(String caseEnvironment) {
		this.caseEnvironment = caseEnvironment;
	}

	/**
	 *  输出内容字段名称举例：返回编码、返回信息、输入条件组合、预期结果、第一轮测试结果、第二轮测试结果、是否通过、测试类型、用例优先级、备注 
	 *  及其获取和设置方法
	 */
	public String resultCode;
	public String resultMessage;
	public String inputMix;
	public String outputMix;
	public String outputMix1;
	public String outputMix2;
	public String isPassed;
	public String caseKind;
	public String casePriority;
	public String caseMark;

	public String getResultCode() {
		return resultCode;
	}

	public void setResultCode(String resultCode) {
		this.resultCode = resultCode;
	}

	public String getResultMessage() {
		return resultMessage;
	}

	public void setResultMessage(String resultMessage) {
		this.resultMessage = resultMessage;
	}

	public String getInputMix() {
		return inputMix;
	}

	public void setInputMix(String inputMix) {
		this.inputMix = inputMix;
	}

	public String getOutputMix() {
		return outputMix;
	}

	public void setOutputMix(String outputMix) {
		this.outputMix = outputMix;
	}

	public String getOutputMix1() {
		return outputMix1;
	}

	public void setOutputMix1(String outputMix1) {
		this.outputMix1 = outputMix1;
	}

	public String getOutputMix2() {
		return outputMix2;
	}

	public void setOutputMix2(String outputMix2) {
		this.outputMix2 = outputMix2;
	}

	public String getIsPassed() {
		return isPassed;
	}

	public void setIsPassed(String isPassed) {
		this.isPassed = isPassed;
	}

	public String getCaseKind() {
		return caseKind;
	}

	public void setCaseKind(String caseKind) {
		this.caseKind = caseKind;
	}

	public String getCasePriority() {
		return casePriority;
	}

	public void setCasePriority(String casePriority) {
		this.casePriority = casePriority;
	}

	public String getCaseMark() {
		return caseMark;
	}

	public void setCaseMark(String caseMark) {
		this.caseMark = caseMark;
	}
}
