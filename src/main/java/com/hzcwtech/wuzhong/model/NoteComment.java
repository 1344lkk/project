package com.hzcwtech.wuzhong.model;

import java.sql.Timestamp;

public class NoteComment {

	private  Integer id;
	
	private Integer noteId;
	
	private String content;
	
	private Timestamp createTime;
	
	private Integer createUserId;
	
	private Integer replyId;
	
	private Integer replyUserId;
	
	private String replyUserName;
	
	private String createName;
	
	private int userRole;
	
	private String commentAvatar;
	
	private int createRole;
	
	private int replyRole;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getNoteId() {
		return noteId;
	}

	public void setNoteId(Integer noteId) {
		this.noteId = noteId;
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

	public Integer getReplyId() {
		return replyId;
	}

	public void setReplyId(Integer replyId) {
		this.replyId = replyId;
	}

	public Integer getReplyUserId() {
		return replyUserId;
	}

	public void setReplyUserId(Integer replyUserId) {
		this.replyUserId = replyUserId;
	}

	public String getReplyUserName() {
		return replyUserName;
	}

	public void setReplyUserName(String replyUserName) {
		this.replyUserName = replyUserName;
	}

	
	public String getCreateName() {
		return createName;
	}

	public void setCreateName(String createName) {
		this.createName = createName;
	}

	public int getUserRole() {
		return userRole;
	}

	public void setUserRole(int userRole) {
		this.userRole = userRole;
	}

	public String getCommentAvatar() {
		return commentAvatar;
	}

	public void setCommentAvatar(String commentAvatar) {
		this.commentAvatar = commentAvatar;
	}

	public int getCreateRole() {
		return createRole;
	}

	public void setCreateRole(int createRole) {
		this.createRole = createRole;
	}

	public int getReplyRole() {
		return replyRole;
	}

	public void setReplyRole(int replyRole) {
		this.replyRole = replyRole;
	}
	
	
	
}
