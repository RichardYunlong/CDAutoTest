package Webdriver;

import DT.GLog;
import Dragon.GDragonAction;
import Dragon.GDragonGuide;
import Dragon.GDragonService;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.awt.Rectangle;
import java.awt.Toolkit;
import java.util.Arrays;
import java.util.List;

/**
 *  高亮等强调类提示
 *
 *  <p>Selenium 控件操作（点击、填值、下拉、日期…）的统一强调入口：
 *  在目标元素上画框，并让 {@link GDragonService} 的人形弹窗指向该元素。</p>
 *
 *  <p>总开关为配置项 {@code sys.DragonShow}：</p>
 *  <ul>
 *      <li>{@code true}：画红色圆角框 + 人形弹窗显示在元素旁（自动选不遮挡元素的方位）；</li>
 *      <li>{@code false}：两者都不做，用例执行过程与平时完全一致。</li>
 *  </ul>
 *
 *  <p>画框用 {@code outline} 而不是 {@code border}，不改变元素尺寸与布局，
 *  避免影响后续点击坐标；圆角由 {@code border-radius} 提供，浏览器会让 outline 跟随圆角。</p>
 *
 *  <p>样式是<b>逐项设置</b>的（{@code style.setProperty}），不是整串替换 {@code style} 属性：
 *  Vue 这类框架重渲染时会重写整个 {@code style} 属性，整串替换的写法会被冲掉，
 *  表现为"红框一闪就没"或"根本不出现"。逐项设置时框架改的是别的属性，框不受影响；
 *  存活期内还会定期检查并在被冲掉时补回。</p>
 */
public class GWCtrlHighLight {

	/**
	 *  是否开启强调的开关
	 *  1-仅开启边框
	 *  2-仅开启高亮
	 *  3-同时开启边框和高亮
	 *  其他-关闭强调效果
	 */
	public static final int bHighLight = 1;

	/**
	 *  上次记录过的目标矩形，用于避免重复打日志
	 */
	private static volatile Rectangle loggedRect;

	static {
		//弹窗几何（元素、窗口、箭头三个矩形）走项目日志：
		//位置不对时直接对照这三行数据，不用再靠截图推测
		GDragonGuide.setLogger(msg -> GLog.logRecordTime(9, msg));
	}

	/**
	 *  边框宽度，单位像素
	 */
	public static final String strBorder = "6";

	/**
	 *  边框颜色
	 */
	public static final String strBorderColor = "red";

	/**
	 *  圆角半径，单位像素
	 */
	public static final String strBorderRadius = "8";

	/**
	 *  高亮背景色
	 */
	public static final String strColor = "yellow";

	/**
	 *  元素提示弹窗的默认文案：控件操作处多数不传提示语
	 */
	public static final String strDefaultTip = "正在操作页面元素";

	/**
	 *  补回被页面重渲染冲掉的框的检查间隔，单位毫秒
	 */
	private static final long KEEP_ALIVE_MS = 250;

