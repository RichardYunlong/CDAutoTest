package DT;

import Base.GClazz;

/**
 * 证书
 */
public class GCert {
	
    /**
     *  构造函数
     */
	private GCert(){
		GClazz.thisAToolClass();
	}

	/**
	 *  证书所属机构
	 */
	private static String organizationID;
	
	/**
	 *  有效期：单位的倍数，例如单位为“月”时，取值“1”，即有效期为“1个月”
	 */
	private static String period;
	
	/**
	 *  有效期单位：证书有效期单位 1-年；2-月；6-日；不传则与被换发的证书有效期一致  用于更新或换发
	 */
	private static String periodUnit;
	
	/**
	 *  截止日期（到），用于申请、更新或换发
	 */
	private static String notAfter;
	
	/**
	 *  一类证书模板号
	 */
	private static String templateID;
	
	/**
	 *  一类证书密钥算法
	 */
	private static String keyAlg;
	
	/**
	 *  一类证书密钥长度
	 */
	private static Integer keyLength;// 密钥长度
	
	/**
	 *  一类证书类型：此值专指“级别类型”，S-单证（或普通）；D-双证（或高级）
	 */
	private static String certLevel;
	
	/**
	 *  二类证书模板号
	 */
	private static String templateID2;
	
	/**
	 *  二类证书密钥算法
	 */
	private static String keyAlg2;
	
	/**
	 *  二类证书密钥长度
	 */
	private static Integer keyLength2;// 密钥长度
	
	/**
	 *  二类证书类型：此值专指“级别类型”，S-单证（或普通）；D-双证（或高级）
	 */
	private static String certLevel2;
	
	/**
	 *  用户名称
	 */
	private static String userName;
	
	/**
	 *  用户别名
	 */
	private static String userNameInDN;
	
	/**
	 *  用户类型:个人证书：1 企业证书：2 设备证书：
	 */
	private static String customerType;
	
	/**
	 *  用户号
	 */
	private static String customerId;
	
	/**
	 *  用户号
	 */
	private static String uId;
	
	/**
	 *  用户证件类型
	 */
	private static String userIdentType;
	
	/**
	 *  用户证件号码
	 */
	private static String userIdentNo;
	
	/**
	 *  是否将证件号码加到主题备用名(1：加，0：不加)
	 */
	private static String  addIdentNoExt;
	
	/**
	 *  用户唯一标识
	 */
	private static String userUniqueIdentNo;
	
	/**
	 *  用户邮箱
	 */
	private static String  email;
	
	/**
	 *  是否将Email加到主题备用名(1：加，0：不加)
	 */
	private static String  isAddEmailExt;
	
	/**
	 *  电话
	 */
	private static String  phoneNo;
	
	/**
	 *  地址
	 */
	private static String  address;
	
	
	/**
	 *  自定义扩展域
	 */
	private static String  selfExtValue;
	
	/**
	 *  一类证书的证书主题用户名 或 二类证书的通用名   
	 */
	private static String  subscriberNameInDN;
	
	/**
	 *  自定义扩展域 或 服务器证书主域名之外的域名，中间以[,]分隔 
	 */
	private static String extensionInfo;
	
	/**
	 *  域名信息
	 */
	private static String domainName;
	
	/**
	 *  备注信息
	 */
	private static String remark;
	
	/**
	 *  联系人
	 */
	private static String contract;
	
	/**
	 *  SSL证书类型：1-普通，2-多域名，3-带通配符
	 */
	private static String sslType;
	
	/**
	 *  IP地址
	 */
	private static String remoteIp;
	
	/**
	 *  国家
	 */
	private static String country;
	
	/**
	 *  营业地点所在地市（L）
	 */
	private static String locality;
	
	/**
	 *  营业地点所在省(S)
	 */
	private static String stateOrProvince;
	
	/**
	 *  部门名称（OU）
	 */
	private static String departmentNameInCert;
	
	/**
	 *  组织机构名称（O）
	 */
	private static String organizationNameInCert;
	
	/**
	 *  安全邮件证书输入项，安全邮件地址(E)
	 */
	private static String emailInCert;
	
	/**
	 *  营业地点，EV证书中的STREET
	 */
	private static String streetAddress;
	
	/**
	 *  营业地点邮政编码，EV证书中的PostalCode
	 */
	private static String postalCode;
	
	/**
	 *  业务类型，EV证书中的2.5.4.15
	 */
	private static String businessCategory;
	
	/**
	 *  注册证件号码，EV证书中的SN
	 */
	private static String serialNoInEvCert;
	
