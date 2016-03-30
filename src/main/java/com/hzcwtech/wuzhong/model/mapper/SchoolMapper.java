package com.hzcwtech.wuzhong.model.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.hzcwtech.mybatis.Pager;
import com.hzcwtech.wuzhong.model.School;

public interface SchoolMapper {
	
	public List<School> searchSchoolList(@Param("pager") Pager pager, @Param("q") String q);
	
	@Select("SELECT * FROM school")
	public List<School> getSchoolList();

	/** 根据ID获取学校 */
	@Select("SELECT * FROM school WHERE id = #{id}")
	public School getSchoolById(int id);
	
	/** 新建学校*/
	public void insertSchool(School school);
	
	/** 修改学校 */
	public void updateSchool(School school);
	
	/** 删除学校 */
	@Delete("DELETE FROM school WHERE id = #{id}")
	public void deleteSchool(@Param("id") int schoolId);
	
	

}
