package main.java.Test;

import main.java.Base.GClazz;

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
	public String getTP_RANGE() {
		return TP_RANGE;
	}
	public void setTP_RANGE(String tP_RANGE) {
		TP_RANGE = tP_RANGE;
	}
}
