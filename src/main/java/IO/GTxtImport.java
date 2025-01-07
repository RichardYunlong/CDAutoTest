package main.java.IO;

import main.java.Base.GFile;
import main.java.Base.GMsg;
import main.java.Base.GText;
import main.java.Base.GValue;
import main.java.DT.GLog;
import main.java.Sys.GPath;
import main.java.Sys.GSys;
import main.java.Test.GTestMission;

import java.io.*;
import java.nio.file.Files;
import java.util.Arrays;

/**
 * Txt导入
 */
public class GTxtImport {
	
	/**
	 * 
	 */
	public GTxtImport(){
		GLog.logShowConsole("This is a tool class.");
	}

	/**
	 *  参数表缓存器
	 */
	private String[][] inputList = null;
	
	/**
	 *  参数表单行缓存器
	 */
	private String[] inputLine = null;
	
	/**
	 *  获取所有用例集合
	 *
	 * @return inputList 所有用例集合
	 */
	public String[][] get() {
		return inputList;
	}
	
	/**
	 *  获得txt参数文件有效行数
	 *  算法为：读出目标文件的总行数，但实际txt文本中的第一行，行号为“0”，实际txt文本中的第二行，行号为“1”，以此类推
	 *  如果GTestCase.TestInputBeginRowIndex的值为1，即“从行号为1的行开始读取”，则认为“行号为0”的行为无效行（有可能为字段名）
	 *  这样定义的目的是考虑到有可能实际的TXT文本中会有标题栏，与Excel输入表格的形式一致，所以做同一性处理
	 *  如果GTestCase.TestInputBeginRowIndex = 2，即从行号为2（实际文本中第3行）开始读取，则有效行数为“实际TXT文本总行数-2”
	 *  以此类推
	 *  
	 *  @return 读取的参数表行数
	 */
	public int getRowCourt() {
		int txtLineNum = 0;
		GFile.deleteFile(GPath.INPUT_TXT_PATH + GMsg.MSG_FIlE_TIP[0] + GPath.INPUT_TXT_NAME);
		txtLineNum = GText.deleteBlankRow(GPath.INPUT_TXT_PATH + GPath.INPUT_TXT_NAME, GPath.INPUT_TXT_PATH + GMsg.MSG_FIlE_TIP[0] + GPath.INPUT_TXT_NAME);
		
		if(txtLineNum > GValue.CASE_NUM_MAX) {
			txtLineNum = GValue.CASE_NUM_MAX;
			GFile.writeStringToGuideBottom("INPUTS MORE THAN '" +GValue.CASE_NUM_MAX+ "' WHICH BE DEFINED IN CODE,ONLY RELOAD " + GValue.CASE_NUM_MAX + " INPUTS");
		}else if(txtLineNum < 1) {
			GFile.writeStringToBottom(GSys.GUIDE ,"ERROR----" + GMsg.MSG_EMPTY[0]);
		}else {
			this.inputList = new String[txtLineNum][GValue.PARAM_NUM_MAX];
			
			for(int i=0;i<txtLineNum;i++) {
				for(int j=0;j<GValue.PARAM_NUM_MAX;j++) {
					this.inputList[i][j] = "";
				}
			}
		}
		
		return txtLineNum;
	}
	
	/**
	 *  按行读入Txt表格，每行按照指定字符分割后，每一段即为一个参数值
	 *  
	 *  @param txtPath 源文件全名
	 *  @param lineNo 行号 
	 *  @param tag 分隔符
	 *  @return 分割后的String[]
	 */
	@SuppressWarnings("UnusedReturnValue")
    public String[] readline(String txtPath, long lineNo, String tag) {
		String line = "";
		String encoding = "UTF-8";
		File txtFile = new File(txtPath);
		
		try(InputStream in = Files.newInputStream(txtFile.toPath());
            InputStreamReader read = new InputStreamReader(in, encoding);
            BufferedReader reader = new BufferedReader(read)) {
			int i = 0;
			while (i < lineNo) {
				line = reader.readLine();
				i++;
			}
			if(null != line) {
				this.inputLine = line.split(tag);
			}
			if (this.inputLine == null) {
				GFile.writeStringErrorToGuideBottom(GMsg.MSG_EXIST[1] + " OR " + GMsg.MSG_EMPTY[0]);
			}
		} catch (Exception e) {
			GFile.writeStringErrorToGuideBottom("readline[" + Arrays.toString(e.getStackTrace()) +"]");
		}
		
		return this.inputLine;
	}
	
	/**
	 *  导入TXT类型的参数表
	 *  
	 *  @param strPath 源文件全名
	 *  @return 读取成功返回true，否则返回false
	 */
	public boolean doImportTxt(String strPath) {
		if(!strPath.equals(GPath.INPUT_TXT_PATH + GPath.INPUT_TXT_NAME)) {
			GFile.writeStringToGuideBottom("FILE PATH IS NOT LIKE INIT");
		}
		GFile.writeStringToGuideBottom("READ INPUTS[" + strPath + "]");
		int inputListLength = getRowCourt();//此处获取的为出标题栏外的有效行数
		GFile.writeStringToGuideBottom("THERE ARE " + inputListLength +" ROWS OF INPUTS,INCLUDE 1 ROWS WHICH IS RECORD BY FIELD NAME");
		if(inputListLength <= 0)return false;
		
		String[][] txt = GTestMission.gtcno.getTCNO_STR().clone();
		for(int i = 0;i < inputListLength;i++) {
			readline(GPath.INPUT_TXT_PATH + GMsg.MSG_FIlE_TIP[0] + GPath.INPUT_TXT_NAME, i + 1, ",");
			if(null != this.inputLine) {
                System.arraycopy(this.inputLine, 0, txt[i], 0, this.inputLine.length);
			}
		}
		GTestMission.gtcno.setTCNO_STR(txt);
		return true;
	}
}
