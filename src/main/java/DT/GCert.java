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
		GClazz.thisADataUnitClass();
	}

	/**
	 *  证书所属机构
	 */
	private String organizationID;
	/**
	 *  获得证书所属机构
	 *
	 *  @return 证书所属机构
	 */
	public String getOrganizationID() {
		return organizationID;
	}
	/**
	 *  设置证书所属机构
	 *
	 *  @param organizationID 证书所属机构
	 */
	public void setOrganizationID(String organizationID) {
		this.organizationID = organizationID;
	}

	/**
	 *  有效期：单位的倍数，例如单位为“月”时，取值“1”，即有效期为“1个月”
	 */
	private String period;
	/**
	 *  获得有效期：单位的倍数，例如单位为“月”时，取值“1”，即有效期为“1个月”
	 *
	 *  @return 有效期：单位的倍数，例如单位为“月”时，取值“1”，即有效期为“1个月”
	 */
	public String getPeriod() {
		return period;
	}
	/**
	 *  设置有效期：单位的倍数，例如单位为“月”时，取值“1”，即有效期为“1个月”
	 *
	 *  @param period 有效期：单位的倍数，例如单位为“月”时，取值“1”，即有效期为“1个月”
	 */
	public void setPeriod(String period) {
		this.period = period;
	}
	
	/**
	 *  有效期单位：证书有效期单位 1-年；2-月；6-日；不传则与被换发的证书有效期一致  用于更新或换发
	 */
	private String periodUnit;
	/**
	 *  获得有效期单位：证书有效期单位 1-年；2-月；6-日；不传则与被换发的证书有效期一致  用于更新或换发
	 *
	 *  @return 有效期单位：证书有效期单位 1-年；2-月；6-日；不传则与被换发的证书有效期一致  用于更新或换发
	 */
	public String getPeriodUnit() {
		return periodUnit;
	}
	/**
	 *  设置有效期单位：证书有效期单位 1-年；2-月；6-日；不传则与被换发的证书有效期一致  用于更新或换发
	 *
	 *  @param periodUnit 有效期单位：证书有效期单位 1-年；2-月；6-日；不传则与被换发的证书有效期一致  用于更新或换发
	 */
	public void setPeriodUnit(String periodUnit) {
		this.periodUnit = periodUnit;
	}
	
	/**
	 *  截止日期（到），用于申请、更新或换发
	 */
	private String notAfter;
	/**
	 *  获得截止日期（到），用于申请、更新或换发
	 *
	 *  @return 截止日期（到），用于申请、更新或换发
	 */
	public String getNotAfter() {
		return notAfter;
	}
	/**
	 *  设置截止日期（到），用于申请、更新或换发
	 *
	 *  @param notAfter 截止日期（到），用于申请、更新或换发
	 */
	public void setNotAfter(String notAfter) {
		this.notAfter = notAfter;
	}
	
	/**
	 *  一类证书模板号
	 */
	private String templateID;
	/**
	 *  获得一类证书模板号
	 *
	 *  @return 一类证书模板号
	 */
	public String getTemplateID() {
		return templateID;
	}
	/**
	 *  设置一类证书模板号
	 *
	 *  @param templateID 一类证书模板号
	 */
	public void setTemplateID(String templateID) {
		this.templateID = templateID;
	}
	
	/**
	 *  一类证书密钥算法
	 */
	private String keyAlg;
	/**
	 *  获得一类证书密钥算法
	 *
	 *  @return 一类证书密钥算法
	 */
	public String getKeyAlg() {
		return keyAlg;
	}
	/**
	 *  设置一类证书密钥算法
	 *
	 *  @param keyAlg 一类证书密钥算法
	 */
	public void setKeyAlg(String keyAlg) {
		this.keyAlg = keyAlg;
	}

	/**
	 *  一类证书密钥长度
	 */
	private Integer keyLength;
	/**
	 *  获得一类证书密钥长度
	 *
	 *  @return 一类证书密钥长度
	 */
	public Integer getKeyLength() {
		return keyLength;
	}
	/**
	 *  设置一类证书密钥长度
	 *
	 *  @param keyLength 一类证书密钥长度
	 */
	public void setKeyLength(Integer keyLength) {
		this.keyLength = keyLength;
	}
	
	/**
	 *  一类证书类型：此值专指“级别类型”，S-单证（或普通）；D-双证（或高级）
	 */
	private String certLevel;
	/**
	 *  获得一类证书类型：此值专指“级别类型”，S-单证（或普通）；D-双证（或高级）
	 *
	 *  @return 一类证书类型：此值专指“级别类型”，S-单证（或普通）；D-双证（或高级）
	 */
	public String getCertLevel() {
		return certLevel;
	}
	/**
	 *  设置一类证书类型：此值专指“级别类型”，S-单证（或普通）；D-双证（或高级）
	 *
	 *  @param certLevel 一类证书类型：此值专指“级别类型”，S-单证（或普通）；D-双证（或高级）
	 */
	public void setCertLevel(String certLevel) {
		this.certLevel = certLevel;
	}
	
	/**
	 *  二类证书模板号
	 */
	private String templateID2;
	/**
	 *  获得二类证书模板号
	 *
	 *  @return 二类证书模板号
	 */
	public String getTemplateID2() {
		return templateID2;
	}
	/**
	 *  设置二类证书模板号
	 *
	 *  @param templateID2 二类证书模板号
	 */
	public void setTemplateID2(String templateID2) {
		this.templateID2 = templateID2;
	}
	
	/**
	 *  二类证书密钥算法
	 */
	private String keyAlg2;
	/**
	 *  获得二类证书密钥算法
	 *
	 *  @return 二类证书密钥算法
	 */
	public String getKeyAlg2() {
		return keyAlg2;
	}
	/**
	 *  设置二类证书密钥算法
	 *
	 *  @param keyAlg2 二类证书密钥算法
	 */
	public void setKeyAlg2(String keyAlg2) {
		this.keyAlg2 = keyAlg2;
	}

	/**
	 *  二类证书密钥长度
	 */
	private Integer keyLength2;// 密钥长度
	/**
	 *  获得二类证书密钥长度
	 *
	 *  @return 二类证书密钥长度
	 */
	public Integer getKeyLength2() {
		return keyLength2;
	}
	/**
	 *  设置二类证书密钥长度
	 *
	 *  @param keyLength2 二类证书密钥长度
	 */
	public void setKeyLength2(Integer keyLength2) {
		this.keyLength2 = keyLength2;
	}

	/**
	 *  二类证书类型：此值专指“级别类型”，S-单证（或普通）；D-双证（或高级）
	 */
	private String certLevel2;
	/**
	 *  获得二类证书类型：此值专指“级别类型”，S-单证（或普通）；D-双证（或高级）
	 *
	 *  @return 二类证书类型：此值专指“级别类型”，S-单证（或普通）；D-双证（或高级）
	 */
	public String getCertLevel2() {
		return certLevel2;
	}
	/**
	 *  设置二类证书类型：此值专指“级别类型”，S-单证（或普通）；D-双证（或高级）
	 *
	 *  @param certLevel2 二类证书类型：此值专指“级别类型”，S-单证（或普通）；D-双证（或高级）
	 */
	public void setCertLevel2(String certLevel2) {
		this.certLevel2 = certLevel2;
	}
	
	/**
	 *  用户名称
	 */
	private String userName;
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
	 *  用户别名
	 */
	private String userNameInDN;
	/**
	 *  获得用户别名
	 *
	 *  @return 用户别名
	 */
	public String getUserNameInDN() {
		return userNameInDN;
	}
	/**
	 *  设置用户别名
	 *
	 *  @param userNameInDN 用户别名
	 */
	public void setUserNameInDN(String userNameInDN) {
		this.userNameInDN = userNameInDN;
	}
	
	/**
	 *  用户类型:个人证书：1 企业证书：2 设备证书：
	 */
	private String customerType;
	/**
	 *  获得用户类型:个人证书：1 企业证书：2 设备证书
	 *
	 *  @return 用户类型:个人证书：1 企业证书：2 设备证书
	 */
	public String getCustomerType() {
		return customerType;
	}
	/**
	 *  设置用户类型:个人证书：1 企业证书：2 设备证书
	 *
	 *  @param customerType 用户类型:个人证书：1 企业证书：2 设备证书
	 */
	public void setCustomerType(String customerType) {
		this.customerType = customerType;
	}

	/**
	 *  用户号
	 */
	private String customerId;
	/**
	 *  获得用户号
	 *
	 *  @return 用户号
	 */
	public String getCustomerId() {
		return customerId;
	}
	/**
	 *  设置用户号
	 *
	 *  @param customerId 用户号
	 */
	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}
	
	/**
	 *  用户号
	 */
	private String uId;
	/**
	 *  获得用户号
	 *
	 *  @return 用户号
	 */
	public String getuId() {
		return uId;
	}
	/**
	 *  设置用户号
	 *
	 *  @param uId 用户号
	 */
	public void setuId(String uId) {
		this.uId = uId;
	}
	
	/**
	 *  用户证件类型
	 */
	private String userIdentType;
	/**
	 *  获得用户证件类型
	 *
	 *  @return 用户证件类型
	 */
	public String getUserIdentType() {
		return userIdentType;
	}
	/**
	 *  设置用户证件类型
	 *
	 *  @param userIdentType 用户证件类型
	 */
	public void setUserIdentType(String userIdentType) {
		this.userIdentType = userIdentType;
	}

	/**
	 *  用户证件号码
	 */
	private String userIdentNo;
	/**
	 *  获得用户证件号码
	 *
	 *  @return 用户证件号码
	 */
	public String getUserIdentNo() {
		return userIdentNo;
	}
	/**
	 *  设置用户证件号码
	 *
	 *  @param userIdentNo 用户证件号码
	 */
	public void setUserIdentNo(String userIdentNo) {
		this.userIdentNo = userIdentNo;
	}

	/**
	 *  是否将证件号码加到主题备用名(1：加，0：不加)
	 */
	private String  addIdentNoExt;
	/**
	 *  获得是否将证件号码加到主题备用名(1：加，0：不加)
	 *
	 *  @return 是否将证件号码加到主题备用名(1：加，0：不加)
	 */
	public String getAddIdentNoExt() {
		return addIdentNoExt;
	}
	/**
	 *  设置是否将证件号码加到主题备用名(1：加，0：不加)
	 *
	 *  @param addIdentNoExt 是否将证件号码加到主题备用名(1：加，0：不加)
	 */
	public void setAddIdentNoExt(String addIdentNoExt) {
		this.addIdentNoExt = addIdentNoExt;
	}
	
	/**
	 *  用户唯一标识
	 */
	private String userUniqueIdentNo;
	/**
	 *  获得用户唯一标识
	 *
	 *  @return 用户唯一标识
	 */
	public String getUserUniqueIdentNo() {
		return userUniqueIdentNo;
	}
	/**
	 *  设置用户唯一标识
	 *
	 *  @param userUniqueIdentNo 用户唯一标识
	 */
	public void setUserUniqueIdentNo(String userUniqueIdentNo) {
		this.userUniqueIdentNo = userUniqueIdentNo;
	}
	
	/**
	 *  用户邮箱
	 */
	private String  email;
	/**
	 *  获得用户邮箱
	 *
	 *  @return 用户邮箱
	 */
	public String getEmail() {
		return email;
	}
	/**
	 *  设置用户邮箱
	 *
	 *  @param email 用户邮箱
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 *  是否将Email加到主题备用名(1：加，0：不加)
	 */
	private String  isAddEmailExt;
	/**
	 *  获得是否将Email加到主题备用名(1：加，0：不加)
	 *
	 *  @return 是否将Email加到主题备用名(1：加，0：不加)
	 */
	public String getIsAddEmailExt() {
		return isAddEmailExt;
	}
	/**
	 *  设置是否将Email加到主题备用名(1：加，0：不加)
	 *
	 *  @param isAddEmailExt 是否将Email加到主题备用名(1：加，0：不加)
	 */
	public void setIsAddEmailExt(String isAddEmailExt) {
		this.isAddEmailExt = isAddEmailExt;
	}
	
	/**
	 *  电话
	 */
	private String  phoneNo;
	/**
	 *  获得电话
	 *
	 *  @return 电话
	 */
	public String getPhoneNo() {
		return phoneNo;
	}
	/**
	 *  设置电话
	 *
	 *  @param phoneNo 电话
	 */
	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}

	/**
	 *  地址
	 */
	private String  address;
	/**
	 *  获得地址
	 *
	 *  @return 地址
	 */
	public String getAddress() {
		return address;
	}
	/**
	 *  设置地址
	 *
	 *  @param address 地址
	 */
	public void setAddress(String address) {
		this.address = address;
	}

	/**
	 *  自定义扩展域
	 */
	private String  selfExtValue;
	/**
	 *  获得自定义扩展域
	 *
	 *  @return 自定义扩展域
	 */
	public String getSelfExtValue() {
		return selfExtValue;
	}
	/**
	 *  设置自定义扩展域
	 *
	 *  @param selfExtValue 自定义扩展域
	 */
	public void setSelfExtValue(String selfExtValue) {
		this.selfExtValue = selfExtValue;
	}

	/**
	 *  一类证书的证书主题用户名 或 二类证书的通用名   
	 */
	private String  subscriberNameInDN;
	/**
	 *  获得一类证书的证书主题用户名 或 二类证书的通用名
	 *
	 *  @return 一类证书的证书主题用户名 或 二类证书的通用名
	 */
	public String getSubscriberNameInDN() {
		return subscriberNameInDN;
	}
	/**
	 *  设置一类证书的证书主题用户名 或 二类证书的通用名
	 *
	 *  @param subscriberNameInDN 一类证书的证书主题用户名 或 二类证书的通用名
	 */
	public void setSubscriberNameInDN(String subscriberNameInDN) {
		this.subscriberNameInDN = subscriberNameInDN;
	}
	
	/**
	 *  自定义扩展域 或 服务器证书主域名之外的域名，中间以[,]分隔 
	 */
	private String extensionInfo;
	/**
	 *  获得自定义扩展域 或 服务器证书主域名之外的域名，中间以[,]分隔
	 *
	 *  @return 自定义扩展域 或 服务器证书主域名之外的域名，中间以[,]分隔
	 */
	public String getExtensionInfo() {
		return extensionInfo;
	}
	/**
	 *  设置自定义扩展域 或 服务器证书主域名之外的域名，中间以[,]分隔
	 *
	 *  @param extensionInfo 自定义扩展域 或 服务器证书主域名之外的域名，中间以[,]分隔
	 */
	public void setExtensionInfo(String extensionInfo) {
		this.extensionInfo = extensionInfo;
	}
	
	/**
	 *  域名信息
	 */
	private String domainName;
	/**
	 *  获得域名信息
	 *
	 *  @return 域名信息
	 */
	public String getDomainName() {
		return domainName;
	}
	/**
	 *  设置域名信息
	 *
	 *  @param domainName 域名信息
	 */
	public void setDomainName(String domainName) {
		this.domainName = domainName;
	}
	
	/**
	 *  备注信息
	 */
	private String remark;
	/**
	 *  获得备注信息
	 *
	 *  @return 备注信息
	 */
	public String getRemark() {
		return remark;
	}
	/**
	 *  设置备注信息
	 *
	 *  @param remark 备注信息
	 */
	public void setRemark(String remark) {
		this.remark = remark;
	}

	/**
	 *  联系人
	 */
	private String contract;
	/**
	 *  获得联系人
	 *
	 *  @return 联系人
	 */
	public String getContract() {
		return contract;
	}
	/**
	 *  设置联系人
	 *
	 *  @param contract 联系人
	 */
	public void setContract(String contract) {
		this.contract = contract;
	}

	/**
	 *  SSL证书类型：1-普通，2-多域名，3-带通配符
	 */
	private String sslType;
	/**
	 *  获得SSL证书类型：1-普通，2-多域名，3-带通配符
	 *
	 *  @return SSL证书类型：1-普通，2-多域名，3-带通配符
	 */
	public String getSslType() {
		return sslType;
	}
	/**
	 *  设置SSL证书类型：1-普通，2-多域名，3-带通配符
	 *
	 *  @param sslType SSL证书类型：1-普通，2-多域名，3-带通配符
	 */
	public void setSslType(String sslType) {
		this.sslType = sslType;
	}

	/**
	 *  IP地址
	 */
	private String remoteIp;
	/**
	 *  获得IP地址
	 *
	 *  @return IP地址
	 */
	public String getRemoteIp() {
		return remoteIp;
	}
	/**
	 *  设置IP地址
	 *
	 *  @param remoteIp IP地址
	 */
	public void setRemoteIp(String remoteIp) {
		this.remoteIp = remoteIp;
	}
	
	/**
	 *  国家
	 */
	private String country;
	/**
	 *  获得国家
	 *
	 *  @return 国家
	 */
	public String getCountry() {
		return country;
	}
	/**
	 *  设置国家
	 *
	 *  @param country 国家
	 */
	public void setCountry(String country) {
		this.country = country;
	}
	
	/**
	 *  营业地点所在地市（L）
	 */
	private String locality;
	/**
	 *  获得营业地点所在地市（L）
	 *
	 *  @return 营业地点所在地市（L）
	 */
	public String getLocality() {
		return locality;
	}
	/**
	 *  设置营业地点所在地市（L）
	 *
	 *  @param locality 营业地点所在地市（L）
	 */
	public void setLocality(String locality) {
		this.locality = locality;
	}
	
	/**
	 *  营业地点所在省(S)
	 */
	private String stateOrProvince;
	/**
	 *  获得营业地点所在省(S)
	 *
	 *  @return 营业地点所在省(S)
	 */
	public String getStateOrProvince() {
		return stateOrProvince;
	}
	/**
	 *  设置营业地点所在省(S)
	 *
	 *  @param stateOrProvince 营业地点所在省(S)
	 */
	public void setStateOrProvince(String stateOrProvince) {
		this.stateOrProvince = stateOrProvince;
	}
	
	/**
	 *  部门名称（OU）
	 */
	private String departmentNameInCert;
	/**
	 *  获得部门名称（OU）
	 *
	 *  @return 部门名称（OU）
	 */
	public String getDepartmentNameInCert() {
		return departmentNameInCert;
	}
	/**
	 *  设置部门名称（OU）
	 *
	 *  @param departmentNameInCert 部门名称（OU）
	 */
	public void setDepartmentNameInCert(String departmentNameInCert) { this.departmentNameInCert = departmentNameInCert; }
	
	/**
	 *  组织机构名称（O）
	 */
	private String organizationNameInCert;
	/**
	 *  获得组织机构名称（O）
	 *
	 *  @return 组织机构名称（O）
	 */
	public String getOrganizationNameInCert() {
		return organizationNameInCert;
	}
	/**
	 *  设置组织机构名称（O）
	 *
	 *  @param organizationNameInCert 组织机构名称（O）
	 */
	public void setOrganizationNameInCert(String organizationNameInCert) { this.organizationNameInCert = organizationNameInCert; }
	
	/**
	 *  安全邮件证书输入项，安全邮件地址(E)
	 */
	private String emailInCert;
	/**
	 *  获得安全邮件证书输入项，安全邮件地址(E)
	 *
	 *  @return 安全邮件证书输入项，安全邮件地址(E)
	 */
	public String getEmailInCert() {
		return emailInCert;
	}
	/**
	 *  设置安全邮件证书输入项，安全邮件地址(E)
	 *
	 *  @param emailInCert 安全邮件证书输入项，安全邮件地址(E)
	 */
	public void setEmailInCert(String emailInCert) {
		this.emailInCert = emailInCert;
	}
	
	/**
	 *  营业地点，EV证书中的STREET
	 */
	private String streetAddress;
	/**
	 *  获得营业地点，EV证书中的STREET
	 *
	 *  @return 营业地点，EV证书中的STREET
	 */
	public String getStreetAddress() {
		return streetAddress;
	}
	/**
	 *  设置营业地点，EV证书中的STREET
	 *
	 *  @param streetAddress 营业地点，EV证书中的STREET
	 */
	public void setStreetAddress(String streetAddress) {
		this.streetAddress = streetAddress;
	}
	
	/**
	 *  营业地点邮政编码，EV证书中的PostalCode
	 */
	private String postalCode;
	/**
	 *  获得营业地点邮政编码，EV证书中的PostalCode
	 *
	 *  @return 营业地点邮政编码，EV证书中的PostalCode
	 */
	public String getPostalCode() {
		return postalCode;
	}
	/**
	 *  设置营业地点邮政编码，EV证书中的PostalCode
	 *
	 *  @param postalCode 营业地点邮政编码，EV证书中的PostalCode
	 */
	public void setPostalCode(String postalCode) { this.postalCode = postalCode; }
	
	/**
	 *  业务类型，EV证书中的2.5.4.15
	 */
	private String businessCategory;
	/**
	 *  获得业务类型，EV证书中的2.5.4.15
	 *
	 *  @return 业务类型，EV证书中的2.5.4.15
	 */
	public String getBusinessCategory() {
		return businessCategory;
	}
	/**
	 *  设置业务类型，EV证书中的2.5.4.15
	 *
	 *  @param businessCategory 业务类型，EV证书中的2.5.4.15
	 */
	public void setBusinessCategory(String businessCategory) { this.businessCategory = businessCategory; }
	
	/**
	 *  注册证件号码，EV证书中的SN
	 */
	private String serialNoInEvCert;
	/**
	 *  获得注册证件号码，EV证书中的SN
	 *
	 *  @return 注册证件号码，EV证书中的SN
	 */
	public String getSerialNoInEvCert() { return serialNoInEvCert; }
	/**
	 *  设置注册证件号码，EV证书中的SN
	 *
	 *  @param serialNoInEvCert 注册证件号码，EV证书中的SN
	 */
	public void setSerialNoInEvCert(String serialNoInEvCert) { this.serialNoInEvCert = serialNoInEvCert; }
	
	/**
	 *  注册管辖区所在地市，EV证书中的1.3.6.1.4.1.311.60.2.1.1
	 */
	private String jurisdictionLocality;
	/**
	 *  获得注册管辖区所在地市，EV证书中的1.3.6.1.4.1.311.60.2.1.1
	 *
	 *  @return 注册管辖区所在地市，EV证书中的1.3.6.1.4.1.311.60.2.1.1
	 */
	public String getJurisdictionLocality() { return jurisdictionLocality; }

	/**
	 *  设置注册管辖区所在地市，EV证书中的1.3.6.1.4.1.311.60.2.1.1
	 *
	 *  @param jurisdictionLocality 注册管辖区所在地市，EV证书中的1.3.6.1.4.1.311.60.2.1.1
	 */
	public void setJurisdictionLocality(String jurisdictionLocality) { this.jurisdictionLocality = jurisdictionLocality; }
	
	/**
	 *  注册管辖区所在省，EV证书中的1.3.6.1.4.1.311.60.2.1.2
	 */
	private String jurisdictionStateOrProvince;
	/**
	 *  获得注册管辖区所在省，EV证书中的1.3.6.1.4.1.311.60.2.1.2
	 *
	 *  @return 注册管辖区所在省，EV证书中的1.3.6.1.4.1.311.60.2.1.2
	 */
	public String getJurisdictionStateOrProvince() { return jurisdictionStateOrProvince; }
	/**
	 *  设置注册管辖区所在省，EV证书中的1.3.6.1.4.1.311.60.2.1.2
	 *
	 *  @param jurisdictionStateOrProvince 注册管辖区所在省，EV证书中的1.3.6.1.4.1.311.60.2.1.2
	 */
	public void setJurisdictionStateOrProvince(String jurisdictionStateOrProvince) { this.jurisdictionStateOrProvince = jurisdictionStateOrProvince; }
	
	/**
	 *  注册管辖区所在国家代码，EV证书中的1.3.6.1.4.1.311.60.2.1.3
	 */
	private String jurisdictionCountry;
	/**
	 *  获得注册管辖区所在国家代码，EV证书中的1.3.6.1.4.1.311.60.2.1.3
	 *
	 *  @return 注册管辖区所在国家代码，EV证书中的1.3.6.1.4.1.311.60.2.1.3
	 */
	public String getJurisdictionCountry() { return jurisdictionCountry; }
	/**
	 *  设置注册管辖区所在国家代码，EV证书中的1.3.6.1.4.1.311.60.2.1.3
	 *
	 *  @param jurisdictionCountry 注册管辖区所在国家代码，EV证书中的1.3.6.1.4.1.311.60.2.1.3
	 */
	public void setJurisdictionCountry(String jurisdictionCountry) { this.jurisdictionCountry = jurisdictionCountry; }
	
	/**
	 *  设备标识
	 */
	private String deviceIdentifier;
	/**
	 *  获得设备标识
	 *
	 *  @return 设备标识
	 */
	public String getDeviceIdentifier() { return deviceIdentifier; }
	/**
	 *  设置设备标识
	 *
	 *  @param deviceIdentifier 设备标识
	 */
	public void setDeviceIdentifier(String deviceIdentifier) { this.deviceIdentifier = deviceIdentifier; }

	/**
	 *  预植证书KeyID
	 */
	private String keyID;
	/**
	 *  获得预植证书KeyID
	 *
	 *  @return 预植证书KeyID
	 */
	public String getKeyID() { return keyID; }
	/**
	 *  设置预植证书KeyID
	 *
	 *  @param keyID 预植证书KeyID
	 */
	public void setKeyID(String keyID) { this.keyID = keyID; }
	
	/**
	 *  证书DN
	 */
	private String dn;
	/**
	 *  获得证书DN
	 *
	 *  @return 证书DN
	 */
	public String getDn() { return dn; }
	/**
	 *  设置证书DN
	 *
	 *  @param dn 证书DN
	 */
	public void setDn(String dn) { this.dn = dn; }
	
	/**
	 *  证书序列号
	 */
	private String serialNo;
	/**
	 *  获得证书序列号
	 *
	 *  @return 证书序列号
	 */
	public String getSerialNo() { return serialNo; }
	/**
	 *  设置证书序列号
	 *
	 *  @param serialNo 证书序列号
	 */
	public void setSerialNo(String serialNo) { this.serialNo = serialNo; }
	
	/**
	 *  证书序列号规范长度
	 */
	private String serialNoLen;
	/**
	 *  获得证书序列号规范长度
	 *
	 *  @return 证书序列号规范长度
	 */
	public String getSerialNoLen() { return serialNoLen; }
	/**
	 *  设置证书序列号规范长度
	 *
	 *  @param serialNoLen 证书序列号规范长度
	 */
	public void setSerialNoLen(String serialNoLen) { this.serialNoLen = serialNoLen; }
	
	/**
	 *  加密证书序列号
	 */
	private String encSerialNo;
	/**
	 *  获得加密证书序列号
	 *
	 *  @return 加密证书序列号
	 */
	public String getEncSerialNo() { return encSerialNo; }
	/**
	 *  设置加密证书序列号
	 *
	 *  @param encSerialNo 加密证书序列号
	 */
	public void setEncSerialNo(String encSerialNo) { this.encSerialNo = encSerialNo; }
	
	/**
	 *  加密证书序列号规范长度
	 */
	private String encSerialNoLen;
	/**
	 *  获得加密证书序列号规范长度
	 *
	 *  @return 加密证书序列号规范长度
	 */
	public String getEncSerialNoLen() { return encSerialNoLen; }
	/**
	 *  设置加密证书序列号规范长度
	 *
	 *  @param encSerialNoLen 加密证书序列号规范长度
	 */
	public void setEncSerialNoLen(String encSerialNoLen) { this.encSerialNoLen = encSerialNoLen; }
	
	/**
	 *  证书授权码
	 */
	private String authCode;
	/**
	 *  获得证书授权码
	 *
	 *  @return 证书授权码
	 */
	public String getAuthCode() { return authCode; }
	/**
	 *  设置证书授权码
	 *
	 *  @param authCode 证书授权码
	 */
	public void setAuthCode(String authCode) { this.authCode = authCode; }
	
	/**
	 *  P10申请书
	 */
	private String csr;
	/**
	 *  获得P10申请书
	 *
	 *  @return P10申请书
	 */
	public String getCsr() { return csr; }
	/**
	 *  设置P10申请书
	 *
	 *  @param csr P10申请书
	 */
	public void setCsr(String csr) { this.csr = csr; }
	
	/**
	 *  P10附加申请书
	 */
	private String csrSub;
	/**
	 *  获得P10附加申请书
	 *
	 *  @return P10附加申请书
	 */
	public String getCsrSub() { return csrSub; }
	/**
	 *  设置P10附加申请书
	 *
	 *  @param csrSub P10附加申请书
	 */
	public void setCsrSub(String csrSub) { this.csrSub = csrSub; }
	
	/**
	 *  证书类型：指个人或企业
	 */
	private String certType;
	/**
	 *  获得证书类型：指个人或企业
	 *
	 *  @return 证书类型：指个人或企业
	 */
	public String getCertType() { return certType; }
	/**
	 *  设置证书类型：指个人或企业
	 *
	 *  @param certType 证书类型：指个人或企业
	 */
	public void setCertType(String certType) { this.certType = certType; }
	
	/**
	 *  CA类型
	 */
	private String caType;
	/**
	 *  获得CA类型
	 *
	 *  @return CA类型
	 */
	public String getCaType() { return caType; }
	/**
	 *  设置CA类型
	 *
	 *  @param caType CA类型
	 */
	public void setCaType(String caType) { this.caType = caType; }
	
	/**
	 *  证书状态（多种状态以逗号[,]分隔）
	 */
	private String certStatus;
	/**
	 *  获得证书状态（多种状态以逗号[,]分隔）
	 *
	 *  @return 证书状态（多种状态以逗号[,]分隔）
	 */
	public String getCertStatus() { return certStatus; }
	/**
	 *  设置证书状态（多种状态以逗号[,]分隔）
	 *
	 *  @param certStatus 证书状态（多种状态以逗号[,]分隔）
	 */
	public void setCertStatus(String certStatus) { this.certStatus = certStatus; }
	
	/**
	 *  是否自动更新 0：否，1：是
	 */
	private String renewAl;
	/**
	 *  获得是否自动更新 0：否，1：是
	 *
	 *  @return 是否自动更新 0：否，1：是
	 */
	public String getRenewAl() { return renewAl; }
	/**
	 *  设置是否自动更新 0：否，1：是
	 *
	 *  @param renewAl 是否自动更新 0：否，1：是
	 */
	public void setRenewAl(String renewAl) { this.renewAl = renewAl; }
	
	/**
	 *  生效日期（从）YYYY-MM-DD
	 */
	private String notBeforeFrom;
	/**
	 *  获得生效日期（从）YYYY-MM-DD
	 *
	 *  @return 生效日期（从）YYYY-MM-DD
	 */
	public String getNotBeforeFrom() { return notBeforeFrom; }
	/**
	 *  设置生效日期（从）YYYY-MM-DD
	 *
	 *  @param notBeforeFrom 生效日期（从）YYYY-MM-DD
	 */
	public void setNotBeforeFrom(String notBeforeFrom) { this.notBeforeFrom = notBeforeFrom; }
	
	/**
	 *  生效日期（到）YYYY-MM-DD
	 */
	private String notBeforeTo;
	/**
	 *  获得生效日期（到）YYYY-MM-DD
	 *
	 *  @return 生效日期（到）YYYY-MM-DD
	 */
	public String getNotBeforeTo() { return notBeforeTo; }
	/**
	 *  设置生效日期（到）YYYY-MM-DD
	 *
	 *  @param notBeforeTo 生效日期（到）YYYY-MM-DD
	 */
	public void setNotBeforeTo(String notBeforeTo) { this.notBeforeTo = notBeforeTo; }
	
	/**
	 *  截止日期（从）YYYY-MM-DD
	 */
	private String notAfterFrom;
	/**
	 *  获得截止日期（从）YYYY-MM-DD
	 *
	 *  @return 截止日期（从）YYYY-MM-DD
	 */
	public String getNotAfterFrom() { return notAfterFrom; }
	/**
	 *  设置截止日期（从）YYYY-MM-DD
	 *
	 *  @param notAfterFrom 截止日期（从）YYYY-MM-DD
	 */
	public void setNotAfterFrom(String notAfterFrom) { this.notAfterFrom = notAfterFrom; }
	
	/**
	 *  截止日期（到）YYYY-MM-DD
	 */
	private String notAfterTo;
	/**
	 *  获得截止日期（到）YYYY-MM-DD
	 *
	 *  @return 截止日期（到）YYYY-MM-DD
	 */
	public String getNotAfterTo() { return notAfterTo; }
	/**
	 *  设置截止日期（到）YYYY-MM-DD
	 *
	 *  @param notAfterTo 截止日期（到）YYYY-MM-DD
	 */
	public void setNotAfterTo(String notAfterTo) { this.notAfterTo = notAfterTo; }
	
	/**
	 *  是否精确匹配,用于查询业务
	 */
	private String isExact;
	/**
	 *  获得是否精确匹配,用于查询业务
	 *
	 *  @return 是否精确匹配,用于查询业务
	 */
	public String getIsExact() { return isExact; }
	/**
	 *  设置是否精确匹配,用于查询业务
	 *
	 *  @param isExact 是否精确匹配,用于查询业务
	 */
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
