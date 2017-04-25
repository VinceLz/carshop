package com.xawl.car.domain;

/*
 * 银行实体
 */
public class Bank {
	private String bankId;
	private String bankname;

	public String getBankId() {
		return bankId;
	}

	public void setBankId(String bankId) {
		switch (bankId) {
		case "105":
			bankname = "建行";
			break;

		case "999":
			bankname = "银联";
			break;
		case "992":
			bankname = "支付宝";
			break;
		case "991":
			bankname = "微信";
			break;
		}
		this.bankId = bankId;
	}

	public String getBankname() {
		return bankname;
	}

	public void setBname(String bname) {
		this.bankname = bname;
	}

}
