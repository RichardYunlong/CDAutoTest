package AutoTest;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import org.apache.commons.io.FileUtils;

/**
 *  生成html测试报告-有错模式
 */
public class GHtmlExportBugs {
  
    public static String url = GTransfer.gServerUrl[0];//测试地址
    public static int tar_success = 99;//成功个数 
    public static int tar_failed = 1;//失败个数
    public static int tar_runtotalno = tar_success + tar_failed;//测试用例总数
    public static DecimalFormat df=new DecimalFormat("0.00");//设置保留位数
    public static String rate = df.format(((float)tar_success/tar_runtotalno)*100)+"%";//测试通过率
    public static java.util.List<String> tableList =  new ArrayList<>();
    private static String anyBugFile = "";
    
    /**
     *  构造函数
     *  
     *  @param Subject 邮件主题
     *  @param AccountDev 开发者邮箱
     *  @param AccountTest 测试员邮箱
     *  @param AccountManager 项目经理邮箱
     *  @param AccountReseacher 需求分析师邮箱
     */
    public GHtmlExportBugs(String Subject, 
				    	   String AccountDev, 
				    	   String AccountTest, 
				    	   String AccountManager, 
				    	   String AccountReseacher) {
        tar_success = GProgress.getTCTotalNum();
        tar_failed = GHtmlExportBugs.tableList.size();
        outPutHtml();
        
        if(GMail.IsMailOn) {
        	 GMail.initEmailServer();
             GMail.strSubject = Subject;
             GMail.receiveMailAccountDev = AccountDev;
             GMail.receiveMailAccountTest = AccountTest;
             GMail.receiveMailAccountManager = AccountManager;
             GMail.receiveMailAccountReseacher = AccountReseacher;
     	    GMail.sentEmail(anyBugFile,"");
        } 
    }
    
