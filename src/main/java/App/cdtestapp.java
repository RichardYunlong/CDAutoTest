package App;

import Net.GWebhook;

import java.io.*;
import java.lang.management.ManagementFactory;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;


/**
 * 使用说明
 *  1.启动任务（带默认5分钟周期）:
 *  java -jar cdtestapp.jar -start
 *  2.启动任务（自定义周期，单位：秒）:
 *  java -jar cdtestapp.jar -start 600
 *  3.停止任务:
 *  java -jar cdtestapp.jar -stop
 *功能说明
 *  使用ScheduledExecutorService实现定时任务调度
 *  支持通过命令行参数设置执行周期（默认300秒）
 *  通过PID文件实现进程状态管理，确保单实例运行
 *  添加了关闭钩子，确保程序退出时能正确清理资源
 *  保留了原有的Webhook调用逻辑，仅添加定时和命令行控制功能
 *  程序运行时会在当前目录创建webhook_task.pid文件存储进程ID，停止时会自动删除该文件。
 * */
public class cdtestapp {

    /**
     *  保存进程号文件
     */
    private static final String PID_FILE = "cdtestapp_task.pid";

    /**
     *  周期执行线程池
     */
    private static ScheduledExecutorService scheduler;

    /**
     *  检测方法
     *
     * @param args 入参列表
     */
    public static void main(String[] args) {
        // 解析命令行参数
        if (args.length == 0) {
            printUsage();
            return;
        }

        switch (args[0].toLowerCase()) {
            case "-start":
                startTask(args);
                break;
            case "-stop":
                stopTask();
                break;
            default:
                System.out.println("未知命令: " + args[0]);
                printUsage();
        }
    }

    /**
     *  打印提示文字
     */
    private static void printUsage() {
        System.out.println("用法:");
        System.out.println("  启动任务: java -jar cdtestapp.jar -start [周期(秒)]");
        System.out.println("  停止任务: java -jar cdtestapp.jar -stop");
        System.out.println("  示例: java -jar cdtestapp.jar -start 300  (每5分钟执行一次)");
    }

    /**
     *  启动进程
     *
     * @param args 入参列表
     */
    private static void startTask(String[] args) {
        // 检查是否已在运行
        if (isTaskRunning()) {
            System.out.println("任务已在运行中");
            return;
        }

        // 默认周期为5分钟(300秒)
        long interval = 300;
        if (args.length > 1) {
            try {
                interval = Long.parseLong(args[1]);
                System.out.println("解析到自定义周期: " + interval + "秒"); // 添加此行
                if (interval < 10) {
                    System.out.println("周期不能小于10秒，已设置为默认值300秒");
                    interval = 300;
                }
            } catch (NumberFormatException e) {
                System.out.println("周期参数格式错误，已使用默认值300秒");
            }
        }

        // 保存进程ID，用于停止任务
        savePid();

        // 创建定时任务
        scheduler = Executors.newSingleThreadScheduledExecutor();
        scheduler.scheduleAtFixedRate(() -> {
            try {
                System.out.println("[" + new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()) + "] 周期执行Webhook任务开始");
                executeWebhookTask();
            } catch (Exception e) {
                System.err.println("定时任务执行失败: " + e.getMessage());
                //noinspection CallToPrintStackTrace
                e.printStackTrace();
            }
        }, 0, interval, TimeUnit.SECONDS);

        System.out.println("定时任务已启动，周期: " + interval + "秒");
        System.out.println("任务PID已保存至: " + PID_FILE);

        // 添加关闭钩子，确保程序退出时清理资源
        Runtime.getRuntime().addShutdownHook(new Thread(cdtestapp::stopTask));
    }

    /**
     *  结束进程
     */
    private static void stopTask() {
        if (scheduler != null && !scheduler.isShutdown()) {
            scheduler.shutdown();
            try {
                if (!scheduler.awaitTermination(1, TimeUnit.SECONDS)) {
                    scheduler.shutdownNow();
                }
            } catch (InterruptedException e) {
                scheduler.shutdownNow();
            }
            System.out.println("定时任务已停止");
        }

        // 删除PID文件
        File pidFile = new File(PID_FILE);
        if (pidFile.exists()) {
            //noinspection ResultOfMethodCallIgnored
            pidFile.delete();
        }
    }

    /**
     *  新增辅助方法
     */
    private static boolean isTaskRunning() {
        File pidFile = new File(PID_FILE);
        if (!pidFile.exists()) {
            return false;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(pidFile))) {
            String pid = reader.readLine();
            if (pid == null || pid.isEmpty()) {
                return false;
            }

            // 检查Windows系统中进程是否存在
            Process process = Runtime.getRuntime().exec("tasklist /FI \"PID eq " + pid + "\"");
            try (BufferedReader taskListReader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
                String line;
                while ((line = taskListReader.readLine()) != null) {
                    if (line.contains(pid)) {
                        return true;
                    }
                }
            }
            return false;
        } catch (IOException e) {
            return false;
        }
    }

    /**
     *  保存进程号
     */
    private static void savePid() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(PID_FILE))) {
            // Java 1.8获取进程ID的方式：从运行时MXBean名称中提取
            String runtimeName = ManagementFactory.getRuntimeMXBean().getName();
            String pid = runtimeName.split("@")[0]; // 格式通常为 "pid@hostname"
            writer.println(pid);
        } catch (IOException e) {
            System.err.println("保存PID文件失败: " + e.getMessage());
        } catch (ArrayIndexOutOfBoundsException e) {
            System.err.println("获取进程ID失败: " + e.getMessage());
        }
    }

    /**
     *  触发钩子
     */
    private static void executeWebhookTask() {
        GWebhook demo = new GWebhook();
        demo.sendMsgs();
    }
}
