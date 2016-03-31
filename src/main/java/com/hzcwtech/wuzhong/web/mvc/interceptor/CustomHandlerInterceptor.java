package com.hzcwtech.wuzhong.web.mvc.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.hzcwtech.wuzhong.web.security.GrantedUser;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class CustomHandlerInterceptor extends HandlerInterceptorAdapter {
	private static final Logger logger = LoggerFactory.getLogger(CustomHandlerInterceptor.class);
	
	/**
	 * TODO 
	 * 如果要使 Spring MVC 工程的权限系统可以跟Phalcon的权限系统完全匹配，则可以使用这个MVC层面的拦截器，
	 * 可以做到只判断controller和action，而不需要管 url 具体是什么。
	 * 具体可以使所有url在Spring Security中可访问，在Spring MVC里自定义控制。
	 * Spring Security只用来管理登录，注销，自动登录等。
	 */
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
		throws Exception {
		
		if (handler != null && handler instanceof HandlerMethod) {
			HandlerMethod hm = (HandlerMethod) handler;
			HandlerMethod rhm = hm.getResolvedFromHandlerMethod();
			Object bean = hm.getBean();
			String controller = null;
			
			if (rhm != null) {
				bean = rhm.getBean();
			}
			
			if (bean instanceof String) {
				controller = (String) bean;
			} else {
				controller = hm.getBean().getClass().getSimpleName();
			}
			
			String method = hm.getMethod().getName();
			
			logger.debug("preHandle : " + controller + "." + method);
		}
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		
		if (modelAndView != null) {
			InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("ipConfig.properties");
			Properties p = new Properties();
			try{
				p.load(inputStream);
				modelAndView.addObject("ip",p.getProperty("ip"));
			} catch (IOException e1){
				e1.printStackTrace();
			}

			GrantedUser user = GrantedUser.getCurrent();
			if (user != null) {
				modelAndView.addObject("grantedUser", user);

			}
		}
	}
	
	
}
