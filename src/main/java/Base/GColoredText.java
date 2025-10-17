package Base;

import Sys.GPath;
import com.github.lalyos.jfiglet.FigletFont;

import java.io.*;


@SuppressWarnings("CommentedOutCode")
public class GColoredText {
    // Reset 【重置】
    public static final String RESET = "\033[0m";  // Text Reset

    // Regular Colors 【常规色彩】
    public static final String BLACK = "\033[0;30m";   // BLACK  黑色
    public static final String WHITE = "\033[0;37m";   // WHITE  白色
    public static final String RED = "\033[0;31m";     // RED    红色
    public static final String YELLOW = "\033[0;33m";  // YELLOW 黄色
    public static final String GREEN = "\033[0;32m";   // GREEN  绿色
    public static final String CYAN = "\033[0;36m";    // CYAN   青色
    public static final String BLUE = "\033[0;34m";    // BLUE   蓝色
    public static final String PURPLE = "\033[0;35m";  // PURPLE 紫色

    // Bold 【加粗】
    public static final String BLACK_BOLD = "\033[1;30m";  // BLACK   黑色-加粗
    public static final String WHITE_BOLD = "\033[1;37m";  // WHITE   白色-加粗
    public static final String RED_BOLD = "\033[1;31m";    // RED     红色-加粗
    public static final String YELLOW_BOLD = "\033[1;33m"; // YELLOW  黄色-加粗
    public static final String GREEN_BOLD = "\033[1;32m";  // GREEN   绿色-加粗
    public static final String CYAN_BOLD = "\033[1;36m";   // CYAN    青色-加粗
    public static final String BLUE_BOLD = "\033[1;34m";   // BLUE    蓝色-加粗
    public static final String PURPLE_BOLD = "\033[1;35m"; // PURPLE  紫色-加粗

    // Underline 【下划线】
    public static final String BLACK_UNDERLINED = "\033[4;30m";  // BLACK   黑色-下划线
    public static final String WHITE_UNDERLINED = "\033[4;37m";  // WHITE   白色-下划线
    public static final String RED_UNDERLINED = "\033[4;31m";    // RED     红色-下划线
    public static final String YELLOW_UNDERLINED = "\033[4;33m"; // YELLOW  黄色-下划线
    public static final String GREEN_UNDERLINED = "\033[4;32m";  // GREEN   绿色-下划线
    public static final String CYAN_UNDERLINED = "\033[4;36m";   // CYAN    青色-下划线
    public static final String BLUE_UNDERLINED = "\033[4;34m";   // BLUE    蓝色-下划线
    public static final String PURPLE_UNDERLINED = "\033[4;35m"; // PURPLE  紫色-下划线

    // Background 【背景色】
    public static final String BLACK_BACKGROUND = "\033[40m";  // BLACK    黑色-背景
    public static final String WHITE_BACKGROUND = "\033[47m";  // WHITE    白色-背景
    public static final String RED_BACKGROUND = "\033[41m";    // RED      红色-背景
    public static final String YELLOW_BACKGROUND = "\033[43m"; // YELLOW   黄色-背景
    public static final String GREEN_BACKGROUND = "\033[42m";  // GREEN    绿色-背景
    public static final String CYAN_BACKGROUND = "\033[46m";   // CYAN     青色-背景
    public static final String BLUE_BACKGROUND = "\033[44m";   // BLUE     蓝色-背景
    public static final String PURPLE_BACKGROUND = "\033[45m"; // PURPLE   紫色-背景

    // High Intensity 【高亮】
    public static final String BLACK_BRIGHT = "\033[0;90m";  // BLACK    黑色-高亮
    public static final String WHITE_BRIGHT = "\033[0;97m";  // WHITE    白色-高亮
    public static final String RED_BRIGHT = "\033[0;91m";    // RED      红色-高亮
    public static final String YELLOW_BRIGHT = "\033[0;93m"; // YELLOW   黄色-高亮
    public static final String GREEN_BRIGHT = "\033[0;92m";  // GREEN    绿色-高亮
    public static final String CYAN_BRIGHT = "\033[0;96m";   // CYAN     青色-高亮
    public static final String BLUE_BRIGHT = "\033[0;94m";   // BLUE     蓝色-高亮
    public static final String PURPLE_BRIGHT = "\033[0;95m"; // PURPLE   紫色-高亮

    // Bold High Intensity 【加粗高亮】
    public static final String BLACK_BOLD_BRIGHT = "\033[1;90m"; // BLACK    黑色-加粗-高亮
    public static final String WHITE_BOLD_BRIGHT = "\033[1;97m"; // WHITE    白色-加粗-高亮
    public static final String RED_BOLD_BRIGHT = "\033[1;91m";   // RED      红色-加粗-高亮
    public static final String YELLOW_BOLD_BRIGHT = "\033[1;93m";// YELLOW   黄色-加粗-高亮
    public static final String GREEN_BOLD_BRIGHT = "\033[1;92m"; // GREEN    绿色-加粗-高亮
    public static final String CYAN_BOLD_BRIGHT = "\033[1;96m";  // CYAN     青色-加粗-高亮
    public static final String BLUE_BOLD_BRIGHT = "\033[1;94m";  // BLUE     蓝色-加粗-高亮
    public static final String PURPLE_BOLD_BRIGHT = "\033[1;95m";// PURPLE   紫色-加粗-高亮

    // High Intensity backgrounds 【背景色高亮】
    public static final String BLACK_BACKGROUND_BRIGHT = "\033[0;100m";// BLACK     黑色-高亮-背景
    public static final String WHITE_BACKGROUND_BRIGHT = "\033[0;107m";   // WHITE  白色-高亮-背景
    public static final String RED_BACKGROUND_BRIGHT = "\033[0;101m";// RED         红色-高亮-背景
    public static final String YELLOW_BACKGROUND_BRIGHT = "\033[0;103m";// YELLOW   黄色-高亮-背景
    public static final String GREEN_BACKGROUND_BRIGHT = "\033[0;102m";// GREEN     绿色-高亮-背景
    public static final String CYAN_BACKGROUND_BRIGHT = "\033[0;106m";  // CYAN     青色-高亮-背景
    public static final String BLUE_BACKGROUND_BRIGHT = "\033[0;104m";// BLUE       蓝色-高亮-背景
    public static final String PURPLE_BACKGROUND_BRIGHT = "\033[0;105m"; // PURPLE  紫色-高亮-背景

//    public static void main(String[] args) {
//        System.out.println(RED_BACKGROUND_BRIGHT + "   " + RESET);
//    }

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

    public static void main(String[] args) {
        printArtFontByJfiglet(BLUE_BOLD_BRIGHT, "Hello, World!");
    }
}
