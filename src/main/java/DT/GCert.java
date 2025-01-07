package main.java.DT;

import main.java.Base.GClazz;

/**
 * 证书
 */
public class GCert {
	
    /**
     *  构造函数
     */
	private GCert(){
		GClazz.thisADataUnitClass();
	}

	/**
	 *  证书所属机构
	 */
	private String organizationID;
	public String getOrganizationID() {
		return organizationID;
	}
	public void setOrganizationID(String organizationID) {
		this.organizationID = organizationID;
	}

	/**
	 *  有效期：单位的倍数，例如单位为“月”时，取值“1”，即有效期为“1个月”
	 */
	private String period;
	public String getPeriod() {
		return period;
	}
	public void setPeriod(String period) {
		this.period = period;
	}
	
	/**
	 *  有效期单位：证书有效期单位 1-年；2-月；6-日；不传则与被换发的证书有效期一致  用于更新或换发
	 */
	private String periodUnit;
	public String getPeriodUnit() {
		return periodUnit;
	}
	public void setPeriodUnit(String periodUnit) {
		this.periodUnit = periodUnit;
	}
	
	/**
	 *  截止日期（到），用于申请、更新或换发
	 */
	private String notAfter;
	public String getNotAfter() {
		return notAfter;
	}
	public void setNotAfter(String notAfter) {
		this.notAfter = notAfter;
	}
	
	/**
	 *  一类证书模板号
	 */
	private String templateID;
	public String getTemplateID() {
		return templateID;
	}
	public void setTemplateID(String templateID) {
		this.templateID = templateID;
	}
	
	/**
	 *  一类证书密钥算法
	 */
	private String keyAlg;
	public String getKeyAlg() {
		return keyAlg;
	}
	public void setKeyAlg(String keyAlg) {
		this.keyAlg = keyAlg;
	}

	/**
	 *  一类证书密钥长度
	 */
	private Integer keyLength;
	public Integer getKeyLength() {
		return keyLength;
	}
	public void setKeyLength(Integer keyLength) {
		this.keyLength = keyLength;
	}
	
	/**
	 *  一类证书类型：此值专指“级别类型”，S-单证（或普通）；D-双证（或高级）
	 */
	private String certLevel;
	public String getCertLevel() {
		return certLevel;
	}
	public void setCertLevel(String certLevel) {
		this.certLevel = certLevel;
	}
	
	/**
	 *  二类证书模板号
	 */
	private String templateID2;
	public String getTemplateID2() {
		return templateID2;
	}
	public void setTemplateID2(String templateID2) {
		this.templateID2 = templateID2;
	}
	
	/**
	 *  二类证书密钥算法
	 */
	private String keyAlg2;
	public String getKeyAlg2() {
		return keyAlg2;
	}
	public void setKeyAlg2(String keyAlg2) {
		this.keyAlg2 = keyAlg2;
	}

	/**
	 *  二类证书密钥长度
	 */
	private Integer keyLength2;// 密钥长度
	public Integer getKeyLength2() {
		return keyLength2;
	}
	public void setKeyLength2(Integer keyLength2) {
		this.keyLength2 = keyLength2;
	}

	/**
	 *  二类证书类型：此值专指“级别类型”，S-单证（或普通）；D-双证（或高级）
	 */
	private String certLevel2;
	public String getCertLevel2() {
		return certLevel2;
	}
	public void setCertLevel2(String certLevel2) {
		this.certLevel2 = certLevel2;
	}
	
	/**
	 *  用户名称
	 */
	private String userName;
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	/**
	 *  用户别名
	 */
	private String userNameInDN;
	public String getUserNameInDN() {
		return userNameInDN;
	}
	public void setUserNameInDN(String userNameInDN) {
		this.userNameInDN = userNameInDN;
	}
	
	/**
	 *  用户类型:个人证书：1 企业证书：2 设备证书：
	 */
	private String customerType;
	public String getCustomerType() {
		return customerType;
	}
	public void setCustomerType(String customerType) {
		this.customerType = customerType;
	}

