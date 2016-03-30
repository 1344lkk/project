package com.hzcwtech.wuzhong.service.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hzcwtech.wuzhong.model.ExcellentWork;
import com.hzcwtech.wuzhong.model.Praise;
import com.alibaba.fastjson.JSONObject;
import com.hzcwtech.wuzhong.model.Work;
import com.hzcwtech.wuzhong.model.WorkComment;
import com.hzcwtech.wuzhong.model.WorkPaper;
import com.hzcwtech.wuzhong.model.WorkPaperAnswer;
import com.hzcwtech.wuzhong.model.WorkPhoto;
import com.hzcwtech.wuzhong.model.mapper.WorkMapper;
import com.hzcwtech.wuzhong.service.WorkService;
import com.hzcwtech.wuzhong.web.security.GrantedUser;
@Service
public class WorkServiceImpl implements WorkService {

	@Autowired
	private WorkMapper workMapper;
	@Override
	public List<Work> getAllWorkList() {
		
		return workMapper.getAllWorkList();
	}

	@Override
	public List<Work> getWorkListByUserId(int userid) {
		
		return workMapper.getWorkListByUserId(userid);
	}

	@Override
	public Work getWork(int workId) {
	
		return workMapper.getWork(workId);
	}

	@Override
	public void deleteWorkPhoto(int workId) {
		workMapper.deleteWorkPhoto(workId);

	}

	@Override
	public void updateWork(Work work) {
		workMapper.updateWork(work);

	}

	@Override
	public void insertWork(Work work) {
		workMapper.insertWork(work);

	}

	@Override
	public List<WorkComment> getCommentListByWorkId(int workId) {
		
		return workMapper.getCommentListByWorkId(workId);
	}

	@Override
	public void insertComment(WorkComment workComment) {
	
		workMapper.insertComment(workComment);

	}

	@Override
	public void updateComment(WorkComment workComment) {
		
		workMapper.updateComment(workComment);

	}

	@Override
	public void deleteComment(int workCommentId) {
		workMapper.deleteComment(workCommentId);

	}

	@Override
	public void insertImge(WorkPhoto workPhoto) {
		
		workMapper.insertImge(workPhoto);

	}
	
	@Override
	public void updateImage(WorkPhoto workPhoto) {
		 
		workMapper.updateImage(workPhoto);
		
	}

	@Override
	public void insertWorkPaperAnswer(WorkPaperAnswer workPaperAnswer) {
		
		workMapper.insertWorkPaperAnswer(workPaperAnswer);
	}

	@Override
	public void updateWorkPaperAnswer(WorkPaperAnswer workPaperAnswer) {
		
		workMapper.updateWorkPaperAnswer(workPaperAnswer);
		
	}

	@Override
	public void insertWorkPaper(WorkPaper workPaper) {
		
		workMapper.insertWorkPaper(workPaper);
		
	}

	@Override
	public void updateWorkPaper(WorkPaper workPaper) {
	
		workMapper.updateWorkPaper(workPaper);
		
	}

	@Override
	public WorkPaper getWorkPaper(int workId) {
	
		return workMapper.getWorkPaper(workId);
	}

	@Override
	@Transactional
	public void insertWorkPhoto(Work work, ArrayList<Object> paths) {
		
		Date date = new Date();
		Timestamp timer = new Timestamp(date.getTime());
		if(work.getId() != null){
			
		}else{
			workMapper.insertWork(work);
			
		}
		
		for (Object path : paths) {
			WorkPhoto workPhoto = new WorkPhoto();
			workPhoto.setImage(path.toString());
			workPhoto.setWorkId(work.getId());
			workPhoto.setCreateTime(timer);
			workMapper.insertImge(workPhoto);
		}
	}

	@Override
	@Transactional
	public void anserWorkPaper(Work work,String answer) {
		Date date = new Date();
		Timestamp timer = new Timestamp(date.getTime());
		if(work.getId()==null){
		workMapper.insertWork(work);}
		WorkPaper workPaper = new WorkPaper();
		workPaper.setWorkId(work.getId());
		workPaper.setDuration(11);
		workPaper.setPoints(0);
		workPaper.setStartTime(timer);
		workPaper.setEndTime(timer);
		workMapper.insertWorkPaper(workPaper);
		String[] answers = answer.split(",");
		for(int i=0;i<answers.length;i++){
			WorkPaperAnswer workPaperAnswer = new  WorkPaperAnswer();
			String[] keyAndValue = answers[i].split("-");
			workPaperAnswer.setAnswer(keyAndValue[1]);
			
			workPaperAnswer.setComment("222");
			workPaperAnswer.setQuestionId(Integer.valueOf(keyAndValue[0]));
			workPaperAnswer.setPaperId(workPaper.getId());
		    
			workMapper.insertWorkPaperAnswer(workPaperAnswer);
			
		}
		int points=0;
		WorkPaper paper =this.gradeScore(workPaper.getId());
		if(paper!=null){
			points =  paper.getPoints();
		}
		workPaper.setPoints(points);
		workMapper.updateWorkPaper(workPaper);
		
	}

