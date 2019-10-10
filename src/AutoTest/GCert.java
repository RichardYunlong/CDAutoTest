package AutoTest;

/**
 * 证书属性
 */
public class GCert {

	/**
	 *  证书所属机构
	 */
	public static String gOrganizationID = "";
	
	/**
	 *  有效期：单位的倍数，例如单位为“月”时，取值“1”，即有效期为“1个月”
	 */
	public static String gPeriod = "";
	
	/**
	 *  有效期单位：证书有效期单位 1-年；2-月；6-日；不传则与被换发的证书有效期一致  用于更新或换发
	 */
	public static String gPeriodUnit = "";
	
	/**
	 *  截止日期（到），用于申请、更新或换发
	 */
	public static String gNotAfter = "";
	
	/**
	 *  一类证书模板号
	 */
	public static String gTemplateID = "";
	
	/**
	 *  一类证书密钥算法
	 */
	public static String gKeyAlg = "";
	
	/**
	 *  一类证书密钥长度
	 */
	public static Integer gKeyLength = 0;// 密钥长度
	
	/**
	 *  一类证书类型：此值专指“级别类型”，S-单证（或普通）；D-双证（或高级）
	 */
	public static String gCertLevel = "";
	
	/**
	 *  二类证书模板号
	 */
	public static String gTemplateID2 = "";
	
	/**
	 *  二类证书密钥算法
	 */
	public static String gKeyAlg2 = "";
	
	/**
	 *  二类证书密钥长度
	 */
	public static Integer gKeyLength2 = 0;// 密钥长度
	
	/**
	 *  二类证书类型：此值专指“级别类型”，S-单证（或普通）；D-双证（或高级）
	 */
	public static String gCertLevel2 = "";
	
	/**
	 *  用户名称
	 */
	public static String gUserName = "";
	
	/**
	 *  用户别名
	 */
	public static String gUserNameInDN = "";
	
	/**
	 *  用户类型:个人证书：1 企业证书：2 设备证书：
	 */
	public static String gCustomerType = "";
	
	/**
	 *  用户号
	 */
	public static String gCustomerId = "";
	
	/**
	 *  用户号
	 */
	public static String gUId = "";
	
	/**
	 *  用户证件类型
	 */
	public static String gUserIdentType = "";
	
	/**
	 *  用户证件号码
	 */
	public static String gUserIdentNo = "";
	
	/**
	 *  是否将证件号码加到主题备用名(1：加，0：不加)
	 */
	public static String  gAddIdentNoExt = "";
	
	/**
	 *  用户唯一标识
	 */
	public static String gUserUniqueIdentNo = "";
	
	/**
	 *  用户邮箱
	 */
	public static String  gEmail = "";
	
	/**
	 *  是否将Email加到主题备用名(1：加，0：不加)
	 */
	public static String  gIsAddEmailExt = "";
	
	/**
	 *  电话
	 */
	public static String  gPhoneNo = "";
	
	/**
	 *  地址
	 */
	public static String  gAddress = "";
	
	
	/**
	 *  自定义扩展域
	 */
	public static String  gSelfExtValue = "";
	
	/**
	 *  一类证书的证书主题用户名 或 二类证书的通用名   
	 */
	public static String  gSubscriberNameInDN = "";
	
	/**
	 *  自定义扩展域 或 服务器证书主域名之外的域名，中间以[,]分隔 
	 */
	public static String gExtensionInfo = "";
	
	/**
	 *  域名信息
	 */
	public static String gDomainName = "";
	
	/**
	 *  备注信息
	 */
	public static String gRemark = "";
	
	/**
	 *  联系人
	 */
	public static String gContract;
	
	/**
	 *  SSL证书类型：1-普通，2-多域名，3-带通配符
	 */
	public static String gSslType = "";
	
	/**
	 *  IP地址
	 */
	public static String gRemoteIp = "";
	
	/**
	 *  国家
	 */
	public static String gCountry = "";
	
	/**
	 *  营业地点所在地市（L）
	 */
	public static String gLocality = "";
	
	/**
	 *  营业地点所在省(S)
	 */
	public static String gStateOrProvince = "";
	
	/**
	 *  部门名称（OU）
	 */
	public static String gDepartmentNameInCert = "";
	
	/**
	 *  组织机构名称（O）
	 */
	public static String gOrganizationNameInCert = "";
	
	/**
	 *  安全邮件证书输入项，安全邮件地址(E)
	 */
	public static String gEmailInCert = "";
	
	/**
	 *  营业地点，EV证书中的STREET
	 */
	public static String gStreetAddress = "";
	
