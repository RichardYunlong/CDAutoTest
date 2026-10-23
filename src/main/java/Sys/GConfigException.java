package Sys;

/**
 * 配置项缺失或格式不正确时抛出的异常。
 */
public class GConfigException extends IllegalStateException {

    public GConfigException(String key, String reason) {
        super("配置项 [" + key + "] " + reason);
    }

    public GConfigException(String key, String reason, Throwable cause) {
        super("配置项 [" + key + "] " + reason, cause);
    }
}
