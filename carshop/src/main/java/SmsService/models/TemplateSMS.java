/**
 * @author Glan.duanyj
 * @date 2014-05-12
 * @project rest_demo
 */
package SmsService.models;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "templateSMS")
public class TemplateSMS {

	private String appId;
	private String templateId;
	private String to;
	private String param;
	public String getAppId() {
		return appId;
	}
	public void setAppId(String appId) {
		this.appId = appId;
	}
	public String getTemplateId() {
		return templateId;
	}
	public void setTemplateId(String templateId) {
		this.templateId = templateId;
	}
	public String getTo() {
		return to;
	}
	public void setTo(String to) {
		this.to = to;
	}
	public String getParam() {
		return param;
	}
	public void setParam(String param) {
		this.param = param;
	}
	
}
