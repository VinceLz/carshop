package com.xawl.car.interceptor;

import java.io.IOException;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.xawl.car.domain.User;
import com.xawl.car.service.UserService;
import com.xawl.car.util.JsonUtil;
import com.xawl.car.util.ResourceUtil;
import com.xawl.car.util.keyUtil;

public class RoleInterceptor implements HandlerInterceptor {
	@Resource
	private UserService userService;

	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {

		// 进行检测权限
		HandlerMethod methodHandler = (HandlerMethod) handler;
		java.lang.reflect.Method method = methodHandler.getMethod();
		Role role = method.getAnnotation(Role.class);
		if (role != null) {
			String token = request.getParameter("token");
			System.out.println("token" + token);
			int roleCode = role.role(); // 权限码
			if ((roleCode & Role.ROLE_USER) != 0) {// 需要登陆
				User user = (User) request.getSession().getAttribute(
						ResourceUtil.CURRENT_USER);
				System.out.println("获取到的user" + user);
				if (user != null && user.getToken().equals(token)) {
					return true;
				} else {
					System.out.println("session过期了");
					// session为空或者已经过期，则进行判断token
					User usernew = userService.getUserByToken(token);
					if (usernew == null) {
						System.out.println("获取新用户为null");
						send(response, keyUtil.SERVICE_NO_LOGIN);
						request.getSession().invalidate();
						return false;
					} else {
						request.getSession().setAttribute(
								ResourceUtil.CURRENT_USER, usernew);
						// 登陆成功 将最新的sessionId写入request并且写回客户端
						Cookie[] cookies = request.getCookies();
						for (Cookie cook : cookies) {
							if ("JSESSIONID".equals(cook.getName())) {
								cook.setValue(request.getSession().getId());
							}
						}
					}
				}

			}
		}
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {

	}

	@Override
	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex)
			throws Exception {

	}

	public void send(HttpServletResponse response, int status) {
		response.setStatus(HttpStatus.OK.value()); // 设置状态码
		response.setContentType(MediaType.APPLICATION_JSON_VALUE); // 设置ContentType
		response.setCharacterEncoding("UTF-8"); // 避免乱码
		response.setContentType("application/json;charset=UTF-8");
		JSONObject jsonObjec = JsonUtil.createJson(status);
		jsonObjec.element("msg", "error");
		response.setHeader("Cache-Control", "no-cache, must-revalidate");
		try {
			response.getWriter().print(jsonObjec.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
