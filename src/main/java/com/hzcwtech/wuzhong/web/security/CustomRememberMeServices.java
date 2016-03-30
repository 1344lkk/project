package com.hzcwtech.wuzhong.web.security;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.rememberme.PersistentTokenBasedRememberMeServices;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

public class CustomRememberMeServices extends PersistentTokenBasedRememberMeServices {
	
	public CustomRememberMeServices(String key, UserDetailsService userDetailsService,
            PersistentTokenRepository tokenRepository) {
		super(key, userDetailsService, tokenRepository);
	}

	@Override
	protected boolean rememberMeRequested(HttpServletRequest request, String parameter) {
		String vv = (String) request.getAttribute("com.hzcwtech.REMEMBER");
		if ("yes".equals(vv)) 
			return true;
		
		return super.rememberMeRequested(request, parameter);
	}
	
	
	
}
