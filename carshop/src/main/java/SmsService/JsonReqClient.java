/**
 * @author Glan.duanyj
 * @date 2014-05-12
 * @project rest_demo
 */
package SmsService;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.Date;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;

import SmsService.models.AppBill;
import SmsService.models.Callback;
import SmsService.models.ClientBill;
import SmsService.models.TemplateSMS;
import SmsService.models.VoiceCode;

import com.google.gson.Gson;

public class JsonReqClient extends AbsRestClient {
	private static Logger logger = Logger.getLogger(JsonReqClient.class);

	@Override
	public String findAccoutInfo(String accountSid, String authToken)
			throws NoSuchAlgorithmException, KeyManagementException {
		// TODO Auto-generated method stub
		String result = "";
		DefaultHttpClient httpclient = getDefaultHttpClient();
		try {
			// MD5加密
			EncryptUtil encryptUtil = new EncryptUtil();
			// 构造请求URL内容
			String timestamp = DateUtil.dateToStr(new Date(),
					DateUtil.DATE_TIME_NO_SLASH);
			String signature = getSignature(accountSid, authToken, timestamp,
					encryptUtil);
			String url = getStringBuffer().append("/").append(version)
					.append("/Accounts/").append(accountSid).append("")
					.append("?sig=").append(signature).toString();
			logger.info(url);
			HttpResponse response = get("application/json", accountSid,
					authToken, timestamp, url, httpclient, encryptUtil);
			// 获取响应实体信息
			HttpEntity entity = response.getEntity();
			if (entity != null) {
				result = EntityUtils.toString(entity, "UTF-8");
			}
			// 确保HTTP响应内容全部被读出或者内容流被关闭
			EntityUtils.consume(entity);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// 关闭连接
			httpclient.getConnectionManager().shutdown();
		}
		return result;
	}

	@Override
	public String createClient(String accountSid, String authToken,
			String appId, String clientName, String chargeType, String charge,
			String mobile) {
		// TODO Auto-generated method stub
		String result = "";
		DefaultHttpClient httpclient = getDefaultHttpClient();
		try {
			// MD5加密
			EncryptUtil encryptUtil = new EncryptUtil();
			// 构造请求URL内容
			String timestamp = DateUtil.dateToStr(new Date(),
					DateUtil.DATE_TIME_NO_SLASH);
			String signature = getSignature(accountSid, authToken, timestamp,
					encryptUtil);
			String url = getStringBuffer().append("/").append(version)
					.append("/Accounts/").append(accountSid).append("/Clients")
					.append("?sig=").append(signature).toString();
			Client client = new Client();
			client.setAppId(appId);
			client.setFriendlyName(clientName);
			client.setClientType(chargeType);
			client.setCharge(charge);
			client.setMobile(mobile);
			Gson gson = new Gson();
			String body = gson.toJson(client);
			body = "{\"client\":" + body + "}";
			HttpResponse response = post("application/json", accountSid,
					authToken, timestamp, url, httpclient, encryptUtil, body);
			// 获取响应实体信息
			HttpEntity entity = response.getEntity();
			if (entity != null) {
				result = EntityUtils.toString(entity, "UTF-8");
			}
			// 确保HTTP响应内容全部被读出或者内容流被关闭
			EntityUtils.consume(entity);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// 关闭连接
			httpclient.getConnectionManager().shutdown();
		}
		return result;
	}

	@Override
	public String findClients(String accountSid, String authToken,
			String appId, String start, String limit) {
		// TODO Auto-generated method stub
		String result = "";
		DefaultHttpClient httpclient = getDefaultHttpClient();
		try {
			// MD5加密
			EncryptUtil encryptUtil = new EncryptUtil();
			// 构造请求URL内容
			String timestamp = DateUtil.dateToStr(new Date(),
					DateUtil.DATE_TIME_NO_SLASH);
			String signature = getSignature(accountSid, authToken, timestamp,
					encryptUtil);
			String url = getStringBuffer().append("/").append(version)
					.append("/Accounts/").append(accountSid)
					.append("/clientList").append("?sig=").append(signature)
					.toString();
			Client client = new Client();
			client.setAppId(appId);
			client.setStart(start);
			client.setLimit(limit);
			Gson gson = new Gson();
			String body = gson.toJson(client);
			body = "{\"client\":" + body + "}";
			logger.info(body);
			HttpResponse response = post("application/json", accountSid,
					authToken, timestamp, url, httpclient, encryptUtil, body);
			// 获取响应实体信息
			HttpEntity entity = response.getEntity();
			if (entity != null) {
				result = EntityUtils.toString(entity, "UTF-8");
			}
			// 确保HTTP响应内容全部被读出或者内容流被关闭
			EntityUtils.consume(entity);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// 关闭连接
			httpclient.getConnectionManager().shutdown();
		}
		return result;
	}

