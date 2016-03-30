package com.hzcwtech.wuzhong.service.impl;


import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hzcwtech.wuzhong.model.Learning;
import com.hzcwtech.wuzhong.model.Lesson;
import com.hzcwtech.wuzhong.model.LessonPlan;
import com.hzcwtech.wuzhong.model.Student;
import com.hzcwtech.wuzhong.model.mapper.LearningMapper;
import com.hzcwtech.wuzhong.model.mapper.LessonMapper;
import com.hzcwtech.wuzhong.model.mapper.StudentMapper;
import com.hzcwtech.wuzhong.service.LessonService;

@Service
public class LessonServiceImpl implements LessonService {

	@Autowired
	private LessonMapper lessonMapper;
	@Autowired
	private LearningMapper learningMapper;
	@Autowired
	private StudentMapper studentMapper;
	@Override
	public List<Lesson> getLessonList() {
		
		return lessonMapper.getLessonList();
	}

	@Override
	public Lesson getLesson(int id) {
		
		return lessonMapper.getLesson(id);
	}

	@Override
	@Transactional
	public void insertLesson(Lesson leeson) {
		
		lessonMapper.insertLesson(leeson);
		
		List<Student> students = studentMapper.getStudentsByClassId(leeson.getClassId());
		if(students.size()>0){
		for(Student student:students){
			Learning learning = new Learning();
			learning.setLessonId(leeson.getId());
			learning.setStageId(1);
			learning.setStudentId(student.getUserId());
			learningMapper.insertLearning(learning);	
		}}
		
	}

	
	@Transactional
	public void updateLesson(Lesson lesson,boolean b) {
		lessonMapper.updateLesson(lesson);
		if(!b){
		learningMapper.deleteLearning(lesson.getId());
		List<Student> students = studentMapper.getStudentsByClassId(lesson.getClassId());
		if(students.size()>0){
		for(Student student:students){
			Learning learning = new Learning();
			learning.setLessonId(lesson.getId());
			learning.setStageId(1);
			learning.setStudentId(student.getUserId());
			learningMapper.insertLearning(learning);	
		}
		}}
		
	}

	@Override
	public void deleteLesson(int id) {
		lessonMapper.deleteLesson(id);
		
	}

	@Override
	public List<LessonPlan> getLessonPlanList(int lessonId) {
		
		return lessonMapper.getLessonPlanList(lessonId);
	}


	@Override
	public void insertLessonPlan(LessonPlan lessonPlan) {
		
		lessonMapper.insertLessonPlan(lessonPlan);
		
	}

	@Override
	public void updateLessonPlan(LessonPlan lessonPlan) {
		
        lessonMapper.updateLessonPlan(lessonPlan);
		
	}

	@Override
	public LessonPlan getLessonPlan(int lessonId, int stageId) {
		
		return lessonMapper.getLessonPlan(lessonId, stageId);
	}

	@Override
	public void deleteLessonPlan(int lessonId, int stageId) {
		
		lessonMapper.deleteLessonPlan(lessonId, stageId);
		
	}

	@Override
	public List<Lesson> getIndexLessonList() {
		
		return lessonMapper.getIndexLessonList();
	}

	@Override
	public void updateLesson(Lesson lesson) {
		
		
	}


}
