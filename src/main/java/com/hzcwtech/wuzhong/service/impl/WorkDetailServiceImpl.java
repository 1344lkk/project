package com.hzcwtech.wuzhong.service.impl;

import com.hzcwtech.wuzhong.model.CourseTask;
import com.hzcwtech.wuzhong.model.TaskDetail;
import com.hzcwtech.wuzhong.model.WorkDetail;
import com.hzcwtech.wuzhong.model.mapper.WorkDetailMapper;
import com.hzcwtech.wuzhong.service.WorkDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Administrator on 2016/4/19.
 */
@Service
public class WorkDetailServiceImpl implements WorkDetailService{
    @Autowired
    private WorkDetailMapper workDetailMapper;

    public List<WorkDetail> getStudentByLessonId(Integer lessonId) {
        return workDetailMapper.getStudentByLessonId(lessonId);
    }

    public List<TaskDetail> getTaskByLessonId(Integer lessonId,Integer stageId) {
        return workDetailMapper.getTaskByLessonId(lessonId,stageId);
    }

    @Override
    public List<CourseTask> getTaskAndWorkByCourseId(Integer stageId, Integer courseId, Integer learningId) {
        return workDetailMapper.getTaskAndWorkByCourseId(stageId,courseId,learningId);
    }
}
