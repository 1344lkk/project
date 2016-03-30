package com.hzcwtech.wuzhong.web.security;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.hzcwtech.wuzhong.model.ACLRole;
import com.hzcwtech.wuzhong.model.User;
import com.hzcwtech.wuzhong.model.mapper.AuthMapper;
import com.hzcwtech.wuzhong.model.mapper.UserMapper;

public class CustomUserDetailsService implements UserDetailsService {

	private static final Logger logger = LoggerFactory.getLogger(CustomUserDetailsService.class);
	
	@Autowired
	private UserMapper userMapper;
	
	@Autowired 
	private AuthMapper authMapper;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userMapper.getUserByUsername(username);
		if (user == null) {
			logger.debug("Username not found: " + username);
			throw new UsernameNotFoundException("Username not found: " + username);
		}
		
		Set<GrantedAuthority> auths = new HashSet<GrantedAuthority>();
		
		List<ACLRole> roles = authMapper.getRolesByUserId(user.getId());
		
		Iterator<ACLRole> it = roles.iterator();
		while (it.hasNext()) {
			ACLRole r = it.next();
			auths.add(new GrantedRole(r.getCode(), r.getName()));
		}
		
		addCustomAuthorities(user.getUsername(), auths);
		
		if (auths.size() == 0) {
			logger.debug("User '" + username + "' has no authorities and will be treated as 'not found'");
			throw new UsernameNotFoundException("User '" + username + "' has no GrantedAuthority");
		}
		return createUserDetails(username, user, auths);
	}

	
	private void addCustomAuthorities(String username, Set<GrantedAuthority> authorities) {
		authorities.add(new GrantedRole("ROLE_USER", "ROLE_USER"));
	}
	
	protected UserDetails createUserDetails(String username, User user, Set<GrantedAuthority> combinedAuthorities) {
		GrantedUser details = new GrantedUser(user, combinedAuthorities);
	
		return details;
	}
}
