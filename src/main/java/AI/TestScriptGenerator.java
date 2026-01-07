package AI;

import com.github.javaparser.ast.expr.MethodCallExpr;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.github.javaparser.ast.expr.Expression;
import com.github.javaparser.ast.NodeList;

public class TestScriptGenerator {
    // 模板：Selenium基础脚本框架
    private static final String SCRIPT_TEMPLATE =
            "import org.openqa.selenium.By;\n" +
                    "import org.openqa.selenium.WebDriver;\n" +
                    "import org.openqa.selenium.chrome.ChromeDriver;\n" +
                    "public class AutoGenTest {\n" +
                    "    public static void main(String[] args) {\n" +
                    "        WebDriver driver = new ChromeDriver();\n" +
                    "        %s\n" +
                    "        driver.quit();\n" +
                    "    }\n" +
                    "}";

    private static final String INDENT = "    ";
    private static final String SCRIPT_TEMPLATE_JASON =
            "import org.openqa.selenium.By;\n" +
                    "import org.openqa.selenium.WebDriver;\n" +
                    "import org.openqa.selenium.WebElement;\n" +
                    "import org.openqa.selenium.chrome.ChromeDriver;\n" +
                    "public class AutoGenTestJson {\n" +
                    INDENT + "public static void main(String[] args) {\n" +
                    INDENT + INDENT + "WebDriver driver = new ChromeDriver();\n" +
                    INDENT + INDENT + "%s\n" +
                    INDENT + INDENT + "driver.quit();\n" +
                    INDENT + "}\n" +
                    "}";

    // 生成自动化脚本
    public static void generateScript(Map<String, MethodCallExpr> callGraph, String outputPath, String fileType) {
        // 将entry转为有序列表，按插入顺序（避免反转）
        List<Map.Entry<String, MethodCallExpr>> sortedEntries = new ArrayList<>(callGraph.entrySet());

        StringBuilder scriptContent = new StringBuilder();
        // 按顺序遍历，保证操作步骤与业务逻辑一致
        for (Map.Entry<String, MethodCallExpr> entry : sortedEntries) {
            MethodCallExpr call = entry.getValue();
            String callee = call.getNameAsString();
            String caller = entry.getKey().split("_")[0];

            switch (callee) {
                case "get":
                    String url = call.getArguments().get(0).toString().replace("\"", "");
                    scriptContent.append("driver.get(\"" + url + "\");\n");
                    break;
                case "sendKeys":
                    String value = call.getArguments().get(0).toString().replace("\"", "");
                    scriptContent.append("driver.findElement(By.id(\"input_" + caller + "\")).sendKeys(\"" + value + "\");\n");
                    break;
                case "findElement":
                    scriptContent.append("driver.findElement(By.id(\"" + caller + "_id\"));\n");
                    break;
                case "click":
                    scriptContent.append("driver.findElement(By.id(\"btn_" + caller + "\")).click();\n");
                    break;
            }
            System.out.println("测试脚本行类型：" + callee);
        }

        // 补充文件写入逻辑
        try {
            // 确保输出目录存在（如果不存在则创建）
            File outputFile = new File(outputPath);
            File parentDir = outputFile.getParentFile();
            if (parentDir != null && !parentDir.exists()) {
                parentDir.mkdirs(); // 递归创建目录
            }

            // 写入内容到文件
            try (FileWriter writer = new FileWriter(outputFile)) {
                String fullScript = String.format(SCRIPT_TEMPLATE, scriptContent.toString());
                writer.write(fullScript);
            }
            System.out.println("测试脚本已生成：" + outputFile.getAbsolutePath());
        } catch (IOException e) {
            throw new RuntimeException("生成脚本文件失败", e);
        }
    }

    public static void generateScript(Map<String, Object> callGraph, String outputPath) {
        StringBuilder scriptContent = new StringBuilder();

        // 遍历JSON解析后的Call Graph
        for (Map.Entry<String, Object> entry : callGraph.entrySet()) {
            MethodCallExprAdapter adapter = (MethodCallExprAdapter) entry.getValue();
            String callee = adapter.getNameAsString();
            NodeList<Expression> args = adapter.getArguments(); // 改用NodeList
            if (args.isEmpty()) {
                continue;
            }
            String argValue = args.get(0).toString().replace("\"", "");

            // 生成缩进后的代码行
            String line = "";
            switch (callee) {
                case "get":
                    line = "driver.get(\"" + argValue + "\");\n";
                    break;
                case "findElement":
                    line = "WebElement " + argValue + " = driver.findElement(By.id(\"" + argValue + "\"));\n";
                    break;
                case "sendKeys":
                    String elementName = getLastFindElementName(callGraph, entry);
                    line = elementName + ".sendKeys(\"" + argValue + "\");\n";
                    break;
                case "click":
                    line = "driver.findElement(By.id(\"" + argValue + "\")).click();\n";
                    break;
            }
            scriptContent.append(line);
        }

        // 写入文件
        File outputFile = new File(outputPath);
        File parentDir = outputFile.getParentFile();
        if (parentDir != null && !parentDir.exists()) {
            parentDir.mkdirs();
        }

        try (FileWriter writer = new FileWriter(outputFile)) {
            String fullScript = String.format(SCRIPT_TEMPLATE_JASON, scriptContent.toString());
            writer.write(fullScript);
            System.out.println("✅ 脚本生成成功：" + outputFile.getAbsolutePath());
        } catch (IOException e) {
            throw new RuntimeException("❌ 写入脚本失败：" + e.getMessage(), e);
        }
    }

    // 适配适配器类的元素名匹配逻辑
    private static String getLastFindElementName(Map<String, Object> callGraph,
                                                 Map.Entry<String, Object> currentEntry) {
        String lastElementName = "unknownElement";
        for (Map.Entry<String, Object> entry : callGraph.entrySet()) {
            if (entry.getKey().equals(currentEntry.getKey())) {
                break;
            }
            MethodCallExprAdapter adapter = (MethodCallExprAdapter) entry.getValue();
            if ("findElement".equals(adapter.getNameAsString()) && !adapter.getArguments().isEmpty()) {
                lastElementName = adapter.getArguments().get(0).toString().replace("\"", "");
            }
        }
        return lastElementName;
    }

    // 主方法：完整流程调用
    public static void main(String[] args) {

//        // 1. 解析手写的UI逻辑代码，生成Call Graph
//        File uiLogicFile = new File("src/main/resources/logiccodes/UiLogic.java"); // 手写的UI操作逻辑
//        Map<String, MethodCallExpr> callGraph = CallGraphGenerator.generateCallGraph(uiLogicFile);
//
//        CallGraphGenerator.visualizeCallGraph(callGraph);
//
//        // 2. 基于Call Graph生成自动化脚本
//        generateScript(callGraph, "src/test/java/yonbip/登陆/AutoGenTest.java");

        File jsonFile = new File("src/main/resources/logiccodes/login_config.json");
        Map<String, Object> callGraph = CallGraphGenerator.generateCallGraph(jsonFile, "jason");
        generateScript(callGraph, "src/test/java/yonbip/登陆/AutoGenTestJson.java");
    }
}