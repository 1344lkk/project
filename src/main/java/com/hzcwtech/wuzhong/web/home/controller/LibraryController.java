package com.hzcwtech.wuzhong.web.home.controller;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hzcwtech.wuzhong.model.Clazz;
import com.hzcwtech.wuzhong.model.Knowledge;
import com.hzcwtech.wuzhong.model.KnowledgeCatalog;
import com.hzcwtech.wuzhong.service.KnowledgeService;
import com.hzcwtech.wuzhong.web.security.GrantedUser;

@Controller("LibraryController")
public class LibraryController {

	private static final Logger logger = LoggerFactory.getLogger(LibraryController.class);
	@Autowired
	private KnowledgeService knowledgeService;

	// 知识库默认页
	@RequestMapping(value = "/library", method = RequestMethod.GET)
	public String index(Locale locale, Model model) {
		return "home/library/index";
		
	}
	@RequestMapping(value = "/library/console", method = RequestMethod.GET)
	public String cosoleLibrary(Locale locale, Model model) {
		
		return "console/library/index";
	}

	// 得到所有目录节点
	@ResponseBody
	@RequestMapping(value = "/library/json", method = RequestMethod.GET)
	public ArrayList<Object> getNodeJson(Locale locale, Model model) {
		ArrayList<Object> cataArray = new ArrayList<Object>();

		KnowledgeCatalog catalogJson = treeJson(0);

		cataArray.add(catalogJson);

		return cataArray;
	}

	// 得到当前目录下的所有知识
	@ResponseBody
	@RequestMapping(value = "/library/knowledgeJson", method = RequestMethod.GET)
	public List<Knowledge> knowledgeJson(Locale locale, Model model, @RequestParam int id) {
        
		return knowledgeService.getKnowledgeByCatalogId(id);

	}
	// 根据id得到知识
		@ResponseBody
		@RequestMapping(value = "/library/knowledge", method = RequestMethod.GET)
		public Knowledge getknowledge(Locale locale, Model model, @RequestParam int id) {
	        
			return knowledgeService.getKnowledge(id);

		}
	// 删除知识
	@RequestMapping(value = "/library/delete", method = RequestMethod.GET)
	public String deleteKnowledge(Locale locale, Model model, @RequestParam int id) {
		
		boolean flag = knowledgeService.deleteKnowledge(id);

		return "home/library/index";
	}

	// 删除目录
	@RequestMapping(value = "/library/deleteCatalog", method = RequestMethod.GET)
	public String deleteKnowledgecatlog(Locale locale, Model model, @RequestParam int id) {
        try {
        	boolean flag = knowledgeService.deleteKnowledgeCatalog(id);
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		
     
		return "home/library/index";
	}

	// 新增目录
	@RequestMapping(value = "/library/addCatalog", method = RequestMethod.POST, headers = {
			"content-type=application/json", "content-type=application/xml" })
	@ResponseBody
	public boolean addCatalog(Locale locale, Model model, @RequestBody KnowledgeCatalog knowledgeCatalog) {
		 boolean flag = true;
		try {
        	 flag= knowledgeService.addKnowledgeCatalog(knowledgeCatalog);
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		

		return flag;
	}

	// 新增知识
	@RequestMapping(value = "/library/addKnowledge", method = RequestMethod.POST, headers = {
			"content-type=application/json", "content-type=application/xml" })
	@ResponseBody
	public boolean addKnowledge(Locale locale, Model model, @RequestBody Knowledge knowledge) {
		boolean flag =true;
		try {
	    Date date=new Date();
	   Timestamp timer=new Timestamp(date.getTime());
		knowledge.setCreateUserId(GrantedUser.getCurrent().getId());
		knowledge.setReaded(10);
        knowledge.setCreateTime(timer);

		flag = knowledgeService.addKnowledge(knowledge);

		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return flag;
	}
	// 更新知识
		@RequestMapping(value = "/library/updateKnowledge", method = RequestMethod.POST, headers = {
				"content-type=application/json", "content-type=application/xml" })
		@ResponseBody
		public boolean updateKnowledge(Locale locale, Model model, @RequestBody Knowledge knowledge) {
			boolean flag =true;
			try {
			knowledge.setCreateUserId(1);
			knowledge.setReaded(10);

			flag = knowledgeService.updateKnowledge(knowledge);

			} catch (Exception e) {
				logger.error(e.getMessage());
			}
			return flag;
		}
	// 递归得到只是目录树
	public KnowledgeCatalog treeJson(int id) {

		KnowledgeCatalog catalog = knowledgeService.getKnowledgeCatalog(id);
		List<KnowledgeCatalog> catalogList = knowledgeService.getKnowledgeCatalogByPid(id);
		for (KnowledgeCatalog cata : catalogList) {
			KnowledgeCatalog childCatalog = treeJson(cata.getId());
			catalog.getNodes().add(childCatalog);
		}
		return catalog;

	}
}
