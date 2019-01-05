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
		FileInputStream fis;
		try {
			fis = new FileInputStream(file);
			@SuppressWarnings("resource")
			Scanner scanner = new Scanner(fis);
			while(scanner.hasNextLine()){
				scanner.nextLine();
				count++;
			}
			System.out.println(count);
		} catch (FileNotFoundException e) {
			System.out.println("FAIL TO READ TXT FILE");
			e.printStackTrace();
		}
		
		return count;
	}
	
	/**
	 *  简单读
	 */
    public static String readFile(String fileName) {
        String fileContent = "";
        try {
            File f = new File(fileName);
            if (f.isFile() && f.exists()) {
                InputStreamReader read = new InputStreamReader(
                        new FileInputStream(f), "utf-8");
                BufferedReader reader = new BufferedReader(read);
                String line;
                while ((line = reader.readLine()) != null) {
                    fileContent += line;
                }
                read.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return fileContent;
    }
    
	/**
	 *  简单写
	 */
    public static void writeFile(String fileName, String fileContent) {
        try {
            File f = new File(fileName);
            if (!f.exists()) {
                f.createNewFile();
            }
            OutputStreamWriter write = new OutputStreamWriter(
                    new FileOutputStream(f), "utf-8");
            BufferedWriter writer = new BufferedWriter(write);
            writer.write(fileContent);
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

	/**
	 *  读取固定文件中的固定字符串
	 */
	public static String readString(String URL)

	{
		String str = "";
		File file = new File(URL);
		try {
			FileInputStream in = new FileInputStream(file);
			// size 为字串的长度 ，这里一次性读完
			int size = in.available();
			byte[] buffer = new byte[size];
			in.read(buffer);
			in.close();
			str = new String(buffer, "utf-8");
		} catch (IOException e) {
			return null;
		}
		return str;
	}

	/**
	 *  按行读取指定目录下的txt文件中的字符串
	 */
	public static String ReadTxtLine(String txtPath, long lineNo) {
		String line = "";
		String encoding = "UTF-8";
		try {
			File txtFile = new File(txtPath);
			InputStream in = new FileInputStream(txtFile);
			InputStreamReader read = new InputStreamReader(in, encoding);
			BufferedReader reader = new BufferedReader(read);
			int i = 0;
			while (i < lineNo) {
				line = reader.readLine();
				i++;
			}
			reader.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return line;
	}

	/**
	 *  按行读取指定目录下的txt文件中的字符串
	 */
	public static String[] ReadTxtLineSplitByTag(String txtPath, long lineNo, String tag) {
		String line = "";
		String encoding = "UTF-8";
		try {
			File txtFile = new File(txtPath);
			InputStream in = new FileInputStream(txtFile);
			InputStreamReader read = new InputStreamReader(in, encoding);
			BufferedReader reader = new BufferedReader(read);
			int i = 0;
			while (i < lineNo) {
				line = reader.readLine();
				i++;
			}
			reader.close();
			PARAMS_LINENO = line.split(tag);
			if (PARAMS_LINENO == null) {
				System.out.println("WRONG OR EMPTY PARAMS FILE");
			}
		} catch (Exception e) {
			e.printStackTrace();
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

		GFile.DeleteFolder("C:\\Users\\hewei\\Desktop\\ErrorCourt.txt");
		GFile.WriteStringToBottom("C:\\Users\\hewei\\Desktop\\ErrorCourt.txt", GTime.getDate());
		GFile.WriteStringToBottom("C:\\Users\\hewei\\Desktop\\ErrorCourt.txt", "COURT RESULT FOLLOWS:");
		while (TestNo.intValue() != 0 && TestNo.intValue() <= dLine) {
			String strParam = GText.ReadTxtLine(url, TestNo.intValue());
			System.out.println("CHECKING ROW:" + TestNo.toString());
			TestNo++;
			if (strParam == "") {
				System.out.println("BLANK ROW,CHECK NEXT");
				continue;
			} else {
				String strT = strParam;
				String strM = tag;
				int dIndex;
				dIndex = 0;
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
		for (int i = 0; i < 5; i++) {
			GFile.WriteStringToBottom("C:\\Users\\hewei\\Desktop\\ErrorCourt.txt",
					"ERROR CODE:" + strError[i] + "ERROR COURT:" + dError[i]);
		}
		GFile.WriteStringToBottom("C:\\Users\\hewei\\Desktop\\ErrorCourt.txt", GTime.getDate());
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
		} catch (IOException e) {
			e.printStackTrace();
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
		try {
			FileInputStream in = new FileInputStream(file);
			// size 为字串的长度 ，这里一次性读完
			int size = in.available();
			byte[] buffer = new byte[size];
			in.read(buffer);
			in.close();
			str = new String(buffer, "utf-8");
		} catch (IOException e) {
			return null;
		}
		return str;
	}

	/**
	 * 删除Txt文本中还有某关键字的所有行
	 */
	public static void DeteleTxtLineByKeyword(String path, String keyWord) {
		try {
			// String remLine =keyWord;
			File file = new File(path);
			BufferedReader br = new BufferedReader(new FileReader(file));
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
				 * //整行匹配 if(temp.trim().equals(remLine)){ System.out.println("找到了要删除的行");
				 * continue; } sb.append(temp+"\r\n");
				 */
			}
			br.close();
			BufferedWriter bw = new BufferedWriter(new FileWriter(file));
			bw.write(sb.toString());
			bw.close();
			System.out.println("DELETE ROW WHICH CONTAIN[" + keyWord + "] OK!");
		} catch (Exception e) {
			System.out.println("DELETE ROW WHICH CONTAIN[" + keyWord + "] ERROR!");
		}
	}

//	 public static void main(String[] agrs) {
//		 DeleteBlankLine("C:\\Users\\hewei\\Desktop\\test\\errorcode.txt","C:\\Users\\hewei\\Desktop\\test\\errorcode111.txt");
//	 }
}
