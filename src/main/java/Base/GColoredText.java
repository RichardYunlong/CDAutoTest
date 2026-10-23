package Base;

import Sys.GPath;
import com.github.lalyos.jfiglet.FigletFont;

import java.io.*;

/**
 * 控制台彩色文本
 *
 * <p>集中定义 ANSI 转义序列常量，用于给控制台输出着色。常量按"色系 + 样式"命名，
 * 例如 {@link #RED}（常规红）、{@link #RED_BOLD}（加粗红）、{@link #RED_BACKGROUND}（红底）。</p>
 *
 * <p>用法要点：颜色是<strong>有状态的</strong>，输出完彩色内容后必须拼接 {@link #RESET}
 * 复位，否则后续所有输出都会沿用该颜色。</p>
 *
 * <pre>{@code
 * System.out.println(GColoredText.RED_BOLD + "用例失败" + GColoredText.RESET);
 * }</pre>
 *
 * <p>注意：Windows 旧版控制台（cmd.exe 默认配置）不解析 ANSI 序列，会直接打印转义字符；
 * 需配合 Jansi 或在 Windows Terminal / PowerShell 7 中运行才能正常显示。</p>
 *
 * @see <a href="https://en.wikipedia.org/wiki/ANSI_escape_code">ANSI escape code</a>
 */
@SuppressWarnings("CommentedOutCode")
public class GColoredText {

    /** 重置所有颜色与样式，彩色输出结束后必须输出该常量 */
    public static final String RESET = "\033[0m";

    // ------------------------------------------------------------------ 常规色彩

    /** 黑色（常规） */
    public static final String BLACK = "\033[0;30m";
    /** 白色（常规） */
    public static final String WHITE = "\033[0;37m";
    /** 红色（常规） */
    public static final String RED = "\033[0;31m";
    /** 黄色（常规） */
    public static final String YELLOW = "\033[0;33m";
    /** 绿色（常规） */
    public static final String GREEN = "\033[0;32m";
    /** 青色（常规） */
    public static final String CYAN = "\033[0;36m";
    /** 蓝色（常规） */
    public static final String BLUE = "\033[0;34m";
    /** 紫色（常规） */
    public static final String PURPLE = "\033[0;35m";

    // ------------------------------------------------------------------ 加粗

    /** 黑色-加粗 */
    public static final String BLACK_BOLD = "\033[1;30m";
    /** 白色-加粗 */
    public static final String WHITE_BOLD = "\033[1;37m";
    /** 红色-加粗 */
    public static final String RED_BOLD = "\033[1;31m";
    /** 黄色-加粗 */
    public static final String YELLOW_BOLD = "\033[1;33m";
    /** 绿色-加粗 */
    public static final String GREEN_BOLD = "\033[1;32m";
    /** 青色-加粗 */
    public static final String CYAN_BOLD = "\033[1;36m";
    /** 蓝色-加粗 */
    public static final String BLUE_BOLD = "\033[1;34m";
    /** 紫色-加粗 */
    public static final String PURPLE_BOLD = "\033[1;35m";

    // ------------------------------------------------------------------ 下划线

    /** 黑色-下划线 */
    public static final String BLACK_UNDERLINED = "\033[4;30m";
    /** 白色-下划线 */
    public static final String WHITE_UNDERLINED = "\033[4;37m";
    /** 红色-下划线 */
    public static final String RED_UNDERLINED = "\033[4;31m";
    /** 黄色-下划线 */
    public static final String YELLOW_UNDERLINED = "\033[4;33m";
    /** 绿色-下划线 */
    public static final String GREEN_UNDERLINED = "\033[4;32m";
    /** 青色-下划线 */
    public static final String CYAN_UNDERLINED = "\033[4;36m";
    /** 蓝色-下划线 */
    public static final String BLUE_UNDERLINED = "\033[4;34m";
    /** 紫色-下划线 */
    public static final String PURPLE_UNDERLINED = "\033[4;35m";

    // ------------------------------------------------------------------ 背景色

    /** 黑色-背景 */
    public static final String BLACK_BACKGROUND = "\033[40m";
    /** 白色-背景 */
    public static final String WHITE_BACKGROUND = "\033[47m";
    /** 红色-背景 */
    public static final String RED_BACKGROUND = "\033[41m";
    /** 黄色-背景 */
    public static final String YELLOW_BACKGROUND = "\033[43m";
    /** 绿色-背景 */
    public static final String GREEN_BACKGROUND = "\033[42m";
    /** 青色-背景 */
    public static final String CYAN_BACKGROUND = "\033[46m";
    /** 蓝色-背景 */
    public static final String BLUE_BACKGROUND = "\033[44m";
    /** 紫色-背景 */
    public static final String PURPLE_BACKGROUND = "\033[45m";

    // ------------------------------------------------------------------ 高亮

