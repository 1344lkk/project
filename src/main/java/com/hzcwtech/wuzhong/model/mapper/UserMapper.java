package com.hzcwtech.wuzhong.model.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.hzcwtech.mybatis.Pager;
import com.hzcwtech.wuzhong.model.User;

public interface UserMapper {

	@Select("SELECT * FROM user")
	public List<User> getUserList();

	@Select("SELECT * FROM user WHERE id = #{userId}")
	public User getUserById(@Param("userId") int userId);

	@Select("SELECT * FROM user WHERE username = #{username}")
	public User getUserByUsername(@Param("username") String username);

	/**
	 * @param user
	 */
	public void updateUser(User user);

	/**
	 * @param user
	 */
	public void insertUser(User user);

	@Select("SELECT * FROM user WHERE username = #{username} AND password=#{password}")
	public User getUserByUserNameAndPassWord(@Param("username") String username, @Param("password") String password);
    
	@Delete("delete from user where id = #{userId}")
	public void deleteUser(Integer userId);
	
	public List<User> searchUserList(@Param("paper")Pager pager,@Param("enabled") Integer enabled, @Param("q") String q);

	public void updateSign(User user);
	
	public void updateAvatar(User user);
}
