package com.hzcwtech.wuzhong.web.console.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

import com.hzcwtech.mybatis.Pager;
import com.hzcwtech.wuzhong.model.Question;
import com.hzcwtech.wuzhong.model.QuestionOption;
import com.hzcwtech.wuzhong.service.QuestionService;


@Controller("QuestionController")
@RequestMapping("/console/question")
public class QuestionController {
	
	private static final Logger logger = LoggerFactory.getLogger(QuestionController.class);
	
	private static final String VIEW_QUESTION_LIST = "/console/question/list";
	private static final String VIEW_QUESTION_CREATE = "/console/question/create";
	private static final String VIEW_QUESTION_UPDATE = "/console/question/create";
	private static final String VIEW_OPTION_LIST = "/console/question/option/list";
	private static final String VIEW_OPTION_CREATE = "/console/question/option/create";
	private static final String VIEW_OPTION_UPDATE = "/console/question/option/create";
	private Integer taskId ;
	@Autowired
	private QuestionService questionService;
	
	@InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(String.class, new StringTrimmerEditor(" \t\r\n\f", true));
    }
	
	@RequestMapping(value = "/list")
	public String index(
			@RequestParam(value="p", defaultValue="1") String p,
			@RequestParam(value="q", required=false) String q,
			@RequestParam(value="taskId",required=false) Integer taskId,
			Model model) {
		Pager pager = new Pager(Integer.valueOf(p), 20); 
		if(taskId != null)
		{
		this.taskId = taskId;
		}
		List<Question> questions = questionService.getQuestionListByTaskId(pager, q, this.taskId);
		
		model.addAttribute("questions", questions);
		model.addAttribute("pager", pager);
		model.addAttribute("q", q);
		return VIEW_QUESTION_LIST;
	}
	
	
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public String create(@ModelAttribute("question") Question question) {
		return VIEW_QUESTION_CREATE;
	}
	
	
	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public String create(Model model, @Valid @ModelAttribute("question") Question question, Errors errors) {
		if (errors.hasErrors()) {
			return VIEW_QUESTION_CREATE;
		}
		
		try {
			question.setSort(1);
			question.setTaskId(this.taskId);
			questionService.insertQuestion(question);
		} catch (DuplicateKeyException e) {
			logger.warn("问题主键冲突 " + question.getId() );
			errors.rejectValue("code", "acl.question.code.duplicate", "问题主键冲突");
			return VIEW_QUESTION_CREATE;
		}
	
		return "redirect:" + VIEW_QUESTION_LIST+"?taskId="+taskId;
	}
	
	@RequestMapping(value = "/update/{questionId}", method = RequestMethod.GET)
	public String update(Model model, @PathVariable("questionId") Integer questionId) {
		Question question = questionService.getQuestion(questionId);
		model.addAttribute("question", question);
		return VIEW_QUESTION_UPDATE;
	}
	
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public String update(Model model, @Valid @ModelAttribute("question") Question question, Errors errors) {
		if (errors.hasErrors()) {
			return VIEW_QUESTION_UPDATE;
		}
		
		try {
			question.setSort(1);
			questionService.updateQuestion(question);
		} catch (Exception e) {
			return VIEW_QUESTION_UPDATE;
		}
		return "redirect:" + VIEW_QUESTION_LIST+"?taskId="+taskId;
	}
 
	@RequestMapping(value = "/delete/{questionId}", method = RequestMethod.GET)
	public String delete(Model model, @PathVariable("questionId") Integer questionId) {
		try {
			questionService.deleteQuestion(questionId);
		} catch (Exception e) {
			
			logger.info(e.getMessage());
		}
		return "redirect:" + VIEW_QUESTION_LIST+"?taskId="+taskId;
	}
	
	@RequestMapping(value = "/option/list")
	public String optionIndex(
			@RequestParam(value="p", defaultValue="1") String p,
			@RequestParam(value="q", required=false) String q,
			@RequestParam(value="questionId",required=false) Integer questionId,
			Model model) {
		Pager pager = new Pager(Integer.valueOf(p), 20); 
		
		List<QuestionOption> options = questionService.getQuestionOptionListByQuestionId(questionId);
		model.addAttribute("questionId",questionId);
		model.addAttribute("options", options);
		model.addAttribute("pager", pager);
		model.addAttribute("q", q);
		return VIEW_OPTION_LIST;
	}
	
	
	@RequestMapping(value = "/option/create/{questionId}", method = RequestMethod.GET)
	public String optionCreate(Model model,@ModelAttribute("questionOption") QuestionOption option,@PathVariable("questionId") Integer questionId) {
		model.addAttribute("questionId",questionId);
		return VIEW_OPTION_CREATE;
	}
	
	
	@RequestMapping(value = "/option/create/{questionId}", method = RequestMethod.POST)
	public String optionCreate(Model model, @Valid @ModelAttribute("questionOption") QuestionOption questionOption,@PathVariable("questionId") Integer questionId, Errors errors) {
		model.addAttribute("questionId",questionId);
		if (errors.hasErrors()) {
			return VIEW_OPTION_CREATE;
		}
		
		try {
			
			questionOption.setQuestionId(questionId);
			questionService.insertQuestionOption(questionOption);
		} catch (DuplicateKeyException e) {
			logger.warn("问题主键冲突 " + questionOption.getId() );
			errors.rejectValue("code", "acl.question.code.duplicate", "问题主键冲突");
			return VIEW_OPTION_CREATE;
		}
	
		return "redirect:" + VIEW_OPTION_LIST+"?questionId="+questionId;
	}
	
	@RequestMapping(value = "/option/update/{questionId}/{id}", method = RequestMethod.GET)
	public String optionUpdate(Model model, @PathVariable("id") Integer id, @PathVariable("questionId") Integer questionId) {
		QuestionOption option = questionService.getQuestionOption(id);
		model.addAttribute("questionId", questionId);
		model.addAttribute("questionOption", option);
		return VIEW_OPTION_UPDATE;
	}
	
	@RequestMapping(value = "/option/update/{questionId}", method = RequestMethod.POST)
	public String optionUpdate(Model model, @Valid @ModelAttribute("questionOption") QuestionOption questionOption,@PathVariable("questionId") Integer questionId, Errors errors) {
		if (errors.hasErrors()) {
			return VIEW_OPTION_UPDATE;
		}
		
		try {
			
			questionService.updateQuestionOption(questionOption);
		} catch (Exception e) {
			return VIEW_OPTION_UPDATE;
		}
		return "redirect:" + VIEW_OPTION_LIST+"?questionId="+questionId;
	}
 
	@RequestMapping(value = "/option/delete/{questionId}/{id}", method = RequestMethod.GET)
	public String optionDelete(Model model, @PathVariable("id") Integer id,@PathVariable("questionId") Integer questionId) {
		try {
			questionService.deleteQuestionOption(id);
		} catch (Exception e) {
			
			logger.info(e.getMessage());
		}
		return "redirect:" + VIEW_OPTION_LIST+"?questionId="+questionId;
	}
	
	
	
	/*--------angularjs-------*/
	@ResponseBody
	@RequestMapping(value = "/getQuestion")
	public List<Question> getQuestions(
			@RequestParam(value="p", defaultValue="1") String p,
			@RequestParam(value="q", required=false) String q,
			@RequestParam(value="taskId",required=false) Integer taskId,
			Model model) {
		Pager pager = new Pager(Integer.valueOf(p), 20); 
		List<Question> questions = questionService.getQuestionListByTaskId(pager, q, taskId);
		List<Question> QuestionWithOption  = new ArrayList<Question>();
	   
		for(Question question:questions){
			
			question.setOptions( questionService.getQuestionOptionListByQuestionId(question.getId()));
			QuestionWithOption.add(question);
		}
		
		return QuestionWithOption;
	}
	@ResponseBody
	@RequestMapping(value = "/getQuestionRandom")
	public List<Question> getQuestionRandom(@RequestParam(value="taskId",required=false) Integer taskId,
			Model model) {
		
		List<Question> questions = questionService.getQuestionListByTaskIdRandom(taskId);
		List<Question> QuestionWithOption  = new ArrayList<Question>();
	   
		for(Question question:questions){
			
			question.setOptions( questionService.getQuestionOptionListByQuestionId(question.getId()));
			QuestionWithOption.add(question);
		}
		
		return QuestionWithOption;
	}
	@ResponseBody
	@RequestMapping(value = "/deleteQuestion/{questionId}", method = RequestMethod.GET)
	public void deleteQuestion(Model model, @PathVariable("questionId") Integer questionId) {
		try {
			questionService.deleteQuestion(questionId);
		} catch (Exception e) {
			
			logger.info(e.getMessage());
		}
	}
	@ResponseBody
	@RequestMapping(value = "/addQuestion", method = RequestMethod.POST,headers = {
			"content-type=application/json", "content-type=application/xml" })
	public void addQuestion(Model model, @RequestBody Question question) {
		
		try {
			question.setSort(1);
			questionService.insertQuestion(question);
			List<QuestionOption> options = question.getOptions();
			for(QuestionOption questionOption:options){
				questionOption.setQuestionId(question.getId());
			questionService.insertQuestionOption(questionOption);}
		} catch (DuplicateKeyException e) {
			
		}
	
		
	}
	@ResponseBody
	@RequestMapping(value = "/editQuestion", method = RequestMethod.POST,headers = {
			"content-type=application/json", "content-type=application/xml" })
	public void editQuestion(Model model, @RequestBody Question question) {
		
		try {
			questionService.editQuestion(question);

		} catch (Exception e) {
      
		}
	
		
	}
}
