package DT;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 *  实时通知
 */
public class GWebhook {

    //这里就是刚才拿到的Webhook的值
    public final String WebHookUrl =
            "https://c2.yonyoucloud.com/yonbip-ec-link/intelligent-robot/system/send?accessToken=2165197244333031435";

    public void sendMessage(String msg){
        //请求的JSON数据，这里用map在工具类里转成json格式
        Map<String, Object> json = new HashMap<>();
        Map<String, Object> text = new HashMap<>();
        json.put("msg_type", "text");
        text.put("text", "测试部告警通知：" + msg);
        json.put("content", text);

        System.out.println(JSON.toJSONString(json));
        /* 发送post请求 */
        @SuppressWarnings("ALL")
        String result = HttpRequest.post(WebHookUrl).body(JSON.toJSONString(json), "application/json;charset=UTF-8").execute().body();
        System.out.println(result);
    }

    public void sendTest() {
        this.sendMessage("测试123");
    }

    public void sendTest2(){
        JSONObject requestJson = new JSONObject();
        requestJson.put("msg_type", "text");
        requestJson.put("text", "测试部告警通知：");
        requestJson.put("content", "123");
        String requestParam = requestJson.toJSONString();
        @SuppressWarnings("ALL")
        HttpResponse responseObj = HttpRequest.post("https://c2.yonyoucloud.com/yonbip-ec-link/intelligent-robot/system/send?accessToken=2165197244333031435").body(requestParam).execute();
    }

    public static void main(String[] agrs){
        GWebhook gWebhook = new GWebhook();
        gWebhook.sendTest();
    }
}
