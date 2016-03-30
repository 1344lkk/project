package com.hzcwtech.wuzhong.service;



import com.hzcwtech.wuzhong.model.ReportsDatas;
import com.hzcwtech.wuzhong.model.mapper.ReportMapper;

public interface ReportService extends ReportMapper {

	public ReportsDatas getReportsList(int functionIdA,int functionIdB);
   
	

}
