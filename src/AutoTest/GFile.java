package AutoTest;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

/**
 *  文件、文件夹操作
 */
public class GFile {
	private GFile(){
		GLog.logShowConsole("This is a tool class.");
	}

	/**
	 *  主日志全名
	 */
	public static String strLogFullName = "";

	/**
	 *  文件操作结果标记
	 */
	public static boolean flag = false;

	/**
	 *  文件变量
	 */
	public static File file = null;

	/**
	 *  压缩操作缓存大小
	 */
	private static final int BUFFER_SIZE = 2 * 1024;
	
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
		File file = null;
		boolean res = false;
		if (!filePath.equals("")) {
			file = new File(filePath);
		} else {
			GSys.logErrorSys(GMsg.MSG_NOTFOUND[2]);
			return res;
		}
		
		if (file.exists()) {
			res = true;
		} else {
			GSys.logErrorSys(GMsg.MSG_EXIST[1]);
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
		if(GExcel.checkExcel(strFullPath) && !file.renameTo(file))
		{
			result = true;
			GSys.logErrorSys(GMsg.MSG_ISOPENED[1]);
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
			GSys.logErrorSys(GMsg.MSG_ISOPENED[1]);
		}
		
		return result;
	}

	/**
	 * 删除单个文件
	 * 
	 * @param sPath 被删除文件的文件名
	 * @return 单个文件删除成功返回true，否则返回false
	 */
	public static boolean deleteFile(String sPath) {
		flag = false;
		file = new File(sPath);
		// 路径为文件且不为空则进行删除
		if (file.isFile() && file.exists() && file.delete()) {
				flag = true;
		}
		return flag;
	}

	/**
	 * 删除目录（文件夹）以及目录下的文件
	 * 
	 * @param sPath 被删除目录的文件路径
	 * @return 目录删除成功返回true，否则返回false
	 */
	public static boolean deleteDirectory(String sPath) {
		boolean res = false;
		// 如果sPath不以文件分隔符结尾，自动添加文件分隔符
		if (!sPath.endsWith(File.separator)) {
			sPath = sPath + File.separator;
		}
		File dirFile = new File(sPath);
		// 如果dir对应的文件不存在，或者不是一个目录，则退出
		if (!dirFile.exists() || !dirFile.isDirectory()) {
			return res;
		}
		flag = true;
		// 删除文件夹下的所有文件(包括子目录)
		File[] files = dirFile.listFiles();
		if (files != null && files.length >= 1) {
			for (int i = 0; i < files.length; i++) {
				// 删除子文件
				if (files[i].isFile()) {
					flag = deleteFile(files[i].getAbsolutePath());
					if (!flag)
						break;
				} // 删除子目录
				else {
					flag = deleteDirectory(files[i].getAbsolutePath());
					if (!flag)
						break;
				}
			}	
		} else {
			GSys.logErrorSys("NO CHILD FILES");
		}

		if (!flag)return res;
		// 删除当前目录
		if (dirFile.delete()) {
			res = true;
		}
		
		return res;
	}

	/**
	 * 根据路径删除指定的目录或文件，无论存在与否
	 * 
	 * @param sPath 要删除的目录或文件
	 * @return 删除成功返回 true，否则返回 false
	 */
	public static boolean deleteFolder(String sPath) {
		flag = false;
		file = new File(sPath);
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
		BufferedWriter out = null;
		OutputStreamWriter outS = null;
		FileOutputStream outF = null;
		try {
			if(file != null) {
				outF = new FileOutputStream(file, true);
				if(null != outF) {
					outS = new OutputStreamWriter(outF, "UTF-8");
					if(outS != null) {
						out = new BufferedWriter(outS);
						out.write(conent + "\r\n");
						GLog.logShowConsole(conent);
					}
				}
			}
		} catch (Exception e) {
			GLog.logShowConsole(GMsg.MSG_CONSOLE[0] + file + "" + conent);
			e.printStackTrace();
		} finally {
			try {
				if(out != null)out.close();
				if(outS != null)outS.close();
				if(outF != null)outF.close();
			} catch (IOException e) {
				GLog.logShowConsole(GMsg.MSG_CONSOLE[0] + file + "" + conent);
				e.printStackTrace();
			}
		}
	}