	@Override
	public String findClientByNbr(String accountSid, String authToken,
			String clientNumber, String appId) {
		// TODO Auto-generated method stub
		String result = "";
		DefaultHttpClient httpclient = getDefaultHttpClient();
		try {
			// MD5加密
			EncryptUtil encryptUtil = new EncryptUtil();
			// 构造请求URL内容
			String timestamp = DateUtil.dateToStr(new Date(),
					DateUtil.DATE_TIME_NO_SLASH);
			String signature = getSignature(accountSid, authToken, timestamp,
					encryptUtil);
			String url = getStringBuffer().append("/").append(version)
					.append("/Accounts/").append(accountSid).append("/Clients")
					.append("?sig=").append(signature).append("&clientNumber=")
					.append(clientNumber).append("&appId=").append(appId)
					.toString();
			HttpResponse response = get("application/json", accountSid,
					authToken, timestamp, url, httpclient, encryptUtil);
			// 获取响应实体信息
			HttpEntity entity = response.getEntity();
			if (entity != null) {
				result = EntityUtils.toString(entity, "UTF-8");
			}
			EntityUtils.consume(entity);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// 关闭连接
			httpclient.getConnectionManager().shutdown();
		}
		return result;
	}

	@Override
	public String closeClient(String accountSid, String authToken,
			String clientId, String appId) {
		// TODO Auto-generated method stub
		String result = "";
		DefaultHttpClient httpclient = getDefaultHttpClient();
		try {
			// MD5加密
			EncryptUtil encryptUtil = new EncryptUtil();
			// 构造请求URL内容
			String timestamp = DateUtil.dateToStr(new Date(),
					DateUtil.DATE_TIME_NO_SLASH);// 时间戳
			String signature = getSignature(accountSid, authToken, timestamp,
					encryptUtil);
			String url = getStringBuffer().append("/").append(version)
					.append("/Accounts/").append(accountSid)
					.append("/dropClient").append("?sig=").append(signature)
					.toString();
			Client client = new Client();
			client.setClientNumber(clientId);
			client.setAppId(appId);
			Gson gson = new Gson();
			String body = gson.toJson(client);
			body = "{\"client\":" + body + "}";
			logger.info(body);
			HttpResponse response = post("application/json", accountSid,
					authToken, timestamp, url, httpclient, encryptUtil, body);
			// 获取响应实体信息
			HttpEntity entity = response.getEntity();
			if (entity != null) {
				result = EntityUtils.toString(entity, "UTF-8");
			}
			// 确保HTTP响应内容全部被读出或者内容流被关闭
			EntityUtils.consume(entity);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// 关闭连接
			httpclient.getConnectionManager().shutdown();
		}
		return result;
	}

	@Override
	public String charegeClient(String accountSid, String authToken,
			String clientNumber, String chargeType, String charge, String appId) {
		// TODO Auto-generated method stub
		String result = "";
		DefaultHttpClient httpclient = getDefaultHttpClient();
		try {
			// MD5加密
			EncryptUtil encryptUtil = new EncryptUtil();
			// 构造请求URL内容
			String timestamp = DateUtil.dateToStr(new Date(),
					DateUtil.DATE_TIME_NO_SLASH);// 时间戳
			String signature = getSignature(accountSid, authToken, timestamp,
					encryptUtil);
			String url = getStringBuffer().append("/2014-06-30/Accounts/")
					.append(accountSid).append("/chargeClient").append("?sig=")
					.append(signature).toString();
			Client client = new Client();
			client.setClientNumber(clientNumber);
			client.setChargeType(chargeType);
			client.setCharge(charge);
			client.setAppId(appId);
			Gson gson = new Gson();
			String body = gson.toJson(client);
			body = "{\"client\":" + body + "}";
			logger.info(body);
			HttpResponse response = post("application/json", accountSid,
					authToken, timestamp, url, httpclient, encryptUtil, body);
			// 获取响应实体信息
			HttpEntity entity = response.getEntity();
			if (entity != null) {
				result = EntityUtils.toString(entity, "UTF-8");
			}
			// 确保HTTP响应内容全部被读出或者内容流被关闭
			EntityUtils.consume(entity);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// 关闭连接
			httpclient.getConnectionManager().shutdown();
		}
		return result;
	}

