package com.hzcwtech.wuzhong.service;

import java.util.List;

import com.hzcwtech.mybatis.Pager;
import com.hzcwtech.wuzhong.model.NoticeCategory;

public interface NoticeCategoryService {
	
	
	/**
	 * 查询公告分类
	 * @return 公告分类列表
	 */
	public List<NoticeCategory> getNoticeCategorys();
	
	/**
	 * 根据id删除公告分类
	 * @param id
	 * @return true/false
	 */
	public boolean deleteNoticeCategory(int id);
	
	/**
	 * 更新公告分类
	 * @param noticeCategory
	 * @return true/false
	 */
	public boolean updateNoticeCategory(NoticeCategory noticeCategory);
	
	/**
	 * 新增公告分类
	 * @param noticeCategory
	 * @return true/false
	 */
	public boolean addNoticeCategory(NoticeCategory noticeCategory);
	

	/**
	 * 通过关键字查询公告
	 * @param pager
	 * @param q
	 * @return
	 */
	public List<NoticeCategory> searchNoticeCategoryList(Pager pager, String q);
	
	
	/**
	 * 通过categoryId获取公告分类信息
	 * @param categoryId
	 * @return
	 */
	public NoticeCategory getNoticeCategoryById(int categoryId);
	
	

}
