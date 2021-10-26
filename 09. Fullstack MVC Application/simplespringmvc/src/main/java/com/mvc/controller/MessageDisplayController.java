package com.mvc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class MessageDisplayController {

	@RequestMapping(value = "/hi")
	public ModelAndView sayHi(Model model) {		
		model.addAttribute("message", "Hi There");
		return new ModelAndView("index");
	}
	
	@RequestMapping(value = "/begin")
	public ModelAndView beginLearning(Model model) {
		model.addAttribute("message", "Let's begin learning");
		return new ModelAndView("index");
	}

}
