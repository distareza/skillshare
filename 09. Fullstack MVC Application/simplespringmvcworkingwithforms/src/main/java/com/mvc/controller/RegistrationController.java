package com.mvc.controller;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.mvc.model.Registration;

@Controller
@RequestMapping("/details")
public class RegistrationController {

	@RequestMapping(value = "/registration", method = RequestMethod.GET)
	public String registrationForm(Model model) {
		Registration registration = new Registration();
		model.addAttribute("registration", registration);
		
		List<String> departmentList = new ArrayList<>();
		departmentList.add("Biology");
		departmentList.add("Mathematics");
		departmentList.add("Chemistry");
		departmentList.add("Arts");
		model.addAttribute("departmentList", departmentList);
		
		return "registration-page";
	}
	
	@RequestMapping(value = "/submission", method = RequestMethod.POST)
	public String submitForm(@Valid @ModelAttribute("registration") Registration registration,
			BindingResult result,
			Model model
	) {
		if (result.hasErrors()) {
			return "registration-page";			
		} else {
			return "confirmation-page";	
		}
	}
	
}
