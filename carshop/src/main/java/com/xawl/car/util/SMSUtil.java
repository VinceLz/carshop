package com.xawl.car.util;

import java.util.Random;

import SmsService.AbsRestClient;
import SmsService.JsonReqClient;
import SmsService.XmlReqClient;

public class SMSUtil {

	public static String getRandNum(int charCount) {
		String charValue = "";
		for (int i = 0; i < charCount; i++) {
			char c = (char) (randomInt(0, 10) + '0');
			charValue += String.valueOf(c);
		}
		return charValue;
	}

	public static int randomInt(int from, int to) {
		Random r = new Random();
		return from + r.nextInt(to - from);
	}

	static AbsRestClient InstantiationRestAPI(boolean enable) {
		if (enable) {
			return new JsonReqClient();
		} else {
			return new XmlReqClient();
		}
	}

	public static void testTemplateSMS(boolean json, String accountSid,
			String authToken, String appId, String templateId, String to,
			String param) {
		try {
			String result = InstantiationRestAPI(json).templateSMS(accountSid,
					authToken, appId, templateId, to, param);
			System.out.println("Response content is: " + result);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
