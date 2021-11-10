package Html;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

import org.apache.commons.io.FileUtils;

import Base.GFile;
import Base.GMsg;
import Base.GTime;
import DT.GLog;
import DT.GTransfer;
import Mail.GMail;
import Sut.GSut;
import Sys.GPath;
import Sys.GSys;
import Test.GTestMission;
import Test.GTestPlan;

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
        if(GMail.isMailOn()) {
	        GMail.initEmailServer();
	        GMail.setSubject(Subject);
            if(!GMail.isReceiveMailIsUsed()){
           	 GMail.setReceiveMailAccountDev(AccountDev);
                GMail.setReceiveMailAccountTest(AccountTest);
                GMail.setReceiveMailAccountManager(AccountManager);
                GMail.setReceiveMailAccountReseacher(AccountReseacher);
            }
	        GMail.sentEmail(allPassFile,"");
        }
    }
    
    /**
     *  构造函数 
     *  
     *  使用默认收件人列表
     *  
     *  @param totalNum 用例总数
     *  @param Subject 邮件主题
     */
    public GHtmlExportPass(int totalNum, String Subject){
    	getCurrentNum(totalNum);
        outPutHtml();
        
        if(GMail.isMailOn()) {
       	 GMail.initEmailServer();
            GMail.setSubject(Subject);
    	     GMail.sentEmail(allPassFile,"");
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
            String content = null;
            
            try(OutputStream fos = new FileOutputStream(templateFile);) {
                content = FileUtils.readFileToString(templateFile, "utf-8");
                
                //替换数据
                String[] serverUrl = GTransfer.getgServerUrl().clone();
                content = content.replaceAll("###URL###",serverUrl[0]);
                content = content.replaceAll("###TAR_RUNTOTALNO###", tar_runtotalno);
                content = content.replaceAll("###T_VERSION###", GSut.getSectionName1());
                content = content.replaceAll("###V_VERSION###", GSut.getSectionName2());
                content = content.replaceAll("###R_VERSION###", GSut.getSectionName3());
                content = content.replaceAll("###SQL_NAME###",  GSut.getSid());

                fos.write(content.getBytes(StandardCharsets.UTF_8));
                fos.flush();
                
                Runtime.getRuntime().exec(GMsg.MSG_CMD[0] + allPassFile);
            } catch (IOException e) {
            	GLog.logSysFunctionException("outPutHtml", e);
            }
        }else {
            GSys.logSys("HTML TEST REPORT OUTPUT FAILED");
        }
    }
    
	/**
	 *  用于独立调试
	 */
	public static void main(String[] args) {
		GTestMission.tmInit();
		GTestMission.tmLogOn();
		if(GTestPlan.isbIsBackup()){
			GSys.setPROGRESS_CUR(100);
		}
	    new GHtmlExportPass(100, 
				    		"自动化测试报告:GEPS（本地调试邮件）", 
				    		"hew-d@glodon.com", 
				    		"hew-d@glodon.com", 
				    		"hew-d@glodon.com", 
				    		"hew-d@glodon.com");
	}
}
