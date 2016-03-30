package com.hzcwtech.wuzhong.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hzcwtech.mybatis.Pager;
import com.hzcwtech.wuzhong.model.NoticeCategory;
import com.hzcwtech.wuzhong.model.mapper.NoticeCategoryMapper;
import com.hzcwtech.wuzhong.service.NoticeCategoryService;
@Service
public class NoticeCategoryServiceImpl implements NoticeCategoryService {
    @Autowired
    private NoticeCategoryMapper  noticeCategoryMapper;
	@Override
	public List<NoticeCategory> getNoticeCategorys() {
		
		return noticeCategoryMapper.getNoticeCategoryList();
	}

	@Override
	public boolean deleteNoticeCategory(int id) {
		
		return noticeCategoryMapper.deleteNoticeCategory(id);
	}

	@Override
	public boolean updateNoticeCategory(NoticeCategory noticeCategory) {
		
		return noticeCategoryMapper.updateNoticeCategory(noticeCategory);
	}

	@Override
	public boolean addNoticeCategory(NoticeCategory noticeCategory) {
		
		return noticeCategoryMapper.addNoticeCategory(noticeCategory);
	}

	@Override
	public List<NoticeCategory> searchNoticeCategoryList(Pager pager, String q) {
		if (q != null && q.isEmpty()) q = null;
		if (q != null) q = "%" + q + "%";
		return noticeCategoryMapper.searchNoticeCategoryList(pager, q);
		
	}

	@Override
	public NoticeCategory getNoticeCategoryById(int categoryId) {
		
		return noticeCategoryMapper.getNoticeCategorybyId(categoryId);
	}

}
