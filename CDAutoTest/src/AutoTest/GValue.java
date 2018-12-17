package AutoTest;

public class GValue {

	public static String RandomStringByLength = "";

	/**
	 * @Description 用户名长度闭区间右值+1，全长150+3
	 */
	public static final String PersonName_OverFlow = "测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试" + "测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试"
			+ "测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试" + "测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试" + "测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试"
			+ "测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试" + "用户名";

	/**
	 * @Description: 用户别名长度闭区间右值+1，全长150+3
	 */
	public static final String UserNameInDN_OverFlow = PersonName_OverFlow;

	/**
	 * @Description: 用户标识长度闭区间右值+1，全长150+3
	 */
	public static final String UserIdent_OverFlow = PersonName_OverFlow;

	/**
	 * @Description: 证件类型长度闭区间右值+1，全长80+3
	 */
	public static final String IdentType_OverFlow = "1111111111111111111111111111111111111111"
			+ "1111111111111111111111111111111111111111" + "zjh";

	/**
	 * @Description: 证件号长度闭区间右值+1，全长80+3
	 */
	public static final String IdentNo_OverFlow = IdentType_OverFlow;

	/**
	 * @Description: 电话号码长度闭区间右值+1，全长150+3
	 */
	public static final String PhoneNo_OverFlow = PersonName_OverFlow;

	/**
	 * @Description: 邮箱长度闭区间右值+1，全长全长200+5+4
	 */
	public static final String Email_OverFlow = "test@" + "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"
			+ "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa" + "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"
			+ "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa" + "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa" + ".com";
	/**
	 * @Description: 图片地址长度闭区间右值+1，全长全长200+7+8
	 */
	public static final String Img_OverFlow = "./CFCA/" + "CFCA/CFCA/CFCA/CFCA/CFCA/CFCA/CFCA/CFCA/CFCA/CFCA/"
			+ "CFCA/CFCA/CFCA/CFCA/CFCA/CFCA/CFCA/CFCA/CFCA/CFCA/"
			+ "CFCA/CFCA/CFCA/CFCA/CFCA/CFCA/CFCA/CFCA/CFCA/CFCA/"
			+ "CFCA/CFCA/CFCA/CFCA/CFCA/CFCA/CFCA/CFCA/CFCA/CFCA/" + "test.jpg";

	/**
	 * @Description: 地址长度闭区间右值+1，全长100+3
	 */
	public static final String Adrress_OverFlow = "测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试"
			+ "测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试" + "测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试"
			+ "测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试" + "测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试"
			+ "测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试" + "测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试"
			+ "测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试" + "测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试"
			+ "测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试" + "测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试"
			+ "测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试" + "测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试"
			+ "测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试" + "测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试"
			+ "测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试" + "测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试"
			+ "测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试" + "测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试"
			+ "测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试" + "测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试"
			+ "测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试" + "测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试"
			+ "测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试" + "测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试" + "用户名";

	/**
	 * @Description: 签署地长度闭区间右值+1，全长100+4
	 */
	public static final String Location_OverFlow = "测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试" + "测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试"
			+ "测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试" + "测试测试测试测试测试" + "签署地址";

	/**
	 * @Description: 秘钥算法长度闭区间右值+1，全长80+3
	 */
	public static final String KeyAlg_OverFlow = IdentType_OverFlow;

	/**
	 * @Description: 秘钥长度闭区间右值+1
	 */
	public static final int KeyLength_OverFlow = 99999999;

	/**
	 * @Description: 机构编码长度闭区间右值+1，全长80+3
	 */
	public static final String BranchCode_OverFlow = IdentType_OverFlow;

	/**
	 * @Description: 有效期长度闭区间右值+1，100年
	 */
	public static final String Guration_OverFlow = "2400";

	/**
	 * @Description: 自定义扩展域长度闭区间右值+1，全长1024+5
	 */
	public static final String SelfExtValue_OverFlow = "testtesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttest"
			+ "testtesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttest"
			+ "testtesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttest"
			+ "testtesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttest"
			+ "testtesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttest"
			+ "testtesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttest"
			+ "testtesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttest"
			+ "testtesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttest"
			+ "testtesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttest"
			+ "testtesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttest"
			+ "testtesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttest"
			+ "testtesttesttesttesttesttest" + "hewei";
	/**
	 * @Description: 申请书长度闭区间右值+1，全长4096+5
	 */
	public static final String P10_OverFlow = SelfExtValue_OverFlow + SelfExtValue_OverFlow + SelfExtValue_OverFlow
			+ SelfExtValue_OverFlow + "hewei";

	/**
	 * @Description: 设备标识长度闭区间右值+1，全长1024+5
	 */
	public static final String DeviceIdentifier_OverFlow = SelfExtValue_OverFlow;

	/**
	 * @Description: 部门名称长度闭区间右值+1，全长1024+5
	 */
	public static final String DepartmentNameInCert_OverFlow = SelfExtValue_OverFlow;

	/**
	 * @Description: 组织机构名称长度闭区间右值+1，全长1024+5
	 */
	public static final String OrganizationNameInCert_OverFlow = SelfExtValue_OverFlow;

	/**
	 * @Description: 营业地点所在地市长度闭区间右值+1，全长1024+5
	 */
	public static final String Locality_OverFlow = SelfExtValue_OverFlow;

	/**
	 * @Description: 营业地点所在省长度闭区间右值+1，全长1024+5
	 */
	public static final String StateOrProvince_OverFlow = SelfExtValue_OverFlow;

	// 安心签专用

	/**
	 * @Description: 签署地长度闭区间右值+1，全长100+3
	 */
	public static final String ContactName_OverFlow = "测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试"
			+ "测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试" + "测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试" + "测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试"
			+ "测试测试测试测试测试测试" + "用户名";

	/**
	 * @Description: 预签合同原文错误临时值
	 */
	public static final String NewSignContractSignatureAttr_Error = "MWkwGAYJKoZIhvcNAQkDMQsGCSqGSIb3DQEHATAcBgkqhkiG9w0BCQUxDxcNMTcwMzA5MDkxOTU1WjAvBgkqhkiG9w0BCQQxIgQgmn5Zzs0uRe+SFcFFHcnW50q1DDF26GscX3lrohr1mMw=";

	public GValue() {
	}

	/**
	 * @Description: 按照长度值和组成字母获取字符串
	 */
	public static String getRandomStringByLength(int length, String tChar) {
		for (int i = 0; i < length; i++) {
			RandomStringByLength = RandomStringByLength + tChar;
		}

		return RandomStringByLength;
	}
}
