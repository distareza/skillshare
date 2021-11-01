package com.mvc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HelloPageController {

	@RequestMapping("/hello")
	public String redirect_hello_page() {
		return "hello_page";
	}
}
