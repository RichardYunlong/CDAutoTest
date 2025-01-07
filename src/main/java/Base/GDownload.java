package Base;

import org.apache.commons.io.FileUtils;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;

/**
 *  下载操作
 */
public class GDownload {

    /**
     *  全地址。包含路径和文件名
     */
    private String strFullAddress = "";

    public String getStrFullAddress() {
        return strFullAddress;
    }

    /**
     *  路径
     */
    private String strAddress = "";

    public String getStrAddress() {
        return strAddress;
    }

    /**
     *  文件名
     */
    private String strName = "";

    public String getStrName() {
        return strName;
    }

    /**
     *  下载状态码及对应提示信息，详情可参考默认赋值方法
     */
    @SuppressWarnings({"FieldMayBeFinal", "CanBeFinal", "MismatchedQueryAndUpdateOfCollection"})
    private HashMap<Integer, String> status = new HashMap<>();

    /**
     *  下载状态码及对应提示信息-默认赋值方法
     */
    public void setStatus() {
        this.status.put(0,"下载完成，检查下载文件存在");
        this.status.put(1,"下载失败，原因未知");
        this.status.put(2,"下载失败，未联网");
        this.status.put(3,"下载失败，目标网络不允许访问");
        this.status.put(4,"下载失败，目标允许访问但不允许下载");
        this.status.put(5,"下载失败，下载过程中网络断开");
        this.status.put(6,"下载失败，下载过程中文件丢失或损坏");
        this.status.put(7,"下载失败，下载结束后文件破损");
        this.status.put(8,"下载中");
        this.status.put(9,"下载未开始");
    }

    /**
     *  下载状态码及对应提示信息-外部赋值方法
     *
     * @param mapStatus 状态存储器
     */
    public void setStatus(HashMap<Integer,String> mapStatus) {
        this.status.clear();
        this.status.putAll(mapStatus);
    }

    /**
     *  保存全名。包含路径和文件名
     */
    private String strSaveFullPath = "";

    public String getStrSaveFullPath() {
        return strSaveFullPath;
    }

    /**
     *  保存路径
     */
    private String strSavePath = "";

    public void setStrSavePath(String strSavePath) {
        this.strSavePath = strSavePath;
    }

    /**
     *  保存文件名
     */
    private String strSaveName = "";

    public void setStrSaveName(String strSaveName) {
        this.strSaveName = strSaveName;
    }

    /**
     *  构造函数1：目标全路径,保存全路径
     *
     * @param strFullAddress 目标下载地址全名
     * @param strSaveFullPath 保存全名
     */
    public GDownload(String strFullAddress,String strSaveFullPath){
        this.setStatus();

        this.strFullAddress = strFullAddress;
        this.strSaveFullPath = strSaveFullPath;
    }

    /**
     *  构造函数2：目标路径和文件名，保存路径和文件名
     *
     *  @param strAddress 目标下载地址
     *  @param strName 目标名称
     *  @param strSavePath 保存位置
     *  @param strSaveName 另存为的文件名
     */
    public GDownload(String strAddress, String strName, String strSavePath, String strSaveName){
        this.setStatus();

        this.strAddress = strAddress;
        this.strName = strName;
        this.strSavePath = strSavePath;
        this.strSaveName = strSaveName;
    }

    /**
     *  执行下载
     */
    @SuppressWarnings("CallToPrintStackTrace")
    public void todo() {
        try{
            this.downloadNet();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     *  下载网络文件到本地,目标路径和文件名，保存路径和文件名
     */
    @SuppressWarnings({"CallToPrintStackTrace", "ResultOfMethodCallIgnored"})
    public void downloadNet() {
        try {
            //自动创建文件夹
            File file = new File(this.strSavePath);
            if (!file.exists() && !file.isDirectory()) {
                System.out.println("新建目录");
                file.mkdirs();
            }
            //解析下载地址
            System.out.println("目标地址:" + this.strAddress + this.strName);
            URL url = new URL(this.strAddress + this.strName);
            HttpURLConnection cnt = (HttpURLConnection)url.openConnection();
            cnt.setRequestMethod("GET");
            cnt.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/51.0.2704.79 Safari/537.36");
            int responseCode = cnt.getResponseCode();
            int fileLength = cnt.getContentLength();

            if (responseCode == HttpURLConnection.HTTP_OK) {
                System.out.println("开始下载");
                InputStream inStream = new BufferedInputStream(cnt.getInputStream());
                FileOutputStream fos = new FileOutputStream(this.strSavePath + this.strSaveName);

                byte[] buffer = new byte[1024 * 1024 * 10];
                int byteread;
                long totalBytesRead = 0;

                while ((byteread = inStream.read(buffer)) != -1) {
                    fos.write(buffer, 0, byteread);
                    totalBytesRead += byteread;

                    int progress = (int) (totalBytesRead * 100 / fileLength);
                    System.out.print("\rDownloading: " + progress + "%\n");
                }

                fos.close();
                inStream.close();

                System.out.println("下载完成");
            } else {
                System.out.println("无法连接到提供下载的服务器，错误代码为：" + responseCode);
            }
        } catch (IOException e) {
            System.out.println("Error downloading file: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     *  下载网络文件到本地,目标路径和文件名，保存路径和文件名
     *
     * @param fileURL 源文件路径
     * @param saveDir 文件保存路径
     *
     * @throws IOException 下载异常
     */
    public static void downloadFile(String fileURL, String saveDir) throws IOException {
        File output = new File(saveDir);
        FileUtils.copyURLToFile(new URL(fileURL), output);
        System.out.println("File downloaded using Apache Commons IO: " + output.getAbsolutePath());
    }

    /**
     *  测试方法
     *
     * @param args 入参列表
     */
    public static void main(String[] args) {
        String target = "https://registry.npmmirror.com/-/binary/chrome-for-testing/132.0.6811.2/win32/";
        String saveAs = "./driver/chrome/";
        String name = "chromedriver-win32.zip";

        GDownload test = new GDownload(target,name,saveAs,name);
        test.todo();
     }
}
