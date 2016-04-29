package com.hzcwtech.wuzhong.web.student.controller;

import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import com.alibaba.fastjson.JSONObject;
import com.hzcwtech.wuzhong.model.CourseTask;
import com.hzcwtech.wuzhong.model.Learning;
import com.hzcwtech.wuzhong.model.Note;
import com.hzcwtech.wuzhong.model.NoteComment;
import com.hzcwtech.wuzhong.model.NoteImage;
import com.hzcwtech.wuzhong.model.PageView;
import com.hzcwtech.wuzhong.model.Praise;
import com.hzcwtech.wuzhong.model.Student;
import com.hzcwtech.wuzhong.model.User;
import com.hzcwtech.wuzhong.model.Work;
import com.hzcwtech.wuzhong.model.WorkComment;
import com.hzcwtech.wuzhong.model.WorkPhoto;
import com.hzcwtech.wuzhong.service.CourseService;
import com.hzcwtech.wuzhong.service.LearningService;
import com.hzcwtech.wuzhong.service.NoteService;
import com.hzcwtech.wuzhong.service.StudentService;
import com.hzcwtech.wuzhong.service.UserService;
import com.hzcwtech.wuzhong.service.WorkService;
import com.hzcwtech.wuzhong.util.ErrorMessage;
import com.hzcwtech.wuzhong.util.RandomStringGenerator;
import com.hzcwtech.wuzhong.web.security.GrantedUser;

@Controller
@RequestMapping("/student")

public class StudentIndexController {

	private static final Logger logger = LoggerFactory.getLogger(StudentIndexController.class);
	@Autowired
	private NoteService noteService;
	@Autowired
	private WorkService workService;
	@Autowired
	private StudentService studentService;
	@Autowired
	private CourseService courseService;
	@Autowired
	private LearningService  learningService;
	@Autowired
	private UserService userService;
	
	@RequestMapping(value = "/{userId}", method = RequestMethod.GET)
	public String index(Locale locale, Model model,@PathVariable int userId) {
		Student student = studentService.getStudentByUserId(userId);
		List<Note> notes = noteService.getNoteListByUserId(userId);
		int currentUserId = GrantedUser.getCurrent().getId();
		for (Note note : notes) {
			note.setNoteComments(noteService.getCommentListByNoteId(note.getId()));
			Praise p = noteService.getPraiseByNoteIdAndUserId(note.getId(),currentUserId);
			if(p == null){
				note.setPraiseOrNot(false);			
			}else{
				note.setPraiseOrNot(true);
			}
			
		}
		boolean ifMastor = compare(userId);
		model.addAttribute("user", student);
		model.addAttribute("notes", notes);
		model.addAttribute("currentId",String.valueOf(currentUserId));
		model.addAttribute("curr", currentUserId);
		model.addAttribute("compare",ifMastor);
		int viewCount = studentService.getViewCount(userId);
		List<PageView> viewUsers = studentService.getViewUser(userId);
		model.addAttribute("viewUsers",viewUsers);
		model.addAttribute("viewCount",viewCount);
		if(!ifMastor){
			Date date=new Date();
			Timestamp timer=new Timestamp(date.getTime());
			PageView page = studentService.getPageView(userId, currentUserId);
			if(page == null){
				page = new PageView();
				page.setMasterId(userId);
				page.setVisitorId(currentUserId);
				page.setViewTime(timer);
				
				studentService.insertPageView(page);
			}else{
				page.setViewCount(page.getViewCount()+1);
				page.setViewTime(timer);
				studentService.updatePageView(page);
			}
			
		}
		return "student/index";
	}
	
	//用于比较当前用户和说说的id是否相等
	private boolean compare(int userId){
		if(GrantedUser.getCurrent().getId() == userId){
			return true;
		}else{
			return false;
		}
	}
	
	@ResponseBody
	@RequestMapping(value = "/getNoteComments/{noteId}",method = RequestMethod.GET)
	public ErrorMessage getNoteComments(Model model,@PathVariable int noteId){
		ErrorMessage message = new ErrorMessage();
		try {
			//Note note = noteService.getNote(noteId);
			List<NoteComment> comments = noteService.getCommentListByNoteId(noteId);
			//note.setNoteComments(comments);
			message.setCode(1);
			message.setResult(comments);
		} catch (Exception e) {
			message.setCode(-1);
			e.printStackTrace();
		}
		return message;
	}
	
