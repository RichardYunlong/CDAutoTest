package Base;

import DT.GLog;
import Sys.GStatic;
import Sys.GSys;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import static IO.GExcelBase.checkExcel;

/**
 *  文件、文件夹操作
 */
public class GFile {
	
    /**
     *  构造函数
     */
	private GFile(){
		GClazz.thisAToolClass();
	}
	
	/**
	 *  用于创建工作表
	 */
	private static HSSFWorkbook workbook = null; 
	
	/**
	 * 判断文件是否存在
	 * 
	 * @param filePath 文件全名
	 * @return 创建成功返回true，否则返回false
	 */
	public static boolean judeFileExists(String filePath) {
		boolean res = false;
		
		File file;
		if (!filePath.isEmpty()) {
			file = new File(filePath);
		} else {
			GFile.writeStringToGuideBottom(GMsg.MSG_NOTFOUND[2]);
			return false;
		}
		if (file.exists()) {
			res = true;
		} else {
			GFile.writeStringToGuideBottom(GMsg.MSG_EXIST[1]);
		}
		
		return res;
	}
	
	/**
	 * 判断文件是否被占用
	 * 
	 * @param strFullPath 被删除文件的文件名
	 * @return 已被占用返回true，否则返回false
	 */
	public static boolean bIsOpened(String strFullPath){
		boolean result = false;
		
		File file = new File(strFullPath);
		if(checkExcel(strFullPath) && !file.renameTo(file))
		{
			result = true;
			GFile.writeStringErrorToGuideBottom(GMsg.MSG_ISOPENED[1]);
		}
		
		return result;
	}
	
	/**
	 * 如果文档打开则关闭
	 * 
	 * @param strFullPath 被删除文件的文件名
	 * @return 关闭返回true，否则返回false
	 */
	public static boolean bIsOpenedBeClose(String strFullPath){
		boolean result = false;
		
		File file = new File(strFullPath);
		if(!file.renameTo(file))
		{
			result = true;
			GFile.writeStringErrorToGuideBottom(GMsg.MSG_ISOPENED[1]);
		}
		
		return result;
	}
	
	/**
	 * 按行读取文件
	 * 
	 * @param strFullPath 文件全名
	 * @return count 文件行数
	 */
	public static long getFileRows(String strFullPath) {
		long count = 0L;
		
		File file = new File(strFullPath);
		if (!file.exists()) {
			GFile.writeStringErrorToGuideBottom(GMsg.MSG_EXIST[1]);
		}

		try(FileInputStream fis = new FileInputStream(file);Scanner scanner = new Scanner(fis)) {
			while(scanner.hasNextLine()){
				scanner.nextLine();
				count++;
			}
		} catch (Exception e) {
			GLog.logSysFunctionException("getFileRows", e);
		}	

		return count;
	}

	/**
	 * 删除单个文件
	 * 
	 * @param sPath 被删除文件的文件名
	 * @return 单个文件删除成功返回true，否则返回false
	 */
	public static boolean deleteFile(String sPath) {
		boolean flag = false;
		
		File file = new File(sPath);
		// 路径为文件且不为空则进行删除
		if (file.isFile() && file.exists()) {
			flag = file.delete();
		}else {
			GFile.writeStringErrorToGuideBottom("[" + sPath +"]DELETE FAILED");
		}
		
		return flag;
	}
	
	/**
	 * 判断是否是个有效的文件或路径
	 * 
	 * @param sPath 目标文件或路径
	 * @return 如果是个有效的文件或路径，则返回其File对象
	 */
	public static File isAvailableDirectory(String sPath) {
		File dirFile;
		
		// 如果sPath不以文件分隔符结尾，自动添加文件分隔符
		if (!sPath.endsWith(File.separator)) {
			sPath = sPath + File.separator;
		}
		dirFile = new File(sPath);
		
		// 如果dir对应的文件不存在，或者不是一个目录，则退出
		if (!dirFile.exists() || !dirFile.isDirectory()) {
			return null;
		}
		
		return dirFile;
	}
	
	/**
	 * 根据文件对象数组删除文件或路径
	 * 如果是个有效的文件或路径，则返回其File对象
	 * 
	 * @param files 目标文件或路径
	 */
	public static void deleteFileArray(File[] files) {
        for (File file : files) {
            if (file.isFile()) {// 删除子文件
                if (!deleteFile(file.getAbsolutePath()))
                    break;
            } else {// 删除子目录
                if (!deleteDirectory(file.getAbsolutePath()))
                    break;
            }
        }
	}

