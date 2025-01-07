package IO;

import Base.GClazz;
import Base.GFile;
import DT.GLog;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

/**
 *  导入Excel
 */
public class GExcelIni {
	
    /**
     *  构造函数
     */
	private GExcelIni(){
		GClazz.thisAToolClass();
	}
	
	/**
	 *  参数表缓存器
	 */
	private static List<GXlsIniVO> inputList = null;
	
	public static List<GXlsIniVO> getInputList() {
		return inputList;
	}

	public static void setInputList(List<GXlsIniVO> inputList) {
		GExcelIni.inputList = inputList;
	}
	
	/**
	 * 【核心数据结构】
	 *  输入参数文件缓存-Strin[][]g类型
	 *  用例输入详情：按照“行列”的形式，将输入参数文件全部读入到缓存中，包含参数文件的【字段名】行和【序号】列
	 */
	private static String[][] iniParams = null;
	
	public static String[][] getIniParams() {
		return iniParams;
	}

	public static void setIniParams(String[][] iniParams) {
		GExcelIni.iniParams = iniParams;
	}

	/**
	 *  参数表缓存器
	 */
	private static int ROWS = 0;
	
	public static int getROWS() {
		return ROWS;
	}

	public static void setROWS(int rOWS) {
		ROWS = rOWS;
	}

	/**
	 *  参数表缓存器
	 */
	private static int COLS = 0;
	
	public static int getCOLS() {
		return COLS;
	}

	public static void setCOLS(int cOLS) {
		COLS = cOLS;
	}

	/**
	 *  设置行列值
	 *  
	 *  @param rowNum 用例总个数
	 *  @param colNum 单个用例参数个数
	 */
	public static void initIniParams(int rowNum, int colNum) {
		if((rowNum > 0) && (colNum > 0)) {
			iniParams = new String[rowNum][colNum];
		}
		
		for (int i = 0; i < rowNum; i++) {
			for (int j = 0; j < colNum; j++) {
				iniParams[i][j] = "empty";
			}
		}
	}
	
	/**
	 *  打印行列值
	 */
	public static void showIniParams() {
		GLog.logRecord(8, "\nLOAD INI PARAMS START");
		for (int i = 0; i < ROWS; i++) {
			for (int j = 0; j < COLS; j++) {
				GLog.logRecordNoEnter(8, "[" + iniParams[i][j] + "]");
			}
			GLog.logRecordNoEnter(8, "\n");
		}
		GLog.logRecord(8, "LOAD INI PARAMS COMPELETE\n");
	}

	/**
	 *  读入Excel表格
	 *  
	 *  @param filePath 源文件全名
	 *  @param maxLimit 最大读取条数
	 *  
	 *  @return 读取到的数据列表
	 */
	public static List<GXlsIniVO> read(String filePath, int maxLimit) {
		List<GXlsIniVO> tmp = null;
		File file = new File(filePath);
		
		try(FileInputStream fileInputStream = new FileInputStream(file)) {
			GXlsIniVO excelIniVO = new GXlsIniVO();
			setFields(excelIniVO);
			setHeader(excelIniVO);
			@SuppressWarnings("unchecked")
			List<GXlsIniVO> list = (List<GXlsIniVO>) GExcelBase.read(fileInputStream, excelIniVO.getHeaders(),
					excelIniVO.getFields(), GXlsIniVO.class, maxLimit);
			tmp = new ArrayList<>();
			for (int i = 0; i < list.size(); i++) {
				// 读入所有核心参数，TestCaseInputArray保存所有主要输入参数的数组
				if (i < maxLimit) {
					iniParams[i][0] = list.get(i).getIndex();
					iniParams[i][1] = list.get(i).getModule();
					iniParams[i][2] = list.get(i).getKeywords();
					iniParams[i][3] = list.get(i).getScription();
					iniParams[i][4] = list.get(i).getNumber();
					iniParams[i][5] = list.get(i).getType();
					iniParams[i][6] = list.get(i).getMark();
				}

				if (i > 0) {
					tmp.add(list.get(i));
				}
			}
		} catch (Exception e) {
			GLog.logSysFunctionException("read", e);
		}
		
		return tmp;
	}
	
	/**
	 *  获得表行数
	 *  不包含首行，使用时需要考虑首行为字段名称的情况
	 *  
	 *  @param strPath 源文件全名
	 *  @param sheetIndex 页签序号
	 *  @return 行数
	 */
	public static int getRowNum(String strPath, int sheetIndex) {
		int rowNum = 0;
		
		if (!GExcelBase.checkExcel(strPath))
			GLog.logRecord(4, "INPUT XLS DOES NOT EXIST");
		
		try(FileInputStream fis = new FileInputStream(strPath)) {
			Workbook workbook = WorkbookFactory.create(fis);
			Sheet sheet = workbook.getSheetAt(sheetIndex);
			rowNum = sheet.getLastRowNum();
		} catch (Exception e) {
			GLog.logSysFunctionException("getRowNum", e);
		}
		
		return rowNum;
	}
	
	/**
	 *  获得表列数
	 *  
	 *  @param strPath 源文件全名
	 *  @param sheetIndex 页签序号
	 *  @return 列数
	 */
	public static int getColNum(String strPath, int sheetIndex) {
		int colNum = 0;
		
		if (!GExcelBase.checkExcel(strPath))
			GLog.logRecord(4, "INPUT XLS DOES NOT EXIST");
		
		try(FileInputStream fis = new FileInputStream(strPath)) {
			Workbook workbook = WorkbookFactory.create(fis);
			Sheet sheet = workbook.getSheetAt(sheetIndex);
			int firstRowNum = sheet.getFirstRowNum();
			Row firstRow = sheet.getRow(firstRowNum);
			colNum = firstRow.getLastCellNum();
		} catch (Exception e) {
			GLog.logSysFunctionException("getColNum", e);
		}

		return colNum;
	}

	/**
	 *  设置区域变量
	 *  
	 *  @param excelIniVO 表格对象
	 */
	private static void setFields(GXlsIniVO excelIniVO) {
		String[] fields = { "index", "module", "keywords", "scription", "number", "type", "mark"};
		excelIniVO.setFields(fields);
	}

	/**
	 *  设置区域提示文字
	 *  
	 *  @param excelIniVO 表格对象
	 */
	private static void setHeader(GXlsIniVO excelIniVO) {
		String[] headers = { "序号", "模块", "关键字", "摘要", "影响脚本数", "类型", "备注"};
		excelIniVO.setHeaders(headers);
	}

	/**
	 *  导入Excel表
	 *  
	 *  @param strPath 源文件全名
	 *  
	 *  @return 读取成功则返回true，否则返回false
	 */
	@SuppressWarnings("UnusedReturnValue")
    public static boolean doXlsIni(String strPath) {
		try {
			if (!GExcelBase.checkExcel(strPath)) {
				GFile.writeStringToGuideBottom("INPUTS XLS IS NOT EXIST");
				return false;
			}
			ROWS = getRowNum(strPath, 0) + 1;
			COLS = getColNum(strPath, 0);
			initIniParams(ROWS, COLS);
			setInputList(read(strPath, ROWS));
			showIniParams();
		} catch (Exception e) {
			GFile.writeStringToGuideBottom("FAIL TO IMPORT XLS");
			GLog.logSysFunctionException("doXlsIni", e);
		}
		return true;
	}
}
