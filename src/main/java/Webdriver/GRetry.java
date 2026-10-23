package Webdriver;

import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

import java.util.Objects;

/**
 * 用例失败重试规则
 *
 * <p>一个 JUnit4 的 {@link TestRule} 实现，用于在被测页面偶发抖动（元素加载慢、
 * 网络延迟等）导致用例失败时自动重跑，从而降低误报率。</p>
 *
 * <p>使用方式：在测试类中声明一个被 {@code @Rule} 标注的本类实例。</p>
 * <pre>
 * &#64;Rule
 * public GRetry retry = new GRetry(3);
 * </pre>
 *
 * <p>重试语义：最多执行 {@code retryCount} 次；任意一次成功即视为通过；
 * 全部失败后抛出<strong>最后一次</strong>的异常。注意每次重试都会真正重新执行用例，
 * 因此带有副作用的用例（例如会新增数据）需要自行保证可重复执行。</p>
 */
public class GRetry implements TestRule {

    /**
     * 最大重试次数
     */
    @SuppressWarnings({"FieldMayBeFinal", "CanBeFinal"})
    private int retryCount;

    /**
     * 构造一个重试规则
     *
     * @param retryCount 最大执行次数，建议大于 0；传 0 或负数时用例一次都不会执行
     */
    public GRetry(int retryCount) {
        this.retryCount = retryCount;
    }

    /**
     * 用重试逻辑包装原始测试语句
     *
     * @param base 被包装的原始测试语句
     * @param description JUnit 对当前测试方法的描述，用于输出日志
     * @return 具备重试能力的测试语句
     */
    public Statement apply(Statement base, Description description) {
        return statement(base, description);
    }

    /**
     * 生成重试语句
     *
     * @param base 被包装的原始测试语句
     * @param description JUnit 对当前测试方法的描述
     * @return 具备重试能力的测试语句
     */
    private Statement statement(final Statement base, final Description description) {
        return new Statement() {
            /**
             * 执行测试语句，失败时按 {@link #retryCount} 重试
             *
             * @throws Throwable 全部重试均失败后抛出最后一次捕获的异常
             */
            @Override
            public void evaluate() throws Throwable {
                Throwable caughtThrowable = null;

                // implement retry logic here
                for (int i = 0; i < retryCount; i++) {
                    try {
                        base.evaluate();
                        return;
                    } catch (Throwable t) {
                        caughtThrowable = t;
                        System.err.println(description.getDisplayName() + ": run " + (i+1) + " failed");
                    }
                }
                System.err.println(description.getDisplayName() + ": giving up after " + retryCount + " failures");
                throw Objects.requireNonNull(caughtThrowable);
            }
        };
    }
}
