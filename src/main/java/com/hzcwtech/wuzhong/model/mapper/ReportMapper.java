package com.hzcwtech.wuzhong.model.mapper;

import java.util.List;

import com.hzcwtech.wuzhong.model.Report;

public interface ReportMapper {
	
	public List<Report> getReportList(int functionId);

}