	/**
	 * 删除目录（文件夹）以及目录下的文件
	 * 
	 * @param sPath 目标文件或路径
	 * @return 目录删除成功返回true，否则返回false
	 */
	public static boolean deleteDirectory(String sPath) {
		boolean flag = false;
		File dirFile = isAvailableDirectory(sPath);
		
		if(dirFile != null) {
			File[] files = dirFile.listFiles();
			if (files != null && files.length >= 1) {
				// 删除文件夹下的所有文件(包括子目录)
				deleteFileArray(files);
			}
			// 删除当前目录
			flag = dirFile.delete();
		} else {
			GFile.writeStringToBottom(GSys.GUIDE,"WARN----" + "[" + sPath +"]" + GMsg.MSG_EXIST[1]);
		}

		return flag;
	}
	
	/**
	 * 清空目录（文件夹）
	 * 
	 * @param sPath 被删除目录的文件路径
	 * @return 清空成功返回true，否则返回false
	 */
	@SuppressWarnings("UnusedReturnValue")
    public static boolean clearDirectory(String sPath) {
		boolean res = false;
		
		if(deleteDirectory(sPath)) {
			res = creatDir(sPath);
		}

		return res;
	}
	
	/**
	 * 清空文本（文件夹）
	 * 
	 * @param sPath 被删除目录的文件全名
	 * @return 清空成功返回true，否则返回false
	 */
	@SuppressWarnings("UnusedReturnValue")
    public static boolean clearFile(String sPath) {
		boolean res = false;
		
		File file =new File(sPath);

        try(FileWriter fileWriter =new FileWriter(file)) {
            fileWriter.write("");
            fileWriter.flush();
            res = true;
        } catch (IOException e) {
        	GLog.logSysFunctionException("clearFile", e);
        }
        
		return res;
	}

	/**
	 * 根据路径删除指定的目录或文件，无论存在与否
	 * 
	 * @param sPath 要删除的目录或文件
	 * @return 删除成功返回 true，否则返回 false
	 */
	@SuppressWarnings("UnusedReturnValue")
    public static boolean deleteFolder(String sPath) {
		boolean flag = false;
		
		File file = new File(sPath);
		// 判断目录或文件是否存在
		if (!file.exists()) { // 不存在返回 false
			return flag;
		} else {
			// 判断是否为文件
			if (file.isFile()) { // 为文件时调用删除文件方法
				return deleteFile(sPath);
			} else { // 为目录时调用删除目录方法
				return deleteDirectory(sPath);
			}
		}
	}

	/**
	 * 根据文件全名，向其尾部换行添加指定文本，如果文件不存在则创建
	 * 
	 * @param file 目标文件全名
	 * @param conent 指定内容
	 */
	public static void writeStringToBottom(String file, String conent) {
		try(FileOutputStream outF = new FileOutputStream(file, true);
			OutputStreamWriter outS = new OutputStreamWriter(outF, StandardCharsets.UTF_8);
			BufferedWriter out = new BufferedWriter(outS)
			) {
				out.write(conent + "\r\n");
				GLog.logShowConsole(conent);
		} catch (Exception e) {
			GLog.logSysFunctionException("writeStringToBottom " + GMsg.MSG_CONSOLE[0] + file + conent, e);
		}
	}

	/**
	 * 根据文件全名，向其右边添加指定文本，如果改文件不存在则创建
	 * 
	 * @param file 目标文件全名
	 * @param conent 指定内容
	 */
	public static void writeStringToRight(String file, String conent) {
		try(FileOutputStream outF = new FileOutputStream(file, true);
			OutputStreamWriter outS = new OutputStreamWriter(outF, StandardCharsets.UTF_8);
			BufferedWriter out = new BufferedWriter(outS)
		) {
			out.write(conent);
		} catch (Exception e) {
			GLog.logSysFunctionException("writeStringToRight " + GMsg.MSG_CONSOLE[0] + file + conent, e);
		}
	}

	/**
	 * 根据路径字符串创建路径
	 * 
	 * @param path 文件路径
	 * @return 创建成功返回 true，否则返回 false
	 */
	@SuppressWarnings("ResultOfMethodCallIgnored")
    public static boolean creatDir(String path) {
		boolean flag = false;
		
		File filename = new File(path);
		if (!filename.exists()) {
			filename.mkdir();
			flag = true;
		}
		
		return flag;
	}
	
