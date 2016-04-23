package com.hzcwtech.wuzhong.model;

import java.util.List;

/**
 * Created by Administrator on 2016/4/19.
 * 记录的是学生五个阶段的作业完成情况
 */
public class TaskDetail {
    private Integer id;
    private Integer taskId;   //任务的id
    private String taskName;  //任务名称
    private Integer stageId;  //记录五个阶段
/*    private boolean accomplishState;//完成状态
    private boolean gradeState;     //评分状态*/

    public void setTaskId(Integer taskId) {
        this.taskId = taskId;
    }

    public Integer getTaskId() {
        return taskId;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setStageId(Integer stageId) {
        this.stageId = stageId;
    }

    public Integer getStageId() {
        return stageId;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getTaskName() {
        return taskName;
    }

}
