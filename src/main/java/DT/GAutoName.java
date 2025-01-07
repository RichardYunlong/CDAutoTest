package DT;

import Base.GClazz;
import Base.GFile;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Random;
import java.util.UUID;

/**
 *  名称
 *  自动获取【用户名称】、【用户证件类型】、【用户证件号码】
 */
public class GAutoName {
	
    /**
     *  构造函数
     */
	private GAutoName(){
		GClazz.thisAToolClass();
	}
	
	/**
	 *  证件类型:共24种
	 */
	protected static final String[] IDENTTYPE = { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", " ", "A", "B", "C",
			"E", "F", "G", "H", "J", "K", "L", "M", "N", "P", "Q", "R", "Z" };

	/**
	 *  提供汉语“姓”
	 */
	protected static final String[] SURNAME = { "赵", "钱", "孙", "李", "周", "吴", "郑", "王", "冯", "陈", "褚", "卫", "蒋", "沈", "韩",
			"杨", "朱", "秦", "尤", "许", "何", "吕", "施", "张", "孔", "曹", "严", "华", "金", "魏", "陶", "姜", "戚", "谢", "邹", "喻",
			"柏", "水", "窦", "章", "云", "苏", "潘", "葛", "奚", "范", "彭", "郎", "鲁", "韦", "昌", "马", "苗", "凤", "花", "方", "俞",
			"任", "袁", "柳", "酆", "鲍", "史", "唐", "费", "廉", "岑", "薛", "雷", "贺", "倪", "汤", "滕", "殷", "罗", "毕", "郝", "邬",
			"安", "常", "乐", "于", "时", "傅", "皮", "卞", "齐", "康", "伍", "余", "元", "卜", "顾", "孟", "平", "黄", "和", "穆", "萧",
			"尹", "姚", "邵", "湛", "汪", "祁", "毛", "禹", "狄", "米", "贝", "明", "臧", "计", "伏", "成", "戴", "谈", "宋", "茅", "庞",
			"熊", "纪", "舒", "屈", "项", "祝", "董", "梁", "杜", "阮", "蓝", "闵", "席", "季", "麻", "强", "贾", "路", "娄", "危", "江",
			"童", "颜", "郭", "梅", "盛", "林", "刁", "钟", "徐", "邱", "骆", "高", "夏", "蔡", "田", "樊", "胡", "凌", "霍", "虞", "万",
			"支", "柯", "昝", "管", "卢", "莫", "经", "房", "裘", "缪", "干", "解", "应", "宗", "丁", "宣", "贲", "邓", "郁", "单", "杭",
			"洪", "包", "诸", "左", "石", "崔", "吉", "钮", "龚", "程", "嵇", "邢", "滑", "裴", "陆", "荣", "翁", "荀", "羊", "于", "惠",
			"甄", "曲", "家", "封", "芮", "羿", "储", "靳", "汲", "邴", "糜", "松", "井", "段", "富", "巫", "乌", "焦", "巴", "弓", "牧",
			"隗", "山", "谷", "车", "侯", "宓", "蓬", "全", "郗", "班", "仰", "秋", "仲", "伊", "宫", "宁", "仇", "栾", "暴", "甘", "钭",
			"厉", "戎", "祖", "武", "符", "刘", "景", "詹", "束", "龙", "叶", "幸", "司", "韶", "郜", "黎", "蓟", "溥", "印", "宿", "白",
			"怀", "蒲", "邰", "从", "鄂", "索", "咸", "籍", "赖", "卓", "蔺", "屠", "蒙", "池", "乔", "阴", "郁", "胥", "能", "苍", "双",
			"闻", "莘", "党", "翟", "谭", "贡", "劳", "逄", "姬", "申", "扶", "堵", "冉", "宰", "郦", "雍", "却", "璩", "桑", "桂", "濮",
			"牛", "寿", "通", "边", "扈", "燕", "冀", "浦", "尚", "农", "温", "别", "庄", "晏", "柴", "瞿", "阎", "充", "慕", "连", "茹",
			"习", "宦", "艾", "鱼", "容", "向", "古", "易", "慎", "戈", "廖", "庾", "终", "暨", "居", "衡", "步", "都", "耿", "满", "弘",
			"匡", "国", "文", "寇", "广", "禄", "阙", "东", "欧", "殳", "沃", "利", "蔚", "越", "夔", "隆", "师", "巩", "厍", "聂", "晁",
			"勾", "敖", "融", "冷", "訾", "辛", "阚", "那", "简", "饶", "空", "曾", "毋", "沙", "乜", "养", "鞠", "须", "丰", "巢", "关",
			"蒯", "相", "查", "后", "荆", "红", "游", "郏", "竺", "权", "逯", "盖", "益", "桓", "公", "仉", "督", "岳", "帅", "缑", "亢",
			"况", "郈", "有", "琴", "归", "海", "晋", "楚", "闫", "法", "汝", "鄢", "涂", "钦", "商", "牟", "佘", "佴", "伯", "赏", "墨",
			"哈", "谯", "篁", "年", "爱", "阳", "佟", "言", "福", "南", "火", "铁", "迟", "漆", "官", "冼", "真", "展", "繁", "檀", "祭",
			"密", "敬", "揭", "舜", "楼", "疏", "冒", "浑", "挚", "胶", "随", "高", "皋", "原", "种", "练", "弥", "仓", "眭", "蹇", "覃",
			"阿", "门", "恽", "来", "綦", "召", "仪", "风", "介", "巨", "木", "京", "狐", "郇", "虎", "枚", "抗", "达", "杞", "苌", "折",
			"麦", "庆", "过", "竹", "端", "鲜", "皇", "亓", "老", "是", "秘", "畅", "邝", "还", "宾", "闾", "辜", "纵", "侴", "万俟", "司马",
			"上官", "欧阳", "夏侯", "诸葛", "闻人", "东方", "赫连", "皇甫", "羊舌", "尉迟", "公羊", "澹台", "公冶", "宗正", "濮阳", "淳于", "单于", "太叔",
			"申屠", "公孙", "仲孙", "轩辕", "令狐", "钟离", "宇文", "长孙", "慕容", "鲜于", "闾丘", "司徒", "司空", "兀官", "司寇", "南门", "呼延", "子车",
			"颛孙", "端木", "巫马", "公西", "漆雕", "车正", "壤驷", "公良", "拓跋", "夹谷", "宰父", "谷梁", "段干", "百里", "东郭", "微生", "梁丘", "左丘",
			"东门", "西门", "南宫", "第五", "公仪", "公乘", "太史", "仲长", "叔孙", "屈突", "尔朱", "东乡", "相里", "胡母", "司城", "张廖", "雍门", "毋丘",
			"贺兰", "綦毋", "屋庐", "独孤", "南郭", "北宫", "王孙" };

