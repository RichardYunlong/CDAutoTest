package Net;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

import Base.GText;
import Sys.GPath;

/**
 * 网络钩子
 */
public class GWebhook {

    /**
     * 钩子清单
     */
    @SuppressWarnings("FieldCanBeLocal")
    private final String[][] WEBHOOKURL_DETAIL_TEMP = {
            { "私有云", "https://c2.yonyoucloud.com/yonbip-ec-link/intelligent-robot/system/send?accessToken=2345586188603097093" },
            { "自动化", "https://c2.yonyoucloud.com/yonbip-ec-link/intelligent-robot/system/send?accessToken=2349602979450978307" }
    };

    /**
     * 消息模板清单
     */
    @SuppressWarnings("FieldCanBeLocal")
    private final String[][] MSGTEMPLATE_DETAIL_TEMP = {
            { "私有云", GPath.JSON_TEMP_PATH + "message.json" },
            { "自动化", GPath.JSON_TEMP_PATH + "message_autotest.json" }
    };

    /**
     * 保存钩子变量
     */
    @SuppressWarnings({ "FieldMayBeFinal", "CanBeFinal" })
    private Map<String, String> WEBHOOKURL_DETAIL = new HashMap<>();

    /**
     * 保存消息模板
     */
    @SuppressWarnings({ "FieldMayBeFinal", "CanBeFinal" })
    private Map<String, String> MSGTEMPLATE_DETAIL = new HashMap<>();

    /**
     * 构造函数
     */
    public GWebhook() {
        for (String[] webhookUrl : WEBHOOKURL_DETAIL_TEMP) {
            // noinspection ConstantValue
            if (WEBHOOKURL_DETAIL_TEMP != null) {
                WEBHOOKURL_DETAIL.put(webhookUrl[0], webhookUrl[1]);
            }
        }

        for (String[] msgTemplate : MSGTEMPLATE_DETAIL_TEMP) {
            // noinspection ConstantValue
            if (MSGTEMPLATE_DETAIL_TEMP != null) {
                MSGTEMPLATE_DETAIL.put(msgTemplate[0], msgTemplate[1]);
            }
        }
    }

    /**
     * 组装并发送请求
     *
     * @param webhookUrl  钩子地址
     * @param msgTemplate 消息模板
     */
    public void sendWebhookMsg(String webhookUrl, String msgTemplate) {
        System.out.println("尝试发送......");
        String content = Base64.getEncoder().encodeToString(GText.readString(msgTemplate).getBytes());
        System.out.println("转Base64: " + content);

        String webhookUrlTemp = webhookUrl + "&domain={bip-fi-test}";
        String jsonPayload = "{\"content\": \"" + content + "\", " +
                "\"timestamp\": \"" + System.currentTimeMillis() + "\", " +
                "\"extendType\": \"universalMessage\"}";

        try {
            String response = callWebhook(webhookUrlTemp, jsonPayload);
            System.out.println("Webhook调用成功！");
            System.out.println("响应内容: " + response);
        } catch (Exception e) {
            System.err.println("Webhook调用失败: " + e.getMessage());
            // noinspection CallToPrintStackTrace
            e.printStackTrace();
        }
    }

    /**
     * 调用钩子方法
     *
     * @param webhookUrl  钩子地址
     * @param jsonPayload 请求参数
     *
     * @throws Exception 异常抛出
     * 
     * @return 响应内容
     */
    @SuppressWarnings("ExtractMethodRecommender")
    public String callWebhook(String webhookUrl, String jsonPayload) throws Exception {
        URL url = new URL(webhookUrl);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        // 设置请求方法为POST
        connection.setRequestMethod("POST");

        // 设置请求头
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setRequestProperty("User-Agent", "Java-Webhook-Caller/1.0");

        // 添加Content-Length头，避免潜在的长度问题
        connection.setRequestProperty("Content-Length", String.valueOf(jsonPayload.getBytes().length));

        // 启用输出流
        connection.setDoOutput(true);

        // ===== 关键修改：在获取输出流之前构建请求报文 =====
        // 构建完整请求报文
        StringBuilder requestMessage = new StringBuilder();

        // 1. 请求行 (方法 + URL + 协议版本)
        requestMessage.append(connection.getRequestMethod())
                .append(" ")
                .append(url)
                .append(" HTTP/1.1\n");

        // 2. 请求头 (所有已设置的请求头)
        connection.getRequestProperties()
                .forEach((headerName, values) -> values.forEach(value -> requestMessage.append(headerName)
                        .append(": ")
                        .append(value)
                        .append("\n")));

        // 3. 空行分隔请求头和请求体
        requestMessage.append("\n");

        // 4. 请求体 (已发送的Payload)
        requestMessage.append(jsonPayload);

        // 打印完整请求报文
        System.out.println("完整请求报文:\n" + requestMessage);
        // ================================================

        // 发送请求体（此时才建立连接）
        try (DataOutputStream outputStream = new DataOutputStream(connection.getOutputStream())) {
            System.out.println("请求报文结束\n");
            outputStream.writeBytes(jsonPayload);
            outputStream.flush();
        }

        // 获取响应代码
        int responseCode = connection.getResponseCode();
        System.out.println("响应代码: " + responseCode);

        // 读取响应内容
        StringBuilder response = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(
                responseCode >= 200 && responseCode < 300 ? connection.getInputStream()
                        : connection.getErrorStream()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
        }

        // 断开连接
        connection.disconnect();

        return response.toString();
    }

    /**
     * 验证方法
     */
    public void sendMsgs() {
        this.sendWebhookMsg(this.WEBHOOKURL_DETAIL.get("私有云"), this.MSGTEMPLATE_DETAIL.get("私有云"));
        this.sendWebhookMsg(this.WEBHOOKURL_DETAIL.get("自动化"), this.MSGTEMPLATE_DETAIL.get("自动化"));
    }

    /**
     * 验证方法
     *
     * @param args 命令行参数
     */
    public static void main(String[] args) {
        GWebhook demo = new GWebhook();
        demo.sendMsgs();
    }
}
