package com.hzcwtech.wuzhong.model.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.hzcwtech.wuzhong.model.Note;
import com.hzcwtech.wuzhong.model.NoteComment;
import com.hzcwtech.wuzhong.model.NoteImage;
import com.hzcwtech.wuzhong.model.Praise;

public interface NoteMapper {
	
	/*------说说------*/
	@Select("select * from note")
	public List<Note> getAllNoteList();
	
	public List<Note> getNoteListByUserId(int userid);
	
	@Select("SELECT * FROM note WHERE id = #{noteId}")
	public Note getNote(int noteId);
	
	public void deleteNote(int noteId);
	
	public void updateNote(Note note);
	
	public void insertNote(Note note);
	
	public void updateOtherNote(int top);
	
	
	/*-------说说评论--------*/
	
	public List<NoteComment> getCommentListByNoteId(int noteId);
	
	public void insertComment(NoteComment noteComment);
	
	public void updateComment(NoteComment noteComment);
	
/*	@Delete("DELETE FROM note_comment WHERE id = #{noteCommentId}")
	public void deleteComment(int noteCommentId);*/
	
	@Delete("DELETE FROM note_comment WHERE noteId = #{noteId}")
	public void deleteComment(int noteId);
	
	/*---------说说点赞-------------*/
	public void insertPraise(@Param("noteId")int noteId, @Param("userId")int userId);
	public Praise getPraiseByNoteIdAndUserId(@Param("noteId")int noteId, @Param("userId")int userId);
	public void deletePraise(@Param("noteId")int noteId, @Param("userId")int userId);
	
	/*-------说说图片--------*/
	
	public void insertImge(NoteImage noteImge);
	
	@Delete("DELETE FROM note_image WHERE noteId = #{noteId}")
	public void deleteImage(int noteId);
	
	/*------获取班级动态-------------*/
	public List<Note> getClazzNote(int userId);
	
	public List<Note> recommendClazzNote(int userId);
	

}