	/**
	 *  画框并在存活期内保活的脚本
	 *
	 *  <p>参数：{@code arguments[0]} 目标元素；{@code arguments[1]} 样式数组，
	 *  按“属性名、属性值”成对排列；{@code arguments[2]} 存活毫秒数。</p>
	 *
	 *  <p>做三件事：</p>
	 *  <ol>
	 *      <li>画新框前先把上一个元素恢复原样（同一时刻只保留一个框）；</li>
	 *      <li>记下被改属性的原值，到期后只还原这几个属性，不动元素其它样式；</li>
	 *      <li>存活期内定时重设，被页面重渲染冲掉时补回来。</li>
	 *  </ol>
	 */
	private static final String PAINT_SCRIPT =
			"(function(){"
					+ "var e = arguments[0], pairs = arguments[1] || [], life = arguments[2];"
					+ "life = (typeof life === 'number' && life > 0) ? life : 2000;"
					+ "var keys = [];"
					+ "for (var i = 0; i + 1 < pairs.length; i += 2) { keys.push(pairs[i]); }"
					+ "function set() {"
					+ "  for (var i = 0; i + 1 < pairs.length; i += 2) {"
					+ "    try { e.style.setProperty(pairs[i], pairs[i + 1], 'important'); } catch (err) {}"
					+ "  }"
					+ "}"
					+ "function reset(mark) {"
					+ "  if (!mark || !mark.e) { return; }"
					+ "  var ks = mark.keys || [], orig = mark.orig || {};"
					+ "  for (var i = 0; i < ks.length; i++) {"
					+ "    try {"
					+ "      var old = orig[ks[i]];"
					+ "      if (old) { mark.e.style.setProperty(ks[i], old); }"
					+ "      else { mark.e.style.removeProperty(ks[i]); }"
					+ "    } catch (err) {}"
					+ "  }"
					+ "}"
					+ "if (window.__cdMark) { reset(window.__cdMark); }"
					+ "var orig = {};"
					+ "for (var i = 0; i < keys.length; i++) {"
					+ "  try { orig[keys[i]] = e.style.getPropertyValue(keys[i]); }"
					+ "  catch (err) { orig[keys[i]] = ''; }"
					+ "}"
					+ "set();"
					+ "window.__cdMark = { e: e, orig: orig, keys: keys };"
					+ "if (window.__cdTimer) { window.clearTimeout(window.__cdTimer); }"
					+ "if (window.__cdKeeper) { window.clearInterval(window.__cdKeeper); }"
					+ "window.__cdKeeper = window.setInterval(set, " + KEEP_ALIVE_MS + ");"
					+ "window.__cdTimer = window.setTimeout(function() {"
					+ "  window.clearInterval(window.__cdKeeper);"
					+ "  window.__cdKeeper = null;"
					+ "  reset(window.__cdMark);"
					+ "  window.__cdMark = null;"
					+ "  window.__cdTimer = null;"
					+ "}, life);"
					+ "})();";

	private GWCtrlHighLight() {
	}

	/**
	 *  通过指定元素的WebElement对象来添加边框
	 *
	 *  @param webDriver 全局驱动
	 *  @param webElement 目标元素
	 *  @param msg 提示语，为空时使用默认提示语
	 */
	private static void byWebElementBorder(WebDriver webDriver, WebElement webElement, String msg) {
		paint(webDriver, webElement, new String[]{"outline", strBorder + "px solid " + strBorderColor,
				"outline-offset", "2px", "border-radius", strBorderRadius + "px"}, msg);
	}

	/**
	 *  通过指定元素的WebElement对象来添加高亮背景
	 *
	 *  @param webDriver 全局驱动
	 *  @param webElement 目标元素
	 *  @param msg 提示语，为空时使用默认提示语
	 */
	private static void byWebElementBackground(WebDriver webDriver, WebElement webElement, String msg) {
		paint(webDriver, webElement, new String[]{"background", strColor}, msg);
	}

	/**
	 *  通过指定元素的WebElement对象来添加高亮背景加边框
	 *
	 *  @param webDriver 全局驱动
	 *  @param webElement 目标元素
	 *  @param msg 提示语，为空时使用默认提示语
	 */
	private static void byWebElement(WebDriver webDriver, WebElement webElement, String msg) {
		paint(webDriver, webElement, new String[]{"background", strColor,
				"outline", strBorder + "px solid " + strBorderColor,
				"outline-offset", "2px", "border-radius", strBorderRadius + "px"}, msg);
	}

	/**
	 *  在目标元素上绘制强调样式，并弹出指向该元素的人形弹窗
	 *
	 *  @param webDriver 全局驱动
	 *  @param webElement 目标元素
	 *  @param style 要设置的样式，按“属性名、属性值”成对排列
	 *  @param msg 提示语，为空时使用默认提示语
	 */
	private static void paint(WebDriver webDriver, WebElement webElement, String[] style, String msg) {
		if (webDriver == null || webElement == null) {
			return;
		}
		//与弹窗活同样久：同一时刻只保留一个框，不会"满屏红框叠加"，
		//压短寿命只会让人来不及看清框画在哪个元素上
		long life = GTestIndicators.DragonShowTime;
		try {
			JavascriptExecutor js = (JavascriptExecutor) webDriver;
			//传List而不是String[]：Selenium 3.8.1 对数组参数的序列化在部分驱动上会丢参数
			js.executeScript(PAINT_SCRIPT, webElement, Arrays.asList(style), life);
		} catch (Exception e) {
			GLog.logSysFunctionException("GWCtrlHighLight.paint", e);
			probe(webDriver, webElement, style, life);
		}
		tip(webDriver, webElement, msg);
	}

