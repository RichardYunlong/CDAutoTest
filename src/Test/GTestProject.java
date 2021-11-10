package Test;

import Base.GClazz;

/**
 * 项目管理
 */
public class GTestProject {
	
    /**
     *  构造函数
     */
	private GTestProject(){
		GClazz.thisAToolClass();
	}
	
    /**
     *  测试范围
     */
    private static String TP_RANGE = "";

	public static String getTP_RANGE() {
		return TP_RANGE;
	}

	public static void setTP_RANGE(String tP_RANGE) {
		TP_RANGE = tP_RANGE;
	}
}
