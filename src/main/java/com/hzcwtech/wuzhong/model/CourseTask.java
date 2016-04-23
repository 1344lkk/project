package com.hzcwtech.wuzhong.model;

import java.io.Serializable;
import java.sql.Timestamp;

public class CourseTask implements Serializable{

	private Integer id;
	
	private Integer courseId;
	
	private Course course;
	
	private Integer stageId;
	
	private CourseStage stage;
	
	private String name;
	
	private String note;
	
	private Integer sort;
	
	private Integer createUserId;
	
	private User createUser;
	
	private Timestamp createTime;

	private Integer  workId;

	private Integer workpoint;

	public void setWorkpoint(Integer workpoint) {
		this.workpoint = workpoint;
	}

	public Integer getWorkpoint() {
		return workpoint;
	}

	public void setWorkId(Integer workId) {
		this.workId = workId;
	}

	public Integer getWorkId() {
		return workId;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getCourseId() {
		return courseId;
	}

	public void setCourseId(Integer courseId) {
		this.courseId = courseId;
	}

	public Course getCourse() {
		return course;
	}

	public void setCourse(Course course) {
		this.course = course;
	}

	public Integer getStageId() {
		return stageId;
	}

	public void setStageId(Integer stageId) {
		this.stageId = stageId;
	}

	public CourseStage getStage() {
		return stage;
	}

	public void setStage(CourseStage stage) {
		this.stage = stage;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public Integer getCreateUserId() {
		return createUserId;
	}

	public void setCreateUserId(Integer createUserId) {
		this.createUserId = createUserId;
	}

	public User getCreateUser() {
		return createUser;
	}

	public void setCreateUser(User createUser) {
		this.createUser = createUser;
	}

	public Timestamp getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}
	
	
}
