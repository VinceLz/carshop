package com.xawl.car.interceptor;

import java.lang.annotation.Documented;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface UserSession {
	public static String USER="user";
	public static String BUSINESS="business";
	//是否从session中注入User 用户，还是前台发过来的用户
	public String session() default "";
}
