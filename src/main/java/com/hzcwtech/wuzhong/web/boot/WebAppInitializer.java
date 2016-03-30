package com.hzcwtech.wuzhong.web.boot;

import javax.servlet.FilterRegistration;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.filter.CharacterEncodingFilter;

public class WebAppInitializer implements WebApplicationInitializer {

	private static Logger logger;
	
	@Override
	public void onStartup(ServletContext servletContext) throws ServletException {
		initLogger(servletContext);
		registerCharacterEncodingFilter(servletContext);
	}

	private void initLogger(ServletContext servletContext) {
		logger = LoggerFactory.getLogger(WebAppInitializer.class);
		logger.debug("WebAppInitializer");
	}
	
	/**
	 * Filter all incoming requests with character encoding UTF-8
	 * @param servletContext 
	 */
	private void registerCharacterEncodingFilter(ServletContext servletContext) {
	    CharacterEncodingFilter encodingFilter = new CharacterEncodingFilter();
	    encodingFilter.setEncoding("UTF-8");
	    encodingFilter.setForceEncoding(true);
	    FilterRegistration.Dynamic characterEncodingFilter = servletContext.addFilter("CHARACTER_ENCODING_FILTER", encodingFilter);
	    characterEncodingFilter.addMappingForUrlPatterns(null, false, "/*");
	    logger.debug("CharacterEncodingFilter (UTF-8) init done");
	}
}
