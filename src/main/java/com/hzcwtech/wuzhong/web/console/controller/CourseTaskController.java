package com.hzcwtech.wuzhong.web.console.controller;

import java.sql.Timestamp;
import java.util.Date;
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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hzcwtech.wuzhong.model.Course;
import com.hzcwtech.wuzhong.model.CourseTask;
import com.hzcwtech.wuzhong.service.CourseService;
import com.hzcwtech.wuzhong.web.security.GrantedUser;

@Controller("CourseTaskController")
@RequestMapping("/console/task")
public class CourseTaskController {
	
	private static final Logger logger = LoggerFactory.getLogger(CourseTaskController.class);
	
	private static final String VIEW_LIST = "/console/task/list";
	private static final String VIEW_CREATE = "/console/task/create";
	private static final String VIEW_UPDATE = "/console/task/create";
	private static final String VIEW__INDEX = "/console/task/index";
	
	@Autowired
	private CourseService courseService;
	int courseId=0;
	@InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(String.class, new StringTrimmerEditor(" \t\r\n\f", false));
    }
	
	
	@RequestMapping(value = "/list")
	public String index(Model model, @RequestParam("courseId") Integer courseId) {
	
		
		List<CourseTask> tasks = courseService.getCourseTaskList(courseId);
		this.courseId = courseId;
		model.addAttribute("courseId",courseId);
		model.addAttribute("tasks",tasks);
		return VIEW_LIST;
	}
	
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public String create(@ModelAttribute("courseTask") CourseTask coursetask,Model model,@RequestParam("courseId") Integer courseId) {
		model.addAttribute("courseStages",courseService.getCourseStageList());
		model.addAttribute("courseId",courseId);
		return VIEW_CREATE;
	}
	
	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public String create(Model model, @Valid @ModelAttribute("courseTask") CourseTask courseTask,@RequestParam("courseId") Integer courseId, Errors errors) {
		if (errors.hasErrors()) return VIEW_CREATE;
		
		try {
		
			 Date date=new Date();
			 Timestamp timer=new Timestamp(date.getTime());
			 courseTask.setCreateTime(timer);
			 courseTask.setSort(1);
			 courseTask.setCreateUserId(GrantedUser.getCurrent().getId());
			courseService.insertCourseTask(courseTask);
		} catch (DuplicateKeyException e) {
			errors.rejectValue("name", "courseTask.name.duplicate", "任务名称重复");
			return VIEW_CREATE;
		}
	    
		return "redirect:" + VIEW_LIST+"?courseId="+courseId;
	}
	
	@RequestMapping(value = "/update/{id}", method = RequestMethod.GET)
	public String update(Model model, @PathVariable("id") Integer id) {
		CourseTask courseTask = courseService.getCourseTask(id);
		model.addAttribute("courseStages",courseService.getCourseStageList());
		model.addAttribute("courseId",courseId);
		model.addAttribute("courseTask", courseTask);
		return VIEW_UPDATE;
	}
	
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public String update(Model model, @Valid @ModelAttribute("courseTask") CourseTask courseTask, Errors errors) {
		if (errors.hasErrors()) return VIEW_CREATE;
		
		try {
			courseService.updateCourseTask(courseTask);
		} catch (DuplicateKeyException e) {
			errors.rejectValue("name", "courseTask.name.duplicate", "任务名称重复");
			return VIEW_CREATE+"?courseId="+courseId;
		}
	
		return "redirect:" + VIEW_LIST+"?courseId="+courseId;
	}
	@RequestMapping(value = "/delete/{taskId}", method = RequestMethod.GET)
	public String delete(Model model, @PathVariable("taskId") Integer taskId) {
		try {
			courseService.deleteCourseTask(taskId);
		} catch (Exception e) {
			
			logger.info(e.getMessage());
		}
		return "redirect:" + VIEW_LIST+"?courseId="+courseId;
	}
	/*-----angularjs---------*/
	
	@RequestMapping(value = "/index")
	public String newIndex(Model model, @RequestParam("courseId") Integer courseId) {
	
		List<CourseTask> tasks = courseService.getCourseTaskList(courseId);
		
		model.addAttribute("courseId",courseId);
		model.addAttribute("tasks",tasks);
		return VIEW__INDEX;
	}
	
	@ResponseBody
	@RequestMapping(value = "/list/{stageId}/{courseId}", method = RequestMethod.GET)
	public List<CourseTask> getTasks(Model model, @PathVariable("stageId") Integer stageId,@PathVariable("courseId") Integer courseId) {
		 List<CourseTask> tasks = courseService.getCourseTaskByStageId(courseId, stageId);
		 return tasks;
	}
	
	@ResponseBody
	@RequestMapping(value = "/deleteTask/{taskId}", method = RequestMethod.GET)
	public String deleteTask(Model model, @PathVariable("taskId") Integer taskId) {
		try {
			courseService.deleteCourseTask(taskId);
		} catch (Exception e) {
			
			logger.info(e.getMessage());
		}
		return "";
	}
	
	@ResponseBody
	@RequestMapping(value = "/addTask", method = RequestMethod.POST,headers = {
			"content-type=application/json", "content-type=application/xml" })
	public void addTask(Model model,  @RequestBody CourseTask courseTask ) {
		
		
		try {
		
			 Date date=new Date();
			 Timestamp timer=new Timestamp(date.getTime());
			 courseTask.setCreateTime(timer);
			 courseTask.setSort(1);
			 courseTask.setCreateUserId(GrantedUser.getCurrent().getId());
			courseService.insertCourseTask(courseTask);
		} catch (DuplicateKeyException e) {
			
			
		}
	  
	}
	
	@ResponseBody
	@RequestMapping(value = "/getTask/{id}", method = RequestMethod.GET)
	public CourseTask getTaskById(Model model, @PathVariable("id") Integer id) {
		 CourseTask task = courseService.getCourseTask(id);
		 return task;
	}
	
	
	@ResponseBody
	@RequestMapping(value = "/editTask", method = RequestMethod.POST,headers = {
			"content-type=application/json", "content-type=application/xml" })
	public void editTask(Model model,  @RequestBody CourseTask courseTask ) {
		
		
		try {
	
			courseService.updateCourseTask(courseTask);
		} catch (DuplicateKeyException e) {
			
			
		}
	  
	}
	
}
