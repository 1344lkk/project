package com.hzcwtech.wuzhong.service.impl;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletOutputStream;

import com.hzcwtech.wuzhong.model.*;
import com.hzcwtech.wuzhong.model.mapper.LearningMapper;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hzcwtech.mybatis.Pager;
import com.hzcwtech.wuzhong.model.mapper.StudentMapper;
import com.hzcwtech.wuzhong.model.mapper.UserMapper;
import com.hzcwtech.wuzhong.service.StudentService;
import com.hzcwtech.wuzhong.util.ExportUtil;
import com.hzcwtech.wuzhong.util.RandomStringGenerator;

@Service
public class StudentServiceImpl implements StudentService {

	@Autowired
	private StudentMapper studentMapper;
	@Autowired
	private UserMapper userMapper;
	@Autowired
	private LearningMapper learningMapper;

	@Override
	public List<Student> getStudents() {
		return studentMapper.getStudents();
	}

	@Override
	@Transactional
	public void deleteStudentByUserId(Integer userId) {
		studentMapper.deleteStudentByUserId(userId);
		userMapper.deleteUser(userId);
	}

	@Override
	@Transactional
	public void updateStudent(Student student) {
		User user = student.getUser();
		studentMapper.updateStudent(student);
		userMapper.updateUser(user);

	}

	@Override
	@Transactional
	public void insertStudent(Student student) {
		User user = student.getUser();
		user.setRole(User.ROLE_STUDENT);
		user.setCreateTime(new Timestamp(System.currentTimeMillis()));
		userMapper.insertUser(user);
		student.setUserId(user.getId());
		studentMapper.insertStudent(student);

	}

	@Override
	public List<Student> getStudentsByClassId(Integer classId) {

		return studentMapper.getStudentsByClassId(classId);
	}

	@Override
	@Transactional
	public void insertStudent(InputStream is, String fileName, Integer classId, Integer createUserId)
			throws IOException, ParseException {

		Student student;
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
					student = new Student();
					user = new User();
					user.setStatus("1");
					user.setRole(User.ROLE_STUDENT);
					user.setCreateTime(new Timestamp(System.currentTimeMillis()));
					user.setCreateUserId(createUserId);
					String randomPassword = RandomStringGenerator.getRandomStringByLength(6);
					String sha1Password =DigestUtils.sha1Hex(randomPassword);
					user.setPassword(sha1Password);
					user.setClearPassword(randomPassword);
				//	student = new Student();
					student.setClassId(classId);
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
							user.setNickname((cell.getStringCellValue()));
							break;

						default:
							break;
						}

					}
					userMapper.insertUser(user);
					student.setUserId(user.getId());
					studentMapper.insertStudent(student);

				}
			}
		}

	}

	@Override
	public void exportStudentListToExcel(String className,List<Student> students, ServletOutputStream outputStream) {
		XSSFWorkbook wb = new XSSFWorkbook();
		XSSFSheet sheet = wb.createSheet(className);
		ExportUtil exportUtil = new ExportUtil(wb, sheet);
		XSSFCellStyle headStyle = exportUtil.getHeadStyle();
		XSSFCellStyle bodyStyle = exportUtil.getBodyStyle();

		Student student;
		String[] titles = { "用户名", "密码", "姓名", "性别", "生日", "昵称", "班级", "学校", "入学年份" };
		// 构建表头
		XSSFRow headRow = sheet.createRow(0);
		XSSFCell cell = null;
		for (int i = 0; i < titles.length; i++) {
			cell = headRow.createCell(i);
			cell.setCellStyle(headStyle);
			cell.setCellValue(titles[i]);
		}
		if (students != null && students.size() > 0) {
			for (int j = 0; j < students.size(); j++) {
				XSSFRow bodyRow = sheet.createRow(j + 1);
				student = students.get(j);
				for (int i = 0; i < titles.length; i++) {
					cell = bodyRow.createCell(i);
					cell.setCellStyle(bodyStyle);
					switch (i) {
					case 0:

						cell.setCellValue(student.getUser().getUsername());
						break;
					case 1:

						cell.setCellValue(student.getUser().getClearPassword());
						break;
					case 2:

						cell.setCellValue(student.getUser().getTruename());
						break;
					case 3:
						cell.setCellValue(student.getUser().getSex());
						break;
					case 4:
						cell.setCellValue(new Date());
						XSSFCellStyle bodyDateStyle = exportUtil.getBodyStyle();
						CreationHelper createHelper = wb.getCreationHelper();
						bodyDateStyle.setDataFormat(createHelper.createDataFormat().getFormat("yyyy-MM-dd"));
						cell.setCellStyle(bodyDateStyle);
						cell.setCellValue(student.getUser().getBirthday());
						break;
					case 5:
						cell.setCellValue(student.getUser().getNickname());
						break;
					case 6:
						cell.setCellValue(student.getClazz().getName());
						break;
					case 7:
						cell.setCellValue(student.getClazz().getSchoolName());
						break;
					case 8:
						cell.setCellValue(student.getClazz().getSchoolYear());
						break;

					default:
						break;
					}

				}

			}
		}
		try {
			wb.write(outputStream);
			outputStream.flush();
			outputStream.close();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				outputStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

	@Override
	public List<Student> searchStudentList(Pager pager, String q) {
		if (q != null && q.isEmpty())
			q = null;
		if (q != null)
			q = "%" + q + "%";
		return studentMapper.searchStudentList(pager, q);
	}

	@Override
	public Student getStudentByUserId(Integer userId) {

		return studentMapper.getStudentByUserId(userId);

	}

	@Override
	public List<Student> getStudentByLessonId(Integer lessonId) {
		
		return studentMapper.getStudentByLessonId(lessonId);
	}

	@Override
	public List<Student> getStudentByTaskId(int lessonId, int taskId) {
		
		return studentMapper.getStudentByTaskId(lessonId, taskId);
	}

	@Override
	@Transactional
	public void addStudent(User user, Integer classId) {
		user.setStatus("1");
		user.setRole(4);
		String randomPassword = RandomStringGenerator.getRandomStringByLength(6);
		String sha1Password =DigestUtils.sha1Hex(randomPassword);
		user.setPassword(sha1Password);
		user.setClearPassword(randomPassword);
		userMapper.insertUser(user);
		Learning learning=learningMapper.getLearningByClassId(classId);
		if(learning !=null){
			Learning lInsert=new Learning();
			lInsert.setLessonId(learning.getLessonId());
			lInsert.setStageId(learning.getStageId());
			lInsert.setStudentId(user.getId());
			learningMapper.insertLearning(lInsert);
		}
		Student student = new  Student();
		student.setClassId(classId);
		student.setUserId(user.getId());

		studentMapper.insertStudent(student);
		
	}

	@Override
	public void insertPageView(PageView page) {
		
		studentMapper.insertPageView(page);
		
	}

	@Override
	public int getViewCount(int masterId) {
		
		return studentMapper.getViewCount(masterId);
	}

	@Override
	public PageView getPageView(int masterId, int visitorId) {
		
		return studentMapper.getPageView(masterId, visitorId);
	}

	@Override
	public void updatePageView(PageView page) {
		
		studentMapper.updatePageView(page);
		
	}

	@Override
	public List<PageView> getViewUser(int masterId) {
		
		return studentMapper.getViewUser(masterId);
	}
	
	@Override
	public String getClassname(int classId){
		
		return studentMapper.getClassname(classId);
	}

}
