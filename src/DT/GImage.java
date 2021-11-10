package DT;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.commons.codec.binary.Base64;

import Base.GClazz;
import Base.GFile;

public class GImage {

    /**
     *  构造函数
     */
	private GImage(){
		GClazz.thisAToolClass();
	}
	
    /**
     * 将图片文件转化为字节数组字符串，并对其进行Base64编码处理
     * @param strImgFilePath 待处理图片
     * @return 
     */
    public static String getImgBase64Str(String strImgFilePath) {
        byte[] data = null;
        try(InputStream in = new FileInputStream(strImgFilePath);) {
            data = new byte[in.available()];
            int size = in.read(data);
            if(size > 0) {
            	GLog.logRecord(8, "ImgSize[" + size + "]");
            }
        } catch (IOException e) {
        	GLog.logSysFunctionException("getImgBase64Str", e);
        }
        return new String(Base64.encodeBase64(data));
    }
	
    /**
     * 对字节数组字符串进行Base64解码并生成图片
     * @param strImgStr 图片数据
     * @param strImgFilePath 保存图片全路径地址
     * @return
     */
    @SuppressWarnings("static-access")
	public static boolean generateImage(String strImgStr, String strImgFilePath) {
        if (strImgStr == null)
            return false;
        Base64 decoder = new Base64();
        byte[] b = decoder.decodeBase64(strImgStr);
        for (int i = 0; i < b.length; ++i) {
            if (b[i] < 0) {// 调整异常数据
                b[i] += 256;
            }
        }
        
        try(OutputStream out = new FileOutputStream(strImgFilePath);) { 
            out.write(b);
            out.flush();
            return true;
        } catch (Exception e) {
        	GLog.logSysFunctionException("generateImage", e);
            return false;
        }
    }
    
    public static void main(String[] args) {
        String imgFile = "./image/icon.jpg";// 待处理的图片
        String imgFileTemp = "./image/icon_temp.jpg";// 新生成的图片
        
        GFile.deleteFile(imgFileTemp);
        
        String imgbese = getImgBase64Str(imgFile);
        GLog.logRecord(8, "图片base64编码长度：" + imgbese.length() + "\r\n图片base64编码：" + imgbese);

        generateImage(imgbese, imgFileTemp);
    }
}
