package main.java.Sys;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import main.java.DUnit.GAttribute;

@Component
public class GSysConfig {
	
	/**
	 *  保存本类所有参数
	 */
	@SuppressWarnings({"CanBeFinal", "FieldMayBeFinal"})
    private static GAttribute sysAttribute = new GAttribute();
	
	public static GAttribute getSysAttribute() {
		return sysAttribute;
	}

	/**
	 *  是否开启AI助理
	 */
    @Value("${sys.DragonShow}")
    private String bDragonShow;
    
    public String getDragonShow() {
    	sysAttribute.putAttribute("\n#是否开启AI助理\nsys.DragonShow", bDragonShow);
        return bDragonShow;
    }
    
	/**
	 *  测试输入获取方式
	 *  0-ObJect[][]集合;
	 *  1-Excel表格;
	 *  2-Txt文本
	 */
    @Value("${sys.TestInputType}")
    private String strTestInputType;
    
    public String getTestInputType() {
    	sysAttribute.putAttribute("\n#测试输入获取方式:0-ObJect[][]集合;1-Excel表格;2-Txt文本\nsys.TestInputType", strTestInputType);
        return strTestInputType;
    }
	
	/**
	 *  测试输入来源：
	 *  0-工具内置;
	 *  1-外部输入。
	 */
    @Value("${sys.TestInputSource}")
	private String strTestInputSource;
    
    public String getTestInputSource() {
    	sysAttribute.putAttribute("\n#测试输入来源：0-工具内置;1-外部输入\nsys.TestInputSource", strTestInputSource);
        return strTestInputSource;
    }
	
	/**
	 *  测试输入获取位置：如果是外部输入参数文件，从第几行开始读取
	 *  通常情况下，第1行为字段名称，不构成参数表，配置文件中该值为1， 如果有若干行注释文字，则配置文件中改为从第几行开始即可
	 *  后续构造的参数表为String[][]类型，默认迭代器有为0的值，为保证参数表中保存的全部为有效用例参数，则形式上认为配置文件第1行的【行号】为“0”，则真正的参数读取开始的【行号】为“[index+1][]”
	 */
    @Value("${sys.TestInputBeginRowIndex}")
	private String strTestInputBeginRowIndex;
    
    public String getTestInputBeginRowIndex() {
    	sysAttribute.putAttribute("\n#测试输入获取位置：如果是外部输入参数文件，从第几行开始读取\nsys.TestInputBeginRowIndex", strTestInputBeginRowIndex);
        return strTestInputBeginRowIndex;
    }
	
	/**
	 *  测试输入获取位置：如果是外部输入参数文件，从第几列开始读取
	 *  根据目前输入文件的字段：序号,系统模块,功能点,用例说明,前置条件,步骤描述,测试环境类型,用例类型,用例编号,用户名,证件类型,证件号码
	 *  通常情况下，前6项为说明文本，用于填充输出报告，不构成参数表，配置文件中该值为5， 如果有若干行说明文本，则配置文件中改为从第几列开始即可
	 *  后续构造的参数表为String[][]类型，默认迭代器有为0的值，为保证参数表中保存的全部为有效用例参数，则形式上认为配置文件第1列的【列号】为“0”，则真正的参数读取开始的【列号】为“[][index+6]”
	 */
    @Value("${sys.TestInputBeginColumnIndex}")
	private String strTestInputBeginColumnIndex;
    
    public String getTestInputBeginColumnIndex() {
    	sysAttribute.putAttribute("\n#测试输入获取位置：如果是外部输入参数文件，从第几列开始读取\nsys.TestInputBeginColumnIndex", strTestInputBeginColumnIndex);
        return strTestInputBeginColumnIndex;
    }
	
	/**
	 *  是否在加载输入参数成功后单独打印输入参数表:0-否;非0-允许打印的条数
	 */
    @Value("${sys.IsLoggedInputs}")
	private String strIsLoggedInputs;
    
    public String getIsLoggedInputs() {
    	sysAttribute.putAttribute("\n#是否在加载输入参数成功后单独打印输入参数表:0-否;非0-允许打印的条数\nsys.IsLoggedInputs", strIsLoggedInputs);
        return strIsLoggedInputs;
    }
	
	/**
	 *  是否只校验不执行
	 */
    @Value("${sys.IsCheckOnly}")
	private String strIsCheckOnly;
    
    public String getIsCheckOnly() {
    	sysAttribute.putAttribute("\n#是否只校验不执行\nsys.IsCheckOnly", strIsCheckOnly);
        return strIsCheckOnly;
    }
	
