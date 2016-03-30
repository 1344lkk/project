package com.hzcwtech.wuzhong.model.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;

import com.hzcwtech.mybatis.Pager;
import com.hzcwtech.wuzhong.model.Teacher;

public interface TeacherMapper {

	/**
	 * 获取老师列表
	 * 
	 * @return
	 */
	public List<Teacher> getTeachers();

	/**
	 * 添加老师
	 * 
	 * @param teacher
	 */
	public void insertTeacher(Teacher teacher);

	/**
	 * 根性老师信息
	 * 
	 * @param teacher
	 */
	public void updateTeacher(Teacher teacher);

	/**
	 * 删除老师
	 * 
	 * @param id
	 */
	@Delete("DELETE FROM teacher WHERE userId=#{userId}")
	public void deleteTeacherById(@Param("userId") Integer userId);

	public Teacher getTeacherByUserId(@Param("userId") Integer userId);

	/**
	 * 搜索老师
	 * 
	 * @param pager
	 * @param q
	 * @return
	 */
	public List<Teacher> searchTeacherList(@Param("pager") Pager pager, @Param("q") String q);

}
