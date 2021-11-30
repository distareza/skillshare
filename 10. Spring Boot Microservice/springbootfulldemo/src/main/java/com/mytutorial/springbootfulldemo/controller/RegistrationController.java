package com.mytutorial.springbootfulldemo.controller;

import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.mytutorial.springbootfulldemo.model.User;
import com.mytutorial.springbootfulldemo.service.EmailService;
import com.mytutorial.springbootfulldemo.service.UserService;

@Controller
public class RegistrationController {

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private EmailService emailService;
	
	@GetMapping("/register")
	public ModelAndView showRegistrationPage(ModelAndView modelAndView, User user) {
		modelAndView.addObject("user", user);
		modelAndView.setViewName("register");
		return modelAndView;
	}
	
	@PostMapping("/register")
	public ModelAndView processRegisterForm(ModelAndView modelAndView,
			@Valid User user, BindingResult bindingResult, HttpServletRequest request) {
		User userExists = userService.findByEmail(user.getEmail());
		System.out.println(userExists);
		
		if (userExists!=null) {
			modelAndView.addObject("alreadyRegisteredMessage", 
					"Oops! There is already a user registered with the email provided.");
			modelAndView.setViewName("register");
			bindingResult.reject("email");
		}
		
		if (bindingResult.hasErrors()) {
			modelAndView.setViewName("register");
		} else {
			user.setEnabled(false);
			user.setConfirmationToken(UUID.randomUUID().toString());
			userService.saveUser(user);
			
			String applUrl = String.format("%s://%s:8080", request.getScheme(), request.getServerName()); 
			String message = String.format("To set your password, please click on the link below: \n %s/confirm?token=%s ",
					applUrl, user.getConfirmationToken()
					);
			emailService.sendEmail(user.getEmail(), "please set a password", message);
			
			modelAndView.addObject("confirmationMessage", 
					String.format("A password set e-mail has been sent to %s", user.getEmail()));
			modelAndView.setViewName("register");			
		}
		return modelAndView;
	}	
	
	@GetMapping("/confirm")
	public ModelAndView confirmRegistration(ModelAndView modelAndView, 
			BindingResult bindingResult, @RequestParam("token") String token) {
		User user = userService.findByConfirmationToken(token);
		if (user == null) {
			modelAndView.addObject("invalidToken", "Invalid confirmation link.");
		} else {
			modelAndView.addObject("confirmationToken", user.getConfirmationToken());
		}
		
		modelAndView.setViewName("confirm");
		return modelAndView;
	}

	@PostMapping("/confirm")
	public ModelAndView confirmRegistration(ModelAndView modelAndView,
			BindingResult bindingResult, @RequestParam Map<String, String> requestParam,
			RedirectAttributes redir
			) {
		User user = userService.findByConfirmationToken(requestParam.get("token"));
		user.setPassword(bCryptPasswordEncoder.encode(requestParam.get("password")));
		user.setEnabled(true);
		userService.saveUser(user);
		
		modelAndView.setViewName("confirm");
		modelAndView.addObject("successMessage", "Password set successfully");
		
		return modelAndView;
	}
			
}
