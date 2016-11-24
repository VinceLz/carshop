package com.xawl.car.util;

import java.sql.Timestamp;

public class SqlDate extends Timestamp{

	public SqlDate(long time) {
		super(time);
	}
	@Override
	public String toString() {
		return toLocaleString();
	}

}
