package AutoTest;

/**
 * 提示语常量
 * @param	public static final String MSG_NOTFOUND[] = {"NO VALUE BE FOUND","NO AVAILABLE VALUE BE FOUND","NO FILE BE FOUND","NO AVAILABLE FILE BE FOUND"};
	public static final String MSG_IOFAILED[] = {"READ FAILED","WRITE FAILED", "CREATE FAILED"};
	public static final String MSG_ISOPENED[] = {"THE TARGET FILE IS NOT OPENEDT","THE TARGET FILE MUST BE CLOSE FIRST"};
	public static final String MSG_FOUND[] = {"A VALUE BE FOUND","AVAILABLE VALUES BE FOUND","A FILE BE FOUND","AVAILABLE FILES BE FOUND"};
	public static final String MSG_EXIST[] = {"FILE EXIST","FILE NOT EXIST"};
	public static final String MSG_EMPTY[] = {"FILE IS EMPTY","FILE IS NOT EMPTY"};
	public static final String MSG_MEASUREMENT[] = {"","TIMES","B","KB","MB","GB","TB"};
	public static final String MSG_CONSOLE[] = {"SOMETHING WRONG WITH PRINTING IN CONSOLE,DETAIL:"}; 
 * */
public class GMsg {
	private GMsg(){
		GLog.logShowConsole("This is a tool class.");
	}
	
	public static final String MSG_NOTFOUND[] = {"NO VALUE BE FOUND","NO AVAILABLE VALUE BE FOUND","NO FILE BE FOUND","NO AVAILABLE FILE BE FOUND"};
	public static final String MSG_IOFAILED[] = {"READ FAILED","WRITE FAILED", "CREATE FAILED", "MUST BE OVERWRITTEN"};
	public static final String MSG_ISOPENED[] = {"THE TARGET FILE IS NOT OPENEDT","THE TARGET FILE MUST BE CLOSE FIRST"};
	public static final String MSG_FOUND[] = {"A VALUE BE FOUND","AVAILABLE VALUES BE FOUND","A FILE BE FOUND","AVAILABLE FILES BE FOUND"};
	public static final String MSG_EXIST[] = {"FILE EXIST","FILE NOT EXIST"};
	public static final String MSG_EMPTY[] = {"FILE IS EMPTY","FILE IS NOT EMPTY"};
	public static final String MSG_MEASUREMENT[] = {"","TIMES","B","KB","MB","GB","TB"};
	public static final String MSG_CONSOLE[] = {"SOMETHING WRONG WITH PRINTING IN CONSOLE,DETAIL:"};
	public static final String MSG_LOGERROR[] = {"UNKOWN LOG TYPE"};
}
