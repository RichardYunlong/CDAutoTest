package IO;

/**
 *  xls配置参数表
 */
public class GXlsIniVO extends GXlsBaseVO {
	
	/**
	 *  输入内容字段名称举例：1-序号、2-模块、3-关键字、4-描述、5-影响数量、6-类型、7-备注
	 *  及其获取和设置方法
	 */
	private String index;
	private String module;
	private String keywords;
	private String scription;
	private String number;
	private String type;
	private String mark;
	
	public String getIndex() {
		return index;
	}
	public void setIndex(String index) {
		this.index = index;
	}

	public String getModule() {
		return module;
	}
	public void setModule(String module) {
		this.module = module;
	}

	public String getKeywords() {
		return keywords;
	}
	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}

	public String getScription() {
		return scription;
	}
	public void setScription(String scription) {
		this.scription = scription;
	}

	public String getNumber() {
		return number;
	}
	public void setNumber(String number) {
		this.number = number;
	}

	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}

	public String getMark() {
		return mark;
	}
	public void setMark(String mark) {
		this.mark = mark;
	}
}
