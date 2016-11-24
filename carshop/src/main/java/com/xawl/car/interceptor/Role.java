package com.xawl.car.interceptor;

import java.lang.annotation.Documented;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface Role {
	public static int ROLE_BUSINESS = 2; // 只有商家
	public static int ROLE_USER = 1; // 只有普通用户
	public static int ROLE_ADMIN = 4; // 只有管理员
	public static int ROLE_USER_BUSINESS = 3; // 用户 和商家
	public static int ROLE_USER_ADMIN = 5; // 用户和 管理员
	public static int ROLE_BUSINESS_ADMIN = 6; // 商家和管理员
	public static int ALL = 7; // 所有的 但是必须登录过
	public static String Msg = "权限不足";

	public int role() default 7;

}
