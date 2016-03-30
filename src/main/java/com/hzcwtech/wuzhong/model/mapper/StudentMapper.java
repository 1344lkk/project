package com.hzcwtech.wuzhong.model.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.scripting.xmltags.WhereSqlNode;

import com.fasterxml.jackson.databind.deser.std.FromStringDeserializer;
import com.hzcwtech.mybatis.Pager;
import com.hzcwtech.wuzhong.model.PageView;
import com.hzcwtech.wuzhong.model.Student;

public interface StudentMapper {

	/**
	 * 获取所有学生信息
	 * 
	 * @return
	 */
	public List<Student> getStudents();

	/**
	 * 根据班级Id获取学生信息
	 * 
	 * @param classId
	 * @return
	 */
	public List<Student> getStudentsByClassId(@Param("classId") Integer classId);
	
	
	public List<Student> getStudentByLessonId(@Param("lessonId") Integer lessonId);
	
	/**
	 * 删除学生信息
	 * 
	 * @param userId
	 */
	@Delete("DELETE FROM student WHERE userId=#{userId}")
	public void deleteStudentByUserId(Integer userId);

	/**
	 * 更新学生信息
	 * 
	 * @param student
	 */
	public void updateStudent(Student student);

	/**
	 * 插入新学生
	 * 
	 * @param student
	 */
	public void insertStudent(Student student);

	/**
	 * 搜索学生信息
	 * 
	 * @param pager
	 * @param q
	 * @return
	 */
	public List<Student> searchStudentList(@Param("pager") Pager pager, @Param("q") String q);

	public Student getStudentByUserId(@Param("userId") Integer userId);
	
	public List<Student> getStudentByTaskId(@Param("lessonId") int lessonId, @Param("taskId") int taskId);
	
	public void insertPageView(PageView page);
	
	public void updatePageView(PageView page);
	
	public int getViewCount(int masterId);
	
	public List<PageView> getViewUser(int masterId);
	
	
	public PageView getPageView(@Param("masterId")int masterId,@Param("visitorId")int visitorId);
	
	@Select("Select name From school_class Where id=#{classID}")
	public String getClassname(int classId);
}
