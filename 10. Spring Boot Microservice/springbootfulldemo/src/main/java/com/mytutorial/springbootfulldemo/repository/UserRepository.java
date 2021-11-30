package com.mytutorial.springbootfulldemo.repository;

import org.springframework.data.repository.CrudRepository;

import com.mytutorial.springbootfulldemo.model.User;

public interface UserRepository extends CrudRepository<User, Long>{

	public User findByEmail(String email);
	
	public User findByConfirmationToken(String confirmationToken);
	
}
