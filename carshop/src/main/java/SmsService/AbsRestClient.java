package SmsService;

import java.io.ByteArrayInputStream;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.BasicHttpEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;
import org.apache.log4j.Logger;

public abstract class AbsRestClient {
	public boolean isTest = Boolean.parseBoolean(SysConfig.getInstance()
			.getProperty("is_test"));
	public String server = SysConfig.getInstance().getProperty("rest_server");
	public String sslIP = SysConfig.getInstance().getProperty("http_ssl_ip");
	public int sslPort = SysConfig.getInstance()
			.getPropertyInt("http_ssl_port");
	public String version = SysConfig.getInstance().getProperty("version");
	private static Logger logger = Logger.getLogger(AbsRestClient.class);

	/**
	 * 
	 * @param accountSid
	 * @param authToken
	 * @return
	 * @throws NoSuchAlgorithmException
	 * @throws KeyManagementException
	 *             String findAccoutInfo
	 */
	public abstract String findAccoutInfo(String accountSid, String authToken)
			throws NoSuchAlgorithmException, KeyManagementException;

	/**
	 * 
	 * @param accountSid
	 * @param authToken
	 * @param appId
	 * @param clientName
	 * @param chargeType
	 * @param charge
	 * @param mobile
	 * @return String createClient
	 */
	public abstract String createClient(String accountSid, String authToken,
			String appId, String clientName, String chargeType, String charge,
			String mobile);

	/**
	 * 
	 * @param accountSid
	 * @param authToken
	 * @param appId
	 * @param start
	 * @param limit
	 * @return String findClients
	 */
	public abstract String findClients(String accountSid, String authToken,
			String appId, String start, String limit);

	/**
	 * 
	 * @param accountSid
	 * @param authToken
	 * @param clientNumber
	 * @param appId
	 * @return String testfindClientByNbr
	 */
	public abstract String findClientByNbr(String accountSid, String authToken,
			String clientNumber, String appId);

	/**
	 * 
	 * @param accountSid
	 * @param authToken
	 * @param clientId
	 * @param appId
	 * @return String closeClient
	 */
	public abstract String closeClient(String accountSid, String authToken,
			String clientId, String appId);

	/**
	 * 
	 * @param accountSid
	 * @param authToken
	 * @param clientNumber
	 * @param chargeType
	 * @param charge
	 * @param appId
	 * @return String charegeClient
	 */
	public abstract String charegeClient(String accountSid, String authToken,
			String clientNumber, String chargeType, String charge, String appId);

	/**
	 * 
	 * @param accountSid
	 * @param authToken
	 * @param appId
	 * @param date
	 * @return String billList
	 */
	public abstract String billList(String accountSid, String authToken,
			String appId, String date);

	/**
	 * 
	 * @param accountSid
	 * @param authToken
	 * @param appId
	 * @param clientNumber
	 * @param date
	 * @return String clientBillList
	 */
	public abstract String clientBillList(String accountSid, String authToken,
			String appId, String clientNumber, String date);

	/**
	 * 
	 * @param accountSid
	 * @param authToken
	 * @param appId
	 * @param fromClient
	 * @param to
	 * @param toSerNum
	 * @return String callback
	 */
	public abstract String callback(String accountSid, String authToken,
			String appId, String fromClient, String to, String fromSerNum,
			String toSerNum);

	/**
	 * 
	 * @param accountSid
	 * @param authToken
	 * @param appId
	 * @param to
	 * @param verifyCode
	 * @param displayNum
	 * @return String voiceCode
	 */
	public abstract String voiceCode(String accountSid, String authToken,
			String appId, String to, String verifyCode);

	/**
	 * 
	 * @param accountSid
	 * @param authToken
	 * @param appId
	 * @param templateId
	 * @param to
	 * @param param
	 * @return String templateSMS
	 */
	public abstract String templateSMS(String accountSid, String authToken,
			String appId, String templateId, String to, String param);

	/**
	 * 
	 * @param accountSid
	 * @param authToken
	 * @param mobile
	 * @param appId
	 * @return
	 */
	public abstract String findClientByMobile(String accountSid,
			String authToken, String mobile, String appId);

	/**
	 * 
	 * @param accountSid
	 * @param authToken
	 * @param mobile
	 * @param appId
	 * @param clientNumber
	 * @param display
	 * @return
	 */
	public abstract String dispalyNumber(String accountSid, String authToken,
			String appId, String clientNumber, String display);

