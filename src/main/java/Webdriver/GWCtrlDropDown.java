package Webdriver;

import Base.GText;
import DT.GLog;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

/**
 *  下拉选择控件
 */
public class GWCtrlDropDown {
	
	/**
	 *  处理下拉菜单：按照元素文字值选中
	 *
	 * 	@param webDriver 浏览器驱动对象
	 *  @param id 元素定位条件id
	 *  @param str 待匹配的文本
	 */
	public static void ByValue(WebDriver webDriver, String id, String str) {
		GLog.logRecordTime(9, "[widget]----[dropdown]----[[");
		try {
			//根据id找到选中值显示框的元素
			WebElement inputField = webDriver.findElement(By.id(id));
			WebElement inputParent = inputField.findElement(By.xpath(".."));
			List<WebElement> inputSpans = inputParent.findElements(By.tagName("span"));
			if(inputSpans != null) {
				for (WebElement inputSpan : inputSpans) {
					if(inputSpan != null) {
						GWCtrlHighLight.apply(webDriver, inputSpan, 1, "");
						inputSpan.click();
						GLog.logRecordTime(9, "----<dropdown<span[" + id + "]>>" + GWCtrlMsg.ui_CLICK[0]);
						break;
					}
				}
			}
			
			List<WebElement> comboLists = webDriver.findElements(By.cssSelector(GText.getCssSelectorTxt("div", "class", "x-combo-list-inner")));
			if(comboLists != null) {
				for (WebElement comboList : comboLists) {
					if(comboList.isDisplayed()) {	
						if(comboList.getAttribute("style") != null && !comboList.getAttribute("style").isEmpty()) {
							List<WebElement> comboListItems = comboList.findElements(By.cssSelector(GText.getCssSelectorTxt("div", "class", "x-combo-list-item")));
							List<WebElement> comboListItemsSelecteds = comboList.findElements(By.cssSelector(GText.getCssSelectorTxt("div", "class", "x-combo-list-item x-combo-selected")));
							
							if(comboListItemsSelecteds != null) {
								for (WebElement comboListItemsSelected : comboListItemsSelecteds) {
									if(!comboListItemsSelected.getText().equals(str)) {
										if(comboListItems != null) {
											for (WebElement comboListItem : comboListItems) {
												if(comboListItem.getText().equals(str)) {
													GWCtrlHighLight.apply(webDriver, comboListItem, 1, "");
													comboListItem.click();
													GLog.logRecordTime(9, "----<dropdown<div[" + str + "]>>" + GWCtrlMsg.ui_CLICK[0]);
													break;
												}
											}
										}
									}else {
										GWCtrlHighLight.apply(webDriver, comboListItemsSelected, 1, "");
										comboListItemsSelected.click();
										GLog.logRecordTime(9, "----<dropdown<div[" + str + "]>>" + GWCtrlMsg.ui_CLICK[0]);
									}		
								}
							}
							break;
						}
					}
				}
			}
		} catch (Exception e) {
			GWCtrlException.switchTo(webDriver, e, 1, 0, "----<exception[dropdown[" + id + "]" + GWCtrlMsg.ui_CLICK[1] + "]>", true);
		}
		GLog.logRecordTime(9, "]]----[dropdown]----[widget]");
	}
	
