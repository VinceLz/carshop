package com.xawl.car.util;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ExecutorUtil {
	// 全局单例模式
	private static ExecutorService service;

	public static synchronized ExecutorService getInstance() {
		if (service == null)
			service = Executors.newCachedThreadPool();
		return service;
	}
}
