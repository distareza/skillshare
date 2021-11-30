package com.mytutorial.springbootmail.interceptor;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

@Component
public class BookHandleInterceptor implements HandlerInterceptor {

	@Autowired
	private JavaMailSender javaMailSender;
	
	private static final SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		String bookId = request.getParameter("bookId");
		if (bookId != null) {
			System.out.println("preHandle() method sending book access mail...");
			sendEmail(bookId, String.format("Book %s accessed", bookId));
			System.out.println("Done");
		}
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		String bookId = request.getParameter("bookId");
		if (bookId != null) {
			System.out.println("postHandle() method sending book access mail...");
			sendEmail(bookId, String.format("Book %s access complete", bookId));
			System.out.println("Done");
		}		
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		String bookId = request.getParameter("bookId");
		if (bookId != null) {
			System.out.println("Sending book request and response completion mail...");
			sendEmail(bookId, "Request and response complete");
			System.out.println("Done");
		}		
	}
	
	private void sendEmail(String bookId, String messageText) {
		SimpleMailMessage msg = new SimpleMailMessage();
		msg.setFrom("distareza_malaysia@yahoo.com");
		msg.setTo("dista.reza@outlook.com");
		msg.setSubject(String.format("Book related activity for book : %s", bookId));
		msg.setText(messageText + " : " + dateFormat.format(new Date()));
		
		javaMailSender.send(msg);
	}
}
