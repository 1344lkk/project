package com.hzcwtech.wuzhong.web.home.controller;

import java.text.DateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import com.hzcwtech.wuzhong.model.ExcellentWork;
import com.hzcwtech.wuzhong.model.Lesson;
import com.hzcwtech.wuzhong.model.User;
import com.hzcwtech.wuzhong.service.LessonService;
import com.hzcwtech.wuzhong.service.UserService;
import com.hzcwtech.wuzhong.service.WorkService;
import com.hzcwtech.wuzhong.web.security.GrantedUser;

@Controller("HomeIndexController")
public class IndexController {
	
	private static final Logger logger = LoggerFactory.getLogger(IndexController.class);
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private LessonService lessonService;
	
	@Autowired 
	private WorkService workService;
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String index(Locale locale, Model model) {
		Date date = new Date();
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);
		String serverTime = dateFormat.format(date);
		
		logger.info("HOME : locale = {} serverTime = {}.", locale, serverTime);
		
		List<Lesson> lesson = lessonService.getIndexLessonList();
		List<ExcellentWork> recentWork = workService.getIndexRecentWorkList();
		List<ExcellentWork> excellentWork = workService.getIndexExcellentWorkList(null);
		model.addAttribute("indexLesson", lesson);
		model.addAttribute("recentWork", recentWork);
		model.addAttribute("excellentWork", excellentWork);
		model.addAttribute("serverTime", serverTime );
	    model.addAttribute("currentUser", GrantedUser.getCurrent());
		return "home/index/index";
	}
	
	
	@RequestMapping(value = "/index/test", method = RequestMethod.GET)
	public String test(Locale locale, Model model) {
		Date date = new Date();
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);
		
		String serverTime = dateFormat.format(date);
	
		List<User> users = userService.getUserList();
		
		model.addAttribute("users", users);
		model.addAttribute("serverTime", serverTime );

		return "home/index/test";
	}

}
