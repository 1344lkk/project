package com.hzcwtech.wuzhong.service;

import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;

import com.hzcwtech.wuzhong.model.mapper.TeacherMapper;

public interface TeacherService extends TeacherMapper {

	public void insertTeachers(InputStream is, String fileName, Integer createrId) throws IOException, ParseException;

}
