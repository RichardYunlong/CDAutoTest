package AutoTest;

/**
 * @see 继承后集成第三方组件
 */
public class GPlugins {
	/**
	 * @see 是否启用备份
	 */
	public static int pluginsNo = 10;// 附加证书秘钥算法
	
	/**
	 * @see 是否启用备份
	 */
	public static String[] pluginsNames = new String[pluginsNo];// 附加证书秘钥长度
	
	/**
	 * @see 是否启用备份
	 */
	GPlugins(){
		for(int i=0;i<pluginsNo;i++) {
			pluginsNames[i]="";
		}
	}
	
}