	@Override
	public String billList(String accountSid, String authToken, String appId,
			String date) {
		// TODO Auto-generated method stub
		// TODO Auto-generated method stub
		String result = "";
		DefaultHttpClient httpclient = getDefaultHttpClient();
		try {
			// MD5加密
			EncryptUtil encryptUtil = new EncryptUtil();
			// 构造请求URL内容
			String timestamp = DateUtil.dateToStr(new Date(),
					DateUtil.DATE_TIME_NO_SLASH);// 时间戳
			String signature = getSignature(accountSid, authToken, timestamp,
					encryptUtil);
			String url = getStringBuffer().append("/").append(version)
					.append("/Accounts/").append(accountSid)
					.append("/billList").append("?sig=").append(signature)
					.toString();
			AppBill appBill = new AppBill();
			appBill.setAppId(appId);
			appBill.setDate(date);
			Gson gson = new Gson();
			String body = gson.toJson(appBill);
			body = "{\"appBill\":" + body + "}";
			logger.info(body);
			HttpResponse response = post("application/json", accountSid,
					authToken, timestamp, url, httpclient, encryptUtil, body);
			// 获取响应实体信息
			HttpEntity entity = response.getEntity();
			if (entity != null) {
				result = EntityUtils.toString(entity, "UTF-8");
			}
			// 确保HTTP响应内容全部被读出或者内容流被关闭
			EntityUtils.consume(entity);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// 关闭连接
			httpclient.getConnectionManager().shutdown();
		}
		return result;
	}

	@Override
	public String clientBillList(String accountSid, String authToken,
			String appId, String clientNumber, String date) {
		// TODO Auto-generated method stub
		// TODO Auto-generated method stub
		String result = "";
		DefaultHttpClient httpclient = getDefaultHttpClient();
		try {
			// MD5加密
			EncryptUtil encryptUtil = new EncryptUtil();
			// 构造请求URL内容
			String timestamp = DateUtil.dateToStr(new Date(),
					DateUtil.DATE_TIME_NO_SLASH);// 时间戳
			String signature = getSignature(accountSid, authToken, timestamp,
					encryptUtil);
			String url = getStringBuffer().append("/").append(version)
					.append("/Accounts/").append(accountSid)
					.append("/Clients/billList").append("?sig=")
					.append(signature).toString();
			ClientBill clientBill = new ClientBill();
			clientBill.setAppId(appId);
			clientBill.setClientNumber(clientNumber);
			clientBill.setDate(date);
			Gson gson = new Gson();
			String body = gson.toJson(clientBill);
			body = "{\"clientBill\":" + body + "}";
			logger.info(body);
			HttpResponse response = post("application/json", accountSid,
					authToken, timestamp, url, httpclient, encryptUtil, body);
			// 获取响应实体信息
			HttpEntity entity = response.getEntity();
			if (entity != null) {
				result = EntityUtils.toString(entity, "UTF-8");
			}
			// 确保HTTP响应内容全部被读出或者内容流被关闭
			EntityUtils.consume(entity);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// 关闭连接
			httpclient.getConnectionManager().shutdown();
		}
		return result;
	}

	@Override
	public String callback(String accountSid, String authToken, String appId,
			String fromClient, String to, String fromSerNum, String toSerNum) {
		// TODO Auto-generated method stub
		String result = "";
		DefaultHttpClient httpclient = getDefaultHttpClient();
		try {
			// MD5加密
			EncryptUtil encryptUtil = new EncryptUtil();
			// 构造请求URL内容
			String timestamp = DateUtil.dateToStr(new Date(),
					DateUtil.DATE_TIME_NO_SLASH);// 时间戳
			String signature = getSignature(accountSid, authToken, timestamp,
					encryptUtil);
			String url = getStringBuffer().append("/").append(version)
					.append("/Accounts/").append(accountSid)
					.append("/Calls/callBack").append("?sig=")
					.append(signature).toString();
			Callback callback = new Callback();
			callback.setAppId(appId);
			callback.setFromClient(fromClient);
			callback.setTo(to);
			callback.setFromSerNum(fromSerNum);
			callback.setToSerNum(toSerNum);
			Gson gson = new Gson();
			String body = gson.toJson(callback);
			body = "{\"callback\":" + body + "}";
			logger.info(body);
			HttpResponse response = post("application/json", accountSid,
					authToken, timestamp, url, httpclient, encryptUtil, body);
			HttpEntity entity = response.getEntity();
			if (entity != null) {
				result = EntityUtils.toString(entity, "UTF-8");
			}
			EntityUtils.consume(entity);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// 关闭连接
			httpclient.getConnectionManager().shutdown();
		}
		return result;
	}