    /** 黑色-高亮 */
    public static final String BLACK_BRIGHT = "\033[0;90m";
    /** 白色-高亮 */
    public static final String WHITE_BRIGHT = "\033[0;97m";
    /** 红色-高亮 */
    public static final String RED_BRIGHT = "\033[0;91m";
    /** 黄色-高亮 */
    public static final String YELLOW_BRIGHT = "\033[0;93m";
    /** 绿色-高亮 */
    public static final String GREEN_BRIGHT = "\033[0;92m";
    /** 青色-高亮 */
    public static final String CYAN_BRIGHT = "\033[0;96m";
    /** 蓝色-高亮 */
    public static final String BLUE_BRIGHT = "\033[0;94m";
    /** 紫色-高亮 */
    public static final String PURPLE_BRIGHT = "\033[0;95m";

    // ------------------------------------------------------------------ 加粗高亮

    /** 黑色-加粗-高亮 */
    public static final String BLACK_BOLD_BRIGHT = "\033[1;90m";
    /** 白色-加粗-高亮 */
    public static final String WHITE_BOLD_BRIGHT = "\033[1;97m";
    /** 红色-加粗-高亮 */
    public static final String RED_BOLD_BRIGHT = "\033[1;91m";
    /** 黄色-加粗-高亮 */
    public static final String YELLOW_BOLD_BRIGHT = "\033[1;93m";
    /** 绿色-加粗-高亮 */
    public static final String GREEN_BOLD_BRIGHT = "\033[1;92m";
    /** 青色-加粗-高亮 */
    public static final String CYAN_BOLD_BRIGHT = "\033[1;96m";
    /** 蓝色-加粗-高亮 */
    public static final String BLUE_BOLD_BRIGHT = "\033[1;94m";
    /** 紫色-加粗-高亮 */
    public static final String PURPLE_BOLD_BRIGHT = "\033[1;95m";

    // ------------------------------------------------------------------ 背景色高亮

    /** 黑色-高亮-背景 */
    public static final String BLACK_BACKGROUND_BRIGHT = "\033[0;100m";
    /** 白色-高亮-背景 */
    public static final String WHITE_BACKGROUND_BRIGHT = "\033[0;107m";
    /** 红色-高亮-背景 */
    public static final String RED_BACKGROUND_BRIGHT = "\033[0;101m";
    /** 黄色-高亮-背景 */
    public static final String YELLOW_BACKGROUND_BRIGHT = "\033[0;103m";
    /** 绿色-高亮-背景 */
    public static final String GREEN_BACKGROUND_BRIGHT = "\033[0;102m";
    /** 青色-高亮-背景 */
    public static final String CYAN_BACKGROUND_BRIGHT = "\033[0;106m";
    /** 蓝色-高亮-背景 */
    public static final String BLUE_BACKGROUND_BRIGHT = "\033[0;104m";
    /** 紫色-高亮-背景 */
    public static final String PURPLE_BACKGROUND_BRIGHT = "\033[0;105m";

    /**
     * 以 ASCII 艺术字形式输出文本
     *
     * <p>使用 jfiglet 将 {@code str} 渲染成大号字符画，并按 {@code style} 指定的
     * 颜色样式输出，输出结束自动追加 {@link #RESET}。常用于程序启动时的标题横幅。</p>
     *
     * <p>字体文件取自 {@code GPath.FONT_TEMP_PATH} 下的 {@code block.flf}；
     * 找不到该字体时会打印提示并使用默认字体渲染。</p>
     *
     * @param style 颜色样式常量，如 {@link #BLUE_BOLD_BRIGHT}
     * @param str 待展示的文本
     */
    public static void printArtFontByJfiglet(String style, String str) {
        // 要显示的文本
        String text = " " + str + " ";

        try {
            String fontPath = GPath.FONT_TEMP_PATH + "block.flf";

            // 加载实心风格的字体文件（需要提前下载放到资源目录）
            InputStream fontStream = GColoredText.class.getResourceAsStream(fontPath);

            // 如果找不到指定字体，使用默认字体
            if (fontStream == null) {
                System.out.println("未找到[" + fontPath + "]字体，使用默认实心字体");
                text = "DEFAULT";
                fontStream = GColoredText.class.getResourceAsStream(fontPath);
            }

            // 生成实心风格的ASCII艺术字
            String asciiArt = FigletFont.convertOneLine(fontStream, text);

            // 打印红色填充的字体
            System.out.println(style + asciiArt + RESET);

        } catch (IOException e) {
            System.err.println("生成填充字体失败: " + e.getMessage());
            //noinspection CallToPrintStackTrace
            e.printStackTrace();
        }
    }

    /**
     * 演示入口
     *
     * @param args 命令行参数，未使用
     */
    public static void main(String[] args) {
        printArtFontByJfiglet(BLUE_BOLD_BRIGHT, "Hello, World!");
    }
}
