package com.mvc.annualsciencefair.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.mvc.annualsciencefair.model.User;
import com.mvc.annualsciencefair.service.UserService;

@Controller
public class RegistrationController {
	
	@Autowired
	private UserService userService;
	
	@RequestMapping(value = "/performRegistration", method = RequestMethod.GET)
	public ModelAndView showRegister(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mv = new ModelAndView("register");
		mv.addObject("user", new User());
		return mv;
	}
	
	@RequestMapping(value = "/performRegistration", method = RequestMethod.POST)
	public String submitRegistrationForm(
				@Valid @ModelAttribute("user") User user,
				BindingResult bindingResult
			) {
		
		if (bindingResult.hasErrors()) {
			return "register";
		}
		
		userService.register(user);
		
		return "redirect:performLogin";
	}

}
