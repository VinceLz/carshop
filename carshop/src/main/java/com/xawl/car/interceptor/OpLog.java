package com.xawl.car.interceptor;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/*
 * 日志注解类
 */
@Target({ ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface OpLog {
	public static int ORDER_CREATE = 0;// 创建订单
	public static int REQUEST_PAY = 1;// 请求支付
	public static int SERVICE_BLAK = 2;// 服务器回调
	public static int Client_BLAK = 3;// 服务器回调

	public int OpLogType();
}
