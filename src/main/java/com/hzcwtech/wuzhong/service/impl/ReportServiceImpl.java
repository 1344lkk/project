package com.hzcwtech.wuzhong.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hzcwtech.wuzhong.model.Report;
import com.hzcwtech.wuzhong.model.ReportsDatas;
import com.hzcwtech.wuzhong.model.mapper.ReportMapper;
import com.hzcwtech.wuzhong.service.ReportService;
import com.hzcwtech.wuzhong.util.DateUtil;
import com.hzcwtech.wuzhong.util.MathUtil;
import com.hzcwtech.wuzhong.util.StringUtil;

@Service
public class ReportServiceImpl  implements ReportService{
    @Autowired
    private ReportMapper report;
	
	public ReportsDatas getReportsList(int functionIdA,int functionIdB) {
		ReportsDatas reprotDatas = new ReportsDatas();
		int[] funtions = {functionIdA,functionIdB};
		String pattern = DateUtil.HH_mm;
		for(int i = 0;i<=1;i++){
		
		List<Double> list = new ArrayList<Double>();
		List<Report> repoort = 	report.getReportList(funtions[i]);
		if(repoort!=null){
		try {
			for(Report re :repoort){
				if(i==0){
			    reprotDatas.getXaxis().add(DateUtil.format(re.getInsTime(),pattern));}
			
			if (StringUtil.isTrimBlank(re.getData())
					|| Double.parseDouble(re.getData()) < 0) {
				list.add(Double.parseDouble("0"));
			} else {
				list.add(Double.parseDouble(MathUtil.round(re.getData(), 1,
						BigDecimal.ROUND_HALF_UP)));
			}
			}
			reprotDatas.getYaxis().add(list);
		} catch (NumberFormatException e) {
			
			e.printStackTrace();
		}
		}}
		
		return reprotDatas;
		 
	}
	

	@Override
	public List<Report> getReportList(int functionId) {
		// TODO Auto-generated method stub
		return null;
	}

}
