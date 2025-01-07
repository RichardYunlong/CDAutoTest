package main.java.DT;

import main.java.Base.GClazz;
import org.apache.commons.codec.binary.Base64;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Objects;

public class GImage {

    /**
     *  构造函数
     */
	private GImage(){
		GClazz.thisAToolClass();
	}
	
    /**
     * 将图片文件转化为字节数组字符串，并对其进行Base64编码处理
     *
     * @param strImgFilePath 待处理图片
     *
     * @return base64格式的内容
     */
    public static String getImgBase64Str(String strImgFilePath) {
        byte[] data = null;
        try(InputStream in = Files.newInputStream(Paths.get(strImgFilePath))) {
            data = new byte[in.available()];
            int size = in.read(data);
            if(size > 0) {
            	GLog.logRecord(8, "ImgSize[" + size + "]");
            }
        } catch (IOException e) {
        	GLog.logSysFunctionException("getImgBase64Str", e);
        }
        return new String(Objects.requireNonNull(Base64.encodeBase64(data)));
    }
	
    /**
     * 对字节数组字符串进行Base64解码并生成图片
     *
     * @param strImgStr 图片数据
     * @param strImgFilePath 保存图片全路径地址
     *
     * @return 生成图片成功返回true
     */
    @SuppressWarnings({"static-access", "UnusedReturnValue"})
	public static boolean generateImage(String strImgStr, String strImgFilePath) {
        if (strImgStr == null)
            return false;
        Base64 decoder = new Base64();
        byte[] b = decoder.decodeBase64(strImgStr);
        for (int i = 0; i < b.length; ++i) {
            if (b[i] < 0) {// 调整异常数据
                b[i] += (byte) 256;
            }
        }
        
        try(OutputStream out = Files.newOutputStream(Paths.get(strImgFilePath))) {
            out.write(b);
            out.flush();
            return true;
        } catch (Exception e) {
        	GLog.logSysFunctionException("generateImage", e);
            return false;
        }
    }
}