	/**
	 *  提供英语-女性-名
	 */
	protected static final String[] FEMALE_FIRSTNAMES = { "Mary", "Patricia", "Linda", "Barbara", "Elizabeth", "Jennifer",
			"Maria", "Susan", "Margaret", "Dorothy", "Lisa", "Nancy", "Karen", "Betty", "Helen", "Sandra", "Donna",
			"Carol", "Ruth", "Sharon", "Michelle", "Laura", "Sarah", "Kimberly", "Deborah", "Jessica", "Shirley",
			"Cynthia", "Angela", "Melissa", "Brenda", "Amy", "Anna", "Rebecca", "Virginia", "Kathleen", "Pamela",
			"Martha", "Debra", "Amanda", "Stephanie", "Carolyn", "Christine", "Marie", "Janet", "Catherine", "Frances",
			"Ann", "Joyce", "Diane", "Alice", "Julie", "Heather", "Teresa", "Doris", "Gloria", "Evelyn", "Jean",
			"Cheryl", "Mildred", "Katherine", "Joan", "Ashley", "Judith", "Rose", "Janice", "Kelly", "Nicole", "Judy",
			"Christina", "Kathy", "Theresa", "Beverly", "Denise", "Tammy", "Irene", "Jane", "Lori", "Rachel", "Marilyn",
			"Andrea", "Kathryn", "Louise", "Sara", "Anne", "Jacqueline", "Wanda", "Bonnie", "Julia", "Ruby", "Lois",
			"Tina", "Phyllis", "Norma", "Paula", "Diana", "Annie", "Lillian", "Emily", "Robin", "Peggy", "Crystal",
			"Gladys", "Rita", "Dawn", "Connie", "Florence", "Tracy", "Edna", "Tiffany", "Carmen", "Rosa", "Cindy",
			"Grace", "Wendy", "Victoria", "Edith", "Kim", "Sherry", "Sylvia", "Josephine", "Thelma", "Shannon",
			"Sheila", "Ethel", "Ellen", "Elaine", "Marjorie", "Carrie", "Charlotte", "Monica", "Esther", "Pauline",
			"Emma", "Juanita", "Anita", "Rhonda", "Hazel", "Amber", "Eva", "Debbie", "April", "Leslie", "Clara",
			"Lucille", "Jamie", "Joanne", "Eleanor", "Valerie", "Danielle", "Megan", "Alicia", "Suzanne", "Michele",
			"Gail", "Bertha", "Darlene", "Veronica", "Jill", "Erin", "Geraldine", "Lauren", "Cathy", "Joann",
			"Lorraine", "Lynn", "Sally", "Regina", "Erica", "Beatrice", "Dolores", "Bernice", "Audrey", "Yvonne",
			"Annette", "June", "Samantha", "Marion", "Dana", "Stacy", "Ana", "Renee", "Ida", "Vivian", "Roberta",
			"Holly", "Brittany", "Melanie", "Loretta", "Yolanda", "Jeanette", "Laurie", "Katie", "Kristen", "Vanessa",
			"Alma", "Sue", "Elsie", "Beth", "Jeanne" };

