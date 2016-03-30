package com.hzcwtech.wuzhong.web.console.controller;

import java.util.ArrayList;

import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hzcwtech.wuzhong.model.ACLResource;
import com.hzcwtech.wuzhong.model.User;
import com.hzcwtech.wuzhong.service.UserService;
import com.hzcwtech.wuzhong.util.ErrorMessage;
import com.hzcwtech.wuzhong.util.MD5Util;
import com.hzcwtech.wuzhong.web.security.GrantedUser;
@Controller
@RequestMapping("/console/member")
public class MemberController {
	
	
    private static final Logger logger = LoggerFactory.getLogger(MemberController.class);
	
	private static final String VIEW_PROFILE = "/console/member/profile";
	private static final String VIEW_CHANGEPASSWORD = "/console/member/changePassword";
	
	@Autowired
	private UserService userService;
	
	@InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(String.class, new StringTrimmerEditor(" \t\r\n\f", false));
    }
	
	@RequestMapping(value = "/profile", method = RequestMethod.GET)
	public String create(Model model) {
		int userId = GrantedUser.getCurrent().getId();
		User  user =  userService.getUserById(userId);
		model.addAttribute("user", user);
		return VIEW_PROFILE;
	}
	@RequestMapping(value = "/changePassword", method = RequestMethod.GET)
	public String changePassword(Model model) {
		int userId = GrantedUser.getCurrent().getId();
		User  user =  userService.getUserById(userId);
		model.addAttribute("user", user);
		return VIEW_CHANGEPASSWORD;
	}
    @ResponseBody
	@RequestMapping(value = "/changeUserPassword", method = RequestMethod.POST)
	public ErrorMessage changeUserPassword(Model model,@RequestParam("oldPassword") String oldPassword,@RequestParam("newPassword") String newPassword,@RequestParam("newPassword2") String newPassword2) {
		ErrorMessage message  =  new  ErrorMessage();
		int userId = GrantedUser.getCurrent().getId();
		User  user =  userService.getUserById(userId);
		String content;	
		oldPassword =DigestUtils.sha1Hex(oldPassword);
		String userPassword = user.getPassword();
		if(!oldPassword.equals(userPassword)){
			content = "原密码错误";
			message.setCode(0);;
			message.setMessage(content);
			return message;
		}
		if(!newPassword2.equals(newPassword)){
			content = "新密码不同";
			message.setCode(1);;
			message.setMessage(content);
			return message;
		}
		
		user.setClearPassword(newPassword);
		if(userService.changePassword(user,oldPassword, DigestUtils.sha1Hex(newPassword))){
			content = "更改成功";
			message.setCode(2);;
			message.setMessage(content);
			return message;
			
		}else{
			content = "更改失败";
			message.setCode(3);;
			message.setMessage(content);
			return message;
		}
		
	}
}
	


