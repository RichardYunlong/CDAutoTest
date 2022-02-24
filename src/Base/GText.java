package Base;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

import DT.GLog;
import Sys.GSys;

/**
 *  文本处理
 */
public class GText {
	
    /**
     *  构造函数
     */
	private GText(){
		GClazz.thisAToolClass();
	}
	
	/**
	 *  随机字符串组成元素
	 */
	public static final String CHARS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
	
	/**
	 *  随机字符串组成元素：小写字母及数字
	 */
	public static final String LOWERCHARS = "abcdefghijklmnopqrstuvwxyz0123456789";
	
	/**
	 *  随机字符串组成元素：大写字母及数字
	 */
	public static final String CAPITALCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
	
	/**
	 *  获得文件中文本行数，包括回车行
	 *  
	 *  @param filePath 源文件全名
	 *  @return 返回文件中的行数
	 */
	public static int getRowsNum(String filePath) {
		int rowsNum=1;
		
		File file = new File(filePath);

		try(FileInputStream fis = new FileInputStream(file);
			Scanner scanner = new Scanner(fis, StandardCharsets.UTF_8.toString());) {
			while(scanner.hasNextLine()){
				scanner.nextLine();
				rowsNum++;
			}
			GLog.logShowConsole(Integer.toString(rowsNum));
		} catch (Exception e) {
			GLog.logSysFunctionException("getRowsNum", e);
		}

		return rowsNum;
	}
	
	/**
	 *  读取固定文件中的固定字符串-byte方式
	 *  
	 *  @param filePath 源文件全名
	 *  @return 返回文件中的内容
	 */
	public static String readString(String filePath){
		String fileContent = "";
		
		File file = new File(filePath);
		
		try(FileInputStream fis = new FileInputStream(file);) {
			// size 为字串的长度 ，这里一次性读完
			int size = fis.available();
			byte[] buffer = new byte[size];
			int count = 0;
			count = fis.read(buffer);
			if(count == 0) {
				GLog.logShowConsole("READ EMPTY");
			}
			fileContent = new String(buffer, StandardCharsets.UTF_8);
		} catch (IOException e) {
			GLog.logSysFunctionException("readString", e);
		}
		
		return fileContent;
	}
	
	/**
	 *  读取文本文件-buffer方式
	 *  
	 *  @param fileName 源文件全名
	 *  @return 返回文件中的内容
	 */
    public static String read(String filePath) {
    	StringBuilder fileContent = new StringBuilder("");
        
        File file = new File(filePath);
        
        try(FileInputStream fis= new FileInputStream(file);
        	InputStreamReader isr = new InputStreamReader(fis, StandardCharsets.UTF_8);
            BufferedReader br= new BufferedReader(isr);) {
                String row;
                while ((row = br.readLine()) != null) {
                	fileContent.append(row + "\r\n");
                }
            } catch (Exception e) {
            	GLog.logSysFunctionException("readFile", e);
            }
        
        return fileContent.toString();
    }
    
	/**
	 *  简单写
	 *  
	 *  @param filePath 目标文件全名
	 *  @param fileContent 待写入的内容
	 */
    public static void write(String filePath, String fileContent) {
        File file = new File(filePath);
        
        try {
			if (!file.exists() && file.createNewFile()) {
			    GLog.logShowConsole("createNewFile");
			}
		} catch (IOException e) {
			GLog.logShowConsole("createNewFile failed");
		}
        
        try(FileOutputStream fos = new FileOutputStream(file);
        	OutputStreamWriter osw = new OutputStreamWriter(fos, StandardCharsets.UTF_8);
            BufferedWriter bw = new BufferedWriter(osw);) {
        	bw.write(fileContent);
        } catch (Exception e) {
        	GLog.logSysFunctionException("writeFile", e);
        }
    }

	/**
	 *  按行号读取指定文件中的行
	 *  
	 *  @param filePath 源文件全名
	 *  @param index 行号
	 *  @return 返回指定行内容
	 */
	public static String readByRowIndex(String filePath, long index) {
		String row = "";

		File file = new File(filePath);
		
		try(InputStream in = new FileInputStream(file);
			InputStreamReader read = new InputStreamReader(in, StandardCharsets.UTF_8);
			BufferedReader reader = new BufferedReader(read);) {
			int i = 0;
			while (i < index) {
				row = reader.readLine();
				i++;
			}
		} catch (Exception e) {
			GLog.logSysFunctionException("readTxtLine", e);
		}

		return row;
	}

