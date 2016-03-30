package com.hzcwtech.wuzhong.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hzcwtech.wuzhong.model.Note;
import com.hzcwtech.wuzhong.model.NoteComment;
import com.hzcwtech.wuzhong.model.NoteImage;
import com.hzcwtech.wuzhong.model.Praise;
import com.hzcwtech.wuzhong.model.mapper.NoteMapper;
import com.hzcwtech.wuzhong.service.NoteService;
@Service
public class NoteServiceImpl implements NoteService {

	@Autowired
	private NoteMapper noteMapper;
	@Override
	public List<Note> getAllNoteList() {
		
		return noteMapper.getAllNoteList();
	}

	@Override
	public List<Note> getNoteListByUserId(int userid) {
		
		return noteMapper.getNoteListByUserId(userid);
	}

	@Override
	public void deleteNote(int noteId) {
	
		noteMapper.deleteNote(noteId);

	}

	@Override
	public void updateNote(Note note) {
	
		noteMapper.updateNote(note);

	}

	@Override
	public void insertNote(Note note) {
	
		noteMapper.insertNote(note);

	}

	@Override
	public List<NoteComment> getCommentListByNoteId(int noteId) {
		
		return noteMapper.getCommentListByNoteId(noteId);
	}

	@Override
	public void insertComment(NoteComment noteComment) {
	
		noteMapper.insertComment(noteComment);
	}

	@Override
	public void updateComment(NoteComment noteComment) {
		
		noteMapper.updateComment(noteComment);

	}

	@Override
	public void deleteComment(int noteCommentId) {
		
		noteMapper.deleteComment(noteCommentId);

	}

	@Override
	public void insertImge(NoteImage noteImge) {
		
		noteMapper.insertImge(noteImge);
		
	}

	@Override
	public Note getNote(int noteId) {
	   return  noteMapper.getNote(noteId);
	}

	@Override
	public void updateOtherNote(int top) {
		noteMapper.updateOtherNote(top);
		
	}

	@Override
	public void deleteImage(int noteId) {
		noteMapper.deleteImage(noteId);
		
	}

	@Override
	@Transactional
	public void insertNoteAndImage(Note note, ArrayList<Object> paths) {
		noteMapper.insertNote(note);
		for (Object path : paths) {
			NoteImage noteImge = new NoteImage();
			noteImge.setNoteId(note.getId());
			noteImge.setPath(path.toString());
			noteMapper.insertImge(noteImge);
		}
		
	}

	@Override
	@Transactional
	public void insertPraise(int noteId, int userId) {
		noteMapper.insertPraise(noteId,userId);
		
	}

	@Override
	public Praise getPraiseByNoteIdAndUserId(int noteId, int userId) {
		return noteMapper.getPraiseByNoteIdAndUserId(noteId, userId);
	}

	@Override
	public void deletePraise(int noteId, int userId) {
		noteMapper.deletePraise(noteId, userId);
	}

     
	
	@Override
	public List<Note> getClazzNote(int userId) {
		// TODO Auto-generated method stub
		return noteMapper.getClazzNote(userId);
	}

	@Override
	public List<Note> recommendClazzNote(int userId) {
		// TODO Auto-generated method stub
		return noteMapper.recommendClazzNote(userId);
	}

}