	@Transactional
	@RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
	public String delete(Model model, @PathVariable int id) {
		int studentId = GrantedUser.getCurrent().getId();
		noteService.deleteNote(id);//删除说说
		noteService.deleteComment(id);//删除说说关联的评论
		noteService.deleteImage(id);//删除说说管理的图片
		return "redirect:/student/"+studentId;
	}
	
	@ResponseBody
	@RequestMapping(value = "/insert", method = RequestMethod.POST)
	public ErrorMessage insert(Model model, @ModelAttribute NoteComment noteComment,int userId){
		ErrorMessage message = new ErrorMessage();
		try {
			noteComment.setReplyUserId(GrantedUser.getCurrent().getId());
			noteComment.setReplyId(1);
			Date date=new Date();
			Timestamp timer=new Timestamp(date.getTime());
			noteComment.setCreateTime(timer);
			noteService.insertComment(noteComment);
			List<NoteComment> comments = noteService.getCommentListByNoteId(noteComment.getNoteId());
			NoteComment realComment = comments.get(comments.size()-1);//获取最后一个
			User createUser = null;
			User replyUser = null;
			Map map = new HashMap();
			if(realComment.getCreateUserId()!= realComment.getReplyUserId()){
				createUser = userService.getUserById(realComment.getCreateUserId());//获取评论创建人
				replyUser = userService.getUserById(realComment.getReplyUserId());//获取评论被回复人				
				map.put("createRole",createUser.getRole());
				map.put("replyRole",replyUser.getRole());
			}
			map.put("comments", realComment);
			map.put("commentCount", comments.size());
			message.setResult(map);
			message.setCode(1);
			
		} catch (Exception e) {
			message.setCode(-1);
			e.printStackTrace();
		}
		return message;

	}

//	@RequestMapping(value = "/insert", method = RequestMethod.POST)
//	public String insert(Model model, @ModelAttribute NoteComment noteComment,int userId){
//		noteComment.setReplyUserId(GrantedUser.getCurrent().getId());
//		noteComment.setReplyId(1);
//		Date date=new Date();
//		Timestamp timer=new Timestamp(date.getTime());
//		noteComment.setCreateTime(timer);
//		noteService.insertComment(noteComment);
//		return "redirect:/student/"+userId;
//		
//	}
	
	@ResponseBody
	@RequestMapping(value = "/like/{noteId}")
	public ErrorMessage like(Model model, @PathVariable int noteId) {
		ErrorMessage message = new ErrorMessage();
		try {
			Note note = noteService.getNote(noteId);
			int userId = GrantedUser.getCurrent().getId();
			Praise praise = noteService.getPraiseByNoteIdAndUserId(noteId,userId);
			Map<Object,Object> noteLike = new HashMap<Object,Object>();
			System.out.println("praise:"+praise);
			if(praise == null){//没有点过赞,就可以点赞
				noteService.insertPraise(noteId,userId);
				note.setLikeCount(note.getLikeCount()+1);
				noteLike.put("praise", true);
			}else{//已经点赞,就取消点赞
				if(note.getLikeCount() <= 0){
					note.setLikeCount(0);
				}else{
					note.setLikeCount((note.getLikeCount() - 1));
				}
				noteService.deletePraise(noteId,userId);
				noteLike.put("praise", false);
			}
			noteService.updateNote(note);
			noteLike.put("likecount",note.getLikeCount());
		    message.setResult(noteLike);
			message.setCode(1);
		} catch (Exception e) {
			message.setCode(-1);
		}
		return message;
	}
	
	
	@RequestMapping(value = "/topNote",method=RequestMethod.GET)
	public String topNote(Model model, int id) {
		Note note = noteService.getNote(id);
		//获取发说说人的id
		int userId = note.getUserId();
		//获取当前的top值
		int top = note.getTop();
		int currentId = GrantedUser.getCurrent().getId();
		if(top == 0){//置顶
			noteService.updateOtherNote(0);
			note.setTop(1);
			noteService.updateNote(note);
		}else{//取消置顶
			note.setTop(0);
			noteService.updateNote(note);
		}
		return "redirect:/student/"+currentId;
	}
	
