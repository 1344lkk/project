package com.hzcwtech.wuzhong.web.console.controller;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.apache.commons.fileupload.DiskFileUpload;
import org.apache.ibatis.javassist.bytecode.stackmap.TypeData.ClassName;
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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSONObject;
import com.hzcwtech.mybatis.Pager;
import com.hzcwtech.wuzhong.model.Clazz;
import com.hzcwtech.wuzhong.model.Student;
import com.hzcwtech.wuzhong.model.User;
import com.hzcwtech.wuzhong.service.ClazzService;
import com.hzcwtech.wuzhong.service.StudentService;
import com.hzcwtech.wuzhong.service.UserService;
import com.hzcwtech.wuzhong.util.ErrorMessage;
import com.hzcwtech.wuzhong.web.security.GrantedUser;

@Controller("StudentController")
@RequestMapping("/console/student")
public class StudentController {

	@Autowired
	private StudentService studentService;
	@Autowired
	private UserService userService;
	@Autowired
	private ClazzService clazzService;

	private static final Logger logger = LoggerFactory.getLogger(StudentController.class);

	private static final String LIST_VIEW = "/console/student/list";

	private static final String CREATE_VIEW = "/console/student/create";

	private static final String UPDATE_VIEW = "/console/student/create";

	/*
	 * @RequestMapping("/list") public String List(@ModelAttribute("students")
	 * List<Student>students) { return LIST_VIEW; }
	 */
	@RequestMapping("/list/{classId}")

	public String List(Model model, @PathVariable("classId") Integer classId) {
		try {
			List<Student> students = studentService.getStudentsByClassId(classId);
			//Student st = students.get(0);
			String className = studentService.getClassname(classId);
			model.addAttribute("className", className);
			model.addAttribute("students", students);
			model.addAttribute("classId", classId);

		} catch (Exception e) {
			logger.warn("获取失败" + e.getMessage());
		}
		return LIST_VIEW;
	}

	@RequestMapping("/list")
	public String AllStudentList(Model model) {
		try {
			List<Student> students = studentService.getStudents();
			Student st = students.get(0);
			model.addAttribute("students", students);

		} catch (Exception e) {
			logger.warn("获取失败" + e.getMessage());
		}
		return LIST_VIEW;
	}

	@RequestMapping(value = "/search")
	public String index(@RequestParam(value = "p", defaultValue = "1") String p,
			@RequestParam(value = "q", required = false) String q, Model model) {
		Pager pager = new Pager(Integer.valueOf(p), 20);

		List<Student> students = studentService.searchStudentList(pager, q);
		model.addAttribute("students", students);
		model.addAttribute("pager", pager);
		model.addAttribute("q", q);
		return LIST_VIEW;
	}

	@RequestMapping(value = "/importStudents/{classId}", method = RequestMethod.POST)
	public String importStudents(@RequestParam("filename") MultipartFile file, @PathVariable("classId") Integer classId,
			HttpServletRequest request, Model model) throws Exception {
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

			studentService.insertStudent(is, name, classId, 1);

		} catch (Exception e) {
			logger.warn(e.getMessage());
			model.addAttribute("errorMessage", "excel结构不正确");
			message.setCode(-1);
			message.setMessage("excel结构不正确");
		}
		String jsonMessage = JSONObject.toJSONString(message);

		return "redirect:" + LIST_VIEW+"/"+classId;
	}

	@RequestMapping("/export/{classId}")
	public String exportExcel(HttpServletResponse response, @PathVariable("classId") Integer classId) {
		response.setContentType("application/binary;charset=ISO8859_1");
		try {
			Clazz clazz = clazzService.getClazzByClazzId(classId);
			String className = null;
			if(clazz!=null){
				className = new String ((clazz.getSchoolName() + clazz.getName()).getBytes(),"ISO8859_1");
			}
			if(className == null){
				className = new String(("Students" + System.currentTimeMillis()).getBytes(), "ISO8859_1");
			}
			ServletOutputStream outputStream = response.getOutputStream();
			
			response.setHeader("Content-disposition", "attachment; filename=" + className + ".xlsx");// 组装附件名称和格式
			List<Student> students = studentService.getStudentsByClassId(classId);
			studentService.exportStudentListToExcel(className,students, outputStream);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	@RequestMapping(value = "/delete/{userId}/{classId}", method = RequestMethod.GET)
	public String deleteGet(Model model, @PathVariable("userId") Integer userId,@PathVariable("classId") Integer classId) {
		try {
			studentService.deleteStudentByUserId(userId);
		} catch (Exception e) {
			model.addAttribute("errorMessage", "执行出错");
			logger.info(e.getMessage());

		}
		return "redirect:" + LIST_VIEW+"/"+classId;

	}

	@RequestMapping(value = "/update/{userId}/{classId}", method = RequestMethod.POST)
	public String update(Model model,@PathVariable("classId") Integer classId, @Valid @ModelAttribute("user") User user, Errors errors) {
		if (errors.hasErrors()) {
			model.addAttribute("errorMessage", "数据不符合规格");
			return UPDATE_VIEW;
		}

		try {
			userService.updateUser(user);
			
		} catch (Exception e) {
			logger.warn("执行失败：" + e.getMessage());
			model.addAttribute("errorMessage", "执行出错");
			return UPDATE_VIEW;
		}
		return "redirect:" + LIST_VIEW+"/"+classId;
		// return CREATE_VIEW;

	}

	@RequestMapping(value = "/update/{userId}", method = RequestMethod.GET)
	public String update(Model model, @PathVariable("userId") Integer userId) {
		Student student = studentService.getStudentByUserId(userId);
		model.addAttribute("student", student);
		return UPDATE_VIEW;
	}

	@RequestMapping(value = "/create/{classId}", method = RequestMethod.GET)
	public String create(@ModelAttribute("student") Student student, @PathVariable("classId") Integer classId) {
		student.setClassId(classId);
		return CREATE_VIEW;
	}

	@RequestMapping(value = "/create/{classId}", method = RequestMethod.POST)
	public String create(Model model, @ModelAttribute("user") User user,@PathVariable("classId") int classId) {
		
		try {
			user.setCreateUserId(GrantedUser.getCurrent().getId());
			Date date  = new Date();
			Timestamp timer = new Timestamp(date.getTime());
			user.setCreateTime(timer);
			studentService.addStudent(user,classId);
		} catch (Exception e) {
			
			e.printStackTrace();
		}

		return "redirect:" + LIST_VIEW+"/"+classId;
	}
	@ResponseBody
	@RequestMapping(value = "/students/{classId}", method = RequestMethod.GET)
	public List<Student> students(Model model, @PathVariable("classId") Integer classId) {
		List<Student> students  =  new ArrayList<Student>();
		try {
			 students = studentService.getStudentsByClassId(classId);
			
		} catch (Exception e) {
			logger.warn("获取失败" + e.getMessage());
		}
		return students;
	}


}
