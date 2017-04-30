package com.xawl.car.service.impl;

import java.lang.reflect.Method;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.xawl.car.dao.OptionLogMapper;
import com.xawl.car.domain.Bank;
import com.xawl.car.domain.OptionLog;
import com.xawl.car.domain.User;
import com.xawl.car.domain.YcOrder;
import com.xawl.car.interceptor.OpLog;
import com.xawl.car.util.DateUtil;

//@Aspect
//@Component
public class SnapshotLogWriteService {
//	@Autowired
	private OptionLogMapper optionLogMapperion;
//
//	public OptionLogMapper getLogService() {
//		return optionLogMapperion;
//	}

	// 环绕通知方法
	//@Around("execution(* com.xawl.car.controller.*.*(..))")
	public Object doWriteLog(ProceedingJoinPoint pjp) {
		Object target = pjp.getTarget();
		// 拦截的方法名称
		String methodName = pjp.getSignature().getName();
		// 拦截的方法参数
		Object[] args = pjp.getArgs();
		// 拦截的放参数类型

		Class[] parameterTypes = ((MethodSignature) pjp.getSignature())
				.getMethod().getParameterTypes();
		Object object = null;
		// 获得被拦截的方法
		Method method = null;
		try {
			method = target.getClass().getMethod(methodName, parameterTypes);
		} catch (NoSuchMethodException e1) {
			e1.printStackTrace();
		} catch (SecurityException e1) {
			e1.printStackTrace();
		}
		OptionLog log = null;
		if (null != method) {
			// 判断是否包含自定义的注解
			if (method.isAnnotationPresent(OpLog.class)) {

				// 获取自定义注解实体
				OpLog myAnnotation = method.getAnnotation(OpLog.class);
				switch (myAnnotation.OpLogType()) {
				case OpLog.ORDER_CREATE: // 创建订单
					User user = findParamByType(User.class, args,
							parameterTypes);
					YcOrder order = findParamByType(YcOrder.class, args,
							parameterTypes);
					log = new OptionLog();
					log.setGoodid(order.getGoodid());
					log.setUlogin(user.getUlogin());
					log.setCreatedate(DateUtil.getSqlDate());
					log.setContent("创建订单-----"
							+ "订单价格:"
							+ order.getPrice()
							+ "----创建时间:"
							+ (DateUtil.getSqlDate() + "商家:"
									+ order.getBmname() + "---手机号:"
									+ order.getUphone() + "---优惠劵" + order
										.getRuid()));
					break;
				case OpLog.REQUEST_PAY:// 请求支付
					Bank bank = findParamByType(Bank.class, args,
							parameterTypes);

					String goodid = findParamByType(String.class, args,
							parameterTypes);
					User user2 = findParamByType(User.class, args,
							parameterTypes);
					log = new OptionLog();
					log.setBankname(bank.getBankname());
					log.setGoodid(goodid);
					log.setUlogin(user2.getUlogin());
					log.setCreatedate(DateUtil.getSqlDate());
					log.setContent("---发起交易请求--------");
					break;
				case OpLog.SERVICE_BLAK:
					// 服务器回调接口
					// 先判断服务器的回调是否正确，不正确则直接丢弃
					HttpServletRequest request = findParamByType(
							HttpServletRequest.class, args, parameterTypes);
					String sign = request.getParameter("sign");
					if (sign == null || sign.isEmpty()) {
						// 直接丢弃本次请求
						return null;
					}
					log = new OptionLog();
					log.setContent("---收到服务器的回调");
					log.setCreatedate(DateUtil.getSqlDate());
					break;
				case OpLog.Client_BLAK:
					User user3 = findParamByType(User.class, args,
							parameterTypes);
					String goodid2 = findParamByType(String.class, args,
							parameterTypes);
					Integer status = findParamByType(Integer.class, args,
							parameterTypes);
					log = new OptionLog();
					log.setContent("---客户端回调  status：" + status);
					log.setCreatedate(DateUtil.getSqlDate());
					log.setGoodid(goodid2);
					log.setUlogin(user3.getUlogin());
					break;
				}
				try {
					object = pjp.proceed(args);
					log.setResult("" + object);
					log.setStatus(1);
				} catch (Throwable e) {
					log.setStatus(0);
					log.setExeception(e.toString());
					e.printStackTrace();
				}
				optionLogMapperion.insertLog(log);
				return object;
			}
			// 不进行拦截
			try {
				object = pjp.proceed(args);
			} catch (Throwable e) {
				e.printStackTrace();
			}

		}
		return object;
	}

	public <T> T findParamByType(Class<T> c, Object[] par,
			Class[] parameterTypes) {
		for (int i = 0; i < parameterTypes.length; i++) {
			if (parameterTypes[i].equals(c)) {
				return (T) par[i];
			}
		}
		return null;
	}
}
