package com.xawl.car.domain;


/*
 * 汽车配置项
 * 
 */
public class CarConfig {
	// 添加到基本参数中
	private String country;// 国家
	private String produce;// 生产方式
	private String seat;// 座位

	private String firm;// 厂商
	private String motor;// 发动机
	private String transmission;// 变速相
	private String level; // 等级
	private String structure;// 结构
	private String l_w_h;
	private String wheel_base;// 轴距
	private String compartment_volume; // 行李厢溶剂

	private String quality;// 整车质量
	private String car_color;// 车身颜色

	private String output;// 排量

	private String air_form;// 进气形式

	private String cylinder;// 气缸

	private String max_soup;// 最大马力

	private String max_torsion;// 最大扭矩

	private String fuel;// 燃料

	private String fuel_label;// 燃油标号

	private String oil_supply;// 供油方式
	private String effluent_standard;// 排放标准
	private String drive;// 驱动

	private String assistance_model;// 助力类型

	private String front_hang;// 前悬挂
	private String back_hang;// 后悬挂

	private String front_braking;// 前制动
	private String back_braking;// 后制动

	private String in_car_braking;// 驻车制动类型

	private String front_tyre;// 前轮胎规格
	private String back_tyre;// 后轮胎规格

	private String lead_air_bag;// 主副安全气囊
	private String font_back_air_bag;// 前后安全气囊
	private String font_back_head_air_bag;// 前后排头部安全气囊
	private String pressure_survey;// 胎压监测
	private String car_lock;// 车内控锁
	private String child_seat;// 儿童座椅接口
	private String no_key_start;// 无钥匙启动
	private String ABS;// 防抱死系统
	private String car_steady;// 车稳定控制

	private String window;// 电动窗户
	private String overall_window;// 全景天窗
	private String headlight;// 大灯
	private String LED;// LED大灯
	private String auto_light;// 自动大灯
	private String front_light;// 前雾灯
	private String front_back_window;// 前后电动车床
	private String back_vision;// 后视镜电动调节
	private String back_vision_hot;// 后视镜加热

	private String corium_seat;// 真皮座椅
	private String seat_hot;// 座椅加热
	private String seat_ventilate;// 座椅通风
	private String steering_wheel;// 方向盘
	private String cruise;// 定速巡航
	private String GPS;// GPS导航
	private String radar;// 雷达
	private String image;// 影像倒车
	private String air_conditioner;// 空调

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

	public String getSeat() {
		return seat;
	}

	public void setSeat(String seat) {
		this.seat = seat;
	}

	public String getFirm() {
		return firm;
	}

	public void setFirm(String firm) {
		this.firm = firm;
	}

	public String getMotor() {
		return motor;
	}

	public void setMotor(String motor) {
		this.motor = motor;
	}

	public String getTransmission() {
		return transmission;
	}

	public void setTransmission(String transmission) {
		this.transmission = transmission;
	}

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public String getStructure() {
		return structure;
	}

	public void setStructure(String structure) {
		this.structure = structure;
	}

	public String getL_w_h() {
		return l_w_h;
	}

	public void setL_w_h(String l_w_h) {
		this.l_w_h = l_w_h;
	}

	public String getWheel_base() {
		return wheel_base;
	}

	public void setWheel_base(String wheel_base) {
		this.wheel_base = wheel_base;
	}

	public String getCompartment_volume() {
		return compartment_volume;
	}

	public void setCompartment_volume(String compartment_volume) {
		this.compartment_volume = compartment_volume;
	}

	public String getQuality() {
		return quality;
	}

	public void setQuality(String quality) {
		this.quality = quality;
	}

	public String getCar_color() {
		return car_color;
	}

	public void setCar_color(String car_color) {
		this.car_color = car_color;
	}

	public String getOutput() {
		return output;
	}

	public void setOutput(String output) {
		this.output = output;
	}

	public String getAir_form() {
		return air_form;
	}

	public void setAir_form(String air_form) {
		this.air_form = air_form;
	}

	public String getCylinder() {
		return cylinder;
	}

	public void setCylinder(String cylinder) {
		this.cylinder = cylinder;
	}

	public String getMax_soup() {
		return max_soup;
	}

	public void setMax_soup(String max_soup) {
		this.max_soup = max_soup;
	}

	public String getMax_torsion() {
		return max_torsion;
	}

	public void setMax_torsion(String max_torsion) {
		this.max_torsion = max_torsion;
	}

	public String getFuel() {
		return fuel;
	}

	public void setFuel(String fuel) {
		this.fuel = fuel;
	}

	public String getFuel_label() {
		return fuel_label;
	}

	public void setFuel_label(String fuel_label) {
		this.fuel_label = fuel_label;
	}

	public String getOil_supply() {
		return oil_supply;
	}

	public void setOil_supply(String oil_supply) {
		this.oil_supply = oil_supply;
	}

	public String getEffluent_standard() {
		return effluent_standard;
	}

	public void setEffluent_standard(String effluent_standard) {
		this.effluent_standard = effluent_standard;
	}

	public String getDrive() {
		return drive;
	}

	public void setDrive(String drive) {
		this.drive = drive;
	}

	public String getAssistance_model() {
		return assistance_model;
	}

