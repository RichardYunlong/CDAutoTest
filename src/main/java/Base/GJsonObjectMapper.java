package Base;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import DT.GLog;
import Test.GTestMission;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 *  Json数据处理
 */
public class GJsonObjectMapper extends ObjectMapper {
	
    /**
     *  Json配置UID
     */
    private static final long serialVersionUID = 1L;

    /**
     *  Json配置
     */
	@SuppressWarnings("deprecation")
	public GJsonObjectMapper() {
        this.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
        this.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        this.configure(SerializationFeature.WRITE_NULL_MAP_VALUES, false);
        this.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        this.setSerializationInclusion(Include.NON_NULL);
    }

	/**
	 *  Json读取数据结构
	 *  
	 *  @param content String对象
	 *  @param valueType 模板类
	 *  
	 *  @return 模板对象
	 */
    public <T> T readValue(String content, Class<T> valueType) {
        try {
            return super.readValue(content, valueType);
        } catch (Exception e) {
            GLog.logSysFunctionException("readValue", e);
        }
        return null;
    }

    /**
     *  Json写入数据处理
     *  
     *  @param object json对象
     *  
     *  @return String对象
     */
    public String writeValueAsString(Object object) {
        try {
            return super.writeValueAsString(object);
        } catch (Exception e) {
            GLog.logSysFunctionException("writeValueAsString", e);
        }
        return null;
    }
    
	/**
	 *  将目标String格式转换成json格式，按照key获得value
	 *  
	 *  @param res 目标报文
	 *  @param key 关键字
	 *  
	 *  @return String对象
	 */
	public static String getJsonValueByName(String res, String key){
		String value = "0";
		JSONObject jsonObject;
		try {
			if(null != res && !res.isEmpty()) {
				jsonObject = JSON.parseObject(res);
				if(null != jsonObject) {
					value = jsonObject.getString(key);
				}else {
					GLog.logRecord(8, "CONVERT TO JSONOBJECT FAILED");
				}
			}
		}catch(Exception e) { 
			GLog.logRecord(8, "TARGET STRING IS NULL OR EMPTY\n" + Arrays.toString(e.getStackTrace()));
		}
		
		return value;
	}
	
	/**
	 *  根据url读入网络json文件中的内容转为String格式，按照key获得value
	 *  
	 *  @param fileUrl 目标json的url
	 *  
	 *  @return String类型的文本值
	 */
	public static String readUrlJson(String fileUrl) {
        int HttpResult; // 服务器返回的状态
        StringBuilder reJson = new StringBuilder();
        try
        {
            URL url =new URL(fileUrl); // 创建URL
            URLConnection urlconn = url.openConnection(); // 试图连接并取得返回状态码
            urlconn.connect();
            HttpURLConnection httpconn =(HttpURLConnection)urlconn;
            HttpResult = httpconn.getResponseCode();
            if(HttpResult != HttpURLConnection.HTTP_OK) {
                GLog.logRecordTime(8, "CONNECT FAILED");
            } else {
                int filesize = urlconn.getContentLength(); // 取数据长度
                if(filesize > 0) {
                    reJson = getStringBuilder(urlconn);
                }else {
                	reJson = new StringBuilder();
                }
            }
        }
        catch (FileNotFoundException e) {
        	GLog.logRecordTime(8, GMsg.MSG_NOTFOUND[2] + "\n" + e);
        }
        catch (IOException e) {
        	GLog.logRecordTime(8, GMsg.MSG_IOFAILED[4] + "\n" + e);
        }

        try {
        	GFile.clearDirectory("./json/");
			GFile.creatSimpleFile("./json/", "aqi-login2desktop.json");
			GFile.writeStringToRight("./json/aqi-login2desktop.json", reJson.toString());
		} catch (IOException e) {
			GLog.logSysFunctionException("readUrlJson", e);
		}

        return reJson.toString();
    }

	/**
	 *  根据地址获得文本值
	 *
	 *  @param urlconn 链接字符串
	 *
	 *  @return StringBuilder对象
	 */
	private static StringBuilder getStringBuilder(URLConnection urlconn) throws IOException {
		InputStreamReader isReader = new InputStreamReader(urlconn.getInputStream(), StandardCharsets.UTF_8);
		BufferedReader reader = new BufferedReader(isReader);
		StringBuilder buffer = new StringBuilder();
		String line; // 用来保存每行读取的内容
		line = reader.readLine(); // 读取第一行
		while (line != null) { // 如果 line 为空说明读完了
			buffer.append(line); // 将读到的内容添加到 buffer 中
			buffer.append(" "); // 添加换行符
			line = reader.readLine(); // 读取下一行
		}
		return buffer;
	}

	/**
	 *  根据文件全名读入json文件中的内容转为json格式
	 *  
	 *  @param filePath 目标json的路径
	 *  
	 *  @return JSONObject对象
	 */
	public static JSONObject readFilePathJson(String filePath) {
		return JSONObject.parseObject(GFile.getContent(filePath));
	}
	
