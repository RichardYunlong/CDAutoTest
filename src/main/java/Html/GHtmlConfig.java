package main.java.Html;

import main.java.Base.GClazz;
import main.java.Base.GFile;
import main.java.Base.GMsg;
import main.java.DT.GLog;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

/**
 *  Html模板驱动
 */
public class GHtmlConfig {
	
    /**
     *  构造函数
     */
	private GHtmlConfig(){
		GClazz.thisAToolClass();
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
		try(OutputStream fos = Files.newOutputStream(templateFile.toPath())) {
			content = FileUtils.readFileToString(templateFile, "utf-8");
			content = content.replaceAll("###payurl###", url);
			content = content.replaceAll("###content1###", msg);
			content = content.replaceAll("###content2###", sign);
			
			fos.write(content.getBytes(StandardCharsets.UTF_8));
			fos.flush();
			
			Runtime.getRuntime().exec(GMsg.MSG_CMD[0] + TAR_TEMP);
		} catch (IOException e) {
			GLog.logSysFunctionException("OpenHtmlPayPageCCB", e);
		}
	}
}
