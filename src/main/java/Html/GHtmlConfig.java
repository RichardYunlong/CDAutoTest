package Html;

import Base.GClazz;
import Base.GFile;
import Base.GMsg;
import DT.GLog;
import Sys.GPath;
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
	private static final String SRC_TEMP = GPath.HTML_TEMP_PATH + "prePage.html";
	
	/**
	 *  支付添加参数页面
	 */
	private static final String TAR_TEMP = GPath.TEST_TEMP_PATH + "prePage1.html";
	
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
		String content;
		try {
			// 必须先读取模板再回写：原实现在读取前就用 newOutputStream 打开同一文件(立即截断)，
			// 读到的是空内容，模板替换实际从未生效。
			content = FileUtils.readFileToString(templateFile, "utf-8");
			// 使用字面量替换，避免 url/msg 中的 $ \ 被当作正则替换符
			content = content.replace("###payurl###", url);
			content = content.replace("###content1###", msg);
			content = content.replace("###content2###", sign);

			try (OutputStream fos = Files.newOutputStream(templateFile.toPath())) {
				fos.write(content.getBytes(StandardCharsets.UTF_8));
				fos.flush();
			}

			Runtime.getRuntime().exec(GMsg.MSG_CMD[0] + TAR_TEMP);
		} catch (IOException e) {
			GLog.logSysFunctionException("OpenHtmlPayPageCCB", e);
		}
	}
}
