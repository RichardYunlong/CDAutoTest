package testdemo;

import Base.*;
import Coverage.GCoverageReport;
import DB.GDBConnector;
import DB.GDBDoSQL;
import DT.GAutoName;
import DT.GImage;
import DT.GLog;
import DT.GRemote;
import DUnit.GTestCaseCount;
import Dragon.GDragon;
import Dragon.GDragonII;
import IO.GExcelIni;
import Sys.GCfgInfo;
import Sys.GPath;
import Sys.GStatic;
import Tclg.GTestClazzGroup;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Arrays;

/**
 *  单元测试
 */
public class UnitTest {
	
    /**
     *  构造函数
     */
	private UnitTest(){
		GClazz.thisAToolClass();
	}
	
	/**
	 * 测试队列
	 */
    public static void testGLinkQueue() {
        //1.声明一个可以存储6个元素的顺序队列，默认值为0,front 和rear指针为-1
    	GQueue queue = new GQueue(6);
        //2.向顺序队列中添加元素
        queue.add("2");
        queue.add("1");
        queue.add("3");
        queue.add("4");
        queue.add("5");
        queue.add("6");
        //2.1打印当前队列元素
        queue.console();
        queue.head();
        //3.将顺序队列中元素取出
        queue.pop();
        queue.pop();
        queue.pop();
        queue.pop();
        queue.pop();
        queue.pop();
        //4.队列中元素全部取出
    }
    
	/**
	 * 测试找出目标json文件中存在但在源json文件中不存在的key值，并返回这些key以及对应的value
	 */
    public static void testConsoleDiffrentKey() {
    	GJsonObjectMapper.consoleDiffrentKey("C:\\Users\\hewei\\Desktop\\T6.3.27033.1800_V9.17.0.18722_R9.17.0.18724_20210915.json"
    									   , "C:\\Users\\hewei\\Desktop\\T6.3.27067.4320_V9.18.7.20765_R9.18.7.20758_20211117.json");
    }
	
	/**
	 * 测试类群
	 */
    public static void testConsoleTestClazzGroup() {
    	GTestClazzGroup temp = new GTestClazzGroup("./json/testclassgroup.json");
    	temp.consoleTestClazzGroup();
    }
	
	/**
	 * 测试IP
	 */
    public static void testGetIP() {
    	GLog.logRecord(8, GRemote.getIP());
    }
    
	/**
	 * 测试获取符合某个特征的IP
	 */
    public static void testGetNetworkIP() {
    	GLog.logRecord(8, GRemote.getNetworkIP(GStatic.gTransfer.getNetworkSegment()));
    }
	
	/**
	 * 测试显示类成员变量
	 *
	 * @param clazzName 类名称
	 */
	public static void testShowClassParams(String clazzName) {
		try {
			@SuppressWarnings("rawtypes")
			Class onwClass = Class.forName(clazzName);
			Object obj= onwClass.newInstance();
			Field[] fields = onwClass.getDeclaredFields();
			for (int i = 0; i < fields.length; i++) {
				GLog.logRecord(8, "成员变量" + i + "类型: " + fields[i].getType().getName());
				GLog.logRecord(8, "成员变量" + i + "变量名: " + fields[i].getName());
				GLog.logRecord(8, "成员变量" + i + "值: " + fields[i].get(obj));
			}
		} catch (Exception e) {
			GLog.logSysFunctionException("testShowClassParams", e);
		}
	}
    
	/**
	 * 测试url文件写r入
	 */
    public static void testGetUrlContent() {
    	GLog.logRecord(8, GJsonObjectMapper.readUrlJson("http://10.0.104.47:28080/echarts-website-asf-site/data/aqi-login2desktop.json"));	
    }

	/**
	 *  测试获得中文姓名 
	 */
	public static void testGetRandomChineseName() {
		GLog.logRecord(8, GAutoName.getRandomChineseName());
	}
	
	/**
	 *  测试数据库链接
	 */
	protected static void testGetConnection() {  
		GLog.logRecord(8, GDBConnector.getConnection().toString());  
	}
	
	/**
	 *  显示一类弹窗
	 */
	protected static void testGDragon(){
		new GDragon("业务测试：\n业务编码\n 提示内容 \n报文类型\n测试类型\n报文内容\n", 5000, 0, 0, "", null, 0, null);
	}
	
	/**
	 *  显示二类弹窗
	 */
	protected static void testGDragonII(){
		new GDragonII(GStatic.gAbout.getWelcome(), 0, 0);
	}
	
