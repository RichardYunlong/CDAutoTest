package Test;

import Base.GClazz;

/**
 * 项目管理
 */
public class GTestProject {
	
    /**
     *  构造函数
     */
	public GTestProject(){
		GClazz.thisAToolClass();
	}
	
    /**
     *  测试范围
     */
    private String TP_RANGE = "";
	/**
	 *  获得测试范围
	 *
	 *  @return 测试范围
	 */
	public String getTP_RANGE() {
		return TP_RANGE;
	}
	/**
	 *  设置测试范围
	 *
	 *  @param tP_RANGE 测试范围
	 */
	public void setTP_RANGE(String tP_RANGE) {
		TP_RANGE = tP_RANGE;
	}
}
