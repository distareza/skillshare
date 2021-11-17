package com.mvc.annualsciencefair.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mvc.annualsciencefair.dao.UserDao;
import com.mvc.annualsciencefair.model.Login;
import com.mvc.annualsciencefair.model.User;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserDao userDao;
	
	@Override
	public boolean register(User user) {
		return userDao.register(user);
	}

	@Override
	public User validateUser(Login login) {
		return userDao.validateUser(login);
	}

	@Override
	public boolean doesEmailExists(String email) {
		return userDao.doesEmailExists(email);
	}

	@Override
	public boolean doesIdExists(Integer studentId) {
		return userDao.doesIdExists(studentId);
	}

}
