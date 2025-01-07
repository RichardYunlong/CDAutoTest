package main.java.DT;

import main.java.Base.GClazz;
import main.java.Base.GFile;
import main.java.Base.GMissionMsg;
import main.java.Sys.GStatic;

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
