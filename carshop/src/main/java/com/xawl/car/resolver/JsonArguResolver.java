package com.xawl.car.resolver;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebArgumentResolver;
import org.springframework.web.context.request.NativeWebRequest;

import com.xawl.car.domain.JSON;
import com.xawl.car.interceptor.Role;
import com.xawl.car.util.JsonUtil;
import com.xawl.car.util.keyUtil;

public class JsonArguResolver implements WebArgumentResolver {
	@Override
	public Object resolveArgument(MethodParameter methodParameter,
			NativeWebRequest webRequest) throws Exception {
		HttpServletRequest request = webRequest
				.getNativeRequest(HttpServletRequest.class);
		//
		if (methodParameter.getParameterType().equals(JSON.class)
				&& methodParameter.getParameterType() != null) {
			JSONObject jsonObjec = JsonUtil.createJson(keyUtil.SERVICE_SUCCESS);

			JSON json = new JSON(jsonObjec);
			Role role = methodParameter.getMethod().getAnnotation(Role.class);
			if (role != null) {
				json.add("JSESSIONID", request.getSession().getId());
			}
			return json;
		}

		// if (methodParameter.getParameterType().equals(User.class)
		// || methodParameter.getParameterType().equals(Business.class)
		// && methodParameter.getParameterType() != null) {
		// UserSession Annotation = methodParameter
		// .getParameterAnnotation(UserSession.class);
		// if (Annotation != null) {
		// String falg = Annotation.session();
		// if (falg.equals("user")) {
		// User user = (User) request.getSession().getAttribute(
		// ResourceUtil.CURRENT_USER);
		// return user;
		// } else if (falg.equals("business")) {
		// Business bu = (Business) request.getSession().getAttribute(
		// ResourceUtil.CURRENT_BUSINESS);
		// return bu;
		// } else if (falg.equals("admin")) {
		// // 管理员
		// } else {
		// return UNRESOLVED;
		// }
		//
		// }
		// }
		return UNRESOLVED;
	}
}