package com.hzcwtech.wuzhong.service.impl;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hzcwtech.wuzhong.model.Learning;
import com.hzcwtech.wuzhong.model.mapper.LearningMapper;
import com.hzcwtech.wuzhong.service.LearningService;

@Service
public class LearningServiceImpl implements LearningService {

	@Autowired
	private LearningMapper learningMapper;

	@Override
	public Learning getLearingByStudentId(int studentId) {
		return learningMapper.getLearingByStudentId(studentId);
	}

	@Override
	public Learning getLearningByClassId(@Param("classId") int classId) {
		return learningMapper.getLearningByClassId(classId);
	}

	@Override
	public void insertLearning(Learning learning) {
		learningMapper.insertLearning(learning);

	}

	@Override
	public void updateLearning(Learning learning) {
		learningMapper.updateLearning(learning);

	}

	@Override
	public void deleteLearning(int lessonId) {
		
		learningMapper.deleteLearning(lessonId);
		
	}

	@Override
	public Learning getLearingIdByStudentId(int studentId) {
		return learningMapper.getLearingByStudentId(studentId);
	}
}
