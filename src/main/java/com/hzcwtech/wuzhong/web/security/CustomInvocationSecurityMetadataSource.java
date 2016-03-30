package com.hzcwtech.wuzhong.web.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;

import com.hzcwtech.wuzhong.model.ACLAuthority;
import com.hzcwtech.wuzhong.model.ACLRole;
import com.hzcwtech.wuzhong.service.AuthService;

public class CustomInvocationSecurityMetadataSource implements FilterInvocationSecurityMetadataSource {

	private static final Logger logger = LoggerFactory.getLogger(CustomInvocationSecurityMetadataSource.class);
	
	@Autowired
	private AuthService authService;
	
	@Override
	public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {
		String url = ((FilterInvocation) object).getRequestUrl();
		int firstIndex = url.indexOf('?');
		if (firstIndex != -1) {
			url = url.substring(0, firstIndex);
		}
		logger.debug("url : " + url);
		
		List<ConfigAttribute> result = new ArrayList<ConfigAttribute>();
		
		//ConfigAttribute attribute = new SecurityConfig("ROLE_USER");
		//result.add(attribute);
		
		try {
			List<ACLRole> roles = authService.getMatchRoles(url);
			if (roles != null && roles.size() > 0) {
				Iterator<ACLRole> it = roles.iterator();
				while (it.hasNext()) {
					ACLRole role = it.next();
					ConfigAttribute conf = new SecurityConfig(role.getCode());
					result.add(conf);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public Collection<ConfigAttribute> getAllConfigAttributes() {
		return null;
	}

	@Override
	public boolean supports(Class<?> clazz) {
		return true; 
	}

}
