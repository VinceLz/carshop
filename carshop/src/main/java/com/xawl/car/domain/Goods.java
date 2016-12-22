package com.xawl.car.domain;

import java.math.BigDecimal;
import java.util.List;

//车
public class Goods {
	// 级别，价格，排量，驱动，燃料，变速箱，国别，生产方式，结构，座位
	// 品牌，型号，车名
	private Integer gid;
	private String gname;
	private Integer bid;// 商家id
	private List<String> gimage;// 图片
	private String bname;// 店家名
	private String gdate;// 上传时间
	private Integer sale;// 售量
	private String title; //优惠信息
	private Integer isHot;
	private BigDecimal maxprice;  //指导价 数据库中没有这个值，只是计算出来的一个
	private BigDecimal minprice;  
	private String gshowImage;
	
	
	public String getGshowImage() {
		return gshowImage;
	}
	public void setGshowImage(String gshowImage) {
		this.gshowImage = gshowImage;
	}
	public Integer getGid() {
		return gid;
	}
	public void setGid(Integer gid) {
		this.gid = gid;
	}
	public String getGname() {
		return gname;
	}
	public void setGname(String gname) {
		this.gname = gname;
	}
	public Integer getBid() {
		return bid;
	}
	public void setBid(Integer bid) {
		this.bid = bid;
	}
	
	
	
	public List<String> getGimage() {
		return gimage;
	}
	public void setGimage(List<String> gimage) {
		this.gimage = gimage;
	}
	public String getBname() {
		return bname;
	}
	public void setBname(String bname) {
		this.bname = bname;
	}
	public String getGdate() {
		return gdate;
	}
	public void setGdate(String gdate) {
		this.gdate = gdate;
	}
	public Integer getSale() {
		return sale;
	}
	public void setSale(Integer sale) {
		this.sale = sale;
	}
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	
	public Integer getIsHot() {
		return isHot;
	}
	public void setIsHot(Integer isHot) {
		this.isHot = isHot;
	}
	public BigDecimal getMaxprice() {
		return maxprice;
	}
	public void setMaxprice(BigDecimal maxprice) {
		this.maxprice = maxprice;
	}
	public BigDecimal getMinprice() {
		return minprice;
	}
	public void setMinprice(BigDecimal minprice) {
		this.minprice = minprice;
	}

	
}