	/**
	 *  画框失败时探测脚本参数在浏览器端的实际接收情况
	 *
	 *  <p>Selenium 3.8.1 与新版本 chromedriver 组合下，executeScript 的参数可能丢失，
	 *  元素参数也可能没被还原成Element对象，仅凭异常信息分不清是哪一个，
	 *  这里把参数个数、元素类型与标签名、样式类型、存活时间类型打到日志里。
	 *  标签名非空即说明元素参数正常。</p>
	 *
	 *  @param webDriver 全局驱动
	 *  @param webElement 目标元素
	 *  @param style 要设置的样式，按“属性名、属性值”成对排列
	 *  @param life 存活毫秒数
	 */
	private static void probe(WebDriver webDriver, WebElement webElement, String[] style, long life) {
		try {
			Object result = ((JavascriptExecutor) webDriver).executeScript(
					"return [arguments.length,"
							+ " typeof arguments[0],"
							+ " (arguments[0] && arguments[0].tagName) ? arguments[0].tagName : '',"
							+ " typeof arguments[1],"
							+ " typeof arguments[2]];",
					webElement, style, life);
			GLog.logSysFunction("GWCtrlHighLight.paint", "[probe]" + result);
		} catch (Exception e) {
			GLog.logSysFunction("GWCtrlHighLight.paint", "[probe]failed:" + e);
		}
	}

	/**
	 *  提交指向目标元素的人形弹窗
	 *
	 *  <p>交给 {@link GDragonGuide} 统一控制节奏：快速连续操作时会被合并成一条，
	 *  操作密集时推迟到空闲后再弹；取不到元素屏幕坐标时退化为屏幕右下角显示。</p>
	 *
	 *  @param webDriver 全局驱动
	 *  @param webElement 目标元素
	 *  @param msg 提示语，为空时使用默认提示语
	 */
	private static void tip(WebDriver webDriver, WebElement webElement, String msg) {
		String text = (msg == null || msg.trim().isEmpty()) ? strDefaultTip : msg.trim();
		long showTime = GTestIndicators.DragonShowTime;
		Rectangle rect = screenRect(webDriver, webElement);
		if (rect == null) {
			GDragonGuide.getInstance().tip(text, GDragonAction.POINT, showTime);
			return;
		}
		//位置不快照：弹窗会被防抖与密集期推迟，等它显示时元素可能已经移动或重排，
		//传入取坐标的方法，让弹窗在真正显示那一刻才去量一次，显示期间还会复查
		GDragonGuide.getInstance().tip(text, GDragonAction.POINT,
				() -> screenRect(webDriver, webElement), showTime);
	}

