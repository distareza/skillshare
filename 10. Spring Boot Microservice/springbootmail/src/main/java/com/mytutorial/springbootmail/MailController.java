package com.mytutorial.springbootmail;

import java.io.IOException;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

@Configuration
public class MailController implements CommandLineRunner {

	@Autowired
	private JavaMailSender javaMailSender;
	
	@Override
	public void run(String... args) throws Exception {
		//System.out.println("Sending email");
		//sendEmail();
		//sendEmailWithAttachement();
		//System.out.println("Done.");
	}

	private void sendEmail() {
		SimpleMailMessage msg = new SimpleMailMessage();
		msg.setFrom("distareza_malaysia@yahoo.com");
		msg.setTo("dista.reza@outlook.com");
		msg.setSubject("Email Sent using SpringBoot");
		msg.setText("Hello \n\nWelcome to Spring Boot, easy to send email wasn't it?");
		
		javaMailSender.send(msg);
	}
	
	private void sendEmailWithAttachement() throws MessagingException, IOException {
		MimeMessage mimeMsg = javaMailSender.createMimeMessage();
		MimeMessageHelper mimeMsgHelper = new MimeMessageHelper(mimeMsg, true);
		
		mimeMsgHelper.setFrom("distareza_malaysia@yahoo.com");
		mimeMsgHelper.setTo("dista.reza@outlook.com");
		mimeMsgHelper.setSubject("Here is a cute photo of a dog");
		mimeMsgHelper.setText("<h3>Take a look at the attachement :-) </h3>", true);
		
		mimeMsgHelper.addAttachment("cute_dog.jpg", new ClassPathResource("dog.jpg"));
		
		javaMailSender.send(mimeMsg);		
	}
}
