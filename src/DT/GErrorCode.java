package DT;

import Base.GClazz;
import Base.GMissionMsg;
import Sys.GSys;

/**
 *  错误码
 */
public class GErrorCode {
	
    /**
     *  构造函数
     */
	private GErrorCode(){
		GClazz.thisAToolClass();
	}
	
	/**
	 *  设置“系统信息”
	 */
	public static void setDefault(){
		long startTime = 0;
		GSys.logSys(GMissionMsg.getStepStart("GErrorCode"));
		startTime = System.currentTimeMillis();
		GSys.logShowAndRecord(startTime, "GErrorCode");
		GSys.logSys(GMissionMsg.getStepEnd());
    }
}
