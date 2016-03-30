package com.hzcwtech.wuzhong.service;



import com.hzcwtech.wuzhong.model.Question;
import com.hzcwtech.wuzhong.model.mapper.QuestionMapper;

public interface QuestionService extends QuestionMapper {

	void editQuestion(Question question);

	
	

}