	/**
	 *  处理下拉菜单：按照元素文字值选中
	 *  1.此方法只用于游标从“基准iframe”开始的操作
	 *
	 *  @param webDriver 浏览器驱动对象
	 *  @param iframeIndex 目标iframe序号
	 *  @param id 元素定位条件id
	 *  @param str 待匹配的文本
	 */
	public static void ByValueDefaultIframe(WebDriver webDriver,
											int iframeIndex,
											String id,
											String str) {
		GLog.logRecordTime(9, "[widget]----[dropdown]----[[");
		GWCtrlFrame.ui_C_SWITCN_ELEMENT(webDriver, GWCtrlWebElementIframe.getIframe(iframeIndex));
		try {
			//根据id找到选中值显示框的元素
			WebElement inputField = webDriver.findElement(By.id(id));
			WebElement inputParent = inputField.findElement(By.xpath(".."));
			List<WebElement> inputSpans = inputParent.findElements(By.tagName("span"));
			if(inputSpans != null) {
				for (WebElement inputSpan : inputSpans) {
					if(inputSpan != null) {
						GWCtrlHighLight.apply(webDriver, inputSpan, 1, "");
						inputSpan.click();
						GLog.logRecordTime(9, "----<dropdown<span[" + id + "]>>" + GWCtrlMsg.ui_CLICK[0]);
						break;
					}
				}
			}
			
			List<WebElement> comboLists = webDriver.findElements(By.cssSelector(GText.getCssSelectorTxt("div", "class", "x-combo-list-inner")));
			if(comboLists != null) {
				for (WebElement comboList : comboLists) {
					if(comboList.isDisplayed()) {	
						if(comboList.getAttribute("style") != null && !comboList.getAttribute("style").isEmpty()) {
							List<WebElement> comboListItems = comboList.findElements(By.cssSelector(GText.getCssSelectorTxt("div", "class", "x-combo-list-item")));
							List<WebElement> comboListItemsSelecteds = comboList.findElements(By.cssSelector(GText.getCssSelectorTxt("div", "class", "x-combo-list-item x-combo-selected")));
							
							if(comboListItemsSelecteds != null) {
								for (WebElement comboListItemsSelected : comboListItemsSelecteds) {
									if(!comboListItemsSelected.getText().equals(str)) {
										if(comboListItems != null) {
											for (WebElement comboListItem : comboListItems) {
												if(comboListItem.getText().equals(str)) {
													GWCtrlHighLight.apply(webDriver, comboListItem, 1, "");
													comboListItem.click();
													GLog.logRecordTime(9, "----<dropdown<div[" + str + "]>>" + GWCtrlMsg.ui_CLICK[0]);
													break;
												}
											}
										}
									}else {
										GWCtrlHighLight.apply(webDriver, comboListItemsSelected, 1, "");
										comboListItemsSelected.click();
										GLog.logRecordTime(9, "----<dropdown<div[" + str + "]>>" + GWCtrlMsg.ui_CLICK[0]);
									}		
								}
							}
							break;
						}
					}
				}
			}
		} catch (Exception e) {
			GWCtrlException.switchTo(webDriver, e, 1, 0, "----<exception[dropdown[" + id + "]" + GWCtrlMsg.ui_CLICK[1] + "]>", true);
		}
		GWCtrlFrame.ui_C_SWITCN_DEFAULT(webDriver);
		GLog.logRecordTime(9, "]]----[dropdown]----[widget]");
	}
	
	/**
	 * 处理下拉菜单：按照元素文字值选中
	 *
	 * @param webDriver 浏览器驱动对象
	 * @param id 呼出控件的input的id
	 * @param cssSelectorText 下拉选项中未选择状态的cssSelectorText
	 * @param cssSelectorTextSelect 下拉选项中选中状态的cssSelectorText
	 * @param str 目标字符串
	 */
	public static void ByValue(WebDriver webDriver,
							   String id,
							   String cssSelectorText,
							   String cssSelectorTextSelect,
							   String str) {
        GLog.logRecordTime(9, "[widget]----[dropdown]----[[");
        try {
            //根据id找到选中值显示框的元素
            WebElement inputField = webDriver.findElement(By.id(id));
            WebElement inputParent = inputField.findElement(By.xpath(".."));
            List<WebElement> inputSpans = inputParent.findElements(By.tagName("span"));
            if(inputSpans != null) {
                for (WebElement inputSpan : inputSpans) {
                    if(inputSpan != null) {
                        GWCtrlHighLight.apply(webDriver, inputSpan, 1, "");
                        inputSpan.click();
                        GLog.logRecordTime(9, "----<dropdown<span[" + id + "]>>" + GWCtrlMsg.ui_CLICK[0]);
                        break;
                    }
                }
            }
            
            List<WebElement> comboLists = webDriver.findElements(By.cssSelector(GText.getCssSelectorTxt("div", "class", "x-combo-list-inner")));
            if(comboLists != null) {
                for (WebElement comboList : comboLists) {
                	if(comboList.isDisplayed()) {		
                		if(comboList.getAttribute("style") != null && !comboList.getAttribute("style").isEmpty()) {
                			//下拉选项中默认选项
                			List<WebElement> comboListItems = comboList.findElements(By.cssSelector(cssSelectorText));
                			//下拉选项中默认选中选项
                			List<WebElement> comboListItemsSelecteds = comboList.findElements(By.cssSelector(cssSelectorTextSelect));
                			for (WebElement comboListItemsSelected : comboListItemsSelecteds) {
                				//先循环当前选择的，是否存在目标字符串
                				if (comboListItemsSelected.getText().equals(str)) {
                					GWCtrlHighLight.apply(webDriver, comboListItemsSelected, 1, "");
                					comboListItemsSelected.click();
                					break;
                				}
                			}
                			for (WebElement comboListItem : comboListItems) {
                				//先循环当前选择的，是否存在目标字符串
                				if (comboListItem.getText().equals(str)) {
                					GWCtrlHighLight.apply(webDriver, comboListItem, 1, "");
                					comboListItem.click();
                					break;
                				}
                			}
                			break;
                		}
                	}
                }
            }
        } catch (Exception e) {
            GWCtrlException.switchTo(webDriver, e, 1, 0, "----<exception[dropdown[" + id + "]" + GWCtrlMsg.ui_CLICK[1] + "]>", true);
        }
        GLog.logRecordTime(9, "]]----[dropdown]----[widget]");
    }
	
