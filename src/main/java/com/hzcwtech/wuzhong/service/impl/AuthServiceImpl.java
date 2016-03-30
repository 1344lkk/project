package com.hzcwtech.wuzhong.service.impl;

import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;

import com.hzcwtech.mybatis.Pager;
import com.hzcwtech.wuzhong.model.ACLAuthority;
import com.hzcwtech.wuzhong.model.ACLResource;
import com.hzcwtech.wuzhong.model.ACLRole;
import com.hzcwtech.wuzhong.model.mapper.AuthMapper;
import com.hzcwtech.wuzhong.service.AuthService;

@Service
public class AuthServiceImpl implements AuthService {

	@Autowired
	private AuthMapper authMapper;

	private boolean urlMatch(String uriPattern, String uri) {   
        PathMatcher matcher = new AntPathMatcher();  
        return matcher.match(uriPattern, uri);  
    }
	
	@Override
	public ACLAuthority getMatchAuthority(String url) {
		List<ACLAuthority> auths = authMapper.getAuthorityList();
		Iterator<ACLAuthority> it = auths.iterator();
		while (it.hasNext()) {
			ACLAuthority auth = it.next();
			if (urlMatch(auth.getPattern(), url)) {
				return auth;
			}
		}
		return null;
	}
	
	@Override
	public List<ACLRole> getMatchRoles(String url) {
		ACLAuthority auth = getMatchAuthority(url);
		if (auth == null) return null;
		
		return authMapper.getRolesByAuthority(auth.getId());
	}
	
	@Override
	public List<ACLRole> getRolesByUserId(Integer userId) {
		return authMapper.getRolesByUserId(userId);
	}

	@Override
	public List<ACLAuthority> getAuthorityList() {
		return authMapper.getAuthorityList();
	}

	@Override
	public List<ACLRole> getRolesByAuthority(Integer authId) {
		return authMapper.getRolesByAuthority(authId);
	}

	@Override
	public List<ACLResource> getResourceList() {
		return authMapper.getResourceList();
	}

	@Override @Transactional
	public void insertResource(ACLResource resource) {
		authMapper.insertResource(resource);
	}

	@Override @Transactional
	public void updateResource(ACLResource resource) {
		authMapper.updateResource(resource);
	}

	@Override @Transactional
	public void deleteResource(int resourceId) {
		authMapper.deleteResource(resourceId);
	}

	@Override
	public List<ACLRole> getRoleList() {
		return authMapper.getRoleList();
	}

	@Override
	public ACLResource getResourceById(int resourceId) {
		return authMapper.getResourceById(resourceId);
	}

	@Override
	public List<ACLResource> searchResourceList(Pager pager, String q) {
		if (q != null && q.isEmpty()) q = null;
		if (q != null) q = "%" + q + "%";
		return authMapper.searchResourceList(pager, q);
	}

	@Override
	public List<ACLRole> searchRoleList(Integer enabled, String q) {
		if (q != null && q.isEmpty()) q = null;
		if (q != null) q = "%" + q + "%";
		return authMapper.searchRoleList(enabled, q);
	}

	@Override @Transactional
	public void insertRole(ACLRole role) {
		authMapper.insertRole(role);
	}

	@Override @Transactional
	public void updateRole(ACLRole role) {
		authMapper.updateRole(role);
	}

	@Override @Transactional
	public void deleteRole(ACLRole role) {
		authMapper.deleteRole(role);
	}

	@Override
	public ACLRole getRoleById(int roleId) {
		return authMapper.getRoleById(roleId);
	}

	@Override
	public void onAuthenticationSuccess(int userId, String ip) {
		authMapper.onAuthenticationSuccess(userId, ip);
	
	}  

	


}
