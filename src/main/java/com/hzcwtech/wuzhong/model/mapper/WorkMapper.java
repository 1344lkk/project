package com.hzcwtech.wuzhong.model.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.scripting.xmltags.WhereSqlNode;

import com.fasterxml.jackson.databind.deser.std.FromStringDeserializer;
import com.hzcwtech.wuzhong.model.ExcellentWork;
import com.hzcwtech.wuzhong.model.Praise;
import com.hzcwtech.wuzhong.model.Work;
import com.hzcwtech.wuzhong.model.WorkComment;
import com.hzcwtech.wuzhong.model.WorkPaper;
import com.hzcwtech.wuzhong.model.WorkPaperAnswer;
import com.hzcwtech.wuzhong.model.WorkPhoto;

public interface WorkMapper {
	
	/*------作业------*/
	@Select("select * from work")
	public List<Work> getAllWorkList();
	
	public Work getWorkIdByStudentId(int id);
	
	public WorkPaper gradeScore(int paperId);
	
	public List<Work> getWorkListByUserId(int userid);
	
	public Work getWorkListByStudentIdAndTaskId(@Param("studentId")int studentId,@Param("taskId")int taskId);
	
	@Select("SELECT * FROM work WHERE id = #{workId}")
	public Work getWork(int workId);
	
	@Select("SELECT * FROM work WHERE learningId = #{learnId}")
	public Work getWorkByLearnId(int learnId);
	
	public void deleteWorkPhoto(int workId);
	
	public void updateWork(Work work);
	
	public void insertWork(Work work);
	
    public List<ExcellentWork> getIndexRecentWorkList();
    
    public List<ExcellentWork> getIndexExcellentWorkList(@Param("classId")Integer classId);

	public List<Work> getWorkByStudentId(@Param("studentId") Integer studentId);
	
	/*-------作业评论--------*/
	
	public List<WorkComment> getCommentListByWorkId(int workId);
	
	public void insertComment(WorkComment workComment);
	
	public void updateComment(WorkComment workComment);
	
	@Delete("DELETE FROM work_comment where id = #{workCommentId}")
	public void deleteComment(int workCommentId);
	
	/*-------作业图片--------*/
	public List<WorkPhoto> getImges(int workId);
	
	public void insertImge(WorkPhoto workPhoto);
	
	public void updateImage(WorkPhoto workPhoto);
	
	/*-------作业试题--------*/
  
	public void insertWorkPaperAnswer(WorkPaperAnswer workPaperAnswer);
	
	public void updateWorkPaperAnswer(WorkPaperAnswer workPaperAnswer);
	
    public void insertWorkPaper(WorkPaper workPaper);
	
	public void updateWorkPaper(WorkPaper workPaper);

	public WorkPaper getWorkPaper(int workId);
	
	
	/*-----------作业点赞-----------*/
	
	public void insertPraise(Praise praise);
	
	public void deletePraise(@Param("workId")int workId,@Param("userId") int userId);
	
	public Praise selectPraise(@Param("workId")int workId,@Param("userId") int userId);
	
	/*---------获取用户图片-----------*/
	
	@Select("Select avatar From user where id=#{replyUserId}")
	public String getUserImage(int replyUserId);
	
	/*------------获取评论的条数--------*/
	@Select ("Select count(*) from work_comment where workId=#{workId}")
	public int getCommentCount(int workId);
	
	/*-----------获取用户成果当前任务名------------*/
	@Select("select task.name from task,work where work.id=#{taskId} and work.taskId=task.id ")
	public String  getWorkTaskName(int taskId);
	
	/*-----------获取任务阶段名-------------*/
	@Select("select course_stage.name from course_stage,task,work where work.id=#{taskId}  AND work.taskId=task.id AND task.stageID=course_stage.id")
	public String  getWorkStage(int taskId);
	
	/*-------------获取课程名称------------*/
	@Select("select lesson.name From lesson,task,work where work.id=#{taskId} and work.taskId=task.id and task.courseId=lesson.courseId")
	public String  getLessonName(int taskId);

	/*-------获取当前学生成果的阶段，课程名，任务名------------*/
	public Work getCourseStageTaskByTaskId(@Param("taskId") int taskId);
}
