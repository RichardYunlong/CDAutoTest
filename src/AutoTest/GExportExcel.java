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
	
	/**
	 *  Excel表格有效性检查结果标记
	 */
	public static boolean IsExportExcelReady = false;

	/**
	 *  当前行游标
	 */
	public static int dWriteIndex = 0;

	/**
	 *  用例输出Excel路径
	 */
	private static String strOutputPath = "";
	
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
	 *  @param ReportVO 输出数据结构
	 *  @param excelPath 文件全名
	 *  @param sheetIndex 表格序号
	 *  @param rowIndex 行号
	 *  @return 输出成功返回true，否则返回false
	 */
	public static boolean writeLine(GReportVO ReportVO, String excelPath, int sheetIndex, int rowIndex) {
		boolean result = false;
		//按行写入测试结果
		try {
	        FileInputStream fs=new FileInputStream(excelPath);  //获取excelPath  
	        POIFSFileSystem ps=new POIFSFileSystem(fs);  //使用POI提供的方法得到excel的信息  
	        HSSFWorkbook wb=new HSSFWorkbook(ps);    
	        HSSFSheet sheet=wb.getSheetAt(sheetIndex);  //获取到工作表，因为一个excel可能有多个工作表  
	        HSSFRow row=sheet.getRow(rowIndex);
	        //GFile.WriteStringToBottom(GSys.Guide, sheet.getLastRowNum()+" "+row.getLastCellNum());  //分别得到最后一行的行号，和一条记录的最后一个单元格  
	         
	        FileOutputStream out=new FileOutputStream(excelPath);  //向excelPath中写数据  
	        row=sheet.createRow((short)(sheet.getLastRowNum()+1)); //在现有行号后追加数据
	        
	        row.createCell(0).setCellValue(ReportVO.getSystemModule());
	        row.createCell(1).setCellValue(ReportVO.getFunctionPoint());
	        row.createCell(2).setCellValue(ReportVO.getCaseScription());
	        row.createCell(3).setCellValue(ReportVO.getPrefixCondition());
	        row.createCell(4).setCellValue(ReportVO.getCaseStep());
	        row.createCell(5).setCellValue(ReportVO.getOutputMix());
	        row.createCell(6).setCellValue(ReportVO.getOutputMix1());
	        row.createCell(7).setCellValue(ReportVO.getOutputMix2());
	        row.createCell(8).setCellValue(ReportVO.getIsPassed());
	        row.createCell(9).setCellValue(ReportVO.getCaseKind());
	        row.createCell(10).setCellValue(ReportVO.getCasePriority());
	        row.createCell(11).setCellValue(ReportVO.getCaseMark());
	         
	        out.flush();  
	        wb.write(out);    
	        out.close();    
	        GFile.WriteStringToBottom(GSys.Guide, "RECORD ROW " + rowIndex);
	        result = true;
		} catch (Exception e) {
			GFile.WriteStringToBottom(GSys.Guide, "WRITE EXCEL FAIL!");
		}
		return result;
	}
	
	/**
	 *  Excel写入表头
	 *  
	 *  @return 输出成功返回true，否则返回false
	 */
	public static boolean WriteExcelHead() {
		boolean result = false;
		try {
	        FileInputStream fs=new FileInputStream(strOutputPath);  //获取excelPath  
	        POIFSFileSystem ps=new POIFSFileSystem(fs);  //使用POI提供的方法得到excel的信息  
	        HSSFWorkbook wb=new HSSFWorkbook(ps);    
	        HSSFSheet sheet=wb.getSheetAt(sheetIndex);  //获取到工作表，因为一个excel可能有多个工作表  
	        HSSFRow row=sheet.getRow(0);
	         
	        FileOutputStream out=new FileOutputStream(strOutputPath);  //向excelPath中写数据  
	        row=sheet.createRow(0);
	        
	        for(int i = 0;i < headers.length;i++) {
	        	row.createCell(i).setCellValue(headers[i]);
	        }
	         
	        out.flush();  
	        wb.write(out);    
	        out.close();    

	        result = true;
		} catch (Exception e) {
			GFile.WriteStringToBottom(GSys.Guide, "WRITE XLS HEAD FAIL!");
		}
		
		return result;
	}
	
	/**
	 *  Excel写入表头：自定义表头
	 *  
	 *  @param strHeaders 字段名
	 *  @return 输出成功返回true，否则返回false
	 */
	public static boolean WriteExcelHead(String[] strHeaders) {
		boolean result = false;
		try {
	        FileInputStream fs=new FileInputStream(strOutputPath);  //获取excelPath  
	        POIFSFileSystem ps=new POIFSFileSystem(fs);  //使用POI提供的方法得到excel的信息  
	        HSSFWorkbook wb=new HSSFWorkbook(ps);    
	        HSSFSheet sheet=wb.getSheetAt(sheetIndex);  //获取到工作表，因为一个excel可能有多个工作表  
	        HSSFRow row=sheet.getRow(0);
	         
	        FileOutputStream out=new FileOutputStream(strOutputPath);  //向excelPath中写数据  
	        row=sheet.createRow(0);
	        
	        for(int i = 0;i < strHeaders.length;i++) {
	        	row.createCell(i).setCellValue(strHeaders[i]);
	        }
	         
	        out.flush();  
	        wb.write(out);    
	        out.close();    

	        result = true;
		} catch (Exception e) {
			GFile.WriteStringToBottom(GSys.Guide, "WRITE XLS HEAD FAIL!");
		}
		
		return result;
	}

	/**
	 *  Excel表格准备
	 *  
	 *  @param Path 文件全名
	 *  @param Name 表格名
	 *  @return 准备成功返回true，否则返回false
	 */
	public static boolean initExportExcel(String Path, String Name) {
		strOutputPath = Path;
		sheetName = Name;
		try {
			GFile.creatXlsFile(strOutputPath);
			File testExcel = new File(strOutputPath);
			if (!testExcel.exists()) {// 文件是否存在
				GFile.WriteStringToBottom(GSys.Guide, "XLS DOSE NOT EXIST");
			}else {
				if(!GFile.IsOpened(strOutputPath)) {
					GFile.deleteExcel(strOutputPath);
					GFile.createExcel(strOutputPath, sheetName, headers);
					if(WriteExcelHead()) {
						IsExportExcelReady = true;
						GFile.WriteStringToBottom(GSys.Guide, "EXPORT XLS READY");
					}else {
						GFile.WriteStringToBottom(GSys.Guide, "FAIL TO WRITE HEAD");
					}
				}
			}
		} catch (Exception e) {
			GFile.WriteStringToBottom(GSys.Guide, "FAIL TO CREATE XLS");
			e.printStackTrace();
		}

		return IsExportExcelReady;
	}
	
	/**
	 *  导出Excel表：输出单行
	 *  
	 *  @param ReportVO 输出数据结构
	 */
	public static void doExportExcelByLine(GReportVO ReportVO) {
		try {
			// 行数加1
			dWriteIndex++;

			// 写excel
			writeLine(ReportVO, strOutputPath, sheetIndex, dWriteIndex);
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
}