	/**
	 *  提供英语-男性-名
	 */
	protected static final String[] MALE_FIRSTNAMES = { "James", "John", "Robert", "Michael", "William", "David",
			"Richard", "Charles", "Joseph", "Thomas", "Christopher", "Daniel", "Paul", "Mark", "Donald", "George",
			"Kenneth", "Steven", "Edward", "Brian", "Ronald", "Anthony", "Kevin", "Jason", "Matthew", "Gary", "Timothy",
			"Jose", "Larry", "Jeffrey", "Frank", "Scott", "Eric", "Stephen", "Andrew", "Raymond", "Gregory", "Joshua",
			"Jerry", "Dennis", "Walter", "Patrick", "Peter", "Harold", "Douglas", "Henry", "Carl", "Arthur", "Ryan",
			"Roger", "Joe", "Juan", "Jack", "Albert", "Jonathan", "Justin", "Terry", "Gerald", "Keith", "Samuel",
			"Willie", "Ralph", "Lawrence", "Nicholas", "Roy", "Ben", "jamin", "Bruce", "Brandon", "Adam", "Harry",
			"Fred", "Wayne", "Billy", "Steve", "Louis", "Jeremy", "Aaron", "Randy", "Howard", "Eugene", "Carlos",
			"Russell", "Bobby", "Victor", "Martin", "Ernest", "Phillip", "Todd", "Jesse", "Craig", "Alan", "Shawn",
			"Clarence", "Sean", "Philip", "Chris", "Johnny", "Earl", "Jimmy", "Antonio", "Danny", "Bryan", "Tony",
			"Luis", "Mike", "Stanley", "Leonard", "Nathan", "Dale", "Manuel", "Rodney", "Curtis", "Norman", "Allen",
			"Marvin", "Vincent", "Glenn", "Jeffery", "Travis", "Jeff", "Chad", "Jacob", "Lee", "Melvin", "Alfred",
			"Kyle", "Francis", "Bradley", "Jesus", "Herbert", "Frederick", "Ray", "Joel", "Edwin", "Don", "Eddie",
			"Ricky", "Troy", "Randall", "Barry", "Alexander", "Bernard", "Mario", "Leroy", "Francisco", "Marcus",
			"Micheal", "Theodore", "Clifford", "Miguel", "Oscar", "Jay", "Jim", "Tom", "Calvin", "Alex", "Jon",
			"Ronnie", "Bill", "Lloyd", "Tommy", "Leon", "Derek", "Warren", "Darrell", "Jerome", "Floyd", "Leo", "Alvin",
			"Tim", "Wesley", "Gordon", "Dean", "Greg", "Jorge", "Dustin", "Pedro", "Derrick", "Dan", "Lewis", "Zachary",
			"Corey", "Herman", "Maurice", "Vernon", "Roberto", "Clyde", "Glen", "Hector", "Shane", "Ricardo", "Sam",
			"Rick", "Lester", "Brent", "Ramon", "Charlie", "Tyler", "Gilbert", "Gene" };

