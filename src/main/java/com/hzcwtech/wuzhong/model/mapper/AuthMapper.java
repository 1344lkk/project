package com.hzcwtech.wuzhong.model.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.hzcwtech.mybatis.Pager;
import com.hzcwtech.wuzhong.model.ACLAuthority;
import com.hzcwtech.wuzhong.model.ACLResource;
import com.hzcwtech.wuzhong.model.ACLRole;

public interface AuthMapper {

	/* 登录成功，记录登录次数、登录时间、登录IP */
	
	public void onAuthenticationSuccess(@Param("userId") int userId, @Param("ip") String ip);
	
	
	public List<ACLRole> getRolesByUserId(@Param("userId") Integer userId);
	
	@Select("SELECT * FROM acl_auth")
	public List<ACLAuthority> getAuthorityList();
	
	public List<ACLRole> getRolesByAuthority(@Param("authId") Integer authId);
	
	
	/* Resource manage */
	
	@Select("SELECT * FROM acl_resource")
	public List<ACLResource> getResourceList();
	
	/** 查询资源列表: 根据code和name模糊查询 */
	public List<ACLResource> searchResourceList(@Param("pager") Pager pager, @Param("q") String q);
	
	@Select("SELECT * FROM acl_resource WHERE id = #{resourceId}")
	public ACLResource getResourceById(int resourceId);
	
	public void insertResource(ACLResource resource);
	
	public void updateResource(ACLResource resource);
	
	@Delete("DELETE FROM acl_resource WHERE id = #{resourceId}")
	public void deleteResource(@Param("resourceId") int resourceId);
	
	/* Role manage */
	
	
	@Select("SELECT * FROM acl_role")
	public List<ACLRole> getRoleList();
	
	public List<ACLRole> searchRoleList(@Param("enabled") Integer enabled, @Param("q") String q);
	
	@Select("SELECT * FROM acl_role WHERE id = #{roleId} ")
	public ACLRole getRoleById(int roleId);
	
	public void insertRole(ACLRole role);
	
	public void updateRole(ACLRole role);
	
	@Delete("DELETE FROM acl_role WHERE id = #{id}")
	public void deleteRole(ACLRole role);
}