	/**
	 *  处理下拉菜单：按照元素的WebElement对象选中
	 *
	 *  @param webDriver 浏览器驱动对象
	 *  @param inputField 元素的WebElement对象
	 *  @param str 待匹配的文本
	 */
	public static void ByElement(WebDriver webDriver,
								 WebElement inputField,
								 String str) {
		GLog.logRecordTime(9, "[widget]----[dropdown]----[[");
		WebElement inputParent;
		try {
	      //根据元素找到选中值显示框的元素
	      inputParent = inputField.findElement(By.xpath(".."));
	      List<WebElement> inputSpans = inputParent.findElements(By.tagName("span"));
	      if(inputSpans != null) {
	          for (WebElement inputSpan : inputSpans) {
	              if(inputSpan != null) {
	                  inputSpan.click();
	                  GLog.logRecordTime(9, "----<dropdown<webElement[" + inputSpan + "]>>" + GWCtrlMsg.ui_CLICK[0]);
	                  break;
	              }
	          }
	      }
	      
	      List<WebElement> comboLists = webDriver.findElements(By.cssSelector(GText.getCssSelectorTxt("div", "class", "x-combo-list-inner")));
	      if(comboLists != null) {
	          for (WebElement comboList : comboLists) {
	              if(comboList.isDisplayed()) {
	                  List<WebElement> comboListItems = comboList.findElements(By.cssSelector(GText.getCssSelectorTxt("div", "class", "x-combo-list-item")));
	                  List<WebElement> comboListItemsSelecteds = comboList.findElements(By.cssSelector(GText.getCssSelectorTxt("div", "class", "x-combo-list-item x-combo-selected")));
	                  
	                  if(comboListItemsSelecteds != null) {
	                      for (WebElement comboListItemsSelected : comboListItemsSelecteds) {
	                          if(!comboListItemsSelected.getText().equals(str)) {
	                              if(comboListItems != null) {
	                                  for (WebElement comboListItem : comboListItems) {
	                                      if(comboListItem.getText().equals(str)) {
	                                          comboListItem.click();
	                                          GLog.logRecordTime(9, "----<dropdown<div[" + str + "]>>" + GWCtrlMsg.ui_CLICK[0]);
	                                          break;
	                                      }
	                                  }
	                              }
	                          }else {
	                              comboListItemsSelected.click();
	                              GLog.logRecordTime(9, "----<dropdown<div[" + str + "]>>" + GWCtrlMsg.ui_CLICK[0]);
	                          }
	                          
	                      }
	                  }
	              }
	          }
	      }
		} catch (Exception e) {
			GWCtrlException.switchTo(webDriver, e, 1, 0, "----<exception[dropdown[" + inputField.toString() + "]" + GWCtrlMsg.ui_CLICK[1] + "]>", true);
		}
		GLog.logRecordTime(9, "]]----[dropdown]----[widget]");
	}
	
