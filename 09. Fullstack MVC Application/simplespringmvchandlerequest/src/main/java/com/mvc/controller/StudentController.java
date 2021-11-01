package com.mvc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.mvc.service.StudentManager;

@Controller
public class StudentController {
	
	@Autowired
	private StudentManager studentManager;
	
	@RequestMapping(value = "/university/students", method = RequestMethod.GET)
	public String getStudentsMapping(Model model) {		
		model.addAttribute("students", studentManager.getStudents());		
		return "students_view";
	}
	
	@RequestMapping(value = "/students/{name}")
	public String displayStudent(@PathVariable("name") String name, Model model) {
		String welcomeMessage = String.format("Welcome to %s's home Page", name);
		model.addAttribute("welcomeMessage", welcomeMessage);
		return "student_home_page";
	}

}
