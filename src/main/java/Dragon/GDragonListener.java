package Dragon;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *  人形弹窗事件回调
 *
 *  <p>弹窗原来直接写 {@code GLog}，导致它必须先初始化整套日志/配置体系才能显示，
 *  无法脱离主流程单独调试。改由回调上报后：</p>
 *  <ul>
 *      <li>调试时可以用 {@link #console()} 直接把生命周期打到控制台；</li>
 *      <li>融入主流程时由调用方注入桥接到 {@code GLog} 的实现即可；</li>
 *      <li>单元测试可以用 {@link #silent()} 或自定义实现断言弹窗行为。</li>
 *  </ul>
 *
 *  @see GDragonService
 */
public interface GDragonListener {

    /**
     *  关闭原因
     */
    enum Reason {
        /**
         *  到达持续时间自动关闭
         */
        TIMEOUT,
        /**
         *  点击了关闭按钮
         */
        CLOSE,
        /**
         *  点击了确定按钮
         */
        YES,
        /**
         *  点击了取消按钮
         */
        NO,
        /**
         *  窗体被销毁（含系统关闭、进度结束等）
         */
        DISPOSED,
        /**
         *  被 {@link GDragonService#closeAll()} 统一关闭
         */
        CLOSE_ALL,
        /**
         *  构建或显示过程中出现异常
         */
        ERROR
    }

    /**
     *  弹窗已显示
     *
     *  @param option 实际生效的参数
     */
    void onShow(GDragonOption option);

    /**
     *  弹窗已关闭
     *
     *  @param option 实际生效的参数
     *  @param reason 关闭原因
     */
    void onClose(GDragonOption option, Reason reason);

    /**
     *  弹窗被跳过（开关未开、无界面环境、超过并发上限或过于频繁）
     *
     *  @param option 实际生效的参数
     *  @param reason 跳过原因
     */
    void onSkipped(GDragonOption option, String reason);

    /**
     *  弹窗处理出现异常
     *
     *  @param function 出错的功能点
     *  @param e 异常
     */
    void onError(String function, Throwable e);

    /**
     *  创建静默回调
     *
     *  @return 不做任何输出的回调
     */
    static GDragonListener silent() {
        return new GDragonListener() {
            @Override
            public void onShow(GDragonOption option) {
            }

            @Override
            public void onClose(GDragonOption option, Reason reason) {
            }

            @Override
            public void onSkipped(GDragonOption option, String reason) {
            }

            @Override
            public void onError(String function, Throwable e) {
            }
        };
    }

    /**
     *  创建控制台回调
     *
     *  <p>用于独立调试：不需要初始化任何日志体系，直接观察弹窗生命周期。</p>
     *
     *  @return 输出到控制台的回调
     */
    static GDragonListener console() {
        return new ConsoleListener();
    }

    /**
     *  控制台输出实现
     */
    class ConsoleListener implements GDragonListener {

        /**
         *  时间格式
         */
        private final SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss.SSS");

        @Override
        public void onShow(GDragonOption option) {
            println("SHOW   " + describe(option));
        }

        @Override
        public void onClose(GDragonOption option, Reason reason) {
            println("CLOSE  " + reason + "  " + describe(option));
        }

        @Override
        public void onSkipped(GDragonOption option, String reason) {
            println("SKIP   " + reason + "  " + describe(option));
        }

        @Override
        public void onError(String function, Throwable e) {
            println("ERROR  " + function + "  " + e);
        }

        /**
         *  输出一行日志
         *
         *  @param text 日志内容
         */
        private void println(String text) {
            System.out.println("[Dragon " + format.format(new Date()) + "] " + text);
        }

        /**
         *  摘要参数，避免整段报文刷屏
         *
         *  @param option 参数
         *  @return 参数摘要
         */
        private String describe(GDragonOption option) {
            if (option == null) {
                return "(null)";
            }
            String message = option.getMessage();
            if (message != null && message.length() > 60) {
                message = message.substring(0, 60) + "...";
            }
            return option.toString() + " message='" + (message == null ? "" : message.replace("\n", "\\n")) + "'";
        }
    }
}
