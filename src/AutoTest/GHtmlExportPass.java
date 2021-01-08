package AutoTest;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import org.apache.commons.io.FileUtils;

/**
 *  生成html测试报告-全过模式
 */
public class GHtmlExportPass {

    public static String url = GTransfer.gServerUrl[0];
    public static String tar_runtotalno = "";
    private static String allPassFile = "";
    
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
        GHtmlExportPass.tar_runtotalno = (Integer.valueOf(totalNum)).toString();
        outPutHtml();
        if(GMail.IsMailOn) {
	        GMail.initEmailServer();
	        GMail.strSubject = Subject;
	        GMail.receiveMailAccountDev = AccountDev;
	        GMail.receiveMailAccountTest = AccountTest;
	        GMail.receiveMailAccountManager = AccountManager;
	        GMail.receiveMailAccountReseacher = AccountReseacher;
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
            OutputStream fos = null;
            try {
                content = FileUtils.readFileToString(templateFile, "utf-8");
                
                //替换数据
                content = content.replaceAll("###URL###",url );
                content = content.replaceAll("###TAR_RUNTOTALNO###", tar_runtotalno);
                content = content.replaceAll("###T_VERSION###", GSut.SectionName1);
                content = content.replaceAll("###V_VERSION###", GSut.SectionName2);
                content = content.replaceAll("###R_VERSION###", GSut.SectionName3);
                content = content.replaceAll("###SQL_NAME###",  GSut.Sid);

                fos = new FileOutputStream(templateFile);
                fos.write(content.getBytes("UTF-8"));
                fos.flush();
                fos.close();
                Runtime.getRuntime().exec("cmd.exe /c start " + allPassFile);
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    if(fos != null)fos.close();
                } catch (IOException e) {
                    GLog.logShowConsole(GMsg.MSG_CONSOLE[0] + content);
                    e.printStackTrace();
                }
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
		if(GTestPlan.bTestOutputBackupResult){
			GLoadConfig.showloadConfig();
			GSys.PROGRESS_CUR = 100;
		}
	    new GHtmlExportPass(100, 
				    		"自动化测试报告:GEPS（本地调试邮件）", 
				    		"hew-d@glodon.com", 
				    		"hew-d@glodon.com", 
				    		"hew-d@glodon.com", 
				    		"hew-d@glodon.com");
	}
}
