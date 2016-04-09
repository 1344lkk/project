package com.hzcwtech.wuzhong.service;

import com.hzcwtech.wuzhong.model.Lesson;
import com.hzcwtech.wuzhong.model.mapper.LessonMapper;

public interface LessonService extends LessonMapper{

	void updateLesson(Lesson lesson, boolean b);
	
	 Lesson getLessonByClassId(int classId);

}
