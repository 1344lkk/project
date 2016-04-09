package com.hzcwtech.wuzhong.model.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.hzcwtech.wuzhong.model.Lesson;
import com.hzcwtech.wuzhong.model.LessonPlan;


public interface LessonMapper {

	
	public List<Lesson> getLessonList();
	
	public Lesson getLesson(@Param("id") int id);
	
	public void insertLesson(Lesson lesson);
	
	public void updateLesson(Lesson lesson);
	
	public void deleteLesson(@Param("id") int id);
	
	public List<Lesson> getIndexLessonList();
	
	/*---------lessonPlan-------*/
	
    public List<LessonPlan> getLessonPlanList(int lessonId);
	
	public LessonPlan getLessonPlan(@Param("lessonId") int lessonId,@Param("stageId") int stageId);
	
	public void insertLessonPlan(LessonPlan lessonPlan);
	
	public void updateLessonPlan(LessonPlan lessonPlan);
	
	public void deleteLessonPlan(@Param("lessonId") int lessonId,@Param("stageId") int stageId);

	public Lesson getLessonByClassId(int classId);
	
}
