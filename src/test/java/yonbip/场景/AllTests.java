package yonbip.场景;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

/**
 *  用例聚合入口
 *
 *  surefire 只执行本类(pom 中 includes = **&#47;AllTests.java)，
 *  因此新增用例类时必须同步追加到这里，否则该用例不会被执行。
 *  AutoGenTestJson 是数据生成工具类，不含 @Test，不要加入。
 */
@RunWith(Suite.class)
@SuiteClasses({
        yonbip.登陆.testcase_签入.class,
        yonbip.登陆.testcase_签出.class,
        yonbip.个人设置.testcase_切换租户.class,
        yonbip.工作台.数字化建模.组织管理.组织机构.业务单元.class,
        yonbip.工作台.数字化建模.权限管理.授权.授权.class,
        yonbip.工作台.数字化建模.权限管理.角色管理.角色管理.class,
        yonbip.工作台.云平台.友云测.测试服务.执行队列.class,
        yonbip.工作台.财务云.事项会计中台.财务公共.科目中心.会计科目.class,
        yonbip.工作台.财务云.事项会计中台.财务公共.科目中心.科目表.class,
        yonbip.工作台.财务云.事项会计中台.财务公共.财务基础档案.凭证类型.class,
        yonbip.工作台.财务云.事项会计中台.财务公共.账簿中心.账簿设置.class,
        yonbip.工作台.财务云.财务会计.总账.凭证.凭证.class
})
public class AllTests {

}