	/**
	 *  SQL
	 */
	protected static void testSQL() {
    	String strTempAdd = "INSERT INTO "
    			+ "CERT("
    			+ "CERT_ID, "
    			+ "CERT_TYPE_ID, "
    			+ "USER_ID, "
    			+ "DN_ELEMENT_ID, "
    			+ "BRANCH_ID, "
    			+ "USER_DN, "
    			+ "CERT_STATUS, "
    			+ "SERIAL_NO, "
    			+ "AUTH_CODE, "
    			+ "DURATION, "
    			+ "APPLY_TIME, "
    			+ "SENDCODE_TIME, "
    			+ "START_TIME, "
    			+ "END_TIME, "
    			+ "CERT_SOURCE, "
    			+ "KEY_ALG, "
    			+ "KEY_LENGTH, "
    			+ "SEQUENCE_NO, "
    			+ "EMAIL, "
    			+ "IS_DELETABLE, "
    			+ "DISTRIBUTE_MODE) " 
    			+ "VALUES ("
    			+ "1622717916586163981, "
    			+ "1, "
    			+ "1622717916530798512, "
    			+ "1622717916041809828, "
    			+ "1, "
    			+ "'CN=051@录入管理员@Zlocal@1,OU=Employees,OU=Local RA,O=CFCA TEST OCA1,C=CN', "
    			+ "4, "
    			+ "'1036482459', "
    			+ "'1111111111', "
    			+ "24, "
    			+ "20000101000001, "
    			+ "20000101000002, "
    			+ "20000101000003, "
    			+ "20991231235959, "
    			+ "1, "
    			+ "'RSA', "
    			+ "2048, "
    			+ "2, "
    			+ "'test@test.com', "
    			+ "0, "
    			+ "0)";
    	String strTempRetrieve = "SELECT "
    			+ "CERT_ID,"
    			+ "CERT_TYPE_ID,"
    			+ "USER_ID,"
    			+ "DN_ELEMENT_ID,"
    			+ "BRANCH_ID,"
    			+ "USER_DN,"
    			+ "CERT_STATUS,"
    			+ "SERIAL_NO,"
    			+ "AUTH_CODE,"
    			+ "DURATION,"
    			+ "APPLY_TIME,"
    			+ "SENDCODE_TIME,"
    			+ "START_TIME,"
    			+ "END_TIME,"
    			+ "CERT_SOURCE,"
    			+ "KEY_ALG,"
    			+ "KEY_LENGTH,"
    			+ "SEQUENCE_NO,"
    			+ "EMAIL,"
    			+ "IS_DELETABLE,"
    			+ "DISTRIBUTE_MODE " 
    			+ "FROM "
    			+ "CERT "
    			+ "WHERE "
    			+ "USER_DN = "
    			+ "'CN=051@录入管理员@Zlocal@1,OU=Employees,OU=Local RA,O=CFCA TEST OCA1,C=CN'";
    	String strTempUpdate = "UPDATE "
    			+ "CERT "
    			+ "SET "
    			+ "CERT_ID = 1622717916586163982,"
    			+ "CERT_TYPE_ID = 1,"
    			+ "USER_ID = 1622717916530798513,"
    			+ "DN_ELEMENT_ID = 1622717916041809828,"
    			+ "BRANCH_ID = 2,"
    			+ "CERT_STATUS = 3,"
    			+ "SERIAL_NO = '1036482460',"
    			+ "AUTH_CODE = '1111111112',"
    			+ "DURATION = 24,"
    			+ "APPLY_TIME = 20000101000000,"
    			+ "SENDCODE_TIME = 20000101000000,"
    			+ "START_TIME = 20000101000000,"
    			+ "END_TIME = 20991231235959,"
    			+ "CERT_SOURCE = 1,"
    			+ "KEY_ALG = 'SM2',"
    			+ "KEY_LENGTH = 4096,"
    			+ "SEQUENCE_NO = 3,"
    			+ "EMAIL = 'test1@test1.com',"
    			+ "IS_DELETABLE = 0,"
    			+ "DISTRIBUTE_MODE = 0 " 
    			+ "WHERE "
    			+ "USER_DN = "
    			+ "'CN=051@录入管理员@Zlocal@1,OU=Employees,OU=Local RA,O=CFCA TEST OCA1,C=CN'";
    	String strTempDelete = "DELETE "
				+ "FROM "
    			+ "CERT "
    			+ "WHERE "
    			+ "USER_DN = "
    			+ "'CN=051@录入管理员@Zlocal@1,OU=Employees,OU=Local RA,O=CFCA TEST OCA1,C=CN'";
    	String strTempTableName = "CERT";
    	GDBDoSQL test = new GDBDoSQL();
    	String select = "retrieve";
        test.doSQL("add", strTempAdd, "");
        test.doSQL("count", "", strTempTableName);
        test.doSQL(select,strTempRetrieve, "");
        test.doSQL("update",strTempUpdate, "");
        test.doSQL(select,strTempRetrieve, "");
        test.doSQL("delete",strTempDelete, "");
        test.doSQL(select,strTempRetrieve, "");
    }
	
