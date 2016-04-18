package com.hzcwtech.wuzhong.web.console.controller;

import java.sql.Timestamp;
import java.util.ArrayList;
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
import org.springframework.web.bind.annotation.*;

import com.hzcwtech.mybatis.Pager;
import com.hzcwtech.wuzhong.model.Clazz;
import com.hzcwtech.wuzhong.model.Course;
import com.hzcwtech.wuzhong.model.CourseStage;
import com.hzcwtech.wuzhong.model.CourseTask;
import com.hzcwtech.wuzhong.model.Learning;
import com.hzcwtech.wuzhong.model.Lesson;
import com.hzcwtech.wuzhong.model.LessonPlan;
import com.hzcwtech.wuzhong.model.Note;
import com.hzcwtech.wuzhong.model.Question;
import com.hzcwtech.wuzhong.model.Student;
import com.hzcwtech.wuzhong.model.Work;
import com.hzcwtech.wuzhong.model.WorkPaper;
import com.hzcwtech.wuzhong.service.ClazzService;
import com.hzcwtech.wuzhong.service.CourseService;
import com.hzcwtech.wuzhong.service.LearningService;
import com.hzcwtech.wuzhong.service.LessonService;
import com.hzcwtech.wuzhong.service.QuestionService;
import com.hzcwtech.wuzhong.service.StudentService;
import com.hzcwtech.wuzhong.service.WorkService;
import com.hzcwtech.wuzhong.util.ErrorMessage;
import com.hzcwtech.wuzhong.web.security.GrantedUser;

@Controller("LessonController")
@RequestMapping("/console/lesson")
public class LessonController {
	
	private static final Logger logger = LoggerFactory.getLogger(LessonController.class);
	
	private static final String VIEW_LIST = "/console/lesson/list";
	private static final String VIEW_CREATE = "/console/lesson/create";
	private static final String VIEW_UPDATE = "/console/lesson/create";
	private static final String VIEW_PLAN_LIST = "/console/lesson/plan/list";
	private static final String VIEW_PLAN_CREATE = "/console/lesson/plan/create";
	private static final String VIEW_PLAN_UPDATE = "/console/lesson/plan/create";
	private static final String VIEW_PAPER_LIST = "/console/lesson/paper/index";
	@Autowired
	private LessonService lessonService;
	@Autowired
	private StudentService studentService;
	@Autowired
	private ClazzService clazzService;
	@Autowired
	private CourseService courseService;
	@Autowired
	private WorkService  workService;
	@Autowired
	private QuestionService  questionService;
	
