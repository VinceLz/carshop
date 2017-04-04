package com.xawl.car.util;


import com.google.common.collect.Maps;
import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpConnectionManagerParams;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.commons.httpclient.protocol.Protocol;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Iterator;
import java.util.Map;

/**
 * 
 * ClassName: HttpClientUtil <br/>
 * Function: HttpClient工具类 . <br/>
 * date: 2016年10月9日 下午5:21:44 <br/>
 *
 * @author 清算项目组
 * @version 2.0
 * @since JDK 1.7
 */
public class HttpClientUtil {
	
	private static final int ConTimeOut = 30000;
	private static final int SoTimeOut = 120000;
	protected static final Logger logger = LoggerFactory.getLogger(HttpClientUtil.class);
	
	/**
	 * 
	 * httpPost: httpclient post方法 . <br/>
	 * 支持重定向，支持请求转发
	 * 例如：
	 * response.sendRedirect(url);//重定向
	   request.getRequestDispatcher(url).forward(request, response);//请求转发
	 * @author tolly
	 * @param url
	 * @param paramMap
	 * @param code
	 * @return String
	 * @since JDK 1.7
	 */
	public static String httpPost(String url, Map<String, String> paramMap, String code) {
		logger.info("GetPage:" + url);
		String content = null;
		if (url == null || url.trim().length() == 0 || paramMap == null || paramMap.isEmpty())
			return null;
		HttpClient httpClient = new HttpClient();
		// 设置header
		httpClient.getParams().setParameter(HttpMethodParams.USER_AGENT, "Mozilla/5.0 (X11; U; Linux i686; zh-CN; rv:1.9.1.2) Gecko/20090803 Fedora/3.5.2-2.fc11 Firefox/3.5.2");//
		
		// 20120627 by ljyan add 设置HttpPost编码
		httpClient.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, code);
		
		 HttpConnectionManagerParams managerParams = httpClient.getHttpConnectionManager().getParams();
		 // 设置连接超时时间(单位毫秒)
		 managerParams.setConnectionTimeout(ConTimeOut);
		 managerParams.setSoTimeout(SoTimeOut);
		
		// 添加信任链接 by ljyan20131008
		if (url.indexOf("https") >= 0) {
			Protocol myhttps = new Protocol("https", new MyProtocolSocketFactory(), 443);
			Protocol.registerProtocol("https", myhttps);
		}
		PostMethod method = new PostMethod(url);
		AppendPostParam(method,paramMap);//设置post方法参数
		try {
			int statusCode = httpClient.executeMethod(method);
			
			// 检查是否重定向
			if ((statusCode == HttpStatus.SC_MOVED_TEMPORARILY) || (statusCode == HttpStatus.SC_MOVED_PERMANENTLY) || (statusCode == HttpStatus.SC_SEE_OTHER) || (statusCode == HttpStatus.SC_TEMPORARY_REDIRECT)) {
				// 读取新的 URL 地址
				Header header = method.getResponseHeader("location");
				if (header != null) {
					String newuri = header.getValue();
					if ((newuri == null) || (newuri.equals("")))
						newuri = "/";
					method = new PostMethod(newuri);
					AppendPostParam(method,paramMap);
					statusCode = httpClient.executeMethod(method);
				}
			}
			logger.info("httpClientUtils::statusCode=" + statusCode);
			logger.info(method.getStatusLine().toString());
			content = new String(method.getResponseBody(), code);
			logger.info("response content:" + content);
		} catch (Exception e) {
			logger.info("time out");
		} finally {
			if (method != null){
				method.releaseConnection();
			}
			method = null;
			httpClient = null;
		}
		return content;
	}
	
	/**
	 * 
	 * httpPost: http post 请求 . <br/>
	 *
	 * @author tolly
	 * @param url
	 * @param param 格式key1=value1@key2=value2
	 * @param code字符集编码
	 * @return String
	 */
	public static String httpPost(String url, String param, String code) {
		Map<String, String> paramMap = Maps.newHashMap();
		if(StringUtils.isBlank(param)){
			return null;
		}
		String[] paramArray = StringUtils.split(param, "&");
		/*for(int i=0;i<paramArray.length;i++){
			logger.info(paramArray[i]);
		}*/
		for (String val : paramArray) {
			String[] valArray = StringUtils.split(val,"=");
			paramMap.put(valArray[0], valArray[1]);
		}
		logger.info("发送的数据:{}", paramMap.toString());
		return httpPost(url, paramMap, code);
	}
	
	/**
	 * 
	 * AppendPostParam: 设置post方法发送参数 . <br/>
	 *
	 * @author tolly
	 * @param method
	 * @param paramMap
	 * @since JDK 1.7
	 */
	private static void AppendPostParam(PostMethod method, Map<String, String> paramMap) {
		Iterator<?> it = paramMap.keySet().iterator();
		while (it.hasNext()) {
			String key = it.next() + "";
			Object o = paramMap.get(key);
			if (o != null && o instanceof String) {
				method.addParameter(new NameValuePair(key, o.toString()));
			}
			if (o != null && o instanceof String[]) {
				String[] s = (String[]) o;
				if (s != null)
					for (int i = 0; i < s.length; i++) {
						method.addParameter(new NameValuePair(key, s[i]));
					}
			}
		}
	}
	
	/**
	 * URL有效性检测
	 * 
	 * @param url
	 * @param code
	 * @return int
	 */
	public static int httpPostTest(String url, String code) {
		logger.info("开始进行URL有效性检测:" + url);
		int statusCode = 0;
		if (url == null || url.trim().length() == 0) {
			return statusCode;
		}
		HttpClient httpClient = new HttpClient();
		// 设置header
		httpClient.getParams().setParameter(HttpMethodParams.USER_AGENT, "Mozilla/5.0 (X11; U; Linux i686; zh-CN; rv:1.9.1.2) Gecko/20090803 Fedora/3.5.2-2.fc11 Firefox/3.5.2");//
		
		// 20120627 by ljyan add 设置HttpPost编码
		httpClient.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, code);
		PostMethod method = new PostMethod(url);
		try {
			statusCode = httpClient.executeMethod(method);
		} catch (Exception e) {
			logger.info("time out");
		} finally {
			if (method != null) {
				method.releaseConnection();
				method = null;
			}
			httpClient = null;
		}
		return statusCode;
	}
}
