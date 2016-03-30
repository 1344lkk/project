package com.hzcwtech.wuzhong.service;

import java.util.List;

import com.hzcwtech.wuzhong.model.Knowledge;
import com.hzcwtech.wuzhong.model.KnowledgeCatalog;

public interface KnowledgeService {
	// 通过id获取知识
	public Knowledge getKnowledge(int id);

	// 通过id删除知识
	public boolean deleteKnowledge(int id);

	// 通过id删除目录
	public boolean deleteKnowledgeCatalog(int id);

	// 通过id得到目录
	public KnowledgeCatalog getKnowledgeCatalog(int id);

	// 得到所有子节点
	public List<KnowledgeCatalog> getKnowledgeCatalogByPid(int pid);

	// 得到所有目录
	public List<KnowledgeCatalog> getAllKnowledgeCatalog();

	// 得到当前目录下的所有知识
	public List<Knowledge> getKnowledgeByCatalogId(int id);

	// 新增目录
	public boolean addKnowledgeCatalog(KnowledgeCatalog knowledgeCatalog);

	// 新增知识
	public boolean addKnowledge(Knowledge knowledge);
	
	//更新知识
	public boolean updateKnowledge(Knowledge knowledge);
}
