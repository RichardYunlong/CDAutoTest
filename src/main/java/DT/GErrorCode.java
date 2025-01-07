package DT;

import Base.GClazz;
import Base.GFile;
import Base.GMissionMsg;
import Sys.GStatic;

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
	 *  设置错误码
	 */
	public void setDefault(){
		long startTime;
		GFile.writeStringToGuideBottom(GMissionMsg.getStepStart("GErrorCode"));
		startTime = System.currentTimeMillis();
		GStatic.gSys.logShowAndRecordGuide(startTime, "GErrorCode");
		GFile.writeStringToGuideBottom(GMissionMsg.getStepEnd());
    }
}
