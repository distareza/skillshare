package com.mvc.annualsciencefair.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.mvc.annualsciencefair.model.Login;
import com.mvc.annualsciencefair.model.User;
import com.mvc.annualsciencefair.service.UserService;

@Controller
public class LoginController {

	@Autowired
	private UserService userService;

	@RequestMapping(value = "/performLogin", method = RequestMethod.GET)
	public ModelAndView showLogin(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mv = new ModelAndView("login");
		mv.addObject("login", new Login());
		return mv;
	}
	
	@RequestMapping(value = "/performLogin", method = RequestMethod.POST)
	public ModelAndView loginProcess(
			HttpServletRequest request, HttpServletResponse response,
			@ModelAttribute("login") Login login, Model model ) {
		
		ModelAndView mv = null;
		User user = userService.validateUser(login);
		
		if (user != null) {
			model.addAttribute("user", user);
			mv = new ModelAndView("welcome");
		} else {
			mv = new ModelAndView("login");
			mv.addObject("message", "User or Password is wrong, Please try again.");
		}
		
		return mv;
	}
}
