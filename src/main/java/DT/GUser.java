package DT;

import Base.GClazz;

/**
 *  用户
 */
public class GUser {
	
    /**
     *  构造函数
     */
	private GUser(){
		GClazz.thisADataUnitClass();
	}
	
	/**
	 *  用户名称
	 */
	private String gSubscriberName = "";
	/**
	 *  获得用户名称
	 *
	 *  @return 用户名称
	 */
	public String getgSubscriberName() {
		return gSubscriberName;
	}
	/**
	 *  设置用户名称
	 *
	 *  @param gSubscriberName 用户名称
	 */
	public void setgSubscriberName(String gSubscriberName) {
		this.gSubscriberName = gSubscriberName;
	}

	/**
	 *  证件类型
	 */
	private String gIdentificationType = "";
	/**
	 *  获得证件类型
	 *
	 *  @return 证件类型
	 */
	public String getgIdentificationType() {
		return gIdentificationType;
	}
	/**
	 *  设置证件类型
	 *
	 *  @param gIdentificationType 证件类型
	 */
	public void setgIdentificationType(String gIdentificationType) { this.gIdentificationType = gIdentificationType; }

	/**
	 *  证件号码
	 */
	private String gIdentificationNo = "";
	/**
	 *  获得证件号码
	 *
	 *  @return 证件号码
	 */
	public String getgIdentificationNo() {
		return gIdentificationNo;
	}
	/**
	 *  设置证件号码
	 *
	 *  @param gIdentificationNo 证件号码
	 */
	public void setgIdentificationNo(String gIdentificationNo) {
		this.gIdentificationNo = gIdentificationNo;
	}
	
	/**
	 *  邮箱地址
	 */
	private String gEmail = "";
	/**
	 *  获得邮箱地址
	 *
	 *  @return 邮箱地址
	 */
	public String getgEmail() {
		return gEmail;
	}
	/**
	 *  设置邮箱地址
	 *
	 *  @param gEmail 邮箱地址
	 */
	public void setgEmail(String gEmail) {
		this.gEmail = gEmail;
	}
	
	/**
	 *  是否将Email加到主题备用名(1：加，0：不加)
	 */
	private String gIsSecurityEmailCert = "";
	/**
	 *  获得是否将Email加到主题备用名(1：加，0：不加)
	 *
	 *  @return 是否将Email加到主题备用名(1：加，0：不加)
	 */
	public String getgIsSecurityEmailCert() {
		return gIsSecurityEmailCert;
	}
	/**
	 *  设置是否将Email加到主题备用名(1：加，0：不加)
	 *
	 *  @param gIsSecurityEmailCert 是否将Email加到主题备用名(1：加，0：不加)
	 */
	public void setgIsSecurityEmailCert(String gIsSecurityEmailCert) { this.gIsSecurityEmailCert = gIsSecurityEmailCert; }
	
	/**
	 *  电话号码
	 */
	private String gPhoneNo = "";
	/**
	 *  获得电话号码
	 *
	 *  @return 电话号码
	 */
	public String getgPhoneNo() {
		return gPhoneNo;
	}
	/**
	 *  设置电话号码
	 *
	 *  @param gPhoneNo 电话号码
	 */
	public void setgPhoneNo(String gPhoneNo) {
		this.gPhoneNo = gPhoneNo;
	}

	/**
	 *  地址
	 */
	private String gAddress = "";
	/**
	 *  获得地址
	 *
	 *  @return 地址
	 */
	public String getgAddress() {
		return gAddress;
	}
	/**
	 *  设置地址
	 *
	 *  @param gAddress 地址
	 */
	public void setgAddress(String gAddress) {
		this.gAddress = gAddress;
	}
	
	/**
	 *  一类证书的证书主题用户名 或 二类证书的通用名
	 */
	private String gSubscriberNameInDN = "";
	/**
	 *  获得一类证书的证书主题用户名 或 二类证书的通用名
	 *
	 *  @return 一类证书的证书主题用户名 或 二类证书的通用名
	 */
	public String getgSubscriberNameInDN() {
		return gSubscriberNameInDN;
	}
	/**
	 *  设置一类证书的证书主题用户名 或 二类证书的通用名
	 *
	 *  @param gSubscriberNameInDN 一类证书的证书主题用户名 或 二类证书的通用名
	 */
	public void setgSubscriberNameInDN(String gSubscriberNameInDN) { this.gSubscriberNameInDN = gSubscriberNameInDN; }

	/**
	 *  部门名称（OU）
	 */
	private String gDepartmentNameInCert = "";
	/**
	 *  获得部门名称（OU）
	 *
	 *  @return 部门名称（OU）
	 */
	public String getgDepartmentNameInCert() {
		return gDepartmentNameInCert;
	}
	/**
	 *  设置部门名称（OU）
	 *
	 *  @param gDepartmentNameInCert 部门名称（OU）
	 */
	public void setgDepartmentNameInCert(String gDepartmentNameInCert) { this.gDepartmentNameInCert = gDepartmentNameInCert; }
	
