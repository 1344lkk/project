package com.hzcwtech.wuzhong.model.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.hzcwtech.mybatis.Pager;
import com.hzcwtech.wuzhong.model.NoticeCategory;

public interface NoticeCategoryMapper {
	//查询公告分类
	@Select("SELECT * FROM notice_category")
	public List<NoticeCategory> getNoticeCategoryList();
    
	//根据公告id查询公告分类
	@Select("SELECT * FROM notice_category WHERE id = #{categoryId}")
	public NoticeCategory getNoticeCategorybyId(int categoryId);
	
	//新增公告分类
	public boolean addNoticeCategory(NoticeCategory noticeCategory);
	
	//更新公告分类
	public boolean updateNoticeCategory(NoticeCategory noticeCategory);
	
	//删除公告分类
	@Delete("DELETE FROM notice_category WHERE id = #{id}")
	public boolean deleteNoticeCategory(int id);
	
	//关键字查询公告分类列表
	public List<NoticeCategory> searchNoticeCategoryList(@Param("pager") Pager pager, @Param("q") String q);
	
	

}
