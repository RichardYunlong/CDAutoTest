package AI;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors; // 必须导入

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.javaparser.JavaParser;
import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.expr.Expression;
import com.github.javaparser.ast.expr.MethodCallExpr;
import com.github.javaparser.ast.expr.StringLiteralExpr;
import com.github.javaparser.ast.nodeTypes.NodeWithArguments;

import guru.nidi.graphviz.engine.Format;
import guru.nidi.graphviz.engine.Graphviz;

/**
 * 适配器类：模拟MethodCallExpr，避免继承冲突
 */
class MethodCallExprAdapter implements NodeWithArguments<MethodCallExpr> {
    private String methodName;
    private NodeList<Expression> arguments = new NodeList<>(); // 严格匹配父类返回类型

    public MethodCallExprAdapter(String action, String value) {
        this.methodName = action;
        // 添加参数（StringLiteralExpr，与JavaParser解析结果一致）
        this.arguments.add(new StringLiteralExpr(value));
    }

    // 模拟核心方法，适配原逻辑
    public String getNameAsString() {
        return methodName;
    }

    @Override
    public NodeList<Expression> getArguments() {
        return arguments; // 返回NodeList，解决类型冲突
    }

    @Override
    public MethodCallExpr setArguments(NodeList<Expression> arguments) {
        this.arguments = arguments;
        return null; // 无需实现，仅满足接口要求
    }

    @Override
    public MethodCallExpr addArgument(Expression expression) {
        this.arguments.add(expression);
        return null; // 无需实现，仅满足接口要求
    }
}

// 生成Call Graph的工具类
public class CallGraphGenerator {

    /**
     * 按代码执行顺序解析UiLogic.java（修复static调用问题，Java 8兼容）
     * 
     * @param javaFile UiLogic.java文件
     * @return 有序Call Graph（LinkedHashMap保证顺序）
     */
    public static Map<String, MethodCallExpr> generateCallGraph(File javaFile) {
        // 核心：LinkedHashMap保证插入顺序（严格按代码中调用顺序）
        Map<String, MethodCallExpr> callGraph = new LinkedHashMap<>();
        CompilationUnit cu;

        // ===== 修复核心：适配JavaParser parse方法的调用方式 =====
        try (FileInputStream fis = new FileInputStream(javaFile)) {
            // 兼容JavaParser 2.x（实例调用）和3.x+（StaticJavaParser静态调用）
            try {
                // 优先尝试3.x+版本的静态调用（推荐）
                cu = StaticJavaParser.parse(fis);
            } catch (NoClassDefFoundError e) {
                // 若为2.x版本，创建实例后调用parse
                JavaParser javaParser = new JavaParser();
                cu = javaParser.parse(fis).getResult().get();
            }
        } catch (IOException e) {
            throw new RuntimeException("读取UiLogic.java文件失败", e);
        }

        // 解析失败抛异常，避免静默丢失操作
        if (cu == null) {
            throw new RuntimeException("解析UiLogic.java语法树失败，请检查文件格式");
        }

        // 遍历所有业务方法（如login()）
        cu.findAll(MethodDeclaration.class).forEach(method -> {
            String caller = method.getNameAsString(); // 调用方：如login
            // 获取方法内所有MethodCallExpr（按语法树顺序，与物理行一致）
            List<MethodCallExpr> callList = method.findAll(MethodCallExpr.class);

            // 按调用顺序遍历（核心：替代行号排序，避免Range依赖）
            int opIndex = 0; // 操作序号，保证Key唯一
            for (MethodCallExpr call : callList) {
                String callee = call.getNameAsString(); // 被调用方：如findElement/sendKeys

                // 过滤仅保留UI核心操作
                for (UiAction action : UiAction.values()) {
                    if (callee.equals(action.getMethodName())) {
                        // 生成唯一Key：调用方_操作序号_被调用方（避免重复覆盖）
                        String key = caller + "_" + opIndex + "_" + callee;
                        callGraph.put(key, call); // 按调用顺序插入，保证顺序
                        opIndex++;
                        break; // 匹配到一个UI操作即可，避免重复
                    }
                }
            }
        });

        System.out.println("读入的内容为:" + callGraph.values());
        return callGraph;
    }