	/**
	 *  提供英语-姓氏
	 */
	protected static final String[] LAST_NAMES = { "Smith", "Johnson", "Williams", "Jones", "Brown", "Davis", "Miller",
			"Wilson", "Moore", "Taylor", "Anderson", "Thomas", "Jackson", "White", "Harris", "Martin", "Thompson",
			"Garcia", "Martinez", "Robinson", "Clark", "Rodriguez", "Lewis", "Lee", "Walker", "Hall", "Allen", "Young",
			"Hernandez", "King", "Wright", "Lopez", "Hill", "Scott", "Green", "Adams", "Baker", "Gonzalez", "Nelson",
			"Carter", "Mitchell", "Perez", "Roberts", "Turner", "Phillips", "Campbell", "Parker", "Evans", "Edwards",
			"Collins", "Stewart", "Sanchez", "Morris", "Rogers", "Reed", "Cook", "Morgan", "Bell", "Murphy", "Bailey",
			"Rivera", "Cooper", "Richardson", "Cox", "Howard", "Ward", "Torres", "Peterson", "Gray", "Ramirez", "James",
			"Watson", "Brooks", "Kelly", "Sanders", "Price", "Bennett", "Wood", "Barnes", "Ross", "Henderson",
			"Coleman", "Jenkins", "Perry", "Powell", "Long", "Patterson", "Hughes", "Flores", "Washington", "Butler",
			"Simmons", "Foster", "Gonzales", "Bryant", "Alexander", "Russell", "Griffin", "Diaz", "Hayes", "Myers",
			"Ford", "Hamilton", "Graham", "Sullivan", "Wallace", "Woods", "Cole", "West", "Jordan", "Owens", "Reynolds",
			"Fisher", "Ellis", "Harrison", "Gibson", "Mcdonald", "Cruz", "Marshall", "Ortiz", "Gomez", "Murray",
			"Freeman", "Wells", "Webb", "Simpson", "Stevens", "Tucker", "Porter", "Hunter", "Hicks", "Crawford",
			"Henry", "Boyd", "Mason", "Morales", "Kennedy", "Warren", "Dixon", "Ramos", "Reyes", "Burns", "Gordon",
			"Shaw", "Holmes", "Rice", "Robertson", "Hunt", "Black", "Daniels", "Palmer", "Mills", "Nichols", "Grant",
			"Knight", "Ferguson", "Rose", "Stone", "Hawkins", "Dunn", "Perkins", "Hudson", "Spencer", "Gardner",
			"Stephens", "Payne", "Pierce", "Berry", "Matthews", "Arnold", "Wagner", "Willis", "Ray", "Watkins", "Olson",
			"Carroll", "Duncan", "Snyder", "Hart", "Cunningham", "Bradley", "Lane", "Andrews", "Ruiz", "Harper", "Fox",
			"Riley", "Armstrong", "Carpenter", "Weaver", "Greene", "Lawrence", "Elliott", "Chavez", "Sims", "Austin",
			"Peters", "Kelley", "Franklin", "Lawson" };

	/**
	 *  提供邮箱后缀
	 */
	protected static final String[] EMAIL_SUFFIX = { "qq.com", "126.com", "163.com", "gmail.com", "163.net", "msn.com",
			"hotmail.com", "yahoo.com.cn", "sina.com", "@mail.com", "263.net", "sohu.com", "21cn.com", "sogou.com" };

	/**
	 *  构造汉语“名”
	 *  
	 *  @return 返回随机汉语名
	 */
	public static String getRandomChineseMing() {
		String str = null;
		int highPos;
		int lowPos;
		Random random = null;
		byte[] bArr = new byte[2];
		
		try {
			random = SecureRandom.getInstanceStrong();
		} catch (NoSuchAlgorithmException e) {
			GFile.writeStringErrorToGuideBottom("GET RANDOM OBJECT FAILED[" + Arrays.toString(e.getStackTrace()) +"]");
		}
		
		if(random != null) {
			highPos = (176 + Math.abs(random.nextInt(71)));// 区码，0xA0打头，从第16区开始，即0xB0=11*16=176,16~55一级汉字，56~87二级汉字
			bArr[0] = (byte)(0XFF & highPos);
		}
		
		try {
			random = SecureRandom.getInstanceStrong();
		} catch (NoSuchAlgorithmException e) {
			GFile.writeStringErrorToGuideBottom("GET RANDOM OBJECT FAILED[" + Arrays.toString(e.getStackTrace()) +"]");
		}
		if(random != null) {
			lowPos = 161 + Math.abs(random.nextInt(94));// 位码，0xA0打头，范围第1~94列
			bArr[1] = (byte)(0XFF & lowPos);
		}

		try {
			str = new String(bArr, "GB2312"); // 区位码组合成汉字
		} catch (Exception e) {
			GFile.writeStringErrorToGuideBottom("getRandomChineseMing[" + Arrays.toString(e.getStackTrace()) +"]");
		}
		return str;
	}

