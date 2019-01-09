package AutoTestWeb;

import AutoTest.GFile;

/**
 * 输出管理
 */
public class GOutPutCtrl {
	
	/**
	 * 输出路径
	 */
	private static String OutputPath = "./output/images/";
	
	/**
	 * 设置输出路径
	 */
	public static void setOutputPath(String path) {
		OutputPath = path;
		GFile.deleteDirectory(OutputPath);//删除旧的文件夹
	}
	
	/**
	 * 获得输出路径
	 */
	public static String getOutputPath() {
		return OutputPath;
	}
}
