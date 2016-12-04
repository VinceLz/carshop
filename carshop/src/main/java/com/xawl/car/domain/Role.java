package com.xawl.car.domain;

/**
 * 规格类
 * 
 * @author kernel
 * 
 */
public class Role {
	private double gprice;// 参考价
	private double subsidy;// 补助
	private double guidegprice;// 参考价
	private String manufacturer;// 厂商

	private String level; // 等级

	private String engine; // 发动机

	private String transmission;// 变速箱

	private String len_wid_hei;// 长 宽高
	private String bodyStructure; // 车身结构
	private String maxVelocity; // 最高车速
	private String official0;
	private String reality0;
	private String reality110;
	private String oilWear;
	private String synthesizeOil;
	private String realityGroundClearance;// 实际离地间隙
	private String qualityAssurance;

	private String lenght;
	private String width;
	private String height;
	private String wheelBase;
	private String frontGauge;
	private String rearTrack;
	private String minGroundClearance;// 最小离地间隙
	private String hostlingQuality;// 整备质量
	private String bodyStruts;// 身结构
	private String doorNum;// 车门数量
	private String seat; // 座位数量
	private String fuelTankage;
	private String trunk;// 行李厢容量

	private String engineType;
	private String output;//
	private String outputMl;// ml
	private String onflowType;// 进气形式
	private String cylinderRank;// 气缸排序
	private String cylinderNum;
	private String eachCylinderRank;//
	private String compress;
	private String admissionGear;// 配气机构
	private String bore;// 缸径
	private String maxSoup;// 最大马力
	private String maxPower;// 最大功率
	private String maxPowerSpeed;// 最大功率转速
	private String maxTorsion;// 最大扭矩
	private String maxTorsionSpeed;// 最大扭矩转速
	private String specificSkill;// 特有技术
	private String fuelForm;// 燃料形式
	private String fuelLabel;// 然后标号
	private String oilSupplyMode;// 供油方式
	private String cylinderMaterial;// 缸体材料
	private String environmentalStandards;// 环保标准

	private String electromotorPower;//
	private String electromotorTorsion;// 电动工具扭矩
	private String frontElectromotorPowerMaxPower;// 前电动机最大攻
	private String frontElectromotorPowerMaxTorsion;//
	private String backElectromotorPowerMaxPower;// 后电动机最大攻
	private String backElectromotorPowerMaxTorsion;// 后
	private String mileage;// 续航里程
	private String batteryCapacity;
	private String batteryAssurance;// 电池质保
	private String batteryChargingTime;// 电池充电时间
	private String batteryPrice;// 电池价格

	private String shortName;// 简称
	private String gearNum;// 档位个数
	private String transmissionType;// 变速器类型
	private String drivingMode; // 驱动方式
	private String FourWheelDriveForm;// 四驱形式
	private String centerDifferentialMechanism;// 中央差速器结构
	private String frontSuspensionType;// 前悬架类型
	private String backSuspensionType;// 后悬架类型
	private String assistType; // 助力类型
	private String carBodyStruts;// 车体结构
	private String frontArresterType;// 前制动器类型
	private String backArresterType;// 后

	private String inCarArresterType;// 驻车制动类型
	private String frontTireType;// 前轮胎

	private String backTireType;// 后轮胎规格
	private String spareTireType;// 备胎规格

	private String mainDeputyPilothouseSafe;// 主/副驾驶室安全气
	private String frontBackExhaustSide; // 前后排侧气囊
	private String frontBackExhaustHead;// 前后排头气囊
	private String kneeAirSac;// 膝部
	private String tirePressureSurvey;// 胎压
	private String zeroTirePressureGo;// 0胎压继续形式
	private String safetyBeltRemind;// 安全带提醒
	private String USIFIXchildren;// 儿童
	private String engineElectronicProtect;// 电动机电子防护
	private String carLock;// 车内中控锁
	private String remoteKey;// 遥控
	private String noKeyStart;// 无遥控启动
	private String noKeyInSys;// 无遥控进入系统

	// 外部配置
	private String powerSunroof; // 电动天窗
	private String panoramicSunroof; // 全景天窗
	private String motionAppearanceSuite; // 运动外观套件
	private String aluminumAlloyWheels; // 铝合金轮圈
	private String electricSuctionDoor; // 电动吸合门
	private String slidingDoor; // 侧滑门
	private String electricTrunk; // 电动后备箱
	private String inductionTrunk; // 感应后备箱
	private String roofRack; // 车顶行李架

	// 操控配置
	private String ABSAntiLock;// ABS防抱死
	private String brakeForceDistribution;// 制动力分配
	private String brakeAssist;// 刹车辅助(EBA/BSA/)
	private String tractionControl;// 牵引力控制(ASR/TCS/)
	private String bodyStabilityControl;// 车身稳定控制(ESC/)
	private String hillStartAssist;// 上坡辅助
	private String automaticParking;// 自动驻车
	private String hdc;// 陡坡缓降
	private String variableSuspension;// 可变悬架
	private String airSuspension;// 空气悬架
	private String variableSteeringRatio;// 可变转向比
	private String frontAxleDifferential2mitedSlipDifferential;// 前桥限滑差速器/差速
	private String centralDifferentialLockFunction;// 中央差速器锁止功能
	private String limitedSlipDifferential2differentialSpeedOfRearAxle;// 后桥限滑差速器/差速

