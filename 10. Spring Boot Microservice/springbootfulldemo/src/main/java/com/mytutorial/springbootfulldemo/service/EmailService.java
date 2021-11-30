package com.mytutorial.springbootfulldemo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service("emailSeervice")
public class EmailService {

	@Autowired
	private JavaMailSender mailSender;
	
	@Value("${spring.mail.username}")
	private String emailFrom;
	
	@Async
	public void sendEmail(String to, String subject, String message) {
		SimpleMailMessage registrationEmail = new SimpleMailMessage();
		
		registrationEmail.setFrom(emailFrom);
		registrationEmail.setTo(to);
		registrationEmail.setSubject(subject);
		registrationEmail.setText(message);
		
		mailSender.send(registrationEmail);
	}
}
