package Dragon;

import java.util.Base64;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 *  语音输出
 *
 *  <p>方案A里的语音是"锦上添花且最影响执行耗时"的一项，因此默认关闭，
 *  且必须满足三条：异步播报、可静音、失败静默降级（不能因为没装语音库就让用例失败）。</p>
 *
 *  <p>Windows 下默认走 .NET {@code System.Speech}：通过 PowerShell 子进程播报，
 *  不引入 native 依赖、崩溃不影响本进程。若需接入 SAPI（项目已有 jacob 依赖），
 *  实现本接口替换即可，调用方无需改动。</p>
 *
 *  @see GDragonService#setVoice(GDragonVoice)
 */
public interface GDragonVoice {

    /**
     *  播报文本
     *
     *  @param text 文本
     */
    void speak(String text);

    /**
     *  异步播报文本
     *
     *  @param text 文本
     *  @param onFinish 播报结束回调，可为 {@code null}
     */
    void speak(String text, Runnable onFinish);

    /**
     *  语音是否可用
     *
     *  @return 可用返回 {@code true}
     */
    boolean isAvailable();

    /**
     *  输出语音实现描述，便于调试时确认当前用的是哪一种
     *
     *  @return 描述
     */
    String describe();

    /**
     *  创建静默实现
     *
     *  @return 不发声的实现
     */
    static GDragonVoice silent() {
        return new GDragonVoice() {
            @Override
            public void speak(String text) {
            }

            @Override
            public void speak(String text, Runnable onFinish) {
                if (onFinish != null) {
                    onFinish.run();
                }
            }

            @Override
            public boolean isAvailable() {
                return false;
            }

            @Override
            public String describe() {
                return "silent";
            }
        };
    }

    /**
     *  创建系统语音实现（Windows PowerShell + System.Speech）
     *
     *  @return 语音实现；非 Windows 平台返回静默实现
     */
    static GDragonVoice system() {
        String os = System.getProperty("os.name", "");
        if (!os.toLowerCase().contains("windows")) {
            return silent();
        }
        return new SystemVoice();
    }

    /**
     *  Windows 系统语音实现
     */
    class SystemVoice implements GDragonVoice {

        /**
         *  最长播报字数，避免长报文占用过多时间
         */
        private static final int MAX_CHARS = 200;

        /**
         *  单次播报超时，单位毫秒
         */
        private static final long TIMEOUT_MS = 60000L;

        /**
         *  播报线程
         */
        private final ExecutorService worker = Executors.newSingleThreadExecutor(runnable -> {
            Thread thread = new Thread(runnable, "GDragonVoice");
            thread.setDaemon(true);
            return thread;
        });

        /**
         *  是否曾失败，失败后不再反复起进程
         */
        private final AtomicBoolean broken = new AtomicBoolean(false);

        @Override
        public void speak(String text) {
            speak(text, null);
        }

        @Override
        public void speak(String text, Runnable onFinish) {
            if (broken.get() || text == null || text.trim().isEmpty()) {
                if (onFinish != null) {
                    onFinish.run();
                }
                return;
            }
            final String safe = clean(text);
            if (safe.isEmpty()) {
                if (onFinish != null) {
                    onFinish.run();
                }
                return;
            }
            worker.submit(() -> {
                try {
                    ProcessBuilder builder = new ProcessBuilder("powershell",
                            "-NoProfile", "-NonInteractive", "-EncodedCommand", encodeCommand(safe));
                    builder.redirectErrorStream(true);
                    Process process = builder.start();
                    boolean exited = process.waitFor(TIMEOUT_MS, java.util.concurrent.TimeUnit.MILLISECONDS);
                    if (!exited) {
                        process.destroyForcibly();
                    }
                } catch (Exception e) {
                    broken.set(true);
                } finally {
                    if (onFinish != null) {
                        onFinish.run();
                    }
                }
            });
        }

        @Override
        public boolean isAvailable() {
            return !broken.get();
        }

        @Override
        public String describe() {
            return broken.get() ? "system(broken)" : "system(powershell)";
        }

        /**
         *  清洗文本：截断、去控制字符、转义单引号
         *
         *  @param text 原始文本
         *  @return 可用于 PowerShell 的文本
         */
        private static String clean(String text) {
            String value = text.replaceAll("[\\p{Cntrl}]", " ").trim();
            if (value.length() > MAX_CHARS) {
                value = value.substring(0, MAX_CHARS);
            }
            return value.replace("'", "''");
        }

        /**
         *  生成 PowerShell -EncodedCommand 参数
         *
         *  <p>用 UTF-16LE + Base64 传递，彻底规避命令行转义与中文编码问题。</p>
         *
         *  @param text 待播报文本
         *  @return 编码后的命令
         */
        private static String encodeCommand(String text) {
            String script = "Add-Type -AssemblyName System.Speech; "
                    + "$s = New-Object System.Speech.Synthesis.SpeechSynthesizer; "
                    + "$s.Speak('" + text + "');";
            return Base64.getEncoder().encodeToString(script.getBytes(java.nio.charset.StandardCharsets.UTF_16LE));
        }
    }
}
