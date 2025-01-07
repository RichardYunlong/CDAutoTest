package main.java.IO;

import main.java.Base.GClazz;
import main.java.Base.GFile;
import main.java.Base.GMissionMsg;
import main.java.Base.GMsg;
import main.java.DT.GLog;
import main.java.Sys.GPath;
import main.java.Sys.GStatic;
import main.java.Test.GTestMission;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.List;

/**
 *  导出Excel
 *  旨在按行追加至目标xls文件，调用方式举例如下：
 */
public class GExcelExport {

	/**
	 *  构造函数
	 */
	public GExcelExport(){
		GClazz.thisADataUnitClass();
	}

	/**
	 *  当前行游标
	 */
	private int dWriteIndex = 0;

	/**
	 *  用例输出Excel下目标sheet的序号
	 */
	@SuppressWarnings({"FieldMayBeFinal", "CanBeFinal"})
    private int sheetIndex = 0;

	/**
	 *  用例输出Excel指定表头
	 */
	private final String[] headers = { "系统模块", "功能点", "用例说明", "前置条件", "步骤描述", "预期结果", "第一轮测试结果", "第二轮测试结果", "是否通过", "测试类型", "用例优先级", "备注" };

	/**
	 *  写Excel：追加行，适用于内置的headers
	 *
	 *  @param reportVO 输出数据结构
	 *  @param excelPath 文件全名
	 *  @param sheetIndex 表格序号
	 *  @param rowIndex 行号
	 *  @return 输出成功返回true，否则返回false
	 */
	@SuppressWarnings("UnusedReturnValue")
    public boolean writeLine(GXlsRequestVO reportVO, String excelPath, int sheetIndex, int rowIndex) {
		boolean result = false;

		//按行写入测试结果
		try(FileOutputStream out=new FileOutputStream(excelPath)) {
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
			GFile.writeStringErrorToGuideBottom(GMsg.MSG_IOFAILED[1]);
		}
		return result;
	}

	/**
	 *  Excel写入表头
	 *
	 *  @return 输出成功返回true，否则返回false
	 */
	public boolean writeExcelHead() {
		boolean result = false;

		String strOutputPath = GPath.OUTPUT_XLS_PATH + GPath.OUTPUT_XLS_NAME;
		try(FileOutputStream out = new FileOutputStream(strOutputPath)) {
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
			GFile.writeStringErrorToGuideBottom(GMsg.MSG_IOFAILED[1]);
		}

		return result;
	}

