package page.base;

import org.openqa.selenium.WebElement;

/**
 *  以列表为主要形态的页面共性
 *  
 *  @author hewei
 *  
 *  @version 20220425210500
 */
public class ListBase {

	/**
	 *页面头
	 */
	@SuppressWarnings({"FieldMayBeFinal", "CanBeFinal"})
    private WebElement header = null;
	
	/**
	 *获得页面头
	 *
	 * @return 页面头
	 */
	public WebElement getHeader() {
		return header;
	}
	
	
}