	/**
	 * 根据文件全名，向其右边添加指定文本，如果改文件不存在则创建
	 * 
	 * @param file 目标文件全名
	 * @param conent 指定内容
	 */
	public static void writeStringToRight(String file, String conent) {
		if(null != GLog.strLogStyle && file.equals(GLog.strLogStyle[4]) && !GTestPlan.bTestOutputBackupResult) {
			return;
		}
		BufferedWriter out = null;
		OutputStreamWriter outS = null;
		FileOutputStream outF = null;
		try {
			if(null != file) {
				outF = new FileOutputStream(file, true);
				if(null != outF) {
					outS = new OutputStreamWriter(outF, "UTF-8");
					if(null != outS) {
						out = new BufferedWriter(outS);
						out.write(conent);
					}
				}
			}
		} catch (Exception e) {
			GLog.logShowConsole(GMsg.MSG_CONSOLE[0] + file + "" + conent);
			e.printStackTrace();
		} finally {
			try {
				if(out != null)out.close();
				if(outS != null)outS.close();
				if(outF != null)outF.close();
			} catch (IOException e) {
				GLog.logShowConsole(GMsg.MSG_CONSOLE[0] + file + "" + conent);
				e.printStackTrace();
			}
		}
	}

	/**
	 * 根据路径字符串创建路径
	 * 
	 * @param path 文件路径
	 * @return 创建成功返回 true，否则返回 false
	 */
	public static boolean creatDir(String path) {
		boolean flag = false;
		strLogFullName = path;
		File filename = new File(strLogFullName);
		if (!filename.exists()) {
			filename.mkdir();
			flag = true;
		}
		return flag;
	}
	
	/**
	 * 根据自定义文件全名创建txt文件
	 * 
	 * @param path 文件路径
	 * @param name 文件名
	 * @return 创建成功返回 true，否则返回 false
	 */
	public static boolean creatTxtFile(String path, String name) throws IOException {
		boolean flag = false;
		strLogFullName = path + name + ".txt";
		File filename = new File(strLogFullName);
		if (!filename.exists() && filename.createNewFile()) {
			flag = true;
		}
		return flag;
	}
	
	/**
	 * 根据自定义文件全名创建文件
	 * 
	 * @param strFullPath
	 * @return 创建成功返回 true，否则返回 false
	 */
	public static boolean creatXlsFile(String strFullPath) throws IOException {
		boolean flag = false;
		strLogFullName = strFullPath;
		File filename = new File(strLogFullName);
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
    @SuppressWarnings({ "deprecation", "unused" })
	public static void createExcel(String fileDir,String sheetName,String[] titleRow) {  
        //创建workbook  
        workbook = new HSSFWorkbook();  
        //添加Worksheet（不添加sheet时生成的xls文件打开时会报错)  
        HSSFSheet sheet1 = workbook.createSheet(sheetName);    
        //新建文件  
        FileOutputStream out = null;  
        try {  
            //添加表头  
            HSSFRow row = workbook.getSheet(sheetName).createRow(0);    //创建第一行    
            for(short i = 0;i < titleRow.length;i++){  
                HSSFCell cell = row.createCell(i);  
                cell.setCellValue(titleRow[i]);  
            }  
            out = new FileOutputStream(fileDir);  
            workbook.write(out);  
        } catch (Exception e) {
			e.printStackTrace();
        } finally {    
            try {
            	if(out != null)out.close();  
            } catch (IOException e) {    
                e.printStackTrace();  
            }    
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
                workbook = new HSSFWorkbook(new FileInputStream(file));  
                //添加Worksheet（不添加sheet时生成的xls文件打开时会报错)  
                HSSFSheet sheet = workbook.getSheet(sheetName);    
                if(sheet!=null)  
                    flag = true;  
            } catch (Exception e) {  
            	e.printStackTrace();
            }   
              
         }
         
         return flag;  
    }  
	
