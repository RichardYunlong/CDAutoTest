package AutoTest;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

public class GJson {
	
	/**
	 *  将目标String格式转换成json格式，按照key获得value
	 */
	public static String GetJsonValueByName(String res, String key){
		String value = null;
		JSONObject jsonObject = null;
		if(null != res && !res.equals("")) {
			jsonObject = JSON.parseObject(res);
			if(null != jsonObject) {
				value = jsonObject.getString(key);
			}else {
				GLog.GLogRecord(9, "CONVERT TO JSONOBJECT FAILED");
			}
		}else {
			GLog.GLogRecord(9, "TARGET STRING IS NULL OR EMPTY");
		}
		
		return value;
	}
}