	/**
	 *  注册管辖区所在地市，EV证书中的1.3.6.1.4.1.311.60.2.1.1
	 */
	private static String jurisdictionLocality;
	
	/**
	 *  注册管辖区所在省，EV证书中的1.3.6.1.4.1.311.60.2.1.2
	 */
	private static String jurisdictionStateOrProvince;
	
	/**
	 *  注册管辖区所在国家代码，EV证书中的1.3.6.1.4.1.311.60.2.1.3
	 */
	private static String jurisdictionCountry;
	
	/**
	 *  设备标识
	 */
	private static String deviceIdentifier;

	/**
	 *  预植证书KeyID
	 */
	private static String keyID;
	
	/**
	 *  证书DN
	 */
	private static String dn;
	
	/**
	 *  证书序列号
	 */
	private static String serialNo;
	
	/**
	 *  证书序列号规范长度
	 */
	private static String serialNoLen;
	
	/**
	 *  加密证书序列号
	 */
	private static String encSerialNo;
	
	/**
	 *  加密证书序列号规范长度
	 */
	private static String encSerialNoLen;
	
	/**
	 *  证书授权码
	 */
	private static String authCode;
	
	/**
	 *  P10申请书
	 */
	private static String csr;
	
	/**
	 *  P10附加申请书
	 */
	private static String csrSub;
	
	/**
	 *  证书类型：指个人或企业
	 */
	private static String certType;
	
	/**
	 *  CA类型
	 */
	private static String caType;
	
	/**
	 *  证书状态（多种状态以逗号[,]分隔）
	 */
	private static String certStatus;
	
	/**
	 *  是否自动更新 0：否，1：是
	 */
	private static String renewAl;
	
	/**
	 *  生效日期（从）YYYY-MM-DD
	 */
	private static String notBeforeFrom;
	
	/**
	 *  生效日期（到）YYYY-MM-DD
	 */
	private static String notBeforeTo;
	
	/**
	 *  截止日期（从）YYYY-MM-DD
	 */
	private static String notAfterFrom;
	
	/**
	 *  截止日期（到）YYYY-MM-DD
	 */
	private static String notAfterTo;
	
	/**
	 *  是否精确匹配,用于查询业务
	 */
	private static String isExact;

