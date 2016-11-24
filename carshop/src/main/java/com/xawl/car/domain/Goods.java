package com.xawl.car.domain;

//车
public class Goods {
	// 级别，价格，排量，驱动，燃料，变速箱，国别，生产方式，结构，座位
	// 品牌，型号，车名
	private Integer gid;
	private String gname;
	private Double gprice;
	private Integer bid;// 商家id
	private String gcontent;// 详情展示页的描述
	private String gfirstimage;// 商品轮图图
	private String bname;// 店家名
	private String gdate;// 上传时间
	private Integer sale;// 售量
	private String glastimage;// 详情页中的图片
	private String format;// 前台规格
	private String level; // 等级
	private String output;// 排量  
	private String drive;// 驱动
	private String fuel;// 燃料
	private String transmission;// 变速相
	private String country;// 国家
	private String produce;// 生产方式
	private String structure;// 结构
	private String seat;// 座位
	private String brand;// 品牌
	private String model;// 型号
	private String keyword; //关键字
	private Double guidegprice;//指导价
	private String title; //优惠信息
	
	public Double getGuidegprice() {
		return guidegprice;
	}
	public void setGuidegprice(Double guidegprice) {
		this.guidegprice = guidegprice;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getKeyword() {
		return keyword;
	}
	public void setKeyword(String keyword) {
		this.keyword = keyword;
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
	public Double getGprice() {
		return gprice;
	}
	public void setGprice(Double gprice) {
		this.gprice = gprice;
	}
	public Integer getBid() {
		return bid;
	}
	public void setBid(Integer bid) {
		this.bid = bid;
	}
	public String getGcontent() {
		return gcontent;
	}
	public void setGcontent(String gcontent) {
		this.gcontent = gcontent;
	}
	public String getGfirstimage() {
		return gfirstimage;
	}
	public void setGfirstimage(String gfirstimage) {
		this.gfirstimage = gfirstimage;
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
	public String getGlastimage() {
		return glastimage;
	}
	public void setGlastimage(String glastimage) {
		this.glastimage = glastimage;
	}
	public String getFormat() {
		return format;
	}
	public void setFormat(String format) {
		this.format = format;
	}
	public String getLevel() {
		return level;
	}
	public void setLevel(String level) {
		this.level = level;
	}
	public String getOutput() {
		return output;
	}
	public void setOutput(String output) {
		this.output = output;
	}
	public String getDrive() {
		return drive;
	}
	public void setDrive(String drive) {
		this.drive = drive;
	}
	public String getFuel() {
		return fuel;
	}
	public void setFuel(String fuel) {
		this.fuel = fuel;
	}
	public String getTransmission() {
		return transmission;
	}
	public void setTransmission(String transmission) {
		this.transmission = transmission;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getProduce() {
		return produce;
	}
	public void setProduce(String produce) {
		this.produce = produce;
	}
	public String getStructure() {
		return structure;
	}
	public void setStructure(String structure) {
		this.structure = structure;
	}
	public String getSeat() {
		return seat;
	}
	public void setSeat(String seat) {
		this.seat = seat;
	}
	public String getBrand() {
		return brand;
	}
	public void setBrand(String brand) {
		this.brand = brand;
	}
	public String getModel() {
		return model;
	}
	public void setModel(String model) {
		this.model = model;
	}
	
	
}
