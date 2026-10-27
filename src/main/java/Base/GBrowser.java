package Base;

import DT.GLog;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * 浏览器与驱动版本探测
 *
 * <p>通过调用外部命令读取本机 Chrome 浏览器与 chromedriver 的版本号，
 * 用于在选择驱动前校验二者版本是否匹配。</p>
 *
 * <p>selenium 自动化一律使用 64 位 Chrome：框架在找不到配置文件指定的浏览器时，
 * 会自动按候选安装位置与注册表查询 64 位 Chrome，并跳过 32 位版本。</p>
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
     * Chrome 可执行文件名称
     */
    private static final String CHROME_EXE_NAME = "chrome.exe";

    /**
     * Chrome 在安装目录下的相对路径
     */
    private static final String CHROME_RELATIVE_EXE = "Google/Chrome/Application/" + CHROME_EXE_NAME;

    /**
     * PE 头机器码-x64
     */
    private static final int IMAGE_FILE_MACHINE_AMD64 = 0x8664;

    /**
     * PE 头机器码-arm64
     */
    private static final int IMAGE_FILE_MACHINE_ARM64 = 0xAA64;

    /**
     * 本机 64 位 Chrome 安装目录缓存，已定位成功时不再重复查找
     */
    private static volatile String chrome64InstallPathCache = "";

    /**
     * 注册表中登记 Chrome 安装位置的键
     */
    private static final String[] CHROME_REGISTRY_KEYS = {
            "HKCU\\SOFTWARE\\Microsoft\\Windows\\CurrentVersion\\App Paths\\chrome.exe",
            "HKLM\\SOFTWARE\\Microsoft\\Windows\\CurrentVersion\\App Paths\\chrome.exe"
    };

    /**
     * 获得本机 64 位 Chrome 浏览器的安装目录
     *
     * <p>优先使用 {@code installPath}；该目录下没有 64 位 chrome.exe 时，
     * 自动按候选安装位置与注册表登记位置重新定位。</p>
     *
     * <p>定位结果会缓存，同一运行周期内重复调用不再执行外部命令。</p>
     *
     * @param installPath 配置的安装目录，如 {@code C:/Program Files (x86)/Google/Chrome/Application/}；
     *                    传 {@code null} 或空串时按候选位置查找
     * @return 以 {@code /} 结尾的 64 位 Chrome 安装目录；未找到时返回空串
     */
    public static String getLocalChrome64InstallPath(String installPath) {
        String cached = chrome64InstallPathCache;
        if (!cached.isEmpty()) {
            return cached;
        }
        String chromeExe = locateChrome64Binary(installPath);
        if (chromeExe.isEmpty()) {
            GLog.logRecordTime(9, "WARN----未在本机定位到64位Chrome浏览器");
            return "";
        }
        String parent = new File(chromeExe).getParent();
        if (parent == null) {
            return "";
        }
        chrome64InstallPathCache = parent.replace("\\", "/") + "/";
        GLog.logRecordTime(9, "已定位64位Chrome浏览器安装目录[" + chrome64InstallPathCache + "]");
        return chrome64InstallPathCache;
    }

    /**
     * 定位本机 64 位 Chrome 浏览器的可执行文件
     *
     * <p>查找顺序：</p>
     * <ol>
     *   <li>入参给定的安装目录或可执行文件全路径；</li>
     *   <li>{@code ProgramFiles/Google/Chrome/Application/chrome.exe}；</li>
     *   <li>{@code ProgramFiles(x86)/Google/Chrome/Application/chrome.exe}；</li>
     *   <li>{@code ProgramW6432/Google/Chrome/Application/chrome.exe}；</li>
     *   <li>{@code LOCALAPPDATA/Google/Chrome/Application/chrome.exe}；</li>
     *   <li>注册表 {@code App Paths\chrome.exe} 中登记的安装位置。</li>
     * </ol>
     *
     * <p>候选文件必须是 64 位程序（读取 PE 头机器码判定），32 位 Chrome 会被跳过，
     * 以保证 selenium 自动化一律使用 64 位浏览器。</p>
     *
     * @param chromePath 优先使用的安装目录或可执行文件全路径；传 {@code null} 或空串时按候选位置查找
     * @return 64 位 Chrome 可执行文件全路径；未找到时返回空串
     */
    public static String locateChrome64Binary(String chromePath) {
        for (String candidate : buildChromeCandidates(chromePath)) {
            File candidateFile = new File(candidate);
            if (!candidateFile.exists()) {
                continue;
            }
            if (is64BitExecutable(candidateFile)) {
                return candidateFile.getAbsolutePath();
            }
            GLog.logRecordTime(9, "WARN----跳过非64位Chrome浏览器[" + candidateFile.getAbsolutePath() + "]");
        }
        return "";
    }

    /**
     * 组装 Chrome 可执行文件候选清单
     *
     * @param chromePath 优先使用的安装目录或可执行文件全路径
     * @return 去重后的候选清单，顺序即查找优先级
     */
    private static List<String> buildChromeCandidates(String chromePath) {
        List<String> candidates = new ArrayList<>();
        addChromeCandidate(candidates, chromePath);
        // ProgramFiles为64位Chrome的默认安装位置，优先于(x86)目录
        addChromeCandidate(candidates, buildPath(System.getenv("ProgramFiles")));
        addChromeCandidate(candidates, buildPath(System.getenv("ProgramFiles(x86)")));
        addChromeCandidate(candidates, buildPath(System.getenv("ProgramW6432")));
        addChromeCandidate(candidates, buildPath(System.getenv("LOCALAPPDATA")));
        for (String registryPath : CHROME_REGISTRY_KEYS) {
            addChromeCandidate(candidates, queryRegistryDefaultValue(registryPath, "/reg:64"));
            addChromeCandidate(candidates, queryRegistryDefaultValue(registryPath, null));
        }
        return candidates;
    }

    /**
     * 拼接 Chrome 可执行文件全路径
     *
     * @param root 盘符根目录或程序目录，如 {@code C:\Program Files (x86)}
     * @return Chrome 可执行文件全路径；入参为空时返回空串
     */
    private static String buildPath(String root) {
        if (root == null || root.trim().isEmpty()) {
            return "";
        }
        return root.trim() + "/" + CHROME_RELATIVE_EXE;
    }

    /**
     * 将候选路径加入清单
     *
     * <p>入参为目录时自动补齐 chrome.exe，避免调用方区分目录与文件。</p>
     *
     * @param candidates 候选清单
     * @param candidate 候选路径，可为目录或可执行文件全路径
     */
    private static void addChromeCandidate(List<String> candidates, String candidate) {
        if (candidate == null || candidate.trim().isEmpty()) {
            return;
        }
        String path = candidate.trim();
        File file = new File(path);
        if (file.isDirectory() || !path.toLowerCase().endsWith(".exe")) {
            path = new File(path, CHROME_EXE_NAME).getAbsolutePath();
        }
        if (!candidates.contains(path)) {
            candidates.add(path);
        }
    }

    /**
     * 读取注册表键的默认值
     *
     * <p>通过 {@code reg query <key> /ve} 获得 {@code App Paths} 中登记的 Chrome 安装位置，
     * 优先读取 64 位注册表视图。</p>
     *
     * @param key 注册表键全路径
     * @param extraArg 附加参数，如 {@code /reg:64}；传 {@code null} 表示不加附加参数
     * @return 默认值内容；查询失败或键不存在时返回空串
     */
    @SuppressWarnings("CallToPrintStackTrace")
    private static String queryRegistryDefaultValue(String key, String extraArg) {
        List<String> command = new ArrayList<>();
        command.add("reg");
        command.add("query");
        command.add(key);
        command.add("/ve");
        if (extraArg != null) {
            command.add(extraArg);
        }
        try {
            Process process = new ProcessBuilder(command).start();
            List<String> lines = new ArrayList<>();
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    if (line.contains("REG_SZ")) {
                        String value = line.substring(line.indexOf("REG_"))
                                .replaceFirst("^REG_[A-Z_]*\\s+", "").trim();
                        if (!value.isEmpty()) {
                            lines.add(value);
                        }
                    }
                }
            }
            process.waitFor(10, TimeUnit.SECONDS);
            if (!lines.isEmpty()) {
                return lines.get(0);
            }
        } catch (Exception e) {
            System.err.println("注册表查询失败: " + key);
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 判断可执行文件是否为 64 位程序
     *
     * <p>读取 PE 头中的机器码字段判定，无法读取时退回按路径判定
     * （不在 {@code Program Files (x86)} 下即视为 64 位）。</p>
     *
     * @param file 待判定的可执行文件
     * @return 64 位返回 {@code true}；32 位或无法判定时返回 {@code false} 或按路径结论
     */
    private static boolean is64BitExecutable(File file) {
        try (RandomAccessFile randomAccessFile = new RandomAccessFile(file, "r")) {
            if (randomAccessFile.length() < 64
                    || randomAccessFile.read() != 'M' || randomAccessFile.read() != 'Z') {
                return is64BitByPath(file);
            }
            randomAccessFile.seek(0x3C);
            int peOffset = readLittleEndian(randomAccessFile, 4);
            if (peOffset <= 0 || peOffset + 6 > randomAccessFile.length()) {
                return is64BitByPath(file);
            }
            randomAccessFile.seek(peOffset);
            if (readLittleEndian(randomAccessFile, 4) != 0x00004550) {
                return is64BitByPath(file);
            }
            int machine = readLittleEndian(randomAccessFile, 2);
            return machine == IMAGE_FILE_MACHINE_AMD64 || machine == IMAGE_FILE_MACHINE_ARM64;
        } catch (Exception e) {
            return is64BitByPath(file);
        }
    }

    /**
     * 按安装路径推断是否为 64 位程序
     *
     * @param file 待判定的可执行文件
     * @return 不在 {@code Program Files (x86)} 目录下返回 {@code true}
     */
    private static boolean is64BitByPath(File file) {
        return !file.getAbsolutePath().toLowerCase().contains("program files (x86)");
    }

    /**
     * 从随机访问文件中读取小端序整数
     *
     * @param file 已定位到目标位置的可随机访问文件
     * @param length 读取字节数，取值 1~4
     * @return 按小端序还原的整数
     * @throws Exception 读取失败时抛出
     */
    private static int readLittleEndian(RandomAccessFile file, int length) throws Exception {
        int value = 0;
        for (int i = 0; i < length; i++) {
            value |= file.readUnsignedByte() << (8 * i);
        }
        return value;
    }

    /**
     * 获得本机 Chrome 浏览器版本
     *
     * <p>{@code chromePath} 为空时，Windows 下会依次尝试常见安装目录与注册表登记目录，
     * 详见 {@link #locateChrome64Binary(String)}。</p>
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
                // 配置路径不存在时自动定位本机64位Chrome，未定位到时保持原路径以便后续报错
                if (chromePath == null || chromePath.isEmpty() || !new File(chromePath).exists()) {
                    String located = locateChrome64Binary(chromePath);
                    if (!located.isEmpty()) {
                        chromePath = located;
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
