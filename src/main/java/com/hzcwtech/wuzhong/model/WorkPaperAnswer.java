package com.hzcwtech.wuzhong.model;

public class WorkPaperAnswer {
	
	private Integer paperId;
	
	private Integer questionId;
	
	private String answer;
	
	private String comment;
	
	public Integer getPaperId() {
		return paperId;
	}
	
	public void setPaperId(Integer paperId) {
		this.paperId = paperId;
	}
	
	public Integer getQuestionId() {
		return questionId;
	}
	
	public void setQuestionId(Integer questionId) {
		this.questionId = questionId;
	}
	
	public String getAnswer() {
		return answer;
	}
	
	public void setAnswer(String answer) {
		this.answer = answer;
	}
	
	public String getComment() {
		return comment;
	}
	
	public void setComment(String comment) {
		this.comment = comment;
	}

}
