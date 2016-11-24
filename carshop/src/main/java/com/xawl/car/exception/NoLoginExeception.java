package com.xawl.car.exception;

public class NoLoginExeception extends Exception {

	/**
	 * 用户没有登陆异常
	 */
	private static final long serialVersionUID = 1L;

	public NoLoginExeception() {
		super();
	}

	public NoLoginExeception(String msg){
		super(msg);
	}
}
