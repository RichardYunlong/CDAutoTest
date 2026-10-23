package IO;

/**
 *  发送报文参数表
 */
public class GXlsRequestVO extends GXlsBaseVO {
	
	/**
	 *  序号
	 */
	private String indexNo;
	/**
	 *  系统模块
	 */
	private String systemModule;
	/**
	 *  功能点
	 */
	private String functionPoint;
	/**
	 *  用例类型
	 */
	private String caseStyle;
	/**
	 *  用例编号
	 */
	private String caseTSNO;
	/**
	 *  用例说明
	 */
	private String caseScription;
	/**
	 *  前置条件
	 */
	private String prefixCondition;
	/**
	 *  步骤描述
	 */
	private String caseStep;
	/**
	 *  测试环境类型
	 */
	private String caseEnvironment;
	/**
	 *  返回编码
	 */
	private String resultCode;
	/**
	 *  返回信息
	 */
	private String resultMessage;
	/**
	 *  输入条件组合
	 */
	private String inputMix;
	/**
	 *  预期结果
	 */
	private String outputMix;
	/**
	 *  第一轮测试结果
	 */
	private String outputMix1;
	/**
	 *  第二轮测试结果
	 */
	private String outputMix2;
	/**
	 *  是否通过
	 */
	private String isPassed;
	/**
	 *  测试类型
	 */
	private String caseKind;
	/**
	 *  用例优先级
	 */
	private String casePriority;
	/**
	 *  备注
	 */
	private String caseMark;
	/**
	 *  证件类型编码
	 */
	private String certType;
	/**
	 *  客户类型编码
	 */
	private String customerType;
	/**
	 *  用户名称
	 */
	private String userName;
	/**
	 *  用户主题
	 */
	private String userNameInDn;
	/**
	 *  用户证件标记
	 */
	private String userIdent;
	/**
	 *  证件类型编码（证书信息内的第二处，与 {@link #certType} 同名不同列）
	 */
	private String identType;
	/**
	 *  证件号
	 */
	private String identNo;
	/**
	 *  密钥类型
	 */
	private String keyAlg;
	/**
	 *  密钥长度
	 */
	private String keyLength;
	/**
	 *  证书主题
	 */
	private String dn;
	/**
	 *  序列号
	 */
	private String serialNo;
	/**
	 *  授权码
	 */
	private String authCode;
	/**
	 *  机构编码
	 */
	private String branchCode;
	/**
	 *  电子邮件
	 */
	private String email;
	/**
	 *  电话号码
	 */
	private String phoneNo;
	/**
	 *  联系地址
	 */
	private String address;
	/**
	 *  有效期
	 */
	private String duration;
	/**
	 *  截止日期
	 */
	private String endTime;

	/**
	 *  获得序号
	 *
	 *  @return 序号
	 */
	public String getIndexNo() {
		return indexNo;
	}
	/**
	 *  设置序号
	 *
	 *  @param indexNo 序号
	 */
	public void setIndexNo(String indexNo) {
		this.indexNo = indexNo;
	}

	/**
	 *  获得系统模块
	 *
	 *  @return 系统模块
	 */
	public String getSystemModule() {
		return systemModule;
	}
	/**
	 *  设置系统模块
	 *
	 *  @param systemModule 系统模块
	 */
	public void setSystemModule(String systemModule) {
		this.systemModule = systemModule;
	}

	/**
	 *  获得功能点
	 *
	 *  @return 功能点
	 */
	public String getFunctionPoint() {
		return functionPoint;
	}
	/**
	 *  设置功能点
	 *
	 *  @param functionPoint 功能点
	 */
	public void setFunctionPoint(String functionPoint) {
		this.functionPoint = functionPoint;
	}

	/**
	 *  获得用例类型
	 *
	 *  @return 用例类型
	 */
	public String getCaseStyle() {
		return caseStyle;
	}
	/**
	 *  设置用例类型
	 *
	 *  @param caseStyle 用例类型
	 */
	public void setCaseStyle(String caseStyle) {
		this.caseStyle = caseStyle;
	}

	/**
	 *  获得用例编号
	 *
	 *  @return 用例编号
	 */
	public String getCaseTSNO() {
		return caseTSNO;
	}
	/**
	 *  设置用例编号
	 *
	 *  @param caseTSNO 用例编号
	 */
	public void setCaseTSNO(String caseTSNO) {
		this.caseTSNO = caseTSNO;
	}

	/**
	 *  获得用例说明
	 *
	 *  @return 用例说明
	 */
	public String getCaseScription() {
		return caseScription;
	}
	/**
	 *  设置用例说明
	 *
	 *  @param caseScription 用例说明
	 */
	public void setCaseScription(String caseScription) {
		this.caseScription = caseScription;
	}

	/**
	 *  获得前置条件
	 *
	 *  @return 前置条件
	 */
	public String getPrefixCondition() {
		return prefixCondition;
	}
	/**
	 *  设置前置条件
	 *
	 *  @param prefixCondition 前置条件
	 */
	public void setPrefixCondition(String prefixCondition) {
		this.prefixCondition = prefixCondition;
	}