	/**
	 *  String转map
	 *  
	 *  @param strMap String类型的值
	 *  
	 *  @return String类型的文本值
	 */
	public static HashMap<String, String> string2Map(String strMap) {

        JSONObject jsonTemp = JSONObject.parseObject(strMap);

        return new HashMap<>(jsonLoop(jsonTemp));
	}
	
	/**
	 *  根据文件全名读入json文件中的内容转为map格式
	 *  
	 *  @param filePath 目标json的路径
	 *
	 *  @return 转换后的map
	 */
	public static HashMap<String, String> readFilePathMap(String filePath) {

        JSONObject jsonTemp = JSONObject.parseObject(GFile.getContent(filePath));

        return new HashMap<>(jsonLoop(jsonTemp));
	}
	
	/**
	 *  根据文件全名读入网络json文件中的内容转为map格式
	 *  
	 *  @param object 循环处理目标
	 *
	 *  @return 转换后的map
	 */
	@SuppressWarnings("CommentedOutCode")
    public static HashMap<String, String> jsonLoop(Object object) {
		HashMap<String, String> mapTemp = new HashMap<>();
		
        if(object instanceof JSONObject) {
            JSONObject jsonObject = (JSONObject) object;
            for (Map.Entry<String, Object> entry: jsonObject.entrySet()) {
//                Object o = entry.getValue();
//                if(o instanceof String) {
//                    mapTemp.put(entry.getKey(), entry.getValue().toString());
//                } else {
//                    jsonLoop(o);
//                }
                mapTemp.put(entry.getKey(), entry.getValue().toString());//只读取第一层json
            }
        }
        if(object instanceof JSONArray) {
            JSONArray jsonArray = (JSONArray) object;
            for (Object o : jsonArray) {
                jsonLoop(o);
            }
        }
        
        return mapTemp;
    }
	
	/**
	 * 找出目标json文件中存在但在源json文件中不存在的key值，并返回这些key以及对应的value
	 *
	 * @param jsonFileSrc 源文件全名
	 * @param jsonFileTar 目标文件全名
	 */
    public static void consoleDiffrentKey(String jsonFileSrc, String jsonFileTar) {
        String strDiff = "";
		
		String strSrc;
		String strTar;
		strSrc = GFile.getContent(jsonFileSrc);
		strTar = GFile.getContent(jsonFileTar);
		
		HashMap<String, String> mapSrc = new HashMap<>();
		HashMap<String, String> mapTar = new HashMap<>();
		HashMap<String, String> mapTarTemp = new HashMap<>();
		
		if(!strSrc.isEmpty()) {
			mapSrc.putAll(string2Map(strSrc));
		}
		
		if(!strTar.isEmpty()) {
			mapTar.putAll(string2Map(strTar));
			mapTarTemp.putAll(string2Map(strTar));
		}
		
		if(!mapSrc.isEmpty() && !mapTar.isEmpty()) {
			for (Map.Entry<String, String> entryTar : mapTar.entrySet()) {
				for (Map.Entry<String, String> entrySrc : mapSrc.entrySet()) {
					try{
						if(entryTar.getKey().equals(entrySrc.getKey())) {
							mapTarTemp.remove(entryTar.getKey());
							break;
						}
					}catch(Exception e) {
						GLog.logRecord(8, "differ Maps error\n" + Arrays.toString(e.getStackTrace()));
					}
				}
			}
		}
		
		if(!mapTarTemp.isEmpty()) {
			for (Map.Entry<String, String> entryTarTemp : mapTarTemp.entrySet()) {
				System.out.println("\"" + entryTarTemp.getKey() + "\":\"" + entryTarTemp.getValue() + "\",");
			}
		}

	}
	
	/**
	 *  将本地文件上传(覆盖)到指定url
	 *  
	 *  @param mapTemp 本地文件地址
	 *  @param filePath 本地文件路径
	 *  @param fileName 本地文件名称
	 */
	public static void export(Map<String, Integer> mapTemp, String filePath, String fileName) {
		String jsonfileFormatName = "json";
		String jsonfilePath = filePath + fileName + "." + jsonfileFormatName;
		
		try {
			GFile.creatDir(filePath);
			GFile.creatFile(filePath, fileName, jsonfileFormatName);
			GFile.clearFile(filePath + fileName + "." + jsonfileFormatName);
		} catch (IOException e) {
			GLog.logSysFunctionException("export", e);
		}
		
		GFile.writeStringToBottom(jsonfilePath, "{");
		for (Map.Entry<String, String> entry : GMap.sortMapByValue(GTestMission.gTestResult.sortTrackware).entrySet()) {
			try{
				GFile.writeStringToBottom(jsonfilePath, "\"" + entry.getKey() + "\":\"" + entry.getValue() + "\",");
			}catch(Exception e) {
				GLog.logRecord(8, "convert Map 2 Integer error\n" + Arrays.toString(e.getStackTrace()));
			}
		}
		GFile.writeStringToBottom(jsonfilePath, "}");
	}
}
