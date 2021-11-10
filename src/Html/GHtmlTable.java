package Html;

/**
 *  html表格操作
 */
public class GHtmlTable {
	
	/**
	 *  表格对象
	 */
	private StringBuilder htmlTable;
	
	public StringBuilder getHtmlTable() {
		return this.htmlTable;
	}
	
	/**
	 *  构造函数
	 */
	public GHtmlTable(){
		this.htmlTable = new StringBuilder();
	}
	
	/**
	 *  设置字段名
	 *  
	 *  @param colTemp 字段名数组
	 *  @param colorTemp 行背景色
	 */
	public void setCol(String[] colTemp, String colorTemp) {
		
		StringBuilder trTemp = new StringBuilder();
		
		trTemp.append("<tr align=\"center\" bgcolor=\"" + colorTemp + "\">");
		
		int colDX = 280;
		for(int i = 0;i < colTemp.length;i++) {
			if(i > 0 && colTemp.length > 1){
				colDX = (1024 - colDX)/(colTemp.length-1);
			}
			trTemp.append("<td style = \"width:" + String.valueOf(colDX) + "px;word-wrap:break-word;\">"
								+ "<font size=\"4\">"
									+ "<b>" 
										+ colTemp[i] 
									+ "</b>"
								+ "</font>"
							+ "</td>"
						);
		}
		trTemp.append("</tr>");
		
		this.htmlTable.append(trTemp.toString());
	}

	/**
	 *  添加记录
	 *  
	 *  @param tdTemp 记录数组
	 *  @param colorTemp 行背景色
	 */
	public void append(String[] tdTemp, String colorTemp) {
		StringBuilder trTemp = new StringBuilder();

		trTemp.append("<tr align=\"center\" bgcolor=\"" + colorTemp + "\">");
			
		if(tdTemp.length > 0) {
			for(int i = 0;i < tdTemp.length;i++) {
				trTemp.append("<td style = \"word-wrap:break-word;\">"
								+ "<font size=\"4\">"
									+ "<b>" 
										+ tdTemp[i] 
									+ "</b>"
								+ "</font>"
							+ "</td>"
				);
			}
		}else {
			trTemp.append("<td style = \"word-wrap:break-word;\">"
							+ "<font size=\"4\">"
								+ "<b>" 
									+ "记录内容字段数量与预期不符，请检查数据格式" 
								+ "</b>"
							+ "</font>"
						+ "</td>"
			);
		}
		
		trTemp.append("</tr>");
		
		this.htmlTable.append(trTemp.toString());
	}
}
