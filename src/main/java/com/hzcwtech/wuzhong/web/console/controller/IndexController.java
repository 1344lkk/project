package com.hzcwtech.wuzhong.web.console.controller;

import java.util.Locale;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.hzcwtech.wuzhong.web.security.GrantedUser;

@Controller("ConsoleIndexController")
@RequestMapping("/console")
public class IndexController {
	
	private static final Logger logger = LoggerFactory.getLogger(IndexController.class);
	
	@RequestMapping(value = "")
	public String index(Locale locale, Model model,HttpSession session) {
		
		return "console/index/index";
	}
	
}
