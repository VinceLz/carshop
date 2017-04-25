package com.xawl.car.util;

/**
 * ClassName: PayConf Function:
 * <p>
 * 参数配置文件
 * </p>
 * date: 2016-11-28 14:39
 * 
 * @author xmliu
 * @version 2.0
 * @since JDK 1.7
 */
public class PayConf {
	public final static String EZFMER_PAY_URL = "http://test.ezf123.com/jspt/payment/back-mobilepay.action";// 此处为模拟EZFMP支付地址，正式投产时需使用清算平台统一分配的地址

	// public final static String EZFMER_PAY_URL =
	// "http://localhost:8080/jspt/payment/back-mobilepay.action";

	public final static String EZFMER_BACKENDURL = "http://www.singpa.com/carshop/ycorder/black.action";// 此处为模拟接收清算平台后台发送通知结果的地址，正式投产时需使用清算平台统一分配的地址

	public final static String VERSION = "2.0"; // 版本号2.0
	public final static String CHARSET = "UTF-8"; // 字符集UTF-8或者GBK
	public final static String SIGNMETHOD = "MD5"; // 签名方法
	public final static String MERID = "20031"; // 此处为模拟联调商户号，正式投产时需使用清算平台分配的商户号
	public final static String SIGNKEY = "FUU2TBXPUE5PUG2GRD8Y5MUBXD6QJCMUWBCYW1UP33DBUHT7Y621962RC7HT";// 此处为模拟联调密钥，正式投产时需使用清算平台分配的密钥
	public final static String ORDERCURRENCY = "156"; // 币种
	public final static String TRANSTYPE = "01"; // 交易类型 01：消费
}
