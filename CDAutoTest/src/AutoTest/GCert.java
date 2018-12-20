package AutoTest;

import java.lang.reflect.Field;

/**
 *  互联网电子身份属性
 */
public class GCert {

	/**
	 *  一类应用OU
	 */
	public static String gApplication = "";
	
	/**
	 *  二类应用名称
	 */
	public static String gApplication2 = "";
	
	/**
	 *  证书所属机构
	 */
	public static String gOrganizationID = "";
	
	/**
	 *  有效期
	 */
	public static String gPeriod = "";
	
	/**
	 *  证书模板号
	 */
	public static String gTemplateID = "";
	
	/**
	 *  二类证书模板号
	 */
	public static String gTemplateID2 = "";
	
	/**
	 *  证书等级
	 */
	public static String gCertLevel = "";
	
	/**
	 *  密钥算法
	 */
	public static String gKeyAlg = "";
	
	/**
	 *  密钥长度
	 */
	public static Integer gKeyLength = 0;
	
	/**
	 *  系统标识（DN规则为5.1时的输入项）
	 */
	public static String gSysIdentification = "";
	
	/**
	 *  用户唯一标识（DN规则为5.1时的输入项）
	 */
	public static String gSubscriberIdentification = "";
	
	/**
	 *  自定义扩展域 或 服务器证书主域名之外的域名，中间以[,]分隔
	 */
	public static String gExtensionInfo = "";
	
	/**
	 *  服务器证书类型：1-普通，2-多域名，3-带通配符
	 */
	public static String gSslType = "";
	
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
	 *  业务类型，EV证书中的2.5.4.15=
	 */
	public static String gBusinessCategory = ""; 

	/**
	 *  目标证书DN
	 */
	public static String gSubjectDN = "";
	
	/**
	 *  目标证书序列号
	 */
	public static String gSerialNo = "";
	
	/**
	 *  目标证书授权码
	 */
	public static String gAuthCode = "";
	
	/**
	 *  P10申请书
	 */
	public static String gCsr = "";
	
	/**
	 *  P10复合申请书
	 */
	public static String gCsrSub = "";
	
	/**
	 *  证书类型
	 */
	public static String gCertType = "";
	
	/**
	 *  证书状态（多种状态以逗号[,]分隔）
	 */
	public static String gCertStatus = "";
	
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
	 *  截止日期（到）YYYY-MM-DD 用于更新或换发
	 */
	public static String gNotAfter = "";

	/**
	 *  预植证书KeyID
	 */
	public static String gKeyID = "";
	
	/**
	 *  预植证书序列号
	 */
	public static String gRefNo = "";
	
	/**
	 *  附加证书秘钥算法
	 */
	public static String gCert2keyAlg = "";
	
	/**
	 *  附加证书秘钥长度
	 */
	public static String gCert2keyLength = ""; 
	
	/**
	 *  附加证书证书等级
	 */
	public static String gCert2keyLevel = "";

	/**
	 *  显示类成员变量
	 */
	public void showClassParams() {
		try {
			GCert aInstance = new GCert();
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
	public static void reSetGCert() {
		gApplication = "";// 一类应用OU
		gApplication2 = "";// 二类应用名称
		gOrganizationID = "";// 机构：证书所属机构
		gPeriod = ""; // 有效期
		gTemplateID = "";// 证书模板号
		gTemplateID2 = "";// 二类证书模板号
		gCertLevel = "";// 证书等级
		gKeyAlg = "";// 密钥算法
		gKeyLength = 0;// 密钥长度
		gSysIdentification = "";// 系统标识（DN规则为5.1时的输入项）
		gSubscriberIdentification = ""; // 用户唯一标识（DN规则为5.1时的输入项）
		gExtensionInfo = "";// 自定义扩展域 或 服务器证书主域名之外的域名，中间以[,]分隔
		gSslType = "";// 服务器证书类型：1-普通，2-多域名，3-带通配符
		gEmailInCert = "";// 安全邮件证书输入项，安全邮件地址(E)
		gStreetAddress = "";// 营业地点，EV证书中的STREET=
		gPostalCode = "";// 营业地点邮政编码，EV证书中的PostalCode=
		gBusinessCategory = ""; // 业务类型，EV证书中的2.5.4.15=

		gSubjectDN = "";// 目标证书DN
		gSerialNo = "";// 目标证书序列号
		gAuthCode = "";// 目标证书授权码
		gCsr = "";// P10申请书
		gCsrSub = "";// P10复合申请书
		gCertType = ""; // 证书类型
		gCertStatus = "";// 证书状态（多种状态以逗号[,]分隔）
		gNotBeforeFrom = ""; // 生效日期（从）YYYY-MM-DD
		gNotBeforeTo = "";// 生效日期（到）YYYY-MM-DD
		gNotAfterFrom = "";// 截止日期（从）YYYY-MM-DD
		gNotAfterTo = "";// 截止日期（到）YYYY-MM-DD
		gNotAfter = "";// 截止日期（到）YYYY-MM-DD 用于更新或换发

		gKeyID = "";// 预植证书KeyID
		gRefNo = "";// 预植证书序列号
		gCert2keyAlg = "";// 附加证书秘钥算法
		gCert2keyLength = "";// 附加证书秘钥长度
		gCert2keyLevel = "";// 附加证书证书等级
	}
}
