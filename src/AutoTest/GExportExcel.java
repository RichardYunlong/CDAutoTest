package AutoTest;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;

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
public class GExportExcel {
	private GExportExcel(){
		GLog.logShowConsole("This is a tool class.");
	}
	
	/**
	 *  输出文件保存路径
	 */
	public static final String OUTPUTPATH = "./output/";
	
	/**
	 *  输出XLS文件名
	 */
	public static final String OUTPUTXLS = "output.xls";

	/**
	 *  当前行游标
	 */
	public static int dWriteIndex = 0;
	
	/**
	 *  用例输出Excel下目标sheet的名称
	 */
	private static String sheetName = "";
	
	/**
	 *  用例输出Excel下目标sheet的序号
	 */
	public static int sheetIndex = 0;
	
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
	public static boolean writeLine(GReportVO reportVO, String excelPath, int sheetIndex, int rowIndex) {
		boolean result = false;
		FileOutputStream out = null;
		//按行写入测试结果
		try {
	        FileInputStream fs=new FileInputStream(excelPath);  //获取excelPath  
	        POIFSFileSystem ps=new POIFSFileSystem(fs);  //使用POI提供的方法得到excel的信息  
	        HSSFWorkbook wb=new HSSFWorkbook(ps);    
	        HSSFSheet sheet=wb.getSheetAt(sheetIndex);  //获取到工作表，因为一个excel可能有多个工作表  
	        HSSFRow row=sheet.getRow(rowIndex);
	         
	        out=new FileOutputStream(excelPath);  //向excelPath中写数据  
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
	        out.close();    
	        GSys.logSys("RECORD ROW " + rowIndex);
	        result = true;
		} catch (Exception e) {
			GSys.logErrorSys(GMsg.MSG_IOFAILED[1]);
		} finally {
			try {
				if(out != null)out.close();
			} catch (IOException e) {
				GSys.logErrorSys(GMsg.MSG_IOFAILED[1]);
				e.printStackTrace();
			} 
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
		FileOutputStream out = null;
		String strOutputPath = OUTPUTPATH + OUTPUTXLS;
		try {
	        FileInputStream fs = new FileInputStream(strOutputPath);  //获取excelPath  
	        POIFSFileSystem ps = new POIFSFileSystem(fs);  //使用POI提供的方法得到excel的信息  
	        HSSFWorkbook wb = new HSSFWorkbook(ps);    
	        HSSFSheet sheet = wb.getSheetAt(sheetIndex);  //获取到工作表，因为一个excel可能有多个工作表  
	        HSSFRow row = sheet.getRow(0);
	         
	        out = new FileOutputStream(strOutputPath);  //向excelPath中写数据  
	        row=sheet.createRow(0);
	        
	        for(int i = 0;i < headers.length;i++) {
	        	row.createCell(i).setCellValue(headers[i]);
	        }
	         
	        out.flush();  
	        wb.write(out);    
	        out.close();    

	        result = true;
		} catch (Exception e) {
			GSys.logErrorSys(GMsg.MSG_IOFAILED[1]);
		} finally {
			try {
				if(out != null)out.close();
			} catch (IOException e) {
				GSys.logErrorSys(GMsg.MSG_IOFAILED[1]);
				e.printStackTrace();
			}	
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
		FileOutputStream out = null;
		String strOutputPath = OUTPUTPATH + OUTPUTXLS;
		try {
	        FileInputStream fs = new FileInputStream(strOutputPath);  //获取excelPath  
	        POIFSFileSystem ps = new POIFSFileSystem(fs);  //使用POI提供的方法得到excel的信息  
	        HSSFWorkbook wb = new HSSFWorkbook(ps);    
	        HSSFSheet sheet = wb.getSheetAt(sheetIndex);  //获取到工作表，因为一个excel可能有多个工作表  
	        HSSFRow row = sheet.getRow(0);
	         
	        out = new FileOutputStream(strOutputPath);  //向excelPath中写数据  
	        row=sheet.createRow(0);
	        
	        for(int i = 0;i < strHeaders.length;i++) {
	        	row.createCell(i).setCellValue(strHeaders[i]);
	        }
	         
	        out.flush();  
	        wb.write(out);    
	        out.close();    

	        result = true;
		} catch (Exception e) {
			GSys.logErrorSys(GMsg.MSG_IOFAILED[1]);
		} finally {
			try {
				if(out != null)out.close();
			} catch (IOException e) {
				GSys.logErrorSys(GMsg.MSG_IOFAILED[1]);
				e.printStackTrace();
			}	
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
		sheetName = strName;
		
		try {
			if(GFile.bIsOpened(strPath)) {
				GSys.logErrorSys(GMsg.MSG_ISOPENED[1]);
				System.exit(0);
			}
			GFile.creatDir(OUTPUTPATH);
			GFile.creatXlsFile(strPath);
			File testExcel = new File(strPath);
			if (!testExcel.exists()) {// 文件是否存在
				GSys.logErrorSys(GMsg.MSG_NOTFOUND[2]);
				System.exit(0);
			}else {
				if(!GFile.bIsOpened(strPath)) {
					GFile.deleteExcel(strPath);
					GFile.createExcel(strPath, sheetName, headers);
					if(writeExcelHead()) {
						GSys.logSys("EXPORT XLS READY");
						return true;
					}else {
						GSys.logErrorSys(GMsg.MSG_IOFAILED[1]);
					}
				}
			}
		} catch (Exception e) {
			GSys.logErrorSys(GMsg.MSG_IOFAILED[2]);
			e.printStackTrace();
		}

		return false;
	}
	
	/**
	 *  导出Excel表：输出单行
	 *  
	 *  @param reportVO 输出数据结构
	 */
	public static void doExportExcelByLine(GReportVO reportVO) {
		String strOutputPath = OUTPUTPATH + OUTPUTXLS;
		try {
			// 行数加1
			dWriteIndex++;

			// 写excel
			writeLine(reportVO, strOutputPath, sheetIndex, dWriteIndex);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 *  导出Excel表：输出列表
	 *  
	 *  @param lstReportVO 输出数据结构
	 */
	public static void doExportExcelByList(List<GReportVO> lstReportVO) {
		String strOutputPath = OUTPUTPATH + OUTPUTXLS;
		try {
			// 行数加1		
			for(int i = 0;i < lstReportVO.size();i++) {
				// 写excel
				writeLine(lstReportVO.get(i), strOutputPath, sheetIndex, i+1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 *  初始化Excel输出流,并输出
	 */
	public static void doExportXls() {
		try {
			for (int i = 1; i <= GParam.dTestTotalNo; i++) {
				String strInputs = GParam.strTestCaseInputArray[i][6] + "||"
								+ GParam.strTestCaseInputArray[i][7] + "||"
								+ GParam.strTestCaseInputArray[i][8] + "||"
								+ GParam.strTestCaseInputArray[i][9] + "||"
								+ GParam.strTestCaseInputArray[i][10] + "||";
				
				//加载一组参数，写入上表，多次执行
				GReportVO reportVO = new GReportVO();
				reportVO.setSystemModule(GParam.strTestCaseInputArray[i][0]);
				reportVO.setFunctionPoint(GParam.strTestCaseInputArray[i][1]);
				reportVO.setCaseScription(GParam.strTestCaseInputArray[i][2]);
				reportVO.setPrefixCondition(GParam.strTestCaseInputArray[i][3]);
				reportVO.setCaseStep(GParam.strTestCaseInputArray[i][4]);
				reportVO.setOutputMix("ResultCode:" + GError.strResultTSNO[i][0] + ";ResultMessage:" + GError.strResultTSNO[i][1]);
				reportVO.setOutputMix1("与预期一致");
				reportVO.setOutputMix2("");
				reportVO.setIsPassed(GError.strResultTSNO[i][2]);
				reportVO.setCaseKind("接口测试");
				reportVO.setCasePriority(GError.strResultTSNO[i][4]);
				reportVO.setCaseMark("");
				GExportExcel.doExportExcelByLine(reportVO);
				
				if (GParam.dRecordInputParamListInTxt != 0 && i <= GParam.dRecordInputParamListInTxt)
					GFile.writeStringToRight(GLog.strLogStyle[4], strInputs + "\r\n");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 *  导出Excel表
	 *  
	 *  @return 成功返回true，否则返回false
	 */
	public static boolean doExportExcel() {
		GSys.logSys("TEST CASE EXPORT START");

		try {
			if(GExportExcel.initExportExcel(GParam.getTestCaseOutputFullName(),"测试用例"))
				doExportXls();

			GSys.logSys("TEST CASE EXPORT COMPELETE");

			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return false;
	}
}
