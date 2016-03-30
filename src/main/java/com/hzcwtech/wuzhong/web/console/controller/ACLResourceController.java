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
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.hzcwtech.mybatis.Pager;
import com.hzcwtech.wuzhong.model.ACLResource;
import com.hzcwtech.wuzhong.service.AuthService;
import  com.hzcwtech.wuzhong.util.ErrorMessage;

@Controller("ACLResourceController")
@RequestMapping("/console/acl/resource")
public class ACLResourceController {
	
	private static final Logger logger = LoggerFactory.getLogger(ACLResourceController.class);
	
	private static final String VIEW_LIST = "/console/acl/resource/list";
	private static final String VIEW_CREATE = "/console/acl/resource/create";
	private static final String VIEW_UPDATE = "/console/acl/resource/create";
	
	@Autowired
	private AuthService authService;
	
	@InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(String.class, new StringTrimmerEditor(" \t\r\n\f", false));
    }
	
	@RequestMapping(value = "/list")
	public String index(
			@RequestParam(value="p", defaultValue="1") String p,
			@RequestParam(value="q", required=false) String q,
			Model model) {
		Pager pager = new Pager(Integer.valueOf(p), 20); //TODO 在配置文件中设置默认页数
		
		List<ACLResource> resources = authService.searchResourceList(pager, q);
		model.addAttribute("resources", resources);
		model.addAttribute("pager", pager);
		model.addAttribute("q", q);
		return VIEW_LIST;
	}
	
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public String create(@ModelAttribute("resource") ACLResource resource) {
		return VIEW_CREATE;
	}
	
	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public String create(Model model, @Valid @ModelAttribute("resource") ACLResource resource, Errors errors) {
		if (errors.hasErrors()) return VIEW_CREATE;
		
		try {
			authService.insertResource(resource);
		} catch (DuplicateKeyException e) {
			logger.warn("资源代码已被使用 " + resource.getCode() );
			errors.rejectValue("code", "acl.resource.code.duplicate", "资源代码已被使用");
			return VIEW_CREATE;
		}
	
		return "redirect:" + VIEW_LIST;
	}
	
	@RequestMapping(value = "/update/{resourceId}", method = RequestMethod.GET)
	public String update(Model model, @PathVariable("resourceId") Integer resourceId) {
		ACLResource resource = authService.getResourceById(resourceId);
		model.addAttribute("resource", resource);
		return VIEW_UPDATE;
	}
	
	@RequestMapping(value = "/update/{resourceId}", method = RequestMethod.POST)
	public String update(Model model, @Valid @ModelAttribute("resource") ACLResource resource, Errors errors) {
		if (errors.hasErrors()) return VIEW_UPDATE;
		
		try {
			authService.updateResource(resource);
		} catch (DuplicateKeyException e) {
			logger.warn("资源代码已被使用 " + resource.getCode() );
			errors.rejectValue("code", "acl.resource.code.duplicate", "资源代码已被使用");
			return VIEW_UPDATE;
		}
		return "redirect:" + VIEW_LIST;
	}
 
	@RequestMapping(value = "/delete/{resourceId}", method = RequestMethod.GET)
	public String delete(Model model, @PathVariable("resourceId") Integer resourceId) {
		try {
			authService.deleteResource(resourceId);
		} catch (Exception e) {
			// TODO: handle exception
			logger.info(e.getMessage());
		}
		return "redirect:" + VIEW_LIST;
	}
}
