package com.hzcwtech.wuzhong.service.impl;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hzcwtech.mybatis.Pager;
import com.hzcwtech.wuzhong.model.Teacher;
import com.hzcwtech.wuzhong.model.User;
import com.hzcwtech.wuzhong.model.mapper.TeacherMapper;
import com.hzcwtech.wuzhong.model.mapper.UserMapper;
import com.hzcwtech.wuzhong.service.TeacherService;
import com.hzcwtech.wuzhong.util.RandomStringGenerator;

@Service
public class TeacherServiceImpl implements TeacherService {

	@Autowired
	private TeacherMapper teacherMapper;
	@Autowired
	private UserMapper userMapper;

	@Override
	public List<Teacher> getTeachers() {
		// TODO Auto-generated method stub
		return teacherMapper.getTeachers();
	}

	@Override
	@Transactional
	public void insertTeacher(Teacher teacher) {
		// TODO Auto-generated method stub
		User user = teacher.getUser();
		user.setRole(User.ROLE_TEACHER);
		user.setCreateTime(new Timestamp(System.currentTimeMillis()));
		userMapper.insertUser(user);
		teacher.setUserId(user.getId());
		teacherMapper.insertTeacher(teacher);

	}

	@Override
	@Transactional
	public void updateTeacher(Teacher teacher) {
		// TODO Auto-generated method stub
		User user = teacher.getUser();
		userMapper.updateUser(user);
		teacherMapper.updateTeacher(teacher);

	}

	@Override
	@Transactional
	public void deleteTeacherById(Integer id) {
		// TODO Auto-generated method stub
		teacherMapper.deleteTeacherById(id);
		userMapper.deleteUser(id);

	}

	@Override
	@Transactional
	public void insertTeachers(InputStream is, String fileName, Integer createrId) throws IOException, ParseException {
		Teacher teacher;
		User user;
		Workbook workBook;
		if (fileName.endsWith("xlsx")) {
			workBook = new XSSFWorkbook(is);
		} else if (fileName.endsWith("xls")) {
			workBook = new HSSFWorkbook(is);
			for (int numSheet = 0; numSheet < workBook.getNumberOfSheets(); numSheet++) {

				Sheet sheet = workBook.getSheetAt(numSheet);
				if (sheet == null) {
					continue;
				}
				// 循环行Row
				for (int rowNum = 1; rowNum <= sheet.getLastRowNum(); rowNum++) {
					teacher = new Teacher();
					user = new User();
					user.setRole(User.ROLE_TEACHER);
					user.setCreateTime(new Timestamp(System.currentTimeMillis()));
					user.setCreateUserId(createrId);
					String randomPassword = RandomStringGenerator.getRandomStringByLength(6);
					String sha1Password =DigestUtils.sha1Hex(randomPassword);
					user.setPassword(sha1Password);
					user.setClearPassword(randomPassword);
					Row row = sheet.getRow(rowNum);
					for (int i = 0; i < row.getLastCellNum(); i++) {
						Cell cell = row.getCell(i);

						switch (i) {
						case 0:
							cell.setCellType(Cell.CELL_TYPE_STRING);
							user.setUsername(cell.getStringCellValue());
							break;
						case 1:
							cell.setCellType(Cell.CELL_TYPE_STRING);
							user.setTruename(cell.getStringCellValue());
							break;
						case 2:
							cell.setCellType(Cell.CELL_TYPE_STRING);
							user.setSex(Integer.parseInt(cell.getStringCellValue()));
							break;
						case 3:
							SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
							Date birthday = cell.getDateCellValue();
							String str_birthday = sdf.format(birthday);
							user.setBirthday(Timestamp.valueOf(str_birthday));
							break;
						case 4:
							cell.setCellType(Cell.CELL_TYPE_STRING);
							user.setNickname((cell.getStringCellValue()));
							break;
						case 5:
							cell.setCellType(Cell.CELL_TYPE_STRING);
							teacher.setNote((cell.getStringCellValue()));
							break;

						default:
							break;
						}

					}
					userMapper.insertUser(user);
					teacher.setUserId(user.getId());
					teacherMapper.insertTeacher(teacher);

				}
			}
		}

	}

	@Override
	public Teacher getTeacherByUserId(Integer userId) {

		return teacherMapper.getTeacherByUserId(userId);

	}

	@Override
	public List<Teacher> searchTeacherList(Pager pager, String q) {
		if (q != null && q.isEmpty())
			q = null;
		if (q != null)
			q = "%" + q + "%";
		return teacherMapper.searchTeacherList(pager, q);
	}

}
