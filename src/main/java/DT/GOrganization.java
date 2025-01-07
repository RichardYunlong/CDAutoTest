package main.java.DT;

import main.java.Base.GClazz;

/**
 *  机构
 */
public class GOrganization {
	
    /**
     *  构造函数
     */
	private GOrganization(){
		GClazz.thisADataUnitClass();
	}
	
	/**
	 *  父机构
	 */
	public static final String[][] gOrg = {{"OrgName1", "OrgCode1"}, {"OrgName2", "OrgCode2"}};
	
	/**
	 *  一级子机构
	 */
	public static final String[][] gChildOrg1 = {{"OrgCode1", "OrgName1-1", "OrgCode1-1"}, {"OrgCode1", "OrgName1-2", "OrgCode1-2"}};
}
