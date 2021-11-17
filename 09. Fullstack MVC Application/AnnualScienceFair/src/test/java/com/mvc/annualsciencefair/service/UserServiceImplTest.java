package com.mvc.annualsciencefair.service;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.mvc.annualsciencefair.model.Login;
import com.mvc.annualsciencefair.model.User;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:applicationContext-test.xml"})
public class UserServiceImplTest {

	@Autowired
	private UserService service;
	
	@Test
	public void testRegistration() {
		User user = new User();
		user.setStudentId(1);
		user.setStudentName("Borland Delphi");
		user.setUniversityName("Oxford");
		user.setProjectArea("Computer Science");
		user.setEmail("borland@delphi.com");
		user.setPassword("programminglanguages");
		assertTrue( service.register(user) );
	}
	
	@Test
	public void testValidateUser() {
		Login login = new Login();
		login.setStudentId(1);
		login.setPassword("dotnet");
		
		assertFalse(service.validateUser(login) != null);
		
		login.setPassword("programminglanguages");
		assertTrue(service.validateUser(login) != null);
	}
}
