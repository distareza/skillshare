package com.mvc.annualsciencefair.service;

import com.mvc.annualsciencefair.model.Login;
import com.mvc.annualsciencefair.model.User;

public interface UserService {

	public boolean register(User user);
	
	public User validateUser(Login login);
	
	public boolean doesEmailExists(String email);
	
	public boolean doesIdExists(Integer studentId);
	
}
