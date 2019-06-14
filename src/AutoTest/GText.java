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
import java.util.Scanner;

/**
 *  文本处理
 */
public class GText {
	
	/**
	 *  内容存储区
	 */
	public static String[] PARAMS_LINENO = null;
	
	/**
	 *  获得文本行数，包括空行
	 */
	public static int getTxtFileLineNum(String fileName) {
		int count=1;
		File file = new File(fileName);
		FileInputStream fis = null;
		Scanner scanner = null;
		try {
			fis = new FileInputStream(file);
			scanner = new Scanner(fis, "utf-8");
			while(scanner.hasNextLine()){
				scanner.nextLine();
				count++;
			}
			System.out.println(count);
			scanner.close();
		} catch (FileNotFoundException e) {
			GFile.WriteStringToBottom(GSys.Guide, "FAIL TO READ TXT FILE");
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
	 */
    public static String readFile(String fileName) {
        String fileContent = "";
        InputStreamReader read = null;
        BufferedReader reader = null;
        try {
            File f = new File(fileName);
            if (f.isFile() && f.exists()) {
                read = new InputStreamReader(new FileInputStream(f), "utf-8");
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
	 */
    public static void writeFile(String fileName, String fileContent) {
        OutputStreamWriter write = null;
        BufferedWriter writer = null;
        try {
            File f = new File(fileName);
            if (!f.exists()) {
                f.createNewFile();
            }
            write = new OutputStreamWriter(new FileOutputStream(f), "utf-8");
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
	 */
	public static String readString(String URL){
		FileInputStream in = null;
		String str = "";
		File file = new File(URL);
		try {
			in = new FileInputStream(file);
			// size 为字串的长度 ，这里一次性读完
			int size = in.available();
			byte[] buffer = new byte[size];
			int count = 0;
			count = in.read(buffer);
			if(count == 0)System.out.println("READ EMPTY");
			in.close();
			str = new String(buffer, "utf-8");
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
	 */
	public static String ReadTxtLine(String txtPath, long lineNo) {
		String line = "";
		String encoding = "UTF-8";
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
	 */
	public static String[] ReadTxtLineSplitByTag(String txtPath, long lineNo, String tag) {
		String line = "";
		String encoding = "UTF-8";
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
			PARAMS_LINENO = line.split(tag);
			if (PARAMS_LINENO == null) {
				GFile.WriteStringToBottom(GSys.Guide, "WRONG OR EMPTY PARAMS FILE");
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
		
		return PARAMS_LINENO;
	}

	/**
	 *  取出指定文本中的某个字符串并统计个数
	 */
	public static void CourtTag(String url, String tag, int dLine) {
		Integer TestNo = 1;
		String strError[] = new String[5];
		Integer dError[] = new Integer[5];

		for (int i = 0; i < 5; i++) {
			strError[i] = "";
			dError[i] = 0;
		}

		GFile.DeleteFolder(url);
		GFile.WriteStringToBottom(url, GTime.getDate());
		GFile.WriteStringToBottom(url, "COURT RESULT FOLLOWS:");
		while (TestNo.intValue() != 0 && TestNo.intValue() <= dLine) {
			String strParam = GText.ReadTxtLine(url, TestNo.intValue());
			GFile.WriteStringToBottom(GSys.Guide, "CHECKING ROW:" + TestNo.toString());
			TestNo++;
			if (strParam == "") {
				GFile.WriteStringToBottom(GSys.Guide, "BLANK ROW,CHECK NEXT");
				continue;
			} else {
				String strT = strParam;
				String strM = tag;
				int dIndex;
				dIndex = 0;
				if(strT != null) {
					dIndex = strT.indexOf(strM);
					if (dIndex != -1) {
						int indexCur = 5;
						// 是否已经存储该值
						for (int i = 0; i < 5; i++) {
							if (!strError[i].equals("")) {
								if (strError[i].equals(strT.substring(dIndex, dIndex + 14))) {
									indexCur = i;
									dError[indexCur]++;
								}
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
			GFile.WriteStringToBottom(url,
					"ERROR CODE:" + strError[i] + "ERROR COURT:" + dError[i]);
		}
		GFile.WriteStringToBottom(url, GTime.getDate());
	}

	/**
	 *  保存没有空行的副本，仅接受完整路径文件名
	 */
	public static int DeleteBlankLine(String InFile, String OutFile) {
		File file = new File(InFile);
		InputStreamReader is = null;
		BufferedReader br = null;
		String tmp;
		FileWriter writer = null;
		int i = 0;
		try {
			is = new InputStreamReader(new FileInputStream(file), "utf-8");
			br = new BufferedReader(is);
			writer = new FileWriter(OutFile, true);
			while ((tmp = br.readLine()) != null) {
				if (tmp.equals(""))
					;
				else {
					writer.write(tmp + "\n");
					i++;
					System.out.println("Non blank line"+i);
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
	 */
	public static String rdStringFromTxt()

	{
		String str = "";
		File file = new File(GLog.LogStyle[6]);
		FileInputStream in = null;
		try {
			in = new FileInputStream(file);
			// size 为字串的长度 ，这里一次性读完
			int size = in.available();
			byte[] buffer = new byte[size];
			int count = 0;
			count = in.read(buffer);
			if(count == 0)System.out.println("READ EMPTY");
			in.close();
			str = new String(buffer, "utf-8");
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
	 */
	public static void DeteleTxtLineByKeyword(String path, String keyWord) {
		BufferedReader br = null;
		BufferedWriter bw = null;
		try {
			// String remLine =keyWord;
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
				/*
				 * //整行匹配 if(temp.trim().equals(remLine)){ GFile.WriteStringToBottom(GSys.Guide, "找到了要删除的行");
				 * continue; } sb.append(temp+"\r\n");
				 */
			}
			br.close();
			bw = new BufferedWriter(new FileWriter(file));
			bw.write(sb.toString());
			bw.close();
			GFile.WriteStringToBottom(GSys.Guide, "DELETE ROW WHICH CONTAIN[" + keyWord + "] OK!");
		} catch (Exception e) {
			GFile.WriteStringToBottom(GSys.Guide, "DELETE ROW WHICH CONTAIN[" + keyWord + "] ERROR!");
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
	 */
	public static void DoLine(String str,int n) {
		if(n>=1) {
			for(int i = 0;i < n;i++) {
				System.out.print(str);
				GFile.WriteStringToRight(GLog.LogStyle[9], str);
			}
			System.out.print("\r\n");
			GFile.WriteStringToRight(GLog.LogStyle[9], "\r\n");
		}
	}

//	 public static void main(String[] agrs) {
//		 DeleteBlankLine("C:\\Users\\hewei\\Desktop\\test\\errorcode.txt","C:\\Users\\hewei\\Desktop\\test\\errorcode111.txt");
//	 }
}
