package com.hzcwtech.wuzhong.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hzcwtech.mybatis.Pager;
import com.hzcwtech.wuzhong.model.User;
import com.hzcwtech.wuzhong.model.mapper.UserMapper;
import com.hzcwtech.wuzhong.service.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserMapper userMapper;

	@Override
	public List<User> getUserList() {
		return userMapper.getUserList();
	}

	@Override
	public User getUserById(int userId) {
		return userMapper.getUserById(userId);
	}

	@Override
	public User getUserByUsername(String username) {
		return userMapper.getUserByUsername(username);
	}

	@Override
	public void updateUser(User user) {
		userMapper.updateUser(user);

	}

	@Override
	public void insertUser(User user) {
		userMapper.insertUser(user);

	}

	@Override
	public boolean changePassword(User user, String passWord, String newPassWord) {
		User user1 = userMapper.getUserByUserNameAndPassWord(user.getUsername(), passWord);
		if (user1 != null) {
			user1.setClearPassword(user.getClearPassword());
			user1.setPassword(newPassWord);
			userMapper.updateUser(user1);
			return true;
		} else {
			return false;
		}

	}

	@Override
	public User getUserByUserNameAndPassWord(String username, String password) {
		return userMapper.getUserByUserNameAndPassWord(username, password);
	}

	@Override
	public List<User> searchUserList(Pager pager, Integer enabled, String q) {
		if (q != null && q.isEmpty()) q = null;
		if (q != null) q = "%" + q + "%";
		return userMapper.searchUserList(pager, enabled, q);
	}

	@Override
	public void deleteUser(Integer userId) {
		
		userMapper.deleteUser(userId);
		
	}

	@Override
	public void updateSign(User user) {
		userMapper.updateSign(user);
		
	}

	@Override
	public void updateAvatar(User user) {
		userMapper.updateAvatar(user);
		
	}

}
