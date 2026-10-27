/**
 * Dragon包包含GUI组件和界面相关的功能。
 *
 * <p>目前的核心是人形弹窗（智能助理），按方案A实现：
 * <b>离线预渲染的 PNG 序列帧（带 alpha）播放 + 规则元素运行时矢量绘制 + 系统语音</b>。</p>
 *
 * <ul>
 *   <li>{@link Dragon.GDragonOption}：显示参数（含动作、语音、指向目标、降级开关），支持 {@code -Dcdtest.dragon.*} 覆盖；</li>
 *   <li>{@link Dragon.GDragonAction} / {@link Dragon.GDragonFrames} / {@link Dragon.GDragonSkin}：
 *       8 个动作与素材加载（图集 / 序列帧目录 / 占位生成，三者可平滑替换）；</li>
 *   <li>{@link Dragon.GDragonAnimation} / {@link Dragon.GDragonStage}：状态机播放与双缓冲绘制，
 *       掉帧只降刷新率不降速度，窗口不可见自动暂停；</li>
 *   <li>{@link Dragon.GDragonBubble} / {@link Dragon.GDragonPointer}：泡泡文字与指向箭头，矢量绘制；</li>
 *   <li>{@link Dragon.GDragonVoice}：语音输出，默认关闭、异步、失败静默降级；</li>
 *   <li>{@link Dragon.GDragonService}：统一入口，开关、限流、无界面环境降级；</li>
 *   <li>{@link Dragon.GDragonDebug}：独立调试入口，命令行调参、动作轮播、性能基准。</li>
 * </ul>
 *
 * <p>业务侧只使用 {@link Dragon.GDragonService}，不要直接 new 窗体。
 * 弹窗的持续增强只会在本包内演进，调用方代码无需改动。</p>
 *
 * <p>指向网页元素时，坐标由调用方用 {@code element.getRect()} 换算后传入
 * {@link Dragon.GDragonOption.Builder#targetRect(int, int, int, int)}，本包不依赖 WebDriver；
 * 元素高亮由 {@code Webdriver.GWCtrlHighLight} 负责（JS 注入，跟随页面滚动）。</p>
 *
 * <p>提示节奏由 {@link Dragon.GDragonGuide} 统一控制：防抖合并、单实例复用、最小驻留、
 * 操作密集期抑制。快速连续操作只会弹出一条（显示最后一步），操作停下来后才提示；
 * 慢速操作则逐步更新同一个弹窗，不会反复新建窗口。</p>
 *
 * <p>Selenium 控件操作与弹窗的联动已经打通，业务侧无需改动：
 * {@code Webdriver.GWCtrlHighLight} 是各控件操作类（点击、填值、下拉、日期等）的统一高亮入口，
 * 它在 {@code sys.DragonShow=true} 时做三件事——在目标元素上画红色圆角框（6 像素）、
 * 换算元素的屏幕矩形、让人形弹窗指向它并自动挑选不遮挡元素的方位；
 * {@code sys.DragonShow=false} 时三者都不做。</p>
 *
 * <p>动作素材规范（放进目录即生效，无需改代码）：</p>
 * <pre>
 * src\main\resources\image\
 *     cdperson_idle\1.png 2.png ...       待机，循环
 *     cdperson_talk\1.png ...             说话，循环
 *     cdperson_point\1.png ...            指向
 *     cdperson_click\1.png ...            点击
 *     cdperson_success\1.png ...          成功
 *     cdperson_fail\1.png ...             失败
 *     cdperson_thinking\1.png ...         思考，循环
 *     cdperson_bye\1.png ...              再见
 *
 * 单张 PNG：480x720，32 位带 alpha（透明底）
 * 角色名默认 cdperson：-Dcdtest.dragon.character=xxx
 * 见 {@link Dragon.GDragonSkin#SOURCE_WIDTH} 与 {@link Dragon.GDragonSkin#SOURCE_HEIGHT}
 * </pre>
 */
package Dragon;
