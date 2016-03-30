package com.hzcwtech.wuzhong.model;

import java.sql.Timestamp;

import org.hibernate.validator.constraints.NotEmpty;

public class Course {
	
	private static final int COURSE_ENABLED = 1;
	
	private static final int COURSE_DISABLED = 0;
	
	private static final String[] GRADE_NAMES = {
			"000",
			"一年级",  "二年级", "三年级", "四年级", "五年级", "六年级",
			"初一", "初二", "初三",
			"高一", "高二", "高三"
			};
	

	private Integer id;
	
	private Integer categoryId = 1;
	
	//TODO
	private CourseCategory category;
	
	private Integer state = COURSE_ENABLED;
	
	private String version;
	
	private Integer grade = 4;
	
	@NotEmpty
	private String name;
	
	private String note;
	
	private Integer createUserId;
	
	private User createUser;
	
	private Timestamp createTime;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Integer categoryId) {
		this.categoryId = categoryId;
	}

	public CourseCategory getCategory() {
		return category;
	}

	public void setCategory(CourseCategory category) {
		this.category = category;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public Integer getGrade() {
		return grade;
	}

	public void setGrade(Integer grade) {
		this.grade = grade;
	}

	public String getGradeName() {
		if (grade < 0 || grade >= GRADE_NAMES.length) return "XXX";
		return GRADE_NAMES[grade];
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
	
	
}