	/**
	 *  是否在测试完成后自动打开测试报告
	 */
    @Value("${sys.IsAutoCheckReport}")
	private String strIsAutoCheckReport;
    
    public String getIsAutoCheckReport() {
    	sysAttribute.putAttribute("\n#是否在测试完成后自动打开测试报告\nsys.IsAutoCheckReport", strIsAutoCheckReport);
        return strIsAutoCheckReport;
    }
	
	/**
	 *  被测件名称及版本号
	 */
    @Value("${sys.Driver}")
	private String strDriver;
    
    public String getDriver() {
    	sysAttribute.putAttribute("\n#是否在测试完成后自动打开测试报告\nsys.Driver", strDriver);
        return strDriver;
    }
	
	/**
	 *  测试输入集合名称：当用例输入使用内置方式时，系统可能内置了很多集合，此参数可以作为选择标记
	 */
    @Value("${sys.TestCaseType}")
	private String strTestCaseType;
    
    public String getTestCaseType() {
    	sysAttribute.putAttribute("\n#测试输入集合名称：当用例输入使用内置方式时，系统可能内置了很多集合，此参数可以作为选择标记\nsys.TestCaseType", strTestCaseType);
        return strTestCaseType;
    }
	
	/**
	 *  测试执行轮数，必须大于0
	 */
    @Value("${sys.LoopCourt}")
	private int dLoopCourt;
    
    public int getLoopCourt() {
    	sysAttribute.putAttribute("\n#测试执行轮数，必须大于0\nsys.LoopCourt", String.valueOf(dLoopCourt));
        return dLoopCourt;
    }
	
	/**
	 *  测试用例执行时间间隔
	 */
    @Value("${sys.TimeWait}")
	private int dTimeWait;
	
    public int getTimeWait() {
    	sysAttribute.putAttribute("\n#测试用例执行时间间隔\nsys.TimeWait", String.valueOf(dTimeWait));
        return dTimeWait;
    }
    
	/**
	 *  是否打包测试结果
	 */
    @Value("${sys.IsBackup}")
	private String strIsBackup;
    
    public String getIsBackup() {
    	sysAttribute.putAttribute("\n#是否打包测试结果\nsys.IsBackup", strIsBackup);
        return strIsBackup;
    }
	
	/**
	 *  服务连接方式
	 */
    @Value("${sys.ServerConnType}")
	private String strServerConnType;
    
    public String getServerConnType() {
    	sysAttribute.putAttribute("\n#服务连接方式\nsys.ServerConnType", strServerConnType);
        return strServerConnType;
    }
    
	/**
	 *  选填，无默认值 服务完整地址
	 */
    @Value("${sys.ServerUrl}")
    private String strServerUrl;
    
    public String getServerUrl() {
    	sysAttribute.putAttribute("\n#选填，无默认值 服务完整地址\nsys.ServerUrl", strServerUrl);
        return strServerUrl;
    } 
    
	/**
	 *  选填，无默认值 服务完整域名
	 */
    @Value("${sys.ServerWWW}")
    private String strServerWWW;
    
    public String getServerWWW() {
    	sysAttribute.putAttribute("\n#选填，无默认值 服务完整域名\nsys.ServerWWW", strServerWWW);
        return strServerWWW;
    }
    
	/**
	 *  选填，无默认值 服务IP
	 */
    @Value("${sys.ServerIp}")
    private String strServerIp;
    
    public String getServerIp() {
    	sysAttribute.putAttribute("\n#选填，无默认值 服务IP\nsys.ServerIp", strServerIp);
        return strServerIp;
    }
    
	/**
	 *  选填，无默认值 服务端口
	 */
    @Value("${sys.ServerPort}")
    private String strServerPort;
    
    public String getServerPort() {
    	sysAttribute.putAttribute("\n#选填，无默认值 服务端口\nsys.ServerPort", strServerPort);
        return strServerPort;
    }
    
	/**
	 *  选填，无默认值 服务名
	 */
    @Value("${sys.ServerName}")
    private String strServerName;
    
    public String getServerName() {
    	sysAttribute.putAttribute("\n#选填，无默认值 服务名\nsys.ServerName", strServerName);
        return strServerName;
    }
    
	/**
	 *  选填，无默认值 通信证书路径
	 */
    @Value("${sys.JKS_PATH}")
    private String strJKS_PATH;
    