	/**
     * 选中下拉
     * 适用于已经呼出的下拉菜单定位及选中。先根据基准列表元素定位下拉列表的范围，即
	 *
	 * @param webDriver 浏览器驱动对象
     * input元素
     * @param input 输入控件
     * 基准列表元素查询条件
     * @param listTagName 列表标签名称 为空时使用下面两项搜多，比如下面两项可以取值为"id;id的值"，然后通过id定位；不为空时，使用cssSelector定位
	 * @param listAtrributeName 列表属性名
	 * @param listAtrributeValue 列表属性值
     * 叶子元素查询条件
     * @param leafTagName 选项标签名称 为空时使用下面两项搜多，比如下面两项可以取值为"id;id的值"，然后通过id定位；不为空时，使用cssSelector定位
     * @param leafAtrributeName 选项属性名
     * @param leafAtrributeValue 选项属性值
     * 目标选项
     * @param str 目标选项值
     */
    public static void ByList(WebDriver webDriver,
							  WebElement input,
    						  String listTagName, 
					    	  String listAtrributeName, 
					    	  String listAtrributeValue, 
					    	  String leafTagName, 
					    	  String leafAtrributeName, 
					          String leafAtrributeValue,
					          String str) {
		GLog.logRecordTime(9, "[widget]----[dropdown]----[[");
		  
	    //根据元素找到选中值显示框的元素
	    WebElement inputParent = input.findElement(By.xpath(".."));
	    List<WebElement> inputSpans = inputParent.findElements(By.tagName("span"));
	    if(inputSpans != null) {
	        for (WebElement inputSpan : inputSpans) {
	            if(inputSpan != null) {
	                inputSpan.click();
	                GLog.logRecordTime(9, "----<dropdown<webElement[" + inputSpan + "]>>" + GWCtrlMsg.ui_CLICK[0]);
	                break;
	            }
	        }
	    }
	      
		WebElement list = null;
		List<WebElement> lists = null;
		List<WebElement> items = null;
		String cssList = GText.getCssSelectorTxt(listTagName, listAtrributeName, listAtrributeValue);
		String cssLeaf = GText.getCssSelectorTxt(leafTagName, leafAtrributeName, leafAtrributeValue);
	  	try {
	  		  //查询下拉列表
			  if(listTagName.isEmpty()) {
				  GLog.logRecordTime(9, "----<list[" + listAtrributeName + ";" + listAtrributeValue + "]>" + GWCtrlMsg.ui_QUERY[0]);
				  list = GWCtrlQuery.ui_Q_V(webDriver, listAtrributeName, listAtrributeValue);
			  }else {
				  GLog.logRecordTime(9, "----<list[" + cssList + "]>" + GWCtrlMsg.ui_QUERY[0]);
				  lists = webDriver.findElements(By.cssSelector(cssList));
			  }
	 
			  //如果下拉列表唯一
			  if(list != null) {
				  System.out.println("----<list[1]>");
				  //如果下拉菜单可见
	        	  if(list.isDisplayed()) {
					  if(leafTagName.isEmpty()) {
						  if(leafAtrributeName.equals("id")) {
							  items = list.findElements(By.id(leafAtrributeValue));  
						  }else{
							  GLog.logRecordTime(9, "----<leaf[this query method is not supported at present]>");
						  }
					  }else{
						  GLog.logRecordTime(9, "----<list[" + cssLeaf + "]>" + GWCtrlMsg.ui_QUERY[0]);
						  items = list.findElements(By.cssSelector(cssLeaf));  
					  }
	        	  }
			  }
			  
			  //如果下拉列表不唯一，但仅有一个菜单可见
			  if(lists != null) {
				  System.out.println("----<lists[" + lists.size() + "]>");
		          for (WebElement tlist:lists) {
		        	  //如果下拉菜单可见
		        	  if(tlist.isDisplayed()) {
						  if(leafTagName.isEmpty()) {
							  if(leafAtrributeName.equals("id")) {
								  items = tlist.findElements(By.id(leafAtrributeValue));
								  break;
							  }else{
								  GLog.logRecordTime(9, "----<leaf[this query method is not supported at present]>");
							  }
						  }else{
							  items = tlist.findElements(By.cssSelector(cssLeaf));  
							  break;
						  }
		        	  }
		          }
			  }
			  
			  if(items != null) {
				  System.out.println("----<items[" + items.size() + "]>");
		          for (WebElement item:items) {
		        	  if(item.getText().equals(str)) {
		        		  item.click();
		        		  GLog.logRecordTime(9, "----<leaf[" + str + "]>" + GWCtrlMsg.ui_CLICK[0]);
		        		  break;
		        	  }
		          }
			  }
		}catch (Exception e) {
			GWCtrlException.switchTo(webDriver, e, 1, 0, "----<exception[dropdown "+ GWCtrlMsg.ui_CLICK[1] + "]", true);
		}
	  	GLog.logRecordTime(9, "]]----[dropdown]----[widget]");
    }
	
