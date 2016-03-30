package com.hzcwtech.wuzhong.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hzcwtech.mybatis.Pager;
import com.hzcwtech.wuzhong.model.Clazz;
import com.hzcwtech.wuzhong.model.School;
import com.hzcwtech.wuzhong.model.mapper.ClazzMapper;
import com.hzcwtech.wuzhong.service.ClazzService;


@Service
public class ClazzServiceImpl implements ClazzService {

	@Autowired
	private ClazzMapper clazzMapper;
	
	@Override
	public List<Clazz> getClazzList() {
		// TODO Auto-generated method stub
		return clazzMapper.getClazzList();
	}

	@Override
	public List<Clazz> getClazzListBySchoolId(Integer schoolId) {
		// TODO Auto-generated method stub
		return clazzMapper.getClazzListBySchoolId(schoolId);
	}

	@Override
	public void insertClazz(Clazz clazz) {
		// TODO Auto-generated method stub
		clazzMapper.insertClazz(clazz);

	}

	@Override
	public void deleteClazzById(Integer id) {
		// TODO Auto-generated method stub
		clazzMapper.deleteClazzById(id);

	}

	@Override
	public void updateClazz(Clazz clazz) {
		// TODO Auto-generated method stub
		clazzMapper.updateClazz(clazz);

	}

	@Override
	public Clazz getClazzByClazzId(Integer id) {
		// TODO Auto-generated method stub
		return clazzMapper.getClazzByClazzId(id);
	}

	@Override
	public List<School> searchClazzList(Pager pager, String q, Integer schoolId) {
		// TODO Auto-generated method stub
		if (q != null && q.isEmpty()) q = null;
		if (q != null) q = "%" + q + "%";
		return clazzMapper.searchClazzList(pager, q,schoolId);
	}

	@Override
	public List<Clazz> updataGetClazzList(Integer classId) {
		// TODO Auto-generated method stub
		return clazzMapper.updataGetClazzList(classId);
	}

}