	/**
	 *  用户号
	 */
	private String customerId;
	public String getCustomerId() {
		return customerId;
	}
	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}
	
	/**
	 *  用户号
	 */
	private String uId;
	public String getuId() {
		return uId;
	}
	public void setuId(String uId) {
		this.uId = uId;
	}
	
	/**
	 *  用户证件类型
	 */
	private String userIdentType;
	public String getUserIdentType() {
		return userIdentType;
	}
	public void setUserIdentType(String userIdentType) {
		this.userIdentType = userIdentType;
	}

	/**
	 *  用户证件号码
	 */
	private String userIdentNo;
	public String getUserIdentNo() {
		return userIdentNo;
	}
	public void setUserIdentNo(String userIdentNo) {
		this.userIdentNo = userIdentNo;
	}

	/**
	 *  是否将证件号码加到主题备用名(1：加，0：不加)
	 */
	private String  addIdentNoExt;
	public String getAddIdentNoExt() {
		return addIdentNoExt;
	}
	public void setAddIdentNoExt(String addIdentNoExt) {
		this.addIdentNoExt = addIdentNoExt;
	}
	
	/**
	 *  用户唯一标识
	 */
	private String userUniqueIdentNo;
	public String getUserUniqueIdentNo() {
		return userUniqueIdentNo;
	}
	public void setUserUniqueIdentNo(String userUniqueIdentNo) {
		this.userUniqueIdentNo = userUniqueIdentNo;
	}
	
	/**
	 *  用户邮箱
	 */
	private String  email;
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 *  是否将Email加到主题备用名(1：加，0：不加)
	 */
	private String  isAddEmailExt;
	public String getIsAddEmailExt() {
		return isAddEmailExt;
	}
	public void setIsAddEmailExt(String isAddEmailExt) {
		this.isAddEmailExt = isAddEmailExt;
	}
	
	/**
	 *  电话
	 */
	private String  phoneNo;
	public String getPhoneNo() {
		return phoneNo;
	}
	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}

	/**
	 *  地址
	 */
	private String  address;
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}

	/**
	 *  自定义扩展域
	 */
	private String  selfExtValue;
	public String getSelfExtValue() {
		return selfExtValue;
	}
	public void setSelfExtValue(String selfExtValue) {
		this.selfExtValue = selfExtValue;
	}

	/**
	 *  一类证书的证书主题用户名 或 二类证书的通用名   
	 */
	private String  subscriberNameInDN;
	public String getSubscriberNameInDN() {
		return subscriberNameInDN;
	}
	public void setSubscriberNameInDN(String subscriberNameInDN) {
		this.subscriberNameInDN = subscriberNameInDN;
	}
	
	/**
	 *  自定义扩展域 或 服务器证书主域名之外的域名，中间以[,]分隔 
	 */
	private String extensionInfo;
	public String getExtensionInfo() {
		return extensionInfo;
	}
	public void setExtensionInfo(String extensionInfo) {
		this.extensionInfo = extensionInfo;
	}
	
	/**
	 *  域名信息
	 */
	private String domainName;
	public String getDomainName() {
		return domainName;
	}
	public void setDomainName(String domainName) {
		this.domainName = domainName;
	}
	
	/**
	 *  备注信息
	 */
	private String remark;
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}

	/**
	 *  联系人
	 */
	private String contract;
	public String getContract() {
		return contract;
	}
	public void setContract(String contract) {
		this.contract = contract;
	}

	/**
	 *  SSL证书类型：1-普通，2-多域名，3-带通配符
	 */
	private String sslType;
	public String getSslType() {
		return sslType;
	}
	public void setSslType(String sslType) {
		this.sslType = sslType;
	}

	/**
	 *  IP地址
	 */
	private String remoteIp;
	public String getRemoteIp() {
		return remoteIp;
	}
	public void setRemoteIp(String remoteIp) {
		this.remoteIp = remoteIp;
	}
	
	/**
	 *  国家
	 */
	private String country;
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	
	/**
	 *  营业地点所在地市（L）
	 */
	private String locality;
	public String getLocality() {
		return locality;
	}
	public void setLocality(String locality) {
		this.locality = locality;
	}
	
	/**
	 *  营业地点所在省(S)
	 */
	private String stateOrProvince;
	public String getStateOrProvince() {
		return stateOrProvince;
	}
	public void setStateOrProvince(String stateOrProvince) {
		this.stateOrProvince = stateOrProvince;
	}
	
	/**
	 *  部门名称（OU）
	 */
	private String departmentNameInCert;
	public String getDepartmentNameInCert() {
		return departmentNameInCert;
	}
	public void setDepartmentNameInCert(String departmentNameInCert) { this.departmentNameInCert = departmentNameInCert; }
	
	/**
	 *  组织机构名称（O）
	 */
	private String organizationNameInCert;
	public String getOrganizationNameInCert() {
		return organizationNameInCert;
	}
	public void setOrganizationNameInCert(String organizationNameInCert) { this.organizationNameInCert = organizationNameInCert; }
	
	/**
	 *  安全邮件证书输入项，安全邮件地址(E)
	 */
	private String emailInCert;
	public String getEmailInCert() {
		return emailInCert;
	}
	public void setEmailInCert(String emailInCert) {
		this.emailInCert = emailInCert;
	}
	
	/**
	 *  营业地点，EV证书中的STREET
	 */
	private String streetAddress;
	public String getStreetAddress() {
		return streetAddress;
	}
	public void setStreetAddress(String streetAddress) {
		this.streetAddress = streetAddress;
	}
	
	/**
	 *  营业地点邮政编码，EV证书中的PostalCode
	 */
	private String postalCode;
	public String getPostalCode() {
		return postalCode;
	}
	public void setPostalCode(String postalCode) { this.postalCode = postalCode; }
	
	/**
	 *  业务类型，EV证书中的2.5.4.15
	 */
	private String businessCategory;
	public String getBusinessCategory() {
		return businessCategory;
	}
	public void setBusinessCategory(String businessCategory) { this.businessCategory = businessCategory; }
	
	/**
	 *  注册证件号码，EV证书中的SN
	 */
	private String serialNoInEvCert;
	public String getSerialNoInEvCert() { return serialNoInEvCert; }
	public void setSerialNoInEvCert(String serialNoInEvCert) { this.serialNoInEvCert = serialNoInEvCert; }
	
	/**
	 *  注册管辖区所在地市，EV证书中的1.3.6.1.4.1.311.60.2.1.1
	 */
	private String jurisdictionLocality;
	public String getJurisdictionLocality() { return jurisdictionLocality; }

	public void setJurisdictionLocality(String jurisdictionLocality) { this.jurisdictionLocality = jurisdictionLocality; }
	
	/**
	 *  注册管辖区所在省，EV证书中的1.3.6.1.4.1.311.60.2.1.2
	 */
	private String jurisdictionStateOrProvince;
	public String getJurisdictionStateOrProvince() { return jurisdictionStateOrProvince; }
	public void setJurisdictionStateOrProvince(String jurisdictionStateOrProvince) { this.jurisdictionStateOrProvince = jurisdictionStateOrProvince; }
	
	/**
	 *  注册管辖区所在国家代码，EV证书中的1.3.6.1.4.1.311.60.2.1.3
	 */
	private String jurisdictionCountry;
	public String getJurisdictionCountry() { return jurisdictionCountry; }
	public void setJurisdictionCountry(String jurisdictionCountry) { this.jurisdictionCountry = jurisdictionCountry; }
	
	/**
	 *  设备标识
	 */
	private String deviceIdentifier;
	public String getDeviceIdentifier() { return deviceIdentifier; }
	public void setDeviceIdentifier(String deviceIdentifier) { this.deviceIdentifier = deviceIdentifier; }

	/**
	 *  预植证书KeyID
	 */
	private String keyID;
	public String getKeyID() { return keyID; }
	public void setKeyID(String keyID) { this.keyID = keyID; }
	
	/**
	 *  证书DN
	 */
	private String dn;
	public String getDn() { return dn; }
	public void setDn(String dn) { this.dn = dn; }
	
	/**
	 *  证书序列号
	 */
	private String serialNo;
	public String getSerialNo() { return serialNo; }
	public void setSerialNo(String serialNo) { this.serialNo = serialNo; }
	
	/**
	 *  证书序列号规范长度
	 */
	private String serialNoLen;
	public String getSerialNoLen() { return serialNoLen; }
	public void setSerialNoLen(String serialNoLen) { this.serialNoLen = serialNoLen; }
	
	/**
	 *  加密证书序列号
	 */
	private String encSerialNo;
	public String getEncSerialNo() { return encSerialNo; }
	public void setEncSerialNo(String encSerialNo) { this.encSerialNo = encSerialNo; }
	
	/**
	 *  加密证书序列号规范长度
	 */
	private String encSerialNoLen;
	public String getEncSerialNoLen() { return encSerialNoLen; }
	public void setEncSerialNoLen(String encSerialNoLen) { this.encSerialNoLen = encSerialNoLen; }
	
	/**
	 *  证书授权码
	 */
	private String authCode;
	public String getAuthCode() { return authCode; }
	public void setAuthCode(String authCode) { this.authCode = authCode; }
	
	/**
	 *  P10申请书
	 */
	private String csr;
	public String getCsr() { return csr; }
	public void setCsr(String csr) { this.csr = csr; }
	
	/**
	 *  P10附加申请书
	 */
	private String csrSub;
	public String getCsrSub() { return csrSub; }
	public void setCsrSub(String csrSub) { this.csrSub = csrSub; }
	
	/**
	 *  证书类型：指个人或企业
	 */
	private String certType;
	public String getCertType() { return certType; }
	public void setCertType(String certType) { this.certType = certType; }
	
	/**
	 *  CA类型
	 */
	private String caType;
	public String getCaType() { return caType; }
	public void setCaType(String caType) { this.caType = caType; }
	
	/**
	 *  证书状态（多种状态以逗号[,]分隔）
	 */
	private String certStatus;
	public String getCertStatus() { return certStatus; }
	public void setCertStatus(String certStatus) { this.certStatus = certStatus; }
	
	/**
	 *  是否自动更新 0：否，1：是
	 */
	private String renewAl;
	public String getRenewAl() { return renewAl; }
	public void setRenewAl(String renewAl) { this.renewAl = renewAl; }
	
	/**
	 *  生效日期（从）YYYY-MM-DD
	 */
	private String notBeforeFrom;
	public String getNotBeforeFrom() { return notBeforeFrom; }
	public void setNotBeforeFrom(String notBeforeFrom) { this.notBeforeFrom = notBeforeFrom; }
	
	/**
	 *  生效日期（到）YYYY-MM-DD
	 */
	private String notBeforeTo;
	public String getNotBeforeTo() { return notBeforeTo; }
	public void setNotBeforeTo(String notBeforeTo) { this.notBeforeTo = notBeforeTo; }
	
	/**
	 *  截止日期（从）YYYY-MM-DD
	 */
	private String notAfterFrom;
	public String getNotAfterFrom() { return notAfterFrom; }
	public void setNotAfterFrom(String notAfterFrom) { this.notAfterFrom = notAfterFrom; }
	
	/**
	 *  截止日期（到）YYYY-MM-DD
	 */
	private String notAfterTo;
	public String getNotAfterTo() { return notAfterTo; }
	public void setNotAfterTo(String notAfterTo) { this.notAfterTo = notAfterTo; }
	
	/**
	 *  是否精确匹配,用于查询业务
	 */
	private String isExact;
	public String getIsExact() { return isExact; }
	public void setIsExact(String isExact) {
		this.isExact = isExact;
	}

	/**
	 * 重置用户参数
	 */
	public void setDefault() {
		organizationID = "";
		period = "";
        templateID = "";
		keyAlg = "";
		keyLength = 0;
		certLevel = "";
		templateID2 = "";
		keyAlg2 = "";
		keyLength2 = 0;
		certLevel2 = "";
		userName = "";
		customerType = "";
		customerId = "";
		uId = "";
		userIdentType = "";
        addIdentNoExt = "";
		userUniqueIdentNo = "";
		email = "";
		isAddEmailExt = "";
		phoneNo = "";
		address = "";
		selfExtValue = "";
		subscriberNameInDN = "";
        domainName = "";
		remark = "";
        userIdentNo = "";
		extensionInfo = "";
		sslType = "";
		remoteIp = "";
		country = "";
		locality = "";
		stateOrProvince = "";
		departmentNameInCert = "";
		organizationNameInCert = "";			
		emailInCert = "";		
		streetAddress = "";
		postalCode = "";
		businessCategory = "";	
		serialNoInEvCert = "";
		jurisdictionLocality = "";
		jurisdictionStateOrProvince = "";
		jurisdictionCountry = "";
		keyID = "";
		dn = "";
		serialNo = "";
		authCode = "";
		csr = "";
		csrSub = "";
		certType = "";
		caType = "";
		certStatus = "";
		renewAl = "";
		notBeforeFrom = "";
		notBeforeTo = "";
		notAfterFrom = "";
		notAfterTo = "";
		notAfter = "";
		isExact = "";
	}
}
