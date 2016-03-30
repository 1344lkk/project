package com.hzcwtech.wuzhong.service;

import java.util.ArrayList;

import com.hzcwtech.wuzhong.model.Note;
import com.hzcwtech.wuzhong.model.Praise;
import com.hzcwtech.wuzhong.model.mapper.NoteMapper;

public interface NoteService  extends NoteMapper{

	void insertNoteAndImage(Note note, ArrayList<Object> paths);

	void insertPraise(int noteId, int userId);

	

}
