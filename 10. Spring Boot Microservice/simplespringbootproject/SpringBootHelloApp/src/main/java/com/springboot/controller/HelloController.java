package com.springboot.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

	@GetMapping("/")
	public String index() {
		return "This is the main page (using a wer deployment)";
	}
	
	@RequestMapping(value = "/welcome", method = RequestMethod.GET)
	public String welcome() {
		return "Welcome to Spring Boot! (using a wer deployment)";
	}
	
	@GetMapping("/hello")
	public String hello() {
		return "Hello Spring Boot! (using a wer deployment)";
	}
}
