package com.xawl.car.util;



import org.apache.commons.httpclient.ConnectTimeoutException;
import org.apache.commons.httpclient.params.HttpConnectionParams;
import org.apache.commons.httpclient.protocol.ProtocolSocketFactory;

import javax.net.SocketFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.io.IOException;
import java.net.*;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

/**
 * 
 * ClassName: MyProtocolSocketFactory <br/>
 * Function: 设置信任的SSL链接 . <br/>
 * date: 2016年10月9日 下午5:22:51 <br/>
 *
 * @author 清算项目组
 * @version 2.0
 * @since JDK 1.7
 */
public class MyProtocolSocketFactory implements ProtocolSocketFactory {
	
	private SSLContext sslcontext = null;
	
	private SSLContext createSSLContext() {
		SSLContext sslcontext = null;
		try {
			sslcontext = SSLContext.getInstance("SSL");
			sslcontext.init(null, new TrustManager[] { new TrustAnyTrustManager() }, new java.security.SecureRandom());
		} catch (NoSuchAlgorithmException e) {
			
		} catch (KeyManagementException e) {
			
		}
		return sslcontext;
	}
	
	private SSLContext getSSLContext() {
		if (this.sslcontext == null) {
			this.sslcontext = createSSLContext();
		}
		return this.sslcontext;
	}
	
	public Socket createSocket(Socket socket, String host, int port, boolean autoClose)
		throws IOException, UnknownHostException {
		return getSSLContext().getSocketFactory().createSocket(socket, host, port, autoClose);
	}
	
	@Override
	public Socket createSocket(String host, int port)
		throws IOException, UnknownHostException {
		return getSSLContext().getSocketFactory().createSocket(host, port);
	}
	
	@Override
	public Socket createSocket(String host, int port, InetAddress clientHost, int clientPort)
		throws IOException, UnknownHostException {
		return getSSLContext().getSocketFactory().createSocket(host, port, clientHost, clientPort);
	}
	
	@Override
	public Socket createSocket(String host, int port, InetAddress localAddress, int localPort, HttpConnectionParams params)
		throws IOException, UnknownHostException, ConnectTimeoutException {
		if (params == null) {
			throw new IllegalArgumentException("Parameters may not be null");
		}
		int timeout = params.getConnectionTimeout();
		SocketFactory socketfactory = getSSLContext().getSocketFactory();
		if (timeout == 0) {
			return socketfactory.createSocket(host, port, localAddress, localPort);
		} else {
			Socket socket = socketfactory.createSocket();
			SocketAddress localaddr = new InetSocketAddress(localAddress, localPort);
			SocketAddress remoteaddr = new InetSocketAddress(host, port);
			socket.bind(localaddr);
			socket.connect(remoteaddr, timeout);
			return socket;
		}
	}
	
	// 自定义私有类
	private static class TrustAnyTrustManager implements X509TrustManager {
		
		@Override
		public void checkClientTrusted(X509Certificate[] chain, String authType)
			throws CertificateException {
		}
		
		@Override
		public void checkServerTrusted(X509Certificate[] chain, String authType)
			throws CertificateException {
		}
		
		@Override
		public X509Certificate[] getAcceptedIssuers() {
			return new X509Certificate[] {};
		}
	}
	
}
