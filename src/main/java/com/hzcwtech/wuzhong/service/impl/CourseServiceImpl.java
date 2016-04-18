package com.hzcwtech.wuzhong.service.impl;

import java.sql.Timestamp;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hzcwtech.mybatis.Pager;
import com.hzcwtech.wuzhong.model.Course;
import com.hzcwtech.wuzhong.model.CourseCategory;
import com.hzcwtech.wuzhong.model.CourseStage;
import com.hzcwtech.wuzhong.model.CourseTask;
import com.hzcwtech.wuzhong.model.mapper.CourseMapper;
import com.hzcwtech.wuzhong.service.CourseService;
import com.hzcwtech.wuzhong.web.security.GrantedUser;

@Service
public class CourseServiceImpl implements CourseService {

	@Autowired
	private CourseMapper courseMapper;
	
	@Override
	public List<CourseCategory> getCourseCategoryList() {
		return courseMapper.getCourseCategoryList();
	}

	@Override
	public List<CourseCategory> getChildCourseCategoryList(int parentId) {
		return courseMapper.getChildCourseCategoryList(parentId);
	}

	@Override @Transactional
	public void insertCourseCategory(CourseCategory category) {
		courseMapper.insertCourseCategory(category);
	}

	@Override @Transactional
	public void updateCourseCategory(CourseCategory category) {
		courseMapper.updateCourseCategory(category);
	}

	@Override @Transactional
	public void deleteCourseCategory(int categoryId) {
		courseMapper.deleteCourseCategory(categoryId);
	}

	@Override
	public List<CourseStage> getCourseStageList() {
		return courseMapper.getCourseStageList();
	}

	@Override @Transactional
	public void insertCourseStage(CourseStage stage) {
		courseMapper.insertCourseStage(stage);
	}

	@Override @Transactional
	public void updateCourseStage(CourseStage stage) {
		courseMapper.updateCourseStage(stage);
	}

	@Override @Transactional
	public void deleteCourseStage(int stageId) {
		courseMapper.deleteCourseStage(stageId);
	}

	@Override
	public List<Course> searchCourseList(Pager pager, Integer state, String q) {
		if (q != null && q.isEmpty()) q = null;
		if (q != null) q = "%" + q + "%";
		return courseMapper.searchCourseList(pager, state, q);
	}

	@Override
	public List<Course> searchCourseListBySort(@Param("grade") int grade, @Param("state") Integer state, @Param("q") String q) {

		if (q != null) q = "%" + q + "%";
		return courseMapper.searchCourseListBySort(grade, state, q);
	}

	@Override
	public List<Course> getCourseListByCategory(Integer categoryId) {
		return courseMapper.getCourseListByCategory(categoryId);
	}

	@Override
	public Course getCourseById(int courseId) {
		return courseMapper.getCourseById(courseId);
	}

	@Override
	public void insertCourse(Course course) {
		course.setCreateUserId(GrantedUser.getCurrent().getId());
		course.setCreateTime(new Timestamp(System.currentTimeMillis()));
		courseMapper.insertCourse(course);
	}

	@Override
	public void updateCourse(Course course) {
		courseMapper.updateCourse(course);
	}

	@Override
	public void deleteCourse(int courseId) {
		courseMapper.deleteCourse(courseId);
	}

	@Override
	public List<CourseTask> getCourseTaskList(int courseId) {
		return courseMapper.getCourseTaskList(courseId);
	}

	@Override
	public CourseTask getCourseTask(int taskId) {
		return courseMapper.getCourseTask(taskId);
	}

	@Override
	public void insertCourseTask(CourseTask task) {
		courseMapper.insertCourseTask(task);
	}

	@Override
	public void updateCourseTask(CourseTask task) {
		courseMapper.updateCourseTask(task);
	}

	@Override
	public void deleteCourseTask(int taskId) {
		courseMapper.deleteCourseTask(taskId);
	}

	@Override
	public List<CourseStage> getCourseStageListByLessonId(int lessonId) {
		
		return courseMapper.getCourseStageListByLessonId(lessonId);
	}

	@Override
	public List<CourseTask> getCourseTaskByStageId(int courseId, int stageId) {
		
		return courseMapper.getCourseTaskByStageId(courseId, stageId);
	}

	@Override
	public List<Course> getIndexCourseList() {
		
		return courseMapper.getIndexCourseList();
	}

	@Override
	public List<CourseTask> getCourseTaskByClassId(int classId) {
		
		return courseMapper.getCourseTaskByClassId(classId);
	}

	

}
