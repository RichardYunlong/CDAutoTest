package AutoTest;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.commons.codec.binary.Base64;

public class GImage {
	private GImage(){
		GLog.logShowConsole("This is a tool class.");
	}
	
    /**
     * 将图片文件转化为字节数组字符串，并对其进行Base64编码处理
     * @param strImgFilePath 待处理图片
     * @return 
     */
    public static String getImgBase64Str(String strImgFilePath) {
        InputStream in = null;
        byte[] data = null;
        try {
            in = new FileInputStream(strImgFilePath);
            data = new byte[in.available()];
            in.read(data);
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
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
        try {
            byte[] b = decoder.decodeBase64(strImgStr);
            for (int i = 0; i < b.length; ++i) {
                if (b[i] < 0) {// 调整异常数据
                    b[i] += 256;
                }
            }
            OutputStream out = new FileOutputStream(strImgFilePath);
            out.write(b);
            out.flush();
            out.close();
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    
    public static void main(String[] args) {
        String imgFile = "./image/icon.jpg";// 待处理的图片
        String imgFileTemp = "./image/icon_temp.jpg";// 新生成的图片
        
        GFile.deleteFile(imgFileTemp);
        
        String imgbese = getImgBase64Str(imgFile);
        System.out.println("图片base64编码长度：" + imgbese.length() + "\r\n图片base64编码：" + imgbese);

        generateImage(imgbese, imgFileTemp);
    }
}
