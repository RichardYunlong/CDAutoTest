package AutoTest;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.ss.usermodel.Workbook;

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
	public static String strOutputPath = GParam.TestCaseOutputExcelFullName;
	
	/**
	 *  输入步骤或参数合集
	 */
	public static String InputMix = "";

	/**
	 *  输出结果及描述合集
	 */
	public static String OutputMix = "";
	
	/**
	 *  输出结果储行
	 */
	public static GReportVO OutputLine = null;

	/**
	 *  输出结果储表
	 */
	public static List<GReportVO> OutputList = null;

	/**
	 *  Excel表格检查
	 */
	public static boolean createExcel() {
		long startTime = System.currentTimeMillis();
		GLog.GLogDoReady(GTime.getDate() + " [doCreateExcel] START");

		File testExcel = new File(strOutputPath);
		if (!testExcel.exists()) {// 文件是否存在
			System.out.println("CREATE EXCEL FAIL!");
			IsExportExcelReady = false;
		}

		GLog.GLogDoReady(startTime, "doCreateExcel");

		return IsExportExcelReady;
	}

	/**
	 *  写入Excel表格:写到指定行
	 */
	public static void writeOutputLine(GReportVO line, int indexNo, String outputPath) {
		Workbook workbook = null;
		FileOutputStream fileOutputStream = null;
		try {
			if (line != null) {
				int[] widths = { 6000, 3000, 6000, 6000, 6000, 10000, 6000, 6000, 3000, 3000, 3000, 3000, 3000, 3000,
						6000, 10000, 30000, 8000, 8000 };
				workbook = GExcel.writeLine(line, indexNo, "测试写", "序号", headers(), fields(), widths, GExcel.Type.XLS);
				ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
				workbook.write(outputStream);
				File file = new File(outputPath);
				fileOutputStream = new FileOutputStream(file);
				fileOutputStream.write(outputStream.toByteArray());
				fileOutputStream.flush();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (fileOutputStream != null) {
				try {
					fileOutputStream.close();
				} catch (IOException ioe) {

				}
			}
		}
	}
	
	/**
	 *  写入Excel表格
	 */
	public static void writeOutputList(List<GReportVO> list, String outputPath) {
		Workbook workbook = null;
		FileOutputStream fileOutputStream = null;
		try {
			if (list.size() > 0) {
				int[] widths = { 6000, 3000, 6000, 6000, 6000, 10000, 6000, 6000, 3000, 3000, 3000, 3000, 3000, 3000,
						6000, 10000, 30000, 8000, 8000 };
				workbook = GExcel.write(list, "测试写", "序号", headers(), fields(), widths, GExcel.Type.XLS);
				ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
				workbook.write(outputStream);
				File file = new File(outputPath);
				fileOutputStream = new FileOutputStream(file);
				fileOutputStream.write(outputStream.toByteArray());
				fileOutputStream.flush();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (fileOutputStream != null) {
				try {
					fileOutputStream.close();
				} catch (IOException ioe) {

				}
			}
		}
	}

	/**
	 *  初始化Excel输出流：输出一行，即一个用例执行结果
	 */
	public static GReportVO initOutputLine(String filePath) throws Exception {
		FileInputStream fileInputStream = null;
		File file = null;
		// GFile.WriteStringToRight(GParam.TestCaseInputArrayFullName, InputMix +
		// "用例主要参数表\r\n");
		try {
			file = new File(filePath);
			fileInputStream = new FileInputStream(file);
			GReportVO RequestVO = new GReportVO();
			setFields(RequestVO);
			setHeader(RequestVO);
			GReportVO line = new GReportVO();
			// 构造所有输出，一部分从输入表格读取或读取后组装，另一部分从TSRESULT_TSNO数组即执行结果数组中读取，保存到RequestVO中
			line.setResultCode(GError.TSRESULT_TSNO[0][0]);
			line.setResultMessage(GError.TSRESULT_TSNO[0][1]);
			line.setIsPassed(GError.TSRESULT_TSNO[0][2]);
			line.setCaseKind(GError.TSRESULT_TSNO[0][3]);
			line.setCasePriority(GError.TSRESULT_TSNO[0][4]);
			line.setInputMix(GError.TSRESULT_TSNO[0][5]);
			line.setCaseStep(GError.TSRESULT_TSNO[0][6]);
			line.setOutputMix(GError.TSRESULT_TSNO[0][7]);
			line.setOutputMix1(GError.TSRESULT_TSNO[0][8]);
			line.setOutputMix2(GError.TSRESULT_TSNO[0][9]);
			line.setCaseKind(GError.TSRESULT_TSNO[0][10]);
			line.setCaseMark(GError.TSRESULT_TSNO[0][11]);

			// GFile.WriteStringToRight(GParam.TestCaseInputArrayFullName, InputMix + "\r\n");
			return line;
		} catch (Exception e) {
			throw e;
		} finally {
			if (fileInputStream != null) {
				try {
					fileInputStream.close();
				} catch (IOException ioe) {

				}
			}
		}
	}
	
	/**
	 *  初始化Excel输出流:输出整个表
	 */
	public static List<GReportVO> initOutputList(String filePath, int maxLimit) throws Exception {
		FileInputStream fileInputStream = null;
		File file = null;
		// GFile.WriteStringToRight(GParam.TestCaseInputArrayFullName, InputMix +
		// "用例主要参数表\r\n");
		try {
			file = new File(filePath);
			fileInputStream = new FileInputStream(file);
			GReportVO RequestVO = new GReportVO();
			setFields(RequestVO);
			setHeader(RequestVO);
			@SuppressWarnings("unchecked")
			List<GReportVO> list = (List<GReportVO>) GExcel.read(fileInputStream, RequestVO.getHeaders(),
					RequestVO.getFields(), AutoTest.GReportVO.class, maxLimit);
			List<GReportVO> tmp = new ArrayList<GReportVO>();
			for (int i = 0; i < list.size(); i++) {

				// 构造所有输出，一部分从输入表格读取或读取后组装，另一部分从TSRESULT_TSNO数组即执行结果数组中读取，保存到RequestVO中
				list.get(i).setResultCode(GError.TSRESULT_TSNO[i][0]);
				list.get(i).setResultMessage(GError.TSRESULT_TSNO[i][1]);
				list.get(i).setIsPassed(GError.TSRESULT_TSNO[i][2]);
				list.get(i).setCaseKind(GError.TSRESULT_TSNO[i][3]);
				list.get(i).setCasePriority(GError.TSRESULT_TSNO[i][4]);
				list.get(i).setInputMix(GError.TSRESULT_TSNO[i][5]);
				list.get(i).setCaseStep(GError.TSRESULT_TSNO[i][6]);
				list.get(i).setOutputMix(GError.TSRESULT_TSNO[i][7]);
				list.get(i).setOutputMix1(GError.TSRESULT_TSNO[i][8]);
				list.get(i).setOutputMix2(GError.TSRESULT_TSNO[i][9]);
				list.get(i).setCaseKind(GError.TSRESULT_TSNO[i][10]);
				list.get(i).setCaseMark(GError.TSRESULT_TSNO[i][11]);
				// GFile.WriteStringToRight(GParam.TestCaseInputArrayFullName, InputMix + "\r\n");
				if (i > 0) {
					tmp.add(list.get(i));
				}
			}
			return tmp;
		} catch (Exception e) {
			throw e;
		} finally {
			if (fileInputStream != null) {
				try {
					fileInputStream.close();
				} catch (IOException ioe) {

				}
			}
		}
	}
	
	/**
	 *  写入Excel表格
	 */
	public static void write() throws Exception {
		System.out.println("Must Be OverWritten !");
		throw new NullPointerException("Must Be OverWritten !");
	}
	
	/**
	 *  设置区域变量
	 */
	private static void setFields(GReportVO GReportVO) {
		String[] fields = { "systemModule", "functionPoint", "caseStyle", "caseTSNO", "caseScription", "prefixCondition",
				"caseStep", "outputMix", "outputMix1", "outputMix2", "isPassed", "isPassed", "casePriority", "caseMark" };
		GReportVO.setFields(fields);
	}

	/**
	 *  设置区域提示文字
	 */
	private static void setHeader(GReportVO GReportVO) {
		String[] headers = { "系统模块", "功能点", "用例类型", "用例编号", "用例说明", "前置条件", "步骤描述", "预期结果", "第一轮测试结果", "第二轮测试结果", "是否通过", "接口测试", "用例优先级", "备注" };
		GReportVO.setHeaders(headers);
	}

	/**
	 *  格式化区域文字
	 */
	private static String[] headers() {
		String[] headers = { "系统模块", "功能点", "用例类型", "用例编号", "用例说明", "前置条件", "步骤描述", "预期结果", "第一轮测试结果", "第二轮测试结果", "是否通过", "接口测试", "用例优先级", "备注" };
		return headers;
	}

	/**
	 *  格式化区域变量
	 */
	private static String[] fields() {
		String[] fields = { "systemModule", "functionPoint", "caseStyle", "caseTSNO", "caseScription", "prefixCondition",
				"caseStep", "outputMix", "outputMix1", "outputMix2", "isPassed", "isPassed", "casePriority", "caseMark" };
		return fields;
	}

	/**
	 *  导出Excel表：输出整个表
	 */
	public void doExportExcelByList() {

		long startTime = System.currentTimeMillis();
		GFile.WriteStringToBottom(GLog.LogStyle[9], "\r\n" + GTime.getDate() + " TEST CASE EXPORT LIST START\r\n");
		GLog.GLogDoReady(GTime.getDate() + " [doExportExcelByList] START");

		try {
			// 准备要输出的值
			OutputList = initOutputList(strOutputPath, GParam.curCaseNO_MAX);

			// 写excel
			writeOutputList(OutputList, strOutputPath);

			GLog.GLogDoReady(startTime, "doExportExcelByList");
			GFile.WriteStringToBottom(GLog.LogStyle[9],
					"\r\n" + GTime.getDate() + " TEST CASE EXPORT LIST COMPELETE" + "\r\n");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 *  导出Excel表：输出单行
	 */
	public void doExportExcelByLine() {

		long startTime = System.currentTimeMillis();
		GFile.WriteStringToBottom(GLog.LogStyle[9], "\r\n" + GTime.getDate() + " TEST CASE EXPORT LINE START\r\n");
		GLog.GLogDoReady(GTime.getDate() + " [doExportExcelByLine] START");

		try {
			// 准备要输出的值
			OutputLine = initOutputLine(strOutputPath);
			// 行数加1
			dWriteIndex++;

			// 写excel
			writeOutputLine(OutputLine, dWriteIndex, strOutputPath);

			GLog.GLogDoReady(startTime, "doExportExcelByLine Index = " + dWriteIndex);
			GFile.WriteStringToBottom(GLog.LogStyle[9],
					"\r\n" + GTime.getDate() + " TEST CASE EXPORT LINE COMPELETE" + "\r\n");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
