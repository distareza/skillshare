package com.mvc.annualsciencefair.dao;

import com.mvc.annualsciencefair.model.Login;
import com.mvc.annualsciencefair.model.User;

public interface UserDao {

	public boolean register(User User);
	
	public User validateUser(Login login);

	public boolean doesEmailExists(String email);
	
	public boolean doesIdExists(Integer studentId);
	
}