	public StringBuffer getStringBuffer() {
		StringBuffer sb = new StringBuffer("https://");
		sb.append(server);
		return sb;
	}

	public DefaultHttpClient getDefaultHttpClient() {
		DefaultHttpClient httpclient = null;
		if (isTest) {
			try {
				SSLHttpClient chc = new SSLHttpClient();
				httpclient = chc.registerSSL(sslIP, "TLS", sslPort, "https");
				HttpParams hParams = new BasicHttpParams();
				hParams.setParameter("https.protocols", "SSLv3,SSLv2Hello");
				httpclient.setParams(hParams);
			} catch (KeyManagementException e) {
				// TODO: handle exception
				logger.error(e);
			} catch (NoSuchAlgorithmException e) {
				// TODO: handle exception
				logger.error(e);
			}
		} else {
			httpclient = new DefaultHttpClient();
		}
		return httpclient;
	}

	public String getSignature(String accountSid, String authToken,
			String timestamp, EncryptUtil encryptUtil) throws Exception {
		String sig = accountSid + authToken + timestamp;
		String signature = encryptUtil.md5Digest(sig);
		return signature;
	}

	public HttpResponse get(String cType, String accountSid, String authToken,
			String timestamp, String url, DefaultHttpClient httpclient,
			EncryptUtil encryptUtil) throws Exception {
		HttpGet httppost = new HttpGet(url);
		httppost.setHeader("Accept", cType);//
		httppost.setHeader("Content-Type", cType + ";charset=utf-8");
		String src = accountSid + ":" + timestamp;
		String auth = encryptUtil.base64Encoder(src);
		httppost.setHeader("Authorization", auth);
		HttpResponse response = httpclient.execute(httppost);
		return response;
	}

	public HttpResponse post(String cType, String accountSid, String authToken,
			String timestamp, String url, DefaultHttpClient httpclient,
			EncryptUtil encryptUtil, String body) throws Exception {
		HttpPost httppost = new HttpPost(url);
		httppost.setHeader("Accept", cType);
		httppost.setHeader("Content-Type", cType + ";charset=utf-8");
		String src = accountSid + ":" + timestamp;
		String auth = encryptUtil.base64Encoder(src);
		httppost.setHeader("Authorization", auth);
		BasicHttpEntity requestBody = new BasicHttpEntity();
		requestBody
				.setContent(new ByteArrayInputStream(body.getBytes("UTF-8")));
		requestBody.setContentLength(body.getBytes("UTF-8").length);
		httppost.setEntity(requestBody);
		// 执行客户端请求
		HttpResponse response = httpclient.execute(httppost);
		return response;
	}

	public HttpResponse delete(String cType, String accountSid,
			String authToken, String timestamp, String url,
			DefaultHttpClient httpclient, EncryptUtil encryptUtil)
			throws Exception {
		HttpDelete httpDelete = new HttpDelete(url);
		httpDelete.setHeader("Accept", cType);
		httpDelete.setHeader("Content-Type", cType + ";charset=utf-8");
		String src = accountSid + ":" + timestamp;
		String auth = encryptUtil.base64Encoder(src);
		httpDelete.setHeader("Authorization", auth);
		HttpResponse response = httpclient.execute(httpDelete);
		return response;
	}

	public HttpResponse put(String cType, String accountSid, String authToken,
			String timestamp, String url, DefaultHttpClient httpclient,
			EncryptUtil encryptUtil, String body) throws Exception {
		HttpPut httpPut = new HttpPut(url);
		httpPut.setHeader("Accept", cType);
		httpPut.setHeader("Content-Type", cType + ";charset=utf-8");
		String src = accountSid + ":" + timestamp;
		String auth = encryptUtil.base64Encoder(src);
		httpPut.setHeader("Authorization", auth);
		logger.info(body);
		BasicHttpEntity requestBody = new BasicHttpEntity();
		requestBody
				.setContent(new ByteArrayInputStream(body.getBytes("UTF-8")));
		requestBody.setContentLength(body.getBytes("UTF-8").length);
		httpPut.setEntity(requestBody);
		HttpResponse response = httpclient.execute(httpPut);
		return response;
	}
}
