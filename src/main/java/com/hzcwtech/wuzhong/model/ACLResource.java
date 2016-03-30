package com.hzcwtech.wuzhong.model;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

/**
 * 权限系统-资源
 * 
 * @author Julian
 *
 */
public class ACLResource {

	private Integer id;
	
	private Integer parentId = 0;
	
	/**
	 * 资源代码
	 */
	@NotNull @NotEmpty
	private String code;
	
	@NotNull @NotEmpty
	private String name;
	
	@NotNull
	private String note;
	
	/**
	 * 资源是否启用，未启用时用户不可操作
	 */
	private Boolean enabled = true;
	
	/**
	 * 资源是否需要鉴权，不需要鉴权时，忽略权限设置
	 */
	private Boolean security = true;
	
	/**
	 * 显示索引
	 */
	private Integer sort = 0;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getParentId() {
		return parentId;
	}

	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public Boolean getEnabled() {
		return enabled;
	}

	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}

	public Boolean getSecurity() {
		return security;
	}

	public void setSecurity(Boolean security) {
		this.security = security;
	}

	public Integer getSortx() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}
	
	
}