	/**
	 * 根据自定义文件全名创建简单文本文件
	 * 创建成功返回 true，否则返回 false
	 * 
	 * @param path 文件路径
	 * @param name 文件名
	 * @param format 文件扩展名
	 *
	 * @throws IOException 创建异常
	 *
	 * @return 创建成功返回true
	 */
	@SuppressWarnings("UnusedReturnValue")
    public static boolean creatFile(String path, String name, String format) throws IOException {
		boolean flag = false;
		
		File filename = new File(path + name + "." + format);
		if (!filename.exists() && filename.createNewFile()) {
			flag = true;
		}
		
		return flag;
	}
	
	/**
	 * 根据自定义文件全名创建txt文件
	 * 创建成功返回 true，否则返回 false
	 * 
	 * @param path 文件路径
	 * @param name 文件名
	 *
	 * @throws IOException 创建异常
	 *
	 * @return 创建成功返回true
	 */
	public static boolean creatTxtFile(String path, String name) throws IOException {
		boolean flag = false;
		
		File filename = new File(path + name + ".txt");
		if (!filename.exists() && filename.createNewFile()) {
			flag = true;
		}
		
		return flag;
	}
	
	/**
	 * 根据自定义文件全名创建简单文件
	 * 此处的“简单”指用基本文本编辑器即可编辑的简单文件，其他带特殊格式的暂不支持
	 * 创建成功返回 true，否则返回 false
	 * 
	 * @param path 文件路径
	 * @param name 文件名
	 *
	 * @throws IOException 创建异常
	 *
	 * @return 创建成功返回true
	 */
	@SuppressWarnings("UnusedReturnValue")
    public static boolean creatSimpleFile(String path, String name) throws IOException {
		boolean flag = false;
		
		File filename = new File(path + name);
		if (!filename.exists() && filename.createNewFile()) {
			flag = true;
		}
		
		return flag;
	}
	
	/**
	 * 根据自定义文件全名创建文件
	 * 创建成功返回 true，否则返回 false
	 * 
	 * @param strFullPath 路径全名
	 *
	 * @throws IOException 创建异常
	 *
	 * @return 创建成功返回true
	 */
	@SuppressWarnings("UnusedReturnValue")
    public static boolean creatXlsFile(String strFullPath) throws IOException {
		boolean flag = false;
		
		File filename = new File(strFullPath);
		if (!filename.exists() && filename.createNewFile()) {
			flag = true;
		}
		
		return flag;
	}

    /** 
     * 创建新excel
     * 
     * @param fileDir  excel的路径 
     * @param sheetName 要创建的表格索引 
     * @param titleRow excel的第一行即表格头 
     */
	public static void createExcel(String fileDir,String sheetName,String[] titleRow) {  
        //创建workbook  
        workbook = new HSSFWorkbook();  
        HSSFRow row = workbook.getSheet(sheetName).createRow(0);    //创建第一行    
        for(short i = 0;i < titleRow.length;i++){  
            HSSFCell cell = row.createCell(i);
            cell.setCellValue(titleRow[i]);  
        }
      	//新建文件
        try(FileOutputStream out = new FileOutputStream(fileDir)) {
            workbook.write(out);  
        } catch (Exception e) {
			GLog.logSysFunctionException("createExcel", e);
        }
    } 
    
    /** 
     * 判断xls的sheet是否存在. 
     * @param fileDir   文件路径 
     * @param sheetName  表格索引名 
     * @return 存在成功返回 true，否则返回 false
     */  
    public static boolean sheetExist(String fileDir,String sheetName) {  
         boolean flag = false;
         
         File file = new File(fileDir);  
         if(file.exists()){    //文件存在  
            //创建workbook  
             try {  
                workbook = new HSSFWorkbook(Files.newInputStream(file.toPath()));
                //添加Worksheet（不添加sheet时生成的xls文件打开时会报错)  
                HSSFSheet sheet = workbook.getSheet(sheetName);    
                if(sheet!=null)  
                    flag = true;  
            } catch (Exception e) {
            	GLog.logSysFunctionException("sheetExist", e);
            }   
         }
         
         return flag;  
    }  
	