    public String getJKS_PATH() {
    	sysAttribute.putAttribute("\n#选填，无默认值 通信证书路径\nsys.JKS_PATH", strJKS_PATH);
        return strJKS_PATH;
    }
    
	/**
	 *  选填，无默认值 通信证书密码
	 */
    @Value("${sys.JKS_PWD}")
    private String strJKS_PWD;
    
    public String getJKS_PWD() {
    	sysAttribute.putAttribute("\n#选填，无默认值 通信证书密码\nsys.JKS_PWD", strJKS_PWD);
        return strJKS_PWD;
    }
    
	/**
	 *  选填，无默认值 通信用户别名
	 */
    @Value("${sys.CommunicationUserALIAS}")
    private String strCommunicationUserALIAS;
    
    public String getCommunicationUserALIAS() {
    	sysAttribute.putAttribute("\n#选填，无默认值 通信用户别名\nsys.CommunicationUserALIAS", strCommunicationUserALIAS);
        return strCommunicationUserALIAS;
    }
    
	/**
	 *  选填，无默认值 通信机构ID
	 */
    @Value("${sys.CommunicationOrgID}")
    private String strCommunicationOrgID;
    
    public String getCommunicationOrgID() {
    	sysAttribute.putAttribute("\n#选填，无默认值 通信机构ID\nsys.CommunicationOrgID", strCommunicationOrgID);
        return strCommunicationOrgID;
    }
    
	/**
	 *  选填，无默认值 通信用户账号
	 */
    @Value("${sys.CommunicationUserID}")
    private String strCommunicationUserID;
    
    public String getCommunicationUserID() {
    	sysAttribute.putAttribute("\n#选填，无默认值 通信用户账号\nsys.CommunicationUserID", strCommunicationUserID);
        return strCommunicationUserID;
    }
    
	/**
	 *  选填，无默认值 通信用户证件照片
	 */
    @Value("${sys.CommunicationImg}")
    private String strCommunicationImg;
    
    public String getCommunicationImg() {
    	sysAttribute.putAttribute("\n#选填，无默认值 通信用户证件照片\nsys.CommunicationImg", strCommunicationImg);
        return strCommunicationImg;
    }
    
	/**
	 *  #选填，无默认值 通信用户签章照片
	 */
    @Value("${sys.CommunicationSeal}")
    private String strCommunicationSeal;

    public String getCommunicationSeal() {
    	sysAttribute.putAttribute("\n#选填，无默认值 通信用户签章照片\nsys.CommunicationSeal", strCommunicationSeal);
        return strCommunicationSeal;
    }
    
	/**
	 *  #选填，无默认值 测试报告所在服务器IP特征
	 */
    @Value("${sys.featuresURL}")
    private String strFeaturesURL;
    
	public String getStrFeaturesURL() {
		sysAttribute.putAttribute("\n#选填，无默认值 测试报告所在服务器IP特征\nsys.featuresURL", strFeaturesURL);
		return strFeaturesURL;
	}
	
	/**
	 *  #选填，无默认值 测试报告所在服务器端口特征
	 */
    @Value("${sys.featuresPort}")
    private String strFeaturesPort;
    
	public String getStrFeaturesPort() {
		sysAttribute.putAttribute("\n#选填，无默认值 测试报告所在服务器端口特征\nsys.featuresPort", strFeaturesPort);
		return strFeaturesPort;
	}

	/**
	 *  #选填，无默认值 html格式的测试报告地址
	 */
    @Value("${sys.ReportURL}")
    private String strReportURL;
    
	public String getStrReportURL() {
		sysAttribute.putAttribute("\n#选填，无默认值 html格式的测试报告地址\nsys.ReportURL", strReportURL);
		return strReportURL;
	}
	
	/**
	 *  #选填，无默认值 html格式的测试报告地址
	 */
    @Value("${sys.SpendTimesURL}")
    private String strSpendTimesURL;
    
	public String getStrSpendTimesURL() {
		sysAttribute.putAttribute("\n#选填，无默认值 html格式的耗时统计地址\nsys.SpendTimesURL", strSpendTimesURL);
		return strSpendTimesURL;
	}
	
	/**
	 *  #选填，无默认值 html格式的测试报告地址
	 */
    @Value("${sys.ReportURLOLD}")
    private String strReportURLOLD;

	public String getStrReportURLOLD() {
		sysAttribute.putAttribute("\n#选填，无默认值 html格式的测试报告地址\nsys.ReportURLOLD", strReportURLOLD);
		return strReportURLOLD;
	}
}
