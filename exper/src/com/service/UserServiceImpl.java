package com.service;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.mapping.UserMapper;
import com.pojo.User;
@Service
public class UserServiceImpl {
	@Resource
	private UserMapper userDao;
	
	public User getUserById(int id){
		return this.userDao.selectByPrimaryKey(id);
	}

	
	public User findUserForLogin(String userName, String pwd) {
		User user = new User();
		user.setUsername(userName);
		user.setPwd(pwd);
		
		return this.userDao.selectByUserNoAndPwd(user);
	}


	
	public List<User> findUserWhereSql(Map params) {
		return this.userDao.selectUserByWhere(params);
	}


	
	public Long findUserCountByWhere(Map params) {
		return this.userDao.selectUserCountByWhere(params);
	}


	
	public int inserUser(User user) {
		return this.userDao.insert(user);
	}


	
	public int updateUser(User user) {
		return this.userDao.updateByPrimaryKey(user);
	}

	
	public int deleteUser(String id) {
		return this.userDao.deleteByPrimaryKey(Integer.parseInt(id));
	}
}
