package main.java.Html;

import main.java.Base.GFile;
import main.java.Base.GMsg;
import main.java.Base.GTime;
import main.java.DT.GLog;
import main.java.Sys.GPath;
import main.java.Sys.GStatic;
import main.java.Test.GTestMission;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.text.DecimalFormat;
import java.util.ArrayList;

/**
 *  生成html测试报告-有错模式
 */
public class GHtmlExportBugs {

    /**
     *  核心数据：表体内容
     */
    @SuppressWarnings({"FieldMayBeFinal", "CanBeFinal"})
    private java.util.List<String> tableList =  new ArrayList<>();

	private int tar_success = 99;//成功个数
	private int tar_failed = 1;//失败个数
    @SuppressWarnings({"FieldMayBeFinal", "CanBeFinal"})
    private int tar_runtotalno = tar_success + tar_failed;//测试用例总数
    @SuppressWarnings({"FieldMayBeFinal", "CanBeFinal"})
    private DecimalFormat df=new DecimalFormat("0.00");//设置保留位数
    @SuppressWarnings({"FieldMayBeFinal", "CanBeFinal"})
    private String rate = df.format(((float)tar_success/tar_runtotalno)*100)+"%";//测试通过率
    private String anyBugFile = "";
    
    /**
     *  获取最新的成功和失败数量
     */
    private void getCurrentNum() {
        this.tar_success = GTestMission.gTestProgress.getTotalNum();
        this.tar_failed = this.tableList.size();
    }
    
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
    	//第一步，获得表大小
        getCurrentNum();

        //第二步，生成html文件
        outPutHtml();

