package main.java.DT;

import main.java.IO.GCsv;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import main.java.Base.GFile;

import java.awt.*;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;

/*
 * 尝试修正json
 * 
 * @author hewei
 * 
 * 部分json有一定的json基础架构，但是存在一定语法错误，本类尝试将这样的json进行修正
 * */
public class GJson {

	/*
	 * 目标json
	 */
	@SuppressWarnings({"CanBeFinal", "FieldMayBeFinal"})
    private String check = "./json/check.json";

	/*
	 * 清洗后的json
	 */
	@SuppressWarnings({"CanBeFinal", "FieldMayBeFinal"})
    private String result = "./json/result.json";

	/*
	 * 系统允许的json最大行数
	 */
	@SuppressWarnings({"CanBeFinal", "FieldMayBeFinal", "FieldCanBeLocal"})
    private String csvDir = "./csvFiles/";

	/*
	 * 系统允许的json最大行数
	 */
	private final int JSON_LENGTH_MAX = 1024;

	/*
	 * 尝试修正1
	 *
	 * 此方法用于这类json：虽然有语法错误，但是大多数该换行的地方有执行换行
	 *
	 * @param content 待修正的字符串整体 不为null且不为""时此参数有效；为null或""时以下参数有效
	 * @param fileFullName 输入文件全名 包含路径和文件名
	 * */
	@SuppressWarnings({"rawtypes", "unchecked"})
	public String tryEnterEnough(String content, String srcFullName) {

		//用于保存待修正的json整体字符串
		StringBuilder jsonTemp = new StringBuilder();

		//第一步，将待优化的json按行保存到一个列表中
		List<String> listTemp = new ArrayList();

		if (null != content && !content.isEmpty()) {
			//按照换行符分割
			String[] lines = content.split("\\r?\\n");
			for (String line : lines) {
				//如果字符串行不为""（空）,且不是由" "（空格）组成，则追加到列表中
				if (!"".equals(line) && !line.replace(" ", "").isEmpty()) {
					listTemp.add(line);
				}
			}
		} else {
			File f = new File(srcFullName);
			try (FileInputStream is = new FileInputStream(f);
				 InputStreamReader read = new InputStreamReader(is, StandardCharsets.UTF_8);
				 BufferedReader reader = new BufferedReader(read)) {

				String line;
				int i;
				for (i = 0; i < JSON_LENGTH_MAX; i++) {
					if (null != (line = reader.readLine())) {
						if (!line.replace(" ", "").isEmpty()) {
							listTemp.add(line);
						}
					}
				}
			} catch (Exception e) {
				GLog.logRecord(8, Arrays.toString(e.getStackTrace()));
			}
		}

		if (!listTemp.isEmpty()) {
			for (String tarStr : listTemp) {
				//如果有关键符号是中文，则修改为英文符号
				tarStr = tarStr.replace("“", "\"");
				tarStr = tarStr.replace("”", "\"");
				tarStr = tarStr.replace("：", ":");
				//删除所有空格
				tarStr = tarStr.replace(" ", "");
				//如果在左括号前或右括号后存在分号，则删除该分号
				tarStr = tarStr.replace("\"{", "{");
				tarStr = tarStr.replace("\"[", "[");
				tarStr = tarStr.replace("}\"", "}");
				tarStr = tarStr.replace("]\"", "]");
				//删除双斜杠注释符号
				tarStr = tarStr.replace("//", "");
				tarStr = tarStr.replace("\\\\", "");
				//将等于号替换为冒号
				tarStr = tarStr.replace("=", ":");
				//删除转义字符和分号的组合
				tarStr = tarStr.replace("\\\"", "\"");

				if (!tarStr.isEmpty()) {
					StringBuilder tarStrNew = new StringBuilder();
					String[] lines = tarStr.split("\\r?\\n");
					for (String line : lines) {
						//如果字符串行不为""（空）,且不是由" "（空格）组成，则追加到列表中
						if (!"".equals(line) && !line.replace(" ", "").isEmpty()) {
							//如果目标行只有一个冒号，则执行处理，
							if ((line.length() - line.replace(":", "").length()) == 1) {
								String[] tarStrTemp = tarStr.split(":");
								if (tarStrTemp.length == 2) {
									//删除分割后两个子串中的英文分号，分别在两端添加
									tarStrTemp[0] = tarStrTemp[0].replace("\"", "");
									tarStrTemp[0] = tarStrTemp[0].replace(" ", "");
									tarStrTemp[1] = tarStrTemp[1].replace("\"", "");
									tarStrTemp[0] = "\"" + tarStrTemp[0] + "\"";
									if (tarStrTemp[1].startsWith("{") || tarStrTemp[1].startsWith("[")) {
										tarStrTemp[1] = "\r\n" + tarStrTemp[1];
									} else {
										tarStrTemp[1] = "\"" + tarStrTemp[1] + "\"";
									}
									tarStrNew.append(tarStrTemp[0]);
									tarStrNew.append(":");
									tarStrNew.append(tarStrTemp[1]).append(",");
								} else {
									tarStrTemp[0] = tarStrTemp[0].replace("\"", "");
									tarStrTemp[0] = tarStrTemp[0].replace(" ", "");
									tarStrTemp[0] = "\"" + tarStrTemp[0] + "\"";
									tarStrNew.append(tarStrTemp[0]);
									tarStrNew.append(":");
									tarStrNew.append("\"\",");
								}
							} else {//否则不执行处理，直接追加
								tarStrNew.append(line);
							}
							tarStrNew.append("\r\n");
						}
					}
					tarStr = tarStrNew.toString();
				}

				jsonTemp.append(tarStr);
			}
		}

		String json = jsonTemp.toString();

		//替换其他语法错误
		json = json.replace("{,", "{");
		json = json.replace("[,", "[");
		json = json.replace(",\r\n}", "\r\n}");
		json = json.replace(",\r\n]", "\r\n]");

		JSONObject object = JSONObject.parseObject(json);
		json = JSON.toJSONString(object, SerializerFeature.PrettyFormat, SerializerFeature.WriteMapNullValue,
				SerializerFeature.WriteDateUseDateFormat);

		return json;
	}

