package main.java.IO;

import main.java.Base.GClazz;
import main.java.Base.GTime;
import main.java.DT.GLog;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 *  Excel表格驱动
 */
public class GExcelBase {

	/**
	 *  构造函数
	 */
	private GExcelBase(){
		GClazz.thisAToolClass();
	}

	private final static short COLOR_NORMAL = 0x7fff;
	private final static short BOLDWEIGHT_NORMAL = 0x190;

	/**
	 *  枚举Excel文件类型
	 */
	public enum Type {
		XLS, XLSX,
	}
	
	/**
	 *  写Excel：删除指定行
	 *  
	 *  @param excelPath 文件全名
	 *  @param sheetIndex 表单序号
	 *  @param rowBeginIndex 开始行
	 *  @param rowEndIndex 结束行
	 *  @param rowCourt 行数
	 *  @return 删除成功返回true，否则返回false
	 */
	public static boolean deleteLine(String excelPath, int sheetIndex, int rowBeginIndex, int rowEndIndex, int rowCourt) {
		boolean result = false;
		
        try(FileInputStream is = new FileInputStream(excelPath);
        	FileOutputStream os = new FileOutputStream(excelPath)){
        	HSSFWorkbook workbook = new HSSFWorkbook(is);  
            HSSFSheet sheet = workbook.getSheetAt(sheetIndex);
            
            sheet.shiftRows(rowBeginIndex, rowEndIndex, rowCourt);
            workbook.write(os);  
            result = true;
        } catch(Exception e) {
        	GLog.logSysFunctionException("deleteLine", e);
        }
        
		return result;
	}

	/**
	 *  写Excel:整个表
	 *  
	 *  @param list 文件全名
	 *  @param title 名称
	 *  @param indexHeader 字段序号
	 *  @param headers 字段名
	 *  @param fields 字段值
	 *  @param widths 列宽
	 *  @param type 类型
	 *  @return 返回工作表
	 */
	public static Workbook write(List<?> list, String title, String indexHeader, String[] headers, String[] fields,
			int[] widths, Type type) {
		Workbook workbook = null;
		try {
			workbook = getWorkbook(type);

			CellStyle cs = getCellStyle(workbook);

			Sheet sheet = workbook.createSheet();
			workbook.setSheetName(0, title);

			int indexNo = 0;
			Row row;
			Cell cell;

			row = sheet.createRow(indexNo++);
			cell = row.createCell(0);
			cell.setCellStyle(cs);
			cell.setCellValue(indexHeader);
			for (int i = 0; i < headers.length; i++) {
				cell = row.createCell(i + 1);
				cell.setCellStyle(cs);
				cell.setCellValue(headers[i]);
			}

			Iterator<?> iterator = list.iterator();
            if (iterator.hasNext()) {
                do {
                    row = sheet.createRow(indexNo++);
                    cell = row.createCell(0);
                    cell.setCellStyle(cs);
                    cell.setCellValue(indexNo - 1);
                    Object obj = iterator.next();
                    for (int i = 0; i < fields.length; i++) {
                        cell = row.createCell(i + 1);
                        cell.setCellStyle(cs);
                        Field field = obj.getClass().getDeclaredField(fields[i]);
                        field.setAccessible(true);
                        String str = String.valueOf(field.get(obj));
                        cell.setCellValue(("null".equals(str) || GTime.isEmpty(str)) ? "" : str);
                    }
                } while (iterator.hasNext());
            }

			for (int i = 0; i < widths.length; i++) {
				sheet.setColumnWidth(i + 1, widths[i]);
			}
		} catch (Exception e) {
			GLog.logRecord(9, "FAIL TO CREATE XLS FILE");
			GLog.logSysFunctionException("write", e);
		}
		return workbook;
	}

