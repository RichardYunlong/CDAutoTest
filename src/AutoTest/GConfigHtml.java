package AutoTest;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import org.apache.commons.io.FileUtils;


public class GConfigHtml {
	private GConfigHtml(){
		GLog.logShowConsole("This is a tool class.");
	}
	
	/**
	 *  支付模板页面
	 */
	private static final String SRC_TEMP = "./config/prePage.html";
	
	/**
	 *  支付添加参数页面
	 */
	private static final String TAR_TEMP = "./test/prePage1.html";
	
	/**
	 * 判断文件是否存在
	 * 
	 * @param url 支付地址
	 * @param msg 支付信息
	 * @param sign 支付签名
	 */
	public static void OpenHtmlPayPageCCB(String url, String msg, String sign) {
		
		File temp = new File(TAR_TEMP);
		if(temp.exists())GFile.deleteFile(TAR_TEMP);
		
		GFile.copyFile(SRC_TEMP, TAR_TEMP);
		
		
		File templateFile = new File(TAR_TEMP);
		String content = null;
		OutputStream fos = null;
		try {
			content = FileUtils.readFileToString(templateFile, "utf-8");
			content = content.replaceAll("###payurl###", url);
			content = content.replaceAll("###content1###", msg);
			content = content.replaceAll("###content2###", sign);
			fos = new FileOutputStream(templateFile);
			fos.write(content.getBytes("UTF-8"));
			fos.flush();
			fos.close();
			Runtime.getRuntime().exec("cmd.exe /c start " + TAR_TEMP);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if(fos != null)fos.close();
			} catch (IOException e) {
				GLog.logShowConsole(GMsg.MSG_CONSOLE[0] + TAR_TEMP);
				e.printStackTrace();
			}
		}
	}
	
//	public static void main(String[] agrs) {
//		String url = "https://ccbcstest.casupport.cn:8443/CSServer/initPayment.do";
//		
//		OpenHtmlPayPageCCB(url, "", "");
//	}
}