	/**
	 *  获得步骤描述
	 *
	 *  @return 步骤描述
	 */
	public String getCaseStep() {
		return caseStep;
	}
	/**
	 *  设置步骤描述
	 *
	 *  @param caseStep 步骤描述
	 */
	public void setCaseStep(String caseStep) {
		this.caseStep = caseStep;
	}

	/**
	 *  获得测试环境类型
	 *
	 *  @return 测试环境类型
	 */
	public String getCaseEnvironment() {
		return caseEnvironment;
	}
	/**
	 *  设置测试环境类型
	 *
	 *  @param caseEnvironment 测试环境类型
	 */
	public void setCaseEnvironment(String caseEnvironment) {
		this.caseEnvironment = caseEnvironment;
	}

	/**
	 *  获得返回编码
	 *
	 *  @return 返回编码
	 */
	public String getResultCode() {
		return resultCode;
	}
	/**
	 *  设置返回编码
	 *
	 *  @param resultCode 返回编码
	 */
	public void setResultCode(String resultCode) {
		this.resultCode = resultCode;
	}

	/**
	 *  获得返回信息
	 *
	 *  @return 返回信息
	 */
	public String getResultMessage() {
		return resultMessage;
	}
	/**
	 *  设置返回信息
	 *
	 *  @param resultMessage 返回信息
	 */
	public void setResultMessage(String resultMessage) {
		this.resultMessage = resultMessage;
	}

	/**
	 *  获得输入条件组合
	 *
	 *  @return 输入条件组合
	 */
	public String getInputMix() {
		return inputMix;
	}
	/**
	 *  设置输入条件组合
	 *
	 *  @param inputMix 输入条件组合
	 */
	public void setInputMix(String inputMix) {
		this.inputMix = inputMix;
	}

	/**
	 *  获得预期结果
	 *
	 *  @return 预期结果
	 */
	public String getOutputMix() {
		return outputMix;
	}
	/**
	 *  设置预期结果
	 *
	 *  @param outputMix 预期结果
	 */
	public void setOutputMix(String outputMix) {
		this.outputMix = outputMix;
	}

	/**
	 *  获得第一轮测试结果
	 *
	 *  @return 第一轮测试结果
	 */
	public String getOutputMix1() {
		return outputMix1;
	}
	/**
	 *  设置第一轮测试结果
	 *
	 *  @param outputMix1 第一轮测试结果
	 */
	public void setOutputMix1(String outputMix1) {
		this.outputMix1 = outputMix1;
	}

	/**
	 *  获得第二轮测试结果
	 *
	 *  @return 第二轮测试结果
	 */
	public String getOutputMix2() {
		return outputMix2;
	}
	/**
	 *  设置第二轮测试结果
	 *
	 *  @param outputMix2 第二轮测试结果
	 */
	public void setOutputMix2(String outputMix2) {
		this.outputMix2 = outputMix2;
	}

	/**
	 *  获得是否通过
	 *
	 *  @return 是否通过
	 */
	public String getIsPassed() {
		return isPassed;
	}
	/**
	 *  设置是否通过
	 *
	 *  @param isPassed 是否通过
	 */
	public void setIsPassed(String isPassed) {
		this.isPassed = isPassed;
	}

	/**
	 *  获得测试类型
	 *
	 *  @return 测试类型
	 */
	public String getCaseKind() {
		return caseKind;
	}
	/**
	 *  设置测试类型
	 *
	 *  @param caseKind 测试类型
	 */
	public void setCaseKind(String caseKind) {
		this.caseKind = caseKind;
	}

	/**
	 *  获得用例优先级
	 *
	 *  @return 用例优先级
	 */
	public String getCasePriority() {
		return casePriority;
	}
	/**
	 *  设置用例优先级
	 *
	 *  @param casePriority 用例优先级
	 */
	public void setCasePriority(String casePriority) {
		this.casePriority = casePriority;
	}

	/**
	 *  获得备注
	 *
	 *  @return 备注
	 */
	public String getCaseMark() {
		return caseMark;
	}
	/**
	 *  设置备注
	 *
	 *  @param caseMark 备注
	 */
	public void setCaseMark(String caseMark) {
		this.caseMark = caseMark;
	}

	/**
	 *  获得证件类型编码
	 *
	 *  @return 证件类型编码
	 */
	public String getCertType() {
		return certType;
	}
	/**
	 *  设置证件类型编码
	 *
	 *  @param certType 证件类型编码
	 */
	public void setCertType(String certType) {
		this.certType = certType;
	}

	/**
	 *  获得客户类型编码
	 *
	 *  @return 客户类型编码
	 */
	public String getCustomerType() {
		return customerType;
	}
	/**
	 *  设置客户类型编码
	 *
	 *  @param customerType 客户类型编码
	 */
	public void setCustomerType(String customerType) {
		this.customerType = customerType;
	}

