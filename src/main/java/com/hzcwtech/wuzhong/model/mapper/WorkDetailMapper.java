package com.hzcwtech.wuzhong.model.mapper;

import com.hzcwtech.wuzhong.model.CourseTask;
import com.hzcwtech.wuzhong.model.TaskDetail;
import com.hzcwtech.wuzhong.model.WorkDetail;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by Administrator on 2016/4/19.
 */
public interface WorkDetailMapper {
    public List<WorkDetail> getStudentByLessonId(@Param("lessonId") Integer lessonId);

    public List<TaskDetail> getTaskByLessonId(@Param("lessonId") Integer lessonId,@Param("stageId") Integer stageId);

    public List<CourseTask> getTaskAndWorkByCourseId(@Param("stageId") Integer stageId,@Param("courseId") Integer courseId,@Param("learningId")Integer learningId);
}