	/**
	 *  日志分割
	 */
	protected static void testDrawRectangle() {
		GGraph.drawRectangle(0, 0, 30, 20);
    }
	
	/**
	 *  日志分割
	 */
	protected static void testGLogShear() {
    	for(int i = 0;i < 10;i++) {
			GStatic.gSys.logShear("./test/log_sys.txt", 20000);
    	}
    }
	
	/**
	 *  加载配置文件
	 */
	protected static void testGLoadConfig(){
		try(ConfigurableApplicationContext appContext = new FileSystemXmlApplicationContext("./xml/spring.xml")) {
			GFile.writeStringToGuideBottom("UnitTest Params Loaded");
		} catch (Exception e) {
			GLog.logSysFunctionException("testGLoadConfig", e);
		}
	}
	
	/**
	 *  发送用例分类统计
	 */
	protected static void testGTestCaseCounts(){
		GTestCaseCount testCaseCounts = new GTestCaseCount();
		testCaseCounts.addTestCaseCount("模块1", "重要程度", "关键");
		testCaseCounts.addTestCaseCount("模块1", "算法", "0");
		testCaseCounts.addTestCaseCount("模块1", "场景", "0");
		testCaseCounts.addTestCaseCount("模块1", "负责人", "hew-d");

		GStatic.gTestCaseCounts.setTestCaseCounts(testCaseCounts);

		GStatic.gTestCaseCounts.exportReport();
		try {
			Runtime.getRuntime().exec(GMsg.MSG_CMD[0] + GStatic.gTestCaseCounts.getTESTCASECOUNT_NAME());
		} catch (IOException e) {
			GLog.logSysFunctionException("testGSubstitute", e);
		}
	}
	
	/**
	 *  普通读入xls内容(含首行字段名)
	 */
	protected static void testGExcelRead(){
		try {
			String xlsPath = "./config/grb.xls";
			GExcelIni.doXlsIni(xlsPath);
		} catch (Exception e) {
			GLog.logSysFunctionException("testGExcelRead", e);
		}
	}

	/**
	 *  添加html版本测试详情测试报告内容-String分割模式
	 */
	protected static void testAddDetailRport(){
		//执行区-加载配置专用
		UnitTest.testGLoadConfig();

		GStatic gs = new GStatic();
		gs.testInit();

		GStatic.geCharts.addDetailReport("1|2333|33333333|42222222|1111111222222221115|212121212122222222222222222212126|222222222227|212121212122222222222222222212126|212121212122222222222222222212126");

		//执行区-单元测试专用
		//UnitTest.testShowSysCfg();
		UnitTest.testConsoleDiffrentKey();

		gs.testFinish();
	}
	
	/**
	 *  发送详细报告邮件
	 */
	protected static void testGDetail(){
		GStatic.geCharts.exportReport("1");
		GStatic.geCharts.exportReport("2");
		GStatic.gDetail.exportReport();
		try {
			Runtime.getRuntime().exec(GMsg.MSG_CMD[0] + GStatic.gDetail.getREPORT_NAME());
		} catch (IOException e) {
			GLog.logSysFunctionException("testGDetail", e);
		}
	}
	
	/**
	 *  发送比对报告邮件
	 */
	protected static void testGSubstitute(){
		GStatic.gSubstitute.exportReport();
		try {
			Runtime.getRuntime().exec(GMsg.MSG_CMD[0] + GStatic.gSubstitute.getSUBSTITUTE_NAME());
		} catch (IOException e) {
			GLog.logSysFunctionException("testGSubstitute", e);
		}
	}
	
	/**
	 *  显示覆盖率报告
	 */
	protected static void testGCoverageReport(){
		GCoverageReport.exportReport();
		try {
			Runtime.getRuntime().exec(GMsg.MSG_CMD[0] + GPath.REPORT_CAVERAGE);
		} catch (IOException e) {
			GLog.logSysFunctionException("testGCoverageReport", e);
		}
	}
	
	/**
	 *  显示质量报告
	 */
	protected static void testGQualityReport(){
		GStatic.gQualityReport.exportReport();
		try {
			Runtime.getRuntime().exec(GMsg.MSG_CMD[0] + GPath.REPORT_QUALITY);
		} catch (IOException e) {
			GLog.logSysFunctionException("testGQualityReport", e);
		}
	}
	
