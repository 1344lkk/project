package com.hzcwtech.wuzhong.model;

import java.util.ArrayList;
import java.util.List;

public class KnowledgeCatalog {

	private int id;
	
	private String title;
	
	private int parentId;
	
	private int sort;
	
	private List<KnowledgeCatalog> nodes = new ArrayList<KnowledgeCatalog>();

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public int getParentId() {
		return parentId;
	}

	public void setParentId(int parentId) {
		this.parentId = parentId;
	}
   
	public int getSort() {
		return sort;
	}

	public void setSort(int sort) {
		this.sort = sort;
	}

	public List<KnowledgeCatalog> getNodes() {
		return nodes;
	}

	public void setNodes(List<KnowledgeCatalog> nodes) {
		this.nodes = nodes;
	}
	
	
	
}
