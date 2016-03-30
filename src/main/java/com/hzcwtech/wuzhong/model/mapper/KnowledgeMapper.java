package com.hzcwtech.wuzhong.model.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import com.hzcwtech.wuzhong.model.Knowledge;
import com.hzcwtech.wuzhong.model.KnowledgeCatalog;

public interface KnowledgeMapper {
	
	public Knowledge getKnowledgeByKnowledgeId(@Param("id") Integer id);
	
	@Select("SELECT * FROM knowledge WHERE id = #{id}")
	public Knowledge getKnowLedgeByCatglogId(@Param("id") int id);
	
	@Delete("delete  FROM knowledge WHERE id = #{id}")
	public boolean deleteKnowLedgeById(@Param("id") int id);
	
	public boolean updateKnowLedge(Knowledge knowledge);
	
	//根据id查询目录
	@Select("SELECT id,parentId,name as title,sort FROM knowledge_category WHERE id = #{id}")
	public KnowledgeCatalog getCatglogByCatglogId(@Param("id") int id);
	
	//查询所有目录
	@Select("SELECT id,parentId,name as title,sort FROM knowledge_category")
	public List<KnowledgeCatalog> getAllCatglog();
	
	//根据id查询子目录
	@Select("SELECT id,parentId,name as title,sort FROM knowledge_category WHERE parentId = #{pid}")
	public List<KnowledgeCatalog> getCatglogByCatglogPid(@Param("pid") int pid);
	
	@Select("SELECT * FROM knowledge WHERE categoryId = #{id}")
	public List<Knowledge> getKnowledgeByCatalogId(@Param("id") Integer id);
	
	public boolean addCatalog(KnowledgeCatalog knowledgeCatalog);
	
	@Delete("delete  FROM knowledge_category WHERE id = #{id}")
	public boolean deleteCatalogByid(@Param("id")int id);
	
	public boolean addKnowledge(Knowledge knowledge);
}
