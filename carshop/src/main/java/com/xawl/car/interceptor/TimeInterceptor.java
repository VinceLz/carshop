package com.xawl.car.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.core.NamedThreadLocal;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.xawl.car.util.NetUtil;

import common.Logger;

//功能：实现对所有类方法执行时间的监控
public class TimeInterceptor implements HandlerInterceptor {
	//引入log4j日志
	private static Logger log = Logger.getLogger(TimeInterceptor.class);
	
	//利用ThreadLocal绑定一个变量，完成线程安全
	NamedThreadLocal<Long> startTimeThreadLocal = new NamedThreadLocal<Long>("startTimeThreadLocal");
	
	//处理类之前，执行preHandle方法
	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		response.addHeader("Access-Control-Allow-Origin", "*");
		long startTime = System.currentTimeMillis();		//记录当前时间
		startTimeThreadLocal.set(startTime);				//绑定变量
		
		return true;
	}

	//处理类之后，执行postHandler方法
	@Override
	public void postHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		long stopTime = System.currentTimeMillis();			//记录结束时间
		
		log.info(String.format("IP:"+NetUtil.getIp(request)+"访问----"+"%s execute %d ms."
				,request.getRequestURI()
				, stopTime - startTimeThreadLocal.get()));
		
	}

	//所有的动作完成，执行一些方法
	@Override
	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		// TODO Auto-generated method stub
		
	}

}