	@InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(String.class, new StringTrimmerEditor(" \t\r\n\f", false));
    }
	
	
	@RequestMapping(value = "/list")
	public String index(Model model) {
	
		
		List<Lesson> lessons = lessonService.getLessonList();
		model.addAttribute("lessons",lessons);
	    return VIEW_LIST;
	}
	
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public String create(@ModelAttribute("lesson") Lesson lesson,Model model) {
		List<Clazz> clazz = clazzService.getClazzList();
		Pager pager = new Pager(1, 100); 
		List<Course> courses = courseService.searchCourseList(pager, 1, "");
		model.addAttribute("classes", clazz);
		model.addAttribute("courses", courses);
		return VIEW_CREATE;
	}
	
	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public String create(Model model, @Valid @ModelAttribute("lesson") Lesson lesson, Errors errors) {
		if (errors.hasErrors()) return VIEW_CREATE;
		
		try {
		
			 Date date=new Date();
			 Timestamp timer=new Timestamp(date.getTime());
			 lesson.setCreateTime(timer);
			 lesson.setCreateUserId(GrantedUser.getCurrent().getId());
			 lessonService.insertLesson(lesson);
		} catch (DuplicateKeyException e) {
			errors.rejectValue("name", "lesson.name.duplicate", "课程名称重复");
			return VIEW_CREATE;
		}
	    
		return "redirect:" + VIEW_LIST;
	}
	
	@RequestMapping(value = "/update/{id}", method = RequestMethod.GET)
	public String update(Model model, @PathVariable("id") Integer id) {
		Lesson lesson = lessonService.getLesson(id);
		List<Clazz> clazz = clazzService.updataGetClazzList(lesson.getClassId());
		Pager pager = new Pager(1, 100); 
		List<Course> courses = courseService.searchCourseList(pager, 1, "");
		model.addAttribute("classes", clazz);
		model.addAttribute("courses", courses);
		model.addAttribute("lesson", lesson);
		return VIEW_UPDATE;
	}
	
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public String update(Model model, @Valid @ModelAttribute("lesson") Lesson lesson,@RequestParam("cId") int cId, Errors errors) {
		if (errors.hasErrors()) return VIEW_CREATE;
		
		try {
			 Date date=new Date();
			 Timestamp timer=new Timestamp(date.getTime());
			 lesson.setCreateTime(timer);
			 lesson.setCreateUserId(GrantedUser.getCurrent().getId());
			 lessonService.updateLesson(lesson,cId==lesson.getClassId());
		} catch (DuplicateKeyException e) {
			errors.rejectValue("name", "lesson.name.duplicate", "课程名称重复");
			return VIEW_CREATE;
		}
	
		return "redirect:" + VIEW_LIST;
	}
	@RequestMapping(value = "/delete/{lessonId}", method = RequestMethod.GET)
	public String delete(Model model, @PathVariable("lessonId") Integer lessonId) {
		try {
			lessonService.deleteLesson(lessonId);
		} catch (Exception e) {
			
			logger.info(e.getMessage());
		}
		return "redirect:" + VIEW_LIST;
	}
	
	/*课程计划*/
	

	@RequestMapping(value = "/plan/list/{lessonId}")
	public String planndex(Model model,@PathVariable("lessonId") Integer lessonId) {
	    List<LessonPlan> lessonPlans = lessonService.getLessonPlanList(lessonId);
	    Boolean enable = true;
	    if( courseService.getCourseStageListByLessonId(lessonId).size() == 0){
	    	enable = false;
	    }
	    model.addAttribute("enable",enable);
		model.addAttribute("lessonPlans",lessonPlans);
		model.addAttribute("lessonId",lessonId);
	    return VIEW_PLAN_LIST;
	}
	
	@RequestMapping(value = "/plan/create/{lessonId}", method = RequestMethod.GET)
	public String planCreate(@ModelAttribute("lessonPlan") LessonPlan lessonPlan,Model model,@PathVariable("lessonId") Integer lessonId) {
	    model.addAttribute("stages",courseService.getCourseStageListByLessonId(lessonId));
	    model.addAttribute("lessonId", lessonId);
		return VIEW_PLAN_CREATE;
	}
	
	@RequestMapping(value = "/plan/create", method = RequestMethod.POST)
	public String create(Model model, @Valid @ModelAttribute("lessonPlan") LessonPlan lessonPlan, Errors errors) {
		Integer lessonId = lessonPlan.getLessonId();
		if (errors.hasErrors()) return VIEW_PLAN_CREATE+"/"+lessonId;
		try {
			 lessonService.insertLessonPlan(lessonPlan);
		} catch (Exception e) {
			return VIEW_PLAN_CREATE+"/"+lessonId;
		}
	    
		return "redirect:" + VIEW_PLAN_LIST+"/"+lessonId;
	}
	
	@RequestMapping(value = "/plan/update/{lessonId}/{stageId}", method = RequestMethod.GET)
	public String planUpdate(Model model, @PathVariable("lessonId") Integer lessonId, @PathVariable("stageId") Integer stageId) {
	    model.addAttribute("stages",courseService.getCourseStageList());
		model.addAttribute("lessonPlan", lessonService.getLessonPlan(lessonId, stageId));
		model.addAttribute("lessonId", lessonId);
		return VIEW_PLAN_UPDATE;
	}
	
	@RequestMapping(value = "/plan/update", method = RequestMethod.POST)
	public String planUpdate(Model model, @Valid @ModelAttribute("lessonPlan") LessonPlan lessonPlan, Errors errors) {
		if (errors.hasErrors()) return VIEW_PLAN_CREATE;
		Integer lessonId = lessonPlan.getLessonId();
		try {
			lessonService.updateLessonPlan(lessonPlan);
		} catch (Exception e) {
			return VIEW_CREATE;
		}
	
		return "redirect:" + VIEW_PLAN_LIST+"/"+lessonId;
	}
	@RequestMapping(value = "/plan/delete/{lessonId}/{stageId}", method = RequestMethod.GET)
	public String planDelete(Model model, @PathVariable("lessonId") Integer lessonId, @PathVariable("stageId") Integer stageId) {
		try {
			lessonService.deleteLessonPlan(lessonId, stageId);
		} catch (Exception e) {
			
			logger.info(e.getMessage());
		}
		return "redirect:" + VIEW_PLAN_LIST+"/"+lessonId;
	}
	
	/* 阅卷*/
	@RequestMapping(value = "/paper/{lessonId}")
	public String paperIndex(Model model,@PathVariable("lessonId") Integer lessonId) {
	    List<Student> students = studentService.getStudentByLessonId(lessonId);
	    int courseId = lessonService.getLesson(lessonId).getCourseId();
		List<CourseTask> tasks = courseService.getCourseTaskList(courseId);
		model.addAttribute("courseId",courseId);
		model.addAttribute("lessonId",lessonId);
		model.addAttribute("tasks",tasks);
		model.addAttribute("students",students);
		
	    return VIEW_PAPER_LIST;
	}
	
	@ResponseBody
	@RequestMapping(value = "/paper/grade/{studentId}/{taskId}/{score}")
	public ErrorMessage gradeWork(Model model,@PathVariable("studentId") Integer studentId,@PathVariable("score") int score,@PathVariable("taskId") int taskId,@RequestParam("remark") String remark) {
		ErrorMessage message = new ErrorMessage();
		try {
			Work work = workService.getWorkListByStudentIdAndTaskId(studentId,taskId);
			work.setRemark(remark);
			work.setPoints(score);
			workService.updateWork(work);
			message.setCode(1);
		} catch (Exception e) {
			message.setCode(-1);
		}

		return message;
	}
	@ResponseBody
	@RequestMapping(value = "/paper/getStudent/{lessonId}/{taskId}")
	public ErrorMessage getStudent(Model model,@PathVariable("lessonId") Integer lessonId,@PathVariable("taskId") int taskId) {
		ErrorMessage message = new ErrorMessage();
		try {
			List<Student> students = studentService.getStudentByTaskId(lessonId, taskId);
			message.setResult(students);
			message.setCode(1);
		} catch (Exception e) {
			message.setCode(-1);
		}

		return message;
	}
	
	@ResponseBody
	@RequestMapping(value = "/paper/paperScore/{studentId}/{taskId}")
	public ErrorMessage gradePaperScore(Model model,@PathVariable("studentId") Integer studentId,@PathVariable("taskId") Integer taskId) {
		ErrorMessage message = new ErrorMessage();
		try {
			Work work = workService.getWorkListByStudentIdAndTaskId(studentId,taskId);
			
			WorkPaper paper = workService.getWorkPaper(work.getId());
			message.setResult(paper.getPoints());
			message.setCode(1);
		} catch (Exception e) {
			message.setCode(-1);
		}

		return message;
	}
	@ResponseBody
	@RequestMapping(value = "/paper/getQuestion/{studentId}/{taskId}")
	public List<Question> getQuestions(Model model,@PathVariable("studentId") Integer studentId,@PathVariable("taskId") Integer taskId) {
		List<Question> QuestionWithOption = new ArrayList<Question>();;
		try {
			Work work = workService.getWorkListByStudentIdAndTaskId(studentId,taskId);
			WorkPaper paper = null;
			if(work != null){
			 paper= workService.getWorkPaper(work.getId());}
			List<Question> questions = new ArrayList<Question>();
			if(paper != null){
			questions =  questionService.getQuestionByPaperId(paper.getId());}
			if(questions != null){
   
			for(Question question:questions){
				
				question.setOptions( questionService.getQuestionOptionListByQuestionId(question.getId()));
				QuestionWithOption.add(question);
			}
			}
		} catch (Exception e) {
			 QuestionWithOption = null;
			e.printStackTrace();
		}
		
		return QuestionWithOption;
	}
}
