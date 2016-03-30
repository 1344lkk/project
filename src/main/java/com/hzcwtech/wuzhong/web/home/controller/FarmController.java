package com.hzcwtech.wuzhong.web.home.controller;

import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hzcwtech.wuzhong.model.Course;
import com.hzcwtech.wuzhong.model.CourseTask;
import com.hzcwtech.wuzhong.model.ExcellentWork;
import com.hzcwtech.wuzhong.model.Lesson;
import com.hzcwtech.wuzhong.model.Report;
import com.hzcwtech.wuzhong.model.ReportsDatas;
import com.hzcwtech.wuzhong.service.AuthService;
import com.hzcwtech.wuzhong.service.CourseService;
import com.hzcwtech.wuzhong.service.LessonService;
import com.hzcwtech.wuzhong.service.ReportService;
import com.hzcwtech.wuzhong.service.WorkService;

@Controller("HomeFarmController")
@RequestMapping("/farm")
public class FarmController {
	
	private static final Logger logger = LoggerFactory.getLogger(FarmController.class);
	@Autowired
	private LessonService lessonService;
	@Autowired 
	private WorkService workService;
	@Autowired 
	private CourseService courseService;
	@Autowired
	private ReportService  report;
	@ResponseBody
	@RequestMapping(value = "/data/{functionIdA}/{functionIdB}", method = RequestMethod.GET)
	public ReportsDatas index(Locale locale, Model model,@PathVariable("functionIdA")int functionIdA,@PathVariable("functionIdB")int functionIdB,HttpServletResponse res) {
	    res.setHeader("Access-Control-Allow-Origin", "*");
		ReportsDatas reprotDatas =  report.getReportsList(functionIdA,functionIdB);
		return reprotDatas;
		
	}

	@RequestMapping(value = "/about")
	public String about(Locale locale, Model model) {
	
		return "home/farm/index";
	}
	
	
	@RequestMapping(value = "/live")
	public String live(Locale locale, Model model) {
		
		return "home/farm/live";
	}

	@RequestMapping(value = "/events")
	public String events(Locale locale, Model model) {
		
		return "home/farm/events";
	}
	@ResponseBody
	@RequestMapping(value = "/events/list")
	public List<Lesson> eventList(Locale locale, Model model) {
		
		List<Lesson> lesson = lessonService.getIndexLessonList();
		return lesson;
		
	}
	@ResponseBody
	@RequestMapping(value = "/events/excellentWork/{classId}")
	public List<ExcellentWork>  excellentWork(Locale locale, Model model,@PathVariable int classId) {
		
		List<ExcellentWork> excellentWork = workService.getIndexExcellentWorkList(classId);
		return excellentWork;
		
	}
	@ResponseBody
	@RequestMapping(value = "/events/tasks/{classId}")
	public List<CourseTask>  tasks(Locale locale, Model model,@PathVariable int classId) {
		
		List<CourseTask> tasks = courseService.getCourseTaskByClassId(classId);
		
		return tasks;
		
	}
	
	
	@RequestMapping(value = "/arts")
	public String arts(Locale locale, Model model) {
		
		return "home/farm/arts";
	}
}