	@Override
	public Work getWorkListByStudentIdAndTaskId(int userid, int taskId) {
		
		return workMapper.getWorkListByStudentIdAndTaskId(userid, taskId);
	}

	@Override
	public List<WorkPhoto> getImges(int workId) {
		
		return workMapper.getImges(workId);
	}

	@Override
	public Work getWorkIdByStudentId(int id) {
		
		return workMapper.getWorkIdByStudentId(id);
	}

	@Override
	public WorkPaper gradeScore(int paperId) {
		
		return workMapper.gradeScore(paperId);
	}

	@Override
	public Work getWorkByLearnId(int learnId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void insertPraise(Praise praise) {
		// TODO Auto-generated method stub
		workMapper.insertPraise(praise);
		
	}

	@Override
	public void deletePraise(int workPhotoId, int userId) {
		// TODO Auto-generated method stub
		workMapper.deletePraise(workPhotoId, userId);
	}

	@Override
	public Praise selectPraise(int workId, int userId) {
		// TODO Auto-generated method stub
		return workMapper.selectPraise(workId, userId);
	}
	
	@Override
	@Transactional
	public void cancel(int workId,int userId){
		Work work = workMapper.getWork(workId);
		work.setLikeCount((work.getLikeCount() - 1));
		workMapper.updateWork(work);
		workMapper.deletePraise(workId, userId);
		
	}
	@Override
	@Transactional
	public void increasePraise (int workId,Praise praise) {
		Work work = workMapper.getWork(workId);
		work.setLikeCount((work.getLikeCount() + 1));
		workMapper.updateWork(work);
		praise.setUserId(GrantedUser.getCurrent().getId());
		praise.setNoteId(1);
		praise.setWorId(workId);
		workMapper.insertPraise(praise);
	}

	@Override
	@Transactional
	public int anserWorkPaper(Work work, JSONObject answers) {
		 Iterator<String> it =answers.keySet().iterator(); 
	 	   
	      
	       Date date = new Date();
			Timestamp timer = new Timestamp(date.getTime());
			if(work.getId()==null){
			workMapper.insertWork(work);}
			WorkPaper workPaper = new WorkPaper();
			workPaper.setWorkId(work.getId());
			workPaper.setDuration(11);
			workPaper.setPoints(0);
			workPaper.setStartTime(timer);
			workPaper.setEndTime(timer);
			workMapper.insertWorkPaper(workPaper);
			 while (it.hasNext()) {  
		          String key = it.next().toString();  
		           String value = answers.get(key).toString();
		           WorkPaperAnswer workPaperAnswer = new  WorkPaperAnswer();
					
					workPaperAnswer.setAnswer(value);
					
					workPaperAnswer.setComment("222");
					workPaperAnswer.setQuestionId(Integer.valueOf(key));
					workPaperAnswer.setPaperId(workPaper.getId());
				    
					workMapper.insertWorkPaperAnswer(workPaperAnswer); 
		      }  
			
			
			int points=0;
			WorkPaper paper =this.gradeScore(workPaper.getId());
			if(paper!=null){
				points =  paper.getPoints();
			}
			workPaper.setPoints(points);
			workMapper.updateWorkPaper(workPaper);
		return workPaper.getPoints();
	}

	@Override
	public List<ExcellentWork> getIndexRecentWorkList() {

		return workMapper.getIndexRecentWorkList();
	}

	@Override
	public List<ExcellentWork> getIndexExcellentWorkList(Integer classId) {
		
		return workMapper.getIndexExcellentWorkList(classId);
	}

	@Override
	public String getUserImage(int replyUserId) {
		// TODO Auto-generated method stub
		return workMapper.getUserImage(replyUserId);
	}

	@Override
	public int getCommentCount(int workId) {
		// TODO Auto-generated method stub
		return workMapper.getCommentCount(workId);
	}

	@Override
	public String getWorkTaskName(int taskId) {
		// TODO Auto-generated method stub
		return workMapper.getWorkTaskName(taskId);
	}

	@Override
	public String getWorkStage(int taskId) {
		// TODO Auto-generated method stub
		return workMapper.getWorkStage(taskId);
	}

	@Override
	public String getLessonName(int taskId) {
		// TODO Auto-generated method stub
		return workMapper.getLessonName(taskId);
	}

	
}
