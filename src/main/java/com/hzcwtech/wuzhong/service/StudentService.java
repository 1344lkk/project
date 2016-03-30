package com.hzcwtech.wuzhong.service;

import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.util.List;

import javax.servlet.ServletOutputStream;

import com.hzcwtech.wuzhong.model.Student;
import com.hzcwtech.wuzhong.model.User;
import com.hzcwtech.wuzhong.model.mapper.StudentMapper;

public interface StudentService extends StudentMapper {

	/**
	 * 将excel数据存入数据库
	 * 
	 * @param is
	 * @param fileName
	 * @param schoolId
	 * @param classId
	 * @param createrId
	 * @throws IOException
	 * @throws ParseException
	 */
	public void insertStudent(InputStream is, String fileName, Integer classId, Integer createrId)
			throws IOException, ParseException;

	/**
	 * 导出Excel
	 * 
	 * @param students
	 * @param outputStream
	 */
	public void exportStudentListToExcel(String className,List<Student> students, ServletOutputStream outputStream);

	public void addStudent(User user, Integer classId);

}
