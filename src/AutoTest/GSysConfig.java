package AutoTest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class GSysConfig {
    
	/**
	 *  测试输入获取方式
	 *  0-ObJect[][]集合；
	 *  1-Excel表格；
	 *  2-Txt文本 。
	 */
    @Value("${sys.TestInputType}")
    private String strTestInputType = "";
	
	/**
	 *  测试输入来源：
	 *  0-工具内置，
	 *  1-外部输入 。
	 */
    @Value("${sys.TestInputSource}")
	private String strTestInputSource = "";
	
	/**
	 *  测试输入获取位置：如果是外部输入参数文件，从第几行开始读取
	 *  通常情况下，第1行为字段名称，不构成参数表，配置文件中该值为1， 如果有若干行注释文字，则配置文件中改为从第几行开始即可
	 *  后续构造的参数表为String[][]类型，默认迭代器有为0的值，为保证参数表中保存的全部为有效用例参数，则形式上认为配置文件第1行的【行号】为“0”，则真正的参数读取开始的【行号】为“[index+1][]”
	 */
    @Value("${sys.TestInputBeginRowIndex}")
	private String strTestInputBeginRowIndex = "";
	
	/**
	 *  测试输入获取位置：如果是外部输入参数文件，从第几列开始读取
	 *  根据目前输入文件的字段：序号,系统模块,功能点,用例说明,前置条件,步骤描述,测试环境类型,用例类型,用例编号,用户名,证件类型,证件号码
	 *  通常情况下，前6项为说明文本，用于填充输出报告，不构成参数表，配置文件中该值为5， 如果有若干行说明文本，则配置文件中改为从第几列开始即可
	 *  后续构造的参数表为String[][]类型，默认迭代器有为0的值，为保证参数表中保存的全部为有效用例参数，则形式上认为配置文件第1列的【列号】为“0”，则真正的参数读取开始的【列号】为“[][index+6]”
	 */
    @Value("${sys.TestInputBeginColumnIndex}")
	private String strTestInputBeginColumnIndex = "";
	
	/**
	 *  是否在加载输入参数成功后单独打印输入参数表：0-否，非0-允许打印的条数
	 */
    @Value("${sys.IsLoggedInputs}")
	private String strIsLoggedInputs = "";
	
	/**
	 *  是否只校验不执行
	 */
    @Value("${sys.IsCheckOnly}")
	private String strIsCheckOnly = "";
	
	/**
	 *  是否在测试完成后自动打开测试报告
	 */
    @Value("${sys.IsAutoCheckReport}")
	private String strIsAutoCheckReport = "";
	
	/**
	 *  被测件名称及版本号
	 */
    @Value("${sys.WelcomeStr}")
	private String strWelcomeStr = "";
	
	/**
	 *  测试输入集合名称：当用例输入使用内置方式时，系统可能内置了很多集合，此参数可以作为选择标记
	 */
    @Value("${sys.TestCaseType}")
	private String strTestCaseType = "";
	
	/**
	 *  测试执行轮数，必须大于0
	 */
    @Value("${sys.LoopCourt}")
	private int dLoopCourt = 0;
	
	/**
	 *  测试用例执行时间间隔
	 */
    @Value("${sys.TimeWait}")
	private int dTimeWait = 0;
	
	/**
	 *  是否打包测试结果
	 */
    @Value("${sys.IsBackup}")
	private String strIsBackup = "";
	
	/**
	 *  服务连接方式
	 */
    @Value("${sys.ServerConnType}")
	private String strServerConnType = "";
    
    public String getTestInputType() {
        return strTestInputType;
    }
    
    public String getTestInputSource() {
        return strTestInputSource;
    }
    
    public String getTestInputBeginRowIndex() {
        return strTestInputBeginRowIndex;
    }
    
    public String getTestInputBeginColumnIndex() {
        return strTestInputBeginColumnIndex;
    }
    
    public String getIsLoggedInputs() {
        return strIsLoggedInputs;
    }
    
    public String getIsCheckOnly() {
        return strIsCheckOnly;
    }
    
    public String getIsAutoCheckReport() {
        return strIsAutoCheckReport;
    }
    
    public String getWelcomeStr() {
        return strWelcomeStr;
    }
    
    public String getTestCaseType() {
        return strTestCaseType;
    }
    
    public int getLoopCourt() {
        return dLoopCourt;
    }
    
    public int getTimeWait() {
        return dTimeWait;
    }
    
    public String getIsBackup() {
        return strIsBackup;
    }
    
    public String getServerConnType() {
        return strServerConnType;
    }
    
	/**
	 *  选填，无默认值 服务完整地址
	 */
    @Value("${sys.ServerUrl}")
    private String strServerUrl = "";
    
	/**
	 *  选填，无默认值 服务完整域名
	 */
    @Value("${sys.ServerWWW}")
    private String strServerWWW = "";
    
	/**
	 *  选填，无默认值 服务IP
	 */
    @Value("${sys.ServerIp}")
    private String strServerIp = "";
    
	/**
	 *  选填，无默认值 服务端口 
	 */
    @Value("${sys.ServerPort}")
    private String strServerPort = "";
    
	/**
	 *  选填，无默认值 服务名 
	 */
    @Value("${sys.ServerName}")
    private String strServerName = "";
    
	/**
	 *  选填，无默认值 通信证书路径 
	 */
    @Value("${sys.JKS_PATH}")
    private String strJKS_PATH = "";
    
	/**
	 *  选填，无默认值 通信证书密码 
	 */
    @Value("${sys.JKS_PWD}")
    private String strJKS_PWD = "";
    
	/**
	 *  选填，无默认值 通信用户别名
	 */
    @Value("${sys.CommunicationUserALIAS}")
    private String strCommunicationUserALIAS = "";
    
	/**
	 *  选填，无默认值 通信机构ID
	 */
    @Value("${sys.CommunicationOrgID}")
    private String strCommunicationOrgID = "";
    
	/**
	 *  选填，无默认值 通信用户账号
	 */
    @Value("${sys.CommunicationUserID}")
    private String strCommunicationUserID = "";
    
	/**
	 *  选填，无默认值 通信用户证件照片
	 */
    @Value("${sys.CommunicationImg}")
    private String strCommunicationImg = "";
    
	/**
	 *  #选填，无默认值 通信用户签章照片
	 */
    @Value("${sys.CommunicationSeal}")
    private String strCommunicationSeal = "";
    
    public String getServerUrl() {
        return strServerUrl;
    } 
    
    public String getServerWWW() {
        return strServerWWW;
    }
    
    public String getServerIp() {
        return strServerIp;
    }
    
    public String getServerPort() {
        return strServerPort;
    }
    
    public String getServerName() {
        return strServerName;
    }
    
    public String getJKS_PATH() {
        return strJKS_PATH;
    }
    
    public String getJKS_PWD() {
        return strJKS_PWD;
    }
    
    public String getCommunicationUserALIAS() {
        return strCommunicationUserALIAS;
    }
    
    public String getCommunicationOrgID() {
        return strCommunicationOrgID;
    }
    
    public String getCommunicationUserID() {
        return strCommunicationUserID;
    }
    
    public String getCommunicationImg() {
        return strCommunicationImg;
    }
    
    public String getCommunicationSeal() {
        return strCommunicationSeal;
    }
}