	/**
	 *  按行读取指定目录下的txt文件中的字符串
	 *  
	 *  @param filePath 源文件全名
	 *  @param index 行号
	 *  @param tag 分隔符
	 *  @return 返回按分隔符处理后的指定行内容的String[][]格式
	 */
	public static String[] readByRowIndexSplitByTag(String filePath, long index, String tag) {
		String row = "";
		String[] rowCols = null;

		File txtFile = new File(filePath);

		try(InputStream in = new FileInputStream(txtFile);
			InputStreamReader read = new InputStreamReader(in, StandardCharsets.UTF_8);
			BufferedReader reader = new BufferedReader(read);) {
			int i = 0;
			while (i < index) {
				row = reader.readLine();
				i++;
			}
			rowCols = row.split(tag);
			if (rowCols == null) {
				GSys.logErrorSys(GMsg.MSG_EXIST[0] + " OR " + GMsg.MSG_EMPTY[0]);
			}
		} catch (Exception e) {
			GLog.logSysFunctionException("readTxtLineSplitByTag", e);
		}
		
		return rowCols;
	}
	
	/**
	 * 删除Txt文本中还有某关键字的所有行
	 * 
	 *  @param filePath 源文件全名
	 *  @param tag 关键字
	 */
	public static void deteleTag(String filePath, String tag) {
		File file = new File(filePath);
		
		try(BufferedReader br = new BufferedReader(new FileReader(file));
			BufferedWriter bw = new BufferedWriter(new FileWriter(file));) {
			StringBuilder sb = new StringBuilder();
			String temp;
			while ((temp = br.readLine()) != null) {
				String strT = temp.trim();
				String strM = tag;
				int dIndex;
				dIndex = 0;
				dIndex = strT.indexOf(strM);
				if (dIndex != -1) {
					continue;
				}
				sb.append(temp + "\r\n");
			}
			bw.write(sb.toString());

			GSys.logSys("DELETE ROW WHICH CONTAIN[" + tag + "] OK!");
		} catch (Exception e) {
			GLog.logSysFunctionException("deteleTxtLineByKeyword", e);
		}
	}

	/**
	 *  保存没有空行的副本，仅接受完整路径文件名
	 *  
	 *  @param fileInPath 源文件全名
	 *  @param fileOutPath 目标文件全名
	 *  @return 返回处理的行数
	 */
	public static int deleteBlankRow(String fileInPath, String fileOutPath) {
		int i = 0;
		String tmp;
		
		File file = new File(fileInPath);
		
		try(FileInputStream fs = new FileInputStream(file);
			InputStreamReader is = new InputStreamReader(fs, StandardCharsets.UTF_8);
			BufferedReader br = new BufferedReader(is);
			FileWriter writer = new FileWriter(fileOutPath, true);) {
			while ((tmp = br.readLine()) != null) {
				if (!tmp.equals("")){
					writer.write(tmp + "\n");
					i++;
					GLog.logShowConsole("Non blank line"+i);
				}
			}
		} catch (IOException e) {
			GLog.logSysFunctionException("deleteBlankLine", e);
		}

		return i;
	}
	
	/**
	 *  控制台输出和日志保存的一行相同的符号，常用于作为视觉分割
	 *  
	 *  @param strPath 源文件全名
	 *  @param str 关键字
	 *  @param n 数量
	 */
	public static void doLine(String strPath, String str, int n) {
		if(n>=1) {
			for(int i = 0;i < n;i++) {
				GFile.writeStringToRight(strPath, str);
			}
			GFile.writeStringToRight(strPath, "\r\n");
		}
	}
	
	/**
	 *  按照长度值和组成字母获取指定字符串
	 *  
	 *  @param length
	 *  @param tChar
	 *  @return 返回目标字符串
	 */
	public static String getRandomStringByLength(int length, String tChar) {
		StringBuilder randomStringByLength = new StringBuilder();
		
		for (int i = 0; i < length; i++) {
			randomStringByLength.append(tChar);
		}

		return randomStringByLength.toString();
	}
	
	/**
	 *  按照长度值和取值范围(取值范围为英文字母大小写和0~9的数字)获取随机字符串
	 *  
	 *  支持指定大写、小写或常规
	 *  
	 *  @param length
	 *  @return 返回目标字符串
	 */
	public static String getRandomString(int length, String letterType) {
		StringBuilder sb = new StringBuilder();
		Random random = null;
		try {
			random = SecureRandom.getInstanceStrong();
		} catch (NoSuchAlgorithmException e) {
			GSys.logErrorSys(GMsg.MSG_RANDOM[0] + "[" + Arrays.toString(e.getStackTrace()) +"]");
		}
		
		if(random != null) {
		    for (int i = 0; i < length; i++) {
		    	int number = 0;
		    	switch(letterType) {
			    	case "LOWERCHARS":{
			    		number = random.nextInt(LOWERCHARS.length());
				        sb.append(LOWERCHARS.charAt(number));
			    		break;
			    	}
			    	case "CAPITALCHARS":{
			    		number = random.nextInt(CAPITALCHARS.length());
				        sb.append(CAPITALCHARS.charAt(number));
			    		break;
			    	}
			    	default:{
			    		number = random.nextInt(CHARS.length());
				        sb.append(CHARS.charAt(number));
			    		break;
			    	}
		    	}    
		    }
		}
		
		return sb.toString();
	}
	
