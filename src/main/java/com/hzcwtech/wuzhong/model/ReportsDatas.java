package com.hzcwtech.wuzhong.model;

import java.util.ArrayList;
import java.util.List;

public class ReportsDatas {

	
	private List<String> xaxis =  new ArrayList<String>();;
	
	private List<List<Double>> yaxis =  new ArrayList<List<Double>>();;

	public List<String> getXaxis() {
		return xaxis;
	}

	public void setXaxis(List<String> xaxis) {
		this.xaxis = xaxis;
	}

	public List<List<Double>> getYaxis() {
		return yaxis;
	}

	public void setYaxis(List<List<Double>> yaxis) {
		this.yaxis = yaxis;
	}

	
	
	
}
