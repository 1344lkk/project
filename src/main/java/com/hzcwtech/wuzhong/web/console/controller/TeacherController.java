package com.hzcwtech.wuzhong.web.console.controller;

import java.io.File;
import java.io.InputStream;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.fileupload.DiskFileUpload;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSONObject;
import com.hzcwtech.mybatis.Pager;
import com.hzcwtech.wuzhong.model.Teacher;
import com.hzcwtech.wuzhong.service.TeacherService;
import com.hzcwtech.wuzhong.util.ErrorMessage;

@Controller("TeacherController")
@RequestMapping("/console/teacher")
public class TeacherController {

	@Autowired
	private TeacherService teacherService;

	private static final Logger logger = LoggerFactory.getLogger(TeacherController.class);

	private static final String LIST_VIEW = "/console/teacher/list";

	private static final String UPDATE_VIEW = "/console/teacher/create";

	private static final String CREATE_VIEW = "/console/teacher/create";

	@RequestMapping("/list")
	public String List(Model model) {

		try {
			List<Teacher> teacheres = teacherService.getTeachers();
			model.addAttribute("teachers", teacheres);
		} catch (Exception e) {
			logger.info("获取出错" + e.getMessage());

		}

		return LIST_VIEW;

	}

	@RequestMapping(value = "/search")
	public String search(@RequestParam(value = "p", defaultValue = "1") String p,
			@RequestParam(value = "q", required = false) String q, Model model) {
		Pager pager = new Pager(Integer.valueOf(p), 20); // TODO 在配置文件中设置默认页数

		List<Teacher> teachers = teacherService.searchTeacherList(pager, q);
		model.addAttribute("teachers", teachers);
		model.addAttribute("pager", pager);
		model.addAttribute("q", q);
		return LIST_VIEW;
	}

	@RequestMapping(value = "/importTeachers", method = RequestMethod.POST)
	public String importTeachers(@RequestParam("filename") MultipartFile file, HttpServletRequest request,
			HttpServletResponse response, Model model) throws Exception {
		ErrorMessage message = new ErrorMessage();
		String temp = request.getSession().getServletContext().getRealPath(File.separator) + "temp"; // 临时目录
		File tempFile = new File(temp);
		if (!tempFile.exists()) {
			tempFile.mkdirs();
		}
		DiskFileUpload fu = new DiskFileUpload();
		fu.setSizeMax(10 * 1024 * 1024); // 设置允许用户上传文件大小,单位:位
		fu.setSizeThreshold(4096); // 设置最多只允许在内存中存储的数据,单位:位
		fu.setRepositoryPath(temp); // 设置一旦文件大小超过getSizeThreshold()的值时数据存放在硬盘的目录
		if (file == null)
			return null;
		logger.info(file.getOriginalFilename());

		String name = file.getOriginalFilename();// 获取上传文件名,包括路径

		long size = file.getSize();
		if ((name == null || name.equals("")) && size == 0)
			return null;
		InputStream is = file.getInputStream();

		try {

			teacherService.insertTeachers(is, name, 1);

		} catch (Exception e) {
			logger.warn(e.getMessage());
			model.addAttribute("errorMessage", "excel结构不正确");
			message.setCode(-1);
			message.setMessage("excel结构不正确");
		}
		String jsonMessage = JSONObject.toJSONString(message);

		return "redirect:" + LIST_VIEW;
	}

	@RequestMapping(value = "/update/{userId}", method = RequestMethod.POST)
	public String update(Model model, @Valid @ModelAttribute("teacher") Teacher teacher, Errors errors) {
		if (errors.hasErrors()) {
			model.addAttribute("errorMessage", "数据不符合规格");
			return UPDATE_VIEW;
		}

		try {
			teacher.getUser().setStatus("1");
			teacher.getUser().setClearPassword(teacher.getUser().getPassword());
			teacher.getUser().setPassword(DigestUtils.sha1Hex(teacher.getUser().getPassword()));
			teacherService.updateTeacher(teacher);
			
		} catch (Exception e) {
			errors.rejectValue("user.username", "username.duplicate", "用户名重复");
			return CREATE_VIEW;
		}
		return "redirect:" + LIST_VIEW;
		// return CREATE_VIEW;

	}

	@RequestMapping(value = "/update/{userId}", method = RequestMethod.GET)
	public String update(Model model, @PathVariable("userId") Integer userId) {
		Teacher teacher = teacherService.getTeacherByUserId(userId);
		model.addAttribute("teacher", teacher);
		return UPDATE_VIEW;
	}

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public String create(@ModelAttribute("teacher") Teacher teacher) {
		return CREATE_VIEW;
	}

	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public String create(Model model, @Valid @ModelAttribute("teacher") Teacher teacher, Errors errors) {
		if (errors.hasErrors()) {
			model.addAttribute("errorMessage", "数据不符合规格");
			return CREATE_VIEW;
		}

		try {
			teacher.getUser().setStatus("1");
			teacher.getUser().setClearPassword(teacher.getUser().getPassword());
			teacher.getUser().setPassword(DigestUtils.sha1Hex(teacher.getUser().getPassword()));
			teacherService.insertTeacher(teacher);
		} catch (Exception e) {
			errors.rejectValue("user.username", "username.duplicate", "用户名重复");
			return CREATE_VIEW;
		}

		return "redirect:" + LIST_VIEW;
	}

	@RequestMapping(value = "/delete/{userId}", method = RequestMethod.GET)
	public String deleteGet(Model model, @PathVariable("userId") Integer userId) {
		try {
			teacherService.deleteTeacherById(userId);
		} catch (Exception e) {
			model.addAttribute("errorMessage", "执行出错");
			logger.info(e.getMessage());

		}
		return "redirect:" + LIST_VIEW;

	}

}
