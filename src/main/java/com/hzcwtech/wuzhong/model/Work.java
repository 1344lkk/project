package com.hzcwtech.wuzhong.model;

import java.util.List;

public class Work {
	
	private Integer learningId; 
	
	private Integer taskId;
	
	private Integer id;
	
	private Integer state = 0;
	
	private Integer viewCount = 0;
	
	private Integer likeCount = 0;
	
	private Integer noteCount = 0;
	
	private Integer points;
	
	private List<WorkPhoto> pthotos;
	
	private List<WorkComment> workComments;
	
	private int count = 0;
	
	private String workTaskName;
	
	private String  workstage;
	
	private String lessonName;

	private String remark; //评语

	public void setRemark(String remark){this.remark = remark;}

	public String getRemark(){return remark;}
	
	public String getLessonName() {
		return lessonName;
	}

	public void setLessonName(String lessonName) {
		this.lessonName = lessonName;
	}

	public String getWorkstage() {
		return workstage;
	}

	public void setWorkstage(String workstage) {
		this.workstage = workstage;
	}

	public String getWorkTaskName() {
		return workTaskName;
	}

	public void setWorkTaskName(String workTaskName) {
		this.workTaskName = workTaskName;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	private boolean praiseOrNot;
	
	
	public boolean isPraiseOrNot() {
		return praiseOrNot;
	}

	public void setPraiseOrNot(boolean praiseOrNot) {
		this.praiseOrNot = praiseOrNot;
	}

	public List<WorkComment> getWorkComments() {
		return workComments;
	}

	public void setWorkComments(List<WorkComment> workComments) {
		this.workComments = workComments;
	}

	public List<WorkPhoto> getPthotos() {
		return pthotos;
	}

	public void setPthotos(List<WorkPhoto> pthotos) {
		this.pthotos = pthotos;
	}

	public Integer getLearningId() {
		return learningId;
	}
	
	public void setLearningId(Integer learningId) {
		this.learningId = learningId;
	}
	
	public Integer getTaskId() {
		return taskId;
	}
	
	public void setTaskId(Integer taskId) {
		this.taskId = taskId;
	}
	
	public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}
	
	public Integer getState() {
		return state;
	}
	
	public void setState(Integer state) {
		this.state = state;
	}
	
	public Integer getViewCount() {
		return viewCount;
	}
	
	public void setViewCount(Integer viewCount) {
		this.viewCount = viewCount;
	}
	
	public Integer getLikeCount() {
		return likeCount;
	}
	
	public void setLikeCount(Integer likeCount) {
		this.likeCount = likeCount;
	}
	
	public Integer getNoteCount() {
		return noteCount;
	}
	
	public void setNoteCount(Integer noteCount) {
		this.noteCount = noteCount;
	}

	public Integer getPoints() {
		return points;
	}

	public void setPoints(Integer points) {
		this.points = points;
	}


}
