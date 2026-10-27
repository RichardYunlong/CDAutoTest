package Dragon;

/**
 *  人形弹窗动作
 *
 *  <p>对应方案A中的 8 个动作状态：待机、说话、指向、点击、成功、失败、思考、再见。
 *  每个动作都有默认帧率与"是否循环"属性：</p>
 *  <ul>
 *      <li>循环动作（idle/talk/thinking）：一直播到被切换；</li>
 *      <li>一次性动作（point/click/success/fail/bye）：播完自动回落到 idle。</li>
 *  </ul>
 *
 *  <p>动作素材的命名与枚举 {@code name} 一致，便于按目录或图集行号对应。</p>
 *
 *  @see GDragonSkin
 *  @see GDragonAnimation
 */
public enum GDragonAction {

    /**
     *  待机：呼吸起伏
     */
    IDLE("idle", 8, true, "待机"),

    /**
     *  说话：口型开合
     */
    TALK("talk", 16, true, "说话"),

    /**
     *  指向：伸手指向目标
     */
    POINT("point", 12, false, "指向"),

    /**
     *  点击：点击涟漪
     */
    CLICK("click", 12, false, "点击"),

    /**
     *  成功：跳跃与星光
     */
    SUCCESS("success", 16, false, "成功"),

    /**
     *  失败：摇头
     */
    FAIL("fail", 12, false, "失败"),

    /**
     *  思考：歪头与省略号
     */
    THINKING("thinking", 8, true, "思考"),

    /**
     *  再见：挥手
     */
    BYE("bye", 12, false, "再见");

    /**
     *  动作名，与素材目录名一致
     */
    private final String name;

    /**
     *  默认帧率
     */
    private final int defaultFps;

    /**
     *  是否循环
     */
    private final boolean loop;

    /**
     *  中文名
     */
    private final String label;

    GDragonAction(String name, int defaultFps, boolean loop, String label) {
        this.name = name;
        this.defaultFps = defaultFps;
        this.loop = loop;
        this.label = label;
    }

    /**
     *  获得动作名
     *
     *  @return 动作名
     */
    public String getName() {
        return name;
    }

    /**
     *  获得默认帧率
     *
     *  @return 默认帧率
     */
    public int getDefaultFps() {
        return defaultFps;
    }

    /**
     *  是否循环播放
     *
     *  @return 循环返回 {@code true}
     */
    public boolean isLoop() {
        return loop;
    }

    /**
     *  获得中文名
     *
     *  @return 中文名
     */
    public String getLabel() {
        return label;
    }

    /**
     *  解析动作
     *
     *  <p>支持枚举名、动作名与中文名，无法识别时回落 {@link #IDLE}。</p>
     *
     *  @param text 动作文本
     *  @return 动作
     */
    public static GDragonAction parse(String text) {
        if (text == null || text.trim().isEmpty()) {
            return IDLE;
        }
        String value = text.trim().toLowerCase();
        for (GDragonAction action : values()) {
            if (action.name.equals(value) || action.name().equals(value) || action.label.equals(text.trim())) {
                return action;
            }
        }
        return IDLE;
    }

    /**
     *  列出全部动作名
     *
     *  @return 动作名数组
     */
    public static String[] names() {
        GDragonAction[] actions = values();
        String[] names = new String[actions.length];
        for (int i = 0; i < actions.length; i++) {
            names[i] = actions[i].name;
        }
        return names;
    }
}
