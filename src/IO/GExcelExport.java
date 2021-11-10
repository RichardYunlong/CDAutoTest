package IO;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;

import Base.GFile;
import Base.GMissionMsg;
import Base.GMsg;
import DT.GLog;
import Sys.GPath;
import Sys.GSys;
import Test.GTCNO;
import Test.GTestPlan;
import Test.GTestProgress;
import Test.GTestResult;

/**
 *  导出Excel
 *  旨在按行追加至目标xls文件，调用方式举例如下：
 *  
	public static void main(String args[]) throws IOException {
		//初始化输出xls、sheet名称、表头，执行一次
		initExportExcel("./output/report.xls","测试用例");
		
		for(int i = 0;i < 10;i++) {
			//加载一组参数，写入上表，多次执行
			GReportVO ReportVO = new GReportVO();
			ReportVO.setSystemModule("系统" + "0");
			ReportVO.setFunctionPoint("功能点" + "1");
			ReportVO.setCaseScription("说明" + "4");
			ReportVO.setPrefixCondition("条件" + "5");
			ReportVO.setCaseStep("步骤" + "6");
			ReportVO.setOutputMix("预期" + "7");
			ReportVO.setOutputMix1("第一轮" + "8");
			ReportVO.setOutputMix2("第二轮" + "9");
			ReportVO.setIsPassed("通过" + "10");
			ReportVO.setCaseKind("类型" + "11");
			ReportVO.setCasePriority("优先级" + "12");
			ReportVO.setCaseMark("备注" + "13");
			doExportExcelByLine(ReportVO);
		}
	} 
 *  
 */
public class GExcelExport {
	
	/**
	 *  构造函数
	 */
	private GExcelExport(){
		GLog.logShowConsole("This is a tool class.");
	}

	/**
	 *  当前行游标
	 */
	private static int dWriteIndex = 0;
	
	/**
	 *  用例输出Excel下目标sheet的序号
	 */
	private static int sheetIndex = 0;
	
	/**
	 *  用例输出Excel指定表头
	 */
	private static final String[] headers = { "系统模块", "功能点", "用例说明", "前置条件", "步骤描述", "预期结果", "第一轮测试结果", "第二轮测试结果", "是否通过", "测试类型", "用例优先级", "备注" };

	/**
	 *  写Excel：追加行，适用于内置的headers
	 *  
	 *  @param reportVO 输出数据结构
	 *  @param excelPath 文件全名
	 *  @param sheetIndex 表格序号
	 *  @param rowIndex 行号
	 *  @return 输出成功返回true，否则返回false
	 */
	public static boolean writeLine(GXlsRequestVO reportVO, String excelPath, int sheetIndex, int rowIndex) {
		boolean result = false;
		
		//按行写入测试结果
		try(FileOutputStream out=new FileOutputStream(excelPath);) {
	        FileInputStream fs=new FileInputStream(excelPath);  //获取excelPath  
	        POIFSFileSystem ps=new POIFSFileSystem(fs);  //使用POI提供的方法得到excel的信息  
	        HSSFWorkbook wb=new HSSFWorkbook(ps);    
	        HSSFSheet sheet=wb.getSheetAt(sheetIndex);  //获取到工作表，因为一个excel可能有多个工作表  
	        HSSFRow row=sheet.getRow(rowIndex);
	         
	        
	        row=sheet.createRow((short)(sheet.getLastRowNum()+1)); //在现有行号后追加数据
	        
	        row.createCell(0).setCellValue(reportVO.getSystemModule());
	        row.createCell(1).setCellValue(reportVO.getFunctionPoint());
	        row.createCell(2).setCellValue(reportVO.getCaseScription());
	        row.createCell(3).setCellValue(reportVO.getPrefixCondition());
	        row.createCell(4).setCellValue(reportVO.getCaseStep());
	        row.createCell(5).setCellValue(reportVO.getOutputMix());
	        row.createCell(6).setCellValue(reportVO.getOutputMix1());
	        row.createCell(7).setCellValue(reportVO.getOutputMix2());
	        row.createCell(8).setCellValue(reportVO.getIsPassed());
	        row.createCell(9).setCellValue(reportVO.getCaseKind());
	        row.createCell(10).setCellValue(reportVO.getCasePriority());
	        row.createCell(11).setCellValue(reportVO.getCaseMark());
	         
	        out.flush();  
	        wb.write(out);    

	        String[] logPath = GLog.getLogpath().clone();
	        GFile.writeStringToRight(logPath[4],"RECORD ROW " + rowIndex);
	        result = true;
		} catch (Exception e) {
			GSys.logErrorSys(GMsg.MSG_IOFAILED[1]);
		}
		return result;
	}
	
