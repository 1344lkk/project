package com.hzcwtech.wuzhong.service;

import java.util.List;

import com.hzcwtech.mybatis.Pager;
import com.hzcwtech.wuzhong.model.Notice;

public interface NoticeService {
	
	
	/**
	 * 查询公告
	 * @return 公告列表
	 */
	public List<Notice> getNotices();
	
	/**
	 * 根据id删除公告
	 * @param notice
	 * @return true/false
	 */
	public boolean deleteNotice(int id);
	
	/**
	 * 更新公告
	 * @param notice
	 * @return true/false
	 */
	public boolean updateNotice(Notice notice);
	
	/**
	 * 新增公告
	 * @param notice
	 * @return true/false
	 */
	public boolean addNotice(Notice notice);
	

	/**
	 * 通过关键字查询公告
	 * @param pager
	 * @param q
	 * @return
	 */
	public List<Notice> searchNoticeList(Pager pager, String q,Integer state);
	
	
	/**
	 * 通过noticeid获取公告信息
	 * @param noticeId
	 * @return
	 */
	public Notice getNoticeById(int noticeId);
	
	

}
