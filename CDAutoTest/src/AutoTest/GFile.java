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
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class GFile {
	/**
	 * @see 主日志路径
	 */
	public static String LogPath = "";

	/**
	 * @see 主日志文件名
	 */
	public static String LogName = "";

	/**
	 * @see 主日志全名
	 */
	public static String LogFullName = "";

	/**
	 * @see 文件操作结果标记
	 */
	public static boolean flag = false;

	/**
	 * @see 文件变量
	 */
	public static File file = null;

	/**
	 * @see 压缩操作缓存大小
	 */
	private static final int BUFFER_SIZE = 2 * 1024;
	
	/**
	 * 删除单个文件
	 * 
	 * @param sPath
	 *            被删除文件的文件名
	 * @return 单个文件删除成功返回true，否则返回false
	 */
	public static boolean judeFileExists(String filePath) {
		File file = null;
		boolean res = false;
		if (!filePath.equals("")) {
			file = new File(filePath);
		} else {
			System.out.println("UNKNOWN FILE PATH!");
			return res;
		}
		
		if (file.exists()) {
			System.out.println("FILE EXISTS");
			res = true;
		} else {
			System.out.println("FILE DOESN'T EXISTS");
		}
		return res;
	}

	/**
	 * 删除单个文件
	 * 
	 * @param sPath
	 *            被删除文件的文件名
	 * @return 单个文件删除成功返回true，否则返回false
	 */
	public static boolean deleteFile(String sPath) {
		flag = false;
		file = new File(sPath);
		// 路径为文件且不为空则进行删除
		if (file.isFile() && file.exists()) {
			file.delete();
			flag = true;
		}
		return flag;
	}

	/**
	 * 删除目录（文件夹）以及目录下的文件
	 * 
	 * @param sPath
	 *            被删除目录的文件路径
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
			System.out.println("NO CHILD FILES");
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
	 * @param sPath
	 *            要删除的目录或文件
	 * @return 删除成功返回 true，否则返回 false。
	 */
	public static boolean DeleteFolder(String sPath) {
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
	 * 根据文件全名，向其尾部换行添加指定文本，如果改文件不存在则创建
	 * 
	 * @param file
	 *            目标文件全名
	 * @param conent
	 *            指定内容。
	 */
	public static void WriteStringToBottom(String file, String conent) {
		BufferedWriter out = null;
		try {
			out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file, true)));
			out.write(conent + "\r\n");
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
	 * 根据文件全名，向其右边添加指定文本，如果改文件不存在则创建
	 * 
	 * @param file
	 *            目标文件全名
	 * @param conent
	 *            指定内容。
	 */
	public static void WriteStringToRight(String file, String conent) {
		if(file.equals(GLog.LogStyle[4]) && !GLog.IsBackup) {
			return;
		}
		BufferedWriter out = null;
		try {
			out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file, true)));
			out.write(conent);
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
	 * 根据自定义文件全名创建txt文
	 * 
	 * @param path
	 * @param name
	 */
	public static boolean creatTxtFile(String path, String name) throws IOException {
		boolean flag = false;
		LogFullName = LogPath + LogName + ".txt";
		File filename = new File(LogFullName);
		if (!filename.exists()) {
			filename.createNewFile();
			flag = true;
		}
		return flag;
	}

	/**
	 * 保存指定文件没有空行的副本，仅接受完整路径文件名
	 * 
	 * @param InFile
	 * @param OutFile
	 */
	public static void DeleteBlankLine(String InFile, String OutFile) {
		File file = new File(InFile);
		InputStream is = null;
		BufferedReader br = null;
		String tmp;
		FileWriter writer = null;
		int i = 0;
		try {
			is = new BufferedInputStream(new FileInputStream(file));
			br = new BufferedReader(new InputStreamReader(is, "utf-8"));
			writer = new FileWriter(OutFile, true);
			while ((tmp = br.readLine()) != null) {
				if (tmp.equals(""))
					;
				else {
					writer.write(tmp + "\n");
					i++;
					System.out.println(i);
				}
			}
			writer.close();
			is.close();
			System.out.println("SAVE NO BLANK COMPLETE");
		} catch (IOException e) {
			System.out.println("SAVE NO BLANK FAILED");
			e.printStackTrace();
		}
	}

	/**
	 * 压缩成ZIP 方法1
	 * 
	 * @param srcDir
	 *            压缩文件夹路径
	 * @param out
	 *            压缩文件输出流
	 * @param KeepDirStructure
	 *            是否保留原来的目录结构,true:保留目录结构;
	 *            false:所有文件跑到压缩包根目录下(注意：不保留目录结构可能会出现同名文件,会压缩失败)
	 * @throws RuntimeException
	 *             压缩失败会抛出运行时异常
	 */
	public static void toZip(String srcDir, OutputStream out, boolean KeepDirStructure) throws RuntimeException {

		long start = System.currentTimeMillis();
		ZipOutputStream zos = null;
		try {
			zos = new ZipOutputStream(out);
			File sourceFile = new File(srcDir);
			compress(sourceFile, zos, sourceFile.getName(), KeepDirStructure);
			long end = System.currentTimeMillis();
			System.out.println("压缩完成，耗时：" + (end - start) + " ms");
			GParam.TestOutputBackupResult = true;
		} catch (Exception e) {
			throw new RuntimeException("zip error from ZipUtils", e);
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
	 * @param srcFiles
	 *            需要压缩的文件列表
	 * @param out
	 *            压缩文件输出流
	 * @throws RuntimeException
	 *             压缩失败会抛出运行时异常
	 */
	public static void toZip(List<File> srcFiles, OutputStream out) throws RuntimeException {
		long start = System.currentTimeMillis();
		ZipOutputStream zos = null;
		try {
			zos = new ZipOutputStream(out);
			for (File srcFile : srcFiles) {
				byte[] buf = new byte[BUFFER_SIZE];
				zos.putNextEntry(new ZipEntry(srcFile.getName()));
				int len;
				FileInputStream in = new FileInputStream(srcFile);
				while ((len = in.read(buf)) != -1) {
					zos.write(buf, 0, len);
				}
				zos.closeEntry();
				in.close();
			}
			long end = System.currentTimeMillis();
			System.out.println("压缩完成，耗时：" + (end - start) + " ms");
		} catch (Exception e) {
			throw new RuntimeException("zip error from ZipUtils", e);
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
	 * 递归压缩方法
	 * 
	 * @param sourceFile
	 *            源文件
	 * @param zos
	 *            zip输出流
	 * @param name
	 *            压缩后的名称
	 * @param KeepDirStructure
	 *            是否保留原来的目录结构,true:保留目录结构;
	 *            false:所有文件跑到压缩包根目录下(注意：不保留目录结构可能会出现同名文件,会压缩失败)
	 * @throws Exception
	 */
	private static void compress(File sourceFile, ZipOutputStream zos, String name, boolean KeepDirStructure)
			throws Exception {
		byte[] buf = new byte[BUFFER_SIZE];
		if (sourceFile.isFile()) {
			// 向zip输出流中添加一个zip实体，构造器中name为zip实体的文件的名字
			zos.putNextEntry(new ZipEntry(name));
			// copy文件到zip输出流中
			int len;
			FileInputStream in = new FileInputStream(sourceFile);
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
				if (KeepDirStructure) {
					// 空文件夹的处理
					zos.putNextEntry(new ZipEntry(name + "/"));
					// 没有文件，不需要文件的copy
					zos.closeEntry();
				}

			} else {
				for (File file : listFiles) {
					// 判断是否需要保留原来的文件结构
					if (KeepDirStructure) {
						// 注意：file.getName()前面需要带上父文件夹的名字加一斜杠,
						// 不然最后压缩包中就不能保留原来的文件结构,即：所有文件都跑到压缩包根目录下了
						compress(file, zos, name + "/" + file.getName(), KeepDirStructure);
					} else {
						compress(file, zos, file.getName(), KeepDirStructure);
					}

				}
			}
		}
	}

	public static void main(String[] args) throws Exception {
		/** 方式1：文件夹压缩 */
		FileOutputStream fosTgs = new FileOutputStream(new File("C:\\Users\\hewei\\Desktop\\backup.zip"));
		GFile.toZip("C:\\Users\\hewei\\Desktop\\backup", fosTgs, true);

		/** 方式2：文件列表压缩 */
		List<File> fileList = new ArrayList<File>();
		fileList.add(new File("C:\\Users\\hewei\\Desktop\\CDAutoTest1.0.2.1.zip"));
		fileList.add(new File("C:\\Users\\hewei\\Desktop\\AutoTest需求说明书.docx"));
		FileOutputStream fosOutZip = new FileOutputStream(new File(GParam.TestOutputBackupName));
		GFile.toZip(fileList, fosOutZip);
	}
}
