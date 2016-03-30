package com.hzcwtech.wuzhong.web.security;

import java.util.Collection;
import java.util.Iterator;

import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

public class CustomAccessDecisionManager implements AccessDecisionManager {

	@Override
	public void decide(Authentication authentication, Object object,
			Collection<ConfigAttribute> configAttributes)
			throws AccessDeniedException, InsufficientAuthenticationException {
		if (configAttributes == null) {  
            return;  
        }  
  
        Iterator<ConfigAttribute> it = configAttributes.iterator();  
  
        while (it.hasNext()) {  
            ConfigAttribute ca = it.next();  
            String needRole = ((SecurityConfig) ca).getAttribute();  
            for (GrantedAuthority ga : authentication.getAuthorities()) {  
                if (needRole.trim().equals(ga.getAuthority().trim())) {  
                    return;  
                }  
            }  
        }  
        throw new AccessDeniedException("无权限！");  
	}

	@Override
	public boolean supports(ConfigAttribute attribute) {
		return true; 
	}

	@Override
	public boolean supports(Class<?> clazz) {
		return true; 
	}

}
