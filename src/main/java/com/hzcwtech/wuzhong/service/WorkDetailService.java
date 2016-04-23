package com.hzcwtech.wuzhong.service;


import com.hzcwtech.wuzhong.model.CourseTask;
import com.hzcwtech.wuzhong.model.TaskDetail;
import com.hzcwtech.wuzhong.model.WorkDetail;
import com.hzcwtech.wuzhong.model.mapper.WorkDetailMapper;

import java.util.List;

/**
 * Created by Administrator on 2016/4/19.
 */
public interface WorkDetailService extends WorkDetailMapper{
    public List<WorkDetail> getStudentByLessonId(Integer lessonId);

    public List<TaskDetail> getTaskByLessonId(Integer lessonId,Integer stage);

    public List<CourseTask> getTaskAndWorkByCourseId(Integer stageId,Integer courseId,Integer learningId);
}
