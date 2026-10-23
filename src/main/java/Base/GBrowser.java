package Base;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;

/**
 * 浏览器与驱动版本探测
 *
 * <p>通过调用外部命令读取本机 Chrome 浏览器与 chromedriver 的版本号，
 * 用于在选择驱动前校验二者版本是否匹配。</p>
 *
 * <p>支持 Windows / macOS / Linux 三类系统：</p>
 * <ul>
 *   <li>Windows：通过 PowerShell 读取 exe 的 {@code FileVersion}；</li>
 *   <li>macOS / Linux：执行 {@code --version} 并解析输出。</li>
 * </ul>
 *
 * <p>所有异常都在方法内部消化，探测失败时返回空串而不是抛出，
 * 因此调用方需要自行判断返回值是否为空。</p>
 */
public class GBrowser {

    /**
     * 获得本机 Chrome 浏览器版本
     *
     * <p>{@code chromePath} 为空时，Windows 下会依次尝试两个常见安装目录：
     * {@code C:/Program Files/Google/Chrome/Application/chrome.exe} 与
     * {@code C:/Program Files (x86)/...}。</p>
     *
     * @param chromePath Chrome 可执行文件全路径；传 {@code null} 或空串时按默认路径探测
     * @return 版本号字符串，如 {@code 141.0.7390.65}；探测失败时返回空串
     */
    public static String getcurrentChromeVersion(String chromePath) {
        String version = "";
        String os = System.getProperty("os.name").toLowerCase();

        try {
            Process process;

            // 根据操作系统类型选择不同的命令
            if (os.contains("win")) {
                // Windows系统 - 改进命令执行方式
                if (chromePath == null || chromePath.isEmpty()) {
                    // 默认检查常见安装路径
                    String[] commonPaths = {
                            "C:/Program Files/Google/Chrome/Application/chrome.exe",
                            "C:/Program Files (x86)/Google/Chrome/Application/chrome.exe"
                    };

                    for (String path : commonPaths) {
                        if (new File(path).exists()) {
                            chromePath = path;
                            break;
                        }
                    }
                }

                if (null != chromePath && !chromePath.isEmpty()) {
                    System.out.println("尝试从路径获取Chrome版本: " + chromePath);
                    try {
                        String escapedPath = new File(chromePath).getAbsolutePath().replace("'", "''");
                        String command = "Write-Output ('Version=' + (Get-Item -LiteralPath '"
                                + escapedPath + "').VersionInfo.FileVersion)";
                        process = new ProcessBuilder(
                                "powershell.exe", "-NoProfile", "-NonInteractive", "-Command", command
                        ).start();
                        version = parseVersionOutput(process, "Version=");
                    } catch (Exception e) {
                        System.err.println("PowerShell命令执行失败: " + e.getMessage());
                    }
                }
            } else if (os.contains("mac")) {
                // macOS系统
                process = Runtime.getRuntime().exec("/Applications/Google\\ Chrome.app/Contents/MacOS/Google\\ Chrome --version");
                version = parseVersionOutput(process, "Google Chrome ");
            } else if (os.contains("nix") || os.contains("nux")) {
                // Linux系统
                try {
                    process = Runtime.getRuntime().exec("google-chrome --version");
                    version = parseVersionOutput(process, "Google Chrome ");
                } catch (Exception e1) {
                    try {
                        process = Runtime.getRuntime().exec("chromium-browser --version");
                        version = parseVersionOutput(process, "Chromium ");
                    } catch (Exception e2) {
                        System.err.println("无法找到Chrome或Chromium浏览器");
                    }
                }
            }

            if (!version.isEmpty()) {
                System.out.println("Chrome Version: " + version);
            } else {
                System.out.println("获取到的Chrome版本号为空");
            }
        } catch (Exception e) {
            System.out.println("无法获取Chrome版本号");
            //noinspection CallToPrintStackTrace
            e.printStackTrace();
        }

        return version;
    }

