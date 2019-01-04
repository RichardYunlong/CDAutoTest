package AutoTest;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;

/**
 *  导出Excel
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
	private static final String[] headers = { "系统模块", "功能点", "用例类型", "用例编号", "用例说明", "前置条件", "步骤描述", "预期结果", "第一轮测试结果", "第二轮测试结果", "是否通过", "测试类型", "用例优先级", "备注" };

	/**
	 *  Excel写入表头
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
	        
	        for(int i = 0;i < 14;i++) {
	        	row.createCell(i).setCellValue(headers[i]);
	        }
	         
	        out.flush();  
	        wb.write(out);    
	        out.close();    

	        result = true;
		} catch (Exception e) {
			System.out.println("WRITE XLS HEAD FAIL!");
		}
		
		return result;
	}

	/**
	 *  Excel表格检查
	 */
	public static boolean initExportExcel(String Path, String Name) {
		strOutputPath = Path;
		sheetName = Name;
		try {
			File testExcel = new File(strOutputPath);
			if (!testExcel.exists()) {// 文件是否存在
				System.out.println("XLS DOSE NOT EXIST");
			}else {
				if(!GFile.IsOpened(strOutputPath)) {
					GFile.deleteExcel(strOutputPath);
					GFile.createExcel(strOutputPath, sheetName, headers);
					if(WriteExcelHead()) {
						IsExportExcelReady = true;
						System.out.println("EXPORT XLS READY");
					}else {
						System.out.println("FAIL TO WRITE HEAD");
					}
				}
			}
		} catch (Exception e) {
			System.out.println("FAIL TO CREATE XLS");
			e.printStackTrace();
		}

		return IsExportExcelReady;
	}
	
	/**
	 *  导出Excel表：输出单行
	 */
	public static void doExportExcelByLine(GReportVO ReportVO) {
		try {
			// 行数加1
			dWriteIndex++;

			// 写excel
			GExcel.writeLine(ReportVO, strOutputPath, sheetIndex, dWriteIndex);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String args[]) throws IOException {
		initExportExcel("./output/report.xls","测试用例");
		GReportVO ReportVO = new GReportVO();
		ReportVO.setSystemModule("系统" + "0");
		ReportVO.setFunctionPoint("功能点" + "1");
		ReportVO.setCaseStyle("类型" + "2");
		ReportVO.setCaseTSNO("编号" + "3");
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
