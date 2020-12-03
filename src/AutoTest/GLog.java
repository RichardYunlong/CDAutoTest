package AutoTest;

import java.awt.Color;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import Dragon.GDragon;

/**
 *  日志
 */
public class GLog {
	private GLog(){
		logShowConsole("This is a tool class.");
	}

	/**
	 *  日志类型：0-有效用例日志；1-失败用例日志；2-无效用例日志；3-中断用例日志；4-参数读取；5-错误码统计；6-缓存配置；7-综合报告；8-系统功能日志；9-综合日志
	 */
	public static String[] strLogStyle = new String[10];
	
	/**
	 *  初始化所有日志配置
	 */
	public static void initGLog() {
		strLogStyle[0] = GPath.strPathStyle[0] + "/log0.txt";// 有效用例日志
		strLogStyle[1] = GPath.strPathStyle[1] + "/log1.txt";// 失败用例日志
		strLogStyle[2] = GPath.strPathStyle[2] + "/log2.txt";// 无效用例日志
		strLogStyle[3] = GPath.strPathStyle[3] + "/log3.txt";// 中断用例日志
		strLogStyle[4] = GPath.strPathStyle[4] + "/inputs.txt";// 参数读取
		strLogStyle[5] = GPath.strPathStyle[5] + "/errorcode.txt";// 错误码统计
		strLogStyle[6] = GPath.strPathStyle[6] + "/cache.txt";// 缓存配置
		strLogStyle[7] = GPath.strPathStyle[7] + "/log_summory.txt";// 综合报告
		strLogStyle[8] = GPath.strPathStyle[8] + "/log_sys.txt";// 系统功能日志
		strLogStyle[9] = GPath.strPathStyle[9] + "/logs.txt";// 综合日志
	}
	
	/**
	 *  输出到控制台
	 *
	 *	结尾换行
	 *  @param str 内容
	 */
	public static void logShowConsole(String str) {
		System.out.println(str);
	}
	
	/**
	 *  输出到控制台
	 *  
	 *  结尾不换行
	 *  @param str 内容
	 */
	public static void logShowNoEnter(String str) {
		System.out.print(str);
	}
	
	/**
	 *  打印日志
	 *
	 *	不计算耗时
	 *  结尾换行
	 *  @param logFile 文件路径
	 *  @param strLog 报文内容
	 */
	public static void logRecord(String logFile, String strLog) {
		GFile.writeStringToBottom(logFile, strLog);
	}
	
	/**
	 * 	打印日志
	 * 
	 * 	不计算耗时
	 *  结尾不换行
	 *  @param logFile 文件路径
	 *  @param strLog 报文内容
	 */
	public static void logRecordNoEnter(String logFile, String strLog) {
		GFile.writeStringToRight(logFile, strLog);
	}

	/**
	 *  打印日志
	 *  
	 *  不计算耗时
	 *  结尾换行-将校验日志序号，如果对应日志不存在则不写入
	 *  @param logIndex 日志序号
	 *  @param strLog 报文内容
	 */
	public static void logRecord(int logIndex, String strLog) {
		if (logIndex < 0 || logIndex > 9) {
			GFile.writeStringToBottom(strLogStyle[logIndex], GMsg.MSG_LOGERROR[0]);
			return;
		}
		GFile.writeStringToBottom(strLogStyle[logIndex], strLog);
	}
	
	/**
	 * 	打印日志
	 * 
	 * 	不计算耗时
	 *  结尾不换行-将校验日志序号，如果对应日志不存在则不写入
	 *  @param logIndex 日志序号
	 *  @param strLog 报文内容
	 */
	public static void logRecordNoEnter(int logIndex, String strLog) {
		if (logIndex < 0 || logIndex > 9) {
			GFile.writeStringToBottom(strLogStyle[logIndex], GMsg.MSG_LOGERROR[0]);
			return;
		}
		GFile.writeStringToBottom(strLogStyle[logIndex], strLog);
	}
	
	/**
	 *  打印日志
	 *  
	 *  记录时间
	 *  结尾换行-将校验日志序号，如果对应日志不存在则不写入
	 *  @param logIndex 日志序号
	 *  @param strLog 报文内容
	 */
	public static void logRecordTime(int logIndex, String strLog) {
		if (logIndex < 0 || logIndex > 9) {
			GFile.writeStringToBottom(strLogStyle[logIndex], "[" + GTime.getDate() + "]" + GMsg.MSG_LOGERROR[0]);
			return;
		}
		GFile.writeStringToBottom(strLogStyle[logIndex], "[" + GTime.getDate() + "]" + strLog);
	}
	
