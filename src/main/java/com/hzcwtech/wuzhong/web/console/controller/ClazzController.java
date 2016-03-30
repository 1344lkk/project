package com.hzcwtech.wuzhong.web.console.controller;

import java.util.List;

import javax.validation.Valid;

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

import com.hzcwtech.mybatis.Pager;
import com.hzcwtech.wuzhong.model.Clazz;
import com.hzcwtech.wuzhong.model.School;
import com.hzcwtech.wuzhong.service.ClazzService;
import com.hzcwtech.wuzhong.service.SchoolService;

@Controller("ClazzController")
@RequestMapping("/console/class")
public class ClazzController {

	private static final Logger logger = LoggerFactory.getLogger(ClazzController.class);

	private static final String LIST_VIEW = "/console/clazz/list";
	private static final String CREATE_VIEW = "/console/clazz/create";

	@Autowired
	private ClazzService clazzService;
	@Autowired
	private SchoolService schoolService;

	@RequestMapping("/list")
	public String list(Model model) {

		try {
			List<Clazz> clazzes = clazzService.getClazzList();
			Clazz clazz = clazzes.get(0);
			model.addAttribute("clazzes", clazzes);
		} catch (Exception e) {
			logger.info(e.getMessage());
		}

		return LIST_VIEW;
	}

	@RequestMapping(value = "/getlist/{schoolId}/{schoolName}", method = RequestMethod.GET)
	public String getClazzlistBySchoolId(Model model, @PathVariable("schoolId") Integer schoolId,
			@PathVariable("schoolName") String schoolName) {

		try {
			List<Clazz> clazzes = clazzService.getClazzListBySchoolId(schoolId);
			model.addAttribute("clazzes", clazzes);
			model.addAttribute("schoolId", schoolId);
			model.addAttribute("schoolName", schoolName);
		} catch (Exception e) {
			logger.info(e.getMessage());
		}

		return LIST_VIEW;
	}

	@RequestMapping(value = "/delete/{clazzId}", method = RequestMethod.GET)
	public String delete(Model model, @PathVariable("clazzId") Integer clazzId) {
		try {
			Clazz  clazz = clazzService.getClazzByClazzId(clazzId);
			int schoolId=clazz.getSchoolId();
			clazzService.deleteClazzById(clazzId);			
			List<Clazz> clazzes = clazzService.getClazzListBySchoolId(schoolId);
			model.addAttribute("clazzes", clazzes);
		} catch (Exception e) {
			logger.info(e.getMessage());
		}
		return LIST_VIEW;

	}

	@RequestMapping(value = "/update/{clazzId}", method = RequestMethod.GET)
	public String update(Model model, @PathVariable("clazzId") Integer clazzId) {
		Clazz clazz = clazzService.getClazzByClazzId(clazzId);
		model.addAttribute("clazz", clazz);
		return CREATE_VIEW;
	}

	@RequestMapping(value = "/update/{clazzId}", method = RequestMethod.POST)
	public String update(Model model, @Valid @ModelAttribute("clazz") Clazz clazz, Errors errors) {
		if (errors.hasErrors()) {
			model.addAttribute("errorMessage", "数据不符合规格");
			return CREATE_VIEW;
		}
		try {
			clazzService.updateClazz(clazz);
			List<Clazz> clazzes = clazzService.getClazzListBySchoolId(clazz.getSchoolId());
			model.addAttribute("clazzes", clazzes);
		} catch (Exception e) {
			logger.warn("操作失败" + e.getMessage());
			model.addAttribute("errorMessage", "操作失败");
			return CREATE_VIEW;
		}
		return LIST_VIEW;
	}

	@RequestMapping(value = "/search")
	public String search(@RequestParam(value = "p", defaultValue = "1") String p,
			@RequestParam(value = "q", required = false) String q, @RequestParam(value = "schoolId") Integer schoolId,
			Model model) {
		Pager pager = new Pager(Integer.valueOf(p), 20);

		List<School> clazzes = clazzService.searchClazzList(pager, q, schoolId);
		model.addAttribute("clazzes", clazzes);
		model.addAttribute("schoolId", schoolId);
		model.addAttribute("pager", pager);
		model.addAttribute("q", q);
		return LIST_VIEW;
	}

	@RequestMapping(value = "/create/{schoolId}", method = RequestMethod.GET)
	public String create(@ModelAttribute("clazz") Clazz clazz, @PathVariable("schoolId") Integer schoolId) {
		School school = schoolService.getSchoolById(schoolId);
		clazz.setSchoolName(school.getName());
		clazz.setSchoolId(schoolId);
		return CREATE_VIEW;
	}

	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public String create(Model model, @Valid @ModelAttribute("clazz") Clazz clazz, Errors errors) {
		if (errors.hasErrors()) {
			model.addAttribute("errorMessage", "数据不符合规格");
			return CREATE_VIEW;
		}

		try {
			clazzService.insertClazz(clazz);
			List<Clazz> clazzes = clazzService.getClazzListBySchoolId(clazz.getSchoolId());
			model.addAttribute("clazzes", clazzes);
		} catch (Exception e) {
			logger.warn("操作失败 " + clazz.getId());
			model.addAttribute("errorMessage", "操作失败");
			return CREATE_VIEW;
		}

		return LIST_VIEW;
	}

}
