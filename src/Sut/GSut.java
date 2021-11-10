package Sut;

import Base.GClazz;
import Sys.GSys;

/*
 * 被测件
 */
public class GSut {
	
    /**
     *  构造函数
     */
	private GSut(){
		GClazz.thisAToolClass();
	}
	
    /*
     * 协议类型
     */
	private static String SysName = "P0";

    /*
     * 子版本1
     */
	private static String SectionName1 = "S1";
	
    /*
     * 子版本2
     */
	private static String SectionName2 = "S2";
	
    /*
     * 子版本3
     */
	private static String SectionName3 = "S3";
    
    /*
     * 数据库服务名称
     */
	private static String Sid= "sid0";
    
	public static String getSysName() {
		return SysName;
	}

	public static void setSysName(String sysName) {
		SysName = sysName;
	}

	public static String getSectionName1() {
		return SectionName1;
	}

	public static void setSectionName1(String sectionName1) {
		SectionName1 = sectionName1;
	}

	public static String getSectionName2() {
		return SectionName2;
	}

	public static void setSectionName2(String sectionName2) {
		SectionName2 = sectionName2;
	}

	public static String getSectionName3() {
		return SectionName3;
	}

	public static void setSectionName3(String sectionName3) {
		SectionName3 = sectionName3;
	}

	public static String getSid() {
		return Sid;
	}

	public static void setSid(String sid) {
		Sid = sid;
	}

	/**
	 *  加载参数
	 */	
	public static void loadConfig() {
		if((!SysName.equals("")) 
				&& (!SectionName1.equals("")) 
				&& (!SectionName2.equals("")) 
				&& (!SectionName3.equals(""))
				&& (!Sid.equals(""))){
			;
		}else {
			GSys.logErrorSys("One of these sut params from sutConfig may has no value, Please check again!");
			System.exit(0);
		}
	}
}