	/**
	 *  获得用户名称
	 *
	 *  @return 用户名称
	 */
	public String getUserName() {
		return userName;
	}
	/**
	 *  设置用户名称
	 *
	 *  @param userName 用户名称
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}

	/**
	 *  获得用户主题
	 *
	 *  @return 用户主题
	 */
	public String getUserNameInDn() {
		return userNameInDn;
	}
	/**
	 *  设置用户主题
	 *
	 *  @param userNameInDn 用户主题
	 */
	public void setUserNameInDn(String userNameInDn) {
		this.userNameInDn = userNameInDn;
	}

	/**
	 *  获得用户证件标记
	 *
	 *  @return 用户证件标记
	 */
	public String getUserIdent() {
		return userIdent;
	}
	/**
	 *  设置用户证件标记
	 *
	 *  @param userIdent 用户证件标记
	 */
	public void setUserIdent(String userIdent) {
		this.userIdent = userIdent;
	}

	/**
	 *  获得证件类型编码（证书信息内的第二处，与 #certType 同名不同列）
	 *
	 *  @return 证件类型编码（证书信息内的第二处，与 #certType 同名不同列）
	 */
	public String getIdentType() {
		return identType;
	}
	/**
	 *  设置证件类型编码（证书信息内的第二处，与 #certType 同名不同列）
	 *
	 *  @param identType 证件类型编码（证书信息内的第二处，与 #certType 同名不同列）
	 */
	public void setIdentType(String identType) {
		this.identType = identType;
	}

	/**
	 *  获得证件号
	 *
	 *  @return 证件号
	 */
	public String getIdentNo() {
		return identNo;
	}
	/**
	 *  设置证件号
	 *
	 *  @param identNo 证件号
	 */
	public void setIdentNo(String identNo) {
		this.identNo = identNo;
	}

	/**
	 *  获得密钥类型
	 *
	 *  @return 密钥类型
	 */
	public String getKeyAlg() {
		return keyAlg;
	}
	/**
	 *  设置密钥类型
	 *
	 *  @param keyAlg 密钥类型
	 */
	public void setKeyAlg(String keyAlg) {
		this.keyAlg = keyAlg;
	}

	/**
	 *  获得密钥长度
	 *
	 *  @return 密钥长度
	 */
	public String getKeyLength() {
		return keyLength;
	}
	/**
	 *  设置密钥长度
	 *
	 *  @param keyLength 密钥长度
	 */
	public void setKeyLength(String keyLength) {
		this.keyLength = keyLength;
	}

	/**
	 *  获得证书主题
	 *
	 *  @return 证书主题
	 */
	public String getDn() {
		return dn;
	}
	/**
	 *  设置证书主题
	 *
	 *  @param dn 证书主题
	 */
	public void setDn(String dn) {
		this.dn = dn;
	}

	/**
	 *  获得序列号
	 *
	 *  @return 序列号
	 */
	public String getSerialNo() {
		return serialNo;
	}
	/**
	 *  设置序列号
	 *
	 *  @param serialNo 序列号
	 */
	public void setSerialNo(String serialNo) {
		this.serialNo = serialNo;
	}

	/**
	 *  获得授权码
	 *
	 *  @return 授权码
	 */
	public String getAuthCode() {
		return authCode;
	}
	/**
	 *  设置授权码
	 *
	 *  @param authCode 授权码
	 */
	public void setAuthCode(String authCode) {
		this.authCode = authCode;
	}

	/**
	 *  获得机构编码
	 *
	 *  @return 机构编码
	 */
	public String getBranchCode() {
		return branchCode;
	}
	/**
	 *  设置机构编码
	 *
	 *  @param branchCode 机构编码
	 */
	public void setBranchCode(String branchCode) {
		this.branchCode = branchCode;
	}

	/**
	 *  获得电子邮件
	 *
	 *  @return 电子邮件
	 */
	public String getEmail() {
		return email;
	}
	/**
	 *  设置电子邮件
	 *
	 *  @param email 电子邮件
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 *  获得电话号码
	 *
	 *  @return 电话号码
	 */
	public String getPhoneNo() {
		return phoneNo;
	}
	/**
	 *  设置电话号码
	 *
	 *  @param phoneNo 电话号码
	 */
	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}

	/**
	 *  获得联系地址
	 *
	 *  @return 联系地址
	 */
	public String getAddress() {
		return address;
	}
	/**
	 *  设置联系地址
	 *
	 *  @param address 联系地址
	 */
	public void setAddress(String address) {
		this.address = address;
	}

	/**
	 *  获得有效期
	 *
	 *  @return 有效期
	 */
	public String getDuration() {
		return duration;
	}
	/**
	 *  设置有效期
	 *
	 *  @param duration 有效期
	 */
	public void setDuration(String duration) {
		this.duration = duration;
	}

	/**
	 *  获得截止日期
	 *
	 *  @return 截止日期
	 */
	public String getEndTime() {
		return endTime;
	}
	/**
	 *  设置截止日期
	 *
	 *  @param endTime 截止日期
	 */
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
}
