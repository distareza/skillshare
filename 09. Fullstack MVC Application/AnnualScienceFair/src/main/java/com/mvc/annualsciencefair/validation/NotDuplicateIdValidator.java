package com.mvc.annualsciencefair.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mvc.annualsciencefair.service.UserService;

@Component
public class NotDuplicateIdValidator implements ConstraintValidator<NotDuplicateId, Integer>{

	@Autowired
	private UserService userService;
	
	public void initialize(NotDuplicateId notExistingId) {		
	}
	
	@Override
	public boolean isValid(Integer studentId, ConstraintValidatorContext context) {
		return !userService.doesIdExists(studentId);
	}

}