	/**
	 *  Excel写入表头：自定义表头
	 *
	 *  @param strHeaders 字段名
	 *  @return 输出成功返回true，否则返回false
	 */
	public boolean writeExcelHead(String[] strHeaders) {
		boolean result = false;

		String strOutputPath = GPath.OUTPUT_XLS_PATH + GPath.OUTPUT_XLS_NAME;
		try(FileOutputStream out = new FileOutputStream(strOutputPath)) {
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
			GFile.writeStringErrorToGuideBottom(GMsg.MSG_IOFAILED[1]);
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
	public boolean initExportExcel(String strPath, String strName) {

		try {
			if(GFile.bIsOpened(strPath)) {
				GFile.writeStringErrorToGuideBottom(GMsg.MSG_ISOPENED[1]);
				System.exit(0);
			}
			GFile.creatDir(GPath.OUTPUT_XLS_PATH);
			GFile.creatXlsFile(strPath);
			File testExcel = new File(strPath);
			if (!testExcel.exists()) {// 文件是否存在
				GFile.writeStringErrorToGuideBottom(GMsg.MSG_NOTFOUND[2]);
				System.exit(0);
			}else {
				if(!GFile.bIsOpened(strPath)) {
					GFile.deleteExcel(strPath);
					GFile.createExcel(strPath, strName, headers);
					if(writeExcelHead()) {
						return true;
					}else {
						GFile.writeStringErrorToGuideBottom(GMsg.MSG_IOFAILED[1]);
					}
				}
			}
		} catch (Exception e) {
			GFile.writeStringErrorToGuideBottom(GMsg.MSG_IOFAILED[2]);
			GLog.logSysFunctionException("initExportExcel", e);
		}

		return false;
	}

	/**
	 *  导出Excel表：输出单行
	 *
	 *  @param reportVO 输出数据结构
	 */
	public void doExportExcelByLine(GXlsRequestVO reportVO) {
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
	public void doExportExcelByList(List<GXlsRequestVO> lstReportVO) {
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
	 *  加载一组参数，写入上表，多次执行
	 */
	public void doExportXls() {
		try {
			GFile.writeStringToGuideBottom("WRITE TYPE [XLS]");
			GFile.writeStringToGuideBottom("WRITE START");
			GFile.writeStringToGuideBottom("THERE ARE " + GTestMission.gTestProgress.getTotalNum().toString() + " RECORDS");
			int i = 1;
			String[][] xls = GTestMission.gtcno.getTCNO_STR().clone();
			String[][] tcResult = GTestMission.gTestResult.getResultString().clone();
			for (;i <= GTestMission.gTestProgress.getTotalNum(); i++) {
				String strInputs = xls[i][6] + "||"
								+ xls[i][7] + "||"
								+ xls[i][8] + "||"
								+ xls[i][9] + "||"
								+ xls[i][10] + "||";
				GXlsRequestVO reportVO = getgXlsRequestVO(xls, i, tcResult);
				doExportExcelByLine(reportVO);
				
				if (GTestMission.gTestPlan.getRecordInputParamListInTxt() != 0 && i <= GTestMission.gTestPlan.getRecordInputParamListInTxt()) {
					String[] logPath = GLog.getLogpath().clone();
					GFile.writeStringToRight(logPath[4], strInputs + "\r\n");
				}		
			}
			GFile.writeStringToGuideBottom("WRITE " + i + " COMPLETE");
		} catch (Exception e) {
			GLog.logSysFunctionException("doExportXls", e);
		}
	}

	/**
	 *  初始化Excel输出流,并输出
	 *  加载一组参数，写入上表，多次执行
	 *
	 * @param xls 报告内容二维表
	 * @param i 序号
	 * @param tcResult 测试结果二维表
	 *
	 * @return GXlsRequestVO对象
	 */
	private static GXlsRequestVO getgXlsRequestVO(String[][] xls, int i, String[][] tcResult) {
		GXlsRequestVO reportVO = new GXlsRequestVO();
		reportVO.setSystemModule(xls[i][0]);
		reportVO.setFunctionPoint(xls[i][1]);
		reportVO.setCaseScription(xls[i][2]);
		reportVO.setPrefixCondition(xls[i][3]);
		reportVO.setCaseStep(xls[i][4]);
		reportVO.setOutputMix("ResultCode:" + tcResult[i -1][0] + ";ResultMessage:" + tcResult[i -1][1]);
		reportVO.setOutputMix1("与预期一致");
		reportVO.setOutputMix2("");
		reportVO.setIsPassed(tcResult[i -1][2]);
		reportVO.setCaseKind("接口测试");
		reportVO.setCasePriority(tcResult[i -1][4]);
		reportVO.setCaseMark("");
		return reportVO;
	}

	/**
	 *  导出Excel表
	 *  
	 *  @return 成功返回true，否则返回false
	 */
	public boolean doExportExcel() {
		long startTime = System.currentTimeMillis();
		GFile.writeStringToGuideBottom(GMissionMsg.getStepTop("XLS REPORT START"));
		GFile.writeStringToGuideBottom(GStatic.gSys.getDate() + " WRITE XLS");
		try {
			if(initExportExcel(GPath.OUTPUT_XLS_PATH + GPath.OUTPUT_XLS_NAME,"测试用例"))
				doExportXls();
			long endTime = System.currentTimeMillis();
			GFile.writeStringToGuideBottom("WRITE XLS -SPEND:" + (endTime - startTime) + "MS");
			GFile.writeStringToGuideBottom("WRITE TO [" + GPath.OUTPUT_XLS_PATH + GPath.OUTPUT_XLS_NAME + "]");
			GFile.writeStringToGuideBottom(GMissionMsg.getStepBottom("XLS REPORT COMPLETE"));

			return true;
		} catch (Exception e) {
			GFile.writeStringErrorToGuideBottom("XLS REPORT FAILED");
			GLog.logSysFunctionException("doExportExcel", e);
		}

		return false;
	}
}
