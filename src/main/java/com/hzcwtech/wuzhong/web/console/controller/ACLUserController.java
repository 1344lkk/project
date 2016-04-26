package com.hzcwtech.wuzhong.web.console.controller;


import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
 import javax.validation.Valid;

import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import com.hzcwtech.mybatis.Pager;
import com.hzcwtech.wuzhong.model.User;
import com.hzcwtech.wuzhong.service.AuthService;
import com.hzcwtech.wuzhong.service.UserService;
import com.hzcwtech.wuzhong.web.security.GrantedUser;


@Controller("UserController")
@RequestMapping("/console/acl/user")
public class ACLUserController {

	@Autowired
	private UserService userService;
    @Autowired
    private AuthService authService;
	private static final Logger logger = LoggerFactory.getLogger(ACLUserController.class);

	private static final String VIEW_LIST = "/console/acl/user/list";
	private static final String VIEW_CREATE = "/console/acl/user/create";
	private static final String VIEW_UPDATE = "/console/acl/user/create";
	
	@RequestMapping(value = "/list")
	public String index(@RequestParam(value="p", defaultValue="1") String p,
			@RequestParam(value="q", required=false) String q,
			@RequestParam(value="enabled", required=false) Integer enabled,
			Model model) {
	
		Pager pager = new Pager(Integer.valueOf(p), 20);
		List<User> users =  userService.searchUserList(pager, enabled, q);
		
		model.addAttribute("users", users);
		model.addAttribute("enable", enabled);
		model.addAttribute("pager", pager);
		model.addAttribute("q", q);
		
		return VIEW_LIST;
	}
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public String create(@ModelAttribute("user") User user,Model model) {
		model.addAttribute("roles", authService.getRoleList());
		return VIEW_CREATE;
	}
	
	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public String create(Model model, @Valid @ModelAttribute("user") User user, Errors errors) {
		if (errors.hasErrors()) return VIEW_CREATE;
		
		try {
			 Date date=new Date();
			 Timestamp timer=new Timestamp(date.getTime());
			 user.setCreateTime(timer);
			 user.setCreateUserId(GrantedUser.getCurrent().getId());
			 user.setClearPassword(user.getPassword());
			 user.setPassword(DigestUtils.sha1Hex(user.getPassword()));
			 userService.insertUser(user);
		} catch (Exception e) {
			model.addAttribute("roles", authService.getRoleList());
			logger.warn("用户名 " + user.getUsername());
			errors.rejectValue("username", "user.username.duplicate", "用户名已被使用");
			return VIEW_CREATE;
		}
	
		return "redirect:" + VIEW_LIST;
	}
	
	@RequestMapping(value = "/update/{userId}", method = RequestMethod.GET)
	public String update(Model model, @PathVariable("userId") Integer userId) {
		model.addAttribute("roles", authService.getRoleList());
		User user = userService.getUserById(userId);
		model.addAttribute("user", user);
		return VIEW_UPDATE;
	}
	
	@RequestMapping(value = "/update/{userId}", method = RequestMethod.POST)
	public String update(Model model, @Valid @ModelAttribute("user") User user, Errors errors) {
		if (errors.hasErrors()) return VIEW_UPDATE;
		
		try {
			 user.setClearPassword(user.getPassword());
			 user.setPassword(DigestUtils.sha1Hex(user.getPassword()));
			userService.updateUser(user);
		} catch (DuplicateKeyException e) {
			model.addAttribute("roles", authService.getRoleList());
			logger.warn("用户名 " + user.getUsername());
			errors.rejectValue("username", "user.username.duplicate", "用户名已被使用");
			return VIEW_UPDATE;
		}
		return "redirect:" + VIEW_LIST;
	}
 
	@RequestMapping(value = "/delete/{userId}", method = RequestMethod.GET)
	public String delete(Model model, @PathVariable("userId") Integer userId) {
		try {
			userService.deleteUser(userId);
		} catch (Exception e) {
			logger.info(e.getMessage());
		}
		return "redirect:" + VIEW_LIST;
	}
	

}
