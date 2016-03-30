package com.hzcwtech.wuzhong.web.security;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;



public class CustomLoginUrlAuthenticationEntryPoint extends LoginUrlAuthenticationEntryPoint {
	
	private static final Logger logger = LoggerFactory.getLogger(CustomLoginUrlAuthenticationEntryPoint.class);

	public CustomLoginUrlAuthenticationEntryPoint(String loginFormUrl) {
        super(loginFormUrl);
    }

	@Override
	protected String determineUrlToUseForThisRequest(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException exception) {
		

		
		
		String urlLogin = super.determineUrlToUseForThisRequest(request, response, exception);
		
		//String ua = request.getHeader("User-Agent");
		
		
		
		
		return urlLogin;
	}
	
	
}
