package com.mytutorial.springbootfulldemo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.mytutorial.springbootfulldemo.model.CurrentUserDetails;
import com.mytutorial.springbootfulldemo.model.User;
import com.mytutorial.springbootfulldemo.repository.UserRepository;

@Service
public class UserService implements UserDetailsService {
	
	private UserRepository userRepo;
	
	@Autowired
	public UserService(UserRepository userRepo) {
		this.userRepo = userRepo;
	}
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepo.findByEmail(username);
		if (user == null) {
			throw new UsernameNotFoundException("User can not be found");
		}
		return new CurrentUserDetails(user);
	}
	
	public User findByEmail(String email) {
		return userRepo.findByEmail(email);
	}
	
	public User findByConfirmationToken(String confirmationToken) {
		return userRepo.findByConfirmationToken(confirmationToken);
	}
	
	public void saveUser(User user) {
		userRepo.save(user);
	}

}
