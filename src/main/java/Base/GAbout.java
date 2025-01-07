package Base;

/**
 * 系统信息
 */
public class GAbout {
	
    /**
     *  构造函数
     */
	public GAbout(){
		GClazz.thisAToolClass();
	}
	
	/**
	 *  内置版本号
	 */
	private String version = "3.4.0.0";
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}

	/**
	 *  系统欢迎语
	 */
	private String welcome;
	public String getWelcome() {
		return welcome;
	}
	public void setWelcome() {
		StringBuilder content = new StringBuilder();

		//首行
		content.append(GText.getRandomStringByLength(GNumerical.SHARP_LINE, "#")).append("\r\n");
		//内容
		int blankNumLeft;
		int blankNumRight;
        for (String s : start) {
            //加入一个“#”
            content.append("#");
            //计算并加入指定个数的左侧“ ”
            blankNumLeft = (GNumerical.SHARP_LINE - s.length()) / 2 - 1;
            content.append(GText.getRandomStringByLength(blankNumLeft, " "));
            //加入指定内容
            content.append(s);
            //计算并加入指定个数的右侧“ ”
            blankNumRight = GNumerical.SHARP_LINE - 1 - blankNumLeft - s.length() - 1;
            content.append(GText.getRandomStringByLength(blankNumRight, " "));
            //加入换行
            content.append("#" + "\r\n");
        }
		//尾行
		content.append(GText.getRandomStringByLength(GNumerical.SHARP_LINE, "#")).append("\r\n");

		welcome = content.toString();
	}
	
	/**
	 *  系统结束语
	 */
	private String ending;
	public String getEnding() { return ending; }
	public void setEnding() {
		StringBuilder content = new StringBuilder();

		//首行
		content.append(GText.getRandomStringByLength(GNumerical.SHARP_LINE, "#")).append("\r\n");
		//内容
		int blankNumLeft;
		int blankNumRight;
        for (String s : end) {
            //加入一个“#”
            content.append("#");
            //计算并加入指定个数的左侧“ ”
            blankNumLeft = (GNumerical.SHARP_LINE - s.length()) / 2 - 1;
            content.append(GText.getRandomStringByLength(blankNumLeft, " "));
            //加入指定内容
            content.append(s);
            //计算并加入指定个数的右侧“ ”
            blankNumRight = GNumerical.SHARP_LINE - 1 - blankNumLeft - s.length() - 1;
            content.append(GText.getRandomStringByLength(blankNumRight, " "));
            //如果不是最后一行则加入换行
            content.append("#" + "\r\n");
        }
		//尾行
		content.append(GText.getRandomStringByLength(GNumerical.SHARP_LINE, "#")).append("\r\n");

		ending = content.toString();
	}
	
	/**
	 *  系统迷你结束语
	 */
	private String endingMini;
	public String getEndingMini() { return endingMini; }
	public void setEndingMini() {
		StringBuilder content = new StringBuilder();
		//首行
		content.append(GText.getRandomStringByLength(GNumerical.MINUS_LINE, "#")).append("\r\n");
		//内容
		int blankNumLeft;
		int blankNumRight;
        for (String s : end) {
            //加入一个“#”
            content.append("#");
            //计算并加入指定个数的左侧“ ”
            blankNumLeft = (GNumerical.MINUS_LINE - s.length()) / 2 - 1;
            content.append(GText.getRandomStringByLength(blankNumLeft, " "));
            //加入指定内容
            content.append(s);
            //计算并加入指定个数的右侧“ ”
            blankNumRight = GNumerical.MINUS_LINE - 1 - blankNumLeft - s.length() - 1;
            content.append(GText.getRandomStringByLength(blankNumRight, " "));
            //如果不是最后一行则加入换行
            content.append("#" + "\r\n");
        }
		//尾行
		content.append(GText.getRandomStringByLength(GNumerical.MINUS_LINE, "#")).append("\r\n");

		endingMini = content.toString();
	}
	
	/**
	 *  环境加载提示
	 */
	private String loading;
	public String getLoading() {
		return loading;
	}
	public void setLoading(String loading) {
		this.loading = loading;
	}

	/**
	 *  环境就绪提示
	 */
	private String complete;
	public String getComplete() { return complete; }
	public void setComplete(String complete) {
		this.complete = complete;
	}

	/**
	 *  开始语
	 *  单行最长90个字符
	 */
	private String[] start;
	public String[] getStart() {
		return start;
	}
	private void setStart() {
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
	
	/**
	 *  结束语
	 *  单行最长90个字符
	 */
	private String[] end;
	public String[] getEnd() {
		return end;
	}
	private void setEnd() {
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
	public void setDefault(){
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