    /**
     * 解析命令输出，提取版本号
     *
     * @param process 已启动的外部进程
     * @param prefix 版本号前置标记，如 {@code Version=} 或 {@code Google Chrome }
     * @return 提取到的版本号；未匹配到 {@code prefix} 时返回空串
     * @throws Exception 读取进程输出流失败时抛出
     */
    private static String parseVersionOutput(Process process, String prefix) throws Exception {
        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
        String line;
        String version = "";

        // 同时读取错误输出，方便调试
        BufferedReader errorReader = new BufferedReader(new InputStreamReader(process.getErrorStream()));
        String errorLine;
        StringBuilder errorOutput = new StringBuilder();
        while ((errorLine = errorReader.readLine()) != null) {
            errorOutput.append(errorLine).append("\n");
        }
        if (errorOutput.length() > 0) {
            System.err.println("命令错误输出: " + errorOutput);
        }

        while ((line = reader.readLine()) != null) {
            line = line.trim();
            if(!line.isEmpty()){
                System.out.println("解析行: " + line); // 添加调试信息
            }

            if (line.contains(prefix)) {
                version = line.substring(line.indexOf(prefix) + prefix.length()).trim();
                // 移除版本号后的额外信息（如有）
                if (version.contains(" ")) {
                    version = version.split(" ")[0];
                }
                break;
            }
        }

        reader.close();
        errorReader.close();
        process.destroy();
        return version;
    }

    /**
     * 获得 chromedriver 版本号
     *
     * @param driverPath chromedriver 可执行文件全路径，如 {@code ./driver/chrome/chromedriver.exe}
     * @return 版本号字符串，如 {@code 141.0.7390.65}；文件不存在或探测失败时返回空串
     */
    public static String getChromeDriverVersion(String driverPath) {
        String version = "";
        String os = System.getProperty("os.name").toLowerCase();

        try {
            Process process;
            File driverFile = new File(driverPath);

            // 检查驱动文件是否存在
            if (!driverFile.exists()) {
                System.out.println("chromedriver文件不存在: " + driverPath);
                return version;
            }

            // 根据操作系统执行不同的命令
            if (os.contains("win")) {
                // Windows系统
                process = Runtime.getRuntime().exec(new String[]{
                        "cmd.exe", "/c", driverPath.replace("/", "\\\\"), "--version"
                });
            } else if (os.contains("mac")) {
                // macOS系统
                process = Runtime.getRuntime().exec(new String[]{"/bin/bash", "-c", driverPath + " --version"});
            } else if (os.contains("nix") || os.contains("nux")) {
                // Linux系统
                process = Runtime.getRuntime().exec(new String[]{"/bin/bash", "-c", driverPath + " --version"});
            } else {
                System.out.println("不支持的操作系统: " + os);
                return version;
            }

            // 解析输出获取版本号
            version = parseDriverVersionOutput(process);

            if (!version.isEmpty()) {
                System.out.println("ChromeDriver Version: " + version);
            } else {
                System.out.println("无法获取ChromeDriver版本号");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return version;
    }

    /**
     * 解析 chromedriver 输出，提取版本号
     *
     * <p>chromedriver 的 {@code --version} 输出形如
     * {@code ChromeDriver 141.0.7390.65 (abcdef123456)}，本方法取第二段作为版本号。</p>
     *
     * @param process 已启动的外部进程
     * @return 版本号字符串；输出不符合预期格式时返回空串
     * @throws Exception 读取进程输出流失败时抛出
     */
    private static String parseDriverVersionOutput(Process process) throws Exception {
        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
        String line;
        String version = "";

        // 读取错误输出，方便调试
        BufferedReader errorReader = new BufferedReader(new InputStreamReader(process.getErrorStream()));
        String errorLine;
        StringBuilder errorOutput = new StringBuilder();
        while ((errorLine = errorReader.readLine()) != null) {
            errorOutput.append(errorLine).append("\n");
        }
        if (errorOutput.length() > 0) {
            System.err.println("命令错误输出: " + errorOutput);
        }

        // chromedriver --version的输出格式通常为：ChromeDriver 141.0.7390.65 (abcdef123456)
        while ((line = reader.readLine()) != null) {
            line = line.trim();
            if(!line.isEmpty()){
                System.out.println("解析行: " + line); // 添加调试信息
            }

            if (line.startsWith("ChromeDriver ")) {
                // 提取版本号部分
                String[] parts = line.split(" ");
                if (parts.length >= 2) {
                    version = parts[1];
                    // 移除可能的括号内容
                    if (version.contains("(")) {
                        version = version.split("\\(")[0];
                    }
                }
                break;
            }
        }

        reader.close();
        errorReader.close();
        process.destroy();
        return version;
    }

    /**
     * 演示入口
     *
     * <p>分别探测本机 Chrome 与 {@code ./driver/chrome/chromedriver.exe} 的版本并打印，
     * 可用于快速确认驱动与浏览器是否匹配。</p>
     *
     * @param args 命令行参数，未使用
     */
    public static void main(String[] args) {
        // 获取Chrome浏览器版本
        getcurrentChromeVersion("");
        // 获取chromedriver版本（示例路径，需要替换为实际路径）
        getChromeDriverVersion("./driver/chrome/chromedriver.exe");
    }
}
