package main.java.Sys;

import main.java.Base.*;
import main.java.DT.GECharts;
import main.java.DT.GLog;
import main.java.DT.GRemote;
import main.java.DT.GTransfer;
import main.java.Detail.GDetail;
import main.java.Detail.GSpendtimes;
import main.java.Detail.GSubstitute;
import main.java.Detail.GTestCaseCounts;
import main.java.IO.GExcelExport;
import main.java.Mail.GMail;
import main.java.Quality.GQFactor;
import main.java.Quality.GQualityAlgo;
import main.java.Quality.GQualityReport;
import main.java.Sut.GSut;
import main.java.Test.GTestMission;
import main.java.Webdriver.GWebDriver;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;

/**
 *  全局静态类
 */
public class GStatic {
    /**
     *  用例执行开始时间
     */
    private static long startTime;

    /**
     *  用例执行结束时间
     */
    @SuppressWarnings("FieldCanBeLocal")
    private static long endTime;

    /**
     *  全局参数表
     */
    @SuppressWarnings("CanBeFinal")
    public static GParam gP = new GParam();

    /**
     *  引导进度
     */
    private static final int progressTatol = 100;
    @SuppressWarnings("CanBeFinal")
    public static GMaticProgress gMP = new GMaticProgress(0, progressTatol);

    /**
     *  系统配置
     */
    @SuppressWarnings("CanBeFinal")
    public static GSys gSys = new GSys();

    /**
     *  被测件名称
     */
    @SuppressWarnings("CanBeFinal")
    public static GSut gSut = new GSut();

    /**
     *  系统常用提示语
     */
    @SuppressWarnings("CanBeFinal")
    public static GAbout gAbout = new GAbout();

    /**
     *  通信配置
     */
    @SuppressWarnings("CanBeFinal")
    public static GTransfer gTransfer = new GTransfer();

    /**
     *  任务日志
     */
    @SuppressWarnings("CanBeFinal")
    public static GLog gLog = new GLog();

    /**
     *  测试用例统计
     */
    @SuppressWarnings("CanBeFinal")
    public static GTestCaseCounts gTestCaseCounts = new GTestCaseCounts();

    /**
     *  测试任务
     */
    @SuppressWarnings("CanBeFinal")
    public static GTestMission gTestMission = new GTestMission();

    /**
     *  测试用例统计
     */
    @SuppressWarnings("CanBeFinal")
    public static GWebDriver gWebDriver = null;


    /**
     * xls版的报告
     */
    @SuppressWarnings("CanBeFinal")
    public static GExcelExport gExcelExport = new GExcelExport();

    /**
     * html版的报告
     */
    @SuppressWarnings("CanBeFinal")
    public static GECharts geCharts = new GECharts();

    /**
     * 耗时详情报告
     */
    @SuppressWarnings("CanBeFinal")
    public static GSpendtimes gSpendtimes = new GSpendtimes();

    /**
     * 测试详情报告
     */
    @SuppressWarnings("CanBeFinal")
    public static GDetail gDetail = new GDetail();

    /**
     * 质量品质系数
     */
    @SuppressWarnings("CanBeFinal")
    public static GQFactor gqFactor = new GQFactor();

    /**
     * 质量算法
     */
    @SuppressWarnings("CanBeFinal")
    public static GQualityAlgo gQualityAlgo = new GQualityAlgo();

    /**
     * html软件质量报告
     */
    @SuppressWarnings("CanBeFinal")
    public static GQualityReport gQualityReport = new GQualityReport();

    /**
     * 报告内容替换
     */
    @SuppressWarnings("CanBeFinal")
    public static GSubstitute gSubstitute = new GSubstitute();

    /**
     * 邮件通知
     */
    @SuppressWarnings("CanBeFinal")
    public static GMail gMail = new GMail();

    /**
     *  构造函数
     */
    public GStatic(){
        GClazz.thisAToolClass();
    }

    /**
     *  测试初始化
     */
    public void testInit(){
        startTime = System.currentTimeMillis();
        if (!gSys.initSys()) {
            gSys.logErrorGuide("PREPARE TESTING ENVIRONMENT FAILED");
        }

        gMP.progress();
        gSys.logGuide(GMissionMsg.getMisstionTip("MISSTION START"));
        gLog.logOn();
        gMP.progress();
        //系统参数表写入8号日志
        if(GTestMission.gTestPlan.isbIsBackup()){
            GCfgInfo gCfgInfo = new GCfgInfo();
            gCfgInfo.logAll();
        }
        gMP.setPROGRESS_CUR(progressTatol);
    }

    /**
     *  执行测试
     */
    public void testRun(){
        gTestMission.tmPreErrorCode();
        gTestMission.tmDateProvider();
        gTestMission.tmStart();
        gTestMission.tmTree();
        gTestMission.tmEnd();
    }

    /**
     *  测试结束
     */
    public void testFinish(){
        GFile.deleteFolder(GPath.OUTPUT_XLS_PATH + GPath.OUTPUT_XLS_NAME);// 如果存在则删除用例输出文件
        if (!gExcelExport.doExportExcel()) {
            gSys.logErrorGuide("EXPORT EXCEL FAILED");
        }

        gLog.logOff();
        if (GTestMission.gTestPlan.isbIsBackup()) {
            gSys.logGuide(GMissionMsg.getStepTop("BACKUP ZIP START"));
            gSys.logGuide(gSys.getDate() + " WRITE ZIP");
            FileOutputStream fosTgs = null;
            try {
                fosTgs = new FileOutputStream(GLog.getLoghome() + "/backup.zip");
            } catch (FileNotFoundException e) {
                GLog.logSysFunctionException("logOff", e);
            }
            GZip.toZip(GLog.getLogpathByIndex(9), fosTgs, true);
            gSys.logGuide("BACKUP TO [" + GLog.getLogpathByIndex(9) + "/backup.zip]");
            gSys.logGuide(GMissionMsg.getStepBottom("BACKUP ZIP COMPLETE"));
        }

        geCharts.report("all","[CD]" + GRemote.getIP());

        endTime = System.currentTimeMillis();
        gSys.logGuide("MISSTION -SPEND:" + (endTime - startTime) + "MS");
        GThread.ByeUI();
        gSys.logGuide(GMissionMsg.getMisstionTip("MISSTION END"));
    }
}
