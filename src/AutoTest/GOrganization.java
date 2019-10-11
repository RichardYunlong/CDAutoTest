package AutoTest;

/**
 *  机构
 */
public class GOrganization {
	/**
	 *  父机构
	 */
	public static String[][] gOrg = {{"OrgName1", "OrgCode1"}, {"OrgName2", "OrgCode2"}};
	
	/**
	 *  一级子机构
	 */
	public static String[][] gChildOrg1 = {{"OrgCode1", "OrgName1-1", "OrgCode1-1"}, {"OrgCode1", "OrgName1-2", "OrgCode1-2"}};
	
	/**
	 * 重置用户参数
	 */
	public static void reSetGApplication() {
		for(int i = 0;i < gOrg[0].length;i++) {
			gOrg[i][0] = "OrgName" + String.valueOf(i+1);
			gOrg[i][1] = "OrgCode" + String.valueOf(i+1);
		}
		
		for(int k = 0;k < gChildOrg1[0].length;k++) {
			gOrg[k][0] = "OrgCode" + String.valueOf(k+1);
			gOrg[k][1] = "OrgName" + String.valueOf(k+1) + "-1";
			gOrg[k][2] = "OrgCode" + String.valueOf(k+1) + "-2";
		}
	}
}