	/**
	 *  组织机构名称（O）
	 */
	private String gOrganizationNameInCert = "";
	/**
	 *  获得组织机构名称（O）
	 *
	 *  @return 组织机构名称（O）
	 */
	public String getgOrganizationNameInCert() {
		return gOrganizationNameInCert;
	}
	/**
	 *  设置组织机构名称（O）
	 *
	 *  @param gOrganizationNameInCert 组织机构名称（O）
	 */
	public void setgOrganizationNameInCert(String gOrganizationNameInCert) { this.gOrganizationNameInCert = gOrganizationNameInCert; }
	
	/**
	 *  营业地点所在地市（L）
	 */
	private String gLocality = "";
	/**
	 *  获得营业地点所在地市（L）
	 *
	 *  @return 营业地点所在地市（L）
	 */
	public String getgLocality() {
		return gLocality;
	}
	/**
	 *  设置营业地点所在地市（L）
	 *
	 *  @param gLocality 营业地点所在地市（L）
	 */
	public void setgLocality(String gLocality) {
		this.gLocality = gLocality;
	}

	/**
	 *  营业地点所在省(S)
	 */
	private String gStateOrProvince = "";
	/**
	 *  获得营业地点所在省(S)
	 *
	 *  @return 营业地点所在省(S)
	 */
	public String getgStateOrProvince() {
		return gStateOrProvince;
	}
	/**
	 *  设置营业地点所在省(S)
	 *
	 *  @param gStateOrProvince 营业地点所在省(S)
	 */
	public void setgStateOrProvince(String gStateOrProvince) {
		this.gStateOrProvince = gStateOrProvince;
	}
	
	/**
	 *  国家
	 */
	private String gCountry = "";
	/**
	 *  获得国家
	 *
	 *  @return 国家
	 */
	public String getgCountry() {
		return gCountry;
	}
	/**
	 *  设置国家
	 *
	 *  @param gCountry 国家
	 */
	public void setgCountry(String gCountry) {
		this.gCountry = gCountry;
	}
	
	/**
	 *  安全邮件证书输入项，安全邮件地址(E)
	 */
	private String gEmailInCert = "";
	/**
	 *  获得安全邮件证书输入项，安全邮件地址(E)
	 *
	 *  @return 安全邮件证书输入项，安全邮件地址(E)
	 */
	public String getgEmailInCert() {
		return gEmailInCert;
	}
	/**
	 *  设置安全邮件证书输入项，安全邮件地址(E)
	 *
	 *  @param gEmailInCert 安全邮件证书输入项，安全邮件地址(E)
	 */
	public void setgEmailInCert(String gEmailInCert) {
		this.gEmailInCert = gEmailInCert;
	}
	
	/**
	 *  营业地点，EV证书中的STREET=
	 */
	private String gStreetAddress = "";
	/**
	 *  获得营业地点，EV证书中的STREET=
	 *
	 *  @return 营业地点，EV证书中的STREET=
	 */
	public String getgStreetAddress() {
		return gStreetAddress;
	}
	/**
	 *  设置营业地点，EV证书中的STREET=
	 *
	 *  @param gStreetAddress 营业地点，EV证书中的STREET=
	 */
	public void setgStreetAddress(String gStreetAddress) {
		this.gStreetAddress = gStreetAddress;
	}
	
	/**
	 *  营业地点邮政编码，EV证书中的PostalCode=
	 */
	private String gPostalCode = "";
	/**
	 *  获得营业地点邮政编码，EV证书中的PostalCode=
	 *
	 *  @return 营业地点邮政编码，EV证书中的PostalCode=
	 */
	public String getgPostalCode() {
		return gPostalCode;
	}
	/**
	 *  设置营业地点邮政编码，EV证书中的PostalCode=
	 *
	 *  @param gPostalCode 营业地点邮政编码，EV证书中的PostalCode=
	 */
	public void setgPostalCode(String gPostalCode) {
		this.gPostalCode = gPostalCode;
	}

	/**
	 *  注册证件号码，EV证书中的SN=
	 */
	private String gSerialNoInEvCert = "";
	/**
	 *  获得注册证件号码，EV证书中的SN=
	 *
	 *  @return 注册证件号码，EV证书中的SN=
	 */
	public String getgSerialNoInEvCert() {
		return gSerialNoInEvCert;
	}
	/**
	 *  设置注册证件号码，EV证书中的SN=
	 *
	 *  @param gSerialNoInEvCert 注册证件号码，EV证书中的SN=
	 */
	public void setgSerialNoInEvCert(String gSerialNoInEvCert) {
		this.gSerialNoInEvCert = gSerialNoInEvCert;
	}
	
