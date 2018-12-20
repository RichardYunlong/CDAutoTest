package AutoTest;

import java.lang.reflect.Field;

/**
 *  用户属性
 */
public class GUser {
	/**
	 *  用户名称
	 */
	public static String gSubscriberName = "";
	
	/**
	 *  证件类型
	 */
	public static String gIdentificationType = "";

	/**
	 *  证件号码
	 */
	public static String gIdentificationNo = "";
	
	/**
	 *  邮箱地址
	 */
	public static String gEmail = "";
	
	/**
	 *  是否将Email加到主题备用名(1：加，0：不加)
	 */
	public static String gIsSecurityEmailCert = ""; 
	
	/**
	 *  电话号码
	 */
	public static String gPhoneNo = "";
	
	/**
	 *  地址
	 */
	public static String gAddress = "";
	
	/**
	 *  一类证书的证书主题用户名 或 二类证书的通用名
	 */
	public static String gSubscriberNameInDN = "";
	
	/**
	 *  部门名称（OU）
	 */
	public static String gDepartmentNameInCert = "";
	
	/**
	 *  组织机构名称（O）
	 */
	public static String gOrganizationNameInCert = "";
	
	/**
	 *  营业地点所在地市（L）
	 */
	public static String gLocality = "";
	
	/**
	 *  营业地点所在省(S)
	 */
	public static String gStateOrProvince = "";
	
	/**
	 *  国家
	 */
	public static String gCountry = "";
	
	/**
	 *  安全邮件证书输入项，安全邮件地址(E)
	 */
	public static String gEmailInCert = "";
	
	/**
	 *  营业地点，EV证书中的STREET=
	 */
	public static String gStreetAddress = "";
	
	/**
	 *  营业地点邮政编码，EV证书中的PostalCode=
	 */
	public static String gPostalCode = "";
	
	/**
	 *  注册证件号码，EV证书中的SN=
	 */
	public static String gSerialNoInEvCert = "";
	
	/**
	 *  注册管辖区所在地市，EV证书中的1.3.6.1.4.1.311.60.2.1.1
	 */
	public static String gJurisdictionLocality = "";
	
	/**
	 *  注册管辖区所在省，EV证书中的1.3.6.1.4.1.311.60.2.1.2
	 */
	public static String gJurisdictionStateOrProvince = "";
	
	/**
	 *  注册管辖区所在国家代码，EV证书中的1.3.6.1.4.1.311.60.2.1.3
	 */
	public static String gJurisdictionCountry = "";

	/**
	 *  显示类成员变量
	 */
	public void showClassParams() {
		try {
			GUser aInstance = new GUser();
			Field[] fields = aInstance.getClass().getDeclaredFields();
			for (int i = 0; i < fields.length; i++) {
				System.out.print("成员变量" + i + "类型 : " + fields[i].getType().getName());
				System.out.print("\t成员变量" + i + "变量名: " + fields[i].getName() + "\t");
				System.out.println("成员变量" + i + "值: " + fields[i].get(aInstance));
			}
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
	}

	/**
	 *  重置用户参数
	 */
	public static void reSetGUser() {
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
