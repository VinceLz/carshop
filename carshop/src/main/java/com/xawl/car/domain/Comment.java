package com.xawl.car.domain;

public class Comment {
	private int comment_id;
	private int mbid;
	private String content;
	private String date;
	private String uname;
	public int getComment_id() {
		return comment_id;
	}
	public void setComment_id(int comment_id) {
		this.comment_id = comment_id;
	}
	public int getMbid() {
		return mbid;
	}
	public void setMbid(int mbid) {
		this.mbid = mbid;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getUname() {
		return uname;
	}
	public void setUname(String uname) {
		this.uname = uname;
	}

}
