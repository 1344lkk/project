package com.hzcwtech.wuzhong.web.console.controller;

import java.util.List;
import java.util.Locale;

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
import com.hzcwtech.wuzhong.model.ACLRole;
import com.hzcwtech.wuzhong.model.ACLRole;
import com.hzcwtech.wuzhong.model.mapper.AuthMapper;
import com.hzcwtech.wuzhong.service.AuthService;

@Controller("ACLRoleController")
@RequestMapping("/console/acl/role")
public class ACLRoleController {
	
	private static final Logger logger = LoggerFactory.getLogger(ACLRoleController.class);
	
	private static final String VIEW_LIST = "/console/acl/role/list";
	private static final String VIEW_CREATE = "/console/acl/role/create";
	private static final String VIEW_UPDATE = "/console/acl/role/create";
	
	@Autowired
	private AuthService authService;
	
	@InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(String.class, new StringTrimmerEditor(" \t\r\n\f", false));
    }
	
	@RequestMapping(value = "/list")
	public String index(
			@RequestParam(value="enabled", required=false) Integer enabled,
			@RequestParam(value="q", required=false) String q,
			Model model) {
		List<ACLRole> roles = authService.searchRoleList(enabled, q);
		model.addAttribute("roles", roles);
		model.addAttribute("enabled", enabled);
		model.addAttribute("q", q);
		return VIEW_LIST;
	}
	
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public String create(@ModelAttribute("role") ACLRole role) {
		return VIEW_CREATE;
	}
	
	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public String create(Model model, @Valid @ModelAttribute("role") ACLRole role, Errors errors) {
		if (errors.hasErrors()) {
			return VIEW_CREATE;
		}
		
		try {
			authService.insertRole(role);
		} catch (DuplicateKeyException e) {
			logger.warn("角色代码已被使用 " + role.getCode() );
			errors.rejectValue("code", "acl.role.code.duplicate", "角色代码已被使用");
			return VIEW_CREATE;
		}
	
		return "redirect:" + VIEW_LIST;
	}
	
	@RequestMapping(value = "/update/{roleId}", method = RequestMethod.GET)
	public String update(Model model, @PathVariable("roleId") Integer roleId) {
		ACLRole role = authService.getRoleById(roleId);
		model.addAttribute("role", role);
		return VIEW_UPDATE;
	}
	
	@RequestMapping(value = "/update/{roleId}", method = RequestMethod.POST)
	public String update(Model model, @Valid @ModelAttribute("role") ACLRole role, Errors errors) {
		if (errors.hasErrors()) {
			return VIEW_UPDATE;
		}
		
		try {
			authService.updateRole(role);
		} catch (DuplicateKeyException e) {
			logger.warn("角色代码已被使用 " + role.getCode() );
			errors.rejectValue("code", "acl.role.code.duplicate", "角色代码已被使用");
			return VIEW_UPDATE;
		}
		return "redirect:" + VIEW_LIST;
	}
	
	@RequestMapping(value = "/delete/{roleId}", method = RequestMethod.GET)
	public String delete(Model model, @PathVariable Integer roleId) {
		try {
			ACLRole  role = new ACLRole();
			role.setId(roleId);
			authService.deleteRole(role);
		} catch (Exception e) {
			
			logger.info(e.getMessage());
		}
		return "redirect:" + VIEW_LIST;
	}
}
