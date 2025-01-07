package main.java.Base;

/**
 * 任务相关信息
 */
public class GMissionMsg {
	
    /**
     *  构造函数
     */
	private GMissionMsg(){
		GClazz.thisAToolClass();
	}
	
	/**
	 *  获得任务提示。类似于
	 *  **************
	 *  ****任务名称****
	 *  **************
	 *  
	 *  @param misstionName 任务名称，最长80个字符
	 *
	 *  @return misstionTip 任务提示
	 */
	public static String getMisstionTip(String misstionName) {
		String misstionTip = "";
		
		//首行
		misstionTip += GText.getRandomStringByLength(GNumerical.STAR_LINE, "*") + "\n";
		//内容
		int blankNumLeft;
		int blankNumRight;
		//加入一个“*”
		misstionTip += "*";
		//计算并加入指定个数的左侧“ ”
		blankNumLeft = (GNumerical.STAR_LINE - misstionName.length())/2 - 1;
		misstionTip += GText.getRandomStringByLength(blankNumLeft, " ");
		//加入指定内容
		misstionTip += misstionName;
		//计算并加入指定个数的右侧“ ”
		blankNumRight = GNumerical.STAR_LINE - 1 - blankNumLeft - misstionName.length() - 1;
		misstionTip += GText.getRandomStringByLength(blankNumRight, " ");
		//加入换行
		misstionTip += "*" + "\n";
		//尾行
		misstionTip += GText.getRandomStringByLength(GNumerical.STAR_LINE, "*") + "\n";
		
		return misstionTip;
	}
	
	/**
	 *  得到步骤开始分割线。类似于
	 *  ----步骤名称----
	 *  
	 *  @param stepName 步骤名称，最长60个字符
	 *
	 *  @return 步骤开始分割线。类似于
	 */
	public static String getStepStart(String stepName) {
		String stepTip = "";
		
		//内容
		int blankNumLeft;
		int blankNumRight;
		//计算并加入指定个数的左侧“-”
		blankNumLeft = (GNumerical.MINUS_LINE - stepName.length())/2;
		stepTip += GText.getRandomStringByLength(blankNumLeft, "-");
		//加入指定内容
		stepTip += stepName;
		//计算并加入指定个数的右侧“-”
		blankNumRight = GNumerical.MINUS_LINE - blankNumLeft - stepName.length();
		stepTip += GText.getRandomStringByLength(blankNumRight, "-");

		return stepTip;
	}
	
	/**
	 *  得到步骤结束分割线。类似于
	 *  -------------
	 *
	 *  @return 步骤结束分割线
	 */
	public static String getStepEnd() {
		String stepTip = "";
		stepTip += GText.getRandomStringByLength(GNumerical.MINUS_LINE, "-") + "\n";
		return stepTip;
	}
	
	/**
	 *  得到步骤头部提示。类似于
	 *  --------------
	 *  |   步骤名称   |
	 *  
	 *  @param stepName 步骤名称，最长60个字符
	 *
	 *  @return 步骤头部提示
	 */
	public static String getStepTop(String stepName) {
		String stepTip = "";
		//首行
		stepTip += GText.getRandomStringByLength(GNumerical.MINUS_LINE, "-") + "\n";
		//加入一个“|”
		stepTip += "|";
		//内容
		int blankNumLeft;
		int blankNumRight;
		//计算并加入指定个数的左侧“ ”
		blankNumLeft = (GNumerical.MINUS_LINE - stepName.length())/2 - 1;
		stepTip += GText.getRandomStringByLength(blankNumLeft, " ");
		//加入指定内容
		stepTip += stepName;
		//计算并加入指定个数的右侧“ ”
		blankNumRight = GNumerical.MINUS_LINE - 1 - blankNumLeft - stepName.length() - 1;
		stepTip += GText.getRandomStringByLength(blankNumRight, " ");
		//加入一个“|”
		stepTip += "|";

		return stepTip;
	}
	
	/**
	 *  得到步骤尾部提示。类似于
	 *  |   步骤名称   |
	 *  --------------
	 *  
	 *  @param stepName 步骤名称，最长60个字符
	 *
	 *  @return 步骤尾部提示
	 */
	public static String getStepBottom(String stepName) {
		String stepTip = "";
		//加入一个“|”
		stepTip += "|";
		//内容
		int blankNumLeft;
		int blankNumRight;
		//计算并加入指定个数的左侧“ ”
		blankNumLeft = (GNumerical.MINUS_LINE - stepName.length())/2 - 1;
		stepTip += GText.getRandomStringByLength(blankNumLeft, " ");
		//加入指定内容
		stepTip += stepName;
		//计算并加入指定个数的右侧“ ”
		blankNumRight = GNumerical.MINUS_LINE - 1 - blankNumLeft - stepName.length() - 1;
		stepTip += GText.getRandomStringByLength(blankNumRight, " ");
		//加入一个“|”
		stepTip += "|" + "\n";
		//尾行
		stepTip += GText.getRandomStringByLength(GNumerical.MINUS_LINE, "-") + "\n";
		return stepTip;
	}
}
