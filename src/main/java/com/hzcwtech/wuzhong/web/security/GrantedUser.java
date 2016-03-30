package com.hzcwtech.wuzhong.web.security;

import java.util.Collection;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.SpringSecurityCoreVersion;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;

public class GrantedUser extends User {
	private static final long serialVersionUID = SpringSecurityCoreVersion.SERIAL_VERSION_UID;

	private int id;
	
	private int role;
	
	private String truename;
	
	private String nickname;
	
	private String icon;
	
	public GrantedUser(com.hzcwtech.wuzhong.model.User user, Collection<? extends GrantedAuthority> authorities) {
		super(user.getUsername(), user.getPassword(), user.getEnabled(), true, true, true, authorities);
		this.id = user.getId();
		this.role = user.getRole();
		this.truename = user.getTruename();
		this.nickname = user.getNickname();
	}

	public int getId() {
		return id;
	}

	public int getRole() {
		return role;
	}

	public String getTruename() {
		return truename;
	}

	public String getNickname() {
		return nickname;
	}

	public String getIcon() {
		return icon;
	}

	public String getLoginUrl() {
		String targetUrl = "/";
		if (role == 4) {
			targetUrl = "/student/" + id;
		} else if (role == 1 || role == 2||role == 3) {
			targetUrl = "/console";
		}
		return targetUrl;
	}

	public static final GrantedUser getCurrent() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		
		if (auth != null) {
			Object principal = auth.getPrincipal();
			if (principal instanceof GrantedUser) {
				return (GrantedUser) auth.getPrincipal();
			}
		}
		return null;
	}
	
	

}
