package com.hzcwtech.wuzhong.web.console.controller;

import com.hzcwtech.wuzhong.model.*;
import com.hzcwtech.wuzhong.service.LearningService;
import com.hzcwtech.wuzhong.service.WorkDetailService;
import com.hzcwtech.wuzhong.service.WorkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/4/19.
 */
@Controller("WorkDetailController")

@RequestMapping("/console/workdetail")
public class WorkDetailController {
    @Autowired
    private WorkDetailService workDetailService;
    @Autowired
    private WorkService  workService;
    @Autowired
    private LearningService learningService;

    @ResponseBody
    @RequestMapping("/studentWork/{lessonId}/{courseId}")
    public List<WorkDetail> getStudentWork(Model model, @PathVariable("lessonId") Integer lessonId,@PathVariable("courseId")Integer courseId){
        List<WorkDetail> students =  workDetailService.getStudentByLessonId(lessonId);
        for (WorkDetail w:students){
            List<List<CourseTask>> studentTasks = new  ArrayList<List<CourseTask>>();
            for (int i = 1; i <=5; i++) {
                List<CourseTask> courseTasks =  workDetailService.getTaskAndWorkByCourseId(i,courseId,w.getLearningId());
                studentTasks.add(courseTasks);
            }
            w.setCourseTasks(studentTasks);
        }
        return students;
    }
}
