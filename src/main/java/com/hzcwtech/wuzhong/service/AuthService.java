package com.hzcwtech.wuzhong.service;

import java.util.List;

import com.hzcwtech.wuzhong.model.ACLAuthority;
import com.hzcwtech.wuzhong.model.ACLRole;
import com.hzcwtech.wuzhong.model.mapper.AuthMapper;

public interface AuthService extends AuthMapper {

	public ACLAuthority getMatchAuthority(String url);
	
	public List<ACLRole> getMatchRoles(String url);
}
