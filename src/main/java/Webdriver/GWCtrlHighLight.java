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
 *  <p>画框用 {@code box-shadow} 的扩散环，不改变元素尺寸与布局，避免影响后续点击坐标；
 *  圆角由 {@code border-radius} 提供，{@code box-shadow} 一律按元素圆角绘制，是圆倒角矩形。
 *  早先用 {@code outline}，它在部分浏览器上不跟随圆角，画出来仍是直角。</p>
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
	public static final String strBorder = "3";

	/**
	 *  边框颜色
	 */
	public static final String strBorderColor = "red";

	/**
	 *  圆角半径，单位像素
	 */
	public static final String strBorderRadius = "12";

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
			//参数必须显式传进自执行函数：写成 (function(){...})() 时，里面的 arguments 是
			//自执行函数自己的空参数表，e 会一直是 undefined，set() 里取 e.style 抛错后被
			//try 吞掉，整个画框静默失效——框不出现，日志里也查不到异常
			"(function(e, pairs, life){"
					+ "pairs = pairs || [];"
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
					+ "})(arguments[0], arguments[1], arguments[2]);";

	/**
	 *  换算元素屏幕矩形的脚本
	 *
	 *  <p>三段累加：元素自身矩形、逐层 iframe 偏移、浏览器窗口位置与工具栏高度。</p>
	 *
	 *  <p><b>iframe 必须逐层累加</b>：{@code getBoundingClientRect()} 返回的是元素所在文档
	 *  视口的坐标。登录页这类页面常把表单放进 iframe，直接当顶层坐标用会整体偏掉
	 *  iframe 在页面里的位置——表现就是红框准确画在输入框上，弹窗却停在页面偏上一大截处。</p>
	 *
	 *  <p>返回：{@code [x, y, w, h, 浏览器屏宽, 浏览器屏高, iframe 层数, 是否跨域]}。</p>
	 */
	/**
	 *  校正目标元素的脚本片段
	 *
	 *  <p><b>不可见元素必须换掉</b>：页面里常留着旧版表单、预渲染骨架，
	 *  它们 {@code visibility:hidden} 却照样占位，{@code getBoundingClientRect()}
	 *  返回的矩形完全正常；Selenium 按文档顺序取第一个，拿到的就是这种看不见的元素。
	 *  于是红框画在一个看不到的地方，弹窗指着页面中部，人却盯着右边的登录框。</p>
	 *
	 *  <p>这里判断可见性，必要时改用同 id 的第一个可见元素，并记录同名元素个数。</p>
	 *
	 *  <p>执行后可用变量：{@code target}（最终元素）、{@code hidden}（0/1）、{@code dup}（同名个数）。</p>
	 */
	private static final String VIS_JS =
			"var doc = el.ownerDocument;"
					+ "var win0 = doc.defaultView || window;"
					+ "function vis(e) {"
					+ "  try {"
					+ "    if (!e || !e.getClientRects || e.getClientRects().length === 0) { return false; }"
					+ "    var cs = win0.getComputedStyle(e);"
					+ "    if (!cs) { return true; }"
					+ "    if (cs.visibility === 'hidden' || cs.display === 'none') { return false; }"
					+ "    if (parseFloat(cs.opacity) === 0) { return false; }"
					+ "    return true;"
					+ "  } catch (err) { return true; }"
					+ "}"
					+ "var hidden = 0, dup = 1, target = el;"
					+ "if (!vis(el)) {"
					+ "  hidden = 1;"
					+ "  try {"
					+ "    var list = el.id ? doc.querySelectorAll('#' + el.id) : [];"
					+ "    dup = list.length;"
					+ "    for (var k = 0; k < list.length; k++) {"
					+ "      if (vis(list[k])) { target = list[k]; break; }"
					+ "    }"
					+ "  } catch (err) {}"
					+ "}";

	private static final String RECT_SCRIPT =
			"var el = arguments[0];"
					+ VIS_JS
					+ "var r = target.getBoundingClientRect();"
					//不再逐层累加 iframe：打点标出的原点就是"元素所在视口"的屏幕位置，
					//它天然含 iframe 偏移；再累加一遍等于把偏移算两次
					+ "var left = r.left, top = r.top, depth = 0, cross = 0;"
					+ "try {"
					+ "  var win = el.ownerDocument.defaultView;"
					+ "  while (win && win.frameElement) {"
					+ "    win = win.parent;"
					+ "    depth++;"
					+ "    if (depth >= 10) { break; }"
					+ "  }"
					+ "} catch (err) { cross = 1; }"
					//必须是元素所在的那个窗口：驱动若已切进 iframe，window 就是 iframe 自己的，
					//再去够 window.top 又会撞上跨域，取到的视口尺寸会跟元素矩形不同源（实测 400x410）
					+ "var W = el.ownerDocument.defaultView || window;"
					+ "var barH = Math.max(W.outerHeight - W.innerHeight, 0);"
					+ "var barW = Math.max(W.outerWidth - W.innerWidth, 0);"
					+ "var maxedX = Math.abs(W.outerWidth - W.screen.availWidth) <= 12;"
					+ "var maxedY = Math.abs(W.outerHeight - W.screen.availHeight) <= 12;"
					+ "var originX = maxedX ? 0 : W.screenX;"
					+ "var originY = maxedY ? 0 : W.screenY;"
					+ "if (originX < 0 || originX + W.innerWidth > W.screen.width + 8) { originX = 0; }"
					+ "if (originY < 0 || originY + W.innerHeight > W.screen.height + 8) { originY = 0; }"
					+ "var sx = W.screenX, sy = W.screenY;"
					//地址进返回值：登录页与登录后的系统页可能页面缩放不同，浏览器窗口却一样大，
					//不带上它就会一直沿用登录页标定的换算比
					+ "var href = '';"
					+ "try { href = String(W.location.href).substring(0, 120); } catch (err) {}"
					+ "return [left, top, r.width, r.height, W.screen.width, W.screen.height, depth, cross,"
					+ " sx, sy, originX + barW / 2, originY + barH, (maxedX && maxedY) ? 1 : 0, barH,"
					+ " hidden, dup, W.innerWidth, W.innerHeight, href];";

	/**
	 *  取元素指纹的脚本
	 *
	 *  <p>返回形如 {@code input#loginname[name=loginname][type=text].el-input__inner} 的短描述，
	 *  用于核对弹窗指的到底是不是预期元素：页面里常有一模一样的隐藏元素、外层容器、
	 *  旧版残留表单，光看位置分不清指到了哪个。</p>
	 *
	 *  <p>不取 {@code value}：密码框里就是密码，不能显示出来；输入框靠 id/name/type 区分即可。</p>
	 */
	private static final String DESC_SCRIPT =
			"var el = arguments[0];"
					+ "if (!el) { return ''; }"
					+ VIS_JS
					+ "var e = target;"
					+ "var s = hidden === 1 ? '隐藏·' : '';"
					+ "s += e.tagName ? e.tagName.toLowerCase() : 'node';"
					+ "if (e.id) { s += '#' + e.id; }"
					+ "var n = e.getAttribute('name'); if (n) { s += '[name=' + n + ']'; }"
					+ "var t = e.getAttribute('type'); if (t) { s += '[type=' + t + ']'; }"
					+ "var p = e.getAttribute('placeholder'); if (p) { s += '[placeholder=' + p + ']'; }"
					+ "var c = e.className;"
					+ "if (c && typeof c === 'string' && c.trim()) {"
					+ "  s += '.' + c.trim().split(/\\s+/).slice(0, 2).join('.');"
					+ "}"
					+ "var txt = (e.innerText || e.textContent || '').replace(/\\s+/g, ' ').trim();"
					+ "if (txt && !e.value) { s += ' \"' + txt.substring(0, 16) + '\"'; }"
					+ "return s.substring(0, 70);";

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
		paint(webDriver, webElement, borderStyle(), msg);
	}

	/**
	 *  拼出强调框的样式
	 *
	 *  <p>三层同宽同色叠在一起，落在同一圈上，看起来就是一个框：</p>
	 *  <ul>
	 *      <li>{@code border}——最基础的绘制方式，一定会出来，作为最终保障；</li>
	 *      <li>{@code box-shadow} 扩散环——必定按 {@code border-radius} 绘制，圆角由它保证；</li>
	 *      <li>{@code outline}——不占布局，元素边框被框架重置时仍能显示。</li>
	 *  </ul>
	 *
	 *  <p>{@code box-sizing: border-box} 是必须的：不然 6 像素边框会把元素撑大，
	 *  框的位置和点击坐标都会偏。{@code outline-offset} 与扩散值都归到边框宽度，
	 *  三条边正好重合，不会出现一框套一框。</p>
	 *
	 *  @return 样式数组，按“属性名、属性值”成对排列
	 */
	private static String[] borderStyle() {
		return new String[]{"border-width", strBorder + "px", "border-style", "solid",
				"border-color", strBorderColor, "box-sizing", "border-box",
				"outline", strBorder + "px solid " + strBorderColor, "outline-offset", "0px",
				"box-shadow", "0 0 0 " + strBorder + "px " + strBorderColor,
				"border-radius", strBorderRadius + "px"};
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
		String[] border = borderStyle();
		String[] style = new String[border.length + 2];
		style[0] = "background";
		style[1] = strColor;
		System.arraycopy(border, 0, style, 2, border.length);
		paint(webDriver, webElement, style, msg);
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
		String desc = describe(webDriver, webElement);
		//默认文案追加元素指纹：弹窗指的到底是不是你以为的那个元素，屏幕上直接能看见。
		//拆成短行：气泡宽度由最长行决定，一整条 60 多字符会把窗口从 444 撑到 760
		String custom = msg == null ? null : msg.trim();
		boolean byDefault = custom == null || custom.isEmpty() || strDefaultTip.equals(custom);
		String text = byDefault ? withDesc(desc) : custom;
		if (!desc.isEmpty()) {
			GLog.logRecordTime(9, "[目标]" + desc.replace("\n", " "));
		}
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
	/**
	 *  把元素指纹拼成多行文案
	 *
	 *  <p>第一行标签名与 id，第二行属性，各自截断。气泡宽度只取决于最长的一行，
	 *  拆行之后窗口宽度基本不变，只是在竖直方向多一两行。</p>
	 *
	 *  @param desc 元素指纹，形如 {@code input#loginname[name=loginname][type=text]}
	 *  @return 完整提示文案
	 */
	private static String withDesc(String desc) {
		if (desc == null || desc.isEmpty()) {
			return strDefaultTip;
		}
		//按 [ 与 . 切成短段，一段一行：气泡宽度由最长行决定，
		//挤成两行会把窗口从 444 撑到 530 以上，多行短句反而保持原宽度
		String[] parts = desc.split("(?=\\[)|(?=\\.)");
		StringBuilder sb = new StringBuilder(strDefaultTip);
		int lines = 0;
		for (String part : parts) {
			if (part.isEmpty()) {
				continue;
			}
			if (lines >= 3) {
				break;
			}
			sb.append('\n').append(part.length() > 18 ? part.substring(0, 18) : part);
			lines++;
		}
		return sb.toString();
	}

	/**
	 *  取元素指纹：标签名、id、name、type、占位符、前两个类名与文本
	 *
	 *  @param webDriver 全局驱动
	 *  @param webElement 目标元素
	 *  @return 短描述；取不到时返回空串
	 */
	private static String describe(WebDriver webDriver, WebElement webElement) {
		try {
			Object result = ((JavascriptExecutor) webDriver).executeScript(DESC_SCRIPT, webElement);
			if (result instanceof String) {
				return ((String) result).trim();
			}
		} catch (Exception e) {
			//取不到描述不能影响提示
		}
		return "";
	}

	/**
	 *  打点标定用的脚本
	 *
	 *  <p>在元素左上与右下各打一个 4×4 的小色块（洋红、青），坐标用 {@code position:fixed}
	 *  放到顶层文档，与 {@code getBoundingClientRect()} 同一坐标系。</p>
	 *
	 *  <p>返回：{@code [left, top, width, height, iframe 层数]}，均为 CSS 像素。</p>
	 */
	private static final String PROBE_SCRIPT =
			"var el = arguments[0];"
					+ "var r = el.getBoundingClientRect();"
					//色块画在元素所在文档里，坐标就用该文档的视口坐标，与 rect 同源
					+ "var left = r.left, top = r.top;"
					//用元素自己的文档：window.top.document 在跨域 iframe 里会直接抛异常，
					//整段脚本失败后标定就静默退回旧算法，表现为"改了却没生效"
					+ "var D = el.ownerDocument;"
					+ "var old = D.getElementById('__cdProbeMark');"
					+ "if (old) { old.parentNode.removeChild(old); }"
					+ "var host = D.body || D.documentElement;"
					+ "function mark(color, x, y) {"
					+ "  var m = D.createElement('div');"
					+ "  m.id = '__cdProbeMark';"
					+ "  m.style.cssText = 'position:fixed;margin:0;padding:0;border:0;width:4px;height:4px;"
					+ "z-index:2147483647;pointer-events:none;background:' + color + ';"
					+ "left:' + Math.round(x) + 'px;top:' + Math.round(y) + 'px;';"
					+ "  host.appendChild(m);"
					+ "  void m.offsetHeight;"
					+ "}"
					+ "mark('#FF00FF', left, top);"
					+ "mark('#00FFFF', left + r.width, top + r.height);"
					+ "return [left, top, r.width, r.height];";

	/**
	 *  给元素临时上色的脚本
	 *
	 *  <p>染的是<b>背景色</b>：红框占着 {@code outline} 与 {@code box-shadow}，两者都不能动
	 *  ——画框脚本每 250 毫秒会把它们重设一遍，染色层设上去立刻被冲掉。</p>
	 *
	 *  <p><b>必须染元素本身</b>：另画一个 {@code position:fixed} 色块来定位不可靠——页面上
	 *  任何祖先只要带 {@code transform}，fixed 就改成相对那个祖先定位，色块会画到别处，
	 *  标定出的原点随之全错（实测登录后原点偏到 1262/424，换算比却是对的）。</p>
	 *
	 *  <p>返回 {@code [left, top, width, height]}，均为 CSS 像素。</p>
	 */
	private static final String MARK_ON =
			"var el = arguments[0];"
					+ "if (!el || !el.style) { return null; }"
					+ "el.setAttribute('data-cd-bg', el.style.getPropertyValue('background-color') || '');"
					+ "el.setAttribute('data-cd-bgi', el.style.getPropertyValue('background-image') || '');"
					+ "el.style.setProperty('background-color', '#FF00FF', 'important');"
					+ "el.style.setProperty('background-image', 'none', 'important');"
					+ "void el.offsetHeight;"
					+ "var r = el.getBoundingClientRect();"
					+ "return [r.left, r.top, r.width, r.height];";

	/**
	 *  还原元素原有背景
	 */
	private static final String MARK_OFF =
			"var el = arguments[0];"
					+ "if (!el || !el.style) { return ''; }"
					+ "var bg = el.getAttribute('data-cd-bg');"
					+ "if (bg) { el.style.setProperty('background-color', bg, 'important'); }"
					+ "else { el.style.removeProperty('background-color'); }"
					+ "var bgi = el.getAttribute('data-cd-bgi');"
					+ "if (bgi) { el.style.setProperty('background-image', bgi, 'important'); }"
					+ "else { el.style.removeProperty('background-image'); }"
					+ "el.removeAttribute('data-cd-bg');"
					+ "el.removeAttribute('data-cd-bgi');"
					+ "return '';";

	/**
	 *  视口换算：CSS 像素到物理像素的比，以及视口原点在屏幕上的位置
	 */
	private static final class View {

		private final double scaleX;

		private final double scaleY;

		private final int x;

		private final int y;

		private View(double scaleX, double scaleY, int x, int y) {
			this.scaleX = scaleX;
			this.scaleY = scaleY;
			this.x = x;
			this.y = y;
		}
	}

	/**
	 *  缓存的视口信息
	 */
	private static volatile View cachedView;

	/**
	 *  视口信息的缓存键：窗口尺寸或位置变化就重算
	 */
	private static volatile String cachedViewKey;

	/**
	 *  用屏幕打点标定视口换算
	 *
	 *  <p>在元素左上与右下各打一个色块，截屏找到它们在屏幕上的位置。两点像素距离除以元素的
	 *  CSS 宽高，就是真正要乘的比；再由左上的落点反推视口原点。</p>
	 *
	 *  <p>这套标定不含任何假设：DPI、页面缩放、工具栏高度、iframe 偏移全部被吸收进去。
	 *  而 {@code window.outerHeight}、{@code screenX} 在缩放环境下会给出互相矛盾的值
	 *  （实测出现顶栏 342 CSS、screenX 虚报 257），任何基于它们的推算都会整体偏移。</p>
	 *
	 *  @param webDriver 全局驱动
	 *  @param webElement 目标元素
	 *  @return 视口信息；标定失败时返回 {@code null}
	 */
	private static View view(WebDriver webDriver, WebElement webElement, double innerWidth, String href) {
		try {
			org.openqa.selenium.Dimension size = webDriver.manage().window().getSize();
			org.openqa.selenium.Point position = webDriver.manage().window().getPosition();
			//视口宽度与页面地址一起进缓存键：换页面时页面缩放可能不同，浏览器窗口却没变，
			//只按窗口判定会一直沿用上一页的换算比
			String key = size.width + "x" + size.height + "@" + position.x + "," + position.y
					+ "/" + Math.round(innerWidth) + "/" + href;
			View cached = cachedView;
			if (cached != null && key.equals(cachedViewKey)) {
				return cached;
			}
			Object result = ((JavascriptExecutor) webDriver).executeScript(PROBE_SCRIPT, webElement);
			if (!(result instanceof List) || ((List<?>) result).size() < 4) {
				probeNote("打点脚本没返回坐标");
				return null;
			}
			List<?> values = (List<?>) result;
			double left = d(values, 0);
			double top = d(values, 1);
			double widthCss = d(values, 2);
			double heightCss = d(values, 3);
			//等浏览器把色块合成到屏幕上再截屏；iframe 里的新元素还要多一次合成，
			//第一次抓不到就再等一拍重抓，否则只能退回推算
			java.awt.Point[] marks = null;
			for (int attempt = 0; attempt < 3 && marks == null; attempt++) {
				Thread.sleep(120);
				marks = findMarks();
			}
			java.awt.Point first = marks == null ? null : marks[0];
			java.awt.Point second = marks == null ? null : marks[1];
			clearMark(webDriver);
			if (first == null || second == null || widthCss < 8 || heightCss < 8) {
				probeNote("屏幕截屏里没找到标记点(洋红=" + (first != null) + " 青=" + (second != null) + ")");
				return null;
			}
			double scaleX = (second.x - first.x) / widthCss;
			double scaleY = (second.y - first.y) / heightCss;
			if (scaleX <= 0.2 || scaleY <= 0.2 || scaleX > 8 || scaleY > 8) {
				probeNote("标记点比例异常(" + scaleX + "," + scaleY + ")");
				return null;
			}
			View fresh = new View(scaleX, scaleY,
					(int) Math.round(first.x - left * scaleX),
					(int) Math.round(first.y - top * scaleY));
			cachedView = fresh;
			cachedViewKey = key;
			return fresh;
		} catch (Throwable t) {
			//标定失败时退回脚本自报的窗口位置；原因只记一次，避免每次复查都刷屏
			probeNote(t.getClass().getSimpleName() + ":" + t.getMessage());
			return null;
		}
	}

	/**
	 *  记一条标定失败原因
	 *
	 *  <p>打点是唯一能拿到真实屏幕位置的手段，它一失败就退回推算，位置必然偏。
	 *  原因记下来，下次一眼能看出是该换策略还是环境问题。</p>
	 *
	 *  @param reason 原因
	 */
	private static void probeNote(String reason) {
		if (probeNoted) {
			return;
		}
		probeNoted = true;
		GLog.logRecordTime(9, "[标定]打点失败,本次改用脚本推算:" + reason);
	}

	/**
	 *  标定失败原因是否已记过
	 */
	private static volatile boolean probeNoted;

	/**
	 *  全屏截屏里找两个标记点
	 *
	 *  @return {@code [洋红, 青]}；任一找不到时返回 {@code null}
	 */
	private static java.awt.Point[] findMarks() {
		try {
			java.awt.Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
			java.awt.image.BufferedImage image = new java.awt.Robot().createScreenCapture(
					new Rectangle(0, 0, screen.width, screen.height));
			java.awt.Point first = null;
			java.awt.Point second = null;
			for (int y = 0; y < image.getHeight(); y++) {
				for (int x = 0; x < image.getWidth(); x++) {
					int rgb = image.getRGB(x, y) & 0xFFFFFF;
					//容差匹配：系统色彩管理会把纯色渲染成差一两个的值
					if (near(rgb, 0xFF00FF)) {
						first = first == null ? new java.awt.Point(x, y)
								: new java.awt.Point(Math.min(first.x, x), Math.min(first.y, y));
					} else if (near(rgb, 0x00FFFF)) {
						second = second == null ? new java.awt.Point(x, y)
								: new java.awt.Point(Math.min(second.x, x), Math.min(second.y, y));
					}
				}
			}
			if (first == null || second == null) {
				dump(image);
				return null;
			}
			return new java.awt.Point[] {first, second};
		} catch (Throwable t) {
			return null;
		}
	}

	/**
	 *  直接量出元素在屏幕上的矩形
	 *
	 *  <p>给元素染上洋红内阴影后截屏，洋红像素的包围盒就是它的屏幕位置。这条路径
	 *  不需要换算比、不需要窗口原点、不受 DPI/页面缩放/iframe/transform 影响，
	 *  是唯一不依赖任何假设的测量方式。</p>
	 *
	 *  @param webDriver 全局驱动
	 *  @param webElement 目标元素
	 *  @param css 元素矩形（CSS 像素），用于校验测到的是不是它
	 *  @return 屏幕矩形；测不到时返回 {@code null}
	 */
	private static Rectangle measure(WebDriver webDriver, WebElement webElement, double[] css) {
		try {
			Object result = ((JavascriptExecutor) webDriver).executeScript(MARK_ON, webElement);
			if (!(result instanceof List) || ((List<?>) result).size() < 4) {
				return null;
			}
			java.awt.Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
			Rectangle area = new Rectangle(0, 0, screen.width, screen.height);
			java.awt.image.BufferedImage image;
			int step = 6;
			int minX = -1;
			int minY = 0;
			int maxX = 0;
			int maxY = 0;
			//等染色上屏后截一张，然后立刻还原样式：
			//像素分析要几百毫秒，拖到分析完再还原，元素会一直保持粉色
			Thread.sleep(90);
			image = new java.awt.Robot().createScreenCapture(area);
			((JavascriptExecutor) webDriver).executeScript(MARK_OFF, webElement);
			for (int y = 0; y < image.getHeight(); y += step) {
				for (int x = 0; x < image.getWidth(); x += step) {
					if (!near(image.getRGB(x, y) & 0xFFFFFF, 0xFF00FF)) {
						continue;
					}
					if (minX < 0) {
						minX = x;
						minY = y;
						maxX = x;
						maxY = y;
					} else {
						minX = Math.min(minX, x);
						minY = Math.min(minY, y);
						maxX = Math.max(maxX, x);
						maxY = Math.max(maxY, y);
					}
				}
			}
			if (minX < 0) {
				dump(image);
				probeNote("屏幕上没找到染色后的元素");
				return null;
			}
			//粗扫漏掉了边角，先外扩一个步长，再在框内逐点扫出精确边界
			int fromX = Math.max(0, minX - step);
			int fromY = Math.max(0, minY - step);
			int toX = Math.min(image.getWidth() - 1, maxX + step);
			int toY = Math.min(image.getHeight() - 1, maxY + step);
			minX = -1;
			for (int y = fromY; y <= toY; y++) {
				for (int x = fromX; x <= toX; x++) {
					if (!near(image.getRGB(x, y) & 0xFFFFFF, 0xFF00FF)) {
						continue;
					}
					if (minX < 0) {
						minX = x;
						minY = y;
						maxX = x;
						maxY = y;
					} else {
						minX = Math.min(minX, x);
						minY = Math.min(minY, y);
						maxX = Math.max(maxX, x);
						maxY = Math.max(maxY, y);
					}
				}
			}
			if (minX < 0) {
				return null;
			}
			int width = maxX - minX + 1;
			int height = maxY - minY + 1;
			//比例校验：页面里若有别的洋红元素，包围盒会被拉大，比例也会对不上
			if (css[1] > 0 && Math.abs((double) width / height - css[0] / css[1]) > 0.35) {
				probeNote("染色区域比例不符(" + width + "x" + height + ")");
				return null;
			}
			//内阴影画在边框以内，四周各少约 1 像素，补回来才与元素的实际边界重合
			return new Rectangle(minX - 1, minY - 1, width + 2, height + 2);
		} catch (Throwable t) {
			probeNote(t.getClass().getSimpleName() + ":" + t.getMessage());
			try {
				((JavascriptExecutor) webDriver).executeScript(MARK_OFF, webElement);
			} catch (Throwable ignored) {
				//还原失败只影响元素外观
			}
			return null;
		}
	}

	/**
	 *  取字符串末尾若干字符
	 *
	 *  <p>地址前面的域名都一样，能区分页面的是末尾那段路由。</p>
	 *
	 *  @param text 原串
	 *  @param max 最多保留的字符数
	 *  @return 截断后的文本
	 */
	private static String tail(String text, int max) {
		if (text == null) {
			return "";
		}
		return text.length() <= max ? text : "…" + text.substring(text.length() - max);
	}

	/**
	 *  记录一次元素屏幕矩形
	 *
	 *  <p>坐标没动就不重复记：显示期间每 300 毫秒复查一次，每次都写会把日志刷满。</p>
	 *
	 *  @param rect 屏幕矩形
	 *  @param values 脚本返回值
	 *  @param href 页面地址
	 *  @param from 来源说明
	 */
	private static void logRect(Rectangle rect, List<?> values, Object href, String from) {
		Rectangle last = loggedRect;
		if (last != null && Math.abs(last.x - rect.x) <= 4 && Math.abs(last.y - rect.y) <= 4
				&& Math.abs(last.width - rect.width) <= 4
				&& Math.abs(last.height - rect.height) <= 4) {
			return;
		}
		loggedRect = rect;
		GLog.logRecordTime(9, "[指向]元素屏幕矩形=" + rect + " 来源=" + from
				+ " 元素CSS=" + (int) d(values, 0) + "/" + (int) d(values, 1)
				+ " 视口CSS=" + (int) d(values, 16) + "x" + (int) d(values, 17)
				+ " iframe=" + (int) d(values, 6)
				+ " 可见=" + (((Number) values.get(14)).intValue() == 0 ? 1 : 0)
				+ " 同名=" + num(values, 15)
				+ " 页面=" + tail(href instanceof String ? (String) href : "", 42));
	}

	/**
	 *  判断截屏里的颜色是否就是标记色
	 *
	 *  @param rgb 截图像素
	 *  @param target 标记色
	 *  @return 三通道各自相差不超过 24 时为真
	 */
	private static boolean near(int rgb, int target) {
		return Math.abs(((rgb >> 16) & 255) - ((target >> 16) & 255)) <= 24
				&& Math.abs(((rgb >> 8) & 255) - ((target >> 8) & 255)) <= 24
				&& Math.abs((rgb & 255) - (target & 255)) <= 24;
	}

	/**
	 *  留一张找不到标记时的截屏
	 *
	 *  <p>只在第一次失败时写，用来判断色块到底画没画出来、画到了哪里。</p>
	 *
	 *  @param image 全屏截图
	 */
	private static void dump(java.awt.image.BufferedImage image) {
		if (probeDumped) {
			return;
		}
		probeDumped = true;
		try {
			javax.imageio.ImageIO.write(image, "png", new java.io.File("logs/probe.png"));
		} catch (Throwable ignored) {
			//写不进就算了，不影响提示
		}
	}

	/**
	 *  是否已留过诊断截图
	 */
	private static volatile boolean probeDumped;

	/**
	 *  上次实测过的元素
	 */
	private static volatile WebElement baseElement;

	/**
	 *  上次实测得到的屏幕矩形
	 */
	private static volatile Rectangle baseRect;

	/**
	 *  实测时元素的左上角，用于按位移换算同元素的后续位置
	 */
	private static volatile double baseLeft;

	/**
	 *  实测时元素的顶边
	 */
	private static volatile double baseTop;

	/**
	 *  实测得到的横向换算比
	 */
	private static volatile double baseScaleX;

	/**
	 *  实测得到的纵向换算比
	 */
	private static volatile double baseScaleY;

	/**
	 *  实测时的页面地址，换页后缓存作废
	 */
	private static volatile String baseHref = "";

	/**
	 *  当前元素是否已尝试过实测，失败也不再重试
	 */
	private static volatile boolean baseTried;

	/**
	 *  清掉打点元素
	 *
	 *  @param webDriver 全局驱动
	 */
	private static void clearMark(WebDriver webDriver) {
		try {
			((JavascriptExecutor) webDriver).executeScript(
					"var d = document;"
							+ "var m = d.getElementById('__cdProbeMark');"
							+ "while (m) { m.parentNode.removeChild(m); "
							+ "m = d.getElementById('__cdProbeMark'); }");
		} catch (Throwable ignored) {
			//残留的 4 像素色块不影响功能
		}
	}

	private static Rectangle screenRect(WebDriver webDriver, WebElement webElement) {
		try {
			Object result = ((JavascriptExecutor) webDriver).executeScript(RECT_SCRIPT, webElement);
			if (!(result instanceof List) || ((List<?>) result).size() < 19) {
				return null;
			}
			List<?> values = (List<?>) result;
			java.awt.Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
			Object href = values.get(18);
			double cssLeft = d(values, 0);
			double cssTop = d(values, 1);
			double cssWidth = d(values, 2);
			double cssHeight = d(values, 3);
			//同一个元素只染色量一次：复查是每 300 毫秒一次，每次都染色会让框持续闪，
			//截图也容易正好落在染色窗口里，看起来像"框一直是粉的"
			String page = href instanceof String ? (String) href : "";
			//换页面后不能沿用：元素 id 可能撞上，两次的位置其实毫无关系
			boolean sameTarget = page.equals(baseHref) && webElement.equals(baseElement) && cssWidth > 0;
			Rectangle base = baseRect;
			if (sameTarget && base != null && baseScaleX > 0.05) {
				Rectangle moved = new Rectangle(
						(int) Math.round(base.x + (cssLeft - baseLeft) * baseScaleX),
						(int) Math.round(base.y + (cssTop - baseTop) * baseScaleY),
						(int) Math.round(cssWidth * baseScaleX),
						(int) Math.round(cssHeight * baseScaleY));
				logRect(moved, values, href, "实测(沿用)");
				return moved;
			}
			//同一个元素只染色量一次：显示期间每 300 毫秒复查一次，每次都染色会让元素一直泛粉
			Rectangle measured = null;
			if (!(sameTarget && baseTried)) {
				measured = measure(webDriver, webElement, new double[] {cssWidth, cssHeight});
				baseTried = true;
				baseElement = webElement;
				baseHref = page;
			}
			if (measured != null) {
				baseRect = measured;
				baseLeft = cssLeft;
				baseTop = cssTop;
				baseScaleX = measured.width / Math.max(cssWidth, 1);
				baseScaleY = measured.height / Math.max(cssHeight, 1);
				logRect(measured, values, href, "实测");
				return measured;
			}
			View view = view(webDriver, webElement, d(values, 16),
					href instanceof String ? (String) href : "");
			double scaleX;
			double scaleY;
			int originX;
			int originY;
			String from;
			if (view != null) {
				scaleX = view.scaleX;
				scaleY = view.scaleY;
				originX = view.x;
				originY = view.y;
				from = "打点";
			} else {
				//标定失败时退回脚本自报的窗口位置，比不换算好
				scaleX = scale(screen.width, d(values, 4));
				scaleY = scaleX;
				originX = (int) Math.round(d(values, 10) * scaleX);
				originY = (int) Math.round(d(values, 11) * scaleY);
				from = "脚本";
			}
			int x = (int) Math.round(d(values, 0) * scaleX) + originX;
			int y = (int) Math.round(d(values, 1) * scaleY) + originY;
			int width = (int) Math.round(d(values, 2) * scaleX);
			int height = (int) Math.round(d(values, 3) * scaleY);
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
			//宽高也参与判重：同一位置命中不同大小的元素（顶部搜索框与列表内搜索框宽度不同），
			//只看坐标会把这种替换漏掉，日志里就分不清登录后指的到底是哪一个
			if (last == null || Math.abs(last.x - x) > 4 || Math.abs(last.y - y) > 4
					|| Math.abs(last.width - width) > 4 || Math.abs(last.height - height) > 4) {
				loggedRect = rect;
				GLog.logRecordTime(9, "[指向]元素屏幕矩形=" + rect
						+ " 换算=" + String.format("%.3f", scaleX) + "x" + String.format("%.3f", scaleY)
						+ "(" + from + ")"
						+ " 原点=" + originX + "/" + originY
						+ " Java屏幕=" + screen.width + "x" + screen.height
						+ " 视口CSS=" + (int) d(values, 16) + "x" + (int) d(values, 17)
						+ " iframe=" + (int) d(values, 6)
						+ (((Number) values.get(7)).intValue() == 1 ? "(跨域,只累加到能访问的那层)" : "")
						+ " 元素CSS=" + (int) d(values, 0) + "/" + (int) d(values, 1)
						+ " 可见=" + (((Number) values.get(14)).intValue() == 0 ? 1 : 0)
						+ " 同名=" + num(values, 15)
						//页面地址：登录前后是两张页面，缩放与结构都可能不同，
						//标定按地址重新做，日志里也要能看出来现在算的是哪一页
						+ " 页面=" + tail(href instanceof String ? (String) href : "", 42));
			}
			return rect;
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 *  取脚本返回数组里某一项的小数值
	 *
	 *  @param values 脚本返回值
	 *  @param index 下标
	 *  @return 数值，缺失时为 0
	 */
	private static double d(List<?> values, int index) {
		Object value = values.get(index);
		return value instanceof Number ? ((Number) value).doubleValue() : 0;
	}

	/**
	 *  取脚本返回数组里某一项的整数值
	 *
	 *  @param values 脚本返回值
	 *  @param index 下标
	 *  @return 文本形式，缺失时返回问号
	 */
	private static String num(List<?> values, int index) {
		Object value = values.get(index);
		return value instanceof Number ? String.valueOf(((Number) value).intValue()) : "?";
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