	/**
	 *  读Excel
	 *  
	 *  @param inputStream 输入流
	 *  @param headers 字段名
	 *  @param fields 字段值
	 *  @param clazz 类型
	 *  @param maxLimit 最大行值
	 *  @return 返回读入的参数表
	 */
	public static List<?> read(InputStream inputStream, String[] headers, String[] fields, Class<?> clazz, int maxLimit) {
		List<Object> list = null;
		try {
			Sheet sheet = WorkbookFactory.create(inputStream).getSheetAt(0);
			if (sheet.getLastRowNum() > maxLimit) {
				GLog.logRecord(9, "BATCH COUNTS OVERFLOW");
				System.exit(0);
			}
			Row row;
			Cell cell;

			row = sheet.getRow(0);
			if (row == null) {
				GLog.logRecord(9, "BATCH COUNTS MUST NOT BE NULL");
				System.exit(0);
			}
			for (int i = 0; i < headers.length; i++) {
				cell = row.getCell(i);
				if (!headers[i].equals(getValue(cell))) {
					GLog.logRecord(9, "FIELD AND TEMPLATE IS NOT MATCH");
					System.exit(0);
				}
			}

			list = new ArrayList<>();
			Iterator<Row> iterator = sheet.rowIterator();
			while (iterator.hasNext()) {
				row = iterator.next();
				if (row != null) {
					Object obj = clazz.newInstance();
					for (int i = 0; i < fields.length; i++) {
						cell = row.getCell(i);
						Field field = clazz.getDeclaredField(fields[i]);
						field.setAccessible(true);
						field.set(obj, getValue(cell));
					}
					list.add(obj);
				}
			}
		} catch (Exception e) {
			GLog.logRecord(9, "FAIL TO READ XLS FILE");
			GLog.logSysFunctionException("read", e);
		}
		return list;
	}

	/**
	 *  获取单元格的值
	 *  
	 *  @param cell 单元格
	 *  @return 返回单元格的值
	 */
	@SuppressWarnings("deprecation")
    private static String getValue(Cell cell) {
		if (cell == null) {
			return null;
		}
		switch (cell.getCellType()) {
		case Cell.CELL_TYPE_STRING:
			return cell.getRichStringCellValue().getString();
		case Cell.CELL_TYPE_BLANK:
			return "";
		case Cell.CELL_TYPE_NUMERIC:
			if (DateUtil.isCellDateFormatted(cell)) {
				return GTime.getTimeInFormat(cell.getDateCellValue(), GTime.FORMAT_14);
			} else {
				return new DecimalFormat("#").format(cell.getNumericCellValue());
			}
		case Cell.CELL_TYPE_FORMULA:
			return getValue(
					cell.getSheet().getWorkbook().getCreationHelper().createFormulaEvaluator().evaluateInCell(cell));
		case Cell.CELL_TYPE_BOOLEAN:
			return String.valueOf(cell.getBooleanCellValue());
		case Cell.CELL_TYPE_ERROR:
            default:
			return null;
		}
	}

	/**
	 *  获取工作表
	 *  
	 *  @param type 类型
	 *  @return 返回表对象
	 */
	private static Workbook getWorkbook(Type type) {
		if (type == Type.XLSX) {
			return new XSSFWorkbook();
		} else {
			return new HSSFWorkbook();
		}
	}

	/**
	 *  获取单元格类型
	 *  
	 *  @param workbook 数据表
	 *  @return 元素类型
	 */
	private static CellStyle getCellStyle(Workbook workbook) {
		Font font = workbook.createFont();
		font.setColor(Font.COLOR_NORMAL);
		font.setFontHeight(BOLDWEIGHT_NORMAL);
		font.setFontHeightInPoints((short) 14);

		CellStyle cs = workbook.createCellStyle();
		cs.setFillBackgroundColor(Font.COLOR_NORMAL);
		cs.setBorderBottom(BorderStyle.THIN);
		cs.setBorderLeft(BorderStyle.THIN);
		cs.setBorderRight(BorderStyle.THIN);
		cs.setBorderTop(BorderStyle.THIN);
		cs.setAlignment(HorizontalAlignment.forInt(BorderStyle.MEDIUM.getCode()));
		cs.setFont(font);
		return cs;
	}
	
	/**
	 *  Excel表格检查
	 *  
	 *  @param strPath 文件全名
	 *  @return 成功返回true，否则返回false
	 */
	public static boolean checkExcel(String strPath) {
		File testExcel = new File(strPath);
		if (!testExcel.exists()) {// 文件是否存在
			GLog.logShowConsole("XLS NOT EXIST!");
			return false;
		}

		return true;
	}
}
