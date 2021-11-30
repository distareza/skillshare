package com.mytutorial.springbootmail.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.mytutorial.springbootmail.interceptor.BookHandleInterceptor;

@Component
public class WebAppConfiguration implements WebMvcConfigurer {

	@Autowired
	private BookHandleInterceptor bookInterceptor;

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(bookInterceptor);
	}	
}

