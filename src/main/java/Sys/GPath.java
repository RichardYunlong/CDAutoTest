package Sys;

import Base.GClazz;
import Base.GFile;
import Base.GMissionMsg;

/**
 *  全局路径
 */
public class GPath {

    /**
     *  构造函数
     */
	private GPath(){
		GClazz.thisAToolClass();
	}
	
	/**
	 *  XLS输入文件-存储位置
	 */
	public static final String INPUT_XLS_PATH = "./input/";
	
	/**
	 *  XLS输入文件-文件名
	 */
	public static final String INPUT_XLS_NAME = "testcase.xls";
	
	/**
	 *  XLS输出文件-存储位置
	 */
	public static final String OUTPUT_XLS_PATH = "./test/";
	
	/**
	 *  XLS输出文件-文件名
	 */
	public static final String OUTPUT_XLS_NAME = "output.xls";
	
	/**
	 *  TXT输入文件-存储位置
	 */
	public static final String INPUT_TXT_PATH = "./input/";
	
	/**
	 *  TXT输入文件-文件名
	 */
	public static final String INPUT_TXT_NAME = "testcase.txt";
	
	/**
	 *  html版本测试用例数量分类统计-存储位置
	 */
	public static final String TESTCASECOUNT_TEMP = "./html/testcasecourt.html";
	
	/**
	 *  html版本测试报告模板-存储位置
	 */
	public static final String SUBSTITUTE_TEMP = "./html/substitute.html";
	
	/**
	 *  html版本测试详情报告模板-存储位置
	 */
	public static final String REPORT_TEMP = "./html/detail.html";
	
	/**
	 *  html版本耗时统计报告模板-存储位置
	 */
	public static final String SPENDTIMES_TEMP = "./html/spendtime.html";
	
	/**
	 *  html版本覆盖率测试报告模板-存储位置
	 */
	public static final String REPORT_CAVERAGE_TEMP = "./html/coverage.html";
	
	/**
	 *  html版本质量测试报告模板-存储位置
	 */
	public static final String REPORT_QUALITY_TEMP = "./html/quality.html";
	
	/**
	 *  html版本测试报告帮助文件-存储位置
	 */
	public static final String SUBSTITUTE_HELPER_TEMP = "./html/substituteHelp.html";
	
	/**
	 *  html版本测试详情报告帮助文件-存储位置
	 */
	public static final String HELPER_TEMP = "./html/help.html";
	
	/**
	 *  html版本测试报告-存储位置
	 */
	public static final String REPORT_PATH = "./report/";
	
	/**
	 * image版本测试报告-存储位置
	 */
	public static final String IMAGE_PATH = REPORT_PATH + "/images/";
	
	/**
	 *  html版本测试报告帮助-文件全名
	 */
	public static final String REPORT_SUBSTITUTE_HELPER = REPORT_PATH + "substituteHelp.html";
	
	/**
	 *  html版本测试详情报告帮助-文件全名
	 */
	public static final String REPORT_NAME_HELPER = REPORT_PATH + "help.html";
	
	/**
	 *  html版本覆盖率测试报告-文件全名
	 */
	public static final String REPORT_CAVERAGE = REPORT_PATH + "coverage.html";
	
	/**
	 *  html版本质量测试报告-文件全名
	 */
	public static final String REPORT_QUALITY = REPORT_PATH + "quality.html";
	
	/**
	 *  初始化所有插件
	 */
	public static void setDefault(){
		long startTime;
		GFile.writeStringToGuideBottom(GMissionMsg.getStepStart("GPath"));
		startTime = System.currentTimeMillis();
		GFile.clearDirectory(GPath.IMAGE_PATH);
		GStatic.gSys.logShowAndRecordGuide(startTime, "GPath");
		GFile.writeStringToGuideBottom(GMissionMsg.getStepEnd());
	}
}
