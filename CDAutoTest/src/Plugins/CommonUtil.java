package Plugins;

import java.io.File;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.PBEParameterSpec;

import AutoTest.GConfig;

public class CommonUtil {
	public static boolean isEmpty(String str) {
		return (str == null || str.trim().length() == 0);
	}

	public static boolean isNotEmpty(String str) {
		return (str != null && str.trim().length() != 0);
	}

	public static void welcome() {
		System.out.println("*******************************************************************");
		System.out.println("*                                                                 *");
		System.out.println("************************欢迎使用RA客户端测试工具*************************");
		System.out.println("*                                                                 *");
		int dLeft = (68-GConfig.WelcomeStr.length())/2;
		for(int i=0;i<dLeft;i++) {
			System.out.print("*");
		}
		System.out.print(GConfig.WelcomeStr);
		for(int i=0;i<dLeft;i++) {
			System.out.print("*");
		}
		System.out.println("*                                                                 *");
		System.out.println("*******************************************************************");
	}

	public static String getConfigPath() {
		return System.getProperty(SystemConst.CONFIG_LOCATION) + File.separator;
	}

	public static void dealException(Throwable e, String message) {
		System.out.println("*****************" + message + "***************");
		e.printStackTrace();
		System.exit(0);
	}
	
	public static String decodeStr(String inputStr) {
		String decodeResult = null;
		if (null != inputStr && !"".equals(inputStr)) {
			decodeResult = new String(decrypt(Base64.decode(inputStr)));
		}
		return decodeResult;
	}
	
	private static byte[] decrypt(byte[] ciphertext) {
		try {
			// "authcode-encrypt"为口令
			PBEKeySpec keySpec = new PBEKeySpec("authcode-encrypt".toCharArray());
			SecretKeyFactory keyFac = SecretKeyFactory.getInstance("PBEWithMD5AndDES");
			SecretKey key = keyFac.generateSecret(keySpec);

			// "CFCA--RA"为盐值,必为8个字符
			String salt = new String("CFCA--RA");
			// 迭代次数为1000
			PBEParameterSpec paramSpec = new PBEParameterSpec(salt.getBytes(), 1000);
			// 加密算法是"PBEWithMD5AndDES"
			Cipher cipher = Cipher.getInstance("PBEWithMD5AndDES");
			cipher.init(Cipher.DECRYPT_MODE, key, paramSpec);
			return cipher.doFinal(ciphertext);
		} catch (Exception e) {
			return ciphertext;
		}
	}
}