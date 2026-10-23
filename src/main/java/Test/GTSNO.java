package Test;

import Base.GClazz;

/**
 *  用例编号
 */
public class GTSNO {
	
    /**
     *  构造函数
     */
	private GTSNO(){
		GClazz.thisAToolClass();
	}
	
	/**
	 * 用例编号
	 */
	private static Integer TSNO = 100000;
	/**
	 *  获得用例编号
	 *
	 *  @return 用例编号
	 */
	public static Integer getTSNO() {
		return TSNO;
	}
	/**
	 *  设置用例编号
	 *
	 *  @param tSNO 用例编号
	 */
	public static void setTSNO(Integer tSNO) {
		TSNO = tSNO;
	}

	/**
	 *  获得当前用例编号后自增
	 *
	 *  @return 自增前的用例编号
	 */
	public static Integer getAdd1() {
		return TSNO++;
	}

	/**
	 *  用例编号自增 1，不返回原值
	 */
	public static void add1() {
		TSNO++;
	}
}
