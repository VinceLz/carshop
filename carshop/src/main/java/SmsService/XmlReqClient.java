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
public class XmlReqClient extends AbsRestClient {
	private static Logger logger=Logger.getLogger(XmlReqClient.class);
	@Override
	public String findAccoutInfo(String accountSid, String authToken)
			throws NoSuchAlgorithmException, KeyManagementException {
		// TODO Auto-generated method stub
		String result = "";
		DefaultHttpClient httpclient=getDefaultHttpClient();
		try {
			//MD5加密
			EncryptUtil encryptUtil = new EncryptUtil();
			// 构造请求URL内容
			String timestamp = DateUtil.dateToStr(new Date(),
					DateUtil.DATE_TIME_NO_SLASH);// 时间戳
			String signature =getSignature(accountSid,authToken,timestamp,encryptUtil);
			String url = getStringBuffer().append("/").append(version)
					.append("/Accounts/").append(accountSid)
					.append("")
					.append(".xml?sig=").append(signature).toString();
			logger.info(url);
			HttpResponse response=get("application/xml",accountSid,authToken,timestamp,url,httpclient,encryptUtil);
			//获取响应实体信息
			HttpEntity entity = response.getEntity();
			if (entity != null) {
				result = EntityUtils.toString(entity, "UTF-8");
			}
			// 确保HTTP响应内容全部被读出或者内容流被关闭
			EntityUtils.consume(entity);
		} catch (Exception e) {
			e.printStackTrace();
		} finally{
			// 关闭连接
		    httpclient.getConnectionManager().shutdown();
		}
		return result;
	}
	@Override
	public String createClient(String accountSid, String authToken,
			String appId, String clientName, String chargeType, String charge
			,String mobile) {
		// TODO Auto-generated method stub
		String result = "";
		DefaultHttpClient httpclient=getDefaultHttpClient();
		try {
			//MD5加密
			EncryptUtil encryptUtil = new EncryptUtil();
			// 构造请求URL内容
			String timestamp = DateUtil.dateToStr(new Date(),
					DateUtil.DATE_TIME_NO_SLASH);// 时间戳
			String signature =getSignature(accountSid,authToken,timestamp,encryptUtil);
			String url = getStringBuffer().append("/").append(version)
					.append("/Accounts/").append(accountSid)
					.append("/Clients")
					.append(".xml?sig=").append(signature).toString();
			// base64(主账户Id + 冒号 +时间戳)
			String body = (new StringBuilder("<?xml version='1.0' encoding='utf-8'?><client>")
	              .append("<appId>").append(appId).append("</appId>")
	              .append("<friendlyName>").append(clientName).append("</friendlyName>")
	              .append("<clientType>").append(chargeType).append("</clientType>")
	              .append("<charge>").append(charge).append("</charge>")
	              .append("<mobile>").append(mobile).append("</mobile>")
	              .append("</client>")).toString();
			HttpResponse response=post("application/xml",accountSid, authToken, timestamp, url, httpclient, encryptUtil, body);
			//获取响应实体信息
			HttpEntity entity = response.getEntity();
			if (entity != null) {
				result = EntityUtils.toString(entity, "UTF-8");
			}
			// 确保HTTP响应内容全部被读出或者内容流被关闭
			EntityUtils.consume(entity);
		} catch (Exception e) {
			e.printStackTrace();
		} finally{
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
		DefaultHttpClient httpclient=getDefaultHttpClient();
		try {
			//MD5加密
			EncryptUtil encryptUtil = new EncryptUtil();
			//构造请求URL内容
			String timestamp = DateUtil.dateToStr(new Date(), DateUtil.DATE_TIME_NO_SLASH);
			String signature=getSignature(accountSid, authToken,timestamp,encryptUtil);
			String url = getStringBuffer().append("/").append(version)
					.append("/Accounts/").append(accountSid)
					.append("/clientList")
					.append(".xml?sig=").append(signature).toString();
			String body = (new StringBuilder("<?xml version='1.0' encoding='utf-8'?><client>")
            .append("<appId>").append(appId).append("</appId>")
            .append("<start>").append(start).append("</start>")
            .append("<limit>").append(limit).append("</limit>")
            .append("</client>")).toString();
			HttpResponse response=post("application/xml",accountSid, authToken, timestamp, url, httpclient, encryptUtil, body);
			//获取响应实体信息
			HttpEntity entity = response.getEntity();
			if (entity != null) {
				result = EntityUtils.toString(entity, "UTF-8");
			}
			// 确保HTTP响应内容全部被读出或者内容流被关闭
			EntityUtils.consume(entity);
		}catch (Exception e) {
			e.printStackTrace();
		}finally{
			// 关闭连接
		    httpclient.getConnectionManager().shutdown();
		}
		return result;
	}
	@Override
	public String findClientByNbr(String accountSid, String authToken,String clientNumber,String appId) {
		// TODO Auto-generated method stub
		String result = "";
		DefaultHttpClient httpclient=getDefaultHttpClient();
		try {
			//MD5加密
			EncryptUtil encryptUtil = new EncryptUtil();
			//构造请求URL内容
			String timestamp = DateUtil.dateToStr(new Date(), DateUtil.DATE_TIME_NO_SLASH);
			String signature=getSignature(accountSid, authToken,timestamp,encryptUtil);
			String url = getStringBuffer().append("/").append(version)
					.append("/Accounts/").append(accountSid)
					.append("/Clients")
					.append(".xml?sig=").append(signature)
					.append("&clientNumber=").append(clientNumber)
					.append("&appId=").append(appId)
					.toString();
			HttpResponse response=get("application/xml",accountSid, authToken, timestamp, url, httpclient, encryptUtil);
			//获取响应实体信息
			HttpEntity entity = response.getEntity();
			if (entity != null) {
				result = EntityUtils.toString(entity, "UTF-8");
			}
			// 确保HTTP响应内容全部被读出或者内容流被关闭
			EntityUtils.consume(entity);
		}catch (Exception e) {
			e.printStackTrace();
		}finally{
			// 关闭连接
		    httpclient.getConnectionManager().shutdown();
		}
		return result;
	}
	@Override
	public String closeClient(String accountSid, String authToken, String clientId,String appId) {
		// TODO Auto-generated method stub
		String result = "";
		DefaultHttpClient httpclient=getDefaultHttpClient();
		try {
			//MD5加密
			EncryptUtil encryptUtil = new EncryptUtil();
			// 构造请求URL内容
			String timestamp = DateUtil.dateToStr(new Date(),
					DateUtil.DATE_TIME_NO_SLASH);// 时间戳
			String signature =getSignature(accountSid,authToken,timestamp,encryptUtil);
			String url = getStringBuffer().append("/").append(version)
					.append("/Accounts/").append(accountSid)
					.append("/dropClient")
					.append(".xml?sig=").append(signature).toString();
			String body = (new StringBuilder("<?xml version='1.0' encoding='utf-8'?><client>")
            .append("<clientNumber>").append(clientId).append("</clientNumber>")
            .append("<appId>").append(appId).append("</appId>")
            .append("</client>")).toString();
			HttpResponse response=post("application/xml",accountSid, authToken, timestamp, url, httpclient, encryptUtil, body);
			//获取响应实体信息
			HttpEntity entity = response.getEntity();
			if (entity != null) {
				result = EntityUtils.toString(entity, "UTF-8");
			}
			// 确保HTTP响应内容全部被读出或者内容流被关闭
			EntityUtils.consume(entity);
		} catch (Exception e) {
			e.printStackTrace();
		} finally{
			// 关闭连接
		    httpclient.getConnectionManager().shutdown();
		}
		return result;
	}
	@Override
	public String charegeClient(String accountSid, String authToken,
			String clientNumber, String chargeType, String charge,String appId) {
		// TODO Auto-generated method stub
		String result = "";
		DefaultHttpClient httpclient=getDefaultHttpClient();
		try {
			//MD5加密
			EncryptUtil encryptUtil = new EncryptUtil();
			// 构造请求URL内容
			String timestamp = DateUtil.dateToStr(new Date(),
					DateUtil.DATE_TIME_NO_SLASH);// 时间戳
			String signature =getSignature(accountSid,authToken,timestamp,encryptUtil);
			String url = getStringBuffer().append("/").append(version)
					.append("/Accounts/").append(accountSid)
					.append("/chargeClient")
					.append(".xml?sig=").append(signature).toString();
			String body = (new StringBuilder("<?xml version='1.0' encoding='utf-8'?><client>")
            .append("<clientNumber>").append(clientNumber).append("</clientNumber>")
            .append("<chargeType>").append(chargeType).append("</chargeType>")
            .append("<charge>").append(charge).append("</charge>")
            .append("<appId>").append(appId).append("</appId>")
            .append("</client>")).toString();
			HttpResponse response=post("application/xml",accountSid, authToken, timestamp, url, httpclient, encryptUtil, body);
			HttpEntity entity = response.getEntity();
			if (entity != null) {
				result = EntityUtils.toString(entity, "UTF-8");
			}
			EntityUtils.consume(entity);
		} catch (Exception e) {
			e.printStackTrace();
		} finally{
			// 关闭连接
		    httpclient.getConnectionManager().shutdown();
		}
		return result;
	}
	@Override
	public String billList(String accountSid, String authToken, String appId,String date) {
		// TODO Auto-generated method stub
		String result = "";
		DefaultHttpClient httpclient=getDefaultHttpClient();
		try {
			//MD5加密
			EncryptUtil encryptUtil = new EncryptUtil();
			// 构造请求URL内容
			String timestamp = DateUtil.dateToStr(new Date(),
					DateUtil.DATE_TIME_NO_SLASH);// 时间戳
			String signature =getSignature(accountSid,authToken,timestamp,encryptUtil);
			String url = getStringBuffer().append("/").append(version)
					.append("/Accounts/").append(accountSid)
					.append("/billList")
					.append(".xml?sig=").append(signature).toString();
			String body = (new StringBuilder("<?xml version='1.0' encoding='utf-8'?><appBill>")
            .append("<date>").append(date).append("</date>")
            .append("<appId>").append(appId).append("</appId>")
            .append("</appBill>")).toString();
			HttpResponse response=post("application/xml",accountSid, authToken, timestamp, url, httpclient, encryptUtil, body);
			HttpEntity entity = response.getEntity();
			if (entity != null) {
				result = EntityUtils.toString(entity, "UTF-8");
			}
			EntityUtils.consume(entity);
		} catch (Exception e) {
			e.printStackTrace();
		} finally{
			// 关闭连接
		    httpclient.getConnectionManager().shutdown();
		}
		return result;
	}
	@Override
	public String clientBillList(String accountSid, String authToken,
			String appId, String clientNumber, String date) {
		// TODO Auto-generated method stub
		String result = "";
		DefaultHttpClient httpclient=getDefaultHttpClient();
		try {
			//MD5加密
			EncryptUtil encryptUtil = new EncryptUtil();
			// 构造请求URL内容
			String timestamp = DateUtil.dateToStr(new Date(),
					DateUtil.DATE_TIME_NO_SLASH);// 时间戳
			String signature =getSignature(accountSid,authToken,timestamp,encryptUtil);
			String url = getStringBuffer().append("/").append(version)
					.append("/Accounts/").append(accountSid)
					.append("/Clients/billList")
					.append(".xml?sig=").append(signature).toString();
			String body = (new StringBuilder("<?xml version='1.0' encoding='utf-8'?><clientBill>")
            .append("<clientNumber>").append(clientNumber).append("</clientNumber>")
            .append("<appId>").append(appId).append("</appId>")
            .append("<date>").append(date).append("</date>")
            .append("</clientBill>")).toString();
			HttpResponse response=post("application/xml",accountSid, authToken, timestamp, url, httpclient, encryptUtil, body);
			HttpEntity entity = response.getEntity();
			if (entity != null) {
				result = EntityUtils.toString(entity, "UTF-8");
			}
			EntityUtils.consume(entity);
		} catch (Exception e) {
			e.printStackTrace();
		} finally{
			// 关闭连接
		    httpclient.getConnectionManager().shutdown();
		}
		return result;
	}
	@Override
	public String callback(String accountSid, String authToken, String appId,
			String fromClient, String to,String fromSerNum,String toSerNum) {
		// TODO Auto-generated method stub
		String result = "";
		DefaultHttpClient httpclient=getDefaultHttpClient();
		try {
			//MD5加密
			EncryptUtil encryptUtil = new EncryptUtil();
			// 构造请求URL内容
			String timestamp = DateUtil.dateToStr(new Date(),DateUtil.DATE_TIME_NO_SLASH);// 时间戳
			String signature =getSignature(accountSid,authToken,timestamp,encryptUtil);
			String url = getStringBuffer().append("/").append(version)
					.append("/Accounts/").append(accountSid)
					.append("/Calls/callBack")
					.append(".xml?sig=").append(signature).toString();
			String body = (new StringBuilder("<?xml version='1.0' encoding='utf-8'?><callback>")
            .append("<appId>").append(appId).append("</appId>")
            .append("<fromClient>").append(fromClient).append("</fromClient>")
            .append("<to>").append(to).append("</to>")
            .append("<fromSerNum>").append(fromSerNum).append("</fromSerNum>")
            .append("<toSerNum>").append(toSerNum).append("</toSerNum>")
            .append("</callback>")).toString();
			HttpResponse response=post("application/xml",accountSid, authToken, timestamp, url, httpclient, encryptUtil, body);
			HttpEntity entity = response.getEntity();
			if (entity != null) {
				result = EntityUtils.toString(entity, "UTF-8");
			}
			EntityUtils.consume(entity);
		} catch (Exception e) {
			e.printStackTrace();
		} finally{
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
		DefaultHttpClient httpclient=getDefaultHttpClient();
		try {
			//MD5加密
			EncryptUtil encryptUtil = new EncryptUtil();
			// 构造请求URL内容
			String timestamp = DateUtil.dateToStr(new Date(),
					DateUtil.DATE_TIME_NO_SLASH);// 时间戳
			String signature =getSignature(accountSid,authToken,timestamp,encryptUtil);
			String url = getStringBuffer().append("/").append(version)
					.append("/Accounts/").append(accountSid)
					.append("/Calls/voiceCode")
					.append(".xml?sig=").append(signature).toString();
			String body = (new StringBuilder("<?xml version='1.0' encoding='utf-8'?><voiceCode>")
            .append("<appId>").append(appId).append("</appId>")
            .append("<verifyCode>").append(verifyCode).append("</verifyCode>")
            .append("<to>").append(to).append("</to>")
            .append("</voiceCode>")).toString();
			HttpResponse response=post("application/xml",accountSid, authToken, timestamp, url, httpclient, encryptUtil, body);
			HttpEntity entity = response.getEntity();
			if (entity != null) {
				result = EntityUtils.toString(entity, "UTF-8");
			}
			EntityUtils.consume(entity);
		} catch (Exception e) {
			e.printStackTrace();
		} finally{
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
		DefaultHttpClient httpclient=getDefaultHttpClient();
		try {
			//MD5加密
			EncryptUtil encryptUtil = new EncryptUtil();
			// 构造请求URL内容
			String timestamp = DateUtil.dateToStr(new Date(),
					DateUtil.DATE_TIME_NO_SLASH);// 时间戳
			String signature =getSignature(accountSid,authToken,timestamp,encryptUtil);
			String url = getStringBuffer().append("/").append(version)
					.append("/Accounts/").append(accountSid)
					.append("/Messages/templateSMS")
					.append(".xml?sig=").append(signature).toString();
			String body = (new StringBuilder("<?xml version='1.0' encoding='utf-8'?><templateSMS>")
            .append("<appId>").append(appId).append("</appId>")
            .append("<templateId>").append(templateId).append("</templateId>")
            .append("<to>").append(to).append("</to>")
            .append("<param>").append(param).append("</param>")
            .append("</templateSMS>")).toString();
			HttpResponse response=post("application/xml",accountSid, authToken, timestamp, url, httpclient, encryptUtil, body);
			HttpEntity entity = response.getEntity();
			if (entity != null) {
				result = EntityUtils.toString(entity, "UTF-8");
			}
			EntityUtils.consume(entity);
		} catch (Exception e) {
			e.printStackTrace();
		} finally{
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
		DefaultHttpClient httpclient=getDefaultHttpClient();
		try {
			//MD5加密
			EncryptUtil encryptUtil = new EncryptUtil();
			//构造请求URL内容
			String timestamp = DateUtil.dateToStr(new Date(), DateUtil.DATE_TIME_NO_SLASH);
			String signature=getSignature(accountSid, authToken,timestamp,encryptUtil);
			String url = getStringBuffer().append("/").append(version)
					.append("/Accounts/").append(accountSid)
					.append("/ClientsByMobile")
					.append(".xml?sig=").append(signature)
					.append("&mobile=").append(mobile)
					.append("&appId=").append(appId)
					.toString();
			HttpResponse response=get("application/xml",accountSid, authToken, timestamp, url, httpclient, encryptUtil);
			//获取响应实体信息
			HttpEntity entity = response.getEntity();
			if (entity != null) {
				result = EntityUtils.toString(entity, "UTF-8");
			}
			// 确保HTTP响应内容全部被读出或者内容流被关闭
			EntityUtils.consume(entity);
		}catch (Exception e) {
			e.printStackTrace();
		}finally{
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
		DefaultHttpClient httpclient=getDefaultHttpClient();
		try {
			//MD5加密
			EncryptUtil encryptUtil = new EncryptUtil();
			//构造请求URL内容
			String timestamp = DateUtil.dateToStr(new Date(), DateUtil.DATE_TIME_NO_SLASH);
			String signature=getSignature(accountSid, authToken,timestamp,encryptUtil);
			String url = getStringBuffer().append("/").append(version)
					.append("/Accounts/").append(accountSid)
					.append("/dispalyNumber")
					.append(".xml?sig=").append(signature)
					.append("&appId=").append(appId)
					.append("&clientNumber=").append(clientNumber)
					.append("&display=").append(display)
					.toString();
			HttpResponse response=get("application/xml",accountSid, authToken, timestamp, url, httpclient, encryptUtil);
			//获取响应实体信息
			HttpEntity entity = response.getEntity();
			if (entity != null) {
				result = EntityUtils.toString(entity, "UTF-8");
			}
			EntityUtils.consume(entity);
		}catch (Exception e) {
			e.printStackTrace();
		}finally{
			// 关闭连接
		    httpclient.getConnectionManager().shutdown();
		}
		return result;
	}
}