	@Override
	public String voiceCode(String accountSid, String authToken, String appId,
			String to, String verifyCode) {
		// TODO Auto-generated method stub
		String result = "";
		DefaultHttpClient httpclient = getDefaultHttpClient();
		try {
			// MD5加密
			EncryptUtil encryptUtil = new EncryptUtil();
			// 构造请求URL内容
			String timestamp = DateUtil.dateToStr(new Date(),
					DateUtil.DATE_TIME_NO_SLASH);// 时间戳
			String signature = getSignature(accountSid, authToken, timestamp,
					encryptUtil);
			String url = getStringBuffer().append("/").append(version)
					.append("/Accounts/").append(accountSid)
					.append("/Calls/voiceCode").append("?sig=")
					.append(signature).toString();
			VoiceCode voiceCode = new VoiceCode();
			voiceCode.setAppId(appId);
			voiceCode.setVerifyCode(verifyCode);
			voiceCode.setTo(to);
			Gson gson = new Gson();
			String body = gson.toJson(voiceCode);
			body = "{\"voiceCode\":" + body + "}";
			logger.info(body);
			HttpResponse response = post("application/json", accountSid,
					authToken, timestamp, url, httpclient, encryptUtil, body);
			HttpEntity entity = response.getEntity();
			if (entity != null) {
				result = EntityUtils.toString(entity, "UTF-8");
			}
			EntityUtils.consume(entity);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// 关闭连接
			httpclient.getConnectionManager().shutdown();
		}
		return result;
	}

	@Override
	public String templateSMS(String accountSid, String authToken,
			String appId, String templateId, String to, String param) {
		// TODO Auto-generated method stub
		String result = "";
		DefaultHttpClient httpclient = getDefaultHttpClient();
		try {
			// MD5加密
			EncryptUtil encryptUtil = new EncryptUtil();
			// 构造请求URL内容
			String timestamp = DateUtil.dateToStr(new Date(),
					DateUtil.DATE_TIME_NO_SLASH);// 时间戳
			String signature = getSignature(accountSid, authToken, timestamp,
					encryptUtil);
			String url = getStringBuffer().append("/").append(version)
					.append("/Accounts/").append(accountSid)
					.append("/Messages/templateSMS").append("?sig=")
					.append(signature).toString();
			TemplateSMS templateSMS = new TemplateSMS();
			templateSMS.setAppId(appId);
			templateSMS.setTemplateId(templateId);
			templateSMS.setTo(to);
			templateSMS.setParam(param);
			Gson gson = new Gson();
			String body = gson.toJson(templateSMS);
			body = "{\"templateSMS\":" + body + "}";
			logger.info(body);
			HttpResponse response = post("application/json", accountSid,
					authToken, timestamp, url, httpclient, encryptUtil, body);
			HttpEntity entity = response.getEntity();
			if (entity != null) {
				result = EntityUtils.toString(entity, "UTF-8");
			}
			EntityUtils.consume(entity);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// 关闭连接
			httpclient.getConnectionManager().shutdown();
		}
		return result;
	}

	@Override
	public String findClientByMobile(String accountSid, String authToken,
			String mobile, String appId) {
		// TODO Auto-generated method stub
		String result = "";
		DefaultHttpClient httpclient = getDefaultHttpClient();
		try {
			// MD5加密
			EncryptUtil encryptUtil = new EncryptUtil();
			// 构造请求URL内容
			String timestamp = DateUtil.dateToStr(new Date(),
					DateUtil.DATE_TIME_NO_SLASH);
			String signature = getSignature(accountSid, authToken, timestamp,
					encryptUtil);
			String url = getStringBuffer().append("/").append(version)
					.append("/Accounts/").append(accountSid)
					.append("/ClientsByMobile").append("?sig=")
					.append(signature).append("&mobile=").append(mobile)
					.append("&appId=").append(appId).toString();
			HttpResponse response = get("application/json", accountSid,
					authToken, timestamp, url, httpclient, encryptUtil);
			// 获取响应实体信息
			HttpEntity entity = response.getEntity();
			if (entity != null) {
				result = EntityUtils.toString(entity, "UTF-8");
			}
			EntityUtils.consume(entity);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// 关闭连接
			httpclient.getConnectionManager().shutdown();
		}
		return result;
	}

	@Override
	public String dispalyNumber(String accountSid, String authToken,
			String appId, String clientNumber, String display) {
		// TODO Auto-generated method stub
		String result = "";
		DefaultHttpClient httpclient = getDefaultHttpClient();
		try {
			// MD5加密
			EncryptUtil encryptUtil = new EncryptUtil();
			// 构造请求URL内容
			String timestamp = DateUtil.dateToStr(new Date(),
					DateUtil.DATE_TIME_NO_SLASH);
			String signature = getSignature(accountSid, authToken, timestamp,
					encryptUtil);
			String url = getStringBuffer().append("/").append(version)
					.append("/Accounts/").append(accountSid)
					.append("/dispalyNumber").append("?sig=").append(signature)
					.append("&appId=").append(appId).append("&clientNumber=")
					.append(clientNumber).append("&display=").append(display)
					.toString();
			HttpResponse response = get("application/json", accountSid,
					authToken, timestamp, url, httpclient, encryptUtil);
			// 获取响应实体信息
			HttpEntity entity = response.getEntity();
			if (entity != null) {
				result = EntityUtils.toString(entity, "UTF-8");
			}
			EntityUtils.consume(entity);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// 关闭连接
			httpclient.getConnectionManager().shutdown();
		}
		return result;
	}
}
