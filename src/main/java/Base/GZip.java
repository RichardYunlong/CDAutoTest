package main.java.Base;

import main.java.DT.GLog;

import java.io.*;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

/**
 *  压缩解压操作
 */
public class GZip {

    /**
     *  压缩操作缓存大小
     */
    private static final int BUFFER_SIZE = 2 * 1024;

    /**
     * 压缩成ZIP 方法1
     *
     * @param srcDir 压缩文件夹路径
     * @param out 压缩文件输出流
     * @param bKeepDirStructure 是否保留原来的目录结构,true:保留目录结构;false:所有文件跑到压缩包根目录下(注意：不保留目录结构可能会出现同名文件,会压缩失败)
     * @throws RuntimeException 压缩失败会抛出运行时异常
     */
    public static void toZip(String srcDir, OutputStream out, boolean bKeepDirStructure) {

        long start = System.currentTimeMillis();
        try(ZipOutputStream zos = new ZipOutputStream(out)) {
            File sourceFile = new File(srcDir);
            compress(sourceFile, zos, sourceFile.getName(), bKeepDirStructure);
            long end = System.currentTimeMillis();
            GFile.writeStringToGuideBottom("ZIP -SPEND:" + (end - start) + " ms");
        } catch (Exception e) {
            GLog.logSysFunctionException("toZip", e);
        }
    }

    /**
     * 压缩成ZIP 方法2
     *
     * @param srcFiles 需要压缩的文件列表
     * @param out 压缩文件输出流
     * @throws RuntimeException 压缩失败会抛出运行时异常
     */
    public static void toZip(List<File> srcFiles, OutputStream out) {
        long start = System.currentTimeMillis();

        if(srcFiles != null && !srcFiles.isEmpty()) {
            for (File srcFile : srcFiles) {
                byte[] buf = new byte[BUFFER_SIZE];
                int len;
                try(ZipOutputStream zos = new ZipOutputStream(out);
                    FileInputStream in = new FileInputStream(srcFile)) {
                    zos.putNextEntry(new ZipEntry(srcFile.getName()));
                    while ((len = in.read(buf)) != -1) {
                        zos.write(buf, 0, len);
                    }
                    zos.closeEntry();
                } catch (Exception e) {
                    GLog.logSysFunctionException("toZip", e);
                }
            }
            long end = System.currentTimeMillis();
            GFile.writeStringToGuideBottom("ZIP COST:" + (end - start) + " ms");
        }
    }

    /**
     * 递归压缩方法-压缩文件夹
     */
    private static void compressDir(File[] listFiles, ZipOutputStream zos, String name, boolean bKeepDirStructure) {
        try {
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
                        compress(file, zos, name + "/" + file.getName(), true);
                    } else {
                        compress(file, zos, file.getName(), false);
                    }
                }
            }
        } catch (Exception e) {
            GLog.logSysFunctionException("compress", e);
        }
    }

    /**
     * 递归压缩方法
     *
     * @param sourceFile 源文件
     * @param zos zip输出流
     * @param name 压缩后的名称
     * @param bKeepDirStructure 是否保留原来的目录结构,true:保留目录结构;false:所有文件跑到压缩包根目录下(注意：不保留目录结构可能会出现同名文件,会压缩失败)
     */
    private static void compress(File sourceFile, ZipOutputStream zos, String name, boolean bKeepDirStructure) {
        byte[] buf = new byte[BUFFER_SIZE];
        if (sourceFile.isFile()) {
            try(FileInputStream in = new FileInputStream(sourceFile)) {
                // 向zip输出流中添加一个zip实体，构造器中name为zip实体的文件的名字
                zos.putNextEntry(new ZipEntry(name));
                // copy文件到zip输出流中
                int len;
                while ((len = in.read(buf)) != -1) {
                    zos.write(buf, 0, len);
                }
                // Complete the entry
                zos.closeEntry();
            } catch (Exception e) {
                GLog.logSysFunctionException("compress", e);
            }
        } else {
            compressDir(sourceFile.listFiles(), zos, name, bKeepDirStructure);
        }
    }

    /**
     *  解压
     *
     *  @param zipFilePath 包全名
     *  @param destDir 解压到的位置
     */
    @SuppressWarnings({"CallToPrintStackTrace", "ResultOfMethodCallIgnored"})
    public static void unzip(String zipFilePath, String destDir) {
        File dir = new File(destDir);

        // 创建输出目录如果它不存在
        if (!dir.exists()) dir.mkdirs();

        byte[] buffer = new byte[1024];
        try {
            FileInputStream fis = new FileInputStream(zipFilePath);
            ZipInputStream zis = new ZipInputStream(fis);
            ZipEntry ze = zis.getNextEntry();

            while (ze != null) {
                String fileName = ze.getName();
                File newFile = new File(destDir + File.separator + fileName);

                // 创建所有非存在的父目录
                new File(newFile.getParent()).mkdirs();

                FileOutputStream fos = new FileOutputStream(newFile);

                int len;
                while ((len = zis.read(buffer)) > 0) {
                    fos.write(buffer, 0, len);
                }

                fos.close();
                // 关闭当前ZipEntry并移至下一个
                zis.closeEntry();
                ze = zis.getNextEntry();
            }

            // 关闭最后一个ZipEntry
            zis.closeEntry();
            zis.close();
            fis.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // 示例用法
    public static void main(String[] args) {
        unzip("./driver/chrome/chromedriver-win32.zip", "./driver/chrome/");
    }
}
