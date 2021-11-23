package com.mytutorial.springboot.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "/organization/")
public class WebServiceController {

	@GetMapping("/")
	public String formPage() {
		return "editName";
	}
	
	@ResponseBody
	@GetMapping("/info")
	public String myName(@RequestParam(defaultValue = "Skillsoft") String name) {
		return "<h2> Welcome to Spring Boot at <i>" + name + "</i>! This is an Http Get Request</h2>";
	}
}
