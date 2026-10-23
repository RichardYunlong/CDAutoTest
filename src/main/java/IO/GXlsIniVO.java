package IO;

/**
 *  xls配置参数表
 */
public class GXlsIniVO extends GXlsBaseVO {
	
	/**
	 *  列序号，对应配置表第 1 列
	 */
	private String index;
	/**
	 *  模块，对应配置表第 2 列
	 */
	private String module;
	/**
	 *  关键字，对应配置表第 3 列
	 */
	private String keywords;
	/**
	 *  描述，对应配置表第 4 列
	 */
	private String scription;
	/**
	 *  影响数量，对应配置表第 5 列
	 */
	private String number;
	/**
	 *  类型，对应配置表第 6 列
	 */
	private String type;
	/**
	 *  备注，对应配置表第 7 列
	 */
	private String mark;
	
	/**
	 *  获得列序号
	 *
	 *  @return 列序号，对应配置表第 1 列
	 */
	public String getIndex() {
		return index;
	}
	/**
	 *  设置列序号
	 *
	 *  @param index 列序号，对应配置表第 1 列
	 */
	public void setIndex(String index) {
		this.index = index;
	}

	/**
	 *  获得模块，对应配置表第 2 列
	 *
	 *  @return 模块，对应配置表第 2 列
	 */
	public String getModule() {
		return module;
	}
	/**
	 *  设置模块，对应配置表第 2 列
	 *
	 *  @param module 模块，对应配置表第 2 列
	 */
	public void setModule(String module) {
		this.module = module;
	}

	/**
	 *  获得关键字，对应配置表第 3 列
	 *
	 *  @return 关键字，对应配置表第 3 列
	 */
	public String getKeywords() {
		return keywords;
	}
	/**
	 *  设置关键字，对应配置表第 3 列
	 *
	 *  @param keywords 关键字，对应配置表第 3 列
	 */
	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}

	/**
	 *  获得描述，对应配置表第 4 列
	 *
	 *  @return 描述，对应配置表第 4 列
	 */
	public String getScription() {
		return scription;
	}
	/**
	 *  设置描述，对应配置表第 4 列
	 *
	 *  @param scription 描述，对应配置表第 4 列
	 */
	public void setScription(String scription) {
		this.scription = scription;
	}

	/**
	 *  获得影响数量，对应配置表第 5 列
	 *
	 *  @return 影响数量，对应配置表第 5 列
	 */
	public String getNumber() {
		return number;
	}
	/**
	 *  设置影响数量，对应配置表第 5 列
	 *
	 *  @param number 影响数量，对应配置表第 5 列
	 */
	public void setNumber(String number) {
		this.number = number;
	}

	/**
	 *  获得类型，对应配置表第 6 列
	 *
	 *  @return 类型，对应配置表第 6 列
	 */
	public String getType() {
		return type;
	}
	/**
	 *  设置类型，对应配置表第 6 列
	 *
	 *  @param type 类型，对应配置表第 6 列
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 *  获得备注，对应配置表第 7 列
	 *
	 *  @return 备注，对应配置表第 7 列
	 */
	public String getMark() {
		return mark;
	}
	/**
	 *  设置备注，对应配置表第 7 列
	 *
	 *  @param mark 备注，对应配置表第 7 列
	 */
	public void setMark(String mark) {
		this.mark = mark;
	}
}
