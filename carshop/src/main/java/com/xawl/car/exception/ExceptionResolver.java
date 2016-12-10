package com.xawl.car.exception;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import com.xawl.car.domain.err;
import com.xawl.car.util.DateUtil;
import com.xawl.car.util.JsonUtil;
import com.xawl.car.util.keyUtil;

/**
 * 全局异常，跳转到404或者...
 * 
 * @author kernel
 * 
 */
@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
// 500错误
public class ExceptionResolver implements HandlerExceptionResolver {
	//private SqlSessionFactory sessionFactory;

//	@Autowired
//	public void setSqlSessionFactory(SqlSessionFactory sqlSessionFactory) {
//		this.sessionFactory = sqlSessionFactory;
//	}

	public ModelAndView resolveException(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex) {
		if (ex instanceof NoLoginExeception) {
			// 没有登陆异常
			System.out.println("我拦截到了");
			response.setStatus(HttpStatus.OK.value()); // 设置状态码
			response.setContentType(MediaType.APPLICATION_JSON_VALUE); // 设置ContentType
			response.setCharacterEncoding("UTF-8"); // 避免乱码
			response.setContentType("application/json;charset=UTF-8");
			JSONObject jsonObjec = JsonUtil
					.createJson(keyUtil.SERVICE_NO_LOGIN);
			jsonObjec.element("msg", "出现错误");
			response.setHeader("Cache-Control", "no-cache, must-revalidate");
			try {
				response.getWriter().print(jsonObjec.toString());
			} catch (IOException e) {
				e.printStackTrace();
			}
			return null;
		}
		err e1 = new err();
		e1.setJ_class(ex.getClass() + "");
		e1.setMessage(ex.getMessage());
		e1.setDate(DateUtil.getSqlDate());
		/* 使用response返回 */
	//	SqlSession openSession = sessionFactory.openSession(true);
	//	openSession.insert("com.xawl.shop.errMapper.insert", e1);
		response.setStatus(HttpStatus.OK.value()); // 设置状态码
		response.setContentType(MediaType.APPLICATION_JSON_VALUE); // 设置ContentType
		response.setCharacterEncoding("UTF-8"); // 避免乱码
		response.setContentType("application/json;charset=UTF-8");
		JSONObject jsonObjec = JsonUtil.createJson(keyUtil.SERVICE_ERROR);
		jsonObjec.element("msg", "出现错误");
		ex.printStackTrace();
		response.setHeader("Cache-Control", "no-cache, must-revalidate");
		try {
			response.getWriter().print(jsonObjec.toString());
		} catch (IOException e) {
		//	openSession.close();
		}
		//openSession.close();
		return null;
	}
}