	/**
     * 适用于单位下拉框
     *
	 * @param webDriver 浏览器驱动对象
     * @param input  input元素
     * @param strValue 要选择的值
     */
    public static void ByElementUnit(WebDriver webDriver,
									 WebElement input,
									 String strValue) {
    	GLog.logRecordTime(9, "[widget]----[dropdown]----[[");
	  	try {
	      //根据元素找到选中值显示框的元素
	      WebElement inputParent = input.findElement(By.xpath(".."));
	      List<WebElement> inputSpans = inputParent.findElements(By.tagName("span"));
	      if(inputSpans != null) {
	          for (WebElement inputSpan : inputSpans) {
	              if(inputSpan != null) {
	                  inputSpan.click();
	                  GLog.logRecordTime(9, "----<span<webElement[" + inputSpan + "]>>" + GWCtrlMsg.ui_CLICK[0]);
	                  break;
	              }
	          }
	      }
	      boolean isClick = false;
	      List<WebElement> comboLists = webDriver.findElements(By.cssSelector(GText.getCssSelectorTxt("div", "class", "x-combo-list-inner")));
	      if(comboLists != null) {
	          for (WebElement comboList : comboLists) {
	              if(comboList.getAttribute("style") != null && !comboList.getAttribute("style").isEmpty()) {
	                  List<WebElement> liWebElements = comboList.findElements(By.tagName("li"));
	                  for (WebElement liWebElement : liWebElements) {
	                      WebElement a = liWebElement.findElement(By.tagName("a"));
	                      WebElement span = a.findElement(By.tagName("span"));
	                      if (strValue.equals(span.getText())) {
	                          span.click();
	                          GLog.logRecordTime(9, "----<span<str[" + strValue + "]>>被点击了");
	                          isClick = true;
	                          break;
	                      }
	                    
	                  }
	              }else {
	                  continue;
	              }
	          if (isClick) {
	              break;    
	          }    
	          }
	      }
		}catch (Exception e) {
			GWCtrlException.switchTo(webDriver, e, 1, 0, "----<exception[dropdown "+ GWCtrlMsg.ui_CLICK[1] + "]", true);
		}
	  	GLog.logRecordTime(9, "]]----[dropdown]----[widget]");
    }
    
    /**
     * 控件实例见物资处置申请-基本信息-处置物类别
	 *
	 * @param webDriver 浏览器驱动对象
     * @param inputid 呼出控件的元素id
     * @param strValue 目标值
     * @param cssSelectorTxt 可确定控件的css语句文本值
     */
    public static void ByElementCategory(WebDriver webDriver,
										 String inputid,
										 String strValue,
										 String cssSelectorTxt) {
      GLog.logRecordTime(9, "[widget]----[dropdown]----[[");
      try {
        // 根据元素找到选中值显示框元素
        WebElement input = webDriver.findElement(By.id(inputid));
        WebElement inputParent = input.findElement(By.xpath(".."));
        List<WebElement> inputSpans = inputParent.findElements(By.tagName("span"));
        if (inputSpans != null) {
          for (WebElement inputSpan : inputSpans) {
            if (inputSpan != null) {
              inputSpan.click();
              GLog.logRecordTime(9,
                  "----<span<webElement[" + inputSpan + "]>>" + GWCtrlMsg.ui_CLICK[0]);
              break;
            }
          }
        }
        boolean isClick = false;
        // 下拉控件
        List<WebElement> comboLists = webDriver.findElements(By.cssSelector(cssSelectorTxt));
        if (comboLists != null) {
          for (WebElement comboList : comboLists) {
            List<WebElement> liWebElements = comboList.findElements(By.tagName("li"));
            for (WebElement liWebElement : liWebElements) {
              WebElement a = liWebElement.findElement(By.tagName("a"));
              WebElement span = a.findElement(By.tagName("span"));
              if (strValue.equals(span.getText())) {
                span.click();
                GLog.logRecordTime(9, "----<span<str[" + strValue + "]>>被点击了");
                isClick = true;
                break;
              }

            }
            if (isClick) {
              break;
            }
          }
        }
      } catch (Exception e) {
        GWCtrlException.switchTo(webDriver, e, 1, 0, "----<exception[dropdown " + GWCtrlMsg.ui_CLICK[1] + "]",
            true);
      }
      GLog.logRecordTime(9, "]]----[dropdown]----[widget]");
    }
}