	/**
	 * 	打印日志
	 * 
	 * 	记录时间
	 *  结尾不换行-将校验日志序号，如果对应日志不存在则不写入
	 *  @param logIndex 日志序号
	 *  @param strLog 报文内容
	 */
	public static void logRecordTimeNoEnter(int logIndex, String strLog) {
		if (logIndex < 0 || logIndex > 9) {
			GFile.writeStringToBottom(strLogStyle[logIndex], "[" + GTime.getDate() + "]" + GMsg.MSG_LOGERROR[0]);
			return;
		}
		GFile.writeStringToBottom(strLogStyle[logIndex], "[" + GTime.getDate() + "]" + strLog);
	}

	/**
	 * 	打印日志
	 * 
	 * 	不计算耗时，提示窗使用默认规则
	 *  向指定类型的日志输入指定格式的内容-结尾换行-将校验日志序号，如果对应日志不存在则不写入
	 *  @param logIndex 日至序号
	 *  @param msgCode 业务编码
	 *  @param msgType 报文类型
	 *  @param msg 报文内容
	 */
	public static void logRecord(int logIndex, String msgCode, String msgType, String msg) {
		String type = "未知报文";
		if(logIndex < 0 || logIndex > 9) {
			GFile.writeStringToBottom(strLogStyle[logIndex], GMsg.MSG_LOGERROR[0]);
			return;
		}
		
		if(msgType.equals("req") || msgType.equals("REQ")) {
			type = "发送报文";
			GParam.gReq = msg;
		}else if (msgType.equals("res") || msgType.equals("RES")) {
			type = "响应报文";
			GParam.gRes = msg;
		}
		
		GFile.writeStringToBottom(strLogStyle[logIndex], "业务测试：\n业务编码\n" + msgCode + "\n报文类型\n" + type + "\n报文内容\n" + msg);
		
		if(GParam.gDragonShow) {
			new GDragon("业务测试：\n业务编码\n" + msgCode + "\n报文类型\n" + type + "\n报文内容\n" + GParam.gRes, 0, 0, 0, "", null, 0, null);
		}
	}
	
	/**
	 *  打印日志并弹窗提示
	 * 
	 *  不计算耗时
	 *  向指定类型的日志输入指定格式的内容-结尾换行-将校验日志序号，如果对应日志不存在则不写入
	 *  @param logIndex 日至序号
	 *  @param msgCode 业务编码
	 *  @param msgType 报文类型
	 *  @param msg 报文内容
	 *  @param mTime 持续时间，单位毫秒
	 *  @param dX 提示位置横坐标
   	 *  @param dY 提示位置纵坐标
	 *  @param img 提示图片 分辨率必须为240*360
   	 *  @param fType 字体
   	 *  @param fSize 字号
   	 *  @param fColor 字色
	 */
	public static void logRecord(int logIndex, String msgCode, String msgType, String msg, 
			int mTime, int dX, int dY, String img, String fType, int fSize, Color fColor) {
		String type = "未知报文";
		if(logIndex < 0 || logIndex > 9) {
			GFile.writeStringToBottom(strLogStyle[logIndex], GMsg.MSG_LOGERROR[0]);
			return;
		}
		
		if(msgType.equals("req") || msgType.equals("REQ")) {
			type = "发送报文";
			GParam.gReq = msg;
		}else if (msgType.equals("res") || msgType.equals("RES")) {
			type = "响应报文";
			GParam.gRes = msg;
		}
		
		GFile.writeStringToBottom(strLogStyle[logIndex], "业务测试：\n业务编码\n" + msgCode + "\n报文类型\n" + type + "\n报文内容\n" + msg);
		
		if(GParam.gDragonShow) {
			new Thread(new Runnable() {
				@Override
				public void run() {
					new GDragon(msg, mTime, dX, dY, img, fType, fSize, fColor);	
				}
			}).start();	
		}
	}
	
