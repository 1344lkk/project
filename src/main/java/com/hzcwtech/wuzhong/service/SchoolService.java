package com.hzcwtech.wuzhong.service;

import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;

import com.hzcwtech.wuzhong.model.mapper.SchoolMapper;

public interface SchoolService extends SchoolMapper {

	public void insertSchoolsForExcel2003(InputStream is) throws IOException, ParseException;

	public void insertSchoolsForExcel2007(InputStream is) throws IOException, ParseException;

}
