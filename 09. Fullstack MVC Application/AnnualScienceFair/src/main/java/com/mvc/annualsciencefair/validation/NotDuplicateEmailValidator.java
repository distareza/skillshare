package com.mvc.annualsciencefair.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mvc.annualsciencefair.service.UserService;

@Component
public class NotDuplicateEmailValidator implements ConstraintValidator<NotDuplicateEmail, String> {

	@Autowired
	private UserService userService;
	
	public void initialize(NotDuplicateEmail notExistingEmail) {		
	}
	
	@Override
	public boolean isValid(String email, ConstraintValidatorContext context) {		
		return !userService.doesEmailExists(email);
	}
	
}
