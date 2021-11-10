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
		GClazz.thisAToolClass();
	}
	
	/**
	 *  用户名称
	 */
	private static String gSubscriberName = "";
	

	/**
	 *  证件类型
	 */
	private static String gIdentificationType = "";

	/**
	 *  证件号码
	 */
	private static String gIdentificationNo = "";
	
	/**
	 *  邮箱地址
	 */
	private static String gEmail = "";
	
	/**
	 *  是否将Email加到主题备用名(1：加，0：不加)
	 */
	private static String gIsSecurityEmailCert = ""; 
	
	/**
	 *  电话号码
	 */
	private static String gPhoneNo = "";
	
	/**
	 *  地址
	 */
	private static String gAddress = "";
	
	/**
	 *  一类证书的证书主题用户名 或 二类证书的通用名
	 */
	private static String gSubscriberNameInDN = "";
	
	/**
	 *  部门名称（OU）
	 */
	private static String gDepartmentNameInCert = "";
	
	/**
	 *  组织机构名称（O）
	 */
	private static String gOrganizationNameInCert = "";
	
	/**
	 *  营业地点所在地市（L）
	 */
	private static String gLocality = "";
	
	/**
	 *  营业地点所在省(S)
	 */
	private static String gStateOrProvince = "";
	
	/**
	 *  国家
	 */
	private static String gCountry = "";
	
	/**
	 *  安全邮件证书输入项，安全邮件地址(E)
	 */
	private static String gEmailInCert = "";
	
	/**
	 *  营业地点，EV证书中的STREET=
	 */
	private static String gStreetAddress = "";
	
	/**
	 *  营业地点邮政编码，EV证书中的PostalCode=
	 */
	private static String gPostalCode = "";
	
	/**
	 *  注册证件号码，EV证书中的SN=
	 */
	private static String gSerialNoInEvCert = "";
	
	/**
	 *  注册管辖区所在地市，EV证书中的1.3.6.1.4.1.311.60.2.1.1
	 */
	private static String gJurisdictionLocality = "";
	
	/**
	 *  注册管辖区所在省，EV证书中的1.3.6.1.4.1.311.60.2.1.2
	 */
	private static String gJurisdictionStateOrProvince = "";
	
	/**
	 *  注册管辖区所在国家代码，EV证书中的1.3.6.1.4.1.311.60.2.1.3
	 */
	private static String gJurisdictionCountry = "";

	public static String getgSubscriberName() {
		return gSubscriberName;
	}

	public static void setgSubscriberName(String gSubscriberName) {
		GUser.gSubscriberName = gSubscriberName;
	}

	public static String getgIdentificationType() {
		return gIdentificationType;
	}

	public static void setgIdentificationType(String gIdentificationType) {
		GUser.gIdentificationType = gIdentificationType;
	}

	public static String getgIdentificationNo() {
		return gIdentificationNo;
	}

	public static void setgIdentificationNo(String gIdentificationNo) {
		GUser.gIdentificationNo = gIdentificationNo;
	}

	public static String getgEmail() {
		return gEmail;
	}

	public static void setgEmail(String gEmail) {
		GUser.gEmail = gEmail;
	}

	public static String getgIsSecurityEmailCert() {
		return gIsSecurityEmailCert;
	}

	public static void setgIsSecurityEmailCert(String gIsSecurityEmailCert) {
		GUser.gIsSecurityEmailCert = gIsSecurityEmailCert;
	}

	public static String getgPhoneNo() {
		return gPhoneNo;
	}

	public static void setgPhoneNo(String gPhoneNo) {
		GUser.gPhoneNo = gPhoneNo;
	}

	public static String getgAddress() {
		return gAddress;
	}

	public static void setgAddress(String gAddress) {
		GUser.gAddress = gAddress;
	}

	public static String getgSubscriberNameInDN() {
		return gSubscriberNameInDN;
	}

	public static void setgSubscriberNameInDN(String gSubscriberNameInDN) {
		GUser.gSubscriberNameInDN = gSubscriberNameInDN;
	}

	public static String getgDepartmentNameInCert() {
		return gDepartmentNameInCert;
	}

	public static void setgDepartmentNameInCert(String gDepartmentNameInCert) {
		GUser.gDepartmentNameInCert = gDepartmentNameInCert;
	}

	public static String getgOrganizationNameInCert() {
		return gOrganizationNameInCert;
	}

	public static void setgOrganizationNameInCert(String gOrganizationNameInCert) {
		GUser.gOrganizationNameInCert = gOrganizationNameInCert;
	}

	public static String getgLocality() {
		return gLocality;
	}

	public static void setgLocality(String gLocality) {
		GUser.gLocality = gLocality;
	}

	public static String getgStateOrProvince() {
		return gStateOrProvince;
	}

	public static void setgStateOrProvince(String gStateOrProvince) {
		GUser.gStateOrProvince = gStateOrProvince;
	}

	public static String getgCountry() {
		return gCountry;
	}

	public static void setgCountry(String gCountry) {
		GUser.gCountry = gCountry;
	}

	public static String getgEmailInCert() {
		return gEmailInCert;
	}

	public static void setgEmailInCert(String gEmailInCert) {
		GUser.gEmailInCert = gEmailInCert;
	}

	public static String getgStreetAddress() {
		return gStreetAddress;
	}

	public static void setgStreetAddress(String gStreetAddress) {
		GUser.gStreetAddress = gStreetAddress;
	}

	public static String getgPostalCode() {
		return gPostalCode;
	}

	public static void setgPostalCode(String gPostalCode) {
		GUser.gPostalCode = gPostalCode;
	}

	public static String getgSerialNoInEvCert() {
		return gSerialNoInEvCert;
	}

	public static void setgSerialNoInEvCert(String gSerialNoInEvCert) {
		GUser.gSerialNoInEvCert = gSerialNoInEvCert;
	}

	public static String getgJurisdictionLocality() {
		return gJurisdictionLocality;
	}

	public static void setgJurisdictionLocality(String gJurisdictionLocality) {
		GUser.gJurisdictionLocality = gJurisdictionLocality;
	}

	public static String getgJurisdictionStateOrProvince() {
		return gJurisdictionStateOrProvince;
	}

	public static void setgJurisdictionStateOrProvince(String gJurisdictionStateOrProvince) {
		GUser.gJurisdictionStateOrProvince = gJurisdictionStateOrProvince;
	}

	public static String getgJurisdictionCountry() {
		return gJurisdictionCountry;
	}

	public static void setgJurisdictionCountry(String gJurisdictionCountry) {
		GUser.gJurisdictionCountry = gJurisdictionCountry;
	}

	/**
	 *  重置用户参数
	 */
	public static void setDefault() {
		gSubscriberName = "";// 用户名称
		gIdentificationType = "";// 证件类型
		gIdentificationNo = "";// 证件号码
		gEmail = "";// Email
		gIsSecurityEmailCert = "";// 是否将Email加到主题备用名(1：加，0：不加)
		gPhoneNo = "";// 电话
		gAddress = "";// 地址
		gSubscriberNameInDN = ""; // 一类证书的证书主题用户名 或 二类证书的通用名
		gDepartmentNameInCert = "";// 部门名称（OU）
		gOrganizationNameInCert = "";// 组织机构名称（O）
		gLocality = "";// 营业地点所在地市（L）
		gStateOrProvince = "";// 营业地点所在省(S)
		gCountry = "";// 国家
		gEmailInCert = "";// 安全邮件证书输入项，安全邮件地址(E)
		gStreetAddress = "";// 营业地点，EV证书中的STREET=
		gPostalCode = "";// 营业地点邮政编码，EV证书中的PostalCode=
		gSerialNoInEvCert = "";// 注册证件号码，EV证书中的SN=
		gJurisdictionLocality = "";// 注册管辖区所在地市，EV证书中的1.3.6.1.4.1.311.60.2.1.1
		gJurisdictionStateOrProvince = "";// 注册管辖区所在省，EV证书中的1.3.6.1.4.1.311.60.2.1.2
		gJurisdictionCountry = "";// 注册管辖区所在国家代码，EV证书中的1.3.6.1.4.1.311.60.2.1.3
	}
}
