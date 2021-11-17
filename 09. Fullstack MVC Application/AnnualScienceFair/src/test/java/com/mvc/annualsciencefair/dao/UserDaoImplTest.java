package com.mvc.annualsciencefair.dao;

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
public class UserDaoImplTest {

	@Autowired
	private UserDao dao;
	
	@Test
	public void testRegister() {
		
		User user = new User();
		user.setStudentId(0);
		user.setStudentName("Alice Morin");
		user.setUniversityName("Harvard");
		user.setProjectArea("Economics");
		user.setEmail("alice@wanderland.com");
		user.setPassword("magicnumber");
		
		dao.register(user);
		assertTrue(true);
	}
	
	@Test
	public void testValidateUser() {
		Login login = new Login();
		login.setStudentId(0);
		login.setPassword("magicnumber");
		
		User user = dao.validateUser(login);
		
		assertTrue(user != null);
		
		login.setPassword("wrongpassword");
		user = dao.validateUser(login);
		
		assertTrue(user == null);
	}
	
	@Test
	public void testDoesEmailExists() {		
		assertTrue(dao.doesEmailExists("alice@wanderland.com"));
		assertFalse(dao.doesEmailExists("alice@holland.com"));
	}
	
	@Test
	public void testDoesStudentIdExists() {
		assertTrue(dao.doesIdExists(0));
		assertFalse(dao.doesIdExists(-1));
	}
	
}
