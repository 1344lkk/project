package com.hzcwtech.wuzhong.model;

import java.sql.Timestamp;
import java.util.List;

public class Note {
	
	private Integer  id;
	
	private Integer userId;
	
	private String replayUserName;
	
	private String content;
	
	private String  photos;
	
	private Integer viewCount = 0;
	
	private List<NoteComment> noteComments;
	
	private Integer likeCount = 0;
	
	private Integer commentCount = 0;
	
	private Timestamp createTime;
	
	//记录说说图片的地址
	private List<NoteImage> noteImage;
	
	//记录哪一个用户点赞
	private String likeUserId;
	
	private boolean praiseOrNot;
	
	//记录置顶
	private int top;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getPhotos() {
		return photos;
	}

	public void setPhotos(String photos) {
		this.photos = photos;
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

	public Integer getCommentCount() {
		return commentCount;
	}

	public void setCommentCount(Integer commentCount) {
		this.commentCount = commentCount;
	}

	public Timestamp getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	public List<NoteComment> getNoteComments() {
		return noteComments;
	}

	public void setNoteComments(List<NoteComment> noteComments) {
		this.noteComments = noteComments;
	}

	public String getReplayUserName() {
		return replayUserName;
	}

	public void setReplayUserName(String replayUserName) {
		this.replayUserName = replayUserName;
	}

	public String getLikeUserId() {
		return likeUserId;
	}

	public void setLikeUserId(String likeUserId) {
		this.likeUserId = likeUserId;
	}

	public int getTop() {
		return top;
	}

	public void setTop(int top) {
		this.top = top;
	}

	public List<NoteImage> getNoteImage() {
		return noteImage;
	}

	public void setNoteImage(List<NoteImage> noteImage) {
		this.noteImage = noteImage;
	}

	public boolean isPraiseOrNot() {
		return praiseOrNot;
	}

	public void setPraiseOrNot(boolean praiseOrNot) {
		this.praiseOrNot = praiseOrNot;
	}

	
	
	
}