	// 座椅配置

	private String seatMaterial; // 座椅材质
	private String seatHeightAdjustment; // 座椅高低调节
	private String lumbarSupportAdjustment; // 腰部支撑调节
	private String zhuFudriverSeatElectricRegulation; // 主副驾驶电动调节
	private String secondRowBackAngleAdjustment; // 第二排靠背角度调节
	private String secondRowSeatMovement; // 第二排座椅移动
	private String frontAndRearRowSeatHeating; // 前/后排座椅加热
	private String thirdRowsOfSeats; // 第三排座椅
	private String theRearSeatsReclineWay; // 后排座椅放倒方式
	private String frontAndBackCentralRail; // 前/后中央扶手
	private String rearCupHolder; // 后排杯架

	// 内部配置
	private String leatherSteeringWheel;// 真皮方向盘
	private String steeringWheelAdjustment;// 方向盘调节
	private String steeringWheelElectricRegulation;// 方向盘电动调节
	private String multifunctionSteeringWheel; // 多功能方向盘
	private String steeringWheelShift; // 方向盘换挡
	private String steeringWheelHeating; // 方向盘加热
	private String steeringWheelMemory; // 方向盘记忆
	private String cruiseontrol; // 定速巡航
	private String frontrearParkingRadar; // 前后驻车雷达
	private String reverseVideoImage; // 倒车视频影像
	private String drivingComputerDisplayScreen; // 行车电脑显示屏
	private String allLCDPanel; // 全液晶仪盘表
	private String HUDheaderDigitalDisplay; // HUD抬头数字显示

	// 多媒体配置
	private String gPSGuidedSystem;// GPS导航系统
	private String locationOfInteractiveServices;// 定位互动服务
	private String instrumentPanelScreen;// 中控台彩色大屏
	private String bluetoothcarTelephone;// 蓝牙车载电话
	private String carTV;// 车载电视
	private String lCD_Panel;// 后排液晶屏
	private String powerSupply;// 220v/230电源
	private String externalAudioSourceInterface;// 外接音源接口
	private String CDsupport;// CD支持
	private String multimediAsystem;// 多媒体系统
	private String speakerBrand;// 扬声器品牌
	private String speakerNum;// 扬声器数量

	// 玻璃后视镜
	private String frontRearPowerWindows;// 前后电动车窗
	private String windowAntiPinchFunction;// 车窗防夹手功能
	private String ultravioletProofHeatInsulatingGlass;// 防紫外线隔热玻璃
	private String electricControlOfRearViewMirror;// 后视镜电动调节
	private String rearViewMirrorHeating;// 后视镜加热
	private String inOutRearViewMirrorAutomaticAntiGlare;// 内外后视镜自动防炫目
	private String rearViewMirrorElectricFolding;// 后视镜电动折叠
	private String rearViewMirrorMemory;// 后视镜记忆
	private String sunShade;// 后风挡遮阳帘
	private String rearSideSunShade;// 后排侧遮阳帘
	private String rearSidePrivacyGlass;// 后排侧隐私玻璃
	private String sunShadeBoardCosmeticMirror;// 遮阳板化妆镜
	private String rearWiper;// 后雨刷
	private String sensingWipers;// 感应雨刷	
	
	
	//空调/冰箱
			private String airConditioningControlMode;//空调控制方式
			private String rearIndependentAirConditioning;//后排独立空调
			private String rearAirOutlet;//后座出风口
			private String temperatureZoneControl;//温度分区控制
			private String airConditioningPollenInTheCar;//车内空气调节/花粉过
			private String aehicleMountedRefrigerator;//车载冰箱
	
	//灯光配置

	private String closeToTheLight ; //近光灯
	private String MuchLight; //远光灯
	private String daytimeRunningLights; //日间行车灯
	private String theAdaptiveDistanceOfLight ; //自适应远近光
	private String automaticHeadlights ; //自动头灯
	private String toAssistLamp; //转向辅助灯
	private String steeringHeadLamp ; // 转向头灯
	private String beforeTheFogLamps; //前雾灯
	private String headlightHeightIsAdjustable ; //大灯高度可调
	private String headlightCleaningDevices; //大灯清洗装置
	private String atmosphereInsideTheCarLights  ; //车内氛围灯

	// 高科技配置
	private String automaticParkings; //自动泊车入位
	private String engineStartStopTechnology; //发动机启停技术
	private String auxiliary; //并线辅助
	private String ldws; // 车道偏离预警系统
	private String activeBrakeActiveSafetySystem; //主动刹车
	private String  integratedActiveTeeringSystem; //整体主动转向系统
	private String  nightVisionSystem; //夜视系统
	private String LCDscreenControlPanelDisplay; //中控液晶屏分屏显示
	private String adaptiveCruiseControl;//自适应巡航
	private String panoramicCamera; //全景摄像头
	
	
	
}
