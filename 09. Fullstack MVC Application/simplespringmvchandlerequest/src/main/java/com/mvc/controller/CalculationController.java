package com.mvc.controller;

import java.sql.SQLException;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class CalculationController {

	@RequestMapping("/calculate")
	public String calculate (
				@RequestParam(name = "number1", required = false) String strNumber1,
				@RequestParam(name = "number2", required = false) String strNumber2,
				Model model
			) throws Exception {
		
		int number1 = 0;
		int number2 = 0;
		
		if (strNumber1 == null || strNumber2 == null || strNumber1.trim().length() == 0 || strNumber2.trim().length() == 0) 
			throw new SQLException("number1 and number2 should not be empty");
		
		number1 = (int) new Integer(strNumber1.trim());
		number2 = (int) new Integer(strNumber2.trim());
		
		
		String message = String.format("Here are your calculation result for : %d and %d ", number1, number2);
		
		model.addAttribute("message", message);
		model.addAttribute("sum", number1 + number2);
		model.addAttribute("subtract", number1 - number2);
		model.addAttribute("multiply", number1 + number2);
		model.addAttribute("divide", number1 / number2);		
		
		return "result_calculation";
	}
	
	@ExceptionHandler({Exception.class, SQLException.class})
	public ModelAndView handleException(Exception ex) {
		ModelAndView model = new ModelAndView("error");
		model.addObject("exceptionMessage", ex.getMessage());
		return model;
	}
}
