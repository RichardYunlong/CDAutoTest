package AutoTest;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *  Excel表格驱动
 */
public class GExcel {
	
	/**
	 *  枚举Excel文件类型
	 */
	public enum Type {
		XLS, XLSX,
	}
	
	/**
	 *  写Excel：删除指定行
	 */
	public static boolean deleteLine(String excelPath, int sheetIndex, int rowBeginIndex, int rowEndIndex, int rowCourt) {
		boolean result = false;
        try{   
            FileInputStream is = new FileInputStream(excelPath);  
            HSSFWorkbook workbook = new HSSFWorkbook(is);  
            HSSFSheet sheet = workbook.getSheetAt(sheetIndex);  
            sheet.shiftRows(rowBeginIndex, rowEndIndex, rowCourt);  
            FileOutputStream os = new FileOutputStream(excelPath);  
            workbook.write(os);  
            is.close();  
            os.close();  
            result = true;
        } catch(Exception e) {   
            e.printStackTrace();  
        }
		return result;
	}

	/**
	 *  写Excel:整个表
	 */
	public static Workbook write(List<?> list, String title, String indexHeader, String[] headers, String[] fields,
			int[] widths, Type type) throws Exception {
		try {
			Workbook workbook = getWorkbook(type);

			CellStyle cs = getCellStyle(workbook);

			Sheet sheet = workbook.createSheet();
			workbook.setSheetName(0, title);

			int indexNo = 0;
			Row row = null;
			Cell cell = null;

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
			while (iterator.hasNext()) {
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
			}

			for (int i = 0; i < widths.length; i++) {
				sheet.setColumnWidth(i + 1, widths[i]);
			}

			return workbook;
		} catch (Exception e) {
			throw new Exception("创建EXCEL文件失败", e);
		}
	}

	/**
	 *  读Excel
	 */
	public static List<?> read(InputStream inputStream, String[] headers, String[] fields, Class<?> clazz, int maxLimit)
			throws Exception {
		try {
			Sheet sheet = WorkbookFactory.create(inputStream).getSheetAt(0);
			if (sheet.getLastRowNum() > maxLimit) {
				throw new Exception("批量条数超过限制");
			}
			Row row = null;
			Cell cell = null;

			row = sheet.getRow(0);
			if (row == null) {
				throw new Exception("批量文件不可为空");
			}
			for (int i = 0; i < headers.length; i++) {
				cell = row.getCell(i);
				if (!headers[i].equals(getValue(cell))) {
					throw new Exception("EXCEL文件表头与模板不符");
				}
			}

			List<Object> list = new ArrayList<Object>();
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
			return list;
		} catch (Exception e) {
			throw new Exception("读取EXCEL文件失败", e);
		}
	}

	/**
	 *  获取单元格的值
	 */
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
			return null;
		default:
			return null;
		}
	}

	/**
	 *  获取工作表
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
	 */
	private static CellStyle getCellStyle(Workbook workbook) {
		Font font = workbook.createFont();
		font.setColor(Font.COLOR_NORMAL);
		font.setBoldweight(Font.BOLDWEIGHT_NORMAL);
		font.setFontHeightInPoints((short) 14);

		CellStyle cs = workbook.createCellStyle();
		cs.setFillBackgroundColor(Font.COLOR_NORMAL);
		cs.setBorderBottom(CellStyle.BORDER_THIN);
		cs.setBorderLeft(CellStyle.BORDER_THIN);
		cs.setBorderRight(CellStyle.BORDER_THIN);
		cs.setBorderTop(CellStyle.BORDER_THIN);
		cs.setAlignment(CellStyle.ALIGN_CENTER);
		cs.setFont(font);
		return cs;
	}
}
