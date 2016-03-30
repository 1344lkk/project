package com.hzcwtech.wuzhong.model;

import java.sql.Timestamp;

public class LessonPlan {
	
	private Integer lessonId;
	
	private Lesson lesson;
	
	private CourseStage  stage;
	
	private String  lessonName;
	
	private String  stageName;
	
	private Integer stageId;
	
	private Timestamp startTime;
	
	private Timestamp endTime;

	public Integer getLessonId() {
		return lessonId;
	}

	public void setLessonId(Integer lessonId) {
		this.lessonId = lessonId;
	}

	public Integer getStageId() {
		return stageId;
	}

	public void setStageId(Integer stageId) {
		this.stageId = stageId;
	}

	public Timestamp getStartTime() {
		return startTime;
	}

	public void setStartTime(Timestamp startTime) {
		this.startTime = startTime;
	}

	public Timestamp getEndTime() {
		return endTime;
	}

	public void setEndTime(Timestamp endTime) {
		this.endTime = endTime;
	}

	public Lesson getLesson() {
		return lesson;
	}

	public void setLesson(Lesson lesson) {
		this.lesson = lesson;
	}

	public CourseStage getStage() {
		return stage;
	}

	public void setStage(CourseStage stage) {
		this.stage = stage;
	}

	public String getLessonName() {
		return lessonName;
	}

	public void setLessonName(String lessonName) {
		this.lessonName = lessonName;
	}

	public String getStageName() {
		return stageName;
	}

	public void setStageName(String stageName) {
		this.stageName = stageName;
	}
	
	
	
	

}
