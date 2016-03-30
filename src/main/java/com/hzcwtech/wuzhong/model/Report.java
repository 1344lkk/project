package com.hzcwtech.wuzhong.model;

import java.sql.Timestamp;

public class Report {
	
	
	private String data;
	
	private  Timestamp insTime;
	
	private  int functionId;

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	
	public Timestamp getInsTime() {
		return insTime;
	}

	public void setInsTime(Timestamp insTime) {
		this.insTime = insTime;
	}

	public int getFunctionId() {
		return functionId;
	}

	public void setFunctionId(int functionId) {
		this.functionId = functionId;
	}
	
	

}