	//此方法用于判断用户原先是否已经点赞
	private  boolean contain(List<String> list, String str) {
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i).equals(str)) {
				return true;
			}
		}
		return false;
	}
	
	//将list转成字符串
	private String changeForString(List<String> list){
		String str = "";
		for(int i = 0 ; i < list.size() ; i++){
				str += list.get(i)+",";
		}
		return str;
	}


	@ResponseBody
	@RequestMapping(value = "/addNote", method = RequestMethod.POST)
	public ErrorMessage addNote(HttpServletRequest request, @RequestParam(value="file", required=false) MultipartFile[] files,
								@ModelAttribute Note note) throws IllegalStateException, IOException {
		ErrorMessage message = new ErrorMessage();
		int studentId = GrantedUser.getCurrent().getId();
		try {

			ArrayList<Object> paths = new ArrayList<Object>();
			if (files.length > 0) {
				for (int i = 0; i < files.length; i++) {
					MultipartFile file = files[i];
					if (file != null) {
						// 取得当前上传文件的文件名称
						String myFileName = file.getOriginalFilename();
						// 如果名称不为“”,说明该文件存在，否则说明该文件不存在
						if (myFileName.trim() != "") {
							// 重命名上传后的文件名
							String fileName = RandomStringGenerator.getSuffix();
							// 定义上传路径
							String path = request.getServletContext().getRealPath(File.separator);
							paths.add("/upload/note/"+ fileName);
							File fileDir = new File(path +"upload"+File.separator+"note"+File.separator+fileName);
							if (!fileDir.exists()) {
								fileDir.mkdirs();
							}
							file.transferTo(fileDir);
						}
					}

				}

			}
			Date date = new Date();
			Timestamp timer = new Timestamp(date.getTime());
			note.setUserId(studentId);
			note.setTop(0);
			note.setPhotos("");
			note.setCreateTime(timer);
			noteService.insertNoteAndImage(note,paths);
			List images = noteService.getNoteImageByNodeId(note.getId());
			Map map = new HashMap();
			map.put("note", note);
			map.put("image",images);
			message.setResult(map);
			message.setCode(1);
		} catch (Exception e) {
			message.setCode(-1);
			logger.error(e.getMessage());
		}
		return message;
		//"redirect:/student/"+studentId;
	}
	
	@RequestMapping(value = "/{userId}/work", method = RequestMethod.GET)
	public String work(Locale locale, Model model ,@PathVariable int userId) {
		int studentId = GrantedUser.getCurrent().getId();
		Student student = studentService.getStudentByUserId(userId);
		List<Work> works = workService.getWorkListByUserId(userId);
		for (Work work : works) {
			Praise selectPraise = workService.selectPraise(work.getId(), studentId);
			if(selectPraise==null){
				work.setPraiseOrNot(false);
			}else{
				work.setPraiseOrNot(true);
			}
			
			List<WorkComment> workComments=workService.getCommentListByWorkId(work.getId());
			for(WorkComment workComment : workComments){
				workComment.setCreateUserImage(workService.getUserImage(workComment.getCreateUserId()));
				workComment.setReplyUserImage(workService.getUserImage(workComment.getReplayId()));
			}
			work.setWorkComments(workComments);
			work.setPthotos(workService.getImges(work.getId()));
			work.setCount(workService.getCommentCount(work.getId()));
			Work w = workService.getCourseStageTaskByTaskId(work.getTaskId());
 			work.setWorkTaskName(w.getWorkTaskName());
			work.setWorkstage(w.getWorkstage());
			work.setLessonName(w.getLessonName());
		}
		boolean ifMastor = compare(userId);
		model.addAttribute("compare",ifMastor);
		model.addAttribute("user", student);
		model.addAttribute("works", works);
		model.addAttribute("currentId",studentId);
		return "student/work";
	}
	@ResponseBody  
	@RequestMapping(value = "/getStudentWork/{studentId}/{taskId}", method = RequestMethod.GET)
	public Work getwork(Locale locale, Model model,@PathVariable int studentId,@PathVariable int taskId) {
		Work work = workService.getWorkListByStudentIdAndTaskId(studentId, taskId);
		if(work!=null){
		work.setPthotos(workService.getImges(work.getId()));}
		return work;
	}

	@RequestMapping(value = "/work/deleteWork/{id}", method = RequestMethod.GET)
	public String deleteWork(Model model, @PathVariable int id) {
		int studentId = GrantedUser.getCurrent().getId();
		workService.deleteWorkPhoto(id);
		return "redirect:/student/"+studentId+"/work";
	}

	@ResponseBody
	@RequestMapping(value = "/work/like/{id}", method = RequestMethod.POST)
	public Map likeWork(Model model,@ModelAttribute Praise praise, @PathVariable int id) {
		Map<Object,Object> map  = new  HashMap<>();
		try {
			 Boolean flag = true;
			int userId=GrantedUser.getCurrent().getId();
			int workId=id;
			
			Work work = workService.getWork(workId);
			int count = work.getLikeCount();
			Praise selectPraise = workService.selectPraise(workId, userId);
			if(selectPraise!=null){
				
				workService.cancel(workId,  userId);
				count--;
				flag =false;
			}
			else{
				
				workService.increasePraise(workId,praise);
				count++;
				
			}
		 map.put("count", count);
		 map.put("code",1);
		 map.put("flag", flag);
		} catch (Exception e) {
			map.put("code",0);
		}

		return map;
	}

	@RequestMapping(value = "/work/workComment/{userId}", method = RequestMethod.POST)
	public String workComment(Model model, @ModelAttribute WorkComment workComment,@PathVariable int userId){
		workComment.setReplyUserId(GrantedUser.getCurrent().getId());
		workComment.setCreateUserId(GrantedUser.getCurrent().getId());
		//workComment.setReplayId(1);
		 Date date=new Date();
		 Timestamp timer=new Timestamp(date.getTime());
		 workComment.setCreateTime(timer);
		workService.insertComment(workComment);		
		return "redirect:/student/"+ userId+"/work";
	}
	
	@RequestMapping(value="/work/replyComment/{userId}", method = RequestMethod.POST)
	public  String  replayComment(Model model, @ModelAttribute WorkComment workComment,@PathVariable int userId){
		workComment.setReplyUserId(GrantedUser.getCurrent().getId());
		//workComment.setReplayId(id);
		 Date date=new Date();
		 Timestamp timer=new Timestamp(date.getTime());
		 workComment.setCreateTime(timer);
		 workService.insertComment(workComment);
		return "redirect:/student/"+userId+"/work/";		
	}
	
	
	@RequestMapping(value = "/addWork", method = RequestMethod.POST)
	public String addWork(HttpServletRequest request, @RequestParam("file") MultipartFile[] files,
			@ModelAttribute Work work) throws IllegalStateException, IOException {
		try {
			int studentId = GrantedUser.getCurrent().getId();
			Learning learning = learningService.getLearingByStudentId(studentId);
			int learningId = learning.getId();
			ArrayList<Object> paths = new ArrayList<Object>();
			if (files.length > 0) {

				for (int i = 0; i < files.length; i++) {
					MultipartFile file = files[i];
					if (file != null) {
						// 取得当前上传文件的文件名称
						String myFileName = file.getOriginalFilename();
						// 如果名称不为“”,说明该文件存在，否则说明该文件不存在
						if (myFileName.trim() != "") {
							// 重命名上传后的文件名
							String fileName = RandomStringGenerator.getSuffix();
							// 定义上传路径`
							String path = request.getServletContext().getRealPath(File.separator);
							paths.add("/upload/work/"+ fileName);
							File fileDir = new File(path+"upload"+File.separator+"work"+File.separator+fileName);
							if (!fileDir.exists()) {
								fileDir.mkdirs();
							}
							file.transferTo(fileDir);
						}
					}

				}

			}
			if (workService.getWorkListByStudentIdAndTaskId(studentId,work.getTaskId()) != null) {
				work = workService.getWorkListByStudentIdAndTaskId(studentId,work.getTaskId());
	 
			} 
			work.setLearningId(learningId);
			workService.insertWorkPhoto(work,paths);
			
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		}
		return "redirect:/student/"+GrantedUser.getCurrent().getId()+"/work/";
	}
	
	
	@RequestMapping(value = "/{userId}/learning")
	public String newIndex(Model model,@PathVariable int userId) {
		int studentId = GrantedUser.getCurrent().getId();
		Student student = studentService.getStudentByUserId(userId);
		Learning learning = learningService.getLearingByStudentId(userId);
		List<CourseTask> tasks = new ArrayList<CourseTask>();
		if(learning != null){
			tasks = courseService.getCourseTaskList(learning.getLesson().getCourseId());
			model.addAttribute("stageId",learning.getStageId());
			model.addAttribute("courseId",learning.getLesson().getCourseId());	
			
		}else{
			
			model.addAttribute("stageId",null);
			model.addAttribute("courseId",null);	
		}
		
		boolean ifMastor = compare(userId);
		model.addAttribute("compare",ifMastor);
		model.addAttribute("tasks",tasks);
		model.addAttribute("user", student);
	
		return "student/learning";
	}

	@ResponseBody
	@RequestMapping(value = "/answerQuestion", method = RequestMethod.POST)
	public ErrorMessage answerQuestion(Model model, @RequestBody JSONObject answer, @RequestParam("taskId") int taskId) {

		ErrorMessage message = new ErrorMessage();
		message.setCode(1);
		try {
			JSONObject answers = answer.getJSONObject("question");

			int studentId = GrantedUser.getCurrent().getId();
			Learning learning = learningService.getLearingByStudentId(studentId);
			Work work = new Work();
			if (workService.getWorkListByStudentIdAndTaskId(studentId,taskId) != null) {
				work = workService.getWorkListByStudentIdAndTaskId(studentId,taskId);
     
			} else {
				work.setTaskId(taskId);
				work.setLearningId(learning.getId());
			}
			int point = workService.anserWorkPaper(work, answers);
			message.setResult(point);
		} catch (Exception e) {
			message.setCode(0);
			logger.error(e.getMessage());
		}
		return message;
	}
	
	@ResponseBody
	@RequestMapping(value = "/photos/{studentId}", method = RequestMethod.GET)
	public ErrorMessage photos(Model model, @PathVariable("studentId") int userId) {

		ErrorMessage message = new ErrorMessage();
        int likeCount  = 0;
		ArrayList images = new ArrayList();
		try {
			List<Note> notes = noteService.getNoteListByUserId(userId);
			List<Work> works = workService.getWorkListByUserId(userId);
			for (Work work : works) {

				work.setPthotos(workService.getImges(work.getId()));

			}
			if (notes != null) {
				for (Note note : notes) {
					likeCount = likeCount + note.getLikeCount();
					List<NoteImage> photo = note.getNoteImage();
					if (photo != null) {
						for (NoteImage image : photo) {
							images.add(image.getPath());

						}

					}

				}
				if (works != null) {

					for (Work work : works) {
						likeCount = likeCount + work.getLikeCount();
						List<WorkPhoto> photo = work.getPthotos();
						if (photo != null) {
							for (WorkPhoto image : photo) {
								images.add(image.getImage());

							}

						}
					}
				}
			}
			message.setCode(1);
		    Map map = new HashMap();
		    map.put("likeCount", likeCount);
		    if(images.size()>=4){
		    	 map.put("images",images.subList(0, 4));	
		    }else{
		    	map.put("images", images);
		    	
		    }
		   
			message.setResult(map);
		} catch (Exception e) {
			message.setCode(0);
			logger.error(e.getMessage());
		}
		return message;
	}
	
	@RequestMapping(value = "{userId}/class", method = RequestMethod.GET)
	public String classNote(Locale locale, Model model,@PathVariable int userId) {
		int currentUserId = GrantedUser.getCurrent().getId();
		Student student = studentService.getStudentByUserId(userId);
		List<Note> notes = noteService.getClazzNote(userId);
		List<Note> recommendNotes = noteService.recommendClazzNote(userId);
		for (Note note : notes) {
			note.setNoteComments(noteService.getCommentListByNoteId(note.getId()));
		}
		if(recommendNotes.size()>=5){
			model.addAttribute("recommendNotes",recommendNotes.subList(0, 5));
		}
		else {
			model.addAttribute("recommendNotes", recommendNotes);
		}
		model.addAttribute("user", student);
		model.addAttribute("notes", notes);
		model.addAttribute("currentId",String.valueOf(currentUserId));
			
		return "student/class";
	}
    
	@RequestMapping(value="/updateSign",method = RequestMethod.POST)
    public String updateSign(User user,Model model){
		userService.updateSign(user);
    	return "redirect:/student/"+user.getId();
    }
	
	@RequestMapping(value="/updateAvatar",method = RequestMethod.POST)
	public String updateAvatar(HttpServletRequest request,@RequestParam("stuavatar")MultipartFile stuavatar,User user){
		try{
			String avatarPath = null;
			if(stuavatar != null){
				// 取得当前上传文件的文件名称
				String myFileName = stuavatar.getOriginalFilename();
				// 如果名称不为“”,说明该文件存在，否则说明该文件不存在
				if (myFileName.trim() != "") {
					// 重命名上传后的文件名
					String fileName = RandomStringGenerator.getSuffix();
					// 定义上传路径`
					String path = request.getServletContext().getRealPath(File.separator);
					avatarPath ="/upload/stuavatar/"+fileName;
					File fileDir = new File(path+"upload"+File.separator+"stuavatar"+File.separator+fileName);
					if(!fileDir.exists()){
						fileDir.mkdirs();
					}
					stuavatar.transferTo(fileDir);
				}
				user.setAvatar(avatarPath);
				userService.updateAvatar(user);
			}
		}catch(Exception e){
			e.printStackTrace();
			logger.error(e.getMessage());
		}
		return "redirect:/student/"+user.getId();
		
	}
}