    /** 
     * 删除xls文件
     * 
     * @param fileDir 文件路径
     * @return 删除成功返回 true，否则返回 false
     */  
    public static boolean deleteExcel(String fileDir) {  
        boolean flag = false;  
        File file = new File(fileDir);  
        // 判断目录或文件是否存在    
        if (!file.exists()) {  // 不存在返回 false    
            return flag;    
        } else {    
            // 判断是否为文件    
            if (file.isFile()) {  // 为文件时调用删除文件方法    
                file.delete();  
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
		File file = new File(strInFile);
		InputStream is = null;
		BufferedReader br = null;
		String tmp;
		FileWriter writer = null;
		int i = 0;
		try {
			is = new BufferedInputStream(new FileInputStream(file));
			br = new BufferedReader(new InputStreamReader(is, "utf-8"));
			if(null != strOutFile)
				writer = new FileWriter(strOutFile, true);
			if(null != writer) {
				while ((tmp = br.readLine()) != null) {
					if (tmp.equals("")) {
						;
					} else {
						writer.write(tmp + "\n");
						i++;
						GLog.logShowConsole(Integer.toString(i));
					}
				}
			}else {
				GSys.logErrorSys(GMsg.MSG_IOFAILED[2]);
			}
        	if(writer != null)writer.close();
        	if(br != null)br.close();
        	if(is != null)is.close();
		} catch (IOException e) {
			GSys.logErrorSys(GMsg.MSG_IOFAILED[1]);
			e.printStackTrace();
		} finally {
            try {
            	if(writer != null)writer.close();
            } catch (IOException e) {    
                e.printStackTrace();  
            }
            try {
            	if(br != null)br.close();
            } catch (IOException e) {    
                e.printStackTrace();  
            } 
            try {
            	if(is != null)is.close();
            } catch (IOException e) {    
                e.printStackTrace();  
            }
		}
	}

	/**
	 * 压缩成ZIP 方法1
	 * 
	 * @param srcDir 压缩文件夹路径
	 * @param out 压缩文件输出流
	 * @param bKeepDirStructure 是否保留原来的目录结构,true:保留目录结构;false:所有文件跑到压缩包根目录下(注意：不保留目录结构可能会出现同名文件,会压缩失败)
	 * @throws RuntimeException 压缩失败会抛出运行时异常
	 */
	public static void toZip(String srcDir, OutputStream out, boolean bKeepDirStructure) throws RuntimeException {

		long start = System.currentTimeMillis();
		ZipOutputStream zos = null;
		try {
			zos = new ZipOutputStream(out);
			File sourceFile = new File(srcDir);
			compress(sourceFile, zos, sourceFile.getName(), bKeepDirStructure);
			long end = System.currentTimeMillis();
			GSys.logSys("ZIP COST:" + (end - start) + " ms");
			GTestPlan.bTestOutputBackupResult = true;
		} catch (Exception e) {
			throw new RuntimeException("ZIP ERROR FROM ZIPUTILS", e);
		} finally {
			if (zos != null) {
				try {
					zos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

	}

	/**
	 * 压缩成ZIP 方法2
	 * 
	 * @param srcFiles 需要压缩的文件列表
	 * @param out 压缩文件输出流
	 * @throws RuntimeException 压缩失败会抛出运行时异常
	 */
	public static void toZip(List<File> srcFiles, OutputStream out) throws RuntimeException {
		long start = System.currentTimeMillis();
		ZipOutputStream zos = null;
		FileInputStream in = null;
		try {
			zos = new ZipOutputStream(out);
			for (File srcFile : srcFiles) {
				byte[] buf = new byte[BUFFER_SIZE];
				zos.putNextEntry(new ZipEntry(srcFile.getName()));
				int len;
				in = new FileInputStream(srcFile);
				while ((len = in.read(buf)) != -1) {
					zos.write(buf, 0, len);
				}
				zos.closeEntry();
				in.close();
			}
			long end = System.currentTimeMillis();
			GSys.logSys("ZIP COST:" + (end - start) + " ms");
			if(null != in)in.close();
		} catch (Exception e) {
			throw new RuntimeException("ZIP ERROR FROM ZIPUTILS", e);
		} finally {
			try {
				if(zos != null)zos.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			try {
				if(in != null)in.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 递归压缩方法
	 * 
	 * @param sourceFile 源文件
	 * @param zos zip输出流
	 * @param name 压缩后的名称
	 * @param KeepDirStructure 是否保留原来的目录结构,true:保留目录结构;false:所有文件跑到压缩包根目录下(注意：不保留目录结构可能会出现同名文件,会压缩失败)
	 * @throws Exception
	 */
	private static void compress(File sourceFile, ZipOutputStream zos, String name, boolean bKeepDirStructure)
			throws Exception {
		FileInputStream in = null;
		byte[] buf = new byte[BUFFER_SIZE];
		try {
			if (sourceFile.isFile()) {
				// 向zip输出流中添加一个zip实体，构造器中name为zip实体的文件的名字
				zos.putNextEntry(new ZipEntry(name));
				// copy文件到zip输出流中
				int len;
				in = new FileInputStream(sourceFile);
				while ((len = in.read(buf)) != -1) {
					zos.write(buf, 0, len);
				}
				// Complete the entry
				zos.closeEntry();
				in.close();
			} else {
				File[] listFiles = sourceFile.listFiles();
				if (listFiles == null || listFiles.length == 0) {
					// 需要保留原来的文件结构时,需要对空文件夹进行处理
					if (bKeepDirStructure) {
						// 空文件夹的处理
						zos.putNextEntry(new ZipEntry(name + "/"));
						// 没有文件，不需要文件的copy
						zos.closeEntry();
					}
	
				} else {
					for (File file : listFiles) {
						// 判断是否需要保留原来的文件结构
						if (bKeepDirStructure) {
							// 注意：file.getName()前面需要带上父文件夹的名字加一斜杠,
							// 不然最后压缩包中就不能保留原来的文件结构,即：所有文件都跑到压缩包根目录下了
							compress(file, zos, name + "/" + file.getName(), bKeepDirStructure);
						} else {
							compress(file, zos, file.getName(), bKeepDirStructure);
						}
	
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if(in != null)in.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
    /** 
     * 复制TXT文件
     * 
     * @param srcDir 源文件路径
     * @param destDir 目标文件路径
     * @return 删除成功返回 true，否则返回 false
     */  
    public static boolean copyFile(String srcDir, String destDir) {  
        boolean flag = false;
        
        FileInputStream in = null;
        FileOutputStream out = null;
        BufferedReader br = null;
        BufferedWriter bw = null;
        
        try {
        	in = new FileInputStream(srcDir);
        	br = new BufferedReader(new InputStreamReader(in,"UTF-8"));
        	out = new FileOutputStream(destDir);
    		bw = new BufferedWriter(new OutputStreamWriter(out,"UTF-8"));

    		String line = null;
    		while ((line = br.readLine()) != null) {
    			bw.write(line);
    			bw.newLine();
    			bw.flush();
    		}
    		
    		bw.close();
    		br.close();
    		out.close();
    		in.close();
    		
    		flag = true;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if(bw != null)bw.close();
				if(br != null)br.close();
				if(out != null)out.close();
				if(in != null)in.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} 
        
        return flag;
    }
}