	/**
	 *  初始化部分日志处理任务：主日志、错误码日志、缓存日志
	 */
	public static void logOn() {
		GSys.logSys("TESTING START");
		for (int i = 0; i < 10; i++) {
			GFile.deleteFolder(GLog.strLogStyle[i]);// 如果存在则删除所有历史测试日志
		}

		logRecord(9, "\r\n" + GTime.getDate() + " [" + GParam.strTestVersion + "] TEST MISSION START \r\n"); // 初始化主日志
		logRecord(5, "\r\n" + GTime.getDate() + " RECORD [" + GParam.strTestVersion + "] ERROR SUMERY\r\n");// 初始化错误码日志
		logRecord(6, "[]");// 初始化缓存日志
		logSysConfigInfo();// 记录配置日志
	}

	/**
	 *  关闭日志处理任务
	 */
	public static void logOff() {

		logRecord(9, "\r\n" + GTime.getDate() + " [" + GParam.strTestVersion + "] TEST MISSION END\r\n");// 关闭测试日志
		logRecord(5, "\r\n" + GTime.getDate() + " ERROR SUMERY IS RECORDED");// 关闭错误码记录

		if (GTestPlan.bTestOutputBackupResult) {
				FileOutputStream fosTgs = null;
				try {
					fosTgs = new FileOutputStream(new File(GPath.strPathStyle[9] + "/backup.zip"));
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}
				GFile.toZip(GPath.strPathStyle[9], fosTgs, true);
				GSys.logSys(GPath.strPathStyle[9] + "/backup.zip");
				GSys.logSys("CHECK THE FOLLOWING BACKUP FILE TO REWEIW DETAILS DURING OR AFTER TESTING");
		}
		GSys.logSys("TESTING END");
	}

	/**
	 *  打印任务及动作耗时日志
	 *  
	 * 	记录某个任务从某一时刻起，到执行完成时，共消耗了多长时间
	 *  当控制台输出和日志保存的内容一样时使用此方法
	 *  @param startTime 开始时间
	 *  @param doName 任务名称
	 *  @param actionName 动作名称
	 */
	public static void logDoReady(long startTime, String doName, String actionName) {
		long endTime = System.currentTimeMillis();
		logRecord(9, GTime.getDate() + " [" + doName + "] " + actionName + " -SPEND:" + (endTime - startTime) + "MS");
	}

	/**
	 * 	打印任务耗时日志
	 * 
	 * 	记录某个任务从某一时刻起，到执行完成时，共消耗了多长时间
	 *  当控制台输出和日志保存的内容一样时使用此方法 需要输入开始时间
	 *  @param startTime 开始时间
	 *  @param doName 任务名称
	 */
	public static void logDoReady(long startTime, String doName) {
		long endTime = System.currentTimeMillis();
		logRecord(9, GTime.getDate() + " [" + doName + "] READY -SPEND:" + (endTime - startTime) + "MS");
	}

	/**
	 * 	打印任务日志
	 * 
	 *  不计算耗时
	 *  当控制台输出和日志保存的内容一样时使用此方法
	 *  @param doName 任务名称
	 */
	public static void logDoReady(String doName) {
		logRecord(9, doName);
	}
	
	/**
	 * 	打印分割线
	 * 
	 *  控制台输出和日志保存的一行相同的符号，常用于作为视觉分割
	 *  @param str 元素
	 *  @param n 个数
	 */
	public static void logDoLine(String str,int n) {
		if(n>=1) {
			for(int i = 0;i < n;i++) {
				GFile.writeStringToRight(strLogStyle[9], str);
			}
		}
		GFile.writeStringToRight(strLogStyle[9], "\n");
	}
	