	/**
	 *  注册管辖区所在地市，EV证书中的1.3.6.1.4.1.311.60.2.1.1
	 */
	private String gJurisdictionLocality = "";
	/**
	 *  获得注册管辖区所在地市，EV证书中的1.3.6.1.4.1.311.60.2.1.1
	 *
	 *  @return 注册管辖区所在地市，EV证书中的1.3.6.1.4.1.311.60.2.1.1
	 */
	public String getgJurisdictionLocality() {
		return gJurisdictionLocality;
	}
	/**
	 *  设置注册管辖区所在地市，EV证书中的1.3.6.1.4.1.311.60.2.1.1
	 *
	 *  @param gJurisdictionLocality 注册管辖区所在地市，EV证书中的1.3.6.1.4.1.311.60.2.1.1
	 */
	public void setgJurisdictionLocality(String gJurisdictionLocality) { this.gJurisdictionLocality = gJurisdictionLocality; }
	
	/**
	 *  注册管辖区所在省，EV证书中的1.3.6.1.4.1.311.60.2.1.2
	 */
	private String gJurisdictionStateOrProvince = "";
	/**
	 *  获得注册管辖区所在省，EV证书中的1.3.6.1.4.1.311.60.2.1.2
	 *
	 *  @return 注册管辖区所在省，EV证书中的1.3.6.1.4.1.311.60.2.1.2
	 */
	public String getgJurisdictionStateOrProvince() {
		return gJurisdictionStateOrProvince;
	}
	/**
	 *  设置注册管辖区所在省，EV证书中的1.3.6.1.4.1.311.60.2.1.2
	 *
	 *  @param gJurisdictionStateOrProvince 注册管辖区所在省，EV证书中的1.3.6.1.4.1.311.60.2.1.2
	 */
	public void setgJurisdictionStateOrProvince(String gJurisdictionStateOrProvince) { this.gJurisdictionStateOrProvince = gJurisdictionStateOrProvince; }

	/**
	 *  注册管辖区所在国家代码，EV证书中的1.3.6.1.4.1.311.60.2.1.3
	 */
	private String gJurisdictionCountry = "";
	/**
	 *  获得注册管辖区所在国家代码，EV证书中的1.3.6.1.4.1.311.60.2.1.3
	 *
	 *  @return 注册管辖区所在国家代码，EV证书中的1.3.6.1.4.1.311.60.2.1.3
	 */
	public String getgJurisdictionCountry() {
		return gJurisdictionCountry;
	}
	/**
	 *  设置注册管辖区所在国家代码，EV证书中的1.3.6.1.4.1.311.60.2.1.3
	 *
	 *  @param gJurisdictionCountry 注册管辖区所在国家代码，EV证书中的1.3.6.1.4.1.311.60.2.1.3
	 */
	public void setgJurisdictionCountry(String gJurisdictionCountry) { this.gJurisdictionCountry = gJurisdictionCountry; }

	/**
	 *  重置用户参数
	 */
	public void setDefault() {
		this.gSubscriberName = "";// 用户名称
		this.gIdentificationType = "";// 证件类型
		this.gIdentificationNo = "";// 证件号码
		this.gEmail = "";// Email
		this.gIsSecurityEmailCert = "";// 是否将Email加到主题备用名(1：加，0：不加)
		this.gPhoneNo = "";// 电话
		this.gAddress = "";// 地址
		this.gSubscriberNameInDN = ""; // 一类证书的证书主题用户名 或 二类证书的通用名
		this.gDepartmentNameInCert = "";// 部门名称（OU）
		this.gOrganizationNameInCert = "";// 组织机构名称（O）
		this.gLocality = "";// 营业地点所在地市（L）
		this.gStateOrProvince = "";// 营业地点所在省(S)
		this.gCountry = "";// 国家
		this.gEmailInCert = "";// 安全邮件证书输入项，安全邮件地址(E)
		this.gStreetAddress = "";// 营业地点，EV证书中的STREET=
		this.gPostalCode = "";// 营业地点邮政编码，EV证书中的PostalCode=
		this.gSerialNoInEvCert = "";// 注册证件号码，EV证书中的SN=
		this.gJurisdictionLocality = "";// 注册管辖区所在地市，EV证书中的1.3.6.1.4.1.311.60.2.1.1
		this.gJurisdictionStateOrProvince = "";// 注册管辖区所在省，EV证书中的1.3.6.1.4.1.311.60.2.1.2
		this.gJurisdictionCountry = "";// 注册管辖区所在国家代码，EV证书中的1.3.6.1.4.1.311.60.2.1.3
	}
}
