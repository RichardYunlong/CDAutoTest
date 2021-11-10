package IO;

/**
 *  xls基础结构
 */
public class GXlsBaseVO {
	
	/**
	 *  表头
	 */
	private String[] headers;
	
	/**
	 *  获取表头
	 */
	public String[] getHeaders() {
		return headers;
	}
	/**
	 *  设置表头
	 */
	public void setHeaders(String[] headers) {
		this.headers = headers;
	}
	
	/**
	 *  字段
	 */
	private String[] fields;
	
	/**
	 *  获取字段
	 */
	public String[] getFields() {
		return fields;
	}
	
	/**
	 *  设置字段
	 */
	public void setFields(String[] fields) {
		this.fields = fields;
	}

}
