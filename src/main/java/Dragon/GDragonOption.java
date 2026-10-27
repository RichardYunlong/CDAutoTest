package Dragon;

import java.awt.Color;
import java.awt.Point;
import java.awt.Rectangle;

/**
 *  人形弹窗显示参数
 *
 *  <p>把原来散落在 {@code GDragon} 构造函数里的八个入参收敛成一个不可变对象。
 *  这样改造是为了让"人形弹窗"可以被独立调试与持续优化：</p>
 *  <ol>
 *      <li>调试时只改参数，不必改动调用方代码；</li>
 *      <li>后续增强弹窗样式（标题、边框、动效等）时新增字段不会波及所有调用点；</li>
 *      <li>运行期可用 {@code -Dcdtest.dragon.*} 系统属性覆盖，无需改配置文件。</li>
 *  </ol>
 *
 *  <p>典型用法：</p>
 *  <pre>{@code
 *  GDragonOption option = GDragonOption.builder()
 *          .message("用例执行完毕")
 *          .time(5000)
 *          .location(1200, 700)
 *          .font("微软雅黑", 14, Color.BLUE)
 *          .build();
 *  }</pre>
 *
 *  @see GDragon
 *  @see GDragonService
 */
public final class GDragonOption {

    /**
     *  系统属性前缀
     */
    public static final String SYS_PREFIX = "cdtest.dragon.";

    /**
     *  默认持续时间，单位毫秒
     */
    public static final int DEFAULT_TIME = 3000;

    /**
     *  默认字体名称
     */
    public static final String DEFAULT_FONT_TYPE = "微软雅黑";

    /**
     *  默认字号
     */
    public static final int DEFAULT_FONT_SIZE = 14;

    /**
     *  默认字色
     */
    public static final Color DEFAULT_FONT_COLOR = Color.BLUE;

    /**
     *  提示内容
     */
    private final String message;

    /**
     *  标题
     */
    private final String title;

    /**
     *  持续时间，单位毫秒
     */
    private final int mTime;

    /**
     *  横坐标；小于等于0时自动贴右下角
     */
    private final int dX;

    /**
     *  纵坐标；小于等于0时自动贴右下角
     */
    private final int dY;

    /**
     *  人形头像图片名；为空时随机选取内置头像
     */
    private final String img;

    /**
     *  字体名称
     */
    private final String fontType;

    /**
     *  字号
     */
    private final int fontSize;

    /**
     *  字色
     */
    private final Color fontColor;

    /**
     *  动作
     */
    private final GDragonAction action;

    /**
     *  语音文本；为空时不播报
     */
    private final String voice;

    /**
     *  指向目标的屏幕矩形；为空表示不指向
     *
     *  <p>矩形用于挑选弹窗位置（避免压住目标），中心点用于画指向箭头。</p>
     */
    private final Rectangle targetRect;

    /**
     *  是否播放动画；关闭时只显示静态首帧，用于低性能环境降级
     */
    private final boolean animate;

    /**
     *  是否使用泡泡文字
     */
    private final boolean bubble;

    /**
     *  泡泡文字是否使用打字机效果
     */
    private final boolean typing;

    private GDragonOption(Builder builder) {
        this.message = builder.message;
        this.title = builder.title;
        this.mTime = builder.mTime;
        this.dX = builder.dX;
        this.dY = builder.dY;
        this.img = builder.img;
        this.fontType = builder.fontType;
        this.fontSize = builder.fontSize;
        this.fontColor = builder.fontColor;
        this.action = builder.action;
        this.voice = builder.voice;
        this.targetRect = builder.targetRect;
        this.animate = builder.animate;
        this.bubble = builder.bubble;
        this.typing = builder.typing;
    }

    /**
     *  创建构造器
     *
     *  @return 携带默认值的构造器
     */
    public static Builder builder() {
        return new Builder();
    }

    /**
     *  创建默认参数
     *
     *  @return 全部取默认值的参数对象
     */
    public static GDragonOption defaults() {
        return builder().build();
    }

    /**
     *  按提示内容创建参数，其余取默认值
     *
     *  @param message 提示内容
     *  @return 参数对象
     */
    public static GDragonOption of(String message) {
        return builder().message(message).build();
    }

