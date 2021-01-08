package TestClass;

import AutoTest.GAssert;
import AutoTest.GAutoName;
import AutoTest.GException;
import AutoTest.GLog;
import AutoTest.GParam;
import AutoTest.GTestCase;


/**
 *  被测类样例
 */
public class CS1001 {
	public CS1001() {
		String myName = GAutoName.getRandomChineseName();
		String myFemaleEnglishName = GAutoName.getRandomFemaleEnglishName();
		String myMaleEnglishName = GAutoName.getRandomMaleEnglishName();
		String myEnglishName = GAutoName.getRandomEnglishName();
		String myAutoName = GAutoName.getRandomName();

		switch (GTestCase.TC_NO.intValue()) {//定义用例编号并初始化用例参数
			case 1001: {
				myName = "";
				myFemaleEnglishName = "";
				myMaleEnglishName = "";
				myEnglishName = "";
				myAutoName = "";
				break;
			}
			case 100101: {
				myName = "预设值1";
				myFemaleEnglishName = "";
				myMaleEnglishName = "";
				myEnglishName = "";
				myAutoName = "";
				break;
			}
			case 100102: {
				myName = "预设值1";
				myFemaleEnglishName = "预设值2";
				myMaleEnglishName = "";
				myEnglishName = "";
				myAutoName = "";
				break;
			}
			case 100103: {
				myName = "预设值1";
				myFemaleEnglishName = "预设值2";
				myMaleEnglishName = "预设值3";
				myEnglishName = "";
				myAutoName = "";
				break;
			}
			/* 等等 */
			default:
				;
				break;
		}

		try {
			GLog.logRecord(9, "CS1001", "req", "<Request>\n<Head>" + "\n" + GTestCase.TC_NO.toString() + "" + "\n</Head>\n<Body>" + "\n"
					+ myName + "\n" + myFemaleEnglishName + "\n" + myMaleEnglishName + "\n" + myEnglishName + "\n"
					+ myAutoName + "\n" + "</Body>\n</Request>");

			Integer usefullCourt = 0;
			if (!myName.equals(""))
				usefullCourt++;
			if (!myFemaleEnglishName.equals(""))
				usefullCourt++;
			if (!myMaleEnglishName.equals(""))
				usefullCourt++;
			if (!myEnglishName.equals(""))
				usefullCourt++;
			if (!myAutoName.equals(""))
				usefullCourt++;
			switch (usefullCourt.intValue()) {
			case 0: {
				GParam.gCode = "0000";
				GParam.gMsg = "既然没有名字，那就给你一个：" + GAutoName.getRandomName();
				break;
			}
			case 1: {
				GParam.gCode = "1111";
				GParam.gMsg = "已经有了一个名字，暂时还就叫这个吧";
				GParam.gRes = "";
				throw new Exception();
			}
			case 2: {
				GParam.gCode = "2222";
				GParam.gMsg = "有效值为" + usefullCourt.toString() + "," + "已经有了两个名字，选一个吧";
				GParam.gRes = "已经有了两个名字，选一个吧";
				throw new Exception();
			}
			case 3: {
				GParam.gCode = "3333";
				GParam.gMsg = "名字太多，有点贪心，请减少到1个~";
				GParam.gRes = "ResultCode:" + GParam.gCode + " ResultMsg:" + GParam.gMsg;
				return;
			}
			default:
				break;
			}

			GLog.logRecord(9, "CS1001", "res", "有效值为" + usefullCourt.toString() + "," + "ResultCode:" + GParam.gCode + " ResultMsg:"
					+ GParam.gMsg);
			GAssert.assertIntegerEqual(Integer.valueOf(GParam.gCode), 2222);//根据需要断言，如返回码为某字符串，与预定义的2222进行比较，如果存在，则此用例返回了计划内的错误信息

		} catch (Exception e) {
			if (GTestCase.TC_TYPE_RES == 1)//必填项，用于记录程序出现失败类错误信息 
				GParam.gRes = GException.getExceptionAllinformation(e);

			e.printStackTrace();
		}
	}

}
