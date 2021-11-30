package com.mytutorial.springbootfulldemo.service;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class TestEmailService {

	@Autowired
	private EmailService email;
	
	@Test
	public void testSendEmail() {
		System.out.println("sending email");
		
		email.sendEmail("dista.reza@outlook.com", "testing", "hallo hallo");
		
		System.out.println("done");
		
		assertTrue(true);
	}
}