	/**
	 *  获取随机汉语名字
	 *  
	 *  @return 返回随机汉语取名
	 */
	public static String getRandomChineseName() {
		String myName;
		Random random = new Random(System.currentTimeMillis());
		int index = random.nextInt(SURNAME.length - 1);
		String name = SURNAME[index]; // 获得一个随机的姓氏
		/* 从常用字中选取一个或两个字作为名 */
		if (random.nextBoolean()) {
			name += getRandomChineseMing() + getRandomChineseMing();
		} else {
			name += getRandomChineseMing();
		}
		myName = name;

		return myName;
	}

    /**
     * 产生n位随机数
     *
     * @return n位随机数
     */
    protected static long generateRandomNumber() {
        return generateRandomNumber(0);
    }

    /**
	 *  产生n位随机数
	 *
	 *  @param n 长度
	 *
	 *  @return n位随机数
	 */
	protected static long generateRandomNumber(int n) {
		if (n < 1) {
			throw new IllegalArgumentException("RANDOM NUMBER MUST BIGGER THAN 0");
		}
        return (long) (Math.random() * 9 * Math.pow(10.0, (double) n - 1.0)) + (long) Math.pow(10.0, (double) n - 1.0);
	}

	/**
	 *  根据随机证件类型获取随机证件号
	 *  
	 *  @param identType 证件类型编码
	 *  @return 返回随机证件号码
	 */
	@SuppressWarnings({"UnreachableCode", "DataFlowIssue"})
    public static String getRandomIdentNoByIdentType(String identType) {
		
		int dIdentTypeIndex = 99;
		for (int i = 0; i < 24; i++) {
			if (identType.equals(IDENTTYPE[i])) {
				dIdentTypeIndex = i;
			}
		}
		
		//居民身份证、军人身份证、武装警察身份证、临时居民身份证
		if(identType.equals("1") || identType.equals("2") || identType.equals("11") || identType.equals("15")) {
			return String.valueOf(generateRandomNumber(18));
		}else {
			switch (dIdentTypeIndex) {
				case 1: return getKeyWordsByIdentType("PP");//护照
				case 3: return getKeyWordsByIdentType("BRC");//工商登记证
				case 4: return getKeyWordsByIdentType("TRC");//税务登记证
				case 5: return getKeyWordsByIdentType("SCC");//股东代码证
				case 6: return getKeyWordsByIdentType("SSC");//社会保障卡
				case 7: return getKeyWordsByIdentType("COC");//组织机构代码证
				case 8: return getKeyWordsByIdentType("BL");//企业营业执照
				case 9: return getKeyWordsByIdentType("LPCC");//法人代码证
				case 10: return getKeyWordsByIdentType("UNK");//未知类型	
				case 12: return getKeyWordsByIdentType("HRP");//港澳居民往来内地通行证		
				case 13: return getKeyWordsByIdentType("TPT");//台湾居民来往大陆通行证		
				case 14: return getKeyWordsByIdentType("RB");//户口簿					
				case 16: return getKeyWordsByIdentType("PC");//警察(警官)证	
				case 17: return getKeyWordsByIdentType("LPCPI");//事业单位法人证书			
				case 18: return getKeyWordsByIdentType("CROLP");//社会团体登记证书			
				case 19: return getKeyWordsByIdentType("PNERC");//民办非企业登记证书			
				case 20: return getKeyWordsByIdentType("RCPROFE");//外国(地区)企业常驻代表机构登记证			
				case 21: return getKeyWordsByIdentType("AI");//政府批文			
				case 22: return getKeyWordsByIdentType("USCCC");//统一社会信用代码证	
				case 23: return getKeyWordsByIdentType("FPRP");//外国人永久居留证			
				case 24: return getKeyWordsByIdentType("RPHXMR");//港澳居民居住证			
				case 25: return getKeyWordsByIdentType("TRRP");//台湾居民居住证			
				case 26: return getKeyWordsByIdentType("OTD");//其他证件类型
				default: return getKeyWordsByIdentType("undefine");
			}
		}
	}

