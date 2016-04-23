package com.hzcwtech.wuzhong.model.mapper;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import com.hzcwtech.wuzhong.model.Learning;
import org.apache.ibatis.annotations.Select;


public interface LearningMapper {
	
	public Learning getLearingByStudentId(@Param("studentId") int studentId);

	public Learning getLearningByClassId(@Param("classId") int classId);
	
	public void insertLearning(Learning learning);
	
	public void updateLearning(Learning learning);
	
	@Delete("delete from learning where lessonId = #{lessonId}")
	public void deleteLearning(int lessonId);

	public Learning getLearingIdByStudentId(@Param("studentId") int studentId);
}
