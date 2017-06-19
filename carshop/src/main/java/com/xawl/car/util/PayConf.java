package com.xawl.car.util;

public class PayConf {
	// public static final String EZFMER_PAY_URL =
	// "https://www.ezf123.com/jspt/payment/frontTransReq.action";
	// public static final String REFUND_QUERY_URL =
	// "https://www.ezf123.com/jspt_query/payment/back-refund-query.action";
	// public static final String JSPT_QUERY_URL =
	// "https://www.ezf123.com/jspt_query/payment/back-order-query.action";
	public static final String EZFMER_BACKENDURL = "http://www.singpa.com/carshop/ycorder/black.action";
	// public static final String REFUND_PAY_URL =
	// "https://www.ezf123.com/jspt/payment/order-refund.action";
	// 支付相关
	public final static String JSPT_PAY_URL = "https://www.ezf123.com/jspt/payment/back-mobilepay.action";
	public final static String JSPT_QUERY_URL = "https://www.ezf123.com/jspt_query/payment/back-order-query.action";
	// 退款相关
	public final static String REFUND_PAY_URL = "https://www.ezf123.com/jspt/payment/order-refund.action";// 测试环境地址，正式投产时需使用清算平台统一分配的地址
	public final static String REFUND_QUERY_URL = "https://www.ezf123.com/jspt_query/payment/back-refund-query.action";// 测试环境地址，正式投产时需使用清算平台统一分配的地址
	public static final String VERSION = "2.0";
	public final static String REF_TRANSTYPE = "02"; // 02-退款交易
	public static final String CHARSET = "UTF-8";
	public static final String SIGNMETHOD = "MD5";
	// public static final String MERID = "20031";
	// public static final String SIGNKEY =
	// "FUU2TBXPUE5PUG2GRD8Y5MUBXD6QJCMUWBCYW1UP33DBUHT7Y621962RC7HT";
	public static final String MERID = "57710";
	public static final String SIGNKEY = "2PM46XMBS9PTYLZ3FDDZYCGUUQGEE3L3HU6R266C5PJ5CNC1DULUC36S6Y13";
	public static final String ORDERCURRENCY = "156";
	public static final String TRANSTYPE = "01";
}