	/**
	 *  获得指定格式的css字符串
	 *  
	 *  @param tagName 标签类型
	 *  @param tagProName 属性名
	 *  @param tagProValue 属性值
	 *  @return 返回目标字符串
	 */
	public static String getCssSelectorTxt(String tagName, String tagProName, String tagProValue) {		
		return "" + tagName + "[" + tagProName + "='" + tagProValue + "']";
	}
	
	/**
	 *  获得指定格式的Xpath字符串
	 *  
	 *  @param tagName 标签类型
	 *  @param tagProName 属性名
	 *  @param tagProValue 属性值
	 *  @return 返回目标字符串
	 */
	public static String getXpathTxt(String tagName, String tagProName, String tagProValue) {		
		return "//" + tagName + "[" + tagProName + "='" + tagProValue + "']";
	}
	
	/**
	 *  String[]转String
	 *  
	 *  @param array 字符串数组
	 *  @return 返回目标字符串
	 */
	public static String array2String(String[] array) {
		StringBuilder res = new StringBuilder();
		for(String strIndex:array) {
			res.append(strIndex);
		}
		return res.toString();
	}
	
	/**
	 *  html文件转String
	 *  
	 *  @param htmlFile 字符串数组
	 *  @return htmlStr 目标字符串
	 */
	public static String htmlFile2String(String htmlFilePath) {
		String htmlStr = "";
		File fin = new File(htmlFilePath);
		try (RandomAccessFile accessFile = new RandomAccessFile(fin, "r");
			FileChannel fcin = accessFile.getChannel();
		){
			Charset charset = Charset.forName(StandardCharsets.UTF_8.toString());
			int bufSize = 100000; 
		    ByteBuffer rBuffer = ByteBuffer.allocate(bufSize); 
			String enterStr = "\n";
			byte[] bs = new byte[bufSize];
			StringBuilder strline = new StringBuilder("");
			StringBuilder strBuf = new StringBuilder("");
			while (fcin.read(rBuffer) != -1) {
				int rSize = rBuffer.position();
				rBuffer.rewind();
				rBuffer.get(bs);
				rBuffer.clear();
				String tempString = new String(bs, 0, rSize,charset);
				tempString = tempString.replaceAll("\r", "");
 
				int fromIndex = 0;
				int endIndex = 0;
				while ((endIndex = tempString.indexOf(enterStr, fromIndex)) != -1) {
					String line = tempString.substring(fromIndex, endIndex);
					line = strBuf.toString() + line;
					strline.append(line.trim());
					
					strBuf.delete(0, strBuf.length());
					fromIndex = endIndex + 1;
				}
				if (rSize > tempString.length()) {
					strline.append(tempString.substring(fromIndex, tempString.length()));
					strBuf.append(tempString.substring(fromIndex, tempString.length()));
				} else {
					strline.append(tempString.substring(fromIndex, rSize));
					strBuf.append(tempString.substring(fromIndex, rSize));
				}
			}
			GLog.logRecord(8, strline.toString().replaceAll("\"", "'"));
		} catch (Exception e) {
			GLog.logSysFunctionException("htmlFile2String", e);
		}
		return htmlStr;
	}
	
	/**
	 *  获得某一字符串中某个字符的个数
	 *  
	 *  @param strRes 范围字符串
	 *  @param strTar 目标字符串
	 *  
	 *  @return court 目标字符
	 */
	public static int getRepeatCount(String strRes, String strTar) {
	    int count = 0;
	    
	    int origialLength = strRes.length();
	    strRes = strRes.replace(strTar, "");
	    int newLength = strRes.length();
	    count = origialLength - newLength;
	    
	    return count;
	}
	
	/**
	 *  去掉前后空格
	 *  
	 *  提出一个共同函数，便于后边修改或增加新功能
	 *  
	 *  @param str 源字符串
	 *  @return 返回目标字符串
	 */
	public static String getTrim(String str) {
		String trimed = "";
		
		trimed = str.trim();

		return trimed;
	}
}