	/**
	 *  Excel写入表头
	 *  
	 *  @return 输出成功返回true，否则返回false
	 */
	public static boolean writeExcelHead() {
		boolean result = false;
		
		String strOutputPath = GPath.OUTPUT_XLS_PATH + GPath.OUTPUT_XLS_NAME;
		try(FileOutputStream out = new FileOutputStream(strOutputPath);) {
	        FileInputStream fs = new FileInputStream(strOutputPath);  //获取excelPath  
	        POIFSFileSystem ps = new POIFSFileSystem(fs);  //使用POI提供的方法得到excel的信息  
	        HSSFWorkbook wb = new HSSFWorkbook(ps);    
	        HSSFSheet sheet = wb.getSheetAt(sheetIndex);  //获取到工作表，因为一个excel可能有多个工作表  
	        HSSFRow row = sheet.getRow(0);
	          
	        row=sheet.createRow(0);
	        
	        for(int i = 0;i < headers.length;i++) {
	        	row.createCell(i).setCellValue(headers[i]);
	        }
	         
	        out.flush();  
	        wb.write(out);

	        result = true;
		} catch (Exception e) {
			GSys.logErrorSys(GMsg.MSG_IOFAILED[1]);
		}
		
		return result;
	}
	
	/**
	 *  Excel写入表头：自定义表头
	 *  
	 *  @param strHeaders 字段名
	 *  @return 输出成功返回true，否则返回false
	 */
	public static boolean writeExcelHead(String[] strHeaders) {
		boolean result = false;
		
		String strOutputPath = GPath.OUTPUT_XLS_PATH + GPath.OUTPUT_XLS_NAME;
		try(FileOutputStream out = new FileOutputStream(strOutputPath);) {
	        FileInputStream fs = new FileInputStream(strOutputPath);  //获取excelPath  
	        POIFSFileSystem ps = new POIFSFileSystem(fs);  //使用POI提供的方法得到excel的信息  
	        HSSFWorkbook wb = new HSSFWorkbook(ps);    
	        HSSFSheet sheet = wb.getSheetAt(sheetIndex);  //获取到工作表，因为一个excel可能有多个工作表  
	        HSSFRow row = sheet.getRow(0);

	        row=sheet.createRow(0);
	        
	        for(int i = 0;i < strHeaders.length;i++) {
	        	row.createCell(i).setCellValue(strHeaders[i]);
	        }
	         
	        out.flush();  
	        wb.write(out);   

	        result = true;
		} catch (Exception e) {
			GSys.logErrorSys(GMsg.MSG_IOFAILED[1]);
			GLog.logSysFunctionException("writeExcelHead", e);
		}
		
		return result;
	}

	/**
	 *  Excel表格准备
	 *  
	 *  @param strPath 文件全名
	 *  @param strName 表格名
	 *  @return 准备成功返回true，否则返回false
	 */
	public static boolean initExportExcel(String strPath, String strName) {
		
		try {
			if(GFile.bIsOpened(strPath)) {
				GSys.logErrorSys(GMsg.MSG_ISOPENED[1]);
				System.exit(0);
			}
			GFile.creatDir(GPath.OUTPUT_XLS_PATH);
			GFile.creatXlsFile(strPath);
			File testExcel = new File(strPath);
			if (!testExcel.exists()) {// 文件是否存在
				GSys.logErrorSys(GMsg.MSG_NOTFOUND[2]);
				System.exit(0);
			}else {
				if(!GFile.bIsOpened(strPath)) {
					GFile.deleteExcel(strPath);
					GFile.createExcel(strPath, strName, headers);
					if(writeExcelHead()) {
						return true;
					}else {
						GSys.logErrorSys(GMsg.MSG_IOFAILED[1]);
					}
				}
			}
		} catch (Exception e) {
			GSys.logErrorSys(GMsg.MSG_IOFAILED[2]);
			GLog.logSysFunctionException("initExportExcel", e);
		}

		return false;
	}
	
