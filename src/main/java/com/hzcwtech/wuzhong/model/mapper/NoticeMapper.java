package com.hzcwtech.wuzhong.model.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.hzcwtech.mybatis.Pager;
import com.hzcwtech.wuzhong.model.Notice;

public interface NoticeMapper {
	//查询公告
	@Select("SELECT * FROM notice")
	public List<Notice> getNoticeList();
    
	//根据公告id查询公告
	@Select("SELECT * FROM notice WHERE id = #{noticeId}")
	public Notice getNoticebyId(int noticeId);
	
	//新增公告
	public boolean addNotice(Notice notice);
	
	//更新公告
	public boolean updateNotice(Notice notice);
	
	//删除公告
	@Delete("DELETE FROM notice WHERE id = #{id}")
	public boolean deleteNotice(int id);
	
	//
	public List<Notice> searchNoticeList(@Param("pager") Pager pager, @Param("q") String q,@Param("state") Integer state);
	
	

}
