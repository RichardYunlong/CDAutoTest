/**
 * Base包包含各种基础工具类，用于支持自动化测试框架的核心功能。
 * 这些工具类包括：
 * <ul>
 *   <li>文件与目录操作（{@link Base.GFile}）</li>
 *   <li>文本处理与字符集转换（{@link Base.GText}）</li>
 *   <li>时间日期处理（{@link Base.GTime}）</li>
 *   <li>断言与校验（{@link Base.GAssert}）</li>
 *   <li>日志与消息文本（{@link Base.GMsg}、{@link Base.GMissionMsg}）</li>
 *   <li>JSON 序列化配置（{@link Base.GJsonObjectMapper}）</li>
 *   <li>压缩解压（{@link Base.GZip}）</li>
 *   <li>文件下载（{@link Base.GDownload}）</li>
 *   <li>进程、命令行与远程执行（{@link Base.GProcess}、{@link Base.GShell}、{@link Base.GWinRM}）</li>
 *   <li>多线程与队列（{@link Base.GThread}、{@link Base.GMultiThread}、{@link Base.GQueue}）</li>
 *   <li>控制台彩色输出（{@link Base.GColoredText}）</li>
 *   <li>浏览器与驱动版本探测（{@link Base.GBrowser}）</li>
 * </ul>
 * 所有这些工具类都以G开头命名，表示"Global"或"General"，便于识别和使用。
 */
package Base;
