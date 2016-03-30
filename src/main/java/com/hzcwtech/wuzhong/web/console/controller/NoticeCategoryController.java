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
import com.hzcwtech.wuzhong.model.NoticeCategory;
import com.hzcwtech.wuzhong.service.NoticeCategoryService;

@Controller("NoticeCategoryController")
@RequestMapping("/console/notice/category")
public class NoticeCategoryController {
	
	private static final Logger logger = LoggerFactory.getLogger(NoticeCategoryController.class);
	
	private static final String VIEW_LIST = "/console/notice/category/list";
	private static final String VIEW_CREATE = "/console/notice/category/create";
	private static final String VIEW_UPDATE = "/console/notice/category/create";
	
	@Autowired
	private NoticeCategoryService noticeCategoryService;
	
	@InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(String.class, new StringTrimmerEditor(" \t\r\n\f", true));
    }
	
	@RequestMapping(value = "/list")
	public String index(
			@RequestParam(value="p", defaultValue="1") String p,
			@RequestParam(value="q", required=false) String q,
			Model model) {
		Pager pager = new Pager(Integer.valueOf(p), 20); 
		
		List<NoticeCategory> noticeCategorys = noticeCategoryService.searchNoticeCategoryList(pager, q);
		model.addAttribute("noticeCategorys", noticeCategorys);
		model.addAttribute("pager", pager);
		model.addAttribute("q", q);
		return VIEW_LIST;
	}
	
	
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public String create(@ModelAttribute("noticeCategory") NoticeCategory noticeCategory) {
		return VIEW_CREATE;
	}
	
	
	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public String create(Model model, @Valid @ModelAttribute("noticeCategory") NoticeCategory noticeCategory, Errors errors) {
		if (errors.hasErrors()) {
			return VIEW_CREATE;
		}
		
		try {
			
			noticeCategoryService.addNoticeCategory(noticeCategory);
		} catch (DuplicateKeyException e) {
			logger.warn("公告主键冲突 " + noticeCategory.getId() );
			errors.rejectValue("code", "acl.noticeCategory.code.duplicate", "公告主键冲突");
			return VIEW_CREATE;
		}
	
		return "redirect:" + VIEW_LIST;
	}
	
	@RequestMapping(value = "/update/{noticeCategoryId}", method = RequestMethod.GET)
	public String update(Model model, @PathVariable("noticeCategoryId") Integer noticeCategoryId) {
		NoticeCategory noticeCategory = noticeCategoryService.getNoticeCategoryById(noticeCategoryId);
		model.addAttribute("noticeCategory", noticeCategory);
		return VIEW_UPDATE;
	}
	
	@RequestMapping(value = "/update/{noticeCategoryId}", method = RequestMethod.POST)
	public String update(Model model, @Valid @ModelAttribute("noticeCategory") NoticeCategory noticeCategory, Errors errors) {
		if (errors.hasErrors()) {
			return VIEW_UPDATE;
		}
		
		try {
			noticeCategoryService.updateNoticeCategory(noticeCategory);
		} catch (Exception e) {
			return VIEW_UPDATE;
		}
		return "redirect:" + VIEW_LIST;
	}
 
	@RequestMapping(value = "/delete/{noticeCategoryId}", method = RequestMethod.GET)
	public String delete(Model model, @PathVariable("noticeCategoryId") Integer noticeCategoryId) {
		try {
			noticeCategoryService.deleteNoticeCategory(noticeCategoryId);
		} catch (Exception e) {
			
			logger.info(e.getMessage());
		}
		return "redirect:" + VIEW_LIST;
	}
}
