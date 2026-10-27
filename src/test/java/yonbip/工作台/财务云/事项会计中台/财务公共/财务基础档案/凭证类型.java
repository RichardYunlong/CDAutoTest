package yonbip.工作台.财务云.事项会计中台.财务公共.财务基础档案;

import Base.GTime;
import Webdriver.*;
import io.qameta.allure.Step;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class 凭证类型 extends GWCrtlTestCaseRun {
	
	@Test
    @Step("凭证类型")
	public void test() {
		//切换租户
		super.switchTenant("0615预发专业多1");
		//打开菜单
		super.openMenu("财务云", "事项会计中台", "财务公共", "财务基础档案", "凭证类型");

		/**/
		String colName = "code";//字段名
		int aria_rowindex = 11;//视觉上的行号，不是数组下标
		
		GWCtrlWait.ViewWaitingAllByCssSelector(gDr, 10, GParam.getCssSelectorBy3K("表格_行存储器"));
		WebElement table = gDr.findElement(By.cssSelector(GParam.getCssSelectorBy3K("表格_行存储器")));
		GWCtrlWait.ViewWaitingAllByWebElement(gDr, 30, table);
		if(null == table) {
			System.out.println("目标表格不存在");
			return;
		}
		
		List<WebElement> rows = table.findElements(By.cssSelector(GParam.getCssSelectorBy3K("表格_角色行")));
		if(rows.size() >= aria_rowindex - 1) {
			for(WebElement row:rows) {
				if(row.getAttribute("aria-rowindex").equals(String.valueOf(aria_rowindex - 1))) {
					List<WebElement> cells = row.findElements(By.cssSelector(GParam.getCssSelectorBy3K("表格_第一行")));
					if(!cells.isEmpty()) {
						for(WebElement cell:cells) {
							String colSrc = cell.getAttribute("fieldid");
							if(!colSrc.isEmpty()) {
								if(colSrc.contains(colName)) {
//									cell.click();
									GWCtrlMouseMove.ToWebElement(gDr, cell);
									GWCtrlWait.ViewWaitingAllByCssSelector(gDr, 10, GParam.getCssSelectorBy3K("表格_行操作_编辑"));
									WebElement editBtn = row.findElement(By.cssSelector(GParam.getCssSelectorBy3K("表格_行操作_编辑")));
									
									if(null != editBtn){
										editBtn.click();
										
										GWCtrlWait.ViewWaitingAllByCssSelector(gDr, 10, GParam.getCssSelectorBy3K("表格_确认窗"));
										WebElement dialogRoot = gDr.findElement(By.cssSelector(GParam.getCssSelectorBy3K("表格_确认窗")));
										
										if(null != dialogRoot){
											GWCtrlWait.ViewWaitingAllByCssSelector(gDr, 10, GParam.getCssSelectorBy3K("表格_操作栏设置"));
											WebElement meta_default_controls = gDr.findElement(By.cssSelector(GParam.getCssSelectorBy3K("表格_操作栏设置")));
											
											GWCtrlWait.ViewWaitingAllByCssSelector(gDr, 10, GParam.getCssSelectorBy3K("表格_单元格_凭证编码输入"));
											WebElement inputCodeDiv = meta_default_controls.findElement(By.cssSelector(GParam.getCssSelectorBy3K("表格_单元格_凭证编码输入")));
											GWCtrlWait.ViewWaitingAllByWebElement(gDr, 10, inputCodeDiv);
											inputCodeDiv.click();
											
											GWCtrlWait.ViewWaitingAllByCssSelector(gDr, 10, GParam.getCssSelectorBy3K("表格_单元格_凭证编码"));
											WebElement inputCode = dialogRoot.findElement(By.cssSelector(GParam.getCssSelectorBy3K("表格_单元格_凭证编码")));
											GWCtrlWait.ViewWaitingAllByWebElement(gDr, 10, inputCode);
											if(null != inputCode){
												GWCtrlInputFill.ByWebElement(gDr, inputCode, "凭证编码为空");
											}
											
											GWCtrlWait.ViewWaitingAllByCssSelector(gDr, 10, GParam.getCssSelectorBy3K("表格_单元格_凭证类型保存"));
											WebElement btnOk = dialogRoot.findElement(By.cssSelector(GParam.getCssSelectorBy3K("表格_单元格_凭证类型保存")));
											if(null != btnOk){
												btnOk.click();
											}
										}
									}
									
									GTime.pause(10);
									GWCtrlWait.ViewWaitingAllByCssSelector(gDr, 10, GParam.getCssSelectorBy3K("表格_行存储器"));
									break;
								}
							}
						}
					}else {
						System.out.println("目标行不存在任何字段");
					}
				}
			}
		}else {
			System.out.println("不存在第" + aria_rowindex + "行");
		}

		super.closeTopTab("凭证类型");
	}
}
