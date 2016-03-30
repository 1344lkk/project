package com.hzcwtech.wuzhong.service.impl;

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

}
