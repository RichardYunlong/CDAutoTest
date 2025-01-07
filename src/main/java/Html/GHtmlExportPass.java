package main.java.Html;

import main.java.Base.GFile;
import main.java.Base.GMsg;
import main.java.Base.GTime;
import main.java.DT.GLog;
import main.java.Sys.GPath;
import main.java.Sys.GStatic;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

/**
 *  生成html测试报告-全过模式
 */
public class GHtmlExportPass {
    private static String tar_runtotalno = "";
    private static String allPassFile = "";
    
    /**
     *  获取最新的总数量
     */
    private static void getCurrentNum(int totalNum) {
    	tar_runtotalno = (Integer.valueOf(totalNum)).toString();
    }
    
    /**
     *  构造函数 包含最基本的参数，全部为必填项
     *  
     *  @param totalNum 用例总数
     *  @param Subject 邮件主题
     *  @param AccountDev 开发者邮箱
     *  @param AccountTest 测试员邮箱
     *  @param AccountManager 项目经理邮箱
     *  @param AccountReseacher 需求分析师邮箱
     */
    public GHtmlExportPass(int totalNum, 
				    		String Subject, 
				    		String AccountDev, 
				    		String AccountTest, 
				    		String AccountManager, 
				    		String AccountReseacher){
    	getCurrentNum(totalNum);
        outPutHtml();
        if(GStatic.gMail.isMailOn()) {
	        GStatic.gMail.initEmailServer();
	        GStatic.gMail.setSubject(Subject);
            if(GStatic.gMail.getReceiveMailIsUsed()){
           	 GStatic.gMail.setReceiveMailAccountDev(AccountDev);
                GStatic.gMail.setReceiveMailAccountTest(AccountTest);
                GStatic.gMail.setReceiveMailAccountManager(AccountManager);
                GStatic.gMail.setReceiveMailAccountReseacher(AccountReseacher);
            }
	        GStatic.gMail.sentEmail(allPassFile,"");
        }
    }
    
    /**
     *  构造函数
     *  使用默认收件人列表
     *  
     *  @param totalNum 用例总数
     *  @param Subject 邮件主题
     */
    public GHtmlExportPass(int totalNum, String Subject){
    	getCurrentNum(totalNum);
        outPutHtml();

        if(GStatic.gMail.isMailOn()) {
       	 GStatic.gMail.initEmailServer();
            GStatic.gMail.setSubject(Subject);
    	     GStatic.gMail.sentEmail(allPassFile,"");
       } 
    }
    
    /**
     * 导出正确报告
     */
    public static void outPutHtml() {
        GFile.deleteFolder(GPath.REPORT_PATH);
        GFile.creatDir(GPath.REPORT_PATH);
        
        allPassFile = GPath.REPORT_PATH + "allpass" + GTime.getCurrentTime(GTime.FORMAT_14) + ".html";
        
        if(GFile.copyFile("./html/allpass.html", allPassFile)) {
            File templateFile = new File(allPassFile);
            String content;
            
            try(OutputStream fos = Files.newOutputStream(templateFile.toPath())) {
                content = FileUtils.readFileToString(templateFile, "utf-8");
                
                //替换数据
                String[] serverUrl = GStatic.gTransfer.getgServerUrl().clone();
                content = content.replaceAll("###URL###",serverUrl[0]);
                content = content.replaceAll("###TAR_RUNTOTALNO###", tar_runtotalno);
                content = content.replaceAll("###T_VERSION###", GStatic.gSut.getSectionName1());
                content = content.replaceAll("###V_VERSION###", GStatic.gSut.getSectionName2());
                content = content.replaceAll("###R_VERSION###", GStatic.gSut.getSectionName3());
                content = content.replaceAll("###SQL_NAME###",  GStatic.gSut.getSid());

                fos.write(content.getBytes(StandardCharsets.UTF_8));
                fos.flush();
                
                Runtime.getRuntime().exec(GMsg.MSG_CMD[0] + allPassFile);
            } catch (IOException e) {
            	GLog.logSysFunctionException("outPutHtml", e);
            }
        }else {
            GFile.writeStringToGuideBottom("HTML TEST REPORT OUTPUT FAILED");
        }
    }
    
	/**
	 *  用于独立调试
     *
     *  @param args 入参表
	 */
	@SuppressWarnings("InstantiationOfUtilityClass")
    public static void main(String[] args) {
        GStatic gs = new GStatic();
        gs.testInit();

	    new GHtmlExportPass(100, 
				    		"自动化测试报告:GEPS（本地调试邮件）", 
				    		"hew-d@glodon.com", 
				    		"hew-d@glodon.com", 
				    		"hew-d@glodon.com", 
				    		"hew-d@glodon.com");

        gs.testFinish();
	}
}
