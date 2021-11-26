package com.mytutorial.springbootrestapi.exception;

public class ProductNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public ProductNotFoundException(Long id) {
		super(String.format("The prodduct with %d cannot be found", id));
	}

}
