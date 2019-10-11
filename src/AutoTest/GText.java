package AutoTest;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Random;
import java.util.Scanner;

/**
 *  文本处理
 */
public class GText {
	private GText(){
		GLog.logShowConsole("This is a tool class.");
	}
	
	/**
	 *  内容存储区
	 */
	public static String[] strParamsLineNo = null;
	
	/**
	 *  数据编码格式
	 */
	private static final String TEXTLANGUAGE = "UTF-8";
	
	/**
	 *  随机字符串组成元素
	 */
	public static final String CHARS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
	
	/**
	 *  获得文本行数，包括空行
	 *  
	 *  @param fileName 源文件全名
	 *  @return 返回文件中的行数
	 */
	public static int getTxtFileLineNum(String fileName) {
		int count=1;
		File file = new File(fileName);
		FileInputStream fis = null;
		Scanner scanner = null;
		try {
			fis = new FileInputStream(file);
			scanner = new Scanner(fis, TEXTLANGUAGE);
			while(scanner.hasNextLine()){
				scanner.nextLine();
				count++;
			}
			GLog.logShowConsole(Integer.toString(count));
			scanner.close();
		} catch (FileNotFoundException e) {
			GSys.logErrorSys("FAIL TO READ TXT FILE");
			e.printStackTrace();
		} finally {
			try {
				if(fis != null)fis.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			try {
				if(scanner != null)scanner.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		return count;
	}
	
	/**
	 *  简单读
	 *  
	 *  @param fileName 源文件全名
	 *  @return 返回文件中的内容
	 */
    public static String readFile(String fileName) {
        String fileContent = "";
        InputStreamReader read = null;
        BufferedReader reader = null;
        try {
            File f = new File(fileName);
            if (f.isFile() && f.exists()) {
                read = new InputStreamReader(new FileInputStream(f), TEXTLANGUAGE);
                reader = new BufferedReader(read);
                String line;
                while ((line = reader.readLine()) != null) {
                    fileContent += line;
                }
                read.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
			try {
				if(read != null)read.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			try {
				if(reader != null)reader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
        
        return fileContent;
    }
    
	/**
	 *  简单写
	 *  
	 *  @param fileName 目标文件全名
	 */
    public static void writeFile(String fileName, String fileContent) {
        OutputStreamWriter write = null;
        BufferedWriter writer = null;
        try {
            File f = new File(fileName);
            if (!f.exists()) {
                f.createNewFile();
            }
            write = new OutputStreamWriter(new FileOutputStream(f), TEXTLANGUAGE);
            writer = new BufferedWriter(write);
            writer.write(fileContent);
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
			try {
				if(write != null)write.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			try {
				if(writer != null)writer.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
    }

	/**
	 *  读取固定文件中的固定字符串
	 *  
	 *  @param strURL 源文件全名
	 *  @return 返回文件中的内容
	 */
	public static String readString(String strURL){
		FileInputStream in = null;
		String str = "";
		File file = new File(strURL);
		try {
			in = new FileInputStream(file);
			// size 为字串的长度 ，这里一次性读完
			int size = in.available();
			byte[] buffer = new byte[size];
			int count = 0;
			count = in.read(buffer);
			if(count == 0)GLog.logShowConsole("READ EMPTY");
			in.close();
			str = new String(buffer, TEXTLANGUAGE);
		} catch (IOException e) {
			return null;
		} finally {
			try {
				if(in != null)in.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		return str;
	}

	/**
	 *  按行读取指定目录下的txt文件中的字符串
	 *  
	 *  @param txtPath 源文件全名
	 *  @param lineNo 行号
	 *  @return 返回指定行内容
	 */
	public static String readTxtLine(String txtPath, long lineNo) {
		String line = "";
		String encoding = TEXTLANGUAGE;
		InputStream in = null;
		InputStreamReader read = null;
		BufferedReader reader = null;
		try {
			File txtFile = new File(txtPath);
			in = new FileInputStream(txtFile);
			read = new InputStreamReader(in, encoding);
			reader = new BufferedReader(read);
			int i = 0;
			while (i < lineNo) {
				line = reader.readLine();
				i++;
			}
			reader.close();
			read.close();
			in.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if(in != null)in.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			try {
				if(read != null)read.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			try {
				if(reader != null)reader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return line;
	}

	/**
	 *  按行读取指定目录下的txt文件中的字符串
	 *  
	 *  @param txtPath 源文件全名
	 *  @param lineNo 行号
	 *  @param tag 分隔符
	 *  @return 返回按分隔符处理后的指定行内容的String[][]格式
	 */
	public static String[] readTxtLineSplitByTag(String txtPath, long lineNo, String tag) {
		String line = "";
		String encoding = TEXTLANGUAGE;
		InputStream in = null;
		InputStreamReader read = null;
		BufferedReader reader = null;
		try {
			File txtFile = new File(txtPath);
			in = new FileInputStream(txtFile);
			read = new InputStreamReader(in, encoding);
			reader = new BufferedReader(read);
			int i = 0;
			while (i < lineNo) {
				line = reader.readLine();
				i++;
			}
			reader.close();
			strParamsLineNo = line.split(tag);
			if (strParamsLineNo == null) {
				GSys.logErrorSys(GMsg.MSG_EXIST[0] + " OR " + GMsg.MSG_EMPTY[0]);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if(in != null)in.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			try {
				if(read != null)read.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			try {
				if(reader != null)reader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		return strParamsLineNo;
	}

	/**
	 *  取出指定文本中的某个字符串并统计个数
	 *  
	 *  @param url 源文件全名
	 *  @param tag 关键字符
	 *  @param dLine 行号
	 */
	public static void courtTag(String url, String tag, int dLine) {
		Integer dTestNo = 1;
		String strError[] = new String[5];
		Integer dError[] = new Integer[5];

		for (int i = 0; i < 5; i++) {
			strError[i] = "";
			dError[i] = 0;
		}

		GFile.deleteFolder(url);
		GFile.writeStringToBottom(url, GTime.getDate());
		GFile.writeStringToBottom(url, "COURT RESULT FOLLOWS:");
		while (dTestNo.intValue() != 0 && dTestNo.intValue() <= dLine) {
			String strParam = GText.readTxtLine(url, dTestNo.intValue());
			GSys.logSys("CHECKING ROW:" + dTestNo.toString());
			dTestNo++;
			if (strParam == "") {
				GSys.logErrorSys("BLANK ROW,CHECK NEXT");
				continue;
			} else {
				String strT = strParam;
				String strM = tag;
				int dIndex = 0;
				if(strT != null) {
					dIndex = strT.indexOf(strM);
					if (dIndex != -1) {
						int indexCur = 5;
						// 是否已经存储该值
						for (int i = 0; i < 5; i++) {
							if (!strError[i].equals("") && strError[i].equals(strT.substring(dIndex, dIndex + 14))) {
									indexCur = i;
									dError[indexCur]++;
							}
						}
						// 如果没有存储过,则在第一个空位置存储该值
						if (indexCur == 5)
							for (int i = 0; i < 5; i++) {
								if (strError[i].equals("")) {
									strError[i] = strT.substring(dIndex, dIndex + 14);
									i = 5;
								}
							}
					} else {
						continue;
					}
				}
			}
		}
		for (int i = 0; i < 5; i++) {
			GFile.writeStringToBottom(url,
					"ERROR CODE:" + strError[i] + "ERROR COURT:" + dError[i]);
		}
		GFile.writeStringToBottom(url, GTime.getDate());
	}

	/**
	 *  保存没有空行的副本，仅接受完整路径文件名
	 *  
	 *  @param strInFile 源文件全名
	 *  @param strOutFile 目标文件全名
	 *  @return 返回处理的行数
	 */
	public static int deleteBlankLine(String strInFile, String strOutFile) {
		File file = new File(strInFile);
		InputStreamReader is = null;
		BufferedReader br = null;
		String tmp;
		FileWriter writer = null;
		int i = 0;
		try {
			is = new InputStreamReader(new FileInputStream(file), TEXTLANGUAGE);
			br = new BufferedReader(is);
			writer = new FileWriter(strOutFile, true);
			while ((tmp = br.readLine()) != null) {
				if (!tmp.equals("")){
					writer.write(tmp + "\n");
					i++;
					GLog.logShowConsole("Non blank line"+i);
				}
			}
			writer.close();
			is.close();
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if(is != null)is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			try {
				if(br != null)br.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			try {
				if(writer != null)writer.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return i;
	}

	/**
	 *  读取固定文件中的固定字符串
	 *  
	 *  @return 返回固定文件中的内容
	 */
	public static String rdStringFromTxt()

	{
		String str = "";
		File file = new File(GLog.strLogStyle[6]);
		FileInputStream in = null;
		try {
			in = new FileInputStream(file);
			// size 为字串的长度 ，这里一次性读完
			int size = in.available();
			byte[] buffer = new byte[size];
			int count = 0;
			count = in.read(buffer);
			if(count == 0)GLog.logShowConsole("READ EMPTY");
			in.close();
			str = new String(buffer, TEXTLANGUAGE);
		} catch (IOException e) {
			return null;
		} finally {
			try {
				if(in != null)in.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return str;
	}

	/**
	 * 删除Txt文本中还有某关键字的所有行
	 * 
	 *  @param path 源文件全名
	 *  @param keyWord 关键字
	 */
	public static void deteleTxtLineByKeyword(String path, String keyWord) {
		BufferedReader br = null;
		BufferedWriter bw = null;
		try {
			File file = new File(path);
			br = new BufferedReader(new FileReader(file));
			StringBuilder sb = new StringBuilder();
			String temp;
			while ((temp = br.readLine()) != null) {
				String strT = temp.trim();
				String strM = keyWord;
				int dIndex;
				dIndex = 0;
				dIndex = strT.indexOf(strM);
				if (dIndex != -1) {
					continue;
				}
				sb.append(temp + "\r\n");
			}
			br.close();
			bw = new BufferedWriter(new FileWriter(file));
			bw.write(sb.toString());
			bw.close();
			GSys.logSys("DELETE ROW WHICH CONTAIN[" + keyWord + "] OK!");
		} catch (Exception e) {
			GSys.logErrorSys("DELETE ROW WHICH CONTAIN[" + keyWord + "] ERROR!");
		} finally {
			try {
				if(br != null)br.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			try {
				if(bw != null)bw.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 *  控制台输出和日志保存的一行相同的符号，常用于作为视觉分割
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
		String randomStringByLength = "";
		
		for (int i = 0; i < length; i++) {
			randomStringByLength = randomStringByLength + tChar;
		}

		return randomStringByLength;
	}
	
	/**
	 *  按照长度值和取值范围获取随机字符串
	 *  
	 *  @param length
	 *  @return 返回目标字符串
	 */
	public static String getRandomStringByLength(int length) {		
		Random random = new Random();
		StringBuffer sb = new StringBuffer();
	    for (int i = 0; i < length; i++) {
	        int number = random.nextInt(CHARS.length());
	        sb.append(CHARS.charAt(number));
	    }

		return sb.toString();
	}
}
