package com.hzcwtech.wuzhong.model.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.hzcwtech.mybatis.Pager;
import com.hzcwtech.wuzhong.model.Question;
import com.hzcwtech.wuzhong.model.QuestionOption;

public interface QuestionMapper {
	
	/*question*/
	
	public List<Question> getQuestionListByTaskId(@Param("pager") Pager pager,
			@Param("q") String q,@Param("taskId")int taskId);
	
	public Question getQuestion(@Param("id") int id);
	
	public List<Question> getQuestionByPaperId(int paperId);
	
	public void insertQuestion(Question question);
	
	public void updateQuestion(Question question);
	
	public void deleteQuestion(@Param("id") int id);
	
	/*question Option*/
	
    public List<QuestionOption> getQuestionOptionListByQuestionId(int questionId);
	
	public QuestionOption getQuestionOption(@Param("id") int id);
	
	public void insertQuestionOption(QuestionOption questionOption);
	
	public void updateQuestionOption(QuestionOption questionOption);
	
	public void deleteQuestionOption(@Param("id") int id);
	
	public List<Question> getQuestionListByTaskIdRandom(Integer taskId);
	
	

}