    /**
     *  获得提示内容
     *
     *  @return 提示内容
     */
    public String getMessage() {
        return message;
    }

    /**
     *  获得标题
     *
     *  @return 标题
     */
    public String getTitle() {
        return title;
    }

    /**
     *  获得持续时间
     *
     *  @return 持续时间，单位毫秒
     */
    public int getMTime() {
        return mTime;
    }

    /**
     *  获得横坐标
     *
     *  @return 横坐标
     */
    public int getDX() {
        return dX;
    }

    /**
     *  获得纵坐标
     *
     *  @return 纵坐标
     */
    public int getDY() {
        return dY;
    }

    /**
     *  获得人形头像图片名
     *
     *  @return 图片名；为空表示随机选取
     */
    public String getImg() {
        return img;
    }

    /**
     *  获得字体名称
     *
     *  @return 字体名称
     */
    public String getFontType() {
        return fontType;
    }

    /**
     *  获得字号
     *
     *  @return 字号
     */
    public int getFontSize() {
        return fontSize;
    }

    /**
     *  获得字色
     *
     *  @return 字色
     */
    public Color getFontColor() {
        return fontColor;
    }

    /**
     *  获得动作
     *
     *  @return 动作
     */
    public GDragonAction getAction() {
        return action;
    }

    /**
     *  获得语音文本
     *
     *  @return 语音文本；为空表示不播报
     */
    public String getVoice() {
        return voice;
    }

    /**
     *  获得指向目标
     *
     *  @return 目标中心点屏幕坐标；无目标返回 {@code null}
     */
    public Point getTarget() {
        if (targetRect == null) {
            return null;
        }
        return new Point(targetRect.x + targetRect.width / 2, targetRect.y + targetRect.height / 2);
    }

    /**
     *  获得指向目标的屏幕矩形
     *
     *  @return 目标矩形；无目标返回 {@code null}
     */
    public Rectangle getTargetRect() {
        return targetRect;
    }

    /**
     *  是否播放动画
     *
     *  @return 播放返回 {@code true}
     */
    public boolean isAnimate() {
        return animate;
    }

    /**
     *  是否使用泡泡文字
     *
     *  @return 使用返回 {@code true}
     */
    public boolean isBubble() {
        return bubble;
    }

    /**
     *  泡泡文字是否使用打字机效果
     *
     *  @return 使用返回 {@code true}
     */
    public boolean isTyping() {
        return typing;
    }

    /**
     *  派生一份参数副本，仅替换是否播放动画
     *
     *  <p>供 {@link GDragonService} 在低性能环境统一降级时使用，调用方无需感知。</p>
     *
     *  @param animate 是否播放动画
     *  @return 参数副本
     */
    public GDragonOption withAnimate(boolean animate) {
        Builder builder = builder()
                .message(message)
                .title(title)
                .time(mTime)
                .location(dX, dY)
                .image(img)
                .font(fontType, fontSize, fontColor)
                .action(action)
                .voice(voice)
                .animate(animate)
                .bubble(bubble)
                .typing(typing);
        if (targetRect != null) {
            builder.targetRect(targetRect.x, targetRect.y, targetRect.width, targetRect.height);
        }
        return builder.build();
    }

    /**
     *  输出参数摘要，便于调试时核对实际生效值
     *
     *  @return 参数摘要
     */
    @Override
    public String toString() {
        return "GDragonOption{mTime=" + mTime
                + ", dX=" + dX
                + ", dY=" + dY
                + ", img='" + img + '\''
                + ", font=" + fontType + '/' + fontSize
                + ", action=" + action.getName()
                + ", animate=" + animate
                + ", bubble=" + bubble
                + ", message='" + message + '\''
                + '}';
    }

    /**
     *  人形弹窗参数构造器
     */
    public static final class Builder {

        private String message = "";
        private String title = "";
        private int mTime = DEFAULT_TIME;
        private int dX = 0;
        private int dY = 0;
        private String img = "";
        private String fontType = DEFAULT_FONT_TYPE;
        private int fontSize = DEFAULT_FONT_SIZE;
        private Color fontColor = DEFAULT_FONT_COLOR;
        private GDragonAction action = GDragonAction.IDLE;
        private String voice = "";
        private Rectangle targetRect = null;
        private boolean animate = true;
        private boolean bubble = true;
        private boolean typing = true;

