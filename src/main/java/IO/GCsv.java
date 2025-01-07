package IO;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.*;

/**
 * 文件操作
 */
public class GCsv {

    /**
     * 生成为CVS文件
     * 
     * @param exportData 源数据List
     * @param map csv文件的列表头map
     * @param outPutPath 文件路径
     * @param fileName 文件名称
     *
     * @return File 创建文件
     */
    @SuppressWarnings({"rawtypes", "unchecked", "CallToPrintStackTrace", "ResultOfMethodCallIgnored"})
    public static File createCSVFile(List exportData, LinkedHashMap map, 
            String outPutPath, String fileName) {
        File csvFile = null;
        BufferedWriter csvFileOutputStream = null;
        try {
            File file = new File(outPutPath);
            if (!file.exists()) {
                file.mkdir();
            }
            // 定义文件名格式并创建
            csvFile = File.createTempFile(fileName, ".csv", new File(outPutPath));
            System.out.println("csvFile：" + csvFile);
            // UTF-8使正确读取分隔符","
            csvFileOutputStream = new BufferedWriter(
                    new OutputStreamWriter(
                            Files.newOutputStream(csvFile.toPath()), StandardCharsets.UTF_8),1024);
            System.out.println("csvFileOutputStream：" + csvFileOutputStream);
            // 写入文件头部
            for (Iterator propertyIterator = map.entrySet().iterator(); 
                    propertyIterator.hasNext();) {
                java.util.Map.Entry propertyEntry = 
                        (java.util.Map.Entry) propertyIterator.next();
                csvFileOutputStream.write(
                        propertyEntry.getValue() != null ?
                                (String) propertyEntry.getValue() : "");
                if (propertyIterator.hasNext()) {
                    csvFileOutputStream.write(",");
                }
            }
            csvFileOutputStream.newLine();
            // 写入文件内容
            for (Iterator iterator = exportData.iterator(); iterator.hasNext();) {
                LinkedHashMap<String, String> mapValues = new LinkedHashMap<>((Map<? extends String, ? extends String>) iterator.next());
                for (Iterator propertyIterator = mapValues.entrySet().iterator(); 
                        propertyIterator.hasNext();) {
                    java.util.Map.Entry propertyEntry = 
                            (java.util.Map.Entry) propertyIterator.next();
                    csvFileOutputStream.write((String) propertyEntry.getValue());
                    if (propertyIterator.hasNext()) {
                        csvFileOutputStream.write(",");
                    }
                }
                if (iterator.hasNext()) {
                    csvFileOutputStream.newLine();
                }
            }
            csvFileOutputStream.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                Objects.requireNonNull(csvFileOutputStream).close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return csvFile;
    }
    
    /**
     * 删除该目录filePath下的所有文件
     * 
     * @param filePath 文件目录路径
     */
    @SuppressWarnings("ResultOfMethodCallIgnored")
    public static void deleteFiles(String filePath) {
        File file = new File(filePath);
        if (file.exists()) {
            File[] files = file.listFiles();
            for (File value : Objects.requireNonNull(files)) {
                if (value.isFile()) {
                    value.delete();
                }
            }
        }
    }

    /**
     * 删除单个文件
     * 
     * @param filePath 文件目录路径
     * @param fileName 文件名称
     */
    @SuppressWarnings("ResultOfMethodCallIgnored")
    public static void deleteFile(String filePath, String fileName) {
        File file = new File(filePath);
        if (file.exists()) {
            File[] files = file.listFiles();
            for (File value : Objects.requireNonNull(files)) {
                if (value.isFile()) {
                    if (value.getName().equals(fileName)) {
                        value.delete();
                        return;
                    }
                }
            }
        }
    }
}