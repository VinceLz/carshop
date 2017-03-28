package com.xawl.car.interceptor;

import java.lang.annotation.Documented;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface Role {
	public static int ROLE_USER = 1; // 只有普通用户
	public static String Msg = "权限不足";
	public int role() default 1;

}
