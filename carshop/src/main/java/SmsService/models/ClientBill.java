/**
 * @author Glan.duanyj
 * @date 2014-05-12
 * @project rest_demo
 */
package SmsService.models;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "clientBill")
public class ClientBill {
	/**
	 * 
	 */
	private String appId;
	private String clientNumber;
	private String date;
	private String downUrl;
	private String token;
	public String getAppId() {
		return appId;
	}
	public void setAppId(String appId) {
		this.appId = appId;
	}
	public String getClientNumber() {
		return clientNumber;
	}
	public void setClientNumber(String clientNumber) {
		this.clientNumber = clientNumber;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getDownUrl() {
		return downUrl;
	}
	public void setDownUrl(String downUrl) {
		this.downUrl = downUrl;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
}