	/**
	 * 	打印参数配置文件加载结果
	 */
    private static void logSysConfigInfo() {
        
		GFile.writeStringToBottom(GSys.GUIDE,"\r\nLOAD SYSTEM CONFIGURAION START\r\n");
		GFile.writeStringToBottom(GLog.strLogStyle[8],"\r\nSYSTEM CONFIGURAION INFORMATION\r\n");
		
		GFile.writeStringToBottom(GLog.strLogStyle[8],"-------------DRIVER------------\r\n");
		GFile.writeStringToBottom(GLog.strLogStyle[8],"必填，无默认值 是否开启AI助理");
		GFile.writeStringToBottom(GLog.strLogStyle[8],"DragonShow=" + GLoadConfig.strDragonShow + "\r\n");	
		GFile.writeStringToBottom(GLog.strLogStyle[8],"必填，无默认值 输入参数提供方式：0-集合，1-Excel表格，2-txt文本");
		GFile.writeStringToBottom(GLog.strLogStyle[8],"TestInputType=" + GLoadConfig.strTestInputType + "\r\n");	
		GFile.writeStringToBottom(GLog.strLogStyle[8],"必填，无默认值 输入参数提供来源：0-工具内置，1-外部输入");
		GFile.writeStringToBottom(GLog.strLogStyle[8],"TestInputSource=" + GLoadConfig.strTestInputSource + "\r\n");
		GFile.writeStringToBottom(GLog.strLogStyle[8],"必填，无默认值 如果是外部输入参数文件，从第几行开始读取，默认填1，第0行为注释 ");
		GFile.writeStringToBottom(GLog.strLogStyle[8],"TestInputBeginRowIndex=" + GLoadConfig.strTestInputBeginRowIndex + "\r\n");
		GFile.writeStringToBottom(GLog.strLogStyle[8],"必填，无默认值 如果是外部输入参数文件，从第几列开始读取，默认填0,使用外置文件输入时为6");
		GFile.writeStringToBottom(GLog.strLogStyle[8],"TestInputBeginColumnIndex=" + GLoadConfig.strTestInputBeginColumnIndex + "\r\n");
		GFile.writeStringToBottom(GLog.strLogStyle[8],"必填，无默认值 是否在加载输入参数成功后单独打印输入参数表：0-否，非0-允许打印的条数");
		GFile.writeStringToBottom(GLog.strLogStyle[8],"IsLoggedInputs=" + GLoadConfig.strIsLoggedInputs + "\r\n");
		GFile.writeStringToBottom(GLog.strLogStyle[8],"必填，无默认值 测试执行轮数，必须大于0");
		GFile.writeStringToBottom(GLog.strLogStyle[8],"LoopCourt=" + GLoadConfig.dLoopCourt + "\r\n");
		GFile.writeStringToBottom(GLog.strLogStyle[8],"必填，无默认值 测试用例与用例之间的执行时间间隔");
		GFile.writeStringToBottom(GLog.strLogStyle[8],"TimeWait=" + GLoadConfig.dTimeWait + "\r\n");
		GFile.writeStringToBottom(GLog.strLogStyle[8],"必填，无默认值 用例类型");
		GFile.writeStringToBottom(GLog.strLogStyle[8],"TestCaseType=" + GLoadConfig.strTestCaseType + "\r\n");
		GFile.writeStringToBottom(GLog.strLogStyle[8],"必填，无默认值 是否记录缓存文件：设置为true时系统会记录一些中间状态的日志文件，便于排查");
		GFile.writeStringToBottom(GLog.strLogStyle[8],"IsBackup=" + GLoadConfig.strIsBackup + "\r\n");
		GFile.writeStringToBottom(GLog.strLogStyle[8],"必填，无默认值 通信连接方式 ");
		GFile.writeStringToBottom(GLog.strLogStyle[8],"ServerConnType=" +  GLoadConfig.strServerConnType + "\r\n");
		GFile.writeStringToBottom(GLog.strLogStyle[8],"必填，无默认值 是否只校验不执行 ");
		GFile.writeStringToBottom(GLog.strLogStyle[8],"CheckOnly=" + GLoadConfig.strIsCheckOnly + "\r\n");
		GFile.writeStringToBottom(GLog.strLogStyle[8],"必填，无默认值 是否在测试完成后自动打开测试报告:目前仅windows系统有效");
		GFile.writeStringToBottom(GLog.strLogStyle[8],"AutoCheckReport=" + GLoadConfig.strIsAutoCheckReport + "\r\n");
		GFile.writeStringToBottom(GLog.strLogStyle[8],"选填，被测件名称及版本号，默认值为“TARGETv1.0.0.0” ");
		GFile.writeStringToBottom(GLog.strLogStyle[8],"WelcomeStr=" + GLoadConfig.strWelcomeStr + "\r\n");
		GFile.writeStringToBottom(GLog.strLogStyle[8],"选填，无默认值 服务完整地址 ");
		GFile.writeStringToBottom(GLog.strLogStyle[8],"ServerUrl=" + GLoadConfig.strServerUrl + "\r\n");
		GFile.writeStringToBottom(GLog.strLogStyle[8],"选填，无默认值 服务完整域名");
		GFile.writeStringToBottom(GLog.strLogStyle[8],"ServerWWW=" + GLoadConfig.strServerWWW + "\r\n");
		GFile.writeStringToBottom(GLog.strLogStyle[8],"选填，无默认值 服务IP");
		GFile.writeStringToBottom(GLog.strLogStyle[8],"ServerIp=" + GLoadConfig.strServerIp + "\r\n");
		GFile.writeStringToBottom(GLog.strLogStyle[8],"选填，无默认值 服务端口");
		GFile.writeStringToBottom(GLog.strLogStyle[8],"ServerPort=" + GLoadConfig.strServerPort + "\r\n");
		GFile.writeStringToBottom(GLog.strLogStyle[8],"选填，无默认值 服务名");
		GFile.writeStringToBottom(GLog.strLogStyle[8],"ServerName=" + GLoadConfig.strServerName + "\r\n");
		GFile.writeStringToBottom(GLog.strLogStyle[8],"选填，无默认值 通信证书路径");
		GFile.writeStringToBottom(GLog.strLogStyle[8],"JKS_PATH=" + GLoadConfig.strJKS_PATH + "\r\n");
		GFile.writeStringToBottom(GLog.strLogStyle[8],"选填，无默认值 通信证书密码");
		GFile.writeStringToBottom(GLog.strLogStyle[8],"JKS_PWD=" + GLoadConfig.strJKS_PWD + "\r\n");
		GFile.writeStringToBottom(GLog.strLogStyle[8],"选填，无默认值 通信用户别名");
		GFile.writeStringToBottom(GLog.strLogStyle[8],"CommunicationUserALIAS=" + GLoadConfig.strCommunicationUserALIAS + "\r\n");
		GFile.writeStringToBottom(GLog.strLogStyle[8],"选填，无默认值 通信机构ID");
		GFile.writeStringToBottom(GLog.strLogStyle[8],"CommunicationOrgID=" + GLoadConfig.strCommunicationOrgID + "\r\n");
		GFile.writeStringToBottom(GLog.strLogStyle[8],"选填，无默认值 通信用户账号");
		GFile.writeStringToBottom(GLog.strLogStyle[8],"CommunicationUserID=" + GLoadConfig.strCommunicationUserID + "\r\n");
		GFile.writeStringToBottom(GLog.strLogStyle[8],"选填，无默认值 通信用户证件照片");
		GFile.writeStringToBottom(GLog.strLogStyle[8],"CommunicationImg=" + GLoadConfig.strCommunicationImg + "\r\n");
		GFile.writeStringToBottom(GLog.strLogStyle[8],"选填，无默认值 通信用户签章照片");
		GFile.writeStringToBottom(GLog.strLogStyle[8],"CommunicationSeal=" + GLoadConfig.strCommunicationSeal + "\r\n");
		
		GFile.writeStringToBottom(GLog.strLogStyle[8],"---------------DB--------------\r\n");
		GFile.writeStringToBottom(GLog.strLogStyle[8],"数据库驱动类型");
		GFile.writeStringToBottom(GLog.strLogStyle[8],"db.driverClassName=" + GLoadConfig.driverClassName + "\r\n");
		GFile.writeStringToBottom(GLog.strLogStyle[8],"数据库地址");
		GFile.writeStringToBottom(GLog.strLogStyle[8],"db.url=" + GLoadConfig.url + "\r\n");
		GFile.writeStringToBottom(GLog.strLogStyle[8],"数据库用户名");
		GFile.writeStringToBottom(GLog.strLogStyle[8],"db.username=" + GLoadConfig.username + "\r\n");
		GFile.writeStringToBottom(GLog.strLogStyle[8],"数据库密码");
		GFile.writeStringToBottom(GLog.strLogStyle[8],"db.password=" + GLoadConfig.pwd + "\r\n");
		GFile.writeStringToBottom(GLog.strLogStyle[8],"数据库校验语句");
		GFile.writeStringToBottom(GLog.strLogStyle[8],"db.validationQuery=" + GLoadConfig.validationQuery + "\r\n");
		GFile.writeStringToBottom(GLog.strLogStyle[8],"数据库连接超时时间");
		GFile.writeStringToBottom(GLog.strLogStyle[8],"db.connectTimeoutAndReadTimeout=" + GLoadConfig.connectTimeoutAndReadTimeout + "\r\n");
		GFile.writeStringToBottom(GLog.strLogStyle[8],"连接池初始化连接数量");
		GFile.writeStringToBottom(GLog.strLogStyle[8],"db.initialSize=" + GLoadConfig.initialSize + "\r\n");
		GFile.writeStringToBottom(GLog.strLogStyle[8],"连接池中最小的空闲的连接数");
		GFile.writeStringToBottom(GLog.strLogStyle[8],"db.minIdle=" + GLoadConfig.minIdle + "\r\n");
		GFile.writeStringToBottom(GLog.strLogStyle[8],"连接池中最大的空闲的连接数");
		GFile.writeStringToBottom(GLog.strLogStyle[8],"db.maxIdle=" + GLoadConfig.maxIdle + "\r\n");
		GFile.writeStringToBottom(GLog.strLogStyle[8],"连接池中可同时连接的最大的连接数");
		GFile.writeStringToBottom(GLog.strLogStyle[8],"db.maxActive=" + GLoadConfig.maxActive + "\r\n");
		GFile.writeStringToBottom(GLog.strLogStyle[8],"最大建立连接等待时间,单位为毫秒");
		GFile.writeStringToBottom(GLog.strLogStyle[8],"db.maxWait=" + GLoadConfig.maxWait + "\r\n");
		GFile.writeStringToBottom(GLog.strLogStyle[8],"是否自动提交");
		GFile.writeStringToBottom(GLog.strLogStyle[8],"db.defaultAutoCommit=" + GLoadConfig.defaultAutoCommit + "\r\n");
		GFile.writeStringToBottom(GLog.strLogStyle[8],"是否自动回收超时连接");
		GFile.writeStringToBottom(GLog.strLogStyle[8],"db.removeAbandoned=" + GLoadConfig.removeAbandoned + "\r\n");
		GFile.writeStringToBottom(GLog.strLogStyle[8],"超时时间(以秒数为单位)");
		GFile.writeStringToBottom(GLog.strLogStyle[8],"db.removeAbandonedTimeout=" + GLoadConfig.removeAbandonedTimeout + "\r\n");
		GFile.writeStringToBottom(GLog.strLogStyle[8],"是否在空闲时间测试连接");
		GFile.writeStringToBottom(GLog.strLogStyle[8],"db.testWhileIdle=" + GLoadConfig.testWhileIdle + "\r\n");
		GFile.writeStringToBottom(GLog.strLogStyle[8],"一个连接在池中最小生存的时间,单位为毫秒");
		GFile.writeStringToBottom(GLog.strLogStyle[8],"db.timeBetweenEvictionRunsMillis=" + GLoadConfig.timeBetweenEvictionRunsMillis + "\r\n");
		GFile.writeStringToBottom(GLog.strLogStyle[8],"连接池中连接可空闲的时间,单位为毫秒");
		GFile.writeStringToBottom(GLog.strLogStyle[8],"db.minEvictableIdleTimeMillis=" + GLoadConfig.minEvictableIdleTimeMillis + "\r\n");
		GFile.writeStringToBottom(GLog.strLogStyle[8],"每次检查链接的数量");
		GFile.writeStringToBottom(GLog.strLogStyle[8],"db.numTestsPerEvictionRun=" + GLoadConfig.numTestsPerEvictionRun + "\r\n");
		GFile.writeStringToBottom(GLog.strLogStyle[8],"返回对象时是否进行验证");
		GFile.writeStringToBottom(GLog.strLogStyle[8],"db.testOnReturn=" + GLoadConfig.testOnReturn + "\r\n");
		GFile.writeStringToBottom(GLog.strLogStyle[8],"取出对象时是否进行验证");
		GFile.writeStringToBottom(GLog.strLogStyle[8],"db.testOnBorrow=" + GLoadConfig.testOnBorrow + "\r\n");
		
		GFile.writeStringToBottom(GLog.strLogStyle[8],"--------------LOCAL------------\r\n");
		
		
		GFile.writeStringToBottom(GSys.GUIDE,"\r\nLOAD SYSTEM CONFIGURAION END\r\n");
    }
}