	/**
	 *  换算目标元素在屏幕上的矩形
	 *
	 *  <p>页面坐标（{@code getBoundingClientRect}）加上浏览器窗口位置与工具栏高度得到屏幕坐标，
	 *  与页面缩放无关；元素不在可视区域时返回 {@code null}。</p>
	 *
	 *  <p><b>高 DPI 缩放必须换算</b>：浏览器给的是 CSS 像素（已经除以了系统缩放比），
	 *  而 Swing 拿到的屏幕坐标是物理像素。系统缩放 150% 时两者差 1.5 倍，
	 *  直接混用会让弹窗整体跑到元素左上方的远处——看起来像"箭头没指到元素"，
	 *  实际是整块坐标都偏了。这里把浏览器的屏幕尺寸一起带回来，
	 *  与 {@link Toolkit#getScreenSize()} 相除得到缩放比，两边就对齐了。</p>
	 *
	 *  @param webDriver 全局驱动
	 *  @param webElement 目标元素
	 *  @return 屏幕矩形；无法换算时返回 {@code null}
	 */
	private static Rectangle screenRect(WebDriver webDriver, WebElement webElement) {
		try {
			Object result = ((JavascriptExecutor) webDriver).executeScript(
					"var r = arguments[0].getBoundingClientRect();"
							+ "var barH = Math.max(window.outerHeight - window.innerHeight, 0);"
							+ "var barW = Math.max(window.outerWidth - window.innerWidth, 0);"
							+ "return [r.left + window.screenX + barW / 2, r.top + window.screenY + barH,"
							+ " r.width, r.height, window.screen.width, window.screen.height];",
					webElement);
			if (!(result instanceof List) || ((List<?>) result).size() < 6) {
				return null;
			}
			List<?> values = (List<?>) result;
			java.awt.Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
			double scaleX = scale(screen.width, ((Number) values.get(4)).doubleValue());
			double scaleY = scale(screen.height, ((Number) values.get(5)).doubleValue());
			int x = (int) Math.round(((Number) values.get(0)).doubleValue() * scaleX);
			int y = (int) Math.round(((Number) values.get(1)).doubleValue() * scaleY);
			int width = (int) Math.round(((Number) values.get(2)).doubleValue() * scaleX);
			int height = (int) Math.round(((Number) values.get(3)).doubleValue() * scaleY);
			if (width <= 0 || height <= 0) {
				return null;
			}
			if (x + width <= 0 || y + height <= 0 || x >= screen.width || y >= screen.height) {
				return null;
			}
			Rectangle rect = new Rectangle(x, y, width, height);
			//弹窗位置全由这个矩形决定，偏移时唯一能核对的就是这几个数。
			//显示期间每 300 毫秒会复查一次，坐标没动就不重复记
			Rectangle last = loggedRect;
			if (last == null || Math.abs(last.x - x) > 4 || Math.abs(last.y - y) > 4) {
				loggedRect = rect;
				GLog.logRecordTime(9, "[指向]元素屏幕矩形=" + rect + " 缩放=" + scaleX + "x" + scaleY
						+ " Java屏幕=" + screen.width + "x" + screen.height
						+ " 浏览器屏幕=" + (int) ((Number) values.get(4)).doubleValue()
						+ "x" + (int) ((Number) values.get(5)).doubleValue());
			}
			return rect;
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 *  计算浏览器像素到 Java 像素的缩放比
	 *
	 *  <p>取两边的屏幕宽度相除：Java 端感知 DPI 时为物理像素，比值就是系统缩放；
	 *  不感知 DPI 时两边都是逻辑像素，比值为 1，同样正确。</p>
	 *
	 *  <p>浏览器在另一块分辨率不同的显示器上时会算出离谱的比值，
	 *  超出合理范围一律按 1 处理，宁可差一点也不要整体偏到屏幕外。</p>
	 *
	 *  @param javaPixels Java 端屏幕尺寸
	 *  @param cssPixels 浏览器报告的屏幕尺寸
	 *  @return 缩放比
	 */
	private static double scale(int javaPixels, double cssPixels) {
		if (cssPixels <= 0) {
			return 1;
		}
		double scale = javaPixels / cssPixels;
		return scale > 0.4 && scale < 3.5 ? scale : 1;
	}

	/**
	 *  通过指定元素的WebElement对象来添加高亮背景加边框
	 *
	 *  @param webDriver 全局驱动
	 *  @param webElement 目标元素
	 *  @param type 1-仅开启边框；2-仅开启高亮；3-同时开启边框和高亮;其他-关闭强调效果
	 *  @param msg 提示语，为空时使用默认提示语
	 */
	@SuppressWarnings({"DataFlowIssue", "UnreachableCode"})
	public static void apply(WebDriver webDriver, WebElement webElement, int type, String msg) {
		//总开关：sys.DragonShow=false 时不画框也不弹窗，只保留日志
		if (!GDragonService.getInstance().isTipEnabled()) {
			if (msg != null && !msg.isEmpty()) {
				GLog.logRecordTime(9, "[提示]----" + msg);
			}
			return;
		}
		if (webDriver == null || webElement == null) {
			return;
		}
		switch (bHighLight) {
			case 1: {
				byWebElementBorder(webDriver, webElement, msg);
				break;
			}
			case 2: {
				byWebElementBackground(webDriver, webElement, msg);
				break;
			}
			case 3: {
				byWebElement(webDriver, webElement, msg);
				break;
			}
			default: {
				break;
			}
		}
	}
}