        //第三步，发送邮件
        if(GStatic.gMail.isMailOn()) {
        	 GStatic.gMail.initEmailServer();
             GStatic.gMail.setSubject(Subject);
             if(GStatic.gMail.getReceiveMailIsUsed()){
            	 GStatic.gMail.setReceiveMailAccountDev(AccountDev);
                 GStatic.gMail.setReceiveMailAccountTest(AccountTest);
                 GStatic.gMail.setReceiveMailAccountManager(AccountManager);
                 GStatic.gMail.setReceiveMailAccountReseacher(AccountReseacher);
             }
     	     GStatic.gMail.sentEmail(anyBugFile,"");
        } 
    }
    
    /**
     *  构造函数
     *  
     *  @param Subject 邮件主题
     */
    public GHtmlExportBugs(String Subject) {
        //第一部，获得表大小
        getCurrentNum();

        //第二步，生成html文件
        outPutHtml();

        //第三步，发送邮件
        if(GStatic.gMail.isMailOn()) {
        	 GStatic.gMail.initEmailServer();
             GStatic.gMail.setSubject(Subject);
     	     GStatic.gMail.sentEmail(anyBugFile,"");
        } 
    }
    
    /**
     * 导出有误报告
     */
    public void outPutHtml() {
        GFile.deleteFolder(GPath.REPORT_PATH);
        GFile.creatDir(GPath.REPORT_PATH);
        
        anyBugFile = GPath.REPORT_PATH + "anybug" + GTime.getCurrentTime(GTime.FORMAT_14) + ".html";
        
        if(GFile.copyFile("./html/anybug.html", anyBugFile)) {
            File templateFile = new File(anyBugFile);
            String content;
            try(OutputStream fos = Files.newOutputStream(templateFile.toPath())) {
                content = FileUtils.readFileToString(templateFile, "utf-8");
                
                //替换数据
                String[] serverUrl = GStatic.gTransfer.getgServerUrl().clone();
                content = content.replaceAll("###URL###", serverUrl[0]);
                content = content.replaceAll("###TAR_RUNTOTALNO###", ((Integer)tar_runtotalno).toString());
                content = content.replaceAll("###TAR_SUCCESS###", ((Integer)tar_success).toString());
                content = content.replaceAll("###TAR_FAILED###", ((Integer)tar_failed).toString());
                content = content.replaceAll("###RATE###", rate + "%");
                content = content.replaceAll("###T_VERSION###", GStatic.gSut.getSectionName1());
                content = content.replaceAll("###V_VERSION###",  GStatic.gSut.getSectionName2());
                content = content.replaceAll("###R_VERSION###", GStatic.gSut.getSectionName3());
                content = content.replaceAll("###SQL_NAME###", GStatic.gSut.getSid());
                
                //动态创建表格
                content = content.replaceAll("###DATA_TABLE###", dynamicTable());

                fos.write(content.getBytes(StandardCharsets.UTF_8));
                fos.flush();
                Runtime.getRuntime().exec(GMsg.MSG_CMD[0] + anyBugFile);
            } catch (IOException e) {
            	GLog.logSysFunctionException("outPutHtml", e);
            }
        }else {
            GFile.writeStringToGuideBottom("HTML TEST REPORT OUTPUT FAILED");
        }
    }
    
    /**
     *  动态生成表格
     *
     *  @return dynamicTable 动态表
     */ 
    public String dynamicTable() {
    	StringBuilder dynamicTable = new StringBuilder();
        
        int nIndex = 0;
        for(;nIndex < tableList.size();nIndex++){
            String number;//序号
            String module;//模块
            String keyWord;//关键字
            String synopsis;//概要
            String influenceNumber;//影响脚本数
            String type;//类型
            String remark;//备注
            String monitorBITemp = tableList.get(nIndex);
            if(monitorBITemp != null && !monitorBITemp.isEmpty()) {
                String[] strArr = monitorBITemp.split("\\|");
                number = strArr[0];
                module = strArr[1];
                keyWord = strArr[2];
                synopsis = strArr[3];
                influenceNumber = strArr[4];
                type = strArr[5];
                remark = strArr[6];
                dynamicTable.append("<tr style=\"font-size: 12pt; z-index: 0;\">\r\n" + "                                <td style=\"font-size: 12pt; height: 16.5pt; z-index: 0; width: 48px; padding: 8px; border: 1px solid;\">\r\n" + "                                    <div align=\"center\" style=\"border-color: rgb(0, 0, 0); font-size: 10.5pt; margin: 0cm 0cm 0pt; text-align: center; z-index: 0;\"><b\r\n" + "                                         style=\"border-color: rgb(0, 0, 0); font-size: 10.5pt; margin: auto; z-index: 0;\"><span style=\"border-color: black; font-size: 11pt; margin: auto; z-index: 0; font-weight: 700; color: black; font-family: 微软雅黑, sans-serif;\">")
                        .append(number)
                        .append("</span></b></div>\r\n")
                        .append("                                </td>\r\n")
                        .append("                                <td style=\"font-size: 12pt; height: 16.5pt; z-index: 0; width: 48px; padding: 8px; border: 1px solid;\">\r\n")
                        .append("                                    <div align=\"center\" style=\"border-color: rgb(0, 0, 0); font-size: 10.5pt; margin: 0cm 0cm 0pt; text-align: center; z-index: 0;\"><b\r\n")
                        .append("                                         style=\"border-color: rgb(0, 0, 0); font-size: 10.5pt; margin: auto; z-index: 0;\"><span style=\"border-color: black; font-size: 11pt; margin: auto; z-index: 0; font-weight: 700; color: black; font-family: 微软雅黑, sans-serif;\">")
                        .append(module)
                        .append("</span></b></div>\r\n")
                        .append("                                </td>\r\n")
                        .append("                                <td style=\"font-size: 12pt; height: 16.5pt; z-index: 0; width: 120px; padding: 8px; border: 1px solid;\">\r\n")
                        .append("                                    <div align=\"center\" style=\"border-color: rgb(0, 0, 0); font-size: 10.5pt; margin: 0cm 0cm 0pt; text-align: center; z-index: 0;\"><b\r\n")
                        .append("                                         style=\"border-color: rgb(0, 0, 0); font-size: 10.5pt; margin: auto; z-index: 0;\"><span style=\"border-color: black; font-size: 11pt; margin: auto; z-index: 0; font-weight: 700; color: black; font-family: 微软雅黑, sans-serif;\">")
                        .append(keyWord)
                        .append("</span></b></div>\r\n")
                        .append("                                </td>\r\n")
                        .append("                                <td style=\"font-size: 12pt; height: 16.5pt; z-index: 0; width: 274px; padding: 8px; border: 1px solid;\">\r\n")
                        .append("                                    <div align=\"center\" style=\"border-color: rgb(0, 0, 0); font-size: 10.5pt; margin: 0cm 0cm 0pt; text-align: center; z-index: 0;\"><b\r\n")
                        .append("                                         style=\"border-color: rgb(0, 0, 0); font-size: 10.5pt; margin: auto; z-index: 0;\"><span style=\"border-color: black; font-size: 11pt; margin: auto; z-index: 0; font-weight: 700; color: black; font-family: 微软雅黑, sans-serif;\">")
                        .append(synopsis).append("</span></b></div>\r\n")
                        .append("                                </td>\r\n")
                        .append("                                <td style=\"font-size: 12pt; height: 16.5pt; z-index: 0; width: 49px; padding: 8px; border: 1px solid;\">\r\n")
                        .append("                                    <div align=\"center\" style=\"border-color: rgb(0, 0, 0); font-size: 10.5pt; margin: 0cm 0cm 0pt; text-align: center; z-index: 0;\"><b\r\n")
                        .append("                                         style=\"border-color: rgb(0, 0, 0); font-size: 10.5pt; margin: auto; z-index: 0;\"><span style=\"border-color: black; font-size: 11pt; margin: auto; z-index: 0; font-weight: 700; color: black; font-family: 微软雅黑, sans-serif;\">")
                        .append(influenceNumber)
                        .append("</span></b></div>\r\n")
                        .append("                                </td>\r\n")
                        .append("                                <td style=\"font-size: 12pt; height: 16.5pt; z-index: 0; width: 63px; padding: 8px; border: 1px solid;\">\r\n")
                        .append("                                    <div align=\"center\" style=\"border-color: rgb(0, 0, 0); font-size: 10.5pt; margin: 0cm 0cm 0pt; text-align: center; z-index: 0;\"><b\r\n")
                        .append("                                         style=\"border-color: rgb(0, 0, 0); font-size: 10.5pt; margin: auto; z-index: 0;\"><span style=\"border-color: black; font-size: 11pt; margin: auto; z-index: 0; font-weight: 700; color: black; font-family: 微软雅黑, sans-serif;\">")
                        .append(type)
                        .append("</span></b></div>\r\n")
                        .append("                                </td>\r\n")
                        .append("                                <td style=\"font-size: 12pt; height: 16.5pt; z-index: 0; width: 73px; padding: 8px; border: 1px solid;\">\r\n")
                        .append("                                    <div align=\"center\" style=\"border-color: rgb(0, 0, 0); font-size: 10.5pt; margin: 0cm 0cm 0pt; text-align: center; z-index: 0;\"><b\r\n")
                        .append("                                         style=\"border-color: rgb(0, 0, 0); font-size: 10.5pt; margin: auto; z-index: 0;\"><span style=\"border-color: black; font-size: 11pt; margin: auto; z-index: 0; font-weight: 700; color: black; font-family: 微软雅黑, sans-serif;\">")
                        .append(remark).append("</span></b></div>\r\n")
                        .append("                                </td>\r\n")
                        .append("                            </tr>");
                }
            }
        return dynamicTable.toString();
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
    public void addData(String number,String module,String keyWord,String synopsis,int influenceNumber,String type,String remark) {
        String dataStr = number+"|"+module+"|"+keyWord+"|"+synopsis+"|"+ influenceNumber +"|"+type+"|"+remark;
        tableList.add(dataStr);
    }
	
	
	/**
	 *  用于独立调试
     *
     * @param args 入参表
	 */
	@SuppressWarnings("CommentedOutCode")
    public static void main(String[] args) {
        GStatic gs = new GStatic();
        gs.testInit();
		
	    //此部分为有报错情况下的样例
	    //addData("1","物资管理","GEPSST-16308","【材料采购结算单】批次明细无税结算单价计算不正确",2,"已知bug","主干已修改");
	    //tableList.add("2|机械管理|GEPSST-000|【材算单】批次明细无税结算单价计算不正确|1|已知bug|主干已修改");
	    new GHtmlExportBugs("自动化测试报告:GEPS（本地调试邮件）",
	    					"hew-d@glodon.com",
	    					"hew-d@glodon.com",
	    					"hew-d@glodon.com",
	    					"hew-d@glodon.com");

        gs.testFinish();
	}
}
