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
	/**
	 *  获得内置版本号
	 *
	 *  @return 内置版本号
	 */
	public String getVersion() {
		return version;
	}
	/**
	 *  设置内置版本号
	 *
	 *  @param version 内置版本号
	 */
	public void setVersion(String version) {
		this.version = version;
	}

	/**
	 *  系统欢迎语
	 */
	private String welcome;
	/**
	 *  获得系统欢迎语
	 *
	 *  @return 系统欢迎语
	 */
	public String getWelcome() {
		return welcome;
	}
	/**
	 *  根据开始语生成带"#"边框的系统欢迎语
	 *
	 *  <p>把 {@link #start} 的每一行居中放入 {@code #} 包围的方框中，结果写入 {@link #welcome}。
	 *  通常在启动时调用一次，生成后由 {@link #getWelcome()} 读取。</p>
	 */
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
	/**
	 *  获得系统结束语
	 *
	 *  @return 系统结束语
	 */
	public String getEnding() { return ending; }
	/**
	 *  根据结束语生成带"#"边框的系统结束语
	 *
	 *  <p>把 {@link #end} 的每一行居中放入 {@code #} 包围的方框中，结果写入 {@link #ending}。</p>
	 */
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
	/**
	 *  获得系统迷你结束语
	 *
	 *  @return 系统迷你结束语
	 */
	public String getEndingMini() { return endingMini; }
	/**
	 *  生成迷你版系统结束语
	 *
	 *  <p>与 {@link #setEnding()} 正文相同，只是边框宽度取 {@code GNumerical.MINUS_LINE}，
	 *  适用于宽度受限的控制台区域，结果写入 {@link #endingMini}。</p>
	 */
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
	/**
	 *  获得环境加载提示
	 *
	 *  @return 环境加载提示
	 */
	public String getLoading() {
		return loading;
	}
	/**
	 *  设置环境加载提示
	 *
	 *  @param loading 环境加载提示
	 */
	public void setLoading(String loading) {
		this.loading = loading;
	}

	/**
	 *  环境就绪提示
	 */
	private String complete;
	/**
	 *  获得环境就绪提示
	 *
	 *  @return 环境就绪提示
	 */
	public String getComplete() { return complete; }
	/**
	 *  设置环境就绪提示
	 *
	 *  @param complete 环境就绪提示
	 */
	public void setComplete(String complete) {
		this.complete = complete;
	}

	/**
	 *  开始语
	 *  单行最长90个字符
	 */
	private String[] start;
	/**
	 *  获得开始语
	 *
	 *  @return 开始语
	 */
	public String[] getStart() {
		return start;
	}
	/**
	 *  初始化开始语
	 *
	 *  <p>写入固定的横幅文本（产品名、版本号、开发者、版权信息），供 {@link #setWelcome()} 排版使用。</p>
	 */
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
	/**
	 *  获得结束语
	 *
	 *  @return 结束语
	 */
	public String[] getEnd() {
		return end;
	}
	/**
	 *  初始化结束语
	 *
	 *  <p>写入固定的结束横幅文本（版本号与联系方式），供 {@link #setEnding()} 与
	 *  {@link #setEndingMini()} 排版使用。</p>
	 */
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
