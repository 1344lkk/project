package com.hzcwtech.wuzhong.web.console.controller;

import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.hzcwtech.mybatis.Pager;
import com.hzcwtech.wuzhong.model.Course;
import com.hzcwtech.wuzhong.service.CourseService;

@Controller("CourseController")
@RequestMapping("/console/course")
public class CourseController {
	
	private static final Logger logger = LoggerFactory.getLogger(CourseController.class);
	
	private static final String VIEW_LIST = "/console/course/list";
	private static final String VIEW_CREATE = "/console/course/create";
	private static final String VIEW_UPDATE = "/console/course/create";
	
	@Autowired
	private CourseService courseService;
	
	@InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(String.class, new StringTrimmerEditor(" \t\r\n\f", false));
    }
	
	
	@RequestMapping(value = "/list")
	public String index(@RequestParam(value="p", defaultValue="1") String p,
			@RequestParam(value="q", required=false) String q,
			@RequestParam(value="state", required=false) Integer state,
			Model model) {
	
		Pager pager = new Pager(Integer.valueOf(p), 20);
		List<Course> courses = courseService.searchCourseList(pager, state, q);
		
		model.addAttribute("courses", courses);
		model.addAttribute("state", state);
		model.addAttribute("pager", pager);
		model.addAttribute("q", q);
		
		return VIEW_LIST;
	}
	
	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public String index() {
		return "/console/course/index";
	} 
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public String create(@ModelAttribute("course") Course course) {
		return VIEW_CREATE;
	}
	
	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public String create(Model model, @Valid @ModelAttribute("course") Course course, Errors errors) {
		if (errors.hasErrors()) return VIEW_CREATE;
		
		try {
			courseService.insertCourse(course);
		} catch (DuplicateKeyException e) {
			errors.rejectValue("name", "course.name.duplicate", "课程名称重复");
			return VIEW_CREATE;
		}
	
		return "redirect:" + VIEW_LIST;
	}
	
	@RequestMapping(value = "/update/{id}", method = RequestMethod.GET)
	public String update(Model model, @PathVariable("id") Integer id) {
		Course course = courseService.getCourseById(id);
		model.addAttribute("course", course);
		return VIEW_UPDATE;
	}
	
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public String update(Model model, @Valid @ModelAttribute("course") Course course, Errors errors) {
		if (errors.hasErrors()) return VIEW_CREATE;
		
		try {
			courseService.updateCourse(course);
		} catch (DuplicateKeyException e) {
			errors.rejectValue("name", "course.name.duplicate", "课程名称重复");
			return VIEW_CREATE;
		}
	
		return "redirect:" + VIEW_LIST;
	}
	@RequestMapping(value = "/state/{id}/{state}", method = RequestMethod.GET)
	public String updateState(Model model,@PathVariable("id") Integer id,@PathVariable("state") Integer state) {
		
		   
		try {
			Course course = courseService.getCourseById(id);
			
			course.setState(state);
			courseService.updateCourse(course);
		} catch (DuplicateKeyException e) {
			
			return VIEW_CREATE;
		}
	
		return "redirect:" + VIEW_LIST;
	}
	@RequestMapping(value = "/delete/{courseId}", method = RequestMethod.GET)
	public String delete(Model model, @PathVariable("courseId") Integer courseId) {
		try {
			courseService.deleteCourse(courseId);
		} catch (Exception e) {
			
			logger.info(e.getMessage());
		}
		return "redirect:" + VIEW_LIST;
	}
	@RequestMapping(value = "/search" ,method = RequestMethod.GET)
	public String search(@RequestParam(value="grade" ,required=false ) int grade,
						 @RequestParam(value="q", required=false) String q,
						 @RequestParam(value="state", required=false) Integer state,
						 Model model) {

		Pager pager = new Pager(Integer.valueOf(grade), 20);
		List<Course> courses = courseService.searchCourseListBySort(grade, state, q);

		model.addAttribute("courses", courses);
		model.addAttribute("state", state);
		model.addAttribute("pager", pager);
		model.addAttribute("grade",grade);
		model.addAttribute("q", q);
		return VIEW_LIST;

	}
}
