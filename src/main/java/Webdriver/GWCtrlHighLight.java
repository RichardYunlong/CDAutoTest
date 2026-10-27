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
		paint(webDriver, webElement, "outline: " + strBorder + "px solid " + strBorderColor + ";"
				+ "outline-offset: 2px;border-radius: " + strBorderRadius + "px;", msg);
	}

	/**
	 *  通过指定元素的WebElement对象来添加高亮背景
	 *
	 *  @param webDriver 全局驱动
	 *  @param webElement 目标元素
	 *  @param msg 提示语，为空时使用默认提示语
	 */
	private static void byWebElementBackground(WebDriver webDriver, WebElement webElement, String msg) {
		paint(webDriver, webElement, "background: " + strColor + ";", msg);
	}

	/**
	 *  通过指定元素的WebElement对象来添加高亮背景加边框
	 *
	 *  @param webDriver 全局驱动
	 *  @param webElement 目标元素
	 *  @param msg 提示语，为空时使用默认提示语
	 */
	private static void byWebElement(WebDriver webDriver, WebElement webElement, String msg) {
		paint(webDriver, webElement, "background: " + strColor + ";"
				+ "outline: " + strBorder + "px solid " + strBorderColor + ";"
				+ "outline-offset: 2px;border-radius: " + strBorderRadius + "px;", msg);
	}

	/**
	 *  在目标元素上绘制强调样式，并弹出指向该元素的人形弹窗
	 *
	 *  @param webDriver 全局驱动
	 *  @param webElement 目标元素
	 *  @param style 追加的 CSS 样式
	 *  @param msg 提示语，为空时使用默认提示语
	 */
	private static void paint(WebDriver webDriver, WebElement webElement, String style, String msg) {
		if (webDriver == null || webElement == null) {
			return;
		}
		//操作密集时缩短框的存活时间，避免满屏红框叠加
		long life = GDragonGuide.getInstance().isBusy()
				? Math.min(GTestIndicators.DragonShowTime, 600)
				: GTestIndicators.DragonShowTime;
		try {
			JavascriptExecutor js = (JavascriptExecutor) webDriver;
			//同一时刻只保留一个红框：画新框前先把上一个元素恢复原样
			js.executeScript(
					"(function(){"
							+ "var e = arguments[0], style = arguments[1], life = arguments[2];"
							+ "if (window.__cdMark && window.__cdMark.e && window.__cdMark.e !== e) {"
							+ "  try { window.__cdMark.e.setAttribute('style', window.__cdMark.s || ''); }"
							+ "  catch (err) {}"
							+ "}"
							+ "if (e.__cdStyle === undefined) { e.__cdStyle = e.getAttribute('style') || ''; }"
							+ "e.setAttribute('style', e.__cdStyle + ';' + style);"
							+ "window.__cdMark = { e: e, s: e.__cdStyle };"
							+ "if (window.__cdTimer) { window.clearTimeout(window.__cdTimer); }"
							+ "window.__cdTimer = window.setTimeout(function(){"
							+ "  try { e.setAttribute('style', e.__cdStyle); } catch (err) {}"
							+ "  if (window.__cdMark && window.__cdMark.e === e) { window.__cdMark = null; }"
							+ "}, life);"
							+ "})();",
					webElement, style, life);
		} catch (Exception e) {
			GLog.logSysFunctionException("GWCtrlHighLight.paint", e);
		}
		tip(webDriver, webElement, msg);
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
		GDragonGuide.getInstance().tip(text, GDragonAction.POINT,
				rect.x, rect.y, rect.width, rect.height, showTime);
	}

	/**
	 *  换算目标元素在屏幕上的矩形
	 *
	 *  <p>页面坐标（{@code getBoundingClientRect}）加上浏览器窗口位置与工具栏高度得到屏幕坐标，
	 *  与页面缩放无关；元素不在可视区域时返回 {@code null}。</p>
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
							+ " r.width, r.height];",
					webElement);
			if (!(result instanceof List) || ((List<?>) result).size() < 4) {
				return null;
			}
			List<?> values = (List<?>) result;
			int x = (int) Math.round(((Number) values.get(0)).doubleValue());
			int y = (int) Math.round(((Number) values.get(1)).doubleValue());
			int width = (int) Math.round(((Number) values.get(2)).doubleValue());
			int height = (int) Math.round(((Number) values.get(3)).doubleValue());
			if (width <= 0 || height <= 0) {
				return null;
			}
			java.awt.Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
			if (x + width <= 0 || y + height <= 0 || x >= screen.width || y >= screen.height) {
				return null;
			}
			return new Rectangle(x, y, width, height);
		} catch (Exception e) {
			return null;
		}
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