	/**
	 *  营业地点邮政编码，EV证书中的PostalCode
	 */
	public static String gPostalCode = "";
	
	/**
	 *  业务类型，EV证书中的2.5.4.15
	 */
	public static String gBusinessCategory = "";
	
	/**
	 *  注册证件号码，EV证书中的SN
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
	 *  设备标识
	 */
	public static String deviceIdentifier = "";

	/**
	 *  预植证书KeyID
	 */
	public static String gKeyID = "";
	
	/**
	 *  证书DN
	 */
	public static String gDN = "";
	
	/**
	 *  证书序列号
	 */
	public static String gSerialNo = "";
	
	/**
	 *  证书序列号规范长度
	 */
	public static String gSerialNoLen = "";
	
	/**
	 *  证书授权码
	 */
	public static String gAuthCode = "";
	
	/**
	 *  P10申请书
	 */
	public static String gCsr = "";
	
	/**
	 *  P10附加申请书
	 */
	public static String gCsrSub = "";
	
	/**
	 *  证书类型：指个人或企业
	 */
	public static String gCertType = "";
	
	/**
	 *  CA类型
	 */
	public static String gCAType = "";
	
	/**
	 *  证书状态（多种状态以逗号[,]分隔）
	 */
	public static String gCertStatus = "";
	
	/**
	 *  是否自动更新 0：否，1：是
	 */
	public static String gRenewAl = "";
	
	/**
	 *  生效日期（从）YYYY-MM-DD
	 */
	public static String gNotBeforeFrom = "";
	
	/**
	 *  生效日期（到）YYYY-MM-DD
	 */
	public static String gNotBeforeTo = "";
	
	/**
	 *  截止日期（从）YYYY-MM-DD
	 */
	public static String gNotAfterFrom = "";
	
	/**
	 *  截止日期（到）YYYY-MM-DD
	 */
	public static String gNotAfterTo = "";
	
	/**
	 *  是否精确匹配,用于查询业务
	 */
	public static String gisExact = "";

	/**
	 * @see 显示类成员变量
	 */
//	public void showClassParams() {
//		try {
//			GCert aInstance = new GCert();
//			Field[] fields = aInstance.getClass().getDeclaredFields();
//			for (int i = 0; i < fields.length; i++) {
//				System.out.print("成员变量" + i + "类型 : " + fields[i].getType().getName());
//				System.out.print("\t成员变量" + i + "变量名: " + fields[i].getName() + "\t");
//				System.out.println("成员变量" + i + "值: " + fields[i].get(aInstance));
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}

	/**
	 * @see 重置用户参数
	 */
	public static void reSetGCert() {
		gOrganizationID = "";
		gPeriod = "";
		gNotAfter = "";
		gTemplateID = "";
		gKeyAlg = "";
		gKeyLength = 0;
		gCertLevel = "";
		gTemplateID2 = "";
		gKeyAlg2 = "";
		gKeyLength2 = 0;
		gCertLevel2 = "";
		gUserName = "";
		gCustomerType = "";
		gCustomerId = "";
		gUId = "";
		gUserIdentType = "";
		gUserIdentNo = "";
		gAddIdentNoExt = "";
		gUserUniqueIdentNo = "";
		gEmail = "";
		gIsAddEmailExt = "";
		gPhoneNo = "";
		gAddress = "";
		gSelfExtValue = "";
		gSubscriberNameInDN = "";
		gUserIdentNo = "";
		gExtensionInfo = "";	
		gDomainName = "";		
		gRemark = "";
		gUserIdentNo = "";		
		gExtensionInfo = "";
		gUserIdentNo = "";		
		gExtensionInfo = "";
		gSslType = "";
		gRemoteIp = "";
		gCountry = "";
		gLocality = "";
		gStateOrProvince = "";
		gDepartmentNameInCert = "";
		gOrganizationNameInCert = "";			
		gEmailInCert = "";		
		gStreetAddress = "";
		gPostalCode = "";
		gBusinessCategory = "";	
		gSerialNoInEvCert = "";
		gJurisdictionLocality = "";
		gJurisdictionStateOrProvince = "";
		gJurisdictionCountry = "";
		gKeyID = "";
		gDN = "";
		gSerialNo = "";
		gAuthCode = "";
		gCsr = "";
		gCsrSub = "";
		gCertType = "";
		gCAType = "";
		gCertStatus = "";
		gRenewAl = "";
		gNotBeforeFrom = "";
		gNotBeforeTo = "";
		gNotAfterFrom = "";
		gNotAfterTo = "";
		gNotAfter = "";
		gisExact = "";
	}
}
