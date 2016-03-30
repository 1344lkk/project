package com.hzcwtech.wuzhong.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hzcwtech.wuzhong.model.Knowledge;
import com.hzcwtech.wuzhong.model.KnowledgeCatalog;
import com.hzcwtech.wuzhong.model.mapper.KnowledgeMapper;
import com.hzcwtech.wuzhong.service.KnowledgeService;

@Service
public class KnowledgeServiceImpl implements KnowledgeService {
	@Autowired
	private KnowledgeMapper knowledgeMapper;

	@Override
	public Knowledge  getKnowledge(int id) {

		return knowledgeMapper.getKnowledgeByKnowledgeId(id);
	}

	@Override
	public boolean deleteKnowledge(int id) {

		return knowledgeMapper.deleteKnowLedgeById(id);
	}

	// 根据id获取目录
	public KnowledgeCatalog getKnowledgeCatalog(int id) {

		return knowledgeMapper.getCatglogByCatglogId(id);
	}

	// 当前目录的所有子节点
	public List<KnowledgeCatalog> getKnowledgeCatalogByPid(int pid) {

		return knowledgeMapper.getCatglogByCatglogPid(pid);
	}

	@Override
	public List<KnowledgeCatalog> getAllKnowledgeCatalog() {

		return knowledgeMapper.getAllCatglog();
	}

	@Override
	public List<Knowledge> getKnowledgeByCatalogId(int id) {

		return knowledgeMapper.getKnowledgeByCatalogId(id);
	}

	@Override
	public boolean addKnowledgeCatalog(KnowledgeCatalog knowledgeCatalog) {

		return knowledgeMapper.addCatalog(knowledgeCatalog);
	}

	@Override
	public boolean deleteKnowledgeCatalog(int id) {

		return knowledgeMapper.deleteCatalogByid(id);
	}

	@Override
	public boolean addKnowledge(Knowledge knowledge) {

		return knowledgeMapper.addKnowledge(knowledge);
	}

	@Override
	public boolean updateKnowledge(Knowledge knowledge) {
		
		return knowledgeMapper.updateKnowLedge(knowledge);
	}
}