	/**
	 * 重置用户参数
	 */
	public static void setDefault() {
		organizationID = "";
		period = "";
		notAfter = "";
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
		userIdentNo = "";
		addIdentNoExt = "";
		userUniqueIdentNo = "";
		email = "";
		isAddEmailExt = "";
		phoneNo = "";
		address = "";
		selfExtValue = "";
		subscriberNameInDN = "";
		userIdentNo = "";
		extensionInfo = "";	
		domainName = "";		
		remark = "";
		userIdentNo = "";		
		extensionInfo = "";
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

	public static String getOrganizationID() {
		return organizationID;
	}

	public static void setOrganizationID(String organizationID) {
		GCert.organizationID = organizationID;
	}

	public static String getPeriod() {
		return period;
	}

	public static void setPeriod(String period) {
		GCert.period = period;
	}

	public static String getPeriodUnit() {
		return periodUnit;
	}

	public static void setPeriodUnit(String periodUnit) {
		GCert.periodUnit = periodUnit;
	}

	public static String getNotAfter() {
		return notAfter;
	}

	public static void setNotAfter(String notAfter) {
		GCert.notAfter = notAfter;
	}

	public static String getTemplateID() {
		return templateID;
	}

	public static void setTemplateID(String templateID) {
		GCert.templateID = templateID;
	}

	public static String getKeyAlg() {
		return keyAlg;
	}

	public static void setKeyAlg(String keyAlg) {
		GCert.keyAlg = keyAlg;
	}

	public static Integer getKeyLength() {
		return keyLength;
	}

	public static void setKeyLength(Integer keyLength) {
		GCert.keyLength = keyLength;
	}

	public static String getCertLevel() {
		return certLevel;
	}

	public static void setCertLevel(String certLevel) {
		GCert.certLevel = certLevel;
	}

	public static String getTemplateID2() {
		return templateID2;
	}

	public static void setTemplateID2(String templateID2) {
		GCert.templateID2 = templateID2;
	}

	public static String getKeyAlg2() {
		return keyAlg2;
	}

	public static void setKeyAlg2(String keyAlg2) {
		GCert.keyAlg2 = keyAlg2;
	}

	public static Integer getKeyLength2() {
		return keyLength2;
	}

	public static void setKeyLength2(Integer keyLength2) {
		GCert.keyLength2 = keyLength2;
	}

	public static String getCertLevel2() {
		return certLevel2;
	}

	public static void setCertLevel2(String certLevel2) {
		GCert.certLevel2 = certLevel2;
	}

	public static String getUserName() {
		return userName;
	}

	public static void setUserName(String userName) {
		GCert.userName = userName;
	}

	public static String getUserNameInDN() {
		return userNameInDN;
	}

	public static void setUserNameInDN(String userNameInDN) {
		GCert.userNameInDN = userNameInDN;
	}

	public static String getCustomerType() {
		return customerType;
	}

	public static void setCustomerType(String customerType) {
		GCert.customerType = customerType;
	}

	public static String getCustomerId() {
		return customerId;
	}

	public static void setCustomerId(String customerId) {
		GCert.customerId = customerId;
	}

	public static String getuId() {
		return uId;
	}

	public static void setuId(String uId) {
		GCert.uId = uId;
	}

	public static String getUserIdentType() {
		return userIdentType;
	}

	public static void setUserIdentType(String userIdentType) {
		GCert.userIdentType = userIdentType;
	}

	public static String getUserIdentNo() {
		return userIdentNo;
	}

	public static void setUserIdentNo(String userIdentNo) {
		GCert.userIdentNo = userIdentNo;
	}

	public static String getAddIdentNoExt() {
		return addIdentNoExt;
	}

	public static void setAddIdentNoExt(String addIdentNoExt) {
		GCert.addIdentNoExt = addIdentNoExt;
	}

	public static String getUserUniqueIdentNo() {
		return userUniqueIdentNo;
	}

	public static void setUserUniqueIdentNo(String userUniqueIdentNo) {
		GCert.userUniqueIdentNo = userUniqueIdentNo;
	}

	public static String getEmail() {
		return email;
	}

	public static void setEmail(String email) {
		GCert.email = email;
	}

	public static String getIsAddEmailExt() {
		return isAddEmailExt;
	}

	public static void setIsAddEmailExt(String isAddEmailExt) {
		GCert.isAddEmailExt = isAddEmailExt;
	}

	public static String getPhoneNo() {
		return phoneNo;
	}

	public static void setPhoneNo(String phoneNo) {
		GCert.phoneNo = phoneNo;
	}

	public static String getAddress() {
		return address;
	}

	public static void setAddress(String address) {
		GCert.address = address;
	}

	public static String getSelfExtValue() {
		return selfExtValue;
	}

	public static void setSelfExtValue(String selfExtValue) {
		GCert.selfExtValue = selfExtValue;
	}

	public static String getSubscriberNameInDN() {
		return subscriberNameInDN;
	}

	public static void setSubscriberNameInDN(String subscriberNameInDN) {
		GCert.subscriberNameInDN = subscriberNameInDN;
	}

	public static String getExtensionInfo() {
		return extensionInfo;
	}

	public static void setExtensionInfo(String extensionInfo) {
		GCert.extensionInfo = extensionInfo;
	}

	public static String getDomainName() {
		return domainName;
	}

	public static void setDomainName(String domainName) {
		GCert.domainName = domainName;
	}

	public static String getRemark() {
		return remark;
	}

	public static void setRemark(String remark) {
		GCert.remark = remark;
	}

	public static String getContract() {
		return contract;
	}

	public static void setContract(String contract) {
		GCert.contract = contract;
	}

	public static String getSslType() {
		return sslType;
	}

	public static void setSslType(String sslType) {
		GCert.sslType = sslType;
	}

	public static String getRemoteIp() {
		return remoteIp;
	}

	public static void setRemoteIp(String remoteIp) {
		GCert.remoteIp = remoteIp;
	}

	public static String getCountry() {
		return country;
	}

	public static void setCountry(String country) {
		GCert.country = country;
	}

	public static String getLocality() {
		return locality;
	}

	public static void setLocality(String locality) {
		GCert.locality = locality;
	}

	public static String getStateOrProvince() {
		return stateOrProvince;
	}

	public static void setStateOrProvince(String stateOrProvince) {
		GCert.stateOrProvince = stateOrProvince;
	}

	public static String getDepartmentNameInCert() {
		return departmentNameInCert;
	}

	public static void setDepartmentNameInCert(String departmentNameInCert) {
		GCert.departmentNameInCert = departmentNameInCert;
	}

	public static String getOrganizationNameInCert() {
		return organizationNameInCert;
	}

	public static void setOrganizationNameInCert(String organizationNameInCert) {
		GCert.organizationNameInCert = organizationNameInCert;
	}

	public static String getEmailInCert() {
		return emailInCert;
	}

	public static void setEmailInCert(String emailInCert) {
		GCert.emailInCert = emailInCert;
	}

	public static String getStreetAddress() {
		return streetAddress;
	}

	public static void setStreetAddress(String streetAddress) {
		GCert.streetAddress = streetAddress;
	}

	public static String getPostalCode() {
		return postalCode;
	}

	public static void setPostalCode(String postalCode) {
		GCert.postalCode = postalCode;
	}

	public static String getBusinessCategory() {
		return businessCategory;
	}

	public static void setBusinessCategory(String businessCategory) {
		GCert.businessCategory = businessCategory;
	}

	public static String getSerialNoInEvCert() {
		return serialNoInEvCert;
	}

	public static void setSerialNoInEvCert(String serialNoInEvCert) {
		GCert.serialNoInEvCert = serialNoInEvCert;
	}

	public static String getJurisdictionLocality() {
		return jurisdictionLocality;
	}

	public static void setJurisdictionLocality(String jurisdictionLocality) {
		GCert.jurisdictionLocality = jurisdictionLocality;
	}

	public static String getJurisdictionStateOrProvince() {
		return jurisdictionStateOrProvince;
	}

	public static void setJurisdictionStateOrProvince(String jurisdictionStateOrProvince) {
		GCert.jurisdictionStateOrProvince = jurisdictionStateOrProvince;
	}

	public static String getJurisdictionCountry() {
		return jurisdictionCountry;
	}

	public static void setJurisdictionCountry(String jurisdictionCountry) {
		GCert.jurisdictionCountry = jurisdictionCountry;
	}

	public static String getDeviceIdentifier() {
		return deviceIdentifier;
	}

	public static void setDeviceIdentifier(String deviceIdentifier) {
		GCert.deviceIdentifier = deviceIdentifier;
	}

	public static String getKeyID() {
		return keyID;
	}

	public static void setKeyID(String keyID) {
		GCert.keyID = keyID;
	}

	public static String getDn() {
		return dn;
	}

	public static void setDn(String dn) {
		GCert.dn = dn;
	}

	public static String getSerialNo() {
		return serialNo;
	}

	public static void setSerialNo(String serialNo) {
		GCert.serialNo = serialNo;
	}

	public static String getSerialNoLen() {
		return serialNoLen;
	}

	public static void setSerialNoLen(String serialNoLen) {
		GCert.serialNoLen = serialNoLen;
	}

	public static String getEncSerialNo() {
		return encSerialNo;
	}

	public static void setEncSerialNo(String encSerialNo) {
		GCert.encSerialNo = encSerialNo;
	}

	public static String getEncSerialNoLen() {
		return encSerialNoLen;
	}

	public static void setEncSerialNoLen(String encSerialNoLen) {
		GCert.encSerialNoLen = encSerialNoLen;
	}

	public static String getAuthCode() {
		return authCode;
	}

	public static void setAuthCode(String authCode) {
		GCert.authCode = authCode;
	}

	public static String getCsr() {
		return csr;
	}

	public static void setCsr(String csr) {
		GCert.csr = csr;
	}

	public static String getCsrSub() {
		return csrSub;
	}

	public static void setCsrSub(String csrSub) {
		GCert.csrSub = csrSub;
	}

	public static String getCertType() {
		return certType;
	}

	public static void setCertType(String certType) {
		GCert.certType = certType;
	}

	public static String getCaType() {
		return caType;
	}

	public static void setCaType(String caType) {
		GCert.caType = caType;
	}

	public static String getCertStatus() {
		return certStatus;
	}

	public static void setCertStatus(String certStatus) {
		GCert.certStatus = certStatus;
	}

	public static String getRenewAl() {
		return renewAl;
	}

	public static void setRenewAl(String renewAl) {
		GCert.renewAl = renewAl;
	}

	public static String getNotBeforeFrom() {
		return notBeforeFrom;
	}

	public static void setNotBeforeFrom(String notBeforeFrom) {
		GCert.notBeforeFrom = notBeforeFrom;
	}

	public static String getNotBeforeTo() {
		return notBeforeTo;
	}

	public static void setNotBeforeTo(String notBeforeTo) {
		GCert.notBeforeTo = notBeforeTo;
	}

	public static String getNotAfterFrom() {
		return notAfterFrom;
	}

	public static void setNotAfterFrom(String notAfterFrom) {
		GCert.notAfterFrom = notAfterFrom;
	}

	public static String getNotAfterTo() {
		return notAfterTo;
	}

	public static void setNotAfterTo(String notAfterTo) {
		GCert.notAfterTo = notAfterTo;
	}

	public static String getIsExact() {
		return isExact;
	}

	public static void setIsExact(String isExact) {
		GCert.isExact = isExact;
	}
}
