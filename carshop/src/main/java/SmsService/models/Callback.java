/**
 * @author Glan.duanyj
 * @date 2014-05-12
 * @project rest_demo
 */
package SmsService.models;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "callback")
public class Callback {

	private String appId;
	private String fromClient;
	private String to;
	private String fromSerNum;
	private String toSerNum;
	
	public String getFromSerNum() {
		return fromSerNum;
	}
	public void setFromSerNum(String fromSerNum) {
		this.fromSerNum = fromSerNum;
	}
	public String getToSerNum() {
		return toSerNum;
	}
	public void setToSerNum(String toSerNum) {
		this.toSerNum = toSerNum;
	}
	public String getAppId() {
		return appId;
	}
	public void setAppId(String appId) {
		this.appId = appId;
	}
	public String getFromClient() {
		return fromClient;
	}
	public void setFromClient(String fromClient) {
		this.fromClient = fromClient;
	}
	public String getTo() {
		return to;
	}
	public void setTo(String to) {
		this.to = to;
	}
	
}
