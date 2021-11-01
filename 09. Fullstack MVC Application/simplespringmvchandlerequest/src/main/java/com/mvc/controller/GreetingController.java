package com.mvc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class GreetingController {

	@RequestMapping("/greeting")
	public String greeting(
			@RequestParam("firstName") String name,
			@RequestParam(required = false) String lastName,
			@RequestParam(defaultValue = "English") String major,			
			Model model) {
		String msg = String.format("Hello %s %s, welcome to your major :%s ", name, lastName, major);
		model.addAttribute("message", msg);
		return "greeting_page";
	}
	
}
