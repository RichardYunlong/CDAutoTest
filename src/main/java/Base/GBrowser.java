package Base;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;

public class GBrowser {
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
                    // 方法1: 使用WMI命令（修复引号问题）
                    try {
                        // 使用CMD /C来执行命令，避免引号嵌套问题
                        process = Runtime.getRuntime().exec(new String[]{
                                "cmd.exe", "/c", "wmic", "datafile", "where",
                                "name='" + chromePath.replace("/", "\\\\") + "'", "get", "Version", "/value"
                        });
                        version = parseVersionOutput(process, "Version=");
                    } catch (Exception e) {
                        System.err.println("WMI命令执行失败，尝试备选方法: " + e.getMessage());
                    }

                    // 如果方法1失败，尝试方法2: 直接运行Chrome --version
                    if (version.isEmpty()) {
                        try {
                            // 提取Chrome.exe所在目录
                            String chromeDir = new File(chromePath).getParent();
                            process = Runtime.getRuntime().exec(new String[]{
                                    "cmd.exe", "/c", "cd", "/d", chromeDir, "&&", "chrome.exe", "--version"
                            });
                            version = parseVersionOutput(process, "Google Chrome ");
                        } catch (Exception e) {
                            System.err.println("Chrome --version命令执行失败: " + e.getMessage());
                        }
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

    // 解析命令输出，提取版本号
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
            System.err.println("命令错误输出: " + errorOutput.toString());
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

    // 新增方法：获取chromedriver版本号
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

    // 解析chromedriver输出，提取版本号
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
            System.err.println("命令错误输出: " + errorOutput.toString());
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

    // 测试方法
    public static void main(String[] args) {
        // 获取Chrome浏览器版本
        getcurrentChromeVersion("");
        // 获取chromedriver版本（示例路径，需要替换为实际路径）
        getChromeDriverVersion("./driver/chrome/chromedriver.exe");
    }
}
