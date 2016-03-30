package com.hzcwtech.wuzhong.service;


import com.hzcwtech.wuzhong.model.User;
import com.hzcwtech.wuzhong.model.mapper.UserMapper;

public interface UserService extends UserMapper {

	public boolean changePassword(User user, String passWord, String newPassWord);

	public void updateSign(User user);

	public void updateAvatar(User user);

	

	

}
