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
	public String getgSubscriberName() {
		return gSubscriberName;
	}
	public void setgSubscriberName(String gSubscriberName) {
		this.gSubscriberName = gSubscriberName;
	}

	/**
	 *  证件类型
	 */
	private String gIdentificationType = "";
	public String getgIdentificationType() {
		return gIdentificationType;
	}
	public void setgIdentificationType(String gIdentificationType) { this.gIdentificationType = gIdentificationType; }

	/**
	 *  证件号码
	 */
	private String gIdentificationNo = "";
	public String getgIdentificationNo() {
		return gIdentificationNo;
	}
	public void setgIdentificationNo(String gIdentificationNo) {
		this.gIdentificationNo = gIdentificationNo;
	}
	
	/**
	 *  邮箱地址
	 */
	private String gEmail = "";
	public String getgEmail() {
		return gEmail;
	}
	public void setgEmail(String gEmail) {
		this.gEmail = gEmail;
	}
	
	/**
	 *  是否将Email加到主题备用名(1：加，0：不加)
	 */
	private String gIsSecurityEmailCert = "";
	public String getgIsSecurityEmailCert() {
		return gIsSecurityEmailCert;
	}
	public void setgIsSecurityEmailCert(String gIsSecurityEmailCert) { this.gIsSecurityEmailCert = gIsSecurityEmailCert; }
	
	/**
	 *  电话号码
	 */
	private String gPhoneNo = "";
	public String getgPhoneNo() {
		return gPhoneNo;
	}
	public void setgPhoneNo(String gPhoneNo) {
		this.gPhoneNo = gPhoneNo;
	}

	/**
	 *  地址
	 */
	private String gAddress = "";
	public String getgAddress() {
		return gAddress;
	}
	public void setgAddress(String gAddress) {
		this.gAddress = gAddress;
	}
	
	/**
	 *  一类证书的证书主题用户名 或 二类证书的通用名
	 */
	private String gSubscriberNameInDN = "";
	public String getgSubscriberNameInDN() {
		return gSubscriberNameInDN;
	}
	public void setgSubscriberNameInDN(String gSubscriberNameInDN) { this.gSubscriberNameInDN = gSubscriberNameInDN; }

	/**
	 *  部门名称（OU）
	 */
	private String gDepartmentNameInCert = "";
	public String getgDepartmentNameInCert() {
		return gDepartmentNameInCert;
	}
	public void setgDepartmentNameInCert(String gDepartmentNameInCert) { this.gDepartmentNameInCert = gDepartmentNameInCert; }
	
	/**
	 *  组织机构名称（O）
	 */
	private String gOrganizationNameInCert = "";
	public String getgOrganizationNameInCert() {
		return gOrganizationNameInCert;
	}
	public void setgOrganizationNameInCert(String gOrganizationNameInCert) { this.gOrganizationNameInCert = gOrganizationNameInCert; }
	
	/**
	 *  营业地点所在地市（L）
	 */
	private String gLocality = "";
	public String getgLocality() {
		return gLocality;
	}
	public void setgLocality(String gLocality) {
		this.gLocality = gLocality;
	}

	/**
	 *  营业地点所在省(S)
	 */
	private String gStateOrProvince = "";
	public String getgStateOrProvince() {
		return gStateOrProvince;
	}
	public void setgStateOrProvince(String gStateOrProvince) {
		this.gStateOrProvince = gStateOrProvince;
	}
	
	/**
	 *  国家
	 */
	private String gCountry = "";
	public String getgCountry() {
		return gCountry;
	}
	public void setgCountry(String gCountry) {
		this.gCountry = gCountry;
	}
	
	/**
	 *  安全邮件证书输入项，安全邮件地址(E)
	 */
	private String gEmailInCert = "";
	public String getgEmailInCert() {
		return gEmailInCert;
	}
	public void setgEmailInCert(String gEmailInCert) {
		this.gEmailInCert = gEmailInCert;
	}
	
	/**
	 *  营业地点，EV证书中的STREET=
	 */
	private String gStreetAddress = "";
	public String getgStreetAddress() {
		return gStreetAddress;
	}
	public void setgStreetAddress(String gStreetAddress) {
		this.gStreetAddress = gStreetAddress;
	}
	
	/**
	 *  营业地点邮政编码，EV证书中的PostalCode=
	 */
	private String gPostalCode = "";
	public String getgPostalCode() {
		return gPostalCode;
	}
	public void setgPostalCode(String gPostalCode) {
		this.gPostalCode = gPostalCode;
	}

	/**
	 *  注册证件号码，EV证书中的SN=
	 */
	private String gSerialNoInEvCert = "";
	public String getgSerialNoInEvCert() {
		return gSerialNoInEvCert;
	}
	public void setgSerialNoInEvCert(String gSerialNoInEvCert) {
		this.gSerialNoInEvCert = gSerialNoInEvCert;
	}
	
	/**
	 *  注册管辖区所在地市，EV证书中的1.3.6.1.4.1.311.60.2.1.1
	 */
	private String gJurisdictionLocality = "";
	public String getgJurisdictionLocality() {
		return gJurisdictionLocality;
	}
	public void setgJurisdictionLocality(String gJurisdictionLocality) { this.gJurisdictionLocality = gJurisdictionLocality; }
	
	/**
	 *  注册管辖区所在省，EV证书中的1.3.6.1.4.1.311.60.2.1.2
	 */
	private String gJurisdictionStateOrProvince = "";
	public String getgJurisdictionStateOrProvince() {
		return gJurisdictionStateOrProvince;
	}
	public void setgJurisdictionStateOrProvince(String gJurisdictionStateOrProvince) { this.gJurisdictionStateOrProvince = gJurisdictionStateOrProvince; }

	/**
	 *  注册管辖区所在国家代码，EV证书中的1.3.6.1.4.1.311.60.2.1.3
	 */
	private String gJurisdictionCountry = "";
	public String getgJurisdictionCountry() {
		return gJurisdictionCountry;
	}
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
