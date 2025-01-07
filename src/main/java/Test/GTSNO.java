package main.java.Test;

import main.java.Base.GClazz;

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
	public static Integer getTSNO() {
		return TSNO;
	}
	public static void setTSNO(Integer tSNO) {
		TSNO = tSNO;
	}

	public static Integer getAdd1() {
		return TSNO++;
	}
	public static void add1() {
		TSNO++;
	}
}
