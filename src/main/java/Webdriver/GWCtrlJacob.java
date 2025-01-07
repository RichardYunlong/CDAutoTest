package main.java.Webdriver;

import autoitx4java.AutoItX;
import com.jacob.activeX.ActiveXComponent;
import com.jacob.com.ComThread;
import com.jacob.com.Dispatch;
import com.jacob.com.LibraryLoader;
import com.jacob.com.Variant;
import org.openqa.selenium.WebDriver;

import java.io.File;

/**
 *  使用java调用Windows控件
 */
public class GWCtrlJacob {
	
    /**
     * 预算文件
     */
    @SuppressWarnings("FieldCanBeLocal")
    private static File file = null;
    
    /**
     * 判断系统位
	 *
	 * @return 系统位
     */
    public static String getOSVersion(){
        return System.getProperty("sun.arch.data.model");
    }
    
    /**
     * 获取java虚拟机32位或64位
     */
    public static void setJacobDllFile(){
        String jacobDllVersion;
        if (getOSVersion().contains("32")){
            jacobDllVersion = "jacob-1.18-x86.dll";
        }else {
            jacobDllVersion = "jacob-1.18-x64.dll";
        }
        file = new File("lib", jacobDllVersion);
        System.setProperty(LibraryLoader.JACOB_DLL_PATH, file.getAbsolutePath());
        System.out.println(System.getProperty("java.library.path"));
    }
	
    /**
     * 控制word样例
	 *
	 * @return 是否控制成功
     */
	@SuppressWarnings("deprecation")
	public static boolean runWord() {
		boolean bCtrl = false;
		
		setJacobDllFile();
		try {
			ComThread.InitSTA();// 初始化com的线程，非常重要！！使用结束后要调用 realease方法 
			//Instantiate objWord //Declare word object 
			ActiveXComponent objWord = new ActiveXComponent("Word.Application"); 
			//Assign a local word object 
			Dispatch wordObject = objWord.getObject();
			//Create a Dispatch Parameter to show the document that is opened 
			Dispatch.put(wordObject, "Visible", new Variant(true));// new Variant(true)表示word应用程序可见
			//Instantiate the Documents Property 
			Dispatch documents = objWord.getProperty("Documents").toDispatch(); //documents表示word的所有文档窗口，（word是多文档应用程序） 
			//Add a new word document, Current Active Document 
			Dispatch document = Dispatch.call(documents, "Add").toDispatch(); // 使用Add命令创建一个新文档，用Open命令可以打开一个现有文档 
			Dispatch wordContent = Dispatch.get(document, "Content").toDispatch(); // 取得word文件的内容 
			Dispatch.call(wordContent, "InsertAfter", "这里是一个段落的内容");//插入一个段落 
			Dispatch paragraphs = Dispatch.get(wordContent, "Paragraphs").toDispatch(); // 所有段落 
			int paragraphCount = Dispatch.get(paragraphs, "Count").toInt(); // 一共的段落数 
			// 找到刚输入的段落，设置格式 
			Dispatch lastParagraph = Dispatch.call(paragraphs, "Item", new Variant(paragraphCount)).toDispatch(); // 最后一段 
			Dispatch lastParagraphRange = Dispatch.get(lastParagraph, "Range").toDispatch(); 
			Dispatch font = Dispatch.get(lastParagraphRange, "Font").toDispatch(); 
			Dispatch.put(font, "Bold", new Variant(true)); // 设置为黑体 
			Dispatch.put(font, "Italic", new Variant(true)); // 设置为斜体 
			Dispatch.put(font, "Name", new Variant("宋体")); // 
			Dispatch.put(font, "Size", new Variant(12)); //小四 
			Dispatch.call(document, "SaveAs", new Variant("C:\\Users\\Hew-d\\Desktop\\abc.doc")); // 保存一个新文档 
			ComThread.Release();//释放com线程。根据jacob的帮助文档，com的线程回收不由java的垃圾回收器处理 
		}catch (Exception e) {
            //noinspection CallToPrintStackTrace
            e.printStackTrace();
		}finally {
			ComThread.Release();
		}
		return bCtrl;
	}
	
