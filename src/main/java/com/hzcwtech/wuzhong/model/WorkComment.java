package com.hzcwtech.wuzhong.model;

import java.sql.Timestamp;

public class WorkComment {

	private Integer workId;
	
	private Integer id;
	
	private String content;
	
	private Timestamp createTime;
	
	private Integer createUserId;
		
	private String 	createUserName;
	
	private String 	replyUserName;
	
	private Integer role;
	
	private String createUserImage;
	
	private String  replyUserImage;
	
	public Object getReplyUserImage() {
		return replyUserImage;
	}

	public void setReplyUserImage(String replyUserImage) {
		this.replyUserImage = replyUserImage;
	}

	public Object getCreateUserImage() {
		return createUserImage;
	}

	public void setCreateUserImage(String createUserImage) {
		this.createUserImage = createUserImage;
	}

	public Integer getRole() {
		return role;
	}

	public void setRole(Integer role) {
		this.role = role;
	}

	public String getCreateUserName() {
		return createUserName;
	}

	public void setCreateUserName(String createUserName) {
		this.createUserName = createUserName;
	}

	public String getReplyUserName() {
		return replyUserName;
	}

	public void setReplyUserName(String replyUserName) {
		this.replyUserName = replyUserName;
	}

	private Integer replyUserId;
	
	private Integer replayId;

	public Integer getWorkId() {
		return workId;
	}

	public void setWorkId(Integer workId) {
		this.workId = workId;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Timestamp getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	public Integer getCreateUserId() {
		return createUserId;
	}

	public void setCreateUserId(Integer createUserId) {
		this.createUserId = createUserId;
	}

	public Integer getReplyUserId() {
		return replyUserId;
	}

	public void setReplyUserId(Integer replyUserId) {
		this.replyUserId = replyUserId;
	}

	public Integer getReplayId() {
		return replayId;
	}

	public void setReplayId(Integer replayId) {
		this.replayId = replayId;
	}
}
