package AutoTest;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

/**
 *  Json解析
 */
public class GJson {
	private GJson(){
		GLog.logShowConsole("This is a tool class.");
	}
	
	/**
	 *  将目标String格式转换成json格式，按照key获得value
	 *  
	 *  @param res 目标报文
	 *  @param key 关键字
	 *  @return 值
	 */
	public static String getJsonValueByName(String res, String key){
		String value = null;
		JSONObject jsonObject = null;
		if(null != res && !res.equals("")) {
			jsonObject = JSON.parseObject(res);
			if(null != jsonObject) {
				value = jsonObject.getString(key);
			}else {
				GLog.logRecord(9, "CONVERT TO JSONOBJECT FAILED");
			}
		}else { 
			GLog.logRecord(9, "TARGET STRING IS NULL OR EMPTY");
		}
		
		return value;
	}
}