    /**
     * 控制word样例
     * 
     * @param filepath 待导入的预算文件全名
	 *
	 * @return 是否控制成功
     */
	public static boolean importBudget(String filepath) {
		boolean bCtrl = false;
		
		setJacobDllFile();
		try {
			ComThread.InitSTA();// 初始化com的线程，非常重要！！使用结束后要调用 realease方法
			
			AutoItX x = new AutoItX();
			//ControlFocus()方法用于识别Window窗口
			x.controlFocus("选择预算", "","Edit1");
			//WinWait()设置10秒钟用于等待窗口的显示
			x.winWait("[CLASS:#32770]","",10);
			//ControlSetText()用于向“文件名”输入框内输入本地文件的路径，如果是在桌面第三个参数直接写文件名
			x.ControlSetText("选择预算", "", "Edit1", filepath);
			
			x.sleep(1000);
			//ControlClick()用于点击上传窗口中的“打开”按钮
			x.controlClick("选择预算", "","Button2");
			
			ComThread.Release();//释放com线程。根据jacob的帮助文档，com的线程回收不由java的垃圾回收器处理 
		}catch (Exception e) {
            //noinspection CallToPrintStackTrace
            e.printStackTrace();
		}finally {
			ComThread.Release();
		}
		return bCtrl;
	}
	
	/**导入预算文件
	 *
	 * @param webDriver 目标驱动
	 * @param text 预算文件路径
	 * @param filepath 是否设置jacob桥接器dll文件
	 * @return 导入预算文件的结果
	 */
	@SuppressWarnings("CallToPrintStackTrace")
    public static boolean importBudget(WebDriver webDriver, String text, String filepath) {
        boolean bCtrl = false;
//        setJacobDllFile();
        try {
            ComThread.InitSTA();// 初始化com的线程，非常重要！！使用结束后要调用 realease方法
            
            AutoItX x = new AutoItX();
            //ControlFocus()方法用于识别Window窗口(等待十秒)
            for (int i = 0; i < 10; i++) {
                
                if (x.controlFocus(text, "","Edit1")) {
                    break;
                }else {
                    GWCtrlTime.Pause(webDriver, 1);
                }
            }
            //WinWait()设置10秒钟用于等待窗口的显示
            x.winWait("[CLASS:#32770]","",10);
            //ControlSetText()用于向“文件名”输入框内输入本地文件的路径，如果是在桌面第三个参数直接写文件名
            x.ControlSetText(text, "", "Edit1", filepath);
            
            x.sleep(1000);
            //ControlClick()用于点击上传窗口中的“打开”按钮
            x.controlClick(text, "","Button2");
            
            ComThread.Release();//释放com线程。根据jacob的帮助文档，com的线程回收不由java的垃圾回收器处理 
            bCtrl = true;
        }catch (Exception e) {
            e.printStackTrace();
        }finally {
            ComThread.Release();
        }
        return bCtrl;
    }
	
	public static void main(String[] agrs) {
	    
  	    ComThread.InitSTA();// 初始化com的线程，非常重要！！使用结束后要调用 realease方法
        AutoItX x = new AutoItX();
        //ControlFocus()方法用于识别Window窗口
        x.controlFocus("打开", "","Edit1");
        //WinWait()设置10秒钟用于等待窗口的显示
        x.winWait("[CLASS:#32770]","",10);
        //ControlSetText()用于向“文件名”输入框内输入本地文件的路径，如果是在桌面第三个参数直接写文件名
        x.ControlSetText("打开", "", "Edit1", "F:\\GEPS产品部文件\\安装包文件\\预算相关安装包\\预算文件\\合同口-预算管理-批量导入（陕西）.GBQ5");
        x.sleep(1000);
        //ControlClick()用于点击上传窗口中的“打开”按钮
        x.controlClick("打开", "","Button2");
        ComThread.Release();
        
	}
}
