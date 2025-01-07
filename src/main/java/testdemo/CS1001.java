package main.java.testdemo;

import main.java.Base.GAssert;
import main.java.DT.GAutoName;
import main.java.DT.GException;
import main.java.DT.GLog;
import main.java.Sys.GStatic;
import main.java.Test.GTestMission;

/**
 *  被测类样例
 */
@SuppressWarnings("RedundantSuppression")
public class CS1001 {
	@SuppressWarnings({"UnreachableCode", "ConstantValue"})
    public CS1001() {
		String myName = "";
		String myFemaleEnglishName = "";
		String myMaleEnglishName = "";
		String myEnglishName = "";
		String myAutoName = "";

		switch (GTestMission.gTestCase.getTC_NO()) {//定义用例编号并初始化用例参数
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
			GLog.logRecord(9, "CS1001", "req", "<Request>\n<Head>" + "\n" + GTestMission.gTestCase.getTC_NO().toString() + "\n</Head>\n<Body>" + "\n"
					+ myName + "\n" + myFemaleEnglishName + "\n" + myMaleEnglishName + "\n" + myEnglishName + "\n"
					+ myAutoName + "\n" + "</Body>\n</Request>");

			int usefullCourt = 0;
			if (!myName.isEmpty())
				usefullCourt++;
			if (!myFemaleEnglishName.isEmpty())
				usefullCourt++;
			if (!myMaleEnglishName.isEmpty())
				usefullCourt++;
			if (!myEnglishName.isEmpty())
				usefullCourt++;
			if (!myAutoName.isEmpty())
				usefullCourt++;
			switch (usefullCourt) {
			case 0: {
				GStatic.gP.setCode("0000");
				GStatic.gP.setMsg("既然没有名字，那就给你一个：" + GAutoName.getRandomName());
				break;
			}
			case 1: {
				GStatic.gP.setCode("1111");
				GStatic.gP.setMsg("已经有了一个名字，暂时还就叫这个吧");
				GStatic.gP.setRes("");
				throw new Exception("CS1001");
			}
			case 2: {
				GStatic.gP.setCode("2222");
				GStatic.gP.setMsg("有效值为" + usefullCourt + "," + "已经有了两个名字，选一个吧");
				GStatic.gP.setRes("已经有了两个名字，选一个吧");
				break;
			}
			case 3: {
				GStatic.gP.setCode("3333");
				GStatic.gP.setMsg("名字太多，有点贪心，请减少到1个~");
				GStatic.gP.setRes("ResultCode:" + GStatic.gP.getCode() + " ResultMsg:" + GStatic.gP.getMsg());
				break;
			}
			default:
				break;
			}

			GLog.logRecord(9, "CS1001", "res", "有效值为" + usefullCourt + "," + "ResultCode:" + GStatic.gP.getCode() + " ResultMsg:"
					+ GStatic.gP.getMsg());
			GAssert.assertIntegerEqual(Integer.valueOf(GStatic.gP.getCode()), 2222);//根据需要断言，如返回码为某字符串，与预定义的2222进行比较，如果存在，则此用例返回了计划内的错误信息

		} catch (Exception e) {
			GException.SwtichTo(e, 1, 9, "[功能]----基本信息填写失败");
		}
	}

}
