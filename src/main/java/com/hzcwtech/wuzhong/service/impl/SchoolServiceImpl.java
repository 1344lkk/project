package com.hzcwtech.wuzhong.service.impl;

import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hzcwtech.mybatis.Pager;
import com.hzcwtech.wuzhong.model.School;
import com.hzcwtech.wuzhong.model.mapper.SchoolMapper;
import com.hzcwtech.wuzhong.service.SchoolService;

@Service
public class SchoolServiceImpl implements SchoolService {

	@Autowired
	private SchoolMapper schoolMapper;

	@Override
	public List<School> getSchoolList() {
		return schoolMapper.getSchoolList();
	}

	@Override
	public School getSchoolById(int schoolId) {
		return schoolMapper.getSchoolById(schoolId);
	}

	@Override
	public List<School> searchSchoolList(Pager pager, String q) {
		if (q != null && q.isEmpty())
			q = null;
		if (q != null)
			q = "%" + q + "%";
		return schoolMapper.searchSchoolList(pager, q);
	}

	@Override
	@Transactional
	public void insertSchool(School school) {
		schoolMapper.insertSchool(school);

	}

	@Override
	@Transactional
	public void updateSchool(School school) {
		schoolMapper.updateSchool(school);
	}

	@Override
	@Transactional
	public void deleteSchool(int schoolId) {
		schoolMapper.deleteSchool(schoolId);

	}

	@Override
	@Transactional
	public void insertSchoolsForExcel2003(InputStream is) throws IOException, ParseException {
		HSSFWorkbook hssfWorkbook = new HSSFWorkbook(is);
		School school;
		for (int numSheet = 0; numSheet < hssfWorkbook.getNumberOfSheets(); numSheet++) {
			HSSFSheet hssfSheet = hssfWorkbook.getSheetAt(numSheet);
			if (hssfSheet == null) {
				continue;
			}
			// 循环行Row
			for (int rowNum = 1; rowNum <= hssfSheet.getLastRowNum(); rowNum++) {
				school = new School();
				HSSFRow hssfRow = hssfSheet.getRow(rowNum);
				for (int i = 0; i < hssfRow.getLastCellNum(); i++) {
					HSSFCell schoolHSSFCell = hssfRow.getCell(i);
					schoolHSSFCell.setCellType(Cell.CELL_TYPE_STRING);
					switch (i) {
					case 0:

						school.setRegionId(Long.valueOf(schoolHSSFCell.getStringCellValue()));

						break;
					case 1:

						school.setName(schoolHSSFCell.getStringCellValue());

						break;
					case 2:

						school.setNote(schoolHSSFCell.getStringCellValue());

						break;
					case 3:

						school.setLat(Integer.parseInt(schoolHSSFCell.getStringCellValue()));

						break;
					case 4:

						school.setLng(Integer.parseInt(schoolHSSFCell.getStringCellValue()));

						break;

					default:
						break;
					}
				}

				schoolMapper.insertSchool(school);
			}
		}

	}

	@Override
	@Transactional
	public void insertSchoolsForExcel2007(InputStream is) throws IOException, ParseException {

		XSSFWorkbook xkb = new XSSFWorkbook(is);
		School school;
		for (int numSheet = 0; numSheet < xkb.getNumberOfSheets(); numSheet++) {
			XSSFSheet xssfSheet = xkb.getSheetAt(numSheet);
			if (xssfSheet == null) {
				continue;
			}
			// 循环行Row
			for (int rowNum = 1; rowNum <= xssfSheet.getLastRowNum(); rowNum++) {
				school = new School();
				XSSFRow hssfRow = xssfSheet.getRow(rowNum);
				for (int i = 0; i < hssfRow.getLastCellNum(); i++) {
					XSSFCell schoolXSSFCell = hssfRow.getCell(i);
					schoolXSSFCell.setCellType(Cell.CELL_TYPE_STRING);
					switch (i) {
					case 0:

						school.setRegionId(Long.valueOf(schoolXSSFCell.getStringCellValue()));

						break;
					case 1:

						school.setName(schoolXSSFCell.getStringCellValue());

						break;
					case 2:

						school.setNote(schoolXSSFCell.getStringCellValue());

						break;
					case 3:

						school.setLat(Integer.parseInt(schoolXSSFCell.getStringCellValue()));

						break;
					case 4:

						school.setLng(Integer.parseInt(schoolXSSFCell.getStringCellValue()));

						break;

					default:
						break;
					}
				}

				schoolMapper.insertSchool(school);
			}
		}

	}

}