	/**
	 *  导出Excel表：输出单行
	 *  
	 *  @param reportVO 输出数据结构
	 */
	public static void doExportExcelByLine(GXlsRequestVO reportVO) {
		String strOutputPath = GPath.OUTPUT_XLS_PATH + GPath.OUTPUT_XLS_NAME;
		try {
			// 行数加1
			dWriteIndex++;

			// 写excel
			writeLine(reportVO, strOutputPath, sheetIndex, dWriteIndex);
		} catch (Exception e) {
			GLog.logSysFunctionException("doExportExcelByLine", e);
		}
	}
	
	/**
	 *  导出Excel表：输出列表
	 *  
	 *  @param lstReportVO 输出数据结构
	 */
	public static void doExportExcelByList(List<GXlsRequestVO> lstReportVO) {
		String strOutputPath = GPath.OUTPUT_XLS_PATH + GPath.OUTPUT_XLS_NAME;
		try {
			// 行数加1		
			for(int i = 0;i < lstReportVO.size();i++) {
				// 写excel
				writeLine(lstReportVO.get(i), strOutputPath, sheetIndex, i+1);
			}
		} catch (Exception e) {
			GLog.logSysFunctionException("doExportExcelByList", e);
		}
	}
	
	/**
	 *  初始化Excel输出流,并输出
	 */
	public static void doExportXls() {
		try {
			GSys.logSys("WRITE TYPE [XLS]");
			GSys.logSys("WRITE START");
			GSys.logSys("THERE ARE " + GTestProgress.getTotalNum().toString() + " RECORDS");
			int i = 1;
			String[][] xls = GTCNO.getTCNO_STR().clone();
			String[][] tcResult = GTestResult.getResultString().clone();
			for (;i <= GTestProgress.getTotalNum(); i++) {
				String strInputs = xls[i][6] + "||"
								+ xls[i][7] + "||"
								+ xls[i][8] + "||"
								+ xls[i][9] + "||"
								+ xls[i][10] + "||";
				
				//加载一组参数，写入上表，多次执行
				GXlsRequestVO reportVO = new GXlsRequestVO();
				reportVO.setSystemModule(xls[i][0]);
				reportVO.setFunctionPoint(xls[i][1]);
				reportVO.setCaseScription(xls[i][2]);
				reportVO.setPrefixCondition(xls[i][3]);
				reportVO.setCaseStep(xls[i][4]);
				reportVO.setOutputMix("ResultCode:" + tcResult[i-1][0] + ";ResultMessage:" + tcResult[i-1][1]);
				reportVO.setOutputMix1("与预期一致");
				reportVO.setOutputMix2("");
				reportVO.setIsPassed(tcResult[i-1][2]);
				reportVO.setCaseKind("接口测试");
				reportVO.setCasePriority(tcResult[i-1][4]);
				reportVO.setCaseMark("");
				doExportExcelByLine(reportVO);
				
				if (GTestPlan.getRecordInputParamListInTxt() != 0 && i <= GTestPlan.getRecordInputParamListInTxt()) {
					String[] logPath = GLog.getLogpath().clone();
					GFile.writeStringToRight(logPath[4], strInputs + "\r\n");
				}		
			}
			GSys.logSys("WRITE " + String.valueOf(i) + " COMPLETE");
		} catch (Exception e) {
			GLog.logSysFunctionException("doExportXls", e);
		}
	}
	
	/**
	 *  导出Excel表
	 *  
	 *  @return 成功返回true，否则返回false
	 */
	public static boolean doExportExcel() {
		long startTime = System.currentTimeMillis();
		GSys.logSys(GMissionMsg.getStepTop("XLS REPORT START"));
		GSys.logSys(GSys.getDate() + " WRITE XLS");
		try {
			if(initExportExcel(GPath.OUTPUT_XLS_PATH + GPath.OUTPUT_XLS_NAME,"测试用例"))
				doExportXls();
			long endTime = System.currentTimeMillis();
			GSys.logSys("WRITE XLS -SPEND:" + (endTime - startTime) + "MS");
			GSys.logSys("WRITE TO [" + GPath.OUTPUT_XLS_PATH + GPath.OUTPUT_XLS_NAME + "]");
			GSys.logSys(GMissionMsg.getStepBottom("XLS REPORT COMPLETE"));

			return true;
		} catch (Exception e) {
			GSys.logErrorSys("XLS REPORT FAILED");
			GLog.logSysFunctionException("doExportExcel", e);
		}

		return false;
	}
}
