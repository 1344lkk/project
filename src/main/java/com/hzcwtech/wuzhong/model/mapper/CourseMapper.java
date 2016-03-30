package com.hzcwtech.wuzhong.model.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.hzcwtech.mybatis.Pager;
import com.hzcwtech.wuzhong.model.*;

public interface CourseMapper {
	
	/* CourseCategory */
	
	public List<CourseCategory> getCourseCategoryList();
	
	public List<CourseCategory> getChildCourseCategoryList(@Param("parentId") int parentId);
	
	public void insertCourseCategory(CourseCategory category);
	
	public void updateCourseCategory(CourseCategory category);
	
	public void deleteCourseCategory(@Param("id") int categoryId);
	
	
	/* CourseStage */
	@Select("SELECT * FROM course_stage")
	public List<CourseStage> getCourseStageList();
	
	public List<CourseStage> getCourseStageListByLessonId(int lessonId);
	
	public void insertCourseStage(CourseStage stage);
	
	public void updateCourseStage(CourseStage stage);
	
	public void deleteCourseStage(@Param("id") int stageId);
	
	/* Course */
	
	public List<Course> searchCourseList(@Param("pager") Pager pager, @Param("state") Integer state, @Param("q") String q);
	
	public List<Course> getCourseListByCategory(@Param("categoryId") Integer categoryId);
	
	public Course getCourseById(@Param("id") int courseId);
	
	public void insertCourse(Course course);
	
	public void updateCourse(Course course);
	
	public void deleteCourse(@Param("id") int courseId);
	
	public List<Course> getIndexCourseList();
	
	/* Course Task */
	
	public List<CourseTask> getCourseTaskList(@Param("courseId") int courseId);
	
	public List<CourseTask> getCourseTaskByStageId(@Param("courseId") int courseId,@Param("stageId") int stageId);
	
	public CourseTask getCourseTask(@Param("id") int taskId);
	
	public void insertCourseTask(CourseTask task);
	
	public void updateCourseTask(CourseTask task);
	
	public void deleteCourseTask(@Param("id") int taskId);
	
	public List<CourseTask> getCourseTaskByClassId(@Param("classId") int classId);
	
	
	
}
