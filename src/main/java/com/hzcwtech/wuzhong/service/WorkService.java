package com.hzcwtech.wuzhong.service;

import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSONObject;
import com.hzcwtech.wuzhong.model.Praise;
import com.hzcwtech.wuzhong.model.Work;
import com.hzcwtech.wuzhong.model.mapper.WorkMapper;

public interface WorkService extends WorkMapper {

	void insertWorkPhoto(Work work, ArrayList<Object> paths);

	void anserWorkPaper(Work work,String answer);

	void increasePraise(int workId,Praise praise);

	void cancel(int workId, int userId);

	public int anserWorkPaper(Work work, JSONObject answer);

	void updateWork(Work work);

	public  List<Work> getWorkByStudentId(Integer stuId);

	

	
}