    /**
     * 导出有误报告
     */
    public static void outPutHtml() {
        GFile.deleteFolder(GPath.REPORT_PATH);
        GFile.creatDir(GPath.REPORT_PATH);
        
        anyBugFile = GPath.REPORT_PATH + "anybug" + GTime.getCurrentTime(GTime.FORMAT_14) + ".html";
        
        if(GFile.copyFile("./html/anybug.html", anyBugFile)) {
            File templateFile = new File(anyBugFile);
            String content = null;
            OutputStream fos = null;
            try {
                content = FileUtils.readFileToString(templateFile, "utf-8");
                
                //替换数据
                content = content.replaceAll("###URL###", url);
                content = content.replaceAll("###TAR_RUNTOTALNO###", ((Integer)tar_runtotalno).toString());
                content = content.replaceAll("###TAR_SUCCESS###", ((Integer)tar_success).toString());
                content = content.replaceAll("###TAR_FAILED###", ((Integer)tar_failed).toString());
                content = content.replaceAll("###RATE###", rate.toString()+"%");
                content = content.replaceAll("###T_VERSION###", GSut.SectionName1);
                content = content.replaceAll("###V_VERSION###",  GSut.SectionName2);
                content = content.replaceAll("###R_VERSION###", GSut.SectionName3);
                content = content.replaceAll("###SQL_NAME###", GSut.Sid);
                
                //动态创建表格
                content = content.replaceAll("###DATA_TABLE###", dynamicTable());
                
                fos = new FileOutputStream(templateFile);
                fos.write(content.getBytes("UTF-8"));
                fos.flush();
                fos.close();
                Runtime.getRuntime().exec("cmd.exe /c start " + anyBugFile);
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
     *  动态生成表格
     */ 
    public static String dynamicTable() {
        String dynamicTable = "";
        
        int nIndex = 0;
        for(;nIndex < tableList.size();nIndex++){
            String number = "";//序号
            String module = "";//模块
            String keyWord = "";//关键字
            String synopsis = "";//概要
            String influenceNumber = "";//影响脚本数
            String type = "";//类型
            String remark = "";//备注 
            String monitorBITemp = tableList.get(Integer.valueOf(nIndex));
            if(monitorBITemp != null && !monitorBITemp.isEmpty()) {
                String[] strArr = monitorBITemp.split("\\|");
                number = strArr[0];
                module = strArr[1];
                keyWord = strArr[2];
                synopsis = strArr[3];
                influenceNumber = strArr[4];
                type = strArr[5];
                remark = strArr[6];
                dynamicTable +=  "<tr style=\"font-size: 12pt; z-index: 0;\">\r\n" + 
                        "                                <td style=\"font-size: 12pt; height: 16.5pt; z-index: 0; width: 48px; padding: 8px; border: 1px solid;\">\r\n" + 
                        "                                    <div align=\"center\" style=\"border-color: rgb(0, 0, 0); font-size: 10.5pt; margin: 0cm 0cm 0pt; text-align: center; z-index: 0;\"><b\r\n" + 
                        "                                         style=\"border-color: rgb(0, 0, 0); font-size: 10.5pt; margin: auto; z-index: 0;\"><span style=\"border-color: black; font-size: 11pt; margin: auto; z-index: 0; font-weight: 700; color: black; font-family: 微软雅黑, sans-serif;\">"+number+"</span></b></div>\r\n" + 
                        "                                </td>\r\n" + 
                        "                                <td style=\"font-size: 12pt; height: 16.5pt; z-index: 0; width: 48px; padding: 8px; border: 1px solid;\">\r\n" + 
                        "                                    <div align=\"center\" style=\"border-color: rgb(0, 0, 0); font-size: 10.5pt; margin: 0cm 0cm 0pt; text-align: center; z-index: 0;\"><b\r\n" + 
                        "                                         style=\"border-color: rgb(0, 0, 0); font-size: 10.5pt; margin: auto; z-index: 0;\"><span style=\"border-color: black; font-size: 11pt; margin: auto; z-index: 0; font-weight: 700; color: black; font-family: 微软雅黑, sans-serif;\">"+module+"</span></b></div>\r\n" + 
                        "                                </td>\r\n" + 
                        "                                <td style=\"font-size: 12pt; height: 16.5pt; z-index: 0; width: 120px; padding: 8px; border: 1px solid;\">\r\n" + 
                        "                                    <div align=\"center\" style=\"border-color: rgb(0, 0, 0); font-size: 10.5pt; margin: 0cm 0cm 0pt; text-align: center; z-index: 0;\"><b\r\n" + 
                        "                                         style=\"border-color: rgb(0, 0, 0); font-size: 10.5pt; margin: auto; z-index: 0;\"><span style=\"border-color: black; font-size: 11pt; margin: auto; z-index: 0; font-weight: 700; color: black; font-family: 微软雅黑, sans-serif;\">"+keyWord+"</span></b></div>\r\n" + 
                        "                                </td>\r\n" + 
                        "                                <td style=\"font-size: 12pt; height: 16.5pt; z-index: 0; width: 274px; padding: 8px; border: 1px solid;\">\r\n" + 
                        "                                    <div align=\"center\" style=\"border-color: rgb(0, 0, 0); font-size: 10.5pt; margin: 0cm 0cm 0pt; text-align: center; z-index: 0;\"><b\r\n" + 
                        "                                         style=\"border-color: rgb(0, 0, 0); font-size: 10.5pt; margin: auto; z-index: 0;\"><span style=\"border-color: black; font-size: 11pt; margin: auto; z-index: 0; font-weight: 700; color: black; font-family: 微软雅黑, sans-serif;\">"+synopsis+"</span></b></div>\r\n" + 
                        "                                </td>\r\n" + 
                        "                                <td style=\"font-size: 12pt; height: 16.5pt; z-index: 0; width: 49px; padding: 8px; border: 1px solid;\">\r\n" + 
                        "                                    <div align=\"center\" style=\"border-color: rgb(0, 0, 0); font-size: 10.5pt; margin: 0cm 0cm 0pt; text-align: center; z-index: 0;\"><b\r\n" + 
                        "                                         style=\"border-color: rgb(0, 0, 0); font-size: 10.5pt; margin: auto; z-index: 0;\"><span style=\"border-color: black; font-size: 11pt; margin: auto; z-index: 0; font-weight: 700; color: black; font-family: 微软雅黑, sans-serif;\">"+influenceNumber+"</span></b></div>\r\n" + 
                        "                                </td>\r\n" + 
                        "                                <td style=\"font-size: 12pt; height: 16.5pt; z-index: 0; width: 63px; padding: 8px; border: 1px solid;\">\r\n" + 
                        "                                    <div align=\"center\" style=\"border-color: rgb(0, 0, 0); font-size: 10.5pt; margin: 0cm 0cm 0pt; text-align: center; z-index: 0;\"><b\r\n" + 
                        "                                         style=\"border-color: rgb(0, 0, 0); font-size: 10.5pt; margin: auto; z-index: 0;\"><span style=\"border-color: black; font-size: 11pt; margin: auto; z-index: 0; font-weight: 700; color: black; font-family: 微软雅黑, sans-serif;\">"+type+"</span></b></div>\r\n" + 
                        "                                </td>\r\n" + 
                        "                                <td style=\"font-size: 12pt; height: 16.5pt; z-index: 0; width: 73px; padding: 8px; border: 1px solid;\">\r\n" + 
                        "                                    <div align=\"center\" style=\"border-color: rgb(0, 0, 0); font-size: 10.5pt; margin: 0cm 0cm 0pt; text-align: center; z-index: 0;\"><b\r\n" + 
                        "                                         style=\"border-color: rgb(0, 0, 0); font-size: 10.5pt; margin: auto; z-index: 0;\"><span style=\"border-color: black; font-size: 11pt; margin: auto; z-index: 0; font-weight: 700; color: black; font-family: 微软雅黑, sans-serif;\">"+remark+"</span></b></div>\r\n" + 
                        "                                </td>\r\n" + 
                        "                            </tr>";
                }
            }
        return dynamicTable;
    }
    
    /**
     * @param number 序号
     * @param module 模块
     * @param keyWord 关键字
     * @param synopsis 概要
     * @param influenceNumber 影响脚本数
     * @param type 类型
     * @param remark 备注
     */
    public static void addData(String number,String module,String keyWord,String synopsis,int influenceNumber,String type,String remark) {
        String dataStr = number+"|"+module+"|"+keyWord+"|"+synopsis+"|"+((Integer)influenceNumber).toString()+"|"+type+"|"+remark;
        tableList.add(dataStr);
      
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
		
	    //此部分为有报错情况下的样例
	    addData("1","物资管理","GEPSST-16308","【材料采购结算单】批次明细无税结算单价计算不正确",2,"已知bug","主干已修改");
	    tableList.add("2|机械管理|GEPSST-000|【材算单】批次明细无税结算单价计算不正确|1|已知bug|主干已修改");
	    new GHtmlExportBugs("自动化测试报告:GEPS（本地调试邮件）", 
	    					"hew-d@glodon.com", 
	    					"hew-d@glodon.com", 
	    					"hew-d@glodon.com", 
	    					"hew-d@glodon.com");
	}
}
