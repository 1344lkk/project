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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import com.hzcwtech.mybatis.Pager;
import com.hzcwtech.wuzhong.model.Notice;
import com.hzcwtech.wuzhong.model.NoticeCategory;
import com.hzcwtech.wuzhong.service.NoticeCategoryService;
import com.hzcwtech.wuzhong.service.NoticeService;
import com.hzcwtech.wuzhong.web.security.GrantedUser;

@Controller("NoticeController")
@RequestMapping("/console/notice")
public class NoticeController {
	
	private static final Logger logger = LoggerFactory.getLogger(NoticeController.class);
	
	private static final String VIEW_LIST = "/console/notice/list";
	private static final String VIEW_CREATE = "/console/notice/create";
	private static final String VIEW_UPDATE = "/console/notice/create";
	
	@Autowired
	private NoticeService noticeService;
	
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
			@RequestParam(value="state", required=false) Integer state,
			Model model) {
		Pager pager = new Pager(Integer.valueOf(p), 20); 
		
		List<Notice> notices = noticeService.searchNoticeList(pager, q, state);
		model.addAttribute("state", state);
		model.addAttribute("notices", notices);
		model.addAttribute("pager", pager);
		model.addAttribute("q", q);
		return VIEW_LIST;
	}
	
	
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public String create(@ModelAttribute("notice") Notice notice,Model model) {
		List<NoticeCategory> catgorys = noticeCategoryService.getNoticeCategorys();
	    model.addAttribute("catgorys", catgorys);
		return VIEW_CREATE;
	}
	
	
	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public String create(Model model, @Valid @ModelAttribute("notice") Notice notice, Errors errors) {
		if (errors.hasErrors()) {
			return VIEW_CREATE;
		}
		
		try {
			Date date=new Date();
			Timestamp timer=new Timestamp(date.getTime());
			notice.setCreateTime(timer);
			notice.setCreateUserId(GrantedUser.getCurrent().getId());
			noticeService.addNotice(notice);
		} catch (DuplicateKeyException e) {
			logger.warn("公告主键冲突 " + notice.getId() );
			errors.rejectValue("code", "acl.notice.code.duplicate", "公告主键冲突");
			return VIEW_CREATE;
		}
	
		return "redirect:" + VIEW_LIST;
	}
	
	@RequestMapping(value = "/update/{noticeId}", method = RequestMethod.GET)
	public String update(Model model, @PathVariable("noticeId") Integer noticeId) {
		Notice notice = noticeService.getNoticeById(noticeId);
		List<NoticeCategory> catgorys = noticeCategoryService.getNoticeCategorys();
	    model.addAttribute("catgorys", catgorys);
		model.addAttribute("notice", notice);
		return VIEW_UPDATE;
	}
	
	@RequestMapping(value = "/update/{noticeId}", method = RequestMethod.POST)
	public String update(Model model, @Valid @ModelAttribute("notice") Notice notice, Errors errors) {
		if (errors.hasErrors()) {
			return VIEW_UPDATE;
		}
		
		try {
			noticeService.updateNotice(notice);
		} catch (Exception e) {
			return VIEW_UPDATE;
		}
		return "redirect:" + VIEW_LIST;
	}
 
	@RequestMapping(value = "/delete/{noticeId}", method = RequestMethod.GET)
	public String delete(Model model, @PathVariable("noticeId") Integer noticeId) {
		try {
			noticeService.deleteNotice(noticeId);
		} catch (Exception e) {
			
			logger.info(e.getMessage());
		}
		return "redirect:" + VIEW_LIST;
	}
}
