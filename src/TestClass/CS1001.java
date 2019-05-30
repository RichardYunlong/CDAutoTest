package TestClass;

import AutoTest.GAssert;
import AutoTest.GAutoName;
import AutoTest.GException;
import AutoTest.GParam;
import AutoTest.GTestCase;

/**
 *  被测类样例
 */
public class CS1001 {
	public CS1001() {
		String MyName = GAutoName.getRandomChineseName();
		String MyFemaleEnglishName = GAutoName.getRandomFemaleEnglishName();
		String MyMaleEnglishName = GAutoName.getRandomMaleEnglishName();
		String MyEnglishName = GAutoName.getRandomEnglishName();
		String MyAutoName = GAutoName.getRandomName();

		switch (GTestCase.TSNO.intValue()) {//定义用例编号并初始化用例参数
			case 1001: {
				MyName = "";
				MyFemaleEnglishName = "";
				MyMaleEnglishName = "";
				MyEnglishName = "";
				MyAutoName = "";
				break;
			}
			case 100101: {
				MyName = "预设值1";
				MyFemaleEnglishName = "";
				MyMaleEnglishName = "";
				MyEnglishName = "";
				MyAutoName = "";
				break;
			}
			case 100102: {
				MyName = "预设值1";
				MyFemaleEnglishName = "预设值2";
				MyMaleEnglishName = "";
				MyEnglishName = "";
				MyAutoName = "";
				break;
			}
			case 100103: {
				MyName = "预设值1";
				MyFemaleEnglishName = "预设值2";
				MyMaleEnglishName = "预设值3";
				MyEnglishName = "";
				MyAutoName = "";
				break;
			}
			/* 等等 */
			default:
				;
				break;
		}

		/* 其他处理过程 */

		try {
			/* 测试类自己的一些处理，如构造请求报文，发送请求报文，接收返回报文等
			* 包括对发送和请求报文的处理 
			* 包括从返回报文中获取重要参数传递给公共变量 
			* GParam.gReq = "更改内容"; GParam.gRes = "更改内容";
			*/

			GParam.gReq = "<Request>\n<Head>" + "\n" + GTestCase.TSNO.toString() + "" + "\n</Head>\n<Body>" + "\n"
					+ MyName + "\n" + MyFemaleEnglishName + "\n" + MyMaleEnglishName + "\n" + MyEnglishName + "\n"
					+ MyAutoName + "\n" + "</Body>\n</Request>";

			GTestCase.RecordInputParams(GParam.gReq, "CS1001");//必填项，用于记录发送报文或者另外自己编写的输入参数表

			Integer usefullCourt = 0;// 如果名字不等于预设值则认为名字获取成功，总数加1;只取到一个名字时为有效，其他情况则抛出异常，没有获取到抛出“失败”，获取到1个以上名字抛出计划内“异常”
			if (!MyName.equals(""))
				usefullCourt++;
			if (!MyFemaleEnglishName.equals(""))
				usefullCourt++;
			if (!MyMaleEnglishName.equals(""))
				usefullCourt++;
			if (!MyEnglishName.equals(""))
				usefullCourt++;
			if (!MyAutoName.equals(""))
				usefullCourt++;
			switch (usefullCourt.intValue()) {
			case 0: {
				GParam.TestResultCode = "0000";
				GParam.TestResultMsg = "既然没有名字，那就给你一个：" + GAutoName.getRandomName();
				break;
			}
			case 1: {
				GParam.TestResultCode = "1111";
				GParam.TestResultMsg = "已经有了一个名字，暂时还就叫这个吧";
				GParam.gRes = "";
				throw new Exception();
			}
			case 2: {
				GParam.TestResultCode = "2222";
				GParam.TestResultMsg = "有效值为" + usefullCourt.toString() + "," + "已经有了两个名字，选一个吧";
				GParam.gRes = "已经有了两个名字，选一个吧";
				throw new Exception();
			}
			case 3: {
				GParam.TestResultCode = "3333";
				GParam.TestResultMsg = "名字太多，有点贪心，请减少到1个~";
				GParam.gRes = "ResultCode:" + GParam.TestResultCode + " ResultMsg:" + GParam.TestResultMsg;
				return;
			}
			default:
				break;
			}

			GParam.gRes = "有效值为" + usefullCourt.toString() + "," + "ResultCode:" + GParam.TestResultCode + " ResultMsg:"
					+ GParam.TestResultMsg;
			GAssert.AssertIntegerEqual(Integer.valueOf(GParam.TestResultCode), 2222);//根据需要断言，如返回码为某字符串，与预定义的2222进行比较，如果存在，则此用例返回了计划内的错误信息

		} catch (Exception e) {
			if (GTestCase.TSSTYLE == 1)//必填项，用于记录程序出现失败类错误信息 
				GParam.gRes = GException.getExceptionAllinformation(e);

			e.printStackTrace();
		}
		System.out.println("请求报文=" + GParam.gReq);// 根据需要在控制台打印请求报文
		System.out.println("响应报文=" + GParam.gRes);// 根据需要在控制台打印返回报文
	}

}
