package com.xawl.car.domain;

public class User {
	public static int USER_STATUS_STOP = 0;
	public static int USER_STATUS_OK = 1;
	private int uid;
	private String uphone;
	private String ulogin;
	
	private String uaddress;
	private String uname;
	private String upassword;
	private String ucreate;
	private String uemail;
	private int status;
	private String uimage;
	private String lasttime;
	private String token;
	
	
	public String getUlogin() {
		return ulogin;
	}

	public void setUlogin(String ulogin) {
		this.ulogin = ulogin;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getLasttime() {
		return lasttime;
	}

	public void setLasttime(String lasttime) {
		this.lasttime = lasttime;
	}

	public int getUid() {
		return uid;
	}

	public void setUid(int uid) {
		this.uid = uid;
	}


	public String getUphone() {
		return uphone;
	}

	public void setUphone(String uphone) {
		this.uphone = uphone;
	}

	public String getUaddress() {
		return uaddress;
	}

	public void setUaddress(String uaddress) {
		this.uaddress = uaddress;
	}

	public String getUname() {
		return uname;
	}

	public void setUname(String uname) {
		this.uname = uname;
	}

	public String getUpassword() {
		return upassword;
	}

	public void setUpassword(String upassword) {
		this.upassword = upassword;
	}

	public String getUcreate() {
		return ucreate;
	}

	public void setUcreate(String ucreate) {
		this.ucreate = ucreate;
	}

	public String getUemail() {
		return uemail;
	}

	public void setUemail(String uemail) {
		this.uemail = uemail;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getUimage() {
		return uimage;
	}

	public void setUimage(String uimage) {
		this.uimage = uimage;
	}

}
