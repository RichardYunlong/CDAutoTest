package Sut;

import Base.GClazz;
import Base.GFile;
import Sys.GSys;

/*
 * 被测件
 */
public class GSut {
	
    /**
     *  构造函数
     */
	public GSut(){
		GClazz.thisAMainClass();
	}
	
    /*
     * 协议类型
     */
	private String SysName = "P0";
	public String getSysName() {
		return SysName;
	}
	public void setSysName(String sysName) {
		SysName = sysName;
	}

    /*
     * 子版本1
     */
	private String SectionName1 = "S1";
	public String getSectionName1() {
		return SectionName1;
	}
	public void setSectionName1(String sectionName1) {
		SectionName1 = sectionName1;
	}
	
    /*
     * 子版本2
     */
	private String SectionName2 = "S2";
	public String getSectionName2() {
		return SectionName2;
	}
	public void setSectionName2(String sectionName2) {
		SectionName2 = sectionName2;
	}

    /*
     * 子版本3
     */
	private String SectionName3 = "S3";
	public String getSectionName3() {
		return SectionName3;
	}
	public void setSectionName3(String sectionName3) {
		SectionName3 = sectionName3;
	}

    /*
     * 数据库服务名称
     */
	private String Sid= "sid0";
	public String getSid() {
		return Sid;
	}
	public void setSid(String sid) {
		Sid = sid;
	}

	/**
	 *  加载参数
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
			System.exit(0);
		}
	}
}