	/**
	 *  提示语测试
	 */
	protected static void testGMsg(){
		GStatic.gAbout.setDefault();
		GLog.logRecord(8, GStatic.gAbout.getWelcome());
		GLog.logRecord(8, GStatic.gAbout.getEnding());
	}
	
	/**
	 *  日期测试
	 */
	protected static void testGTime(){
		String year = "year";
		String month = "month";
		String day = "day";
		String start = "start";
		String end = "end";
		String date = "date";
		String time = "time";
		
		GLog.logRecord(8, GTime.getCurrentTime(GTime.FORMAT_6));	
		GLog.logRecord(8, GTime.getCurrentTime(GTime.FORMAT_6_TEXT));
		GLog.logRecord(8, GTime.getCurrentTime(GTime.FORMAT_8));	
		GLog.logRecord(8, GTime.getCurrentTime(GTime.FORMAT_8_TEXT));
		GLog.logRecord(8, GTime.getCurrentTime(GTime.FORMAT_14));	
		GLog.logRecord(8, GTime.getCurrentTime(GTime.FORMAT_14_TEXT));
		GLog.logRecord(8, GTime.getAccountingPeriod(year, start, date));
		GLog.logRecord(8, GTime.getAccountingPeriod(year, start, time));
		GLog.logRecord(8, GTime.getAccountingPeriod(year, end, date));
		GLog.logRecord(8, GTime.getAccountingPeriod(year, end, time));
		GLog.logRecord(8, GTime.getAccountingPeriod(month, start, date));
		GLog.logRecord(8, GTime.getAccountingPeriod(month, start, time));
		GLog.logRecord(8, GTime.getAccountingPeriod(month, end, date));
		GLog.logRecord(8, GTime.getAccountingPeriod(month, end, time));
		GLog.logRecord(8, GTime.getAccountingPeriod(day, start, date));
		GLog.logRecord(8, GTime.getAccountingPeriod(day, start, time));
		GLog.logRecord(8, GTime.getAccountingPeriod(day, end, date));
		GLog.logRecord(8, GTime.getAccountingPeriod(day, end, time));
	}
	
	/**
	 *  加载Object类型参数表
	 */
	protected static void testGObject2dimension() {
		String user = "用户管理";
		String auth = "权限创建";
		String theme = "版式修改";
		String contentNum = "\n集合元素个数：";
		String content = "集合内容：";
		
		Object[][] data1 = new Object[][]{
	    	{0, 8101, user, "新_建111"},
	    	{0, 8201, auth, "新_建111"},	
	    	{0, 8202, theme, "修_改111"},
	    	};
	    	
		Object[][] data2 = new Object[][]{
	    	{0, 8101, user, "新_建222"},
	    	{0, 8201, auth, "新_建222"},	
	    	{0, 8202, theme, "修_改222"},
	    	};
	    
		Object[][] data3 = new Object[][]{
	    	{0, 8101, user, "新_建333"},
	    	{0, 8201, auth, "新_建333"},	
	    	{0, 8202, theme, "修_改333"},
	    	};
	    
	    if(GObject2dimension.getInputList() == null) {
	    	GLog.logRecord(8, "集合内容：null\n集合元素个数：0");	
	    }else {
	    	GLog.logRecord(8, content + Arrays.toString(GObject2dimension.getInputList()) + contentNum + GObject2dimension.getTotalNum());
	    }
	    	
	    GObject2dimension.addObject2d(data1);
	    GLog.logRecord(8, content + Arrays.toString(GObject2dimension.getInputList()) + contentNum + GObject2dimension.getTotalNum());
	    
	    GObject2dimension.addObject2d(data2);
	    GLog.logRecord(8, content + Arrays.toString(GObject2dimension.getInputList()) + contentNum + GObject2dimension.getTotalNum());
	    
	    GObject2dimension.addObject2d(data3);
	    GLog.logRecord(8, content + Arrays.toString(GObject2dimension.getInputList()) + contentNum + GObject2dimension.getTotalNum());
	}

	/**
	 *  图片编码转换
	 */
	protected static void testGImage() {
		String imgFile = "./image/icon.jpg";// 待处理的图片
		String imgFileTemp = "./image/icon_temp.jpg";// 新生成的图片

		GFile.deleteFile(imgFileTemp);

		String imgbese = GImage.getImgBase64Str(imgFile);
		GLog.logRecord(8, "图片base64编码长度：" + imgbese.length() + "\r\n图片base64编码：" + imgbese);

		GImage.generateImage(imgbese, imgFileTemp);
	}
	
	/**
	 *  打印系统参数
	 */
	protected static void testShowSysCfg() {
		GCfgInfo gCfgInfo = new GCfgInfo();
		gCfgInfo.logAll();
	}
}
