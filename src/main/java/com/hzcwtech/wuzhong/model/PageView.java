package com.hzcwtech.wuzhong.model;

import java.sql.Timestamp;

public class PageView {
	
	private Integer masterId;
	
	private Integer visitorId;
	
	private  String  viewUser;
	
	private Timestamp  viewTime;
	
	private Integer viewCount;
	
	// 当前访问人的角色
	private Integer role;

	public Integer getMasterId() {
		return masterId;
	}

	public void setMasterId(Integer masterId) {
		this.masterId = masterId;
	}

	public Integer getVisitorId() {
		return visitorId;
	}

	public void setVisitorId(Integer visitorId) {
		this.visitorId = visitorId;
	}

	public Timestamp getViewTime() {
		return viewTime;
	}

	public void setViewTime(Timestamp viewTime) {
		this.viewTime = viewTime;
	}

	public Integer getViewCount() {
		return viewCount;
	}

	public void setViewCount(Integer viewCount) {
		this.viewCount = viewCount;
	}

	public String getViewUser() {
		return viewUser;
	}

	public void setViewUser(String viewUser) {
		this.viewUser = viewUser;
	}

	public Integer getRole() {
		return role;
	}

	public void setRole(Integer role) {
		this.role = role;
	}

	
   
	
	
	

}