	public void setAssistance_model(String assistance_model) {
		this.assistance_model = assistance_model;
	}

	public String getFront_hang() {
		return front_hang;
	}

	public void setFront_hang(String front_hang) {
		this.front_hang = front_hang;
	}

	public String getBack_hang() {
		return back_hang;
	}

	public void setBack_hang(String back_hang) {
		this.back_hang = back_hang;
	}

	public String getFront_braking() {
		return front_braking;
	}

	public void setFront_braking(String front_braking) {
		this.front_braking = front_braking;
	}

	public String getBack_braking() {
		return back_braking;
	}

	public void setBack_braking(String back_braking) {
		this.back_braking = back_braking;
	}

	public String getIn_car_braking() {
		return in_car_braking;
	}

	public void setIn_car_braking(String in_car_braking) {
		this.in_car_braking = in_car_braking;
	}

	public String getFront_tyre() {
		return front_tyre;
	}

	public void setFront_tyre(String front_tyre) {
		this.front_tyre = front_tyre;
	}

	public String getBack_tyre() {
		return back_tyre;
	}

	public void setBack_tyre(String back_tyre) {
		this.back_tyre = back_tyre;
	}

	public String getLead_air_bag() {
		return lead_air_bag;
	}

	public void setLead_air_bag(String lead_air_bag) {
		this.lead_air_bag = lead_air_bag;
	}

	public String getFont_back_air_bag() {
		return font_back_air_bag;
	}

	public void setFont_back_air_bag(String font_back_air_bag) {
		this.font_back_air_bag = font_back_air_bag;
	}

	public String getFont_back_head_air_bag() {
		return font_back_head_air_bag;
	}

	public void setFont_back_head_air_bag(String font_back_head_air_bag) {
		this.font_back_head_air_bag = font_back_head_air_bag;
	}

	public String getPressure_survey() {
		return pressure_survey;
	}

	public void setPressure_survey(String pressure_survey) {
		this.pressure_survey = pressure_survey;
	}

	public String getCar_lock() {
		return car_lock;
	}

	public void setCar_lock(String car_lock) {
		this.car_lock = car_lock;
	}

	public String getChild_seat() {
		return child_seat;
	}

	public void setChild_seat(String child_seat) {
		this.child_seat = child_seat;
	}

	public String getNo_key_start() {
		return no_key_start;
	}

	public void setNo_key_start(String no_key_start) {
		this.no_key_start = no_key_start;
	}

	public String getABS() {
		return ABS;
	}

	public void setABS(String aBS) {
		ABS = aBS;
	}

	public String getCar_steady() {
		return car_steady;
	}

	public void setCar_steady(String car_steady) {
		this.car_steady = car_steady;
	}

	public String getWindow() {
		return window;
	}

	public void setWindow(String window) {
		this.window = window;
	}

	public String getOverall_window() {
		return overall_window;
	}

	public void setOverall_window(String overall_window) {
		this.overall_window = overall_window;
	}

	public String getHeadlight() {
		return headlight;
	}

	public void setHeadlight(String headlight) {
		this.headlight = headlight;
	}

	public String getLED() {
		return LED;
	}

	public void setLED(String lED) {
		LED = lED;
	}

	public String getAuto_light() {
		return auto_light;
	}

	public void setAuto_light(String auto_light) {
		this.auto_light = auto_light;
	}

	public String getFront_light() {
		return front_light;
	}

	public void setFront_light(String front_light) {
		this.front_light = front_light;
	}

	public String getFront_back_window() {
		return front_back_window;
	}

	public void setFront_back_window(String front_back_window) {
		this.front_back_window = front_back_window;
	}

	public String getBack_vision() {
		return back_vision;
	}

	public void setBack_vision(String back_vision) {
		this.back_vision = back_vision;
	}

	public String getBack_vision_hot() {
		return back_vision_hot;
	}

	public void setBack_vision_hot(String back_vision_hot) {
		this.back_vision_hot = back_vision_hot;
	}

	public String getCorium_seat() {
		return corium_seat;
	}

	public void setCorium_seat(String corium_seat) {
		this.corium_seat = corium_seat;
	}

	public String getSeat_hot() {
		return seat_hot;
	}

	public void setSeat_hot(String seat_hot) {
		this.seat_hot = seat_hot;
	}

	public String getSeat_ventilate() {
		return seat_ventilate;
	}

	public void setSeat_ventilate(String seat_ventilate) {
		this.seat_ventilate = seat_ventilate;
	}

	public String getSteering_wheel() {
		return steering_wheel;
	}

	public void setSteering_wheel(String steering_wheel) {
		this.steering_wheel = steering_wheel;
	}

	public String getCruise() {
		return cruise;
	}

	public void setCruise(String cruise) {
		this.cruise = cruise;
	}

	public String getGPS() {
		return GPS;
	}

	public void setGPS(String gPS) {
		GPS = gPS;
	}

	public String getRadar() {
		return radar;
	}

	public void setRadar(String radar) {
		this.radar = radar;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getAir_conditioner() {
		return air_conditioner;
	}

	public void setAir_conditioner(String air_conditioner) {
		this.air_conditioner = air_conditioner;
	}
}
