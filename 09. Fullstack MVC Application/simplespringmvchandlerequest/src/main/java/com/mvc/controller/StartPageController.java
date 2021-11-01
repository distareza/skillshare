package com.mvc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class StartPageController {

	@RequestMapping("/start")
	public String redirect_start_page() {
		return "start_page";
	}
}
