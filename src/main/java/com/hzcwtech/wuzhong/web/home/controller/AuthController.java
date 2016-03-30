package com.hzcwtech.wuzhong.web.home.controller;

import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hzcwtech.wuzhong.model.Student;
import com.hzcwtech.wuzhong.model.User;
import com.hzcwtech.wuzhong.service.AuthService;
import com.hzcwtech.wuzhong.service.StudentService;
import com.hzcwtech.wuzhong.service.UserService;
import com.hzcwtech.wuzhong.util.ErrorMessage;
import com.hzcwtech.wuzhong.web.security.GrantedUser;

@Controller("HomeAuthController")
@RequestMapping("/auth")
public class AuthController {
	
	private static final Logger logger = LoggerFactory.getLogger(AuthController.class);
	
	@Autowired 
	private UserService userService;
	
	@Autowired
	private AuthService authService;
	
	@Autowired
	private StudentService studentService;
	
	@RequestMapping(value = "/signin", method = RequestMethod.GET)
	public String signin(Locale locale, Model model,@RequestParam(value="error", required=false)boolean error) {
		//@RequestParam(value="error", required=false)
		Date date = new Date();
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);
		String serverTime = dateFormat.format(date);
		
		logger.info("HOME : locale = {} serverTime = {}.", locale, serverTime);
		
		model.addAttribute("serverTime", serverTime );
		if(error){
			model.addAttribute("msg", "用户名或密码错误" );
		}
		return "home/auth/signin";
	}
	
	
	@RequestMapping(value = "/signout")
	public String signout(Locale locale, Model model) {
		return "redirect:/";
	}
	
	@RequestMapping(value="/changePassword",method = RequestMethod.GET)
	public String changePassword(Model model){
		int userId = GrantedUser.getCurrent().getId();
		Student student = studentService.getStudentByUserId(userId);
		model.addAttribute("user", student);
		return "home/auth/changePassword";
	}
	
	@ResponseBody
	@RequestMapping(value="/member/Password",method = RequestMethod.POST)
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
			content = "两次密码输入不一致";
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
			content = "更改失败,服务器繁忙";
			message.setCode(3);;
			message.setMessage(content);
			return message;
		}
		
	}

}
