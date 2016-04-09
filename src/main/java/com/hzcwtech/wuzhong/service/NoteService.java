package com.hzcwtech.wuzhong.service;

import java.util.ArrayList;
import java.util.List;

import com.hzcwtech.wuzhong.model.Note;
import com.hzcwtech.wuzhong.model.Praise;
import com.hzcwtech.wuzhong.model.mapper.NoteMapper;

public interface NoteService  extends NoteMapper{

	void insertNoteAndImage(Note note, ArrayList<Object> paths);

	void insertPraise(int noteId, int userId);

	List getNoteImageByNodeId(Integer id);



}
