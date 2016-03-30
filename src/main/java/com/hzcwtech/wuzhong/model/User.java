package com.hzcwtech.wuzhong.model;

import java.sql.Timestamp;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

public class User {

	public static final int ROLE_SUPER_ADMIN = 1;

	public static final int ROLE_ADMIN = 2;

	public static final int ROLE_TEACHER = 3;

	public static final int ROLE_STUDENT = 4;

	public static final int PASSWORD_DEFAULT = 0;

	public static final int PASSWORD_VALID = 1;

	private Integer id;

	private Integer role;

	private String roleName;
	
	private Boolean enabled;

	private Boolean deleted;

	private String password;

	private Integer passwordState;
	
	private String clearPassword;
	 

	@NotNull
	@NotEmpty
	private String username;

	@NotNull
	@NotEmpty
	private String truename;

	@NotNull
	@NotEmpty
	private String nickname;

	private String icon;

	private String status = "1";

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
   
	private Work work;
	
	private Integer sex;

	private Timestamp birthday;

	private Integer createUserId;

	private Timestamp createTime;
	
	private String sign;
	
	private String avatar;
	
	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	private Integer signinCount;

	private Timestamp signinTime;

	private String signinAddr;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getTruename() {
		return truename;
	}

	public void setTruename(String truename) {
		this.truename = truename;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public Integer getRole() {
		return role;
	}

	public void setRole(Integer role) {
		this.role = role;
	}

	public Boolean getEnabled() {
		return enabled;
	}

	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}

	public Boolean getDeleted() {
		return deleted;
	}

	public void setDeleted(Boolean deleted) {
		this.deleted = deleted;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Integer getPasswordState() {
		return passwordState;
	}

	public void setPasswordState(Integer passwordState) {
		this.passwordState = passwordState;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public Integer getSex() {
		return sex;
	}

	public void setSex(Integer sex) {
		this.sex = sex;
	}

	public Timestamp getBirthday() {
		return birthday;
	}

	public void setBirthday(Timestamp birthday) {
		this.birthday = birthday;
	}

	public Integer getCreateUserId() {
		return createUserId;
	}

	public void setCreateUserId(Integer createUserId) {
		this.createUserId = createUserId;
	}

	public Timestamp getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	public Integer getSigninCount() {
		return signinCount;
	}

	public void setSigninCount(Integer signinCount) {
		this.signinCount = signinCount;
	}

	public Timestamp getSigninTime() {
		return signinTime;
	}

	public void setSigninTime(Timestamp signinTime) {
		this.signinTime = signinTime;
	}

	public String getSigninAddr() {
		return signinAddr;
	}

	public void setSigninAddr(String signinAddr) {
		this.signinAddr = signinAddr;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public Work getWork() {
		return work;
	}

	public void setWork(Work work) {
		this.work = work;
	}

	public String getClearPassword() {
		return clearPassword;
	}

	public void setClearPassword(String clearPassword) {
		this.clearPassword = clearPassword;
	}

	
	

}
