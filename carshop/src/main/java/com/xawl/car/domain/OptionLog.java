package com.xawl.car.domain;

public class OptionLog {
	public static int ORDER_KEEP_CAR = 0;
	public static int ORDER_CAR = 1;
	private Integer oid;
	private String content;
	private String goodid;
	private String ulogin;
	private String createdate;
	private String bankname;
	private String result;
	private int status;
	private String exeception;
	private int type;

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getExeception() {
		return exeception;
	}

	public void setExeception(String exeception) {
		this.exeception = exeception;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public Integer getOid() {
		return oid;
	}

	public void setOid(Integer oid) {
		this.oid = oid;
	}

	public String getContent() {
		return content;
	}

	public String getBankname() {
		return bankname;
	}

	public void setBankname(String bankname) {
		this.bankname = bankname;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getGoodid() {
		return goodid;
	}

	public void setGoodid(String goodid) {
		this.goodid = goodid;
	}

	public String getUlogin() {
		return ulogin;
	}

	public void setUlogin(String ulogin) {
		this.ulogin = ulogin;
	}

	public String getCreatedate() {
		return createdate;
	}

	public void setCreatedate(String createdate) {
		this.createdate = createdate;
	}

}
