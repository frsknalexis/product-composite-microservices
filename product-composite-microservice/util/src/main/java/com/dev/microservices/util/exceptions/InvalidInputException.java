package com.dev.microservices.util.exceptions;

public class InvalidInputException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8157386753260629795L;

	public InvalidInputException(String message) {
		super(message);
	}
	
	public InvalidInputException(String message, Throwable cause) {
		super(message, cause);
	}
	
	public InvalidInputException(Throwable cause) {
		super(cause);
	}
}