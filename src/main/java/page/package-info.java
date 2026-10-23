/**
 * page 包是页面对象模型（Page Object Model，POM）的根包。
 *
 * <p>它把被测系统的界面拆成"可复用的基础能力"与"具体页面"两层，脚本只与页面对象交互，
 * 不直接写 XPath，从而降低界面改动对用例的冲击。</p>
 *
 * <p>子包职责：</p>
 * <ul>
 *   <li>{@link page.base} —— 页面元素与页面对象的抽象基类，定义定位、等待、取值的统一入口；</li>
 *   <li>{@link page.baseused} —— 被各页面复用的数据结构（元素映射表、元素列表等）；</li>
 *   <li>{@link page.page} —— 具体页面对象，如登录页、首页、单据页；</li>
 *   <li>{@link page.widget} —— 跨页面复用的通用控件，如页签、分页、下拉、搜索框；</li>
 *   <li>{@link page.table} —— 表格类控件及其行、列、单元格操作；</li>
 *   <li>{@link page.page2window} —— 页面与多窗口（弹窗、新开标签页）之间的切换逻辑。</li>
 * </ul>
 */
package page;
