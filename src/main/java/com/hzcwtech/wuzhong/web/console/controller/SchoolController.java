package com.hzcwtech.wuzhong.web.console.controller;

import java.io.File;
import java.io.InputStream;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSONObject;
import com.hzcwtech.mybatis.Pager;
import com.hzcwtech.wuzhong.model.School;
import com.hzcwtech.wuzhong.service.SchoolService;
import com.hzcwtech.wuzhong.util.ErrorMessage;

@Controller("SchoolController")
@RequestMapping("/console/school")
public class SchoolController {

	private static final Logger logger = LoggerFactory.getLogger(SchoolController.class);
	private static final String LIST_VIEW = "/console/school/list";
	private static final String CREATE_VIEW = "/console/school/create";

	@Autowired
	private SchoolService schoolService;

	@RequestMapping("/list")
	public String getSchoolList(Model model) {

		List<School> schools = schoolService.getSchoolList();
		model.addAttribute("schools", schools);

		return LIST_VIEW;
	}

	@RequestMapping(value = "/update/{schoolId}", method = RequestMethod.POST)
	public String update(Model model, @Valid @ModelAttribute("school") School school, Errors errors) {
		if (errors.hasErrors()) {
			model.addAttribute("errorMessage", "数据不符合规格");
			return CREATE_VIEW;
		}

		try {
			schoolService.updateSchool(school);
		} catch (Exception e) {
			logger.warn("执行失败：" + e.getMessage());
			model.addAttribute("errorMessage", "执行出错");
			return CREATE_VIEW;
		}
		return "redirect:" + LIST_VIEW;
		// return CREATE_VIEW;

	}

	@RequestMapping(value = "/update/{schoolId}", method = RequestMethod.GET)
	public String update(Model model, @PathVariable("schoolId") Integer schoolId) {
		School school = schoolService.getSchoolById(schoolId);
		model.addAttribute("school", school);
		return "/console/school/create";
	}

	@RequestMapping(value = "/delete/{schoolId}", method = RequestMethod.GET)
	public String deleteGet(Model model, @PathVariable("schoolId") Integer schoolId) {
		try {
			schoolService.deleteSchool(schoolId);
		} catch (Exception e) {
			model.addAttribute("errorMessage", "执行出错");
			logger.info(e.getMessage());

		}
		return "redirect:" + LIST_VIEW;

	}

	@RequestMapping(value = "/search")
	public String index(@RequestParam(value = "p", defaultValue = "1") String p,
			@RequestParam(value = "q", required = false) String q, Model model) {
		Pager pager = new Pager(Integer.valueOf(p), 20); // TODO 在配置文件中设置默认页数

		List<School> schools = schoolService.searchSchoolList(pager, q);
		model.addAttribute("schools", schools);
		model.addAttribute("pager", pager);
		model.addAttribute("q", q);
		return LIST_VIEW;
	}

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public String create(@ModelAttribute("school") School school) {
		return "/console/school/create";
	}

	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public String create(Model model, @Valid @ModelAttribute("school") School school, Errors errors) {
		if (errors.hasErrors()) {
			model.addAttribute("errorMessage", "数据不符合规格");
			return CREATE_VIEW;
		}

		try {
			schoolService.insertSchool(school);
		} catch (Exception e) {
			logger.warn("资源主键冲突 " + school.getId());
			model.addAttribute("errorMessage", "执行出错");
			// errors.rejectValue("code", "acl.resource.code.duplicate",
			// "资源主键冲突");
			return CREATE_VIEW;
		}

		return "redirect:" + LIST_VIEW;
	}

	@RequestMapping(value = "/importSchools", method = RequestMethod.POST)
	public String importBrandSort(@RequestParam("filename") MultipartFile file, HttpServletRequest request, Model model)
			throws Exception {
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
			if (name.endsWith("xlsx")) {
				schoolService.insertSchoolsForExcel2007(is);
			} else if (name.endsWith("xls")) {
				schoolService.insertSchoolsForExcel2003(is);
			} else {
				model.addAttribute("errorMessage", "上传文件格式不正确");
				message.setMessage("上传文件格式不正确");
				message.setCode(-1);
			}

		} catch (Exception e) {
			logger.warn(e.getMessage());
			model.addAttribute("errorMessage", "excel结构不正确");
			message.setCode(-1);
			message.setMessage("excel结构不正确");

		}
		String jsonMessage = JSONObject.toJSONString(message);

		return "redirect:" + LIST_VIEW;
	}

}
