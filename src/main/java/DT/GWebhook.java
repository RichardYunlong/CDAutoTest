package DT;

import java.util.HashMap;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;

/**
 * 实时通知
 */
public class GWebhook {

    /**
     *  钩子地址前缀：accessToken 不入库，通过环境变量(或 -D 系统属性) CDTEST_WEBHOOK_TOKEN 提供
     */
    private static final String WEBHOOK_URL_TEMP =
            "https://c2.yonyoucloud.com/yonbip-ec-link/intelligent-robot/system/send?accessToken=";

    public final String WebHookUrl = WEBHOOK_URL_TEMP + readToken("CDTEST_WEBHOOK_TOKEN");

    /**
     *  读取口令：优先系统属性(-D)，其次环境变量
     *
     *  @param key 变量名
     *  @return 口令，未配置时返回空串
     */
    private static String readToken(String key) {
        String token = System.getProperty(key);
        if (token == null || token.trim().isEmpty()) {
            token = System.getenv(key);
        }
        return token == null ? "" : token.trim();
    }

    /**
     * 向企业微信机器人推送一条文本消息
     *
     * <p>未配置 {@code CDTEST_WEBHOOK_TOKEN} 时只打印提示并直接返回，
     * 不会发起网络请求。推送失败只会打印响应体，不抛出异常。</p>
     *
     * @param msg 消息正文
     */
    public void sendMessage(String msg) {
        if (WebHookUrl.equals(WEBHOOK_URL_TEMP)) {
            System.out.println("[GWebhook] 未配置环境变量 CDTEST_WEBHOOK_TOKEN，跳过消息发送");
            return;
        }
        // 请求的JSON数据，这里用map在工具类里转成json格式
        Map<String, Object> json = new HashMap<>();
        Map<String, Object> text = new HashMap<>();
        json.put("msg_type", "text");
        text.put("text", "测试部告警通知：" + msg);
        json.put("content", text);

        System.out.println(JSON.toJSONString(json));
        /* 发送post请求 */
        @SuppressWarnings("ALL")
        String result = HttpRequest.post(WebHookUrl).body(JSON.toJSONString(json), "application/json;charset=UTF-8")
                .execute().body();
        System.out.println(result);
    }

    /**
     * 连通性自检：推送一条固定的测试消息
     */
    public void sendTest() {
        this.sendMessage("测试123");
    }

    /**
     * 连通性自检：直接构造 JSON 请求体推送文本消息
     *
     * <p>与 {@link #sendMessage(String)} 的区别是不做 token 缺省判断，
     * 仅用于本地验证 webhook 地址是否有效。</p>
     */
    @SuppressWarnings("unused")
    public void sendTest2() {
        JSONObject requestJson = new JSONObject();
        requestJson.put("msg_type", "text");
        requestJson.put("text", "测试部告警通知：");
        requestJson.put("content", "123");
        String requestParam = requestJson.toJSONString();
        @SuppressWarnings("ALL")
        HttpResponse responseObj = HttpRequest.post(WebHookUrl)
                .body(requestParam).execute();
    }

    /**
     * 演示入口：发送一条测试消息
     *
     * @param agrs 命令行参数，未使用
     */
    public static void main(String[] agrs) {
        GWebhook gWebhook = new GWebhook();
        gWebhook.sendTest();
    }
}