    /** 
     * 删除xls文件
	 * 删除成功返回 true，否则返回 false
     * 
     * @param fileDir 文件路径
	 *
	 * @throws IOException 删除异常
	 *
	 * @return 删除成功返回true
     */  
    @SuppressWarnings("UnusedReturnValue")
    public static boolean deleteExcel(String fileDir) throws IOException{
        boolean flag = false;  

    	File file = new File(fileDir);
    	Path fPath = Paths.get(fileDir);
        // 判断目录或文件是否存在    
        if (!file.exists()) {  // 不存在返回 false    
            return false;
        } else {    
            // 判断是否为文件    
            if (file.isFile()) {  // 为文件时调用删除文件方法    
            	Files.delete(fPath);
            	flag = true;
            }   
        }
	    
        return flag;  
    } 
    
	/**
	 * 保存指定文件没有空行的副本，仅接受完整路径文件名
	 * 
	 * @param strInFile 输入文件全名
	 * @param strOutFile 输出文件全名
	 */
	public static void deleteBlankLine(String strInFile, String strOutFile) {
		if(strInFile != null && strOutFile != null) {
			File file = new File(strInFile);
			String tmp;
			int i = 0;
			try(InputStream is = new BufferedInputStream(Files.newInputStream(file.toPath()));
                BufferedReader br = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));
                FileWriter writer = new FileWriter(strOutFile, true)) {
				while ((tmp = br.readLine()) != null) {
					if (!tmp.isEmpty()) {
						writer.write(tmp + "\n");
						i++;
						GLog.logShowConsole(Integer.toString(i));
					}
				}
			} catch (IOException e) {
				GLog.logSysFunctionException("deleteBlankLine", e);
			}
		}
	}

    /** 
     * 复制TXT文件
     * 
     * @param srcDir 源文件全名
     * @param destDir 目标文件全名
     * @return 删除成功返回 true，否则返回 false
     */  
    public static boolean copyFile(String srcDir, String destDir) {  
        boolean flag = false;
        
        try(FileInputStream in = new FileInputStream(srcDir);
        	InputStreamReader inr =	new InputStreamReader(in,StandardCharsets.UTF_8);
        	BufferedReader br = new BufferedReader(inr);
        	FileOutputStream out = new FileOutputStream(destDir);
        	OutputStreamWriter otw = new OutputStreamWriter(out,StandardCharsets.UTF_8);
        	BufferedWriter bw = new BufferedWriter(otw)) {
        	
    		String line;
    		while ((line = br.readLine()) != null) {
    			bw.write(line);
    			bw.newLine();
    			bw.flush();
    		}
    		
    		flag = true;
		} catch (Exception e) {
			GLog.logSysFunctionException("copyFile", e);
		}
        return flag;
    }

	/**
	 * 二进制文件复制
	 *
	 * @param srcDir 源文件全名
	 * @param destDir 目标文件全名
	 * @return 删除成功返回 true，否则返回 false
	 */
	@SuppressWarnings("UnusedReturnValue")
    public static boolean copyBinaryFile(String srcDir, String destDir) {
		boolean flag = false;
		File srcfile = new File(srcDir);
		File destfile = new File(destDir);

		try(InputStream input = Files.newInputStream(srcfile.toPath());
            OutputStream output = Files.newOutputStream(destfile.toPath())) {
			byte[] buf = new byte[1024];
			int bytesRead;
			while ((bytesRead = input.read(buf)) > 0) {
				output.write(buf, 0, bytesRead);
			}

			output.flush();

			flag = true;
		} catch (Exception e) {
			GLog.logSysFunctionException("copyFile", e);
		}
		return flag;
	}
    
    /** 
     * 复制件到指定全名
	 * 删除成功返回 true，否则返回 false
     * 
     * @param source 源文件
     * @param dir 目标文件路径
     * @param dest 目标文件名称全名
     */  
    @SuppressWarnings("ResultOfMethodCallIgnored")
    public static void copyFile(File source, String dir, String dest){
    	File filedir = new File(dir);
    	if (!filedir.exists())    
            filedir.mkdirs();  
        File file = new File(filedir, dest); 
        try(InputStream input = Files.newInputStream(source.toPath());
            OutputStream output = Files.newOutputStream(file.toPath())) {
        	byte[] buf = new byte[1024];     
        	int bytesRead;
    		while ((bytesRead = input.read(buf)) > 0) { 
    	        output.write(buf, 0, bytesRead); 
    	  	} 
        	output.flush(); 
        } catch(IOException e) {
		  GLog.logSysFunctionException("copyFile", e);
        }
    } 

    /**
     * html转StringBuffer
     * 
     * @param htmlFilePath html文件
     * @return StringBuffer值 
     */
    public static StringBuilder html2StringBuffer(String htmlFilePath) {
    	StringBuilder htmlSB = new StringBuilder();
    	try(FileReader file = new FileReader(htmlFilePath);
    		BufferedReader br = new BufferedReader(file)) {
            String line;
            while ((line=br.readLine())!=null) {
            	htmlSB.append(line);
				if(GStatic.gP.isbAutoCheckReport()){
					GLog.logRecord(8, "加载行：" + line);
				}
            }
    	}catch(Exception e) {
    		GLog.logSysFunctionException("html2StringBuffer", e);
    	}
    	return htmlSB;
    }
    
    /**
     * 按指定行数分割大文件
     * 
     * @param targetFile 目标文件路径
     * @param saveDir 存放的目录
     * @param saveFileName 生成文件的前缀名
     * @param suffix 生成文件的后缀名
     * @param splitSize  每一个文件 多少行数据
     */
    public static void splitFile(String targetFile, 
    		String saveDir , 
    		String saveFileName, 
    		String suffix,
    		long splitSize) {

        if( !saveDir.endsWith("\\") ){
            saveDir += File.separator;
        }

        File file = new File(targetFile);
        if (!file.exists()) {
        	GLog.logSysFunctionException("splitFile", new Exception("[ " + targetFile + " ]" + GMsg.MSG_PATH_EXIST[1]));
        }
        
        String str;
        // 行数
        long len = 0;

        GLog.logRecord(8, "start split ......");
        long startTime = System.currentTimeMillis();
        
        // 输入缓冲流
    	try(FileInputStream fs = new FileInputStream(file);
    		InputStreamReader is = new InputStreamReader(fs,StandardCharsets.UTF_8);
    		BufferedReader reader = new BufferedReader(is)) {
	        // 输出缓冲流
	        while ((str = reader.readLine()) != null) {
	            // 当前行文件
	            long txtSize = (len / splitSize) + 1;
	            String fileName = saveDir + saveFileName + GTime.getCurrentTime(GTime.FORMAT_14) + "_" + txtSize + "." + suffix;
	            splitFileBufferedWriter(str, fileName);
	            len++;
	        }
	        GLog.logRecord(8, "split complete,these was " + len + " reacords,cost " + ( System.currentTimeMillis() - startTime ) / 1000 + " s");
    	}catch(Exception e) {
    		GLog.logSysFunctionException("splitFile", e);
    	}
    }
    
    /**
     * 按指定行数分割大文件
     * 
     * @param str 待写入的内容
     * @param fileName 目标文件路径
     */
    private static void splitFileBufferedWriter(String str, String fileName) {
        // 使用 BufferedWriter 如果 不进行 flush 或者 close 写入不了内容。
        try(FileOutputStream file =	new FileOutputStream(fileName, true);
        	OutputStreamWriter ot =	new OutputStreamWriter(file,"gb2312");
        	BufferedWriter writer = new BufferedWriter(ot)){
            writer.write(str + System.lineSeparator() );
            writer.flush();
        }catch(Exception e) {
        	GLog.logSysFunctionException("splitFileBufferedWriter", e);
    	}
    }
    
    /**
     * 删除txt文本前若干行
     * 
     * @param srcFile 源文件路径
     * @param lineNum 存放的目录
	 *
	 * @throws IOException 删除异常
	 *
	 * @return 删除后的内容
     */
    @SuppressWarnings({"UnusedReturnValue", "RedundantOperationOnEmptyContainer"})
    public static List<String> readAndRemoveFirstLines(String srcFile, int lineNum) throws IOException {
    	List<String> strList = new ArrayList<>();
    	
    	File file = new File(srcFile);
    	if (!file.exists()) {
    		GLog.logRecord(8, GMsg.MSG_EXIST[1]);
    		strList.clear();
    		return strList;
    	}

        try(RandomAccessFile raf = new RandomAccessFile(file, "rw")){
             //Initial write position                                             
            long writePosition = raf.getFilePointer();
            for (int i = 0 ; i < lineNum ; i++){
                String line = raf.readLine();
                if(line == null){
                    break;
                }
                strList.add(line);                     
            }
            // Shift the next lines upwards.                                      
            long readPosition = raf.getFilePointer();                             
    
            byte[] buff = new byte[1024];                                         
            int n;                                                                
            while (-1 != (n = raf.read(buff))) {                                  
                raf.seek(writePosition);                                          
                raf.write(buff, 0, n);                                            
                readPosition += n;                                                
                writePosition += n;                                               
                raf.seek(readPosition);                                           
            }                                                                     
            raf.setLength(writePosition);
        } catch(IOException e){
        	GLog.logSysFunctionException("readAndRemoveFirstLines", e);
            throw e;
        }
        
        return strList;
    }
    
    /** 
     * 读取简单文本内容到字符串
     * 
     * @param path 目标文本全名
	 *
	 * @return 文本内容
     */  
    public static String getContent(String path) {  
    	StringBuilder a = new StringBuilder();
    	
        File f = new File(path); 
        try(FileInputStream is = new FileInputStream(f);
        	InputStreamReader read = new InputStreamReader(is, StandardCharsets.UTF_8);
        	BufferedReader reader = new BufferedReader(read)) {

            String line; 
            int i;
            for (i = 0; i < 20000; i++)   
            {  
                if ((line = reader.readLine()) != null)  
                {  
                	a.append(line);
                    a.append("\r\n"); 
                }  
            }
        } catch (Exception e) {
        	GLog.logRecord(8, Arrays.toString(e.getStackTrace()));
        }
        
        return a.toString();  
    }
    
    /** 
     * 读取txt内容到数组
     * 
     * @param path 目标文本全名
	 *
	 * @return 文本按行存放到数组
     */  
    public static String[] getTxtContent(String path) {  
    	String[] a = null;
    	
        File f = new File(path); 
        try(FileInputStream is = new FileInputStream(f);
        	InputStreamReader read = new InputStreamReader(is, StandardCharsets.UTF_8);
        	BufferedReader reader = new BufferedReader(read)) {
            a = new String[20000];
            String line;  
            int i;
            for (i = 0; i < 20000; i++)   
            {  
                if ((line = reader.readLine()) != null)  
                {  
                    a[i] = line;  
                }  
            }
        } catch (Exception e) {
        	GLog.logSysFunctionException("getTxtContent", e);
        }
        
        return a;  
    }
    
    /** 
     * 读取指定一行内容
     * 
     * @param path 文件路径全名
     * @param rowIndex 行号
     * 
     * @return 取到的字符串
     */  
    public static String getTxtByRowIndex(String path,Integer rowIndex) {  
        String[] s = getTxtContent(path);
        if(s != null) {
        	return s[rowIndex-1];  
        }else {
        	return "";
        }
    }  
    
    /** 
     * 读取指定区间行内容
     * 
     * @param path 文件路径全名
     * @param start 开始行号
     * @param end 结束行号
     * 
     * @return 取到的字符串
     */ 
    public static List<String> getTxtByRowInterval(String path,Integer start,Integer end) {  
        List<String> list;
        
        String[] s = getTxtContent(path);   
        if(s != null) {
            list = new ArrayList<>(Arrays.asList(s).subList(start - 1, end));
        }else {
        	list = null;
        }
        
        return list;  
    } 
    
    /** 
     * 使用输出流导出报告
     * 
     * @param keyword 关键词，用于识别是哪个步骤异常
     * @param content 要导出到报告的内容
     * @param templateFile 要导出到报告的存储文件
     */  
    public static void outputStreamReport(String keyword, String content, File templateFile) {
		if(content != null && templateFile != null) {
			try(OutputStream fos = Files.newOutputStream(templateFile.toPath())){
				fos.write(content.getBytes(StandardCharsets.UTF_8));
				fos.flush();
			} catch (IOException e) {
				GLog.logSysFunctionException(keyword, e);
			}
		}
    }

	/**
	 * 使用输出流导出报告
	 *
	 * @param content 写入引导系统日志的内容
	 */
	public static void writeStringToGuideBottom(String content) {
		GFile.writeStringToBottom(GSys.GUIDE, content);
	}

	/**
	 * 使用输出流导出报告
	 *
	 * @param content 写入引导系统日志的内容
	 */
	public static void writeStringErrorToGuideBottom(String content) {
		GFile.writeStringToBottom(GSys.GUIDE, "ERROR----" + content);
	}

}