        private Builder() {
        }

        /**
         *  设置提示内容
         *
         *  @param message 提示内容
         *  @return 构造器自身
         */
        public Builder message(String message) {
            if (message != null) {
                this.message = message;
            }
            return this;
        }

        /**
         *  设置标题
         *
         *  @param title 标题
         *  @return 构造器自身
         */
        public Builder title(String title) {
            if (title != null) {
                this.title = title;
            }
            return this;
        }

        /**
         *  设置持续时间
         *
         *  @param mTime 持续时间，单位毫秒；小于等于0时保持默认值
         *  @return 构造器自身
         */
        public Builder time(int mTime) {
            if (mTime > 0) {
                this.mTime = mTime;
            }
            return this;
        }

        /**
         *  设置左上角顶点位置
         *
         *  @param dX 横坐标；小于等于0时自动贴右下角
         *  @param dY 纵坐标；小于等于0时自动贴右下角
         *  @return 构造器自身
         */
        public Builder location(int dX, int dY) {
            this.dX = Math.max(dX, 0);
            this.dY = Math.max(dY, 0);
            return this;
        }

        /**
         *  设置人形头像
         *
         *  @param img 图片名或路径；为空时随机选取内置头像
         *  @return 构造器自身
         */
        public Builder image(String img) {
            this.img = img == null ? "" : img.trim();
            return this;
        }

        /**
         *  设置字体
         *
         *  <p>三个入参均支持"留空保持默认"：为 {@code null}、空串或小于等于0时不覆盖原值。</p>
         *
         *  @param fontType 字体名称
         *  @param fontSize 字号
         *  @param fontColor 字色
         *  @return 构造器自身
         */
        public Builder font(String fontType, int fontSize, Color fontColor) {
            if (fontType != null && !fontType.isEmpty()) {
                this.fontType = fontType;
            }
            if (fontSize > 0) {
                this.fontSize = fontSize;
            }
            if (fontColor != null) {
                this.fontColor = fontColor;
            }
            return this;
        }

        /**
         *  设置字色
         *
         *  @param fontColor 字色
         *  @return 构造器自身
         */
        public Builder fontColor(Color fontColor) {
            if (fontColor != null) {
                this.fontColor = fontColor;
            }
            return this;
        }

        /**
         *  设置动作
         *
         *  @param action 动作
         *  @return 构造器自身
         */
        public Builder action(GDragonAction action) {
            this.action = action == null ? GDragonAction.IDLE : action;
            return this;
        }

        /**
         *  设置动作
         *
         *  <p>支持枚举名、动作名与中文名，无法识别时回落 idle。</p>
         *
         *  @param action 动作文本
         *  @return 构造器自身
         */
        public Builder action(String action) {
            this.action = GDragonAction.parse(action);
            return this;
        }

        /**
         *  设置语音文本
         *
         *  @param voice 语音文本；为空表示不播报
         *  @return 构造器自身
         */
        public Builder voice(String voice) {
            this.voice = voice == null ? "" : voice;
            return this;
        }

        /**
         *  设置指向目标的屏幕坐标
         *
         *  @param targetX 目标横坐标
         *  @param targetY 目标纵坐标
         *  @return 构造器自身
         */
        public Builder target(int targetX, int targetY) {
            this.targetRect = new Rectangle(targetX, targetY, 0, 0);
            return this;
        }

        /**
         *  设置指向目标的屏幕矩形
         *
         *  <p>给出完整矩形时，弹窗会挑选一个不压住目标的位置；只给点时退化为按点定位。</p>
         *
         *  @param targetX 目标左上角横坐标
         *  @param targetY 目标左上角纵坐标
         *  @param targetWidth 目标宽
         *  @param targetHeight 目标高
         *  @return 构造器自身
         */
        public Builder targetRect(int targetX, int targetY, int targetWidth, int targetHeight) {
            this.targetRect = new Rectangle(targetX, targetY, Math.max(targetWidth, 0), Math.max(targetHeight, 0));
            return this;
        }

