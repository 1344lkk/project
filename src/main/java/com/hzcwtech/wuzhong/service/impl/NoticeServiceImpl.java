package com.hzcwtech.wuzhong.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.hzcwtech.mybatis.Pager;
import com.hzcwtech.wuzhong.model.Notice;
import com.hzcwtech.wuzhong.model.mapper.NoticeMapper;
import com.hzcwtech.wuzhong.service.NoticeService;
@Service
public class NoticeServiceImpl implements NoticeService {
	

	@Autowired
	private NoticeMapper noticeMapper;
	
	@Override
	public List<Notice> getNotices() {
		
		return noticeMapper.getNoticeList();
	}

	@Override
	public boolean deleteNotice(int id) {
		
		return noticeMapper.deleteNotice(id);
	}

	@Override
	public boolean updateNotice(Notice notice) {
		
		return noticeMapper.updateNotice(notice);
	}

	@Override
	public boolean addNotice(Notice notice) {
		
		return noticeMapper.addNotice(notice);
	}
	
	@Override
	public List<Notice> searchNoticeList(Pager pager, String q,Integer state) {
		if (q != null && q.isEmpty()) q = null;
		if (q != null) q = "%" + q + "%";
		return noticeMapper.searchNoticeList(pager, q,state);
	}

	@Override
	public Notice getNoticeById(int noticeId) {
		
		return noticeMapper.getNoticebyId(noticeId);
	}
	
}