	/*
	 * 按照key值输出csv
	 *
	 * @param content 待解析的合法json
	 * @param fileFullName 输入文件全名 包含路径和文件名
	 * */
	@SuppressWarnings({"rawtypes", "unchecked", "CallToPrintStackTrace"})
	public void tryCsvEnough(String content, String srcFullName) {

		//第一步，将待优化的json按行保存到一个列表中
		List<String> listTemp = new ArrayList();

		//表头<位置顺序号，字段名称>
		LinkedHashMap<String, String> header = new LinkedHashMap<>();
		//表数据<行<位置顺序号, 单元格的值>>
		ArrayList<LinkedHashMap<String, String>> csvData = new ArrayList<>();
		LinkedHashMap<String, String> row1 = new LinkedHashMap<>();

		if (null != content && !content.isEmpty()) {
			//按照换行符分割
			String[] lines = content.split("\\r?\\n");
			for (String line : lines) {
				//如果字符串行不为""（空）,且不是由" "（空格）组成，则追加到列表中
				if (!"".equals(line) && !line.replace(" ", "").isEmpty()) {
					listTemp.add(line);
				}
			}
		} else {
			File f = new File(srcFullName);
			try (FileInputStream is = new FileInputStream(f);
				 InputStreamReader read = new InputStreamReader(is, StandardCharsets.UTF_8);
				 BufferedReader reader = new BufferedReader(read)) {

				String line;
				int i;
				for (i = 0; i < JSON_LENGTH_MAX; i++) {
					if (null != (line = reader.readLine())) {
						if (!line.replace(" ", "").isEmpty()) {
							listTemp.add(line);
						}
					}
				}
			} catch (Exception e) {
				GLog.logRecord(8, Arrays.toString(e.getStackTrace()));
			}
		}

		int rowIndex = 0;

		if (!listTemp.isEmpty()) {
            for (String s : listTemp) {
                if (!"".equals(s)) {
                    String[] lines = s.split("\\r?\\n");
                    for (String line : lines) {
                        //如果字符串行不为""（空）,且不是由" "（空格）组成，则追加到列表中
                        if (!"".equals(line)) {
                            line = line.replace(" ", "");
                            //如果目标行只有一个冒号，则执行处理，
                            if ((line.length() - line.replace(":", "").length()) == 1) {
                                String[] tarStrTemp = s.split(":");
                                if (tarStrTemp.length == 2) {
                                    //删除分割后两个子串中的英文分号，分别在两端添加
                                    tarStrTemp[0] = tarStrTemp[0].replace("\"", "");
                                    tarStrTemp[0] = tarStrTemp[0].replace("\t", "");
                                    if (!tarStrTemp[0].isEmpty()) {
                                        tarStrTemp[0] = tarStrTemp[0].replace(" ", "");
                                        rowIndex++;
                                        header.put(Integer.toString(rowIndex), tarStrTemp[0]);
                                        System.out.println("[表头序号[" + rowIndex + "]----表头值[" + tarStrTemp[0] + "]");

                                        tarStrTemp[1] = tarStrTemp[1].replace("\"", "");
                                        tarStrTemp[1] = tarStrTemp[1].replace(",", "");
                                        tarStrTemp[1] = tarStrTemp[1].replace(" ", "");

                                        String localValue = tarStrTemp[1];
                                        if (localValue.contains("{")
                                                || localValue.contains("[")) {
                                            localValue = "null";
                                        }
                                        row1.put(Integer.toString(rowIndex), localValue);
                                        System.out.println("[数据序号[" + rowIndex + "]----数据值[" + localValue + "]];");
                                    }
                                }
                            }
                        }
                    }
                }
            }
		}
		System.out.print("\n");
		csvData.add(row1);
		LinkedHashMap<String, String> rowEnd = new LinkedHashMap<>();
		csvData.add(rowEnd);
		File CSV = GCsv.createCSVFile(csvData, header, csvDir, "参数驱动temp");
		Desktop desktop = Desktop.getDesktop();
		if (CSV.exists())
			System.out.println("生成csv成功");
		try {
			desktop.open(CSV);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void selfFormat(String resFileFullName, String tarFileFullName) {

		//清空输出文档
		GFile.clearFile(tarFileFullName);

		//备份文件中的所有内容，保存到String对象中
		String contentBackup = GFile.getContent(resFileFullName);

		//去掉临时存储区中的空白行,利用输出文件回写
		GFile.deleteBlankLine(resFileFullName, tarFileFullName);
		String contentTemp = GFile.getContent(tarFileFullName);
		GFile.clearFile(resFileFullName);
		GFile.writeStringToBottom(resFileFullName, contentTemp);
		GFile.clearFile(tarFileFullName);

		//获取删除空行的文件中的所有内容，保存到String对象中，并清空源文件
		String content = GFile.getContent(resFileFullName);
		GFile.clearFile(resFileFullName);

		//使用英文减号替换所有空格
		content = content.replace(" ", "");

		//将json保留符号由中文改为英文
		content = content.replace("：", ":");
		content = content.replace("“", "\"");
		content = content.replace("”", "\"");
		content = content.replace("｛", "{");
		content = content.replace("｝", "}");
		content = content.replace("【", "[");
		content = content.replace("】", "]");
		content = content.replace("，", ",");

		//如果英文冒号两边有多个英文分号出现，则减少为一个
		content = content.replace("\"\":", "\":");
		content = content.replace("\"\"\":", "\":");
		content = content.replace(":\"\"", ":\"");
		content = content.replace(":\"\"\"", ":\"");

		//如果英文冒号右边跟的是括号，则英文冒号和括号之间不能有分号
		content = content.replace(":\"[", ":[");
		content = content.replace(":\"[", ":{");

		//删除双斜杠注释
		content = content.replace("\\\\", "");
		content = content.replace("//", "");

		//先删除所有右引号加逗号
		content = content.replace("\",", "");

		//如果分号缺右引号，则补充
		content = content.replace("\":", "\":\"");

		//将替换后的内容保存到临时存储区
		GFile.writeStringToBottom(resFileFullName, content);

		//给临时存储区的每一行行末都加上英文分好和逗号
		long countL = GFile.getFileRows(resFileFullName);
		if(countL < 65535L) {
			for(int i = 1;i <= countL;i++) {
				String line = GFile.getTxtByRowIndex(resFileFullName, i);

				String addChar = "";

				if(i == 1  || i == countL - 1 || i == countL || "}".equals(line) || "]".equals(line)
						|| line.endsWith("{") || line.endsWith("[") || line.endsWith("},") || line.endsWith("],")) {
					addChar = "";
				}else {
					addChar = "\",";
				}

				GFile.writeStringToBottom(tarFileFullName, line + addChar);
			}
		}

		//使用备份数据恢复输入文档
		GFile.clearFile(resFileFullName);
		GFile.writeStringToBottom(resFileFullName, contentBackup);

		//获取输出文件中的所有内容，保存到String对象中，并清空源文件
		String contentResult = GFile.getContent(tarFileFullName);
		GFile.clearFile(tarFileFullName);

		//取消所有空格
		contentResult = contentResult.replace(" ", "");

		//如果分号和扩号之间有分号，则删除
		contentResult = contentResult.replace(":\"[", ":[");
		contentResult = contentResult.replace(":\"{", ":{");
		contentResult = contentResult.replace("]\",", "],");
		contentResult = contentResult.replace("}\",", "},");

		//如果多余的分号和逗号出现在行尾，则修正
		contentResult = contentResult.replace("\"\",", "\",");
		contentResult = contentResult.replace("\"\",", "\",,");
		contentResult = contentResult.replace("\"\"\",", "\",");
		contentResult = contentResult.replace("\"\"\",", "\",");
		contentResult = contentResult.replace(":\"\"", ":\"");
		contentResult = contentResult.replace(":\"\"\"", ":\"");

		//将替换后的内容保存输出文件
		GFile.writeStringToBottom(tarFileFullName, contentResult);
	}

	public static void main(String[] args) {
		GJson gJson = new GJson();

		GFile.clearFile(gJson.result);
		GFile.writeStringToBottom(gJson.result, gJson.tryEnterEnough("", gJson.check));
		gJson.tryCsvEnough("", gJson.result);
	}
}
