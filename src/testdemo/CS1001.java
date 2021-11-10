package testdemo;

import Base.GAssert;
import DT.GAutoName;
import DT.GException;
import DT.GLog;
import Sys.GParam;
import Test.GTestCase;

/**
 *  被测类样例
 */
public class CS1001 {
	public CS1001() {
		String myName = "";
		String myFemaleEnglishName = "";
		String myMaleEnglishName = "";
		String myEnglishName = "";
		String myAutoName = "";

		switch (GTestCase.getTC_NO().intValue()) {//定义用例编号并初始化用例参数
			case 1001: {
				break;
			}
			case 100101: {
				myName = "预设值1";
				break;
			}
			case 100102: {
				myName = "预设值1";
				myFemaleEnglishName = "预设值2";
				break;
			}
			case 100103: {
				myName = "预设值1";
				myFemaleEnglishName = "预设值2";
				myMaleEnglishName = "预设值3";
				break;
			}
			/* 等等 */
			default:{
				break;
			}
		}

		try {
			GLog.logRecord(9, "CS1001", "req", "<Request>\n<Head>" + "\n" + GTestCase.getTC_NO().toString() + "" + "\n</Head>\n<Body>" + "\n"
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
				GParam.setCode("0000");
				GParam.setMsg("既然没有名字，那就给你一个：" + GAutoName.getRandomName());
				break;
			}
			case 1: {
				GParam.setCode("1111");
				GParam.setMsg("已经有了一个名字，暂时还就叫这个吧");
				GParam.setRes("");
				throw new Exception("CS1001");
			}
			case 2: {
				GParam.setCode("2222");
				GParam.setMsg("有效值为" + usefullCourt.toString() + "," + "已经有了两个名字，选一个吧");
				GParam.setRes("已经有了两个名字，选一个吧");
				break;
			}
			case 3: {
				GParam.setCode("3333");
				GParam.setMsg("名字太多，有点贪心，请减少到1个~");
				GParam.setRes("ResultCode:" + GParam.getCode() + " ResultMsg:" + GParam.getMsg());
				break;
			}
			default:
				break;
			}

			GLog.logRecord(9, "CS1001", "res", "有效值为" + usefullCourt.toString() + "," + "ResultCode:" + GParam.getCode() + " ResultMsg:"
					+ GParam.getMsg());
			GAssert.assertIntegerEqual(Integer.valueOf(GParam.getCode()), 2222);//根据需要断言，如返回码为某字符串，与预定义的2222进行比较，如果存在，则此用例返回了计划内的错误信息

		} catch (Exception e) {
			GException.SwtichTo(e, 1, 9, "[功能]----基本信息填写失败");
		}
	}

}