	/**
	 *  根据随机证件类型获取随机证件号
	 *  
	 *  @param strIdentKeys 证件类型关键词
	 *  @return 返回包含证件类型关键词的随机字符串
	 */
	private static String getKeyWordsByIdentType(String strIdentKeys) {
		UUID uuid = UUID.randomUUID();
		
		return strIdentKeys + uuid;
	}
	
	/**
	 *  获得随机证件类型编号
	 *  
	 *  @return 返回随机证件类型编码
	 */
	public static String getRandomIdentType() {
		int dIdentTypeIndex;
		Random random = new Random(System.currentTimeMillis());
		dIdentTypeIndex = random.nextInt(IDENTTYPE.length - 1);
		return IDENTTYPE[dIdentTypeIndex];
	}

	/**
	 *  获取随机女士英文名字
	 *  
	 *  @return 返回随机英文女生全名
	 */
	public static String getRandomFemaleEnglishName() {
		String myName;
		Random random = new Random(System.currentTimeMillis());
		int indexFN = random.nextInt(FEMALE_FIRSTNAMES.length - 1);
		int indexLN = random.nextInt(LAST_NAMES.length - 1);
		String firstName = FEMALE_FIRSTNAMES[indexFN];
		String lastName = LAST_NAMES[indexLN];
		myName = firstName + " " + lastName;

		return myName;
	}

	/**
	 *  获取随机男士英文名字
	 *  
	 *  @return 返回随机英文男士全名
	 */
	public static String getRandomMaleEnglishName() {
		String myName;
		Random random = new Random(System.currentTimeMillis());
		int indexFN = random.nextInt(MALE_FIRSTNAMES.length - 1);
		int indexLN = random.nextInt(LAST_NAMES.length - 1);
		String firstName = MALE_FIRSTNAMES[indexFN];
		String lastName = LAST_NAMES[indexLN];
		myName = firstName + " " + lastName;

		return myName;
	}

	/**
	 *  获取随机英文名字
	 *  
	 *  @return 返回随机英文全名
	 */
	@SuppressWarnings({"UnreachableCode", "ConstantValue"})
    public static String getRandomEnglishName() {
		String myName;
		int indexFN;
		String firstName;
		Random random = new Random(System.currentTimeMillis());
		int index = random.nextInt(1);
		if (index == 0) {
			indexFN = random.nextInt(FEMALE_FIRSTNAMES.length - 1);
			firstName = FEMALE_FIRSTNAMES[indexFN];
		} else {
			indexFN = random.nextInt(MALE_FIRSTNAMES.length - 1);
			firstName = MALE_FIRSTNAMES[indexFN];
		}
		int indexLN = random.nextInt(LAST_NAMES.length - 1);

		String lastName = LAST_NAMES[indexLN];
		myName = firstName + " " + lastName;

		return myName;
	}

	/**
	 *  获取随机名字，中、英文姓名均包含
	 *  
	 *  @return 返回随机全名
	 */
	@SuppressWarnings({"UnreachableCode", "DataFlowIssue"})
    public static String getRandomName() {
		String myName;
		Random random = new Random(System.currentTimeMillis());
		int index = random.nextInt(3);
		switch (index) {
		case 0:
			myName = getRandomChineseName();
			break;
		case 1:
			myName = getRandomFemaleEnglishName();
			break;
		case 2:
			myName = getRandomMaleEnglishName();
			break;
		case 3:
			myName = getRandomEnglishName();
			break;
		default:
			myName = "";
			break;
		}

		return myName;
	}

	/**
	 *  获取随机邮箱后缀
	 *  
	 *  @return 返回随机邮箱后缀
	 */
	public static String getAutoEmailMark() {
		String strEmailSuffix;
		Random random = new Random(System.currentTimeMillis());
		int index = random.nextInt(EMAIL_SUFFIX.length - 1);
		strEmailSuffix = EMAIL_SUFFIX[index];

		return strEmailSuffix;
	}
}
