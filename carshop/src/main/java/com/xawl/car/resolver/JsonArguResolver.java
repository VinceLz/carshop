package com.xawl.car.resolver;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.apache.ibatis.io.ResolverUtil;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebArgumentResolver;
import org.springframework.web.context.request.NativeWebRequest;

import com.xawl.car.domain.JSON;
import com.xawl.car.domain.MaintainBusiness;
import com.xawl.car.interceptor.Role;
import com.xawl.car.util.JsonUtil;
import com.xawl.car.util.ResourceUtil;
import com.xawl.car.util.keyUtil;

public class JsonArguResolver implements WebArgumentResolver {
	@Override
	public Object resolveArgument(MethodParameter methodParameter,
			NativeWebRequest webRequest) throws Exception {
		HttpServletRequest request = webRequest
				.getNativeRequest(HttpServletRequest.class);
		//
		System.out.println("-----------" + methodParameter.getParameterType());
		if (methodParameter.getParameterType() == JSON.class
				&& methodParameter.getParameterType() != null) {
			JSONObject jsonObjec = JsonUtil.createJson(keyUtil.SERVICE_SUCCESS);
			JSON json = new JSON(jsonObjec);
			return json;
		} else if (methodParameter.getParameterType() == MaintainBusiness.class
				&& methodParameter.getParameterType() != null) {
			// 需要注入商家
			MaintainBusiness business = (MaintainBusiness) request.getSession()
					.getAttribute(ResourceUtil.CURRENT_BUSINESS);
			if (business != null) {
				return business;
			}
			return UNRESOLVED;
		}

		return UNRESOLVED;
	}
}