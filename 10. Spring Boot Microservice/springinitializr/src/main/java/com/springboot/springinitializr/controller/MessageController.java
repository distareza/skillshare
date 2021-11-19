package com.springboot.springinitializr.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MessageController {

	@GetMapping("/home")
	public String index() {
		return "home";
	}
}
