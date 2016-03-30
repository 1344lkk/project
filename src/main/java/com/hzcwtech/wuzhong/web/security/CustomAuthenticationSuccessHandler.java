package com.hzcwtech.wuzhong.web.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;

import com.hzcwtech.wuzhong.service.AuthService;

public class CustomAuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {
	
	@Autowired
	private AuthService authService;
	
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws ServletException, IOException {
		Object principal = authentication.getPrincipal();
		
		if (principal != null && principal instanceof GrantedUser)  {
			GrantedUser user = (GrantedUser) principal;
			String ipAddress = request.getHeader("X-FORWARDED-FOR");  
		   if (ipAddress == null) {  
			   ipAddress = request.getRemoteAddr();  
		   }
			authService.onAuthenticationSuccess(user.getId(), ipAddress);
		}
		super.onAuthenticationSuccess(request, response, authentication);
	}

	protected void handle(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
		String targetUrl = determineTargetUrl(request, response, authentication);

		if (response.isCommitted()) {
			logger.debug("Response has already been committed. Unable to redirect to "
					+ targetUrl);
			return;
		}

		getRedirectStrategy().sendRedirect(request, response, targetUrl);
	}
	
	private String determineTargetUrl(HttpServletRequest request,
			HttpServletResponse response, Authentication authentication) {
		Object principal = authentication.getPrincipal();
		
		if (principal != null && principal instanceof GrantedUser)  {
			GrantedUser user = (GrantedUser) principal;
			return user.getLoginUrl();
		}
		
		return super.determineTargetUrl(request, response);
	}
}
