package com.hzcwtech.wuzhong.model;

import java.sql.Timestamp;

public class WorkPhoto {
	
	private Integer workId;
	
	private Integer id;
	
	private String image;
	
	private Integer viewCount = 0;
	
	private Integer likeCount = 0;
	
	private Timestamp createTime;
	
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
	
	public String getImage() {
		return image;
	}
	
	public void setImage(String image) {
		this.image = image;
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
	
	public Timestamp getCreateTime() {
		return createTime;
	}
	
	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}
}
