package com.hzcwtech.wuzhong.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2016/4/19.
 */
public class WorkDetail{
    private Integer id;
    private Integer learningId;
    private Integer studentId;   //学生的id
    private String studentName; //学生名字
    private List<List<CourseTask>> courseTasks;

    public void setLearningId(Integer learningId) {
        this.learningId = learningId;
    }

    public Integer getLearningId() {
        return learningId;
    }

    public void setCourseTasks(List<List<CourseTask>> courseTasks) {
        this.courseTasks = courseTasks;
    }

    public List<List<CourseTask>> getCourseTasks() {
        return courseTasks;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentId(Integer studentId) {
        this.studentId = studentId;
    }

    public Integer getStudentId() {
        return studentId;
    }

}