    /**
     * 按代码执行顺序解析UiLogic.java（修复static调用问题，Java 8兼容）
     * 
     * @param jsonFile login_config.json文件
     * @param fileType jason格式
     * @return 有序Call Graph（LinkedHashMap保证顺序）
     */
    public static Map<String, Object> generateCallGraph(File jsonFile, String fileType) {
        // 核心：LinkedHashMap保证JSON步骤顺序，value为MethodCallExprAdapter（替代原MethodCallExpr）
        Map<String, Object> callGraph = new LinkedHashMap<>();

        // 1. 读取JSON文件
        StringBuilder jsonContent = new StringBuilder();
        try (FileReader reader = new FileReader(jsonFile)) {
            int c;
            while ((c = reader.read()) != -1) {
                jsonContent.append((char) c);
            }
        } catch (IOException e) {
            throw new RuntimeException("读取JSON配置文件失败：" + e.getMessage(), e);
        }

        // 2. 解析JSON配置
        JSONObject config = JSON.parseObject(jsonContent.toString());
        String logicName = config.getString("name"); // 如"login"
        JSONArray steps = config.getJSONArray("steps");
        if (steps == null || steps.isEmpty()) {
            throw new RuntimeException("JSON配置中无有效steps节点");
        }

        // 3. 按JSON步骤生成Call Graph
        int opIndex = 0;
        for (int i = 0; i < steps.size(); i++) {
            JSONObject step = steps.getJSONObject(i);
            String action = step.getString("action");
            String value = step.getString("value");

            // 过滤无效步骤
            if (action == null || value == null) {
                continue;
            }

            // 4. 创建适配器实例（无继承冲突）
            MethodCallExprAdapter callAdapter = new MethodCallExprAdapter(action, value);
            // 生成唯一Key：逻辑名_操作序号_动作名
            String key = logicName + "_" + opIndex + "_" + action;
            callGraph.put(key, callAdapter);
            System.out.println("读入的内容为:" + key + ":" + callAdapter.getArguments());
            opIndex++;
        }

        return callGraph;
    }

    // 适配Map<String, MethodCallExpr>的可视化方法（纯DOT语法实现）
    public static void visualizeCallGraph(Map<String, Object> callGraph) {
        // 1. 构建Graphviz原生DOT语法字符串
        StringBuilder dotContent = new StringBuilder();
        dotContent.append("digraph \"UI操作调用图\" {\n");
        dotContent.append("  rankdir=LR; // 横向布局，更直观显示步骤顺序\n");
        dotContent.append("  node [fontname=\"SimHei\"]; // 支持中文\n");
        dotContent.append("  edge [fontname=\"SimHei\"];\n");

        int stepNum = 1; // 步骤序号，保证顺序不反转
        for (Map.Entry<String, Object> entry : callGraph.entrySet()) {
            String entryKey = entry.getKey();
            Object value = entry.getValue();

            // 提取核心信息
            String caller = entryKey.split("_")[0]; // 业务方法名（如login）

            String callee;
            List<String> args = new ArrayList<>();

            // 根据实际类型处理
            if (value instanceof MethodCallExpr) {
                MethodCallExpr callExpr = (MethodCallExpr) value;
                callee = callExpr.getNameAsString(); // UI操作名（如get/sendKeys）

                // 提取参数（兼容Java 8+，Java 7可替换为普通循环）
                if (!callExpr.getArguments().isEmpty()) {
                    args = callExpr.getArguments().stream()
                            .map(arg -> arg.toString().replace("\"", ""))
                            .collect(Collectors.toList());
                }
            } else if (value instanceof MethodCallExprAdapter) {
                MethodCallExprAdapter callExpr = (MethodCallExprAdapter) value;
                callee = callExpr.getNameAsString(); // UI操作名

                // 提取参数
                if (!callExpr.getArguments().isEmpty()) {
                    args = callExpr.getArguments().stream()
                            .map(arg -> arg.toString().replace("\"", ""))
                            .collect(Collectors.toList());
                }
            } else {
                System.err.println("未知类型: " + value.getClass().getName());
                stepNum++;
                continue;
            }

            String argLabel = args.isEmpty() ? "" : "(" + String.join(",", args) + ")";
            String calleeNodeName = callee + "_" + stepNum; // 避免节点重名

            // 2. 定义节点（原生DOT语法）
            // 业务方法节点：蓝色矩形
            dotContent.append("  \"").append(caller).append("\" [shape=box, color=blue];\n");
            // UI操作节点：绿色椭圆
            dotContent.append("  \"").append(calleeNodeName).append("\" [shape=ellipse, color=green, label=\"")
                    .append(callee).append(argLabel).append("\"];\n");
            // 3. 定义边（带步骤标签）
            dotContent.append("  \"").append(caller).append("\" -> \"").append(calleeNodeName).append("\" [label=\"步骤")
                    .append(stepNum).append("\"];\n");

            stepNum++;
        }
        dotContent.append("}\n");

        // 3. 写入DOT文件（可选，用于调试）
        try (FileWriter dotWriter = new FileWriter("文档/ui-call-graph.dot")) {
            dotWriter.write(dotContent.toString());
        } catch (Exception e) {
            System.err.println("写入DOT文件失败：" + e.getMessage());
        }

        // 4. 渲染DOT语法为PNG图片
        try {
            File outputFile = new File("文档/ui-call-graph.png");
            Graphviz.fromString(dotContent.toString()) // 直接解析DOT字符串
                    .width(800)
                    .render(Format.PNG)
                    .toFile(outputFile);
            System.out.println("✅ 调用图生成成功：" + outputFile.getAbsolutePath());
        } catch (Exception e) {
            System.err.println("❌ 生成调用图失败：" + e.getMessage());
            e.printStackTrace();
        }
    }

}