        /**
         *  设置是否播放动画
         *
         *  @param animate 播放返回 {@code true}
         *  @return 构造器自身
         */
        public Builder animate(boolean animate) {
            this.animate = animate;
            return this;
        }

        /**
         *  设置是否使用泡泡文字
         *
         *  @param bubble 使用返回 {@code true}
         *  @return 构造器自身
         */
        public Builder bubble(boolean bubble) {
            this.bubble = bubble;
            return this;
        }

        /**
         *  设置泡泡文字是否使用打字机效果
         *
         *  @param typing 使用返回 {@code true}
         *  @return 构造器自身
         */
        public Builder typing(boolean typing) {
            this.typing = typing;
            return this;
        }

        /**
         *  用系统属性覆盖当前取值
         *
         *  <p>支持：{@code cdtest.dragon.time / x / y / img / font / fontSize / color / message}。</p>
         *
         *  @return 构造器自身
         */
        public Builder applySystemProperties() {
            this.mTime = intProp("time", this.mTime);
            this.dX = intProp("x", this.dX);
            this.dY = intProp("y", this.dY);
            this.fontSize = intProp("fontSize", this.fontSize);

            String imgProp = System.getProperty(SYS_PREFIX + "img");
            if (imgProp != null && !imgProp.trim().isEmpty()) {
                this.img = imgProp.trim();
            }
            String fontProp = System.getProperty(SYS_PREFIX + "font");
            if (fontProp != null && !fontProp.trim().isEmpty()) {
                this.fontType = fontProp.trim();
            }
            String colorProp = System.getProperty(SYS_PREFIX + "color");
            if (colorProp != null) {
                Color parsed = parseColor(colorProp);
                if (parsed != null) {
                    this.fontColor = parsed;
                }
            }
            String messageProp = System.getProperty(SYS_PREFIX + "message");
            if (messageProp != null) {
                this.message = messageProp;
            }
            String actionProp = System.getProperty(SYS_PREFIX + "action");
            if (actionProp != null) {
                this.action = GDragonAction.parse(actionProp);
            }
            String voiceProp = System.getProperty(SYS_PREFIX + "voice");
            if (voiceProp != null) {
                this.voice = voiceProp;
            }
            if (System.getProperty(SYS_PREFIX + "animate") != null) {
                this.animate = Boolean.parseBoolean(System.getProperty(SYS_PREFIX + "animate"));
            }
            if (System.getProperty(SYS_PREFIX + "bubble") != null) {
                this.bubble = Boolean.parseBoolean(System.getProperty(SYS_PREFIX + "bubble"));
            }
            return this;
        }

        /**
         *  构造参数对象
         *
         *  @return 参数对象
         */
        public GDragonOption build() {
            return new GDragonOption(this);
        }

        /**
         *  读取整数型系统属性
         *
         *  @param name 属性名（不含前缀）
         *  @param defaultValue 缺省值
         *  @return 属性值或缺省值
         */
        private static int intProp(String name, int defaultValue) {
            String value = System.getProperty(SYS_PREFIX + name);
            if (value == null || value.trim().isEmpty()) {
                return defaultValue;
            }
            try {
                return Integer.parseInt(value.trim());
            } catch (NumberFormatException e) {
                return defaultValue;
            }
        }

        /**
         *  解析颜色文本
         *
         *  <p>支持 {@code #RRGGBB} 与少量颜色名；无法解析时返回 {@code null}。</p>
         *
         *  @param text 颜色文本
         *  @return 颜色；解析失败返回 {@code null}
         */
        private static Color parseColor(String text) {
            String value = text.trim();
            if (value.startsWith("#") && (value.length() == 7 || value.length() == 9)) {
                try {
                    return Color.decode(value);
                } catch (NumberFormatException e) {
                    return null;
                }
            }
            switch (value.toLowerCase()) {
                case "blue":
                    return Color.BLUE;
                case "red":
                    return Color.RED;
                case "green":
                    return Color.GREEN;
                case "black":
                    return Color.BLACK;
                case "white":
                    return Color.WHITE;
                case "gray":
                case "grey":
                    return Color.GRAY;
                case "orange":
                    return Color.ORANGE;
                case "yellow":
                    return Color.YELLOW;
                default:
                    return null;
            }
        }
    }
}
