package Sut;

import Base.GClazz;
import Base.GFile;
import Sys.GSys;

/**
 * 被测件（SUT，System Under Test）
 *
 * <p>描述一次测试所针对的产品版本信息，包含主版本、三个子版本与数据库服务名。
 * 这些取值来自 {@code config/sut.ini}，是日志归档、报告命名与数据库定位的基础参数。</p>
 *
 * <p>典型取值形如：主版本 {@code P0}、子版本 {@code S1}/{@code S2}/{@code S3}、服务名 {@code sid0}。</p>
 *
 * @see Sys.GSys
 */
public class GSut {
	
    /**
     *  构造函数
     */
	public GSut(){
		GClazz.thisAMainClass();
	}
	
    /**
     * 主版本名称
     */
	private String SysName = "P0";
	/**
	 * 获得主版本名称
	 *
	 * @return 主版本名称，如 {@code P0}
	 */
	public String getSysName() {
		return SysName;
	}
	/**
	 * 设置主版本名称
	 *
	 * @param sysName 主版本名称，不可为空
	 */
	public void setSysName(String sysName) {
		SysName = sysName;
	}

    /**
     * 子版本1名称
     */
	private String SectionName1 = "S1";
	/**
	 * 获得子版本1名称
	 *
	 * @return 子版本1名称，如 {@code S1}
	 */
	public String getSectionName1() {
		return SectionName1;
	}
	/**
	 * 设置子版本1名称
	 *
	 * @param sectionName1 子版本1名称，不可为空
	 */
	public void setSectionName1(String sectionName1) {
		SectionName1 = sectionName1;
	}
	
    /**
     * 子版本2名称
     */
	private String SectionName2 = "S2";
	/**
	 * 获得子版本2名称
	 *
	 * @return 子版本2名称，如 {@code S2}
	 */
	public String getSectionName2() {
		return SectionName2;
	}
	/**
	 * 设置子版本2名称
	 *
	 * @param sectionName2 子版本2名称，不可为空
	 */
	public void setSectionName2(String sectionName2) {
		SectionName2 = sectionName2;
	}

    /**
     * 子版本3名称
     */
	private String SectionName3 = "S3";
	/**
	 * 获得子版本3名称
	 *
	 * @return 子版本3名称，如 {@code S3}
	 */
	public String getSectionName3() {
		return SectionName3;
	}
	/**
	 * 设置子版本3名称
	 *
	 * @param sectionName3 子版本3名称，不可为空
	 */
	public void setSectionName3(String sectionName3) {
		SectionName3 = sectionName3;
	}

    /**
     * 数据库服务名称（Oracle SID）
     */
	private String Sid= "sid0";
	/**
	 * 获得数据库服务名称
	 *
	 * @return 数据库服务名称，如 {@code sid0}
	 */
	public String getSid() {
		return Sid;
	}
	/**
	 * 设置数据库服务名称
	 *
	 * @param sid 数据库服务名称，不可为空
	 */
	public void setSid(String sid) {
		Sid = sid;
	}

	/**
	 *  加载参数
	 *
	 *  <p>校验主版本、三个子版本与数据库服务名是否均已配置。</p>
	 *
	 *  @throws IllegalStateException 任一参数为空时抛出，提示检查 {@code sut.ini}
	 */	
	public void loadConfig() {
		if((!SysName.isEmpty())
				&& (!SectionName1.isEmpty())
				&& (!SectionName2.isEmpty())
				&& (!SectionName3.isEmpty())
				&& (!Sid.isEmpty())){
			GFile.writeStringToGuideBottom("GSut Params Loaded");
		}else {
			GFile.writeStringToBottom(GSys.GUIDE,"ERROR----" +"One of these sut params from sutConfig may has no value, Please check again!");
			throw new IllegalStateException("被测系统配置不完整，请检查 sut.ini");
		}
	}
}
