package Base;

/**
 * 系统信息
 */
public class GAbout {
	
    /**
     *  构造函数
     */
	private GAbout(){
		GClazz.thisAToolClass();
	}
	
	/**
	 *  内置版本号
	 */
	private static String version = "3.3.0.0";

	/**
	 *  系统欢迎语
	 */
	private static String welcome;
	
	/**
	 *  系统结束语
	 */
	private static String ending;
	
	/**
	 *  系统迷你结束语
	 */
	private static String endingMini;
	
	/**
	 *  环境加载提示
	 */
	private static String loading;
	
	/**
	 *  环境就绪提示
	 */
	private static String complete;
	
	/**
	 *  开始语
	 *  
	 *  单行最长90个字符
	 */
	private static String[] start;
	
	/**
	 *  结束语
	 *  
	 *  单行最长90个字符
	 */
	private static String[] end;

	public static String getVersion() {
		return version;
	}


	public static void setVersion(String version) {
		GAbout.version = version;
	}


	public static String getWelcome() {
		return welcome;
	}


	public static void setWelcome() {
		StringBuilder content = new StringBuilder("");
		
		//首行
		content.append(GText.getRandomStringByLength(GNumerical.SHARP_LINE, "#") + "\r\n");
		//内容
		int blankNumLeft = 0;
		int blankNumRight = 0;
		for(int i = 0;i < start.length;i++){
			//加入一个“#”
			content.append("#");
			//计算并加入指定个数的左侧“ ”
			blankNumLeft = (GNumerical.SHARP_LINE - start[i].length())/2 - 1;
			content.append(GText.getRandomStringByLength(blankNumLeft, " "));
			//加入指定内容
			content.append(start[i]);
			//计算并加入指定个数的右侧“ ”
			blankNumRight = GNumerical.SHARP_LINE - 1 - blankNumLeft - start[i].length() - 1;
			content.append(GText.getRandomStringByLength(blankNumRight, " "));
			//加入换行
			content.append("#" + "\r\n");
		}
		//尾行
		content.append(GText.getRandomStringByLength(GNumerical.SHARP_LINE, "#") + "\r\n");
		
		welcome = content.toString();
	}


	public static String getEnding() {
		return ending;
	}


	public static void setEnding() {
		StringBuilder content = new StringBuilder("");
		
		//首行
		content.append(GText.getRandomStringByLength(GNumerical.SHARP_LINE, "#") + "\r\n");
		//内容
		int blankNumLeft = 0;
		int blankNumRight = 0;
		for(int i = 0;i < end.length;i++){
			//加入一个“#”
			content.append("#");
			//计算并加入指定个数的左侧“ ”
			blankNumLeft = (GNumerical.SHARP_LINE - end[i].length())/2 - 1;
			content.append(GText.getRandomStringByLength(blankNumLeft, " "));
			//加入指定内容
			content.append(end[i]);
			//计算并加入指定个数的右侧“ ”
			blankNumRight = GNumerical.SHARP_LINE - 1 - blankNumLeft - end[i].length() - 1;
			content.append(GText.getRandomStringByLength(blankNumRight, " "));
			//如果不是最后一行则加入换行
			content.append("#" + "\r\n");
		}
		//尾行
		content.append(GText.getRandomStringByLength(GNumerical.SHARP_LINE, "#") + "\r\n");
		
		ending = content.toString();
	}


	public static String getEndingMini() {
		return endingMini;
	}


	public static void setEndingMini() {
		StringBuilder content = new StringBuilder("");
		//首行
		content.append(GText.getRandomStringByLength(GNumerical.MINUS_LINE, "#") + "\r\n");
		//内容
		int blankNumLeft = 0;
		int blankNumRight = 0;
		for(int i = 0;i < end.length;i++){
			//加入一个“#”
			content.append("#");
			//计算并加入指定个数的左侧“ ”
			blankNumLeft = (GNumerical.MINUS_LINE - end[i].length())/2 - 1;
			content.append(GText.getRandomStringByLength(blankNumLeft, " "));
			//加入指定内容
			content.append(end[i]);
			//计算并加入指定个数的右侧“ ”
			blankNumRight = GNumerical.MINUS_LINE - 1 - blankNumLeft - end[i].length() - 1;
			content.append(GText.getRandomStringByLength(blankNumRight, " "));
			//如果不是最后一行则加入换行
			content.append("#" + "\r\n");
		}
		//尾行
		content.append(GText.getRandomStringByLength(GNumerical.MINUS_LINE, "#") + "\r\n");
		
		endingMini = content.toString();
	}


	public static String getLoading() {
		return loading;
	}


	public static void setLoading(String loading) {
		GAbout.loading = loading;
	}


	public static String getComplete() {
		return complete;
	}


	public static void setComplete(String complete) {
		GAbout.complete = complete;
	}
	
	public static String[] getStart() {
		return start;
	}

	private static void setStart() {
		start = new String[]{
				"WELCOME TO USE", 
				"Autotest",
				"VERSION",
				version,
				"DEV BY", 
				"Wei·He",
				"PRO BY", 
				"Richard·CDragon",
				"FROM", 
				"CDragon Studio",
				"URL", 
				"https://gitee.com/cloudydragon",
				"MOBILE", 
				"13717761827",
				"EMAIL", 
				"hewei200685@hotmail.com"
		};
	}
	
	public static String[] getEnd() {
		return end;
	}

	private static void setEnd() {
		end = new String[]{
				"THANK YOU FOR USING",
				version,
				"ANY QUESTIONS TO SEND E-MAIL TO",
				"hewei200685@hotmail.com"
		};
	}
	
	/**
	 *  设置“系统信息”
	 */
	public static void setDefault(){
		//设置开始信息数组
		setStart();
		//设置结束信息数组
		setEnd();
		//设置片头
		setWelcome();
		//设置片尾
		setEnding();
		//设置只能助理片尾
		setEndingMini();
	}
}
